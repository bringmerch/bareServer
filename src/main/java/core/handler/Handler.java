package core.handler;

import core.BareException;
import core.handlerResult.HandlerResult;
import core.model.HeaderMap;
import core.model.Request;
import core.model.Response;
import core.model.WorkOrder;
import core.type.Constants;
import core.type.MethodType;
import core.worker.ErrorWorker;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * Package Name: core.handler
 * File Name: Handler
 * Description:
 * author: munke
 *
 * @version 1.0
 * @see core.handler
 * @since 2026-08-03
 * <p>
 * Modification Information
 * 수정일          수정자                    수정내용
 * --------- ------------------- -------------------------------
 * 2026-08-03        munke                   최초개정
 */
public abstract class Handler {
    protected static final String newLine = Constants.CRLF.getValue();
    protected static final Charset charset = Charset.forName("UTF-8");
    private static final String USER_DIR = System.getProperty("user.dir");
    private static final String RESOURCE_ROOT = "/src/main/resources";

    public static Request readRequest(BufferedReader bufferedReader) throws IOException, BareException {
        String rawRequest = readRequestAsString(bufferedReader);
        return parseRequest(rawRequest);
    }

    private static String readRequestAsString(BufferedReader bufferedReader) throws IOException, BareException {
        String result = "";

        // 1. startline
        String startline = bufferedReader.readLine();
        if (startline == null || startline.isBlank())
            throw new BareException(500, "read failed: startline is empty.");

        // 2. header
        StringBuilder headerlines = new StringBuilder();
        String headerline;

        while ((headerline = bufferedReader.readLine()) != null) {
            if (headerline.isBlank())
                break; // 개행 나오면 헤더 끝
            headerlines.append(headerline)
                .append(Constants.CRLF.getValue()); // 줄바꿈
        }
        if (headerlines.isEmpty())
            throw new BareException(500, "read fail: Host header is required.");

        return result.concat(startline).concat(newLine).concat(headerlines.toString());
    }

    private static Request parseRequest(String rawRequest) {
        Request request = new Request();

        if (rawRequest == null || rawRequest.isBlank())
            throw new IllegalArgumentException("parse failed: empty rawRequest.");

        String[] lines = rawRequest.split(Constants.CRLF.getValue());

        // 1. header
        HeaderMap headerMap = new HeaderMap();

        for (int i = 1; i < lines.length; i++) {
            String[] keyValue = lines[i].split(":", 2);

            if (keyValue.length != 2)
                throw new IllegalArgumentException("parse failed: no ':' in header line. lines[i] is : " + lines[i]);
            if (keyValue[1].isBlank())
                throw new IllegalArgumentException("parse failed: empty header field value. header field is : " + keyValue[0]);

            headerMap.put(keyValue[0].trim(), keyValue[1].trim());
        }

        if (headerMap.get("Host") == null || headerMap.get("Host").isBlank())
            throw new IllegalArgumentException("parse failed: Host header is required.");

        // 2. methodType, path
        String[] parts = lines[0].split("\\s"); // 공백으로 쪼갬

        if (parts.length != 3)
            throw new IllegalArgumentException("parse failed: invalid parts length. lines[0] is: " + lines[0]);

        MethodType methodType = MethodType.from(parts[0]);
        String rawPath = parts[1];

        boolean isAbsoluteUrl = rawPath.startsWith("http://") || rawPath.startsWith("https://");
        boolean isAbsolutePath = !isAbsoluteUrl && rawPath.startsWith("/");

        if (!isAbsoluteUrl && !isAbsolutePath)
            throw new IllegalArgumentException("parse failed: HTTP request target must be an absolute path or URL.");

        String urlString = isAbsoluteUrl ? rawPath : "http://" + headerMap.get("Host") + rawPath;
        URL url;

        try {
            url = URI.create(urlString).toURL();
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("parse failed: invalid path. url generation failed. rawPath: " + rawPath, e);
        }

        String rawQuery = url.getQuery();
        Map<String, String> queryMap = new HashMap<>();

        if (rawQuery != null) {
            String[] queries = rawQuery.split("&");

            for (String q : queries) {
                String[] keyValue = q.split("=");

                if (keyValue.length != 2)
                    throw new IllegalArgumentException("parse failed: invalid query string. rawQuery: " + rawQuery);

                queryMap.put(keyValue[0], keyValue[1]);
            }
        }

        request.setMethod(methodType);
        request.setPath(url.getPath());
        request.setQueryMap(queryMap);
        request.setHeaderMap(headerMap);

        return request;
    }

    File loadFile(String filePath) {
        if (filePath == null || filePath.isBlank()) {
            throw new IllegalArgumentException("filePath must not be blank.");
        }
        return new File(USER_DIR + RESOURCE_ROOT + filePath);
    }

    protected void writeHeader(int statusCode, String contentType, OutputStream outputStream) throws IOException {
        if (statusCode == 0)
            throw new IllegalArgumentException("writeHeader fail: statusCode is empty");
        if (contentType == null || contentType.isBlank())
            throw new IllegalArgumentException("writeHeader fail: contentType is empty");

        String header =
            "HTTP/1.1 " + statusCode + newLine
                + "Content-Type: " + contentType + newLine
                + "Transfer-Encoding: chunked" + newLine
                + "Connection: close" + newLine
                + newLine;
        outputStream.write(header.getBytes(charset));
    }

    protected abstract void writeBody(HandlerResult handlerResult, OutputStream outputStream);

    public static void executeErrorWorker(int statusCode, OutputStream outputStream) {
        try {
            new ErrorWorker().execute(new WorkOrder(statusCode), outputStream);
        } catch (Exception e) { // 에러반환도 실패했으면 로그찍고 무시
            System.out.println(e.getMessage());
        }
    }

    public void doResponse(Response response, HandlerResult handlerResult, OutputStream outputStream) {
        this.writeHeader(response, outputStream);
        this.writeBody(handlerResult, outputStream);
    }




}
