package core;

import java.security.Key;

/**
 *
 * Package Name: core
 * File Name: QueryString
 * Description:
 * author: munke
 *
 * @version 1.0
 * @see core
 * @since 2026-07-02
 * <p>
 * Modification Information
 * 수정일          수정자                    수정내용
 * --------- ------------------- -------------------------------
 * 2026-07-02        munke                   최초개정
 */
public record QueryString(
    String key,
    String value
) {
}
