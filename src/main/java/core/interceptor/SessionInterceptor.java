package core.interceptor;

import core.model.Request;
import core.model.Response;
import core.session.Session;
import core.session.SessionManager;

/**
 *
 * Package Name: core.interceptor
 * File Name: SessionInterceptor
 * Description:
 * author: munke
 *
 * @version 1.0
 * @see core.interceptor
 * @since 2026-07-31
 * <p>
 * Modification Information
 * 수정일          수정자                    수정내용
 * --------- ------------------- -------------------------------
 * 2026-07-31        munke                   최초개정
 */
public class SessionInterceptor implements Interceptor {
    SessionManager sessionManager;

    public SessionInterceptor(SessionManager sessionManager) {
        if (sessionManager == null)
            throw new IllegalArgumentException("SessionInterceptor constructor failed: sessionManager must not be null");

        this.sessionManager = sessionManager;
    }

    @Override
    public boolean preHandle(Request request, Response response) {
        String sessionId = request.getHeader("B-SESSION-ID");
        Session session = this.sessionManager.getSession(sessionId);

        if (session == null) {
            session = this.sessionManager.createSession();
            response.setCookie("B-SESSION-ID", session.getSessionId(), SessionManager.MAX_AGE);
        } else
            request.setSession(session);

        return true;
    }
}
