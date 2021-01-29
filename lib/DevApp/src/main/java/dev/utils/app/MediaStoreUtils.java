package dev.utils.app;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.text.TextUtils;

import androidx.annotation.IntRange;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;

import dev.DevUtils;
import dev.utils.LogPrintUtils;
import dev.utils.app.image.BitmapUtils;
import dev.utils.app.image.ImageUtils;
import dev.utils.common.CloseUtils;
import dev.utils.common.ConvertUtils;
import dev.utils.common.DateUtils;
import dev.utils.common.FileIOUtils;
import dev.utils.common.FileUtils;

/**
 * detail: MediaStore 工具类
 * @author Ttt
 * <pre>
 *     通过 FileProvider Uri 是无法进行读取 ( MediaStore Cursor 无法扫描 内部存储、外部存储 ( 私有目录 ) )
 *     需通过 {@link ResourceUtils#openInputStream(Uri)} 获取并保存到 {@link PathUtils#getAppExternal()}  外部存储 ( 私有目录 ) 中
 *     调用此类方法传入 filePath 获取所需信息 ( 私有目录不需要兼容 Android Q )
 *     <p></p>
 *     MimeType
 *     @see <a href="https://www.jianshu.com/p/f3fcf033be5c"/>
 *     存储后缀根据 MIME_TYPE 决定, 值类型 {@link libcore.net.MimeUtils}
 *     @see <a href="https://www.androidos.net.cn/android/9.0.0_r8/xref/libcore/luni/src/main/java/libcore/net/MimeUtils.java"/>
 *     <p></p>
 *     访问下载内容 ( 文档和电子书籍 ) 加载系统的文件选择器
 *     {@link IntentUtils#getOpenDocumentIntent()}
 *     使用存储访问框架打开文件 {@link ResourceUtils#openInputStream(Uri)}
 *     @see <a href="https://developer.android.google.cn/guide/topics/providers/document-provider#java"/>
 * </pre>
 */
public final class MediaStoreUtils {

    private MediaStoreUtils() {
    }

    // 日志 TAG
    private static final String TAG = MediaStoreUtils.class.getSimpleName();

    // ===========
    // = 通知相册 =
    // ===========

    /**
     * 通知刷新本地资源
     * @param filePath 文件路径
     * @return {@code true} success, {@code false} fail
     */
    public static boolean notifyMediaStore(final String filePath) {
        return notifyMediaStore(FileUtils.getFile(filePath));
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
     */
    public static boolean notifyMediaStore(final File file) {
//        // Android Q 以后的版本需要通过 MediaStore 插入数据
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
        if (file != null) {
            try {
                // 通知图库扫描更新
                return AppUtils.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, DevUtils.getUriForFile(file)));
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "notifyMediaStore");
            }
        }
//        }
        return false;
    }

    // ============
    // = 创建 Uri =
    // ============

    // PNG
    public static final String MIME_TYPE_IMAGE_PNG = "image/png";
    // JPEG
    public static final String MIME_TYPE_IMAGE_JPG = "image/jpeg";
    // 图片类型
    public static final String MIME_TYPE_IMAGE     = MIME_TYPE_IMAGE_PNG;
    // 视频类型
    public static final String MIME_TYPE_VIDEO     = "video/mp4";
    // 音频类型
    public static final String MIME_TYPE_AUDIO     = "audio/mpeg";
    // 图片文件夹
    public static final String RELATIVE_IMAGE_PATH = Environment.DIRECTORY_PICTURES;
    // 视频文件夹
    public static final String RELATIVE_VIDEO_PATH = Environment.DIRECTORY_DCIM + "/Video";
    // 音频文件夹
    public static final String RELATIVE_AUDIO_PATH = Environment.DIRECTORY_MUSIC;

    /**
     * 获取待显示名
     * @param prefix 前缀
     * @return DISPLAY_NAME
     */
    public static String getDisplayName(final String prefix) {
        return prefix + "_" + DateUtils.getDateNow(DateUtils.yyyyMMdd_HHmmss);
    }

    /**
     * 获取 Image 显示名
     * @return Image DISPLAY_NAME
     */
    public static String getImageDisplayName() {
        return getDisplayName("IMG");
    }

    /**
     * 获取 Video 显示名
     * @return Video DISPLAY_NAME
     */
    public static String getVideoDisplayName() {
        return getDisplayName("VID");
    }

    /**
     * 获取 Audio 显示名
     * @return Audio DISPLAY_NAME
     */
    public static String getAudioDisplayName() {
        return getDisplayName("AUD");
    }

    // ========
    // = 图片 =
    // ========

    /**
     * 创建图片 Uri
     * @return 图片 Uri
     */
    public static Uri createImageUri() {
        return createImageUri(getImageDisplayName(), System.currentTimeMillis(), MIME_TYPE_IMAGE, RELATIVE_IMAGE_PATH);
    }

    /**
     * 创建图片 Uri
     * @param mimeType 资源类型
     * @return 图片 Uri
     */
    public static Uri createImageUri(final String mimeType) {
        return createImageUri(getImageDisplayName(), System.currentTimeMillis(), mimeType, RELATIVE_IMAGE_PATH);
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
        return createImageUri(getImageDisplayName(), System.currentTimeMillis(), mimeType, relativePath);
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
        return createMediaUri(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, displayName, createTime, mimeType, relativePath);
    }

    // ========
    // = 视频 =
    // ========

    /**
     * 创建视频 Uri
     * @return 视频 Uri
     */
    public static Uri createVideoUri() {
        return createVideoUri(getVideoDisplayName(), System.currentTimeMillis(), MIME_TYPE_VIDEO, RELATIVE_VIDEO_PATH);
    }

    /**
     * 创建视频 Uri
     * @param mimeType 资源类型
     * @return 视频 Uri
     */
    public static Uri createVideoUri(final String mimeType) {
        return createVideoUri(getVideoDisplayName(), System.currentTimeMillis(), mimeType, RELATIVE_VIDEO_PATH);
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
        return createVideoUri(getVideoDisplayName(), System.currentTimeMillis(), mimeType, relativePath);
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
        return createMediaUri(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, displayName, createTime, mimeType, relativePath);
    }

    // ========
    // = 音频 =
    // ========

    /**
     * 创建音频 Uri
     * @return 音频 Uri
     */
    public static Uri createAudioUri() {
        return createAudioUri(getAudioDisplayName(), System.currentTimeMillis(), MIME_TYPE_AUDIO, RELATIVE_AUDIO_PATH);
    }

    /**
     * 创建音频 Uri
     * @param mimeType 资源类型
     * @return 音频 Uri
     */
    public static Uri createAudioUri(final String mimeType) {
        return createAudioUri(getAudioDisplayName(), System.currentTimeMillis(), mimeType, RELATIVE_AUDIO_PATH);
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
        return createAudioUri(getAudioDisplayName(), System.currentTimeMillis(), mimeType, relativePath);
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
        return createMediaUri(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, displayName, createTime, mimeType, relativePath);
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
        return createMediaUri(uri, displayName, System.currentTimeMillis(), mimeType, relativePath);
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
        boolean       isAndroidQ = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q);
        ContentValues values     = new ContentValues(isAndroidQ ? 4 : 3);
        values.put(MediaStore.Files.FileColumns.DISPLAY_NAME, displayName); // 文件名
        values.put(MediaStore.Files.FileColumns.DATE_TAKEN, createTime); // 创建时间
        values.put(MediaStore.Files.FileColumns.MIME_TYPE, mimeType); // 资源类型
        if (isAndroidQ) { // MediaStore 会根据文件的 RELATIVE_PATH 去自动进行分类存储
            values.put(MediaStore.Files.FileColumns.RELATIVE_PATH, relativePath);
        }
        try {
            return ResourceUtils.getContentResolver().insert(uri, values);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "createMediaUri");
        }
        return null;
    }

    // ===========
    // = 插入数据 =
    // ===========

    /**
     * 插入一张图片
     * <pre>
     *     Android Q 已抛弃仍可用, 推荐使用传入 Uri 方式 {@link #createImageUri}
     * </pre>
     * @param filePath 文件路径
     * @param name     保存文件名
     * @param notify   是否通知相册
     * @return {@code true} success, {@code false} fail
     */
    @Deprecated
    public static Uri insertImage(
            final String filePath,
            final String name,
            final boolean notify
    ) {
        if (filePath != null && name != null) {
            try {
                String uriString = MediaStore.Images.Media.insertImage(ResourceUtils.getContentResolver(), filePath, name, null);
                Uri    uri       = Uri.parse(uriString);
                if (notify) notifyMediaStore(UriUtils.getFilePathByUri(uri));
                return uri;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "insertImage");
            }
        }
        return null;
    }

    /**
     * 插入一张图片
     * @param uri      {@link #createImageUri} or {@link #createMediaUri}
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
        if (uri == null || inputUri == null || format == null) return false;
        OutputStream         os             = null;
        ParcelFileDescriptor fileDescriptor = null;
        try {
            os = ResourceUtils.openOutputStream(uri);
            fileDescriptor = ResourceUtils.openFileDescriptor(inputUri, "r");
            Bitmap bitmap = ImageUtils.decodeFileDescriptor(fileDescriptor.getFileDescriptor());
            return ImageUtils.saveBitmapToStream(bitmap, os, format, quality);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "insertImage");
        } finally {
            CloseUtils.closeIOQuietly(os, fileDescriptor);
        }
        return false;
    }

    // =

    /**
     * 插入一张图片
     * @param uri      {@link #createImageUri} or {@link #createMediaUri}
     * @param inputUri 输入 Uri ( 待存储文件 Uri )
     * @return {@code true} success, {@code false} fail
     */
    public static boolean insertImage(
            final Uri uri,
            final Uri inputUri
    ) {
        return insertMedia(uri, inputUri);
    }

    /**
     * 插入一条视频
     * @param uri      {@link #createVideoUri} or {@link #createMediaUri}
     * @param inputUri 输入 Uri ( 待存储文件 Uri )
     * @return {@code true} success, {@code false} fail
     */
    public static boolean insertVideo(
            final Uri uri,
            final Uri inputUri
    ) {
        return insertMedia(uri, inputUri);
    }

    /**
     * 插入一条音频
     * @param uri      {@link #createAudioUri()} or {@link #createMediaUri}
     * @param inputUri 输入 Uri ( 待存储文件 Uri )
     * @return {@code true} success, {@code false} fail
     */
    public static boolean insertAudio(
            final Uri uri,
            final Uri inputUri
    ) {
        return insertMedia(uri, inputUri);
    }

    /**
     * 插入一条多媒体资源
     * @param uri      {@link #createImageUri} or {@link #createVideoUri} or
     *                 {@link #createAudioUri()} or {@link #createMediaUri}
     * @param inputUri 输入 Uri ( 待存储文件 Uri )
     * @return {@code true} success, {@code false} fail
     */
    public static boolean insertMedia(
            final Uri uri,
            final Uri inputUri
    ) {
        if (uri == null || inputUri == null) return false;
        return insertMedia(
                ResourceUtils.openOutputStream(uri),
                ResourceUtils.openInputStream(inputUri)
        );
    }

    /**
     * 插入一条多媒体资源
     * @param uri         {@link #createImageUri} or {@link #createVideoUri} or
     *                    {@link #createAudioUri()} or {@link #createMediaUri}
     * @param inputStream {@link InputStream}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean insertMedia(
            final Uri uri,
            final InputStream inputStream
    ) {
        if (uri == null || inputStream == null) return false;
        return insertMedia(
                ResourceUtils.openOutputStream(uri), inputStream
        );
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
        return FileIOUtils.copyLarge(inputStream, outputStream) != -1;
    }

    // =

    /**
     * 插入一条多媒体资源
     * @param uri      {@link #createImageUri} or {@link #createVideoUri} or
     *                 {@link #createAudioUri()} or {@link #createMediaUri}
     * @param filePath 待存储文件路径
     * @return {@code true} success, {@code false} fail
     */
    public static boolean insertMedia(
            final Uri uri,
            final String filePath
    ) {
        return insertMedia(uri, FileUtils.getFile(filePath));
    }

    /**
     * 插入一条多媒体资源
     * @param uri  {@link #createImageUri} or {@link #createVideoUri} or
     *             {@link #createAudioUri()} or {@link #createMediaUri}
     * @param file 待存储文件
     * @return {@code true} success, {@code false} fail
     */
    public static boolean insertMedia(
            final Uri uri,
            final File file
    ) {
        return insertMedia(uri, UriUtils.getUriForFile(file));
    }

    // =

    /**
     * 插入一条多媒体资源
     * @param uri      {@link #createImageUri} or {@link #createVideoUri} or
     *                 {@link #createAudioUri()} or {@link #createMediaUri}
     * @param drawable 待保存图片
     * @return {@code true} success, {@code false} fail
     */
    public static boolean insertMedia(
            final Uri uri,
            final Drawable drawable
    ) {
        return insertMedia(uri, drawable, Bitmap.CompressFormat.PNG);
    }

    /**
     * 插入一条多媒体资源
     * @param uri      {@link #createImageUri} or {@link #createVideoUri} or
     *                 {@link #createAudioUri()} or {@link #createMediaUri}
     * @param drawable 待保存图片
     * @param format   如 Bitmap.CompressFormat.PNG
     * @return {@code true} success, {@code false} fail
     */
    public static boolean insertMedia(
            final Uri uri,
            final Drawable drawable,
            final Bitmap.CompressFormat format
    ) {
        return insertMedia(uri, drawable, format, 100);
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
    public static boolean insertMedia(
            final Uri uri,
            final Drawable drawable,
            final Bitmap.CompressFormat format,
            @IntRange(from = 0, to = 100) final int quality
    ) {
        return insertMedia(uri, ImageUtils.drawableToBitmap(drawable), format, quality);
    }

    // =

    /**
     * 插入一条多媒体资源
     * @param uri    {@link #createImageUri} or {@link #createVideoUri} or
     *               {@link #createAudioUri()} or {@link #createMediaUri}
     * @param bitmap 待保存图片
     * @return {@code true} success, {@code false} fail
     */
    public static boolean insertMedia(
            final Uri uri,
            final Bitmap bitmap
    ) {
        return insertMedia(uri, bitmap, Bitmap.CompressFormat.PNG);
    }

    /**
     * 插入一条多媒体资源
     * @param uri    {@link #createImageUri} or {@link #createVideoUri} or
     *               {@link #createAudioUri()} or {@link #createMediaUri}
     * @param bitmap 待保存图片
     * @param format 如 Bitmap.CompressFormat.PNG
     * @return {@code true} success, {@code false} fail
     */
    public static boolean insertMedia(
            final Uri uri,
            final Bitmap bitmap,
            final Bitmap.CompressFormat format
    ) {
        return insertMedia(uri, bitmap, format, 100);
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
    public static boolean insertMedia(
            final Uri uri,
            final Bitmap bitmap,
            final Bitmap.CompressFormat format,
            @IntRange(from = 0, to = 100) final int quality
    ) {
        return ImageUtils.saveBitmapToStream(
                bitmap, ResourceUtils.openOutputStream(uri), format, quality
        );
    }

    // ===========
    // = 资源信息 =
    // ===========

    /**
     * 获取本地视频时长
     * @param filePath 文件路径
     * @return 本地视频时长
     */
    public static long getVideoDuration(final String filePath) {
        return getVideoDuration(filePath, true);
    }

    /**
     * 获取本地视频时长
     * @param filePath   文件路径
     * @param isAndroidQ 是否兼容 Android Q ( 私有目录传入 false )
     * @return 本地视频时长
     */
    public static long getVideoDuration(
            final String filePath,
            final boolean isAndroidQ
    ) {
        if (TextUtils.isEmpty(filePath)) return 0;
        try {
            if (isAndroidQ && Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                return getVideoDuration(ContentResolverUtils.getMediaUri(filePath));
            }
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            mmr.setDataSource(filePath);
            return Long.parseLong(mmr.extractMetadata
                    (MediaMetadataRetriever.METADATA_KEY_DURATION));
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getVideoDuration");
            return 0;
        }
    }

    /**
     * 获取本地视频时长
     * @param uri Video Uri content://
     * @return 本地视频时长
     */
    public static long getVideoDuration(final Uri uri) {
        if (uri == null) return 0;
        try {
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            mmr.setDataSource(DevUtils.getContext(), uri);
            return Long.parseLong(mmr.extractMetadata
                    (MediaMetadataRetriever.METADATA_KEY_DURATION));
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getVideoDuration");
            return 0;
        }
    }

    // =

    /**
     * 获取本地视频宽高
     * @param filePath 文件路径
     * @return 本地视频宽高 0 = 宽, 1 = 高
     */
    public static int[] getVideoSize(final String filePath) {
        return getVideoSize(filePath, true);
    }

    /**
     * 获取本地视频宽高
     * @param filePath   文件路径
     * @param isAndroidQ 是否兼容 Android Q ( 私有目录传入 false )
     * @return 本地视频宽高 0 = 宽, 1 = 高
     */
    public static int[] getVideoSize(
            final String filePath,
            final boolean isAndroidQ
    ) {
        int[] size = new int[2];
        try {
            if (isAndroidQ && Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                return getVideoSize(ContentResolverUtils.getMediaUri(filePath));
            }
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            mmr.setDataSource(filePath);
            size[0] = ConvertUtils.toInt(mmr.extractMetadata
                    (MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));
            size[1] = ConvertUtils.toInt(mmr.extractMetadata
                    (MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getVideoSize");
        }
        return size;
    }

    /**
     * 获取本地视频宽高
     * @param uri Video Uri
     * @return 本地视频宽高 0 = 宽, 1 = 高
     */
    public static int[] getVideoSize(final Uri uri) {
        int[] size = new int[2];
        try {
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            mmr.setDataSource(DevUtils.getContext(), uri);
            size[0] = ConvertUtils.toInt(mmr.extractMetadata
                    (MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));
            size[1] = ConvertUtils.toInt(mmr.extractMetadata
                    (MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getVideoSize");
        }
        return size;
    }

    // =

    /**
     * 获取本地图片宽高
     * @param filePath 文件路径
     * @return 本地图片宽高 0 = 宽, 1 = 高
     */
    public static int[] getImageWidthHeight(final String filePath) {
        return getImageWidthHeight(filePath, true);
    }

    /**
     * 获取本地图片宽高
     * @param filePath   文件路径
     * @param isAndroidQ 是否兼容 Android Q ( 私有目录传入 false )
     * @return 本地图片宽高 0 = 宽, 1 = 高
     */
    public static int[] getImageWidthHeight(
            final String filePath,
            final boolean isAndroidQ
    ) {
        try {
            if (isAndroidQ && Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                return getImageWidthHeight(ContentResolverUtils.getMediaUri(filePath));
            }
            return BitmapUtils.getBitmapWidthHeight(filePath);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getImageWidthHeight");
        }
        return new int[]{0, 0};
    }

    /**
     * 获取本地图片宽高
     * @param uri Image Uri content://
     * @return 本地图片宽高 0 = 宽, 1 = 高
     */
    public static int[] getImageWidthHeight(final Uri uri) {
        try {
            InputStream is = ResourceUtils.openInputStream(uri);
            return BitmapUtils.getBitmapWidthHeight(is);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getImageWidthHeight");
        }
        return new int[]{0, 0};
    }

    // =

    /**
     * 获取多媒体资源信息
     * <pre>
     *     FileProvider Uri 无法进行获取
     *     根据类型调用 {@link #getVideoSize}、{@link #getVideoDuration}、{@link #getImageWidthHeight}
     *     其余信息可通过 File 直接获取
     * </pre>
     * @param filePath 文件路径
     * @return 多媒体资源信息
     */
    public static String[] getMediaInfo(final String filePath) {
        return ContentResolverUtils.mediaQuery(filePath, ContentResolverUtils.MEDIA_QUERY_INFO);
    }

    /**
     * 获取多媒体资源信息
     * <pre>
     *     FileProvider Uri 无法进行获取
     *     根据类型调用 {@link #getVideoSize}、{@link #getVideoDuration}、{@link #getImageWidthHeight}
     *     其余信息可通过 File 直接获取
     * </pre>
     * @param uri Uri content://
     * @return 多媒体资源信息
     */
    public static String[] getMediaInfo(final Uri uri) {
        return ContentResolverUtils.mediaQuery(uri, TAG, ContentResolverUtils.MEDIA_QUERY_INFO_URI);
    }

    // ===============
    // = 执行批量操作 =
    // ===============

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {
                return MediaStore.createWriteRequest(ResourceUtils.getContentResolver(), uris);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "createWriteRequest");
            }
        }
        return null;
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {
                return MediaStore.createFavoriteRequest(ResourceUtils.getContentResolver(), uris, favorite);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "createFavoriteRequest");
            }
        }
        return null;
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {
                return MediaStore.createTrashRequest(ResourceUtils.getContentResolver(), uris, trashed);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "createTrashRequest");
            }
        }
        return null;
    }

    /**
     * 获取用户立即永久删除指定的媒体文件 ( 而不是先将其放入垃圾箱 ) 的请求
     * @param uris 待请求 Uri 集
     * @return {@link PendingIntent}
     */
    public static PendingIntent createDeleteRequest(final Collection<Uri> uris) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {
                return MediaStore.createDeleteRequest(ResourceUtils.getContentResolver(), uris);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "createDeleteRequest");
            }
        }
        return null;
    }
}