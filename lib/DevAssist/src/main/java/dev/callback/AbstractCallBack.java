package dev.callback;

import java.util.UUID;

/**
 * detail: 抽象回调 ( 基类 )
 * @author Ttt
 */
public abstract class AbstractCallBack<T> {

    // uuid 一定程度上唯一
    private final int    mUUID = UUID.randomUUID().hashCode();
    // 标记 tag
    private       String mTag;
    // Value
    private       T      mValue;
    // Object
    private       Object mObject;

    public AbstractCallBack() {
    }

    public AbstractCallBack(T value) {
        this.mValue = value;
    }

    public AbstractCallBack(T value, Object object) {
        this.mValue = value;
        this.mObject = object;
    }

    public AbstractCallBack(T value, Object object, String tag) {
        this.mValue = value;
        this.mObject = object;
        this.mTag = tag;
    }

    // ===============
    // = 对外公开方法 =
    // ===============

    /**
     * 获取 UUID
     * @return random UUID HashCode
     */
    public final int getUUID() {
        return mUUID;
    }

    /**
     * 获取标记 Tag
     * @return 标记 Tag
     */
    public String getTag() {
        return mTag;
    }

    /**
     * 设置标记 Tag
     * @param tag Tag
     * @return {@link AbstractCallBack}
     */
    public AbstractCallBack<T> setTag(String tag) {
        this.mTag = tag;
        return this;
    }

    /**
     * 获取 Value
     * @return Value
     */
    public T getValue() {
        return mValue;
    }

    /**
     * 设置 Value
     * @param value T Class Object
     * @return {@link AbstractCallBack}
     */
    public AbstractCallBack<T> setValue(T value) {
        this.mValue = value;
        return this;
    }

    /**
     * 获取 Object
     * @return Object
     */
    public Object getObject() {
        return mObject;
    }

    /**
     * 设置 Object
     * @param object Object
     * @return {@link AbstractCallBack}
     */
    public AbstractCallBack<T> setObject(Object object) {
        this.mObject = object;
        return this;
    }
}