package core.model;

import core.session.Session;
import core.type.MethodType;

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
    private MethodType methodType;
    private String path;
    private Map<String, String> queryMap;
    private HeaderMap headerMap;
    private String body;
    private String resourcePath;
    private Session session;

    public MethodType getMethodType() {
        return this.methodType;
    }

    public String getPath() {
        return this.path;
    }

    public String getBody() {
        return this.body;
    }

    public HeaderMap getHeaderMap() {
        return this.headerMap;
    }

    public Map<String, String> getQueryMap() {
        return this.queryMap;
    }

    public String getResourcePath() {
        return this.resourcePath;
    }

    public Session getSession() {
        return this.session;
    }

    public void setMethod(MethodType methodType) {
        if (methodType == null)
            throw new IllegalArgumentException("methodType must not be null.");
        this.methodType = methodType;
    }

    public void setPath(String path) {
        if (path == null || path.isBlank())
            throw new IllegalArgumentException("path must not be blank.");
        this.path = path;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void setHeaderMap(HeaderMap headerMap) {
        if (headerMap == null || headerMap.isEmpty())
            throw new IllegalArgumentException("setHeaders failed: empty headerMap.");
        this.headerMap = headerMap;
    }

    public void setResourcePath(String ResourcePath) {
        this.resourcePath = ResourcePath;
    }

    public void setQueryMap(Map<String, String> queryMap) {
        this.queryMap = queryMap;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
