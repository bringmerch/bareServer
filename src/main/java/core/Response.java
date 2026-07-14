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
public class Response<T> {
    private int statusCode;
    private List<Header> headers = new ArrayList<>();
    private T body;

    Response(T body) {
        this.body = body;
    }

    Response(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public void addHeader(Header header) {
        this.headers.add(header);
    }

    public Header getHeader(String fieldName) {
        for (Header header : this.headers) {
            if (header.fieldName().equalsIgnoreCase(fieldName)) {
                return header;
            }
        }
        return null;
    }

    public T getBody() {
        return this.body;
    }

    public void setBody(T body) {this.body = body;}
}
