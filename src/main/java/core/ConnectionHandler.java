package core;

import java.io.*;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URI;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.io.File;

/**
 *
 * Package Name: core
 * File Name: ConnectionHandler
 * Description:
 * author: munke
 *
 * @version 1.0
 * @see core
 * @since 2026-07-01
 * <p>
 * Modification Information
 * 수정일          수정자                    수정내용
 * --------- ------------------- -------------------------------
 * 2026-07-01        munke                   최초개정
 */
public class ConnectionHandler implements Runnable {
    private final Socket clientSocket;
    private final InputStream inputStream;
    private final BufferedOutputStream bufferedOutputStream;
    private final ByteBuffer inputBuffer;
    private Request request;
    private Response response;
    private final FileManager fileManager;

    ConnectionHandler(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.inputStream = getClientInputStream();
        this.bufferedOutputStream = new BufferedOutputStream(getClientOutputStream(), 8192);
        this.inputBuffer = ByteBuffer.allocate(8192);
        this.inputBuffer.flip();
        fileManager = new FileManager();
    }

    public void run() {
        while (!clientSocket.isClosed()) {
           this.request = new Request();

            System.out.println("run....");
            // 1. 요청 프레이밍
            // TODO 쓰레기요청 무시
            // TODO 요청 body 파싱
            if (!frame()) {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    throw new RuntimeException("request framing failed.");
                }
            }

            // 2. 비즈니스로직
            // TODO 어댑터 호출
            // Adapter adapter = new Adapter();
            // String response = parser.parse(adapter.deliver(this.request));

            // 3. 응답
            try {
                this.writeResponse();
            } catch (IOException e) {
                throw new RuntimeException("writeResponse failed.");
            }

            // 4. 다음 요청 준비
            // TODO 같은 클라이언트(host:port)의 다음 요청 들을 준비
            // if (keepAlive)
        }
    }

    private boolean frame() {
        System.out.println("frame...");
        String rawStartLine = this.readStartLine();
        String rawHeader = this.readHeader();
        parseHeader(rawHeader);
        parseStartLine(rawStartLine);

        // TODO body 읽기

        return true;
    }

    private int fillInputBuffer() {
        byte[] tempByteArr = new byte[this.inputBuffer.capacity()];
        int read;

        this.inputBuffer.clear();

        try {
            read = inputStream.read(tempByteArr);
            if (read > 0)
                this.inputBuffer.put(tempByteArr, 0, read);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return read;
    }

    private String readStartLine() throws RuntimeException {
        byte b;
        StringBuilder rawStartLine = new StringBuilder();
        boolean fin = false; // start line 읽기 종료 여부
        int read = this.inputBuffer.remaining();
        int remaining;

        if (read == 0) {
            read = this.fillInputBuffer();

            if (read <= 0)
                throw new RuntimeException("invalid http message start line. empty message.");

            this.inputBuffer.flip(); // 읽기 모드 진입
        }

        while (!fin) {
            if (read == 0) {
                read = this.fillInputBuffer();

                if (read <= 0)
                    break;

                this.inputBuffer.flip(); // 읽기모드 진입
            }

            remaining = read;
            int processed = 0;

            for (int i = 0; i < remaining; i++) {
                b = this.inputBuffer.get();
                processed++;

                if (b == '\r') // \r 뒤에 어차피 \n 오니까 먹는다.
                    continue;

                if (b == '\n') {
                    fin = true;
                    break;
                }

                rawStartLine.append((char)b);
            }

            read = remaining == processed ? 0 : remaining - processed;
        }

        if (rawStartLine.isEmpty())
            throw new RuntimeException("invalid http message start line.");

        return rawStartLine.toString();
    }

    private void parseStartLine(String rawStartLine) {
        String[] parts = rawStartLine.split(" ");

        if (parts.length != 3)
            throw new RuntimeException("invalid http message start line. start line must be 3 parts.");

        this.request.setMethod(Method.from(parts[0]));
        URI uri;
        URL url;

        List<Header> hosts = this.request.getHeaders("Host");
        if (hosts.size() != 1)
            throw new RuntimeException("invalid host header.");
        String host = hosts.getFirst().fieldValue();

        String path = parts[1];
        boolean isAbsoluteUrl = path.startsWith("http://") || path.startsWith("https://");
        boolean isPath = !isAbsoluteUrl && path.startsWith("/");

        if (!(isAbsoluteUrl || isPath))
            throw new RuntimeException("invalid http message start line. path must start with / or scheme.");

        String urlString = isPath ? host + path : path;

        try {
            uri = URI.create("http://" + urlString);
            url = uri.toURL();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new RuntimeException("URL parsing failed.");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("MalformedUrlException occurred.");
        }

        this.request.setPath(url.getPath());
        String query = url.getQuery();

        if (query != null) {
            String[] pairs = query.split("&");

            for (String pair : pairs) {
                String[] keyValue = pair.split("=");
                this.request.addQueryString(new QueryString(keyValue[0], keyValue[1]));
            }
        }

        if (!parts[2].equalsIgnoreCase("HTTP/1.1"))
            throw new RuntimeException("invalid http version. must be HTTP/1.1.");
    }

    private String readHeader() throws RuntimeException {
        System.out.println("readHeader...");
        byte b;
        StringBuilder rawHeaderLines = new StringBuilder();
        boolean fin = false; // header 읽기 종료 여부
        int read = this.inputBuffer.remaining();
        int remaining;

        if (read == 0) {
            read = this.fillInputBuffer();

            if (read <= 0)
                throw new RuntimeException("invalid header lines. at least one header required.");

            this.inputBuffer.flip(); // 읽기 모드 진입
        }

        while (!fin) {
            if (read == 0) {
                read = this.fillInputBuffer();

                if (read <= 0)
                    break;

                this.inputBuffer.flip(); // 읽기 모드 진입
            }

            remaining = read;
            int processed = 0;

            boolean prevWasNewLine = false;

            for (int i = 0; i < remaining; i++) {
                b = this.inputBuffer.get();
                processed++;

                if (b == '\r') // \r 뒤에 어차피 \n 오니까 먹는다.
                    continue;

                rawHeaderLines.append((char)b);

                if (b == '\n') {
                    if (prevWasNewLine) { // 두번연속 new line 나오면 헤더 읽기 종료
                        fin = true;
                        break;
                    }
                    prevWasNewLine = true;
                } else {
                    prevWasNewLine = false;
                }
            }

            read = remaining == processed ? 0 : remaining - processed;
        }

        if (rawHeaderLines.isEmpty()) {
            throw new RuntimeException("invalid header lines.");
        }

        return rawHeaderLines.toString();
    }

    private void parseHeader(String rawHeader) {
        String[] headers = rawHeader.split("\n");

        System.out.println("rawHeader = " + rawHeader);

        for (String h : headers) {
            String[] pair = h.split(":", 2);
            if (pair.length != 2)
                throw new RuntimeException("invalid Header. parsing failed.");
            String fieldName = pair[0].trim();
            String fieldValue = pair[1].trim();
            //TODO 멀티밸류
            this.request.addHeader(new Header(fieldName, fieldValue));
        }
    }

    private InputStream getClientInputStream() throws IOException {
        return this.clientSocket.getInputStream();
    }

    private OutputStream getClientOutputStream() throws IOException {
        return this.clientSocket.getOutputStream();
    }

    private void writeResponse() throws IOException {
        System.out.println("writeResponse....");
        File file;
        FileInputStream fileInputStream;

        try {
            file = this.fileManager.loadHtmlFile("/hello.html");
            fileInputStream = new FileInputStream(file);
        } catch (IOException e) {
            throw new RuntimeException("load html file failed.");
        }

        if (file == null || fileInputStream == null)
            throw new RuntimeException("file reading failed.");

        long contentLength = file.length();
        long sent = 0;

        StringBuilder sb = new StringBuilder();
        sb.append("""
                HTTP/1.1 200\r
                Content-Type: text/plain\r
                Content-Length: %d\r
                \r
                """.formatted(contentLength));
        this.bufferedOutputStream.write(sb.toString().getBytes(StandardCharsets.UTF_8));

        System.out.println("response header ===> " + sb.toString().getBytes(StandardCharsets.UTF_8));

        while(true) {
            byte[] temp = new byte[8192];
            int read = fileInputStream.read(temp);
            if(read > 0)
                this.bufferedOutputStream.write(temp, 0, read);
            else
                break;

            sent = sent + read;

            if (sent == contentLength)
                break;

            if (sent > contentLength)
                throw new RuntimeException("sent can't exceed contentLength.");

            System.out.println(new String(temp, StandardCharsets.UTF_8));
        }
        fileInputStream.close();
        this.bufferedOutputStream.flush();
    }
}
