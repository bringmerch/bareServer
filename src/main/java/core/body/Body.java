package core.body;

import core.type.Constants;
import core.type.ContentType;

import java.io.*;
import java.nio.charset.Charset;

/**
 *
 * Package Name: core
 * File Name: Body
 * Description:
 * author: munke
 *
 * @version 1.0
 * @see core
 * @since 2026-08-04
 * <p>
 * Modification Information
 * 수정일          수정자                    수정내용
 * --------- ------------------- -------------------------------
 * 2026-08-04        munke                   최초개정
 */
public abstract class Body<T> {
    protected final T content;
    protected final ContentType contentType;
    protected final Charset charset = Constants.charset;

    public Body(T content, ContentType contentType) {
        this.content = content;
        this.contentType = contentType;
    }

    public T getContent() {
        return this.content;
    }

    public ContentType getContentType() {
        return this.contentType;
    }

    protected abstract long getContentLength();
}
