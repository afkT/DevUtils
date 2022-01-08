package dev.utils.common.comparator.sort;

import java.util.Comparator;

/**
 * detail: Float 升序排序
 * @author Ttt
 */
public class FloatSortAsc<T extends FloatSort>
        implements Comparator<T> {

    @Override
    public int compare(
            T t,
            T t1
    ) {
        float value1 = (t != null) ? t.getFloatSortValue() : 0F;
        float value2 = (t1 != null) ? t1.getFloatSortValue() : 0F;
        return Float.compare(value1, value2);
    }
}