package dev.callback;

import java.util.UUID;

/**
 * detail: 抽象回调 - 基类
 * @author Ttt
 */
public abstract class AbstractCallBack<T> {

    // uuid 一定程度上唯一
    private final int uuid = UUID.randomUUID().hashCode();
    // 标记 tag1
    private String tag;
    // Value
    private T value;
    // Object
    private Object object;

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
