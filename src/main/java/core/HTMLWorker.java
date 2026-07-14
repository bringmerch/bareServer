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
public class HTMLWorker implements Worker<String> {
    final ContentType contentType = ContentType.TEXT_HTML;

    @Override
    public void execute(Request request, DataProcessor dataProcessor) throws IOException {
        Response response = this.doProcess(request);
        Writer.writeString(response, dataProcessor);
    }

    @Override
    public Response doProcess(Request request) throws IOException {
        if (request.getResponseStatusCode() > 0) { // 에러 코드 세팅해서 들어오는 애는 에러페이지 리턴
            Response response = new Response(this.doError(request));
            response.setStatusCode(request.getResponseStatusCode());
            response.addHeader(new Header("Content-Type", contentType.value));
            return response;
        } else {
            Response response = new Response(this.doGet(request));
            response.setStatusCode(200);
            response.addHeader(new Header("Content-Type", contentType.value));
            return response;
        }
    }

    @Override
    public String doGet(Request request) throws IOException {
        FileManager fileManager = new FileManager();
        File file = fileManager.loadFile(contentType.resourceDir + request.getPath() + contentType.extension);
        FileInputStream fileInputStream = new FileInputStream(file);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line;
        String responseBody = "";

        try {
            while ((line = bufferedReader.readLine()) != null) {
                responseBody += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        bufferedReader.close();
        return responseBody;
    }

    @Override
    public void doPost() throws IOException {}

    @Override
    public void doPut() throws IOException {}

    @Override
    public void doDelete() throws IOException {}
}
