package core;

import java.io.PrintWriter;
import java.util.List;

/**
 *
 * Package Name: core.record
 * File Name: Response
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
public record Response(
    String version,
    int statusCode,
    List<Header> headers,
    byte[] body,
    PrintWriter writer) {
}
