package dev.utils.common.comparator.sort;

import java.util.Comparator;

/**
 * detail: String Windows 排序比较器简单实现
 * @author Ttt
 */
public class StringSortWindowsSimple<T extends StringSort>
        implements Comparator<T> {

    private final WindowsExplorerStringSimpleComparator COMPARATOR = new WindowsExplorerStringSimpleComparator();

    @Override
    public int compare(
            T t,
            T t1
    ) {
        String value1 = (t != null) ? t.getStringSortValue() : null;
        String value2 = (t1 != null) ? t1.getStringSortValue() : null;
        if (value1 == null) value1 = "";
        if (value2 == null) value2 = "";
        return COMPARATOR.compare(value1, value2);
    }
}