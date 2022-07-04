package afkt.project.feature.ui_effect.qrcode

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.databinding.ActivityScanShapeBinding
import afkt.project.feature.dev_widget.scan_shape.ScanShapeUtils
import afkt.project.feature.ui_effect.qrcode.zxing.DecodeConfig
import afkt.project.feature.ui_effect.qrcode.zxing.DecodeResult
import afkt.project.feature.ui_effect.qrcode.zxing.Operate
import afkt.project.feature.ui_effect.qrcode.zxing.ZXingDecodeAssist
import afkt.project.model.item.RouterPath
import afkt.project.utils.createGalleryConfig
import android.Manifest
import android.content.Intent
import android.graphics.Rect
import android.hardware.Camera
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.zxing.Result
import dev.engine.DevEngine
import dev.engine.barcode.BarCodeResult
import dev.engine.barcode.listener.BarCodeDecodeCallback
import dev.engine.permission.IPermissionEngine
import dev.kotlin.engine.log.log_dTag
import dev.kotlin.engine.log.log_eTag
import dev.kotlin.engine.permission.permission_request
import dev.utils.app.HandlerUtils
import dev.utils.app.ListenerUtils
import dev.utils.app.ResourceUtils
import dev.utils.app.ViewUtils
import dev.utils.app.assist.BeepVibrateAssist
import dev.utils.app.assist.InactivityTimerAssist
import dev.utils.app.camera.camera1.FlashlightUtils
import dev.utils.app.image.ImageUtils
import dev.widget.ui.ScanShapeView

/**
 * detail: 二维码扫描解析
 * @author Ttt
 */
@Route(path = RouterPath.UI_EFFECT.QRCodeScanActivity_PATH)
class QRCodeScanActivity : BaseActivity<ActivityScanShapeBinding>() {

    // 无操作计时辅助类
    private var mInactivityTimerAssist = InactivityTimerAssist(this)

    // 扫描成功响声 + 震动
    private var mBeepVibrateAssist = BeepVibrateAssist(this, R.raw.dev_beep)

    override fun baseLayoutId(): Int = R.layout.activity_scan_shape

    override fun onDestroy() {
        // 销毁处理
        binding.vidSsv.destroy()
        // 结束计时
        mInactivityTimerAssist.onDestroy()
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        // 开始动画
        binding.vidSsv.startAnim()
        // 开始计时
        mInactivityTimerAssist.onResume()
        // 准备扫描
        zxingDecodeAssist.onResume(binding.vidSurface)
    }

    override fun onPause() {
        super.onPause()
        // 停止动画
        binding.vidSsv.stopAnim()
        // 暂停计时
        mInactivityTimerAssist.onPause()
        // 暂停扫描
        zxingDecodeAssist.onPause()
    }

    override fun initValue() {
        super.initValue()

        // 设置扫描类型
        ScanShapeUtils.refShape(binding.vidSsv, ScanShapeView.Shape.Square)
        // 显示图片识别按钮
        ViewUtils.setVisibility(true, findViewById(R.id.vid_image_iv))
    }

    override fun initListener() {
        super.initListener()
        ListenerUtils.setOnClicks(
            this,
            binding.vidFlashlightIv,
            binding.vidSquareIv,
            binding.vidHexagonIv,
            binding.vidAnnulusIv,
            binding.vidImageIv
        )
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.vid_flashlight_iv -> {
                if (!FlashlightUtils.isFlashlightEnable()) {
                    showToast(false, "暂不支持开启手电筒")
                    return
                }
                // 设置手电筒开关
                zxingDecodeAssist.setFlashlight(
                    !ViewUtils.isSelected(binding.vidFlashlightIv)
                )
            }
            R.id.vid_square_iv -> {
                ScanShapeUtils.refShape(
                    binding.vidSsv,
                    ScanShapeView.Shape.Square
                )
            }
            R.id.vid_hexagon_iv -> {
                ScanShapeUtils.refShape(
                    binding.vidSsv,
                    ScanShapeView.Shape.Hexagon
                )
            }
            R.id.vid_annulus_iv -> {
                ScanShapeUtils.refShape(
                    binding.vidSsv,
                    ScanShapeView.Shape.Annulus
                )
            }
            R.id.vid_image_iv -> {
                mActivity?.let { activity ->
                    // 打开图片选择器
                    DevEngine.getMedia()?.openGallery(activity, activity.createGalleryConfig())
                }
            }
        }
    }

    // ==========
    // = 图片回传 =
    // ==========

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        intent: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, intent)
        // 判断是否属于图片选择
        if (resultCode == RESULT_OK && intent != null) {
            // 获取图片地址
            val imgUri = DevEngine.getMedia()?.getSingleSelectorUri(intent, false)
            // 获取图片 Bitmap
            val selectBitmap = ImageUtils.decodeStream(ResourceUtils.openInputStream(imgUri))
            // 解析图片
            DevEngine.getBarCode().decodeBarCode(
                selectBitmap, object : BarCodeDecodeCallback<BarCodeResult> {
                    override fun onResult(
                        success: Boolean,
                        result: BarCodeResult?,
                        error: Throwable?
                    ) {
                        HandlerUtils.postRunnable {
                            if (success) {
                                mDecodeResult.handleDecode(result?.getResult(), Bundle())
                            } else {
                                showToast(false, "图片非二维码 / 识别失败")
                            }
                        }
                    }
                }
            )
        }
    }

    // ===============
    // = 二维码识别相关 =
    // ===============

    private val zxingDecodeAssist: ZXingDecodeAssist by lazy {
        ZXingDecodeAssist(mOperate, mDecodeConfig, mDecodeResult)
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
            showToast(true, "二维码内容: $resultStr")

            // 以下代码只是为了解决停留在此页面可以一直扫码, 实际扫码成功应该回传
            zxingDecodeAssist.captureHandler()?.let {
                // 延迟重置, 否则手机一直震动 ( 扫描成功, 重置后又解析成功连续触发 )
                HandlerUtils.postRunnable({
                    it.restartPreviewAndDecode()
                }, 1000)
            }
        }
    }

    // 扫描相关操作接口
    private val mOperate = object : Operate {
        override fun cameraPermission() {
            permission_request(
                permissions = arrayOf(
                    Manifest.permission.CAMERA
                ),
                callback = object : IPermissionEngine.Callback {
                    override fun onGranted() {
                        zxingDecodeAssist.startPreview(binding.vidSurface)
                    }

                    override fun onDenied(
                        grantedList: List<String>,
                        deniedList: List<String>,
                        notFoundList: List<String>
                    ) {
                    }
                }
            )
        }

        override fun switchFlashlight(state: Boolean) {
            ViewUtils.setSelected(state, binding.vidFlashlightIv)
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
                val rectF = binding.vidSsv.region
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