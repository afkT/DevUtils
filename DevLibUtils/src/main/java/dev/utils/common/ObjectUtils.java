package dev.utils.common;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

import dev.utils.JCLogUtils;

/**
 * detail: 对象相关工具类
 * Created by Ttt
 */
public final class ObjectUtils {

    private ObjectUtils() {
    }

    // 日志 TAG
    private static final String TAG = ObjectUtils.class.getSimpleName();

    /**
     * 判断对象是否为空
     * @param obj 对象
     * @return true : 为空, false : 不为空
     */
    public static boolean isEmpty(final Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof CharSequence && obj.toString().length() == 0) {
            return true;
        }
        if (obj.getClass().isArray() && Array.getLength(obj) == 0) {
            return true;
        }
        if (obj instanceof Collection && ((Collection) obj).isEmpty()) {
            return true;
        }
        if (obj instanceof Map && ((Map) obj).isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * 判断对象是否非空
     * @param obj 对象
     * @return true : 非空, false : 空
     */
    public static boolean isNotEmpty(final Object obj) {
        return !isEmpty(obj);
    }

    /**
     * 判断对象是否相等
     * @param o1 对象1
     * @param o2 对象2
     * @return true : 相等, false : 不相等
     */
    public static boolean equals(Object o1, Object o2) {
        return o1 == o2 || (o1 != null && o1.equals(o2));
    }

    /**
     * 检查对象非空
     * @param object 对象
     * @param message 报错
     * @param <T> 范型
     * @return 非空对象
     */
    public static <T> T requireNonNull(T object, String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
        return object;
    }

    /**
     * 获取非空或默认对象
     * @param object 对象
     * @param defaultObject 默认值
     * @param <T> 范型
     * @return 非空或默认对象
     */
    public static <T> T getOrDefault(T object, T defaultObject) {
        if (object == null) {
            return defaultObject;
        }
        return object;
    }

    /**
     * 获取对象哈希值
     * @param object 对象
     * @return 哈希值
     */
    public static int hashCode(Object object) {
        return object != null ? object.hashCode() : 0;
    }

    /**
     * 获取一个对象的独一无二的标记
     * @param object
     * @return
     */
    public static String getObjectTag(Object object) {
        if (object == null) return null;
        // 对象所在的包名 + 对象的内存地址
        return object.getClass().getName() + Integer.toHexString(object.hashCode());
    }

    /**
     * 获取转换对象
     * @param obj 对象
     * @param <T> 范型
     * @return 非空或默认对象
     */
    public static <T> T converObj(Object obj) {
        try {
            return (T) obj;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "converObj");
        }
        return null;
    }
}
