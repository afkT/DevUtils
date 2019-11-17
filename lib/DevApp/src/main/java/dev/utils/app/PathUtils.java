package dev.utils.app;

import android.os.Build;
import android.os.Environment;

import java.io.File;

import dev.DevUtils;

/**
 * detail: 路径相关工具类
 * @author Ttt
 */
public final class PathUtils {

    private PathUtils() {
    }

    /**
     * 获取 Android 系统根目录 - path /system
     * @return /system
     */
    public static String getRootPath() {
        return getAbsolutePath(Environment.getRootDirectory());
    }

    /**
     * 获取 data 目录 - path /data
     * @return /data
     */
    public static String getDataPath() {
        return getAbsolutePath(Environment.getDataDirectory());
    }

    /**
     * 获取下载缓存目录 - path data/cache
     * @return data/cache
     */
    public static String getDownloadCachePath() {
        return getAbsolutePath(Environment.getDownloadCacheDirectory());
    }

    /**
     * 获取内存应用缓存路径 - path /data/data/package/cache
     * @return /data/data/package/cache
     */
    public static String getInternalCachePath() {
        return getAbsolutePath(DevUtils.getContext().getCacheDir());
    }

    /**
     * 获取内存应用数据路径 - path /data/data/package
     * @return /data/data/package
     */
    public static String getInternalAppDataPath() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            return DevUtils.getContext().getApplicationInfo().dataDir;
        }
        return getAbsolutePath(DevUtils.getContext().getDataDir());
    }

    /**
     * 获取内存应用代码缓存路径 - path /data/data/package/code_cache
     * @return /data/data/package/code_cache
     */
    public static String getInternalAppCodeCacheDir() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return DevUtils.getContext().getApplicationInfo().dataDir + "/code_cache";
        }
        return getAbsolutePath(DevUtils.getContext().getCodeCacheDir());
    }

    /**
     * 获取内存应用数据库路径 - path /data/data/package/databases
     * @return /data/data/package/databases
     */
    public static String getInternalAppDbsPath() {
        return DevUtils.getContext().getApplicationInfo().dataDir + "/databases";
    }

    /**
     * 获取内存应用数据库路径 - path /data/data/package/databases/name
     * @param name 数据库路径 + 名称
     * @return /data/data/package/databases/name
     */
    public static String getInternalAppDbPath(final String name) {
        return getAbsolutePath(DevUtils.getContext().getDatabasePath(name));
    }

    /**
     * 获取内存应用文件路径 - path/data/data/package/files
     * @return /data/data/package/files
     */
    public static String getInternalAppFilesPath() {
        return getAbsolutePath(DevUtils.getContext().getFilesDir());
    }

    /**
     * 获取内存应用 SP 路径 - path/data/data/package/shared_prefs
     * @return /data/data/package/shared_prefs
     */
    public static String getInternalAppSpPath() {
        return DevUtils.getContext().getApplicationInfo().dataDir + "shared_prefs";
    }

    /**
     * 获取内存应用未备份文件路径 - path/data/data/package/no_backup
     * @return /data/data/package/no_backup
     */
    public static String getInternalAppNoBackupFilesPath() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return DevUtils.getContext().getApplicationInfo().dataDir + "no_backup";
        }
        return getAbsolutePath(DevUtils.getContext().getNoBackupFilesDir());
    }

    /**
     * 获取外存路径 - path/storage/emulated/0
     * @return /storage/emulated/0
     */
    public static String getExternalStoragePath() {
        if (!isSDCardEnable()) return "";
        return getAbsolutePath(Environment.getExternalStorageDirectory());
    }

    /**
     * 获取外存音乐路径 - path/storage/emulated/0/Music
     * @return /storage/emulated/0/Music
     */
    public static String getExternalMusicPath() {
        if (!isSDCardEnable()) return "";
        return getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC));
    }

    /**
     * 获取外存播客路径 - path/storage/emulated/0/Podcasts
     * @return /storage/emulated/0/Podcasts
     */
    public static String getExternalPodcastsPath() {
        if (!isSDCardEnable()) return "";
        return getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PODCASTS));
    }

    /**
     * 获取外存铃声路径 - path/storage/emulated/0/Ringtones
     * @return /storage/emulated/0/Ringtones
     */
    public static String getExternalRingtonesPath() {
        if (!isSDCardEnable()) return "";
        return getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_RINGTONES));
    }

    /**
     * 获取外存闹铃路径 - path/storage/emulated/0/Alarms
     * @return /storage/emulated/0/Alarms
     */
    public static String getExternalAlarmsPath() {
        if (!isSDCardEnable()) return "";
        return getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS));
    }

    /**
     * 获取外存通知路径 - path/storage/emulated/0/Notifications
     * @return /storage/emulated/0/Notifications
     */
    public static String getExternalNotificationsPath() {
        if (!isSDCardEnable()) return "";
        return getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_NOTIFICATIONS));
    }

    /**
     * 获取外存图片路径 - path/storage/emulated/0/Pictures
     * @return /storage/emulated/0/Pictures
     */
    public static String getExternalPicturesPath() {
        if (!isSDCardEnable()) return "";
        return getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
    }

    /**
     * 获取外存影片路径 - path/storage/emulated/0/Movies
     * @return /storage/emulated/0/Movies
     */
    public static String getExternalMoviesPath() {
        if (!isSDCardEnable()) return "";
        return getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES));
    }

    /**
     * 获取外存下载路径 - path/storage/emulated/0/Download
     * @return /storage/emulated/0/Download
     */
    public static String getExternalDownloadsPath() {
        if (!isSDCardEnable()) return "";
        return getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS));
    }

    /**
     * 获取外存数码相机图片路径 - path/storage/emulated/0/DCIM
     * @return /storage/emulated/0/DCIM
     */
    public static String getExternalDcimPath() {
        if (!isSDCardEnable()) return "";
        return getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM));
    }

    /**
     * 获取外存文档路径 - path/storage/emulated/0/Documents
     * @return /storage/emulated/0/Documents
     */
    public static String getExternalDocumentsPath() {
        if (!isSDCardEnable()) return "";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return getAbsolutePath(Environment.getExternalStorageDirectory()) + "/Documents";
        }
        return getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS));
    }

    /**
     * 获取外存应用数据路径 - path/storage/emulated/0/Android/data/package
     * @return /storage/emulated/0/Android/data/package
     */
    public static String getExternalAppDataPath() {
        if (!isSDCardEnable()) return "";
        return getAbsolutePath(DevUtils.getContext().getExternalCacheDir().getParentFile());
    }

    /**
     * 获取外存应用缓存路径 - path/storage/emulated/0/Android/data/package/cache
     * @return /storage/emulated/0/Android/data/package/cache
     */
    public static String getExternalAppCachePath() {
        if (!isSDCardEnable()) return "";
        return getAbsolutePath(DevUtils.getContext().getExternalCacheDir());
    }

    /**
     * 获取外存应用文件路径 - path/storage/emulated/0/Android/data/package/files
     * @return /storage/emulated/0/Android/data/package/files
     */
    public static String getExternalAppFilesPath() {
        if (!isSDCardEnable()) return "";
        return getAbsolutePath(DevUtils.getContext().getExternalFilesDir(null));
    }

    /**
     * 获取外存应用音乐路径 - path/storage/emulated/0/Android/data/package/files/Music
     * @return /storage/emulated/0/Android/data/package/files/Music
     */
    public static String getExternalAppMusicPath() {
        if (!isSDCardEnable()) return "";
        return getAbsolutePath(DevUtils.getContext().getExternalFilesDir(Environment.DIRECTORY_MUSIC));
    }

    /**
     * 获取外存应用播客路径 - path/storage/emulated/0/Android/data/package/files/Podcasts
     * @return /storage/emulated/0/Android/data/package/files/Podcasts
     */
    public static String getExternalAppPodcastsPath() {
        if (!isSDCardEnable()) return "";
        return getAbsolutePath(DevUtils.getContext().getExternalFilesDir(Environment.DIRECTORY_PODCASTS));
    }

    /**
     * 获取外存应用铃声路径 - path/storage/emulated/0/Android/data/package/files/Ringtones
     * @return /storage/emulated/0/Android/data/package/files/Ringtones
     */
    public static String getExternalAppRingtonesPath() {
        if (!isSDCardEnable()) return "";
        return getAbsolutePath(DevUtils.getContext().getExternalFilesDir(Environment.DIRECTORY_RINGTONES));
    }

    /**
     * 获取外存应用闹铃路径 - path/storage/emulated/0/Android/data/package/files/Alarms
     * @return /storage/emulated/0/Android/data/package/files/Alarms
     */
    public static String getExternalAppAlarmsPath() {
        if (!isSDCardEnable()) return "";
        return getAbsolutePath(DevUtils.getContext().getExternalFilesDir(Environment.DIRECTORY_ALARMS));
    }

    /**
     * 获取外存应用通知路径 - path/storage/emulated/0/Android/data/package/files/Notifications
     * @return /storage/emulated/0/Android/data/package/files/Notifications
     */
    public static String getExternalAppNotificationsPath() {
        if (!isSDCardEnable()) return "";
        return getAbsolutePath(DevUtils.getContext().getExternalFilesDir(Environment.DIRECTORY_NOTIFICATIONS));
    }

    /**
     * 获取外存应用图片路径 - path/storage/emulated/0/Android/data/package/files/Pictures
     * @return path /storage/emulated/0/Android/data/package/files/Pictures
     */
    public static String getExternalAppPicturesPath() {
        if (!isSDCardEnable()) return "";
        return getAbsolutePath(DevUtils.getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES));
    }

    /**
     * 获取外存应用影片路径 - path/storage/emulated/0/Android/data/package/files/Movies
     * @return /storage/emulated/0/Android/data/package/files/Movies
     */
    public static String getExternalAppMoviesPath() {
        if (!isSDCardEnable()) return "";
        return getAbsolutePath(DevUtils.getContext().getExternalFilesDir(Environment.DIRECTORY_MOVIES));
    }

    /**
     * 获取外存应用下载路径 - path/storage/emulated/0/Android/data/package/files/Download
     * @return /storage/emulated/0/Android/data/package/files/Download
     */
    public static String getExternalAppDownloadPath() {
        if (!isSDCardEnable()) return "";
        return getAbsolutePath(DevUtils.getContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
    }

    /**
     * 获取外存应用数码相机图片路径 - path/storage/emulated/0/Android/data/package/files/DCIM
     * @return /storage/emulated/0/Android/data/package/files/DCIM
     */
    public static String getExternalAppDcimPath() {
        if (!isSDCardEnable()) return "";
        return getAbsolutePath(DevUtils.getContext().getExternalFilesDir(Environment.DIRECTORY_DCIM));
    }

    /**
     * 获取外存应用文档路径 - path/storage/emulated/0/Android/data/package/files/Documents
     * @return /storage/emulated/0/Android/data/package/files/Documents
     */
    public static String getExternalAppDocumentsPath() {
        if (!isSDCardEnable()) return "";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return getAbsolutePath(DevUtils.getContext().getExternalFilesDir(null)) + "/Documents";
        }
        return getAbsolutePath(DevUtils.getContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS));
    }

    /**
     * 获取外存应用 OBB 路径 - path/storage/emulated/0/Android/obb/package
     * @return /storage/emulated/0/Android/obb/package
     */
    public static String getExternalAppObbPath() {
        if (!isSDCardEnable()) return "";
        return getAbsolutePath(DevUtils.getContext().getObbDir());
    }

    // ======================
    // = 其他工具类实现代码 =
    // ======================

    // ===============
    // = SDCardUtils =
    // ===============

    /**
     * 判断内置 SDCard 是否正常挂载
     * @return {@code true} yes, {@code false} no
     */
    private static boolean isSDCardEnable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    // =============
    // = FileUtils =
    // =============

    /**
     * 获取文件绝对路径
     * @param file 文件
     * @return 文件绝对路径
     */
    private static String getAbsolutePath(final File file) {
        return file != null ? file.getAbsolutePath() : null;
    }
}