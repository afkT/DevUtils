package dev.utils.app.helper;

import android.app.Activity;
import android.app.PendingIntent;
import android.net.Uri;

import java.io.File;
import java.util.Collection;

import dev.utils.app.AppUtils;
import dev.utils.app.ContentResolverUtils;
import dev.utils.app.MediaStoreUtils;
import dev.utils.app.PathUtils;
import dev.utils.app.UriUtils;

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

    /**
     * 判断 Uri 路径资源是否存在
     * <pre>
     *     uri 非 FilePath, 可通过 {@link UriUtils#getMediaUri} 获取
     * </pre>
     * @param uriString uri 路径
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isUriExists(final String uriString) {
        return UriUtils.isUriExists(uriString);
    }

    /**
     * 判断 Uri 路径资源是否存在
     * @param uri {@link Uri}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isUriExists(final Uri uri) {
        return UriUtils.isUriExists(uri);
    }

    /**
     * 通过 File 获取 Media Uri
     * @param file 文件
     * @return 指定文件 {@link Uri}
     */
    public static Uri getMediaUri(final File file) {
        return ContentResolverUtils.getMediaUri(file);
    }

    /**
     * 通过 File 获取 Media Uri
     * @param uri  MediaStore.media-type.Media.EXTERNAL_CONTENT_URI
     * @param file 文件
     * @return 指定文件 {@link Uri}
     */
    public static Uri getMediaUri(final Uri uri, final File file) {
        return ContentResolverUtils.getMediaUri(uri, file);
    }

    /**
     * 通过 File Path 获取 Media Uri
     * @param filePath 文件路径
     * @return 指定文件 {@link Uri}
     */
    public static Uri getMediaUri(final String filePath) {
        return ContentResolverUtils.getMediaUri(filePath);
    }

    /**
     * 通过 File Path 获取 Media Uri
     * @param uri      MediaStore.media-type.Media.EXTERNAL_CONTENT_URI
     * @param filePath 文件路径
     * @return 指定文件 {@link Uri}
     */
    public static Uri getMediaUri(final Uri uri, final String filePath) {
        return ContentResolverUtils.getMediaUri(uri, filePath);
    }

    /**
     * 通过 Uri 复制文件
     * @param uri {@link Uri}
     * @return 复制后的文件路径
     */
    public static String copyByUri(final Uri uri) {
        return UriUtils.copyByUri(uri);
    }

    /**
     * 通过 Uri 复制文件
     * @param uri      {@link Uri}
     * @param fileName 文件名 {@link ContentResolverUtils#getDisplayNameColumn}
     * @return 复制后的文件路径
     */
    public static String copyByUri(final Uri uri, final String fileName) {
        return UriUtils.copyByUri(uri, fileName);
    }

    /**
     * 通过 Uri 获取文件路径
     * @param uri {@link Uri}
     * @return 文件路径
     */
    public static String getFilePathByUri(final Uri uri) {
        return UriUtils.getFilePathByUri(uri);
    }

    /**
     * 通过 Uri 获取文件路径
     * <pre>
     *     默认不复制文件, 防止影响已经使用该方法的功能 ( 文件过大导致 ANR、耗时操作等 )
     * </pre>
     * @param uri     {@link Uri}
     * @param isQCopy Android Q 及其以上版本是否复制文件
     * @return 文件路径
     */
    public static String getFilePathByUri(final Uri uri, final boolean isQCopy) {
        return UriUtils.getFilePathByUri(uri, isQCopy);
    }


    /**
     * 获取 FileProvider File Uri
     * @param file 文件
     * @return 指定文件 {@link Uri}
     */
    public static Uri getUriForFile(final File file) {
        return UriUtils.getUriForFile(file);
    }

    /**
     * 获取 FileProvider File Path Uri
     * @param filePath 文件路径
     * @return 指定文件 {@link Uri}
     */
    public static Uri getUriForPath(final String filePath) {
        return UriUtils.getUriForPath(filePath);
    }

    /**
     * 获取 FileProvider File Path Uri ( 自动添加包名 ${applicationId} )
     * @param file         文件
     * @param fileProvider android:authorities = ${applicationId}.fileProvider
     * @return 指定文件 {@link Uri}
     */
    public static Uri getUriForFileToName(final File file, final String fileProvider) {
        return UriUtils.getUriForFileToName(file, fileProvider);
    }

    /**
     * 获取 FileProvider File Path Uri
     * @param file      文件
     * @param authority android:authorities
     * @return 指定文件 {@link Uri}
     */
    public static Uri getUriForFile(final File file, final String authority) {
        return UriUtils.getUriForFile(file, authority);
    }

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