package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Package Name: core
 * File Name: ClosableRegistry
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
public class ClosableRegistry {
    static Map<Long, List<AutoCloseable>> threadId2resources = new HashMap<>();

    public static void addClosableResource(long threadId, AutoCloseable resource) {
        threadId2resources.computeIfAbsent(threadId, t -> new ArrayList<>()).add(resource);
    }

    public static void closeAllResource() {
        threadId2resources.keySet().forEach(threadId -> {
            threadId2resources.get(threadId).forEach(resource -> {
                try {
                    resource.close();
                } catch (Exception e) {
                    // 부분 close 허용
                    e.printStackTrace();
                }
            });
        });

        threadId2resources.clear();
    }
}
