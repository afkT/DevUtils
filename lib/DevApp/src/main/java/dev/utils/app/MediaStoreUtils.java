package dev.utils.app;

import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;

import java.io.InputStream;

import dev.DevUtils;
import dev.utils.LogPrintUtils;
import dev.utils.app.image.BitmapUtils;
import dev.utils.common.ConvertUtils;

/**
 * detail: MediaStore 工具类
 * <pre>
 *     通过 FileProvider Uri 是无法进行读取 ( MediaStore Cursor 是无法进行保存的 )
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
