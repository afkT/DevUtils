package dev.utils.app;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.core.app.ActivityOptionsCompat;
import androidx.lifecycle.LifecycleOwner;

import dev.utils.LogPrintUtils;
import dev.utils.app.activity_result.ActivityResultAssist;

/**
 * detail: Activity Result 工具类
 * @author Ttt
 * <pre>
 *     Activity Result API
 *     @see <a href="https://developer.android.com/training/basics/intents/result"/>
 *     <p></p>
 *     只是为了拆分原始 onActivityResult 实现方式以及新的 ActivityResult API 实现方式
 *     可不封装 ( 内部捕获异常并返回操作是否成功 )
 *     <p></p>
 *     关于 registerForActivityResult 创建的 ActivityResultLauncher 可以看
 *     {@link androidx.activity.result.ActivityResultRegistry#register}
 *     正常在 Activity 中使用顺序是
 *     registerForActivityResult().launch(input)
 *     可考虑使用
 *     {@link ActivityResultAssist}
 * </pre>
 */
public final class ActivityResultUtils {

    private ActivityResultUtils() {
    }

    // 日志 TAG
    private static final String TAG = ActivityResultUtils.class.getSimpleName();

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
            try {
                return launcher.getContract();
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getContract");
            }
        }
        return null;
    }

    // ========================
    // = ActivityResultCaller =
    // ========================

    /**
     * 注册创建跳转回传值启动器并返回
     * @param caller   ActivityResultCaller ( 只要属于继承 Fragment、FragmentActivity 传入 this 即可 )
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
                LogPrintUtils.eTag(
                        TAG, e, "ActivityResultCaller registerForActivityResult"
                );
            }
        }
        return null;
    }

    /**
     * 注册创建跳转回传值启动器并返回
     * @param caller   ActivityResultCaller ( 只要属于继承 Fragment、FragmentActivity 传入 this 即可 )
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
        if (caller != null && contract != null && registry != null && callback != null) {
            try {
                return caller.registerForActivityResult(contract, registry, callback);
            } catch (Exception e) {
                LogPrintUtils.eTag(
                        TAG, e, "ActivityResultCaller registerForActivityResult"
                );
            }
        }
        return null;
    }

    // ==========================
    // = ActivityResultRegistry =
    // ==========================

    /**
     * 注册创建跳转回传值启动器并返回
     * <pre>
     *     ActivityResultRegistry ( 可通过 ComponentActivity、Fragment getActivityResultRegistry() 获取 )
     * </pre>
     * @param registry       ActivityResultRegistry
     * @param key            唯一值字符串
     * @param lifecycleOwner 生命周期监听
     * @param contract       ActivityResultContract
     * @param callback       ActivityResultCallback 回传回调
     * @param <I>            启动所需输入参数类型
     * @param <O>            回传结果解析值类型
     * @return ActivityResultLauncher
     */
    public static <I, O> ActivityResultLauncher<I> register(
            final ActivityResultRegistry registry,
            final String key,
            final LifecycleOwner lifecycleOwner,
            final ActivityResultContract<I, O> contract,
            final ActivityResultCallback<O> callback
    ) {
        if (registry != null && key != null && lifecycleOwner != null
                && contract != null && callback != null) {
            try {
                return registry.register(key, lifecycleOwner, contract, callback);
            } catch (Exception e) {
                LogPrintUtils.eTag(
                        TAG, e, "ActivityResultRegistry register"
                );
            }
        }
        return null;
    }

    /**
     * 注册创建跳转回传值启动器并返回
     * <pre>
     *     ActivityResultRegistry ( 可通过 ComponentActivity、Fragment getActivityResultRegistry() 获取 )
     * </pre>
     * @param registry ActivityResultRegistry
     * @param key      唯一值字符串
     * @param contract ActivityResultContract
     * @param callback ActivityResultCallback 回传回调
     * @param <I>      启动所需输入参数类型
     * @param <O>      回传结果解析值类型
     * @return ActivityResultLauncher
     */
    public static <I, O> ActivityResultLauncher<I> register(
            final ActivityResultRegistry registry,
            final String key,
            final ActivityResultContract<I, O> contract,
            final ActivityResultCallback<O> callback
    ) {
        if (registry != null && key != null && contract != null && callback != null) {
            try {
                return registry.register(key, contract, callback);
            } catch (Exception e) {
                LogPrintUtils.eTag(
                        TAG, e, "ActivityResultRegistry register"
                );
            }
        }
        return null;
    }
}