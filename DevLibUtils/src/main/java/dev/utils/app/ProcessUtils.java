package dev.utils.app;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.provider.Settings;
import android.support.annotation.NonNull;
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

/**
 * detail: 进程相关工具类
 * Created by Ttt
 */
public final class ProcessUtils {

    private ProcessUtils() {
    }

    // 日志TAG
    private static final String TAG = ProcessUtils.class.getSimpleName();

    /**
     * 获取进程号对应的进程名
     * @param pid 进程号 => android.os.Process.myPid()
     * @return 进程名
     */
    public static String getProcessName(int pid) {
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
     * 获取当前进程的名字
     * hit: 获取当前进程 DevUtils.getContext().getApplicationInfo().packageName
     * @return 进程号
     */
    public static String getCurProcessName() {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) DevUtils.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

    /**
     * 获取前台线程包名
     * <uses-permission android:name="android.permission.PACKAGE_USAGE_STATS" /> => 属于系统权限
     * @return 前台应用包名
     */
    public static String getForegroundProcessName() {
        if (DevUtils.getContext() == null){
            return null;
        }
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
    public static Set<String> getAllBackgroundProcesses() {
        if (DevUtils.getContext() == null){
            return Collections.emptySet();
        }
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
    }

    /**
     * 杀死所有的后台服务进程
     * <uses-permission android:name="android.permission.KILL_BACKGROUND_PROCESSES" />
     * @return 被暂时杀死的服务集合
     */
    @SuppressLint("MissingPermission")
    public static Set<String> killAllBackgroundProcesses() {
        if (DevUtils.getContext() == null){
            return null;
        }
        ActivityManager activityManager = (ActivityManager) DevUtils.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager == null) return Collections.emptySet();
        List<ActivityManager.RunningAppProcessInfo> listInfos = activityManager.getRunningAppProcesses();
        Set<String> set = new HashSet<>();
        for (ActivityManager.RunningAppProcessInfo apInfo : listInfos) {
            for (String pkg : apInfo.pkgList) {
                activityManager.killBackgroundProcesses(pkg);
                set.add(pkg);
            }
        }
        listInfos = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo aInfo : listInfos) {
            for (String pkg : aInfo.pkgList) {
                set.remove(pkg);
            }
        }
        return set;
    }

    /**
     * 杀死后台服务进程
     * <uses-permission android:name="android.permission.KILL_BACKGROUND_PROCESSES" />
     * @param packageName
     * @return true : 杀死成功, false : 杀死失败
     */
    @SuppressLint("MissingPermission")
    public static boolean killBackgroundProcesses(@NonNull final String packageName) {
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
    }
}
