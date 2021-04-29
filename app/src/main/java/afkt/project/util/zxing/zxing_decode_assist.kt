package afkt.project.util.zxing

import android.hardware.Camera
import android.os.Handler
import dev.engine.log.DevLogEngine

/**
 * detail: 预览回调
 * @author Ttt
 */
class PreviewCallback(
    // 显示的大小
    private val mSize: Camera.Size?
) : Camera.PreviewCallback {

    // 日志 TAG
    private val TAG = PreviewCallback::class.java.simpleName

    // 处理 Handler
    private var mPreviewHandler: Handler? = null

    // 处理 what
    private var mWhat = 0

    /**
     * 设置 Handler、what
     * @param previewHandler 通知处理 Handler
     * @param what           通知 What
     * @return [PreviewCallback]
     */
    fun setHandler(
        previewHandler: Handler?,
        what: Int
    ): PreviewCallback {
        mPreviewHandler = previewHandler
        mWhat = what
        return this
    }

    override fun onPreviewFrame(
        data: ByteArray,
        camera: Camera
    ) {
        if (mSize != null && mPreviewHandler != null) {
            mPreviewHandler?.apply {
                val message = obtainMessage(mWhat, mSize.width, mSize.height, data)
                message.sendToTarget()
                mPreviewHandler = null
            }
        } else {
            DevLogEngine.getEngine()?.dTag(
                TAG, "Got preview callback, but no handler or resolution available"
            )
        }
    }
}