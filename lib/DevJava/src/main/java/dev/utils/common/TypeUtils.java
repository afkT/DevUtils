package dev.utils.common;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import dev.utils.JCLogUtils;

/**
 * detail: 类型工具类
 * @author Ttt
 * <pre>
 *     Java 中与泛型相关的接口之 ParameterizedType
 *     @see <a href="https://www.jianshu.com/p/cfa74c980b25"/>
 *     Java 知识总结之 Type
 *     @see <a href="https://www.jianshu.com/p/0f3eda48d611"/>
 *     @see <a href="https://www.cnblogs.com/wytiger/p/11412072.html"/>
 * </pre>
 */
public final class TypeUtils {

    private TypeUtils() {
    }

    // 日志 TAG
    private static final String TAG = TypeUtils.class.getSimpleName();

    /**
     * 获取 Array Type
     * @param type Bean.class
     * @return Bean[] Type
     */
    public static Type getArrayType(final Type type) {
        if (type == null) return null;
        try {
            return new GenericArrayType() {
                @Override
                public Type getGenericComponentType() {
                    return type;
                }
            };
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getArrayType");
        }
        return null;
    }

    /**
     * 获取 List Type
     * @param type Bean.class
     * @return List<Bean> Type
     */
    public static Type getListType(final Type type) {
        if (type == null) return null;
        try {
            return new ParameterizedTypeImpl(new Type[]{type}, null, List.class);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getListType");
        }
        return null;
    }

    /**
     * 获取 Set Type
     * @param type Bean.class
     * @return Set<Bean> Type
     */
    public static Type getSetType(final Type type) {
        if (type == null) return null;
        try {
            return new ParameterizedTypeImpl(new Type[]{type}, null, Set.class);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getSetType");
        }
        return null;
    }

    /**
     * 获取 Map Type
     * @param keyType   Key.class
     * @param valueType Value.class
     * @return Map<Key, Value> Type
     */
    public static Type getMapType(
            final Type keyType,
            final Type valueType
    ) {
        if (keyType == null || valueType == null) return null;
        try {
            return new ParameterizedTypeImpl(new Type[]{keyType, valueType}, null, Map.class);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getMapType");
        }
        return null;
    }

    /**
     * 获取 Type
     * @param rawType       raw type
     * @param typeArguments type arguments
     * @return Type
     */
    public static Type getType(
            final Type rawType,
            final Type... typeArguments
    ) {
        try {
            return new ParameterizedTypeImpl(typeArguments, null, rawType);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getType");
        }
        return null;
    }

    // ========
    // = Type =
    // ========

    /**
     * detail: ParameterizedType 实现类
     * @author Ttt
     */
    public static class ParameterizedTypeImpl
            implements ParameterizedType {

        private final Type[] actualTypeArguments;
        private final Type   ownerType;
        private final Type   rawType;

        public ParameterizedTypeImpl(
                Type[] actualTypeArguments,
                Type ownerType,
                Type rawType
        ) {
            this.actualTypeArguments = actualTypeArguments;
            this.ownerType = ownerType;
            this.rawType = rawType;
        }

        public Type[] getActualTypeArguments() {
            return actualTypeArguments;
        }

        public Type getOwnerType() {
            return ownerType;
        }

        public Type getRawType() {
            return rawType;
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) return true;
            if (object == null || getClass() != object.getClass()) return false;

            ParameterizedTypeImpl that = (ParameterizedTypeImpl) object;
            if (!Arrays.equals(actualTypeArguments, that.actualTypeArguments)) return false;
            if (ownerType != null ? !ownerType.equals(that.ownerType) : that.ownerType != null) {
                return false;
            }
            return rawType != null ? rawType.equals(that.rawType) : that.rawType == null;
        }

        @Override
        public int hashCode() {
            int result = actualTypeArguments != null ? Arrays.hashCode(actualTypeArguments) : 0;
            result = 31 * result + (ownerType != null ? ownerType.hashCode() : 0);
            result = 31 * result + (rawType != null ? rawType.hashCode() : 0);
            return result;
        }
    }
}