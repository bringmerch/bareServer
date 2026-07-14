package core;

/**
 *
 * Package Name: core
 * File Name: ByteArrayWrapper
 * Description:
 * author: munke
 *
 * @version 1.0
 * @see core
 * @since 2026-07-14
 * <p>
 * Modification Information
 * 수정일          수정자                    수정내용
 * --------- ------------------- -------------------------------
 * 2026-07-14        munke                   최초개정
 */
public class ByteArrayWrapper {
    byte[] bytes;

    ByteArrayWrapper(byte[] bytes) {
        this.bytes = bytes;
    }

    public byte[] getBytes() {
        return this.bytes;
    }
}
