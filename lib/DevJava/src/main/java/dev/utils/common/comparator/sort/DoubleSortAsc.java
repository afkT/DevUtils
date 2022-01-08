package dev.utils.common.comparator.sort;

import java.util.Comparator;

/**
 * detail: Double 升序排序
 * @author Ttt
 */
public class DoubleSortAsc<T extends DoubleSort>
        implements Comparator<T> {

    @Override
    public int compare(
            T t,
            T t1
    ) {
        double value1 = (t != null) ? t.getDoubleSortValue() : 0D;
        double value2 = (t1 != null) ? t1.getDoubleSortValue() : 0D;
        return Double.compare(value1, value2);
    }
}