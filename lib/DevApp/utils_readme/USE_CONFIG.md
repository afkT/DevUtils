
## 初始化

```java
/**
 * detail: 全局 Application
 * @author Ttt
 */
public class BaseApplication extends Application {

    // 日志 TAG
    private final String LOG_TAG = BaseApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

//        // 初始化工具类 - 可不调用, 在 DevUtils FileProviderDevApp 中已初始化, 无需主动调用
//        DevUtils.init(this.getApplicationContext());
        // = 初始化日志配置 =
        // 设置默认 Logger 配置
        LogConfig logConfig = new LogConfig();
        logConfig.logLevel = LogLevel.DEBUG;
        logConfig.tag = LOG_TAG;
        logConfig.sortLog = true; // 美化日志, 边框包围
        logConfig.methodCount = 0;
        DevLogger.init(logConfig);
        // 打开 lib 内部日志 - 线上环境, 不调用方法
        DevUtils.openLog();
        DevUtils.openDebug();
    }
}
```

# 配置与使用相关 - [目录](https://github.com/afkT/DevUtils/blob/master/app/src/main/java/utils_use)

## [DevLogger 日志工具类文档](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/utils_readme/logger/DevLogger.md)

## [DevToast Toast工具类文档](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/utils_readme/toast/DevToast.md)

## [ToastTintUtils Toast美化工具类文档](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/utils_readme/toast/ToastTintUtils.md)

## [SnackbarUtils Snackbar工具类文档](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/utils_readme/snackbar/SnackbarUtils.md)

## [FileRecordUtils、AnalysisRecordUtils 日志、异常文件记录保存工具类文档](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/utils_readme/record/FileRecord.md)

## [SharedUtils - SharedPreferences工具类文档](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/utils_readme/share/SharedUtils.md)

## [DevCache - 缓存工具类文档](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/utils_readme/cache/DevCache.md)

## [TimerManager 定时器工具类文档](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/utils_readme/timer/TimerManager.md)

## [DevThreadManager - 线程工具类文档](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/utils_readme/thread/DevThreadManager.md)

## [DevMediaManager 多媒体工具类文档](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/utils_readme/media/DevMediaManager.md)

## [WifiHotUtils - Wifi热点工具类文档](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/utils_readme/wifi/WifiHotUtils.md)

## [TextViewUtils - 字体计算工具类使用](https://github.com/afkT/DevUtils/blob/master/app/src/main/java/utils_use/text/TextCalcUse.java)
