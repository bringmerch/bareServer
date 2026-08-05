package core;

import core.type.MethodType;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * Package Name: core
 * File Name: ResourceMapping
 * Description:
 * author: munke
 *
 * @version 1.0
 * @see core
 * @since 2026-08-04
 * <p>
 * Modification Information
 * 수정일          수정자                    수정내용
 * --------- ------------------- -------------------------------
 * 2026-08-04        munke                   최초개정
 */
public class ResourceMapping {
    private final Map<Route, String> mappings = new HashMap<>();

    public ResourceMapping() {
        mappings.put(
            new Route("/index", MethodType.GET),
            "/static/html/index.html"
        );
        mappings.put(
            new Route("/panda", MethodType.GET),
            "/static/images/panda.jpg"
        );
        mappings.put(
            new Route("/css/style", MethodType.GET),
            "/static/css/style.css"
        );
    }

    public String findResourcePath(String path, MethodType methodType) {
        Route route = new Route(path, methodType);
        String resourcePath;

        if ((resourcePath = mappings.get(route)) != null)
            return resourcePath;
        else
            return null;
    }
}
