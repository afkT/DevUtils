package dev.utils.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import dev.utils.JCLogUtils;

/**
 * detail: 集合工具类 (Collection - List、Set、Queue) 等
 * @author Ttt
 * @see <a href="https://mp.weixin.qq.com/s/DmRmazyp-EmTQPXdfw9wpQ"/>
 * @see <a href="https://www.cnblogs.com/mr-wuxiansheng/p/7500560.html"/>
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
     * @param collection Collection
     * @return {@code true} is null, {@code false} not null
     */
    public static boolean isEmpty(final Collection collection) {
        return (collection == null || collection.size() == 0);
    }

    /**
     * 判断 Collection 是否不为 null
     * @param collection Collection
     * @return {@code true} not null, {@code false} is null
     */
    public static boolean isNotEmpty(final Collection collection) {
        return (collection != null && collection.size() != 0);
    }

    // ============
    // = 判断长度 =
    // ============

    /**
     * 获取 Collection 长度
     * @param collection Collection
     * @return 如果 Collection 为 null, 则返回默认长度, 如果不为 null, 则返回 collection.size()
     */
    public static int length(final Collection collection) {
        return length(collection, 0);
    }

    /**
     * 获取 Collection 长度
     * @param collection    Collection
     * @param defaultLength 集合为 null 默认长度
     * @return 如果 Collection 为 null, 则返回 defaultLength, 如果不为 null, 则返回 collection.size()
     */
    public static int length(final Collection collection, final int defaultLength) {
        return collection != null ? collection.size() : defaultLength;
    }

    // =

    /**
     * 获取长度 to Collection 是否等于期望长度
     * @param collection Collection
     * @param length     期望长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isLength(final Collection collection, final int length) {
        return collection != null && collection.size() == length;
    }

    // =

    /**
     * 判断 Collection 长度是否大于指定长度
     * @param collection Collection
     * @param length     指定长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean greaterThan(final Collection collection, final int length) {
        return collection != null && collection.size() > length;
    }

    /**
     * 判断 Collection 长度是否大于等于指定长度
     * @param collection Collection
     * @param length     指定长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean greaterThanOrEqual(final Collection collection, final int length) {
        return collection != null && collection.size() >= length;
    }

    // =

    /**
     * 判断 Collection 长度是否小于指定长度
     * @param collection Collection
     * @param length     指定长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean lessThan(final Collection collection, final int length) {
        return collection != null && collection.size() < length;
    }

    /**
     * 判断 Collection 长度是否小于等于指定长度
     * @param collection Collection
     * @param length     指定长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean lessThanOrEqual(final Collection collection, final int length) {
        return collection != null && collection.size() <= length;
    }

    // ============
    // = 数据获取 =
    // ============

    /**
     * 获取数据
     * @param collection Collection
     * @param pos        索引
     * @param <T>        泛型
     * @return 对应索引的值
     */
    public static <T> T get(final Collection<T> collection, final int pos) {
        if (collection != null) {
            // 防止索引为负数
            if (pos < 0) {
                return null;
            }
            if (collection instanceof List) {
                try {
                    return (T) ((List) collection).get(pos);
                } catch (Exception e) {
                    JCLogUtils.eTag(TAG, e, "get - " + pos);
                }
            } else {
                try {
                    return (T) collection.toArray()[pos];
                } catch (Exception e) {
                    JCLogUtils.eTag(TAG, e, "get - " + pos);
                }
            }
        }
        return null;
    }

    /**
     * 获取第一条数据
     * @param collection Collection
     * @param <T>        泛型
     * @return 索引为0的值
     */
    public static <T> T getFirst(final Collection<T> collection) {
        return get(collection, 0);
    }

    /**
     * 获取最后一条数据
     * @param collection Collection
     * @param <T>        泛型
     * @return 索引 length - 1 的值
     */
    public static <T> T getLast(final Collection<T> collection) {
        return get(collection, length(collection) - 1);
    }

    // =

    /**
     * 根据指定 value 获取 value 所在位置 + 偏移量的值
     * @param collection Collection
     * @param value      值
     * @param number     符合条件次数(从0开始, 0 = 1)
     * @param notNull    是否不允许值为null
     * @param offset     偏移量
     * @param <T>        泛型
     * @return 对应索引的值
     */
    public static <T> T get(final Collection<T> collection, final T value, final int number, final boolean notNull, final int offset) {
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
     * @param collection Collection
     * @param value      值
     * @param number     符合条件次数(从0开始, 0 = 1)
     * @param notNull    是否不允许值为null
     * @param offset     偏移量
     * @param <T>        泛型
     * @return 对应的索引
     */
    public static <T> int getPosition(final Collection<T> collection, final T value, final int number, final boolean notNull, final int offset) {
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
     * @param collection Collection
     * @param value      值
     * @param <T>        泛型
     * @return 对应的索引
     */
    public static <T> int getPosition(final Collection<T> collection, final T value) {
        return getPosition(collection, value, 0, false, 0);
    }

    /**
     * 根据指定 value 获取索引
     * @param collection Collection
     * @param value      值
     * @param number     符合条件次数(从0开始, 0 = 1)
     * @param <T>        泛型
     * @return 对应的索引
     */
    public static <T> int getPosition(final Collection<T> collection, final T value, final int number) {
        return getPosition(collection, value, number, false, 0);
    }

    /**
     * 根据指定 value 获取索引
     * @param collection Collection
     * @param value      值
     * @param notNull    是否不允许值为null
     * @param <T>        泛型
     * @return 对应的索引
     */
    public static <T> int getPosition(final Collection<T> collection, final T value, final boolean notNull) {
        return getPosition(collection, value, 0, notNull, 0);
    }

    /**
     * 根据指定 value 获取索引
     * @param collection Collection
     * @param value      值
     * @param number     符合条件次数(从0开始, 0 = 1)
     * @param notNull    是否不允许值为null
     * @param <T>        泛型
     * @return 对应的索引
     */
    public static <T> int getPosition(final Collection<T> collection, final T value, final int number, final boolean notNull) {
        return getPosition(collection, value, number, notNull, 0);
    }

    // =

    /**
     * 根据指定 value 获取索引, 不允许值为null
     * @param collection Collection
     * @param value      值
     * @param <T>        泛型
     * @return 对应的索引
     */
    public static <T> int getPositionNotNull(final Collection<T> collection, final T value) {
        return getPosition(collection, value, 0, true, 0);
    }

    /**
     * 根据指定 value 获取索引, 不允许值为null
     * @param collection Collection
     * @param value      值
     * @param number     符合条件次数(从0开始, 0 = 1)
     * @param <T>        泛型
     * @return 对应的索引
     */
    public static <T> int getPositionNotNull(final Collection<T> collection, final T value, final int number) {
        return getPosition(collection, value, number, true, 0);
    }

    // =

    /**
     * 根据指定 value 获取 value 所在位置的下一个值
     * @param collection Collection
     * @param value      值
     * @param <T>        泛型
     * @return 对应索引的值
     */
    public static <T> T getNext(final Collection<T> collection, final T value) {
        return get(collection, value, 0, false, 1);
    }

    /**
     * 根据指定 value 获取 value 所在位置的下一个值
     * @param collection Collection
     * @param value      值
     * @param number     符合条件次数(从0开始, 0 = 1)
     * @param <T>        泛型
     * @return 对应索引的值
     */
    public static <T> T getNext(final Collection<T> collection, final T value, final int number) {
        return get(collection, value, number, false, 1);
    }

    /**
     * 根据指定 value 获取 value 所在位置的下一个值
     * @param collection Collection
     * @param value      值
     * @param notNull    是否不允许值为null
     * @param <T>        泛型
     * @return 对应索引的值
     */
    public static <T> T getNext(final Collection<T> collection, final T value, final boolean notNull) {
        return get(collection, value, 0, notNull, 1);
    }

    /**
     * 根据指定 value 获取 value 所在位置的下一个值
     * @param collection Collection
     * @param value      值
     * @param number     符合条件次数(从0开始, 0 = 1)
     * @param notNull    是否不允许值为null
     * @param <T>        泛型
     * @return 对应索引的值
     */
    public static <T> T getNext(final Collection<T> collection, final T value, final int number, final boolean notNull) {
        return get(collection, value, number, notNull, 1);
    }

    // =

    /**
     * 根据指定 value 获取 value 所在位置的下一个值, 不允许值为null
     * @param collection Collection
     * @param value      值
     * @param <T>        泛型
     * @return 对应索引的值
     */
    public static <T> T getNextNotNull(final Collection<T> collection, final T value) {
        return get(collection, value, 0, true, 1);
    }

    /**
     * 根据指定 value 获取 value 所在位置的下一个值, 不允许值为null
     * @param collection Collection
     * @param value      值
     * @param number     符合条件次数(从0开始, 0 = 1)
     * @param <T>        泛型
     * @return 对应索引的值
     */
    public static <T> T getNextNotNull(final Collection<T> collection, final T value, final int number) {
        return get(collection, value, number, true, 1);
    }

    // =

    /**
     * 根据指定 value 获取 value 所在位置的上一个值
     * @param collection Collection
     * @param value      值
     * @param <T>        泛型
     * @return 对应索引的值
     */
    public static <T> T getPrevious(final Collection<T> collection, final T value) {
        return get(collection, value, 0, false, -1);
    }

    /**
     * 根据指定 value 获取 value 所在位置的上一个值
     * @param collection Collection
     * @param value      值
     * @param number     符合条件次数(从0开始, 0 = 1)
     * @param <T>        泛型
     * @return 对应索引的值
     */
    public static <T> T getPrevious(final Collection<T> collection, final T value, final int number) {
        return get(collection, value, number, false, -1);
    }

    /**
     * 根据指定 value 获取 value 所在位置的上一个值
     * @param collection Collection
     * @param value      值
     * @param notNull    是否不允许值为null
     * @param <T>        泛型
     * @return 对应索引的值
     */
    public static <T> T getPrevious(final Collection<T> collection, final T value, final boolean notNull) {
        return get(collection, value, 0, notNull, -1);
    }

    /**
     * 根据指定 value 获取 value 所在位置的上一个值
     * @param collection Collection
     * @param value      值
     * @param number     符合条件次数(从0开始, 0 = 1)
     * @param notNull    是否不允许值为null
     * @param <T>        泛型
     * @return 对应索引的值
     */
    public static <T> T getPrevious(final Collection<T> collection, final T value, final int number, final boolean notNull) {
        return get(collection, value, number, notNull, -1);
    }

    // =

    /**
     * 根据指定 value 获取 value 所在位置的上一个值, 不允许值为null
     * @param collection Collection
     * @param value      值
     * @param <T>        泛型
     * @return 对应索引的值
     */
    public static <T> T getPreviousNotNull(final Collection<T> collection, final T value) {
        return get(collection, value, 0, true, -1);
    }

    /**
     * 根据指定 value 获取 value 所在位置的上一个值, 不允许值为null
     * @param collection Collection
     * @param value      值
     * @param number     符合条件次数(从0开始, 0 = 1)
     * @param <T>        泛型
     * @return 对应索引的值
     */
    public static <T> T getPreviousNotNull(final Collection<T> collection, final T value, final int number) {
        return get(collection, value, number, true, -1);
    }

    // ============
    // = 添加数据 =
    // ============

    /**
     * 添加一条数据
     * @param collection Collection
     * @param value      值
     * @param <T>        泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T> boolean add(final Collection<T> collection, final T value) {
        return add(collection, value, false);
    }

    /**
     * 添加一条数据
     * @param collection Collection
     * @param value      值
     * @param notNull    是否不允许添加null数据
     * @param <T>        泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T> boolean add(final Collection<T> collection, final T value, final boolean notNull) {
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
     * 添加一条数据 - value 不允许为null
     * @param collection Collection
     * @param value      值
     * @param <T>        泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T> boolean addNotNull(final Collection<T> collection, final T value) {
        return add(collection, value, true);
    }

    // =

    /**
     * 添加集合数据
     * @param collection Collection
     * @param values     准备添加的值(集合)
     * @param <T>        泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T> boolean addAll(final Collection<T> collection, final Collection<T> values) {
        return addAll(collection, values, false);
    }

    /**
     * 添加集合数据
     * @param collection Collection
     * @param values     准备添加的值(集合)
     * @param notNull    是否不允许添加null值
     * @param <T>        泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T> boolean addAll(final Collection<T> collection, final Collection<T> values, final boolean notNull) {
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
     * 添加集合数据 - values 内的值不允许为null
     * @param collection Collection
     * @param values     准备添加的值(集合)
     * @param <T>        泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T> boolean addAllNotNull(final Collection<T> collection, final Collection<T> values) {
        return addAll(collection, values, true);
    }

    // ===============================
    // = 添加数据 - 到指定索引(List) =
    // ===============================

    /**
     * 添加一条数据到指定索引后
     * @param index 索引
     * @param list  集合
     * @param value 值
     * @param <T>   泛型
     */
    public static <T> void add(final int index, final List<T> list, final T value) {
        add(index, list, value, false);
    }

    /**
     * 添加一条数据到指定索引后
     * @param index   索引
     * @param list    集合
     * @param value   值
     * @param notNull 是否不允许添加null数据
     * @param <T>     泛型
     */
    public static <T> void add(final int index, final List<T> list, final T value, final boolean notNull) {
        if (list != null) {
            if (notNull) {
                if (value != null) {
                    try {
                        list.add(index, value);
                    } catch (Exception e) {
                        JCLogUtils.eTag(TAG, e, "add notNull");
                    }
                }
            } else {
                try {
                    list.add(index, value);
                } catch (Exception e) {
                    JCLogUtils.eTag(TAG, e, "add");
                }
            }
        }
    }

    /**
     * 添加一条数据到指定索引后 - value 不允许为null
     * @param index 索引
     * @param list  集合
     * @param value 值
     * @param <T>   泛型
     */
    public static <T> void addNotNull(final int index, final List<T> list, final T value) {
        add(index, list, value, true);
    }

    // =

    /**
     * 添加集合数据到指定索引后
     * @param index  索引
     * @param list   集合
     * @param values 准备添加的值(集合)
     * @param <T>    泛型
     */
    public static <T> void addAll(final int index, final List<T> list, final List<T> values) {
        addAll(index, list, values, false);
    }

    /**
     * 添加集合数据到指定索引后
     * @param index   索引
     * @param list    集合
     * @param values  准备添加的值(集合)
     * @param notNull 是否不允许添加null值
     * @param <T>     泛型
     */
    public static <T> void addAll(final int index, final List<T> list, final List<T> values, final boolean notNull) {
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
                } catch (Exception e) {
                    JCLogUtils.eTag(TAG, e, "addAll notNull");
                }
            } else {
                try {
                    list.addAll(index, values);
                } catch (Exception e) {
                    JCLogUtils.eTag(TAG, e, "addAll");
                }
            }
        }
    }

    /**
     * 添加集合数据到指定索引后 - values 内的值不允许为null
     * @param index  索引
     * @param list   集合
     * @param values 准备添加的值(集合)
     * @param <T>    泛型
     */
    public static <T> void addAllNotNull(final int index, final List<T> list, final List<T> values) {
        addAll(index, list, values, true);
    }

    // ============
    // = 删除数据 =
    // ============

    /**
     * 移除一条数据
     * @param collection Collection
     * @param value      准备删除的值
     * @param <T>        泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T> boolean remove(final Collection<T> collection, final T value) {
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
    public static <T> T remove(final List<T> list, final int pos) {
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
     * @param collection Collection
     * @param values     准备删除的集合
     * @param <T>        泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T> boolean removeAll(final Collection<T> collection, final Collection<T> values) {
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
     * @param collection Collection
     * @param value      准备对比移除的值
     * @param <T>        泛型
     */
    public static <T> void clear(final Collection<T> collection, final T value) {
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
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "clear");
            }
        }
    }

    /**
     * 保留集合中符合指定 value 的全部数据
     * @param collection Collection
     * @param value      准备对比保留的值
     * @param <T>        泛型
     */
    public static <T> void clearNotBelong(final Collection<T> collection, final T value) {
        if (collection != null) {
            try {
                Iterator<T> iterator = collection.iterator();
                while (iterator.hasNext()) {
                    T t = iterator.next();
                    // 判断值是否不一样（保留不一样的）
                    if (!equals(t, value)) {
                        iterator.remove();

                    }
                }
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "clearNotBelong");
            }
        }
    }

    /**
     * 清空集合全部数据
     * @param collection Collection
     */
    public static <T> void clearAll(final Collection<T> collection) {
        if (collection != null) {
            try {
                collection.clear();
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "clearAll");
            }
        }
    }

    /**
     * 清空集合中为 null 的值
     * @param collection Collection
     * @param <T>        泛型
     */
    private static <T> void clearNull(final Collection<T> collection) {
        clear(collection, null);
    }

    // ====================
    // = 判断集合是否相同 =
    // ====================

    /**
     * 判断两个集合是否相同
     * @param collection1 第一个集合
     * @param collection2 第二个集合
     * @param <T>         泛型
     * @return {@code true} yes, {@code false} no
     */
    public static <T> boolean isEqualCollection(final Collection<T> collection1, final Collection<T> collection2) {
        // 数据长度
        int len = 0;
        // 判断数据是否相同
        if (collection1 != null && collection2 != null && (len = collection1.size()) == collection2.size()) {
            if (len == 0) return true;

            // 进行判断类型, 如果不同, 则直接跳过不处理
            if (!collection1.getClass().getName().equals(collection2.getClass().getName())) {
                return false;
            }

            // 如果集合相等, 直接跳过
            if (collection1 == collection2) {
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

    // ============
    // = 集合处理 =
    // ============

    // ========
    // = 并集 =
    // ========

    /**
     * 两个集合并集处理
     * @param collection1 第一个集合
     * @param collection2 第二个集合
     * @param <T>         泛型
     * @return 并集集合
     */
    public static <T> Collection<T> union(final Collection<T> collection1, final Collection<T> collection2) {
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
                    // 防止集合为null
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

    // ========
    // = 交集 =
    // ========

    /**
     * 两个集合交集处理
     * @param collection1 第一个集合
     * @param collection2 第二个集合
     * @param <T>         泛型
     * @return 交集集合
     */
    public static <T> Collection<T> intersection(final Collection<T> collection1, final Collection<T> collection2) {
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
    public static <T> Collection<T> disjunction(final Collection<T> collection1, final Collection<T> collection2) {
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
     * 两个集合差集（扣除）处理
     * @param collection1 第一个集合
     * @param collection2 第二个集合
     * @param <T>         泛型
     * @return 差集（扣除）集合
     */
    public static <T> Collection<T> subtract(final Collection<T> collection1, final Collection<T> collection2) {
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

    // ============
    // = 快捷判断 =
    // ============

    /**
     * 判断两个值是否一样
     * @param value1 第一个值
     * @param value2 第二个值
     * @param <T>    泛型
     * @return {@code true} yes, {@code false} no
     */
    public static <T> boolean equals(final T value1, final T value2) {
        // 两个值都不为 null
        if (value1 != null && value2 != null) {
            try {
                if (value1 instanceof String && value2 instanceof String) {
                    return value1.equals(value2);
                } else if (value1 instanceof CharSequence && value2 instanceof CharSequence) {
                    CharSequence v1 = (CharSequence) value1;
                    CharSequence v2 = (CharSequence) value2;
                    // 获取数据长度
                    int length = v1.length();
                    // 判断数据长度是否一致
                    if (length == v2.length()) {
                        for (int i = 0; i < length; i++) {
                            if (v1.charAt(i) != v2.charAt(i)) {
                                return false;
                            }
                        }
                        return true;
                    }
                    return false;
                }
                // 其他都使用 equals 判断
                return value1.equals(value2);
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "equals");
            }
            return false;
        }
        // 防止两个值都为null
        return (value1 == null && value2 == null);
    }

    // ============
    // = 转换处理 =
    // ============

    /**
     * 转换数组 to 集合数据类型数组
     * @param collection Collection
     * @param <T>        泛型
     * @return 转换后的数组
     */
    public static <T> T[] toArray(final Collection<T> collection) {
        if (collection != null) {
            try {
                return (T[]) collection.toArray();
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "toArray");
            }
        }
        return null;
    }

    /**
     * 转换数组 to Object
     * @param collection Collection
     * @param <T>        泛型
     * @return 转换后的数组
     */
    public static <T> Object[] toArrayToObject(final Collection<T> collection) {
        if (collection != null) {
            try {
                return collection.toArray();
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "toArrayToObject");
            }
        }
        return null;
    }

    /**
     * 集合翻转处理
     * @param collection Collection
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
}
