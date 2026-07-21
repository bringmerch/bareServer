package core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * Package Name: core
 * File Name: FileBody
 * Description:
 * author: munke
 *
 * @version 1.0
 * @see core
 * @since 2026-07-21
 * <p>
 * Modification Information
 * 수정일          수정자                    수정내용
 * --------- ------------------- -------------------------------
 * 2026-07-21        munke                   최초개정
 */
public class FileBody implements ResponseBody {
    private static final int BUFFER_SIZE = 4096;
    private final File file;

    FileBody(File file) {
        if (file == null || !file.isFile())
            throw new IllegalArgumentException("file must be an existing file.");
        this.file = file;
    }

    @Override
    public long contentLength() {
        return file.length();
    }

    @Override
    public void writeTo(OutputStream outputStream) throws IOException {
        if (outputStream == null)
            throw new IOException("FileBody.writeTo() Fail: outputStream is null.");

        FileInputStream fileInputStream = null;

        try {
            fileInputStream = new FileInputStream(file);

            byte[] buffer = new byte[BUFFER_SIZE];
            int readLength;

            while((readLength = fileInputStream.read(buffer)) != -1)
                outputStream.write(buffer, 0, readLength);
        } finally {
            ResourceCloser.close(fileInputStream);
        }
    }
}
