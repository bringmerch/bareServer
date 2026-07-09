package core.path;

import core.ContentType;
import core.Method;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * Package Name: core
 * File Name: DynamicPath
 * Description:
 * author: munke
 *
 * @version 1.0
 * @see core
 * @since 2026-07-09
 * <p>
 * Modification Information
 * 수정일          수정자                    수정내용
 * --------- ------------------- -------------------------------
 * 2026-07-09        munke                   최초개정
 */
public enum DynamicPath implements Path {
    ECHO("/api/echo", List.of(Method.GET), "echo", ContentType.TEXT_PLAIN);

    String path;
    List<Method> allowMethods;
    String handlerMethod;
    ContentType responseContentType;

    public static final Map<String, DynamicPath> map = Arrays.stream(DynamicPath.values()).collect(
        Collectors.toMap(
            DynamicPath::getPath, Function.identity()
        )
    );

    public String getPath() {
        return this.path;
    }

    public String getHandlerMethod() {
        return this.handlerMethod;
    }

    DynamicPath(String path, List<Method> allowMethods, String handlerMethod, ContentType responseContentType) {
        this.path = path;
        this.allowMethods = allowMethods;
        this.handlerMethod = handlerMethod;
        this.responseContentType = responseContentType;
    }
}
