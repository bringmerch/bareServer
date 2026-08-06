package core.routes;

import core.type.MethodType;

/**
 *
 * Package Name: core
 * File Name: Route
 * Description:
 * author: munke
 *
 * @version 1.0
 * @see core
 * @since 2026-08-03
 * <p>
 * Modification Information
 * 수정일          수정자                    수정내용
 * --------- ------------------- -------------------------------
 * 2026-08-03        munke                   최초개정
 */
public enum Route {
    INDEX("/index", MethodType.GET),
    STYLE("/css/style", MethodType.GET),
    PANDA("/panda", MethodType.GET),
    BALANCE("/balance", MethodType.GET);

    private final String path;
    private final MethodType methodType;

    Route(String path, MethodType methodType) {
        this.path = path;
        this.methodType = methodType;
    }

    public static Route from(String path, MethodType methodType) {
        for (Route value : Route.values()) {
            if (value.path.equalsIgnoreCase(path) && value.methodType.equals(methodType))
                return value;
        }

        return null;
    }
}
