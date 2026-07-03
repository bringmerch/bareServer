package core;

import java.util.List;

/**
 *
 * Package Name: core.record
 * File Name: Request
 * Description:
 * author: munke
 *
 * @version 1.0
 * @see core.record
 * @since 2026-07-01
 * <p>
 * Modification Information
 * 수정일          수정자                    수정내용
 * --------- ------------------- -------------------------------
 * 2026-07-01        munke                   최초개정
 */
public class Request {
    String method;
    String path;
    List<QueryString> queryStrings;
    String httpVersion;
    List<Header> headers;
    byte[] body;
    List<byte[]> files;
    final Connector connector;

    Request(Connector connector) {
        this.connector = connector;
    }

    public void recycle() {
        //reset fields
    }
}
