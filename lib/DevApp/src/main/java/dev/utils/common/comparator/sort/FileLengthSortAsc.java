package dev.utils.common.comparator.sort;

import java.io.File;
import java.util.Comparator;

/**
 * detail: 文件大小升序排序
 * @author Ttt
 * <pre>
 *     不考虑文件夹内部文件大小, 文件夹大小根据 API length() 进行比较
 * </pre>
 */
public class FileLengthSortAsc
        implements Comparator<File> {

    @Override
    public int compare(
            File f,
            File f1
    ) {
        long value1 = (f != null) ? f.length() : 0L;
        long value2 = (f1 != null) ? f1.length() : 0L;
        return Long.compare(value1, value2);
    }
}