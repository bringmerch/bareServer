package core;


import java.io.BufferedWriter;
import java.io.IOException;
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
    public static void writeByte(byte[] responseBody, DataProcessor dataProcessor) {}

    public static void writeString(Response response, DataProcessor dataProcessor) throws IOException {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(dataProcessor.outputStream, StandardCharsets.UTF_8);
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

        StringBuilder sb = new StringBuilder();

        sb.append("""
                HTTP/1.1 %d\r
                Content-Type: %s\r
                Content-Length: %d\r
                \r
                """
            .formatted(
                response.getStatusCode(),
                response.getHeader("Content-Type").fieldValue(),
                String.valueOf(((String)response.getBody()).getBytes(StandardCharsets.UTF_8))
            )
        );

        bufferedWriter.write(sb.toString());
        bufferedWriter.write((String)response.getBody());
        bufferedWriter.flush();
        bufferedWriter.close();

        dataProcessor.close();
    }
}
