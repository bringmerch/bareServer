package core;

import javax.swing.text.AbstractDocument;

/**
 *
 * Package Name: core
 * File Name: WorkOrder
 * Description:
 * author: munke
 *
 * @version 1.0
 * @see core
 * @since 2026-07-22
 * <p>
 * Modification Information
 * 수정일          수정자                    수정내용
 * --------- ------------------- -------------------------------
 * 2026-07-22        munke                   최초개정
 */
public class WorkOrder {
    private String resourcePath;
    private ContentType contentType;

    WorkOrder(String resourcePath, ContentType contentType) {
        this.resourcePath = resourcePath;
        this.contentType = contentType;
    }

    public String getResourcePath() {
        return this.resourcePath;
    }

    public ContentType getContentType() {
        return this.contentType;
    }
}
