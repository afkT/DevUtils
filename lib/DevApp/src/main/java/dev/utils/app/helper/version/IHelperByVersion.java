package dev.utils.app.helper.version;

import android.app.Activity;
import android.app.PendingIntent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;

import androidx.annotation.IntRange;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;

import dev.utils.app.AppUtils;
import dev.utils.app.ContentResolverUtils;
import dev.utils.app.IntentUtils;
import dev.utils.app.UriUtils;

/**
 * detail: VersionHelper 接口
 * @author Ttt
 */
public interface IHelperByVersion<T> {

    // ====================
    // = Android 10 ( Q ) =
    // ====================

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
    boolean isUriExists(String uriString);

    /**
     * 判断 Uri 路径资源是否存在
     * @param uri {@link Uri}
     * @return {@code true} yes, {@code false} no
     */
    boolean isUriExists(Uri uri);

    /**
     * 通过 File 获取 Media Uri
     * @param file 文件
     * @return 指定文件 {@link Uri}
     */
    Uri getMediaUri(File file);

    /**
     * 通过 File 获取 Media Uri
     * @param uri  MediaStore.media-type.Media.EXTERNAL_CONTENT_URI
     * @param file 文件
     * @return 指定文件 {@link Uri}
     */
    Uri getMediaUri(
            Uri uri,
            File file
    );

    /**
     * 通过 File Path 获取 Media Uri
     * @param filePath 文件路径
     * @return 指定文件 {@link Uri}
     */
    Uri getMediaUri(String filePath);

    /**
     * 通过 File Path 获取 Media Uri
     * @param uri      MediaStore.media-type.Media.EXTERNAL_CONTENT_URI
     * @param filePath 文件路径
     * @return 指定文件 {@link Uri}
     */
    Uri getMediaUri(
            Uri uri,
            String filePath
    );

    /**
     * 通过 Uri 复制文件
     * @param uri {@link Uri}
     * @return 复制后的文件路径
     */
    String copyByUri(Uri uri);

    /**
     * 通过 Uri 复制文件
     * @param uri      {@link Uri}
     * @param fileName 文件名 {@link ContentResolverUtils#getDisplayNameColumn}
     * @return 复制后的文件路径
     */
    String copyByUri(
            Uri uri,
            String fileName
    );

    /**
     * 通过 Uri 复制文件
     * @param uri      {@link Uri}
     * @param file     文件
     * @param fileName 文件名 {@link ContentResolverUtils#getDisplayNameColumn}
     * @return 复制后的文件路径
     */
    String copyByUri(
            Uri uri,
            File file,
            String fileName
    );

    /**
     * 通过 Uri 复制文件
     * @param uri      {@link Uri}
     * @param filePath 文件路径
     * @param fileName 文件名 {@link ContentResolverUtils#getDisplayNameColumn}
     * @return 复制后的文件路径
     */
    String copyByUri(
            Uri uri,
            String filePath,
            String fileName
    );

    /**
     * 通过 Uri 获取文件路径
     * @param uri {@link Uri}
     * @return 文件路径
     */
    String getFilePathByUri(Uri uri);

    /**
     * 通过 Uri 获取文件路径
     * <pre>
     *     默认不复制文件, 防止影响已经使用该方法的功能 ( 文件过大导致 ANR、耗时操作等 )
     * </pre>
     * @param uri     {@link Uri}
     * @param isQCopy Android 10 ( Q ) 及其以上版本是否复制文件
     * @return 文件路径
     */
    String getFilePathByUri(
            Uri uri,
            boolean isQCopy
    );

    /**
     * 获取 FileProvider File Uri
     * @param file 文件
     * @return 指定文件 {@link Uri}
     */
    Uri getUriForFile(File file);

    /**
     * 获取 FileProvider File Path Uri
     * @param filePath 文件路径
     * @return 指定文件 {@link Uri}
     */
    Uri getUriForPath(String filePath);

    /**
     * 获取 FileProvider File Path Uri ( 自动添加包名 ${applicationId} )
     * @param file         文件
     * @param fileProvider android:authorities = ${applicationId}.fileProvider
     * @return 指定文件 {@link Uri}
     */
    Uri getUriForFileToName(
            File file,
            String fileProvider
    );

    /**
     * 获取 FileProvider File Path Uri
     * @param file      文件
     * @param authority android:authorities
     * @return 指定文件 {@link Uri}
     */
    Uri getUriForFile(
            File file,
            String authority
    );

    // ===================
    // = MediaStoreUtils =
    // ===================

    // =======
    // = 图片 =
    // =======

    /**
     * 创建图片 Uri
     * @return 图片 Uri
     */
    Uri createImageUri();

    /**
     * 创建图片 Uri
     * @param mimeType 资源类型
     * @return 图片 Uri
     */
    Uri createImageUri(String mimeType);

    /**
     * 创建图片 Uri
     * @param mimeType     资源类型
     * @param relativePath 存储目录 ( 如 DCIM、Video、Pictures、Music、Download )
     * @return 图片 Uri
     */
    Uri createImageUri(
            String mimeType,
            String relativePath
    );

    /**
     * 创建图片 Uri
     * @param displayName  显示名 ( 无需后缀, 根据 mimeType 决定, 如果 mimeType 用了 xxx/* 则需指定后缀 )
     * @param mimeType     资源类型
     * @param relativePath 存储目录 ( 如 DCIM、Video、Pictures、Music、Download )
     * @return 图片 Uri
     */
    Uri createImageUri(
            String displayName,
            String mimeType,
            String relativePath
    );

    /**
     * 创建图片 Uri
     * @param displayName  显示名 ( 无需后缀, 根据 mimeType 决定, 如果 mimeType 用了 xxx/* 则需指定后缀 )
     * @param mimeType     资源类型
     * @param relativePath 存储目录 ( 如 DCIM、Video、Pictures、Music、Download )
     * @param createTime   创建时间
     * @return 图片 Uri
     */
    Uri createImageUri(
            String displayName,
            String mimeType,
            String relativePath,
            long createTime
    );

    // =======
    // = 视频 =
    // =======

    /**
     * 创建视频 Uri
     * @return 视频 Uri
     */
    Uri createVideoUri();

    /**
     * 创建视频 Uri
     * @param mimeType 资源类型
     * @return 视频 Uri
     */
    Uri createVideoUri(String mimeType);

    /**
     * 创建视频 Uri
     * @param mimeType     资源类型
     * @param relativePath 存储目录 ( 如 DCIM、Video、Pictures、Music、Download )
     * @return 视频 Uri
     */
    Uri createVideoUri(
            String mimeType,
            String relativePath
    );

    /**
     * 创建视频 Uri
     * @param displayName  显示名 ( 无需后缀, 根据 mimeType 决定, 如果 mimeType 用了 xxx/* 则需指定后缀 )
     * @param mimeType     资源类型
     * @param relativePath 存储目录 ( 如 DCIM、Video、Pictures、Music、Download )
     * @return 视频 Uri
     */
    Uri createVideoUri(
            String displayName,
            String mimeType,
            String relativePath
    );

    /**
     * 创建视频 Uri
     * @param displayName  显示名 ( 无需后缀, 根据 mimeType 决定, 如果 mimeType 用了 xxx/* 则需指定后缀 )
     * @param mimeType     资源类型
     * @param relativePath 存储目录 ( 如 DCIM、Video、Pictures、Music、Download )
     * @param createTime   创建时间
     * @return 视频 Uri
     */
    Uri createVideoUri(
            String displayName,
            String mimeType,
            String relativePath,
            long createTime
    );

    // =======
    // = 音频 =
    // =======

    /**
     * 创建音频 Uri
     * @return 音频 Uri
     */
    Uri createAudioUri();

    /**
     * 创建音频 Uri
     * @param mimeType 资源类型
     * @return 音频 Uri
     */
    Uri createAudioUri(String mimeType);

    /**
     * 创建音频 Uri
     * @param mimeType     资源类型
     * @param relativePath 存储目录 ( 如 DCIM、Video、Pictures、Music、Download )
     * @return 音频 Uri
     */
    Uri createAudioUri(
            String mimeType,
            String relativePath
    );

    /**
     * 创建音频 Uri
     * @param displayName  显示名 ( 无需后缀, 根据 mimeType 决定, 如果 mimeType 用了 xxx/* 则需指定后缀 )
     * @param mimeType     资源类型
     * @param relativePath 存储目录 ( 如 DCIM、Video、Pictures、Music、Download )
     * @return 音频 Uri
     */
    Uri createAudioUri(
            String displayName,
            String mimeType,
            String relativePath
    );

    /**
     * 创建音频 Uri
     * @param displayName  显示名 ( 无需后缀, 根据 mimeType 决定, 如果 mimeType 用了 xxx/* 则需指定后缀 )
     * @param mimeType     资源类型
     * @param relativePath 存储目录 ( 如 DCIM、Video、Pictures、Music、Download )
     * @param createTime   创建时间
     * @return 音频 Uri
     */
    Uri createAudioUri(
            String displayName,
            String mimeType,
            String relativePath,
            long createTime
    );

    // ============
    // = Download =
    // ============

    /**
     * 创建 Download Uri
     * @param displayName 显示名 ( 需后缀 )
     * @return Download Uri
     */
    Uri createDownloadUri(String displayName);

    /**
     * 创建 Download Uri
     * @param displayName 显示名 ( 无需后缀, 根据 mimeType 决定, 如果 mimeType 用了 xxx/* 则需指定后缀 )
     * @param mimeType    资源类型
     * @return Download Uri
     */
    Uri createDownloadUri(
            String displayName,
            String mimeType
    );

    /**
     * 创建 Download Uri
     * @param displayName  显示名 ( 无需后缀, 根据 mimeType 决定, 如果 mimeType 用了 xxx/* 则需指定后缀 )
     * @param mimeType     资源类型
     * @param relativePath 存储目录 ( 如 DCIM、Video、Pictures、Music、Download )
     * @return Download Uri
     */
    Uri createDownloadUri(
            String displayName,
            String mimeType,
            String relativePath
    );

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
    Uri createDownloadUri(
            String displayName,
            String mimeType,
            String relativePath,
            long createTime
    );

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
    Uri createMediaUri(
            Uri uri,
            String displayName,
            String mimeType,
            String relativePath
    );

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
    Uri createMediaUri(
            Uri uri,
            String displayName,
            String mimeType,
            String relativePath,
            long createTime
    );

    // ========
    // = File =
    // ========

    /**
     * 通过 File Path 创建 Uri
     * @param fileName 文件名
     * @return File Uri
     */
    Uri createUriByPath(String fileName);

    /**
     * 通过 File Path 创建 Uri
     * @param fileName 文件名
     * @param filePath 文件路径
     * @return File Uri
     */
    Uri createUriByPath(
            String fileName,
            String filePath
    );

    /**
     * 通过 File Path 创建 Uri
     * @param filePath 文件路径
     * @return File Uri
     */
    Uri createUriByFile(String filePath);

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
    Uri createUriByFile(File file);

    // =

    /**
     * 插入一张图片
     * @param uri      Media Uri
     * @param inputUri 输入 Uri ( 待存储文件 Uri )
     * @param format   如 Bitmap.CompressFormat.PNG
     * @param quality  质量
     * @return {@code true} success, {@code false} fail
     */
    boolean insertImage(
            Uri uri,
            Uri inputUri,
            Bitmap.CompressFormat format,
            @IntRange(from = 0, to = 100) int quality
    );

    /**
     * 插入一张图片
     * @param uri      Media Uri
     * @param inputUri 输入 Uri ( 待存储文件 Uri )
     * @return {@code true} success, {@code false} fail
     */
    boolean insertImage(
            Uri uri,
            Uri inputUri
    );

    /**
     * 插入一条视频
     * @param uri      Media Uri
     * @param inputUri 输入 Uri ( 待存储文件 Uri )
     * @return {@code true} success, {@code false} fail
     */
    boolean insertVideo(
            Uri uri,
            Uri inputUri
    );

    /**
     * 插入一条音频
     * @param uri      Media Uri
     * @param inputUri 输入 Uri ( 待存储文件 Uri )
     * @return {@code true} success, {@code false} fail
     */
    boolean insertAudio(
            Uri uri,
            Uri inputUri
    );

    /**
     * 插入一条多媒体资源
     * @param uri      Media Uri
     * @param inputUri 输入 Uri ( 待存储文件 Uri )
     * @return {@code true} success, {@code false} fail
     */
    boolean insertMedia(
            Uri uri,
            Uri inputUri
    );

    /**
     * 插入一条多媒体资源
     * @param uri         Media Uri
     * @param inputStream {@link InputStream}
     * @return {@code true} success, {@code false} fail
     */
    boolean insertMedia(
            Uri uri,
            InputStream inputStream
    );

    /**
     * 插入一条多媒体资源
     * @param outputStream {@link OutputStream}
     * @param inputStream  {@link InputStream}
     * @return {@code true} success, {@code false} fail
     */
    boolean insertMedia(
            OutputStream outputStream,
            InputStream inputStream
    );

    // =

    /**
     * 插入一条多媒体资源
     * @param uri      Media Uri
     * @param filePath 待存储文件路径
     * @return {@code true} success, {@code false} fail
     */
    boolean insertMedia(
            Uri uri,
            String filePath
    );

    /**
     * 插入一条多媒体资源
     * @param uri  Media Uri
     * @param file 待存储文件
     * @return {@code true} success, {@code false} fail
     */
    boolean insertMedia(
            Uri uri,
            File file
    );

    // =

    /**
     * 插入一条多媒体资源
     * @param uri      Media Uri
     * @param drawable 待保存图片
     * @return {@code true} success, {@code false} fail
     */
    boolean insertMedia(
            Uri uri,
            Drawable drawable
    );

    /**
     * 插入一条多媒体资源
     * @param uri      Media Uri
     * @param drawable 待保存图片
     * @param format   如 Bitmap.CompressFormat.PNG
     * @return {@code true} success, {@code false} fail
     */
    boolean insertMedia(
            Uri uri,
            Drawable drawable,
            Bitmap.CompressFormat format
    );

    /**
     * 插入一条多媒体资源
     * @param uri      Media Uri
     * @param drawable 待保存图片
     * @param format   如 Bitmap.CompressFormat.PNG
     * @param quality  质量
     * @return {@code true} success, {@code false} fail
     */
    boolean insertMedia(
            Uri uri,
            Drawable drawable,
            Bitmap.CompressFormat format,
            @IntRange(from = 0, to = 100) int quality
    );

    // =

    /**
     * 插入一条多媒体资源
     * @param uri    Media Uri
     * @param bitmap 待保存图片
     * @return {@code true} success, {@code false} fail
     */
    boolean insertMedia(
            Uri uri,
            Bitmap bitmap
    );

    /**
     * 插入一条多媒体资源
     * @param uri    Media Uri
     * @param bitmap 待保存图片
     * @param format 如 Bitmap.CompressFormat.PNG
     * @return {@code true} success, {@code false} fail
     */
    boolean insertMedia(
            Uri uri,
            Bitmap bitmap,
            Bitmap.CompressFormat format
    );

    /**
     * 插入一条多媒体资源
     * @param uri     Media Uri
     * @param bitmap  待保存图片
     * @param format  如 Bitmap.CompressFormat.PNG
     * @param quality 质量
     * @return {@code true} success, {@code false} fail
     */
    boolean insertMedia(
            Uri uri,
            Bitmap bitmap,
            Bitmap.CompressFormat format,
            @IntRange(from = 0, to = 100) int quality
    );

    // ====================
    // = Android 11 ( R ) =
    // ====================

    /**
     * 是否获得 MANAGE_EXTERNAL_STORAGE 权限
     * @return {@code true} yes, {@code false} no
     */
    boolean isExternalStorageManager();

    /**
     * 检查是否有 MANAGE_EXTERNAL_STORAGE 权限并跳转设置页面
     * @return {@code true} yes, {@code false} no
     */
    boolean checkExternalStorageAndIntentSetting();

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
    PendingIntent createWriteRequest(Collection<Uri> uris);

    /**
     * 获取用户将设备上指定的媒体文件标记为收藏的请求
     * <pre>
     *     对该文件具有读取访问权限的任何应用都可以看到用户已将该文件标记为收藏
     * </pre>
     * @param uris     待请求 Uri 集
     * @param favorite 是否喜欢
     * @return {@link PendingIntent}
     */
    PendingIntent createFavoriteRequest(
            Collection<Uri> uris,
            boolean favorite
    );

    /**
     * 获取用户将指定的媒体文件放入设备垃圾箱的请求
     * <pre>
     *     垃圾箱中的内容会在系统定义的时间段后被永久删除
     * </pre>
     * @param uris    待请求 Uri 集
     * @param trashed 是否遗弃
     * @return {@link PendingIntent}
     */
    PendingIntent createTrashRequest(
            Collection<Uri> uris,
            boolean trashed
    );

    /**
     * 获取用户立即永久删除指定的媒体文件 ( 而不是先将其放入垃圾箱 ) 的请求
     * @param uris 待请求 Uri 集
     * @return {@link PendingIntent}
     */
    PendingIntent createDeleteRequest(Collection<Uri> uris);
}