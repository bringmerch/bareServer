package core;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ErrorWorker extends StaticWorker {
    private static final String ERROR_PAGE_DIR = "/static/html/error/";

    @Override
    public void execute(WorkOrder workOrder,OutputStream outputStream) throws BareException, IOException {
        if (workOrder == null || workOrder.getStatusCode() == 0)
            throw new IllegalArgumentException("execute failed: wrong workOrder.");
        if (outputStream == null)
            throw new IllegalArgumentException("execute failed: outputStream is null.");

        int statusCode = workOrder.getStatusCode();

        File file = loadFile(ERROR_PAGE_DIR + statusCode + ".html");
        if (!file.isFile())
            throw new BareException(404, "TextWorker failed: resource not found.");

        writeHeader(statusCode, ContentType.TEXT_HTML.getMIMEType(), outputStream);
        writeBody(file, outputStream);
    }
}
