package afkt.project.util.zxing

import afkt.project.util.zxing.DecodeFormat.DecodeMode
import android.Manifest
import android.hardware.Camera
import android.os.Handler
import android.os.Message
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.google.zxing.Result
import dev.engine.log.DevLogEngine
import dev.utils.app.camera.camera1.CameraAssist
import dev.utils.app.camera.camera1.CameraAssist.PreviewNotify
import dev.utils.app.camera.camera1.CameraUtils
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
 * detail: 捕获预览画面处理 Handler
 * @author Ttt
 */
class CaptureHandler(
    // 解码配置
    decodeConfig: DecodeConfig,
    // 解码类型
    decodeMode: Int,
    // Camera 辅助类
    private val mCameraAssist: CameraAssist,
    // 数据回调
    private val mPreviewCallback: PreviewCallback?,
    // 解码结果回调
    private val mDecodeResult: DecodeResult
) : Handler() {

    // 日志 TAG
    private val TAG = CaptureHandler::class.java.simpleName

    // 识别状态
    private var mState = State.SUCCESS

    // 解码线程
    private val mDecodeThread = DecodeThread(decodeConfig, decodeMode)

    init {
        // 启动解码线程
        mDecodeThread.start()
        // 开始预览
        mCameraAssist.startPreview()
        // 设置预览解码线程
        restartPreviewAndDecode()
    }

    /**
     * detail: 内部枚举状态值
     * @author Ttt
     */
    private enum class State {
        PREVIEW,
        SUCCESS,
        DONE
    }

    override fun handleMessage(message: Message) {
        if (message.what == WHAT_RESTART_PREVIEW) {
            restartPreviewAndDecode()
        } else if (message.what == WHAT_DECODE_SUCCEEDED) { // 解析成功
            DevLogEngine.getEngine()?.dTag(TAG, "解析成功")
            mState = State.SUCCESS
            val bundle = message.data
            mDecodeResult.handleDecode(message.obj as Result, bundle)
        } else if (message.what == WHAT_DECODE_FAILED) { // 解析失败 ( 解析不出来触发 )
            DevLogEngine.getEngine()?.dTag(TAG, "解析失败")
            // 表示预览中
            mState = State.PREVIEW
            // 设置预览解码线程
            requestPreviewFrame(mDecodeThread.handler, WHAT_DECODE)
        }
    }

    // =============
    // = 内部处理方法 =
    // =============

    /**
     * 设置预览帧数据监听
     * @param handler 解码 Handler [DecodeHandler]
     * @param message 解码消息
     */
    @Synchronized
    private fun requestPreviewFrame(
        handler: DecodeHandler?,
        message: Int
    ) {
        DevLogEngine.getEngine()?.dTag(TAG, "requestPreviewFrame")
        val theCamera = mCameraAssist.camera
        // 不为 null 并且预览中才处理
        if (theCamera != null && mCameraAssist.isPreviewing) {
            mPreviewCallback?.setHandler(handler, message)
            theCamera.setOneShotPreviewCallback(mPreviewCallback)
        }
    }

    /**
     * 重新设置预览以及解码处理
     */
    fun restartPreviewAndDecode() {
        DevLogEngine.getEngine()?.dTag(TAG, "restartPreviewAndDecode")
        if (mState == State.SUCCESS) {
            mState = State.PREVIEW
            // 设置请求预览页面
            requestPreviewFrame(mDecodeThread.handler, WHAT_DECODE)
        }
    }

    /**
     * 同步退出解析处理
     */
    fun quitSynchronously() {
        DevLogEngine.getEngine()?.dTag(TAG, "退出扫描")
        // 表示状态为默认
        mState = State.DONE
        // 停止预览
        mCameraAssist.stopPreview()
        val quit = Message.obtain(mDecodeThread.handler, WHAT_QUIT)
        quit.sendToTarget()
        try {
            // 进行处理解析数据
            mDecodeThread.join(200L)
        } catch (e: InterruptedException) {
        }
        // 移除堵塞在队列的消息
        removeMessages(WHAT_DECODE_SUCCEEDED)
        removeMessages(WHAT_DECODE_FAILED)
    }
}

/**
 * detail: 扫描相关操作接口
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
    // 扫描相关操作接口
    val mOperate: Operate,
    // 解码配置
    val mDecodeConfig: DecodeConfig,
    // 解码结果回调
    val mDecodeResult: DecodeResult
) {

    // 日志 TAG
    private val TAG = ZXingDecodeAssist::class.java.simpleName

    // 解码类型
    @DecodeMode
    private val DECODE_MODE = DecodeFormat.ALL

    // 摄像头辅助类
    private val mCameraAssist = CameraAssist()

    // 预览回调
    private var mPreviewCallback: PreviewCallback? = null

    // 数据解析 Handler
    private var mCaptureHandler: CaptureHandler? = null

    private val mHolderCallback: SurfaceHolder.Callback = object : SurfaceHolder.Callback {
        override fun surfaceCreated(holder: SurfaceHolder) {
            // Camera 权限申请
            mOperate.cameraPermission()
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

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 停止预览
     */
    fun stopPreview() {
        try {
            // 停止预览
            mCameraAssist.stopPreview()
        } catch (e: Exception) {
            DevLogEngine.getEngine()?.eTag(TAG, e, "stopPreview")
        }
    }

    /**
     * 开始预览
     * @param view SurfaceView
     * 在获取 [Operate.cameraPermission] 权限成功后调用
     */
    fun startPreview(view: SurfaceView?) {
        if (PermissionUtils.isGranted(Manifest.permission.CAMERA)) {
            try {
                // 打开摄像头
                val camera = CameraUtils.open()
                camera.setDisplayOrientation(90) // 设置竖屏显示
                mCameraAssist.camera = camera
                // 设置监听
                mCameraAssist.setPreviewNotify(object : PreviewNotify {
                    override fun stopPreviewNotify() {
                        DevLogEngine.getEngine()?.dTag(TAG, "停止预览通知")
                        mPreviewCallback?.setHandler(null, 0)
                    }

                    override fun startPreviewNotify() {
                        DevLogEngine.getEngine()?.dTag(TAG, "开始预览通知")
                    }
                })
                // 获取预览大小
                val size = mCameraAssist.cameraResolution
                // 设置预览大小, 需要这样设置, 开闪光灯才不会闪烁
                val parameters = camera.parameters
                parameters.setPreviewSize(size.width, size.height)
                camera.parameters = parameters
                // 打开摄像头
                mCameraAssist.openDriver(view?.holder)
                // 初始化预览回调
                mPreviewCallback = PreviewCallback(size)
                // 初始化获取 Handler
                if (mCaptureHandler == null) {
                    // 初始化捕获预览画面处理 Handler
                    mCaptureHandler = CaptureHandler(
                        mDecodeConfig, DECODE_MODE,
                        mCameraAssist, mPreviewCallback,
                        mDecodeResult
                    )
                }
            } catch (e: java.lang.Exception) {
                DevLogEngine.getEngine()?.eTag(TAG, e, "checkPermission startPreview")
            }
        }
    }

    // =============
    // = 生命周期调用 =
    // =============

    fun onPause() {
        mCaptureHandler?.let {
            it.quitSynchronously()
            mCaptureHandler = null
        }
        // 停止预览
        stopPreview()
    }

    fun onResume(view: SurfaceView?) {
        // 清空重新初始化
        mCaptureHandler = null
        // 绑定 SurfaceHolder Callback
        view?.holder?.apply {
            try {
                removeCallback(mHolderCallback)
            } catch (e: Exception) {
            }
            addCallback(mHolderCallback)
        }
    }

    // ============
    // = 手电筒处理 =
    // ============

    /**
     * 设置手电筒开关
     * @param open 是否打开
     */
    fun setFlashlight(open: Boolean) {
        if (open) {
            mCameraAssist.setFlashlightOn()
        } else {
            mCameraAssist.setFlashlightOff()
        }
        mOperate.switchFlashlight(open)
    }

    /**
     * 切换手电筒开关
     */
    fun toggleFlashlight() {
        setFlashlight(!mCameraAssist.isFlashlightOn)
    }

    // =

    /**
     * 获取捕获预览画面处理 Handler
     * @return [CaptureHandler]
     */
    fun captureHandler(): CaptureHandler? {
        return mCaptureHandler
    }

    /**
     * 获取 Camera 辅助类
     * @return [CameraAssist]
     */
    fun cameraAssist(): CameraAssist {
        return mCameraAssist
    }

    /**
     * 获取 Camera 预览大小
     * @return [Camera.Size]
     */
    fun cameraPreviewSize(): Camera.Size? {
        return mCameraAssist.previewSize
    }
}