package dev.utils.common.comparator.sort;

import java.util.Comparator;

/**
 * detail: String 降序排序
 * @author Ttt
 */
public class StringSortDesc<T extends StringSort>
        implements Comparator<T> {

    @Override
    public int compare(
            T t,
            T t1
    ) {
        String value1 = (t != null) ? t.getStringSortValue() : null;
        String value2 = (t1 != null) ? t1.getStringSortValue() : null;
        if (value1 == null) value1 = "";
        if (value2 == null) value2 = "";
        return value2.compareTo(value1);
    }
}