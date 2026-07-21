package core;

import java.io.Closeable;
import java.io.IOException;

public class ResourceCloser {
    public static <T extends Closeable> void close(T resource) {
        try {
            if (resource != null)
                resource.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
