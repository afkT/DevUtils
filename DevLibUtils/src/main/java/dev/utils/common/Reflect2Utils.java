package dev.utils.common;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import dev.utils.JCLogUtils;

/**
 * detail: 反射相关工具类
 * @author Ttt
 */
public final class Reflect2Utils {

    private Reflect2Utils() {
    }

    // 日志 TAG
    private static final String TAG = Reflect2Utils.class.getSimpleName();

    /**
     * 获取某个对象的公共属性
     * @param owner     对象
     * @param fieldName 属性名
     * @return 该属性对象
     */
    public static Object getProperty(final Object owner, final String fieldName) {
        if (owner == null || fieldName == null) return null;
        try {
            Class ownerClass = owner.getClass();
            Field field = ownerClass.getField(fieldName);
            Object property = field.get(owner);
            return property;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getProperty");
        }
        return null;
    }

    /**
     * 获取某类的静态公共属性
     * @param className 类名
     * @param fieldName 属性名
     * @return 该属性对象
     */
    public static Object getStaticProperty(final String className, final String fieldName) {
        if (className == null || fieldName == null) return null;
        try {
            Class ownerClass = Class.forName(className);
            Field field = ownerClass.getField(fieldName);
            Object property = field.get(ownerClass);
            return property;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getStaticProperty");
        }
        return null;
    }

    /**
     * 执行某对象方法
     * @param owner      对象
     * @param methodName 方法名
     * @return 方法返回值
     */
    public static Object invokeMethod(final Object owner, final String methodName) {
        return invokeMethod(owner, methodName, new Object[0]);
    }

    /**
     * 执行某对象方法
     * @param owner      对象
     * @param methodName 方法名
     * @param args       参数
     * @return 方法返回值
     */
    public static Object invokeMethod(final Object owner, final String methodName, final Object[] args) {
        if (owner == null || methodName == null) return null;
        try {
            Class ownerClass = owner.getClass();
            Class[] argsClass = new Class[args.length];
            for (int i = 0, j = args.length; i < j; i++) {
                argsClass[i] = args[i].getClass();
            }
            Method method = ownerClass.getMethod(methodName, argsClass);
            return method.invoke(owner, args);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "invokeMethod");
        }
        return null;
    }

    /**
     * 执行某类的静态方法
     * @param className  类名
     * @param methodName 方法名
     * @return 执行方法返回的结果
     */
    public static Object invokeStaticMethod(final String className, final String methodName) {
        return invokeStaticMethod(className, methodName, new Object[0]);
    }

    /**
     * 执行某类的静态方法
     * @param className  类名
     * @param methodName 方法名
     * @param args       参数数组
     * @return 执行方法返回的结果
     */
    public static Object invokeStaticMethod(final String className, final String methodName, final Object[] args) {
        if (className == null || methodName == null) return null;
        try {
            Class ownerClass = Class.forName(className);
            Class[] argsClass = new Class[args.length];
            for (int i = 0, j = args.length; i < j; i++) {
                argsClass[i] = args[i].getClass();
            }
            // getDeclaredMethod() 获取的是类自身声明的所有方法，包含public、protected和private方法。
            // getMethod() 获取的是类的所有共有方法，这就包括自身的所有public方法，和从基类继承的、从接口实现的所有public方法。
            Method method = ownerClass.getDeclaredMethod(methodName, argsClass);
            if (!method.isAccessible()) method.setAccessible(true);
            return method.invoke(null, args);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "invokeStaticMethod");
        }
        return null;
    }


    /**
     * 新建实例
     * @param className 类名
     * @param args      构造函数的参数 如果无构造参数，args 填写为 null
     * @param argsType  参数类型
     * @return 新建的实例
     */
    public static Object newInstance(final String className, final Object[] args, final Class[] argsType) {
        if (className == null) return null;
        try {
            Class newoneClass = Class.forName(className);
            if (args == null) {
                return newoneClass.newInstance();
            } else {
//                Class[] argsClass = new Class[args.length];
//                for (int i = 0, j = args.length; i < j; i++) {
//                    argsClass[i] = args[i].getClass();
//                }
                Constructor cons = newoneClass.getConstructor(argsType);
                return cons.newInstance(args);
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "newInstance");
        }
        return null;
    }

    /**
     * 是不是某个类的实例
     * @param object 实例
     * @param clazz  待判断类
     * @return 如果 obj 是此类的实例，则返回 true
     */
    public static boolean isInstance(final Object object, final Class clazz) {
        if (object == null || clazz == null) return false;
        try {
            return clazz.isInstance(object);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "isInstance");
        }
        return false;
    }

    /**
     * 获取数组中的某个元素
     * @param array 数组
     * @param index 索引
     * @return 指定数组对象中索引组件的值
     */
    public static Object getByArray(final Object array, final int index) {
        if (array == null || index < 0) return null;
        try {
            return Array.get(array, index);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getByArray");
        }
        return null;
    }

    // =

    /**
     * 通过反射获取全部字段
     * 如: (ListView) getDeclaredField(对象, "私有属性")
     * @param object 对象
     * @param name   属性名
     * @return 字段所属对象
     */
    public static Object getDeclaredField(final Object object, final String name) {
        if (object == null || name == null) return null;
        try {
            Field field = object.getClass().getDeclaredField(name);
            field.setAccessible(true);
            return field.get(object);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getDeclaredField");
        }
        return null;
    }

    /**
     * 获取父类中的变量对象
     * @param object    子类对象
     * @param fieldName 父类中的属性名
     * @return 父类中的变量对象
     */
    public static Object getDeclaredFieldParentObj(final Object object, final String fieldName) {
        if (object == null || fieldName == null) return null;
        try {
            Field field = getDeclaredFieldParent(object, fieldName);
            field.setAccessible(true);
            return field.get(object);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getDeclaredFieldParentObj");
        }
        return null;
    }

    /**
     * 循环向上转型, 获取对象的 DeclaredField
     * @param object    子类对象
     * @param fieldName 父类中的属性名
     * @return 父类中的变量对象
     */
    public static Field getDeclaredFieldParent(final Object object, final String fieldName) {
        if (object == null || fieldName == null) return null;
        try {
            Field field;
            Class<?> clazz = object.getClass();
            for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
                try {
                    field = clazz.getDeclaredField(fieldName);
                    return field;
                } catch (Exception e) {
                    // 这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
                    // 如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了
                }
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getDeclaredFieldParent");
        }
        return null;
    }

    /**
     * 设置反射的方法
     * @param object 对象
     * @param name   方法名
     * @param args   方法需要的参数
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setFieldMethod(final Object object, final String name, final Object... args) {
        if (object == null || name == null) return false;
        try {
            Method method = object.getClass().getDeclaredMethod(name);
            method.setAccessible(true);
            // 如果不为null, 则不放参数
            if (args != null && args.length != 0) {
                method.invoke(object, args);
            } else {
                method.invoke(object);
            }
            return true;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "setFieldMethod");
        }
        return false;
    }

    /**
     * 设置反射的字段
     * @param object 对象
     * @param name   字段名
     * @param value  字段值
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setFieldValue(final Object object, final String name, final Object value) {
        if (object == null || name == null) return false;
        try {
            Field field = object.getClass().getDeclaredField(name);
            field.setAccessible(true);
            field.set(object, value);
            return true;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "setFieldValue");
        }
        return false;
    }
}
