package dev.utils.common.assist;

/**
 * detail: 标记值计算存储 ( 位运算符 )
 * @author Ttt
 */
public final class FlagsValue {

    public FlagsValue() {
    }

    public FlagsValue(final int flags) {
        this.mFlags = flags;
    }

    // =============
    // = 对外公开方法 =
    // =============

    // flags value
    private int mFlags;

    /**
     * 获取 flags value
     * @return flags value
     */
    public int getFlags() {
        return mFlags;
    }

    /**
     * 设置 flags value
     * @param flags flags value
     * @return {@link FlagsValue}
     */
    public FlagsValue setFlags(final int flags) {
        this.mFlags = flags;
        return this;
    }

    /**
     * 添加 flags value
     * @param flags flags value
     * @return {@link FlagsValue}
     */
    public FlagsValue addFlags(final int flags) {
        this.mFlags |= flags;
        return this;
    }

    /**
     * 移除 flags value
     * @param flags flags value
     * @return {@link FlagsValue}
     */
    public FlagsValue clearFlags(final int flags) {
        this.mFlags &= ~flags;
        return this;
    }

    /**
     * 是否存在 flags value
     * @param flags flags value
     * @return {@code true} yes, {@code false} no
     */
    public boolean hasFlags(final int flags) {
        return (mFlags & flags) == flags;
    }

    /**
     * 是否不存在 flags value
     * @param flags flags value
     * @return {@code true} yes, {@code false} no
     */
    public boolean notHasFlags(final int flags) {
        return (mFlags & flags) != flags;
    }
}