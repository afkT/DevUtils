package dev.utils.common.comparator.sort;

import java.util.Comparator;

/**
 * detail: Long 降序排序
 * @author Ttt
 */
public class LongSortDesc<T extends LongSort>
        implements Comparator<T> {

    @Override
    public int compare(
            T t,
            T t1
    ) {
        long value1 = (t != null) ? t.getLongSortValue() : 0L;
        long value2 = (t1 != null) ? t1.getLongSortValue() : 0L;
        return Long.compare(value2, value1);
    }
}