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

/**
 * detail: 反射相关工具类
 * Created by Ttt
 * ==============
 * 有两个方法: getMethod, getDeclaredMethod
 * getMethod 只能调用 public 声明的方法，而 getDeclaredMethod 基本可以调用任何类型声明的方法
 * 反射多用 getDeclaredMethod，尽量少用getMethod
 */
public final class ReflectUtils {

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
     * @throws ReflectException 反射异常
     */
    public static ReflectUtils reflect(final Class<?> clazz) throws ReflectException {
        return new ReflectUtils(clazz);
    }

    /**
     * 设置要反射的类
     * @param object 类对象
     * @return {@link ReflectUtils}
     * @throws ReflectException 反射异常
     */
    public static ReflectUtils reflect(final Object object) throws ReflectException {
        return new ReflectUtils(object == null ? Object.class : object.getClass(), object);
    }

    // =

    /**
     * 获取 Class
     * @param className
     * @return
     */
    private static Class<?> forName(final String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new ReflectException(e);
        }
    }

    /**
     * 获取 Class
     * @param name
     * @param classLoader
     * @return
     */
    private static Class<?> forName(final String name, final ClassLoader classLoader) {
        try {
            return Class.forName(name, true, classLoader);
        } catch (ClassNotFoundException e) {
            throw new ReflectException(e);
        }
    }

    // ===============
    // = newInstance =
    // ===============

    /**
     * 实例化反射对象
     * @return {@link ReflectUtils}
     */
    public ReflectUtils newInstance() {
        return newInstance(new Object[0]);
    }

    /**
     * 实例化反射对象
     * @param args 实例化需要的参数
     * @return {@link ReflectUtils}
     */
    public ReflectUtils newInstance(final Object... args) {
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
        }
    }

    /**
     * 获取参数类型
     * @param args
     * @return
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
     * @param list
     */
    private void sortConstructors(final List<Constructor<?>> list) {
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
     * @param constructor
     * @param args
     * @return
     */
    private ReflectUtils newInstance(final Constructor<?> constructor, final Object... args) {
        try {
            return new ReflectUtils(constructor.getDeclaringClass(), accessible(constructor).newInstance(args));
        } catch (Exception e) {
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
     */
    public ReflectUtils field(final String name) {
        try {
            Field field = getField(name);
            return new ReflectUtils(field.getType(), field.get(object));
        } catch (IllegalAccessException e) {
            throw new ReflectException(e);
        }
    }

    /**
     * 设置反射的字段
     * @param name  字段名
     * @param value 字段值
     * @return {@link ReflectUtils}
     */
    public ReflectUtils field(final String name, final Object value) {
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
     * @param name
     * @return
     * @throws IllegalAccessException
     */
    private Field getField(final String name) throws IllegalAccessException {
        Field field = getAccessibleField(name);
        if ((field.getModifiers() & Modifier.FINAL) == Modifier.FINAL) {
            try {
                Field modifiersField = Field.class.getDeclaredField("modifiers");
                modifiersField.setAccessible(true);
                modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
            } catch (NoSuchFieldException ignore) {
            }
        }
        return field;
    }

    /**
     * 获取可访问字段, 返回 Field 对象
     * @param name
     * @return
     */
    private Field getAccessibleField(final String name) {
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
            throw new ReflectException(e);
        }
    }

    /**
     * 获取对象
     * @param object
     * @return
     */
    private Object unwrap(final Object object) {
        if (object instanceof ReflectUtils) {
            return ((ReflectUtils) object).get();
        }
        return object;
    }

    // =

    /**
     * 获取Object 对象
     * 例: 获取父类中的变量
     * Object obj = 对象;
     * getObject(getDeclaredFieldBase(obj, "父类中变量名"), obj);
     * @param field
     * @param object
     * @return
     */
    public static Object getObject(final Field field, final Object object) {
        try {
            field.setAccessible(true);
            return field.get(object);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 设置枚举值
     * @param clazz 类型
     * @param name
     * @param val
     */
    public ReflectUtils setEnumVal(final Class<?> clazz, final String name, final String val) {
        try {
            return field(name, Enum.valueOf((Class<Enum>) clazz, val));
        } catch (Exception e) {
        }
        return this;
    }

    /**
     * 通过反射获取全部字段
     * @param object
     * @param name
     * @return
     * @throws Exception
     */
    public static Object getDeclaredField(final Object object, final String name) throws Exception {
        Field field = object.getClass().getDeclaredField(name);
        field.setAccessible(true);
        return field.get(object);
    }

    /**
     * 循环向上转型, 获取对象的 DeclaredField
     * @param object
     * @param fieldName
     * @return
     */
    public static Field getDeclaredFieldBase(final Object object, final String fieldName) {
        return getDeclaredFieldBase(object, fieldName, false);
    }

    /**
     * 循环向上转型, 获取对象的 DeclaredField
     * @param object    : 子类对象
     * @param fieldName : 父类中的属性名
     * @param isSuper   是否一直跟到最后, 如果父类还有父类，并且有相同变量名, 则设置isSuper = true，一直会跟到最后的变量
     * @return 父类中的属性对象
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
        } catch (NoSuchMethodException e) {
            try {
                Method method = similarMethod(name, types);
                return method(method, object, args);
            } catch (NoSuchMethodException e1) {
                throw new ReflectException(e1);
            }
        }
    }

    /**
     * 设置反射的方法处理
     * @param method
     * @param object
     * @param args
     * @return
     */
    private ReflectUtils method(final Method method, final Object object, final Object... args) {
        try {
            accessible(method);
            if (method.getReturnType() == void.class) {
                method.invoke(object, args);
                return reflect(object);
            } else {
                return reflect(method.invoke(object, args));
            }
        } catch (Exception e) {
            throw new ReflectException(e);
        }
    }

    /**
     * 获取准确参数的方法
     * @param name
     * @param types
     * @return
     * @throws NoSuchMethodException
     */
    private Method exactMethod(final String name, final Class<?>[] types) throws NoSuchMethodException {
        Class<?> type = type();
        try {
            return type.getMethod(name, types);
        } catch (NoSuchMethodException e) {
            do {
                try {
                    return type.getDeclaredMethod(name, types);
                } catch (NoSuchMethodException ignore) {
                }
                type = type.getSuperclass();
            } while (type != null);
            throw new NoSuchMethodException();
        }
    }

    /**
     * 获取相似参数的方法
     * @param name
     * @param types
     * @return
     * @throws NoSuchMethodException
     */
    private Method similarMethod(final String name, final Class<?>[] types) throws NoSuchMethodException {
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

        throw new NoSuchMethodException("No similar method " + name + " with params " + Arrays.toString(types) + " could be found on type " + type() + ".");
    }

    /**
     * 进行方法排序
     * @param methods
     */
    private void sortMethods(final List<Method> methods) {
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

    private boolean isSimilarSignature(final Method possiblyMatchingMethod, final String desiredMethodName, final Class<?>[] desiredParamTypes) {
        return possiblyMatchingMethod.getName().equals(desiredMethodName) && match(possiblyMatchingMethod.getParameterTypes(), desiredParamTypes);
    }

    private boolean match(final Class<?>[] declaredTypes, final Class<?>[] actualTypes) {
        if (declaredTypes.length == actualTypes.length) {
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
     * @return 返回代理的对象
     */
    @SuppressWarnings("unchecked")
    public <P> P proxy(final Class<P> proxyType) {
        final boolean isMap = (object instanceof Map);
        final InvocationHandler handler = new InvocationHandler() {
            @Override
            @SuppressWarnings("null")
            public Object invoke(Object proxy, Method method, Object[] args) {
                String name = method.getName();
                try {
                    return reflect(object).method(name, args).get();
                } catch (ReflectException e) {
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
                    throw e;
                }
            }
        };
        return (P) Proxy.newProxyInstance(proxyType.getClassLoader(), new Class[]{proxyType}, handler);
    }

    /**
     * 获取实体类属性名 get/set
     * @param string
     * @return
     */
    private static String property(final String string) {
        int length = string.length();

        if (length == 0) {
            return "";
        } else if (length == 1) {
            return string.toLowerCase();
        } else {
            return string.substring(0, 1).toLowerCase() + string.substring(1);
        }
    }

    // =

    /**
     * 获取类型
     * @return
     */
    public Class<?> type() {
        return type;
    }

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
     * @param <T> 返回的范型
     * @return 反射想要获取的
     */
    @SuppressWarnings("unchecked")
    public <T> T get() {
        return (T) object;
    }

    /**
     * 获取 HashCode
     * @return
     */
    @Override
    public int hashCode() {
        return object.hashCode();
    }

    /**
     * 判断反射的两个对象是否一样
     * @param object
     * @return
     */
    @Override
    public boolean equals(final Object object) {
        return object instanceof ReflectUtils && this.object.equals(((ReflectUtils) object).get());
    }

    /**
     * 获取反射获取的对象 toString
     * @return
     */
    @Override
    public String toString() {
        return object.toString();
    }

    // =

    /**
     * 内部标记 null
     */
    private static class NULL {
    }

    /**
     * 定义反射异常类
     */
    public static class ReflectException extends RuntimeException {

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
