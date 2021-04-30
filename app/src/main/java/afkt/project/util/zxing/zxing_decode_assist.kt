package afkt.project.util.zxing

import afkt.project.ui.activity.QRCodeScanActivity.CaptureHandler
import afkt.project.util.zxing.DecodeFormat.DecodeMode
import android.Manifest
import android.hardware.Camera
import android.os.Handler
import android.view.SurfaceHolder
import android.view.SurfaceView
import dev.engine.log.DevLogEngine
import dev.utils.app.camera1.CameraAssist
import dev.utils.app.camera1.CameraAssist.PreviewNotify
import dev.utils.app.camera1.CameraUtils
import dev.utils.app.permission.PermissionUtils

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

/**
 * detail: 操作接口
 * @author Ttt
 */
interface Operate {

    /**
     * Camera 权限申请
     */
    fun cameraPermission()

    /**
     * 手电筒开关状态通知
     * @param state Boolean
     */
    fun switchFlashlight(state: Boolean)
}

/**
 * detail: ZXing 解码辅助类
 * @author Ttt
 */
class ZXingDecodeAssist(
    // 操作接口
    val operate: Operate,
    // 解码配置
    val decodeConfig: DecodeConfig,
    // 解码结果回调
    val decodeResult: DecodeResult
) {

    // 日志 TAG
    private val TAG = ZXingDecodeAssist::class.java.simpleName

    // 解码类型
    @DecodeMode
    private val DECODE_MODE = DecodeFormat.ALL

    // 摄像头辅助类
    private val _cameraAssist = CameraAssist()

    // 预览回调
    private var _previewCallback: PreviewCallback? = null

    // 数据解析 Handler
    private var _captureHandler: CaptureHandler? = null

    private val mHolderCallback: SurfaceHolder.Callback = object : SurfaceHolder.Callback {
        override fun surfaceCreated(holder: SurfaceHolder) {
            // Camera 权限申请
            operate.cameraPermission()
        }

        override fun surfaceChanged(
            holder: SurfaceHolder,
            format: Int,
            width: Int,
            height: Int
        ) {
        }

        override fun surfaceDestroyed(holder: SurfaceHolder) {
            // 关闭手电筒
            setFlashlight(false)
            // 停止预览
            stopPreview()
        }
    }

    // ===============
    // = 对外公开方法 =
    // ===============

    /**
     * 停止预览
     */
    fun stopPreview() {
        try {
            // 停止预览
            _cameraAssist.stopPreview()
        } catch (e: Exception) {
            DevLogEngine.getEngine()?.eTag(TAG, e, "stopPreview")
        }
    }

    /**
     * 开始预览
     * @param view SurfaceView
     */
    fun startPreview(view: SurfaceView) {
        if (PermissionUtils.isGranted(Manifest.permission.CAMERA)) {
            try {
                // 打开摄像头
                val camera = CameraUtils.open()
                camera.setDisplayOrientation(90) // 设置竖屏显示
                _cameraAssist.camera = camera
                // 设置监听
                _cameraAssist.setPreviewNotify(object : PreviewNotify {
                    override fun stopPreviewNotify() {
                        DevLogEngine.getEngine().dTag(TAG, "停止预览通知")
                        _previewCallback?.setHandler(null, 0)
                    }

                    override fun startPreviewNotify() {
                        DevLogEngine.getEngine().dTag(TAG, "开始预览通知")
                    }
                })
                // 获取预览大小
                val size = _cameraAssist.cameraResolution
                // 设置预览大小, 需要这样设置, 开闪光灯才不会闪烁
                val parameters = camera.parameters
                parameters.setPreviewSize(size.width, size.height)
                camera.parameters = parameters
                // 打开摄像头
                _cameraAssist.openDriver(view.getHolder())
                // 初始化预览回调
                _previewCallback = PreviewCallback(size)
                // 初始化获取 Handler
                if (_captureHandler == null) {
                    // 初始化捕获预览画面处理 Handler
                    _captureHandler = CaptureHandler(
                        decodeConfig, DECODE_MODE,
                        _cameraAssist, _previewCallback,
                        decodeResult
                    )
                }
            } catch (e: java.lang.Exception) {
                DevLogEngine.getEngine().eTag(TAG, e, "checkPermission startPreview")
            }
        }
    }

    // =============
    // = 手电筒处理 =
    // =============

    /**
     * 设置手电筒开关
     * @param open 是否打开
     */
    fun setFlashlight(open: Boolean) {
        if (open) {
            _cameraAssist.setFlashlightOn()
        } else {
            _cameraAssist.setFlashlightOff()
        }
        operate.switchFlashlight(open)
    }

    /**
     * 切换手电筒开关
     */
    fun toggleFlashlight() {
        setFlashlight(!_cameraAssist.isFlashlightOn)
    }
}