package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ConnectionHandler {
    public void handle(final Socket clientSocket) throws BareException, IOException {
        if (clientSocket == null)
            throw new BareException(500, "handle failed: clientSocket is empty");
        BufferedReader bufferedReader = null;
        OutputStream outputStream = null;

        try {
            if ((bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8))) == null)
                throw new IOException("handle failed: bufferedReader is empty");
            if ((outputStream = clientSocket.getOutputStream()) == null)
                throw new BareException(500, "handle failed: outputStream is empty");
            RequestReader requestReader = new RequestReader();
            Request request = requestReader.readRequest(bufferedReader);
            Container container = Container.findByPathAndMethod(request.getPath(), request.getMethod());
            Worker worker = container.getWorkerInstance();
            WorkOrder workOrder = new WorkOrder(container.getResourcePath(), container.getContentType());
            worker.process(workOrder, outputStream);
        } catch (BareException e) {
            Worker.sendError(e.getStatusCode(), outputStream);
        } catch (IllegalArgumentException e) {
            Worker.sendError(400, outputStream);
        } catch (IOException e) {
            Worker.sendError(500, outputStream);
        } finally {
            ResourceCloser.close(bufferedReader);
            ResourceCloser.close(outputStream);
        }
    }
}
