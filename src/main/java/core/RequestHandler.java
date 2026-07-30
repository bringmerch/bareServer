package core;

import core.model.Request;
import core.worker.Worker;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;

public class RequestHandler {
    public void handle(BufferedReader bufferedReader, OutputStream outputStream) throws IOException, BareException {
        Request request = Worker.parseRequest(Worker.readRequestAsString(bufferedReader));
        Dispatcher.dispatch(request, outputStream);
    }
}