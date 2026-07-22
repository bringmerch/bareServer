package core;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.NullPointerException;

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
    int BUFFER_SIZE = 4096;
    long contentLength();
    void writeTo(OutputStream outputStream) throws IOException, BareException;
}
