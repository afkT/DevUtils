package afkt.project.feature.ui_effect.capture_picture

import afkt.project.R
import afkt.project.base.project.BaseProjectActivity
import afkt.project.base.project.BaseProjectViewModel
import afkt.project.data_model.button.RouterPath
import afkt.project.databinding.ActivityCapturePictureWebBinding
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.therouter.router.Route
import dev.base.DevSource
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
@Route(path = RouterPath.UI_EFFECT.CapturePictureWebActivity_PATH)
class CapturePictureWebActivity :
    BaseProjectActivity<ActivityCapturePictureWebBinding, BaseProjectViewModel>(
        R.layout.activity_capture_picture_web, simple_Agile = {
            if (it is CapturePictureWebActivity) {
                it.apply {
                    // 关闭 WebView 优化
                    CapturePictureUtils.enableSlowWholeDocumentDraw()
                    // 截图按钮
                    val view = QuickHelper.get(AppCompatTextView(this))
                        .setText("截图")
                        .setBold()
                        .setTextColors(ResourceUtils.getColor(R.color.white))
                        .setTextSizeBySp(15.0F)
                        .setPaddingLeft(30)
                        .setPaddingRight(30)
                        .setOnClick {
                            DevEngine.getStorage()?.insertImageToExternal(
                                StorageItem.createExternalItem("web.jpg"),
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
                    // 加载网页
                    binding.vidWv.loadUrl("https://www.csdn.net/")
                }
            }
        }
    )