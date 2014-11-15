package vfs;

import java.util.Iterator;

public interface VFS {
    boolean isDirectory(String path);

    String getAbsolutePath(String file);

    Iterator<String> getIterator(String startDir);
}
