package dev.utils.app;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import androidx.annotation.RequiresApi;
import android.text.format.Formatter;

import java.io.BufferedReader;
import java.io.FileReader;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: 内存信息工具类
 * @author Ttt
 */
public final class MemoryUtils {

    private MemoryUtils() {
    }

    // 日志 TAG
    private static final String TAG = MemoryUtils.class.getSimpleName();
    // 内存信息文件地址
    private static final String MEM_INFO_PATH = "/proc/meminfo";
    // 获取内存总大小
    private static final String MEMTOTAL = "MemTotal";
    // 获取可用内存
    private static final String MEMAVAILABLE = "MemAvailable";

    /**
     * 获取内存信息
     * <pre>
     *     Print memory info. such as:
     *     MemTotal:        1864292 kB
     *     MemFree:          779064 kB
     *     Buffers:            4540 kB
     *     Cached:           185656 kB
     *     SwapCached:        13160 kB
     *     Active:           435588 kB
     *     Inactive:         269312 kB
     *     Active(anon):     386188 kB
     *     Inactive(anon):   132576 kB
     *     Active(file):      49400 kB
     *     Inactive(file):   136736 kB
     *     Unevictable:        2420 kB
     *     Mlocked:               0 kB
     *     HighTotal:       1437692 kB
     *     HighFree:         520212 kB
     *     LowTotal:         426600 kB
     *     LowFree:          258852 kB
     *     SwapTotal:        511996 kB
     *     SwapFree:         171876 kB
     *     Dirty:               412 kB
     *     Writeback:             0 kB
     *     AnonPages:        511924 kB
     *     Mapped:           152368 kB
     *     Shmem:              1636 kB
     *     Slab:             109224 kB
     *     SReclaimable:      75932 kB
     *     SUnreclaim:        33292 kB
     *     KernelStack:       13056 kB
     *     PageTables:        28032 kB
     *     NFS_Unstable:          0 kB
     *     Bounce:                0 kB
     *     WritebackTmp:          0 kB
     *     CommitLimit:     1444140 kB
     *     Committed_AS:   25977748 kB
     *     VmallocTotal:     458752 kB
     *     VmallocUsed:      123448 kB
     *     VmallocChunk:     205828 kB
     * </pre>
     * @return 内存信息
     */
    public static String printMemoryInfo() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(MEM_INFO_PATH), 4 * 1024);
            StringBuilder builder = new StringBuilder();
            String str;
            while ((str = br.readLine()) != null) {
                builder.append(str);
            }
            br.close();
            return builder.toString();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "printMemoryInfo");
        }
        return null;
    }

    /**
     * 获取内存信息
     * @return 内存信息
     */
    @RequiresApi(Build.VERSION_CODES.CUPCAKE)
    public static String printMemoryInfo2() {
        try {
            ActivityManager.MemoryInfo memoryInfo = getMemoryInfo();
            StringBuilder builder = new StringBuilder();
            builder.append("Memory: ");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                builder.append("\ntotalMem: ").append(memoryInfo.totalMem);
            }
            builder.append("\navailMem: ").append(memoryInfo.availMem);
            builder.append("\nlowMemory: ").append(memoryInfo.lowMemory);
            builder.append("\nthreshold: ").append(memoryInfo.threshold);
            return builder.toString();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "printMemoryInfo2");
        }
        return null;
    }

    /**
     * 获取内存信息
     * @return 内存信息
     */
    @RequiresApi(Build.VERSION_CODES.CUPCAKE)
    public static ActivityManager.MemoryInfo getMemoryInfo() {
        try {
            ActivityManager activityManager = (ActivityManager) DevUtils.getContext().getSystemService(Context.ACTIVITY_SERVICE);
            ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
            activityManager.getMemoryInfo(memoryInfo);
            return memoryInfo;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getMemoryInfo");
        }
        return null;
    }

    // =

    /**
     * 获取可用内存信息
     * @return 可用内存信息
     */
    @RequiresApi(Build.VERSION_CODES.CUPCAKE)
    public static long getAvailMemory() {
        try {
            // 获取 android 当前可用内存大小
            ActivityManager activityManager = (ActivityManager) DevUtils.getContext().getSystemService(Context.ACTIVITY_SERVICE);
            ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
            activityManager.getMemoryInfo(memoryInfo);
            // 当前系统的可用内存
            return memoryInfo.availMem;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAvailMemory");
        }
        return 0l;
    }

    /**
     * 获取可用内存信息 ( 格式化 )
     * @return 可用内存信息
     */
    @RequiresApi(Build.VERSION_CODES.CUPCAKE)
    public static String getAvailMemoryFormat() {
        return Formatter.formatFileSize(DevUtils.getContext(), getAvailMemory());
    }

    // =

    /**
     * 获取总内存大小
     * @return 总内存大小
     */
    public static long getTotalMemory() {
        return getMemInfoIype(MEMTOTAL);
    }

    /**
     * 获取总内存大小 ( 格式化 )
     * @return 总内存大小
     */
    public static String getTotalMemoryFormat() {
        return Formatter.formatFileSize(DevUtils.getContext(), getTotalMemory());
    }

    // =

    /**
     * 获取可用内存大小
     * @return 可用内存大小
     */
    public static long getMemoryAvailable() {
        return getMemInfoIype(MEMAVAILABLE);
    }

    /**
     * 获取可用内存大小 ( 格式化 )
     * @return 可用内存大小
     */
    public static String getMemoryAvailableFormat() {
        return Formatter.formatFileSize(DevUtils.getContext(), getMemoryAvailable());
    }

    /**
     * 通过不同 type 获取对应的内存信息
     * @param type 内存类型
     * @return 对应 type 内存信息
     */
    public static long getMemInfoIype(final String type) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(MEM_INFO_PATH), 4 * 1024);
            String str;
            while ((str = br.readLine()) != null) {
                if (str.contains(type)) {
                    break;
                }
            }
            br.close();
            // 拆分空格、回车、换行等空白符
            String[] array = str.split("\\s+");
            // 获取系统总内存, 单位是 KB, 乘以 1024 转换为 Byte
            return Long.valueOf(array[1]).longValue() * 1024;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getMemInfoIype - " + type);
        }
        return 0l;
    }
}