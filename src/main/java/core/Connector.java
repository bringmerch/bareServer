package core;

import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Connector {
    public void listen(ApplicationContext applicationContext) throws Exception {
        ServerSocket serverSocket;
        serverSocket = new ServerSocket(8080);

        while (!serverSocket.isClosed()) {
            RequestHandler handler = new RequestHandler();
            Socket clientSocket = null;
            OutputStream outputStream = null;
            try {
                clientSocket = serverSocket.accept();
                handler.handle(clientSocket, applicationContext);
            } catch (Exception e) {
                System.out.println("connection handling failed: " + e.getMessage());
            } finally {
                ResourceCloser.close(clientSocket);
                ResourceCloser.close(outputStream);
            }
        }
    }
}
