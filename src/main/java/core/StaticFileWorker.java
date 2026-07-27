package core;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Map;

public class StaticFileWorker implements Worker {
    @Override
    public void process(WorkOrder workOrder, OutputStream outputStream) throws BareException, IOException {
        String resourcePath = workOrder.getResourcePath();
        ContentType contentType = workOrder.getContentType();
        if (resourcePath == null || resourcePath.isBlank() || contentType == null)
            throw new IllegalArgumentException("StaticFileWorker failed: illegal workOrder.");

        File file = FileManager.loadFile(resourcePath);
        if (!file.isFile())
            throw new BareException(404, "StaticFileWorker failed: resource not found.");

        Response response = new Response(200, createBody(file, contentType));
        Header header = new Header(
            Map.of("Content-Type", contentType.getMIMEType())
        );
        response.setHeader(header);
        sendResponse(response, outputStream);
    }

    private ResponseBody createBody(File file, ContentType contentType) throws IOException {
        if (!contentType.isText())
            return new FileBody(file);

        return new TextBody(Files.readString(file.toPath(), StandardCharsets.UTF_8));
    }
}
