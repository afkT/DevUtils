package afkt.project.ui.activity

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.base.config.RouterPath
import afkt.project.databinding.ActivityScanShapeBinding
import afkt.project.util.ProjectUtils.refShape
import afkt.project.util.zxing.DecodeConfig
import afkt.project.util.zxing.DecodeResult
import afkt.project.util.zxing.Operate
import afkt.project.util.zxing.ZXingDecodeAssist
import android.Manifest
import android.content.Intent
import android.graphics.Rect
import android.hardware.Camera
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.zxing.Result
import dev.engine.log.DevLogEngine
import dev.engine.media.DevMediaEngine
import dev.utils.app.*
import dev.utils.app.assist.BeepVibrateAssist
import dev.utils.app.assist.InactivityTimerAssist
import dev.utils.app.camera.camera1.FlashlightUtils
import dev.utils.app.image.ImageUtils
import dev.utils.app.permission.PermissionUtils
import dev.utils.app.permission.PermissionUtils.PermissionCallback
import dev.widget.ui.ScanShapeView
import ktx.dev.engine.media.MediaConfig
import ktx.dev.other.ZXingQRCodeUtils

/**
 * detail: 二维码扫描解析
 * @author Ttt
 */
@Route(path = RouterPath.QRCodeScanActivity_PATH)
class QRCodeScanActivity : BaseActivity<ActivityScanShapeBinding>() {

    // 无操作计时辅助类
    private var mInactivityTimerAssist = InactivityTimerAssist(this)

    // 扫描成功响声 + 震动
    private var mBeepVibrateAssist = BeepVibrateAssist(this, R.raw.dev_beep)

    override fun baseLayoutId(): Int = R.layout.activity_scan_shape

    override fun onDestroy() {
        // 销毁处理
        binding.vidAssScanview.destroy()
        // 结束计时
        mInactivityTimerAssist.onDestroy()
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        // 开始动画
        binding.vidAssScanview.startAnim()
        // 开始计时
        mInactivityTimerAssist.onResume()
        // 准备扫描
        zxingDecodeAssist.onResume(binding.vidAssSurface)
    }

    override fun onPause() {
        super.onPause()
        // 停止动画
        binding.vidAssScanview.stopAnim()
        // 暂停计时
        mInactivityTimerAssist.onPause()
        // 暂停扫描
        zxingDecodeAssist.onPause()
    }

    override fun initValue() {
        super.initValue()

        // 设置扫描类型
        refShape(binding.vidAssScanview, ScanShapeView.Shape.Square)
        // 显示图片识别按钮
        ViewUtils.setVisibility(true, findViewById(R.id.vid_ass_image_igview))
    }

    override fun initListener() {
        super.initListener()
        ListenerUtils.setOnClicks(
            this,
            binding.vidAssFlashlightIgview,
            binding.vidAssSquareIgview,
            binding.vidAssHexagonIgview,
            binding.vidAssAnnulusIgview,
            binding.vidAssImageIgview
        )
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.vid_ass_flashlight_igview -> {
                if (!FlashlightUtils.isFlashlightEnable()) {
                    showToast(false, "暂不支持开启手电筒")
                    return
                }
                // 设置手电筒开关
                zxingDecodeAssist.setFlashlight(
                    !ViewUtils.isSelected(binding.vidAssFlashlightIgview)
                )
            }
            R.id.vid_ass_square_igview -> {
                refShape(
                    binding.vidAssScanview,
                    ScanShapeView.Shape.Square
                )
            }
            R.id.vid_ass_hexagon_igview -> {
                refShape(
                    binding.vidAssScanview,
                    ScanShapeView.Shape.Hexagon
                )
            }
            R.id.vid_ass_annulus_igview -> {
                refShape(
                    binding.vidAssScanview,
                    ScanShapeView.Shape.Annulus
                )
            }
            R.id.vid_ass_image_igview -> {
                // 初始化图片配置
                var config = MediaConfig()
                    .setCompress(false).setMaxSelectNum(1).setCrop(false)
                    .setMimeType(MediaConfig.MimeType.ofImage())
                    .setCamera(true).setGif(false)
                // 打开图片选择器
                DevMediaEngine.getEngine().openGallery(mActivity, config)
            }
        }
    }

    // ==========
    // = 图片回传 =
    // ==========

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        // 判断是否属于图片选择
        if (resultCode == RESULT_OK && data != null) {
            // 获取图片地址
            val imgPath = DevMediaEngine.getEngine().getSingleSelectorPath(data, true)
            // 获取图片 Bitmap
            val selectBitmap = if (UriUtils.isUri(imgPath)) {
                ImageUtils.decodeStream(
                    ResourceUtils.openInputStream(
                        UriUtils.getUriForString(imgPath)
                    )
                )
            } else {
                ImageUtils.decodeFile(imgPath)
            }
            // 解析图片
            ZXingQRCodeUtils.decodeQRCode(selectBitmap, object : ZXingQRCodeUtils.QRScanCallback {
                override fun onResult(
                    success: Boolean,
                    result: Result?,
                    e: Exception?
                ) {
                    HandlerUtils.postRunnable {
                        if (success) {
                            mDecodeResult.handleDecode(result, Bundle())
                        } else {
                            showToast(false, "图片非二维码 / 识别失败")
                        }
                    }
                }
            })
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
            val resultStr = ZXingQRCodeUtils.getResultData(result)
            // 提示解析成功声音
            mBeepVibrateAssist.playBeepSoundAndVibrate()
            // 打印结果
            DevLogEngine.getEngine()?.dTag(
                TAG, "handleDecode result: %s", resultStr
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
            PermissionUtils.permission(Manifest.permission.CAMERA)
                .callback(object : PermissionCallback {
                    override fun onGranted() {
                        zxingDecodeAssist.startPreview(binding.vidAssSurface)
                    }

                    override fun onDenied(
                        grantedList: List<String>,
                        deniedList: List<String>,
                        notFoundList: List<String>
                    ) {
                    }
                }).request(mActivity)
        }

        override fun switchFlashlight(state: Boolean) {
            ViewUtils.setSelected(state, binding.vidAssFlashlightIgview)
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
            e: Exception?
        ) {
            // 记录是否发生异常
            tryError = isError
            // 打印日志
            DevLogEngine.getEngine().eTag(TAG, e, "setError")
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
                val rectF = binding.vidAssScanview.region
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