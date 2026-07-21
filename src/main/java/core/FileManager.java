package core;

import java.io.File;

// todo
public class FileManager {
    private static final String USER_DIR = System.getProperty("user.dir");
    private static final String RESOURCE_ROOT = "/src/main/resources";

    public static File loadFile(String filePath) {
        if (filePath == null || filePath.isBlank()) {
            throw new IllegalArgumentException("filePath must not be blank.");
        }
        return new File(USER_DIR + RESOURCE_ROOT + filePath);
    }
}
