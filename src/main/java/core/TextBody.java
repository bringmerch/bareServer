package core;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 *
 * Package Name: core
 * File Name: TextBody
 * Description:
 * author: munke
 *
 * @version 1.0
 * @see core
 * @since 2026-07-22
 * <p>
 * Modification Information
 * 수정일          수정자                    수정내용
 * --------- ------------------- -------------------------------
 * 2026-07-22        munke                   최초개정
 */
public class TextBody implements ResponseBody {
    private final String body;

    public TextBody(String body) {
        if (body == null || body.isBlank())
            throw new IllegalArgumentException("TextBody creation fail: body is empty.");
        this.body = body;
    }

    @Override
    public long contentLength() {
        return body.getBytes(StandardCharsets.UTF_8).length;
    }

    @Override
    public void writeTo(OutputStream outputStream) throws IOException, BareException {
        if (outputStream == null)
            throw new IllegalArgumentException("writeTo failed: output stream is empty.");

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
        if (outputStreamWriter == null)
            throw new IOException("writeTo failed: outputStreamWriter is empty.");

        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
        if (bufferedWriter == null)
            throw new IOException("writeTo failed: bufferedWriter is null.");

        long len = body.length();

        if (len == 0)
            throw new BareException(404, "writeTo failed: resource not found.");

        int offset = 0;

        while (offset < len) {
            int end = Math.min(offset + BUFFER_SIZE, body.length());
            bufferedWriter.write(this.body, offset, end);
            offset = end;
        }
        bufferedWriter.flush();
    }
}
