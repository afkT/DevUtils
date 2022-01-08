package dev.utils.common.comparator.sort;

import java.util.Comparator;
import java.util.Date;

/**
 * detail: Date 升序排序
 * @author Ttt
 */
public class DateSortAsc<T extends DateSort>
        implements Comparator<T> {

    @Override
    public int compare(
            T t,
            T t1
    ) {
        Date date1  = (t != null) ? t.getDateSortValue() : null;
        Date date2  = (t1 != null) ? t1.getDateSortValue() : null;
        long value1 = (date1 != null) ? date1.getTime() : 0L;
        long value2 = (date2 != null) ? date2.getTime() : 0L;
        return Long.compare(value1, value2);
    }
}