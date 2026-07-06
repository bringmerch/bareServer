package core;

import java.util.ArrayList;
import java.util.List;

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
    Method method;
    String path;
    List<QueryString> queryStrings = new ArrayList<>();
    List<Header> headers = new ArrayList<>();
    byte[] bodies;

    public void setMethod(Method method) {
        this.method = method;
    }

    public Method getMethod() {
        return this.method;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void addHeader(Header header) {
        this.headers.add(header);
    }

    public void addQueryString(QueryString queryString) {
        this.queryStrings.add(queryString);
    }

    public Header getHeader(String fieldName) {
        for (Header header : this.headers) {
            if (header.fieldName().equalsIgnoreCase(fieldName)) {
                return header;
            }
        }
        return null;
    }

    public void recycle() {

    }
}
