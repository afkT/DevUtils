package dev.utils.app;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: 服务相关工具类
 * @author Ttt
 */
public final class ServiceUtils {

    private ServiceUtils() {
    }

    // 日志 TAG
    private static final String TAG = ServiceUtils.class.getSimpleName();

    /**
     * 判断服务是否运行
     * @param className 完整包名的服务类名
     * @return {@code true} 是, {@code false} 否
     */
    public static boolean isServiceRunning(final String className) {
        try {
            ActivityManager activityManager = (ActivityManager) DevUtils.getContext().getSystemService(Context.ACTIVITY_SERVICE);
            if (activityManager == null) return false;
            List<RunningServiceInfo> listInfos = activityManager.getRunningServices(0x7FFFFFFF);
            if (listInfos == null || listInfos.size() == 0) return false;
            for (RunningServiceInfo rInfo : listInfos) {
                if (className.equals(rInfo.service.getClassName())) return true;
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "isServiceRunning");
        }
        return false;
    }

    /**
     * 判断服务是否运行
     * @param clazz
     * @return {@code true} 是, {@code false} 否
     */
    public static boolean isServiceRunning(final Class<?> clazz) {
        try {
            return isServiceRunning(clazz.getName());
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "isServiceRunning");
        }
        return false;
    }

    /**
     * 获取所有运行的服务
     * @return 服务名集合
     */
    public static Set getAllRunningService() {
        try {
            ActivityManager activityManager = (ActivityManager) DevUtils.getContext().getSystemService(Context.ACTIVITY_SERVICE);
            if (activityManager == null) return Collections.emptySet();
            List<RunningServiceInfo> listInfos = activityManager.getRunningServices(0x7FFFFFFF);
            if (listInfos == null || listInfos.size() == 0) return null;
            Set<String> names = new HashSet<>();
            for (RunningServiceInfo rInfo : listInfos) {
                names.add(rInfo.service.getClassName());
            }
            return names;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAllRunningService");
        }
        return Collections.emptySet();
    }

    /**
     * 启动服务
     * @param className 完整包名的服务类名
     */
    public static void startService(final String className) {
        try {
            startService(Class.forName(className));
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "startService");
        }
    }

    /**
     * 启动服务
     * @param clazz 服务类
     */
    public static void startService(final Class<?> clazz) {
        try {
            Intent intent = new Intent(DevUtils.getContext(), clazz);
            DevUtils.getContext().startService(intent);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "startService");
        }
    }

    /**
     * 停止服务
     * @param className 完整包名的服务类名
     * @return {@code true} 停止成功, {@code false} 停止失败
     */
    public static boolean stopService(final String className) {
        try {
            return stopService(Class.forName(className));
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "stopService");
            return false;
        }
    }

    /**
     * 停止服务
     * @param clazz 服务类
     * @return {@code true} 停止成功, {@code false} 停止失败
     */
    public static boolean stopService(final Class<?> clazz) {
        try {
            Intent intent = new Intent(DevUtils.getContext(), clazz);
            return DevUtils.getContext().stopService(intent);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "stopService");
            return false;
        }
    }

    /**
     * 绑定服务
     * @param className 完整包名的服务类名
     * @param conn      服务连接对象
     * @param flags     绑定选项
     *                  {@link Context#BIND_AUTO_CREATE}
     *                  {@link Context#BIND_DEBUG_UNBIND}
     *                  {@link Context#BIND_NOT_FOREGROUND}
     *                  {@link Context#BIND_ABOVE_CLIENT}
     *                  {@link Context#BIND_ALLOW_OOM_MANAGEMENT}
     *                  {@link Context#BIND_WAIVE_PRIORITY}
     */
    public static void bindService(final String className, final ServiceConnection conn, final int flags) {
        try {
            bindService(Class.forName(className), conn, flags);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "bindService");
        }
    }

    /**
     * 绑定服务
     * @param clazz 服务类
     * @param conn  服务连接对象
     * @param flags 绑定选项
     *              {@link Context#BIND_AUTO_CREATE}
     *              {@link Context#BIND_DEBUG_UNBIND}
     *              {@link Context#BIND_NOT_FOREGROUND}
     *              {@link Context#BIND_ABOVE_CLIENT}
     *              {@link Context#BIND_ALLOW_OOM_MANAGEMENT}
     *              {@link Context#BIND_WAIVE_PRIORITY}
     */
    public static void bindService(final Class<?> clazz, final ServiceConnection conn, final int flags) {
        try {
            Intent intent = new Intent(DevUtils.getContext(), clazz);
            DevUtils.getContext().bindService(intent, conn, flags);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "bindService");
        }
    }

    /**
     * 解绑服务
     * @param conn 服务连接对象
     */
    public static void unbindService(final ServiceConnection conn) {
        try {
            DevUtils.getContext().unbindService(conn);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "unbindService");
        }
    }
}
