Change Log
==========

Version 1.4.6 *(2022-07-04)*
----------------------------

* `[Add]` 新增 StringUtils#equalsIgnoreCase、equalsIgnoreCaseNotNull

* `[Add]` 新增 ValiToPhoneUtils 中国广电手机号码段、更新最新运营商号码段

* `[Add]` 新增 DevFinal 部分常量值

Version 1.4.5 *(2022-06-26)*
----------------------------

* `[Review]` 检查并调整使用 equals、equalsIgnoreCase 代码

* `[Add]` 新增 StringUtils#equalsNotNull 方法

Version 1.4.4 *(2022-05-13)*
----------------------------

* `[Add]` 新增 DevFinal#INT 部分默认值

* `[Add]` 新增 UrlExtras Url 携带信息解析类，并默认提供 DevJavaUrlParser 实现

* `[Add]` 新增 ConvertUtils#newString 方法处理 CharSequence

* `[Add]` 新增 ConvertUtils#newStringNotArrayDecode 并修改所有工具类 instanceof String 判断转换使用该方法

* `[Add]` 新增 BigDecimalUtils 抛出异常相对应捕获异常快捷方法，并修改抛出方法名为 xxxThrow(param)

Version 1.4.3 *(2022-03-20)*
----------------------------

* `[Add]` 新增 DevFinal#DEFAULT 默认值，并全局统一该库默认值

* `[Add]` 新增 BigDecimalUtils 快捷方法抛出异常便于自定义异常值

Version 1.4.2 *(2022-02-12)*
----------------------------

* `[Add]` StringUtils#join、joinArgs

* `[Add]` CollectionUtils#clearAndAddAll、clearAndAddAllNotNull

Version 1.4.1 *(2022-01-23)*
----------------------------

* `[Update]` 部分布尔值变量 is 命名移除

Version 1.4.0 *(2022-01-10)*
----------------------------

* `[Add]` ComparatorUtils 排序比较器工具类

* `[Add]` FileUtils#getFile、listOrEmpty、listFilesOrEmpty

Version 1.3.9 *(2021-12-30)*
----------------------------

* `[Refactor]` 修改整个 DevFinal 常量类，并统一使用该常量类

Version 1.3.8 *(2021-12-20)*
----------------------------

* `[Refactor]` 修改整个 DevFinal 常量类，并统一使用该常量类

* `[Add]` FlagsValue 标记值计算存储 ( 位运算符 )

Version 1.3.7 *(2021-11-26)*
----------------------------

* `[Add]` WeakReferenceAssist 弱引用辅助类

* `[Add]` MapUtils#mapToString

Version 1.3.6 *(2021-09-20)*
----------------------------

* `[Refactor]` review code、代码格式化处理、方法名、参数名、变量名等规范排查

* `[Add]` ForUtils

* `[Add]` XorUtils#xorChecksum

* `[Add]` FileUtils#createTimestampFileName

Version 1.3.5 *(2021-06-28)*
----------------------------

* `[Refactor]` 重构 FileRecordUtils 并进行内部类拆分便于 DevApp 模块使用

Version 1.3.4 *(2021-06-21)*
----------------------------

* `[Add]` StringUtils#urlDecodeWhile

* `[Add]` HttpParamsUtils#getUrlParams、getUrlParamsArray、existsParams、existsParamsByURL、joinUrlParams、getUrlParamsJoinSymbol、splitParamsByUrl

Version 1.3.3 *(2021-06-04)*
----------------------------

* `[Add]` DevFinal 新增部分常量

Version 1.3.2 *(2021-05-19)*
----------------------------

* `[Add]` ColorUtils#sortHUE、sortSaturation、sortBrightness

Version 1.3.1 *(2021-05-09)*
----------------------------

* `[sync]` sync to Maven Central

Version 1.3.0 *(2021-03-21)*
----------------------------

* `[Add]` ColorUtils#blendColor、transitionColor

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

* `[Add]` ScaleUtils#XY type 扩展字段

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

* `[Add]` FileUtils#listFilesInDirBean、listFilesInDirWithFilterBean 方法，获取文件目录列表集合 FileList

Version 1.0.7 *(2019-12-25)*
----------------------------

* `[Add]` ColorUtils#getARGB、grayLevel、sortGray、sortHSB 并增加内部类 ColorInfo，支持颜色排序

* `[Add]` FileIOUtils#copyLarge、DateUtils#yyyyMMdd_HHmmss、CoordinateUtils#getDistance、getAngle、getDirection

* `[Add]` DevCommonUtils、StringUtils#appendsIgnoreLast

* `[Update]` 删除 DevCommonUtils、StringUtils 几个重载方法 appends

* `[Update]` 更新部分工具类、方法注释代码、代码间距等

Version 1.0.6 *(2019-11-25)*
----------------------------

* `[Refactor]` 重构整个项目，优化代码逻辑判断、代码风格、合并工具类减少包大小等，并修改 95% 返回值 void 的方法为 boolean 明确获取调用结果

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

* `[Update]` 修改 FileBreadthFirstSearchUtils 部分方法返回值 ( 返回当前对象，方便链式调用 )

Version 1.0.1 *(2019-09-12)*
----------------------------

* `[Add]` ConvertUtils#convert

Version 1.0.0 *(2019-08-25)*
----------------------------

* Initial release
