package core;

import java.io.PrintWriter;
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
    String version;
    int statusCode;
    List<Header> headers = new ArrayList<>();
    byte[] body;

    public void setVersion(String version){
        this.version = version;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setHeader(Header header) {
        this.headers.add(header);
    }

    public void setBody(byte[] bytes, boolean isEnd, String boundary) {

    }
}
