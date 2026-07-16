package core;

import java.util.List;
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

    static final ConcurrentHashMap<String, List<Method>> pathMethodsMap;

    // 1. 자원 close
    // 2. parser는 String 받아서 파싱만 !!
    // 3. worker 책임 분리 - worker는 method를 알 필요가 없다. 요청사항 받아서 execute하면 땡
    // 4. throw IOException을 해놨으면 위로 던질 것 !!
    // 5. inputStream 멤버변수 삭제
    // 7. 파라미터 유효성 검사 !! 공통
    // 8. 응답 시 buffer 사용
    // 9. finally 써도 됨
    // 10. catch에서 return null 하지말고 웬만하면 나를 호출한 원점까지 Exception을 던져라...
    // 11. buffered return은 보통 4kb씩

    static {
        pathWorkerMap = new ConcurrentHashMap<>();
        pathWorkerMap.put("/index", HTMLWorker.class);
        pathWorkerMap.put("/hello", HTMLWorker.class);
        pathWorkerMap.put("/panda", ImageWorker.class);

        pathMethodsMap = new ConcurrentHashMap<>();
        pathMethodsMap.put("/index", List.of(Method.GET));
        pathMethodsMap.put("/hello", List.of(Method.GET));
        pathMethodsMap.put("/panda", List.of(Method.GET));
    }

    public static Class getWorker(String path) {
        return pathWorkerMap.get(path);
    }

    public static List getMethods(String path) {
        return pathMethodsMap.get(path);
    }
}
