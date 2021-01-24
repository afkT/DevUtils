package afkt.project.util.zxing;

import android.hardware.Camera;
import android.os.Handler;
import android.os.Message;

import dev.engine.log.DevLogEngine;

/**
 * detail: 预览回调
 * @author Ttt
 */
public class PreviewCallback
        implements Camera.PreviewCallback {

    // 日志 TAG
    private final String      TAG = PreviewCallback.class.getSimpleName();
    // 显示的大小
    private final Camera.Size mSize;
    // 处理 Handler
    private       Handler     mPreviewHandler;
    // 处理 what
    private       int         mWhat;

    /**
     * 初始化构造函数
     * @param size {@link Camera.Size}
     */
    public PreviewCallback(Camera.Size size) {
        this.mSize = size;
    }

    /**
     * 设置 Handler、what
     * @param previewHandler 通知处理 Handler
     * @param what           通知 What
     * @return {@link PreviewCallback}
     */
    public PreviewCallback setHandler(
            Handler previewHandler,
            int what
    ) {
        this.mPreviewHandler = previewHandler;
        this.mWhat = what;
        return this;
    }

    @Override
    public void onPreviewFrame(
            byte[] data,
            Camera camera
    ) {
        Handler thePreviewHandler = mPreviewHandler;
        if (mSize != null && thePreviewHandler != null) {
            Message message = thePreviewHandler.obtainMessage(mWhat, mSize.width, mSize.height, data);
            message.sendToTarget();
            mPreviewHandler = null;
        } else {
            DevLogEngine.getEngine().dTag(TAG, "Got preview callback, but no handler or resolution available");
        }
    }
}