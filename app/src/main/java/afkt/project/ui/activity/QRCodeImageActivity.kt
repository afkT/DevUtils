package afkt.project.ui.activity

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.base.config.RouterPath
import afkt.project.databinding.ActivityQrcodeImageBinding
import android.content.Intent
import android.graphics.Bitmap
import android.text.TextUtils
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.zxing.Result
import com.luck.picture.lib.config.PictureMimeType
import dev.engine.media.DevMediaEngine
import dev.engine.media.MediaConfig
import dev.utils.DevFinal
import dev.utils.app.*
import dev.utils.app.image.ImageUtils
import dev.utils.app.toast.ToastTintUtils
import dev.utils.common.StringUtils
import dev.utils.common.ThrowableUtils
import ktx.dev.other.ZXingQRCodeUtils

/**
 * detail: 二维码图片解析
 * @author Ttt
 */
@Route(path = RouterPath.QRCodeImageActivity_PATH)
class QRCodeImageActivity : BaseActivity<ActivityQrcodeImageBinding>() {

    // 图片 Bitmap
    private var selectBitmap: Bitmap? = null

    override fun baseLayoutId(): Int = R.layout.activity_qrcode_image

    override fun initListener() {
        super.initListener()
        ListenerUtils.setOnClicks(
            this,
            binding.vidAqiSelectBtn, binding.vidAqiTv
        )
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.vid_aqi_select_btn -> {
                // 初始化图片配置
                var config = MediaConfig()
                    .setCompress(false).setMaxSelectNum(1).setCrop(false)
                    .setMimeType(PictureMimeType.ofImage())
                    .setCamera(true).setGif(false)
                // 打开图片选择器
                DevMediaEngine.getEngine().openGallery(mActivity, config)
            }
            R.id.vid_aqi_tv -> {
                val text = TextViewUtils.getText(binding.vidAqiTv)
                if (TextUtils.isEmpty(text)) return
                // 复制到剪切板
                ClipboardUtils.copyText(text)
                // 进行提示
                ToastTintUtils.success(ResourceUtils.getString(R.string.str_copy_suc) + " -> " + text)
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
            // 解析图片
            ZXingQRCodeUtils.decodeQRCode(selectBitmap, object : ZXingQRCodeUtils.QRScanCallback {
                override fun onResult(
                    success: Boolean,
                    result: Result?,
                    e: Exception?
                ) {
                    HandlerUtils.postRunnable {
                        if (success) {
                            val builder = StringBuilder()
                                .append("二维码解析数据: \n")
                                .append(
                                    StringUtils.checkValue(
                                        DevFinal.NULL_STR,
                                        ZXingQRCodeUtils.getResultData(result)
                                    )
                                )
                            TextViewUtils.setText(binding.vidAqiTv, builder.toString())
                        } else {
                            TextViewUtils.setText(
                                binding.vidAqiTv,
                                "图片非二维码 / 识别失败\n" + ThrowableUtils.getThrowableStackTrace(e)
                            )
                        }
                    }
                }
            })
        }
    }
}