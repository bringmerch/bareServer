package core;

import java.io.IOException;

/**
 *
 * Package Name: core
 * File Name: HeaderWorker
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
public class HeaderParser {
    public void parse(String data, Request request) {
        String[] headers = data.split("\n");

        System.out.println("data = " + data);

        for (String h : headers) {
            String[] pair = h.split(":", 2);

            if (pair.length != 2)
                throw new RuntimeException("invalid Header. parsing failed.");

            String fieldName = pair[0].trim();
            String fieldValue = pair[1].trim();

            request.addHeader(new Header(fieldName, fieldValue));
        }
    }

    public String read(DataProcessor dataProcessor) throws IOException {
        StringBuilder lines = new StringBuilder();
        String readLine;

        while (true) {
            readLine = dataProcessor.readLine();

            if (readLine == null || readLine.isBlank())
                break;

            lines.append(readLine);
        }

        if (lines.isEmpty()) {
            throw new RuntimeException("invalid header lines.");
        }

        System.out.println("Headers : " + lines);
        return lines.toString();
    }
}
