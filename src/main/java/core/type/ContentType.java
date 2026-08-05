package core.type;

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
    TEXT_HTML("text/html; charset=utf-8", "html"),
    TEXT_PLAIN("text/plain; charset=utf-8", "text"),
    IMAGE_JPEG("image/jpeg", "jpeg"),
    APPLICATION_JSON("application/json; charset=utf-8", "json"),
    STYLE("text/css", "css");

    private final String MIMEType;
    private final String extension;

    ContentType(String MIMEType, String extension) {
        this.MIMEType = MIMEType;
        this.extension = extension;
    }

    public String getMIMEType() {
        return this.MIMEType;
    }

    public String getExtension() {
        return this.extension;
    }

    public static ContentType getByExtension(String path) {
        String[] pieces = path.split("\\.");
        String lastPiece = pieces[pieces.length - 1];

        for (ContentType contentType : ContentType.values()) {
            if (contentType.extension.equalsIgnoreCase(lastPiece)) {
                return contentType;
            }
        }
        return null;
    }
}
