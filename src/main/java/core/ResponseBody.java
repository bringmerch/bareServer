package core;

import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * Package Name: core
 * File Name: ResponseBody
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
public interface ResponseBody {
    long contentLength() throws IOException;
    void writeTo(OutputStream outputStream) throws IOException;
}
