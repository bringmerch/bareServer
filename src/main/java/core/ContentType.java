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
    TEXT_HTML("text/html; charset=utf-8", true),
    TEXT_PLAIN("text/plain; charset=utf-8", true),
    IMAGE_JPEG("image/jpeg", false),
    APPLICATION_JSON("application/json; charset=utf-8", true);

    private final String MIMEType;
    private final boolean text;

    ContentType(String MIMEType, boolean text) {
        this.MIMEType = MIMEType;
        this.text = text;
    }

    public String getMIMEType() {
        return this.MIMEType;
    }

    public boolean isText() {
        return this.text;
    }
}
