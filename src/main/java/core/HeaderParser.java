package core;

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
    protected void parse(String data, Request request) {
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

    protected String read(DataProcessor inputDataProcessor) {
        System.out.println("readHeader...");
        byte b;
        StringBuilder rawHeaderLines = new StringBuilder();
        boolean fin = false; // header 읽기 종료 여부
        int read = 0;
        int remaining;

        while (!fin) {
            if (read == 0) {
                read = inputDataProcessor.loadByteBuffer();

                if (read <= 0)
                    break;

                inputDataProcessor.inputBuffer.flip(); // 읽기 모드 진입
            }

            remaining = read;
            int processed = 0;

            boolean prevWasNewLine = false;

            for (int i = 0; i < remaining; i++) {
                b = inputDataProcessor.inputBuffer.get();
                processed++;

                if (b == '\r') // \r 뒤에 어차피 \n 오니까 먹는다.
                    continue;

                rawHeaderLines.append((char)b);

                if (b == '\n') {
                    if (prevWasNewLine) { // 두번연속 new line 나오면 헤더 읽기 종료
                        fin = true;
                        break;
                    }
                    prevWasNewLine = true;
                } else {
                    prevWasNewLine = false;
                }
            }

            read = remaining == processed ? 0 : remaining - processed;
        }

        if (rawHeaderLines.isEmpty()) {
            throw new RuntimeException("invalid header lines.");
        }

        return rawHeaderLines.toString();
    }
}
