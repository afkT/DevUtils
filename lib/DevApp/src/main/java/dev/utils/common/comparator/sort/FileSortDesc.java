package dev.utils.common.comparator.sort;

import java.io.File;
import java.util.Comparator;

/**
 * detail: 文件降序排序
 * @author Ttt
 */
public class FileSortDesc
        implements Comparator<File> {

    @Override
    public int compare(
            File f,
            File f1
    ) {
        if (f == null || f1 == null) {
            return 0;
        }
        return f1.compareTo(f);
    }
}