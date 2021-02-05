package dev.base;

import java.io.Serializable;
import java.util.UUID;

import dev.utils.common.ObjectUtils;

/**
 * detail: 通用 Object
 * @author Ttt
 */
public class DevObject<T>
        implements Serializable {

    // uuid ( 一定程度上唯一 )
    private final int    mUUID        = UUID.randomUUID().hashCode();
    // Value
    private       T      mValue;
    // 标记 Tag
    private       Object mTag;
    // model id
    private       int    mModelId;
    // code String
    private       String mCode;
    // Type
    private       int    mType;
    // Operate id
    private       long   mOperateUUID = UUID.randomUUID().hashCode();

    public DevObject() {
    }

    public DevObject(final T value) {
        this.mValue = value;
    }

    public DevObject(
            final T value,
            final Object tag
    ) {
        this.mValue = value;
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
     * 获取 Value
     * @return Value
     */
    public T getValue() {
        return mValue;
    }

    /**
     * 设置 Value
     * @param value T Class Object
     * @return {@link DevObject}
     */
    public DevObject<T> setValue(final T value) {
        this.mValue = value;
        return this;
    }

    /**
     * 获取标记 Tag
     * @return 标记 Tag
     */
    public Object getTag() {
        return mTag;
    }

    /**
     * 设置标记 Tag
     * @param tag Tag
     * @return {@link DevObject}
     */
    public DevObject<T> setTag(final Object tag) {
        this.mTag = tag;
        return this;
    }

    /**
     * 获取 Model id
     * @return model id
     */
    public int getModelId() {
        return mModelId;
    }

    /**
     * 设置 Model id
     * @param modelId model id
     * @return {@link DevObject}
     */
    public DevObject<T> setModelId(final int modelId) {
        this.mModelId = modelId;
        return this;
    }

    /**
     * 获取 Code
     * @return Code
     */
    public String getCode() {
        return mCode;
    }

    /**
     * 设置 Code
     * @param code Code
     * @return {@link DevObject}
     */
    public DevObject<T> setCode(final String code) {
        this.mCode = code;
        return this;
    }

    /**
     * 设置 Code
     * @param code Code
     * @return {@link DevObject}
     */
    public DevObject<T> setCode(final int code) {
        return setCode(String.valueOf(code));
    }

    /**
     * 获取 Type
     * @return Type
     */
    public int getType() {
        return mType;
    }

    /**
     * 设置 Type
     * @param type Type
     * @return {@link DevObject}
     */
    public DevObject<T> setType(final int type) {
        this.mType = type;
        return this;
    }

    /**
     * 获取 Operate UUID
     * @return Operate UUID
     */
    public long getOperateUUID() {
        return mOperateUUID;
    }

    /**
     * 设置 Operate UUID
     * @param operateUUID operate UUID
     * @return {@link DevObject}
     */
    public DevObject<T> setOperateUUID(final long operateUUID) {
        this.mOperateUUID = operateUUID;
        return this;
    }

    /**
     * 重置随机 Operate UUID
     * @return Operate UUID
     */
    public long randomOperateUUID() {
        mOperateUUID = UUID.randomUUID().hashCode();
        return mOperateUUID;
    }

    // ===========
    // = 判断方法 =
    // ===========

    /**
     * 判断 Value 是否一致
     * @param value 待校验 Value
     * @return {@code true} yes, {@code false} no
     */
    public boolean equalsValue(final T value) {
        return value != null && ObjectUtils.equals(this.mValue, value);
    }

    /**
     * 判断 Tag 是否一致
     * @param tag 待校验 Tag
     * @return {@code true} yes, {@code false} no
     */
    public boolean equalsTag(final Object tag) {
        return tag != null && ObjectUtils.equals(this.mTag, tag);
    }

    /**
     * 判断 Model id 是否一致
     * @param modelId 待校验 Model id
     * @return {@code true} yes, {@code false} no
     */
    public boolean equalsModelId(final int modelId) {
        return this.mModelId == modelId;
    }

    /**
     * 判断 Code 是否一致
     * @param code 待校验 Code
     * @return {@code true} yes, {@code false} no
     */
    public boolean equalsCode(final int code) {
        return equalsCode(String.valueOf(code));
    }

    /**
     * 判断 Code 是否一致
     * @param code 待校验 Code
     * @return {@code true} yes, {@code false} no
     */
    public boolean equalsCode(final String code) {
        return code != null && ObjectUtils.equals(this.mCode, code);
    }

    /**
     * 判断 Type 是否一致
     * @param type 待校验 Type
     * @return {@code true} yes, {@code false} no
     */
    public boolean equalsType(final int type) {
        return this.mType == type;
    }

    /**
     * 判断 Operate UUID 是否一致
     * @param operateUUID 待校验 Operate UUID
     * @return {@code true} yes, {@code false} no
     */
    public boolean equalsOperateUUID(final long operateUUID) {
        return this.mOperateUUID == operateUUID;
    }

    // ===========
    // = 校验方法 =
    // ===========

    /**
     * 校验数据正确性
     * <pre>
     *     根据需要重写
     * </pre>
     * @return {@code true} correct, {@code false} error
     */
    public boolean isCorrect() {
        return false;
    }

    /**
     * 校验数据正确性
     * @param data {@link DevObject}
     * @return {@code true} correct, {@code false} error
     */
    public static final boolean isCorrect(final DevObject data) {
        return data != null && data.isCorrect();
    }
}