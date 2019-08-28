package dev.callback;

import java.util.UUID;

import dev.utils.common.ClassUtils;

/**
 * detail: 抽象回调 - 基类
 * @author Ttt
 */
public abstract class AbstractCallBack<T> {

    // uuid 一定程度上唯一
    private final int uuid = UUID.randomUUID().hashCode();
    // 标记 tag
    private String tag;
    // Value
    private T value;
    // Object
    private Object object;
    // 泛型对象 Class
    private Class<T> clas;

    public AbstractCallBack() {
    }

    public AbstractCallBack(T value) {
        this.value = value;
    }

    public AbstractCallBack(T value, Object object) {
        this.value = value;
        this.object = object;
    }

    public AbstractCallBack(T value, Object object, String tag) {
        this.value = value;
        this.object = object;
        this.tag = tag;
    }

    // ================
    // = 对外公开方法 =
    // ================

    /**
     * 获取泛型对象 Class Type
     * @return T Class Type
     */
    public final Class<T> getTClass() {
        if (clas == null) {
            try {
                clas = (Class<T>) ClassUtils.getGenericSuperclass(getClass());
            } catch (Exception e) {
            }
        }
        return clas;
    }

    /**
     * 获取 UUID
     * @return random UUID HashCode
     */
    public final int getUuid() {
        return uuid;
    }

    /**
     * 获取标记 Tag
     * @return 标记 tag
     */
    public String getTag() {
        return tag;
    }

    /**
     * 设置标记 Tag
     * @param tag tag
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * 获取 Value
     * @return Value
     */
    public T getValue() {
        return value;
    }

    /**
     * 设置 Value
     * @param value T Class Object
     */
    public void setValue(T value) {
        this.value = value;
    }

    /**
     * 获取 Object
     * @return Object
     */
    public Object getObject() {
        return object;
    }

    /**
     * 设置 Object
     * @param object Object
     */
    public void setObject(Object object) {
        this.object = object;
    }
}
