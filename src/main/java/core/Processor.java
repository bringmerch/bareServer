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
public class Processor implements Runnable {
    final InputStream inputStream;
    final OutputStream outputStream;
    final BufferedWriter bufferedWriter;

    Processor(Socket socket) throws IOException {
        this.inputStream = socket.getInputStream();
        this.outputStream = socket.getOutputStream();
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
        ClosableRegistry.addClosableResource(Thread.currentThread().threadId(), bufferedWriter);
    }

    public void run() {
        Ship ship = new Ship();
        String response = Parser.parse(ship.deliver(Parser.parse(this.inputStream)));

        try {
            bufferedWriter.write(response);
            bufferedWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
