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
 * <pre>
 *     Android 中的存储路径之内部存储
 *     @see <a href="https://www.jianshu.com/p/c04b8899cf85"/>
 *     Android 中的存储路径之外部存储
 *     @see <a href="https://www.jianshu.com/p/2881260e74d7"/>
 * </pre>
 */
public final class PathUtils {

    private PathUtils() {
    }

    // 日志 TAG
    private static final String TAG = PathUtils.class.getSimpleName();
    // 内部存储路径类
    private static final InternalPath sInternalPath = new InternalPath();
    // 应用外部存储路径类
    private static final AppExternalPath sAppExternalPath = new AppExternalPath();

    /**
     * 获取内部存储路径类
     * @return {@link InternalPath}
     */
    public static InternalPath getInternal() {
        return sInternalPath;
    }

    /**
     * 获取应用外部存储路径类
     * @return {@link AppExternalPath}
     */
    public static AppExternalPath getAppExternal() {
        return sAppExternalPath;
    }

    // ==========================================================
    // = 应用外部存储 /storage/emulated/0/Android/data/package/ =
    // ==========================================================

    /**
     * detail: 应用外部存储路径类
     * @author Ttt
     * <pre>
     *     外部存储 ( 内部存储之外的路径都是外部存储 ), 属于 SDCard 路径中为 APP 创建的私有目录
     *     Android 7.0 以后其他应用需通过 FileProvider 方式访问其私有目录
     *     路径: /storage/emulated/0/Android/data/package/ 目录
     *     App 卸载该目录文件会全部清除
     *     无需读写权限
     *     推荐常用私有目录
     * </pre>
     */
    public static final class AppExternalPath {

        private AppExternalPath() {
        }

        /**
         * 获取应用外部存储缓存路径 - path /storage/emulated/0/Android/data/package/cache
         * @return /storage/emulated/0/Android/data/package/cache
         */
        public String getAppCachePath() {
            if (!SDCardUtils.isSDCardEnable()) return null;
            try {
                return FileUtils.getAbsolutePath(DevUtils.getContext().getExternalCacheDir());
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getAppCachePath");
            }
            return null;
        }
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
            return FileUtils.getAbsolutePath(getAppDbsDir());
        }

        /**
         * 获取应用内部存储数据库路径 - path /data/data/package/databases
         * @return /data/data/package/databases
         */
        public File getAppDbsDir() {
            return FileUtils.getFile(getAppDataPath(), "databases");
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
         * 获取应用内部存储文件路径 - path /data/data/package/files
         * @return /data/data/package/files
         */
        public String getAppFilesPath() {
            return FileUtils.getAbsolutePath(getAppFilesDir());
        }

        /**
         * 获取应用内部存储文件路径 - path /data/data/package/files
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
         * 获取应用内部存储 SP 路径 - path /data/data/package/shared_prefs
         * @return /data/data/package/shared_prefs
         */
        public String getAppSpPath() {
            return FileUtils.getAbsolutePath(getAppSpDir());
        }

        /**
         * 获取应用内部存储 SP 路径 - path /data/data/package/shared_prefs
         * @return /data/data/package/shared_prefs
         */
        public File getAppSpDir() {
            return FileUtils.getFile(getAppDataPath(), "shared_prefs");
        }

        /**
         * 获取应用内部存储 SP 路径 - path /data/data/package/shared_prefs
         * @param spName SP 文件名
         * @return /data/data/package/shared_prefs
         */
        public String getAppSpPath(final String spName) {
            return FileUtils.getAbsolutePath(getAppSpFile(spName));
        }

        /**
         * 获取应用内部存储 SP 路径 - path /data/data/package/shared_prefs
         * @param spName SP 文件名
         * @return /data/data/package/shared_prefs
         */
        public File getAppSpFile(final String spName) {
            return FileUtils.getFile(getAppSpPath(), spName);
        }

        // =

        /**
         * 获取应用内部存储未备份文件路径 - path /data/data/package/no_backup
         * @return /data/data/package/no_backup
         */
        public String getAppNoBackupFilesPath() {
            return FileUtils.getAbsolutePath(getAppNoBackupFilesDir());
        }

        /**
         * 获取应用内部存储未备份文件路径 - path /data/data/package/no_backup
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