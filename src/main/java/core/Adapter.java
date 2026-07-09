package core;

import core.path.DynamicPath;
import core.path.Path;
import core.path.StaticPath;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * Package Name: core
 * File Name: Adapter
 * Description:
 * author: munke
 *
 * @version 1.0
 * @see core
 * @since 2026-07-02
 * <p>
 * Modification Information
 * 수정일          수정자                    수정내용
 * --------- ------------------- -------------------------------
 * 2026-07-02        munke                   최초개정
 */
public class Adapter {
    public static Path findPath(String requestPath) {
        Path path = null;

        if (requestPath.startsWith("/static")) {
            return StaticPath.map.get(requestPath);
        } else if (requestPath.startsWith("/api")) {
            return DynamicPath.map.get(requestPath);
        }

        return path;
    }
}

