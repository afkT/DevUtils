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
     * 默认用于闹钟、通知点击等「模板不变」的场景：{@link PendingIntent#FLAG_UPDATE_CURRENT}，
     * Android 12（API 31）起附加 {@link PendingIntent#FLAG_IMMUTABLE}。
     */
    public static int flagsDefaultImmutable() {
        int flags = PendingIntent.FLAG_UPDATE_CURRENT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            flags |= PendingIntent.FLAG_IMMUTABLE;
        }
        return flags;
    }

    /**
     * 用于短信发送结果等框架可能回填 extras 的场景：{@link PendingIntent#FLAG_UPDATE_CURRENT}，
     * Android 12（API 31）起附加 {@link PendingIntent#FLAG_MUTABLE}。
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
     * {@link PendingIntent#getActivity(Context, int, Intent, int)}，flags 使用 {@link #flagsDefaultImmutable()}。
     */
    public static PendingIntent getActivity(
            final Context context,
            final int requestCode,
            final Intent intent
    ) {
        return PendingIntent.getActivity(context, requestCode, intent, flagsDefaultImmutable());
    }

    /**
     * 同 {@link #getActivity(Context, int, Intent)}，flags 使用 {@link #flagsDefaultMutable()}。
     */
    public static PendingIntent getActivityMutable(
            final Context context,
            final int requestCode,
            final Intent intent
    ) {
        return PendingIntent.getActivity(context, requestCode, intent, flagsDefaultMutable());
    }

    /**
     * 自定义 flags（须自行满足 API 31+ 对 IMMUTABLE/MUTABLE 的要求）。
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
     * {@link PendingIntent#getBroadcast(Context, int, Intent, int)}，flags 使用 {@link #flagsDefaultImmutable()}。
     */
    public static PendingIntent getBroadcast(
            final Context context,
            final int requestCode,
            final Intent intent
    ) {
        return PendingIntent.getBroadcast(context, requestCode, intent, flagsDefaultImmutable());
    }

    /**
     * 同 {@link #getBroadcast(Context, int, Intent)}，flags 使用 {@link #flagsDefaultMutable()}。
     */
    public static PendingIntent getBroadcastMutable(
            final Context context,
            final int requestCode,
            final Intent intent
    ) {
        return PendingIntent.getBroadcast(context, requestCode, intent, flagsDefaultMutable());
    }

    /**
     * 自定义 flags。
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
     * {@link PendingIntent#getService(Context, int, Intent, int)}，flags 使用 {@link #flagsDefaultImmutable()}。
     */
    public static PendingIntent getService(
            final Context context,
            final int requestCode,
            final Intent intent
    ) {
        return PendingIntent.getService(context, requestCode, intent, flagsDefaultImmutable());
    }

    /**
     * {@link PendingIntent#getService(Context, int, Intent, int)}，flags 使用 {@link #flagsDefaultMutable()}。
     */
    public static PendingIntent getServiceMutable(
            final Context context,
            final int requestCode,
            final Intent intent
    ) {
        return PendingIntent.getService(context, requestCode, intent, flagsDefaultMutable());
    }

    /**
     * 自定义 flags。
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
     * {@link PendingIntent#getForegroundService(Context, int, Intent, int)}，flags 使用 {@link #flagsDefaultImmutable()}。
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
     * {@link PendingIntent#getForegroundService(Context, int, Intent, int)}，flags 使用 {@link #flagsDefaultMutable()}。
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
     * 自定义 flags。
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