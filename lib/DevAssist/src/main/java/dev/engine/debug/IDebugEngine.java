package dev.engine.debug;

import androidx.fragment.app.FragmentActivity;

/**
 * detail: Debug 编译辅助开发 Engine 接口
 * @author Ttt
 */
public interface IDebugEngine<Config extends IDebugEngine.EngineConfig> {

    /**
     * detail: Debug Config
     * @author Ttt
     */
    interface EngineConfig {
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 初始化方法
     * @param config Debug Config
     */
    void initialize(Config config);

    /**
     * 是否显示 Debug 功能开关
     * @return {@code true} yes, {@code false} no
     */
    boolean isDisplayDebugFunction();

    /**
     * 设置 Debug 功能开关
     * @param display 是否显示 Debug 功能
     */
    void setDebugFunction(boolean display);

    /**
     * 连接 ( 显示 ) Debug 功能关联
     * @param activity 所属 Activity
     */
    void attachDebug(FragmentActivity activity);

    /**
     * 分离 ( 隐藏 ) Debug 功能关联
     * @param activity 所属 Activity
     */
    void detachDebug(FragmentActivity activity);

    /**
     * 更新 Debug Config
     * @param config Debug Config
     */
    void updateConfig(Config config);
}