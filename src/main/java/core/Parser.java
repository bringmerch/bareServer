package core;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static core.Constants.CRLF;

/**
 *
 * Package Name: core
 * File Name: Parser
 * Description:
 *  1. byte stream 형태의 http message를 Request 객체로 parsing
 *  2. Request 객체를 byte stream 형태의 http message로 parsing
 * author: munke
 *
 * @version 1.0
 * @see core
 * @since 2026-07-02
 * <p>
 * Modification Information
 * 수정일          수정자                    수정내용
 * --------- ------------------- -------------------------------
 * 2026-07-02        munke                   최초개정
 */
public class Parser {
    ByteBuffer byteBuffer;

    Parser() {
        byteBuffer = ByteBuffer.allocate(8192);
    }

    public Request parse(InputStream inputStream) {
        1. stream.read(buffer)
        2. buffer.flip( read mode 전환 )

        ByteBuffer byteBuffer = new ByteBuffer();
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

    public String parse(Response response) {
        return "";

    }

    // 한바이트씩 parser에 먹임.
    public void accept(byte b) {

    }
}
