package core;

import java.io.BufferedReader;
import java.io.IOException;

public class DataProcessor {
    public Request readRequest(BufferedReader bufferedReader) throws IOException, BareException {
        if (bufferedReader == null)
            throw new IllegalArgumentException("readRequest failed: bufferedReader is empty.");
        Request request = new Request();
        String rawStartline = readRawStartline(bufferedReader);
        String rawHeader = readRawHeaders(bufferedReader);
        Header header = HeaderParser.parse(rawHeader);
        request.setHeader(header);
        Startline startline = StartlineParser.parse(header.get("host"), rawStartline);
        request.setMethod(startline.method());
        request.setPath(startline.path());
        if (!startline.query().isEmpty())
            request.setQuery(startline.query());
        return request;
    }

    private String readRawStartline(BufferedReader bufferedReader) throws IOException, BareException {
        String line = bufferedReader.readLine();
        if (line == null || line.isBlank())
            throw new BareException(500, "readRawStartline failed: startline is empty.");
        return line;
    }

    private String readRawHeaders(BufferedReader bufferedReader) throws IOException, BareException {
        StringBuilder lines = new StringBuilder();
        String line;
        while (true) {
            line = bufferedReader.readLine();
            if (line == null || line.isBlank())
                break;
            lines.append(line)
                 .append(Constants.CRLF.getValue());
        }
        if (lines.isEmpty())
            throw new BareException(500, "At least 1 header is required.");
        return lines.toString();
    }
}
