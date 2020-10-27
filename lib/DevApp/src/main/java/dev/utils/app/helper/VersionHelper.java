package dev.utils.app.helper;

import android.app.Activity;
import android.app.PendingIntent;
import android.net.Uri;

import java.util.Collection;

import dev.utils.app.AppUtils;
import dev.utils.app.MediaStoreUtils;
import dev.utils.app.PathUtils;

/**
 * detail: Android 版本适配 Helper 类
 * @author Ttt
 * <pre>
 *     Android 版本适配 Helper 类, 方便快捷使用
 *     并简化需多工具类组合使用的功能
 *     关于路径建议及兼容建议查看 {@link PathUtils}
 *     <p></p>
 *     Android 10 更新内容
 *     @see <a href="https://developer.android.google.cn/about/versions/10"/>
 *     Android 11 更新内容
 *     @see <a href="https://developer.android.google.cn/about/versions/11"/>
 *     <p></p>
 *     Android Q 适配指南
 *     @see <a href="https://juejin.im/post/5ddd2417f265da060a5217ff"/>
 *     Android 11 最全适配实践指南
 *     @see <a href="https://mp.weixin.qq.com/s/ZrsO5VvURwW98PTHei0kFA"/>
 *     MANAGE_EXTERNAL_STORAGE
 *     @see <a href="https://developer.android.google.cn/preview/privacy/storage"/>
 * </pre>
 */
public final class VersionHelper {

    private VersionHelper() {
    }

    // 日志 TAG
    private static final String TAG = VersionHelper.class.getSimpleName();

    // =============
    // = Android Q =
    // =============



    // =============
    // = Android R =
    // =============

    /**
     * 是否获得 MANAGE_EXTERNAL_STORAGE 权限
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isExternalStorageManager() {
        return PathUtils.isExternalStorageManager();
    }

    /**
     * 检查是否有 MANAGE_EXTERNAL_STORAGE 权限并跳转设置页面
     * @return {@code true} yes, {@code false} no
     */
    public static boolean checkExternalStorageAndIntentSetting() {
        return PathUtils.checkExternalStorageAndIntentSetting();
    }

    /**
     * 获取用户向应用授予对指定媒体文件组的写入访问权限的请求
     * <pre>
     *     以下四个方法搭配 startIntentSenderForResult() 使用
     *     {@link AppUtils#startIntentSenderForResult(Activity, PendingIntent, int)}
     *     <p></p>
     *     startIntentSenderForResult(pendingIntent.getIntentSender(), EDIT_REQUEST_CODE, null, 0, 0, 0)
     * </pre>
     * @param uris 待请求 Uri 集
     * @return {@link PendingIntent}
     */
    public static PendingIntent createWriteRequest(final Collection<Uri> uris) {
        return MediaStoreUtils.createWriteRequest(uris);
    }

    /**
     * 获取用户将设备上指定的媒体文件标记为收藏的请求
     * <pre>
     *     对该文件具有读取访问权限的任何应用都可以看到用户已将该文件标记为收藏
     * </pre>
     * @param uris     待请求 Uri 集
     * @param favorite 是否喜欢
     * @return {@link PendingIntent}
     */
    public static PendingIntent createFavoriteRequest(final Collection<Uri> uris, final boolean favorite) {
        return MediaStoreUtils.createFavoriteRequest(uris, favorite);
    }

    /**
     * 获取用户将指定的媒体文件放入设备垃圾箱的请求
     * <pre>
     *     垃圾箱中的内容会在系统定义的时间段后被永久删除
     * </pre>
     * @param uris    待请求 Uri 集
     * @param trashed 是否遗弃
     * @return {@link PendingIntent}
     */
    public static PendingIntent createTrashRequest(final Collection<Uri> uris, final boolean trashed) {
        return MediaStoreUtils.createTrashRequest(uris, trashed);
    }

    /**
     * 获取用户立即永久删除指定的媒体文件 ( 而不是先将其放入垃圾箱 ) 的请求
     * @param uris 待请求 Uri 集
     * @return {@link PendingIntent}
     */
    public static PendingIntent createDeleteRequest(final Collection<Uri> uris) {
        return MediaStoreUtils.createDeleteRequest(uris);
    }
}