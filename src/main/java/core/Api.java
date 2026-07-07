package core;

import java.util.List;

/**
 *
 * Package Name: core
 * File Name: Path
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
public enum Api {
    INDEX("/index", List.of(Method.GET), "/src/main/resources/static/"),
    HELLO("/hello", List.of(Method.GET), "");

    String path;
    List<Method> allowMethods;
    String url;

    Api(String path, List<Method> allowMethods, String url) {
        this.path = path;
        this.allowMethods = allowMethods;
        this.url = url;
    }
}
