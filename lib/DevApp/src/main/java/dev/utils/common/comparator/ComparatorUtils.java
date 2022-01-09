package dev.utils.common.comparator;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import dev.utils.common.CollectionUtils;
import dev.utils.common.FileUtils;
import dev.utils.common.comparator.sort.DateSort;
import dev.utils.common.comparator.sort.DateSortAsc;
import dev.utils.common.comparator.sort.DateSortDesc;
import dev.utils.common.comparator.sort.DoubleSort;
import dev.utils.common.comparator.sort.DoubleSortAsc;
import dev.utils.common.comparator.sort.DoubleSortDesc;
import dev.utils.common.comparator.sort.FileLastModifiedSortAsc;
import dev.utils.common.comparator.sort.FileLastModifiedSortDesc;
import dev.utils.common.comparator.sort.FileLengthSortAsc;
import dev.utils.common.comparator.sort.FileLengthSortDesc;
import dev.utils.common.comparator.sort.FileNameSortAsc;
import dev.utils.common.comparator.sort.FileNameSortDesc;
import dev.utils.common.comparator.sort.FileSortAsc;
import dev.utils.common.comparator.sort.FileSortDesc;
import dev.utils.common.comparator.sort.FloatSort;
import dev.utils.common.comparator.sort.FloatSortAsc;
import dev.utils.common.comparator.sort.FloatSortDesc;
import dev.utils.common.comparator.sort.IntSort;
import dev.utils.common.comparator.sort.IntSortAsc;
import dev.utils.common.comparator.sort.IntSortDesc;
import dev.utils.common.comparator.sort.LongSort;
import dev.utils.common.comparator.sort.LongSortAsc;
import dev.utils.common.comparator.sort.LongSortDesc;
import dev.utils.common.comparator.sort.StringSort;
import dev.utils.common.comparator.sort.StringSortAsc;
import dev.utils.common.comparator.sort.StringSortDesc;

/**
 * detail: 排序比较器工具类
 * @author Ttt
 * <pre>
 *     使用以下方法要求 List 中不能存在 null 数据
 *     {@link #sort(List, Comparator)}
 *     {@link #sortAsc(List)}
 *     {@link #sortDesc(List)}
 *     视情况可用以下方法清空 null 数据
 *     {@link CollectionUtils#clearNull(Collection)}
 *     <p></p>
 *     File 排序可直接使用以下方法获取 List
 *     {@link FileUtils#listOrEmpty(File)}
 *     {@link FileUtils#listFilesOrEmpty(File)}
 * </pre>
 */
public final class ComparatorUtils {

    private ComparatorUtils() {
    }

    /**
     * List 反转处理
     * @param list 集合
     * @return {@code true} success, {@code false} fail
     */
    public static boolean reverse(final List<?> list) {
        if (list != null) {
            Collections.reverse(list);
            return true;
        }
        return false;
    }

    /**
     * List 排序处理
     * @param list       集合
     * @param comparator 排序比较器
     * @param <T>        泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T> boolean sort(
            final List<T> list,
            final Comparator<? super T> comparator
    ) {
        if (list != null) {
            Collections.sort(list, comparator);
            return true;
        }
        return false;
    }

    /**
     * List 升序处理
     * @param list 集合
     * @param <T>  泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T extends Comparable<? super T>> boolean sortAsc(final List<T> list) {
        if (list != null) {
            Collections.sort(list);
            return true;
        }
        return false;
    }

    /**
     * List 降序处理
     * @param list 集合
     * @param <T>  泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T> boolean sortDesc(final List<T> list) {
        return sort(list, Collections.reverseOrder());
    }

    // ========
    // = File =
    // ========

    /**
     * 文件修改时间升序排序
     * @param list 集合
     * @param <T>  泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T extends File> boolean sortFileLastModifiedAsc(final List<T> list) {
        return sort(list, new FileLastModifiedSortAsc());
    }

    /**
     * 文件修改时间降序排序
     * @param list 集合
     * @param <T>  泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T extends File> boolean sortFileLastModifiedDesc(final List<T> list) {
        return sort(list, new FileLastModifiedSortDesc());
    }

    /**
     * 文件大小升序排序
     * @param list 集合
     * @param <T>  泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T extends File> boolean sortFileLengthAsc(final List<T> list) {
        return sort(list, new FileLengthSortAsc());
    }

    /**
     * 文件大小降序排序
     * @param list 集合
     * @param <T>  泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T extends File> boolean sortFileLengthDesc(final List<T> list) {
        return sort(list, new FileLengthSortDesc());
    }

    /**
     * 文件名升序排序
     * @param list 集合
     * @param <T>  泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T extends File> boolean sortFileNameAsc(final List<T> list) {
        return sort(list, new FileNameSortAsc());
    }

    /**
     * 文件名降序排序
     * @param list 集合
     * @param <T>  泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T extends File> boolean sortFileNameDesc(final List<T> list) {
        return sort(list, new FileNameSortDesc());
    }

    /**
     * 文件升序排序
     * @param list 集合
     * @param <T>  泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T extends File> boolean sortFileAsc(final List<T> list) {
        return sort(list, new FileSortAsc());
    }

    /**
     * 文件降序排序
     * @param list 集合
     * @param <T>  泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T extends File> boolean sortFileDesc(final List<T> list) {
        return sort(list, new FileSortDesc());
    }

    // ========
    // = Date =
    // ========

    /**
     * Date 升序排序
     * @param list 集合
     * @param <T>  泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T extends DateSort> boolean sortDateAsc(final List<T> list) {
        return sort(list, new DateSortAsc<>());
    }

    /**
     * Date 降序排序
     * @param list 集合
     * @param <T>  泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T extends DateSort> boolean sortDateDesc(final List<T> list) {
        return sort(list, new DateSortDesc<>());
    }

    // =========
    // = String =
    // =========

    /**
     * String 升序排序
     * @param list 集合
     * @param <T>  泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T extends StringSort> boolean sortStringAsc(final List<T> list) {
        return sort(list, new StringSortAsc<>());
    }

    /**
     * String 降序排序
     * @param list 集合
     * @param <T>  泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T extends StringSort> boolean sortStringDesc(final List<T> list) {
        return sort(list, new StringSortDesc<>());
    }

    // =========
    // = Double =
    // =========

    /**
     * Double 升序排序
     * @param list 集合
     * @param <T>  泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T extends DoubleSort> boolean sortDoubleAsc(final List<T> list) {
        return sort(list, new DoubleSortAsc<>());
    }

    /**
     * Double 降序排序
     * @param list 集合
     * @param <T>  泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T extends DoubleSort> boolean sortDoubleDesc(final List<T> list) {
        return sort(list, new DoubleSortDesc<>());
    }

    // =========
    // = Float =
    // =========

    /**
     * Float 升序排序
     * @param list 集合
     * @param <T>  泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T extends FloatSort> boolean sortFloatAsc(final List<T> list) {
        return sort(list, new FloatSortAsc<>());
    }

    /**
     * Float 降序排序
     * @param list 集合
     * @param <T>  泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T extends FloatSort> boolean sortFloatDesc(final List<T> list) {
        return sort(list, new FloatSortDesc<>());
    }

    // =======
    // = Int =
    // =======

    /**
     * Int 升序排序
     * @param list 集合
     * @param <T>  泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T extends IntSort> boolean sortIntAsc(final List<T> list) {
        return sort(list, new IntSortAsc<>());
    }

    /**
     * Int 降序排序
     * @param list 集合
     * @param <T>  泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T extends IntSort> boolean sortIntDesc(final List<T> list) {
        return sort(list, new IntSortDesc<>());
    }

    // ========
    // = Long =
    // ========

    /**
     * Long 升序排序
     * @param list 集合
     * @param <T>  泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T extends LongSort> boolean sortLongAsc(final List<T> list) {
        return sort(list, new LongSortAsc<>());
    }

    /**
     * Long 降序排序
     * @param list 集合
     * @param <T>  泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T extends LongSort> boolean sortLongDesc(final List<T> list) {
        return sort(list, new LongSortDesc<>());
    }
}