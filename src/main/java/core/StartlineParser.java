package core;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class StartlineParser {
    private void parse(String host, String rawStartline) {
        if (host == null || host.isBlank() || rawStartline == null || rawStartline.isBlank()) {
            throw new IllegalArgumentException("HTTP request target must not be blank.");
        }

        Startline startline = new Startline();

        if (hosts.size() != 1 || hosts.get(0).fieldValue().isBlank()) {
            throw new IllegalArgumentException("HTTP/1.1 request must have exactly one Host header.");
        }

        boolean isAbsoluteUrl = rawPath.startsWith("http://") || rawPath.startsWith("https://");
        boolean isAbsolutePath = !isAbsoluteUrl && rawPath.startsWith("/");
        if (!isAbsoluteUrl && !isAbsolutePath) {
            throw new IllegalArgumentException("HTTP request target must be an absolute path or URL.");
        }

        String urlString = isAbsoluteUrl ? rawPath : "http://" + hosts.get(0).fieldValue() + rawPath;
        try {
            URL url = URI.create(urlString).toURL();
            request.setPath(url.getPath());
            parseQuery(url.getQuery(), request);
        } catch (IllegalArgumentException | MalformedURLException e) {
            throw new IllegalArgumentException("Invalid request target: " + rawPath, e);
        }
    }
}
