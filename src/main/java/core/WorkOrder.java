package core;

/**
 *
 * Package Name: core
 * File Name: WorkOrder
 * Description:
 * author: munke
 *
 * @version 1.0
 * @see core
 * @since 2026-07-21
 * <p>
 * Modification Information
 * 수정일          수정자                    수정내용
 * --------- ------------------- -------------------------------
 * 2026-07-21        munke                   최초개정
 */
public record WorkOrder (String resourcePath, ContentType contentType){}
