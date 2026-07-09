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
    TEXT_HTML("text/html", "/static/html/"),
    TEXT_PLAIN("text/plain", "/static/text/"),
    IMAGE_JPEG("image/jpeg", "/static/jpg/"),
    APPLICATION_JSON("application/json", "/static/json/");

    String value;
    String resourceDir;

    ContentType(String value, String resourceDir) {
        this.value = value;
        this.resourceDir = resourceDir;
    }
}
