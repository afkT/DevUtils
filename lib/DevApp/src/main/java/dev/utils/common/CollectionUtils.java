package dev.utils.common;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import dev.utils.JCLogUtils;

/**
 * detail: 集合工具类 ( Collection - List、Set、Queue ) 等
 * @author Ttt
 * <pre>
 *     @see <a href="https://mp.weixin.qq.com/s/DmRmazyp-EmTQPXdfw9wpQ"/>
 *     // 升序
 *     Collections.sort(list);
 *     // 降序
 *     Collections.sort(list, Collections.reverseOrder());
 *     // 逆序
 *     Collections.reverse(list);
 *     // 创建不可修改集合
 *     Collections.unmodifiableList()
 *     Arrays.asList()
 * </pre>
 */
public final class CollectionUtils {

    private CollectionUtils() {
    }

    // 日志 TAG
    private static final String TAG = CollectionUtils.class.getSimpleName();

    // ==============
    // = Collection =
    // ==============

    /**
     * 判断 Collection 是否为 null
     * @param collection {@link Collection}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isEmpty(final Collection collection) {
        return (collection == null || collection.size() == 0);
    }

    /**
     * 判断 Collection 是否不为 null
     * @param collection {@link Collection}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNotEmpty(final Collection collection) {
        return (collection != null && collection.size() != 0);
    }

    // ===========
    // = 判断长度 =
    // ===========

    /**
     * 获取 Collection 长度
     * @param collection {@link Collection}
     * @return 如果 Collection 为 null, 则返回默认长度, 如果不为 null, 则返回 collection.size()
     */
    public static int length(final Collection collection) {
        return length(collection, 0);
    }

    /**
     * 获取 Collection 长度
     * @param collection    {@link Collection}
     * @param defaultLength 集合为 null 默认长度
     * @return 如果 Collection 为 null, 则返回 defaultLength, 如果不为 null, 则返回 collection.size()
     */
    public static int length(
            final Collection collection,
            final int defaultLength
    ) {
        return collection != null ? collection.size() : defaultLength;
    }

    // =

    /**
     * 获取长度 Collection 是否等于期望长度
     * @param collection {@link Collection}
     * @param length     期望长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isLength(
            final Collection collection,
            final int length
    ) {
        return collection != null && collection.size() == length;
    }

    // =

    /**
     * 判断 Collection 长度是否大于指定长度
     * @param collection {@link Collection}
     * @param length     指定长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean greaterThan(
            final Collection collection,
            final int length
    ) {
        return collection != null && collection.size() > length;
    }

    /**
     * 判断 Collection 长度是否大于等于指定长度
     * @param collection {@link Collection}
     * @param length     指定长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean greaterThanOrEqual(
            final Collection collection,
            final int length
    ) {
        return collection != null && collection.size() >= length;
    }

    // =

    /**
     * 判断 Collection 长度是否小于指定长度
     * @param collection {@link Collection}
     * @param length     指定长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean lessThan(
            final Collection collection,
            final int length
    ) {
        return collection != null && collection.size() < length;
    }

    /**
     * 判断 Collection 长度是否小于等于指定长度
     * @param collection {@link Collection}
     * @param length     指定长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean lessThanOrEqual(
            final Collection collection,
            final int length
    ) {
        return collection != null && collection.size() <= length;
    }

    // ===============
    // = 获取长度总和 =
    // ===============

    /**
     * 获取 Collection 数组长度总和
     * @param collections Collection[]
     * @return Collection 数组长度总和
     */
    public static int getCount(final Collection... collections) {
        if (collections == null) return 0;
        int count = 0;
        for (Collection collection : collections) {
            count += length(collection);
        }
        return count;
    }

    // ===========
    // = 数据获取 =
    // ===========

    /**
     * 获取数据
     * @param collection {@link Collection}
     * @param pos        索引
     * @param <T>        泛型
     * @return 对应索引的值
     */
    public static <T> T get(
            final Collection<T> collection,
            final int pos
    ) {
        if (collection != null) {
            // 防止索引为负数
            if (pos < 0) {
                return null;
            }
            if (collection instanceof List) {
                try {
                    return (T) ((List) collection).get(pos);
                } catch (Exception e) {
                    JCLogUtils.eTag(TAG, e, "get %s", pos);
                }
            } else {
                try {
                    return (T) collection.toArray()[pos];
                } catch (Exception e) {
                    JCLogUtils.eTag(TAG, e, "get %s", pos);
                }
            }
        }
        return null;
    }

    /**
     * 获取第一条数据
     * @param collection {@link Collection}
     * @param <T>        泛型
     * @return 索引为 0 的值
     */
    public static <T> T getFirst(final Collection<T> collection) {
        return get(collection, 0);
    }

    /**
     * 获取最后一条数据
     * @param collection {@link Collection}
     * @param <T>        泛型
     * @return 索引 length - 1 的值
     */
    public static <T> T getLast(final Collection<T> collection) {
        return get(collection, length(collection) - 1);
    }

    // =

    /**
     * 根据指定 value 获取 value 所在位置 + 偏移量的值
     * @param collection {@link Collection}
     * @param value      值
     * @param number     符合条件次数 ( 从 0 开始, 0 = 1)
     * @param notNull    是否不允许值为 null
     * @param offset     偏移量
     * @param <T>        泛型
     * @return 对应索引的值
     */
    public static <T> T get(
            final Collection<T> collection,
            final T value,
            final int number,
            final boolean notNull,
            final int offset
    ) {
        if (collection != null) {
            if (notNull && value == null) {
                return null;
            }
            try {
                // 保存当前临时次数
                int temp = number;
                // 转换数组
                T[] arrays = (T[]) collection.toArray();
                // 进行循环判断
                for (int i = 0, len = arrays.length; i < len; i++) {
                    T t = arrays[i];
                    // 判断值是否一样
                    if (equals(t, value)) {
                        if (temp - 1 < 0) {
                            return arrays[i + offset];
                        }
                        temp--;
                    }
                }
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "get");
            }
        }
        return null;
    }

    /**
     * 根据指定 value 获取 value 所在位置 + 偏移量的索引
     * @param collection {@link Collection}
     * @param value      值
     * @param number     符合条件次数 ( 从 0 开始, 0 = 1)
     * @param notNull    是否不允许值为 null
     * @param offset     偏移量
     * @param <T>        泛型
     * @return 对应的索引
     */
    public static <T> int getPosition(
            final Collection<T> collection,
            final T value,
            final int number,
            final boolean notNull,
            final int offset
    ) {
        if (collection != null) {
            if (notNull && value == null) {
                return -1;
            }
            try {
                // 保存当前临时次数
                int temp = number;
                // 转换数组
                T[] arrays = (T[]) collection.toArray();
                // 进行循环判断
                for (int i = 0, len = arrays.length; i < len; i++) {
                    T t = arrays[i];
                    // 判断值是否一样
                    if (equals(t, value)) {
                        if (temp - 1 < 0) {
                            return i + offset;
                        }
                        temp--;
                    }
                }
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "getPosition");
            }
        }
        return -1;
    }

    // =

    /**
     * 根据指定 value 获取索引
     * @param collection {@link Collection}
     * @param value      值
     * @param <T>        泛型
     * @return 对应的索引
     */
    public static <T> int getPosition(
            final Collection<T> collection,
            final T value
    ) {
        return getPosition(collection, value, 0, false, 0);
    }

    /**
     * 根据指定 value 获取索引
     * @param collection {@link Collection}
     * @param value      值
     * @param number     符合条件次数 ( 从 0 开始, 0 = 1)
     * @param <T>        泛型
     * @return 对应的索引
     */
    public static <T> int getPosition(
            final Collection<T> collection,
            final T value,
            final int number
    ) {
        return getPosition(collection, value, number, false, 0);
    }

    /**
     * 根据指定 value 获取索引
     * @param collection {@link Collection}
     * @param value      值
     * @param notNull    是否不允许值为 null
     * @param <T>        泛型
     * @return 对应的索引
     */
    public static <T> int getPosition(
            final Collection<T> collection,
            final T value,
            final boolean notNull
    ) {
        return getPosition(collection, value, 0, notNull, 0);
    }

    /**
     * 根据指定 value 获取索引
     * @param collection {@link Collection}
     * @param value      值
     * @param number     符合条件次数 ( 从 0 开始, 0 = 1)
     * @param notNull    是否不允许值为 null
     * @param <T>        泛型
     * @return 对应的索引
     */
    public static <T> int getPosition(
            final Collection<T> collection,
            final T value,
            final int number,
            final boolean notNull
    ) {
        return getPosition(collection, value, number, notNull, 0);
    }

    // =

    /**
     * 根据指定 value 获取索引, 不允许值为 null
     * @param collection {@link Collection}
     * @param value      值
     * @param <T>        泛型
     * @return 对应的索引
     */
    public static <T> int getPositionNotNull(
            final Collection<T> collection,
            final T value
    ) {
        return getPosition(collection, value, 0, true, 0);
    }

    /**
     * 根据指定 value 获取索引, 不允许值为 null
     * @param collection {@link Collection}
     * @param value      值
     * @param number     符合条件次数 ( 从 0 开始, 0 = 1)
     * @param <T>        泛型
     * @return 对应的索引
     */
    public static <T> int getPositionNotNull(
            final Collection<T> collection,
            final T value,
            final int number
    ) {
        return getPosition(collection, value, number, true, 0);
    }

    // =

    /**
     * 根据指定 value 获取 value 所在位置的下一个值
     * @param collection {@link Collection}
     * @param value      值
     * @param <T>        泛型
     * @return 对应索引的值
     */
    public static <T> T getNext(
            final Collection<T> collection,
            final T value
    ) {
        return get(collection, value, 0, false, 1);
    }

    /**
     * 根据指定 value 获取 value 所在位置的下一个值
     * @param collection {@link Collection}
     * @param value      值
     * @param number     符合条件次数 ( 从 0 开始, 0 = 1)
     * @param <T>        泛型
     * @return 对应索引的值
     */
    public static <T> T getNext(
            final Collection<T> collection,
            final T value,
            final int number
    ) {
        return get(collection, value, number, false, 1);
    }

    /**
     * 根据指定 value 获取 value 所在位置的下一个值
     * @param collection {@link Collection}
     * @param value      值
     * @param notNull    是否不允许值为 null
     * @param <T>        泛型
     * @return 对应索引的值
     */
    public static <T> T getNext(
            final Collection<T> collection,
            final T value,
            final boolean notNull
    ) {
        return get(collection, value, 0, notNull, 1);
    }

    /**
     * 根据指定 value 获取 value 所在位置的下一个值
     * @param collection {@link Collection}
     * @param value      值
     * @param number     符合条件次数 ( 从 0 开始, 0 = 1)
     * @param notNull    是否不允许值为 null
     * @param <T>        泛型
     * @return 对应索引的值
     */
    public static <T> T getNext(
            final Collection<T> collection,
            final T value,
            final int number,
            final boolean notNull
    ) {
        return get(collection, value, number, notNull, 1);
    }

    // =

    /**
     * 根据指定 value 获取 value 所在位置的下一个值, 不允许值为 null
     * @param collection {@link Collection}
     * @param value      值
     * @param <T>        泛型
     * @return 对应索引的值
     */
    public static <T> T getNextNotNull(
            final Collection<T> collection,
            final T value
    ) {
        return get(collection, value, 0, true, 1);
    }

    /**
     * 根据指定 value 获取 value 所在位置的下一个值, 不允许值为 null
     * @param collection {@link Collection}
     * @param value      值
     * @param number     符合条件次数 ( 从 0 开始, 0 = 1)
     * @param <T>        泛型
     * @return 对应索引的值
     */
    public static <T> T getNextNotNull(
            final Collection<T> collection,
            final T value,
            final int number
    ) {
        return get(collection, value, number, true, 1);
    }

    // =

    /**
     * 根据指定 value 获取 value 所在位置的上一个值
     * @param collection {@link Collection}
     * @param value      值
     * @param <T>        泛型
     * @return 对应索引的值
     */
    public static <T> T getPrevious(
            final Collection<T> collection,
            final T value
    ) {
        return get(collection, value, 0, false, -1);
    }

    /**
     * 根据指定 value 获取 value 所在位置的上一个值
     * @param collection {@link Collection}
     * @param value      值
     * @param number     符合条件次数 ( 从 0 开始, 0 = 1)
     * @param <T>        泛型
     * @return 对应索引的值
     */
    public static <T> T getPrevious(
            final Collection<T> collection,
            final T value,
            final int number
    ) {
        return get(collection, value, number, false, -1);
    }

    /**
     * 根据指定 value 获取 value 所在位置的上一个值
     * @param collection {@link Collection}
     * @param value      值
     * @param notNull    是否不允许值为 null
     * @param <T>        泛型
     * @return 对应索引的值
     */
    public static <T> T getPrevious(
            final Collection<T> collection,
            final T value,
            final boolean notNull
    ) {
        return get(collection, value, 0, notNull, -1);
    }

    /**
     * 根据指定 value 获取 value 所在位置的上一个值
     * @param collection {@link Collection}
     * @param value      值
     * @param number     符合条件次数 ( 从 0 开始, 0 = 1)
     * @param notNull    是否不允许值为 null
     * @param <T>        泛型
     * @return 对应索引的值
     */
    public static <T> T getPrevious(
            final Collection<T> collection,
            final T value,
            final int number,
            final boolean notNull
    ) {
        return get(collection, value, number, notNull, -1);
    }

    // =

    /**
     * 根据指定 value 获取 value 所在位置的上一个值, 不允许值为 null
     * @param collection {@link Collection}
     * @param value      值
     * @param <T>        泛型
     * @return 对应索引的值
     */
    public static <T> T getPreviousNotNull(
            final Collection<T> collection,
            final T value
    ) {
        return get(collection, value, 0, true, -1);
    }

    /**
     * 根据指定 value 获取 value 所在位置的上一个值, 不允许值为 null
     * @param collection {@link Collection}
     * @param value      值
     * @param number     符合条件次数 ( 从 0 开始, 0 = 1)
     * @param <T>        泛型
     * @return 对应索引的值
     */
    public static <T> T getPreviousNotNull(
            final Collection<T> collection,
            final T value,
            final int number
    ) {
        return get(collection, value, number, true, -1);
    }

    // ===========
    // = 添加数据 =
    // ===========

    /**
     * 添加一条数据
     * @param collection {@link Collection}
     * @param value      值
     * @param <T>        泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T> boolean add(
            final Collection<T> collection,
            final T value
    ) {
        return add(collection, value, false);
    }

    /**
     * 添加一条数据
     * @param collection {@link Collection}
     * @param value      值
     * @param notNull    是否不允许添加 null 数据
     * @param <T>        泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T> boolean add(
            final Collection<T> collection,
            final T value,
            final boolean notNull
    ) {
        if (collection != null) {
            if (notNull) {
                if (value != null) {
                    try {
                        return collection.add(value);
                    } catch (Exception e) {
                        JCLogUtils.eTag(TAG, e, "add notNull");
                    }
                }
            } else {
                try {
                    return collection.add(value);
                } catch (Exception e) {
                    JCLogUtils.eTag(TAG, e, "add");
                }
            }
        }
        return false;
    }

    /**
     * 添加一条数据 ( value 不允许为 null )
     * @param collection {@link Collection}
     * @param value      值
     * @param <T>        泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T> boolean addNotNull(
            final Collection<T> collection,
            final T value
    ) {
        return add(collection, value, true);
    }

    // =

    /**
     * 添加集合数据
     * @param collection {@link Collection}
     * @param values     准备添加的值 ( 集合 )
     * @param <T>        泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T> boolean addAll(
            final Collection<T> collection,
            final Collection<T> values
    ) {
        return addAll(collection, values, false);
    }

    /**
     * 添加集合数据
     * @param collection {@link Collection}
     * @param values     准备添加的值 ( 集合 )
     * @param notNull    是否不允许添加 null 值
     * @param <T>        泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T> boolean addAll(
            final Collection<T> collection,
            final Collection<T> values,
            final boolean notNull
    ) {
        if (collection != null && values != null) {
            if (notNull) {
                try {
                    for (T t : values) {
                        if (t != null) {
                            collection.add(t);
                        }
                    }
                    return true;
                } catch (Exception e) {
                    JCLogUtils.eTag(TAG, e, "addAll notNull");
                }
            } else {
                try {
                    return collection.addAll(values);
                } catch (Exception e) {
                    JCLogUtils.eTag(TAG, e, "addAll");
                }
            }
        }
        return false;
    }

    /**
     * 添加集合数据 ( values 内的值不允许为 null )
     * @param collection {@link Collection}
     * @param values     准备添加的值 ( 集合 )
     * @param <T>        泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T> boolean addAllNotNull(
            final Collection<T> collection,
            final Collection<T> values
    ) {
        return addAll(collection, values, true);
    }

    // ===========================
    // = 添加数据到指定索引 (List) =
    // ===========================

    /**
     * 添加一条数据到指定索引后
     * @param index 索引
     * @param list  集合
     * @param value 值
     * @param <T>   泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T> boolean add(
            final int index,
            final List<T> list,
            final T value
    ) {
        return add(index, list, value, false);
    }

    /**
     * 添加一条数据到指定索引后
     * @param index   索引
     * @param list    集合
     * @param value   值
     * @param notNull 是否不允许添加 null 数据
     * @param <T>     泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T> boolean add(
            final int index,
            final List<T> list,
            final T value,
            final boolean notNull
    ) {
        if (list != null) {
            if (notNull) {
                if (value != null) {
                    try {
                        list.add(index, value);
                        return true;
                    } catch (Exception e) {
                        JCLogUtils.eTag(TAG, e, "add notNull");
                    }
                }
            } else {
                try {
                    list.add(index, value);
                    return true;
                } catch (Exception e) {
                    JCLogUtils.eTag(TAG, e, "add");
                }
            }
        }
        return false;
    }

    /**
     * 添加一条数据到指定索引后 ( value 不允许为 null )
     * @param index 索引
     * @param list  集合
     * @param value 值
     * @param <T>   泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T> boolean addNotNull(
            final int index,
            final List<T> list,
            final T value
    ) {
        return add(index, list, value, true);
    }

    // =

    /**
     * 添加集合数据到指定索引后
     * @param index  索引
     * @param list   集合
     * @param values 准备添加的值 ( 集合 )
     * @param <T>    泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T> boolean addAll(
            final int index,
            final List<T> list,
            final List<T> values
    ) {
        return addAll(index, list, values, false);
    }

    /**
     * 添加集合数据到指定索引后
     * @param index   索引
     * @param list    集合
     * @param values  准备添加的值 ( 集合 )
     * @param notNull 是否不允许添加 null 值
     * @param <T>     泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T> boolean addAll(
            final int index,
            final List<T> list,
            final List<T> values,
            final boolean notNull
    ) {
        if (list != null && values != null) {
            if (notNull) {
                try {
                    List<T> tempList = new ArrayList<>();
                    for (T t : values) {
                        if (t != null) {
                            tempList.add(t);
                        }
                    }
                    // 添加到集合中
                    list.addAll(index, tempList);
                    return true;
                } catch (Exception e) {
                    JCLogUtils.eTag(TAG, e, "addAll notNull");
                }
            } else {
                try {
                    list.addAll(index, values);
                    return true;
                } catch (Exception e) {
                    JCLogUtils.eTag(TAG, e, "addAll");
                }
            }
        }
        return false;
    }

    /**
     * 添加集合数据到指定索引后 ( values 内的值不允许为 null )
     * @param index  索引
     * @param list   集合
     * @param values 准备添加的值 ( 集合 )
     * @param <T>    泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T> boolean addAllNotNull(
            final int index,
            final List<T> list,
            final List<T> values
    ) {
        return addAll(index, list, values, true);
    }

    // ===========
    // = 删除数据 =
    // ===========

    /**
     * 移除一条数据
     * @param collection {@link Collection}
     * @param value      准备删除的值
     * @param <T>        泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T> boolean remove(
            final Collection<T> collection,
            final T value
    ) {
        if (collection != null) {
            try {
                return collection.remove(value);
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "remove");
            }
        }
        return false;
    }

    /**
     * 移除一条数据
     * @param list 集合
     * @param pos  准备删除的索引
     * @param <T>  泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T> T remove(
            final List<T> list,
            final int pos
    ) {
        if (list != null) {
            // 防止索引为负数
            if (pos < 0) {
                return null;
            }
            try {
                return list.remove(pos);
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "remove");
            }
        }
        return null;
    }

    /**
     * 移除集合数据
     * @param collection {@link Collection}
     * @param values     准备删除的集合
     * @param <T>        泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T> boolean removeAll(
            final Collection<T> collection,
            final Collection<T> values
    ) {
        if (collection != null && values != null) {
            try {
                return collection.removeAll(values);
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "removeAll");
            }
        }
        return false;
    }

    // =

    /**
     * 清空集合中符合指定 value 的全部数据
     * @param collection {@link Collection}
     * @param value      准备对比移除的值
     * @param <T>        泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T> boolean clear(
            final Collection<T> collection,
            final T value
    ) {
        if (collection != null) {
            try {
                Iterator<T> iterator = collection.iterator();
                while (iterator.hasNext()) {
                    T t = iterator.next();
                    // 判断值是否一样
                    if (equals(t, value)) {
                        iterator.remove();
                    }
                }
                return true;
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "clear");
            }
        }
        return false;
    }

    /**
     * 保留集合中符合指定 value 的全部数据
     * @param collection {@link Collection}
     * @param value      准备对比保留的值
     * @param <T>        泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T> boolean clearNotBelong(
            final Collection<T> collection,
            final T value
    ) {
        if (collection != null) {
            try {
                Iterator<T> iterator = collection.iterator();
                while (iterator.hasNext()) {
                    T t = iterator.next();
                    // 判断值是否不一样 ( 移除不一样的 )
                    if (!equals(t, value)) {
                        iterator.remove();
                    }
                }
                return true;
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "clearNotBelong");
            }
        }
        return false;
    }

    /**
     * 清空集合全部数据
     * @param collection {@link Collection}
     * @param <T>        泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T> boolean clearAll(final Collection<T> collection) {
        if (collection != null) {
            try {
                collection.clear();
                return true;
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "clearAll");
            }
        }
        return false;
    }

    /**
     * 清空集合中为 null 的值
     * @param collection {@link Collection}
     * @param <T>        泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T> boolean clearNull(final Collection<T> collection) {
        return clear(collection, null);
    }

    // ===================
    // = 判断集合是否相同 =
    // ===================

    /**
     * 判断两个集合是否相同
     * @param collection1 第一个集合
     * @param collection2 第二个集合
     * @param <T>         泛型
     * @return {@code true} yes, {@code false} no
     */
    public static <T> boolean isEqualCollection(
            final Collection<T> collection1,
            final Collection<T> collection2
    ) {
        // 数据长度
        int len;
        // 判断数据是否相同
        if (collection1 != null && collection2 != null && (len = collection1.size()) == collection2.size()) {
            if (len == 0) return true;

            // 进行判断类型, 如果不同, 则直接跳过不处理
            if (!collection1.getClass().getName().equals(collection2.getClass().getName())) {
                return false;
            }

            // 如果集合相等, 直接跳过
            if (collection1.equals(collection2)) {
                return true;
            }

            T[] arrays1, arrays2;
            try {
                // 转换数组, 防止异常
                arrays1 = (T[]) collection1.toArray();
                arrays2 = (T[]) collection2.toArray();
            } catch (Exception e) {
                return false;
            }
            if (arrays1 == null || arrays2 == null) {
                return false;
            }

            for (int i = 0; i < len; i++) {
                // 判断两个值是否一样
                boolean equals = equals(arrays1[i], arrays2[i]);
                // 如果不一样, 直接 return
                if (!equals) {
                    return false;
                }
            }
            return true;
        }
        // 如果不符合条件, 防止两个集合都是为 null
        return (collection1 == null && collection2 == null);
    }

    /**
     * 判断多个集合是否相同
     * @param collections 集合数组
     * @param <T>         泛型
     * @return {@code true} yes, {@code false} no
     */
    public static <T> boolean isEqualCollections(final Collection<T>... collections) {
        if (collections != null && collections.length >= 2) {
            // 获取长度
            int len = collections.length;
            // 设置临时值为第一个
            Collection<T> temp = collections[0];
            // 进行判断
            for (int i = 1; i < len; i++) {
                // 判断是否一样
                boolean equalCollection = isEqualCollection(temp, collections[i]);
                // 如果不一样, 直接返回
                if (!equalCollection) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    // ===========
    // = 集合处理 =
    // ===========

    // =======
    // = 并集 =
    // =======

    /**
     * 两个集合并集处理
     * @param collection1 第一个集合
     * @param collection2 第二个集合
     * @param <T>         泛型
     * @return 并集集合
     */
    public static <T> Collection<T> union(
            final Collection<T> collection1,
            final Collection<T> collection2
    ) {
        if (collection1 != null && collection2 != null) {
            try {
                // 初始化新的集合, 默认保存第一个集合的数据
                Set<T> sets = new LinkedHashSet<>(collection1);
                sets.addAll(collection2);
                // 返回集合
                return sets;
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "union");
            }
            return null;
        } else if (collection1 != null) {
            return collection1;
        }
        return collection2;
    }

    /**
     * 多个集合并集处理
     * @param collections 集合数组
     * @param <T>         泛型
     * @return 并集集合
     */
    public static <T> Collection<T> unions(final Collection<T>... collections) {
        if (collections != null) {
            int len = collections.length;
            if (len >= 2) {
                try {
                    // 保存第一个集合
                    Set<T> sets = new LinkedHashSet<>();
                    // 防止集合为 null
                    if (collections[0] != null) {
                        sets.addAll(collections[0]);
                    }
                    // 进行循环处理
                    for (int i = 1; i < len; i++) {
                        // 获取集合值
                        Collection<T> value = collections[i];
                        if (value != null) {
                            sets.addAll(value);
                        }
                    }
                    return sets;
                } catch (Exception e) {
                    JCLogUtils.eTag(TAG, e, "unions");
                }
                return null;
            }
            return collections[0];
        }
        return null;
    }

    // =======
    // = 交集 =
    // =======

    /**
     * 两个集合交集处理
     * @param collection1 第一个集合
     * @param collection2 第二个集合
     * @param <T>         泛型
     * @return 交集集合
     */
    public static <T> Collection<T> intersection(
            final Collection<T> collection1,
            final Collection<T> collection2
    ) {
        if (collection1 != null && collection2 != null) {
            try {
                // 专门用于返回中转的集合
                Set<T> sets = new LinkedHashSet<>();

                // 初始化新的集合, 默认保存第一个集合的数据
                Set<T> setsTemp = new LinkedHashSet<>(collection1);
                // 循环第二个集合
                Iterator<T> iterator = collection2.iterator();
                while (iterator.hasNext()) {
                    T t = iterator.next();
                    // 判断是否存在, 存在则保存
                    if (setsTemp.contains(t)) {
                        sets.add(t);
                    }
                }
                // 返回集合
                return sets;
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "intersection");
            }
            return null;
        } else if (collection1 != null) {
            return collection1;
        }
        return collection2;
    }

    /**
     * 两个集合交集的补集处理
     * @param collection1 第一个集合
     * @param collection2 第二个集合
     * @param <T>         泛型
     * @return 交集集合
     */
    public static <T> Collection<T> disjunction(
            final Collection<T> collection1,
            final Collection<T> collection2
    ) {
        try {
            // 先进行并集处理
            Collection<T> unionC = union(collection1, collection2);
            // 在进行交集处理
            Collection<T> intersectionC = intersection(collection1, collection2);
            // 再进行移除处理
            if (unionC != null && intersectionC != null) {
                try {
                    // 移除数据
                    unionC.removeAll(intersectionC);
                    return unionC;
                } catch (Exception e) {
                    JCLogUtils.eTag(TAG, e, "disjunction");
                }
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "disjunction");
        }
        return null;
    }

    /**
     * 两个集合差集 ( 扣除 ) 处理
     * @param collection1 第一个集合
     * @param collection2 第二个集合
     * @param <T>         泛型
     * @return 差集 ( 扣除 ) 集合
     */
    public static <T> Collection<T> subtract(
            final Collection<T> collection1,
            final Collection<T> collection2
    ) {
        try {
            // 先进行交集处理
            Collection<T> intersectionC = intersection(collection1, collection2);
            // 保存到新的集合中
            Set<T> sets = new LinkedHashSet<>(collection1);
            // 进行移除
            sets.removeAll(intersectionC);
            // 返回集合
            return sets;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "subtract");
        }
        return null;
    }

    // ===========
    // = 快捷判断 =
    // ===========

    /**
     * 判断两个值是否一样
     * @param value1 第一个值
     * @param value2 第二个值
     * @param <T>    泛型
     * @return {@code true} yes, {@code false} no
     */
    public static <T> boolean equals(
            final T value1,
            final T value2
    ) {
        return ObjectUtils.equals(value1, value2);
    }

    // ===========
    // = 转换处理 =
    // ===========

    /**
     * 转换数组 to Object
     * @param collection {@link Collection}
     * @param <T>        泛型
     * @return 转换后的数组
     */
    public static <T> Object[] toArray(final Collection<T> collection) {
        if (collection != null) {
            try {
                return collection.toArray();
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "toArray");
            }
        }
        return null;
    }

    /**
     * 转换数组 to T
     * @param collection {@link Collection}
     * @param <T>        泛型
     * @return 转换后的泛型数组
     */
    public static <T> T[] toArrayT(final Collection<T> collection) {
        if (collection != null) {
            try {
                return new ArrayWithTypeToken<T>(collection).create();
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "toArrayT");
            }
        }
        return null;
    }

    /**
     * 集合翻转处理
     * @param collection {@link Collection}
     * @param <T>        泛型
     * @return 翻转后的集合
     */
    public static <T> Collection<T> reverse(final Collection<T> collection) {
        try {
            // 返回集合
            List<T> lists = new ArrayList<>();
            // 转换数据
            T[] arrays = (T[]) collection.toArray();
            // 循环处理
            for (int i = arrays.length - 1; i >= 0; i--) {
                lists.add(arrays[i]);
            }
            // 保存新的数据
            return lists;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "reverse");
        }
        return null;
    }

    // =============
    // = 最小值索引 =
    // =============

    /**
     * 获取集合中最小值索引
     * @param list 集合
     * @return 最小值索引
     */
    public static int getMinimumIndexI(final List<Integer> list) {
        if (list != null) {
            int len = list.size();
            if (len > 0) {
                int     index = 0;
                Integer temp  = list.get(index);
                for (int i = 1; i < len; i++) {
                    Integer value = list.get(i);
                    if (value != null) {
                        if (temp == null) {
                            index = i;
                            temp = value;
                        }
                        if (value < temp) {
                            index = i;
                            temp = value;
                        }
                    }
                }
                if (temp == null) {
                    return -1;
                }
                return index;
            }
        }
        return -1;
    }

    /**
     * 获取集合中最小值索引
     * @param list 集合
     * @return 最小值索引
     */
    public static int getMinimumIndexL(final List<Long> list) {
        if (list != null) {
            int len = list.size();
            if (len > 0) {
                int  index = 0;
                Long temp  = list.get(index);
                for (int i = 1; i < len; i++) {
                    Long value = list.get(i);
                    if (value != null) {
                        if (temp == null) {
                            index = i;
                            temp = value;
                        }
                        if (value < temp) {
                            index = i;
                            temp = value;
                        }
                    }
                }
                if (temp == null) {
                    return -1;
                }
                return index;
            }
        }
        return -1;
    }

    /**
     * 获取集合中最小值索引
     * @param list 集合
     * @return 最小值索引
     */
    public static int getMinimumIndexF(final List<Float> list) {
        if (list != null) {
            int len = list.size();
            if (len > 0) {
                int   index = 0;
                Float temp  = list.get(index);
                for (int i = 1; i < len; i++) {
                    Float value = list.get(i);
                    if (value != null) {
                        if (temp == null) {
                            index = i;
                            temp = value;
                        }
                        if (value < temp) {
                            index = i;
                            temp = value;
                        }
                    }
                }
                if (temp == null) {
                    return -1;
                }
                return index;
            }
        }
        return -1;
    }

    /**
     * 获取集合中最小值索引
     * @param list 集合
     * @return 最小值索引
     */
    public static int getMinimumIndexD(final List<Double> list) {
        if (list != null) {
            int len = list.size();
            if (len > 0) {
                int    index = 0;
                Double temp  = list.get(index);
                for (int i = 1; i < len; i++) {
                    Double value = list.get(i);
                    if (value != null) {
                        if (temp == null) {
                            index = i;
                            temp = value;
                        }
                        if (value < temp) {
                            index = i;
                            temp = value;
                        }
                    }
                }
                if (temp == null) {
                    return -1;
                }
                return index;
            }
        }
        return -1;
    }

    // =============
    // = 最大值索引 =
    // =============

    /**
     * 获取集合中最大值索引
     * @param list 集合
     * @return 最大值索引
     */
    public static int getMaximumIndexI(final List<Integer> list) {
        if (list != null) {
            int len = list.size();
            if (len > 0) {
                int     index = 0;
                Integer temp  = list.get(index);
                for (int i = 1; i < len; i++) {
                    Integer value = list.get(i);
                    if (value != null) {
                        if (temp == null) {
                            index = i;
                            temp = value;
                        }
                        if (value > temp) {
                            index = i;
                            temp = value;
                        }
                    }
                }
                if (temp == null) {
                    return -1;
                }
                return index;
            }
        }
        return -1;
    }

    /**
     * 获取集合中最大值索引
     * @param list 集合
     * @return 最大值索引
     */
    public static int getMaximumIndexL(final List<Long> list) {
        if (list != null) {
            int len = list.size();
            if (len > 0) {
                int  index = 0;
                Long temp  = list.get(index);
                for (int i = 1; i < len; i++) {
                    Long value = list.get(i);
                    if (value != null) {
                        if (temp == null) {
                            index = i;
                            temp = value;
                        }
                        if (value > temp) {
                            index = i;
                            temp = value;
                        }
                    }
                }
                if (temp == null) {
                    return -1;
                }
                return index;
            }
        }
        return -1;
    }

    /**
     * 获取集合中最大值索引
     * @param list 集合
     * @return 最大值索引
     */
    public static int getMaximumIndexF(final List<Float> list) {
        if (list != null) {
            int len = list.size();
            if (len > 0) {
                int   index = 0;
                Float temp  = list.get(index);
                for (int i = 1; i < len; i++) {
                    Float value = list.get(i);
                    if (value != null) {
                        if (temp == null) {
                            index = i;
                            temp = value;
                        }
                        if (value > temp) {
                            index = i;
                            temp = value;
                        }
                    }
                }
                if (temp == null) {
                    return -1;
                }
                return index;
            }
        }
        return -1;
    }

    /**
     * 获取集合中最大值索引
     * @param list 集合
     * @return 最大值索引
     */
    public static int getMaximumIndexD(final List<Double> list) {
        if (list != null) {
            int len = list.size();
            if (len > 0) {
                int    index = 0;
                Double temp  = list.get(index);
                for (int i = 1; i < len; i++) {
                    Double value = list.get(i);
                    if (value != null) {
                        if (temp == null) {
                            index = i;
                            temp = value;
                        }
                        if (value > temp) {
                            index = i;
                            temp = value;
                        }
                    }
                }
                if (temp == null) {
                    return -1;
                }
                return index;
            }
        }
        return -1;
    }

    // =============
    // = 获取最小值 =
    // =============

    /**
     * 获取集合中最小值
     * @param list 集合
     * @return 最小值
     */
    public static int getMinimumI(final List<Integer> list) {
        try {
            return list.get(getMinimumIndexI(list));
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getMinimumI");
        }
        return 0;
    }

    /**
     * 获取集合中最小值
     * @param list 集合
     * @return 最小值
     */
    public static long getMinimumL(final List<Long> list) {
        try {
            return list.get(getMinimumIndexL(list));
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getMinimumL");
        }
        return 0L;
    }

    /**
     * 获取集合中最小值
     * @param list 集合
     * @return 最小值
     */
    public static float getMinimumF(final List<Float> list) {
        try {
            return list.get(getMinimumIndexF(list));
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getMinimumF");
        }
        return 0f;
    }

    /**
     * 获取集合中最小值
     * @param list 集合
     * @return 最小值
     */
    public static double getMinimumD(final List<Double> list) {
        try {
            return list.get(getMinimumIndexD(list));
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getMinimumD");
        }
        return 0d;
    }

    // =============
    // = 获取最大值 =
    // =============

    /**
     * 获取集合中最大值
     * @param list 集合
     * @return 最大值
     */
    public static int getMaximumI(final List<Integer> list) {
        try {
            return list.get(getMaximumIndexI(list));
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getMaximumI");
        }
        return 0;
    }

    /**
     * 获取集合中最大值
     * @param list 集合
     * @return 最大值
     */
    public static long getMaximumL(final List<Long> list) {
        try {
            return list.get(getMaximumIndexL(list));
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getMaximumL");
        }
        return 0L;
    }

    /**
     * 获取集合中最大值
     * @param list 集合
     * @return 最大值
     */
    public static float getMaximumF(final List<Float> list) {
        try {
            return list.get(getMaximumIndexF(list));
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getMaximumF");
        }
        return 0f;
    }

    /**
     * 获取集合中最大值
     * @param list 集合
     * @return 最大值
     */
    public static double getMaximumD(final List<Double> list) {
        try {
            return list.get(getMaximumIndexD(list));
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getMaximumD");
        }
        return 0d;
    }

    // ===============
    // = 计算集合总和 =
    // ===============

    /**
     * 计算集合总和
     * @param lists 集合
     * @return 集合总和
     */
    public static int sumlistI(final List<Integer> lists) {
        return sumlistI(lists, 0, length(lists), 0);
    }

    /**
     * 计算集合总和
     * @param lists 集合
     * @param end   结束位置
     * @return 集合总和
     */
    public static int sumlistI(
            final List<Integer> lists,
            final int end
    ) {
        return sumlistI(lists, 0, end, 0);
    }

    /**
     * 计算集合总和
     * @param lists 集合
     * @param end   结束位置
     * @param extra 额外值
     * @return 集合总和
     */
    public static int sumlistI(
            final List<Integer> lists,
            final int end,
            final int extra
    ) {
        return sumlistI(lists, 0, end, extra);
    }

    /**
     * 计算集合总和
     * @param lists 集合
     * @param start 开始位置
     * @param end   结束位置
     * @param extra 额外值
     * @return 集合总和
     */
    public static int sumlistI(
            final List<Integer> lists,
            final int start,
            final int end,
            final int extra
    ) {
        int total = 0;
        if (lists != null) {
            for (int i = start; i < end; i++) {
                try {
                    total += (lists.get(i) + extra);
                } catch (Exception e) {
                    JCLogUtils.eTag(TAG, e, "sumlistI");
                }
            }
        }
        return total;
    }

    // =

    /**
     * 计算集合总和
     * @param lists 集合
     * @return 集合总和
     */
    public static long sumlistL(final List<Long> lists) {
        return sumlistL(lists, 0, length(lists), 0);
    }

    /**
     * 计算集合总和
     * @param lists 集合
     * @param end   结束位置
     * @return 集合总和
     */
    public static long sumlistL(
            final List<Long> lists,
            final int end
    ) {
        return sumlistL(lists, 0, end, 0);
    }

    /**
     * 计算集合总和
     * @param lists 集合
     * @param end   结束位置
     * @param extra 额外值
     * @return 集合总和
     */
    public static long sumlistL(
            final List<Long> lists,
            final int end,
            final long extra
    ) {
        return sumlistL(lists, 0, end, extra);
    }

    /**
     * 计算集合总和
     * @param lists 集合
     * @param start 开始位置
     * @param end   结束位置
     * @param extra 额外值
     * @return 集合总和
     */
    public static long sumlistL(
            final List<Long> lists,
            final int start,
            final int end,
            final long extra
    ) {
        long total = 0;
        if (lists != null) {
            for (int i = start; i < end; i++) {
                try {
                    total += (lists.get(i) + extra);
                } catch (Exception e) {
                    JCLogUtils.eTag(TAG, e, "sumlistL");
                }
            }
        }
        return total;
    }

    // =

    /**
     * 计算集合总和
     * @param lists 集合
     * @return 集合总和
     */
    public static float sumlistF(final List<Float> lists) {
        return sumlistF(lists, 0, length(lists), 0);
    }

    /**
     * 计算集合总和
     * @param lists 集合
     * @param end   结束位置
     * @return 集合总和
     */
    public static float sumlistF(
            final List<Float> lists,
            final int end
    ) {
        return sumlistF(lists, 0, end, 0);
    }

    /**
     * 计算集合总和
     * @param lists 集合
     * @param end   结束位置
     * @param extra 额外值
     * @return 集合总和
     */
    public static float sumlistF(
            final List<Float> lists,
            final int end,
            final float extra
    ) {
        return sumlistF(lists, 0, end, extra);
    }

    /**
     * 计算集合总和
     * @param lists 集合
     * @param start 开始位置
     * @param end   结束位置
     * @param extra 额外值
     * @return 集合总和
     */
    public static float sumlistF(
            final List<Float> lists,
            final int start,
            final int end,
            final float extra
    ) {
        float total = 0;
        if (lists != null) {
            for (int i = start; i < end; i++) {
                try {
                    total += (lists.get(i) + extra);
                } catch (Exception e) {
                    JCLogUtils.eTag(TAG, e, "sumlistF");
                }
            }
        }
        return total;
    }

    // =

    /**
     * 计算集合总和
     * @param lists 集合
     * @return 集合总和
     */
    public static double sumlistD(final List<Double> lists) {
        return sumlistD(lists, 0, length(lists), 0);
    }

    /**
     * 计算集合总和
     * @param lists 集合
     * @param end   结束位置
     * @return 集合总和
     */
    public static double sumlistD(
            final List<Double> lists,
            final int end
    ) {
        return sumlistD(lists, 0, end, 0);
    }

    /**
     * 计算集合总和
     * @param lists 集合
     * @param end   结束位置
     * @param extra 额外值
     * @return 集合总和
     */
    public static double sumlistD(
            final List<Double> lists,
            final int end,
            final double extra
    ) {
        return sumlistD(lists, 0, end, extra);
    }

    /**
     * 计算集合总和
     * @param lists 集合
     * @param start 开始位置
     * @param end   结束位置
     * @param extra 额外值
     * @return 集合总和
     */
    public static double sumlistD(
            final List<Double> lists,
            final int start,
            final int end,
            final double extra
    ) {
        double total = 0;
        if (lists != null) {
            for (int i = start; i < end; i++) {
                try {
                    total += (lists.get(i) + extra);
                } catch (Exception e) {
                    JCLogUtils.eTag(TAG, e, "sumlistD");
                }
            }
        }
        return total;
    }

    // =============
    // = 内部实现类 =
    // =============

    /**
     * detail: 持有数组 TypeToken 实体类
     * @author Ttt
     */
    public static class ArrayWithTypeToken<T> {

        // 泛型数组
        private T[] array;

        public ArrayWithTypeToken(Collection<T> collection) {
            newInstance(collection);
        }

        public ArrayWithTypeToken(
                Class<T> type,
                int size
        ) {
            newInstance(type, size);
        }

        /**
         * 添加数据
         * @param index 索引
         * @param item  数据
         */
        public void put(
                final int index,
                final T item
        ) {
            array[index] = item;
        }

        /**
         * 获取对应索引的数据
         * @param index 索引
         * @return 对应索引的数据
         */
        public T get(final int index) {
            return array[index];
        }

        /**
         * 获取数组
         * @return 泛型数组
         */
        public T[] create() {
            return array;
        }

        // ===============
        // = 内部处理方法 =
        // ===============

        /**
         * 创建数组方法
         * @param type 数组类型
         * @param size 数组长度
         */
        private void newInstance(
                final Class<T> type,
                final int size
        ) {
            array = (T[]) Array.newInstance(type, size);
        }

        /**
         * 创建数组方法
         * @param collection 集合
         */
        private void newInstance(final Collection<T> collection) {
            // 泛型实体类
            T value = null;
            // 数组
            Object[] objects = collection.toArray();
            // 获取不为 null 的泛型实体类
            for (Object object : objects) {
                if (object != null) {
                    value = (T) object;
                    break;
                }
            }
            newInstance((Class<T>) value.getClass(), objects.length);
            // 保存数据
            for (int i = 0, len = objects.length; i < len; i++) {
                Object object = objects[i];
                put(i, (object != null) ? (T) object : null);
            }
        }
    }
}