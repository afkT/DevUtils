package dev.callback;

/**
 * detail: 通用 Click 回调
 * @author Ttt
 */
public class DevClickCallback<T>
        extends BaseCallback<T> {

    public DevClickCallback() {
    }

    public DevClickCallback(final T object) {
        super(object);
    }

    public DevClickCallback(
            final T object,
            final Object tag
    ) {
        super(object, tag);
    }

    // ===========
    // = 通用方法 =
    // ===========

    /**
     * 点击回调
     */
    public void onClick() {
    }

    /**
     * 点击回调
     * @param value 回调值
     */
    public void onClick(T value) {
    }

    /**
     * 点击回调
     * @param value 回调值
     * @param param 回调参数
     */
    public void onClick(
            T value,
            int param
    ) {
    }

    // =

    /**
     * 长按回调
     */
    public void onLongClick() {
    }

    /**
     * 长按回调
     * @param value 回调值
     */
    public void onLongClick(T value) {
    }

    /**
     * 长按回调
     * @param value 回调值
     * @param param 回调参数
     */
    public void onLongClick(
            T value,
            int param
    ) {
    }
}