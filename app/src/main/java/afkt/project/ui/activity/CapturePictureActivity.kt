package afkt.project.ui.activity

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.base.config.PathConfig
import afkt.project.databinding.ActivityCapturePictureBinding
import afkt.project.model.item.ButtonValue
import afkt.project.util.SkipUtils.startActivity
import android.graphics.Bitmap
import android.view.View
import dev.utils.app.CapturePictureUtils
import dev.utils.app.ListenerUtils
import dev.utils.app.image.ImageUtils

/**
 * detail: CapturePictureUtils 截图工具类
 * @author Ttt
 */
class CapturePictureActivity : BaseActivity<ActivityCapturePictureBinding>() {

    override fun baseLayoutId(): Int = R.layout.activity_capture_picture

    override fun initValue() {
        super.initValue()

        // ===========
        // = 截图方法 =
        // ===========

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
            binding.vidAcpScreenBtn, binding.vidAcpScreen1Btn,
            binding.vidAcpLinearBtn, binding.vidAcpScrollBtn,
            binding.vidAcpListBtn, binding.vidAcpGridBtn,
            binding.vidAcpRecyBtn, binding.vidAcpWebviewBtn
        )
    }

    override fun onClick(v: View) {
        super.onClick(v)
        val bitmap: Bitmap
        val result: Boolean
        val filePath = PathConfig.AEP_DOWN_IMAGE_PATH
        val fileName: String
        when (v.id) {
            R.id.vid_acp_screen_btn -> {
                fileName = "screen.jpg"
                bitmap = CapturePictureUtils.snapshotWithStatusBar(mActivity)
                result = ImageUtils.saveBitmapToSDCardJPEG(bitmap, filePath + fileName)
                showToast(result, "保存成功\n" + (filePath + fileName), "保存失败")
            }
            R.id.vid_acp_screen1_btn -> {
                fileName = "screen1.jpg"
                bitmap = CapturePictureUtils.snapshotWithoutStatusBar(mActivity)
                result = ImageUtils.saveBitmapToSDCardJPEG(bitmap, filePath + fileName)
                showToast(result, "保存成功\n" + (filePath + fileName), "保存失败")
            }
            R.id.vid_acp_linear_btn -> {
                // snapshotByLinearLayout、snapshotByFrameLayout、snapshotByRelativeLayout
                // 以上方法都是使用 snapshotByView
                fileName = "linear.jpg"
                bitmap = CapturePictureUtils.snapshotByLinearLayout(binding.vidAcpLinear)
                result = ImageUtils.saveBitmapToSDCardJPEG(bitmap, filePath + fileName)
                showToast(result, "保存成功\n" + (filePath + fileName), "保存失败")
            }
            R.id.vid_acp_scroll_btn -> {
                // snapshotByScrollView、snapshotByHorizontalScrollView、snapshotByNestedScrollView
                fileName = "scroll.jpg"
                bitmap = CapturePictureUtils.snapshotByNestedScrollView(binding.vidAcpScroll)
                result = ImageUtils.saveBitmapToSDCardJPEG(bitmap, filePath + fileName)
                showToast(result, "保存成功\n" + (filePath + fileName), "保存失败")
            }
            R.id.vid_acp_list_btn -> {
                startActivity(
                    CapturePictureListActivity::class.java,
                    ButtonValue(1, "CapturePictureUtils ListView 截图")
                )
            }
            R.id.vid_acp_grid_btn -> {
                startActivity(
                    CapturePictureGridActivity::class.java,
                    ButtonValue(2, "CapturePictureUtils GridView 截图")
                )
            }
            R.id.vid_acp_recy_btn -> {
                startActivity(
                    CapturePictureRecyActivity::class.java,
                    ButtonValue(3, "CapturePictureUtils RecyclerView 截图")
                )
            }
            R.id.vid_acp_webview_btn -> {
                startActivity(
                    CapturePictureWebActivity::class.java,
                    ButtonValue(4, "CapturePictureUtils WebView 截图")
                )
            }
        }
    }
}