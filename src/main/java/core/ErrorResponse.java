package core;

import java.io.File;
import java.util.Map;

/**
 *
 * Package Name: core
 * File Name: ErrorResponse
 * Description:
 * author: munke
 *
 * @version 1.0
 * @see core
 * @since 2026-07-22
 * <p>
 * Modification Information
 * 수정일          수정자                    수정내용
 * --------- ------------------- -------------------------------
 * 2026-07-22        munke                   최초개정
 */
public class ErrorResponse {
    private static final String ERROR_PAGE_DIR = "/static/html/error/";

    public static Response create(int statusCode) {
        ResponseBody body = createBody(statusCode);
        Response response = new Response(statusCode, body);
        response.setHeader(new Header(
            Map.of("Content-type", ContentType.TEXT_HTML.getMIMEType())
        ));
        return response;
    }

    private static ResponseBody createBody(int statusCode) {
        File errorPage = FileManager.loadFile(ERROR_PAGE_DIR + statusCode + ".html");
        if (errorPage.isFile())
            return new FileBody(errorPage);

        File fallbackPage = FileManager.loadFile(ERROR_PAGE_DIR + "500.HTML");
        if (fallbackPage.isFile())
            return new FileBody(fallbackPage);

        return new TextBody("""
            <!DOCTYPE html>
                <html lang="en">
                    <head>
                    <meta charset="UTF-8">
                    <title>Internal Server Error</title>
                    </head>
                <body>
                <div>
                <div  style="font-size: 52px; font-weight: bold">
                500 - Internal Server Error
                </div>
                <a href="/index">Go to index page</a>
                </div>
                </body>
                </html>
            """);
    }
}
