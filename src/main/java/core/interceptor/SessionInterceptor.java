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
    @Override
    public boolean preHandle(Request request, Response response) {
        if (SessionManager.getSession())
            Session session = SessionManager.createSession()


        return true;
    }
}
