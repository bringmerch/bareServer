package core;

import core.path.Path;

/**
 *
 * Package Name: Handler
 * File Name: Handler
 * Description:
 * author: munke
 *
 * @version 1.0
 * @see Handler
 * @since 2026-07-02
 * <p>
 * Modification Information
 * 수정일          수정자                    수정내용
 * --------- ------------------- -------------------------------
 * 2026-07-02        munke                   최초개정
 */
public class Handler {
    final Path path;

    Handler(Path path) {
        this.path = path;
    }

    public Response handlerMethod1(Request request) {
        return null;
    }

    public Response staticHandler(Request request) {
        return null;
    }
}
