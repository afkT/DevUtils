package afkt.project.features.ui_effect.capture_picture

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.ActivityCapturePictureBinding
import android.graphics.Bitmap
import android.view.View
import dev.base.DevSource
import dev.engine.DevEngine
import dev.engine.storage.OnDevInsertListener
import dev.engine.storage.StorageItem
import dev.engine.storage.StorageResult
import dev.expand.engine.toast.toast_showShort
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
class CapturePictureFragment : AppFragment<ActivityCapturePictureBinding, AppViewModel>(
    R.layout.activity_capture_picture, BR.viewModel,
    simple_Agile = {
    }
) {

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

//            R.id.vid_list_btn -> {
//                ButtonValue(
//                    1, "CapturePictureUtils ListView 截图", ""
//                ).routerActivity()
//            }
//
//            R.id.vid_recy_btn -> {
//                ButtonValue(
//                    3, "CapturePictureUtils RecyclerView 截图", ""
//                ).routerActivity()
//            }
//
//            R.id.vid_webview_btn -> {
//                ButtonValue(
//                    4, "CapturePictureUtils WebView 截图", ""
//                ).routerActivity()
//            }
        }
    }

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