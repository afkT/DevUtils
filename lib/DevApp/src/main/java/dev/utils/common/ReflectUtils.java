package dev.utils.common;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import dev.utils.JCLogUtils;

/**
 * detail: 反射相关工具类
 * @author Ttt
 */
public final class ReflectUtils {

    // 日志 TAG
    private static final String TAG = ReflectUtils.class.getSimpleName();

    private final Class<?> mType;

    private final Object mObject;

    private ReflectUtils(final Class<?> type) {
        this(type, type);
    }

    private ReflectUtils(
            final Class<?> type,
            final Object object
    ) {
        this.mType = type;
        this.mObject = object;
    }

    // ===========
    // = reflect =
    // ===========

    /**
     * 设置要反射的类
     * @param className 完整类名
     * @return {@link ReflectUtils}
     * @throws ReflectException 反射异常
     */
    public static ReflectUtils reflect(final String className)
            throws ReflectException {
        return reflect(forName(className));
    }

    /**
     * 设置要反射的类
     * @param className   完整类名
     * @param classLoader 类加载器
     * @return {@link ReflectUtils}
     * @throws ReflectException 反射异常
     */
    public static ReflectUtils reflect(
            final String className,
            final ClassLoader classLoader
    )
            throws ReflectException {
        return reflect(forName(className, classLoader));
    }

    /**
     * 设置要反射的类
     * @param clazz 类的类型
     * @return {@link ReflectUtils}
     */
    public static ReflectUtils reflect(final Class<?> clazz) {
        return new ReflectUtils(clazz);
    }

    /**
     * 设置要反射的类
     * @param object 类对象
     * @return {@link ReflectUtils}
     */
    public static ReflectUtils reflect(final Object object) {
        return new ReflectUtils(object == null ? Object.class : object.getClass(), object);
    }

    // =

    /**
     * 获取 Class
     * @param className 类名
     * @param <?>       未知类型
     * @return 指定类
     * @throws ReflectException 反射异常
     */
    private static Class<?> forName(final String className)
            throws ReflectException {
        try {
            return Class.forName(className);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "forName");
            throw new ReflectException(e);
        }
    }

    /**
     * 获取 Class
     * @param name        类名
     * @param classLoader 类加载器
     * @param <?>         未知类型
     * @return 指定类
     * @throws ReflectException 反射异常
     */
    private static Class<?> forName(
            final String name,
            final ClassLoader classLoader
    )
            throws ReflectException {
        try {
            return Class.forName(name, true, classLoader);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "forName");
            throw new ReflectException(e);
        }
    }

    // ===============
    // = newInstance =
    // ===============

    /**
     * 实例化反射对象
     * @return {@link ReflectUtils}
     * @throws ReflectException 反射异常
     */
    public ReflectUtils newInstance()
            throws ReflectException {
        return newInstance(new Object[0]);
    }

    /**
     * 实例化反射对象
     * @param args 实例化需要的参数
     * @return {@link ReflectUtils}
     * @throws ReflectException 反射异常
     */
    public ReflectUtils newInstance(final Object... args)
            throws ReflectException {
        Class<?>[] types = getArgsType(args);
        try {
            Constructor<?> constructor = type().getDeclaredConstructor(types);
            return newInstance(constructor, args);
        } catch (NoSuchMethodException e) {
            List<Constructor<?>> list = new ArrayList<>();
            for (Constructor<?> constructor : type().getDeclaredConstructors()) {
                if (match(constructor.getParameterTypes(), types)) {
                    list.add(constructor);
                }
            }
            if (list.isEmpty()) {
                throw new ReflectException(e);
            } else {
                sortConstructors(list);
                return newInstance(list.get(0), args);
            }
        } catch (Exception e) {
            throw new ReflectException(e);
        }
    }

    /**
     * 获取参数类型
     * @param args 参数数组
     * @param <?>  未知类型
     * @return 参数类型数组
     */
    private Class<?>[] getArgsType(final Object... args) {
        if (args == null) return new Class[0];
        Class<?>[] result = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            Object value = args[i];
            result[i] = value == null ? NULL.class : value.getClass();
        }
        return result;
    }

    /**
     * 进行排序
     * @param list 类构造函数信息集合
     */
    private void sortConstructors(final List<Constructor<?>> list) {
        if (list == null) return;
        Collections.sort(list, new Comparator<Constructor<?>>() {
            @Override
            public int compare(
                    Constructor<?> o1,
                    Constructor<?> o2
            ) {
                Class<?>[] types1 = o1.getParameterTypes();
                Class<?>[] types2 = o2.getParameterTypes();
                int        len    = types1.length;
                for (int i = 0; i < len; i++) {
                    if (!types1[i].equals(types2[i])) {
                        if (wrapper(types1[i]).isAssignableFrom(wrapper(types2[i]))) {
                            return 1;
                        } else {
                            return -1;
                        }
                    }
                }
                return 0;
            }
        });
    }

    /**
     * 获取实例对象
     * @param constructor 类构造函数信息
     * @param args        构造参数数组
     * @return {@link ReflectUtils}
     * @throws ReflectException 反射异常
     */
    private ReflectUtils newInstance(
            final Constructor<?> constructor,
            final Object... args
    )
            throws ReflectException {
        try {
            return new ReflectUtils(constructor.getDeclaringClass(), accessible(constructor).newInstance(args));
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "newInstance");
            throw new ReflectException(e);
        }
    }

    // =========
    // = field =
    // =========

    /**
     * 设置反射的字段
     * @param name 字段名
     * @return {@link ReflectUtils}
     * @throws ReflectException 反射异常
     */
    public ReflectUtils field(final String name)
            throws ReflectException {
        try {
            Field field = getField(name);
            return new ReflectUtils(field.getType(), field.get(mObject));
        } catch (Exception e) {
            throw new ReflectException(e);
        }
    }

    /**
     * 设置反射的字段
     * @param name  字段名
     * @param value 字段值
     * @return {@link ReflectUtils}
     * @throws ReflectException 反射异常
     */
    public ReflectUtils field(
            final String name,
            final Object value
    )
            throws ReflectException {
        try {
            Field field = getField(name);
            field.set(mObject, unwrap(value));
            return this;
        } catch (Exception e) {
            throw new ReflectException(e);
        }
    }

    /**
     * 获取 Field 对象
     * @param name 字段名
     * @return {@link Field}
     * @throws ReflectException 反射异常
     */
    private Field getField(final String name)
            throws ReflectException {
        Field field = getAccessibleField(name);
        if ((field.getModifiers() & Modifier.FINAL) == Modifier.FINAL) {
            try {
                Field modifiersField = Field.class.getDeclaredField("modifiers");
                modifiersField.setAccessible(true);
                modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
            } catch (NoSuchFieldException ignore) {
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "getField");
                throw new ReflectException(e);
            }
        }
        return field;
    }

    /**
     * 获取可访问字段, 返回 Field 对象
     * @param name 字段名
     * @return {@link Field}
     * @throws ReflectException 反射异常
     */
    private Field getAccessibleField(final String name)
            throws ReflectException {
        Class<?> type = type();
        try {
            return accessible(type.getField(name));
        } catch (NoSuchFieldException e) {
            do {
                try {
                    return accessible(type.getDeclaredField(name));
                } catch (NoSuchFieldException ignore) {
                }
                type = type.getSuperclass();
            } while (type != null);
            JCLogUtils.eTag(TAG, e, "getAccessibleField");
            throw new ReflectException(e);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getAccessibleField");
            throw new ReflectException(e);
        }
    }

    /**
     * 获取对象
     * @param object 对象
     * @return 需要反射的对象
     */
    private Object unwrap(final Object object) {
        if (object instanceof ReflectUtils) {
            return ((ReflectUtils) object).get();
        }
        return object;
    }

    // =

    /**
     * 设置枚举值
     * @param clazz 类型
     * @param name  字段名
     * @param value 字段值
     * @return {@link ReflectUtils}
     * @throws ReflectException 反射异常
     */
    public ReflectUtils setEnumVal(
            final Class<?> clazz,
            final String name,
            final String value
    )
            throws ReflectException {
        try {
            return field(name, Enum.valueOf((Class<Enum>) clazz, value));
        } catch (Exception e) {
            throw new ReflectException(e);
        }
    }

    // ==========
    // = method =
    // ==========

    /**
     * 设置反射的方法
     * @param name 方法名
     * @return {@link ReflectUtils}
     * @throws ReflectException 反射异常
     */
    public ReflectUtils method(final String name)
            throws ReflectException {
        return method(name, new Object[0]);
    }

    /**
     * 设置反射的方法
     * @param name 方法名
     * @param args 方法需要的参数
     * @return {@link ReflectUtils}
     * @throws ReflectException 反射异常
     */
    public ReflectUtils method(
            final String name,
            final Object... args
    )
            throws ReflectException {
        Class<?>[] types = getArgsType(args);
        try {
            Method method = exactMethod(name, types);
            return method(method, mObject, args);
        } catch (Exception e) {
            try {
                Method method = similarMethod(name, types);
                return method(method, mObject, args);
            } catch (Exception e1) {
                throw new ReflectException(e1);
            }
        }
    }

    /**
     * 设置反射的方法处理
     * @param method 方法
     * @param object 对象
     * @param args   参数
     * @return {@link ReflectUtils}
     * @throws ReflectException 反射异常
     */
    private ReflectUtils method(
            final Method method,
            final Object object,
            final Object... args
    )
            throws ReflectException {
        try {
            accessible(method);
            if (method.getReturnType() == void.class) {
                method.invoke(object, args);
                return reflect(object);
            } else {
                return reflect(method.invoke(object, args));
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "method");
            throw new ReflectException(e);
        }
    }

    /**
     * 获取准确参数的方法
     * @param name  方法
     * @param types 参数类型
     * @return {@link Method}
     * @throws ReflectException 反射异常
     */
    private Method exactMethod(
            final String name,
            final Class<?>[] types
    )
            throws ReflectException {
        Class<?> type = type();
        try {
            return type.getMethod(name, types);
        } catch (Exception e) {
            do {
                try {
                    return type.getDeclaredMethod(name, types);
                } catch (Exception ignore) {
                }
                type = type.getSuperclass();
            } while (type != null);
            JCLogUtils.eTag(TAG, e, "exactMethod");
            throw new ReflectException(e);
        }
    }

    /**
     * 获取相似参数的方法
     * @param name  方法
     * @param types 参数类型
     * @return {@link Method}
     * @throws ReflectException 反射异常
     */
    private Method similarMethod(
            final String name,
            final Class<?>[] types
    )
            throws ReflectException {
        Class<?>     type    = type();
        List<Method> methods = new ArrayList<>();
        for (Method method : type.getMethods()) {
            if (isSimilarSignature(method, name, types)) {
                methods.add(method);
            }
        }
        if (!methods.isEmpty()) {
            sortMethods(methods);
            return methods.get(0);
        }
        do {
            for (Method method : type.getDeclaredMethods()) {
                if (isSimilarSignature(method, name, types)) {
                    methods.add(method);
                }
            }
            if (!methods.isEmpty()) {
                sortMethods(methods);
                return methods.get(0);
            }
            type = type.getSuperclass();
        } while (type != null);
        throw new ReflectException(String.format("No similar method %s with params %s could be found on type %s", name, Arrays.toString(types), type()));
    }

    /**
     * 进行方法排序
     * @param methods 方法集合
     */
    private void sortMethods(final List<Method> methods) {
        if (methods == null) return;
        Collections.sort(methods, new Comparator<Method>() {
            @Override
            public int compare(
                    Method o1,
                    Method o2
            ) {
                Class<?>[] types1 = o1.getParameterTypes();
                Class<?>[] types2 = o2.getParameterTypes();
                int        len    = types1.length;
                for (int i = 0; i < len; i++) {
                    if (!types1[i].equals(types2[i])) {
                        if (wrapper(types1[i]).isAssignableFrom(wrapper(types2[i]))) {
                            return 1;
                        } else {
                            return -1;
                        }
                    }
                }
                return 0;
            }
        });
    }

    /**
     * 判断是否相似方法
     * @param possiblyMatchingMethod 可能的匹配方法
     * @param desiredMethodName      期望方法名
     * @param desiredParamTypes      所需参数类型
     * @return {@code true} yes, {@code false} no
     */
    private boolean isSimilarSignature(
            final Method possiblyMatchingMethod,
            final String desiredMethodName,
            final Class<?>[] desiredParamTypes
    ) {
        return possiblyMatchingMethod.getName().equals(desiredMethodName) && match(possiblyMatchingMethod.getParameterTypes(), desiredParamTypes);
    }

    /**
     * 对比处理, 判断是否一样
     * @param declaredTypes 声明类型
     * @param actualTypes   实际类型
     * @return {@code true} yes, {@code false} no
     */
    private boolean match(
            final Class<?>[] declaredTypes,
            final Class<?>[] actualTypes
    ) {
        if (declaredTypes != null && actualTypes != null && declaredTypes.length == actualTypes.length) {
            for (int i = 0; i < actualTypes.length; i++) {
                if (actualTypes[i] == NULL.class || wrapper(declaredTypes[i]).isAssignableFrom(wrapper(actualTypes[i]))) {
                    continue;
                }
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * 设置对象可访问处理
     * @param accessible 对象
     * @param <T>        泛型
     * @return 传入的对象
     */
    private <T extends AccessibleObject> T accessible(final T accessible) {
        if (accessible == null) return null;
        if (accessible instanceof Member) {
            Member member = (Member) accessible;
            if (Modifier.isPublic(member.getModifiers()) && Modifier.isPublic(member.getDeclaringClass().getModifiers())) {
                return accessible;
            }
        }
        if (!accessible.isAccessible()) accessible.setAccessible(true);
        return accessible;
    }

    // ========
    // = 代理 =
    // ========

    /**
     * 根据类, 代理创建并返回对象
     * @param proxyType 代理类
     * @param <P>       泛型
     * @return 代理的对象
     */
    public <P> P proxy(final Class<P> proxyType) {
        if (proxyType == null || mObject == null) return null;
        final boolean isMap = (mObject instanceof Map);
        final InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(
                    Object proxy,
                    Method method,
                    Object[] args
            ) {
                String name = method.getName();
                try {
                    return reflect(mObject).method(name, args).get();
                } catch (Exception e) {
                    if (isMap) {
                        Map<String, Object> map    = (Map<String, Object>) mObject;
                        int                 length = (args == null ? 0 : args.length);
                        if (length == 0 && name.startsWith("get")) {
                            return map.get(property(name.substring(3)));
                        } else if (length == 0 && name.startsWith("is")) {
                            return map.get(property(name.substring(2)));
                        } else if (length == 1 && name.startsWith("set")) {
                            map.put(property(name.substring(3)), args[0]);
                            return null;
                        }
                    }
                    JCLogUtils.eTag(TAG, e, "proxy");
                }
                return null;
            }
        };
        return (P) Proxy.newProxyInstance(proxyType.getClassLoader(), new Class[]{proxyType}, handler);
    }

    /**
     * 获取实体类属性名 get/set
     * @param str 属性名
     * @return 属性名字
     */
    private String property(final String str) {
        int length = str.length();
        if (length == 0) {
            return "";
        } else if (length == 1) {
            return str.toLowerCase();
        } else {
            return str.substring(0, 1).toLowerCase() + str.substring(1);
        }
    }

    // =

    /**
     * 获取类型
     * @return {@link Class}
     */
    public Class<?> type() {
        return mType;
    }

    /**
     * 获取类型
     * @param type {@link Class}
     * @param <?>  未知类型
     * @return {@link Class} 类所属类型
     */
    private Class<?> wrapper(final Class<?> type) {
        if (type == null) {
            return null;
        } else if (type.isPrimitive()) {
            if (boolean.class == type) {
                return Boolean.class;
            } else if (int.class == type) {
                return Integer.class;
            } else if (long.class == type) {
                return Long.class;
            } else if (short.class == type) {
                return Short.class;
            } else if (byte.class == type) {
                return Byte.class;
            } else if (double.class == type) {
                return Double.class;
            } else if (float.class == type) {
                return Float.class;
            } else if (char.class == type) {
                return Character.class;
            } else if (void.class == type) {
                return Void.class;
            }
        }
        return type;
    }

    /**
     * 获取反射想要获取的
     * @param <T> 泛型
     * @return 反射想要获取的
     */
    public <T> T get() {
        return (T) mObject;
    }

    /**
     * 获取 HashCode
     * @return hashCode
     */
    @Override
    public int hashCode() {
        return this.mObject != null ? mObject.hashCode() : 0;
    }

    /**
     * 判断反射的两个对象是否一样
     * @param object 对象
     * @return {@code true} yes, {@code false} no
     */
    @Override
    public boolean equals(final Object object) {
        if (this.mObject == null && object == null) {
            return true;
        } else {
            if (this.mObject != null && object != null) {
                if (object instanceof ReflectUtils) {
                    return this.mObject.equals(((ReflectUtils) object).get());
                } else {
                    return this.mObject.equals(object);
                }
            }
            return false;
        }
    }

    /**
     * 获取反射获取的对象
     * @return {@link Object#toString()}
     */
    @Override
    public String toString() {
        return this.mObject != null ? mObject.toString() : null;
    }

    // =

    /**
     * detail: 内部标记 null
     * @author Ttt
     */
    private static class NULL {
    }

    /**
     * detail: 定义 ReflectUtils 工具异常类
     * @author Ttt
     */
    public static class ReflectException
            extends Exception {

        private static final long serialVersionUID = 858774075258496016L;

        public ReflectException(String message) {
            super(message);
        }

        public ReflectException(
                String message,
                Throwable cause
        ) {
            super(message, cause);
        }

        public ReflectException(Throwable cause) {
            super(cause);
        }
    }
}