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
    private Method method;
    private String path;
    private List<QueryString> queryStrings = new ArrayList<>();
    private List<Header> headers = new ArrayList<>();
    private byte[] bodies;
    private boolean isParsed;

    public void setMethod(Method method) {
        this.method = method;
    }

    public void setIsParsed(boolean isParsed) {
        this.isParsed = isParsed;
    }

    public boolean getIsParsed() {
        return isParsed;
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

    public List<Header> getHeaders(String fieldName) {
        List<Header> headers = new ArrayList<>();
        for (Header header : this.headers) {
            if (header.fieldName().equalsIgnoreCase(fieldName)) {
                headers.add(header);
            }
        }
        return headers;
    }

    public void recycle() {

    }
}
