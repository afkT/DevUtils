package dev.utils.app;

import android.os.Environment;

import java.io.File;
import java.math.BigDecimal;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail:
 *      本应用数据清除管理器
 *      主要功能有清除内/外缓存，清除数据库，清除sharedPreference，清除files和清除自定义目录
 * Created by Ttt
 */
public final class CleanUtils {

    private CleanUtils(){
    }

    // 日志TAG
    private static final String TAG = CleanUtils.class.getSimpleName();


    /**
     * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache)
     */
    public static void cleanInternalCache() {
        try {
            deleteFilesByDirectory(DevUtils.getContext().getCacheDir());
        } catch (Exception e){
            LogPrintUtils.eTag(TAG, e, "cleanInternalCache");
        }
    }

    /**
     * 清除本应用所有数据库(/data/data/com.xxx.xxx/databases)
     */
    public static void cleanDatabases() {
        try {
            deleteFilesByDirectory(new File(DevUtils.getContext().getFilesDir().getPath() + DevUtils.getContext().getPackageName() + "/databases"));
        } catch (Exception e){
            LogPrintUtils.eTag(TAG, e, "cleanDatabases");
        }
    }

    /**
     * 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs)
     */
    public static void cleanSharedPreference() {
        try {
            deleteFilesByDirectory(new File(DevUtils.getContext().getFilesDir().getPath() + DevUtils.getContext().getPackageName() + "/shared_prefs"));
        } catch (Exception e){
            LogPrintUtils.eTag(TAG, e, "cleanSharedPreference");
        }
    }

    /**
     * 按名字清除本应用数据库
     * @param dbName 数据库名称
     */
    public static void cleanDatabaseByName(String dbName) {
        try {
            DevUtils.getContext().deleteDatabase(dbName);
        } catch (Exception e){
            LogPrintUtils.eTag(TAG, e, "cleanDatabaseByName");
        }
    }

    /**
     * 清除/data/data/com.xxx.xxx/files下的内容
     */
    public static void cleanFiles() {
        try {
            deleteFilesByDirectory(DevUtils.getContext().getFilesDir());
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "cleanFiles");
        }
    }

    /**
     * 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache)
     */
    public static void cleanExternalCache() {
        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                deleteFilesByDirectory(DevUtils.getContext().getExternalCacheDir());
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "cleanExternalCache");
        }
    }

    /**
     * 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除
     * @param filePath 文件路径
     */
    public static void cleanCustomCache(String filePath) {
        deleteFilesByDirectory(getFileByPath(filePath));
    }

    /**
     * 清除本应用所有的数据
     * @param filePaths 文件路径
     */
    public static void cleanApplicationData(String... filePaths) {
        cleanInternalCache();
        cleanExternalCache();
        cleanDatabases();
        cleanSharedPreference();
        cleanFiles();
        try {
            for (String fPath : filePaths) {
                cleanCustomCache(fPath);
            }
        } catch (Exception e){
            LogPrintUtils.eTag(TAG, e, "cleanApplicationData");
        }
    }

    /**
     * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理
     * @param directory 文件夹File对象
     */
    private static void deleteFilesByDirectory(File directory) {
        // 存在并且属于文件夹
        if (directory != null && directory.exists() && directory.isDirectory()) {
            try {
                // 获取文件列表
                File[] files = directory.listFiles();
                // 遍历删除文件
                for (File item : files) {
                    // 删除文件
                    item.delete();
                }
            } catch (Exception e){
                LogPrintUtils.eTag(TAG, e, "deleteFilesByDirectory");
            }
        }
    }

    /**
     * 获取文件夹大小
     * Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
     * Context.getExternalCacheDir() --> SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
     * @param file
     * @return
     */
    public static long getFolderSize(File file) {
        long size = 0;
        try {
            File[] files = file.listFiles();
            for (int i = 0, len = files.length; i < len; i++) {
                // 如果下面还有文件
                if (files[i].isDirectory()) {
                    size = size + getFolderSize(files[i]);
                } else {
                    size = size + files[i].length();
                }
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getFolderSize");
        }
        return size;
    }

    /**
     * 获取缓存文件大小
     * @param file
     * @return
     */
    public static String getCacheSize(File file){
        return getFormatSize(getFolderSize(file));
    }

    /**
     * 格式化单位
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }

    // ==

    /**
     * 获取文件
     * @param filePath
     * @return
     */
    private static File getFileByPath(final String filePath){
        return filePath != null ? new File(filePath) : null;
    }

    /**
     * 判断字符串是否为 null 或全为空白字符
     * @param str 待校验字符串
     * @return
     */
    private static boolean isSpace(final String str) {
        if (str == null) return true;
        for (int i = 0, len = str.length(); i < len; ++i) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}