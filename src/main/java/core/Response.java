package core;

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
    private Header header = new Header();
    private ResponseBody body;

    Response(ResponseBody body) {
        if (body == null)
            throw new IllegalArgumentException("body must not be null.");
        this.body = body;
    }

    Response(int statusCode) {
        this.statusCode = statusCode;
    }

    Response(int statusCode, ResponseBody body) {
        if (body == null)
            throw new IllegalArgumentException("body must not be null.");
        this.body = body;
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public String getHeader(String key) {
        if (key == null || key.isBlank())
            throw new IllegalArgumentException("key must not be blank.");
        return this.header.get(key);
    }

    public ResponseBody getBody() {
        return this.body;
    }
}
