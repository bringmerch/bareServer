package core;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public interface Worker {
    void process(WorkOrder workOrder, OutputStream outputStream) throws BareException, IOException;

    default void sendResponse(Response response, OutputStream outputStream) throws BareException, IOException {
        if (response == null)
            throw new IllegalArgumentException("response must not be null.");
        if (outputStream == null)
            throw new IllegalArgumentException("outputStream must not be null.");
        if (response.getBody() == null)
            throw new BareException(500, "response body must not be null.");

        ResponseBody body = response.getBody();
        writeHeader(response.getStatusCode(), response.getHeader("Content-Type"), body.contentLength(), outputStream);
        body.writeTo(outputStream);
        outputStream.flush();
    }

    static void sendError(int statusCode, OutputStream outputStream) {
        if (outputStream == null)
            return;

        try {
            new ErrorWorker().process(new WorkOrder(statusCode), outputStream);
        } catch (Exception ignored) {
        }
    }

    private void writeHeader(int statusCode, String contentType, long contentLength, OutputStream outputStream) throws IOException {
        if (statusCode == 0)
            throw new IllegalArgumentException("writeHeader fail: statusCode is empty");
        if (contentType == null || contentType.isBlank())
            throw new IllegalArgumentException("writeHeader fail: contentType is empty");

        String header =
              "HTTP/1.1 " + statusCode + "\r\n"
            + "Content-Type: " + contentType + "\r\n"
            + "Content-Length: " + contentLength + "\r\n"
            + "\r\n";
        outputStream.write(header.getBytes(StandardCharsets.UTF_8));
    }
}
