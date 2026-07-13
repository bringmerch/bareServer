package core;

import java.io.File;
import java.io.IOException;
/**
 *
 * Package Name: core
 * File Name: FileManager
 * Description:
 * author: munke
 *
 * @version 1.0
 * @see core
 * @since 2026-07-08
 * <p>
 * Modification Information
 * 수정일          수정자                    수정내용
 * --------- ------------------- -------------------------------
 * 2026-07-08        munke                   최초개정
 */
public class FileManager {
    static final String userDir = System.getProperty("user.dir");
    static final String resourceRoot = "/src/main/resources";

    public File loadFile(String filePath) {
        return new File(userDir + resourceRoot + filePath);
    }
}
