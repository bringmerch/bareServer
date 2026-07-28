package core;

import java.io.*;
import java.nio.charset.Charset;

public abstract class Worker {
    protected static final String newLine = Constants.CRLF.getValue();
    protected static final Charset charset = Charset.forName("UTF-8");
    private static final String USER_DIR = System.getProperty("user.dir");
    private static final String RESOURCE_ROOT = "/src/main/resources";

    public abstract void execute(WorkOrder workOrder, OutputStream outputStream) throws BareException, IOException;
    protected abstract void writeBody(File file, OutputStream outputStream) throws IOException;

    File loadFile(String filePath) {
        if (filePath == null || filePath.isBlank()) {
            throw new IllegalArgumentException("filePath must not be blank.");
        }
        return new File(USER_DIR + RESOURCE_ROOT + filePath);
    }

    protected void writeHeader(int statusCode, String contentType, OutputStream outputStream) throws IOException {
        if (statusCode == 0)
            throw new IllegalArgumentException("writeHeader fail: statusCode is empty");
        if (contentType == null || contentType.isBlank())
            throw new IllegalArgumentException("writeHeader fail: contentType is empty");

        String header =
              "HTTP/1.1 " + statusCode + newLine
            + "Content-Type: " + contentType + newLine
            + "Transfer-Encoding: chunked" + newLine
            + "Connection: close" + newLine
            + newLine;
        outputStream.write(header.getBytes(charset));
    }

    public static void executeErrorWorker(int statusCode, OutputStream outputStream) {
        try {
            new ErrorWorker().execute(new WorkOrder(statusCode), outputStream);
        } catch (Exception e) { // 에러반환도 실패했으면 로그찍고 무시
            System.out.println(e.getMessage());
        }
    }
}
