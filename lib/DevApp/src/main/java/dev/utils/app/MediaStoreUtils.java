package dev.utils.app;

import android.content.ContentValues;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.File;
import java.io.InputStream;

import dev.DevUtils;
import dev.utils.LogPrintUtils;
import dev.utils.app.image.BitmapUtils;
import dev.utils.common.ConvertUtils;
import dev.utils.common.FileUtils;

/**
 * detail: MediaStore 工具类
 * <pre>
 *     通过 FileProvider Uri 是无法进行读取 ( MediaStore Cursor 无法扫描 内部存储、外部存储 ( 私有目录 ) )
 *     需通过 {@link ResourceUtils#openInputStream(Uri)} 获取并保存到 {@link PathUtils#getAppExternal()}  外部存储 ( 私有目录 ) 中
 *     调用此类方法传入 filePath 获取所需信息 ( 私有目录不需要兼容 Android Q )
 * </pre>
 * @author Ttt
 */
public final class MediaStoreUtils {

    private MediaStoreUtils() {
    }

    // 日志 TAG
    private static final String TAG = MediaStoreUtils.class.getSimpleName();

    // ============
    // = 通知相册 =
    // ============

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
        // Android Q 以后的版本需要通过 MediaStore 插入数据
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            if (file != null) {
                try {
                    // 通知图库扫描更新
                    return AppUtils.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "notifyMediaStore");
                }
            }
        }
        return false;
    }

    // ============
    // = 插入数据 =
    // ============

    // 图片类型
    public final static String MIME_TYPE_IMAGE = "image/jpeg";
    // 视频类型
    public final static String MIME_TYPE_VIDEO = "video/mp4";
    // 音频类型
    public final static String MIME_TYPE_AUDIO = "audio/mpeg";

    /**
     * 创建一条图片 Uri
     * @return 图片 Uri
     */
    public static Uri createImageUri() {
        long createTime = System.currentTimeMillis();
        return createImageUri("IMG_" + createTime, createTime);
    }

    /**
     * 创建一条图片 Uri
     * @param fileName 文件名
     * @return 图片 Uri
     */
    public static Uri createImageUri(final String fileName) {
        return createImageUri(fileName, System.currentTimeMillis());
    }

    /**
     * 创建一条图片 Uri
     * @param fileName 文件名
     * @param createTime 创建时间
     * @return 图片 Uri
     */
    public static Uri createImageUri(final String fileName, final long createTime) {
        boolean isAndroidQ = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q);

        ContentValues values = new ContentValues(isAndroidQ ? 4 : 3);
        values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName); // 文件名
        values.put(MediaStore.Images.Media.DATE_TAKEN, createTime); // 创建时间
        values.put(MediaStore.Images.Media.MIME_TYPE, MIME_TYPE_IMAGE);
        if (isAndroidQ) {
            // MediaStore 会根据文件的 RELATIVE_PATH 去自动进行分类存储
            values.put(MediaStore.Images.Media.RELATIVE_PATH, PathUtils.DCIM);
        }
        try {
            return ResourceUtils.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "createImageUri");
        }
        return null;
    }

    // ============
    // = 资源信息 =
    // ============

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
    public static long getVideoDuration(final String filePath, final boolean isAndroidQ) {
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
    public static int[] getVideoSize(final String filePath, final boolean isAndroidQ) {
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
    public static int[] getImageWidthHeight(final String filePath, final boolean isAndroidQ) {
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
            InputStream inputStream = ResourceUtils.openInputStream(uri);
            return BitmapUtils.getBitmapWidthHeight(inputStream);
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
}
