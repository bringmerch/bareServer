package core.body;

import core.type.ContentType;

import java.io.File;

/**
 *
 * Package Name: core
 * File Name: FileBody
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
public class FileBody extends Body<File> {
    public FileBody(File content, ContentType contentType) {
        super(content, contentType);
    }

    @Override
    public long getContentLength() {
        return this.content.length();
    }

}
