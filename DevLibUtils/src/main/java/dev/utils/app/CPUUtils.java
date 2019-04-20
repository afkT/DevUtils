package dev.utils.app;

import android.text.format.Formatter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.regex.Pattern;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: 获取CPU信息工具类
 * @author Ttt
 */
public final class CPUUtils {

    private CPUUtils() {
    }

    // 日志 TAG
    private static final String TAG = CPUUtils.class.getSimpleName();

    /**
     * 获取处理器的Java虚拟机的数量
     */
    public static int getProcessorsCount() {
        return Runtime.getRuntime().availableProcessors();
    }

    /**
     * 获取手机CPU序列号
     * @return cpu序列号(16位) 读取失败为"0000000000000000"
     */
    public static String getSysCPUSerialNum() {
        String str = "", strCPU = "", cpuSerialNum = "0000000000000000";
        try {
            //读取CPU信息
            Process pp = Runtime.getRuntime().exec("cat/proc/cpuinfo");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            //查找CPU序列号
            for (int i = 1; i < 100; i++) {
                str = input.readLine();
                if (str != null) {
                    //查找到序列号所在行
                    if (str.indexOf("Serial") > -1) {
                        //提取序列号
                        strCPU = str.substring(str.indexOf(":") + 1);
                        //去空格
                        cpuSerialNum = strCPU.trim();
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
     * 获取CPU 信息
     * @return
     */
    public static String getCpuInfo() {
        try {
            FileReader fr = new FileReader("/proc/cpuinfo");
            BufferedReader br = new BufferedReader(fr);
            return br.readLine();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getCpuInfo");
        }
        return "";
    }

    /**
     * 获取CPU 型号
     * @return
     */
    public static String getCpuModel() {
        try {
            FileReader fr = new FileReader("/proc/cpuinfo");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            String[] array = text.split(":\\s+", 2);
            return array[1];
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getCpuModel");
        }
        return "";
    }

    /**
     * 获取 CPU 最大频率(单位KHZ)
     * @return
     */
    public static String getMaxCpuFreq() {
        String result = "";
        ProcessBuilder cmd;
        InputStream in = null;
        try {
            String[] args = {"/system/bin/cat", "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq"};
            cmd = new ProcessBuilder(args);
            Process process = cmd.start();
            in = process.getInputStream();
            byte[] re = new byte[24];
            while (in.read(re) != -1) {
                result = result + new String(re);
            }
            in.close();
            result = Formatter.formatFileSize(DevUtils.getContext(), Long.parseLong(result.trim()) * 1024) + " Hz";
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getMaxCpuFreq");
            result = "unknown";
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
        return result;
    }

    /**
     * 获取 CPU 最小频率(单位KHZ)
     * @return
     */
    public static String getMinCpuFreq() {
        String result = "";
        ProcessBuilder cmd;
        InputStream in = null;
        try {
            String[] args = {"/system/bin/cat", "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq"};
            cmd = new ProcessBuilder(args);
            Process process = cmd.start();
            in = process.getInputStream();
            byte[] re = new byte[24];
            while (in.read(re) != -1) {
                result = result + new String(re);
            }
            in.close();
            result = Formatter.formatFileSize(DevUtils.getContext(), Long.parseLong(result.trim()) * 1024) + " Hz";
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getMinCpuFreq");
            result = "unknown";
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
        return result;
    }

    /**
     * 实时获取 CPU 当前频率(单位KHZ)
     * @return
     */
    public static String getCurCpuFreq() {
        String result = "";
        try {
            FileReader fr = new FileReader("/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            result = Formatter.formatFileSize(DevUtils.getContext(), Long.parseLong(text.trim()) * 1024) + " Hz";
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getCurCpuFreq");
            result = "unknown";
        }
        return result;
    }

    /**
     * 获取 CPU 几核
     * @return
     */
    public static int getCoresNumbers() {
        // Private Class to display only CPU devices in the directory listing
        class CpuFilter implements FileFilter {
            @Override
            public boolean accept(File pathname) {
                //Check if filename is "cpu", followed by a single digit number
                if (Pattern.matches("cpu[0-9]+", pathname.getName())) {
                    return true;
                }
                return false;
            }
        }
        // CPU 核心数
        int CPU_CORES = 0;
        try {
            //Get directory containing CPU info
            File dir = new File("/sys/devices/system/cpu/");
            //Filter to only list the devices we care about
            File[] files = dir.listFiles(new CpuFilter());
            //Return the number of cores (virtual CPU devices)
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
     * 获取CPU名字
     * @return
     */
    public static String getCpuName() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("/proc/cpuinfo"), 8192);
            String line = bufferedReader.readLine();
            bufferedReader.close();
            String[] array = line.split(":\\s+", 2);
            if (array.length > 1) {
                return array[1];
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getCpuName");
        }
        return null;
    }

    /**
     * 获取 CMD 指令回调数据
     * @param strings
     * @return
     */
    public static String getCMDOutputString(final String[] strings) {
        InputStream in = null;
        try {
            ProcessBuilder cmd = new ProcessBuilder(strings);
            Process process = cmd.start();
            in = process.getInputStream();
            StringBuilder builder = new StringBuilder();
            byte[] re = new byte[64];
            int len;
            while ((len = in.read(re)) != -1) {
                builder.append(new String(re, 0, len));
            }
            in.close();
            process.destroy();
            return builder.toString();
        } catch (IOException e) {
            LogPrintUtils.eTag(TAG, e, "getCMDOutputString");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }
}
