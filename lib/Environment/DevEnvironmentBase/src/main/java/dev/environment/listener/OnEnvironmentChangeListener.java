package dev.environment.listener;

import dev.environment.bean.EnvironmentBean;
import dev.environment.bean.ModuleBean;

/**
 * detail: 模块环境改变触发事件
 * @author Ttt
 */
public interface OnEnvironmentChangeListener {

    /**
     * 模块环境发生变化时触发
     * @param module         环境发生变化的模块
     * @param oldEnvironment 该模块的旧环境
     * @param newEnvironment 该模块的最新环境
     */
    void onEnvironmentChanged(
            ModuleBean module,
            EnvironmentBean oldEnvironment,
            EnvironmentBean newEnvironment
    );
}