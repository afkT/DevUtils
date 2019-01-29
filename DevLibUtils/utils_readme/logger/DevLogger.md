# DevLogger 日志工具类

#### 框架亮点

* 支持全局日志统一配置, 以及部分日志个性化配置

* 支持无限长度日志打印, 无 Logcat 4000 字符限制

* 支持可变参数传参，任意个数打印参数

* 支持 JSON、XML 字符串解析、格式化打印

* 支持无 Tag 快捷打印、以及全局配置 Tag

* 支持显示行号、线程、类、方法信息等打印, 以及堆栈信息跟踪、偏移打印等

* 支持全局控制打印级别, 防止信息泄露, 以及控制是否打印日志

* 支持存储日志信息到文件中(含手机设备信息、应用版本信息), 并且可追加顶部、底部信息等


#### 全局配置

```java
// == 在BaseApplication 中调用 ==
// 初始化日志配置
LogConfig lConfig = new LogConfig();
// 堆栈方法总数(显示经过的方法)
lConfig.methodCount = 3;
// 堆栈方法索引偏移(0 = 最新经过调用的方法信息,偏移则往上推,如 1 = 倒数第二条经过调用的方法信息)
lConfig.methodOffset = 0;
// 是否输出全部方法(在特殊情况下，如想要打印全部经过的方法，但是不知道经过的总数)
lConfig.isOutputMethodAll = false;
// 显示日志线程信息(特殊情况，显示经过的线程信息,具体情况如上)
lConfig.isDisplayThreadInfo = false;
// 是否排序日志(格式化后)
lConfig.isSortLog = false; // 是否美化日志, == 边框包围
// 日志级别
lConfig.logLevel = LogLevel.DEBUG;
// 设置Tag(特殊情况使用，不使用全部的Tag时,如单独输出在某个Tag下)
lConfig.tag = "BaseLog";
// 进行初始化配置 => 这样设置后, 默认全部日志都使用改配置, 特殊使用 DevLogger.other(config).d(xxx);
DevLogger.init(lConfig);
// 进行初始化配置 => 在DevUtils.init() 内部调用了
// DevLoggerUtils.init(mContext); // 日志操作工具类, 快捷获取 LogConfig、以及保存日志到文件中等
```