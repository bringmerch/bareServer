package core;

import java.io.IOException;

/**
 *
 * Package Name: core
 * File Name: Worker
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
public interface Worker<T> {
    void execute(DataProcessor dataProcessor) throws IOException;
    Response doProcess(Request request) throws IOException;
    T doGet(Request request) throws IOException;
    void doPost() throws IOException;
    void doPut() throws IOException;
    void doDelete() throws IOException;
}
