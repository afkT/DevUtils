package dev.utils.common;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Date;

import dev.utils.JCLogUtils;

/**
 * detail: 类工具
 * Created by Ttt
 */
public final class ClassUtils {

    private ClassUtils() {
    }

    // 日志 TAG
    private static final String TAG = ClassUtils.class.getSimpleName();

    /**
     * 判断类是否是基础数据类型
     * 目前支持11种
     * @param clazz
     * @return
     */
    public static boolean isBaseDataType(Class<?> clazz) {
        return clazz != null && (clazz.isPrimitive() || clazz.equals(String.class) || clazz.equals(Boolean.class)
                || clazz.equals(Integer.class) || clazz.equals(Long.class) || clazz.equals(Float.class)
                || clazz.equals(Double.class) || clazz.equals(Byte.class) || clazz.equals(Character.class)
                || clazz.equals(Short.class) || clazz.equals(Date.class) || clazz.equals(byte[].class)
                || clazz.equals(Byte[].class));
    }

    /**
     * 根据类获取对象：不再必须一个无参构造
     * @param clazz
     * @return
     */
    public static <T> T newInstance(Class<T> clazz) {
        try {
            Constructor<?>[] cons = clazz.getDeclaredConstructors();
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
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "newInstance");
        }
        return null;
    }

    /**
     * 判断 Class 是否为原始类型(boolean、char、byte、short、int、long、float、double)
     * @param clazz
     * @return
     */
    public static Object getDefaultPrimiticeValue(Class clazz) {
        if (clazz != null && clazz.isPrimitive()) {
            return clazz == boolean.class ? false : 0;
        }
        return null;
    }

    /**
     * 判断是否集合类型
     * @param clazz
     * @return
     */
    public static boolean isCollection(Class clazz) {
        return (clazz != null && Collection.class.isAssignableFrom(clazz));
    }

    /**
     * 是否数组类型
     * @param clazz
     * @return
     */
    public static boolean isArray(Class clazz) {
        return (clazz != null && clazz.isArray());
    }

    // =

    /**
     * 获取父类泛型类型
     * @param object
     * @return
     */
    public static Class<?> getGenericSuperclass(Object object) {
        return getGenericSuperclass(object, 0);
    }

    /**
     * 获取父类泛型类型
     * @param object
     * @param pos 泛型参数位置
     * @return
     */
    public static Class<?> getGenericSuperclass(Object object, int pos) {
        if (object != null) {
            return getGenericSuperclass(object.getClass(), pos);
        }
        return null;
    }

    // =

    /**
     * 获取父类泛型类型
     * @param clazz
     * @return
     */
    public static Class<?> getGenericSuperclass(Class clazz) {
        return getGenericSuperclass(clazz, 0);
    }

    /**
     * 获取父类泛型类型
     * @param clazz
     * @param pos 泛型参数位置
     * @return
     */
    public static Class<?> getGenericSuperclass(Class clazz, int pos) {
        if (clazz != null && pos >= 0) {
            try {
                return (Class<?>) ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()[pos];
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "getGenericSuperclass");
            }
        }
        return null;
    }

    // =

    /**
     * 获取接口泛型类型
     * @param object
     * @param interfaceClazz 接口 Class
     * @return
     */
    public static Class<?> getGenericInterfaces(Object object, Class interfaceClazz) {
        return getGenericInterfaces(object, interfaceClazz, 0);
    }


    /**
     * 获取接口泛型类型
     * @param object
     * @param interfaceClazz 接口 Class
     * @param pos 泛型参数位置
     * @return
     */
    public static Class<?> getGenericInterfaces(Object object, Class interfaceClazz, int pos) {
        if (object != null) {
            return getGenericInterfaces(object.getClass(), interfaceClazz, pos);
        }
        return null;
    }

    // =

    /**
     * 获取接口泛型类型
     * @param clazz
     * @param interfaceClazz 接口 Class
     * @return
     */
    public static Class<?> getGenericInterfaces(Class clazz, Class interfaceClazz) {
        return getGenericInterfaces(clazz, interfaceClazz, 0);
    }

    /**
     * 获取接口泛型类型
     * @param clazz
     * @param interfaceClazz 接口 Class
     * @param pos 泛型参数位置
     * @return
     */
    public static Class<?> getGenericInterfaces(Class clazz, Class interfaceClazz, int pos) {
        if (clazz != null && interfaceClazz != null && pos >= 0) {
            try {
                // 获取接口类名
                String iName = interfaceClazz.getName();
                if (iName.equals("")) return null;
                // 获取接口泛型类型数组
                Type[] types = clazz.getGenericInterfaces();
                // 循环类型
                for (Type type : types) {
                    if (type instanceof ParameterizedType) {
                        ParameterizedType parameterizedType = (ParameterizedType) type;
                        // 判断是否属于该接口
                        String rawType = parameterizedType.getRawType().toString();
                        // 判断接口包名是否一致
                        if (rawType.startsWith("interface ")) {
                            if (rawType.equals("interface " + iName)) {
                                return (Class<?>) parameterizedType.getActualTypeArguments()[pos];
                            }
                        } else {
                            if (rawType.equals(iName)) {
                                return (Class<?>) parameterizedType.getActualTypeArguments()[pos];
                            }
                        }
                    }
                }
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "getGenericInterfaces");
            }
        }
        return null;
    }
}
