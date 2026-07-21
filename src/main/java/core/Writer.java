package core;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class Writer {
    public static void write(Response response, OutputStream outputStream) throws IOException {
        if (response == null)
            throw new IllegalArgumentException("response must not be null.");
        if (outputStream == null)
            throw new IllegalArgumentException("outputStream must not be null.");

        ResponseBody body = response.getBody();
        writeHeader(response, body.contentLength(), outputStream);
        body.writeTo(outputStream);
        outputStream.flush();
    }

    private static void writeHeader(Response response, long contentLength, OutputStream outputStream) throws IOException {
        int statusCode = response.getStatusCode();
        if (statusCode == 0)
            throw new IllegalArgumentException("writeHeader fail: statusCode is empty");
        if (contentLength == 0)
            throw new IllegalArgumentException("writeHeader fail: contentLength is empty");
        String contentType = response.getHeader().get("content-type");
        if (contentType == null || contentType.isBlank())
            throw new IllegalArgumentException("writeHeader fail: contentType is empty");

        String header =
              "HTTP/1.1 " + response.getStatusCode() + "\r\n"
            + "Content-Type: " + contentType + "\r\n"
            + "Content-Length: " + contentLength + "\r\n"
            + "\r\n";
        outputStream.write(header.getBytes(StandardCharsets.UTF_8));
    }
}
