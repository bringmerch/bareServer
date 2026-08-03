package core;

import core.handlerResult.HandlerResult;
import core.model.*;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;

/**
 *
 * Package Name: core
 * File Name: Dispatcher
 * Description:
 * author: munke
 *
 * @version 1.0
 * @see core
 * @since 2026-07-30
 * <p>
 * Modification Information
 * 수정일          수정자                    수정내용
 * --------- ------------------- -------------------------------
 * 2026-07-30        munke                   최초개정
 */
public class Dispatcher {
    public static void dispatch(Request request,
                                OutputStream outputStream,
                                ApplicationContext applicationContext) throws BareException, IOException, InvocationTargetException, IllegalAccessException {
        Response response = new Response();

        if (applicationContext.getInterceptorRegistry().doIntercept(request, response))
            throw new BareException(500, "intercept failed: interceptor error.");


        String staticResourcePath = applicationContext.getHandlerMapping().getStaticResourcePath(request.getPath());
        if (staticResourcePath != null) {
            request.setResourcePath(staticResourcePath);
        }

        HandlerMethod handlerMethod = applicationContext.getHandlerMapping().findHandlerMethod(request.getPath(), request.getMethod());
        HandlerResult handlerResult = (HandlerResult)handlerMethod.method().invoke(handlerMethod.handler(), request, response);
        HandlerResult.doResponse()

    }
}
