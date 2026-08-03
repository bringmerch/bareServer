package core.handlerResult;

import core.ResourceCloser;

import java.io.*;

/**
 *
 * Package Name: core
 * File Name: HandlerResult
 * Description:
 * author: munke
 *
 * @version 1.0
 * @see core
 * @since 2026-08-03
 * <p>
 * Modification Information
 * 수정일          수정자                    수정내용
 * --------- ------------------- -------------------------------
 * 2026-08-03        munke                   최초개정
 */
public sealed interface HandlerResult
    permits TextResult, JsonResult, FileResult, HTMLResult {

    public void writeBody(File file, OutputStream outputStream) throws IOException {
        writeFile();
    }

    public abstract void writeBody(Ou)

    public void writeFile() {
        BufferedOutputStream bufferedOutputStream = null;
        BufferedInputStream bufferedInputStream = null;
        try {
            bufferedOutputStream = new BufferedOutputStream(outputStream); // null 리턴 안 함
            bufferedInputStream = new BufferedInputStream(new FileInputStream(file)); // null 리턴 안 함

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
