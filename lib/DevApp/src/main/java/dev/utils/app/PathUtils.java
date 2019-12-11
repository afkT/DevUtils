package dev.utils.app;

import android.os.Build;
import android.os.Environment;

import java.io.File;

import dev.DevUtils;
import dev.utils.LogPrintUtils;
import dev.utils.common.FileUtils;

/**
 * detail: 路径相关工具类
 * @author Ttt
 */
public final class PathUtils {

    private PathUtils() {
    }

    // 日志 TAG
    private static final String TAG = PathUtils.class.getSimpleName();
    // 内部存储路径类
    private static final InternalPath sInternalPath = new InternalPath();

    /**
     * 获取内部存储路径类
     * @return {@link InternalPath}
     */
    public static InternalPath getInternal() {
        return sInternalPath;
    }

    /**
     * 获取外存路径 - path/storage/emulated/0
     * @return /storage/emulated/0
     */
    public static String getExternalStoragePath() {
        if (!SDCardUtils.isSDCardEnable()) return null;
        return FileUtils.getAbsolutePath(Environment.getExternalStorageDirectory());
    }

    /**
     * 获取外存音乐路径 - path/storage/emulated/0/Music
     * @return /storage/emulated/0/Music
     */
    public static String getExternalMusicPath() {
        if (!SDCardUtils.isSDCardEnable()) return null;
        return FileUtils.getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC));
    }

    /**
     * 获取外存播客路径 - path/storage/emulated/0/Podcasts
     * @return /storage/emulated/0/Podcasts
     */
    public static String getExternalPodcastsPath() {
        if (!SDCardUtils.isSDCardEnable()) return null;
        return FileUtils.getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PODCASTS));
    }

    /**
     * 获取外存铃声路径 - path/storage/emulated/0/Ringtones
     * @return /storage/emulated/0/Ringtones
     */
    public static String getExternalRingtonesPath() {
        if (!SDCardUtils.isSDCardEnable()) return null;
        return FileUtils.getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_RINGTONES));
    }

    /**
     * 获取外存闹铃路径 - path/storage/emulated/0/Alarms
     * @return /storage/emulated/0/Alarms
     */
    public static String getExternalAlarmsPath() {
        if (!SDCardUtils.isSDCardEnable()) return null;
        return FileUtils.getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS));
    }

    /**
     * 获取外存通知路径 - path/storage/emulated/0/Notifications
     * @return /storage/emulated/0/Notifications
     */
    public static String getExternalNotificationsPath() {
        if (!SDCardUtils.isSDCardEnable()) return null;
        return FileUtils.getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_NOTIFICATIONS));
    }

    /**
     * 获取外存图片路径 - path/storage/emulated/0/Pictures
     * @return /storage/emulated/0/Pictures
     */
    public static String getExternalPicturesPath() {
        if (!SDCardUtils.isSDCardEnable()) return null;
        return FileUtils.getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
    }

    /**
     * 获取外存影片路径 - path/storage/emulated/0/Movies
     * @return /storage/emulated/0/Movies
     */
    public static String getExternalMoviesPath() {
        if (!SDCardUtils.isSDCardEnable()) return null;
        return FileUtils.getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES));
    }

    /**
     * 获取外存下载路径 - path/storage/emulated/0/Download
     * @return /storage/emulated/0/Download
     */
    public static String getExternalDownloadsPath() {
        if (!SDCardUtils.isSDCardEnable()) return null;
        return FileUtils.getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS));
    }

    /**
     * 获取外存数码相机图片路径 - path/storage/emulated/0/DCIM
     * @return /storage/emulated/0/DCIM
     */
    public static String getExternalDcimPath() {
        if (!SDCardUtils.isSDCardEnable()) return null;
        return FileUtils.getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM));
    }

    /**
     * 获取外存文档路径 - path/storage/emulated/0/Documents
     * @return /storage/emulated/0/Documents
     */
    public static String getExternalDocumentsPath() {
        if (!SDCardUtils.isSDCardEnable()) return null;
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                return FileUtils.getAbsolutePath(Environment.getExternalStorageDirectory()) + "/Documents";
            }
            return FileUtils.getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS));
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getExternalDocumentsPath");
        }
        return null;
    }

    /**
     * 获取外存应用数据路径 - path/storage/emulated/0/Android/data/package
     * @return /storage/emulated/0/Android/data/package
     */
    public static String getExternalAppDataPath() {
        if (!SDCardUtils.isSDCardEnable()) return null;
        try {
            return FileUtils.getAbsolutePath(DevUtils.getContext().getExternalCacheDir().getParentFile());
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getExternalAppDataPath");
        }
        return null;
    }

    /**
     * 获取外存应用缓存路径 - path/storage/emulated/0/Android/data/package/cache
     * @return /storage/emulated/0/Android/data/package/cache
     */
    public static String getExternalAppCachePath() {
        if (!SDCardUtils.isSDCardEnable()) return null;
        try {
            return FileUtils.getAbsolutePath(DevUtils.getContext().getExternalCacheDir());
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getExternalAppCachePath");
        }
        return null;
    }

    /**
     * 获取外存应用文件路径 - path/storage/emulated/0/Android/data/package/files
     * @return /storage/emulated/0/Android/data/package/files
     */
    public static String getExternalAppFilesPath() {
        if (!SDCardUtils.isSDCardEnable()) return null;
        try {
            return FileUtils.getAbsolutePath(DevUtils.getContext().getExternalFilesDir(null));
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getExternalAppFilesPath");
        }
        return null;
    }

    /**
     * 获取外存应用音乐路径 - path/storage/emulated/0/Android/data/package/files/Music
     * @return /storage/emulated/0/Android/data/package/files/Music
     */
    public static String getExternalAppMusicPath() {
        if (!SDCardUtils.isSDCardEnable()) return null;
        try {
            return FileUtils.getAbsolutePath(DevUtils.getContext().getExternalFilesDir(Environment.DIRECTORY_MUSIC));
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getExternalAppMusicPath");
        }
        return null;
    }

    /**
     * 获取外存应用播客路径 - path/storage/emulated/0/Android/data/package/files/Podcasts
     * @return /storage/emulated/0/Android/data/package/files/Podcasts
     */
    public static String getExternalAppPodcastsPath() {
        if (!SDCardUtils.isSDCardEnable()) return null;
        try {
            return FileUtils.getAbsolutePath(DevUtils.getContext().getExternalFilesDir(Environment.DIRECTORY_PODCASTS));
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getExternalAppPodcastsPath");
        }
        return null;
    }

    /**
     * 获取外存应用铃声路径 - path/storage/emulated/0/Android/data/package/files/Ringtones
     * @return /storage/emulated/0/Android/data/package/files/Ringtones
     */
    public static String getExternalAppRingtonesPath() {
        if (!SDCardUtils.isSDCardEnable()) return null;
        try {
            return FileUtils.getAbsolutePath(DevUtils.getContext().getExternalFilesDir(Environment.DIRECTORY_RINGTONES));
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getExternalAppRingtonesPath");
        }
        return null;
    }

    /**
     * 获取外存应用闹铃路径 - path/storage/emulated/0/Android/data/package/files/Alarms
     * @return /storage/emulated/0/Android/data/package/files/Alarms
     */
    public static String getExternalAppAlarmsPath() {
        if (!SDCardUtils.isSDCardEnable()) return null;
        try {
            return FileUtils.getAbsolutePath(DevUtils.getContext().getExternalFilesDir(Environment.DIRECTORY_ALARMS));
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getExternalAppAlarmsPath");
        }
        return null;
    }

    /**
     * 获取外存应用通知路径 - path/storage/emulated/0/Android/data/package/files/Notifications
     * @return /storage/emulated/0/Android/data/package/files/Notifications
     */
    public static String getExternalAppNotificationsPath() {
        if (!SDCardUtils.isSDCardEnable()) return null;
        try {
            return FileUtils.getAbsolutePath(DevUtils.getContext().getExternalFilesDir(Environment.DIRECTORY_NOTIFICATIONS));
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getExternalAppNotificationsPath");
        }
        return null;
    }

    /**
     * 获取外存应用图片路径 - path/storage/emulated/0/Android/data/package/files/Pictures
     * @return path /storage/emulated/0/Android/data/package/files/Pictures
     */
    public static String getExternalAppPicturesPath() {
        if (!SDCardUtils.isSDCardEnable()) return null;
        try {
            return FileUtils.getAbsolutePath(DevUtils.getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES));
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getExternalAppPicturesPath");
        }
        return null;
    }

    /**
     * 获取外存应用影片路径 - path/storage/emulated/0/Android/data/package/files/Movies
     * @return /storage/emulated/0/Android/data/package/files/Movies
     */
    public static String getExternalAppMoviesPath() {
        if (!SDCardUtils.isSDCardEnable()) return null;
        try {
            return FileUtils.getAbsolutePath(DevUtils.getContext().getExternalFilesDir(Environment.DIRECTORY_MOVIES));
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getExternalAppMoviesPath");
        }
        return null;
    }

    /**
     * 获取外存应用下载路径 - path/storage/emulated/0/Android/data/package/files/Download
     * @return /storage/emulated/0/Android/data/package/files/Download
     */
    public static String getExternalAppDownloadPath() {
        if (!SDCardUtils.isSDCardEnable()) return null;
        try {
            return FileUtils.getAbsolutePath(DevUtils.getContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getExternalAppDownloadPath");
        }
        return null;
    }

    /**
     * 获取外存应用数码相机图片路径 - path/storage/emulated/0/Android/data/package/files/DCIM
     * @return /storage/emulated/0/Android/data/package/files/DCIM
     */
    public static String getExternalAppDcimPath() {
        if (!SDCardUtils.isSDCardEnable()) return null;
        try {
            return FileUtils.getAbsolutePath(DevUtils.getContext().getExternalFilesDir(Environment.DIRECTORY_DCIM));
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getExternalAppDcimPath");
        }
        return null;
    }

    /**
     * 获取外存应用文档路径 - path/storage/emulated/0/Android/data/package/files/Documents
     * @return /storage/emulated/0/Android/data/package/files/Documents
     */
    public static String getExternalAppDocumentsPath() {
        if (!SDCardUtils.isSDCardEnable()) return null;
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                return FileUtils.getAbsolutePath(DevUtils.getContext().getExternalFilesDir(null)) + "/Documents";
            }
            return FileUtils.getAbsolutePath(DevUtils.getContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS));
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getExternalAppDocumentsPath");
        }
        return null;
    }

    /**
     * 获取外存应用 OBB 路径 - path/storage/emulated/0/Android/obb/package
     * @return /storage/emulated/0/Android/obb/package
     */
    public static String getExternalAppObbPath() {
        if (!SDCardUtils.isSDCardEnable()) return null;
        try {
            return FileUtils.getAbsolutePath(DevUtils.getContext().getObbDir());
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getExternalAppObbPath");
        }
        return null;
    }

    // ===============================
    // = 内部存储 /data/data/package =
    // ===============================

    /**
     * detail: 内部存储路径类
     * @author Ttt
     * <pre>
     *     内部存储, 只有本 App 才可以访问, 其他应用无法访问
     *     路径: /data/data/package/ 目录
     *     App 卸载该目录文件会全部清除
     *     无需读写权限
     * </pre>
     */
    public static final class InternalPath {

        private InternalPath() {
        }

        /**
         * 获取 Android 系统根目录 - path /system
         * @return /system
         */
        public String getRootPath() {
            return FileUtils.getAbsolutePath(getRootDirectory());
        }

        /**
         * 获取 Android 系统根目录 - path /system
         * @return /system
         */
        public File getRootDirectory() {
            return Environment.getRootDirectory();
        }

        // =

        /**
         * 获取 data 目录 - path /data
         * @return /data
         */
        public String getDataPath() {
            return FileUtils.getAbsolutePath(getDataDirectory());
        }

        /**
         * 获取 data 目录 - path /data
         * @return /system
         */
        public File getDataDirectory() {
            return Environment.getDataDirectory();
        }

        // =

        /**
         * 获取下载缓存目录 - path data/cache
         * @return data/cache
         */
        public String getDownloadCachePath() {
            return FileUtils.getAbsolutePath(getDownloadCacheDirectory());
        }

        /**
         * 获取下载缓存目录 - path data/cache
         * @return data/cache
         */
        public File getDownloadCacheDirectory() {
            return Environment.getDownloadCacheDirectory();
        }

        // =

        /**
         * 获取应用内部存储数据路径 - path /data/data/package
         * @return /data/data/package
         */
        public String getAppDataPath() {
            return FileUtils.getAbsolutePath(getAppDataDir());
        }

        /**
         * 获取应用内部存储数据路径 - path /data/data/package
         * @return /data/data/package
         */
        public File getAppDataDir() {
            try {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                    return FileUtils.getFile(DevUtils.getContext().getApplicationInfo().dataDir);
                }
                return DevUtils.getContext().getDataDir();
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getAppDataDir");
            }
            return null;
        }

        /**
         * 获取应用内部存储数据路径 - path /data/data/package
         * @param fileName 文件名
         * @return /data/data/package
         */
        public String getAppDataPath(final String fileName) {
            return FileUtils.getAbsolutePath(FileUtils.getFile(getAppDataPath(), fileName));
        }

        /**
         * 获取应用内部存储数据路径 - path /data/data/package
         * @param fileName 文件名
         * @return /data/data/package
         */
        public File getAppDataDir(final String fileName) {
            return FileUtils.getFile(getAppDataPath(), fileName);
        }

        // =

        /**
         * 获取应用内部存储缓存路径 - path /data/data/package/cache
         * @return /data/data/package/cache
         */
        public String getAppCachePath() {
            return FileUtils.getAbsolutePath(getAppCacheDir());
        }

        /**
         * 获取应用内部存储缓存路径 - path /data/data/package/cache
         * @return /data/data/package/cache
         */
        public File getAppCacheDir() {
            try {
                return DevUtils.getContext().getCacheDir();
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getAppCacheDir");
            }
            return null;
        }

        /**
         * 获取应用内部存储缓存路径 - path /data/data/package/cache
         * @param fileName 文件名
         * @return /data/data/package/cache
         */
        public String getAppCachePath(final String fileName) {
            return FileUtils.getAbsolutePath(FileUtils.getFile(getAppCachePath(), fileName));
        }

        /**
         * 获取应用内部存储缓存路径 - path /data/data/package/cache
         * @param fileName 文件名
         * @return /data/data/package/cache
         */
        public File getAppCacheDir(final String fileName) {
            return FileUtils.getFile(getAppCachePath(), fileName);
        }

        // =

        /**
         * 获取应用内部存储代码缓存路径 - path /data/data/package/code_cache
         * @return /data/data/package/code_cache
         */
        public String getAppCodeCachePath() {
            return FileUtils.getAbsolutePath(getAppCodeCacheDir());
        }

        /**
         * 获取应用内部存储代码缓存路径 - path /data/data/package/code_cache
         * @return /data/data/package/code_cache
         */
        public File getAppCodeCacheDir() {
            try {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    return FileUtils.getFile(getAppDataPath(), "code_cache");
                }
                return DevUtils.getContext().getCodeCacheDir();
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getAppCodeCacheDir");
            }
            return null;
        }

        // =

        /**
         * 获取应用内部存储数据库路径 - path /data/data/package/databases
         * @return /data/data/package/databases
         */
        public String getAppDbsPath() {
            try {
                String filePath = getAppDataPath();
                if (filePath == null) return null;
                return filePath + "/databases";
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getAppDbsPath");
            }
            return null;
        }

        /**
         * 获取应用内部存储数据库路径 - path /data/data/package/databases
         * @return /data/data/package/databases
         */
        public File getAppDbsDir() {
            return FileUtils.getFile(getAppDbsPath());
        }

        // =

        /**
         * 获取应用内部存储数据库路径 - path /data/data/package/databases/name
         * @param name 数据库名
         * @return /data/data/package/databases/name
         */
        public String getAppDbPath(final String name) {
            return FileUtils.getAbsolutePath(getAppDbFile(name));
        }

        /**
         * 获取应用内部存储数据库路径 - path /data/data/package/databases/name
         * @param name 数据库名
         * @return /data/data/package/databases/name
         */
        public File getAppDbFile(final String name) {
            try {
                return DevUtils.getContext().getDatabasePath(name);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getAppDbFile - " + name);
            }
            return null;
        }

        // =

        /**
         * 获取应用内部存储文件路径 - path/data/data/package/files
         * @return /data/data/package/files
         */
        public String getAppFilesPath() {
            return FileUtils.getAbsolutePath(getAppFilesDir());
        }

        /**
         * 获取应用内部存储文件路径 - path/data/data/package/files
         * @return /data/data/package/files
         */
        public File getAppFilesDir() {
            try {
                return DevUtils.getContext().getFilesDir();
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getAppFilesDir");
            }
            return null;
        }

        // =

        /**
         * 获取应用内部存储 SP 路径 - path/data/data/package/shared_prefs
         * @return /data/data/package/shared_prefs
         */
        public String getAppSpPath() {
            try {
                String filePath = getAppDataPath();
                if (filePath == null) return null;
                return filePath + "/shared_prefs";
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getAppSpPath");
            }
            return null;
        }

        /**
         * 获取应用内部存储 SP 路径 - path/data/data/package/shared_prefs
         * @return /data/data/package/shared_prefs
         */
        public File getAppSpDir() {
            return FileUtils.getFile(getAppSpPath());
        }

        /**
         * 获取应用内部存储 SP 路径 - path/data/data/package/shared_prefs
         * @param spName SP 文件名
         * @return /data/data/package/shared_prefs
         */
        public String getAppSpPath(final String spName) {
            return FileUtils.getAbsolutePath(getAppSpFile(spName));
        }

        /**
         * 获取应用内部存储 SP 路径 - path/data/data/package/shared_prefs
         * @param spName SP 文件名
         * @return /data/data/package/shared_prefs
         */
        public File getAppSpFile(final String spName) {
            try {
                return FileUtils.getFile(getAppSpPath(), spName);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getAppSpFile - " + spName);
            }
            return null;
        }

        // =

        /**
         * 获取应用内部存储未备份文件路径 - path/data/data/package/no_backup
         * @return /data/data/package/no_backup
         */
        public String getAppNoBackupFilesPath() {
            return FileUtils.getAbsolutePath(getAppNoBackupFilesDir());
        }

        /**
         * 获取应用内部存储未备份文件路径 - path/data/data/package/no_backup
         * @return /data/data/package/no_backup
         */
        public File getAppNoBackupFilesDir() {
            try {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    return FileUtils.getFile(getAppDataPath(), "no_backup");
                }
                return DevUtils.getContext().getNoBackupFilesDir();
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getAppNoBackupFilesDir");
            }
            return null;
        }
    }
}