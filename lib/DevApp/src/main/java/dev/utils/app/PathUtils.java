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
 *     处理外部存储中的媒体文件
 *     @see <a href="https://developer.android.google.cn/training/data-storage/files/media"/>
 *     管理分区外部存储访问
 *     @see <a href="https://developer.android.google.cn/training/data-storage/files/external-scoped"/>
 *     <p></p>
 *     内部存储 : /data/data/package/ 目录
 *     外部存储 ( 私有目录 ) : /storage/emulated/0/Android/data/package/ 目录
 *     外部存储 ( 公开目录 ) : /storage/emulated/0/ 目录
 *     <p></p>
 *     推荐使用 {@link PathUtils#getAppExternal()}、 {@link PathUtils#getInternal()} ( 外部存储 ( 私有目录 ) 、内部存储 )
 *     Android 11 ( R ) 对外部存储 ( 公开目录 ) 进行限制 Scoped Storage, 或使用 MediaStore 对部分公开目录进行操作
 *     <p></p>
 *     关于路径建议及兼容:
 *     推荐隐私数据存储到内部存储中 {@link PathUtils#getInternal()}
 *     应用数据全部存储到 外部存储 ( 私有目录 ) 中 {@link PathUtils#getAppExternal()}
 *     SDCard 目录推荐使用 {@link MediaStoreUtils} 存储常用 Image、Video、Document 等, 尽量减少需适配情况
 *     并且能够完善项目存储目录结构, 减少外部存储 ( 公开目录 ) 目录混乱等
 * </pre>
 */
public final class PathUtils {

    private PathUtils() {
    }

    // 日志 TAG
    private static final String TAG = PathUtils.class.getSimpleName();

    // 内部存储路径类
    private static final InternalPath    sInternalPath    = new InternalPath();
    // 应用外部存储路径类
    private static final AppExternalPath sAppExternalPath = new AppExternalPath();
    // SDCard 外部存储路径类
    private static final SDCardPath      sSDCardPath      = new SDCardPath();
    // Internal
    public static final  String          INTERNAL         = "internal";
    // External
    public static final  String          EXTERNAL         = "external";

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

    /**
     * 获取 SDCard 外部存储路径类
     * @return {@link SDCardPath}
     */
    public static SDCardPath getSDCard() {
        return sSDCardPath;
    }

    /**
     * 是否获得 MANAGE_EXTERNAL_STORAGE 权限
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isExternalStorageManager() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        }
        return false;
    }

    /**
     * 检查是否有 MANAGE_EXTERNAL_STORAGE 权限并跳转设置页面
     * <pre>
     *     MANAGE_EXTERNAL_STORAGE
     *     @see <a href="https://developer.android.google.cn/preview/privacy/storage"/>
     * </pre>
     * @return {@code true} yes, {@code false} no
     */
    public static boolean checkExternalStorageAndIntentSetting() {
        if (!isExternalStorageManager()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                AppUtils.startActivity(IntentUtils.getManageAppAllFilesAccessPermissionIntent());
            }
            return false;
        }
        return true;
    }

    // ======================================
    // = SDCard 外部存储 /storage/emulated/0/ =
    // ======================================

    /**
     * detail: SDCard 外部存储路径类
     * @author Ttt
     * <pre>
     *     外部存储, 属于 SDCard 公开目录
     *     路径: /storage/emulated/0/ 目录
     *     需读写权限
     * </pre>
     */
    public static final class SDCardPath {

        private SDCardPath() {
        }

        /**
         * 判断 SDCard 是否正常挂载
         * @return {@code true} yes, {@code false} no
         */
        public boolean isSDCardEnable() {
            return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
        }

        /**
         * 获取 SDCard 外部存储路径 ( path /storage/emulated/0/ )
         * @return /storage/emulated/0/
         * @deprecated 推荐使用 {@link PathUtils#getAppExternal()}、 {@link PathUtils#getInternal()} ( 外部存储 ( 私有目录 ) 、内部存储 )
         * Android 11 ( R ) 对外部存储 ( 公开目录 ) 进行限制 Scoped Storage, 或使用 MediaStore 对部分公开目录进行操作
         */
        @Deprecated
        public File getSDCardFile() {
            return Environment.getExternalStorageDirectory();
        }

        /**
         * 获取 SDCard 外部存储路径 ( path /storage/emulated/0/ )
         * @return /storage/emulated/0/
         */
        @Deprecated
        public String getSDCardPath() {
            return FileUtils.getAbsolutePath(getSDCardFile());
        }

        // =

        /**
         * 获取 SDCard 外部存储路径 ( path /storage/emulated/0/ )
         * @param fileName 文件名
         * @return /storage/emulated/0/
         */
        @Deprecated
        public File getSDCardFile(final String fileName) {
            return FileUtils.getFile(getSDCardPath(), fileName);
        }

        /**
         * 获取 SDCard 外部存储路径 ( path /storage/emulated/0/ )
         * @param fileName 文件名
         * @return /storage/emulated/0/
         */
        @Deprecated
        public String getSDCardPath(final String fileName) {
            return FileUtils.getAbsolutePath(getSDCardFile(fileName));
        }

        // =

        /**
         * 获取 SDCard 外部存储文件路径 ( path /storage/emulated/0/ )
         * @param type 文件类型
         * @return /storage/emulated/0/
         */
        @Deprecated
        public String getExternalStoragePublicPath(final String type) {
            return FileUtils.getAbsolutePath(getExternalStoragePublicDir(type));
        }

        /**
         * 获取 SDCard 外部存储文件路径 ( path /storage/emulated/0/ )
         * <pre>
         *     Environment.STANDARD_DIRECTORIES
         * </pre>
         * @param type 文件类型
         * @return /storage/emulated/0/
         * @deprecated 推荐使用 {@link PathUtils#getAppExternal()}、 {@link PathUtils#getInternal()} ( 外部存储 ( 私有目录 ) 、内部存储 )
         * Android 11 ( R ) 对外部存储 ( 公开目录 ) 进行限制 Scoped Storage, 或使用 MediaStore 对部分公开目录进行操作
         */
        @Deprecated
        public File getExternalStoragePublicDir(final String type) {
            if (!getSDCard().isSDCardEnable()) return null;
            try {
                return Environment.getExternalStoragePublicDirectory(type);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getExternalStoragePublicDir");
            }
            return null;
        }

        // =

        /**
         * 获取 SDCard 外部存储音乐路径 ( path /storage/emulated/0/Music )
         * @return /storage/emulated/0/Music
         */
        @Deprecated
        public String getMusicPath() {
            return getExternalStoragePublicPath(Environment.DIRECTORY_MUSIC);
        }

        /**
         * 获取 SDCard 外部存储音乐路径 ( path /storage/emulated/0/Music )
         * @return /storage/emulated/0/Music
         */
        @Deprecated
        public File getMusicDir() {
            return getExternalStoragePublicDir(Environment.DIRECTORY_MUSIC);
        }

        // =

        /**
         * 获取 SDCard 外部存储播客路径 ( path /storage/emulated/0/Podcasts )
         * @return /storage/emulated/0/Podcasts
         */
        @Deprecated
        public String getPodcastsPath() {
            return getExternalStoragePublicPath(Environment.DIRECTORY_PODCASTS);
        }

        /**
         * 获取 SDCard 外部存储播客路径 ( path /storage/emulated/0/Podcasts )
         * @return /storage/emulated/0/Podcasts
         */
        @Deprecated
        public File getPodcastsDir() {
            return getExternalStoragePublicDir(Environment.DIRECTORY_PODCASTS);
        }

        // =

        /**
         * 获取 SDCard 外部存储铃声路径 ( path /storage/emulated/0/Ringtones )
         * @return /storage/emulated/0/Ringtones
         */
        @Deprecated
        public String getRingtonesPath() {
            return getExternalStoragePublicPath(Environment.DIRECTORY_RINGTONES);
        }

        /**
         * 获取 SDCard 外部存储铃声路径 ( path /storage/emulated/0/Ringtones )
         * @return /storage/emulated/0/Ringtones
         */
        @Deprecated
        public File getRingtonesDir() {
            return getExternalStoragePublicDir(Environment.DIRECTORY_RINGTONES);
        }

        // =

        /**
         * 获取 SDCard 外部存储闹铃路径 ( path /storage/emulated/0/Alarms )
         * @return /storage/emulated/0/Alarms
         */
        @Deprecated
        public String getAlarmsPath() {
            return getExternalStoragePublicPath(Environment.DIRECTORY_ALARMS);
        }

        /**
         * 获取 SDCard 外部存储闹铃路径 ( path /storage/emulated/0/Alarms )
         * @return /storage/emulated/0/Alarms
         */
        @Deprecated
        public File getAlarmsDir() {
            return getExternalStoragePublicDir(Environment.DIRECTORY_ALARMS);
        }

        // =

        /**
         * 获取 SDCard 外部存储通知路径 ( path /storage/emulated/0/Notifications )
         * @return /storage/emulated/0/Notifications
         */
        @Deprecated
        public String getNotificationsPath() {
            return getExternalStoragePublicPath(Environment.DIRECTORY_NOTIFICATIONS);
        }

        /**
         * 获取 SDCard 外部存储通知路径 ( path /storage/emulated/0/Notifications )
         * @return /storage/emulated/0/Notifications
         */
        @Deprecated
        public File getNotificationsDir() {
            return getExternalStoragePublicDir(Environment.DIRECTORY_NOTIFICATIONS);
        }

        // =

        /**
         * 获取 SDCard 外部存储图片路径 ( path /storage/emulated/0/Pictures )
         * @return /storage/emulated/0/Pictures
         */
        @Deprecated
        public String getPicturesPath() {
            return getExternalStoragePublicPath(Environment.DIRECTORY_PICTURES);
        }

        /**
         * 获取 SDCard 外部存储图片路径 ( path /storage/emulated/0/Pictures )
         * @return /storage/emulated/0/Pictures
         */
        @Deprecated
        public File getPicturesDir() {
            return getExternalStoragePublicDir(Environment.DIRECTORY_PICTURES);
        }

        // =

        /**
         * 获取 SDCard 外部存储影片路径 ( path /storage/emulated/0/Movies )
         * @return /storage/emulated/0/Movies
         */
        @Deprecated
        public String getMoviesPath() {
            return getExternalStoragePublicPath(Environment.DIRECTORY_MOVIES);
        }

        /**
         * 获取 SDCard 外部存储影片路径 ( path /storage/emulated/0/Movies )
         * @return /storage/emulated/0/Movies
         */
        @Deprecated
        public File getMoviesDir() {
            return getExternalStoragePublicDir(Environment.DIRECTORY_MOVIES);
        }

        // =

        /**
         * 获取 SDCard 外部存储下载路径 ( path /storage/emulated/0/Download )
         * @return /storage/emulated/0/Download
         */
        @Deprecated
        public String getDownloadPath() {
            return getExternalStoragePublicPath(Environment.DIRECTORY_DOWNLOADS);
        }

        /**
         * 获取 SDCard 外部存储下载路径 ( path /storage/emulated/0/Download )
         * @return /storage/emulated/0/Download
         */
        @Deprecated
        public File getDownloadDir() {
            return getExternalStoragePublicDir(Environment.DIRECTORY_DOWNLOADS);
        }

        // =

        /**
         * 获取 SDCard 外部存储数码相机图片路径 ( path /storage/emulated/0/DCIM )
         * @return /storage/emulated/0/DCIM
         */
        @Deprecated
        public String getDCIMPath() {
            return getExternalStoragePublicPath(Environment.DIRECTORY_DCIM);
        }

        /**
         * 获取 SDCard 外部存储数码相机图片路径 ( path /storage/emulated/0/DCIM )
         * @return /storage/emulated/0/DCIM
         */
        @Deprecated
        public File getDCIMDir() {
            return getExternalStoragePublicDir(Environment.DIRECTORY_DCIM);
        }

        // =

        /**
         * 获取 SDCard 外部存储文档路径 ( path /storage/emulated/0/Documents )
         * @return /storage/emulated/0/Documents
         */
        @Deprecated
        public String getDocumentsPath() {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                return getExternalStoragePublicPath("Documents");
            }
            return getExternalStoragePublicPath(Environment.DIRECTORY_DOCUMENTS);
        }

        /**
         * 获取 SDCard 外部存储文档路径 ( path /storage/emulated/0/Documents )
         * @return /storage/emulated/0/Documents
         */
        @Deprecated
        public File getDocumentsDir() {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                return getExternalStoragePublicDir("Documents");
            }
            return getExternalStoragePublicDir(Environment.DIRECTORY_DOCUMENTS);
        }

        // =

        /**
         * 获取 SDCard 外部存储有声读物路径 ( path /storage/emulated/0/Audiobooks )
         * @return /storage/emulated/0/Audiobooks
         */
        @Deprecated
        public String getAudiobooksPath() {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                return getExternalStoragePublicPath("Audiobooks");
            }
            return getExternalStoragePublicPath(Environment.DIRECTORY_AUDIOBOOKS);
        }

        /**
         * 获取 SDCard 外部存储有声读物路径 ( path /storage/emulated/0/Audiobooks )
         * @return /storage/emulated/0/Audiobooks
         */
        @Deprecated
        public File getAudiobooksDir() {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                return getExternalStoragePublicDir("Audiobooks");
            }
            return getExternalStoragePublicDir(Environment.DIRECTORY_AUDIOBOOKS);
        }
    }

    // =======================================================
    // = 应用外部存储 /storage/emulated/0/Android/data/package/ =
    // =======================================================

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
         * 获取应用外部存储数据路径 ( path /storage/emulated/0/Android/data/package )
         * @return /storage/emulated/0/Android/data/package
         */
        public String getAppDataPath() {
            return FileUtils.getAbsolutePath(getAppDataDir());
        }

        /**
         * 获取应用外部存储数据路径 ( path /storage/emulated/0/Android/data/package )
         * @return /storage/emulated/0/Android/data/package
         */
        public File getAppDataDir() {
            if (!getSDCard().isSDCardEnable()) return null;
            try {
                return DevUtils.getContext().getExternalCacheDir().getParentFile();
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getAppDataDir");
            }
            return null;
        }

        /**
         * 获取应用外部存储数据路径 ( path /storage/emulated/0/Android/data/package )
         * @param fileName 文件名
         * @return /storage/emulated/0/Android/data/package
         */
        public String getAppDataPath(final String fileName) {
            return FileUtils.getAbsolutePath(FileUtils.getFile(getAppDataPath(), fileName));
        }

        /**
         * 获取应用外部存储数据路径 ( path /storage/emulated/0/Android/data/package )
         * @param fileName 文件名
         * @return /storage/emulated/0/Android/data/package
         */
        public File getAppDataDir(final String fileName) {
            return FileUtils.getFile(getAppDataPath(), fileName);
        }

        // =

        /**
         * 获取应用外部存储缓存路径 ( path /storage/emulated/0/Android/data/package/cache )
         * @return /storage/emulated/0/Android/data/package/cache
         */
        public String getAppCachePath() {
            return FileUtils.getAbsolutePath(getAppCacheDir());
        }

        /**
         * 获取应用外部存储缓存路径 ( path /storage/emulated/0/Android/data/package/cache )
         * @return /storage/emulated/0/Android/data/package/cache
         */
        public File getAppCacheDir() {
            if (!getSDCard().isSDCardEnable()) return null;
            try {
                return DevUtils.getContext().getExternalCacheDir();
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getAppCacheDir");
            }
            return null;
        }

        /**
         * 获取应用外部存储缓存路径 ( path /storage/emulated/0/Android/data/package/cache )
         * @param fileName 文件名
         * @return /storage/emulated/0/Android/data/package/cache
         */
        public String getAppCachePath(final String fileName) {
            return FileUtils.getAbsolutePath(FileUtils.getFile(getAppCachePath(), fileName));
        }

        /**
         * 获取应用外部存储缓存路径 ( path /storage/emulated/0/Android/data/package/cache )
         * @param fileName 文件名
         * @return /storage/emulated/0/Android/data/package/cache
         */
        public File getAppCacheDir(final String fileName) {
            return FileUtils.getFile(getAppCachePath(), fileName);
        }

        // =

        /**
         * 获取应用外部存储文件路径 ( path /storage/emulated/0/Android/data/package/files )
         * @param type 文件类型
         * @return /storage/emulated/0/Android/data/package/files
         */
        public String getExternalFilesPath(final String type) {
            return FileUtils.getAbsolutePath(getExternalFilesDir(type));
        }

        /**
         * 获取应用外部存储文件路径 ( path /storage/emulated/0/Android/data/package/files )
         * <pre>
         *     Environment.STANDARD_DIRECTORIES
         * </pre>
         * @param type 文件类型
         * @return /storage/emulated/0/Android/data/package/files
         */
        public File getExternalFilesDir(final String type) {
            if (!getSDCard().isSDCardEnable()) return null;
            try {
                return DevUtils.getContext().getExternalFilesDir(type);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getExternalFilesDir");
            }
            return null;
        }

        // =

        /**
         * 获取应用外部存储文件路径 ( path /storage/emulated/0/Android/data/package/files )
         * @return /storage/emulated/0/Android/data/package/files
         */
        public String getAppFilesPath() {
            return getExternalFilesPath(null);
        }

        /**
         * 获取应用外部存储文件路径 ( path /storage/emulated/0/Android/data/package/files )
         * @return /storage/emulated/0/Android/data/package/files
         */
        public File getAppFilesDir() {
            return getExternalFilesDir(null);
        }

        // =

        /**
         * 获取应用外部存储文件路径 ( path /storage/emulated/0/Android/data/package/files )
         * @param fileName 文件名
         * @return /storage/emulated/0/Android/data/package/files
         */
        public String getAppFilesPath(final String fileName) {
            return FileUtils.getAbsolutePath(getAppFilesDir(fileName));
        }

        /**
         * 获取应用外部存储文件路径 ( path /storage/emulated/0/Android/data/package/files )
         * @param fileName 文件名
         * @return /storage/emulated/0/Android/data/package/files
         */
        public File getAppFilesDir(final String fileName) {
            return FileUtils.getFile(getAppFilesPath(), fileName);
        }

        // =

        /**
         * 获取应用外部存储音乐路径 ( path /storage/emulated/0/Android/data/package/files/Music )
         * @return /storage/emulated/0/Android/data/package/files/Music
         */
        public String getAppMusicPath() {
            return getExternalFilesPath(Environment.DIRECTORY_MUSIC);
        }

        /**
         * 获取应用外部存储音乐路径 ( path /storage/emulated/0/Android/data/package/files/Music )
         * @return /storage/emulated/0/Android/data/package/files/Music
         */
        public File getAppMusicDir() {
            return getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        }

        // =

        /**
         * 获取应用外部存储播客路径 ( path /storage/emulated/0/Android/data/package/files/Podcasts )
         * @return /storage/emulated/0/Android/data/package/files/Podcasts
         */
        public String getAppPodcastsPath() {
            return getExternalFilesPath(Environment.DIRECTORY_PODCASTS);
        }

        /**
         * 获取应用外部存储播客路径 ( path /storage/emulated/0/Android/data/package/files/Podcasts )
         * @return /storage/emulated/0/Android/data/package/files/Podcasts
         */
        public File getAppPodcastsDir() {
            return getExternalFilesDir(Environment.DIRECTORY_PODCASTS);
        }

        // =

        /**
         * 获取应用外部存储铃声路径 ( path /storage/emulated/0/Android/data/package/files/Ringtones )
         * @return /storage/emulated/0/Android/data/package/files/Ringtones
         */
        public String getAppRingtonesPath() {
            return getExternalFilesPath(Environment.DIRECTORY_RINGTONES);
        }

        /**
         * 获取应用外部存储铃声路径 ( path /storage/emulated/0/Android/data/package/files/Ringtones )
         * @return /storage/emulated/0/Android/data/package/files/Ringtones
         */
        public File getAppRingtonesDir() {
            return getExternalFilesDir(Environment.DIRECTORY_RINGTONES);
        }

        // =

        /**
         * 获取应用外部存储闹铃路径 ( path /storage/emulated/0/Android/data/package/files/Alarms )
         * @return /storage/emulated/0/Android/data/package/files/Alarms
         */
        public String getAppAlarmsPath() {
            return getExternalFilesPath(Environment.DIRECTORY_ALARMS);
        }

        /**
         * 获取应用外部存储闹铃路径 ( path /storage/emulated/0/Android/data/package/files/Alarms )
         * @return /storage/emulated/0/Android/data/package/files/Alarms
         */
        public File getAppAlarmsDir() {
            return getExternalFilesDir(Environment.DIRECTORY_ALARMS);
        }

        // =

        /**
         * 获取应用外部存储通知路径 ( path /storage/emulated/0/Android/data/package/files/Notifications )
         * @return /storage/emulated/0/Android/data/package/files/Notifications
         */
        public String getAppNotificationsPath() {
            return getExternalFilesPath(Environment.DIRECTORY_NOTIFICATIONS);
        }

        /**
         * 获取应用外部存储通知路径 ( path /storage/emulated/0/Android/data/package/files/Notifications )
         * @return /storage/emulated/0/Android/data/package/files/Notifications
         */
        public File getAppNotificationsDir() {
            return getExternalFilesDir(Environment.DIRECTORY_NOTIFICATIONS);
        }

        // =

        /**
         * 获取应用外部存储图片路径 ( path /storage/emulated/0/Android/data/package/files/Pictures )
         * @return /storage/emulated/0/Android/data/package/files/Pictures
         */
        public String getAppPicturesPath() {
            return getExternalFilesPath(Environment.DIRECTORY_PICTURES);
        }

        /**
         * 获取应用外部存储图片路径 ( path /storage/emulated/0/Android/data/package/files/Pictures )
         * @return /storage/emulated/0/Android/data/package/files/Pictures
         */
        public File getAppPicturesDir() {
            return getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        }

        // =

        /**
         * 获取应用外部存储影片路径 ( path /storage/emulated/0/Android/data/package/files/Movies )
         * @return /storage/emulated/0/Android/data/package/files/Movies
         */
        public String getAppMoviesPath() {
            return getExternalFilesPath(Environment.DIRECTORY_MOVIES);
        }

        /**
         * 获取应用外部存储影片路径 ( path /storage/emulated/0/Android/data/package/files/Movies )
         * @return /storage/emulated/0/Android/data/package/files/Movies
         */
        public File getAppMoviesDir() {
            return getExternalFilesDir(Environment.DIRECTORY_MOVIES);
        }

        // =

        /**
         * 获取应用外部存储下载路径 ( path /storage/emulated/0/Android/data/package/files/Download )
         * @return /storage/emulated/0/Android/data/package/files/Download
         */
        public String getAppDownloadPath() {
            return getExternalFilesPath(Environment.DIRECTORY_DOWNLOADS);
        }

        /**
         * 获取应用外部存储下载路径 ( path /storage/emulated/0/Android/data/package/files/Download )
         * @return /storage/emulated/0/Android/data/package/files/Download
         */
        public File getAppDownloadDir() {
            return getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        }

        // =

        /**
         * 获取应用外部存储数码相机图片路径 ( path /storage/emulated/0/Android/data/package/files/DCIM )
         * @return /storage/emulated/0/Android/data/package/files/DCIM
         */
        public String getAppDCIMPath() {
            return getExternalFilesPath(Environment.DIRECTORY_DCIM);
        }

        /**
         * 获取应用外部存储数码相机图片路径 ( path /storage/emulated/0/Android/data/package/files/DCIM )
         * @return /storage/emulated/0/Android/data/package/files/DCIM
         */
        public File getAppDCIMDir() {
            return getExternalFilesDir(Environment.DIRECTORY_DCIM);
        }

        // =

        /**
         * 获取应用外部存储文档路径 ( path /storage/emulated/0/Android/data/package/files/Documents )
         * @return /storage/emulated/0/Android/data/package/files/Documents
         */
        public String getAppDocumentsPath() {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                return getExternalFilesPath("Documents");
            }
            return getExternalFilesPath(Environment.DIRECTORY_DOCUMENTS);
        }

        /**
         * 获取应用外部存储文档路径 ( path /storage/emulated/0/Android/data/package/files/Documents )
         * @return /storage/emulated/0/Android/data/package/files/Documents
         */
        public File getAppDocumentsDir() {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                return getExternalFilesDir("Documents");
            }
            return getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        }

        // =

        /**
         * 获取应用外部存储有声读物路径 ( path /storage/emulated/0/Android/data/package/files/Audiobooks )
         * @return /storage/emulated/0/Android/data/package/files/Audiobooks
         */
        public String getAppAudiobooksPath() {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                return getExternalFilesPath("Audiobooks");
            }
            return getExternalFilesPath(Environment.DIRECTORY_AUDIOBOOKS);
        }

        /**
         * 获取应用外部存储有声读物路径 ( path /storage/emulated/0/Android/data/package/files/Audiobooks )
         * @return /storage/emulated/0/Android/data/package/files/Audiobooks
         */
        public File getAppAudiobooksDir() {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                return getExternalFilesDir("Audiobooks");
            }
            return getExternalFilesDir(Environment.DIRECTORY_AUDIOBOOKS);
        }

        // =

        /**
         * 获取应用外部存储 OBB 路径 ( path /storage/emulated/0/Android/obb/package )
         * @return /storage/emulated/0/Android/obb/package
         */
        public String getAppObbPath() {
            return FileUtils.getAbsolutePath(getAppObbDir());
        }

        /**
         * 获取应用外部存储 OBB 路径 ( path /storage/emulated/0/Android/obb/package )
         * @return /storage/emulated/0/Android/obb/package
         */
        public File getAppObbDir() {
            if (!getSDCard().isSDCardEnable()) return null;
            try {
                return DevUtils.getContext().getObbDir();
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getAppObbDir");
            }
            return null;
        }
    }

    // =============================
    // = 内部存储 /data/data/package =
    // =============================

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
         * 获取 Android 系统根目录 ( path /system )
         * @return /system
         */
        public String getRootPath() {
            return FileUtils.getAbsolutePath(getRootDirectory());
        }

        /**
         * 获取 Android 系统根目录 ( path /system )
         * @return /system
         */
        public File getRootDirectory() {
            return Environment.getRootDirectory();
        }

        // =

        /**
         * 获取 data 目录 ( path /data )
         * @return /data
         */
        public String getDataPath() {
            return FileUtils.getAbsolutePath(getDataDirectory());
        }

        /**
         * 获取 data 目录 ( path /data )
         * @return /system
         */
        public File getDataDirectory() {
            return Environment.getDataDirectory();
        }

        // =

        /**
         * 获取下载缓存目录 ( path data/cache )
         * @return data/cache
         */
        public String getDownloadCachePath() {
            return FileUtils.getAbsolutePath(getDownloadCacheDirectory());
        }

        /**
         * 获取下载缓存目录 ( path data/cache )
         * @return data/cache
         */
        public File getDownloadCacheDirectory() {
            return Environment.getDownloadCacheDirectory();
        }

        // =

        /**
         * 获取应用内部存储数据路径 ( path /data/data/package )
         * @return /data/data/package
         */
        public String getAppDataPath() {
            return FileUtils.getAbsolutePath(getAppDataDir());
        }

        /**
         * 获取应用内部存储数据路径 ( path /data/data/package )
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
         * 获取应用内部存储数据路径 ( path /data/data/package )
         * @param fileName 文件名
         * @return /data/data/package
         */
        public String getAppDataPath(final String fileName) {
            return FileUtils.getAbsolutePath(FileUtils.getFile(getAppDataPath(), fileName));
        }

        /**
         * 获取应用内部存储数据路径 ( path /data/data/package )
         * @param fileName 文件名
         * @return /data/data/package
         */
        public File getAppDataDir(final String fileName) {
            return FileUtils.getFile(getAppDataPath(), fileName);
        }

        // =

        /**
         * 获取应用内部存储缓存路径 ( path /data/data/package/cache )
         * @return /data/data/package/cache
         */
        public String getAppCachePath() {
            return FileUtils.getAbsolutePath(getAppCacheDir());
        }

        /**
         * 获取应用内部存储缓存路径 ( path /data/data/package/cache )
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
         * 获取应用内部存储缓存路径 ( path /data/data/package/cache )
         * @param fileName 文件名
         * @return /data/data/package/cache
         */
        public String getAppCachePath(final String fileName) {
            return FileUtils.getAbsolutePath(FileUtils.getFile(getAppCachePath(), fileName));
        }

        /**
         * 获取应用内部存储缓存路径 ( path /data/data/package/cache )
         * @param fileName 文件名
         * @return /data/data/package/cache
         */
        public File getAppCacheDir(final String fileName) {
            return FileUtils.getFile(getAppCachePath(), fileName);
        }

        // =

        /**
         * 获取应用内部存储代码缓存路径 ( path /data/data/package/code_cache )
         * @return /data/data/package/code_cache
         */
        public String getAppCodeCachePath() {
            return FileUtils.getAbsolutePath(getAppCodeCacheDir());
        }

        /**
         * 获取应用内部存储代码缓存路径 ( path /data/data/package/code_cache )
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
         * 获取应用内部存储数据库路径 ( path /data/data/package/databases )
         * @return /data/data/package/databases
         */
        public String getAppDbsPath() {
            return FileUtils.getAbsolutePath(getAppDbsDir());
        }

        /**
         * 获取应用内部存储数据库路径 ( path /data/data/package/databases )
         * @return /data/data/package/databases
         */
        public File getAppDbsDir() {
            return FileUtils.getFile(getAppDataPath(), "databases");
        }

        // =

        /**
         * 获取应用内部存储数据库路径 ( path /data/data/package/databases/name )
         * @param name 数据库名
         * @return /data/data/package/databases/name
         */
        public String getAppDbPath(final String name) {
            return FileUtils.getAbsolutePath(getAppDbFile(name));
        }

        /**
         * 获取应用内部存储数据库路径 ( path /data/data/package/databases/name )
         * @param name 数据库名
         * @return /data/data/package/databases/name
         */
        public File getAppDbFile(final String name) {
            try {
                return DevUtils.getContext().getDatabasePath(name);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getAppDbFile %s", name);
            }
            return null;
        }

        // =

        /**
         * 获取应用内部存储文件路径 ( path /data/data/package/files )
         * @return /data/data/package/files
         */
        public String getAppFilesPath() {
            return FileUtils.getAbsolutePath(getAppFilesDir());
        }

        /**
         * 获取应用内部存储文件路径 ( path /data/data/package/files )
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

        /**
         * 获取应用内部存储文件路径 ( path /data/data/package/files )
         * @param type 文件类型
         * @return /data/data/package/files
         */
        public String getAppFilesPath(final String type) {
            return FileUtils.getAbsolutePath(getAppFilesDir(type));
        }

        /**
         * 获取应用内部存储文件路径 ( path /data/data/package/files )
         * <pre>
         *     Environment.STANDARD_DIRECTORIES
         * </pre>
         * @param type 文件类型
         * @return /data/data/package/files
         */
        public File getAppFilesDir(final String type) {
            return FileUtils.getFile(getAppFilesPath(), type);
        }

        // =

        /**
         * 获取应用内部存储 SP 路径 ( path /data/data/package/shared_prefs )
         * @return /data/data/package/shared_prefs
         */
        public String getAppSpPath() {
            return FileUtils.getAbsolutePath(getAppSpDir());
        }

        /**
         * 获取应用内部存储 SP 路径 ( path /data/data/package/shared_prefs )
         * @return /data/data/package/shared_prefs
         */
        public File getAppSpDir() {
            return FileUtils.getFile(getAppDataPath(), "shared_prefs");
        }

        /**
         * 获取应用内部存储 SP 路径 ( path /data/data/package/shared_prefs )
         * @param spName SP 文件名
         * @return /data/data/package/shared_prefs
         */
        public String getAppSpPath(final String spName) {
            return FileUtils.getAbsolutePath(getAppSpFile(spName));
        }

        /**
         * 获取应用内部存储 SP 路径 ( path /data/data/package/shared_prefs )
         * @param spName SP 文件名
         * @return /data/data/package/shared_prefs
         */
        public File getAppSpFile(final String spName) {
            return FileUtils.getFile(getAppSpPath(), spName);
        }

        // =

        /**
         * 获取应用内部存储未备份文件路径 ( path /data/data/package/no_backup )
         * @return /data/data/package/no_backup
         */
        public String getAppNoBackupFilesPath() {
            return FileUtils.getAbsolutePath(getAppNoBackupFilesDir());
        }

        /**
         * 获取应用内部存储未备份文件路径 ( path /data/data/package/no_backup )
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

        // =

        /**
         * 获取应用内部存储音乐路径 ( path  /data/data/package/files/Music )
         * @return /data/data/package/files/Music
         */
        public String getAppMusicPath() {
            return getAppFilesPath(Environment.DIRECTORY_MUSIC);
        }

        /**
         * 获取应用内部存储音乐路径 ( path  /data/data/package/files/Music )
         * @return /data/data/package/files/Music
         */
        public File getAppMusicDir() {
            return getAppFilesDir(Environment.DIRECTORY_MUSIC);
        }

        // =

        /**
         * 获取应用内部存储播客路径 ( path  /data/data/package/files/Podcasts )
         * @return /data/data/package/files/Podcasts
         */
        public String getAppPodcastsPath() {
            return getAppFilesPath(Environment.DIRECTORY_PODCASTS);
        }

        /**
         * 获取应用内部存储播客路径 ( path  /data/data/package/files/Podcasts )
         * @return /data/data/package/files/Podcasts
         */
        public File getAppPodcastsDir() {
            return getAppFilesDir(Environment.DIRECTORY_PODCASTS);
        }

        // =

        /**
         * 获取应用内部存储铃声路径 ( path  /data/data/package/files/Ringtones )
         * @return /data/data/package/files/Ringtones
         */
        public String getAppRingtonesPath() {
            return getAppFilesPath(Environment.DIRECTORY_RINGTONES);
        }

        /**
         * 获取应用内部存储铃声路径 ( path  /data/data/package/files/Ringtones )
         * @return /data/data/package/files/Ringtones
         */
        public File getAppRingtonesDir() {
            return getAppFilesDir(Environment.DIRECTORY_RINGTONES);
        }

        // =

        /**
         * 获取应用内部存储闹铃路径 ( path  /data/data/package/files/Alarms )
         * @return /data/data/package/files/Alarms
         */
        public String getAppAlarmsPath() {
            return getAppFilesPath(Environment.DIRECTORY_ALARMS);
        }

        /**
         * 获取应用内部存储闹铃路径 ( path  /data/data/package/files/Alarms )
         * @return /data/data/package/files/Alarms
         */
        public File getAppAlarmsDir() {
            return getAppFilesDir(Environment.DIRECTORY_ALARMS);
        }

        // =

        /**
         * 获取应用内部存储通知路径 ( path  /data/data/package/files/Notifications )
         * @return /data/data/package/files/Notifications
         */
        public String getAppNotificationsPath() {
            return getAppFilesPath(Environment.DIRECTORY_NOTIFICATIONS);
        }

        /**
         * 获取应用内部存储通知路径 ( path  /data/data/package/files/Notifications )
         * @return /data/data/package/files/Notifications
         */
        public File getAppNotificationsDir() {
            return getAppFilesDir(Environment.DIRECTORY_NOTIFICATIONS);
        }

        // =

        /**
         * 获取应用内部存储图片路径 ( path  /data/data/package/files/Pictures )
         * @return /data/data/package/files/Pictures
         */
        public String getAppPicturesPath() {
            return getAppFilesPath(Environment.DIRECTORY_PICTURES);
        }

        /**
         * 获取应用内部存储图片路径 ( path  /data/data/package/files/Pictures )
         * @return /data/data/package/files/Pictures
         */
        public File getAppPicturesDir() {
            return getAppFilesDir(Environment.DIRECTORY_PICTURES);
        }

        // =

        /**
         * 获取应用内部存储影片路径 ( path  /data/data/package/files/Movies )
         * @return /data/data/package/files/Movies
         */
        public String getAppMoviesPath() {
            return getAppFilesPath(Environment.DIRECTORY_MOVIES);
        }

        /**
         * 获取应用内部存储影片路径 ( path  /data/data/package/files/Movies )
         * @return /data/data/package/files/Movies
         */
        public File getAppMoviesDir() {
            return getAppFilesDir(Environment.DIRECTORY_MOVIES);
        }

        // =

        /**
         * 获取应用内部存储下载路径 ( path  /data/data/package/files/Download )
         * @return /data/data/package/files/Download
         */
        public String getAppDownloadPath() {
            return getAppFilesPath(Environment.DIRECTORY_DOWNLOADS);
        }

        /**
         * 获取应用内部存储下载路径 ( path  /data/data/package/files/Download )
         * @return /data/data/package/files/Download
         */
        public File getAppDownloadDir() {
            return getAppFilesDir(Environment.DIRECTORY_DOWNLOADS);
        }

        // =

        /**
         * 获取应用内部存储数码相机图片路径 ( path  /data/data/package/files/DCIM )
         * @return /data/data/package/files/DCIM
         */
        public String getAppDCIMPath() {
            return getAppFilesPath(Environment.DIRECTORY_DCIM);
        }

        /**
         * 获取应用内部存储数码相机图片路径 ( path  /data/data/package/files/DCIM )
         * @return /data/data/package/files/DCIM
         */
        public File getAppDCIMDir() {
            return getAppFilesDir(Environment.DIRECTORY_DCIM);
        }

        // =

        /**
         * 获取应用内部存储文档路径 ( path  /data/data/package/files/Documents )
         * @return /data/data/package/files/Documents
         */
        public String getAppDocumentsPath() {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                return getAppFilesPath("Documents");
            }
            return getAppFilesPath(Environment.DIRECTORY_DOCUMENTS);
        }

        /**
         * 获取应用内部存储文档路径 ( path  /data/data/package/files/Documents )
         * @return /data/data/package/files/Documents
         */
        public File getAppDocumentsDir() {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                return getAppFilesDir("Documents");
            }
            return getAppFilesDir(Environment.DIRECTORY_DOCUMENTS);
        }

        // =

        /**
         * 获取应用内部存储有声读物路径 ( path  /data/data/package/files/Audiobooks )
         * @return /data/data/package/files/Audiobooks
         */
        public String getAppAudiobooksPath() {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                return getAppFilesPath("Audiobooks");
            }
            return getAppFilesPath(Environment.DIRECTORY_AUDIOBOOKS);
        }

        /**
         * 获取应用内部存储有声读物路径 ( path  /data/data/package/files/Audiobooks )
         * @return /data/data/package/files/Audiobooks
         */
        public File getAppAudiobooksDir() {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                return getAppFilesDir("Audiobooks");
            }
            return getAppFilesDir(Environment.DIRECTORY_AUDIOBOOKS);
        }
    }
}