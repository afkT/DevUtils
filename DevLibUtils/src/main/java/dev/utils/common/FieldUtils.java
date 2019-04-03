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
 * detail: 域工具
 * Created by Ttt
 */
public final class FieldUtils {

    private FieldUtils() {
    }

    // 日志 TAG
    private static final String TAG = FieldUtils.class.getSimpleName();

    /**
     * 判断是否序列化
     * @param f
     * @return
     */
    public static boolean isSerializable(Field f) {
        Class<?>[] cls = f.getType().getInterfaces();
        for (Class<?> c : cls) {
            if (Serializable.class == c) {
                return true;
            }
        }
        return false;
    }

    /**
     * 设置域的值
     * @param f
     * @param obj
     * @return
     */
    public static Object set(Field f, Object obj, Object value) {
        try {
            f.setAccessible(true);
            f.set(obj, value);
            return f.get(obj);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "set");
        }
        return null;
    }

    /**
     * 获取域的值
     * @param f
     * @param obj
     * @return
     */
    public static Object get(Field f, Object obj) {
        try {
            f.setAccessible(true);
            return f.get(obj);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "get");
        }
        return null;
    }

    /**
     * 是否 long 类型
     * @param field
     * @return
     */
    public static boolean isLong(Field field) {
        return field.getType() == long.class || field.getType() == Long.class;
    }

    /**
     * 是否 Integer 类型
     * @param field
     * @return
     */
    public static boolean isInteger(Field field) {
        return field.getType() == int.class || field.getType() == Integer.class;
    }

    /**
     * 获取域的泛型类型，如果不带泛型返回null
     * @param f
     * @return
     */
    public static Class<?> getGenericType(Field f) {
        Type type = f.getGenericType();
        if (type instanceof ParameterizedType) {
            type = ((ParameterizedType) type).getActualTypeArguments()[0];
            if (type instanceof Class<?>) return (Class<?>) type;
        } else if (type instanceof Class<?>) return (Class<?>) type;
        return null;
    }

    /**
     * 获取数组的类型
     * @param f
     * @return
     */
    public static Class<?> getComponentType(Field f) {
        return f.getType().getComponentType();
    }

    /**
     * 获取全部Field，包括父类
     * @param clazz
     * @return
     */
    public static List<Field> getAllDeclaredFields(Class<?> clazz) {
        // find all field.
        LinkedList<Field> fieldList = new LinkedList<>();
        while (clazz != null && clazz != Object.class) {
            Field[] fs = clazz.getDeclaredFields();
            for (int i = 0; i < fs.length; i++) {
                Field f = fs[i];
                if (!isInvalid(f)) {
                    fieldList.addLast(f);
                }
            }
            clazz = clazz.getSuperclass();
        }
        return fieldList;
    }

    /**
     * 是静态常量或者内部结构属性
     * @param f
     * @return
     */
    public static boolean isInvalid(Field f) {
        return (Modifier.isStatic(f.getModifiers()) && Modifier.isFinal(f.getModifiers())) || f.isSynthetic();
    }
}
