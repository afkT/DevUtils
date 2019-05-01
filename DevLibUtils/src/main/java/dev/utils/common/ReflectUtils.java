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
 * <pre>
 *      有两个方法: getMethod, getDeclaredMethod
 *      getMethod 只能调用 public 声明的方法，而 getDeclaredMethod 基本可以调用任何类型声明的方法
 *      反射多用 getDeclaredMethod，尽量少用 getMethod
 * </pre>
 */
public final class ReflectUtils {

    // 日志 TAG
    private static final String TAG = ReflectUtils.class.getSimpleName();

    private final Class<?> type;

    private final Object object;

    private ReflectUtils(final Class<?> type) {
        this(type, type);
    }

    private ReflectUtils(final Class<?> type, final Object object) {
        this.type = type;
        this.object = object;
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
    public static ReflectUtils reflect(final String className) throws ReflectException {
        return reflect(forName(className));
    }

    /**
     * 设置要反射的类
     * @param className   完整类名
     * @param classLoader 类加载器
     * @return {@link ReflectUtils}
     * @throws ReflectException 反射异常
     */
    public static ReflectUtils reflect(final String className, final ClassLoader classLoader) throws ReflectException {
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
     * @return 获取指定类
     * @throws ReflectException 反射异常
     */
    private static Class<?> forName(final String className) throws ReflectException {
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
     * @return 获取指定类
     * @throws ReflectException 反射异常
     */
    private static Class<?> forName(final String name, final ClassLoader classLoader) throws ReflectException {
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
    public ReflectUtils newInstance() throws ReflectException {
        return newInstance(new Object[0]);
    }

    /**
     * 实例化反射对象
     * @param args 实例化需要的参数
     * @return {@link ReflectUtils}
     * @throws ReflectException 反射异常
     */
    public ReflectUtils newInstance(final Object... args) throws ReflectException {
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
            public int compare(Constructor<?> o1, Constructor<?> o2) {
                Class<?>[] types1 = o1.getParameterTypes();
                Class<?>[] types2 = o2.getParameterTypes();
                int len = types1.length;
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
    private ReflectUtils newInstance(final Constructor<?> constructor, final Object... args) throws ReflectException {
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
    public ReflectUtils field(final String name) throws ReflectException {
        try {
            Field field = getField(name);
            return new ReflectUtils(field.getType(), field.get(object));
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
    public ReflectUtils field(final String name, final Object value) throws ReflectException {
        try {
            Field field = getField(name);
            field.set(object, unwrap(value));
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
    private Field getField(final String name) throws ReflectException {
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
    private Field getAccessibleField(final String name) throws ReflectException {
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
     * 获取 Object 对象
     * 例: 获取父类中的变量
     * Object obj = 对象;
     * getObject(getDeclaredFieldBase(obj, "父类中变量名"), obj);
     * @param field  Field
     * @param object 对象
     * @return Object
     * @throws ReflectException 反射异常
     */
    public static Object getObject(final Field field, final Object object) throws ReflectException {
        try {
            field.setAccessible(true);
            return field.get(object);
        } catch (Exception e) {
            throw new ReflectException(e);
        }
    }

    /**
     * 设置枚举值
     * @param clazz 类型
     * @param name  字段名
     * @param value 字段值
     * @return {@link ReflectUtils}
     * @throws ReflectException 反射异常
     */
    public ReflectUtils setEnumVal(final Class<?> clazz, final String name, final String value) throws ReflectException {
        try {
            return field(name, Enum.valueOf((Class<Enum>) clazz, value));
        } catch (Exception e) {
            throw new ReflectException(e);
        }
    }

    /**
     * 通过反射获取全部字段
     * @param object 对象
     * @param name   指定名
     * @return Object
     * @throws ReflectException 反射异常
     */
    public static Object getDeclaredField(final Object object, final String name) throws ReflectException {
        try {
            Field field = object.getClass().getDeclaredField(name);
            field.setAccessible(true);
            return field.get(object);
        } catch (Exception e) {
            throw new ReflectException(e);
        }
    }

    /**
     * 循环向上转型, 获取对象的 DeclaredField
     * @param object    对象
     * @param fieldName 属性名
     * @return {@link Field}
     */
    public static Field getDeclaredFieldBase(final Object object, final String fieldName) {
        return getDeclaredFieldBase(object, fieldName, false);
    }

    /**
     * 循环向上转型, 获取对象的 DeclaredField
     * @param object    子类对象
     * @param fieldName 父类中的属性名
     * @param isSuper   是否一直跟到最后, 如果父类还有父类，并且有相同变量名, 则设置isSuper = true，一直会跟到最后的变量
     * @return {@link Field} 父类中的属性对象
     */
    public static Field getDeclaredFieldBase(final Object object, final String fieldName, final boolean isSuper) {
        Field field = null;
        Class<?> clazz = object.getClass();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
                if (!isSuper) {
                    return field;
                }
            } catch (Exception e) {
                //这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
                //如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了
            }
        }
        return field;
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
    public ReflectUtils method(final String name) throws ReflectException {
        return method(name, new Object[0]);
    }

    /**
     * 设置反射的方法
     * @param name 方法名
     * @param args 方法需要的参数
     * @return {@link ReflectUtils}
     * @throws ReflectException 反射异常
     */
    public ReflectUtils method(final String name, final Object... args) throws ReflectException {
        Class<?>[] types = getArgsType(args);
        try {
            Method method = exactMethod(name, types);
            return method(method, object, args);
        } catch (Exception e) {
            try {
                Method method = similarMethod(name, types);
                return method(method, object, args);
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
    private ReflectUtils method(final Method method, final Object object, final Object... args) throws ReflectException {
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
    private Method exactMethod(final String name, final Class<?>[] types) throws ReflectException {
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
    private Method similarMethod(final String name, final Class<?>[] types) throws ReflectException {
        Class<?> type = type();
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
        throw new ReflectException("No similar method " + name + " with params " + Arrays.toString(types) + " could be found on type " + type() + ".");
    }

    /**
     * 进行方法排序
     * @param methods 方法集合
     */
    private void sortMethods(final List<Method> methods) {
        if (methods == null) return;
        Collections.sort(methods, new Comparator<Method>() {
            @Override
            public int compare(Method o1, Method o2) {
                Class<?>[] types1 = o1.getParameterTypes();
                Class<?>[] types2 = o2.getParameterTypes();
                int len = types1.length;
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
    private boolean isSimilarSignature(final Method possiblyMatchingMethod, final String desiredMethodName, final Class<?>[] desiredParamTypes) {
        return possiblyMatchingMethod.getName().equals(desiredMethodName) && match(possiblyMatchingMethod.getParameterTypes(), desiredParamTypes);
    }

    /**
     * 对比处理, 判断是否一样
     * @param declaredTypes 声明类型
     * @param actualTypes   实际类型
     * @return {@code true} yes, {@code false} no
     */
    private boolean match(final Class<?>[] declaredTypes, final Class<?>[] actualTypes) {
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
     * @param proxyType
     * @return 代理的对象
     */
    @SuppressWarnings("unchecked")
    public <P> P proxy(final Class<P> proxyType) {
        if (proxyType == null || object == null) return null;
        final boolean isMap = (object instanceof Map);
        final InvocationHandler handler = new InvocationHandler() {
            @Override
            @SuppressWarnings("null")
            public Object invoke(Object proxy, Method method, Object[] args) {
                String name = method.getName();
                try {
                    return reflect(object).method(name, args).get();
                } catch (Exception e) {
                    if (isMap) {
                        Map<String, Object> map = (Map<String, Object>) object;
                        int length = (args == null ? 0 : args.length);
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
    private static String property(final String str) {
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
        return type;
    }

    /**
     * 获取类型
     * @param type {@link Class}
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
    @SuppressWarnings("unchecked")
    public <T> T get() {
        return (T) object;
    }

    /**
     * 获取 HashCode
     * @return hashCode
     */
    @Override
    public int hashCode() {
        return this.object != null ? object.hashCode() : 0;
    }

    /**
     * 判断反射的两个对象是否一样
     * @param obj 对象
     * @return {@code true} yes, {@code false} no
     */
    @Override
    public boolean equals(final Object obj) {
        if (this.object == null && obj == null) {
            return true;
        } else {
            if (this.object != null && obj != null) {
                if (obj instanceof ReflectUtils) {
                    return this.object.equals(((ReflectUtils) obj).get());
                } else {
                    this.object.equals(obj);
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
        return this.object != null ? object.toString() : null;
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
    public static class ReflectException extends Exception {

        private static final long serialVersionUID = 858774075258496016L;

        public ReflectException(String message) {
            super(message);
        }

        public ReflectException(String message, Throwable cause) {
            super(message, cause);
        }

        public ReflectException(Throwable cause) {
            super(cause);
        }
    }
}
