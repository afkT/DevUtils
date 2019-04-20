package dev.utils.app;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.support.annotation.RequiresApi;
import android.text.format.Formatter;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dev.DevUtils;
import dev.utils.LogPrintUtils;
import dev.utils.common.FileUtils;

/**
 * detail: SD卡相关辅助类
 * @author Ttt
 */
public final class SDCardUtils {

    private SDCardUtils() {
    }

    // 日志 TAG
    private static final String TAG = SDCardUtils.class.getSimpleName();

    // =

    /**
     * 判断SDCard是否正常挂载
     * @return
     */
    public static boolean isSDCardEnable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取SD卡路径(File对象)
     * @return
     */
    public static File getSDCardFile() {
        return Environment.getExternalStorageDirectory();
    }

    /**
     * 获取SD卡路径(无添加  -> / -> File.separator)
     * @return
     */
    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    // =

    /**
     * 判断 SD 卡是否可用
     * @return {@code true} 可用, {@code false} 不可用
     */
    public static boolean isSDCardEnablePath() {
        return !getSDCardPaths().isEmpty();
    }

    /**
     * 获取 SD 卡路径
     * @param removable true : 外置 SD 卡, {@code false} 内置 SD 卡
     * @return SD 卡路径
     */
    @SuppressWarnings("TryWithIdenticalCatches")
    public static List<String> getSDCardPaths(final boolean removable) {
        List<String> listPaths = new ArrayList<>();
        try {
            StorageManager storageManager = (StorageManager) DevUtils.getContext().getSystemService(Context.STORAGE_SERVICE);
            Class<?> storageVolumeClazz = Class.forName("android.os.storage.StorageVolume");
            Method getVolumeList = StorageManager.class.getMethod("getVolumeList");
            Method getPath = storageVolumeClazz.getMethod("getPath");
            Method isRemovable = storageVolumeClazz.getMethod("isRemovable");
            Object result = getVolumeList.invoke(storageManager);
            final int length = Array.getLength(result);
            for (int i = 0; i < length; i++) {
                Object storageVolumeElement = Array.get(result, i);
                String path = (String) getPath.invoke(storageVolumeElement);
                boolean res = (Boolean) isRemovable.invoke(storageVolumeElement);
                if (removable == res) {
                    listPaths.add(path);
                }
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getSDCardPaths");
        }
        return listPaths;
    }

    /**
     * 获取 SD 卡路径
     * @return SD 卡路径
     */
    @SuppressWarnings("TryWithIdenticalCatches")
    public static List<String> getSDCardPaths() {
        List<String> listPaths = new ArrayList<>();
        try {
            StorageManager storageManager = (StorageManager) DevUtils.getContext().getSystemService(Context.STORAGE_SERVICE);
            Method getVolumePathsMethod = StorageManager.class.getMethod("getVolumePaths");
            getVolumePathsMethod.setAccessible(true);
            Object invoke = getVolumePathsMethod.invoke(storageManager);
            listPaths = Arrays.asList((String[]) invoke);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getSDCardPaths");
        }
        return listPaths;
    }

    // =

    /**
     * 返回对应路径的空间总大小
     * @param path
     * @return
     */
    public static long getAllBlockSize(final String path) {
        try {
            // 获取路径的存储空间信息
            StatFs statFs = new StatFs(path);
            // 获取单个数据块的大小(Byte)
            long blockSize = statFs.getBlockSize();
            // 获取数据块的数量
            long blockCount = statFs.getBlockCount();
            // 返回空间总大小
            return (blockCount * blockSize);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAllBlockSize");
        }
        return -1l;
    }

    /**
     * 返回对应路径的空闲空间(byte 字节大小)
     * @param path
     * @return
     */
    public static long getAvailableBlocks(final String path) {
        try {
            // 获取路径的存储空间信息
            StatFs statFs = new StatFs(path);
            // 获取单个数据块的大小(Byte)
            long blockSize = statFs.getBlockSize();
            // 空闲的数据块的数量
            long freeBlocks = statFs.getAvailableBlocks();
            // 返回空闲空间
            return (freeBlocks * blockSize);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAvailableBlocks");
        }
        return -1l;
    }


    /**
     * 返回对应路径,已使用的空间大小
     * @param path
     * @return
     */
    public static long getAlreadyBlock(final String path) {
        try {
            // 获取路径的存储空间信息
            StatFs statFs = new StatFs(path);
            // 获取单个数据块的大小(Byte)
            long blockSize = statFs.getBlockSize();
            // 获取数据块的数量
            long blockCount = statFs.getBlockCount();
            // 空闲的数据块的数量
            long freeBlocks = statFs.getAvailableBlocks();
            // 返回空间总大小
            return ((blockCount - freeBlocks) * blockSize);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAlreadyBlock");
        }
        return -1l;
    }

    /**
     * 返回对应路径的空间大小信息
     * @return 返回数据，0 = 总空间大小，1 = 空闲控件大小 ， 2 = 已使用空间大小
     */
    public static long[] getBlockSizeInfos(final String path) {
        try {
            // 获取路径的存储空间信息
            StatFs statFs = new StatFs(path);
            // 获取单个数据块的大小(Byte)
            long blockSize = statFs.getBlockSize();
            // 获取数据块的数量
            long blockCount = statFs.getBlockCount();
            // 空闲的数据块的数量
            long freeBlocks = statFs.getAvailableBlocks();
            // 计算空间大小信息
            long[] blocks = new long[3];
            blocks[0] = blockSize * blockCount;
            blocks[1] = blockSize * freeBlocks;
            blocks[2] = ((blockCount - freeBlocks) * blockSize);
            // 返回空间大小信息
            return blocks;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getBlockSizeInfos");
        }
        return null;
    }

    /**
     * 获取 SD 卡总大小
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static String getSDTotalSize() {
        try {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSizeLong();
            long totalBlocks = stat.getBlockCountLong();
            return Formatter.formatFileSize(DevUtils.getContext(), blockSize * totalBlocks);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getSDTotalSize");
        }
        return "unknown";
    }

    /**
     * 获取 SD 卡剩余容量，即可用大小
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static String getSDAvailableSize() {
        try {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSizeLong();
            long availableBlocks = stat.getAvailableBlocksLong();
            return Formatter.formatFileSize(DevUtils.getContext(), blockSize * availableBlocks);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getSDAvailableSize");
        }
        return "unknown";
    }

    /**
     * 获取机身内存总大小
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static String getRomTotalSize() {
        try {
            File path = Environment.getDataDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSizeLong();
            long totalBlocks = stat.getBlockCountLong();
            return Formatter.formatFileSize(DevUtils.getContext(), blockSize * totalBlocks);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getRomTotalSize");
        }
        return "unknown";
    }

    /**
     * 获取机身可用内存
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static String getRomAvailableSize() {
        try {
            File path = Environment.getDataDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSizeLong();
            long availableBlocks = stat.getAvailableBlocksLong();
            return Formatter.formatFileSize(DevUtils.getContext(), blockSize * availableBlocks);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getRomAvailableSize");
        }
        return "unknown";
    }

    // =

    /**
     * 获取缓存地址
     * @return
     */
    public static String getDiskCacheDir() {
        String cachePath;
        if (isSDCardEnable()) { // 判断SDCard是否挂载
            cachePath = DevUtils.getContext().getExternalCacheDir().getPath();
        } else {
            cachePath = DevUtils.getContext().getCacheDir().getPath();
        }
        // 防止不存在目录文件，自动创建
        FileUtils.createFolder(cachePath);
        // 返回文件存储地址
        return cachePath;
    }

    /**
     * 获取缓存资源地址
     * @param filePath 文件路径
     * @return
     */
    public static File getCacheFile(final String filePath) {
        return FileUtils.getFile(getCachePath(filePath));
    }

    /**
     * 获取缓存资源地址
     * @param filePath 文件路径
     * @return
     */
    public static String getCachePath(final String filePath) {
        if (filePath == null) return null;
        // 获取缓存地址
        String cachePath = new File(getDiskCacheDir(), filePath).getAbsolutePath();
        // 防止不存在目录文件，自动创建
        FileUtils.createFolder(cachePath);
        // 返回缓存地址
        return cachePath;
    }
}

