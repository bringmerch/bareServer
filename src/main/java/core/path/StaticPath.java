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
public enum StaticPath implements Path {
    INDEX("/static/index/", List.of(Method.GET), ContentType.TEXT_HTML, "index.html"),
    HELLO("/static/hello/", List.of(Method.GET), ContentType.TEXT_HTML, "hello.html"),
    PO("/static/po/", List.of(Method.GET), ContentType.IMAGE_JPEG, "po.jpg");

    String dir;
    List<Method> allowMethods;
    ContentType contentType;
    String fileName;

    public static final Map<String, StaticPath> map = Arrays.stream(StaticPath.values()).collect(
        Collectors.toMap(
            StaticPath::getDir, Function.identity()
        )
    );

    StaticPath(String dir, List<Method> allowMethods, ContentType contentType, String fileName) {
        this.dir = dir;
        this.allowMethods = allowMethods;
        this.contentType = contentType;
        this.fileName = fileName;
    }

    public String getDir() {
        return this.dir;
    }

    public String getFileName() {
        return this.fileName;
    }
}
