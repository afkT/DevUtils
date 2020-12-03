# 日志、异常文件记录保存工具类文档

#### 使用演示类 [FileRecordUse](https://github.com/afkT/DevUtils/blob/master/app/src/main/java/utils_use/record/FileRecordUse.java) 介绍了配置参数及使用

> 实际上是两个工具类的差异 ( FileRecordUtils、AnalysisRecordUtils )

#### 项目类结构

* 文件记录工具类（[FileRecordUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/FileRecordUtils.java)）：专门用于信息存储工具类

* 分析记录工具类（[AnalysisRecordUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/AnalysisRecordUtils.java)）：专业记录信息, 并存储方便分析, 支持存储目录、时间段保存

## API 文档

* **文件记录工具类 ->** [FileRecordUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/FileRecordUtils.java)

| 方法 | 注释 |
| :- | :- |
| setInsertInfo | 设置插入信息 |
| setCallback | 设置文件记录回调 |
| saveErrorLog | 保存异常日志 |
| saveLog | 保存日志 |

* **分析记录工具类 ->** [AnalysisRecordUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/AnalysisRecordUtils.java)

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
| get | 获取记录分析文件信息 |
| getLogPath | 获取日志地址 |
| getIntervalTimeFolder | 获取时间间隔 - 文件夹 |

#### FileRecordUtils 工具类 - 使用方法
```java
try {
    String s = null;
    s.indexOf('c');
} catch (NullPointerException e) {
    
    // 设置插入信息
    FileRecordUtils.setInsertInfo(AppCommonUtils.getAppDeviceInfo());

    // 设置插入信息
    FileRecordUtils.setInsertInfo(AppCommonUtils.getAppDeviceInfo());

    FileRecordUtils.saveErrorLog(e, LOG_SD_PATH, System.currentTimeMillis() + ".log");

    FileRecordUtils.saveErrorLog(e, LOG_SD_PATH, System.currentTimeMillis() + ".log", false);

    FileRecordUtils.saveErrorLog(e, LOG_SD_PATH, System.currentTimeMillis() + "_存在头部_底部.log", "头部", "底部", true);

    FileRecordUtils.saveLog("日志内容", LOG_SD_PATH, System.currentTimeMillis() + ".log");

    FileRecordUtils.saveLog("日志内容", LOG_SD_PATH, System.currentTimeMillis() + ".log", false);

    FileRecordUtils.saveLog("日志内容", LOG_SD_PATH, System.currentTimeMillis() + "_存在头部_底部.log", "头部", "底部", true);
}
```

#### AnalysisRecordUtils 工具类 - 使用方法
```java
// 默认存储到 android/data/包名/cache文件/, 可以自己特殊设置
AnalysisRecordUtils.setLogStoragePath(PathUtils.getSDCard().getSDCardPath());

// 设置存储文件夹名
AnalysisRecordUtils.setLogFolderName(AnalysisRecordUtils.getLogFolderName() + "/v" + AppUtils.getAppVersionName());

// AnalysisRecordUtils.HH、MM、SS 以对应的时间, 创建文件夹 HH_23/MM_13/SS_01 依此类推, 放到对应文件夹, 不传则放到当日文件夹下
AnalysisRecordUtils.FileInfo fileInfo = AnalysisRecordUtils.FileInfo.get("test_log.txt", "测试记录", AnalysisRecordUtils.HH);

// 存储路径、存储文件夹、文件名、记录功能提示、时间间隔、是否处理日志记录(是否保存)
// FileInfo(String storagePath, String folderName, String fileName, String fileFunction, @AnalysisRecordUtils.TIME int fileIntervalTime, boolean handler)

// = FileInfo 配置 =

fileInfo = AnalysisRecordUtils.FileInfo.get("test_log.txt", "测试记录");

fileInfo = AnalysisRecordUtils.FileInfo.get("TempRecord","test_log.txt", "测试记录");

fileInfo = AnalysisRecordUtils.FileInfo.get(PathUtils.getSDCard().getSDCardPath(),"TempRecord","test_log.txt", "测试记录");

// =

fileInfo = AnalysisRecordUtils.FileInfo.get("test_log.txt", "测试记录", AnalysisRecordUtils.HH);

fileInfo = AnalysisRecordUtils.FileInfo.get("TempRecord","test_log.txt", "测试记录", AnalysisRecordUtils.MM);

fileInfo = AnalysisRecordUtils.FileInfo.get(PathUtils.getSDCard().getSDCardPath(),"TempRecord","test_log.txt", "测试记录", AnalysisRecordUtils.SS);

// =

// 存储到 android/data/包名/LogFolderName/2018-08-23/LogFolderName/xxx/log.txt
AnalysisRecordUtils.record(fileInfo, "日志内容");

// 存储到 sdcard/LogFolderName/2018-08-23/SDRecord/xxx/log.txt
AnalysisRecordUtils.record(AnalysisRecordUtils.FileInfo.get(PathUtils.getSDCard().getSDCardPath(),"SDRecord","sd_log.txt", "根目录保存", AnalysisRecordUtils.HH),
        "日志内容");

// 存储到 sdcard/特殊地址/LogFolderName/2018-08-23/OtherRecord/xxx/log.txt
AnalysisRecordUtils.record(AnalysisRecordUtils.FileInfo.get(PathUtils.getSDCard().getSDCardPath() + "/特殊地址","OtherRecord","log.txt", "临时地址", AnalysisRecordUtils.HH),
        "日志内容");

// =

// 保存错误信息
NullPointerException nullPointerException = new NullPointerException("报错啦, null 异常啊");
// 记录日志
AnalysisRecordUtils.record(fileInfo, ErrorUtils.getThrowable(nullPointerException));
```