package core;

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
    CONTENT_TYPE__TEXT_HTML("text/html"),
    CONTENT_TYPE__TEXT_PLAIN("text/plain");

    String value;

    ContentType(String value) {
        this.value = value;
    }

    public ContentType from(String value) {
        for (ContentType type : ContentType.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }

        return null;
    }
}
