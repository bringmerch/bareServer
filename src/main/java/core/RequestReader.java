package core;

import core.model.HeaderMap;
import core.model.Request;
import core.type.Constants;
import core.type.MethodType;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * Package Name: core.worker
 * File Name: RequestReader
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
public class RequestReader {
    private static final String newline = Constants.newline;

    public static Request readRequest(BufferedReader bufferedReader) throws BareException, IOException {
        return parseRequest(readRequestAsString(bufferedReader));
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
                .append(newline); // 줄바꿈
        }
        if (headerlines.isEmpty())
            throw new BareException(500, "read fail: Host header is required.");

        return result.concat(startline).concat(newline).concat(headerlines.toString());
    }

    private static Request parseRequest(String rawRequest) {
        Request request = new Request();

        if (rawRequest == null || rawRequest.isBlank())
            throw new IllegalArgumentException("parse failed: empty rawRequest.");

        String[] lines = rawRequest.split(newline);

        // 1. header
        HeaderMap headerMap = new HeaderMap();

        for (int i = 1; i < lines.length; i++) {
            String[] keyValue = lines[i].split(":", 2);

            if (keyValue.length != 2)
                throw new IllegalArgumentException("parse failed: no ':' in header line. lines[i] is : " + lines[i]);
            if (keyValue[1].isBlank())
                throw new IllegalArgumentException("parse failed: empty header field value. header field is : " + keyValue[0]);

            if (keyValue[0].equalsIgnoreCase("Cookie")) {
                String sessionId = getSessionId(keyValue[1]);
                headerMap.put("B-SESSION-ID", sessionId); // 세션ID 쿠키는 B-SESSION-ID 헤더에 따로 담는다.
            }

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

    private static String getSessionId(String cookieValue) {
        String key = "B-SESSION-ID=";
        int startIndex = cookieValue.indexOf(key);

        if (startIndex == -1)
            return null;

        int sessionIdStart = startIndex + key.length(); // sessionId 시작하는 index
        int nextSemicolon = cookieValue.indexOf(";", sessionIdStart); // sessionId 오른쪽에서 제일 가까운 ";"의 위치
        int sessionIdEnd = (nextSemicolon == -1) ? cookieValue.length(): nextSemicolon; // ";" 없으면 문자열 맨끝

        return cookieValue.substring(sessionIdStart, sessionIdEnd);
    }
}
