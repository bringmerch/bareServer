package core;

import java.io.*;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URI;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

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

/**
 *
 * Request 하나가 완성이 되면 adapter를 호출하고 adapter가 컨트롤러를 호출하게 한다.
 *
 * Adapter로부터 받은 response를 byte로 바꾸고 outputStream에 흘려보내기 위해 OutputBuffer를 이용한다.
 *
 * 응답 다 했으면  request 비운다(recycle) & 버퍼 compact.
 *
 * connection keep 여부에 따라 client 소켓을 닫고 inputBuffer, outputBuffer 날린다(필요시?).
 */
public class ConnectionHandler implements Runnable {
    final Socket clientSocket;
    final InputStream clientInputStream;
    final ByteBuffer inputBuffer;
    final ByteBuffer outputBuffer;
    final Charset defaultCharSet = StandardCharsets.UTF_8;
    private Request request;

    ConnectionHandler(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.clientInputStream = getClientInputStream();
        inputBuffer = ByteBuffer.allocate(8192);
        outputBuffer = ByteBuffer.allocate(8192);
    }

    public void run() {
        while (!clientSocket.isClosed()) {
           if (this.request == null) {
               this.request = new Request();
           }
           if (!frame())
               break;

            // TODO 어댑터 호출
            // Adapter adapter = new Adapter();
            // String response = parser.parse(adapter.deliver(this.request));

            // TODO 응답 쓰기
            // try {
            //     bufferedWriter.write(response);
            //     bufferedWriter.flush();
            // } catch (IOException e) {
            //     throw new RuntimeException(e);
            // }

            // TODO request 재활용, 다음 요청 들을 준비
            // if (keepAlive)
            // this.nextRequest();
        }
    }

    private int fillBuffer() {
        byte[] tempByteArr = new byte[inputBuffer.remaining()];

        try {
            int read = clientInputStream.read(tempByteArr);

            if (read > 0)
                inputBuffer.put(tempByteArr, 0, read);

            return read;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private boolean frame() {
        String rawStartLine = this.readStartLine();
        parseStartLine(rawStartLine);

        String rawHeader = this.readHeader();
        parseHeader(rawHeader);

        // TODO body 읽기

        return true;
    }

    private String readStartLine() throws RuntimeException {
        byte b;
        StringBuilder rawStartLine = new StringBuilder();
        boolean metNewLine = false; // start line의 끝을 의미하는 개행문자 읽었는지

        while (true) {
            int read = this.fillBuffer();

            if (read <= 0)
                break;

            inputBuffer.flip(); // 읽기모드 진입

            for (int i = 0; i < read ; i++) {
                b = inputBuffer.get();

                if (b == '\r') // \r 뒤에 어차피 \n 오니까 먹는다.
                    continue;

                if (b == '\n') { // 개행문자까지 읽는다.
                    metNewLine = true;
                    break;
                } else {
                    rawStartLine.append((char)b);
                }
            }
        }

        if (rawStartLine.isEmpty() || !metNewLine)
            throw new RuntimeException("invalid http message start line");

        return rawStartLine.toString();
    }

    private void parseStartLine(String rawStartLine) {
        String[] parts = rawStartLine.toString().split(" ");

        if (parts.length != 3)
            throw new RuntimeException("invalid http message start line");

        this.request.setMethod(Method.from(parts[0]));

        try {
            URI uri = URI.create(parts[1]);
            URL url = uri.toURL();
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
                throw new RuntimeException("invalid http version.");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new RuntimeException("URL parsing failed.");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("MalformedUrlException occurred.");
        }
    }

    private String readHeader() throws RuntimeException {
        byte b;
        StringBuilder rawHeaderLines = new StringBuilder();
        int newLineCounter = 0;

        while (true) {
            int read = this.fillBuffer();

            if (read <= 0)
                break;

            inputBuffer.flip(); // 읽기모드 진입

            for (int i = 0 ; i < read ; i++) {
                b = inputBuffer.get();

                if (b == '\r') // \r 뒤에 어차피 \n 오니까 먹는다.
                    continue;

                rawHeaderLines.append((char)b);

                if (b == '\n' && ++newLineCounter >= 2)  // 개행문자 = 해당 headerLine 끝
                    break;
            }
        }

        if (newLineCounter != 2 || rawHeaderLines.isEmpty()) {
            throw new RuntimeException("invalid header lines.");
        }

        return rawHeaderLines.toString();
    }

    private void parseHeader(String rawHeader) {
        String[] headers = rawHeader.split("\n");

        for (String h : headers) {
            String[] pair = h.split(":");
            if (pair.length != 2)
                throw new RuntimeException("invalid Header. parsing failed.");
            String fieldName = pair[0];
            String fieldValue = pair[1];
            List<Header.FieldValue> fieldValues = new ArrayList<>();

            for (String member : fieldValue.split(",")) {
                List<String> parameters = new ArrayList<>();

                for (String parameter : member.split(";")) {
                    parameters.add(parameter.trim());
                }

                fieldValues.add(new Header.FieldValue(member, parameters));
            }

            this.request.addHeader(new Header(fieldName, fieldValues));
        }
    }

    private InputStream getClientInputStream() throws IOException {
        return this.clientSocket.getInputStream();
    }
}
