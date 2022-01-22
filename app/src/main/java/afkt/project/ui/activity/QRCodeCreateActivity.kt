package afkt.project.ui.activity

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.base.config.RouterPath
import afkt.project.databinding.ActivityQrcodeCreateBinding
import afkt.project.util.AppSize
import android.content.Intent
import android.graphics.Bitmap
import android.text.TextUtils
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import dev.engine.DevEngine
import dev.engine.barcode.BarCodeData
import dev.engine.media.MediaConfig
import dev.utils.app.*
import dev.utils.app.image.ImageUtils
import dev.utils.common.ThrowableUtils

/**
 * detail: 创建二维码
 * @author Ttt
 */
@Route(path = RouterPath.QRCodeCreateActivity_PATH)
class QRCodeCreateActivity : BaseActivity<ActivityQrcodeCreateBinding>() {

    // 图片 Bitmap
    private var selectBitmap: Bitmap? = null

    override fun baseLayoutId(): Int = R.layout.activity_qrcode_create

    override fun initListener() {
        super.initListener()
        ListenerUtils.setOnClicks(
            this,
            binding.vidCreateBtn, binding.vidSelectBtn
        )
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.vid_create_btn -> {
                val text = EditTextUtils.getText(binding.vidContentEt)
                // 判断是否存在内容
                if (TextUtils.isEmpty(text)) {
                    showToast(false, "请输入生成二维码内容")
                    return
                }
                val size = AppSize.dp2px(200f)
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
                // 初始化图片配置
                val config = MediaConfig()
                    .setCompress(false).setMaxSelectNum(1).setCrop(false)
                    .setMimeType(MediaConfig.MimeType.ofImage())
                    .setCamera(true).setGif(false)
                // 打开图片选择器
                DevEngine.getMedia()?.openGallery(mActivity, config)
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
            val imgPath = DevEngine.getMedia()?.getSingleSelectorPath(data, true)
            // 获取图片 Bitmap
            selectBitmap = if (UriUtils.isUri(imgPath)) {
                ImageUtils.decodeStream(
                    ResourceUtils.openInputStream(
                        UriUtils.getUriForString(imgPath)
                    )
                )
            } else {
                ImageUtils.decodeFile(imgPath)
            }
        }
    }
}