package com.dev;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import dev.DevUtils;
import dev.utils.app.logger.DevLogger;
import dev.utils.app.logger.LogConfig;
import dev.utils.app.logger.LogLevel;

/**
 * detail: 全局Application
 * @author Ttt
 */
public class BaseApplication extends Application {

    // 日志 TAG
    private final String LOG_TAG = BaseApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化工具类 - 可不调用, 在 DevUtils FileProviderDevApp 中已初始化
        DevUtils.init(this.getApplicationContext());
        // = 初始化日志配置 =
        // 设置默认 Logger 配置
        LogConfig logConfig = new LogConfig();
        logConfig.logLevel = LogLevel.DEBUG;
        logConfig.tag = LOG_TAG;
        logConfig.sortLog = true; // 美化日志, 边框包围
        logConfig.methodCount = 0;
        DevLogger.init(logConfig);
        // 打开 lib 内部日志 - 线上环境, 不调用方法就行
        DevUtils.openLog();
        DevUtils.openDebug();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }
}
