package dev.utils.app;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.core.app.ActivityOptionsCompat;

import dev.utils.LogPrintUtils;
import dev.utils.app.activity_result.DefaultActivityResult;

/**
 * detail: Activity Result 工具类
 * @author Ttt
 * <pre>
 *     可不封装, 只是为了拆分原始 onActivityResult 实现方式
 *     以及新的 ActivityResult API 实现方式
 *     <p></p>
 *     Activity Result API
 *     @see <a href="https://developer.android.google.cn/training/basics/intents/result"/>
 *     <p></p>
 *     关于 registerForActivityResult 创建的 ActivityResultLauncher 可以看
 *     {@link androidx.activity.result.ActivityResultRegistry#register}
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

    // ==========================
    // = ActivityResultLauncher =
    // ==========================

    /**
     * 执行 ActivityResultContract createIntent 并进行跳转
     * @param launcher ActivityResultLauncher
     * @param input    输入参数
     * @param <I>      启动所需输入参数类型
     * @return {@code true} success, {@code false} fail
     */
    public static <I> boolean launch(
            final ActivityResultLauncher<I> launcher,
            final I input
    ) {
        if (launcher != null) {
            try {
                // 虽然内部也是调用 launch(input, options) 防止后期迭代更新适配, 分开调用
                launcher.launch(input);
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "launch");
            }
        }
        return false;
    }

    /**
     * 执行 ActivityResultContract createIntent 并进行跳转
     * @param launcher ActivityResultLauncher
     * @param input    输入参数
     * @param options  Activity 启动选项
     * @param <I>      启动所需输入参数类型
     * @return {@code true} success, {@code false} fail
     */
    public static <I> boolean launch(
            final ActivityResultLauncher<I> launcher,
            final I input,
            final ActivityOptionsCompat options
    ) {
        if (launcher != null) {
            try {
                launcher.launch(input, options);
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "launch - options");
            }
        }
        return false;
    }

    /**
     * 取消启动器注册, 并释放回调监听
     * @param launcher ActivityResultLauncher
     * @param <I>      启动所需输入参数类型
     * @return {@code true} success, {@code false} fail
     */
    public static <I> boolean unregister(final ActivityResultLauncher<I> launcher) {
        if (launcher != null) {
            try {
                launcher.unregister();
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "unregister");
            }
        }
        return false;
    }

    /**
     * 获取创建启动器对应 ActivityResultContract
     * @param launcher ActivityResultLauncher
     * @param <I>      启动所需输入参数类型
     * @return ActivityResultContract
     */
    public static <I> ActivityResultContract<I, ?> getContract(final ActivityResultLauncher<I> launcher) {
        if (launcher != null) {
            return launcher.getContract();
        }
        return null;
    }

    // ========================
    // = ActivityResultCaller =
    // ========================

    /**
     * 注册创建跳转回传值启动器并返回
     * @param caller   ActivityResultCaller
     * @param contract ActivityResultContract
     * @param callback ActivityResultCallback 回传回调
     * @param <I>      启动所需输入参数类型
     * @param <O>      回传结果解析值类型
     * @return ActivityResultLauncher
     */
    public static <I, O> ActivityResultLauncher<I> registerForActivityResult(
            final ActivityResultCaller caller,
            final ActivityResultContract<I, O> contract,
            final ActivityResultCallback<O> callback
    ) {
        if (caller != null && contract != null && callback != null) {
            try {
                return caller.registerForActivityResult(contract, callback);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "registerForActivityResult");
            }
        }
        return null;
    }

    /**
     * 注册创建跳转回传值启动器并返回
     * @param caller   ActivityResultCaller
     * @param contract ActivityResultContract
     * @param registry ActivityResultRegistry
     * @param callback ActivityResultCallback 回传回调
     * @param <I>      启动所需输入参数类型
     * @param <O>      回传结果解析值类型
     * @return ActivityResultLauncher
     */
    public static <I, O> ActivityResultLauncher<I> registerForActivityResult(
            final ActivityResultCaller caller,
            final ActivityResultContract<I, O> contract,
            final ActivityResultRegistry registry,
            final ActivityResultCallback<O> callback
    ) {
        if (caller != null && contract != null && callback != null && registry != null) {
            try {
                return caller.registerForActivityResult(contract, registry, callback);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "registerForActivityResult");
            }
        }
        return null;
    }
}