package dev.utils.app;

import android.content.ContentResolver;
import android.content.ContentUris;
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
 *     <p></p>
 *     例: content://xxxx/49
 *     可通过 {@link ContentUris#parseId(Uri)} 获取 49
 * </pre>
 */
public final class ContentResolverUtils {

    private ContentResolverUtils() {
    }

    // 日志 TAG
    private static final String TAG = ContentResolverUtils.class.getSimpleName();

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
    private static final String VOLUME_EXTERNAL = PathUtils.EXTERNAL;
    // 文件 URI
    public static final Uri FILES_URI = MediaStore.Files.getContentUri(VOLUME_EXTERNAL);

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
     * @param file       文件
     * @param mediaQuery 多媒体查询抽象类
     * @return Media 信息
     */
    public static String[] mediaQuery(final File file, final MediaQuery mediaQuery) {
        return mediaQuery(FILES_URI, file, mediaQuery);
    }

    /**
     * 通过 File 获取 Media 信息
     * @param uri        MediaStore.media-type.Media.EXTERNAL_CONTENT_URI
     * @param file       文件
     * @param mediaQuery 多媒体查询抽象类
     * @return Media 信息
     */
    public static String[] mediaQuery(final Uri uri, final File file, final MediaQuery mediaQuery) {
        return mediaQuery(uri, FileUtils.getAbsolutePath(file), mediaQuery);
    }

    /**
     * 通过 File Path 获取 Media Uri
     * @param filePath   文件路径
     * @param mediaQuery 多媒体查询抽象类
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
                    mediaQuery.getProjection(uri, filePath),
                    mediaQuery.getSelection(uri, filePath),
                    mediaQuery.getSelectionArgs(uri, filePath),
                    mediaQuery.getSortOrder(uri, filePath)
            );
            if (cursor != null && cursor.moveToFirst()) {
                return mediaQuery.getResult(uri, filePath, cursor);
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
    // 多媒体查询信息处理
    public static final MediaQueryInfo MEDIA_QUERY_INFO = new MediaQueryInfo();
    // 多媒体查询信息处理 Uri
    public static final MediaQueryInfoUri MEDIA_QUERY_INFO_URI = new MediaQueryInfoUri();

    /**
     * detail: 多媒体查询抽象类
     * @author Ttt
     */
    public static abstract class MediaQuery {

        /**
         * 获取查询结果
         * @param uri      Uri
         * @param filePath 文件路径
         * @param cursor   Cursor
         * @return 查询结果
         */
        public abstract String[] getResult(Uri uri, String filePath, Cursor cursor);

        // ============
        // = 查询条件 =
        // ============

        /**
         * 获取查询的字段
         * @param uri      Uri
         * @param filePath 文件路径
         * @return 查询的字段
         */
        public abstract String[] getProjection(Uri uri, String filePath);

        /**
         * 获取查询的条件
         * @param uri      Uri
         * @param filePath 文件路径
         * @return 查询的条件
         */
        public abstract String getSelection(Uri uri, String filePath);

        /**
         * 获取查询条件的参数
         * @param uri      Uri
         * @param filePath 文件路径
         * @return 查询条件的参数
         */
        public abstract String[] getSelectionArgs(Uri uri, String filePath);

        /**
         * 获取排序方式
         * @param uri      Uri
         * @param filePath 文件路径
         * @return 排序方式
         */
        public String getSortOrder(Uri uri, String filePath) {
            return null;
        }
    }

    /**
     * detail: 多媒体查询获取 Uri 处理
     * @author Ttt
     */
    private static class MediaQueryUri extends MediaQuery {
        @Override
        public String[] getResult(Uri uri, String filePath, Cursor cursor) {
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
        public String[] getProjection(Uri uri, String filePath) {
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
        public String getSelection(Uri uri, String filePath) {
            return MediaStore.Files.FileColumns.DATA + "=?";
        }

        @Override
        public String[] getSelectionArgs(Uri uri, String filePath) {
            return new String[]{filePath};
        }
    }

    /**
     * detail: 多媒体查询信息处理
     * @author Ttt
     */
    public static class MediaQueryInfo extends MediaQuery {
        @Override
        public String[] getResult(Uri uri, String filePath, Cursor cursor) {
            String[] result = new String[8];
            long rowId = cursor.getLong(cursor.getColumnIndex(MediaStore.Files.FileColumns._ID));
            int width = cursor.getInt(cursor.getColumnIndex(MediaStore.Files.FileColumns.WIDTH));
            int height = cursor.getInt(cursor.getColumnIndex(MediaStore.Files.FileColumns.HEIGHT));
            String mimeType = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.MIME_TYPE));
            long mediaType = cursor.getInt(cursor.getColumnIndex(MediaStore.Files.FileColumns.MEDIA_TYPE));
            String dateAdded = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATE_ADDED));
            String dateModified = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATE_MODIFIED));
            long duration = 0;
            if (mediaType == MediaStore.Files.FileColumns.MEDIA_TYPE_AUDIO
                    || mediaType == MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO) {
                duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Files.FileColumns.DURATION));
            }

            result[0] = String.valueOf(rowId);
            result[1] = String.valueOf(width);
            result[2] = String.valueOf(height);
            result[3] = mimeType;
            result[4] = String.valueOf(mediaType);
            result[5] = dateAdded;
            result[6] = dateModified;
            result[7] = String.valueOf(duration);
            return result;
        }

        @Override
        public String[] getProjection(Uri uri, String filePath) {
            return new String[]{
                    MediaStore.Files.FileColumns._ID, // ID
                    MediaStore.Files.FileColumns.WIDTH, // 宽度
                    MediaStore.Files.FileColumns.HEIGHT, // 高度
                    MediaStore.Files.FileColumns.MIME_TYPE, // 文件类型
                    MediaStore.Files.FileColumns.MEDIA_TYPE, // 资源类型
                    MediaStore.Files.FileColumns.DATE_ADDED, // 添加时间
                    MediaStore.Files.FileColumns.DATE_MODIFIED, // 修改时间
                    MediaStore.Files.FileColumns.DURATION, // 多媒体时长
            };
        }

        @Override
        public String getSelection(Uri uri, String filePath) {
            return MediaStore.Files.FileColumns.DATA + "=?";
        }

        @Override
        public String[] getSelectionArgs(Uri uri, String filePath) {
            return new String[]{filePath};
        }
    }

    /**
     * detail: 多媒体查询信息处理 Uri
     * @author Ttt
     */
    public static class MediaQueryInfoUri extends MediaQueryInfo {

        @Override
        public String getSelection(Uri uri, String filePath) {
//            return MediaStore.Files.FileColumns._ID + "=?";
            return null;
        }

        @Override
        public String[] getSelectionArgs(Uri uri, String filePath) {
//            return new String[]{ContentUris.parseId(uri) + ""};
            return null;
        }
    }
}