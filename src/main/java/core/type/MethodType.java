package core.type;

/**
 *
 * Package Name: core
 * File Name: MethodType
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
public enum MethodType {
    GET("GET"),
    POST("POST"),
    PUT("PUT");

    private final String method;

    MethodType(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public static MethodType from(String method) {
        if (method == null || method.isBlank())
            throw new IllegalArgumentException("MethodType find failed: empty methodType.");

        method = method.toUpperCase();

        for (MethodType value : MethodType.values()) {
            if (value.getMethod().equalsIgnoreCase(method))
                return value;
        }

        throw new IllegalArgumentException("Unknown methodType: " + method);
    }
}
