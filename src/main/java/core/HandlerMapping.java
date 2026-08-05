package core;

import core.model.Request;
import core.model.Response;
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
        // 현재 컨트롤러 1개.
        BasicHandlerImpl basicHandlerImpl = new BasicHandlerImpl();

        // TODO : 메서드 선언에 애노테이션 붙여서 동적으로 만들기....
        mappings.put(
            new Route("/index", MethodType.GET),
            new HandlerMethod(basicHandlerImpl, this.getMethod(basicHandlerImpl, "getIndex"))
        );
        mappings.put(
            new Route("/panda", MethodType.GET), // TODO : 와일드카드
            new HandlerMethod(basicHandlerImpl, this.getMethod(basicHandlerImpl, "getFile"))
        );
        mappings.put(
            new Route("/css/style", MethodType.GET), // TODO : 와일드카드
            new HandlerMethod(basicHandlerImpl, this.getMethod(basicHandlerImpl, "getFile"))
        );
        mappings.put(
            new Route("/balance", MethodType.GET),
            new HandlerMethod(basicHandlerImpl, this.getMethod(basicHandlerImpl, "getBalance"))
        );
    }

    private Method getMethod(Handler handler, String method) throws NoSuchMethodException {
        return handler.getClass().getDeclaredMethod(method, Request.class, Response.class);
    }

    public HandlerMethod findHandlerMethod(String path, MethodType methodType) {
        HandlerMethod handlerMethod;

        if ((handlerMethod = mappings.get(new Route(path, methodType))) != null)
            return handlerMethod;
        else
            throw new IllegalArgumentException("findHandlerMethod failed: no such route for path : " + path);
    }
}
