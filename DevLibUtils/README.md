### Use

> 只需要在 Application 中调用 DevUtils.init() 进行初始化就行
> <p>DevUtils.openLog() 是打开内部工具类 日志输出, 发包则不调用此句
> <p> DevLogger => [DevLogger](https://github.com/afkT/DevLogger)

```
/**
 * detail: 全局Application
 * Created by Ttt
 */
public class BaseApplication extends Application{

    // 日志TAG
    private final String LOG_TAG = BaseApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化工具类
        DevUtils.init(this.getApplicationContext());
        // == 初始化日志配置 ==
        // 设置默认Logger配置
        LogConfig logConfig = new LogConfig();
        logConfig.logLevel = LogLevel.DEBUG;
        logConfig.tag = LOG_TAG;
        DevLogger.init(logConfig);
        // 打开 lib 内部日志
        DevUtils.openLog();
        DevUtils.openDebug();
    }
}
```

### API