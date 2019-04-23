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

    /**
     * 判断对象是否为空
     * @param object 对象
     * @return {@code true} is null, {@code false} not null
     */
    public static boolean isEmpty(final Object object) {
        if (object == null) {
            return true;
        }
        if (object instanceof CharSequence && object.toString().length() == 0) {
            return true;
        }
        if (object.getClass().isArray() && Array.getLength(object) == 0) {
            return true;
        }
        if (object instanceof Collection && ((Collection) object).isEmpty()) {
            return true;
        }
        if (object instanceof Map && ((Map) object).isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * 判断对象是否非空
     * @param object 对象
     * @return {@code true} not null, {@code false} is null
     */
    public static boolean isNotEmpty(final Object object) {
        return !isEmpty(object);
    }

    /**
     * 判断对象是否相等
     * @param o1 对象1
     * @param o2 对象2
     * @return {@code true} 相等, {@code false} 不相等
     */
    public static <T> boolean equals(final Object o1, final Object o2) {
        // 两个值都不为 null
        if (o1 != null && o2 != null) {
            try {
                return o1.equals(o2);
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "equals");
            }
            return false;
        }
        // 防止两个值都为null
        return (o1 == null && o2 == null);
    }

    /**
     * 检查对象非空
     * @param object  对象
     * @param message 报错
     * @param <T>     泛型
     * @return 非空对象
     */
    public static <T> T requireNonNull(final T object, final String message) {
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
    public static <T> T getOrDefault(final T object, final T defaultObject) {
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
     * @param object
     * @return
     */
    public static String getObjectTag(final Object object) {
        if (object == null) return null;
        // 对象所在的包名 + 对象的内存地址
        return object.getClass().getName() + Integer.toHexString(object.hashCode());
    }

    /**
     * 获取转换对象
     * @param object 对象
     * @param <T>    泛型
     * @return 非空或默认对象
     */
    public static <T> T converObj(final Object object) {
        if (object == null) return null;
        try {
            return (T) object;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "converObj");
        }
        return null;
    }
}
