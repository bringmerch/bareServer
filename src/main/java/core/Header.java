package core;

import java.util.List;

/**
 *
 * Package Name: core
 * File Name: Header
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
public record Header(
    String fieldName,
    List<FieldValue> fieldValues
) {
    public record FieldValue (
        String member,
        List<String> parameters
    ) {
    }
}
