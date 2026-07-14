package core;

import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * Package Name: core
 * File Name: Container
 * Description:
 * author: munke
 *
 * @version 1.0
 * @see core
 * @since 2026-07-13
 * <p>
 * Modification Information
 * 수정일          수정자                    수정내용
 * --------- ------------------- -------------------------------
 * 2026-07-13        munke                   최초개정
 */
public class Container {
    static final ConcurrentHashMap<String, Class<? extends Worker>> pathWorkerMap;

    static {
        pathWorkerMap = new ConcurrentHashMap<>();
        pathWorkerMap.put("/index", HTMLWorker.class);
        pathWorkerMap.put("/hello", HTMLWorker.class);
    }

    public static Class get(String path) {
        return pathWorkerMap.get(path);
    }
}
