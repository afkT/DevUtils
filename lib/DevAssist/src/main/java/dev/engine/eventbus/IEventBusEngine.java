package dev.engine.eventbus;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

/**
 * detail: EventBus Engine 接口
 * @author Ttt
 */
public interface IEventBusEngine<Config extends IEventBusEngine.EngineConfig> {

    /**
     * detail: EventBus Config
     * @author Ttt
     */
    interface EngineConfig {
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 初始化方法
     * @param config EventBus Config
     * @return {@code true} success, {@code false} fail
     */
    boolean initialize(Config config);

    /**
     * 应用事件配置
     * @param key    key
     * @param config EventBus Config
     * @return {@code true} success, {@code false} fail
     */
    boolean config(
            String key,
            Config config
    );

    // ==========
    // = 发送事件 =
    // ==========

    /**
     * 发送事件
     * @param key   key
     * @param value 事件数据
     * @return {@code true} success, {@code false} fail
     */
    <T> boolean post(
            String key,
            T value
    );

    /**
     * 发送延迟事件
     * @param key   key
     * @param value 事件数据
     * @param delay 延迟毫秒数
     * @return {@code true} success, {@code false} fail
     */
    <T> boolean postDelay(
            String key,
            T value,
            long delay
    );

    /**
     * 发送延迟事件
     * @param key       key
     * @param lifecycle 生命周期持有者
     * @param value     事件数据
     * @param delay     延迟毫秒数
     * @return {@code true} success, {@code false} fail
     */
    <T> boolean postDelay(
            String key,
            LifecycleOwner lifecycle,
            T value,
            long delay
    );

    /**
     * 顺序发送事件
     * @param key   key
     * @param value 事件数据
     * @return {@code true} success, {@code false} fail
     */
    <T> boolean postOrderly(
            String key,
            T value
    );

    /**
     * 跨进程发送事件
     * @param key   key
     * @param value 事件数据
     * @return {@code true} success, {@code false} fail
     */
    <T> boolean postAcrossProcess(
            String key,
            T value
    );

    /**
     * 跨 App 发送事件
     * @param key   key
     * @param value 事件数据
     * @return {@code true} success, {@code false} fail
     */
    <T> boolean postAcrossApp(
            String key,
            T value
    );

    /**
     * 广播形式发送事件
     * @param key   key
     * @param value 事件数据
     * @return {@code true} success, {@code false} fail
     * @deprecated 建议使用 {@link #postAcrossProcess(String, Object)} 或 {@link #postAcrossApp(String, Object)}
     */
    <T> boolean broadcast(
            String key,
            T value
    );

    /**
     * 广播形式发送事件
     * @param key        key
     * @param value      事件数据
     * @param foreground {@code true} 前台广播, {@code false} 后台广播
     * @param onlyInApp  {@code true} 只在 App 内有效, {@code false} 全局有效
     * @return {@code true} success, {@code false} fail
     */
    <T> boolean broadcast(
            String key,
            T value,
            boolean foreground,
            boolean onlyInApp
    );

    // ==========
    // = 观察事件 =
    // ==========

    /**
     * 注册生命周期感知观察者
     * @param key       key
     * @param type      事件类型
     * @param lifecycle 生命周期持有者
     * @param observer  事件观察者
     * @return {@code true} success, {@code false} fail
     */
    <T> boolean observe(
            String key,
            Class<T> type,
            LifecycleOwner lifecycle,
            Observer<T> observer
    );

    /**
     * 注册生命周期感知 Sticky 观察者
     * @param key       key
     * @param type      事件类型
     * @param lifecycle 生命周期持有者
     * @param observer  事件观察者
     * @return {@code true} success, {@code false} fail
     */
    <T> boolean observeSticky(
            String key,
            Class<T> type,
            LifecycleOwner lifecycle,
            Observer<T> observer
    );

    /**
     * 注册永久观察者
     * @param key      key
     * @param type     事件类型
     * @param observer 事件观察者
     * @return {@code true} success, {@code false} fail
     */
    <T> boolean observeForever(
            String key,
            Class<T> type,
            Observer<T> observer
    );

    /**
     * 注册永久 Sticky 观察者
     * @param key      key
     * @param type     事件类型
     * @param observer 事件观察者
     * @return {@code true} success, {@code false} fail
     */
    <T> boolean observeStickyForever(
            String key,
            Class<T> type,
            Observer<T> observer
    );

    /**
     * 移除永久观察者
     * @param key      key
     * @param type     事件类型
     * @param observer 事件观察者
     * @return {@code true} success, {@code false} fail
     */
    <T> boolean removeObserver(
            String key,
            Class<T> type,
            Observer<T> observer
    );
}