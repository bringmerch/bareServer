package core.model;

import core.handler.DynamicHandler;
import core.handler.StaticHandler;
import core.type.MethodType;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * Package Name: core.model
 * File Name: HandlerMapping
 * Description:
 * author: munke
 *
 * @version 1.0
 * @see core.model
 * @since 2026-08-03
 * <p>
 * Modification Information
 * 수정일          수정자                    수정내용
 * --------- ------------------- -------------------------------
 * 2026-08-03        munke                   최초개정
 */
public class HandlerMapping {
    private final Map<Route, HandlerMethod> mappings = new HashMap<>();

    public HandlerMapping() throws NoSuchMethodException {
        // 1. mappings
        StaticHandler staticHandler = new StaticHandler();
        Method staticHandlerMethod = StaticHandler.class.getDeclaredMethod("serve", Request.class, Response.class);
        DynamicHandler dynamicHandler = new DynamicHandler();

        mappings.put(
            new Route("/index", MethodType.GET),
            new HandlerMethod(staticHandler, staticHandlerMethod, "/html/index.html")
        );
        mappings.put(
            new Route("/hello", MethodType.GET),
            new HandlerMethod(staticHandler, staticHandlerMethod, "/html/hello.html")
        );
        mappings.put(
            new Route("/panda", MethodType.GET),
            new HandlerMethod(staticHandler, staticHandlerMethod, "/image/jpeg/panda.jpeg")
        );
        mappings.put(
            new Route("/balance", MethodType.GET),
            new HandlerMethod(dynamicHandler, DynamicHandler.class.getDeclaredMethod("balance", Request.class), null)
        );
    }

    public HandlerMethod findHandlerMethod(String path, MethodType methodType) {
        HandlerMethod handlerMethod;
        if ((handlerMethod = mappings.get(new Route(path, methodType))) != null)
            return handlerMethod;
        else
            throw new IllegalArgumentException("HandlerMapping findHandlerMethod failed: no such route.");
    }

    public String getStaticResourcePath(String path) {
        return staticResourceMap.get(path);
    }
}
