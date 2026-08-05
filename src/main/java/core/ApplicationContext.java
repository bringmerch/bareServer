package core;

import core.interceptor.InterceptorRegistry;
import core.session.SessionManager;

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
    private final ResourceMapping resourceMapping;

    public ApplicationContext() throws NoSuchMethodException {
        this.sessionManager = new SessionManager();
        this.resourceMapping = new ResourceMapping();
        this.interceptorRegistry = new InterceptorRegistry(this.sessionManager, this.resourceMapping);
        this.handlerMapping = new HandlerMapping();
        this.dispatcher = new Dispatcher(this);
    }

    public Dispatcher getDispatcher() {
        return this.dispatcher;
    }

    public InterceptorRegistry getInterceptorRegistry() {
        return this.interceptorRegistry;
    }

    public HandlerMapping getHandlerMapping() {
        return this.handlerMapping;
    }
}
