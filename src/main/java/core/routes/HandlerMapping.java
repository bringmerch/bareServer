package core.routes;

import core.BasicHandlerImpl;
import core.Handler;
import core.HandlerMethod;
import core.model.Request;
import core.model.Response;
import core.type.MethodType;

import java.lang.reflect.Method;

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
public class HandlerMapping extends Mapping<HandlerMethod> {
    public HandlerMapping() throws NoSuchMethodException {
        // 컨트롤러 인스턴스 생성
        BasicHandlerImpl basicHandlerImpl = new BasicHandlerImpl();

        // 경로별 <컨트롤러 인스턴스, 메서드>
        mappings.put(
            Route.INDEX,
            new HandlerMethod(basicHandlerImpl, this.getMethod(basicHandlerImpl, "getIndex"))
        );
        mappings.put(
            Route.PANDA,
            new HandlerMethod(basicHandlerImpl, this.getMethod(basicHandlerImpl, "getFile"))
        );
        mappings.put(
            Route.STYLE,
            new HandlerMethod(basicHandlerImpl, this.getMethod(basicHandlerImpl, "getFile"))
        );
        mappings.put(
            Route.BALANCE,
            new HandlerMethod(basicHandlerImpl, this.getMethod(basicHandlerImpl, "getBalance"))
        );
    }

    @Override
    public HandlerMethod get(String path, MethodType methodType) {
        return mappings.get(Route.from(path, methodType));
    }

    private Method getMethod(Handler handler, String method) throws NoSuchMethodException {
        return handler.getClass().getDeclaredMethod(method, Request.class, Response.class);
    }
}
