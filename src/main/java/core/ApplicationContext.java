package core;

import core.handler.DynamicHandler;
import core.handler.Handler;
import core.handler.StaticHandler;
import core.interceptor.InterceptorRegistry;
import core.model.HandlerMapping;
import core.model.Route;
import core.session.SessionManager;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * Package Name: core
 * File Name: ApplicationContext
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
public class ApplicationContext {
    private final SessionManager sessionManager;
    private final InterceptorRegistry interceptorRegistry;
    private final Dispatcher dispatcher;
    private final HandlerMapping handlerMapping;

    public ApplicationContext() throws NoSuchMethodException {
        this.sessionManager = new SessionManager();
        this.dispatcher = new Dispatcher();
        this.handlerMapping = new HandlerMapping();
        this.interceptorRegistry = new InterceptorRegistry();
    }

    public HandlerMapping getHandlerMapping() {
        return handlerMapping;
    }

    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    public InterceptorRegistry getInterceptorRegistry() {
        return interceptorRegistry;
    }
}
