package core;

import core.body.Body;
import core.body.FileBody;
import core.body.StringBody;
import core.body.TemplateBody;
import core.model.HeaderMap;
import core.model.Response;
import core.type.Constants;
import core.type.ContentType;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * Package Name: core.worker
 * File Name: Writer
 * Description:
 * author: munke
 *
 * @version 1.0
 * @since 2026-08-04
 * <p>
 * Modification Information
 * 수정일          수정자                    수정내용
 * --------- ------------------- -------------------------------
 * 2026-08-04        munke                   최초개정
 */
public class Writer {
    private final String newline = Constants.newline;
    private final Charset charset = Constants.charset;

    public void writeResponse(Response response, OutputStream outputStream) throws IOException {
        ContentType contentType = null;
        Body body = response.getBody();

        if (body != null)
            contentType = body.getContentType();

        writeHeader(response, contentType, outputStream);
        writeBody(response.getBody(), outputStream);
    }

    private void writeHeader(Response response, ContentType contentType, OutputStream outputStream) throws IOException {
        int statusCode = response.getStatusCode();

        if (statusCode == 0)
            throw new IllegalArgumentException("writeHeader fail: statusCode is empty");

        String header =
            "HTTP/1.1 " + statusCode + newline;

        HeaderMap headerMap = response.getHeader();
        for (String key : headerMap.keySet()) {
            header += key + ": " + headerMap.get(key) + newline;
        }

        if (contentType != null)
            header += "Content-Type: " + contentType.getMIMEType() + newline
                    + "Transfer-Encoding: chunked" + newline;

        header += "Connection: close" + newline
            + newline;

        outputStream.write(header.getBytes(charset));
    }

    private void writeBody(Body body, OutputStream outputStream) throws IOException {
        if (body instanceof StringBody stringBody)
            write(stringBody, outputStream);
        else if (body instanceof TemplateBody templateBody)
            write(templateBody, outputStream);
        else if (body instanceof FileBody fileBody)
            write(fileBody, outputStream);
    }

    private void writeChunk(BufferedOutputStream bufferedOutputStream, byte[] bytes, int len) throws IOException {
        String chunkSizeInHex = Integer.toHexString(len) + newline;

        bufferedOutputStream.write(chunkSizeInHex.getBytes(charset));
        bufferedOutputStream.write(bytes, 0, len);
        bufferedOutputStream.write(newline.getBytes(charset));
    }

    private void writeLastChunk(OutputStream outputStream) throws IOException {
        String lastChunk = "0" + newline + newline;
        outputStream.write(lastChunk.getBytes(charset));
        outputStream.flush();
    }

    private void writeFile(BufferedInputStream bufferedInputStream, BufferedOutputStream bufferedOutputStream) throws IOException {
        byte[] bytes = new byte[4096];
        int bytesRead;

        while ((bytesRead = bufferedInputStream.read(bytes)) != -1) {
            writeChunk(bufferedOutputStream, bytes, bytesRead);
        }
    }

    private void write(StringBody stringBody, OutputStream outputStream) throws IOException {
        BufferedOutputStream bufferedOutputStream = null;

        try {
            bufferedOutputStream = new BufferedOutputStream(outputStream);
            String content = stringBody.getContent();
            byte[] bytes = content.getBytes(charset);

            writeChunk(bufferedOutputStream, bytes, bytes.length);
            writeLastChunk(outputStream);

            bufferedOutputStream.flush();
        } finally {
            ResourceCloser.close(bufferedOutputStream);
        }
    }

    private void write(FileBody fileBody, OutputStream outputStream) throws IOException {
        BufferedInputStream bufferedInputStream = null;
        BufferedOutputStream bufferedOutputStream = null;

        try {
            bufferedInputStream = new BufferedInputStream(new FileInputStream(fileBody.getContent()));
            bufferedOutputStream = new BufferedOutputStream(outputStream);

            writeFile(bufferedInputStream, bufferedOutputStream);
            writeLastChunk(bufferedOutputStream);

            bufferedOutputStream.flush();
        } finally {
            ResourceCloser.close(bufferedInputStream);
            ResourceCloser.close(bufferedOutputStream);
        }
    }

    private void write(TemplateBody templateBody, OutputStream outputStream) throws IOException {
        BufferedInputStream bufferedInputStream = null;
        BufferedOutputStream bufferedOutputStream = null;

        try {
            bufferedInputStream = new BufferedInputStream(new FileInputStream(templateBody.getContent()));
            bufferedOutputStream = new BufferedOutputStream(outputStream);

            writeFile(bufferedInputStream, bufferedOutputStream);

            if (templateBody.getModel() != null) {
                String json = map2json(templateBody.getModel());

                String script = """
                    <script>
                        window.page_variables = %s;
                    <script>
                    """.formatted(json);

                byte[] bytes = script.getBytes(charset);

                writeChunk(bufferedOutputStream, bytes, bytes.length);
                writeLastChunk(bufferedOutputStream);

                bufferedOutputStream.flush();
            }
        } finally {
            ResourceCloser.close(bufferedInputStream);
            ResourceCloser.close(bufferedOutputStream);
        }
    }

    public static String map2json(Map<String, String> map) {
        return map.entrySet().stream()
            .map(e -> "\"" + e.getKey() + "\":" + e.getValue())
            .collect(Collectors.joining(",", "{", "}"));
    }
}
