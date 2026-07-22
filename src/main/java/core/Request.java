package core;

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
    private Map<String, String> query;
    private Header header;

    public Method getMethod() {
        return this.method;
    }

    public String getPath() {
        return this.path;
    }

    public Header getHeader() {
        return this.header;
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

    public void setHeader(Header header) {
        if (header == null || header.isEmpty())
            throw new IllegalArgumentException("setHeaders failed: empty header.");
        this.header = header;
    }

    public void setQuery(Map<String, String> query) {
        if (query == null || query.isEmpty())
            throw new IllegalArgumentException("setQuery failed: empty query.");
        this.query = query;
    }
}
