package dev.utils.common.comparator.sort;

import java.util.Comparator;

/**
 * detail: Int 升序排序
 * @author Ttt
 */
public class IntSortAsc<T extends IntSort>
        implements Comparator<T> {

    @Override
    public int compare(
            T t,
            T t1
    ) {
        int value1 = (t != null) ? t.getIntSortValue() : 0;
        int value2 = (t1 != null) ? t1.getIntSortValue() : 0;
        return Integer.compare(value1, value2);
    }
}