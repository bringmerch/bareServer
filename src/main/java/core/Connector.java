package core;

import java.net.ServerSocket;
import java.net.Socket;

public class Connector {
    public void start() throws Exception {
        ServerSocket serverSocket;
        serverSocket = new ServerSocket(8080);

        while (!serverSocket.isClosed()) {
            ConnectionHandler handler = new ConnectionHandler();
            Socket clientSocket = serverSocket.accept();
            handler.handle(clientSocket);
            if (!clientSocket.isClosed())
                ResourceCloser.close(clientSocket);
        }
        ResourceCloser.close(serverSocket);
    }
}
