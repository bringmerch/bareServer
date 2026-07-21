package core;

import java.io.BufferedReader;
import java.io.IOException;

public class DataProcessor {
    public Request readRequest(BufferedReader bufferedReader) throws IOException, HttpException {
        if (bufferedReader == null)
            throw new IllegalArgumentException("bufferedReader must not be null.");
        Request request = new Request();
        String rawStartline = readRawStartline(bufferedReader);
        String rawHeaders = readRawHeaders(bufferedReader);
        HeaderParser headerParser = new HeaderParser();
        HeaderMap header = headerParser.parse(rawHeaders);
        request.setHeader(header);
        StartlineParser startlineParser = new StartlineParser();
        Startline startline = startlineParser.parse(header.get(), rawStartline);
        request.setMethod(startline.method());
        request.setPath(startline.path());
        request.setQuery(startline.queryStrings());
        return request;
    }

    private String readRawStartline(BufferedReader bufferedReader) throws IOException, HttpException {
        String line = bufferedReader.readLine();
        if (line == null || line.isBlank()) {
            throw new HttpException(500, "Empty HTTP request start line.");
        }
        return line;
    }

    private String readRawHeaders(BufferedReader bufferedReader) throws IOException, HttpException {
        StringBuilder lines = new StringBuilder();
        String line;

        while (true) {
            line = bufferedReader.readLine();
            if (line == null || line.isBlank()) {
                break;
            }
            lines.append(line)
                 .append(Constants.CRLF.getValue());
        }

        if (lines.isEmpty()) {
            throw new HttpException(500, "At least 1 header is required.");
        }

        return lines.toString();
    }
}
