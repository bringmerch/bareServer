package core;

/**
 *
 * Package Name: core
 * File Name: JSONWorker
 * Description:
 * author: munke
 *
 * @version 1.0
 * @see core
 * @since 2026-07-21
 * <p>
 * Modification Information
 * 수정일          수정자                    수정내용
 * --------- ------------------- -------------------------------
 * 2026-07-21        munke                   최초개정
 */
public class JSONWorker implements Worker<String> {
    @Override
    public Response<String> execute(String path, Method method) {
        return new Response<String>();
    }
}
