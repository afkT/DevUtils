package dev.callback;

/**
 * detail: 通用 Dialog 回调
 * @author Ttt
 */
public class DevDialogCallback<T>
        extends DevClickCallback<T> {

    public DevDialogCallback() {
    }

    public DevDialogCallback(final T object) {
        super(object);
    }

    public DevDialogCallback(
            final T object,
            final Object tag
    ) {
        super(object, tag);
    }

    // ===========
    // = 通知方法 =
    // ===========

    /**
     * 特殊通知
     */
    public void onDialogNotify() {
    }

    /**
     * show 通知
     */
    public void onDialogShow() {
    }

    /**
     * dismiss 通知
     */
    public void onDialogDismiss() {
    }

    // =

    /**
     * start 通知
     */
    public void onDialogStart() {
    }

    /**
     * resume 通知
     */
    public void onDialogResume() {
    }

    /**
     * pause 通知
     */
    public void onDialogPause() {
    }

    /**
     * stop 通知
     */
    public void onDialogStop() {
    }

    /**
     * destroy 通知
     */
    public void onDialogDestroy() {
    }
}