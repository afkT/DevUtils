package dev.utils.app;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.File;

import dev.utils.LogPrintUtils;
import dev.utils.common.ArrayUtils;
import dev.utils.common.CloseUtils;
import dev.utils.common.ConvertUtils;
import dev.utils.common.FileUtils;

/**
 * detail: ContentResolver 工具类
 * @author Ttt
 * <pre>
 *     @see <a href="https://blog.csdn.net/lemon_blue/article/details/52353851"/>
 * </pre>
 */
public final class ContentResolverUtils {

    private ContentResolverUtils() {
    }

    // 日志 TAG
    private static final String TAG = ContentResolverUtils.class.getSimpleName();

    /**
     * 通知刷新本地资源
     * <pre>
     *     注意事项: 部分手机 ( 如小米 ) 通知文件地址层级过深, 将会并入相册文件夹中
     *     尽量放在 SDCrad/XXX/xx.jpg 层级中
     * </pre>
     * @param file 文件
     * @return {@code true} success, {@code false} fail
     */
    public static boolean notifyMediaStore(final File file) {
        if (file != null) {
            try {
                // 通知图库扫描更新
                return AppUtils.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "notifyMediaStore");
            }
        }
        return false;
    }

    /**
     * 添加图片到系统相册 ( 包含原图、相册图, 会存在两张 ) - 想要一张, 直接调用 notifyMediaStore()
     * @param file     文件
     * @param fileName 文件名
     * @param isNotify 是否广播通知图库扫描
     * @return {@code true} success, {@code false} fail
     */
    public static boolean insertImageIntoMediaStore(final File file, final String fileName, final boolean isNotify) {
        if (file != null) {
            try {
                // 添加到相册
                MediaStore.Images.Media.insertImage(ResourceUtils.getContentResolver(),
                        file.getAbsolutePath(), TextUtils.isEmpty(fileName) ? file.getName() : fileName, null);
                // 通知图库扫描更新
                if (isNotify) notifyMediaStore(file);
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "insertImageIntoMediaStore");
            }
        }
        return false;
    }

    // =

    /**
     * 添加视频到系统相册
     * @param file 文件
     * @return {@code true} success, {@code false} fail
     */
    public static boolean insertVideoIntoMediaStore(final File file) {
        return insertIntoMediaStore(file, -1, true, "video/3gp");
    }

    /**
     * 保存到系统相册
     * @param file       文件
     * @param createTime 创建时间
     * @param isVideo    是否视频
     * @param mimeType   资源类型
     * @return {@code true} success, {@code false} fail
     */
    public static boolean insertIntoMediaStore(final File file, final long createTime, final boolean isVideo, final String mimeType) {
        if (file != null && !TextUtils.isEmpty(mimeType)) {
            try {
                ContentResolver resolver = ResourceUtils.getContentResolver();
                // 插入时间
                long insertTime = createTime;
                // 防止创建时间为 null
                if (insertTime <= 0)
                    insertTime = System.currentTimeMillis();

                ContentValues values = new ContentValues();
                values.put(MediaStore.MediaColumns.TITLE, file.getName());
                values.put(MediaStore.MediaColumns.DISPLAY_NAME, file.getName());
                // 值一样, 但是还是用常量区分对待
                values.put(isVideo ? MediaStore.Video.VideoColumns.DATE_TAKEN : MediaStore.Images.ImageColumns.DATE_TAKEN, insertTime);
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
                // 生成所属的 URI 资源
                Uri uri = resolver.insert(isVideo ? MediaStore.Video.Media.EXTERNAL_CONTENT_URI : MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                // 最后通知图库更新
                return AppUtils.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "insertIntoMediaStore");
            }
        }
        return false;
    }

    // ==========
    // = Cursor =
    // ==========

    // 卷名
    private static final String VOLUME_EXTERNAL = "external";
    // 文件搜索 URI
    private static final Uri FILES_URI = MediaStore.Files.getContentUri(VOLUME_EXTERNAL);

    /**
     * 获取 Uri Cursor
     * <pre>
     *     // 获取指定类型文件
     *     uri => MediaStore.media-type.Media.EXTERNAL_CONTENT_URI
     *     // 全部文件搜索
     *     MediaStore.Files.getContentUri("external")
     * </pre>
     * @param uri           {@link Uri}
     * @param projection    查询的字段
     * @param selection     查询的条件
     * @param selectionArgs 查询条件的参数
     * @param sortOrder     排序方式
     * @return {@link Cursor}
     */
    public static Cursor query(final Uri uri, final String[] projection, final String selection,
                               final String[] selectionArgs, final String sortOrder) {
        try {
            return ResourceUtils.getContentResolver().query(uri, projection, selection, selectionArgs, sortOrder);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "query");
        }
        return null;
    }

    /**
     * 通过 File 获取 Media Uri
     * @param file 文件
     * @return 指定文件 {@link Uri}
     */
    public static Uri getMediaUri(final File file) {
        return getMediaUri(FILES_URI, file);
    }

    /**
     * 通过 File 获取 Media Uri
     * @param uri  MediaStore.media-type.Media.EXTERNAL_CONTENT_URI
     * @param file 文件
     * @return 指定文件 {@link Uri}
     */
    public static Uri getMediaUri(final Uri uri, final File file) {
        return getMediaUri(uri, FileUtils.getAbsolutePath(file));
    }

    /**
     * 通过 File Path 获取 Media Uri
     * @param filePath 文件路径
     * @return 指定文件 {@link Uri}
     */
    public static Uri getMediaUri(final String filePath) {
        return getMediaUri(FILES_URI, filePath);
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
    public static Uri getMediaUri(final Uri uri, final String filePath) {
        String[] result = mediaQuery(uri, filePath, MEDIA_QUERY_URI);
        if (ArrayUtils.isLength(result, 2)) {
            try {
                // 返回外部存储 ( 公开目录 ) SDCard 文件地址获取对应的 Uri content://
                return MediaStore.Files.getContentUri(result[1], ConvertUtils.toLong(result[0]));
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getMediaUri - " + filePath);
            }
        }
        return null;
    }

    // =

    /**
     * 通过 File 获取 Media 信息
     * @param file 文件
     * @return Media 信息
     */
    public static String[] mediaQuery(final File file, final MediaQuery mediaQuery) {
        return mediaQuery(FILES_URI, file, mediaQuery);
    }

    /**
     * 通过 File 获取 Media 信息
     * @param uri  MediaStore.media-type.Media.EXTERNAL_CONTENT_URI
     * @param file 文件
     * @return Media 信息
     */
    public static String[] mediaQuery(final Uri uri, final File file, final MediaQuery mediaQuery) {
        return mediaQuery(uri, FileUtils.getAbsolutePath(file), mediaQuery);
    }

    /**
     * 通过 File Path 获取 Media Uri
     * @param filePath 文件路径
     * @return Media 信息
     */
    public static String[] mediaQuery(final String filePath, final MediaQuery mediaQuery) {
        return mediaQuery(FILES_URI, filePath, mediaQuery);
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
    public static String[] mediaQuery(final Uri uri, final String filePath, final MediaQuery mediaQuery) {
        if (uri == null || TextUtils.isEmpty(filePath) && mediaQuery != null) return null;
        Cursor cursor = null;
        try {
            cursor = query(uri,
                    mediaQuery.getProjection(filePath),
                    mediaQuery.getSelection(filePath),
                    mediaQuery.getSelectionArgs(filePath),
                    mediaQuery.getSortOrder(filePath)
            );
            if (cursor != null && cursor.moveToFirst()) {
                return mediaQuery.getResult(filePath, cursor);
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "mediaQuery - " + filePath);
        } finally {
            CloseUtils.closeIOQuietly(cursor);
        }
        return null;
    }

    // ===============
    // = Media Query =
    // ===============

    // 多媒体查询获取 Uri 处理
    private static final MediaQueryUri MEDIA_QUERY_URI = new MediaQueryUri();

    /**
     * detail: 多媒体查询抽象类
     * @author Ttt
     */
    public static abstract class MediaQuery {

        /**
         * 获取查询结果
         * @param filePath 文件路径
         * @param cursor   Cursor
         * @return 查询结果
         */
        public abstract String[] getResult(String filePath, Cursor cursor);

        // ============
        // = 查询条件 =
        // ============

        /**
         * 获取查询的字段
         * @param filePath 文件路径
         * @return 查询的字段
         */
        public abstract String[] getProjection(String filePath);

        /**
         * 获取查询的条件
         * @param filePath 文件路径
         * @return 查询的条件
         */
        public abstract String getSelection(String filePath);

        /**
         * 获取查询条件的参数
         * @param filePath 文件路径
         * @return 查询条件的参数
         */
        public abstract String[] getSelectionArgs(String filePath);

        /**
         * 获取排序方式
         * @param filePath 文件路径
         * @return 排序方式
         */
        public String getSortOrder(String filePath) {
            return null;
        }
    }

    /**
     * detail: 多媒体查询获取 Uri 处理
     * @author Ttt
     */
    private static class MediaQueryUri extends MediaQuery {
        @Override
        public String[] getResult(String filePath, Cursor cursor) {
            String[] result = new String[2];
            long rowId = cursor.getLong(cursor.getColumnIndex(MediaStore.Files.FileColumns._ID));
            String volumeName = VOLUME_EXTERNAL;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                volumeName = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.VOLUME_NAME));
            }
            result[0] = String.valueOf(rowId);
            result[1] = volumeName;
            return result;
        }

        @Override
        public String[] getProjection(String filePath) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                return new String[]{
                        MediaStore.Files.FileColumns._ID,
                        MediaStore.Files.FileColumns.VOLUME_NAME
                };
            } else {
                return new String[]{MediaStore.Files.FileColumns._ID};
            }
        }

        @Override
        public String getSelection(String filePath) {
            return MediaStore.Files.FileColumns.DATA + "=?";
        }

        @Override
        public String[] getSelectionArgs(String filePath) {
            return new String[]{filePath};
        }
    }
}