package core;

import java.io.File;
import java.util.Map;

public class StaticFileWorker implements Worker {
    @Override
    public Response execute(WorkOrder workOrder) throws BareException {
        String resourcePath = workOrder.getResourcePath();
        ContentType contentType = workOrder.getContentType();
        if (resourcePath == null || resourcePath.isBlank() || contentType == null)
            throw new IllegalArgumentException("StaticFileWorker failed: illegal workOrder.");

        File file = FileManager.loadFile(resourcePath);
        if (!file.isFile())
            throw new BareException(404, "StaticFileWorker failed: resource not found.");

        Response response = new Response(200, new FileBody(file));
        Header header = new Header(
            Map.of("Content-Type", contentType.getMIMEType())
        );
        response.setHeader(header);
        return response;
    }
}
