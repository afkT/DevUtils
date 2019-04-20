package dev.utils.app;

import android.app.ActivityManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

import static android.Manifest.permission.KILL_BACKGROUND_PROCESSES;
import static android.Manifest.permission.PACKAGE_USAGE_STATS;

/**
 * detail: 进程相关工具类
 * @author Ttt
 */
public final class ProcessUtils {

    private ProcessUtils() {
    }

    // 日志 TAG
    private static final String TAG = ProcessUtils.class.getSimpleName();

    /**
     * 销毁自身进程
     */
    public static void kill() {
        kill(android.os.Process.myPid());
    }

    /**
     * 销毁进程
     * @param pid
     */
    public static void kill(final int pid) {
        //从操作系统中结束掉当前程序的进程
        android.os.Process.killProcess(pid);
    }

    /**
     * 判断是否当前进程
     * @return
     */
    public static boolean isCurProcess() {
        try {
            return DevUtils.getContext().getPackageName().equals(getCurProcessName());
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "isCurProcess");
        }
        return false;
    }

    /**
     * 获取当前进程的名字
     * hit: 获取当前进程 DevUtils.getContext().getApplicationInfo().packageName
     * @return 进程号
     */
    public static String getCurProcessName() {
        try {
            int pid = android.os.Process.myPid();
            ActivityManager activityManager = (ActivityManager) DevUtils.getContext().getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> listInfos = activityManager.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo appProcess : listInfos) {
                if (appProcess.pid == pid) {
                    return appProcess.processName;
                }
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getCurProcessName");
        }
        return null;
    }

    /**
     * 获取进程号对应的进程名
     * @param pid 进程号 => android.os.Process.myPid()
     * @return 进程名
     */
    public static String getProcessName(final int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            LogPrintUtils.eTag(TAG, throwable, "getProcessName");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }

    /**
     * 根据包名获取进程id
     * @param packageName
     * @return
     */
    public static int getPid(final String packageName) {
        if (TextUtils.isEmpty(packageName)) return 0;
        try {
            ActivityManager activityManager = (ActivityManager) DevUtils.getContext().getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> listInfos = activityManager.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo appProcess : listInfos) {
                if (appProcess.processName.equals(packageName)) {
                    return appProcess.pid;
                }
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getPid");
        }
        return 0;
    }

    /**
     * 根据 pid 获取进程信息
     * @param pid
     * @return
     */
    public static ActivityManager.RunningAppProcessInfo getRunningAppProcessInfo(final int pid) {
        try {
            ActivityManager activityManager = (ActivityManager) DevUtils.getContext().getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> listInfos = activityManager.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo appProcess : listInfos) {
                if (appProcess.pid == pid) {
                    return appProcess;
                }
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getRunningAppProcessInfo");
        }
        return null;
    }

    /**
     * 根据包名获取进程信息
     * @param packageName
     * @return
     */
    public static ActivityManager.RunningAppProcessInfo getRunningAppProcessInfo(final String packageName) {
        if (TextUtils.isEmpty(packageName)) return null;
        try {
            ActivityManager activityManager = (ActivityManager) DevUtils.getContext().getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> listInfos = activityManager.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo appProcess : listInfos) {
                if (appProcess.processName.equals(packageName)) {
                    return appProcess;
                }
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getRunningAppProcessInfo");
        }
        return null;
    }

    // =

    /**
     * 获取前台线程包名
     * <uses-permission android:name="android.permission.PACKAGE_USAGE_STATS" />
     * @return 前台应用包名
     */
    @RequiresPermission(PACKAGE_USAGE_STATS)
    public static String getForegroundProcessName() {
        if (DevUtils.getContext() == null) return null;
        try {
            ActivityManager activityManager = (ActivityManager) DevUtils.getContext().getSystemService(Context.ACTIVITY_SERVICE);
            if (activityManager == null) return null;
            List<ActivityManager.RunningAppProcessInfo> listInfos = activityManager.getRunningAppProcesses();
            if (listInfos != null && listInfos.size() > 0) {
                for (ActivityManager.RunningAppProcessInfo apInfo : listInfos) {
                    if (apInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                        return apInfo.processName;
                    }
                }
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getForegroundProcessName");
        }
        // SDK 大于 21 时
        if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.LOLLIPOP) {
            PackageManager packageManager = DevUtils.getContext().getPackageManager();
            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            List<ResolveInfo> listResolves = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            // 无权限
            if (listResolves.size() <= 0) {
                return null;
            }
            try {
                UsageStatsManager usageStatsManager = (UsageStatsManager) DevUtils.getContext().getSystemService(Context.USAGE_STATS_SERVICE);
                List<UsageStats> listUsageStats = null;
                if (usageStatsManager != null) {
                    long endTime = System.currentTimeMillis();
                    long beginTime = endTime - 86400000 * 7;
                    listUsageStats = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, beginTime, endTime);
                }
                if (listUsageStats == null || listUsageStats.isEmpty()) return null;
                UsageStats recentStats = null;
                for (UsageStats usageStats : listUsageStats) {
                    if (recentStats == null || usageStats.getLastTimeUsed() > recentStats.getLastTimeUsed()) {
                        recentStats = usageStats;
                    }
                }
                return recentStats == null ? null : recentStats.getPackageName();
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getForegroundProcessName");
            }
        }
        return null;
    }

    /**
     * 获取后台服务进程
     * <uses-permission android:name="android.permission.KILL_BACKGROUND_PROCESSES" />
     * @return 后台服务进程
     */
    @RequiresPermission(KILL_BACKGROUND_PROCESSES)
    public static Set<String> getAllBackgroundProcesses() {
        if (DevUtils.getContext() == null) return Collections.emptySet();
        try {
            ActivityManager activityManager = (ActivityManager) DevUtils.getContext().getSystemService(Context.ACTIVITY_SERVICE);
            if (activityManager == null) return Collections.emptySet();
            List<ActivityManager.RunningAppProcessInfo> listInfos = activityManager.getRunningAppProcesses();
            Set<String> set = new HashSet<>();
            if (listInfos != null) {
                for (ActivityManager.RunningAppProcessInfo apInfo : listInfos) {
                    Collections.addAll(set, apInfo.pkgList);
                }
            }
            return set;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAllBackgroundProcesses");
        }
        return null;
    }

    /**
     * 杀死所有的后台服务进程
     * <uses-permission android:name="android.permission.KILL_BACKGROUND_PROCESSES" />
     * @return 被暂时杀死的服务集合
     */
    @RequiresPermission(KILL_BACKGROUND_PROCESSES)
    public static Set<String> killAllBackgroundProcesses() {
        if (DevUtils.getContext() == null) return null;
        try {
            ActivityManager activityManager = (ActivityManager) DevUtils.getContext().getSystemService(Context.ACTIVITY_SERVICE);
            if (activityManager == null) return Collections.emptySet();
            List<ActivityManager.RunningAppProcessInfo> listInfos = activityManager.getRunningAppProcesses();
            Set<String> set = new HashSet<>();
            for (ActivityManager.RunningAppProcessInfo apInfo : listInfos) {
                for (String packageName : apInfo.pkgList) {
                    activityManager.killBackgroundProcesses(packageName);
                    set.add(packageName);
                }
            }
            listInfos = activityManager.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo aInfo : listInfos) {
                for (String packageName : aInfo.pkgList) {
                    set.remove(packageName);
                }
            }
            return set;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "killAllBackgroundProcesses");
        }
        return null;
    }

    /**
     * 杀死后台服务进程
     * <uses-permission android:name="android.permission.KILL_BACKGROUND_PROCESSES" />
     * @param packageName
     * @return {@code true} 杀死成功, {@code false} 杀死失败
     */
    @RequiresPermission(KILL_BACKGROUND_PROCESSES)
    public static boolean killBackgroundProcesses(@NonNull final String packageName) {
        try {
            ActivityManager activityManager = (ActivityManager) DevUtils.getContext().getSystemService(Context.ACTIVITY_SERVICE);
            if (activityManager == null) return false;
            List<ActivityManager.RunningAppProcessInfo> listInfos = activityManager.getRunningAppProcesses();
            if (listInfos == null || listInfos.size() == 0) return true;
            for (ActivityManager.RunningAppProcessInfo apInfo : listInfos) {
                if (Arrays.asList(apInfo.pkgList).contains(packageName)) {
                    activityManager.killBackgroundProcesses(packageName);
                }
            }
            listInfos = activityManager.getRunningAppProcesses();
            if (listInfos == null || listInfos.size() == 0) return true;
            for (ActivityManager.RunningAppProcessInfo aInfo : listInfos) {
                if (Arrays.asList(aInfo.pkgList).contains(packageName)) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "killBackgroundProcesses");
        }
        return false;
    }
}
