package dev.utils.app;

import dev.utils.app.activity_result.DefaultActivityResult;

/**
 * detail: Activity Result 工具类
 * @author Ttt
 * <pre>
 *     可不封装, 只是为了拆分原始 onActivityResult 实现方式
 *     以及新的 ActivityResult API 实现方式
 * </pre>
 */
public final class ActivityResultUtils {

    private ActivityResultUtils() {
    }

    // 日志 TAG
    private static final String TAG = ActivityResultUtils.class.getSimpleName();

    // ===================================
    // = 默认实现 ( 原始 onActivityResult ) =
    // ===================================

    /**
     * 获取默认实现 ( 原始 onActivityResult ) 封装辅助类
     * @return DefaultActivityResult
     */
    public static DefaultActivityResult getDefault() {
        return DefaultActivityResult.getInstance();
    }

    // =========================
    // = 新 Activity Result API =
    // =========================
}