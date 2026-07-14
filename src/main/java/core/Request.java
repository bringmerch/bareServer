package core;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

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
    private String[] startline;
    private List<QueryString> queryStrings = new ArrayList<>();
    private List<Header> headers = new ArrayList<>();
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

    public String[] getStartline() {
        return this.startline;
    }

    public void setStartline(String[] startline) {
        this.startline = startline;
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
