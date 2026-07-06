package core;

/**
 *
 * Package Name: Clerk
 * File Name: Clerk
 * Description:
 * author: munke
 *
 * @version 1.0
 * @see Clerk
 * @since 2026-07-02
 * <p>
 * Modification Information
 * 수정일          수정자                    수정내용
 * --------- ------------------- -------------------------------
 * 2026-07-02        munke                   최초개정
 */
public class Clerk {
    // todo: container는 Request 객체 이외는 몰라야한다.
    private final Request request;

    public Clerk(Request request) {
        this.request = request;
    }

    public Response doGet() {
//        return request.get();
        return null;
    }
}
