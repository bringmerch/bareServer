package core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Connector {
    public void start() throws Exception {
        ServerSocket serverSocket;
        serverSocket = new ServerSocket(8080);

        while (!serverSocket.isClosed()) {
            ConnectionHandler handler = new ConnectionHandler();
            Socket clientSocket = serverSocket.accept();
            try {
                handler.handle(clientSocket);
            } catch (BareException | IOException | IllegalArgumentException e) {
                System.out.println("connection handling failed: " + e.getMessage());
            } finally {
                ResourceCloser.close(clientSocket);
            }
        }
        ResourceCloser.close(serverSocket);
    }
}
