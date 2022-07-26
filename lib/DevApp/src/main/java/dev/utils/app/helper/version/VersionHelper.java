package dev.utils.app.helper.version;

import android.app.Activity;
import android.app.PendingIntent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import androidx.annotation.IntRange;
import androidx.annotation.RequiresApi;

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
import dev.utils.common.FileUtils;

/**
 * detail: Android 版本适配 Helper 类
 * @author Ttt
 * <pre>
 *     Android 版本适配 Helper 类, 方便快捷使用
 *     并简化需多工具类组合使用的功能
 *     关于路径建议及兼容建议查看 {@link PathUtils}
 *     <p></p>
 *     推荐使用 DevEngine 库 MediaStore Engine 实现 ( DevMediaStoreEngineImpl ) 进行外部、内部文件存储统一处理
 *     DevEngine README
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevEngine/README.md"/>
 *     <p></p>
 *     Android 10 ( Q ) 更新内容
 *     @see <a href="https://developer.android.com/about/versions/10"/>
 *     Android 11 ( R ) 更新内容
 *     @see <a href="https://developer.android.com/about/versions/11"/>
 *     Android 12 ( S ) 更新内容
 *     @see <a href="https://developer.android.com/about/versions/12"/>
 *     <p></p>
 *     Android 10 ( Q ) 适配指南
 *     @see <a href="https://juejin.im/post/5ddd2417f265da060a5217ff"/>
 *     Android 11 ( R ) 适配指南
 *     @see <a href="https://mp.weixin.qq.com/s/ZrsO5VvURwW98PTHei0kFA"/>
 *     <p></p>
 *     Android 版本适配全套指南
 *     @see <a href="https://github.com/getActivity/AndroidVersionAdapter"/>
 * </pre>
 */
public final class VersionHelper
        implements IHelperByVersion {

    private VersionHelper() {
    }

    // VersionHelper
    private static final VersionHelper HELPER = new VersionHelper();

    /**
     * 获取单例 VersionHelper
     * @return {@link VersionHelper}
     */
    public static VersionHelper get() {
        return HELPER;
    }

    // ====================
    // = IHelperByVersion =
    // ====================

    // ============
    // = UriUtils =
    // ============

    // ================
    // = FileProvider =
    // ================

    /**
     * 获取 FileProvider File Uri
     * @param file 文件
     * @return 指定文件 {@link Uri}
     */
    @Override
    public Uri getUriForFile(File file) {
        return UriUtils.getUriForFile(file);
    }

    /**
     * 获取 FileProvider File Path Uri
     * @param filePath 文件路径
     * @return 指定文件 {@link Uri}
     */
    @Override
    public Uri getUriForPath(String filePath) {
        return UriUtils.getUriForPath(filePath);
    }

    /**
     * 获取 FileProvider File Path Uri ( 自动添加包名 ${applicationId} )
     * @param file         文件
     * @param fileProvider android:authorities = ${applicationId}.fileProvider
     * @return 指定文件 {@link Uri}
     */
    @Override
    public Uri getUriForFileToName(
            File file,
            String fileProvider
    ) {
        return UriUtils.getUriForFileToName(file, fileProvider);
    }

    /**
     * 获取 FileProvider File Path Uri
     * @param file      文件
     * @param authority android:authorities
     * @return 指定文件 {@link Uri}
     */
    @Override
    public Uri getUriForFile(
            File file,
            String authority
    ) {
        return UriUtils.getUriForFile(file, authority);
    }

    /**
     * 通过 String 获取 Uri
     * @param uriString uri 路径
     * @return {@link Uri}
     */
    @Override
    public Uri getUriForString(String uriString) {
        return UriUtils.getUriForString(uriString);
    }

    /**
     * 通过 File Path 创建 Uri
     * @param filePath 文件路径
     * @return {@link Uri}
     */
    @Override
    public Uri fromFile(String filePath) {
        return UriUtils.fromFile(filePath);
    }

    /**
     * 通过 File 创建 Uri
     * <pre>
     *     File 的文件夹需要存在才能够对文件进行写入
     *     可以传入前调用 {@link FileUtils#createFolderByPath(File)} 进行创建文件夹
     * </pre>
     * @param file 文件
     * @return {@link Uri}
     */
    @Override
    public Uri fromFile(File file) {
        return UriUtils.fromFile(file);
    }

    // =======
    // = Uri =
    // =======

    /**
     * 判断是否 Uri
     * @param uriString uri 路径
     * @return {@code true} yes, {@code false} no
     */
    @Override
    public boolean isUri(String uriString) {
        return UriUtils.isUri(uriString);
    }

    /**
     * 判断是否 Uri
     * @param uri {@link Uri}
     * @return {@code true} yes, {@code false} no
     */
    @Override
    public boolean isUri(Uri uri) {
        return UriUtils.isUri(uri);
    }

    // =

    /**
     * 获取 Uri Scheme
     * @param uriString uri 路径
     * @return Uri Scheme
     */
    @Override
    public String getUriScheme(String uriString) {
        return UriUtils.getUriScheme(uriString);
    }

    /**
     * 获取 Uri Scheme
     * @param uri {@link Uri}
     * @return Uri Scheme
     */
    @Override
    public String getUriScheme(Uri uri) {
        return UriUtils.getUriScheme(uri);
    }

    /**
     * 判断 Uri 路径资源是否存在
     * <pre>
     *     uri 非 FilePath, 可通过 {@link UriUtils#getMediaUri} 获取
     * </pre>
     * @param uriString uri 路径
     * @return {@code true} yes, {@code false} no
     */
    @Override
    public boolean isUriExists(String uriString) {
        return UriUtils.isUriExists(uriString);
    }

    /**
     * 判断 Uri 路径资源是否存在
     * @param uri {@link Uri}
     * @return {@code true} yes, {@code false} no
     */
    @Override
    public boolean isUriExists(Uri uri) {
        return UriUtils.isUriExists(uri);
    }

    // ==========
    // = 复制文件 =
    // ==========

    /**
     * 通过 Uri 复制文件
     * @param uri {@link Uri}
     * @return 复制后的文件路径
     */
    @Override
    public String copyByUri(Uri uri) {
        return UriUtils.copyByUri(uri);
    }

    /**
     * 通过 Uri 复制文件
     * @param uri      {@link Uri}
     * @param fileName 文件名 {@link ContentResolverUtils#getDisplayNameColumn}
     * @return 复制后的文件路径
     */
    @Override
    public String copyByUri(
            Uri uri,
            String fileName
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
    @Override
    public String copyByUri(
            Uri uri,
            File file,
            String fileName
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
    @Override
    public String copyByUri(
            Uri uri,
            String filePath,
            String fileName
    ) {
        return UriUtils.copyByUri(uri, filePath, fileName);
    }

    // =============
    // = 获取文件路径 =
    // =============

    /**
     * 通过 Uri 获取文件路径
     * @param uri {@link Uri}
     * @return 文件路径
     */
    @Override
    public String getFilePathByUri(Uri uri) {
        return UriUtils.getFilePathByUri(uri);
    }

    /**
     * 通过 Uri 获取文件路径
     * <pre>
     *     默认不复制文件, 防止影响已经使用该方法的功能 ( 文件过大导致 ANR、耗时操作等 )
     * </pre>
     * @param uri     {@link Uri}
     * @param isQCopy Android 10 ( Q ) 及其以上版本是否复制文件
     * @return 文件路径
     */
    @Override
    public String getFilePathByUri(
            Uri uri,
            boolean isQCopy
    ) {
        return UriUtils.getFilePathByUri(uri, isQCopy);
    }

    // ========================
    // = ContentResolverUtils =
    // ========================

    /**
     * 通过 File 获取 Media Uri
     * @param file 文件
     * @return 指定文件 {@link Uri}
     */
    @Override
    public Uri getMediaUri(File file) {
        return ContentResolverUtils.getMediaUri(file);
    }

    /**
     * 通过 File 获取 Media Uri
     * @param uri  MediaStore.media-type.Media.EXTERNAL_CONTENT_URI
     * @param file 文件
     * @return 指定文件 {@link Uri}
     */
    @Override
    public Uri getMediaUri(
            Uri uri,
            File file
    ) {
        return ContentResolverUtils.getMediaUri(uri, file);
    }

    /**
     * 通过 File Path 获取 Media Uri
     * @param filePath 文件路径
     * @return 指定文件 {@link Uri}
     */
    @Override
    public Uri getMediaUri(String filePath) {
        return ContentResolverUtils.getMediaUri(filePath);
    }

    /**
     * 通过 File Path 获取 Media Uri
     * <pre>
     *     只能用于查询 Media ( SDK_INT >= Q 使用, SDK_INT < Q 则直接使用 {@link Uri#fromFile(File)})
     *     通过外部存储 ( 公开目录 ) SDCard 文件地址获取对应的 Uri content://
     * </pre>
     * @param uri      MediaStore.media-type.Media.EXTERNAL_CONTENT_URI
     * @param filePath 文件路径
     * @return 指定文件 {@link Uri}
     */
    @Override
    public Uri getMediaUri(
            Uri uri,
            String filePath
    ) {
        return ContentResolverUtils.getMediaUri(uri, filePath);
    }

    // =

    /**
     * 通过 File 获取 Media 信息
     * @param file       文件
     * @param mediaQuery 多媒体查询抽象类
     * @return Media 信息
     */
    @Override
    public String[] mediaQuery(
            File file,
            ContentResolverUtils.MediaQuery mediaQuery
    ) {
        return ContentResolverUtils.mediaQuery(file, mediaQuery);
    }

    /**
     * 通过 File 获取 Media 信息
     * @param uri        MediaStore.media-type.Media.EXTERNAL_CONTENT_URI
     * @param file       文件
     * @param mediaQuery 多媒体查询抽象类
     * @return Media 信息
     */
    @Override
    public String[] mediaQuery(
            Uri uri,
            File file,
            ContentResolverUtils.MediaQuery mediaQuery
    ) {
        return ContentResolverUtils.mediaQuery(uri, file, mediaQuery);
    }

    /**
     * 通过 File Path 获取 Media Uri
     * @param filePath   文件路径
     * @param mediaQuery 多媒体查询抽象类
     * @return Media 信息
     */
    @Override
    public String[] mediaQuery(
            String filePath,
            ContentResolverUtils.MediaQuery mediaQuery
    ) {
        return ContentResolverUtils.mediaQuery(filePath, mediaQuery);
    }

    /**
     * 通过 File Path 获取 Media Uri
     * <pre>
     *     只能用于查询 Media ( SDK_INT >= Q 使用, SDK_INT < Q 则直接使用 {@link Uri#fromFile(File)})
     *     通过外部存储 ( 公开目录 ) SDCard 文件地址获取对应的 Uri content://
     * </pre>
     * @param uri        MediaStore.media-type.Media.EXTERNAL_CONTENT_URI
     * @param filePath   文件路径
     * @param mediaQuery 多媒体查询抽象类
     * @return Media 信息
     */
    @Override
    public String[] mediaQuery(
            Uri uri,
            String filePath,
            ContentResolverUtils.MediaQuery mediaQuery
    ) {
        return ContentResolverUtils.mediaQuery(uri, filePath, mediaQuery);
    }

    // ===================
    // = MediaStoreUtils =
    // ===================

    // ==========
    // = 通知相册 =
    // ==========

    /**
     * 通知刷新本地资源
     * @param filePath 文件路径
     * @return {@code true} success, {@code false} fail
     */
    @Deprecated
    @Override
    public boolean notifyMediaStore(String filePath) {
        return MediaStoreUtils.notifyMediaStore(filePath);
    }

    /**
     * 通知刷新本地资源
     * <pre>
     *     注意事项: 部分手机 ( 如小米 ) 通知文件地址层级过深, 将会并入相册文件夹中
     *     尽量放在 SDCrad/XXX/xx.jpg 层级中, 推荐直接存储到 {@link PathUtils#getSDCard().getDCIMPath()}
     *     该方法只能通知刷新 外部存储 ( 公开目录 ) SDCard 文件地址
     *     MediaStore Cursor 无法扫描 内部存储、外部存储 ( 私有目录 )
     *     <p></p>
     *     正确的操作应该是: 不论版本统一存储到 外部存储 ( 私有目录 ) 再通过 MediaStore 插入数据
     * </pre>
     * @param file 文件
     * @return {@code true} success, {@code false} fail
     * @deprecated Android 10 ( Q ) 以后的版本需要通过 MediaStore 插入数据
     */
    @Deprecated
    @Override
    public boolean notifyMediaStore(File file) {
        return MediaStoreUtils.notifyMediaStore(file);
    }

    /**
     * 通知刷新本地资源
     * @param uri {@link Uri}
     * @return {@code true} success, {@code false} fail
     */
    @Override
    public boolean notifyMediaStore(Uri uri) {
        return MediaStoreUtils.notifyMediaStore(uri);
    }

    // =======
    // = 图片 =
    // =======

    /**
     * 创建图片 Uri
     * @return 图片 Uri
     */
    @Override
    public Uri createImageUri() {
        return MediaStoreUtils.createImageUri();
    }

    /**
     * 创建图片 Uri
     * @param mimeType 资源类型
     * @return 图片 Uri
     */
    @Override
    public Uri createImageUri(String mimeType) {
        return MediaStoreUtils.createImageUri(mimeType);
    }

    /**
     * 创建图片 Uri
     * @param mimeType     资源类型
     * @param relativePath 存储目录 ( 如 DCIM、Video、Pictures、Music、Download )
     * @return 图片 Uri
     */
    @Override
    public Uri createImageUri(
            String mimeType,
            String relativePath
    ) {
        return MediaStoreUtils.createImageUri(mimeType, relativePath);
    }

    /**
     * 创建图片 Uri
     * @param displayName  显示名 ( 无需后缀, 根据 mimeType 决定, 如果 mimeType 用了 xxx/* 则需指定后缀 )
     * @param mimeType     资源类型
     * @param relativePath 存储目录 ( 如 DCIM、Video、Pictures、Music、Download )
     * @return 图片 Uri
     */
    @Override
    public Uri createImageUri(
            String displayName,
            String mimeType,
            String relativePath
    ) {
        return MediaStoreUtils.createImageUri(
                displayName, mimeType, relativePath
        );
    }

    /**
     * 创建图片 Uri
     * @param displayName  显示名 ( 无需后缀, 根据 mimeType 决定, 如果 mimeType 用了 xxx/* 则需指定后缀 )
     * @param mimeType     资源类型
     * @param relativePath 存储目录 ( 如 DCIM、Video、Pictures、Music、Download )
     * @param createTime   创建时间
     * @return 图片 Uri
     */
    @Override
    public Uri createImageUri(
            String displayName,
            String mimeType,
            String relativePath,
            long createTime
    ) {
        return MediaStoreUtils.createImageUri(
                displayName, mimeType, relativePath, createTime
        );
    }

    // =======
    // = 视频 =
    // =======

    /**
     * 创建视频 Uri
     * @return 视频 Uri
     */
    @Override
    public Uri createVideoUri() {
        return MediaStoreUtils.createVideoUri();
    }

    /**
     * 创建视频 Uri
     * @param mimeType 资源类型
     * @return 视频 Uri
     */
    @Override
    public Uri createVideoUri(String mimeType) {
        return MediaStoreUtils.createVideoUri(mimeType);
    }

    /**
     * 创建视频 Uri
     * @param mimeType     资源类型
     * @param relativePath 存储目录 ( 如 DCIM、Video、Pictures、Music、Download )
     * @return 视频 Uri
     */
    @Override
    public Uri createVideoUri(
            String mimeType,
            String relativePath
    ) {
        return MediaStoreUtils.createVideoUri(mimeType, relativePath);
    }

    /**
     * 创建视频 Uri
     * @param displayName  显示名 ( 无需后缀, 根据 mimeType 决定, 如果 mimeType 用了 xxx/* 则需指定后缀 )
     * @param mimeType     资源类型
     * @param relativePath 存储目录 ( 如 DCIM、Video、Pictures、Music、Download )
     * @return 视频 Uri
     */
    @Override
    public Uri createVideoUri(
            String displayName,
            String mimeType,
            String relativePath
    ) {
        return MediaStoreUtils.createVideoUri(
                displayName, mimeType, relativePath
        );
    }

    /**
     * 创建视频 Uri
     * @param displayName  显示名 ( 无需后缀, 根据 mimeType 决定, 如果 mimeType 用了 xxx/* 则需指定后缀 )
     * @param mimeType     资源类型
     * @param relativePath 存储目录 ( 如 DCIM、Video、Pictures、Music、Download )
     * @param createTime   创建时间
     * @return 视频 Uri
     */
    @Override
    public Uri createVideoUri(
            String displayName,
            String mimeType,
            String relativePath,
            long createTime
    ) {
        return MediaStoreUtils.createVideoUri(
                displayName, mimeType, relativePath, createTime
        );
    }

    // =======
    // = 音频 =
    // =======

    /**
     * 创建音频 Uri
     * @return 音频 Uri
     */
    @Override
    public Uri createAudioUri() {
        return MediaStoreUtils.createAudioUri();
    }

    /**
     * 创建音频 Uri
     * @param mimeType 资源类型
     * @return 音频 Uri
     */
    @Override
    public Uri createAudioUri(String mimeType) {
        return MediaStoreUtils.createAudioUri(mimeType);
    }

    /**
     * 创建音频 Uri
     * @param mimeType     资源类型
     * @param relativePath 存储目录 ( 如 DCIM、Video、Pictures、Music、Download )
     * @return 音频 Uri
     */
    @Override
    public Uri createAudioUri(
            String mimeType,
            String relativePath
    ) {
        return MediaStoreUtils.createAudioUri(mimeType, relativePath);
    }

    /**
     * 创建音频 Uri
     * @param displayName  显示名 ( 无需后缀, 根据 mimeType 决定, 如果 mimeType 用了 xxx/* 则需指定后缀 )
     * @param mimeType     资源类型
     * @param relativePath 存储目录 ( 如 DCIM、Video、Pictures、Music、Download )
     * @return 音频 Uri
     */
    @Override
    public Uri createAudioUri(
            String displayName,
            String mimeType,
            String relativePath
    ) {
        return MediaStoreUtils.createAudioUri(
                displayName, mimeType, relativePath
        );
    }

    /**
     * 创建音频 Uri
     * @param displayName  显示名 ( 无需后缀, 根据 mimeType 决定, 如果 mimeType 用了 xxx/* 则需指定后缀 )
     * @param mimeType     资源类型
     * @param relativePath 存储目录 ( 如 DCIM、Video、Pictures、Music、Download )
     * @param createTime   创建时间
     * @return 音频 Uri
     */
    @Override
    public Uri createAudioUri(
            String displayName,
            String mimeType,
            String relativePath,
            long createTime
    ) {
        return MediaStoreUtils.createAudioUri(
                displayName, mimeType, relativePath, createTime
        );
    }

    // ============
    // = Download =
    // ============

    /**
     * 创建 Download Uri
     * @param displayName 显示名 ( 需后缀 )
     * @return Download Uri
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public Uri createDownloadUri(String displayName) {
        return MediaStoreUtils.createDownloadUri(displayName);
    }

    /**
     * 创建 Download Uri
     * @param displayName 显示名 ( 无需后缀, 根据 mimeType 决定, 如果 mimeType 用了 xxx/* 则需指定后缀 )
     * @param mimeType    资源类型
     * @return Download Uri
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public Uri createDownloadUri(
            String displayName,
            String mimeType
    ) {
        return MediaStoreUtils.createDownloadUri(
                displayName, mimeType
        );
    }

    /**
     * 创建 Download Uri
     * @param displayName  显示名 ( 无需后缀, 根据 mimeType 决定, 如果 mimeType 用了 xxx/* 则需指定后缀 )
     * @param mimeType     资源类型
     * @param relativePath 存储目录 ( 如 DCIM、Video、Pictures、Music、Download )
     * @return Download Uri
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public Uri createDownloadUri(
            String displayName,
            String mimeType,
            String relativePath
    ) {
        return MediaStoreUtils.createDownloadUri(
                displayName, mimeType, relativePath
        );
    }

    /**
     * 创建 Download Uri
     * <pre>
     *     Android 10 ( Q ) 以下直接通过 File 写入到 {@link Environment#DIRECTORY_DOWNLOADS}
     * </pre>
     * @param displayName  显示名 ( 无需后缀, 根据 mimeType 决定, 如果 mimeType 用了 xxx/* 则需指定后缀 )
     * @param mimeType     资源类型
     * @param relativePath 存储目录 ( 如 DCIM、Video、Pictures、Music、Download )
     * @param createTime   创建时间
     * @return Download Uri
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public Uri createDownloadUri(
            String displayName,
            String mimeType,
            String relativePath,
            long createTime
    ) {
        return MediaStoreUtils.createDownloadUri(
                displayName, mimeType, relativePath, createTime
        );
    }

    // ==========
    // = 通用创建 =
    // ==========

    /**
     * 创建预存储 Media Uri
     * @param uri          MediaStore.media-type.Media.EXTERNAL_CONTENT_URI
     * @param displayName  显示名 ( 无需后缀, 根据 mimeType 决定, 如果 mimeType 用了 xxx/* 则需指定后缀 )
     * @param mimeType     资源类型
     * @param relativePath 存储目录 ( 如 DCIM、Video、Pictures、Music、Download )
     * @return Media Uri
     */
    @Override
    public Uri createMediaUri(
            Uri uri,
            String displayName,
            String mimeType,
            String relativePath
    ) {
        return MediaStoreUtils.createMediaUri(
                uri, displayName, mimeType, relativePath
        );
    }

    /**
     * 创建预存储 Media Uri
     * <pre>
     *     也可通过 {@link IntentUtils#getCreateDocumentIntent(String, String)} 创建
     * </pre>
     * @param uri          MediaStore.media-type.Media.EXTERNAL_CONTENT_URI
     * @param displayName  显示名 ( 无需后缀, 根据 mimeType 决定, 如果 mimeType 用了 xxx/* 则需指定后缀 )
     * @param mimeType     资源类型
     * @param relativePath 存储目录 ( 如 DCIM、Video、Pictures、Music、Download )
     * @param createTime   创建时间
     * @return Media Uri
     */
    @Override
    public Uri createMediaUri(
            Uri uri,
            String displayName,
            String mimeType,
            String relativePath,
            long createTime
    ) {
        return MediaStoreUtils.createMediaUri(
                uri, displayName, mimeType, relativePath, createTime
        );
    }

    // ========
    // = File =
    // ========

    /**
     * 通过 File Path 创建 Uri
     * @param fileName 文件名
     * @return File Uri
     */
    @Override
    public Uri createUriByPath(String fileName) {
        return MediaStoreUtils.createUriByPath(fileName);
    }

    /**
     * 通过 File Path 创建 Uri
     * @param fileName 文件名
     * @param filePath 文件路径
     * @return File Uri
     */
    @Override
    public Uri createUriByPath(
            String fileName,
            String filePath
    ) {
        return MediaStoreUtils.createUriByPath(fileName, filePath);
    }

    /**
     * 通过 File Path 创建 Uri
     * @param filePath 文件路径
     * @return File Uri
     */
    @Override
    public Uri createUriByFile(String filePath) {
        return MediaStoreUtils.createUriByFile(filePath);
    }

    /**
     * 通过 File 创建 Uri
     * <pre>
     *     主要用于如低版本写入 Download 文件夹, 创建 Uri
     *     统一使用 {@link #insertMedia(Uri, Uri)} 写入文件夹
     *     高版本使用 {@link #createDownloadUri(String)}
     *     并不局限 Download 文件夹操作
     * </pre>
     * @param file 文件
     * @return File Uri
     */
    @Override
    public Uri createUriByFile(File file) {
        return MediaStoreUtils.createUriByFile(file);
    }

    // ==========
    // = 插入数据 =
    // ==========

    /**
     * 插入一张图片
     * <pre>
     *     Android 10 ( Q ) 已抛弃仍可用, 推荐使用传入 Uri 方式 {@link #createImageUri}
     * </pre>
     * @param filePath 文件路径
     * @param name     存储文件名
     * @param notify   是否通知相册
     * @return {@code true} success, {@code false} fail
     */
    @Deprecated
    @Override
    public Uri insertImage(
            String filePath,
            String name,
            boolean notify
    ) {
        return MediaStoreUtils.insertImage(filePath, name, notify);
    }

    /**
     * 插入一张图片
     * @param uri      {@link #createImageUri} or {@link #createMediaUri}
     * @param inputUri 输入 Uri ( 待存储文件 Uri )
     * @param format   如 Bitmap.CompressFormat.PNG
     * @param quality  质量
     * @return {@code true} success, {@code false} fail
     */
    @Override
    public boolean insertImage(
            Uri uri,
            Uri inputUri,
            Bitmap.CompressFormat format,
            @IntRange(from = 0, to = 100) int quality
    ) {
        return MediaStoreUtils.insertImage(uri, inputUri, format, quality);
    }

    // =

    /**
     * 插入一张图片
     * @param uri      {@link #createImageUri} or {@link #createMediaUri}
     * @param inputUri 输入 Uri ( 待存储文件 Uri )
     * @return {@code true} success, {@code false} fail
     */
    @Override
    public boolean insertImage(
            Uri uri,
            Uri inputUri
    ) {
        return MediaStoreUtils.insertImage(uri, inputUri);
    }

    /**
     * 插入一条视频
     * @param uri      {@link #createVideoUri} or {@link #createMediaUri}
     * @param inputUri 输入 Uri ( 待存储文件 Uri )
     * @return {@code true} success, {@code false} fail
     */
    @Override
    public boolean insertVideo(
            Uri uri,
            Uri inputUri
    ) {
        return MediaStoreUtils.insertVideo(uri, inputUri);
    }

    /**
     * 插入一条音频
     * @param uri      {@link #createAudioUri()} or {@link #createMediaUri}
     * @param inputUri 输入 Uri ( 待存储文件 Uri )
     * @return {@code true} success, {@code false} fail
     */
    @Override
    public boolean insertAudio(
            Uri uri,
            Uri inputUri
    ) {
        return MediaStoreUtils.insertAudio(uri, inputUri);
    }

    /**
     * 插入一条文件资源
     * @param uri      {@link #createDownloadUri} or {@link #createMediaUri}
     * @param inputUri 输入 Uri ( 待存储文件 Uri )
     * @return {@code true} success, {@code false} fail
     */
    @Override
    public boolean insertDownload(
            Uri uri,
            Uri inputUri
    ) {
        return MediaStoreUtils.insertDownload(uri, inputUri);
    }

    // =

    /**
     * 插入一条多媒体资源
     * @param uri      {@link #createImageUri} or {@link #createVideoUri} or
     *                 {@link #createAudioUri()} or {@link #createMediaUri}
     * @param inputUri 输入 Uri ( 待存储文件 Uri )
     * @return {@code true} success, {@code false} fail
     */
    @Override
    public boolean insertMedia(
            Uri uri,
            Uri inputUri
    ) {
        return MediaStoreUtils.insertMedia(uri, inputUri);
    }

    /**
     * 插入一条多媒体资源
     * @param uri         {@link #createImageUri} or {@link #createVideoUri} or
     *                    {@link #createAudioUri()} or {@link #createMediaUri}
     * @param inputStream {@link InputStream}
     * @return {@code true} success, {@code false} fail
     */
    @Override
    public boolean insertMedia(
            Uri uri,
            InputStream inputStream
    ) {
        return MediaStoreUtils.insertMedia(uri, inputStream);
    }

    /**
     * 插入一条多媒体资源
     * @param outputStream {@link OutputStream}
     * @param inputStream  {@link InputStream}
     * @return {@code true} success, {@code false} fail
     */
    @Override
    public boolean insertMedia(
            OutputStream outputStream,
            InputStream inputStream
    ) {
        return MediaStoreUtils.insertMedia(outputStream, inputStream);
    }

    // =

    /**
     * 插入一条多媒体资源
     * @param uri      {@link #createImageUri} or {@link #createVideoUri} or
     *                 {@link #createAudioUri()} or {@link #createMediaUri}
     * @param filePath 待存储文件路径
     * @return {@code true} success, {@code false} fail
     */
    @Override
    public boolean insertMedia(
            Uri uri,
            String filePath
    ) {
        return MediaStoreUtils.insertMedia(uri, filePath);
    }

    /**
     * 插入一条多媒体资源
     * @param uri  {@link #createImageUri} or {@link #createVideoUri} or
     *             {@link #createAudioUri()} or {@link #createMediaUri}
     * @param file 待存储文件
     * @return {@code true} success, {@code false} fail
     */
    @Override
    public boolean insertMedia(
            Uri uri,
            File file
    ) {
        return MediaStoreUtils.insertMedia(uri, file);
    }

    // =

    /**
     * 插入一条多媒体资源
     * @param uri      {@link #createImageUri} or {@link #createVideoUri} or
     *                 {@link #createAudioUri()} or {@link #createMediaUri}
     * @param drawable 待保存图片
     * @return {@code true} success, {@code false} fail
     */
    @Override
    public boolean insertMedia(
            Uri uri,
            Drawable drawable
    ) {
        return MediaStoreUtils.insertMedia(uri, drawable);
    }

    /**
     * 插入一条多媒体资源
     * @param uri      {@link #createImageUri} or {@link #createVideoUri} or
     *                 {@link #createAudioUri()} or {@link #createMediaUri}
     * @param drawable 待保存图片
     * @param format   如 Bitmap.CompressFormat.PNG
     * @return {@code true} success, {@code false} fail
     */
    @Override
    public boolean insertMedia(
            Uri uri,
            Drawable drawable,
            Bitmap.CompressFormat format
    ) {
        return MediaStoreUtils.insertMedia(uri, drawable, format);
    }

    /**
     * 插入一条多媒体资源
     * @param uri      {@link #createImageUri} or {@link #createVideoUri} or
     *                 {@link #createAudioUri()} or {@link #createMediaUri}
     * @param drawable 待保存图片
     * @param format   如 Bitmap.CompressFormat.PNG
     * @param quality  质量
     * @return {@code true} success, {@code false} fail
     */
    @Override
    public boolean insertMedia(
            Uri uri,
            Drawable drawable,
            Bitmap.CompressFormat format,
            @IntRange(from = 0, to = 100) int quality
    ) {
        return MediaStoreUtils.insertMedia(uri, drawable, format, quality);
    }

    // =

    /**
     * 插入一条多媒体资源
     * @param uri    {@link #createImageUri} or {@link #createVideoUri} or
     *               {@link #createAudioUri()} or {@link #createMediaUri}
     * @param bitmap 待保存图片
     * @return {@code true} success, {@code false} fail
     */
    @Override
    public boolean insertMedia(
            Uri uri,
            Bitmap bitmap
    ) {
        return MediaStoreUtils.insertMedia(uri, bitmap);
    }

    /**
     * 插入一条多媒体资源
     * @param uri    {@link #createImageUri} or {@link #createVideoUri} or
     *               {@link #createAudioUri()} or {@link #createMediaUri}
     * @param bitmap 待保存图片
     * @param format 如 Bitmap.CompressFormat.PNG
     * @return {@code true} success, {@code false} fail
     */
    @Override
    public boolean insertMedia(
            Uri uri,
            Bitmap bitmap,
            Bitmap.CompressFormat format
    ) {
        return MediaStoreUtils.insertMedia(uri, bitmap, format);
    }

    /**
     * 插入一条多媒体资源
     * @param uri     {@link #createImageUri} or {@link #createVideoUri} or
     *                {@link #createAudioUri()} or {@link #createMediaUri}
     * @param bitmap  待保存图片
     * @param format  如 Bitmap.CompressFormat.PNG
     * @param quality 质量
     * @return {@code true} success, {@code false} fail
     */
    @Override
    public boolean insertMedia(
            Uri uri,
            Bitmap bitmap,
            Bitmap.CompressFormat format,
            @IntRange(from = 0, to = 100) int quality
    ) {
        return MediaStoreUtils.insertMedia(uri, bitmap, format, quality);
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
    @Override
    public PendingIntent createWriteRequest(Collection<Uri> uris) {
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
    @Override
    public PendingIntent createFavoriteRequest(
            Collection<Uri> uris,
            boolean favorite
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
    @Override
    public PendingIntent createTrashRequest(
            Collection<Uri> uris,
            boolean trashed
    ) {
        return MediaStoreUtils.createTrashRequest(uris, trashed);
    }

    /**
     * 获取用户立即永久删除指定的媒体文件 ( 而不是先将其放入垃圾箱 ) 的请求
     * @param uris 待请求 Uri 集
     * @return {@link PendingIntent}
     */
    @Override
    public PendingIntent createDeleteRequest(Collection<Uri> uris) {
        return MediaStoreUtils.createDeleteRequest(uris);
    }

    // =============
    // = PathUtils =
    // =============

    /**
     * 是否获得 MANAGE_EXTERNAL_STORAGE 权限
     * @return {@code true} yes, {@code false} no
     */
    @Override
    public boolean isExternalStorageManager() {
        return PathUtils.isExternalStorageManager();
    }

    /**
     * 检查是否有 MANAGE_EXTERNAL_STORAGE 权限并跳转设置页面
     * @return {@code true} yes, {@code false} no
     */
    @Override
    public boolean checkExternalStorageAndIntentSetting() {
        return PathUtils.checkExternalStorageAndIntentSetting();
    }
}