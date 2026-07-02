package core;

import java.io.IOException;

/**
 *
 * Package Name: core
 * File Name: BareServer
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

public class BareServer {
    // application start
    public static void main(String[] args) throws IOException {
        new Connector().start();
    }
}