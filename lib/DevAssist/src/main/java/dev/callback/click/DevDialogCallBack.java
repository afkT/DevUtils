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
    public void onNotify() {
    }

    /**
     * show 通知
     */
    public void onNotifyShow() {
    }

    /**
     * dismiss 通知
     */
    public void onNotifyDismiss() {
    }

    // =

    /**
     * start 通知
     */
    public void onNotifyStart() {
    }

    /**
     * resume 通知
     */
    public void onNotifyResume() {
    }

    /**
     * pause 通知
     */
    public void onNotifyPause() {
    }

    /**
     * stop 通知
     */
    public void onNotifyStop() {
    }

    /**
     * destroy 通知
     */
    public void onNotifyDestroy() {
    }
}
