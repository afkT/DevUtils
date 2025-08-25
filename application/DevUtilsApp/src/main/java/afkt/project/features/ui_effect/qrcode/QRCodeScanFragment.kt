package afkt.project.features.ui_effect.qrcode

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.databinding.FragmentDevWidgetScanShapeBinding
import afkt.project.features.dev_widget.scan_shape.ScanShapeUtils
import afkt.project.features.dev_widget.scan_shape.ScanShapeViewModel
import android.Manifest
import android.app.Activity
import android.graphics.Rect
import android.graphics.RectF
import android.hardware.Camera
import android.os.Bundle
import android.os.Handler
import android.view.SurfaceView
import androidx.lifecycle.lifecycleScope
import com.google.zxing.Result
import dev.base.simple.extensions.asFragment
import dev.engine.barcode.BarCodeResult
import dev.engine.permission.IPermissionEngine
import dev.expand.engine.log.log_dTag
import dev.expand.engine.log.log_eTag
import dev.expand.engine.permission.permission_isGranted
import dev.expand.engine.permission.permission_request
import dev.expand.engine.toast.toast_showLong
import dev.expand.engine.toast.toast_showShort
import dev.utils.app.HandlerUtils
import dev.utils.app.ResourceUtils
import dev.utils.app.assist.BeepVibrateAssist
import dev.widget.ui.ScanShapeView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * detail: 二维码扫描解析
 * @author Ttt
 * 该二维码解析只是结合 ScanShapeView 演示效果，关于动画卡顿等情况
 * 可自行根据 zxing_decode.kt 文件中 DecodeHandler 的 handleMessage 方法进行修改
 * 1. 每次解析后，延迟 0.x 秒再次进行下次解析
 * 2. 减少解析区域，避免对预览画面解码存储 Bitmap 并识别二维码过程占用性能导致卡顿
 * 该类只是用于演示，具体扫描功能可使用第三方开源库 CameraX 封装优化实现
 */
class QRCodeScanFragment : AppFragment<FragmentDevWidgetScanShapeBinding, QRCodeScanViewModel>(
    R.layout.fragment_dev_widget_scan_shape, BR.viewModel, simple_Agile = { frg ->
        frg.asFragment<QRCodeScanFragment> {
            binding.setVariable(BR.scanShape, binding.vidSsv)
            // 设置扫描类型
            ScanShapeUtils.refreshShape(binding.vidSsv, ScanShapeView.Shape.Square)
        }
    }
) {
    override fun onDestroyView() {
        // 销毁处理
        binding.vidSsv.destroy()
        super.onDestroyView()
    }

    override fun onResume() {
        super.onResume()
        // 防止进来初始化太多导致卡顿
        fragmentResume(isInit)
    }

    override fun onPause() {
        super.onPause()
        // 停止动画
        binding.vidSsv.stopAnim()
        // 关闭手电筒
        viewModel.closeFlashlight()
        // 暂停扫描
        viewModel.scanCode?.onPause()
    }

    override fun initValue() {
        super.initValue()

        viewModel.initializeScanCode(
            QRCodeScanCode(
                requireActivity(), object : Operate {
                    override fun cameraPermission() {
                        checkPermission()
                    }

                    override fun switchFlashlight(state: Boolean) {
                        viewModel.flashlightStatus.set(state)
                    }
                },
                {
                    // 返回扫描区域
                    binding.vidSsv.region
                }
            )
        )
    }

    // ==========
    // = 内部方法 =
    // ==========

    var isInit = false

    private fun fragmentResume(preview: Boolean) {
        // 刷新手电筒状态
        viewModel.refreshFlashlightOn()
        // 开始动画
        binding.vidSsv.startAnim()
        // 是否开始预览
        if (preview) {
            // 准备扫描
            viewModel.scanCode?.onResume(binding.vidSurface)
            return@fragmentResume
        }
        // 延迟进行显示
        lifecycleScope.launch {
            delay(100L)
            isInit = true
            // 准备扫描
            viewModel.scanCode?.onResume(binding.vidSurface)
            // 开始校验权限
            checkPermission()
        }
    }

    /**
     * 检查摄像头权限
     */
    private fun checkPermission() {
        // 摄像头权限
        val cameraPermission = Manifest.permission.CAMERA
        // 判断是否允许权限
        val isGranted = context?.permission_isGranted(
            permissions = arrayOf(cameraPermission)
        )
        if (isGranted == true) {
            viewModel.scanCode?.startPreview(binding.vidSurface)
        } else {
            activity?.permission_request(
                permissions = arrayOf(cameraPermission),
                callback = object : IPermissionEngine.Callback {
                    override fun onGranted() {
                        // 刷新处理
                        checkPermission()
                    }

                    override fun onDenied(
                        grantedList: List<String>,
                        deniedList: List<String>,
                        notFoundList: List<String>
                    ) {
                        toast_showShort(text = "需要摄像头权限预览")
                    }
                }
            )
        }
    }
}

class QRCodeScanViewModel : ScanShapeViewModel() {

    // 二维码扫描解析代码
    var scanCode: QRCodeScanCode? = null

    fun initializeScanCode(scanCode: QRCodeScanCode) {
        this.scanCode = scanCode
    }

    override fun setFlashlight(open: Boolean) {
        scanCode?.setFlashlight(open)
    }
}

/**
 * detail: 二维码扫描解析代码
 * @author Ttt
 * 单独抽处理方便理解避免代码数量过多
 */
class QRCodeScanCode(
    private val activity: Activity,
    // 扫描相关操作接口
    private val operate: Operate,
    // 获取扫描区域
    private val scanRect: () -> RectF,
) {

    // 日志 TAG
    private val TAG = QRCodeScanCode::class.java.simpleName

    // 扫描成功响声 + 震动
    private var mBeepVibrateAssist = BeepVibrateAssist(
        activity, BeepVibrateAssist.buildMediaPlayer(
            ResourceUtils.openFd("beep.ogg"), 0.1F
        )
    )

    // ==============
    // = 对外公开方法 =
    // ==============

    /**
     * 开始预览
     * @param view SurfaceView
     * 在获取 [Operate.cameraPermission] 权限成功后调用
     */
    fun startPreview(view: SurfaceView?) {
        zxingDecodeAssist.startPreview(view)
    }

    // =============
    // = 生命周期调用 =
    // =============

    fun onPause() {
        zxingDecodeAssist.onPause()
    }

    fun onResume(view: SurfaceView?) {
        zxingDecodeAssist.onResume(view)
    }

    // ============
    // = 手电筒处理 =
    // ============

    /**
     * 设置手电筒开关
     * @param open 是否打开
     */
    fun setFlashlight(open: Boolean) {
        zxingDecodeAssist.setFlashlight(open)
    }

    // ===============
    // = 二维码识别相关 =
    // ===============

    private val zxingDecodeAssist: ZXingDecodeAssist by lazy {
        ZXingDecodeAssist(operate, mDecodeConfig, mDecodeResult)
    }

    // 解码结果回调
    private val mDecodeResult = object : DecodeResult {
        override fun handleDecode(
            result: Result?,
            bundle: Bundle?
        ) {
            val resultStr = BarCodeResult(result).getResultData()
            // 提示解析成功声音
            mBeepVibrateAssist.playBeepSoundAndVibrate()
            // 打印结果
            TAG.log_dTag(
                message = "handleDecode result: $resultStr"
            )
            toast_showLong(text = "二维码内容: $resultStr")

            // 以下代码只是为了解决停留在此页面可以一直扫码, 实际扫码成功应该回传
            zxingDecodeAssist.captureHandler()?.let {
                // 延迟重置, 否则手机一直震动 ( 扫描成功, 重置后又解析成功连续触发 )
                HandlerUtils.postRunnable({
                    it.restartPreviewAndDecode()
                }, 1000)
            }
        }
    }

    // 解码配置
    private val mDecodeConfig = object : DecodeConfig {
        // 是否出现异常
        private var tryError = false

        // 扫描区域
        private var mCropRect: Rect? = null

        override fun isError(): Boolean {
            return tryError
        }

        override fun setError(
            isError: Boolean,
            error: Throwable?
        ) {
            // 记录是否发生异常
            tryError = isError
            // 打印日志
            TAG.log_eTag(
                throwable = error,
                message = "setError"
            )
        }

        override fun getHandler(): Handler? {
            return zxingDecodeAssist.captureHandler()
        }

        override fun getPreviewSize(): Camera.Size? {
            return zxingDecodeAssist.cameraPreviewSize()
        }

        override fun isCropRect(): Boolean = false

        override fun getCropRect(): Rect? {
            // 优化专门识别指定区域需 isCropRect() 返回 true
            mCropRect?.let {
                val rectF = scanRect.invoke()
                val rect = Rect()
                rect.left = rectF.left.toInt()
                rect.top = rectF.top.toInt()
                rect.right = rectF.right.toInt()
                rect.bottom = rectF.bottom.toInt()
                mCropRect = rect
            }
            return mCropRect
        }
    }
}