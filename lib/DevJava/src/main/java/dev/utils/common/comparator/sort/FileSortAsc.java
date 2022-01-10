package dev.utils.common.comparator.sort;

import java.io.File;
import java.util.Comparator;

/**
 * detail: 文件升序排序
 * @author Ttt
 */
public class FileSortAsc
        implements Comparator<File> {

    @Override
    public int compare(
            File f,
            File f1
    ) {
        if (f == null || f1 == null) {
            return -1;
        }
        return f.compareTo(f1);
    }
}