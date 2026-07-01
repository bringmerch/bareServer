package core.record;

import java.util.Map;

/**
 *
 * Package Name: core.record
 * File Name: HttpResponse
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
public record HttpResponse(
    String version,
    int statusCode,
    String statusMessage,
    Map<String, Object> header,
    String body) implements HttpMessage {
}
