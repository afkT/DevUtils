package dev.utils.app;

import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;

import dev.DevUtils;
import dev.utils.LogPrintUtils;
import dev.utils.common.ConvertUtils;

/**
 * detail: MediaStore 工具类
 * @author Ttt
 */
public final class MediaStoreUtils {

    private MediaStoreUtils() {
    }

    // 日志 TAG
    private static final String TAG = MediaStoreUtils.class.getSimpleName();

    // ========
    // = 视频 =
    // ========

    /**
     * 获取本地视频时长
     * @param uri Video Uri
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

    /**
     * 获取本地视频时长
     * @param filePath 文件路径
     * @return 本地视频时长
     */
    public static long getVideoDuration(final String filePath) {
        if (TextUtils.isEmpty(filePath)) return 0;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
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
     * 获取本地视频宽高
     * @param filePath 文件路径
     * @return 本地视频宽高 0 = 宽, 1 = 高
     */
    public static int[] getVideoSize(final String filePath) {
        int[] size = new int[2];
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
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
}
