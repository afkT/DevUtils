# 日志、异常文件记录保存工具类文档

#### 使用演示类 [FileRecordUse](https://github.com/afkT/DevUtils/blob/master/app/src/main/java/com/dev/utils/record/FileRecordUse.java) 介绍了配置参数及使用

> 三个工具类, 实际上是两个工具类的差异 ( DevLoggerUtils/FileRecordUtils 、 AnalysisRecordUtils)

> DevLoggerUtils => 内部的 Utils, 实际和 FileRecordUtils 代码相同, 使用方式一致

#### 项目类结构

* 日志操作工具类（[DevLoggerUtils](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/logger/DevLoggerUtils.java)）：提供常用日志配置快捷获取方法、以及日志存储方法等

* App 文件记录工具类（[FileRecordUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/FileRecordUtils.java)）：同 DevLoggerUtils 一样, 专门用于信息存储工具类

* 分析记录工具类（[AnalysisRecordUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/AnalysisRecordUtils.java)）：专业记录信息, 并存储方便分析, 支持存储目录、时间段保存

## API 文档

* **日志操作工具类 ->** [DevLoggerUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/logger/DevLoggerUtils.java)

| 方法 | 注释 |
| :- | :- |
| init | 初始化调用方法(获取版本号) |
| getReleaseLogConfig | 获取发布 Log 配置(打印线程信息,显示方法总数3,从0开始,不进行排序, 默认属于ERROR级别日志) |
| getDebugLogConfig | 获取调试 Log 配置(打印线程信息,显示方法总数3,从0开始,不进行排序, 默认属于ERROR级别日志) |
| getSortLogConfig | 获取 Log 配置(打印线程信息,显示方法总数3,从0开始,并且美化日志信息, 默认属于DEBUG级别日志) |
| getLogConfig | 获取 Log 配置 |
| saveErrorLog | 保存 App 错误日志 |
| saveLog | 保存 App 日志 |
| saveLogHeadBottom | 保存 App 日志 - 包含头部、底部信息 |

* **App 文件记录工具类 ->** [FileRecordUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/FileRecordUtils.java)

| 方法 | 注释 |
| :- | :- |
| init | 初始化调用方法 |
| saveErrorLog | 保存 App 错误日志 |
| saveLog | 保存 App 日志 |
| handlerVariable | 处理可变参数 |


* **分析记录工具类 ->** [AnalysisRecordUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/AnalysisRecordUtils.java)

| 方法 | 注释 |
| :- | :- |
| init | 初始化操作 |
| record | 日志记录 |
| isHandler | 是否处理日志记录 |
| setHandler | 设置是否处理日志记录 |
| isAppendSpace | 是否追加空格 |
| setAppendSpace | 设置是否追加空格 |
| getLogFolderName | 获取文件日志名 |
| setLogFolderName | 设置日志文件夹名 |
| getLogStoragePath | 获取日志存储路径 |
| setLogStoragePath | 设置日志存储路径 |
| getStoragePath | 获取存储路径 |
| getFileName | 获取日志文件名 |
| getFileFunction | 获取日志文件记录的功能 |
| getFileIntervalTime | 获取日志文件记录间隔时间 |
| getFolderName | 获取日志文件夹名 |
| obtain | 获取记录分析文件信息 |
| getLogPath | 获取日志地址 |
| getIntervalTimeFolder | 获取时间间隔 - 文件夹 |

#### DevLoggerUtils、FileRecordUtils 工具类 - 使用方法
```java
try {
    String s = null;
    s.indexOf("c");
} catch (NullPointerException e) {

    // = DevLoggerUtils 使用方法 =

    // 保存的路径
    String fileName = LOG_SD_PATH + System.currentTimeMillis() + ".log";
    // 保存日志信息
    DevLoggerUtils.saveErrorLog(e, fileName, true);
    // =
    // 保存自定义头部、底部信息
    DevLoggerUtils.saveErrorLog(e, "头部", "底部", LOG_SD_PATH, System.currentTimeMillis() + "_存在头部_底部.log", true);
    // =
    // 自定义(无设备信息、失败信息获取失败) - 正常不会出现，所以其实这个可以不用
    String[] eHint = new String[]{"DeviceInfo = 获取设备信息失败", "获取失败"};
    // 保存的路径
    fileName = LOG_SD_PATH + System.currentTimeMillis() + "_orgs.log";
    // 保存日志信息
    DevLoggerUtils.saveErrorLog(e, fileName, true, eHint);

    // 保存日志信息
    DevLoggerUtils.saveLog("日志内容", LOG_SD_PATH, System.currentTimeMillis() + ".log");
    // 保存日志信息
    DevLoggerUtils.saveLogHeadBottom("日志内容", "头部", "底部", LOG_SD_PATH, System.currentTimeMillis() + "_存在头部_底部.log");

    // = FileRecordUtils 使用方法 =

    FileRecordUtils.saveErrorLog(e, "头部", "底部", LOG_SD_PATH, System.currentTimeMillis() + "_存在头部_底部.log", true, true, "xaskdjaslkd");

    FileRecordUtils.saveLog("日志内容", "头部", "底部", LOG_SD_PATH, System.currentTimeMillis() + "_存在头部_底部.log", true, "qqqqweqweqwe");
}
```

#### AnalysisRecordUtils 工具类 - 使用方法
```java
// 默认存储到 android/data/包名/cache文件/ , 可以自己特殊设置
AnalysisRecordUtils.setLogStoragePath(SDCardUtils.getSDCardPath());

// 设置存储文件夹名
AnalysisRecordUtils.setLogFolderName(AnalysisRecordUtils.getLogFolderName() + "/v" + AppUtils.getAppVersionName());

// AnalysisRecordUtils.HH、MM、SS => 以对应的时间, 创建文件夹 HH_23/MM_13/SS_01 依此类推，放到对应文件夹, 不传则放到当日文件夹下
AnalysisRecordUtils.FileInfo fileInfo = AnalysisRecordUtils.FileInfo.obtain("test_log.txt", "测试记录", AnalysisRecordUtils.HH);

// 存储路径、存储文件夹、文件名、记录功能提示、时间间隔、是否处理日志记录(是否保存)
// FileInfo(String storagePath, String folderName, String fileName, String fileFunction, @AnalysisRecordUtils.TIME int fileIntervalTime, boolean handler)

// = FileInfo 配置 =

fileInfo = AnalysisRecordUtils.FileInfo.obtain("test_log.txt", "测试记录");

fileInfo = AnalysisRecordUtils.FileInfo.obtain("TempRecord","test_log.txt", "测试记录");

fileInfo = AnalysisRecordUtils.FileInfo.obtain(SDCardUtils.getSDCardPath(),"TempRecord","test_log.txt", "测试记录");

// =

fileInfo = AnalysisRecordUtils.FileInfo.obtain("test_log.txt", "测试记录", AnalysisRecordUtils.HH);

fileInfo = AnalysisRecordUtils.FileInfo.obtain("TempRecord","test_log.txt", "测试记录", AnalysisRecordUtils.MM);

fileInfo = AnalysisRecordUtils.FileInfo.obtain(SDCardUtils.getSDCardPath(),"TempRecord","test_log.txt", "测试记录", AnalysisRecordUtils.SS);

// =

// 存储到 android/data/包名/LogFolderName/2018-08-23/LogFolderName/xxx/log.txt
AnalysisRecordUtils.record(fileInfo, "日志内容");

// 存储到 sdcard/LogFolderName/2018-08-23/SDRecord/xxx/log.txt
AnalysisRecordUtils.record(AnalysisRecordUtils.FileInfo.obtain(SDCardUtils.getSDCardPath(),"SDRecord","sd_log.txt", "根目录保存", AnalysisRecordUtils.HH),
        "日志内容");

// 存储到 sdcard/特殊地址/LogFolderName/2018-08-23/OtherRecord/xxx/log.txt
AnalysisRecordUtils.record(AnalysisRecordUtils.FileInfo.obtain(SDCardUtils.getSDCardPath() + "/特殊地址","OtherRecord","log.txt", "临时地址", AnalysisRecordUtils.HH),
        "日志内容");

// =

// 保存错误信息
NullPointerException nullPointerException = new NullPointerException("报错啦， null 异常啊");
// 记录日志
AnalysisRecordUtils.record(fileInfo, ErrorUtils.getThrowableMsg(nullPointerException));
```