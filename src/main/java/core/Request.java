package core;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;
import java.net.HttpURLConnection;
import java.net.URL;

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
public record Request(
    String method,
    String path,
    List<QueryString> queryStrings,
    String httpVersion,
    List<Header> headers,
    byte[] body,
    PrintWriter writer) {
}
