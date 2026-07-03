package core;

import java.io.InputStream;
import java.nio.ByteBuffer;
/**
 *
 * Package Name: core
 * File Name: InputBuffer
 * Description:
 * author: munke
 *
 * @version 1.0
 * @see core
 * @since 2026-07-03
 * <p>
 * Modification Information
 * 수정일          수정자                    수정내용
 * --------- ------------------- -------------------------------
 * 2026-07-03        munke                   최초개정
 */
public class InputBuffer {
    final ByteBuffer byteBuffer;
    final InputStream inputStream;

    InputBuffer(InputStream inputStream) {
        this.inputStream = inputStream;
        byteBuffer = ByteBuffer.allocate(8192);
    }

    void nextRequest() {
        if (byteBuffer.remaining() > 0) {
            byteBuffer.compact();
            byteBuffer.flip();
        }
    }

}
