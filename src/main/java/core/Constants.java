package core;

/**
 *
 * Package Name: core
 * File Name: Constants
 * Description:
 * author: munke
 *
 * @version 1.0
 * @see core
 * @since 2026-07-01
 * <p>
 * Modification Information
 * 수정일          수정자                    수정내용
 * --------- ------------------- -------------------------------
 * 2026-07-01        munke                   최초개정
 */
public enum Constants {
    //
    CRLF("\r\n"),

    //
    HTTP_VERSION("HTTP/1.1"),

    //
    HTTP_METHOD_GET("GET"),
    HTTP_METHOD_POST("POST"),
    HTTP_METHOD_PUT("PUT"),
    HTTP_METHOD_DELETE("DELETE"),
    HTTP_METHOD_HEAD("HEAD"),
    HTTP_METHOD_OPTIONS("OPTIONS"),
    HTTP_METHOD_PATCH("PATCH"),
    HTTP_METHOD_TRACE("TRACE"),
    HTTP_METHOD_CONNECT("CONNECT");

    private final String value;

    public String getValue() {
        return value;
    }

    Constants(String value) {
        this.value = value;
    }
}
