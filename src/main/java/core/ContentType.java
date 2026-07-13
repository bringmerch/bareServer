package core;

import javax.swing.text.AbstractDocument;

/**
 *
 * Package Name: core
 * File Name: ContentType
 * Description:
 * author: munke
 *
 * @version 1.0
 * @see core
 * @since 2026-07-06
 * <p>
 * Modification Information
 * 수정일          수정자                    수정내용
 * --------- ------------------- -------------------------------
 * 2026-07-06        munke                   최초개정
 */
public enum ContentType {
    TEXT_HTML("text/html", "/static/html/", ".html"),
    TEXT_PLAIN("text/plain", "/static/text/", ".txt"),
    IMAGE_JPEG("image/jpeg", "/static/jpg/", ".jpeg"),
    APPLICATION_JSON("application/json", "/static/json/", ".json");

    String value;
    String resourceDir;
    String extension;

    ContentType(String value, String resourceDir, String extension) {
        this.value = value;
        this.resourceDir = resourceDir;
        this.extension = extension;
    }

    public String getValue() {
        return this.value;
    }

    public String getExtension() {
        return this.extension;
    }
}
