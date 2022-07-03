Change Log
==========

Version 2.4.0 *(2022-07-03)*
----------------------------

* `[Add]` 新增 UriUtils#isAndroidResourceScheme、isFileScheme、isContentScheme、isUriScheme

* `[Add]` 新增 StringUtils#equalsIgnoreCase、equalsIgnoreCaseNotNull

* `[Add]` 新增 ValiToPhoneUtils 中国广电手机号码段、更新最新运营商号码段

* `[Add]` 新增 DevFinal 部分常量值

Version 2.3.9 *(2022-06-26)*
----------------------------

* `[Review]` 检查并调整使用 equals、equalsIgnoreCase 代码

* `[Add]` 新增 StringUtils#equalsNotNull 方法

* `[Add]` 新增 图片 EXIF 读写辅助类

Version 2.3.8 *(2022-06-02)*
----------------------------

* `[Refactor]` 新建 DefaultActivityResult 类并迁移 ActivityUtils 原始 startActivityForResult 封装实现代码

* `[Add]` 新增 New Activity Result API 封装辅助类 ActivityResultAssist

* `[Add]` 新增 ActivityResultUtils 工具类，用于使用新旧 ActivityResult 兼容

Version 2.3.7 *(2022-05-13)*
----------------------------

* `[Add]` 新增 DevUtils#getHandler(handler) 判断 null，视情况返回全局 MainHandler

* `[Refactor]` 重构 AccessibilityUtils 工具类，并对节点信息、节点、日志打印操作进行独立封装 Node、Operation、Print 类

* `[Refactor]` Helper 链式相关类，移除 IHelper 并且新增 BaseHelper 内置通用方法

* `[Add]` 新增 FlowHelper 流式 ( 链式 ) 连接 Helper 类，便捷链接其他 Helper 但不局限于上述类使用

* `[Add]` 新增 DevFinal#INT 部分默认值

* `[Add]` 新增 UrlExtras Url 携带信息解析类，并默认提供 DevJavaUrlParser、AndroidUrlParser 实现

* `[Add]` 新增 ConvertUtils#newString 方法处理 CharSequence

* `[Add]` 新增 ConvertUtils#newStringNotArrayDecode 并修改所有工具类 instanceof String 判断转换使用该方法

* `[Add]` 新增 BigDecimalUtils 抛出异常相对应捕获异常快捷方法，并修改抛出方法名为 xxxThrow(param)

Version 2.3.6 *(2022-03-20)*
----------------------------

* `[Add]` 新增 DevFinal#DEFAULT 默认值，并全局统一该库默认值

* `[Add]` 新增 BigDecimalUtils 快捷方法抛出异常便于自定义异常值

* `[Add]` 新增 RecyclerViewUtils#getOrientation、canScrollVertically、canScrollHorizontally LayoutManager 参数

* `[Add]` 新增 NotificationUtils#createNotificationBuilder 方法

Version 2.3.5 *(2022-02-12)*
----------------------------

* `[Add]` StringUtils#join、joinArgs

* `[Add]` CollectionUtils#clearAndAddAll、clearAndAddAllNotNull

* `[Add]` RecyclerViewUtils#setSpanCount、getSpanCount

* `[Add]` ViewHelper、QuickHelper#setSpanCount

* `[Add]` IFloatingListener、DevFloatingListener、DevFloatingCommon 解决悬浮窗 onTouchEvent 与 click、longClick 冲突处理

Version 2.3.4 *(2022-01-23)*
----------------------------

* `[Update]` SizeUtils#dipConvertPx、dipConvertPxf、pxConvertDip、pxConvertDipf、spConvertPx、spConvertPxf、pxConvertSp、pxConvertSpf 方法重命名为 dp2px、dp2pxf、px2dp、px2dpf、sp2px、sp2pxf、px2sp、px2spf

* `[Update]` ClickUtils、ListenerUtils 移除 listener 判 null 处理

Version 2.3.3 *(2022-01-10)*
----------------------------

* `[Add]` ComparatorUtils 排序比较器工具类

* `[Add]` FileUtils#getFile、listOrEmpty、listFilesOrEmpty

Version 2.3.2 *(2021-12-30)*
----------------------------

* `[Refactor]` 修改整个 DevFinal 常量类，并统一使用该常量类

* `[Add]` IFloatingEdge、DevFloatingEdgeIMPL、DevFloatingTouchIMPL2 等悬浮窗边缘检测、触摸实现类

* `[Add]` QuickHelper#setX、setY

* `[Add]` ViewHelper#setX、setY

* `[Add]` ActivityUtils#isDestroyed、isNotDestroyed、assertValidActivity

* `[Add]` ViewUtils#setX、setY、getX、getY

Version 2.3.1 *(2021-12-20)*
----------------------------

* `[Refactor]` 修改整个 DevFinal 常量类，并统一使用该常量类

* `[Add]` FloatingWindowManagerAssist、FloatingWindowManagerAssist2 悬浮窗两种实现方案辅助类

* `[Add]` ActivityLifecycleAssist Activity 生命周期监听辅助类

* `[Add]` WindowAssist Window 辅助类

* `[Add]` WindowUtils Window 工具类

* `[Add]` DevHelper Dev 工具类链式调用类

* `[Add]` FlagsValue 标记值计算存储 ( 位运算符 )

Version 2.3.0 *(2021-11-26)*
----------------------------

* `[Refactor]` 检查整个项目新增部分方法支持传入 Context 参数 ( 以便屏幕适配等 )

* `[Add]` ActivityManagerAssist Activity 栈管理辅助类

* `[Add]` WeakReferenceAssist 弱引用辅助类

* `[Add]` MapUtils#mapToString

Version 2.2.9 *(2021-09-20)*
----------------------------

* `[Refactor]` review code、代码格式化处理、方法名、参数名、变量名等规范排查

* `[Add]` ForUtils

* `[Add]` XorUtils#xorChecksum

* `[Add]` FileUtils#createTimestampFileName

* `[Add]` RecyclerViewUtils

* `[Add]` SharedPreferences 操作监听器 OnSPOperateListener

* `[Add]` ViewUtils#setVisibilityIN、setVisibilityINs、getGlobalVisibleRect、getLocalVisibleRect、getLocationInWindow、getLocationOnScreen、setBarProgress、setBarMax、setBarValue

* `[Add]` KeyBoardUtils#openKeyboardByFocus

* `[Add]` ImageViewUtils#setImageLevel

* `[Add]` VersionUtils#isEclair、isKitkat_Watch、isLollipop_MR1、convertSDKVersionName

* `[Refactor]` 重构 QuickHelper、ViewHelper、VersionHelper

* `[Delete]` DevHelper

Version 2.2.8 *(2021-06-28)*
----------------------------

* `[Delete]` AnalysisRecordUtils

* `[Refactor]` 重构 FileRecordUtils 并进行内部类拆分便于 DevApp 模块使用

* `[Update]` MediaStoreUtils createImageUri、createVideoUri、createAudioUri、createDownloadUri 方法参数位置

Version 2.2.7 *(2021-06-21)*
----------------------------

* `[Delete]` AppCommonUtils 使用 VersionUtils

* `[Add]` MediaStoreUtils#notifyMediaStore、createDownloadUri、createUriByPath、createUriByFile

* `[Add]` VersionHelper#createDownloadUri、createUriByPath、createUriByFile

* `[Add]` BarUtils#getStatusBarHeight2、ScreenUtils#getStatusBarHeight2

* `[Add]` AppUtils#getCurrentWindowMetrics、getMaximumWindowMetrics

* `[Add]` UriUtils#fromFile

* `[Add]` StringUtils#urlDecodeWhile

* `[Add]` HttpParamsUtils#getUrlParams、getUrlParamsArray、existsParams、existsParamsByURL、joinUrlParams、getUrlParamsJoinSymbol、splitParamsByUrl

Version 2.2.6 *(2021-06-04)*
----------------------------

* `[Add]` UriUtils#getUriForString、isUri、getUriScheme

* `[Add]` DevFinal 新增部分常量

Version 2.2.5 *(2021-05-19)*
----------------------------

* `[Add]` ColorUtils#sortHUE、sortSaturation、sortBrightness

Version 2.2.4 *(2021-05-09)*
----------------------------

* `[sync]` sync to Maven Central

Version 2.2.3 *(2021-03-27)*
----------------------------

* `[Refactor]` DevCache

* `[Add]` ViewUtils#getWidthHeightExact、getWidthHeightExact2

Version 2.2.2 *(2021-03-21)*
----------------------------

* `[Add]` ColorUtils#blendColor、transitionColor

Version 2.2.1 *(2021-03-17)*
----------------------------

* `[Refactor]` TimerManager、DevTimer 简化使用复杂性并进行拆分类

Version 2.2.0 *(2021-03-16)*
----------------------------

* `[Add]` IPreference、PreferenceImpl 新增 Double 类型存储、默认值传参 ( 配合 DevAssis#StorageEngine )

Version 2.1.9 *(2021-03-02)*
----------------------------

* `[Refactor]` BigDecimalUtils

Version 2.1.8 *(2021-03-01)*
----------------------------

* `[Refactor]` NotificationUtils 新增 Params、Callback

* `[Add]` DeviceUtils#isDevelopmentSettingsEnabled

Version 2.1.7 *(2021-02-27)*
----------------------------

* `[Add]` NumberUtils#subZeroAndDot

* `[Refactor]` BigDecimalUtils

* `[Add]` DevFinal 新增部分常量

* `[Add]` ClickUtils#OnMultiClickListener

Version 2.1.6 *(2021-02-08)*
----------------------------

* `[Add]` ColorUtils#getRandomColorString 方法

* `[Add]` DevFinal 新增部分常量

* `[Add]` DevicePolicyUtils 设备管理工具类

Version 2.1.5 *(2021-01-24)*
----------------------------

* `[Perf]` 进行代码检测优化

* `[Add]` DevFinal 新增部分常量

* `[Add]` VersionUtils

* `[Add]` DeviceUtils#getAppDeviceInfo、refreshAppDeviceInfo、getUUID、getUUIDDevice 方法

* `[Delete]` AppCommonUtils#getAppDeviceInfo、refreshAppDeviceInfo、getUUID、getUUIDDevice、getFormatRes 方法

Version 2.1.4 *(2021-01-01)*
----------------------------

* `[Style]` 代码格式化处理 ( 间距美化调整等 )

* `[Add]` 移除 MissingPermission 重新补回权限要求 RequiresPermission 注解

Version 2.1.3 *(2020-12-28)*
----------------------------

* `[Add]` DelayAssist 延迟触发辅助类

* `[Add]` WallpaperUtils 壁纸工具类

* `[Update]` KeyBoardUtils 移除 close/openKeyboard Handler 参数方法修改为 close/openKeyboardDelay

Version 2.1.2 *(2020-12-21)*
----------------------------

* `[Add]` MediaStoreUtils#insertMedia Bitmap、Drawable、File、Stream 参数方法

* `[Add]` VersionHelper#insertMedia Bitmap、Drawable、File、Stream 参数方法

Version 2.1.1 *(2020-12-10)*
----------------------------

* `[Style]` 代码格式化处理 ( 间距美化调整等 )

* `[Update]` 修改 CallBack 相关代码为 Callback

* `[Add]` DevFinal 新增部分常量

* `[Delete]` PermissionConstants 类

* `[Add]` ViewUtils#getChildAtLast、getId、setId

Version 2.1.0 *(2020-11-15)*
----------------------------

* `[Refactor]` 使用 QAPlugs ( PMD、findbugs、checkstyle )、IDEA Analyze 进行代码质量分析、代码优化等

Version 2.0.9 *(2020-11-05)*
----------------------------

* `[Refactor]` 使用 QAPlugs ( PMD、findbugs、checkstyle )、IDEA Analyze 进行代码质量分析、代码优化等

Version 2.0.8 *(2020-10-29)*
----------------------------

* `[Feature]` 适配 Android 11 ( R )

* `[Add]` VersionHelper ( Android 版本适配 Helper 类 )，方便快捷使用并简化需多工具类组合使用的功能

* `[Add]` MediaStoreUtils#createWriteRequest、createFavoriteRequest、createTrashRequest、createDeleteRequest

* `[Add]` PathUtils#isExternalStorageManager、checkExternalStorageAndIntentSetting

* `[Add]` AppUtils#startIntentSenderForResult

* `[Add]` IntentUtils#getManageAppAllFilesAccessPermissionIntent、getManageAllFilesAccessPermissionIntent、getImageCaptureIntent、getVideoCaptureIntent

* `[Add]` ArrayUtils#asListArgs

* `[Update]` MapUtils#putToList、removeToList、removeToMap 参数类型

* `[Update]` 进行 Spelling typo Analyze 修改部分拼写错误字段

Version 2.0.7 *(2020-10-20)*
----------------------------

* `[Update]` StringUtils#convertHideMobile、convertSymbolHide Method Name

* `[Fix]` StringUtils#replaceSEWith、clearSEWiths、clearEndsWith 索引判断问题

Version 2.0.6 *(2020-10-12)*
----------------------------

* `[Add]` ScreenshotUtils 截图监听工具类

* `[Add]` FilePartUtils 文件分片工具类

* `[Add]` BitmapUtils#getVideoThumbnail

* `[Add]` DevHelper#flush、flushQuietly、flushCloseIO、flushCloseIOQuietly

* `[Add]` CloseUtils#flush、flushQuietly、flushCloseIO、flushCloseIOQuietly

* `[Add]` FileUtils#convertFiles、convertPaths

* `[Refactor]` 修改整个库 Closeable Close 代码内部调用 CloseUtils

* `[Update]` SpannableStringUtils 修改为 SpanUtils

Version 2.0.5 *(2020-09-30)*
----------------------------

* `[Add]` ResourceAssist ( Resources 辅助类 )

* `[Add]` ResourcePluginUtils ( 从 APK 中读取 Resources 可实现换肤等功能 )

* `[Update]` 修改部分方法 obtain 为 get、newCache ( DevCache )

* `[Refactor]` 整合 DevApp Utils 代码，统一通过 ResourceAssist 辅助类进行 Resources 获取、适配控制等

Version 2.0.4 *(2020-09-27)*
----------------------------

* `[Add]` DateUtils#getZodiac、getConstellation、getConstellationDate 获取生肖、星座方法

* `[Add]` CalendarUtils 日历 ( 公历、农历 ) 工具类

Version 2.0.3 *(2020-09-20)*
----------------------------

* `[Fix]` NotificationUtils#createNotification 方法新增适配处理

* `[Fix]` PermissionUtils 内存泄露问题

* `[Delete]` DevCommonUtils 中其他工具类快捷方法

* `[Update]` 更新部分代码注释

Version 2.0.2 *(2020-09-15)*
----------------------------

* `[Add]` AnimationUtils#cancelAnimation

* `[Add]` KeyBoardUtils#setSoftInputMode

* `[Add]` HandlerUtils#isMainThread

* `[Add]` HandlerUtils 新增 Key Runnable Map 方便通过 Key 快捷控制 Runnable，进行 postDelayed、removeCallbacks

* `[Add]` StringUtils#clearTab、clearTabTrim、clearLine、clearLineTrim、clearSpaceTabLine、clearSpaceTabLineTrim

* `[Add]` ScaleUtils#XY type 扩展字段

* `[Add]` NumberUtils#addZero

* `[Update]` DateUtils#convertTime 为 timeAddZero

* `[Update]` 去除部分方法名 to 前缀

Version 2.0.1 *(2020-09-07)*
----------------------------

* `[Add]` ScaleUtils#calcScale、calcScaleToMath、calcXY

* `[Add]` StringUtils#forJoint

* `[Add]` AppCommonUtils#isR

* `[Add]` SnackbarUtils#getSnackbarLayout、getSnackbarContentLayout

* `[Add]` ClickUtils#setCheckViewId 方法，OnDebouncingClickListener、OnCountClickListener 事件

* `[Fix]` 修复部分 LayoutParams 操作，未 setLayoutParams 处理

Version 2.0.0 *(2020-08-29)*
----------------------------

* `[Add]` FileUtils#canRead、canWrite、canReadWrite

Version 1.9.9 *(2020-08-27)*
----------------------------

* `[Fix]` UriUtils#getFilePathByUri 方法新增 Android 10 ( Q ) 适配操作

* `[Add]` ContentResolverUtils#getDisplayNameColumn

* `[Add]` UriUtils#copyByUri

Version 1.9.8 *(2020-08-23)*
----------------------------

* `[Update]` ShapeUtils

* `[Add]` ViewHelper#quickHelper、QuickHelper#quickHelper、DevHelper#quickHelper

* `[Add]` IntentUtils#getCategoryLauncherIntent

Version 1.9.7 *(2020-08-04)*
----------------------------

* `[Add]` ChineseUtils 中文工具类

* `[Add]` QuickHelper 简化链式设置 View Helper 类

* `[Add]` StringUtils#forString

* `[Delete]` PhoneUtils 双卡模块代码

* `[Delete]` AsyncExecutor 异步执行辅助类

Version 1.9.6 *(2020-07-19)*
----------------------------

* `[Add]` ViewHelper#setPaintFlags、setAntiAliasFlag

* `[Add]` ViewUtils#setPaintFlags、setAntiAliasFlag

* `[Add]` DevLogger#setPrint、LogPrintUtils#setPrint 自定义输出接口

* `[Update]` ClickUtils 变量声明顺序

Version 1.9.5 *(2020-06-08)*
----------------------------

* `[Update]` PermissionUtils#isGranted

* `[Add]` ResourceUtils#getDimensionInt

Version 1.9.4 *(2020-05-18)*
----------------------------

* `[Update]` MediaStoreUtils#notifyMediaStore 通知刷新本地资源方法版本处理

* `[Update]` ClassUtils#getGenericSuperclass、getGenericInterfaces 返回类型

Version 1.9.3 *(2020-04-25)*
----------------------------

* `[Add]` LanguageUtils#isEn、isZh、isZhCN、isZhTW、isLanguage、isRegion、getSystemCountry

* `[Add]` DeviceUtils#isTablet

* `[Add]` ScreenUtils#isFullScreen、setFullScreenNoTitle

* `[Update]` BarUtils

* `[Delete]` OSUtils

* `[Add]` ROMUtils

Version 1.9.2 *(2020-03-19)*
----------------------------

* `[Add]` WidgetUtils 控件工具类

* `[Add]` ViewUtils#getClipChildren、setClipChildren、getContentView、getRootParent

* `[Add]` ViewHelper#setClipChildren

* `[Add]` TextViewUtils#reckonTextSizeByWidth

* `[Update]` TextViewUtils#reckonTextSize 方法名为 TextViewUtils#reckonTextSizeByHeight

Version 1.9.1 *(2020-03-11)*
----------------------------

* `[Add]` ViewUtils#isShown、isShowns

* `[Add]` StringUtils#split

* `[Add]` NumberUtils#calculateUnit

* `[Update]` StringUtils#replaceStr、replaceStrToNull 方法名为 StringUtils#replaceAll、replaceAllToNull

* `[Update]` TimerManager#startTimer、closeTimer 返回值为 AbsTimer

Version 1.9.0 *(2020-02-21)*
----------------------------

* `[Add]` StringUtils#getBytes

* `[Add]` FileIOUtils#getFileInputStream、getFileOutputStream

* `[Update]` FileUtils#saveFile、appendFile

* `[Update]` FileRecordUtils、AnalysisRecordUtils 关联引用 saveFile、appendFile 方法处理

Version 1.8.9 *(2020-01-26)*
----------------------------

* `[Add]` TypeUtils 类型工具类

* `[Add]` ClassUtils#getClass、isGenericParamType、getGenericParamType

* `[Add]` ConvertUtils#toBigDecimal、toBigInteger、newString、charAt

* `[Update]` ConvertUtils#toString、toInt、toBoolean、toFloat、toDouble、toLong、toShort、toChar、toByte、toChars、toBytes

Version 1.8.8 *(2020-01-16)*
----------------------------

* `[Add]` BitmapUtils#calculateQuality 计算最佳压缩质量值方法

* `[Add]` FileUtils#listFilesInDirBean、listFilesInDirWithFilterBean 方法，获取文件目录列表集合 FileList

* `[Fix]` 修复 AppUtils#isInstalledApp 判断是否安装错误情况

* `[Update]` 兼容 Android P 获取 versionCode 处理 ( getLongVersionCode )

Version 1.8.7 *(2020-01-07)*
----------------------------

* `[Update]` PermissionUtils#shouldShowRequestPermissionRationale 方法，增加可变数组权限传入

* `[Add]` PermissionUtils#getDeniedPermissionStatus 获取拒绝权限询问状态集合方法、PermissionUtils#againRequest 处理拒绝权限操作方法

* `[Update]` ActivityUtils#appExit 为 exitApplication

* `[Add]` ActivityUtils#startActivityForResult 跳转方法，支持通过接口回调方式通知

Version 1.8.6 *(2019-12-25)*
----------------------------

* `[Feature]` 适配 Android 10 ( Q ) 并重构 PathUtils 工具类，提供适配思路以及增加 MediaStoreUtils 多媒体工具类用于外部存储适配操作

* `[Add]` ViewUtils#toggleFocusable、toggleSelected、toggleEnabled、toggleClickable、toggleLongClickable、getChilds

* `[Add]` AppCommonUtils#getUUIDDevice、NotificationUtils#checkAndIntentSetting、isNotificationListenerEnabled、startNotificationListenSettings

* `[Add]` UriUtils#isUriExists、IntentUtils#getLaunchAppNotificationListenSettingsIntent、getOpenBrowserIntent、getCreateDocumentIntent、getOpenDocumentIntent

* `[Add]` CrashUtils UncaughtException 处理工具类、MediaStoreUtils 多媒体工具类

* `[Change]` 移动 ImageViewUtils 部分方法到 ViewUtils、更新 ContentResolverUtils 工具类代码，拆分到 UriUtils、MediaStoreUtils

* `[Add]` ColorUtils#getARGB、grayLevel、sortGray、sortHSB 并增加内部类 ColorInfo，支持颜色排序

* `[Add]` FileIOUtils#copyLarge、DateUtils#yyyyMMdd_HHmmss、CoordinateUtils#getDistance、getAngle、getDirection

* `[Add]` DevCommonUtils、StringUtils#appendsIgnoreLast

* `[Update]` DevCommonUtils、StringUtils 几个重载方法 appends

* `[Update]` 更新部分工具类、方法注释代码、代码间距等

Version 1.8.5 *(2019-11-25)*
----------------------------

* `[Refactor]` 重构整个项目，优化代码逻辑判断、代码风格、合并工具类减少包大小等，并修改 95% 返回值 void 的方法为 boolean 明确获取调用结果

* `[Add]` JSONObjectUtils#isJSONObject、isJSONArray、jsonToMap、jsonToList、getJSONObject、getJSONArray、get、opt

* `[Add]` AppCommonUtils#getAppDeviceInfo、refreshAppDeviceInfo

* `[Add]` AnalysisRecordUtils、FileRecordUtils 文件记录结果回调

* `[Add]` BigDecimalUtils#setScale、setRoundingMode、getBigDecimal、toString、toPlainString、toEngineeringString

* `[Add]` ClassUtils#getClass、isPrimitive、isMap

* `[Add]` MapUtils、CollectionUtils 获取泛型数组 toArrayT

* `[Update]` 移动 FileRecordUtils、HtmlUtils 到 Java 模块

Version 1.8.4 *(2019-11-05)*
----------------------------

* `[Add]` FileUtils#isImageFormats、isAudioFormats、isVideoFormats、isFileFormats

* `[Add]` ViewUtils#getWidthHeight、getNextFocusUpId、getNextFocusRightId、getNextFocusLeftId、getNextFocusDownId、getNextFocusForwardId、isScrollContainer、getChildCount、getRotation、getRotationX、getRotationY、getScaleX、getScaleY、getTextAlignment、getTextDirection、getPivotX、getPivotY、getTranslationX、getTranslationY、getLayerType、isFocusable、isSelected、isEnabled、isClickable、isLongClickable、findFocus、isFocused、hasFocus、hasFocusable、isFocusableInTouchMode、setFocusableInTouchMode、scrollTo、scrollBy、setScrollX、setScrollY、getScrollX、getScrollY、isHorizontalScrollBarEnabled、setHorizontalScrollBarEnabled、isVerticalScrollBarEnabled、setVerticalScrollBarEnabled、setDescendantFocusability、setOverScrollMode

* `[Add]` TextViewUtils#getTypeface、getLetterSpacing、getLineSpacingExtra、getLineSpacingMultiplier、getTextScaleX、getIncludeFontPadding、getInputType、getImeOptions、getMaxLines、getMinLines、getMaxEms、getMinEms、getEllipsize、getAutoLinkMask、getGravity、clearFocus、requestFocus、requestLayout、getTransformationMethod、setTransformationMethod

* `[Add]` EditTextUtils#isCursorVisible、getInputType、getImeOptions、getTransformationMethod、setTransformationMethod

* `[Add]` AnimationUtils#setAnimationListener

* `[Add]` ListViewUtils - 列表 View 相关工具类 ( 支持快捷滑动到指定索引、指定 x、y 轴坐标、回到顶部、底部等 )

* `[Add]` DevHelper、ViewHelper 快捷链式调用 Helper 类

Version 1.8.3 *(2019-10-31)*
----------------------------

* `[Add]` ArrayUtils#getMinimum、getMaximum、getMinimumIndex、getMaximumIndex、sumarray

* `[Add]` CollectionUtils#getMinimum、getMaximum、、getMinimumIndex、getMaximumIndex、sumlist

* `[Add]` AnimationUtils#setAnimation、getAnimation、clearAnimation、startAnimation、cancel

* `[Add]` ViewUtils#setAnimation、getAnimation、clearAnimation、startAnimation、cancel、measureView、setWidthHeight、setWidth、setHeight、addRule、removeRule、getRule、addRules、removeRules

* `[Add]` AppUtils#startActivity、startActivityForResult

* `[Add]` IntentUtils#getLaunchAppInstallPermissionSettingsIntent、getLaunchAppNotificationSettingsIntent

* `[Add]` PermissionUtils#canRequestPackageInstalls

* `[Add]` NotificationUtils#isNotificationEnabled

* `[Add]` CapturePictureUtils 截图工具类 ( 支持 View、Activity、FrameLayout、RelativeLayout、LinearLayout、ListView、GridView、ScrollView、HorizontalScrollView、NestedScrollView、WebView、RecyclerView(GridLayoutManager、LinearLayoutManager、StaggeredGridLayoutManager) )

Version 1.8.2 *(2019-10-18)*
----------------------------

* `[Add]` TextViewUtils#setMinLines、setMaxEms、setMinEms、setEms、setMaxLength、setMaxLengthAndText、setInputType、setImeOptions

* `[Add]` EditTextUtils#setInputType、setImeOptions

* `[Add]` JSONObjectUtils#isJSON

* `[Add]` ViewUtils#setLayerType、setAllCaps、setAlpha、getAlpha、setScrollContainer、setNextFocusForwardId、setNextFocusDownId、setNextFocusLeftId、setNextFocusRightId、setNextFocusUpId、setRotation、setRotationX、setRotationY、setScaleX、setScaleY、setTextAlignment、setTextDirection、setPivotX、setPivotY、setTranslationX、setTranslationY

Version 1.8.1 *(2019-10-13)*
----------------------------

* `[Add]` EditTextUtils#addTextChangedListener、removeTextChangedListener、setTexts

* `[Add]` TextViewUtils#getHint、getHints、getHintTextColors、setHintTextColor、setHintTextColors、getTextColors、setTextColor、setTextColors、setGravity、setHint、setAutoLinkMask、setEllipsize、setMaxLines、setLines

* `[Add]` ViewUtils#getMinimumHeight、setMinimumHeight、getMinimumWidth、setMinimumWidth

* `[Add]` ImageViewUtils#getAdjustViewBounds、setAdjustViewBounds、getMaxHeight、setMaxHeight、getMaxWidth、setMaxWidth

Version 1.8.0 *(2019-10-09)*
----------------------------

* `[Update]` TextViewUtils#calcTextWidth 使用二分法优化处理

* `[Add]` TextViewUtils#calcTextLine、TextViewUtils#getPaint、TextViewUtils#getTextWidth

* `[Add]` DialogUtils#dismiss(DialogFragment)

* `[Add]` ViewUtils#inflate

* `[Add]` NumberUtils#getMultiple、getMultipleI、getMultipleD、getMultipleL、getMultipleF

Version 1.7.9 *(2019-09-19)*
----------------------------

* `[Update]` compileSdkVersion 29 Android 10 ( Q )

* `[Update]` AppCommonUtils#convertSDKVersion

* `[Update]` ImageUtils#getImageType、ImageUtils#isImage modify to isImageFormats

* `[Update]` 修改部分方法 void 返回值 ( 返回当前对象，方便链式调用 )

* `[Add]` AppCommonUtils#isQ

* `[Add]` BitmapUtils#isImage

* `[Add]` ListenerUtils#setOnLongClicks

* `[Add]` ImageUtils#isICO、ImageUtils#isTIFF

* `[Add]` ViewUtils#getTag、setTag

Version 1.7.8 *(2019-09-12)*
----------------------------

* `[Add]` ImageViewUtils#setBackgroundResources

* `[Add]` ViewUtils#getParent

* `[Add]` ConvertUtils#convert

* `[Fix]` DialogUtils#showDialog、closeDialog try catch

Version 1.7.7 *(2019-08-25)*
----------------------------

* `[New]` Support for AndroidX

* `[Add]` DevCommonUtils#subSetSymbol

* `[Add]` ScreenUtils#setWindowSecure

* `[Add]` ViewUtils#set/getMargin、set/getPadding，ViewUtils#set/getLayoutParams

* `[Add]` AndroidManifest.xml FileProvider config

* `[Update]` Update AppUtils、IntentUtils、UriUtils 使用 FileProvider authority 处理

* `[Fix]` Reflect2Utils#getDeclaredFieldParent fieldNumber param 判断处理

Version 1.7.6 *(2019-08-02)*
----------------------------

* `[New]` SpannableStringUtils

* `[Add]` ViewUtils#set/getCompoundDrawables、set/getCompoundDrawablePadding

* `[Add]` ImageUtils#setBounds

Version 1.0.0 ~ 1.7.5 *(2019-07-26)*
----------------------------

* 整个工具类 review code，并规范代码风格、检测注释、代码间距等
