package core;

import core.handler.Handler;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;

public class RequestHandler {
    public void handle(final Socket clientSocket, final ApplicationContext applicationContext) throws BareException {
        if (clientSocket == null)
            throw new BareException(500, "handle failed: clientSocket is empty");

        InputStream inputStream; // bufferedReader 닫을 때 같이 닫힘
        OutputStream outputStream = null; // bufferedWriter 닫을 때 같이 닫힘
        BufferedReader bufferedReader = null;

        try {
            inputStream = clientSocket.getInputStream(); // null 리턴 안 함
            outputStream = clientSocket.getOutputStream(); // null 리턴 안 함
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream)); // null 리턴 안 함

            applicationContext.getDispatcher().dispatch(
                Handler.readRequest(bufferedReader),
                outputStream,
                applicationContext
            );
        } catch (BareException e) {
            Worker.executeErrorWorker(e.getStatusCode(), outputStream);
        } catch (IllegalArgumentException e) {
            Worker.executeErrorWorker(400, outputStream);
        } catch (IOException | InvocationTargetException | IllegalAccessException e) {
            Worker.executeErrorWorker(500, outputStream);
        } finally {
            ResourceCloser.close(bufferedReader);
            ResourceCloser.close(outputStream);
        }
    }
}
