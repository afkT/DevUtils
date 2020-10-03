package dev.base;

import dev.utils.common.ObjectUtils;

/**
 * detail: Event 基类
 * @author Ttt
 */
public class DevBaseEvent<T> {

    // code
    private int    mCode;
    // code String
    private String mCodeStr;
    // value
    private T      mValue;
    // object
    private Object mObject;

    // ===============
    // = 对外公开方法 =
    // ===============

    /**
     * 获取 Code
     * @return Code
     */
    public int getCode() {
        return mCode;
    }

    /**
     * 设置 Code
     * @param code Code
     * @return {@link DevBaseEvent}
     */
    public DevBaseEvent<T> setCode(final int code) {
        this.mCode = code;
        return this;
    }

    // =

    /**
     * 获取 Code String
     * @return Code String
     */
    public String getCodeStr() {
        return mCodeStr;
    }

    /**
     * 设置 Code String
     * @param codeStr Code String
     * @return {@link DevBaseEvent}
     */
    public DevBaseEvent<T> setCodeStr(final String codeStr) {
        this.mCodeStr = codeStr;
        return this;
    }

    // =

    /**
     * 获取 Value
     * @return Value
     */
    public T getValue() {
        return mValue;
    }

    /**
     * 设置 Value
     * @param value Value
     * @return {@link DevBaseEvent}
     */
    public DevBaseEvent<T> setValue(final T value) {
        this.mValue = value;
        return this;
    }

    // =

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
     * @return {@link DevBaseEvent}
     */
    public DevBaseEvent<T> setObject(final Object object) {
        this.mObject = object;
        return this;
    }

    // ===========
    // = 判断方法 =
    // ===========

    /**
     * 判断 Code 是否一致
     * @param code 待校验 Code
     * @return {@code true} yes, {@code false} no
     */
    public boolean equalsCode(final int code) {
        return this.mCode == code;
    }

    /**
     * 判断 Code String 是否一致
     * @param codeStr 待校验 Code String
     * @return {@code true} yes, {@code false} no
     */
    public boolean equalsCode(final String codeStr) {
        return this.mCodeStr != null && codeStr != null && this.mCodeStr.equals(codeStr);
    }

    /**
     * 判断 Value 是否一致
     * @param value 待校验 Value
     * @return {@code true} yes, {@code false} no
     */
    public boolean equalsValue(final T value) {
        return this.mValue != null && value != null && ObjectUtils.equals(this.mValue, value);
    }

    /**
     * 判断 Object 是否一致
     * @param object 待校验 Object
     * @return {@code true} yes, {@code false} no
     */
    public boolean equalsObject(final Object object) {
        return this.mObject != null && object != null && ObjectUtils.equals(this.mObject, object);
    }
}