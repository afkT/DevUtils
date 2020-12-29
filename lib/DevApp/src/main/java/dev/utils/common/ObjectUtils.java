package dev.utils.common;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

import dev.utils.JCLogUtils;

/**
 * detail: 对象相关工具类
 * @author Ttt
 */
public final class ObjectUtils {

    private ObjectUtils() {
    }

    // 日志 TAG
    private static final String TAG = ObjectUtils.class.getSimpleName();

    // ==========
    // = Object =
    // ==========

    /**
     * 判断对象是否为空
     * @param object 对象
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isEmpty(final Object object) {
        if (object == null) return true;
        try {
            if (object.getClass().isArray() && Array.getLength(object) == 0) {
                return true;
            }
            if (object instanceof CharSequence && object.toString().length() == 0) {
                return true;
            }
            if (object instanceof Collection && ((Collection) object).isEmpty()) {
                return true;
            }
            if (object instanceof Map && ((Map) object).isEmpty()) {
                return true;
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "isEmpty");
        }
        return false;
    }

    /**
     * 判断对象是否非空
     * @param object 对象
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNotEmpty(final Object object) {
        return !isEmpty(object);
    }

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
        // 防止两个值都为 null
        return (value1 == null && value2 == null);
    }

    /**
     * 检查对象是否为 null, 为 null 则抛出异常, 不为 null 则返回该对象
     * @param object  对象
     * @param message 报错信息
     * @param <T>     泛型
     * @return 非空对象
     * @throws NullPointerException null 异常
     */
    public static <T> T requireNonNull(
            final T object,
            final String message
    )
            throws NullPointerException {
        if (object == null) throw new NullPointerException(message);
        return object;
    }

    /**
     * 获取非空或默认对象
     * @param object        对象
     * @param defaultObject 默认值
     * @param <T>           泛型
     * @return 非空或默认对象
     */
    public static <T> T getOrDefault(
            final T object,
            final T defaultObject
    ) {
        return (object != null) ? object : defaultObject;
    }

    /**
     * 获取对象哈希值
     * @param object 对象
     * @return 哈希值
     */
    public static int hashCode(final Object object) {
        return object != null ? object.hashCode() : 0;
    }

    /**
     * 获取一个对象的独一无二的标记
     * @param object 对象
     * @return 对象唯一标记
     */
    public static String getObjectTag(final Object object) {
        if (object == null) return null;
        // 对象所在的包名 + 对象的内存地址
        return object.getClass().getName() + Integer.toHexString(object.hashCode());
    }

    /**
     * Object 转换所需类型对象
     * @param object Object
     * @param <T>    泛型
     * @return Object convert T object
     */
    public static <T> T convert(final Object object) {
        if (object == null) return null;
        try {
            return (T) object;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "convert");
        }
        return null;
    }
}