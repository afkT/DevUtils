package dev.utils.app;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import java.io.File;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: Uri 工具类
 * @author Ttt
 */
public final class UriUtils {

    private UriUtils() {
    }

    // 日志 TAG
    private static final String TAG = UriUtils.class.getSimpleName();

    /**
     <provider
     android:name="android.support.v4.content.FileProvider"
     android:authorities="${applicationId}.fileProvider"
     android:exported="false"
     android:grantUriPermissions="true">
     <meta-data
     android:name="android.support.FILE_PROVIDER_PATHS"
     android:resource="@xml/file_paths" />
     </provider>
     */

    /**
     * 返回处理后的Uri, 单独传递名字, 自动添加包名 ${applicationId}
     * @param file
     * @param name
     * @return getUriForFileToName(file, " fileProvider ");
     * getUriForFile(file, "包名.fileProvider");
     */
    public static Uri getUriForFileToName(final File file, final String name) {
        try {
            String authority = DevUtils.getContext().getPackageName() + "." + name;
            return getUriForFile(file, authority);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getUriForFileToName");
        }
        return null;
    }

    /**
     * Return a content URI for a given file.
     * @param file
     * @return
     */
    public static Uri getUriForFile(final File file, final String authority) {
        if (file == null) return null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return FileProvider.getUriForFile(DevUtils.getContext(), authority, file);
        } else {
            return Uri.fromFile(file);
        }
    }

    /**
     * 通过 Uri 获取 文件路径
     * @param context
     * @param uri
     * @return
     */
    public static String getFilePathByUri(final Context context, final Uri uri) {
        String path = null;
        // 以 file:// 开头的
        if (ContentResolver.SCHEME_FILE.equals(uri.getScheme())) {
            path = uri.getPath();
            return path;
        }
        // 以 content:// 开头的，比如 content://media/extenral/images/media/17766
        if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme()) && Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    if (columnIndex > -1) {
                        path = cursor.getString(columnIndex);
                    }
                }
                cursor.close();
            }
            return path;
        }
        // 4.4及之后的 是以 content:// 开头的，比如 content://com.android.providers.media.documents/document/image%3A235700
        if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme()) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (DocumentsContract.isDocumentUri(context, uri)) {
                if (isExternalStorageDocument(uri)) {
                    // ExternalStorageProvider
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];
                    if ("primary".equalsIgnoreCase(type)) {
                        path = Environment.getExternalStorageDirectory() + "/" + split[1];
                        return path;
                    }
                } else if (isDownloadsDocument(uri)) {
                    // DownloadsProvider
                    final String id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                            Long.valueOf(id));
                    path = getDataColumn(context, contentUri, null, null);
                    return path;
                } else if (isMediaDocument(uri)) {
                    // MediaProvider
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];
                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }
                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[]{split[1]};
                    path = getDataColumn(context, contentUri, selection, selectionArgs);
                    return path;
                }
            }
        }
        return null;
    }

    private static String getDataColumn(final Context context, final Uri uri, final String selection, final String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    private static boolean isExternalStorageDocument(final Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private static boolean isDownloadsDocument(final Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private static boolean isMediaDocument(final Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
}
