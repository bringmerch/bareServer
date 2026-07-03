package zzz;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 *
 * Package Name: zzz
 * File Name: BufferUsage
 * Description:
 * author: munke
 *
 * @version 1.0
 * @see zzz
 * @since 2026-07-03
 * <p>
 * Modification Information
 * 수정일          수정자                    수정내용
 * --------- ------------------- -------------------------------
 * 2026-07-03        munke                   최초개정
 */
public class BufferUsage {
    public void main() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8192);

        String input1 = "hello, ByteBuffer.";
        byte[] inputByteArray1 = input1.getBytes(StandardCharsets.UTF_8);
        byteBuffer.put(inputByteArray1); // position이 끝으로 이동해서 읽기 시 flip() 필요

        // 읽기모드 전환
        // limit = position
        // position = 0
        byteBuffer.flip();

        while (byteBuffer.hasRemaining()) {
            System.out.println("byteBuffer.get() = " + byteBuffer.get());
        }

        // position = 0
        // limit = capacity
        byteBuffer.clear();

        String input2 = "Bye, ByteBuffer.";
        byte[] inputByteArray2 = input2.getBytes(StandardCharsets.UTF_8);
        byteBuffer.put(inputByteArray2);

        /**
         * 쓰기모드 진입 시 반드시
         * compact()
         * -> put()
         *
         * 읽기모드 진입 시 반드시
         * flip()
         * -> get()
         *
         *
         * */



    }
}
