package core;

import java.io.*;

/**
 *
 * Package Name: core
 * File Name: StaticWorker
 * Description:
 * author: munke
 *
 * @version 1.0
 * @see core
 * @since 2026-07-28
 * <p>
 * Modification Information
 * 수정일          수정자                    수정내용
 * --------- ------------------- -------------------------------
 * 2026-07-28        munke                   최초개정
 */
public class StaticWorker extends Worker {
    @Override
    public void execute(WorkOrder workOrder, OutputStream outputStream) throws BareException, IOException {
        String resourcePath = workOrder.getResourcePath();
        ContentType contentType = workOrder.getContentType();
        if (resourcePath == null || resourcePath.isBlank() || contentType == null)
            throw new IllegalArgumentException("execute failed: illegal workOrder.");

        File file = loadFile(resourcePath);
        if (!file.isFile())
            throw new BareException(404, "execute failed: resource not found.");

        writeHeader(200, contentType.getMIMEType(), outputStream);
        writeBody(file, outputStream);
    }

    @Override
    protected void writeBody(File file, OutputStream outputStream) throws IOException {
        BufferedOutputStream bufferedOutputStream = null;
        BufferedInputStream bufferedInputStream = null;
        try {
            if ((bufferedOutputStream = new BufferedOutputStream(outputStream)) == null)
                throw new IOException("writeBody failed: empty bufferedOutputStream.");
            if ((bufferedInputStream = new BufferedInputStream(new FileInputStream(file))) == null)
                throw new IOException("writeBody failed: empty bufferedInputStream.");

            byte[] bytes = new byte[4096];
            int bytesRead;

            while ((bytesRead = bufferedInputStream.read(bytes)) != -1) {
                String chunkSizeInHex = Integer.toHexString(bytesRead) + newLine;
                bufferedOutputStream.write(chunkSizeInHex.getBytes(charset));
                bufferedOutputStream.write(bytes, 0, bytesRead);
                bufferedOutputStream.write(newLine.getBytes(charset));
            }

            bufferedOutputStream.flush();

            String lastChunk = "0" + newLine + newLine;
            bufferedOutputStream.write(lastChunk.getBytes(charset));
            bufferedOutputStream.flush();
        } finally {
            ResourceCloser.close(bufferedInputStream);
            ResourceCloser.close(bufferedOutputStream);
        }
    }
}
