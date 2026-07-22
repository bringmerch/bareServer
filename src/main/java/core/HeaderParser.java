package core;

public class HeaderParser {
    public static Header parse(String rawHeader) {
        if (rawHeader == null || rawHeader.isBlank())
            throw new IllegalArgumentException("header parsing failed: empty rawHeader.");

        String[] lines = rawHeader.split(Constants.CRLF.getValue());
        Header header = new Header();
        int hostHeaderCount = 0;

        for (String line : lines) {
            String[] keyValue = line.split(":", 2);
            if (keyValue.length != 2)
                throw new IllegalArgumentException("header parsing failed: Invalid rawHeader = " + rawHeader);
            if (keyValue[0].isBlank() || keyValue[1].isBlank())
                throw new IllegalArgumentException("header parsing failed: empty key or value not allowed. key: " + keyValue[0] + " value: " + keyValue[1]);
            if (keyValue[0].equalsIgnoreCase("host"))
                hostHeaderCount++;
            header.put(keyValue[0].trim(), keyValue[1].trim());
        }

        if (header.isEmpty())
            throw new IllegalArgumentException("header parsing failed: illegal rawHeader. at least one header required.");
        if (hostHeaderCount == 0 || hostHeaderCount > 1)
            throw new IllegalArgumentException("header parsing failed: wrong host header. host header counted: " + hostHeaderCount);

        return header;
    }
}
