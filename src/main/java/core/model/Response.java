package core.model;

import core.body.Body;

/**
 *
 * Package Name: core.model
 * File Name: Response
 * Description:
 * author: munke
 *
 * @version 1.0
 * @see core.model
 * @since 2026-07-31
 * <p>
 * Modification Information
 * 수정일          수정자                    수정내용
 * --------- ------------------- -------------------------------
 * 2026-07-31        munke                   최초개정
 */
public class Response {
    private int statusCode;
    private HeaderMap header = new HeaderMap();
    private Body<?> body;

    public HeaderMap getHeader() {
        return this.header;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public Body<?> getBody() {
        return this.body;
    }

    public void addHeader(String key, String value) {
        this.header.put(key, value);
    }

    public void setBody(Body<?> body) {
        this.body = body;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setCookie(String key, String value, long maxAge) {
        header.put("Set-Cookie", key + "=" + value + "; Max-Age=" + maxAge);
    }
}
