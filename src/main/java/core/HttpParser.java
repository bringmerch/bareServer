package core;

import core.record.HttpMessage;
import core.record.HttpMessageType;

import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * Package Name: core
 * File Name: HttpParser
 * Description:
 * author: munke
 *
 * @version 1.0
 * @see core
 * @since 2026-07-01
 * <p>
 * Modification Information
 * 수정일          수정자                    수정내용
 * --------- ------------------- -------------------------------
 * 2026-07-01        munke                   최초개정
 */
public class HttpParser <T> {
    private final HttpMessageType httpMessageType;

    // Constructor
    HttpParser (HttpMessageType httpMessageType) {
        this.httpMessageType = httpMessageType;
    }

    public parseMessage() {

    }

    private static void parseStartLine() {

    }

    private static void parseHeader() {

    }

    private static void parseBody() {

    }

    private static void parseFiles() {

    }

    private static void parseFile() {

    }
}
