package core;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class Writer {
    public static void write(Response response, OutputStream outputStream) throws IOException, BareException {
        if (response == null)
            throw new IllegalArgumentException("response must not be null.");
        if (outputStream == null)
            throw new IllegalArgumentException("outputStream must not be null.");

        ResponseBody body = response.getBody();
        writeHeader(response.getStatusCode(), response.getHeader("Content-Type"), body.contentLength(), outputStream);
        body.writeTo(outputStream);
        outputStream.flush();
    }

    private static void writeHeader(int statusCode, String contentType, long contentLength, OutputStream outputStream) throws IOException {
        if (statusCode == 0)
            throw new IllegalArgumentException("writeHeader fail: statusCode is empty");
        if (contentLength == 0)
            throw new IllegalArgumentException("writeHeader fail: contentLength is empty");
        if (contentType == null || contentType.isBlank())
            throw new IllegalArgumentException("writeHeader fail: contentType is empty");
        if (outputStream == null)
            throw new IllegalArgumentException("writeHeader fail: output stream is empty");

        String header =
              "HTTP/1.1 " + statusCode + "\r\n"
            + "Content-Type: " + contentType + "\r\n"
            + "Content-Length: " + contentLength + "\r\n"
            + "\r\n";
        outputStream.write(header.getBytes(StandardCharsets.UTF_8));
    }
}
