package dev.utils.app;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.File;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: ContentResolver 工具类
 * @author Ttt
 * <pre>
 *      @see <a href="https://www.cnblogs.com/Sharley/p/7942142.html"/>
 *      @see <a href="https://blog.csdn.net/lemon_blue/article/details/52353851"/>
 * </pre>
 */
public final class ContentResolverUtils {

    private ContentResolverUtils() {
    }

    // 日志 TAG
    private static final String TAG = ContentResolverUtils.class.getSimpleName();

    /**
     * 通知刷新本地资源
     * @param file
     * @return
     */
    public static boolean notifyMediaStore(final File file) {
        if (file != null) {
            try {
                // 最后通知图库扫描更新
                DevUtils.getContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
                // 通知成功
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "notifyMediaStore");
            }
        }
        return false;
    }

    /**
     * 添加图片到系统相册(包含原图、相册图, 会存在两张) - 想要一张, 直接调用 notifyMediaStore()
     * @param file
     * @param fileName
     * @param isNotify
     * @return
     */
    public static boolean insertImageIntoMediaStore(final File file, final String fileName, final boolean isNotify) {
        if (file != null) {
            try {
                // 添加到相册
                MediaStore.Images.Media.insertImage(DevUtils.getContext().getContentResolver(), file.getAbsolutePath(), TextUtils.isEmpty(fileName) ? file.getName() : fileName, null);
                if (isNotify) {
                    notifyMediaStore(file);
                }
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "insertImageIntoMediaStore");
            }
        }
        return false;
    }

    /**
     * 添加视频到系统相册
     * @param file
     * @return
     */
    public static boolean insertVideoIntoMediaStore(final File file) {
        return insertIntoMediaStore(file, -1, true, "video/3gp");
    }

    /**
     * 保存到系统相册
     * @param file
     * @param createTime
     * @param isVideo
     * @param mimeType
     */
    public static boolean insertIntoMediaStore(final File file, long createTime, final boolean isVideo, final String mimeType) {
        if (file != null && !TextUtils.isEmpty(mimeType)) {
            try {
                ContentResolver mContentResolver = DevUtils.getContext().getContentResolver();
                // 防止创建时间为null
                if (createTime <= 0)
                    createTime = System.currentTimeMillis();

                ContentValues values = new ContentValues();
                values.put(MediaStore.MediaColumns.TITLE, file.getName());
                values.put(MediaStore.MediaColumns.DISPLAY_NAME, file.getName());
                // 值一样，但是还是用常量区分对待
                values.put(isVideo ? MediaStore.Video.VideoColumns.DATE_TAKEN : MediaStore.Images.ImageColumns.DATE_TAKEN, createTime);
                values.put(MediaStore.MediaColumns.DATE_MODIFIED, System.currentTimeMillis());
                values.put(MediaStore.MediaColumns.DATE_ADDED, System.currentTimeMillis());
                if (!isVideo)
                    values.put(MediaStore.Images.ImageColumns.ORIENTATION, 0);
                // 文件路径
                values.put(MediaStore.MediaColumns.DATA, file.getAbsolutePath());
                // 文件大小
                values.put(MediaStore.MediaColumns.SIZE, file.length());
                // 文件类型
                values.put(MediaStore.MediaColumns.MIME_TYPE, mimeType);
                // 生成所属的URI资源
                Uri uri = mContentResolver.insert(isVideo ? MediaStore.Video.Media.EXTERNAL_CONTENT_URI : MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                // 最后通知图库更新
                DevUtils.getContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
                // 表示成功
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "insertIntoMediaStore");
            }
        }
        return false;
    }
}
