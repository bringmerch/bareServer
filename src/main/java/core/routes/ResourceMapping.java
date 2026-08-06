package core.routes;

import core.type.MethodType;

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
public class ResourceMapping extends Mapping<String> {
    public ResourceMapping() {
        // 경로별 정적자원 주소
        mappings.put(
            Route.INDEX,
            "/static/html/index.html"
        );
        mappings.put(
            Route.PANDA,
            "/static/images/jpeg/panda.jpeg"
        );
        mappings.put(
            Route.STYLE,
            "/static/css/style.css"
        );
    }

    @Override
    public String get(String path, MethodType methodType) {
        return mappings.get(Route.from(path, methodType));
    }
}
