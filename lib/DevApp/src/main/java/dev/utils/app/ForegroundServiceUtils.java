package dev.utils.app;

import android.app.Notification;
import android.app.Service;
import android.os.Build;

import dev.utils.LogPrintUtils;

/**
 * detail: 前台 Service 工具类
 * @author Ttt
 * <pre>
 *     从 Context 侧启动前台服务请使用 {@link ServiceUtils#startForegroundService(android.content.Intent)}；
 *     本类仅封装 {@link Service#startForeground(int, Notification)} 在 API 29+ 需传入
 *     {@code foregroundServiceType} 与 Manifest {@code android:foregroundServiceType} 对齐的适配，
 *     便于后续平台对前台类型约束变更时集中修改。
 * </pre>
 */
public final class ForegroundServiceUtils {

    private ForegroundServiceUtils() {
    }

    // 日志 TAG
    private static final String TAG = ForegroundServiceUtils.class.getSimpleName();

    /**
     * 将当前 Service 提升为前台
     * <pre>
     *     API 29+ 使用三参数 {@link Service#startForeground(int, Notification, int)} 传入类型位掩码；
     *     API 26–28 使用双参数 {@link Service#startForeground(int, Notification)}；
     *     低于 API 26 无前台 Service 能力，本方法直接返回 false。
     * </pre>
     * @param service      当前 {@link Service}
     * @param id           通知 id，不可为 0
     * @param notification 已构建好的 {@link Notification}（含渠道等）
     * @param fgsType      {@link android.content.pm.ServiceInfo} 中 {@code FOREGROUND_SERVICE_TYPE_*} 的组合，
     *                     须与 Manifest 中 {@code android:foregroundServiceType} 声明一致
     * @return {@code true} 已调用 startForeground；{@code false} 参数非法或系统版本过低
     */
    public static boolean startForeground(
            final Service service,
            final int id,
            final Notification notification,
            final int fgsType
    ) {
        if (service == null || notification == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return false;
        }
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                service.startForeground(id, notification, fgsType);
            } else {
                service.startForeground(id, notification);
            }
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "startForeground");
        }
        return false;
    }
}