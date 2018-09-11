package dev.utils.common;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.Date;

import dev.utils.JCLogUtils;

/**
 * detail: 类工具
 * @author mty
 */
public final class ClassUtils {

    private ClassUtils(){
    }

    // 日志TAG
    private static final String TAG = ClassUtils.class.getSimpleName();

    /**
     * 判断类是否是基础数据类型
     * 目前支持11种
     * @param clazz
     * @return
     */
    public static boolean isBaseDataType(Class<?> clazz) {
        return clazz.isPrimitive() || clazz.equals(String.class) || clazz.equals(Boolean.class)
                || clazz.equals(Integer.class) || clazz.equals(Long.class) || clazz.equals(Float.class)
                || clazz.equals(Double.class) || clazz.equals(Byte.class) || clazz.equals(Character.class)
                || clazz.equals(Short.class) || clazz.equals(Date.class) || clazz.equals(byte[].class)
                || clazz.equals(Byte[].class);
    }

    /**
     * 根据类获取对象：不再必须一个无参构造
     * @param claxx
     * @return
     */
    public static <T> T newInstance(Class<T> claxx){
        try {
            Constructor<?>[] cons = claxx.getDeclaredConstructors();
            for (Constructor<?> c : cons) {
                Class[] cls = c.getParameterTypes();
                if (cls.length == 0) {
                    c.setAccessible(true);
                    return (T) c.newInstance();
                } else {
                    Object[] objs = new Object[cls.length];
                    for (int i = 0; i < cls.length; i++) {
                        objs[i] = getDefaultPrimiticeValue(cls[i]);
                    }
                    c.setAccessible(true);
                    return (T) c.newInstance(objs);
                }
            }
        } catch (Exception e){
            JCLogUtils.eTag(TAG, e, "newInstance");
        }
        return null;
    }

    /**
     * 判断 Class 是否为原始类型（boolean、char、byte、short、int、long、float、double）
     * @param clazz
     * @return
     */
    public static Object getDefaultPrimiticeValue(Class clazz) {
        if (clazz.isPrimitive()) {
            return clazz == boolean.class ? false : 0;
        }
        return null;
    }

    /**
     * 判断是否集合类型
     * @param claxx
     * @return
     */
    public static boolean isCollection(Class claxx) {
        return Collection.class.isAssignableFrom(claxx);
    }

    /**
     * 是否数组类型
     * @param claxx
     * @return
     */
    public static boolean isArray(Class claxx) {
        return claxx.isArray();
    }

}
