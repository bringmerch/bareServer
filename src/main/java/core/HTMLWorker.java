package core;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 *
 * Package Name: core
 * File Name: HTMLWorker
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
public class HTMLWorker implements Worker {
    Request request;
    Response response;
    DataProcessor dataProcessor;

    @Override
    public void execute() {
        try {
            this.request = new Request();
            this.response = new Response<String>();
            this.read();
            this.doGet();
            this.write();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void read() throws IOException {
        this.dataProcessor = new DataProcessor(this.request.clientSocket);
        HeaderParser headerParser = new HeaderParser();
        StartlineParser startlineParser = new StartlineParser();
        String headers = headerParser.read(dataProcessor);
        String startline = startlineParser.read(dataProcessor);
        headerParser.parse(headers, this.request);
        startlineParser.parse(startline, this.request);
        this.request.setIsParsed(true); // request 파싱 완료
    }

    @Override
    public void doGet() throws IOException {
        ContentType contentType = ContentType.TEXT_HTML;
        this.response.addHeader(new Header("Content-Type", contentType.getValue()));

        FileManager fileManager = new FileManager();
        File file = fileManager.loadFile(contentType.resourceDir + this.request.getPath() + contentType.getExtension());
        FileInputStream fileInputStream = new FileInputStream(file);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line;
        String content = "";

        while ((line = bufferedReader.readLine()) != null) {
            content += line;
        }

        this.response.addHeader(new Header("Content-Length", String.valueOf(content.getBytes(StandardCharsets.UTF_8))));
        this.response.setBody(content);
        this.response.setStatusCode(200);

        bufferedReader.close();
        this.dataProcessor.inputStream.close();
    }

    @Override
    public void write() throws IOException {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.dataProcessor.outputStream, StandardCharsets.UTF_8);
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

        StringBuilder sb = new StringBuilder();

        sb.append("""
                HTTP/1.1 %d\r
                Content-Type: %s\r
                Content-Length: %d\r
                \r
                """
            .formatted(this.response.getStatusCode(), this.response.getHeader("Content-Type"), this.response.getHeader("Content-Length")));

        bufferedWriter.write(sb.toString());
        bufferedWriter.write((String)this.response.getBody());
        bufferedWriter.flush();

        bufferedWriter.close();
    }

    @Override
    public Request getRequest() {
        return this.request;
    }

    @Override
    public Response getResponse() {
        return this.response;
    }

    @Override
    public DataProcessor getDataProcessor() {
        return this.dataProcessor;
    }
}
