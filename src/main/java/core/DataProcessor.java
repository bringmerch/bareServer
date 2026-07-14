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
    final InputStream inputStream; // byte 단위 읽는 용
    final BufferedReader bufferedReader; // line 단위 읽는 용
    final OutputStream outputStream;

    DataProcessor(Socket clientSocket) throws IOException {
        this.inputBuffer =  ByteBuffer.allocate(8192); // 버퍼 생성
        this.inputBuffer.flip(); // 버퍼를 읽기모드로 전환
        this.inputStream = clientSocket.getInputStream();
        this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        this.outputStream = clientSocket.getOutputStream();
    }

    /**
     * inputStream을 읽어 inputBuffer에 적재한다.
     * @return inputBuffer에 적재한 바이트 수
     */
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

    public String readLine() throws IOException {
        return this.bufferedReader.readLine();
    }
}
