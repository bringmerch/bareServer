package core;

import core.model.Request;
import core.model.Response;
import core.type.Constants;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

/**
 *
 * Package Name: core.handler
 * File Name: Handler
 * Description:
 * author: munke
 *
 * @version 1.0
 * @since 2026-08-03
 * <p>
 * Modification Information
 * 수정일          수정자                    수정내용
 * --------- ------------------- -------------------------------
 * 2026-08-03        munke                   최초개정
 */
public abstract class Handler {
    protected final String RESOURCE_LOCATION = Constants.USER_DIR + Constants.RESOURCE_ROOT;

    public static void handle(HandlerMapping handlerMapping, Request request, Response response) throws InvocationTargetException, IllegalAccessException {
        HandlerMethod handlerMethod = handlerMapping.findHandlerMethod(request.getPath(), request.getMethodType());
        handlerMethod.method().invoke(handlerMethod.handler(), request, response);
    }

    public static File getFile(String fileLocation) {
        if (fileLocation == null || fileLocation.isBlank()) {
            throw new IllegalArgumentException("fileLocation must not be blank.");
        }
        return new File(fileLocation);
    }
}
