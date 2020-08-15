package afkt.project.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import afkt.project.R;
import afkt.project.base.app.BaseToolbarActivity;
import afkt.project.base.config.PathConfig;
import butterknife.BindView;
import dev.base.widget.BaseTextView;
import dev.utils.app.CapturePictureUtils;
import dev.utils.app.ResourceUtils;
import dev.utils.app.helper.QuickHelper;
import dev.utils.app.image.ImageUtils;

/**
 * detail: CapturePictureUtils WebView 截图
 * @author Ttt
 */
public class CapturePictureWebActivity extends BaseToolbarActivity {

    // = View =
    @BindView(R.id.vid_acp_webview)
    WebView vid_acp_webview;

    @Override
    public int getLayoutId() {
        return R.layout.activity_capture_picture_web;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 关闭 WebView 优化
        CapturePictureUtils.enableSlowWholeDocumentDraw();
        super.onCreate(savedInstanceState);
        // 截图按钮
        View view = QuickHelper.get(new BaseTextView(this))
                .setText("截图")
                .setBold()
                .setTextColor(ResourceUtils.getColor(R.color.white))
                .setTextSizeBySp(15.0f)
                .setPaddingLeft(30)
                .setPaddingRight(30)
                .setOnClicks(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String filePath = PathConfig.AEP_DOWN_IMAGE_PATH;
                        String fileName = "web.jpg";

                        Bitmap bitmap = CapturePictureUtils.snapshotByWebView(vid_acp_webview);
                        boolean result = ImageUtils.saveBitmapToSDCardJPEG(bitmap, filePath + fileName);
                        showToast(result, "保存成功\n" + (filePath + fileName), "保存失败");
                    }
                }).getView();
        vid_bt_toolbar.addView(view);
    }

    @Override
    public void initValue() {
        super.initValue();
        // 加载网页
        vid_acp_webview.loadUrl("https://www.csdn.net/");
    }
}