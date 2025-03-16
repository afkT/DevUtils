package afkt.project.feature.ui_effect.qrcode

import afkt.project.R
import afkt.project.base.project.BaseProjectActivity
import afkt.project.base.project.BaseProjectViewModel
import afkt.project.data_model.button.RouterPath
import afkt.project.databinding.ActivityQrcodeCreateBinding
import afkt.project.ui.createGalleryConfig
import android.content.Intent
import android.graphics.Bitmap
import android.view.View
import com.therouter.router.Route
import dev.engine.DevEngine
import dev.engine.barcode.BarCodeData
import dev.mvvm.utils.size.AppSize
import dev.utils.app.*
import dev.utils.app.image.ImageUtils
import dev.utils.common.StringUtils
import dev.utils.common.ThrowableUtils

/**
 * detail: 创建二维码
 * @author Ttt
 */
@Route(path = RouterPath.UI_EFFECT.QRCodeCreateActivity_PATH)
class QRCodeCreateActivity : BaseProjectActivity<ActivityQrcodeCreateBinding, BaseProjectViewModel>(
    R.layout.activity_qrcode_create, simple_Agile = {
        if (it is QRCodeCreateActivity) {
            it.apply {
                ListenerUtils.setOnClicks(
                    this, binding.vidCreateBtn, binding.vidSelectBtn
                )
            }
        }
    }
) {

    // 图片 Bitmap
    private var selectBitmap: Bitmap? = null

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.vid_create_btn -> {
                val text = EditTextUtils.getText(binding.vidContentEt)
                // 判断是否存在内容
                if (StringUtils.isEmpty(text)) {
                    showToast(false, "请输入生成二维码内容")
                    return
                }
                val size = AppSize.dp2px(200F)
                // 创建二维码
                DevEngine.getBarCode().encodeBarCode(
                    BarCodeData[text, size].setIcon(selectBitmap)
                ) { success, bitmap, error ->
                    if (success) {
                        HandlerUtils.postRunnable {
                            ImageViewUtils.setImageBitmap(
                                binding.vidIv,
                                bitmap
                            )
                        }
                    } else {
                        showToast(false, ThrowableUtils.getThrowable(error))
                    }
                }
            }

            R.id.vid_select_btn -> {
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
            selectBitmap = ImageUtils.decodeStream(ResourceUtils.openInputStream(imgUri))
        }
    }
}