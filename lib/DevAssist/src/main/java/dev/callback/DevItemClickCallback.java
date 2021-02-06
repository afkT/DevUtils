package dev.callback;

/**
 * detail: 通用 Item Click 回调
 * @author Ttt
 */
public class DevItemClickCallback<T>
        extends BaseCallback<T> {

    public DevItemClickCallback() {
    }

    public DevItemClickCallback(final T object) {
        super(object);
    }

    public DevItemClickCallback(
            final T object,
            final Object tag
    ) {
        super(object, tag);
    }

    // ===========
    // = 通用方法 =
    // ===========

    /**
     * 点击 Item 回调
     */
    public void onItemClick() {
    }

    /**
     * 点击 Item 回调
     * @param value 回调值
     */
    public void onItemClick(T value) {
    }

    /**
     * 点击 Item 回调
     * @param value 回调值
     * @param param 回调参数
     */
    public void onItemClick(
            T value,
            int param
    ) {
    }

    // =

    /**
     * 长按 Item 回调
     */
    public void onItemLongClick() {
    }

    /**
     * 长按 Item 回调
     * @param value 回调值
     */
    public void onItemLongClick(T value) {
    }

    /**
     * 长按 Item 回调
     * @param value 回调值
     * @param param 回调参数
     */
    public void onItemLongClick(
            T value,
            int param
    ) {
    }
}