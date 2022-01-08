package dev.utils.common.comparator.sort;

import java.io.File;
import java.util.Comparator;

/**
 * detail: 文件修改时间升序排序
 * @author Ttt
 */
public class FileLastModifiedSortAsc
        implements Comparator<File> {

    @Override
    public int compare(
            File f,
            File f1
    ) {
        long value1 = (f != null) ? f.lastModified() : 0L;
        long value2 = (f1 != null) ? f1.lastModified() : 0L;
        return Long.compare(value1, value2);
    }
}