package core.routes;

import core.type.MethodType;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * Package Name: core.routes
 * File Name: Mapping
 * Description:
 * author: munke
 *
 * @version 1.0
 * @see core.routes
 * @since 2026-08-06
 * <p>
 * Modification Information
 * 수정일          수정자                    수정내용
 * --------- ------------------- -------------------------------
 * 2026-08-06        munke                   최초개정
 */
public abstract class Mapping<T> {
    protected final Map<Route, T> mappings = new HashMap<>();

    protected abstract T get(String path, MethodType methodType);
}
