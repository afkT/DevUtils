package afkt.project.ui.activity;



import android.graphics.Bitmap;
import android.view.View;
import android.widget.LinearLayout;

import afkt.project.R;
import afkt.project.base.app.BaseToolbarActivity;
import afkt.project.base.config.PathConfig;
import afkt.project.model.item.ButtonValue;
import afkt.project.util.SkipUtils;
import butterknife.BindView;
import butterknife.OnClick;
import dev.utils.app.CapturePictureUtils;
import dev.utils.app.image.ImageUtils;
import dev.widget.CustomNestedScrollView;

/**
 * detail: CapturePictureUtils 截图工具类
 * @author Ttt
 */
public class CapturePictureActivity extends BaseToolbarActivity {

    // = View =
    @BindView(R.id.vid_acp_scroll)
    CustomNestedScrollView vid_acp_scroll;
    @BindView(R.id.vid_acp_linear)
    LinearLayout vid_acp_linear;

    @Override
    public int getLayoutId() {
        return R.layout.activity_capture_picture;
    }

    @Override
    public void initValues() {
        super.initValues();

        // ============
        // = 截图方法 =
        // ============

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

    @OnClick({R.id.vid_acp_screen_btn, R.id.vid_acp_screen1_btn,
            R.id.vid_acp_linear_btn, R.id.vid_acp_scroll_btn, R.id.vid_acp_list_btn,
            R.id.vid_acp_grid_btn, R.id.vid_acp_recy_btn, R.id.vid_acp_webview_btn})
    @Override
    public void onClick(View v) {
        super.onClick(v);

        Bitmap bitmap;
        boolean result;
        String filePath = PathConfig.SDP_DOWN_IMAGE_PATH;
        String fileName;

        switch (v.getId()) {
            case R.id.vid_acp_screen_btn:
                fileName = "screen.jpg";
                bitmap = CapturePictureUtils.snapshotWithStatusBar(CapturePictureActivity.this);
                result = ImageUtils.saveBitmapToSDCardJPEG(bitmap, filePath + fileName);
                showToast(result, "保存成功\n" + (filePath + fileName), "保存失败");
                break;
            case R.id.vid_acp_screen1_btn:
                fileName = "screen1.jpg";
                bitmap = CapturePictureUtils.snapshotWithoutStatusBar(CapturePictureActivity.this);
                result = ImageUtils.saveBitmapToSDCardJPEG(bitmap, filePath + fileName);
                showToast(result, "保存成功\n" + (filePath + fileName), "保存失败");
                break;
            case R.id.vid_acp_linear_btn:
                // snapshotByLinearLayout、snapshotByFrameLayout、snapshotByRelativeLayout
                // 以上方法都是使用 snapshotByView
                fileName = "linear.jpg";
                bitmap = CapturePictureUtils.snapshotByLinearLayout(vid_acp_linear);
                result = ImageUtils.saveBitmapToSDCardJPEG(bitmap, filePath + fileName);
                showToast(result, "保存成功\n" + (filePath + fileName), "保存失败");
                break;
            case R.id.vid_acp_scroll_btn:
                // snapshotByScrollView、snapshotByHorizontalScrollView、snapshotByNestedScrollView
                fileName = "scroll.jpg";
                bitmap = CapturePictureUtils.snapshotByNestedScrollView(vid_acp_scroll);
                result = ImageUtils.saveBitmapToSDCardJPEG(bitmap, filePath + fileName);
                showToast(result, "保存成功\n" + (filePath + fileName), "保存失败");
                break;
            case R.id.vid_acp_list_btn:
                SkipUtils.startActivity(CapturePictureListActivity.class, new ButtonValue(1, "CapturePictureUtils ListView 截图"));
                break;
            case R.id.vid_acp_grid_btn:
                break;
            case R.id.vid_acp_recy_btn:
                break;
            case R.id.vid_acp_webview_btn:
                break;
        }
    }
}