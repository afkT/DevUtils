package afkt.project.ui.activity

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.base.config.RouterPath
import afkt.project.databinding.ActivityCapturePictureWebBinding
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import dev.base.DevSource
import dev.base.widget.BaseTextView
import dev.engine.DevEngine
import dev.engine.storage.OnDevInsertListener
import dev.engine.storage.StorageItem
import dev.engine.storage.StorageResult
import dev.utils.app.CapturePictureUtils
import dev.utils.app.ResourceUtils
import dev.utils.app.helper.quick.QuickHelper
import dev.utils.common.FileUtils

/**
 * detail: CapturePictureUtils WebView 截图
 * @author Ttt
 */
@Route(path = RouterPath.CapturePictureWebActivity_PATH)
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
            .setTextColors(ResourceUtils.getColor(R.color.white))
            .setTextSizeBySp(15.0f)
            .setPaddingLeft(30)
            .setPaddingRight(30)
            .setOnClick {
                DevEngine.getStorage()?.insertImageToExternal(
                    StorageItem.createExternalItem(
                        "web.jpg"
                    ),
                    DevSource.create(
                        CapturePictureUtils.snapshotByWebView(binding.vidWv)
                    ),
                    object : OnDevInsertListener {
                        override fun onResult(
                            result: StorageResult,
                            params: StorageItem?,
                            source: DevSource?
                        ) {
                            showToast(
                                result.isSuccess(),
                                "保存成功\n${FileUtils.getAbsolutePath(result.getFile())}",
                                "保存失败"
                            )
                        }
                    }
                )
            }.getView<View>()
        toolbar?.addView(view)
    }

    override fun initValue() {
        super.initValue()
        // 加载网页
        binding.vidWv.loadUrl("https://www.csdn.net/")
    }
}