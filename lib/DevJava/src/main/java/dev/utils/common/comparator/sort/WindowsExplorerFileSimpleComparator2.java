package dev.utils.common.comparator.sort;

import java.io.File;
import java.util.Comparator;

/**
 * detail: Windows 目录资源文件排序比较器
 * @author Ttt
 */
public class WindowsExplorerFileSimpleComparator2
        implements Comparator<File> {

    private final WindowsExplorerStringSimpleComparator2 COMPARATOR = new WindowsExplorerStringSimpleComparator2();

    @Override
    public int compare(
            File f,
            File f1
    ) {
        if (f == null || f1 == null) {
            return -1;
        }
        return COMPARATOR.compare(f.getName(), f1.getName());
    }
}