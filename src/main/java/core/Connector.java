package core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * Package Name: core
 * File Name: Connector
 * Description:
 * author: munke
 *
 * @version 1.0
 * @see core
 * @since 2026-07-02
 * <p>
 * Modification Information
 * 수정일          수정자                    수정내용
 * --------- ------------------- -------------------------------
 * 2026-07-02        munke                   최초개정
 */
public class Connector {
    private ServerSocket serverSocket;

    public void start() throws IOException {
        serverSocket = new ServerSocket(8080);

        if (!serverSocket.isClosed())
            ClosableRegistry.addClosableResource(Thread.currentThread().threadId(), serverSocket);

        while (!serverSocket.isClosed()) {
            Socket socket = serverSocket.accept();
            ClosableRegistry.addClosableResource(Thread.currentThread().threadId(), socket);
            new Thread(new ConnectionHandler(socket)).start();
        }

        ClosableRegistry.closeAllResource();
    }
}
