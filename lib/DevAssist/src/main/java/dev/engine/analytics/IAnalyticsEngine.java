package dev.engine.analytics;

import android.app.Application;
import android.content.Context;

/**
 * detail: Analytics Engine 接口
 * @author Ttt
 */
public interface IAnalyticsEngine<Config extends IAnalyticsEngine.EngineConfig, Item extends IAnalyticsEngine.EngineItem> {

    /**
     * detail: Analytics Config
     * @author Ttt
     */
    class EngineConfig {
    }

    /**
     * detail: Analytics Item
     * @author Ttt
     */
    class EngineItem {
    }

    // ===============
    // = 对外公开方法 =
    // ===============

    /**
     * 初始化方法
     * @param application {@link Application}
     * @param config      {@link EngineConfig}
     */
    void initialize(
            Application application,
            Config config
    );

    /**
     * 绑定
     * @param context {@link Context}
     * @param config  {@link EngineConfig}
     */
    void register(
            Context context,
            Config config
    );

    /**
     * 解绑
     * @param context {@link Context}
     * @param config  {@link EngineConfig}
     */
    void unregister(
            Context context,
            Config config
    );

    // =

    /**
     * 数据统计 ( 埋点 ) 方法
     * @param params {@link EngineItem}
     * @return {@code true} success, {@code false} fail
     */
    boolean track(Item params);
}