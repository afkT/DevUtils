package dev.base;

import java.io.Serializable;
import java.util.UUID;

/**
 * detail: Model 基类
 * @author Ttt
 * <pre>
 *     isCorrect 可以用于 BaseModel 子类的数据校验
 * </pre>
 */
public abstract class DevModel
        implements Serializable {

    private static final long serialVersionUID = 2514563577168445802L;

    // uuid
    private final int mUUID = UUID.randomUUID().hashCode();
    // model id
    private       int mModelId;

    // ===============
    // = 对外公开方法 =
    // ===============

    /**
     * 获取 UUID
     * @return random UUID HashCode
     */
    public int getUuid() {
        return mUUID;
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
     * @return {@link DevModel}
     */
    public DevModel setModelId(final int modelId) {
        this.mModelId = modelId;
        return this;
    }

    // =

    /**
     * 校验数据正确性
     * @param data {@link DevModel}
     * @return {@code true} correct, {@code false} error
     */
    public static final boolean isCorrect(final DevModel data) {
        return data != null && data.isCorrect();
    }

    /**
     * 校验数据正确性
     * @return {@code true} correct, {@code false} error
     */
    protected abstract boolean isCorrect();
}