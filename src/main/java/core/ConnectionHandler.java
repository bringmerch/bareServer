package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ConnectionHandler {
    public void handle(final Socket clientSocket) {
        BufferedReader bufferedReader = null;
        OutputStream outputStream = null;
        if (clientSocket == null)
            throw new IllegalArgumentException("clientSocket must not be null.");
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
            outputStream = clientSocket.getOutputStream();
            DataProcessor dataProcessor = new DataProcessor();
            Request request = dataProcessor.readRequest(bufferedReader);
            Worker worker = getWorker(request.getPath(), request.getMethod());
            Response response = worker.execute(new WorkOrder());
            writeResponse(outputStream, response);
        } catch (HttpException e) {
            writeErrorResponse(outputStream, e.getStatusCode(), e.getMessage());
        } catch (IllegalArgumentException e) {
            writeErrorResponse(outputStream, 400, e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            writeErrorResponse(outputStream, 500, "Internal server error.");
        } finally {
            ResourceCloser.close(bufferedReader);
            ResourceCloser.close(outputStream);
        }
    }

    private Worker getWorker(String path, Method method) throws HttpException {
        if (path == null || method == null)
            throw new IllegalArgumentException();
        if (!Route.containsPath(path))
            throw new HttpException(404, "Resource not found.");
        Worker worker = Route.getWorker(path);
        List<Method> allowedMethods = Route.getMethods(path);
        if (!allowedMethods.contains(method))
            throw new HttpException(405, "Method not allowed.");
        return worker;
    }

    private void writeResponse(OutputStream outputStream, Response response) {
        try {
            Writer.write(response, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeErrorResponse(OutputStream outputStream, int statusCode, String message) {
        if (outputStream == null)
            return;
        try {
            Response<String> response = Writer.errorResponse(statusCode, message);
            Writer.write(response, outputStream);
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}
