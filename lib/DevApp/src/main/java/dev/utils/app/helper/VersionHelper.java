package dev.utils.app.helper;

import android.app.Activity;
import android.app.PendingIntent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import androidx.annotation.IntRange;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;

import dev.utils.app.AppUtils;
import dev.utils.app.ContentResolverUtils;
import dev.utils.app.IntentUtils;
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
 *     <p></p>
 *     在 Android Q 上想要公开的资源, 通过 Image、Video、Audio、Document
 *     {@link #createImageUri()}、{@link #createVideoUri()}、
 *     {@link #createAudioUri()}、{@link IntentUtils#getCreateDocumentIntent(String, String)}
 *     去创建 Uri 并写入数据
 *     {@link #insertImage(Uri, Uri)} ()}、{@link #insertVideo(Uri, Uri)} ()}、
 *     {@link #insertAudio(Uri, Uri)} ()}、{@link #insertMedia(Uri, Uri)} ()}
 *     关于参数:
 *     inputUri 输入 Uri ( 待存储文件 Uri )
 *     通过 {@link #getUriForFile(File)} 把需要存储到外部的文件传入
 *     <p></p>
 *     获取文件通过 {@link IntentUtils#getOpenDocumentIntent()} startActivityForResult 获取选择的 Uri
 *     如果需要把选中的文件进行存储调用 {@link #copyByUri(Uri)}
 * </pre>
 */
public final class VersionHelper {

    private VersionHelper() {
    }

    // =============
    // = Android Q =
    // =============

    // ============
    // = UriUtils =
    // ============

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
    public static Uri getMediaUri(
            final Uri uri,
            final File file
    ) {
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
    public static Uri getMediaUri(
            final Uri uri,
            final String filePath
    ) {
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
    public static String copyByUri(
            final Uri uri,
            final String fileName
    ) {
        return UriUtils.copyByUri(uri, fileName);
    }

    /**
     * 通过 Uri 复制文件
     * @param uri      {@link Uri}
     * @param file     文件
     * @param fileName 文件名 {@link ContentResolverUtils#getDisplayNameColumn}
     * @return 复制后的文件路径
     */
    public static String copyByUri(
            final Uri uri,
            final File file,
            final String fileName
    ) {
        return UriUtils.copyByUri(uri, file, fileName);
    }

    /**
     * 通过 Uri 复制文件
     * @param uri      {@link Uri}
     * @param filePath 文件路径
     * @param fileName 文件名 {@link ContentResolverUtils#getDisplayNameColumn}
     * @return 复制后的文件路径
     */
    public static String copyByUri(
            final Uri uri,
            final String filePath,
            final String fileName
    ) {
        return UriUtils.copyByUri(uri, filePath, fileName);
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
    public static String getFilePathByUri(
            final Uri uri,
            final boolean isQCopy
    ) {
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
    public static Uri getUriForFileToName(
            final File file,
            final String fileProvider
    ) {
        return UriUtils.getUriForFileToName(file, fileProvider);
    }

    /**
     * 获取 FileProvider File Path Uri
     * @param file      文件
     * @param authority android:authorities
     * @return 指定文件 {@link Uri}
     */
    public static Uri getUriForFile(
            final File file,
            final String authority
    ) {
        return UriUtils.getUriForFile(file, authority);
    }

    // ===================
    // = MediaStoreUtils =
    // ===================

    // ========
    // = 图片 =
    // ========

    /**
     * 创建图片 Uri
     * @return 图片 Uri
     */
    public static Uri createImageUri() {
        return MediaStoreUtils.createImageUri();
    }

    /**
     * 创建图片 Uri
     * @param mimeType 资源类型
     * @return 图片 Uri
     */
    public static Uri createImageUri(final String mimeType) {
        return MediaStoreUtils.createImageUri(mimeType);
    }

    /**
     * 创建图片 Uri
     * @param mimeType     资源类型
     * @param relativePath 存储目录 ( 如 DCIM、Video、Pictures )
     * @return 图片 Uri
     */
    public static Uri createImageUri(
            final String mimeType,
            final String relativePath
    ) {
        return MediaStoreUtils.createImageUri(mimeType, relativePath);
    }

    /**
     * 创建图片 Uri
     * @param displayName  显示名 ( 无需后缀, 根据 mimeType 决定 )
     * @param createTime   创建时间
     * @param mimeType     资源类型
     * @param relativePath 存储目录 ( 如 DCIM、Video、Pictures )
     * @return 图片 Uri
     */
    public static Uri createImageUri(
            final String displayName,
            final long createTime,
            final String mimeType,
            final String relativePath
    ) {
        return MediaStoreUtils.createImageUri(displayName, createTime, mimeType, relativePath);
    }

    // ========
    // = 视频 =
    // ========

    /**
     * 创建视频 Uri
     * @return 视频 Uri
     */
    public static Uri createVideoUri() {
        return MediaStoreUtils.createVideoUri();
    }

    /**
     * 创建视频 Uri
     * @param mimeType 资源类型
     * @return 视频 Uri
     */
    public static Uri createVideoUri(final String mimeType) {
        return MediaStoreUtils.createVideoUri(mimeType);
    }

    /**
     * 创建视频 Uri
     * @param mimeType     资源类型
     * @param relativePath 存储目录 ( 如 DCIM、Video、Pictures )
     * @return 视频 Uri
     */
    public static Uri createVideoUri(
            final String mimeType,
            final String relativePath
    ) {
        return MediaStoreUtils.createVideoUri(mimeType, relativePath);
    }

    /**
     * 创建视频 Uri
     * @param displayName  显示名 ( 无需后缀, 根据 mimeType 决定 )
     * @param createTime   创建时间
     * @param mimeType     资源类型
     * @param relativePath 存储目录 ( 如 DCIM、Video、Pictures )
     * @return 视频 Uri
     */
    public static Uri createVideoUri(
            final String displayName,
            final long createTime,
            final String mimeType,
            final String relativePath
    ) {
        return MediaStoreUtils.createVideoUri(displayName, createTime, mimeType, relativePath);
    }

    // ========
    // = 音频 =
    // ========

    /**
     * 创建音频 Uri
     * @return 音频 Uri
     */
    public static Uri createAudioUri() {
        return MediaStoreUtils.createAudioUri();
    }

    /**
     * 创建音频 Uri
     * @param mimeType 资源类型
     * @return 音频 Uri
     */
    public static Uri createAudioUri(final String mimeType) {
        return MediaStoreUtils.createAudioUri(mimeType);
    }

    /**
     * 创建音频 Uri
     * @param mimeType     资源类型
     * @param relativePath 存储目录 ( 如 DCIM、Video、Pictures )
     * @return 音频 Uri
     */
    public static Uri createAudioUri(
            final String mimeType,
            final String relativePath
    ) {
        return MediaStoreUtils.createAudioUri(mimeType, relativePath);
    }

    /**
     * 创建音频 Uri
     * @param displayName  显示名 ( 无需后缀, 根据 mimeType 决定 )
     * @param createTime   创建时间
     * @param mimeType     资源类型
     * @param relativePath 存储目录 ( 如 DCIM、Video、Pictures )
     * @return 音频 Uri
     */
    public static Uri createAudioUri(
            final String displayName,
            final long createTime,
            final String mimeType,
            final String relativePath
    ) {
        return MediaStoreUtils.createAudioUri(displayName, createTime, mimeType, relativePath);
    }

    // ===========
    // = 通用创建 =
    // ===========

    /**
     * 创建预存储 Media Uri
     * @param uri          MediaStore.media-type.Media.EXTERNAL_CONTENT_URI
     * @param displayName  显示名 ( 无需后缀, 根据 mimeType 决定 )
     * @param mimeType     资源类型
     * @param relativePath 存储目录 ( 如 DCIM、Video、Pictures )
     * @return Media Uri
     */
    public static Uri createMediaUri(
            final Uri uri,
            final String displayName,
            final String mimeType,
            final String relativePath
    ) {
        return MediaStoreUtils.createMediaUri(uri, displayName, mimeType, relativePath);
    }

    /**
     * 创建预存储 Media Uri
     * <pre>
     *     也可通过 {@link IntentUtils#getCreateDocumentIntent(String, String)} 创建
     * </pre>
     * @param uri          MediaStore.media-type.Media.EXTERNAL_CONTENT_URI
     * @param displayName  显示名 ( 无需后缀, 根据 mimeType 决定 )
     * @param createTime   创建时间
     * @param mimeType     资源类型
     * @param relativePath 存储目录 ( 如 DCIM、Video、Pictures )
     * @return Media Uri
     */
    public static Uri createMediaUri(
            final Uri uri,
            final String displayName,
            final long createTime,
            final String mimeType,
            final String relativePath
    ) {
        return MediaStoreUtils.createMediaUri(uri, displayName, createTime, mimeType, relativePath);
    }

    // =

    /**
     * 插入一张图片
     * @param uri      Media Uri
     * @param inputUri 输入 Uri ( 待存储文件 Uri )
     * @param format   如 Bitmap.CompressFormat.PNG
     * @param quality  质量
     * @return {@code true} success, {@code false} fail
     */
    public static boolean insertImage(
            final Uri uri,
            final Uri inputUri,
            final Bitmap.CompressFormat format,
            @IntRange(from = 0, to = 100) final int quality
    ) {
        return MediaStoreUtils.insertImage(uri, inputUri, format, quality);
    }

    /**
     * 插入一张图片
     * @param uri      Media Uri
     * @param inputUri 输入 Uri ( 待存储文件 Uri )
     * @return {@code true} success, {@code false} fail
     */
    public static boolean insertImage(
            final Uri uri,
            final Uri inputUri
    ) {
        return MediaStoreUtils.insertImage(uri, inputUri);
    }

    /**
     * 插入一条视频
     * @param uri      Media Uri
     * @param inputUri 输入 Uri ( 待存储文件 Uri )
     * @return {@code true} success, {@code false} fail
     */
    public static boolean insertVideo(
            final Uri uri,
            final Uri inputUri
    ) {
        return MediaStoreUtils.insertVideo(uri, inputUri);
    }

    /**
     * 插入一条音频
     * @param uri      Media Uri
     * @param inputUri 输入 Uri ( 待存储文件 Uri )
     * @return {@code true} success, {@code false} fail
     */
    public static boolean insertAudio(
            final Uri uri,
            final Uri inputUri
    ) {
        return MediaStoreUtils.insertAudio(uri, inputUri);
    }

    /**
     * 插入一条多媒体资源
     * @param uri      Media Uri
     * @param inputUri 输入 Uri ( 待存储文件 Uri )
     * @return {@code true} success, {@code false} fail
     */
    public static boolean insertMedia(
            final Uri uri,
            final Uri inputUri
    ) {
        return MediaStoreUtils.insertMedia(uri, inputUri);
    }

    /**
     * 插入一条多媒体资源
     * @param uri         Media Uri
     * @param inputStream {@link InputStream}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean insertMedia(
            final Uri uri,
            final InputStream inputStream
    ) {
        return MediaStoreUtils.insertMedia(uri, inputStream);
    }

    /**
     * 插入一条多媒体资源
     * @param outputStream {@link OutputStream}
     * @param inputStream  {@link InputStream}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean insertMedia(
            final OutputStream outputStream,
            final InputStream inputStream
    ) {
        return MediaStoreUtils.insertMedia(outputStream, inputStream);
    }

    // =

    /**
     * 插入一条多媒体资源
     * @param uri      Media Uri
     * @param filePath 待存储文件路径
     * @return {@code true} success, {@code false} fail
     */
    public static boolean insertMedia(
            final Uri uri,
            final String filePath
    ) {
        return MediaStoreUtils.insertMedia(uri, filePath);
    }

    /**
     * 插入一条多媒体资源
     * @param uri  Media Uri
     * @param file 待存储文件
     * @return {@code true} success, {@code false} fail
     */
    public static boolean insertMedia(
            final Uri uri,
            final File file
    ) {
        return MediaStoreUtils.insertMedia(uri, file);
    }

    // =

    /**
     * 插入一条多媒体资源
     * @param uri      Media Uri
     * @param drawable 待保存图片
     * @return {@code true} success, {@code false} fail
     */
    public static boolean insertMedia(
            final Uri uri,
            final Drawable drawable
    ) {
        return MediaStoreUtils.insertMedia(uri, drawable);
    }

    /**
     * 插入一条多媒体资源
     * @param uri      Media Uri
     * @param drawable 待保存图片
     * @param format   如 Bitmap.CompressFormat.PNG
     * @return {@code true} success, {@code false} fail
     */
    public static boolean insertMedia(
            final Uri uri,
            final Drawable drawable,
            final Bitmap.CompressFormat format
    ) {
        return MediaStoreUtils.insertMedia(uri, drawable, format);
    }

    /**
     * 插入一条多媒体资源
     * @param uri      Media Uri
     * @param drawable 待保存图片
     * @param format   如 Bitmap.CompressFormat.PNG
     * @param quality  质量
     * @return {@code true} success, {@code false} fail
     */
    public static boolean insertMedia(
            final Uri uri,
            final Drawable drawable,
            final Bitmap.CompressFormat format,
            @IntRange(from = 0, to = 100) final int quality
    ) {
        return MediaStoreUtils.insertMedia(uri, drawable, format, quality);
    }

    // =

    /**
     * 插入一条多媒体资源
     * @param uri    Media Uri
     * @param bitmap 待保存图片
     * @return {@code true} success, {@code false} fail
     */
    public static boolean insertMedia(
            final Uri uri,
            final Bitmap bitmap
    ) {
        return MediaStoreUtils.insertMedia(uri, bitmap);
    }

    /**
     * 插入一条多媒体资源
     * @param uri    Media Uri
     * @param bitmap 待保存图片
     * @param format 如 Bitmap.CompressFormat.PNG
     * @return {@code true} success, {@code false} fail
     */
    public static boolean insertMedia(
            final Uri uri,
            final Bitmap bitmap,
            final Bitmap.CompressFormat format
    ) {
        return MediaStoreUtils.insertMedia(uri, bitmap, format);
    }

    /**
     * 插入一条多媒体资源
     * @param uri     Media Uri
     * @param bitmap  待保存图片
     * @param format  如 Bitmap.CompressFormat.PNG
     * @param quality 质量
     * @return {@code true} success, {@code false} fail
     */
    public static boolean insertMedia(
            final Uri uri,
            final Bitmap bitmap,
            final Bitmap.CompressFormat format,
            @IntRange(from = 0, to = 100) final int quality
    ) {
        return MediaStoreUtils.insertMedia(uri, bitmap, format, quality);
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
    public static PendingIntent createFavoriteRequest(
            final Collection<Uri> uris,
            final boolean favorite
    ) {
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
    public static PendingIntent createTrashRequest(
            final Collection<Uri> uris,
            final boolean trashed
    ) {
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