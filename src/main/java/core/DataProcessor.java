package core;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;

/**
 *
 * Package Name: core
 * File Name: Reader
 * Description:
 * author: munke
 *
 * @version 1.0
 * @see core
 * @since 2026-07-13
 * <p>
 * Modification Information
 * 수정일          수정자                    수정내용
 * --------- ------------------- -------------------------------
 * 2026-07-13        munke                   최초개정
 */
public class DataProcessor {
    final ByteBuffer inputBuffer;
    final InputStream inputStream; // byte 단위 읽는 용
    final BufferedReader bufferedReader; // line 단위 읽는 용
    final OutputStream outputStream;

    DataProcessor(Socket clientSocket) throws IOException {
        this.inputBuffer =  ByteBuffer.allocate(8192); // 버퍼 생성
        this.inputBuffer.flip(); // 버퍼를 읽기모드로 전환

        this.inputStream = clientSocket.getInputStream();
        this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        this.outputStream = clientSocket.getOutputStream();
        // 얘네 다 readCommon에서 해도 상관없음......
    }

    // startLine, header 읽어서 request 세팅
    public Request readCommon(Socket clientSocket) throws IOException {
        Request request = new Request();
        HeaderParser headerParser = new HeaderParser();
        StartlineParser startlineParser = new StartlineParser();
        String startline = startlineParser.read(this);
        String headers = headerParser.read(this);
        headerParser.parse(headers, request); // parser는 String만 던져서 파싱만 맡겨라 !!
        startlineParser.parse(startline, request);
        request.setIsParsed(true);
        return request;
    }

    public String read(DataProcessor dataProcessor) throws IOException {
        StringBuilder lines = new StringBuilder();
        String readLine;

        while (true) {
            readLine = dataProcessor.readLine();

            if (readLine == null || readLine.isBlank())
                break;

            lines.append(readLine);
            lines.append(Constants.CRLF.getValue());
        }

        if (lines.isEmpty()) {
            throw new RuntimeException("invalid header lines.");
        }

        System.out.println("Headers : " + lines);
        return lines.toString();
    }

    // LINE 읽기
    public String readLine() throws IOException {
        return this.bufferedReader.readLine();
    }

    public void close() {
        closeInputStream();
        closeOutputStream();
    }

    public void closeInputStream() {
        try {
            this.bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeOutputStream() {
        try {
            this.outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
