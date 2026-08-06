package core;

/**
 *
 * Package Name: core
 * File Name: HandlerMethod
 * Description: Handler MethodType
 * author: munke
 *
 * @version 1.0
 * @see core
 * @since 2026-08-03
 * <p>
 * Modification Information
 * 수정일          수정자                    수정내용
 * --------- ------------------- -------------------------------
 * 2026-08-03        munke                   최초개정
 */
// 컨트롤러 인스턴스(싱글턴) + 메서드 meta
public record HandlerMethod(Handler handler, java.lang.reflect.Method method) {
}
