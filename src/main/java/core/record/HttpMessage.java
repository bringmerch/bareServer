package core.record;

/**
 *
 * Package Name: core.type
 * File Name: HttpMessage
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
public sealed interface HttpMessage permits HttpRequest, HttpResponse {

}
