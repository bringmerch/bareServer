package core;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 *
 * Package Name: core
 * File Name: Worker
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
public interface Worker<T> {
    void execute(Request request, DataProcessor dataProcessor) throws IOException;

    Response doProcess(Request request) throws IOException;

    T doGet(Request request) throws IOException;

    void doPost() throws IOException;

    void doPut() throws IOException;

    void doDelete() throws IOException;

    default Response getErrorResponse(Request request) throws IOException {
        Response<String> response = new Response(request.getResponseStatusCode());
        response.addHeader(new Header("Content-Type", ContentType.TEXT_HTML.value));

        FileManager fileManager = new FileManager();


        String line;
        String responseBody = "";

        // 1. null 선언

        try {
            //2. work
            File file = fileManager.loadFile(ContentType.TEXT_HTML.resourceDir + "/error/" + request.getResponseStatusCode() + ContentType.TEXT_HTML.extension);
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            // if null ? throw RuntimeException () : go!

            while ((line = bufferedReader.readLine()) != null) {
                responseBody += line;
            }
        } catch (IOException e) {
            throw e;
        } finally {
            // 3. 여전히 null이면 안댐
            if ()
            bufferedReader.close();
        }

        response.setBody(responseBody);

        return response;
    }

    // impl한테 contentType 멤버변수 요구
    ContentType getContentType();
}
