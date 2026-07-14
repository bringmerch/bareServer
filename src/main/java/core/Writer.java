package core;


import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

/**
 *
 * Package Name: core
 * File Name: Writer
 * Description:
 * author: munke
 *
 * @version 1.0
 * @see core
 * @since 2026-07-14
 * <p>
 * Modification Information
 * 수정일          수정자                    수정내용
 * --------- ------------------- -------------------------------
 * 2026-07-14        munke                   최초개정
 */
public class Writer {
    public static void writeByte(Response<ByteArrayWrapper> response, DataProcessor dataProcessor) throws IOException {
        ByteArrayWrapper bodyWrapper = response.getBody();
        byte[] body = bodyWrapper.getBytes();

        String commonPart = Writer.getCommonPart(
            response.getStatusCode(),
            response.getHeader("Content-Type").fieldValue(),
            body.length
        );

        OutputStream outputStream = dataProcessor.outputStream;

        outputStream.write(commonPart.getBytes());
        outputStream.write(body);
        outputStream.flush();
    }

    public static void writeString(Response<String> response, DataProcessor dataProcessor) throws IOException {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(dataProcessor.outputStream, StandardCharsets.UTF_8);
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

        bufferedWriter.write(
            Writer.getCommonPart(
                response.getStatusCode(),
                response.getHeader("Content-Type").fieldValue(),
                (response.getBody()).getBytes(StandardCharsets.UTF_8).length
            )
        );
        bufferedWriter.write((String)response.getBody());
        bufferedWriter.flush();
        bufferedWriter.close();
    }

    public static String getCommonPart(int statusCode, String contentType, int contentLength) {
        return """
                HTTP/1.1 %d\r
                Content-Type: %s\r
                Content-Length: %d\r
                \r
                """
            .formatted(
                statusCode,
                contentType,
                contentLength
            );
    }
}
