Change Log
==========

Version 1.2.9 *(2021-03-02)*
----------------------------

* `[Refactor]` BigDecimalUtils

Version 1.2.8 *(2021-02-27)*
----------------------------

* `[Add]` NumberUtils#subZeroAndDot

* `[Refactor]` BigDecimalUtils

* `[Add]` DevFinal 新增部分常量

Version 1.2.7 *(2021-02-08)*
----------------------------

* `[Add]` ColorUtils#getRandomColorString 方法

* `[Add]` DevFinal 新增部分常量

Version 1.2.6 *(2021-01-24)*
----------------------------

* `[Perf]` 进行代码检测优化

* `[Add]` DevFinal 新增部分常量

Version 1.2.5 *(2021-01-01)*
----------------------------

* `[Style]` 代码格式化处理 ( 间距美化调整等 )

Version 1.2.4 *(2020-12-10)*
----------------------------

* `[Style]` 代码格式化处理 ( 间距美化调整等 )

* `[Update]` 修改 CallBack 相关代码为 Callback

* `[Add]` DevFinal 新增部分常量

Version 1.2.3 *(2020-11-15)*
----------------------------

* `[Refactor]` 使用 QAPlugs ( PMD、findbugs、checkstyle )、IDEA Analyze 进行代码质量分析、代码优化等

Version 1.2.2 *(2020-11-05)*
----------------------------

* `[Refactor]` 使用 QAPlugs ( PMD、findbugs、checkstyle )、IDEA Analyze 进行代码质量分析、代码优化等

Version 1.2.1 *(2020-10-29)*
----------------------------

* `[Add]` ArrayUtils#asListArgs

* `[Update]` MapUtils#putToList、removeToList、removeToMap 参数类型

* `[Update]` 进行 Spelling typo Analyze 修改部分拼写错误字段

Version 1.2.0 *(2020-10-20)*
----------------------------

* `[Update]` StringUtils#convertHideMobile、convertSymbolHide Method Name

* `[Fix]` StringUtils#replaceSEWith、clearSEWiths、clearEndsWith 索引判断问题

Version 1.1.9 *(2020-10-12)*
----------------------------

* `[Add]` FilePartUtils 文件分片工具类

* `[Add]` CloseUtils#flush、flushQuietly、flushCloseIO、flushCloseIOQuietly

* `[Add]` FileUtils#convertFiles、convertPaths、flushCloseIO、flushCloseIOQuietly

* `[Refactor]` 修改整个库 Closeable Close 代码内部调用 CloseUtils

Version 1.1.8 *(2020-09-27)*
----------------------------

* `[Add]` DateUtils#getZodiac、getConstellation、getConstellationDate 获取生肖、星座方法

* `[Add]` CalendarUtils 日历 ( 公历、农历 ) 工具类

Version 1.1.7 *(2020-09-20)*
----------------------------

* `[Delete]` 删除 DevCommonUtils 中其他工具类快捷方法

* `[Update]` 更新部分代码注释

Version 1.1.6 *(2020-09-15)*
----------------------------

* `[Add]` StringUtils#clearTab、clearTabTrim、clearLine、clearLineTrim、clearSpaceTabLine、clearSpaceTabLineTrim

* `[Add]` ScaleUtils#XY type 拓展字段

* `[Add]` NumberUtils#addZero

* `[Update]` DateUtils#convertTime 为 timeAddZero

* `[Update]` 去除部分方法名 to 前缀

Version 1.1.5 *(2020-09-07)*
----------------------------

* `[Add]` ScaleUtils#calcScale、calcScaleToMath、calcXY

* `[Add]` StringUtils#forJoint

Version 1.1.4 *(2020-08-29)*
----------------------------

* `[Add]` FileUtils#canRead、canWrite、canReadWrite

Version 1.1.3 *(2020-08-04)*
----------------------------

* `[Add]` ChineseUtils 中文工具类

* `[Add]` StringUtils#forString

Version 1.1.2 *(2020-05-18)*
----------------------------

* `[Update]` ClassUtils#getGenericSuperclass、getGenericInterfaces 返回类型

Version 1.1.1 *(2020-03-11)*
----------------------------

* `[Add]` StringUtils#split

* `[Add]` NumberUtils#calculateUnit

* `[Update]` StringUtils#replaceStr、replaceStrToNull 方法名为 StringUtils#replaceAll、replaceAllToNull

Version 1.1.0 *(2020-02-21)*
----------------------------

* `[Add]` StringUtils#getBytes

* `[Add]` FileIOUtils#getFileInputStream、getFileOutputStream

* `[Update]` FileUtils#saveFile、appendFile

Version 1.0.9 *(2020-01-26)*
----------------------------

* `[Add]` TypeUtils 类型工具类

* `[Add]` ClassUtils#getClass、isGenericParamType、getGenericParamType

* `[Add]` ConvertUtils#toBigDecimal、toBigInteger、newString、charAt

* `[Update]` ConvertUtils#toString、toInt、toBoolean、toFloat、toDouble、toLong、toShort、toChar、toByte、toChars、toBytes

Version 1.0.8 *(2020-01-16)*
----------------------------

* `[Add]` FileUtils#listFilesInDirBean、listFilesInDirWithFilterBean 方法, 获取文件目录列表集合 FileList

Version 1.0.7 *(2019-12-25)*
----------------------------

* `[Add]` ColorUtils#getARGB、grayLevel、sortGray、sortHSB 并增加内部类 ColorInfo, 支持颜色排序

* `[Add]` FileIOUtils#copyLarge、DateUtils#yyyyMMdd_HHmmss、CoordinateUtils#getDistance、getAngle、getDirection

* `[Add]` DevCommonUtils、StringUtils#appendsIgnoreLast

* `[Update]` 删除 DevCommonUtils、StringUtils 几个重载方法 appends

* `[Update]` 更新部分工具类、方法注释代码、代码间距等

Version 1.0.6 *(2019-11-25)*
----------------------------

* `[Refactor]` 重构整个项目, 优化代码逻辑判断、代码风格、合并工具类减少包大小等, 并修改 95% 返回值 void 的方法为 boolean 明确获取调用结果
 
* `[Add]` FileRecordUtils 文件记录结果回调

* `[Add]` MapUtils、CollectionUtils 获取泛型数组 toArrayT

* `[Update]` 移动 FileRecordUtils、HtmlUtils 到 Java 模块

Version 1.0.5 *(2019-11-05)*
----------------------------
 
* `[Add]` FileUtils#isImageFormats、isAudioFormats、isVideoFormats、isFileFormats

Version 1.0.4 *(2019-10-31)*
----------------------------
 
* `[Add]` ArrayUtils#getMinimum、getMaximum、getMinimumIndex、getMaximumIndex、sumarray

* `[Add]` CollectionUtils#getMinimum、getMaximum、getMinimumIndex、getMaximumIndex、sumlist

Version 1.0.3 *(2019-10-09)*
----------------------------
 
 * `[Add]` NumberUtils#getMultiple、getMultipleI、getMultipleD、getMultipleL、getMultipleF

Version 1.0.2 *(2019-09-19)*
----------------------------
 
 * `[Update]` 修改 FileBreadthFirstSearchUtils 部分方法返回值 ( 返回当前对象, 方便链式调用 )

Version 1.0.1 *(2019-09-12)*
----------------------------
 
 * `[Add]` ConvertUtils#convert

Version 1.0.0 *(2019-08-25)*
----------------------------

 Initial release
