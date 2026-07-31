package core;

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
    private final Dispatcher dispatcher;

    public ApplicationContext() {
        sessionManager = new SessionManager();
        dispatcher = new Dispatcher();
    }

}
