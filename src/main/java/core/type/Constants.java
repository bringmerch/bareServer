package core.type;

import java.nio.charset.Charset;

/**
 *
 * Package Name: core
 * File Name: Constants
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
public class Constants {
    public static final String newline = "\r\n";
    public static final String USER_DIR = System.getProperty("user.dir");
    public static final String RESOURCE_ROOT = "/src/main/resources";
    public static final Charset charset = Charset.forName("UTF-8");
    public static final String charsetName = "UTF-8";
}
