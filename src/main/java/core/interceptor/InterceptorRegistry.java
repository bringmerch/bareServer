package core.interceptor;

import core.ResourceMapping;
import core.model.Request;
import core.model.Response;
import core.session.SessionManager;

import java.util.ArrayList;
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
    private final List<Interceptor> interceptors = new ArrayList<>();

    public InterceptorRegistry(SessionManager sessionManager, ResourceMapping resourceMapping) {
        this.interceptors.add(new SessionInterceptor(sessionManager));
        this.interceptors.add(new ResourceMappingInterceptor(resourceMapping));
    }

    public boolean doInterceptors(Request request, Response response) {
        for (Interceptor i : this.interceptors) {
            if (!i.preHandle(request, response))
                return false;
        }
        return true;
    }
}
