package dev.callback.click;

/**
 * detail: 通用 Dialog 回调
 * @author Ttt
 */
public class DevDialogCallBack<T> extends DevClickCallBack<T> {

    public DevDialogCallBack() {
    }

    public DevDialogCallBack(T value) {
        super(value);
    }

    public DevDialogCallBack(T value, Object object) {
        super(value, object);
    }

    public DevDialogCallBack(T value, Object object, String tag) {
        super(value, object, tag);
    }

    // ============
    // = 通知方法 =
    // ============

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
