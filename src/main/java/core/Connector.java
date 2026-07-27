package core;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Connector {
    public void start() throws Exception {
        ServerSocket serverSocket;
        serverSocket = new ServerSocket(8081);

        while (!serverSocket.isClosed()) {
            ConnectionHandler handler = new ConnectionHandler();
            Socket clientSocket = null;
            OutputStream outputStream = null;
            try {
                clientSocket = serverSocket.accept();
                handler.handle(clientSocket);
            } catch (IllegalArgumentException | IOException | BareException e) {
                System.out.println("connection handling failed: " + e.getMessage());
                try {
                    if (clientSocket != null && (outputStream = clientSocket.getOutputStream()) != null)
                        Worker.sendError(500, outputStream);
                } catch (Exception exception) {}
            } finally {
                ResourceCloser.close(clientSocket);
                ResourceCloser.close(outputStream);
            }
        }
        ResourceCloser.close(serverSocket);
    }
}
