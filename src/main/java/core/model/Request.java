package core.model;

import core.type.Method;

import java.util.Map;

/**
 *
 * Package Name: core
 * File Name: Request
 * Description:
 * author: munke
 *
 * @version 1.0
 * @see core
 * @since 2026-07-01
 * <p>
 * Modification Information
 * 수정일          수정자                    수정내용
 * --------- ------------------- -------------------------------
 * 2026-07-01        munke                   최초개정
 */
public class Request {
    private Method method;
    private String path;
    private Map<String, String> queryMap;
    private HeaderMap headerMap;

    public Method getMethod() {
        return this.method;
    }

    public String getPath() {
        return this.path;
    }

    public HeaderMap getHeaderMap() {
        return this.headerMap;
    }

    public Map<String, String> getQueryMap() {
        return this.queryMap;
    }

    public void setMethod(Method method) {
        if (method == null)
            throw new IllegalArgumentException("method must not be null.");
        this.method = method;
    }

    public void setPath(String path) {
        if (path == null || path.isBlank())
            throw new IllegalArgumentException("path must not be blank.");
        this.path = path;
    }

    public void setHeaderMap(HeaderMap headerMap) {
        if (headerMap == null || headerMap.isEmpty())
            throw new IllegalArgumentException("setHeaders failed: empty headerMap.");
        this.headerMap = headerMap;
    }

    public void setQueryMap(Map<String, String> queryMap) {
        this.queryMap = queryMap;
    }
}
