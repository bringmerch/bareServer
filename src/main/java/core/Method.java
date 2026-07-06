package core;

import java.util.Locale;

/**
 *
 * Package Name: core
 * File Name: Method
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
public enum Method {
    GET("GET"),
    POST("POST"),
    PUT("PUT");

    final String method;

    Method(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public static Method from(String method) {
        method = method.toUpperCase();
        for (Method value : Method.values()) {
            if (value.getMethod().equalsIgnoreCase(method))
                return value;
        }
        throw new IllegalArgumentException("Unknown method: " + method);
    }
}
