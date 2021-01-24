package afkt.project.ui.activity;

import android.net.http.SslError;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import afkt.project.R;
import afkt.project.base.BaseApplication;
import afkt.project.base.app.BaseActivity;
import afkt.project.databinding.ActivityWebviewBinding;
import dev.assist.WebViewAssist;
import dev.engine.log.DevLogEngine;

/**
 * detail: WebView 辅助类
 * @author Ttt
 */
public class WebViewActivity
        extends BaseActivity<ActivityWebviewBinding> {

    // WebView 辅助类
    private final WebViewAssist mWebViewAssist = new WebViewAssist();

    @Override
    public int baseLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    public void initValue() {
        super.initValue();
        // 长按监听事件
        binding.vidAwWebview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                WebView.HitTestResult result = ((WebView) view).getHitTestResult();
                if (result != null) {
                    switch (result.getType()) {
                        case WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE:
                            String imgUrl = result.getExtra();
                            DevLogEngine.getEngine().dTag(TAG, "SRC_IMAGE_ANCHOR_TYPE %s", imgUrl);
                            return true;
                    }
                }
                return false;
            }
        });

        // 设置 WebView
        mWebViewAssist.setWebView(binding.vidAwWebview);

        // 设置辅助 WebView 处理 Javascript 对话框、标题等对象
        mWebViewAssist.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(
                    WebView view,
                    int position
            ) {
                // 加载进度监听
                if (position == 100) { // 加载完成
                    DevLogEngine.getEngine().dTag(TAG, "加载完成");
                }
                super.onProgressChanged(view, position);
            }
        });
        // 设置处理各种通知和请求事件对象
        mWebViewAssist.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(
                    WebView view,
                    SslErrorHandler handler,
                    SslError error
            ) {
                handler.proceed(); // 接受所有网站的证书
                //handler.cancel(); // Android 默认的处理方式
                //handleMessage(Message msg); // 进行其他处理
            }

            @Override
            public boolean shouldOverrideUrlLoading(
                    WebView view,
                    String url
            ) {
                // 重定向 URL 请求, 返回 true 表示拦截此 url, 返回 false 表示不拦截此 url
                if (url.startsWith("weixin://")) {
                    url = url.replace("weixin://", "http://");
                    mWebViewAssist.loadUrl(url);
                    return true;
                }

                // 在本页面的 WebView 打开, 防止外部浏览器打开此链接
                mWebViewAssist.loadUrl(url);
                return true;
            }
        });

        /**
         * 默认使用全局配置
         * {@link BaseApplication#initWebViewBuilder}
         */
//        // 如果使用全局配置, 则直接调用 apply 方法
//        mWebViewAssist.apply();

        // 也可以传入自定义配置
        WebViewAssist.Builder builder = WebViewAssist.getGlobalBuilder().clone(true); // new WebViewAssist.Builder();
        builder.setBuiltInZoomControls(false).setDisplayZoomControls(false);
        // 当克隆方法 ( clone ) 传入 true 表示复用 OnApplyListener 但是想要能够添加其他配置处理则如下方法操作
        WebViewAssist.Builder.OnApplyListener applyListener = builder.getApplyListener();
        builder.setOnApplyListener(new WebViewAssist.Builder.OnApplyListener() {
            @Override
            public void onApply(
                    WebViewAssist webViewAssist,
                    WebViewAssist.Builder builder
            ) {
                if (applyListener != null) {
                    applyListener.onApply(webViewAssist, builder);
                }
                // BaseApplication 也会打印 WebViewAssist Builder onApply
                DevLogEngine.getEngine().dTag(TAG, "自定义监听");
                // 全局配置或者自定义配置以外, 再次配置操作
                // 加载网页
                mWebViewAssist.loadUrl("https://www.csdn.net/");
            }
        });

        // 设置配置并应用
        mWebViewAssist.setBuilder(builder, true);
    }

    @Override
    public boolean onKeyDown(
            int keyCode,
            KeyEvent event
    ) {
        if (mWebViewAssist.handlerKeyDown(keyCode, event)) return true;
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebViewAssist.destroy();
    }
}