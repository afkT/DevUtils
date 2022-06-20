package java.dev.other;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import java.dev.engine.media.LocalMediaData;
import java.dev.engine.media.MediaConfig;
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

    /**
     * 获取拍照存储地址
     * @return 拍照存储地址
     */
    public static String getCameraSavePath() {
        return IMPL.getCameraSavePath();
    }

    /**
     * 获取压缩图片存储地址
     * @return 压缩图片存储地址
     */
    public static String getCompressSavePath() {
        return IMPL.getCompressSavePath();
    }

    /**
     * 设置存储地址
     * @param cameraSavePath   拍照存储地址
     * @param compressSavePath 压缩图片存储地址
     */
    public static void setSavePath(
            String cameraSavePath,
            String compressSavePath
    ) {
        IMPL.setSavePath(cameraSavePath, compressSavePath);
    }

    /**
     * 获取图片大于多少才进行压缩 ( kb )
     * @return 最小压缩大小
     */
    public static int getMinimumCompressSize() {
        return IMPL.getMinimumCompressSize();
    }

    /**
     * 设置图片大于多少才进行压缩 ( kb )
     * @param minimumCompressSize 最小压缩大小
     */
    public static void setMinimumCompressSize(int minimumCompressSize) {
        IMPL.setMinimumCompressSize(minimumCompressSize);
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
    public static List<LocalMediaData> getSelectors(Intent intent) {
        return IMPL.getSelectors(intent);
    }

    /**
     * 获取 Media Selector Path List
     * @param intent   onActivityResult Intent data
     * @param original 是否使用原图地址
     * @return Media Selector Path List
     */
    public static List<String> getSelectorPaths(
            Intent intent,
            boolean original
    ) {
        return IMPL.getSelectorPaths(intent, original);
    }

    /**
     * 获取 Single Media Selector Data
     * @param intent onActivityResult Intent data
     * @return Single Media Selector Data
     */
    public static LocalMediaData getSingleSelector(Intent intent) {
        return IMPL.getSingleSelector(intent);
    }

    /**
     * 获取 Single Media Selector Path
     * @param intent   onActivityResult Intent data
     * @param original 是否使用原图地址
     * @return Single Media Selector Path
     */
    public static String getSingleSelectorPath(
            Intent intent,
            boolean original
    ) {
        return IMPL.getSingleSelectorPath(intent, original);
    }
}