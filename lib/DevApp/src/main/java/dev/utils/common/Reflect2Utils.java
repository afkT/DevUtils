package dev.utils.common;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import dev.utils.JCLogUtils;

/**
 * detail: 反射相关工具类
 * @author Ttt
 * <pre>
 *     有两个方法: getMethod, getDeclaredMethod
 *     <p></p>
 *     getDeclaredMethod() 获取的是类自身声明的所有方法, 包含 public、protected 和 private 方法
 *     getMethod() 获取的是类的所有共有方法, 这就包括自身的所有 public 方法, 和从基类继承的、从接口实现的所有 public 方法
 *     <p></p>
 *     getMethod 只能调用 public 声明的方法, 而 getDeclaredMethod 基本可以调用任何类型声明的方法
 *     反射多用 getDeclaredMethod 尽量少用 getMethod
 * </pre>
 */
public final class Reflect2Utils {

    private Reflect2Utils() {
    }

    // 日志 TAG
    private static final String TAG = Reflect2Utils.class.getSimpleName();

    // ===========
    // = 对象变量 =
    // ===========

    /**
     * 设置某个对象变量值 ( 可设置静态变量 )
     * @param object    对象
     * @param fieldName 属性名
     * @param value     字段值
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setProperty(
            final Object object,
            final String fieldName,
            final Object value
    ) {
        if (object == null || fieldName == null) return false;
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, value);
            return true;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "setProperty");
        }
        return false;
    }

    /**
     * 获取某个对象的变量 ( 可获取静态变量 )
     * @param object    对象
     * @param fieldName 属性名
     * @param <T>       泛型
     * @return 该变量对象
     */
    public static <T> T getProperty(
            final Object object,
            final String fieldName
    ) {
        if (object == null || fieldName == null) return null;
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return (T) field.get(object);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getProperty");
        }
        return null;
    }

    // ======================
    // = 获取某个类的静态变量 =
    // ======================

    /**
     * 获取某个类的静态变量 ( 只能获取静态变量 )
     * @param object    对象
     * @param fieldName 属性名
     * @param <T>       泛型
     * @return 该变量对象
     */
    public static <T> T getStaticProperty(
            final Object object,
            final String fieldName
    ) {
        if (object == null) return null;
        return getStaticProperty(object.getClass().getName(), fieldName);
    }

    /**
     * 获取某个类的静态变量 ( 只能获取静态变量 )
     * @param clazz     类
     * @param fieldName 属性名
     * @param <T>       泛型
     * @return 该变量对象
     */
    public static <T> T getStaticProperty(
            final Class clazz,
            final String fieldName
    ) {
        if (clazz == null) return null;
        return getStaticProperty(clazz.getName(), fieldName);
    }

    /**
     * 获取某个类的静态变量 ( 只能获取静态变量 )
     * @param className 类名
     * @param fieldName 属性名
     * @param <T>       泛型
     * @return 该变量对象
     */
    public static <T> T getStaticProperty(
            final String className,
            final String fieldName
    ) {
        if (className == null || fieldName == null) return null;
        try {
            Class clazz = Class.forName(className);
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return (T) field.get(clazz);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getStaticProperty");
        }
        return null;
    }

    // ==================
    // = 执行某个对象方法 =
    // ==================

    /**
     * 执行某个对象方法 ( 可执行静态方法 )
     * @param object     对象
     * @param methodName 方法名
     * @param <T>        泛型
     * @return 执行方法返回的结果
     */
    public static <T> T invokeMethod(
            final Object object,
            final String methodName
    ) {
        return invokeMethod(object, methodName, null, null);
    }

    /**
     * 执行某个对象方法 ( 可执行静态方法 )
     * @param object     对象
     * @param methodName 方法名
     * @param args       参数
     * @param <T>        泛型
     * @return 执行方法返回的结果
     */
    public static <T> T invokeMethod(
            final Object object,
            final String methodName,
            final Object[] args
    ) {
        return invokeMethod(object, methodName, args, getArgsClass(args));
    }

    /**
     * 执行某个对象方法 ( 可执行静态方法 )
     * @param object     对象
     * @param methodName 方法名
     * @param args       参数
     * @param argsClass  参数类型
     * @param <T>        泛型
     * @return 执行方法返回的结果
     */
    public static <T> T invokeMethod(
            final Object object,
            final String methodName,
            final Object[] args,
            final Class[] argsClass
    ) {
        if (object == null || methodName == null) return null;
        try {
            Class clazz = object.getClass();
            if (args != null && argsClass != null) { // 参数、参数类型不为 null, 并且数量相等
                if (args.length == argsClass.length && args.length != 0) {
                    Method method = clazz.getDeclaredMethod(methodName, argsClass);
                    method.setAccessible(true);
                    return (T) method.invoke(object, args);
                }
            } else {
                // 无参数、参数类型, 才执行
                if (args == null && argsClass == null) {
                    Method method = clazz.getDeclaredMethod(methodName);
                    method.setAccessible(true);
                    return (T) method.invoke(object);
                }
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "invokeMethod");
        }
        return null;
    }

    // ======================
    // = 执行某个类的静态方法 =
    // ======================

    /**
     * 执行某个类的静态方法 ( 只能执行静态方法 )
     * @param object     对象
     * @param methodName 方法名
     * @param <T>        泛型
     * @return 执行方法返回的结果
     */
    public static <T> T invokeStaticMethod(
            final Object object,
            final String methodName
    ) {
        if (object == null) return null;
        return invokeStaticMethod(object.getClass().getName(), methodName, null, null);
    }

    /**
     * 执行某个类的静态方法 ( 只能执行静态方法 )
     * @param object     对象
     * @param methodName 方法名
     * @param args       参数数组
     * @param <T>        泛型
     * @return 执行方法返回的结果
     */
    public static <T> T invokeStaticMethod(
            final Object object,
            final String methodName,
            final Object[] args
    ) {
        if (object == null) return null;
        return invokeStaticMethod(object.getClass().getName(), methodName, args, getArgsClass(args));
    }

    /**
     * 执行某个类的静态方法 ( 只能执行静态方法 )
     * @param object     对象
     * @param methodName 方法名
     * @param args       参数数组
     * @param argsClass  参数类型
     * @param <T>        泛型
     * @return 执行方法返回的结果
     */
    public static <T> T invokeStaticMethod(
            final Object object,
            final String methodName,
            final Object[] args,
            final Class[] argsClass
    ) {
        if (object == null) return null;
        return invokeStaticMethod(object.getClass().getName(), methodName, args, argsClass);
    }

    // =

    /**
     * 执行某个类的静态方法 ( 只能执行静态方法 )
     * @param clazz      类
     * @param methodName 方法名
     * @param <T>        泛型
     * @return 执行方法返回的结果
     */
    public static <T> T invokeStaticMethod(
            final Class clazz,
            final String methodName
    ) {
        if (clazz == null) return null;
        return invokeStaticMethod(clazz.getName(), methodName, null, null);
    }

    /**
     * 执行某个类的静态方法 ( 只能执行静态方法 )
     * @param clazz      类
     * @param methodName 方法名
     * @param args       参数数组
     * @param <T>        泛型
     * @return 执行方法返回的结果
     */
    public static <T> T invokeStaticMethod(
            final Class clazz,
            final String methodName,
            final Object[] args
    ) {
        if (clazz == null) return null;
        return invokeStaticMethod(clazz.getName(), methodName, args, getArgsClass(args));
    }

    /**
     * 执行某个类的静态方法 ( 只能执行静态方法 )
     * @param clazz      类
     * @param methodName 方法名
     * @param args       参数数组
     * @param argsClass  参数类型
     * @param <T>        泛型
     * @return 执行方法返回的结果
     */
    public static <T> T invokeStaticMethod(
            final Class clazz,
            final String methodName,
            final Object[] args,
            final Class[] argsClass
    ) {
        if (clazz == null) return null;
        return invokeStaticMethod(clazz.getName(), methodName, args, argsClass);
    }

    // =

    /**
     * 执行某个类的静态方法 ( 只能执行静态方法 )
     * @param className  类名
     * @param methodName 方法名
     * @param <T>        泛型
     * @return 执行方法返回的结果
     */
    public static <T> T invokeStaticMethod(
            final String className,
            final String methodName
    ) {
        if (className == null) return null;
        return invokeStaticMethod(className, methodName, null, null);
    }

    /**
     * 执行某个类的静态方法 ( 只能执行静态方法 )
     * @param className  类名
     * @param methodName 方法名
     * @param args       参数数组
     * @param <T>        泛型
     * @return 执行方法返回的结果
     */
    public static <T> T invokeStaticMethod(
            final String className,
            final String methodName,
            final Object[] args
    ) {
        if (className == null) return null;
        return invokeStaticMethod(className, methodName, args, getArgsClass(args));
    }

    /**
     * 执行某个类的静态方法 ( 只能执行静态方法 )
     * @param className  类名
     * @param methodName 方法名
     * @param args       参数数组
     * @param argsClass  参数类型
     * @param <T>        泛型
     * @return 执行方法返回的结果
     */
    public static <T> T invokeStaticMethod(
            final String className,
            final String methodName,
            final Object[] args,
            final Class[] argsClass
    ) {
        if (className == null || methodName == null) return null;
        try {
            Class clazz = Class.forName(className);
            if (args != null && argsClass != null) { // 参数、参数类型不为 null, 并且数量相等
                if (args.length == argsClass.length && args.length != 0) {
                    Method method = clazz.getDeclaredMethod(methodName, argsClass);
                    method.setAccessible(true);
                    return (T) method.invoke(clazz, args);
                }
            } else {
                // 无参数、参数类型, 才执行
                if (args == null && argsClass == null) {
                    Method method = clazz.getDeclaredMethod(methodName);
                    method.setAccessible(true);
                    return (T) method.invoke(clazz);
                }
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "invokeStaticMethod");
        }
        return null;
    }

    // ===========================
    // = 新建实例 ( 构造函数创建 ) =
    // ===========================

    /**
     * 新建实例 ( 构造函数创建 )
     * @param object 对象
     * @param <T>    泛型
     * @return 新建的实例
     */
    public static <T> T newInstance(final Object object) {
        if (object == null) return null;
        return newInstance(object.getClass().getName(), null, null);
    }

    /**
     * 新建实例 ( 构造函数创建 )
     * @param object 对象
     * @param args   参数
     * @param <T>    泛型
     * @return 新建的实例
     */
    public static <T> T newInstance(
            final Object object,
            final Object[] args
    ) {
        if (object == null) return null;
        return newInstance(object.getClass().getName(), args, getArgsClass(args));
    }

    /**
     * 新建实例 ( 构造函数创建 )
     * @param object    对象
     * @param args      参数
     * @param argsClass 参数类型
     * @param <T>       泛型
     * @return 新建的实例
     */
    public static <T> T newInstance(
            final Object object,
            final Object[] args,
            final Class[] argsClass
    ) {
        if (object == null) return null;
        return newInstance(object.getClass().getName(), args, argsClass);
    }

    // =

    /**
     * 新建实例 ( 构造函数创建 )
     * @param clazz 类
     * @param <T>   泛型
     * @return 新建的实例
     */
    public static <T> T newInstance(final Class clazz) {
        if (clazz == null) return null;
        return newInstance(clazz.getName(), null, null);
    }

    /**
     * 新建实例 ( 构造函数创建 )
     * @param clazz 类
     * @param args  参数
     * @param <T>   泛型
     * @return 新建的实例
     */
    public static <T> T newInstance(
            final Class clazz,
            final Object[] args
    ) {
        if (clazz == null) return null;
        return newInstance(clazz.getName(), args, getArgsClass(args));
    }

    /**
     * 新建实例 ( 构造函数创建 )
     * @param clazz     类
     * @param args      参数
     * @param argsClass 参数类型
     * @param <T>       泛型
     * @return 新建的实例
     */
    public static <T> T newInstance(
            final Class clazz,
            final Object[] args,
            final Class[] argsClass
    ) {
        if (clazz == null) return null;
        return newInstance(clazz.getName(), args, argsClass);
    }

    // =

    /**
     * 新建实例 ( 构造函数创建 )
     * @param className 类名
     * @param <T>       泛型
     * @return 新建的实例
     */
    public static <T> T newInstance(final String className) {
        if (className == null) return null;
        return newInstance(className, null, null);
    }

    /**
     * 新建实例 ( 构造函数创建 )
     * @param className 类名
     * @param args      参数
     * @param <T>       泛型
     * @return 新建的实例
     */
    public static <T> T newInstance(
            final String className,
            final Object[] args
    ) {
        if (className == null) return null;
        return newInstance(className, args, getArgsClass(args));
    }

    /**
     * 新建实例 ( 构造函数创建 )
     * @param className 类名
     * @param args      参数
     * @param argsClass 参数类型
     * @param <T>       泛型
     * @return 新建的实例
     */
    public static <T> T newInstance(
            final String className,
            final Object[] args,
            final Class[] argsClass
    ) {
        if (className == null) return null;
        try {
            Class newClass = Class.forName(className);
            if (args == null) {
                return (T) newClass.newInstance();
            } else {
                Constructor cons = newClass.getConstructor(argsClass);
                return (T) cons.newInstance(args);
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "newInstance");
        }
        return null;
    }

    // =

    /**
     * 是不是某个类的实例
     * @param object 实例
     * @param clazz  待判断类
     * @return 如果 obj 是此类的实例, 则返回 true
     */
    public static boolean isInstance(
            final Object object,
            final Class clazz
    ) {
        if (object == null || clazz == null) return false;
        try {
            return clazz.isInstance(object);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "isInstance");
        }
        return false;
    }

    /**
     * 获取参数类型
     * @param args 参数
     * @return 参数类型数组
     */
    public static Class[] getArgsClass(final Object... args) {
        if (args != null) {
            try {
                Class[] argsClass = new Class[args.length];
                for (int i = 0, len = args.length; i < len; i++) {
                    argsClass[i] = args[i].getClass();
                }
                return argsClass;
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "getArgsClass");
            }
        }
        return new Class[0];
    }

    // =

    /**
     * 获取某个对象的变量
     * <pre>
     *     例: 获取父类中的变量
     *     Object object = 对象;
     *     getObject(getDeclaredFieldParent(object, "父类中变量名"), object);
     * </pre>
     * @param object 对象
     * @param field  {@link Field}
     * @param <T>    泛型
     * @return 该变量对象
     */
    public static <T> T getProperty(
            final Object object,
            final Field field
    ) {
        if (object == null || field == null) return null;
        try {
            field.setAccessible(true);
            return (T) field.get(object);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getProperty");
        }
        return null;
    }

    /**
     * 获取父类中的变量对象
     * @param object    子类对象
     * @param fieldName 父类中的属性名
     * @param <T>       泛型
     * @return 父类中的变量对象
     */
    public static <T> T getPropertyByParent(
            final Object object,
            final String fieldName
    ) {
        return getPropertyByParent(object, fieldName, 1);
    }

    /**
     * 获取父类中的变量对象
     * @param object      子类对象
     * @param fieldName   父类中的属性名
     * @param fieldNumber 字段出现次数, 如果父类还有父类, 并且有相同变量名, 设置负数 一直会跟到最后的变量
     * @param <T>         泛型
     * @return 父类中的变量对象
     */
    public static <T> T getPropertyByParent(
            final Object object,
            final String fieldName,
            final int fieldNumber
    ) {
        if (object == null || fieldName == null) return null;
        try {
            Field field = getDeclaredFieldParent(object, fieldName, fieldNumber);
            return getProperty(object, field);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getPropertyByParent");
        }
        return null;
    }

    /**
     * 获取父类中的变量对象 ( 循环向上转型, 获取对象的 DeclaredField)
     * @param object    对象
     * @param fieldName 属性名
     * @return {@link Field}
     */
    public static Field getDeclaredFieldParent(
            final Object object,
            final String fieldName
    ) {
        return getDeclaredFieldParent(object, fieldName, 1);
    }

    /**
     * 获取父类中的变量对象 ( 循环向上转型, 获取对象的 DeclaredField)
     * @param object      子类对象
     * @param fieldName   父类中的属性名
     * @param fieldNumber 字段出现次数, 如果父类还有父类, 并且有相同变量名, 设置负数 一直会跟到最后的变量
     * @return {@link Field} 父类中的属性对象
     */
    public static Field getDeclaredFieldParent(
            final Object object,
            final String fieldName,
            final int fieldNumber
    ) {
        if (object == null || fieldName == null) return null;
        try {
            if (fieldNumber == 0) return null;
            // 获取当前出现次数
            int number = 0;
            // 限制值
            int limitNumber = (fieldNumber >= 0) ? fieldNumber : Integer.MAX_VALUE;
            // =
            Field    field = null;
            Class<?> clazz = object.getClass();
            for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
                try {
                    field = clazz.getDeclaredField(fieldName);
                    number++;
                } catch (Exception e) {
                    // 这里甚么都不要做, 并且这里的异常必须这样写, 不能抛出去
                    // 如果这里的异常打印或者往外抛, 则就不会执行 clazz = clazz.getSuperclass(), 最后就不会进入到父类中了
                }
                if (number >= limitNumber) {
                    return field;
                }
            }
            // 负数表示跟到最后
            if (fieldNumber < 0) {
                return field;
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getDeclaredFieldParent");
        }
        return null;
    }
}