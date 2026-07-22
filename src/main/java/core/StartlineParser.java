package core;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class StartlineParser {
    public static Startline parse(String host, String rawStartline) {
        if (host == null || host.isBlank())
            throw new IllegalArgumentException("startline parse failed: invalid host.");
        if (rawStartline == null || rawStartline.isBlank())
            throw new IllegalArgumentException("startline parse failed: empty startline.");

        String[] parts = rawStartline.split("\\s");
        if (parts.length != 3)
            throw new IllegalArgumentException("startline parse failed: invalid parts length.");

        Method method = Method.from(parts[0]);
        if (method == null)
            throw new IllegalArgumentException("startline parse failed: undefined method.");


        String rawPath = parts[1];

        boolean isAbsoluteUrl = rawPath.startsWith("http://") || rawPath.startsWith("https://");
        boolean isAbsolutePath = !isAbsoluteUrl && rawPath.startsWith("/");
        if (!isAbsoluteUrl && !isAbsolutePath)
            throw new IllegalArgumentException("startline parse failed: HTTP request target must be an absolute path or URL.");

        String urlString = isAbsoluteUrl ? rawPath : "http://" + host + rawPath;
        URL url;
        try {
            url = URI.create(urlString).toURL();
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("startline parse failed: invalid path. url cannot be generated. rawPath: " + rawPath, e);
        }

        String rawQuery = url.getQuery();
        Map<String, String> query = new HashMap<>();

        if (rawQuery != null) {
            String[] queries = rawQuery.split("&");

            for (String q : queries) {
                String[] keyValue = q.split("=");
                if (keyValue.length != 2)
                    throw new IllegalArgumentException("startline parse failed: invalid queryString. rawQuery: " + rawQuery);
                query.put(keyValue[0], keyValue[1]);
            }
        }

        return new Startline(method, url.getPath(), query);
    }
}
