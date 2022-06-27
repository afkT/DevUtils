package dev.utils.app;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.InputStream;

import dev.DevUtils;
import dev.utils.LogPrintUtils;
import dev.utils.common.CloseUtils;
import dev.utils.common.FileIOUtils;
import dev.utils.common.FileUtils;
import dev.utils.common.StringUtils;

/**
 * detail: Uri 工具类
 * @author Ttt
 * <pre>
 *     <provider
 *          android:name="android.support.v4.content.FileProvider"
 *          android:authorities="${applicationId}.fileProvider"
 *          android:exported="false"
 *          android:grantUriPermissions="true">
 *          <meta-data
 *              android:name="android.support.FILE_PROVIDER_PATHS"
 *              android:resource="@xml/file_paths"/>
 *     </provider>
 * </pre>
 */
public final class UriUtils {

    private UriUtils() {
    }

    // 日志 TAG
    private static final String TAG = UriUtils.class.getSimpleName();

    // ================
    // = FileProvider =
    // ================

    /**
     * 获取 FileProvider File Uri
     * @param file 文件
     * @return 指定文件 {@link Uri}
     */
    public static Uri getUriForFile(final File file) {
        return getUriForFile(file, DevUtils.getAuthority());
    }

    /**
     * 获取 FileProvider File Path Uri
     * @param filePath 文件路径
     * @return 指定文件 {@link Uri}
     */
    public static Uri getUriForPath(final String filePath) {
        return getUriForFile(FileUtils.getFileByPath(filePath), DevUtils.getAuthority());
    }

    /**
     * 获取 FileProvider File Path Uri ( 自动添加包名 ${applicationId} )
     * @param file         文件
     * @param fileProvider android:authorities = ${applicationId}.fileProvider
     * @return 指定文件 {@link Uri}
     */
    public static Uri getUriForFileToName(
            final File file,
            final String fileProvider
    ) {
        if (file == null || fileProvider == null) return null;
        try {
            String authority = AppUtils.getPackageName() + "." + fileProvider;
            return getUriForFile(file, authority);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getUriForFileToName");
            return null;
        }
    }

    /**
     * 获取 FileProvider File Path Uri
     * @param file      文件
     * @param authority android:authorities
     * @return 指定文件 {@link Uri}
     */
    public static Uri getUriForFile(
            final File file,
            final String authority
    ) {
        if (file == null || authority == null) return null;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                return FileProvider.getUriForFile(DevUtils.getContext(), authority, file);
            } else {
                return Uri.fromFile(file);
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getUriForFile");
            return null;
        }
    }

    /**
     * 通过 String 获取 Uri
     * @param uriString uri 路径
     * @return {@link Uri}
     */
    public static Uri getUriForString(final String uriString) {
        if (TextUtils.isEmpty(uriString)) return null;
        return Uri.parse(uriString);
    }

    /**
     * 通过 File Path 创建 Uri
     * @param filePath 文件路径
     * @return {@link Uri}
     */
    public static Uri fromFile(final String filePath) {
        return fromFile(FileUtils.getFile(filePath));
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
    public static Uri fromFile(final File file) {
        if (file != null) {
            try {
                return Uri.fromFile(file);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "fromFile");
            }
        }
        return null;
    }

    // =======
    // = Uri =
    // =======

    /**
     * 判断是否 Uri
     * @param uriString uri 路径
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isUri(final String uriString) {
        Uri uri = getUriForString(uriString);
        return StringUtils.isNotEmpty(getUriScheme(uri));
    }

    /**
     * 判断是否 Uri
     * @param uri {@link Uri}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isUri(final Uri uri) {
        return StringUtils.isNotEmpty(getUriScheme(uri));
    }

    // =

    /**
     * 获取 Uri Scheme
     * @param uriString uri 路径
     * @return Uri Scheme
     */
    public static String getUriScheme(final String uriString) {
        return getUriScheme(getUriForString(uriString));
    }

    /**
     * 获取 Uri Scheme
     * @param uri {@link Uri}
     * @return Uri Scheme
     */
    public static String getUriScheme(final Uri uri) {
        if (uri == null) return null;
        return uri.getScheme();
    }

    // =

    /**
     * 判断 Uri 路径资源是否存在
     * <pre>
     *     uri 非 FilePath, 可通过 {@link UriUtils#getMediaUri} 获取
     * </pre>
     * @param uriString uri 路径
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isUriExists(final String uriString) {
        if (TextUtils.isEmpty(uriString)) return false;
        return isUriExists(Uri.parse(uriString));
    }

    /**
     * 判断 Uri 路径资源是否存在
     * @param uri {@link Uri}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isUriExists(final Uri uri) {
        AssetFileDescriptor afd = ResourceUtils.openAssetFileDescriptor(uri, "r");
        try {
            return (afd != null);
        } finally {
            CloseUtils.closeIOQuietly(afd);
        }
    }

    // =============
    // = Media Uri =
    // =============

    /**
     * 通过 File 获取 Media Uri
     * @param file 文件
     * @return 指定文件 {@link Uri}
     */
    public static Uri getMediaUri(final File file) {
        return ContentResolverUtils.getMediaUri(file);
    }

    /**
     * 通过 File 获取 Media Uri
     * @param uri  MediaStore.media-type.Media.EXTERNAL_CONTENT_URI
     * @param file 文件
     * @return 指定文件 {@link Uri}
     */
    public static Uri getMediaUri(
            final Uri uri,
            final File file
    ) {
        return ContentResolverUtils.getMediaUri(uri, file);
    }

    /**
     * 通过 File Path 获取 Media Uri
     * @param filePath 文件路径
     * @return 指定文件 {@link Uri}
     */
    public static Uri getMediaUri(final String filePath) {
        return ContentResolverUtils.getMediaUri(filePath);
    }

    /**
     * 通过 File Path 获取 Media Uri
     * @param uri      MediaStore.media-type.Media.EXTERNAL_CONTENT_URI
     * @param filePath 文件路径
     * @return 指定文件 {@link Uri}
     */
    public static Uri getMediaUri(
            final Uri uri,
            final String filePath
    ) {
        return ContentResolverUtils.getMediaUri(uri, filePath);
    }

    // ==========
    // = 复制文件 =
    // ==========

    /**
     * 通过 Uri 复制文件
     * @param uri {@link Uri}
     * @return 复制后的文件路径
     */
    public static String copyByUri(final Uri uri) {
        return copyByUri(uri, ContentResolverUtils.getDisplayNameColumn(uri));
    }

    /**
     * 通过 Uri 复制文件
     * @param uri      {@link Uri}
     * @param fileName 文件名 {@link ContentResolverUtils#getDisplayNameColumn}
     * @return 复制后的文件路径
     */
    public static String copyByUri(
            final Uri uri,
            final String fileName
    ) {
        return copyByUri(uri, PathUtils.getAppExternal().getAppCachePath(), fileName);
    }

    /**
     * 通过 Uri 复制文件
     * @param uri      {@link Uri}
     * @param file     文件
     * @param fileName 文件名 {@link ContentResolverUtils#getDisplayNameColumn}
     * @return 复制后的文件路径
     */
    public static String copyByUri(
            final Uri uri,
            final File file,
            final String fileName
    ) {
        return copyByUri(uri, FileUtils.getAbsolutePath(file), fileName);
    }

    /**
     * 通过 Uri 复制文件
     * @param uri      {@link Uri}
     * @param filePath 文件路径
     * @param fileName 文件名 {@link ContentResolverUtils#getDisplayNameColumn}
     * @return 复制后的文件路径
     */
    public static String copyByUri(
            final Uri uri,
            final String filePath,
            final String fileName
    ) {
        if (uri == null || TextUtils.isEmpty(filePath)) return null;
        FileUtils.createFolder(filePath);
        InputStream is = null;
        try {
            String child = TextUtils.isEmpty(fileName) ? String.valueOf(System.currentTimeMillis()) : fileName;
            is = ResourceUtils.openInputStream(uri);
            File file = new File(filePath, child);
            FileIOUtils.writeFileFromIS(file.getAbsolutePath(), is);
            return file.getAbsolutePath();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "copyByUri");
        } finally {
            CloseUtils.closeIOQuietly(is);
        }
        return null;
    }

    // =============
    // = 获取文件路径 =
    // =============

    /**
     * 通过 Uri 获取文件路径
     * @param uri {@link Uri}
     * @return 文件路径
     */
    public static String getFilePathByUri(final Uri uri) {
        return getFilePathByUri(uri, false);
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
    public static String getFilePathByUri(
            final Uri uri,
            final boolean isQCopy
    ) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && isQCopy) {
                return copyByUri(uri);
            }
            return getFilePathByUri(DevUtils.getContext(), uri);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getFilePathByUri");
            return null;
        }
    }

    /**
     * 通过 Uri 获取文件路径
     * @param context {@link Context}
     * @param uri     {@link Uri}
     * @return 文件路径
     */
    private static String getFilePathByUri(
            final Context context,
            final Uri uri
    ) {
        if (context == null || uri == null) return null;

        // 以 file:// 开头
        if (ContentResolver.SCHEME_FILE.equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        // 当前 Android SDK 是否大于等于 4.4
        boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // 4.4 之前以 content:// 开头, 比如 content://media/extenral/images/media/17766
        if (ContentResolver.SCHEME_CONTENT.equalsIgnoreCase(uri.getScheme()) && !isKitKat) {
            if (isGooglePhotosUri(uri)) return uri.getLastPathSegment();
            return ContentResolverUtils.getDataColumn(uri, null, null);
        }

        // 4.4 及之后以 content:// 开头, 比如 content://com.android.providers.media.documents/document/image%3A235700
        if (ContentResolver.SCHEME_CONTENT.equalsIgnoreCase(uri.getScheme()) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // isKitKat
            if (DocumentsContract.isDocumentUri(context, uri)) { // DocumentProvider
                if (isExternalStorageDocument(uri)) { // ExternalStorageProvider
                    final String   docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String   type  = split[0];

                    if ("primary".equalsIgnoreCase(type)) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            return context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + split[1];
                        } else {
                            return Environment.getExternalStorageDirectory() + "/" + split[1];
                        }
                    }
                } else if (isDownloadsDocument(uri)) { // DownloadsProvider
                    final String id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"),
                            Long.parseLong(id)
                    );
                    return ContentResolverUtils.getDataColumn(contentUri, null, null);
                } else if (isMediaDocument(uri)) { // MediaProvider
                    final String   docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String   type  = split[0];

                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }

                    final String   selection     = "_id=?";
                    final String[] selectionArgs = new String[]{split[1]};
                    return ContentResolverUtils.getDataColumn(contentUri, selection, selectionArgs);
                }
            }
        }
        return ContentResolverUtils.getDataColumn(uri, null, null);
    }

    /**
     * 判读 Uri authority 是否为 ExternalStorage Provider
     * @param uri {@link Uri}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isExternalStorageDocument(final Uri uri) {
        if (uri == null) return false;
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * 判读 Uri authority 是否为 Downloads Provider
     * @param uri {@link Uri}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isDownloadsDocument(final Uri uri) {
        if (uri == null) return false;
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * 判断 Uri authority 是否为 Media Provider
     * @param uri {@link Uri}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isMediaDocument(final Uri uri) {
        if (uri == null) return false;
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * 判断 Uri authority 是否为 Google Photos Provider
     * @param uri {@link Uri}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isGooglePhotosUri(final Uri uri) {
        if (uri == null) return false;
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    // ==============
    // = Uri Scheme =
    // ==============

    /**
     * 判断 Uri Scheme 是否 ContentResolver.SCHEME_ANDROID_RESOURCE
     * @param uri {@link Uri}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isAndroidResourceScheme(final Uri uri) {
        return isUriScheme(ContentResolver.SCHEME_ANDROID_RESOURCE, uri);
    }

    /**
     * 判断 Uri Scheme 是否 ContentResolver.SCHEME_ANDROID_RESOURCE
     * @param scheme 待校验 Scheme
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isAndroidResourceScheme(final String scheme) {
        return isUriScheme(ContentResolver.SCHEME_ANDROID_RESOURCE, scheme);
    }

    /**
     * 判断 Uri Scheme 是否 ContentResolver.SCHEME_FILE
     * @param uri {@link Uri}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isFileScheme(final Uri uri) {
        return isUriScheme(ContentResolver.SCHEME_FILE, uri);
    }

    /**
     * 判断 Uri Scheme 是否 ContentResolver.SCHEME_FILE
     * @param scheme 待校验 Scheme
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isFileScheme(final String scheme) {
        return isUriScheme(ContentResolver.SCHEME_FILE, scheme);
    }

    /**
     * 判断 Uri Scheme 是否 ContentResolver.SCHEME_CONTENT
     * @param uri {@link Uri}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isContentScheme(final Uri uri) {
        return isUriScheme(ContentResolver.SCHEME_CONTENT, uri);
    }

    /**
     * 判断 Uri Scheme 是否 ContentResolver.SCHEME_CONTENT
     * @param scheme 待校验 Scheme
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isContentScheme(final String scheme) {
        return isUriScheme(ContentResolver.SCHEME_CONTENT, scheme);
    }

    /**
     * 判断是否指定的 Uri Scheme
     * @param uriScheme 如 ContentResolver.SCHEME_CONTENT
     * @param uri       {@link Uri}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isUriScheme(
            final String uriScheme,
            final Uri uri
    ) {
        return isUriScheme(uriScheme, getUriScheme(uri));
    }

    /**
     * 判断是否指定的 Uri Scheme ( 忽略大小写 )
     * @param uriScheme 如 ContentResolver.SCHEME_CONTENT
     * @param scheme    待校验 Scheme
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isUriScheme(
            final String uriScheme,
            final String scheme
    ) {
        return StringUtils.equalsIgnoreCaseNotNull(uriScheme, scheme);
    }
}