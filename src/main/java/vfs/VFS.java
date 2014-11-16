package vfs;

import java.io.File;
import java.util.Iterator;

public interface VFS {
    static boolean isDirectory(String path) {
        return new File(path).isDirectory();
    }

    static boolean exists(String path) {
        return new File(path).exists();
    }

    Iterator<String> getIterator(String startDir);
}
