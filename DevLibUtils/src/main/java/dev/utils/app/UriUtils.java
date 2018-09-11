package dev.utils.app;

import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: Uri 工具类
 * Created by Blankj
 * http://blankj.com
 * Update to Ttt
 */
public final class UriUtils {

    private UriUtils() {
    }

    // 日志TAG
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
     * @return
     * getUriForFileToName(file, "fileProvider");
     * getUriForFile(file, "包名.fileProvider");
     */
    public static Uri getUriForFileToName(final File file, String name){
        try {
            String authority = DevUtils.getContext().getPackageName() + "." + name;
            return getUriForFile(file, authority);
        } catch (Exception e){
            LogPrintUtils.eTag(TAG, e, "getUriForFileToName");
        }
        return null;
    }


    /**
     * Return a content URI for a given file.
     * @param file The file.
     * @return a content URI for a given file
     */
    public static Uri getUriForFile(final File file, final String authority) {
        if (file == null) return null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return FileProvider.getUriForFile(DevUtils.getContext(), authority, file);
        } else {
            return Uri.fromFile(file);
        }
    }
}
