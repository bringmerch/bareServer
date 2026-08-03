package core.handler;

import core.BareException;
import core.handlerResult.FileResult;
import core.handlerResult.HTMLResult;
import core.handlerResult.HandlerResult;
import core.model.Request;
import core.model.Response;
import core.type.ContentType;

import java.io.File;

/**
 *
 * Package Name: core
 * File Name: StaticHandler
 * Description:
 * author: munke
 *
 * @version 1.0
 * @see core
 * @since 2026-08-03
 * <p>
 * Modification Information
 * 수정일          수정자                    수정내용
 * --------- ------------------- -------------------------------
 * 2026-08-03        munke                   최초개정
 */
public class StaticHandler implements Handler {
    HandlerResult serve(Request request, Response response) throws BareException {
        String resourcePath = request.getResourcePath();
        if (request.getResourcePath().isBlank())
            throw new BareException(404, "serve failed: no resource.");
//
//        String resourcePath = Static.getResourcePath();
//        ContentType contentType = workOrder.getContentType();
//        if (resourcePath == null || resourcePath.isBlank() || contentType == null)
//            throw new IllegalArgumentException("execute failed: illegal workOrder.");
//
//        File file = loadFile(resourcePath);
//        if (!file.isFile())
//            throw new BareException(404, "execute failed: resource not found.");
//
//        writeHeader(200, contentType.getMIMEType(), outputStream);
//        writeBody(file, outputStream);
    }

}
