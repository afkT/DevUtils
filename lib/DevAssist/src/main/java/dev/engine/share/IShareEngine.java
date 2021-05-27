package dev.engine.share;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;

import dev.engine.share.listener.ShareListener;

/**
 * detail: Share Engine 接口
 * @author Ttt
 */
public interface IShareEngine<Config extends IShareEngine.EngineConfig,
        Item extends IShareEngine.EngineItem> {

    /**
     * detail: Share Config
     * @author Ttt
     */
    class EngineConfig {
    }

    /**
     * detail: Share Item
     * @author Ttt
     */
    class EngineItem {
    }

    // ===============
    // = 对外公开方法 =
    // ===============

    /**
     * 初始化方法
     * @param application {@link Application}
     * @param config      {@link EngineConfig}
     */
    void initialize(
            Application application,
            Config config
    );

    // =

    /**
     * 打开小程序
     * @param activity {@link Activity}
     * @param params   {@link EngineItem}
     * @return {@code true} success, {@code false} fail
     */
    boolean openMinApp(
            Activity activity,
            Item params
    );

    /**
     * 分享小程序
     * @param activity {@link Activity}
     * @param params   {@link EngineItem}
     * @param listener {@link ShareListener}
     * @return {@code true} success, {@code false} fail
     */
    boolean shareMinApp(
            Activity activity,
            Item params,
            ShareListener<Item> listener
    );

    /**
     * 分享链接
     * @param activity {@link Activity}
     * @param params   {@link EngineItem}
     * @param listener {@link ShareListener}
     * @return {@code true} success, {@code false} fail
     */
    boolean shareUrl(
            Activity activity,
            Item params,
            ShareListener<Item> listener
    );

    /**
     * 分享图片
     * @param activity {@link Activity}
     * @param params   {@link EngineItem}
     * @param listener {@link ShareListener}
     * @return {@code true} success, {@code false} fail
     */
    boolean shareImage(
            Activity activity,
            Item params,
            ShareListener<Item> listener
    );

    /**
     * 分享视频
     * @param activity {@link Activity}
     * @param params   {@link EngineItem}
     * @param listener {@link ShareListener}
     * @return {@code true} success, {@code false} fail
     */
    boolean shareVideo(
            Activity activity,
            Item params,
            ShareListener<Item> listener
    );

    /**
     * 分享音乐
     * @param activity {@link Activity}
     * @param params   {@link EngineItem}
     * @param listener {@link ShareListener}
     * @return {@code true} success, {@code false} fail
     */
    boolean shareMusic(
            Activity activity,
            Item params,
            ShareListener<Item> listener
    );

    /**
     * 分享表情
     * @param activity {@link Activity}
     * @param params   {@link EngineItem}
     * @param listener {@link ShareListener}
     * @return {@code true} success, {@code false} fail
     */
    boolean shareEmoji(
            Activity activity,
            Item params,
            ShareListener<Item> listener
    );

    /**
     * 分享文本
     * @param activity {@link Activity}
     * @param params   {@link EngineItem}
     * @param listener {@link ShareListener}
     * @return {@code true} success, {@code false} fail
     */
    boolean shareText(
            Activity activity,
            Item params,
            ShareListener<Item> listener
    );

    /**
     * 分享文件
     * @param activity {@link Activity}
     * @param params   {@link EngineItem}
     * @param listener {@link ShareListener}
     * @return {@code true} success, {@code false} fail
     */
    boolean shareFile(
            Activity activity,
            Item params,
            ShareListener<Item> listener
    );

    /**
     * 分享操作 ( 通用扩展 )
     * @param activity {@link Activity}
     * @param params   {@link EngineItem}
     * @param listener {@link ShareListener}
     * @return {@code true} success, {@code false} fail
     */
    boolean share(
            Activity activity,
            Item params,
            ShareListener<Item> listener
    );

    // =

    /**
     * 部分平台 Activity onActivityResult 额外调用处理
     * @param context     {@link Context}
     * @param requestCode 请求 code
     * @param resultCode  resultCode
     * @param data        {@link Intent}
     */
    void onActivityResult(
            Context context,
            int requestCode,
            int resultCode,
            Intent data
    );
}