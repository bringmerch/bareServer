package core;

import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Connector {
    public void listen(ApplicationContext applicationContext) throws Exception {
        ServerSocket serverSocket;
        serverSocket = new ServerSocket(8080);

        while (!serverSocket.isClosed()) {
            Socket clientSocket = null;
            try {
                clientSocket = serverSocket.accept();
                new ClientHandler().handle(clientSocket, applicationContext);
            } finally {
                ResourceCloser.close(clientSocket);
            }
        }
    }
}
