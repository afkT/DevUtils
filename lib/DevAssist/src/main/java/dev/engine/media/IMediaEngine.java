package dev.engine.media;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import java.util.List;

/**
 * detail: Media Selector Engine 接口
 * @author Ttt
 */
public interface IMediaEngine<Config extends IMediaEngine.EngineConfig,
        Data extends IMediaEngine.MediaData> {

    /**
     * detail: Media Selector Config
     * @author Ttt
     */
    class EngineConfig {
    }

    /**
     * detail: Media Selector Data
     * @author Ttt
     */
    class MediaData {
    }

    // ===============
    // = 对外公开方法 =
    // ===============

    /**
     * 打开相册拍照
     * @param activity {@link Activity}
     * @return {@code true} success, {@code false} fail
     */
    boolean openCamera(Activity activity);

    /**
     * 打开相册拍照
     * @param activity {@link Activity}
     * @param config   配置信息
     * @return {@code true} success, {@code false} fail
     */
    boolean openCamera(
            Activity activity,
            Config config
    );

    /**
     * 打开相册拍照
     * @param fragment {@link Fragment}
     * @return {@code true} success, {@code false} fail
     */
    boolean openCamera(Fragment fragment);

    /**
     * 打开相册拍照
     * @param fragment {@link Fragment}
     * @param config   配置信息
     * @return {@code true} success, {@code false} fail
     */
    boolean openCamera(
            Fragment fragment,
            Config config
    );

    // =

    /**
     * 打开相册选择
     * @param activity {@link Activity}
     * @return {@code true} success, {@code false} fail
     */
    boolean openGallery(Activity activity);

    /**
     * 打开相册选择
     * @param activity {@link Activity}
     * @param config   配置信息
     * @return {@code true} success, {@code false} fail
     */
    boolean openGallery(
            Activity activity,
            Config config
    );

    /**
     * 打开相册选择
     * @param fragment {@link Fragment}
     * @return {@code true} success, {@code false} fail
     */
    boolean openGallery(Fragment fragment);

    /**
     * 打开相册选择
     * @param fragment {@link Fragment}
     * @param config   配置信息
     * @return {@code true} success, {@code false} fail
     */
    boolean openGallery(
            Fragment fragment,
            Config config
    );

    // ===========
    // = 配置方法 =
    // ===========

    /**
     * 获取全局配置
     * @return 全局配置信息
     */
    Config getConfig();

    /**
     * 设置全局配置
     * @param config 新的配置信息
     */
    void setConfig(Config config);

    /**
     * 获取拍照保存地址
     * @return 拍照保存地址
     */
    String getCameraSavePath();

    /**
     * 获取压缩图片保存地址
     * @return 压缩图片保存地址
     */
    String getCompressSavePath();

    /**
     * 设置保存地址
     * @param cameraSavePath   拍照保存地址
     * @param compressSavePath 压缩图片保存地址
     */
    void setSavePath(
            String cameraSavePath,
            String compressSavePath
    );

    /**
     * 获取图片大于多少才进行压缩 (kb)
     * @return 最小压缩大小
     */
    int getMinimumCompressSize();

    /**
     * 设置图片大于多少才进行压缩 (kb)
     * @param minimumCompressSize 最小压缩大小
     */
    void setMinimumCompressSize(int minimumCompressSize);

    // ===========
    // = 其他方法 =
    // ===========

    /**
     * 删除缓存文件
     * @param context {@link Context}
     * @param type    类型 ( 图片、视频 )
     */
    void deleteCacheDirFile(
            Context context,
            int type
    );

    /**
     * 删除全部缓存文件
     * @param context {@link Context}
     */
    void deleteAllCacheDirFile(Context context);

    /**
     * 是否图片选择 ( onActivityResult )
     * @param requestCode 请求 code
     * @param resultCode  resultCode
     * @return {@code true} success, {@code false} fail
     */
    boolean isMediaSelectorResult(
            int requestCode,
            int resultCode
    );

    // =

    /**
     * 获取 Media Selector Data List
     * @param data onActivityResult Intent data
     * @return Media Selector Data List
     */
    List<Data> getSelectors(Intent data);

    /**
     * 获取 Media Selector Path List
     * @param data     onActivityResult Intent data
     * @param original 是否使用原图地址
     * @return Media Selector Path List
     */
    List<String> getSelectorPaths(
            Intent data,
            boolean original
    );

    /**
     * 获取 Single Media Selector Data
     * @param data onActivityResult Intent data
     * @return Single Media Selector Data
     */
    Data getSingleSelector(Intent data);

    /**
     * 获取 Single Media Selector Path
     * @param data     onActivityResult Intent data
     * @param original 是否使用原图地址
     * @return Single Media Selector Path
     */
    String getSingleSelectorPath(
            Intent data,
            boolean original
    );
}