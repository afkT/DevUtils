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
    private final int    mUUID      = UUID.randomUUID().hashCode();
    // Object
    private       T      mObject;
    // 标记 Tag
    private       Object mTag;
    // model id
    private       int    mModelId;
    // code String
    private       String mCode;
    // Type
    private       int    mType;
    // State
    private       int    mState;
    // Token uuid
    private       long   mTokenUUID = UUID.randomUUID().hashCode();

    public DevObject() {
    }

    public DevObject(final T object) {
        this.mObject = object;
    }

    public DevObject(
            final T object,
            final Object tag
    ) {
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
     * 获取 Object
     * @return Object
     */
    public T getObject() {
        return mObject;
    }

    /**
     * 设置 Object
     * @param object T Class Object
     * @return {@link DevObject}
     */
    public DevObject<T> setObject(final T object) {
        this.mObject = object;
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
     * 转换标记 Tag
     * @param <CTO> 泛型
     * @return Object convert T object
     */
    public <CTO> CTO convertTag() {
        try {
            return (CTO) mTag;
        } catch (Exception e) {
        }
        return null;
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
     * 获取 State
     * @return State
     */
    public int getState() {
        return mState;
    }

    /**
     * 设置 State
     * @param state State
     * @return {@link DevObject}
     */
    public DevObject<T> setState(final int state) {
        this.mState = state;
        return this;
    }

    /**
     * 获取 Token UUID
     * @return Token UUID
     */
    public long getTokenUUID() {
        return mTokenUUID;
    }

    /**
     * 设置 Token UUID
     * @param uuid token UUID
     * @return {@link DevObject}
     */
    public DevObject<T> setTokenUUID(final long uuid) {
        this.mTokenUUID = uuid;
        return this;
    }

    /**
     * 重置随机 Token UUID
     * @return Token UUID
     */
    public long randomTokenUUID() {
        mTokenUUID = UUID.randomUUID().hashCode();
        return mTokenUUID;
    }

    // ===========
    // = 判断方法 =
    // ===========

    /**
     * 判断 Object 是否一致
     * @param object 待校验 Object
     * @return {@code true} yes, {@code false} no
     */
    public boolean equalsObject(final T object) {
        return object != null && ObjectUtils.equals(this.mObject, object);
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
     * 判断 State 是否一致
     * @param state 待校验 State
     * @return {@code true} yes, {@code false} no
     */
    public boolean equalsState(final int state) {
        return this.mState == state;
    }

    /**
     * 判断 Token UUID 是否一致
     * @param uuid 待校验 Token UUID
     * @return {@code true} yes, {@code false} no
     */
    public boolean equalsTokenUUID(final long uuid) {
        return this.mTokenUUID == uuid;
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