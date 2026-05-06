package dev.utils.app;

import android.app.PendingIntent;
import android.os.Build;

/**
 * detail: PendingIntent 工具类
 * @author Ttt
 * <pre>
 *     创建常用 flags 适配（API 31+ 须指定 IMMUTABLE / MUTABLE）
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
}
