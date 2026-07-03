package core;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 *
 * Package Name: core
 * File Name: Handler
 * Description:
 * author: munke
 *
 * @version 1.0
 * @see core
 * @since 2026-07-01
 * <p>
 * Modification Information
 * 수정일          수정자                    수정내용
 * --------- ------------------- -------------------------------
 * 2026-07-01        munke                   최초개정
 */
public class ConnectionHandler implements Runnable {
    final Socket clientSocket;

    ConnectionHandler(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
    }

    public void run() {
        Adapter adapter = new Adapter();
        InputBuffer inputBuffer = new InputBuffer(clientSocket.getInputStream());
        -> http message 만들어졌으면
        input bufffer 내부에서 response까지 만드는 걸 연쇄적으로 해야됨.
        여기서 return하는 게 아니라


        String response = parser.parse(adapter.deliver(Parser.parse(this.inputStream)));

        try {
            bufferedWriter.write(response);
            bufferedWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



//    public void loadBuffer() throws IOException {
//        Parser parser = new Parser();
//        byte[] bytes = new byte[8192];
//
//        int numberOfBytesRead;
//
//        while (true) {
//            if (((numberOfBytesRead = inputStream.read(bytes)) != -1)) break;
//            for (byte b : bytes) {
//                parser.accept(b); // parser에 한바이트씩 먹임
//            }
//
//        }
//    }
}
