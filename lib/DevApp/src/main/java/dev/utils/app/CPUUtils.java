package dev.utils.app;

import android.text.format.Formatter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.regex.Pattern;

import dev.DevUtils;
import dev.utils.LogPrintUtils;
import dev.utils.common.CloseUtils;

/**
 * detail: 获取 CPU 信息工具类
 * @author Ttt
 */
public final class CPUUtils {

    private CPUUtils() {
    }

    // 日志 TAG
    private static final String TAG = CPUUtils.class.getSimpleName();

    /**
     * 获取处理器的 Java 虚拟机的数量
     * @return 处理器的 Java 虚拟机的数量
     */
    public static int getProcessorsCount() {
        return Runtime.getRuntime().availableProcessors();
    }

    /**
     * 获取手机 CPU 序列号
     * @return CPU 序列号 (16 位 ) 读取失败为 "0000000000000000"
     */
    public static String getSysCPUSerialNum() {
        String str, cpuSerialNum = "0000000000000000";
        try {
            // 读取 CPU 信息
            Process           pp    = Runtime.getRuntime().exec("cat/proc/cpuinfo");
            InputStreamReader isr   = new InputStreamReader(pp.getInputStream());
            LineNumberReader  input = new LineNumberReader(isr);
            // 查找 CPU 序列号
            for (int i = 1; i < 100; i++) {
                str = input.readLine();
                if (str != null) {
                    // 查找到序列号所在行
                    if (str.indexOf("Serial") > -1) {
                        // 提取序列号
                        cpuSerialNum = str.substring(str.indexOf(':') + 1).trim();
                        break;
                    }
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getSysCPUSerialNum");
        }
        return cpuSerialNum;
    }

    /**
     * 获取 CPU 信息
     * @return CPU 信息
     */
    public static String getCpuInfo() {
        try {
            FileReader     fr = new FileReader("/proc/cpuinfo");
            BufferedReader br = new BufferedReader(fr);
            return br.readLine();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getCpuInfo");
        }
        return "";
    }

    /**
     * 获取 CPU 型号
     * @return CPU 型号
     */
    public static String getCpuModel() {
        try {
            FileReader     fr   = new FileReader("/proc/cpuinfo");
            BufferedReader br   = new BufferedReader(fr);
            String         text = br.readLine();
            return text.split(":\\s+", 2)[1];
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getCpuModel");
        }
        return "";
    }

    /**
     * 获取 CPU 最大频率 ( 单位 KHZ)
     * @return CPU 最大频率 ( 单位 KHZ)
     */
    public static String getMaxCpuFreq() {
        ProcessBuilder cmd;
        InputStream    is = null;
        try {
            StringBuilder builder = new StringBuilder();
            String[]      args    = {"/system/bin/cat", "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq"};
            cmd = new ProcessBuilder(args);
            Process process = cmd.start();
            is = process.getInputStream();
            byte[] re = new byte[24];
            while (is.read(re) != -1) {
                builder.append(new String(re));
            }
            return Formatter.formatFileSize(
                    DevUtils.getContext(), Long.parseLong(builder.toString().trim()) * 1024
            ) + " Hz";
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getMaxCpuFreq");
        } finally {
            CloseUtils.closeIOQuietly(is);
        }
        return "unknown";
    }

    /**
     * 获取 CPU 最小频率 ( 单位 KHZ)
     * @return CPU 最小频率 ( 单位 KHZ)
     */
    public static String getMinCpuFreq() {
        ProcessBuilder cmd;
        InputStream    is = null;
        try {
            StringBuilder builder = new StringBuilder();
            String[]      args    = {"/system/bin/cat", "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq"};
            cmd = new ProcessBuilder(args);
            Process process = cmd.start();
            is = process.getInputStream();
            byte[] re = new byte[24];
            while (is.read(re) != -1) {
                builder.append(new String(re));
            }
            return Formatter.formatFileSize(
                    DevUtils.getContext(), Long.parseLong(builder.toString().trim()) * 1024
            ) + " Hz";
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getMinCpuFreq");
        } finally {
            CloseUtils.closeIOQuietly(is);
        }
        return "unknown";
    }

    /**
     * 获取 CPU 当前频率 ( 单位 KHZ)
     * @return CPU 当前频率 ( 单位 KHZ)
     */
    public static String getCurCpuFreq() {
        try {
            FileReader     fr   = new FileReader("/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq");
            BufferedReader br   = new BufferedReader(fr);
            String         text = br.readLine();
            return Formatter.formatFileSize(DevUtils.getContext(), Long.parseLong(text.trim()) * 1024) + " Hz";
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getCurCpuFreq");
        }
        return "unknown";
    }

    /**
     * 获取 CPU 核心数
     * @return CPU 核心数
     */
    public static int getCoresNumbers() {
        // Private Class to display only CPU devices in the directory listing
        class CpuFilter
                implements FileFilter {
            @Override
            public boolean accept(File pathname) {
                // Check if filename is "cpu", followed by a single digit number
                return Pattern.matches("cpu[0-9]+", pathname.getName());
            }
        }
        // CPU 核心数
        int CPU_CORES = 0;
        try {
            // Get directory containing CPU info
            File dir = new File("/sys/devices/system/cpu/");
            // Filter to only list the devices we care about
            File[] files = dir.listFiles(new CpuFilter());
            // Return the number of cores (virtual CPU devices)
            CPU_CORES = files.length;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getCoresNumbers");
        }
        if (CPU_CORES < 1) {
            CPU_CORES = Runtime.getRuntime().availableProcessors();
        }
        if (CPU_CORES < 1) {
            CPU_CORES = 1;
        }
        return CPU_CORES;
    }

    /**
     * 获取 CPU 名字
     * @return CPU 名字
     */
    public static String getCpuName() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("/proc/cpuinfo"), 8192);
            String   line  = br.readLine();
            String[] array = line.split(":\\s+", 2);
            if (array.length > 1) {
                return array[1];
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getCpuName");
        } finally {
            CloseUtils.closeIOQuietly(br);
        }
        return null;
    }

    /**
     * 获取 CMD 指令回调数据
     * @param strings 指令集
     * @return 执行结果
     */
    public static String getCMDOutputString(final String[] strings) {
        InputStream is = null;
        try {
            ProcessBuilder cmd     = new ProcessBuilder(strings);
            Process        process = cmd.start();
            is = process.getInputStream();
            StringBuilder builder = new StringBuilder();
            byte[]        re      = new byte[64];
            int           len;
            while ((len = is.read(re)) != -1) {
                builder.append(new String(re, 0, len));
            }
            CloseUtils.closeIOQuietly(is);
            process.destroy();
            return builder.toString();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getCMDOutputString");
        } finally {
            CloseUtils.closeIOQuietly(is);
        }
        return null;
    }
}