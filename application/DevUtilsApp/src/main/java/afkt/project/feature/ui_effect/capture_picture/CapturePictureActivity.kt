package afkt.project.feature.ui_effect.capture_picture

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.data_model.button.ButtonValue
import afkt.project.data_model.button.RouterPath
import afkt.project.databinding.ActivityCapturePictureBinding
import android.graphics.Bitmap
import android.view.View
import com.therouter.router.Route
import dev.base.DevSource
import dev.engine.DevEngine
import dev.engine.storage.OnDevInsertListener
import dev.engine.storage.StorageItem
import dev.engine.storage.StorageResult
import dev.utils.app.CapturePictureUtils
import dev.utils.app.ListenerUtils
import dev.utils.common.FileUtils

/**
 * detail: CapturePictureUtils 截图工具类
 * @author Ttt
 */
@Route(path = RouterPath.UI_EFFECT.CapturePictureActivity_PATH)
class CapturePictureActivity : BaseActivity<ActivityCapturePictureBinding>() {

    override fun baseLayoutId(): Int = R.layout.activity_capture_picture

    override fun initValue() {
        super.initValue()

        // ==========
        // = 截图方法 =
        // ==========

//        | snapshotWithStatusBar | 获取当前屏幕截图, 包含状态栏 ( 顶部灰色 TitleBar 高度, 没有设置 android:theme 的 NoTitleBar 时会显示 ) |
//        | snapshotWithoutStatusBar | 获取当前屏幕截图, 不包含状态栏 ( 如果 android:theme 全屏, 则截图无状态栏 ) |
//        | enableSlowWholeDocumentDraw | 关闭 WebView 优化 |
//        | snapshotByWebView | 截图 WebView |
//        | snapshotByView | 通过 View 绘制为 Bitmap |
//        | snapshotByViewCache | 通过 View Cache 绘制为 Bitmap |
//        | snapshotByLinearLayout | 通过 LinearLayout 绘制为 Bitmap |
//        | snapshotByFrameLayout | 通过 FrameLayout 绘制为 Bitmap |
//        | snapshotByRelativeLayout | 通过 RelativeLayout 绘制为 Bitmap |
//        | snapshotByScrollView | 通过 ScrollView 绘制为 Bitmap |
//        | snapshotByHorizontalScrollView | 通过 HorizontalScrollView 绘制为 Bitmap |
//        | snapshotByNestedScrollView | 通过 NestedScrollView 绘制为 Bitmap |
//        | snapshotByListView | 通过 ListView 绘制为 Bitmap |
//        | snapshotByGridView | 通过 GridView 绘制为 Bitmap |
//        | snapshotByRecyclerView | 通过 RecyclerView 绘制为 Bitmap |
    }

    override fun initListener() {
        super.initListener()
        ListenerUtils.setOnClicks(
            this,
            binding.vidScreenBtn, binding.vidScreen1Btn,
            binding.vidLinearBtn, binding.vidScrollBtn,
            binding.vidListBtn, binding.vidGridBtn,
            binding.vidRecyBtn, binding.vidWebviewBtn
        )
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.vid_screen_btn -> {
                saveBitmap(
                    "screen.jpg",
                    CapturePictureUtils.snapshotWithStatusBar(mActivity)
                )
            }

            R.id.vid_screen1_btn -> {
                saveBitmap(
                    "screen1.jpg",
                    CapturePictureUtils.snapshotWithoutStatusBar(mActivity)
                )
            }

            R.id.vid_linear_btn -> {
                // snapshotByLinearLayout、snapshotByFrameLayout、snapshotByRelativeLayout
                // 以上方法都是使用 snapshotByView
                saveBitmap(
                    "linear.jpg",
                    CapturePictureUtils.snapshotByLinearLayout(binding.vidLl)
                )
            }

            R.id.vid_scroll_btn -> {
                // snapshotByScrollView、snapshotByHorizontalScrollView、snapshotByNestedScrollView
                saveBitmap(
                    "scroll.jpg",
                    CapturePictureUtils.snapshotByNestedScrollView(binding.vidNsv)
                )
            }

            R.id.vid_list_btn -> {
                routerActivity(
                    ButtonValue(
                        1, "CapturePictureUtils ListView 截图",
                        RouterPath.UI_EFFECT.CapturePictureListActivity_PATH
                    )
                )
            }

            R.id.vid_grid_btn -> {
                routerActivity(
                    ButtonValue(
                        2, "CapturePictureUtils GridView 截图",
                        RouterPath.UI_EFFECT.CapturePictureGridActivity_PATH
                    )
                )
            }

            R.id.vid_recy_btn -> {
                routerActivity(
                    ButtonValue(
                        3, "CapturePictureUtils RecyclerView 截图",
                        RouterPath.UI_EFFECT.CapturePictureRecyActivity_PATH
                    )
                )
            }

            R.id.vid_webview_btn -> {
                routerActivity(
                    ButtonValue(
                        4, "CapturePictureUtils WebView 截图",
                        RouterPath.UI_EFFECT.CapturePictureWebActivity_PATH
                    )
                )
            }
        }
    }

    private fun saveBitmap(
        fileName: String,
        bitmap: Bitmap?
    ) {
        DevEngine.getStorage()?.insertImageToExternal(
            StorageItem.createExternalItem(
                fileName
            ),
            DevSource.create(
                bitmap
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
    }
}