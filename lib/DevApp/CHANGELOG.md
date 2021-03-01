Change Log
==========

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

* `[Add]` 新增 DevicePolicyUtils 设备管理工具类

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

* `[Delete]` 删除 PermissionConstants 类

* `[Add]` ViewUtils#getChildAtLast、getId、setId

Version 2.1.0 *(2020-11-15)*
----------------------------

* `[Refactor]` 使用 QAPlugs ( PMD、findbugs、checkstyle )、IDEA Analyze 进行代码质量分析、代码优化等

Version 2.0.9 *(2020-11-05)*
----------------------------

* `[Refactor]` 使用 QAPlugs ( PMD、findbugs、checkstyle )、IDEA Analyze 进行代码质量分析、代码优化等

Version 2.0.8 *(2020-10-29)*
----------------------------

* `[Feature]` 适配 Android R ( Android 11 )

* `[Add]` 新增 VersionHelper ( Android 版本适配 Helper 类 ), 方便快捷使用并简化需多工具类组合使用的功能

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

* `[Add]` 新增 ResourceAssist ( Resources 辅助类 )

* `[Add]` 新增 ResourcePluginUtils ( 从 APK 中读取 Resources 可实现换肤等功能 )

* `[Update]` 修改部分方法 obtain 为 get、newCache ( DevCache )

* `[Refactor]` 整合 DevApp Utils 代码, 统一通过 ResourceAssist 辅助类进行 Resources 获取、适配控制等

Version 2.0.4 *(2020-09-27)*
----------------------------

* `[Add]` DateUtils#getZodiac、getConstellation、getConstellationDate 获取生肖、星座方法

* `[Add]` CalendarUtils 日历 ( 公历、农历 ) 工具类

Version 2.0.3 *(2020-09-20)*
----------------------------

* `[Fix]` NotificationUtils#createNotification 方法新增适配处理

* `[Fix]` PermissionUtils 内存泄露问题

* `[Delete]` 删除 DevCommonUtils 中其他工具类快捷方法

* `[Update]` 更新部分代码注释

Version 2.0.2 *(2020-09-15)*
----------------------------

* `[Add]` AnimationUtils#cancelAnimation

* `[Add]` KeyBoardUtils#setSoftInputMode

* `[Add]` HandlerUtils#isMainThread

* `[Add]` HandlerUtils 新增 Key Runnable Map 方便通过 Key 快捷控制 Runnable, 进行 postDelayed、removeCallbacks

* `[Add]` StringUtils#clearTab、clearTabTrim、clearLine、clearLineTrim、clearSpaceTabLine、clearSpaceTabLineTrim

* `[Add]` ScaleUtils#XY type 拓展字段

* `[Add]` NumberUtils#addZero

* `[Update]` DateUtils#convertTime 为 timeAddZero

* `[Update]` 去除部分方法名 to 前缀

Version 2.0.1 *(2020-09-07)*
----------------------------

* `[Add]` ScaleUtils#calcScale、calcScaleToMath、calcXY

* `[Add]` StringUtils#forJoint

* `[Add]` AppCommonUtils#isR

* `[Add]` SnackbarUtils#getSnackbarLayout、getSnackbarContentLayout

* `[Add]` ClickUtils#setCheckViewId 方法, OnDebouncingClickListener、OnCountClickListener 事件

* `[Fix]` 修复部分 LayoutParams 操作, 未 setLayoutParams 处理

Version 2.0.0 *(2020-08-29)*
----------------------------

* `[Add]` FileUtils#canRead、canWrite、canReadWrite

Version 1.9.9 *(2020-08-27)*
----------------------------

* `[Fix]` UriUtils#getFilePathByUri 方法新增 Android Q 适配操作

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

* `[Add]` FileUtils#listFilesInDirBean、listFilesInDirWithFilterBean 方法, 获取文件目录列表集合 FileList

* `[Fix]` 修复 AppUtils#isInstalledApp 判断是否安装错误情况

* `[Update]` 兼容 Android P 获取 versionCode 处理 ( getLongVersionCode )

Version 1.8.7 *(2020-01-07)*
----------------------------

* `[Update]` PermissionUtils#shouldShowRequestPermissionRationale 方法, 增加可变数组权限传入

* `[Add]` PermissionUtils#getDeniedPermissionStatus 获取拒绝权限询问状态集合方法、PermissionUtils#againRequest 处理拒绝权限操作方法

* `[Update]` ActivityUtils#appExit 为 exitApplication

* `[Add]` ActivityUtils#startActivityForResult 跳转方法, 支持通过接口回调方式通知

Version 1.8.6 *(2019-12-25)*
----------------------------

* `[Feature]` 适配 Android Q 并重构 PathUtils 工具类, 提供适配思路以及增加 MediaStoreUtils 多媒体工具类用于外部存储适配操作

* `[Add]` ViewUtils#toggleFocusable、toggleSelected、toggleEnabled、toggleClickable、toggleLongClickable、getChilds

* `[Add]` AppCommonUtils#getUUIDDevice、NotificationUtils#checkAndIntentSetting、isNotificationListenerEnabled、startNotificationListenSettings

* `[Add]` UriUtils#isUriExists、IntentUtils#getLaunchAppNotificationListenSettingsIntent、getOpenBrowserIntent、getCreateDocumentIntent、getOpenDocumentIntent

* `[Add]` CrashUtils UncaughtException 处理工具类、MediaStoreUtils 多媒体工具类

* `[Change]` 移动 ImageViewUtils 部分方法到 ViewUtils、更新 ContentResolverUtils 工具类代码, 拆分到 UriUtils、MediaStoreUtils

* `[Add]` ColorUtils#getARGB、grayLevel、sortGray、sortHSB 并增加内部类 ColorInfo, 支持颜色排序

* `[Add]` FileIOUtils#copyLarge、DateUtils#yyyyMMdd_HHmmss、CoordinateUtils#getDistance、getAngle、getDirection

* `[Add]` DevCommonUtils、StringUtils#appendsIgnoreLast

* `[Update]` 删除 DevCommonUtils、StringUtils 几个重载方法 appends

* `[Update]` 更新部分工具类、方法注释代码、代码间距等

Version 1.8.5 *(2019-11-25)*
----------------------------

* `[Refactor]` 重构整个项目, 优化代码逻辑判断、代码风格、合并工具类减少包大小等, 并修改 95% 返回值 void 的方法为 boolean 明确获取调用结果
 
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
 
 * `[Update]` compileSdkVersion 29 Android Q
 
 * `[Update]` AppCommonUtils#convertSDKVersion
 
 * `[Update]` ImageUtils#getImageType、ImageUtils#isImage modify to isImageFormats
 
 * `[Update]` 修改部分方法 void 返回值 ( 返回当前对象, 方便链式调用 )
 
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
 
 * `[Add]` ViewUtils#set/getMargin、set/getPadding, ViewUtils#set/getLayoutParams
 
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

 * 整个工具类 review code, 并规范代码风格、检测注释、代码间距等
