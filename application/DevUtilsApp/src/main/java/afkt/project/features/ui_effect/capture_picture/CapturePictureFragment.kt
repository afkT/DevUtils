package afkt.project.features.ui_effect.capture_picture

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentUiEffectCapturePictureBinding
import afkt.project.features.ui_effect.capture_picture.CapturePictureFragment.Companion.saveBitmap
import afkt.project.model.button.ButtonEnum
import afkt.project.model.button.click
import android.graphics.Bitmap
import android.view.View
import android.widget.LinearLayout
import androidx.core.widget.NestedScrollView
import dev.base.DevSource
import dev.base.simple.extensions.asFragment
import dev.engine.DevEngine
import dev.engine.core.storage.OnDevInsertListener
import dev.engine.core.storage.StorageItem
import dev.engine.core.storage.StorageResult
import dev.engine.extensions.toast.toast_showShort
import dev.utils.app.ActivityUtils
import dev.utils.app.CapturePictureUtils
import dev.utils.common.FileUtils

/**
 * detail: CapturePictureUtils 截图工具类
 * @author Ttt
 * // ==========
 * // = 截图方法 =
 * // ==========
 *
 * | setBitmapConfig | 设置 Bitmap Config |
 * | setBackgroundColor | 设置 Canvas 背景色 |
 * | setPaint | 设置画笔 |
 * | snapshotWithStatusBar | 获取当前屏幕截图, 包含状态栏 ( 顶部灰色 TitleBar 高度, 没有设置 android:theme 的 NoTitleBar 时会显示 ) |
 * | snapshotWithoutStatusBar | 获取当前屏幕截图, 不包含状态栏 ( 如果 android:theme 全屏, 则截图无状态栏 ) |
 * | enableSlowWholeDocumentDraw | 关闭 WebView 优化 |
 * | snapshotByWebView | 截图 WebView |
 * | snapshotByView | 通过 View 绘制为 Bitmap |
 * | snapshotByViewCache | 通过 View Cache 绘制为 Bitmap |
 * | snapshotByLinearLayout | 通过 LinearLayout 绘制为 Bitmap |
 * | snapshotByFrameLayout | 通过 FrameLayout 绘制为 Bitmap |
 * | snapshotByRelativeLayout | 通过 RelativeLayout 绘制为 Bitmap |
 * | snapshotByScrollView | 通过 ScrollView 绘制为 Bitmap |
 * | snapshotByHorizontalScrollView | 通过 HorizontalScrollView 绘制为 Bitmap |
 * | snapshotByNestedScrollView | 通过 NestedScrollView 绘制为 Bitmap |
 * | snapshotByListView | 通过 ListView 绘制为 Bitmap |
 * | snapshotByGridView | 通过 GridView 绘制为 Bitmap |
 * | snapshotByRecyclerView | 通过 RecyclerView 绘制为 Bitmap |
 */
class CapturePictureFragment : AppFragment<FragmentUiEffectCapturePictureBinding, CapturePictureViewModel>(
    R.layout.fragment_ui_effect_capture_picture, BR.viewModel,
    simple_Agile = { frg ->
        frg.asFragment<CapturePictureFragment> {
            binding.setVariable(BR.linearLayout, binding.vidLl)
            binding.setVariable(BR.nestedScrollView, binding.vidNsv)
        }
    }
) {

    companion object {

        /**
         * 通用保存截图方法
         * @param fileName 文件名
         * @param bitmap 截图
         */
        fun saveBitmap(
            fileName: String,
            bitmap: Bitmap?
        ) {
            DevEngine.getStorage()?.insertImageToExternal(
                StorageItem.createExternalItem(fileName),
                DevSource.create(bitmap),
                object : OnDevInsertListener {
                    override fun onResult(
                        result: StorageResult,
                        params: StorageItem?,
                        source: DevSource?
                    ) {
                        if (result.isSuccess()) {
                            toast_showShort(
                                text = "保存成功\n${FileUtils.getAbsolutePath(result.getFile())}"
                            )
                        } else {
                            toast_showShort(text = "保存失败")
                        }
                    }
                }
            )
        }
    }
}

class CapturePictureViewModel : AppViewModel() {

    // 当前屏幕截图 ( 含状态栏 )
    val clickScreenBtn: (View) -> Unit = { view ->
        val activity = ActivityUtils.getActivity(view)
        saveBitmap(
            "screen_with_status_bar.jpg",
            CapturePictureUtils.snapshotWithStatusBar(activity)
        )
    }

    // 当前屏幕截图 ( 不含状态栏 )
    val clickScreen1Btn: (View) -> Unit = { view ->
        val activity = ActivityUtils.getActivity(view)
        saveBitmap(
            "screen_without_status_bar.jpg",
            CapturePictureUtils.snapshotWithoutStatusBar(activity)
        )
    }

    // LinearLayout 截图
    val clickLinearBtn: (LinearLayout?) -> Unit = { view ->
        // snapshotByLinearLayout、snapshotByFrameLayout、snapshotByRelativeLayout
        // 以上方法都是使用 snapshotByView
        saveBitmap(
            "linear_layout.jpg",
            CapturePictureUtils.snapshotByLinearLayout(view)
        )
    }

    // 当前屏幕截图 ( 不含状态栏 )
    val clickScrollBtn: (NestedScrollView?) -> Unit = { view ->
        // snapshotByScrollView、snapshotByHorizontalScrollView、snapshotByNestedScrollView
        saveBitmap(
            "scroll_view.jpg",
            CapturePictureUtils.snapshotByNestedScrollView(view)
        )
    }

    // ListView 截图
    val clickListViewBtn: (View) -> Unit = { view ->
        ButtonEnum.BTN_UI_EFFECT_CAPTURE_PICTURE_LIST_VIEW.click()
    }

    // RecyclerView 截图
    val clickRecyclerViewBtn: (View) -> Unit = { view ->
        ButtonEnum.BTN_UI_EFFECT_CAPTURE_PICTURE_RECYCLER_VIEW.click()
    }

    // Webview 截图
    val clickWebViewBtn: (View) -> Unit = { view ->
        ButtonEnum.BTN_UI_EFFECT_CAPTURE_PICTURE_WEB_VIEW.click()
    }
}