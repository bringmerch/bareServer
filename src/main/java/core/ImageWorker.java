package core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 *
 * Package Name: core
 * File Name: ImageWorker
 * Description:
 * author: munke
 *
 * @version 1.0
 * @see core
 * @since 2026-07-14
 * <p>
 * Modification Information
 * 수정일          수정자                    수정내용
 * --------- ------------------- -------------------------------
 * 2026-07-14        munke                   최초개정
 */
public class ImageWorker implements Worker<ByteArrayWrapper> {
    final ContentType contentType = ContentType.IMAGE_JPEG;

    @Override
    public void execute(Request request, DataProcessor dataProcessor) throws IOException {
        Response response = this.doProcess(request);
        Writer.writeByte(response, dataProcessor);
    }

    @Override
    public Response doProcess(Request request) throws IOException {
        if (request.getResponseStatusCode() > 0) {
            return this.getErrorResponse(request);
        } else {
            Response<ByteArrayWrapper> response = new Response(this.doGet(request));
            response.setStatusCode(200);
            response.addHeader(new Header("Content-Type", contentType.value));
            return response;
        }
    }

    @Override
    public ByteArrayWrapper doGet(Request request) throws IOException {
        FileManager fileManager = new FileManager();
        File file = fileManager.loadFile(contentType.resourceDir + request.getPath() + contentType.extension);
        FileInputStream fileInputStream = new FileInputStream(file);
        ByteArrayWrapper body = new ByteArrayWrapper(fileInputStream.readAllBytes());
        fileInputStream.close();
        return body;
    }

    @Override
    public void doPost() throws IOException {}

    @Override
    public void doPut() throws IOException {}

    @Override
    public void doDelete() throws IOException {}

    @Override
    public ContentType getContentType() {
        return this.contentType;
    }
}
