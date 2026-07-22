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
        Response response;

        try {
            if ((bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8))) == null)
                throw new IOException("handle failed: bufferedReader is empty");
            if ((outputStream = clientSocket.getOutputStream()) == null)
                throw new BareException(500, "handle failed: outputStream is empty");
            DataProcessor dataProcessor = new DataProcessor();
            Request request = dataProcessor.readRequest(bufferedReader);
            Container container = Container.findByPathAndMethod(request.getPath(), request.getMethod());
            Worker worker = container.getWorkerInstance();
            WorkOrder workOrder = new WorkOrder(container.getResourcePath(), container.getContentType());
            response = worker.execute(workOrder);
            Writer.write(response, outputStream);
        } catch (BareException e) {
            Writer.write(ErrorResponse.create(e.getStatusCode()), outputStream);
        } catch (IllegalArgumentException e) {
            Writer.write(ErrorResponse.create(400), outputStream);
        } catch (IOException e) {
            Writer.write(ErrorResponse.create(500), outputStream);
        } finally {
            ResourceCloser.close(bufferedReader);
            ResourceCloser.close(outputStream);
        }
    }
}
