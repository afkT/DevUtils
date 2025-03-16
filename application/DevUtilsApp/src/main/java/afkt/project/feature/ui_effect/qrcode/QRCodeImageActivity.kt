package afkt.project.feature.ui_effect.qrcode

import afkt.project.R
import afkt.project.base.project.BaseProjectActivity
import afkt.project.base.project.BaseProjectViewModel
import afkt.project.data_model.button.RouterPath
import afkt.project.databinding.ActivityQrcodeImageBinding
import afkt.project.ui.createGalleryConfig
import android.content.Intent
import android.graphics.Bitmap
import com.therouter.router.Route
import dev.base.DevSource
import dev.engine.DevEngine
import dev.engine.barcode.BarCodeResult
import dev.engine.barcode.listener.BarCodeDecodeCallback
import dev.engine.image.listener.BitmapListener
import dev.expand.engine.image.loadBitmap
import dev.mvvm.utils.toSource
import dev.utils.DevFinal
import dev.utils.app.*
import dev.utils.app.toast.ToastTintUtils
import dev.utils.common.StringUtils
import dev.utils.common.ThrowableUtils
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

/**
 * detail: 二维码图片解析
 * @author Ttt
 */
@Route(path = RouterPath.UI_EFFECT.QRCodeImageActivity_PATH)
class QRCodeImageActivity : BaseProjectActivity<ActivityQrcodeImageBinding, BaseProjectViewModel>(
    R.layout.activity_qrcode_image, simple_Agile = {
        if (it is QRCodeImageActivity) {
            it.apply {
                ListenerUtils.setOnClicks(
                    { v ->
                        when (v.id) {
                            R.id.vid_select_btn -> {
                                // 打开图片选择器
                                DevEngine.getMedia()?.openGallery(it, it.createGalleryConfig())
                            }

                            R.id.vid_tv -> {
                                val text = TextViewUtils.getText(binding.vidTv)
                                if (StringUtils.isEmpty(text)) return@setOnClicks
                                // 复制到剪切板
                                ClipboardUtils.copyText(text)
                                // 进行提示
                                ToastTintUtils.success(ResourceUtils.getString(R.string.str_copy_suc) + " -> " + text)
                            }
                        }
                    }, binding.vidSelectBtn, binding.vidTv
                )
            }
        }
    }
) {

    // 图片 Bitmap
    private var selectBitmap: Bitmap? = null

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
            MainScope().launch {
                // 获取图片地址
                val imgUri = DevEngine.getMedia()?.getSingleSelectorUri(intent, false)

                mActivity.loadBitmap(
                    source = imgUri?.toSource(), config = null,
                    listener = object : BitmapListener() {
                        override fun onStart(source: DevSource) {}
                        override fun onResponse(
                            source: DevSource,
                            value: Bitmap
                        ) {
                            // 获取图片 Bitmap
                            selectBitmap = value
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
                                                val builder = StringBuilder()
                                                    .append("二维码解析数据: \n")
                                                    .append(
                                                        StringUtils.checkValue(
                                                            DevFinal.SYMBOL.NULL,
                                                            result?.getResultData()
                                                        )
                                                    )
                                                TextViewUtils.setText(
                                                    binding.vidTv,
                                                    builder.toString()
                                                )
                                            } else {
                                                TextViewUtils.setText(
                                                    binding.vidTv, "图片非二维码 / 识别失败\n" +
                                                            ThrowableUtils.getThrowableStackTrace(
                                                                error
                                                            )
                                                )
                                            }
                                        }
                                    }
                                }
                            )
                        }

                        override fun onFailure(
                            source: DevSource,
                            throwable: Throwable
                        ) {
                            TextViewUtils.setText(
                                binding.vidTv, "图片非二维码 / 识别失败\n"
                                        + ThrowableUtils.getThrowableStackTrace(throwable)
                            )
                        }
                    }
                )
            }
        }
    }
}