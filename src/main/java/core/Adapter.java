package core;

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
// TODO 매핑 따라서 컨테이너로 요청 던짐
public class Adapter {
    public Response deliver(Request request) {
        Clerk clerk = new Clerk(request);
        return clerk.doGet();
    }
}

