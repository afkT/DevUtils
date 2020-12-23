package dev.utils.common;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import dev.utils.JCLogUtils;

/**
 * detail: 变量字段工具类
 * @author Ttt
 */
public final class FieldUtils {

    private FieldUtils() {
    }

    // 日志 TAG
    private static final String TAG = FieldUtils.class.getSimpleName();

    // =

    /**
     * 获取变量对象
     * @param object {@link Object}
     * @param name   变量名
     * @return {@link Field}
     */
    public static Field getField(
            final Object object,
            final String name
    ) {
        return getField(ClassUtils.getClass(object), name);
    }

    /**
     * 获取变量对象
     * <pre>
     *     public 成员变量, 包括基类
     * </pre>
     * @param clazz {@link Class}
     * @param name  变量名
     * @return {@link Field}
     */
    public static Field getField(
            final Class clazz,
            final String name
    ) {
        if (clazz != null && name != null) {
            try {
                return clazz.getField(name);
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "getField");
            }
        }
        return null;
    }

    // =

    /**
     * 获取变量对象
     * @param object {@link Object}
     * @param name   变量名
     * @return {@link Field}
     */
    public static Field getDeclaredField(
            final Object object,
            final String name
    ) {
        return getDeclaredField(ClassUtils.getClass(object), name);
    }

    /**
     * 获取变量对象
     * <pre>
     *     所有成员变量, 不包括基类
     * </pre>
     * @param clazz {@link Class}
     * @param name  变量名
     * @return {@link Field}
     */
    public static Field getDeclaredField(
            final Class clazz,
            final String name
    ) {
        if (clazz != null && name != null) {
            try {
                return clazz.getDeclaredField(name);
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "getDeclaredField");
            }
        }
        return null;
    }

    // =

    /**
     * 获取变量对象数组
     * @param object {@link Object}
     * @return Field[]
     */
    public static Field[] getFields(final Object object) {
        return getFields(ClassUtils.getClass(object));
    }

    /**
     * 获取变量对象数组
     * <pre>
     *     public 成员变量, 包括基类
     * </pre>
     * @param clazz {@link Class}
     * @return Field[]
     */
    public static Field[] getFields(final Class clazz) {
        if (clazz != null) {
            try {
                return clazz.getFields();
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "getFields");
            }
        }
        return null;
    }

    // =

    /**
     * 获取变量对象数组
     * @param object {@link Object}
     * @return Field[]
     */
    public static Field[] getDeclaredFields(final Object object) {
        return getDeclaredFields(ClassUtils.getClass(object));
    }

    /**
     * 获取变量对象数组
     * <pre>
     *     所有成员变量, 不包括基类
     * </pre>
     * @param clazz {@link Class}
     * @return Field[]
     */
    public static Field[] getDeclaredFields(final Class clazz) {
        if (clazz != null) {
            try {
                return clazz.getDeclaredFields();
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "getDeclaredFields");
            }
        }
        return null;
    }

    // =

    /**
     * 设置字段的值
     * @param field  {@link Field}
     * @param object Object
     * @param value  Object-Value
     * @return 对应的 Object
     */
    public static Object set(
            final Field field,
            final Object object,
            final Object value
    ) {
        if (field == null || object == null) return null;
        try {
            field.setAccessible(true);
            field.set(object, value);
            return field.get(object);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "set");
        }
        return null;
    }

    /**
     * 获取字段的值
     * @param field  {@link Field}
     * @param object Object
     * @return 对应的 Object
     */
    public static Object get(
            final Field field,
            final Object object
    ) {
        if (field == null || object == null) return null;
        try {
            field.setAccessible(true);
            return field.get(object);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "get");
        }
        return null;
    }

    // =

    /**
     * 是否 long/Long 类型
     * @param field {@link Field}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isLong(final Field field) {
        return field != null && (field.getType() == long.class || field.getType() == Long.class);
    }

    /**
     * 是否 float/Float 类型
     * @param field {@link Field}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isFloat(final Field field) {
        return field != null && (field.getType() == float.class || field.getType() == Float.class);
    }

    /**
     * 是否 double/Double 类型
     * @param field {@link Field}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isDouble(final Field field) {
        return field != null && (field.getType() == double.class || field.getType() == Double.class);
    }

    /**
     * 是否 int/Integer 类型
     * @param field {@link Field}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isInteger(final Field field) {
        return field != null && (field.getType() == int.class || field.getType() == Integer.class);
    }

    /**
     * 是否 boolean/Boolean 类型
     * @param field {@link Field}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isBoolean(final Field field) {
        return field != null && (field.getType() == boolean.class || field.getType() == Boolean.class);
    }

    /**
     * 是否 char/Character 类型
     * @param field {@link Field}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isCharacter(final Field field) {
        return field != null && (field.getType() == char.class || field.getType() == Character.class);
    }

    /**
     * 是否 byte/Byte 类型
     * @param field {@link Field}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isByte(final Field field) {
        return field != null && (field.getType() == byte.class || field.getType() == Byte.class);
    }

    /**
     * 是否 short/Short 类型
     * @param field {@link Field}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isShort(final Field field) {
        return field != null && (field.getType() == short.class || field.getType() == Short.class);
    }

    /**
     * 是否 String 类型
     * @param field {@link Field}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isString(final Field field) {
        return field != null && (field.getType() == String.class);
    }

    // =

    /**
     * 判断是否序列化
     * @param field {@link Field}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isSerializable(final Field field) {
        if (field == null) return false;
        try {
            Class<?>[] clazzs = field.getType().getInterfaces();
            for (Class<?> clazz : clazzs) {
                if (Serializable.class == clazz) {
                    return true;
                }
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "isSerializable");
        }
        return false;
    }

    /**
     * 是否静态常量或者内部结构属性
     * @param field {@link Field}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isInvalid(final Field field) {
        return isStaticFinal(field) || isSynthetic(field);
    }

    /**
     * 是否静态变量
     * @param field {@link Field}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isStatic(final Field field) {
        return field != null && Modifier.isStatic(field.getModifiers());
    }

    /**
     * 是否常量
     * @param field {@link Field}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isFinal(final Field field) {
        return field != null && Modifier.isFinal(field.getModifiers());
    }

    /**
     * 是否静态变量
     * @param field {@link Field}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isStaticFinal(final Field field) {
        return field != null && Modifier.isStatic(field.getModifiers()) && Modifier.isFinal(field.getModifiers());
    }

    /**
     * 是否内部结构属性
     * @param field {@link Field}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isSynthetic(final Field field) {
        return field != null && field.isSynthetic();
    }

    // =

    /**
     * 获取字段的泛型类型, 如果不带泛型返回 null
     * @param field {@link Field}
     * @param <?>   未知类型
     * @return 泛型类型
     */
    public static Class<?> getGenericType(final Field field) {
        if (field == null) return null;
        try {
            Type type = field.getGenericType();
            if (type instanceof ParameterizedType) {
                type = ((ParameterizedType) type).getActualTypeArguments()[0];
                if (type instanceof Class<?>) {
                    return (Class<?>) type;
                }
            } else if (type instanceof Class<?>) {
                return (Class<?>) type;
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getGenericType");
        }
        return null;
    }

    /**
     * 获取数组的类型
     * @param field {@link Field}
     * @param <?>   未知类型
     * @return 数组类型
     */
    public static Class<?> getComponentType(final Field field) {
        if (field == null || field.getType() == null) return null;
        return field.getType().getComponentType();
    }

    /**
     * 获取全部 Field, 包括父类
     * @param clazz {@link Class}
     * @return {@link List<Field>}
     */
    public static List<Field> getAllDeclaredFields(final Class<?> clazz) {
        if (clazz == null) return null;
        try {
            Class<?> clazzTemp = clazz;
            // find all field.
            LinkedList<Field> fieldList = new LinkedList<>();
            while (clazzTemp != null && clazzTemp != Object.class) {
                Field[] fs = clazzTemp.getDeclaredFields();
                for (int i = 0; i < fs.length; i++) {
                    Field f = fs[i];
                    if (!isInvalid(f)) {
                        fieldList.addLast(f);
                    }
                }
                clazzTemp = clazzTemp.getSuperclass();
            }
            return fieldList;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getAllDeclaredFields");
        }
        return null;
    }
}