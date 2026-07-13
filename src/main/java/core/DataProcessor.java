package core;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;

/**
 *
 * Package Name: core
 * File Name: Reader
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
public class DataProcessor {
    final ByteBuffer inputBuffer;
    final InputStream inputStream;
    final OutputStream outputStream;

    DataProcessor(Socket clientSocket) throws IOException {
        this.inputBuffer =  ByteBuffer.allocate(8192);
        this.inputBuffer.flip();
        this.inputStream = clientSocket.getInputStream();
        this.outputStream = clientSocket.getOutputStream();
    }

    public int loadByteBuffer() {
        byte[] tempByteArr = new byte[this.inputBuffer.capacity()];
        int read;

        this.inputBuffer.clear();

        try {
            read = this.inputStream.read(tempByteArr);
            if (read > 0)
                this.inputBuffer.put(tempByteArr, 0, read);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return read;
    }
}
