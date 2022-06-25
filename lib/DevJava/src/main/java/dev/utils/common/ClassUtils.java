package dev.utils.common;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Collection;
import java.util.Map;

import dev.utils.JCLogUtils;

/**
 * detail: 类 ( Class ) 工具类
 * @author Ttt
 */
public final class ClassUtils {

    private ClassUtils() {
    }

    // 日志 TAG
    private static final String TAG = ClassUtils.class.getSimpleName();

    /**
     * 根据类获取对象, 不再必须一个无参构造
     * @param clazz {@link Class}
     * @param <T>   泛型
     * @return 初始化后的对象
     */
    public static <T> T newInstance(final Class<T> clazz) {
        if (clazz == null) return null;
        try {
            Constructor<?>[] cons = clazz.getDeclaredConstructors();
            for (Constructor<?> c : cons) {
                Class<?>[] cls = c.getParameterTypes();
                if (cls.length == 0) {
                    c.setAccessible(true);
                    return (T) c.newInstance();
                } else {
                    Object[] objects = new Object[cls.length];
                    for (int i = 0; i < cls.length; i++) {
                        objects[i] = getDefaultPrimitiveValue(cls[i]);
                    }
                    c.setAccessible(true);
                    return (T) c.newInstance(objects);
                }
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "newInstance");
        }
        return null;
    }

    /**
     * 获取 Class 原始类型值
     * @param clazz {@link Class}
     * @return 原始类型值
     */
    public static Object getDefaultPrimitiveValue(final Class<?> clazz) {
        if (clazz != null && clazz.isPrimitive()) {
            return clazz == boolean.class ? false : 0;
        }
        return null;
    }

    // =

    /**
     * 获取 Object Class
     * @param object {@link Object}
     * @return Object Class
     */
    public static Class<?> getClass(final Object object) {
        return (object != null) ? object.getClass() : null;
    }

    /**
     * 获取 Type Class
     * @param type {@link Type}
     * @return Type Class
     */
    public static Class<?> getClass(final Type type) {
        if (type == null) return null;
        try {
            if (type.getClass() == Class.class) {
                return (Class<?>) type;
            }
            if (type instanceof ParameterizedType) {
                return getClass(((ParameterizedType) type).getRawType());
            }
            if (type instanceof TypeVariable) {
                Type boundType = ((TypeVariable<?>) type).getBounds()[0];
                return (Class<?>) boundType;
            }
            if (type instanceof WildcardType) {
                Type[] upperBounds = ((WildcardType) type).getUpperBounds();
                if (upperBounds.length == 1) {
                    return getClass(upperBounds[0]);
                }
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getClass");
        }
        return Object.class;
    }

    /**
     * 判断 Class 是否为原始类型
     * @param clazz {@link Class}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isPrimitive(final Class<?> clazz) {
        return (clazz != null && clazz.isPrimitive());
    }

    /**
     * 判断是否 Collection 类型
     * @param clazz {@link Class}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isCollection(final Class<?> clazz) {
        return (clazz != null && Collection.class.isAssignableFrom(clazz));
    }

    /**
     * 判断是否 Map 类型
     * @param clazz {@link Class}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isMap(final Class<?> clazz) {
        return (clazz != null && Map.class.isAssignableFrom(clazz));
    }

    /**
     * 判断是否 Array 类型
     * @param clazz {@link Class}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isArray(final Class<?> clazz) {
        return (clazz != null && clazz.isArray());
    }

    /**
     * 判断是否参数类型
     * @param type {@link Type}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isGenericParamType(final Type type) {
        if (type != null) {
            if (type instanceof ParameterizedType) {
                return true;
            }
            if (type instanceof Class) {
                try {
                    Type superType = ((Class<?>) type).getGenericSuperclass();
                    return superType != Object.class && isGenericParamType(superType);
                } catch (Exception e) {
                    JCLogUtils.eTag(TAG, e, "isGenericParamType");
                }
            }
        }
        return false;
    }

    /**
     * 获取参数类型
     * @param type {@link Type}
     * @return {@link Type}
     */
    public static Type getGenericParamType(final Type type) {
        if (type != null) {
            if (type instanceof ParameterizedType) {
                return type;
            }
            if (type instanceof Class) {
                try {
                    return getGenericParamType(((Class<?>) type).getGenericSuperclass());
                } catch (Exception e) {
                    JCLogUtils.eTag(TAG, e, "getGenericParamType");
                }
            }
        }
        return type;
    }

    // =

    /**
     * 获取父类泛型类型
     * @param object Object
     * @return 泛型类型
     */
    public static Type getGenericSuperclass(final Object object) {
        return getGenericSuperclass(object, 0);
    }

    /**
     * 获取父类泛型类型
     * @param object Object
     * @param pos    泛型参数索引
     * @return 泛型类型
     */
    public static Type getGenericSuperclass(
            final Object object,
            final int pos
    ) {
        return getGenericSuperclass(getClass(object), pos);
    }

    // =

    /**
     * 获取父类泛型类型
     * @param clazz {@link Class}
     * @return 泛型类型
     */
    public static Type getGenericSuperclass(final Class<?> clazz) {
        return getGenericSuperclass(clazz, 0);
    }

    /**
     * 获取父类泛型类型
     * @param clazz {@link Class}
     * @param pos   泛型参数索引
     * @return 泛型类型
     */
    public static Type getGenericSuperclass(
            final Class<?> clazz,
            final int pos
    ) {
        if (clazz != null && pos >= 0) {
            try {
                return ((ParameterizedType) clazz.getGenericSuperclass())
                        .getActualTypeArguments()[pos];
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "getGenericSuperclass");
            }
        }
        return null;
    }

    // =

    /**
     * 获取接口泛型类型
     * @param object         Object
     * @param interfaceClazz 接口 Class
     * @return 泛型类型
     */
    public static Type getGenericInterfaces(
            final Object object,
            final Class<?> interfaceClazz
    ) {
        return getGenericInterfaces(object, interfaceClazz, 0);
    }

    /**
     * 获取接口泛型类型
     * @param object         Object
     * @param interfaceClazz 接口 Class
     * @param pos            泛型参数索引
     * @return 泛型类型
     */
    public static Type getGenericInterfaces(
            final Object object,
            final Class<?> interfaceClazz,
            final int pos
    ) {
        return getGenericInterfaces(getClass(object), interfaceClazz, pos);
    }

    // =

    /**
     * 获取接口泛型类型
     * @param clazz          {@link Class}
     * @param interfaceClazz 接口 Class
     * @return 泛型类型
     */
    public static Type getGenericInterfaces(
            final Class<?> clazz,
            final Class<?> interfaceClazz
    ) {
        return getGenericInterfaces(clazz, interfaceClazz, 0);
    }

    /**
     * 获取接口泛型类型
     * @param clazz          {@link Class}
     * @param interfaceClazz 接口 Class
     * @param pos            泛型参数索引
     * @return 泛型类型
     */
    public static Type getGenericInterfaces(
            final Class<?> clazz,
            final Class<?> interfaceClazz,
            final int pos
    ) {
        if (clazz != null && interfaceClazz != null && pos >= 0) {
            try {
                // 获取接口类名
                String iName = interfaceClazz.getName();
                if ("".equals(iName)) return null;
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
                                return parameterizedType.getActualTypeArguments()[pos];
                            }
                        } else {
                            if (rawType.equals(iName)) {
                                return parameterizedType.getActualTypeArguments()[pos];
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