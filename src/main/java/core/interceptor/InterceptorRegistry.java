package core.interceptor;

import core.model.Request;

import java.util.List;

/**
 *
 * Package Name: core
 * File Name: InterceptorRegistry
 * Description:
 * author: munke
 *
 * @version 1.0
 * @see core
 * @since 2026-07-31
 * <p>
 * Modification Information
 * 수정일          수정자                    수정내용
 * --------- ------------------- -------------------------------
 * 2026-07-31        munke                   최초개정
 */
public class InterceptorRegistry {
    private static final List<Interceptor> interceptors = List.of(new SessionInterceptor());

    public void runPreHandles(Request request) {
        for (Interceptor i : interceptors) {
            if (i.preHandle())
                continue;
        }
    }

    public void runPostHandles(Response response) {

    }
}
