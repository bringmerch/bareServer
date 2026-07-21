package core;

import java.util.ArrayList;
import java.util.List;

public class HeaderParser {
    public HeaderMap parse(String data) throws IllegalArgumentException {
        if (data == null || data.isBlank())
            throw new IllegalArgumentException("header parsing failed: empty data.");
        String[] rawHeader = data.split(Constants.CRLF.getValue());
        HeaderMap header = new HeaderMap();
        int hostHeaderCount = 0;

        for (String line : rawHeader) {
            String[] keyValue = line.split(":", 2);
            if (keyValue.length != 2)
                throw new IllegalArgumentException("header parsing failed: Invalid rawHeader = " + rawHeader);
            if (keyValue[0].equalsIgnoreCase("host"))
                hostHeaderCount++;
            if (keyValue[0].isBlank() || keyValue[1].isBlank())
                throw new IllegalArgumentException("header parsing failed: empty key or value not allowed. key: " + keyValue[0] + " value: " + keyValue[1]);
            header.put(keyValue[0].trim(), keyValue[1].trim());
        }

        if (headers.isEmpty())
            throw new IllegalArgumentException("header parsing failed: illegal data.");

        if (hostHeaderCount == 0 || hostHeaderCount > 1)
            throw new IllegalArgumentException("header parsing failed: wrong host header. host header counted: " + hostHeaderCount);

        return headers;
    }
}
