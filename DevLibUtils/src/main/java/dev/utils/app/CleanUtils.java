package dev.utils.app;

import android.os.Environment;

import java.io.File;
import java.math.BigDecimal;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: 本应用数据清除管理器
 * @author Ttt
 * <pre>
 *      主要功能有清除内/外缓存，清除数据库，清除sharedPreference，清除files和清除自定义目录
 * </pre>
 */
public final class CleanUtils {

    private CleanUtils() {
    }

    // 日志 TAG
    private static final String TAG = CleanUtils.class.getSimpleName();

    /**
     * 清除内部缓存 - path /data/data/package/cache
     * @return
     */
    public static boolean cleanInternalCache() {
        try {
            return deleteFilesInDir(DevUtils.getContext().getCacheDir());
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "cleanInternalCache");
        }
        return false;
    }

    /**
     * 清除内部文件 - path /data/data/package/files
     * @return
     */
    public static boolean cleanInternalFiles() {
        try {
            return deleteFilesInDir(DevUtils.getContext().getFilesDir());
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "cleanInternalFiles");
        }
        return false;
    }

    /**
     * 清除内部数据库 - path /data/data/package/databases
     * @return
     */
    public static boolean cleanInternalDbs() {
        try {
            return deleteFilesInDir(new File(DevUtils.getContext().getFilesDir().getParent(), "databases"));
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "cleanInternalDbs");
        }
        return false;
    }

    /**
     * 根据名称清除数据库 - path /data/data/package/databases/dbName
     * @param dbName
     * @return
     */
    public static boolean cleanInternalDbByName(final String dbName) {
        try {
            return DevUtils.getContext().deleteDatabase(dbName);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "cleanInternalDbByName");
        }
        return false;
    }

    /**
     * 清除内部 SP - path /data/data/package/shared_prefs
     * @return
     */
    public static boolean cleanInternalSp() {
        try {
            return deleteFilesInDir(new File(DevUtils.getContext().getFilesDir().getParent(), "shared_prefs"));
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "cleanInternalSp");
        }
        return false;
    }

    /**
     * 清除外部缓存 - path /storage/emulated/0/android/data/package/cache
     * @return
     */
    public static boolean cleanExternalCache() {
        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                return deleteFilesInDir(DevUtils.getContext().getExternalCacheDir());
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "cleanExternalCache");
        }
        return false;
    }

    /**
     * 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除
     * @param filePath 文件路径
     */
    public static boolean cleanCustomDir(final String filePath) {
        return deleteFilesInDir(getFileByPath(filePath));
    }

    /**
     * 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除
     * @param file 文件路径
     */
    public static boolean cleanCustomDir(final File file) {
        return deleteFilesInDir(file);
    }

    /**
     * 清除本应用所有的数据
     * @param filePaths 文件路径
     */
    public static void cleanApplicationData(final String... filePaths) {
        cleanInternalCache();
        cleanExternalCache();
        cleanInternalDbs();
        cleanInternalSp();
        cleanInternalFiles();
        try {
            for (String path : filePaths) {
                cleanCustomDir(path);
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "cleanApplicationData");
        }
    }

    /**
     * 获取文件夹大小
     * Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
     * Context.getExternalCacheDir() --> SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
     * @param file
     * @return
     */
    public static long getFolderSize(final File file) {
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
    public static String getCacheSize(final File file) {
        return getFormatSize(getFolderSize(file));
    }

    /**
     * 格式化单位
     * @param size
     * @return
     */
    public static String getFormatSize(final double size) {
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

    // =

    /**
     * 删除目录下所有过滤的文件
     * @param dir 目录
     * @return {@code true} 删除成功, {@code false} 删除失败
     */
    private static boolean deleteFilesInDir(final File dir) {
        if (dir == null) return false;
        // dir doesn't exist then return true
        if (!dir.exists()) return true;
        // dir isn't a directory then return false
        if (!dir.isDirectory()) return false;
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isFile()) {
                    if (!file.delete()) return false;
                } else if (file.isDirectory()) {
                    if (!deleteDir(file)) return false;
                }
            }
        }
        return true;
    }

    /**
     * 删除目录
     * @param dir 目录
     * @return {@code true} 删除成功, {@code false} 删除失败
     */
    private static boolean deleteDir(final File dir) {
        if (dir == null) return false;
        // dir doesn't exist then return true
        if (!dir.exists()) return true;
        // dir isn't a directory then return false
        if (!dir.isDirectory()) return false;
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isFile()) {
                    if (!file.delete()) return false;
                } else if (file.isDirectory()) {
                    if (!deleteDir(file)) return false;
                }
            }
        }
        return dir.delete();
    }

    /**
     * 获取文件
     * @param filePath
     * @return
     */
    private static File getFileByPath(final String filePath) {
        return filePath != null ? new File(filePath) : null;
    }

    /**
     * 判断字符串是否为 null 或全为空白字符
     * @param str 待校验字符串
     * @return {@code true} yes, {@code false} no
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