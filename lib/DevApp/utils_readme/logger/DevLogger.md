# DevLogger 日志工具类

#### 使用演示类 [LoggerUse](https://github.com/afkT/DevUtils/blob/master/app/src/main/java/utils_use/logger/LoggerUse.java) 介绍了配置参数及使用

#### 项目类结构 - [包目录](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/logger)

* 日志操作类（[DevLogger](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/logger/DevLogger.java)）：日志操作类(对外公开直接调用), 直接

* 日志接口（[IPrinter](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/logger/IPrinter.java)）：主要编写可以被外部调用接口, 以及可以操作的类型

* 日志输出类（[LoggerPrinter](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/logger/LoggerPrinter.java)）：实现日志接口, 并且对对应的方法, 进行处理, 最终打印

* 日志设置（[LogConfig](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/logger/LogConfig.java)）：该类主要控制日志输出方式, 以及是否输入日志, 堆栈方法等、提供常用日志配置快捷获取方法

* 日志配置（[LogConstants](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/logger/LogConstants.java)）：该类主要是常量配置信息

* 日志级别（[LogLevel](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/logger/LogLevel.java)）：该类主要控制日志级别


#### 框架亮点

* 支持全局日志统一配置, 以及部分日志个性化配置

* 支持无限长度日志打印, 无 Logcat 4000 字符限制

* 支持可变参数传参, 任意个数打印参数

* 支持 JSON、XML 字符串解析、格式化打印

* 支持无 Tag 快捷打印、以及全局配置 Tag

* 支持显示行号、线程、类、方法信息等打印, 以及堆栈信息跟踪、偏移打印等

* 支持全局控制打印级别, 防止信息泄露, 以及控制是否打印日志

* 支持存储日志信息到文件中(含手机设备信息、应用版本信息), 并且可追加顶部、底部信息等

* 美化日志, 与系统日志打印格式区分, 清晰快捷找到关键日志

* 支持输出含当前类及行号和函数名等堆栈信息, 点击跳转


## API 文档

| 方法 | 注释 |
| :- | :- |
| other | 使用单次其他日志配置 |
| getLogConfig | 获取日志配置信息 |
| init | 初始化日志配置信息(使用默认配置) |
| d | 打印 Log.DEBUG |
| e | 打印 Log.ERROR |
| w | 打印 Log.WARN |
| i | 打印 Log.INFO |
| v | 打印 Log.VERBOSE |
| wtf | 打印 Log.ASSERT |
| json | 格式化 JSON 格式数据, 并打印 |
| xml | 格式化 XML 格式数据, 并打印 |
| dTag | 打印 Log.DEBUG |
| eTag | 打印 Log.ERROR |
| wTag | 打印 Log.WARN |
| iTag | 打印 Log.INFO |
| vTag | 打印 Log.VERBOSE |
| wtfTag | 打印 Log.ASSERT |
| jsonTag | 格式化 JSON 格式数据, 并打印 |
| xmlTag | 格式化 XML 格式数据, 并打印 |


#### 全局配置

```java
// = 在 BaseApplication 中调用 =
// 初始化日志配置
LogConfig logConfig = new LogConfig();
// 堆栈方法总数(显示经过的方法)
logConfig.methodCount = 3;
// 堆栈方法索引偏移(0 = 最新经过调用的方法信息, 偏移则往上推, 如 1 = 倒数第二条经过调用的方法信息)
logConfig.methodOffset = 0;
// 是否输出全部方法(在特殊情况下, 如想要打印全部经过的方法, 但是不知道经过的总数)
logConfig.outputMethodAll = false;
// 显示日志线程信息(特殊情况, 显示经过的线程信息, 具体情况如上)
logConfig.displayThreadInfo = false;
// 是否排序日志(格式化后)
logConfig.sortLog = false; // 是否美化日志, 边框包围
// 日志级别
logConfig.logLevel = LogLevel.DEBUG;
// 设置 TAG (特殊情况使用, 不使用全部的 TAG 时, 如单独输出在某个 TAG 下)
logConfig.tag = "BaseLog";
// 进行初始化配置, 这样设置后, 默认全部日志都使用改配置, 特殊使用 DevLogger.other(config).d(xxx);
DevLogger.init(logConfig);
```


#### 配置事项
```java
// 发布的时候, 默认不需要打印日志则修改为
LogConfig logConfig = new LogConfig();
logConfig.logLevel = LogLevel.NONE; // 全部不打印
DevLogger.init(logConfig); // 该方法设置全局默认日志配置

// 还有一种情况, 部分日志发布的时候不打印, 但是有部分异常信息需要打印, 则单独使用配置
LogConfig.getReleaseLogConfig(TAG) // 使用封装好的线上配置都行
LogConfig.getReleaseLogConfig(TAG, LogLevel) // 使用封装好的线上配置都行
DevLogger.init(LogConfig.getReleaseLogConfig(TAG));
```


#### 打印日志
```java
// 无 Tag 快捷打印 (使用全局 LogConfig.tag)
DevLogger.v("测试数据 - v");
DevLogger.d("测试数据 - d");
DevLogger.i("测试数据 - i");
DevLogger.w("测试数据 - w");
DevLogger.e("错误 - e");
DevLogger.wtf("测试数据 - wtf");

// 使用 自定义 Tag 打印日志
DevLogger.vTag(tag, "测试数据 - v");
DevLogger.dTag(tag, "测试数据 - d");
DevLogger.iTag(tag, "测试数据 - i");
DevLogger.wTag(tag, "测试数据 - w");
DevLogger.eTag(tag, "错误 - e");
DevLogger.wtfTag(tag, "测试数据 - wtf");

// 占位符(其他类型, 一样)
DevLogger.d("%s测试占位符数据 d%s", new Object[]{"1.", " - Format"});
// =
DevLogger.dTag(tag, "%s测试占位符数据 d%s", new Object[]{"1.", " - Format"});

// 打印 JSON、XML 格式字符串数据
// JSON对象
DevLogger.json(TestData.SMALL_SON_WITH_NO_LINE_BREAK);
DevLogger.jsonTag(tag, TestData.SMALL_SON_WITH_NO_LINE_BREAK);
// XML数据
DevLogger.xml(TestData.XML_DATA);
DevLogger.xmlTag(tag, TestData.XML_DATA);
```


#### 打印日志(自定义配置)
```java
// 初始化日志配置
LogConfig logConfig = new LogConfig();
// 是否排序日志(格式化后)
logConfig.sortLog = true;
// 日志级别
logConfig.logLevel = LogLevel.DEBUG;
// 设置 TAG (特殊情况使用, 不使用全部的 TAG 时, 如单独输出在某个 TAG 下)
logConfig.tag = "SAD";
// 打印日志信息
DevLogger.other(logConfig).e("new Config - e");
DevLogger.other(logConfig).e(new Exception("报错"), "new Config - e");
DevLogger.other(logConfig).eTag(tag, "new Config - e");
DevLogger.other(logConfig).eTag(tag, new Exception("报错"), "new Config - e");

// 有 Tag 优先使用自定义 Tag, 无 Tag 才使用 LogConfig.tag 
DevLogger.other(logConfig).eTag(tag, "new Config - e");
```


# 预览

***XML、JSON 格式化打印***

![](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/utils_readme/logger/log_xml_json.png)

***打印堆栈信息***

![](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/utils_readme/logger/log_default.png)

***打印异常信息***

![](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/utils_readme/logger/log_error.png)

***正常打印***

![](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/utils_readme/logger/log_other.png)