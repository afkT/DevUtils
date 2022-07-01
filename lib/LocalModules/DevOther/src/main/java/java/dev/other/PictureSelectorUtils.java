package java.dev.other;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.fragment.app.Fragment;

import java.dev.engine.media.MediaConfig;
import java.dev.engine.media.MediaData;
import java.dev.engine.media.PictureSelectorEngineImpl;
import java.util.List;

/**
 * detail: Android 平台下的图片选择器
 * @author Ttt
 * @deprecated 推荐使用 DevMediaEngine 实现类 PictureSelectorEngineImpl
 */
@Deprecated
public final class PictureSelectorUtils {

    private PictureSelectorUtils() {
    }

    private static final PictureSelectorEngineImpl IMPL = new PictureSelectorEngineImpl();

    // ==========
    // = 配置方法 =
    // ==========

    /**
     * 获取全局配置
     * @return 全局配置信息
     */
    public static MediaConfig getConfig() {
        return IMPL.getConfig();
    }

    /**
     * 设置全局配置
     * @param config 新的配置信息
     */
    public static void setConfig(MediaConfig config) {
        IMPL.setConfig(config);
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 打开相册拍照
     * @param activity {@link Activity}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean openCamera(Activity activity) {
        return IMPL.openCamera(activity);
    }

    /**
     * 打开相册拍照
     * @param activity {@link Activity}
     * @param config   配置信息
     * @return {@code true} success, {@code false} fail
     */
    public static boolean openCamera(
            Activity activity,
            MediaConfig config
    ) {
        return IMPL.openCamera(activity, config);
    }

    /**
     * 打开相册拍照
     * @param fragment {@link Fragment}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean openCamera(Fragment fragment) {
        return IMPL.openCamera(fragment);
    }

    /**
     * 打开相册拍照
     * @param fragment {@link Fragment}
     * @param config   配置信息
     * @return {@code true} success, {@code false} fail
     */
    public static boolean openCamera(
            Fragment fragment,
            MediaConfig config
    ) {
        return IMPL.openCamera(fragment, config);
    }

    // =

    /**
     * 打开相册选择
     * @param activity {@link Activity}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean openGallery(Activity activity) {
        return IMPL.openGallery(activity);
    }

    /**
     * 打开相册选择
     * @param activity {@link Activity}
     * @param config   配置信息
     * @return {@code true} success, {@code false} fail
     */
    public static boolean openGallery(
            Activity activity,
            MediaConfig config
    ) {
        return IMPL.openGallery(activity, config);
    }

    /**
     * 打开相册选择
     * @param fragment {@link Fragment}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean openGallery(Fragment fragment) {
        return IMPL.openGallery(fragment);
    }

    /**
     * 打开相册选择
     * @param fragment {@link Fragment}
     * @param config   配置信息
     * @return {@code true} success, {@code false} fail
     */
    public static boolean openGallery(
            Fragment fragment,
            MediaConfig config
    ) {
        return IMPL.openGallery(fragment, config);
    }

    // =

    /**
     * 打开相册预览
     * @param activity {@link Activity}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean openPreview(Activity activity) {
        return IMPL.openPreview(activity);
    }

    /**
     * 打开相册预览
     * @param activity {@link Activity}
     * @param config   配置信息
     * @return {@code true} success, {@code false} fail
     */
    public static boolean openPreview(
            Activity activity,
            MediaConfig config
    ) {
        return IMPL.openPreview(activity, config);
    }

    /**
     * 打开相册预览
     * @param fragment {@link Fragment}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean openPreview(Fragment fragment) {
        return IMPL.openPreview(fragment);
    }

    /**
     * 打开相册预览
     * @param fragment {@link Fragment}
     * @param config   配置信息
     * @return {@code true} success, {@code false} fail
     */
    public static boolean openPreview(
            Fragment fragment,
            MediaConfig config
    ) {
        return IMPL.openPreview(fragment, config);
    }

    // ==========
    // = 其他方法 =
    // ==========

    /**
     * 删除缓存文件
     * @param context {@link Context}
     * @param type    类型 ( 图片、视频 )
     */
    public static void deleteCacheDirFile(
            Context context,
            int type
    ) {
        IMPL.deleteCacheDirFile(context, type);
    }

    /**
     * 删除全部缓存文件
     * @param context {@link Context}
     */
    public static void deleteAllCacheDirFile(Context context) {
        IMPL.deleteAllCacheDirFile(context);
    }

    /**
     * 是否图片选择 ( onActivityResult )
     * @param requestCode 请求 code
     * @param resultCode  resultCode
     * @return {@code true} success, {@code false} fail
     */
    public static boolean isMediaSelectorResult(
            int requestCode,
            int resultCode
    ) {
        return IMPL.isMediaSelectorResult(requestCode, resultCode);
    }

    // =

    /**
     * 获取 Media Selector Data List
     * @param intent onActivityResult Intent data
     * @return Media Selector Data List
     */
    public static List<MediaData> getSelectors(Intent intent) {
        return IMPL.getSelectors(intent);
    }

    /**
     * 获取 Media Selector Uri List
     * @param intent   onActivityResult Intent data
     * @param original 是否使用原图
     * @return Media Selector Path List
     */
    public static List<Uri> getSelectorUris(
            Intent intent,
            boolean original
    ) {
        return IMPL.getSelectorUris(intent, original);
    }

    /**
     * 获取 Single Media Selector Data
     * @param intent onActivityResult Intent data
     * @return Single Media Selector Data
     */
    public static MediaData getSingleSelector(Intent intent) {
        return IMPL.getSingleSelector(intent);
    }

    /**
     * 获取 Single Media Selector Uri
     * @param intent   onActivityResult Intent data
     * @param original 是否使用原图
     * @return Single Media Selector Path
     */
    public static Uri getSingleSelectorUri(
            Intent intent,
            boolean original
    ) {
        return IMPL.getSingleSelectorUri(intent, original);
    }
}