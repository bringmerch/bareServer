package core;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class StaticFileWorker implements Worker {
    private final String resourcePath;
    private final ContentType contentType;

    StaticFileWorker(String resourcePath, ContentType contentType) {
        if (resourcePath == null || resourcePath.isBlank())
            throw new IllegalArgumentException("StaticFileWorker constructor failed: empty resourcePath.");
        if (contentType == null)
            throw new IllegalArgumentException("StaticFileWorker constructor failed: empty contentType.");

        this.resourcePath = resourcePath;
        this.contentType = contentType;
    }

    @Override
    public Response execute(WorkOrder workOrder) throws BareException {
        if (workOrder.resourcePath() == null || workOrder.contentType() == null)
            throw new IllegalArgumentException("StaticFileWorker failed: illegal workOrder.");

        File file = FileManager.loadFile(resourcePath);
        if (!file.isFile())
            throw new BareException(500, "StaticFileWorker failed: file not found.");
        Response response = new Response(200, new FileBody(file));
        HeaderMap header = new HeaderMap(
            new HashMap<>(
                Map.of("Content-Type", workOrder.contentType().getMIMEType())
            )
        );
        response.setHeader(header);
        return response;
    }
}
