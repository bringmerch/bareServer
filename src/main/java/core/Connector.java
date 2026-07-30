package core;

import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Connector {
    public void start() throws Exception {
        ServerSocket serverSocket;
        serverSocket = new ServerSocket(8080);

        while (!serverSocket.isClosed()) {
            ConnectionHandler handler = new ConnectionHandler();
            Socket clientSocket = null;
            OutputStream outputStream = null;
            try {
                clientSocket = serverSocket.accept();
                handler.handle(clientSocket);
            } catch (Exception e) {
                System.out.println("connection handling failed: " + e.getMessage());
            } finally {
                ResourceCloser.close(clientSocket);
                ResourceCloser.close(outputStream);
            }
        }
    }
}
