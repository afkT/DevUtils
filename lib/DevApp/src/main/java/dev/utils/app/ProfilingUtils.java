package dev.utils.app;

import android.content.Context;
import android.os.Build;
import android.os.ProfilingManager;
import android.os.ProfilingResult;
import android.os.ProfilingTrigger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: 触发式性能采集工具类
 * @author Ttt
 * <pre>
 *     Android 17 起通过 {@link ProfilingManager} 注册系统触发器（如
 *     {@link ProfilingTrigger#TRIGGER_TYPE_ANOMALY}、{@link ProfilingTrigger#TRIGGER_TYPE_OOM}），
 *     在异常或内存超限前收集堆栈/堆转储。
 *     @see <a href="https://developer.android.com/topic/performance/tracing/profiling-manager/overview">ProfilingManager</a>
 * </pre>
 */
public final class ProfilingUtils {

    private ProfilingUtils() {
    }

    // 日志 TAG
    private static final String TAG = ProfilingUtils.class.getSimpleName();

    /**
     * 是否支持 Profiling 管理器
     * <pre>
     *     {@link ProfilingManager} 需 API 37+。
     * </pre>
     * @return {@code true} API 37+, {@code false} 低版本
     */
    public static boolean isProfilingManagerSupported() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.CINNAMON_BUN;
    }

    /**
     * 获取 Profiling 管理器
     * <pre>
     *     通过 {@link Context#getSystemService(Class)} 获取
     *     {@link ProfilingManager} 系统服务实例。
     * </pre>
     * @param context {@link Context}
     * @return {@link ProfilingManager} 实例；低版本或不可用时返回 null
     */
    @RequiresApi(api = Build.VERSION_CODES.CINNAMON_BUN)
    @Nullable
    public static ProfilingManager getProfilingManager(final Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.CINNAMON_BUN) {
            return null;
        }
        try {
            Context appContext = DevUtils.getContext(context);
            if (appContext == null) {
                return null;
            }
            return appContext.getSystemService(ProfilingManager.class);
        } catch (Throwable e) {
            LogPrintUtils.eTag(TAG, e, "getProfilingManager");
            return null;
        }
    }

    /**
     * 注册 Profiling 结果回调并添加异常检测触发器
     * <pre>
     *     触发器类型为 {@link ProfilingTrigger#TRIGGER_TYPE_ANOMALY}；
     *     适用于在系统施加内存上限等 enforcement 之前收集诊断数据。
     * </pre>
     * @param context  {@link Context}
     * @param executor 回调线程 {@link Executor}
     * @param callback {@link Consumer}{@code <}{@link ProfilingResult}{@code >} 结果回调
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.CINNAMON_BUN)
    public static boolean registerAnomalyProfiling(
            final Context context,
            @NonNull final Executor executor,
            @NonNull final Consumer<ProfilingResult> callback
    ) {
        return registerProfiling(
                context, executor, callback,
                Collections.singletonList(ProfilingTrigger.TRIGGER_TYPE_ANOMALY)
        );
    }

    /**
     * 注册 Profiling 结果回调并添加指定类型触发器
     * <pre>
     *     {@code triggerTypes} 为 null 或空时仅注册结果回调，不添加触发器。
     * </pre>
     * @param context      {@link Context}
     * @param executor     回调线程 {@link Executor}
     * @param callback     {@link Consumer}{@code <}{@link ProfilingResult}{@code >} 结果回调
     * @param triggerTypes {@link ProfilingTrigger} 类型常量列表
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.CINNAMON_BUN)
    public static boolean registerProfiling(
            final Context context,
            @NonNull final Executor executor,
            @NonNull final Consumer<ProfilingResult> callback,
            @Nullable final List<Integer> triggerTypes
    ) {
        ProfilingManager manager = getProfilingManager(context);
        if (manager == null || executor == null || callback == null) {
            return false;
        }
        try {
            manager.registerForAllProfilingResults(executor, callback);
            if (triggerTypes == null || triggerTypes.isEmpty()) {
                return true;
            }
            List<ProfilingTrigger> triggers = new ArrayList<>(triggerTypes.size());
            for (Integer type : triggerTypes) {
                if (type == null) {
                    continue;
                }
                triggers.add(new ProfilingTrigger.Builder(type).build());
            }
            if (!triggers.isEmpty()) {
                manager.addProfilingTriggers(triggers);
            }
            return true;
        } catch (Throwable e) {
            LogPrintUtils.eTag(TAG, e, "registerProfiling");
            return false;
        }
    }

    /**
     * 注销 Profiling 结果回调
     * @param context  {@link Context}
     * @param callback 与注册时相同的 {@link Consumer} 实例
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.CINNAMON_BUN)
    public static boolean unregisterProfilingCallback(
            final Context context,
            @NonNull final Consumer<ProfilingResult> callback
    ) {
        ProfilingManager manager = getProfilingManager(context);
        if (manager == null || callback == null) {
            return false;
        }
        try {
            manager.unregisterForAllProfilingResults(callback);
            return true;
        } catch (Throwable e) {
            LogPrintUtils.eTag(TAG, e, "unregisterProfilingCallback");
            return false;
        }
    }

    /**
     * 清除已注册的全部 Profiling 触发器
     * @param context {@link Context}
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.CINNAMON_BUN)
    public static boolean clearProfilingTriggers(final Context context) {
        ProfilingManager manager = getProfilingManager(context);
        if (manager == null) {
            return false;
        }
        try {
            manager.clearProfilingTriggers();
            return true;
        } catch (Throwable e) {
            LogPrintUtils.eTag(TAG, e, "clearProfilingTriggers");
            return false;
        }
    }
}