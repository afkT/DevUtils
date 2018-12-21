package dev.utils.app;

import android.os.Build;
import android.os.Environment;

import dev.DevUtils;

/**
 * detail: 路径相关工具类
 * Created by Ttt
 */
public final class PathUtils {

    private PathUtils() {
    }

    /**
     * 获取 Android 系统根目录 - path /system
     * @return /system
     */
    public static String getRootPath() {
        return Environment.getRootDirectory().getAbsolutePath();
    }

    /**
     * 获取 data 目录 - path /data
     * @return /data
     */
    public static String getDataPath() {
        return Environment.getDataDirectory().getAbsolutePath();
    }

    /**
     * 获取下载缓存目录 - path data/cache
     * @return data/cache
     */
    public static String getDownloadCachePath() {
        return Environment.getDownloadCacheDirectory().getAbsolutePath();
    }

    /**
     * 获取内存应用数据路径 - path /data/data/package
     * @return /data/data/package
     */
    public static String getInternalAppDataPath() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            return DevUtils.getContext().getApplicationInfo().dataDir;
        }
        return DevUtils.getContext().getDataDir().getAbsolutePath();
    }

    /**
     * 获取内存应用代码缓存路径 - path /data/data/package/code_cache
     * @return /data/data/package/code_cache
     */
    public static String getInternalAppCodeCacheDir() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return DevUtils.getContext().getApplicationInfo().dataDir + "/code_cache";
        }
        return DevUtils.getContext().getCodeCacheDir().getAbsolutePath();
    }

    /**
     * 获取内存应用数据库路径 - path /data/data/package/databases.
     * @return /data/data/package/databases.
     */
    public static String getInternalAppDbsPath() {
        return DevUtils.getContext().getApplicationInfo().dataDir + "/databases";
    }

    /**
     * 获取内存应用数据库路径 - path /data/data/package/databases/name.
     * @param name
     * @return /data/data/package/databases/name
     */
    public static String getInternalAppDbPath(String name) {
        return DevUtils.getContext().getDatabasePath(name).getAbsolutePath();
    }

    /**
     * 获取内存应用文件路径 - path/data/data/package/files.
     * @return /data/data/package/files
     */
    public static String getInternalAppFilesPath() {
        return DevUtils.getContext().getFilesDir().getAbsolutePath();
    }

    /**
     * 获取内存应用 SP 路径 - path/data/data/package/shared_prefs.
     * @return /data/data/package/shared_prefs
     */
    public static String getInternalAppSpPath() {
        return DevUtils.getContext().getApplicationInfo().dataDir + "shared_prefs";
    }

    /**
     * 获取内存应用未备份文件路径 - path/data/data/package/no_backup.
     * @return /data/data/package/no_backup
     */
    public static String getInternalAppNoBackupFilesPath() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return DevUtils.getContext().getApplicationInfo().dataDir + "no_backup";
        }
        return DevUtils.getContext().getNoBackupFilesDir().getAbsolutePath();
    }

    /**
     * 获取外存路径 - path/storage/emulated/0.
     * @return /storage/emulated/0
     */
    public static String getExternalStoragePath() {
        if (isExternalStorageDisable()) return "";
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    /**
     * 获取外存音乐路径 - path/storage/emulated/0/Music.
     * @return /storage/emulated/0/Music
     */
    public static String getExternalMusicPath() {
        if (isExternalStorageDisable()) return "";
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath();
    }

    /**
     * 获取外存播客路径 - path/storage/emulated/0/Podcasts.
     * @return /storage/emulated/0/Podcasts
     */
    public static String getExternalPodcastsPath() {
        if (isExternalStorageDisable()) return "";
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PODCASTS).getAbsolutePath();
    }

    /**
     * 获取外存铃声路径 - path/storage/emulated/0/Ringtones.
     * @return /storage/emulated/0/Ringtones
     */
    public static String getExternalRingtonesPath() {
        if (isExternalStorageDisable()) return "";
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_RINGTONES).getAbsolutePath();
    }

    /**
     * 获取外存闹铃路径 - path/storage/emulated/0/Alarms.
     * @return /storage/emulated/0/Alarms
     */
    public static String getExternalAlarmsPath() {
        if (isExternalStorageDisable()) return "";
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS).getAbsolutePath();
    }

    /**
     * 获取外存通知路径 - path/storage/emulated/0/Notifications.
     * @return /storage/emulated/0/Notifications
     */
    public static String getExternalNotificationsPath() {
        if (isExternalStorageDisable()) return "";
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_NOTIFICATIONS).getAbsolutePath();
    }

    /**
     * 获取外存图片路径 - path/storage/emulated/0/Pictures.
     * @return /storage/emulated/0/Pictures
     */
    public static String getExternalPicturesPath() {
        if (isExternalStorageDisable()) return "";
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();
    }

    /**
     * 获取外存影片路径 - path/storage/emulated/0/Movies.
     * @return /storage/emulated/0/Movies
     */
    public static String getExternalMoviesPath() {
        if (isExternalStorageDisable()) return "";
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES).getAbsolutePath();
    }

    /**
     * 获取外存下载路径 - path/storage/emulated/0/Download.
     * @return /storage/emulated/0/Download
     */
    public static String getExternalDownloadsPath() {
        if (isExternalStorageDisable()) return "";
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
    }

    /**
     * 获取外存数码相机图片路径 - path/storage/emulated/0/DCIM.
     * @return /storage/emulated/0/DCIM
     */
    public static String getExternalDcimPath() {
        if (isExternalStorageDisable()) return "";
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();
    }

    /**
     * 获取外存文档路径 - path/storage/emulated/0/Documents.
     * @return /storage/emulated/0/Documents
     */
    public static String getExternalDocumentsPath() {
        if (isExternalStorageDisable()) return "";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return Environment.getExternalStorageDirectory().getAbsolutePath() + "/Documents";
        }
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath();
    }

    /**
     * 获取外存应用数据路径 - path/storage/emulated/0/Android/data/package.
     * @return /storage/emulated/0/Android/data/package
     */
    public static String getExternalAppDataPath() {
        if (isExternalStorageDisable()) return "";
        return DevUtils.getContext().getExternalCacheDir().getParentFile().getAbsolutePath();
    }

    /**
     * 获取外存应用缓存路径 - path/storage/emulated/0/Android/data/package/cache.
     * @return /storage/emulated/0/Android/data/package/cache
     */
    public static String getExternalAppCachePath() {
        if (isExternalStorageDisable()) return "";
        return DevUtils.getContext().getExternalCacheDir().getAbsolutePath();
    }

    /**
     * 获取外存应用文件路径 - path/storage/emulated/0/Android/data/package/files.
     * @return /storage/emulated/0/Android/data/package/files
     */
    public static String getExternalAppFilesPath() {
        if (isExternalStorageDisable()) return "";
        return DevUtils.getContext().getExternalFilesDir(null).getAbsolutePath();
    }

    /**
     * 获取外存应用音乐路径 - path/storage/emulated/0/Android/data/package/files/Music.
     * @return /storage/emulated/0/Android/data/package/files/Music
     */
    public static String getExternalAppMusicPath() {
        if (isExternalStorageDisable()) return "";
        return DevUtils.getContext().getExternalFilesDir(Environment.DIRECTORY_MUSIC).getAbsolutePath();
    }

    /**
     * 获取外存应用播客路径 - path/storage/emulated/0/Android/data/package/files/Podcasts.
     * @return /storage/emulated/0/Android/data/package/files/Podcasts
     */
    public static String getExternalAppPodcastsPath() {
        if (isExternalStorageDisable()) return "";
        return DevUtils.getContext().getExternalFilesDir(Environment.DIRECTORY_PODCASTS).getAbsolutePath();
    }

    /**
     * 获取外存应用铃声路径 - path/storage/emulated/0/Android/data/package/files/Ringtones.
     * @return /storage/emulated/0/Android/data/package/files/Ringtones
     */
    public static String getExternalAppRingtonesPath() {
        if (isExternalStorageDisable()) return "";
        return DevUtils.getContext().getExternalFilesDir(Environment.DIRECTORY_RINGTONES).getAbsolutePath();
    }

    /**
     * 获取外存应用闹铃路径 - path/storage/emulated/0/Android/data/package/files/Alarms.
     * @return /storage/emulated/0/Android/data/package/files/Alarms
     */
    public static String getExternalAppAlarmsPath() {
        if (isExternalStorageDisable()) return "";
        return DevUtils.getContext().getExternalFilesDir(Environment.DIRECTORY_ALARMS).getAbsolutePath();
    }

    /**
     * 获取外存应用通知路径 - path/storage/emulated/0/Android/data/package/files/Notifications.
     * @return /storage/emulated/0/Android/data/package/files/Notifications
     */
    public static String getExternalAppNotificationsPath() {
        if (isExternalStorageDisable()) return "";
        return DevUtils.getContext().getExternalFilesDir(Environment.DIRECTORY_NOTIFICATIONS).getAbsolutePath();
    }

    /**
     * 获取外存应用图片路径 - path/storage/emulated/0/Android/data/package/files/Pictures.
     * @return path /storage/emulated/0/Android/data/package/files/Pictures
     */
    public static String getExternalAppPicturesPath() {
        if (isExternalStorageDisable()) return "";
        return DevUtils.getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
    }

    /**
     * 获取外存应用影片路径 - path/storage/emulated/0/Android/data/package/files/Movies.
     * @return /storage/emulated/0/Android/data/package/files/Movies
     */
    public static String getExternalAppMoviesPath() {
        if (isExternalStorageDisable()) return "";
        return DevUtils.getContext().getExternalFilesDir(Environment.DIRECTORY_MOVIES).getAbsolutePath();
    }

    /**
     * 获取外存应用下载路径 - path/storage/emulated/0/Android/data/package/files/Download.
     * @return /storage/emulated/0/Android/data/package/files/Download
     */
    public static String getExternalAppDownloadPath() {
        if (isExternalStorageDisable()) return "";
        return DevUtils.getContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
    }

    /**
     * 获取外存应用数码相机图片路径 - path/storage/emulated/0/Android/data/package/files/DCIM.
     * @return /storage/emulated/0/Android/data/package/files/DCIM
     */
    public static String getExternalAppDcimPath() {
        if (isExternalStorageDisable()) return "";
        return DevUtils.getContext().getExternalFilesDir(Environment.DIRECTORY_DCIM).getAbsolutePath();
    }

    /**
     * 获取外存应用文档路径 - path/storage/emulated/0/Android/data/package/files/Documents.
     * @return /storage/emulated/0/Android/data/package/files/Documents
     */
    public static String getExternalAppDocumentsPath() {
        if (isExternalStorageDisable()) return "";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return DevUtils.getContext().getExternalFilesDir(null).getAbsolutePath() + "/Documents";
        }
        return DevUtils.getContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath();
    }

    /**
     * 获取外存应用 OBB 路径 - path/storage/emulated/0/Android/obb/package.
     * @return /storage/emulated/0/Android/obb/package
     */
    public static String getExternalAppObbPath() {
        if (isExternalStorageDisable()) return "";
        return DevUtils.getContext().getObbDir().getAbsolutePath();
    }

    /**
     * 判断SDCard是否正常挂载
     * @return
     */
    private static boolean isExternalStorageDisable() {
        return !Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }
}
