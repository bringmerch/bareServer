package core;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Package Name: core
 * File Name: Response
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
public class Response {
    private int statusCode;
    private HeaderMap header = new HeaderMap();
    private ResponseBody body;

    Response(ResponseBody body) {
        if (body == null)
            throw new IllegalArgumentException("body must not be null.");
        this.body = body;
    }

    Response(int statusCode) {
        validateStatusCode(statusCode);
        this.statusCode = statusCode;
    }

    Response(int statusCode, ResponseBody body) {
        if (body == null)
            throw new IllegalArgumentException("body must not be null.");
        this.body = body;
        this.statusCode = statusCode;
    }

    public void setStatusCode(int statusCode) {
        validateStatusCode(statusCode);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public void setHeader(HeaderMap header) {
        this.header = header;
    }

    public String getHeader(String fieldName) {
        if (fieldName == null || fieldName.isBlank())
            throw new IllegalArgumentException("fieldName must not be blank.");
        if (!this.header.containsKey(fieldName))
            throw new IllegalArgumentException("header doesn't exists.");
        return this.header.get(fieldName);
    }

    public HeaderMap getHeader() {
        return this.header;
    }

    public ResponseBody getBody() {
        return this.body;
    }

    public void setBody(ResponseBody body) {
        if (body == null) {
            throw new IllegalArgumentException("body must not be null.");
        }
        this.body = body;
    }

    private void validateStatusCode(int statusCode) {
        if (statusCode < 100 || statusCode > 599) {
            throw new IllegalArgumentException("statusCode must be between 100 and 599.");
        }
    }
}
