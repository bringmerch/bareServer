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
    TEXT_HTML("text/html; charset=utf-8"),
    TEXT_PLAIN("text/plain; charset=utf-8"),
    IMAGE_JPEG("image/jpeg"),
    APPLICATION_JSON("application/json; charset=utf-8");

    private final String MIMEType;

    ContentType(String MIMEType) {
        this.MIMEType = MIMEType;
    }

    public String getMIMEType() {
        return this.MIMEType;
    }
}
