package core.record;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 *
 * Package Name: core.record
 * File Name: HttpRequest
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
public record HttpRequest (
    String method,
    String path,
    String version,
    Map<String, List<String>> header,
    File[] body) implements HttpMessage {
}
