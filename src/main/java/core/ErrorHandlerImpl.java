package core;

import core.body.FileBody;
import core.model.Response;
import core.type.ContentType;

import java.io.File;
import java.io.IOException;

public class ErrorHandlerImpl extends Handler {
    private final String ERROR_PAGE_DIR = "/static/html/error/";

    public void serveErrorPage(int statusCode, Response response) throws IOException {
        statusCode = statusCode == 0 ? 500 : statusCode;
        response.setStatusCode(statusCode);

        File file = super.getFile(RESOURCE_LOCATION + ERROR_PAGE_DIR + statusCode + ".html");

        if (!file.isFile())
            throw new IOException("serveErrorPage: resource not found.");

        response.setBody(new FileBody(file, ContentType.TEXT_HTML));
    }
}
