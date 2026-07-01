package core;

import core.record.HttpMessage;
import core.record.HttpMessageType;
import core.record.HttpRequest;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import static core.record.Constants.CRLF;

/**
 *
 * Package Name: core
 * File Name: ClientHandler
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
public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final InputStream inputStream;
    private final OutputStream outputStream;
    private HttpRequest httpRequest;
    private HttpRequest httpResponse;

    // Constructor
    ClientHandler(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.inputStream = clientSocket.getInputStream();
        this.outputStream = clientSocket.getOutputStream();
    }

    @Override
    public void run() {
        System.out.println("""  
            ClientHandler is running...getAllThread() : %s  
            """
            .formatted(Thread.getAllStackTraces()));

        // 파싱 + etc + 응답
        try {
            StringBuilder requestMessage = new StringBuilder();
            String requestLine;
            boolean transferEncodingChunked = false;
            int contentLength = 0;
            // 1. request startLine
            String requestStartLine = inputStream
            byte

            // byte stream parsing
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        // 자원해제
        try {
            inputStream.close();
            outputStream.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        // http message를 바이트스트림으로부터 읽어서 가공
        HttpRequest httpRequest = new HttpRequest("", "", "", new HashMap<>());
        Map<String, Object> rawHttp = parse2Http(new BufferedInputStream(clientSocket.getInputStream()));


    }

    private void readRequest() {
//        BufferedInputStream inputStream = new BufferedInputStream(this.clientSocket.getInputStream());

        // http request message 파싱
        StringBuilder requestMessage = new StringBuilder();
        String requestLine;
        boolean transferEncodingChunked = false;
        int contentLength = 0;
        // 1. request startLine
        String requestStartLine = bufferedReader.readLine();
        if (requestStartLine == null || requestStartLine.isBlank()) {
            throw new IOException("Empty http request startLine.");
        }
        String[] requestStartLineParts = requestStartLine.split(" ", 3);
        if (requestStartLineParts.length != 3) {
            throw new IllegalArgumentException("Invalid HTTP Request Start Line.");
        }
        String method = requestStartLineParts[0];
        String path = requestStartLineParts[1];
        String version = requestStartLineParts[2]; // http version
        Map<String, String> headers = new HashMap<>();

        requestMessage
            .append(requestStartLineParts[0])
            .append(" ")
            .append("/backend")
            .append(path)
            .append(" ")
            .append(requestStartLineParts[2])
            .append(CRLF);
        // 2. 헤더
        while (true) {
            requestLine = bufferedReader.readLine();
            // 빈 줄 만나기 전에 EOF 나오면 정상적으로 온 메시지가 아님
            if (requestLine == null) {
                throw new EOFException("Unexpected EOF while reading http request headers.");
            }
            // 빈 줄 만나면 헤더 끝
            if (requestLine.isBlank())
                break;
            // Host 헤더는 Backend Host로 변경할 것이므로 pass
            if (requestLine.toLowerCase().startsWith("host"))
                continue;
            // body framing을 위해 content-length 저장
            if (!transferEncodingChunked && requestLine.toLowerCase().startsWith("content-length"))
                contentLength = Integer.parseInt(requestLine.split(":")[1].trim());
            // transfer-encoding: chunked 여부 저장
            if (requestLine.toLowerCase().startsWith("transfer-encoding"))
                transferEncodingChunked = requestLine.split(":")[1].trim().equalsIgnoreCase("chunked");
            requestMessage
                .append(requestLine)
                .append(CRLF);
        }
        // Host 헤더는 Backend Host로 변경
        requestMessage
            .append("Host: ")
            .append(BACKEND_HOST)
            .append(CRLF);
        // 헤더 끝을 의미하는 개행 (헤더 없어도, 마지막에 개행 필요)
        requestMessage.append(CRLF);
        // 3. 바디
        // 바디있는지 판단 = chunked false, content-length 0 이상
        if (!transferEncodingChunked && contentLength > 0) {
            char[] body = new char[contentLength];
            bufferedReader.read(body, 0, contentLength);
            requestMessage
                .append(new String(body))
                .append(CRLF);
        }
        System.out.println("requestMessage = " + requestMessage);
        return requestMessage.toString();
    }
}
