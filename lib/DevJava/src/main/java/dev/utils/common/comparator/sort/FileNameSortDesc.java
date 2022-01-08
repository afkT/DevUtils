package dev.utils.common.comparator.sort;

import java.io.File;
import java.util.Comparator;

/**
 * detail: 文件名降序排序
 * @author Ttt
 */
public class FileNameSortDesc
        implements Comparator<File> {

    @Override
    public int compare(
            File f,
            File f1
    ) {
        if (f == null || f1 == null) {
            return 0;
        }
        if (f.isDirectory() && f1.isFile()) {
            return 1;
        }
        if (f.isFile() && f1.isDirectory()) {
            return -1;
        }
        return f1.getName().compareTo(f.getName());
    }
}