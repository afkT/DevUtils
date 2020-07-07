package afkt.project.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import afkt.project.R;
import afkt.project.base.app.BaseToolbarActivity;
import afkt.project.base.config.PathConfig;
import afkt.project.ui.widget.BaseTextView;
import butterknife.BindView;
import dev.utils.app.CapturePictureUtils;
import dev.utils.app.ResourceUtils;
import dev.utils.app.helper.ViewHelper;
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
        BaseTextView baseTextView = new BaseTextView(this);
        ViewHelper.get().setText(baseTextView, "截图").setBold(baseTextView)
                .setTextColor(baseTextView, ResourceUtils.getColor(R.color.white))
                .setTextSizeBySp(baseTextView, 15.0f)
                .setPaddingLeft(baseTextView, 30)
                .setPaddingRight(baseTextView, 30)
                .setOnClicks(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String filePath = PathConfig.AEP_DOWN_IMAGE_PATH;
                        String fileName = "web.jpg";

                        Bitmap bitmap = CapturePictureUtils.snapshotByWebView(vid_acp_webview);
                        boolean result = ImageUtils.saveBitmapToSDCardJPEG(bitmap, filePath + fileName);
                        showToast(result, "保存成功\n" + (filePath + fileName), "保存失败");
                    }
                }, baseTextView);
        vid_bt_toolbar.addView(baseTextView);
    }

    @Override
    public void initValues() {
        super.initValues();
        // 加载网页
        vid_acp_webview.loadUrl("https://www.csdn.net/");
    }
}