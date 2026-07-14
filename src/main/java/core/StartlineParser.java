package core;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;

/**
 *
 * Package Name: core
 * File Name: StartlineWorker
 * Description:
 * author: munke
 *
 * @version 1.0
 * @see core
 * @since 2026-07-13
 * <p>
 * Modification Information
 * 수정일          수정자                    수정내용
 * --------- ------------------- -------------------------------
 * 2026-07-13        munke                   최초개정
 */
public class StartlineParser {
    public String read(DataProcessor dataProcessor) throws IOException {
        String readLine = dataProcessor.readLine();

        if (readLine == null || readLine.isBlank()) {
            throw new IOException("Empty http request startLine.");
        }

        return readLine;
    }

    public void parse(String data, Request request) {
        String[] parts = data.split(" ");

        if (parts.length != 3)
            throw new RuntimeException("invalid http message start line. start line must be 3 parts.");

        if (!parts[2].equalsIgnoreCase("HTTP/1.1"))
            throw new RuntimeException("invalid http version. must be HTTP/1.1.");

        request.setStartline(parts);

        URI uri;
        URL url;

        List<Header> hosts = request.getHeaders("Host");
        if (hosts.size() != 1)
            throw new RuntimeException("invalid host header.");
        String host = hosts.getFirst().fieldValue();

        request.setMethod(Method.from(parts[0]));

        String path = parts[1];
        boolean isAbsoluteUrl = path.startsWith("http://") || path.startsWith("https://");
        boolean isPath = !isAbsoluteUrl && path.startsWith("/");

        if (!(isAbsoluteUrl || isPath))
            throw new RuntimeException("invalid http message start line. path must start with / or scheme.");

        String urlString = isPath ? host + path : path;

        try {
            uri = URI.create("http://" + urlString);
            url = uri.toURL();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new RuntimeException("URL parsing failed.");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("MalformedUrlException occurred.");
        }

        request.setPath(url.getPath());
        String query = url.getQuery();

        if (query != null) {
            String[] pairs = query.split("&");

            for (String pair : pairs) {
                String[] keyValue = pair.split("=");
                request.addQueryString(new QueryString(keyValue[0], keyValue[1]));
            }
        }
    }
}
