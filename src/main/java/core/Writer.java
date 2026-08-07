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

    public void writeResponse(Response response, BufferedOutputStream bufferedOutputStream) throws IOException {
        ContentType contentType = null;
        Body<?> body = response.getBody();

        if (body != null)
            contentType = body.getContentType();

        writeHeader(response, contentType, bufferedOutputStream);
        writeBody(response.getBody(), bufferedOutputStream);
    }

    private void writeHeader(Response response, ContentType contentType, BufferedOutputStream bufferedOutputStream) throws IOException {
        int statusCode = response.getStatusCode();

        if (statusCode == 0)
            throw new IllegalArgumentException("writeHeader fail: statusCode is empty");

        StringBuilder header =
            new StringBuilder("HTTP/1.1 " + statusCode + newline);

        HeaderMap headerMap = response.getHeader();
        for (String key : headerMap.keySet()) {
            header.append(key).append(": ").append(headerMap.get(key)).append(newline);
        }

        if (contentType != null) {
            header.append("Content-Type: ").append(contentType.getMIMEType()).append(newline);
            header.append("Transfer-Encoding: chunked" + newline);
        }

        header.append("Connection: close" + newline);
        header.append(newline);

        bufferedOutputStream.write(header.toString().getBytes(charset));
        bufferedOutputStream.flush();
    }

    private void writeBody(Body body, BufferedOutputStream bufferedOutputStream) throws IOException {
        if (body instanceof StringBody stringBody)
            write(stringBody, bufferedOutputStream);
        else if (body instanceof TemplateBody templateBody)
            write(templateBody, bufferedOutputStream);
        else if (body instanceof FileBody fileBody)
            write(fileBody, bufferedOutputStream);
    }

    private void writeChunk(BufferedOutputStream bufferedOutputStream, byte[] bytes, int len) throws IOException {
        String chunkSizeInHex = Integer.toHexString(len) + newline;

        bufferedOutputStream.write(chunkSizeInHex.getBytes(charset));
        bufferedOutputStream.write(bytes, 0, len);
        bufferedOutputStream.write(newline.getBytes(charset));
        bufferedOutputStream.flush();
    }

    private void writeLastChunk(BufferedOutputStream bufferedOutputStream) throws IOException {
        String lastChunk = "0" + newline + newline;
        bufferedOutputStream.write(lastChunk.getBytes(charset));
        bufferedOutputStream.flush();
    }

    private void writeFile(BufferedInputStream bufferedInputStream, BufferedOutputStream bufferedOutputStream) throws IOException {
        byte[] bytes = new byte[4096];
        int bytesRead;

        while ((bytesRead = bufferedInputStream.read(bytes)) != -1) {
            writeChunk(bufferedOutputStream, bytes, bytesRead);
        }
    }

    private void write(StringBody stringBody, BufferedOutputStream bufferedOutputStream) throws IOException {
        String content = stringBody.getContent();
        byte[] bytes = content.getBytes(charset);

        writeChunk(bufferedOutputStream, bytes, bytes.length); // StringBody는 한번에보낸다.
        writeLastChunk(bufferedOutputStream);
    }

    private void write(FileBody fileBody, BufferedOutputStream bufferedOutputStream) throws IOException {
        BufferedInputStream bufferedInputStream = null;

        try {
            bufferedInputStream = new BufferedInputStream(new FileInputStream(fileBody.getContent()));

            writeFile(bufferedInputStream, bufferedOutputStream);
            writeLastChunk(bufferedOutputStream);
        } finally {
            ResourceCloser.close(bufferedInputStream);
        }
    }

    private void write(TemplateBody templateBody, BufferedOutputStream bufferedOutputStream) throws IOException {
        BufferedInputStream bufferedInputStream = null;

        try {
            bufferedInputStream = new BufferedInputStream(new FileInputStream(templateBody.getContent()));

            // 동적 정보 필요한 페이지
                String json = map2json(templateBody.getModel());

            // page_variables 전역객체 전달
            String script = """
                <script>
                    window.page_variables = %s;
                </script>
                """.formatted(json);

            byte[] bytes = script.getBytes(charset);
            // 페이지 상단에 전역객체 정보를 담은 script
            writeChunk(bufferedOutputStream, bytes, bytes.length);

            // html
            writeFile(bufferedInputStream, bufferedOutputStream);

            // 종료
            writeLastChunk(bufferedOutputStream);
        } finally {
            ResourceCloser.close(bufferedInputStream);
        }
    }

    public static String map2json(Map<String, String> map) {
        return map.entrySet().stream()
            .map(e ->
                "\"" + e.getKey() + "\"" +
                ":"
                + "\"" + e.getValue() + "\"")
            .collect(Collectors.joining(",", "{", "}"));
    }
}