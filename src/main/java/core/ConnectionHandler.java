package core;

import core.worker.Worker;

import java.io.*;
import java.net.Socket;

public class ConnectionHandler {
    public void handle(final Socket clientSocket) throws BareException {
        if (clientSocket == null)
            throw new BareException(500, "handle failed: clientSocket is empty");

        InputStream inputStream; // bufferedReader 닫을 때 같이 닫힘
        OutputStream outputStream = null; // bufferedWriter 닫을 때 같이 닫힘
        BufferedReader bufferedReader = null;

        try {
            inputStream = clientSocket.getInputStream(); // null 리턴 안 함
            outputStream = clientSocket.getOutputStream(); // null 리턴 안 함
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream)); // null 리턴 안 함
            new RequestHandler().handle(bufferedReader, outputStream);
        } catch (BareException e) {
            Worker.executeErrorWorker(e.getStatusCode(), outputStream);
        } catch (IllegalArgumentException e) {
            Worker.executeErrorWorker(400, outputStream);
        } catch (IOException e) {
            Worker.executeErrorWorker(500, outputStream);
        } finally {
            ResourceCloser.close(bufferedReader);
            ResourceCloser.close(outputStream);
        }
    }
}
