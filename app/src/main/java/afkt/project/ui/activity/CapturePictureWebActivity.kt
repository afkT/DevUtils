package afkt.project.ui.activity

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.base.config.PathConfig
import afkt.project.databinding.ActivityCapturePictureWebBinding
import android.os.Bundle
import android.view.View
import dev.base.widget.BaseTextView
import dev.utils.app.CapturePictureUtils
import dev.utils.app.ResourceUtils
import dev.utils.app.helper.QuickHelper
import dev.utils.app.image.ImageUtils

/**
 * detail: CapturePictureUtils WebView 截图
 * @author Ttt
 */
class CapturePictureWebActivity : BaseActivity<ActivityCapturePictureWebBinding>() {

    override fun baseLayoutId(): Int = R.layout.activity_capture_picture_web

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 关闭 WebView 优化
        CapturePictureUtils.enableSlowWholeDocumentDraw()
        // 截图按钮
        val view = QuickHelper.get(BaseTextView(this))
            .setText("截图")
            .setBold()
            .setTextColor(ResourceUtils.getColor(R.color.white))
            .setTextSizeBySp(15.0f)
            .setPaddingLeft(30)
            .setPaddingRight(30)
            .setOnClicks {
                val filePath = PathConfig.AEP_DOWN_IMAGE_PATH
                val fileName = "web.jpg"
                val bitmap = CapturePictureUtils.snapshotByWebView(binding.vidAcpWebview)
                val result = ImageUtils.saveBitmapToSDCardJPEG(bitmap, filePath + fileName)
                showToast(result, "保存成功\n${filePath + fileName}", "保存失败")
            }.getView<View>()
        toolbar?.addView(view)
    }

    override fun initValue() {
        super.initValue()
        // 加载网页
        binding.vidAcpWebview.loadUrl("https://www.csdn.net/")
    }
}