package core.interceptor;

import core.model.Request;
import core.model.Response;

/**
 *
 * Package Name: core
 * File Name: Interceptor
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
public interface Interceptor {
    boolean preHandle(Request request, Response response);
}
