package dev.utils.app;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;

/**
 * detail: PendingIntent 工具类
 * @author Ttt
 * <pre>
 *     统一创建入口（flags 与 API 31+ IMMUTABLE/MUTABLE 适配集中在此类）
 *     业务侧请通过本类 {@link #getActivity}、{@link #getBroadcast} 等方法获取 PendingIntent，
 *     避免各处直接调用 {@link PendingIntent#getActivity} 等导致 flags 遗漏。
 * </pre>
 */
public final class PendingIntentUtils {

    private PendingIntentUtils() {
    }

    // 日志 TAG
    private static final String TAG = PendingIntentUtils.class.getSimpleName();

    /**
     * 默认用于闹钟、通知点击等「模板不变」场景的 PendingIntent flags
     * <pre>
     *     包含 FLAG_UPDATE_CURRENT，API 31+ 附加 FLAG_IMMUTABLE
     * </pre>
     * @return 组合后的 PendingIntent flags
     */
    public static int flagsDefaultImmutable() {
        int flags = PendingIntent.FLAG_UPDATE_CURRENT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            flags |= PendingIntent.FLAG_IMMUTABLE;
        }
        return flags;
    }

    /**
     * 用于短信发送结果等框架可能回填 extras 场景的 PendingIntent flags
     * <pre>
     *     包含 FLAG_UPDATE_CURRENT，API 31+ 附加 FLAG_MUTABLE
     * </pre>
     * @return 组合后的 PendingIntent flags
     */
    public static int flagsDefaultMutable() {
        int flags = PendingIntent.FLAG_UPDATE_CURRENT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            flags |= PendingIntent.FLAG_MUTABLE;
        }
        return flags;
    }

    // ==============
    // = getActivity =
    // ==============

    /**
     * 获取指向 Activity 的 PendingIntent
     * <pre>
     *     等价 PendingIntent.getActivity(Context, int, Intent, int)，flags 使用 flagsDefaultImmutable()
     * </pre>
     * @param context     {@link Context}
     * @param requestCode 请求码
     * @param intent      {@link Intent}
     * @return {@link PendingIntent}
     */
    public static PendingIntent getActivity(
            final Context context,
            final int requestCode,
            final Intent intent
    ) {
        return PendingIntent.getActivity(context, requestCode, intent, flagsDefaultImmutable());
    }

    /**
     * 获取指向 Activity 的 PendingIntent（默认可变 flags）
     * <pre>
     *     同三参数 getActivity，flags 使用 flagsDefaultMutable()
     * </pre>
     * @param context     {@link Context}
     * @param requestCode 请求码
     * @param intent      {@link Intent}
     * @return {@link PendingIntent}
     */
    public static PendingIntent getActivityMutable(
            final Context context,
            final int requestCode,
            final Intent intent
    ) {
        return PendingIntent.getActivity(context, requestCode, intent, flagsDefaultMutable());
    }

    /**
     * 获取指向 Activity 的 PendingIntent（自定义 flags）
     * <pre>
     *     须自行满足 API 31+ 对 IMMUTABLE / MUTABLE 的要求
     * </pre>
     * @param context     {@link Context}
     * @param requestCode 请求码
     * @param intent      {@link Intent}
     * @param flags       PendingIntent 创建用 flags
     * @return {@link PendingIntent}
     */
    public static PendingIntent getActivity(
            final Context context,
            final int requestCode,
            final Intent intent,
            final int flags
    ) {
        return PendingIntent.getActivity(context, requestCode, intent, flags);
    }

    // ===============
    // = getBroadcast =
    // ===============

    /**
     * 获取 BroadcastReceiver 用的 PendingIntent
     * <pre>
     *     等价 PendingIntent.getBroadcast(Context, int, Intent, int)，flags 使用 flagsDefaultImmutable()
     * </pre>
     * @param context     {@link Context}
     * @param requestCode 请求码
     * @param intent      {@link Intent}
     * @return {@link PendingIntent}
     */
    public static PendingIntent getBroadcast(
            final Context context,
            final int requestCode,
            final Intent intent
    ) {
        return PendingIntent.getBroadcast(context, requestCode, intent, flagsDefaultImmutable());
    }

    /**
     * 获取 BroadcastReceiver 用的 PendingIntent（默认可变 flags）
     * <pre>
     *     同三参数 getBroadcast，flags 使用 flagsDefaultMutable()
     * </pre>
     * @param context     {@link Context}
     * @param requestCode 请求码
     * @param intent      {@link Intent}
     * @return {@link PendingIntent}
     */
    public static PendingIntent getBroadcastMutable(
            final Context context,
            final int requestCode,
            final Intent intent
    ) {
        return PendingIntent.getBroadcast(context, requestCode, intent, flagsDefaultMutable());
    }

    /**
     * 获取 BroadcastReceiver 用的 PendingIntent（自定义 flags）
     * @param context     {@link Context}
     * @param requestCode 请求码
     * @param intent      {@link Intent}
     * @param flags       PendingIntent 创建用 flags
     * @return {@link PendingIntent}
     */
    public static PendingIntent getBroadcast(
            final Context context,
            final int requestCode,
            final Intent intent,
            final int flags
    ) {
        return PendingIntent.getBroadcast(context, requestCode, intent, flags);
    }

    // =============
    // = getService =
    // =============

    /**
     * 获取指向 Service 的 PendingIntent
     * <pre>
     *     等价 PendingIntent.getService(Context, int, Intent, int)，flags 使用 flagsDefaultImmutable()
     * </pre>
     * @param context     {@link Context}
     * @param requestCode 请求码
     * @param intent      {@link Intent}
     * @return {@link PendingIntent}
     */
    public static PendingIntent getService(
            final Context context,
            final int requestCode,
            final Intent intent
    ) {
        return PendingIntent.getService(context, requestCode, intent, flagsDefaultImmutable());
    }

    /**
     * 获取指向 Service 的 PendingIntent（默认可变 flags）
     * <pre>
     *     等价 PendingIntent.getService(Context, int, Intent, int)，flags 使用 flagsDefaultMutable()
     * </pre>
     * @param context     {@link Context}
     * @param requestCode 请求码
     * @param intent      {@link Intent}
     * @return {@link PendingIntent}
     */
    public static PendingIntent getServiceMutable(
            final Context context,
            final int requestCode,
            final Intent intent
    ) {
        return PendingIntent.getService(context, requestCode, intent, flagsDefaultMutable());
    }

    /**
     * 获取指向 Service 的 PendingIntent（自定义 flags）
     * @param context     {@link Context}
     * @param requestCode 请求码
     * @param intent      {@link Intent}
     * @param flags       PendingIntent 创建用 flags
     * @return {@link PendingIntent}
     */
    public static PendingIntent getService(
            final Context context,
            final int requestCode,
            final Intent intent,
            final int flags
    ) {
        return PendingIntent.getService(context, requestCode, intent, flags);
    }

    // =======================
    // = getForegroundService =
    // =======================

    /**
     * 获取指向前台 Service 的 PendingIntent
     * <pre>
     *     等价 PendingIntent.getForegroundService(Context, int, Intent, int)，flags 使用 flagsDefaultImmutable()
     * </pre>
     * @param context     {@link Context}
     * @param requestCode 请求码
     * @param intent      {@link Intent}
     * @return {@link PendingIntent}
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static PendingIntent getForegroundService(
            final Context context,
            final int requestCode,
            final Intent intent
    ) {
        return PendingIntent.getForegroundService(context, requestCode, intent, flagsDefaultImmutable());
    }

    /**
     * 获取指向前台 Service 的 PendingIntent（默认可变 flags）
     * <pre>
     *     等价 PendingIntent.getForegroundService(Context, int, Intent, int)，flags 使用 flagsDefaultMutable()
     * </pre>
     * @param context     {@link Context}
     * @param requestCode 请求码
     * @param intent      {@link Intent}
     * @return {@link PendingIntent}
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static PendingIntent getForegroundServiceMutable(
            final Context context,
            final int requestCode,
            final Intent intent
    ) {
        return PendingIntent.getForegroundService(context, requestCode, intent, flagsDefaultMutable());
    }

    /**
     * 获取指向前台 Service 的 PendingIntent（自定义 flags）
     * @param context     {@link Context}
     * @param requestCode 请求码
     * @param intent      {@link Intent}
     * @param flags       PendingIntent 创建用 flags
     * @return {@link PendingIntent}
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static PendingIntent getForegroundService(
            final Context context,
            final int requestCode,
            final Intent intent,
            final int flags
    ) {
        return PendingIntent.getForegroundService(context, requestCode, intent, flags);
    }
}