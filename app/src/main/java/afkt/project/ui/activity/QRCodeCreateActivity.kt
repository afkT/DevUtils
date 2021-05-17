package afkt.project.ui.activity

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.databinding.ActivityQrcodeCreateBinding
import android.content.Intent
import android.graphics.Bitmap
import android.text.TextUtils
import android.view.View
import com.luck.picture.lib.config.PictureMimeType
import dev.engine.media.DevMediaEngine
import dev.engine.media.MediaConfig
import dev.utils.app.*
import dev.utils.app.image.ImageUtils
import dev.utils.common.ThrowableUtils
import ktx.dev.other.ZXingQRCodeUtils

/**
 * detail: 创建二维码
 * @author Ttt
 */
class QRCodeCreateActivity : BaseActivity<ActivityQrcodeCreateBinding>() {

    // 图片 Bitmap
    var selectBitmap: Bitmap? = null

    override fun baseLayoutId(): Int = R.layout.activity_qrcode_create

    override fun initListener() {
        super.initListener()
        ListenerUtils.setOnClicks(
            this,
            binding.vidAqcCreateBtn, binding.vidAqcSelectBtn
        )
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.vid_aqc_create_btn -> {
                val text = EditTextUtils.getText(binding.vidAqcContentEdit)
                // 判断是否存在内容
                if (TextUtils.isEmpty(text)) {
                    showToast(false, "请输入生成二维码内容")
                    return
                }
                val size = SizeUtils.dipConvertPx(200f)
                // 创建二维码
                ZXingQRCodeUtils.createQRCodeImage(
                    text, size, selectBitmap,
                    object : ZXingQRCodeUtils.QRResultCallback {
                        override fun onResult(
                            success: Boolean,
                            bitmap: Bitmap?,
                            e: Exception?
                        ) {
                            if (success) {
                                HandlerUtils.postRunnable {
                                    ImageViewUtils.setImageBitmap(
                                        binding.vidAqcIgview,
                                        bitmap
                                    )
                                }
                            } else {
                                showToast(false, ThrowableUtils.getThrowable(e))
                            }
                        }
                    })
            }
            R.id.vid_aqc_select_btn -> {
                // 初始化图片配置
                var config = MediaConfig()
                    .setCompress(false).setMaxSelectNum(1).setCrop(false)
                    .setMimeType(PictureMimeType.ofImage())
                    .setCamera(true).setGif(false)
                // 打开图片选择器
                DevMediaEngine.getEngine().openGallery(mActivity, config)
            }
        }
    }

    // ===========
    // = 图片回传 =
    // ===========

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
            selectBitmap = ImageUtils.decodeFile(imgPath)
        }
    }
}