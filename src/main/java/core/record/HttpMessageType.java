package core.record;

/**
 *
 * Package Name: core.record
 * File Name: HttpMessageType
 * Description:
 * author: munke
 *
 * @version 1.0
 * @see core.record
 * @since 2026-07-01
 * <p>
 * Modification Information
 * 수정일          수정자                    수정내용
 * --------- ------------------- -------------------------------
 * 2026-07-01        munke                   최초개정
 */
public enum HttpMessageType {
    REQUEST(HttpRequest.class),
    RESPONSE(HttpResponse.class);

    private final Class<? extends HttpMessage> httpMessage;

    public Class<? extends HttpMessage> getHttpMessage() {
        return httpMessage;
    }

    HttpMessageType(Class<? extends HttpMessage> httpMessage) {
        this.httpMessage = httpMessage;
    }
}
