## 使用

> ##### 只需要在 Application 中调用 `DevUtils.init()` 进行初始化就行

## 目录结构

```
- dev.utils             | 根目录
   - app                | app相关工具类
      - anim            | 动画相关
      - assist          | 辅助类, 如 Camera，ScreenSensor
      - cache           | 缓存工具类
      - image           | 图片相关处理
      - info            | App信息, PackageInfo 等
      - logger          | 日志库 DevLogger
      - share           | SharedPreferences 封装
      - toast           | Toast、Toasty
      - wifi            | wifi、热点
   - common             | java工具类, 不依赖android api
      - assist          | 各种快捷辅助类
      - cipher          | 编/解码工具类
      - encrypt         | 加密工具类
      - thread          | 线程相关
      - validator       | 数据校验工具类
```

## 事项

- 内部存在两个日志工具类
```java
// dev.utils.app - App 打印日志工具类
LogPrintUtils
// dev.utils.common - Java Common 日志打印工具类
JCLogUtils
```

- 需要开启日志, 单独调用
```java
// 打开 lib 内部日志
DevUtils.openLog();
// 标示 debug 模式
DevUtils.openDebug();
```

- 部分api更新不及时或有遗漏等，`具体以对应的工具类为准`

## API

### `dev.utils.app.wifi`

* **Wifi 热点工具类(兼容到Android 8.0) ->** [WifiHotUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/wifi/WifiHotUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| createWifiConfigToAp | 创建Wifi配置信息（无其他操作，单独返回WifiConfig） |
| stratWifiAp | 开启Wifi热点 |
| closeWifiAp | 关闭Wifi热点 |
| getWifiApState | 获取Wifi热点状态 |
| getWifiApConfiguration | 获取Wifi热点配置信息 |
| setWifiApConfiguration | 设置Wifi热点配置信息 |
| isOpenWifiAp | 判断是否打开Wifi热点 |
| closeWifiApCheck | 关闭Wifi热点(判断当前状态) |
| isConnectHot | 是否有连接热点的设备 |
| getHotspotServiceIp | 获取热点主机ip地址 |
| getHotspotAllotIp | 获取连接上的子网关热点IP(一个) |
| getHotspotSplitIpMask | 获取热点拼接后的ip网关掩码 |
| intToString | 转换ip地址 |
| getApWifiSSID | 获取Wifi 热点名 |
| getApWifiPwd | 获取Wifi 热点密码 |
| setOnWifiAPListener | 设置Android Wifi监听(Android 8.0) |


* **wifi工具类 ->** [WifiHotUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/wifi/WifiUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| getWifiManager | 获取wifi管理对象 |
| isOpenWifi | 判断是否打开wifi |
| openWifi | 打开WIFI |
| closeWifi | 关闭WIFI |
| toggleWifiEnabled | 自动切换wifi开关状态 |
| getWifiState | 获取当前WIFI连接状态 |
| startScan | 开始扫描wifi |
| getConfiguration | 获取已配置的网络 |
| getWifiList | 获取wifi网络列表 |
| getWifiInfo | 获取WifiInfo对象 |
| getMacAddress | 获取MAC地址 |
| getBSSID | 获取接入点的BSSID |
| getIPAddress | 获取IP地址 |
| getNetworkId | 获取连接的ID |
| getSSID | 获取SSID |
| formatSSID | 判断是否存在\"ssid\"，存在则裁剪返回 |
| getPassword | 获取密码（经过处理） |
| getWifiType | 获取加密类型(int常量) - 判断String |
| getWifiTypeInt | 获取加密类型(int常量) - 判断int(String) |
| getWifiType | 获取加密类型(int常量) |
| getWifiTypeStr | 获取加密类型(String) |
| isConnNull | 判断是否连接为null - <unknown ssid> |
| isConnectAphot | 判断是否连接上Wifi(非连接中) |
| getSecurity | 获取Wifi配置,加密类型 |
| isExsitsPwd | 获知Wifi配置，是否属于密码加密类型 |
| isExsits | 查看以前是否也配置过这个网络 |
| delWifiConfig | 删除指定的 Wifi(SSID) 配置信息 |
| quickConnWifi | 快速连接Wifi(不使用静态ip方式) |
| createWifiConfig | 创建Wifi配置信息（无其他操作，单独返回WifiConfig） |
| removeWifiConfig | 移除某个Wifi配置信息 |
| disconnectWifi | 断开指定ID的网络 |
| setStaticWifiConfig | 设置静态Wifi信息 |
| setDNS | 设置DNS |
| setGateway | 设置网关 |
| setIpAddress | 设置Ip地址 |
| setStaticIpConfig | 设置Ip地址、网关、DNS(5.0之后) |

### `dev.utils.app`

* **无障碍功能工具类 ->** [AccessibilityUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/AccessibilityUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| checkAccessibility | 检查是否开启无障碍功能 |
| isAccessibilitySettingsOn | 判断是否开启无障碍功能 |
| printAccessibilityEvent | 打印Event 日志 |


* **Acitivty 工具类 ->** [ActivityUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/ActivityUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| isActivityExists | 判断是否存在指定的Activity |
| startHomeActivity | 回到桌面 -> 同点击Home键效果 |
| getLauncherActivity | 跳转到桌面 |
| getActivityIcon | 返回Activity 对应的图标 |
| getActivityLogo | 返回Activity 对应的Logo |


* **AlarmManager (全局定时器/闹钟）指定时长或以周期形式执行某项操作 ->** [AlarmUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/AlarmUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| startAlarmIntent | 开启定时器 |
| stopAlarmIntent | 关闭定时器 |
| startAlarmService | 开启轮询服务 |
| stopAlarmService | 停止启轮询服务 |


* **分析记录工具类 ->** [AnalysisRecordUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/AnalysisRecordUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| init | 初始化操作 |
| record | 日志记录 |
| isHandler | 是否处理日志记录 |
| setHandler | 设置是否处理日志记录 |
| getLogFolderName | 获取文件日志名 |
| setLogFolderName | 设置日志文件夹名 |
| getLogStoragePath | 获取日志存储路径 |
| setLogStoragePath | 设置日志存储路径 |
| getStoragePath | getStoragePath |
| getFileName | getFileName |
| getFileFunction | getFileFunction |
| getFileIntervalTime | getFileIntervalTime |
| getFolderName | getFolderName |
| obtain | 获取记录分析文件信息 |
| getLogPath | 获取日志地址 |
| getIntervalTimeFolder | 获取时间间隔 - 文件夹 |


* **App通用工具类 ->** [AppCommonUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/AppCommonUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| getUUID | 获取设备唯一id |
| getRandomUUID | 获取随机数 唯一id |
| isFroyo | 是否在2.2版本及以上 |
| isGingerbread | 是否在2.3版本及以上 |
| isGingerbreadMR1 | 是否在2.3.3版本及以上 |
| isHoneycomb | 是否在3.0版本及以上 |
| isHoneycombMR1 | 是否在3.1版本及以上 |
| isIceCreamSandwich | 是否在4.0版本及以上 |
| isIceCreamSandwichMR1 | 是否在4.0.3版本及以上 |
| isJellyBean | 是否在4.1版本及以上 |
| isKitkat | 是否在4.4.2版本及以上 |
| isLollipop | 是否在5.0.1版本及以上 |
| isM | 是否在6.0版本及以上 |
| isN | 是否在7.0版本及以上 |
| isN_MR1 | 是否在7.1.1版本及以上 |
| isO | 是否在8.0版本及以上 |
| convertSDKVersion | 转换SDK版本 |


* **App（Android 工具类） ->** [AppUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/AppUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| getWindowManager | 通过上下文获取 WindowManager |
| getMetaData | 获取 Manifest Meta Data |
| getView | 获取View |
| getResources | getResources |
| getString | getString |
| getTheme | getTheme |
| getAssets | getAssets |
| getDrawable | getDrawable |
| getColor | getColor |
| getColorStateList | getColorStateList |
| getSystemService | getSystemService |
| getPackageManager | getPackageManager |
| getConfiguration | getConfiguration |
| getDisplayMetrics | getDisplayMetrics |
| getContentResolver | getContentResolver |
| getAppIcon | 获取app的图标 |
| getAppPackageName | 获取app 包名 |
| getAppName | 获取app 名 |
| getAppVersionName | 获取app版本名 - 对外显示 |
| getAppVersionCode | 获取app版本号 - 内部判断 |
| setLanguage | 对内设置指定语言 (app 多语言,单独改变app语言) |
| installApp | 安装 App（支持 8.0）的意图 |
| installAppSilent | 静默安装app |
| uninstallApp | 卸载 App |
| uninstallAppSilent | 静默卸载 App |
| isAppInstalled | 判断是否安装了应用 |
| isInstalledApp | 判断是否安装指定包名的APP |
| isAppRoot | 判断是否存在root 权限 |
| isAppDebug | 判断是否app 是否debug模式 |
| isAppSystem | 判断app 是否系统app |
| isAppForeground | 判断app 是否在前台 |
| launchApp | 打开app |
| launchAppDetailsSettings | 跳转到 专门的APP 设置详情页面 |
| launchAppDetails | 跳转到 专门的APP 应用商城详情页面 |
| getAppPath | 获取app 路径 /data/data/包名/.apk |
| getAppSignature | 获取app 签名 |
| getAppSignatureSHA1 | 获取 app sha1值 |
| openPDFFile | 启动本地应用打开PDF |
| openWordFile | 启动本地应用打开PDF |
| openOfficeByWPS | 调用WPS打开office文档 |


* **状态栏相关工具类 ->** [BarUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/BarUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| getStatusBarHeight | 获取状态栏高度 |
| setStatusBarVisibility | 设置状态栏是否显示 |
| isStatusBarVisible | 判断状态栏是否显示 |
| setStatusBarLightMode | 设置状态是否高亮模式 |
| addMarginTopEqualStatusBarHeight | 添加状态栏同等高度到View的顶部 |
| subtractMarginTopEqualStatusBarHeight | 减去状态栏同等高度 |
| setStatusBarColor | 设置状态栏颜色 |
| setStatusBarAlpha | 设置状态栏透明度 |
| setStatusBarColor4Drawer | 设置状态栏的颜色 |
| setStatusBarAlpha4Drawer | 设置状态栏透明度 |
| getActionBarHeight | 返回 ActionBase 高度 |
| setNotificationBarVisibility | 设置通知栏是否显示 |
| getNavBarHeight | 获取 NavigationView 高度 |
| setNavBarVisibility | 设置导航栏是否可见(图标显示) |
| setNavBarImmersive | 设置沉浸模式 |
| isNavBarVisible | 判断顶部状态栏(图标)是否显示 |
| setNavBar | 设置是否显示状态栏图标等 |


* **亮度相关工具类 ->** [BrightnessUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/BrightnessUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| isAutoBrightnessEnabled | 判断是否开启自动调节亮度 |
| setAutoBrightnessEnabled | 设置是否开启自动调节亮度 |
| getBrightness | 获取屏幕亮度 |
| setBrightness | 设置屏幕亮度 |
| setWindowBrightness | 设置窗口亮度 |
| getWindowBrightness | 获取窗口亮度 |


* **摄像头相关工具类 ->** [CameraUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/CameraUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| isSupportReverse | 判断是否支持反转摄像头(是否存在前置摄像头) |
| checkCameraFacing | 检查是否有摄像头 |
| isFrontCamera | 判断是否使用前置摄像头 |
| isBackCamera | 判断是否使用后置摄像头 |
| isUseCameraFacing | 判断使用的视像头 |
| freeCameraResource | 释放摄像头资源 |
| initCamera | 初始化摄像头 |
| open | 打开摄像头 |


* ** ->** [CleanUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/CleanUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| cleanInternalCache | 清除本应用内部缓存(/data/data/com.xxx.xxx/cache) |
| cleanDatabases | 清除本应用所有数据库(/data/data/com.xxx.xxx/databases) |
| cleanSharedPreference | 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs) |
| cleanDatabaseByName | 按名字清除本应用数据库 |
| cleanFiles | 清除/data/data/com.xxx.xxx/files下的内容 |
| cleanExternalCache | 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache) |
| cleanCustomCache | 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除 |
| cleanApplicationData | 清除本应用所有的数据 |
| getFolderSize | 获取文件夹大小 |
| getCacheSize | 获取缓存文件大小 |
| getFormatSize | 格式化单位 |


* **点击工具类 ->** [ClickUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/ClickUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| isFastDoubleClick | 判断两次点击的间隔 小于默认间隔时间(1秒), 则认为是多次无效点击 |
| initConfig | 初始化配置信息 |
| putConfig | 添加配置信息 |
| removeConfig | 移除配置信息 |


* **剪贴板相关工具类 ->** [ClipboardUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/ClipboardUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| copyText | 复制文本到剪贴板 |
| getText | 获取剪贴板的文本 |
| copyUri | 复制uri到剪贴板 |
| getUri | 获取剪贴板的uri |
| copyIntent | 复制意图到剪贴板 |
| getIntent | 获取剪贴板的意图 |


* **ContentResolver 工具类 ->** [ContentResolverUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/ContentResolverUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| notifyMediaStore | 通知刷新本地资源 |
| insertImageIntoMediaStore | 添加图片到系统相册(包含原图、相册图, 会存在两张) - 想要一张, 直接调用 notifyMediaStore() |
| insertVideoIntoMediaStore | 添加视频到系统相册 |
| insertIntoMediaStore | 保存到系统相册 |


* **获取CPU信息工具类 ->** [CPUUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/CPUUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| getProcessorsCount | 获取处理器的Java虚拟机的数量 |
| getSysCPUSerialNum | 获取手机CPU序列号 |
| getCpuInfo | 获取CPU 信息 |
| getCpuModel | 获取CPU 型号 |
| getMaxCpuFreq | 获取 CPU 最大频率（单位KHZ） |
| getMinCpuFreq | 获取 CPU 最小频率（单位KHZ） |
| getCurCpuFreq | 实时获取 CPU 当前频率（单位KHZ） |
| getCoresNumbers | 获取 CPU 几核 |
| getCpuName | 获取CPU名字 |
| getCMDOutputString | 获取 CMD 指令回调数据 |


* **数据库工具类 (导入导出等) ->** [DBUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/DBUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| startExportDatabase | 导出数据库 |
| startImportDatabase | 导入数据库 |
| getDBPath | 获取数据库路径 |


* **设备相关工具类 ->** [DeviceUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/DeviceUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| getSysLanguage | 获取当前操作系统的语言 |
| getDeviceInfo | 获取设备信息 |
| getDeviceInfo2 | 获取设备信息 |
| handleDeviceInfo | 处理设备信息 |
| getSDKVersionName | 获取设备系统版本号 |
| getSDKVersion | 获取当前SDK 版本号 |
| getAndroidId | 获取Android id |
| isDeviceRooted | 判断设备是否 root |
| getMacAddress | 获取设备 MAC 地址 |
| getManufacturer | 获取设备厂商 如 Xiaomi |
| getModel | 获取设备型号 如 RedmiNote4X |
| shutdown | 关机 需要 root 权限或者系统权限 <android:sharedUserId="android.uid.system" /> |
| reboot | 重启 需要 root 权限或者系统权限 <android:sharedUserId="android.uid.system" /> |
| reboot2Recovery | 重启到 recovery 需要 root 权限 |
| reboot2Bootloader | 重启到 bootloader 需要 root 权限 |
| getBaseband_Ver | BASEBAND-VER 基带版本 |
| getLinuxCore_Ver | CORE-VER 内核版本 |


* **Dialog 操作相关工具类 ->** [DialogUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/DialogUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| closeDialog | 关闭Dialog |
| closeDialogs | 关闭多个Dialog |
| closePopupWindow | 关闭PopupWindow |
| closePopupWindows | 关闭多个PopupWindow |
| creDialog | 创建加载 Dialog |
| creAutoCloseDialog | 创建自动关闭dialog |


* **EditText 工具类 ->** [EditTextUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/EditTextUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| setMaxLengthAnText | 设置长度限制，并且设置内容 |
| setMaxLength | 设置长度限制 |
| getSelectionStart | 获取光标位置 |
| getText | 获取输入的内容 |
| getTextLength | 获取输入的内容长度 |
| setText | 设置内容 |
| insert | 追加内容 - 当前光标位置追加 |
| setSelectTop | 设置光标在第一位 |
| setSelectBottom | 设置光标在最后一位 |
| setSelect | 设置光标位置 |
| setKeyListener | 设置输入限制 |
| getNumberAndEnglishKeyListener | 限制只能输入字母和数字，默认弹出英文输入法 |
| getNumberKeyListener | 限制只能输入数字，默认弹出数字列表 |
| getMarkId | getMarkId |
| isOperate | isOperate |
| setOperate | setOperate |
| getOperateState | getOperateState |
| setOperateState | setOperateState |
| beforeTextChanged | 在文本变化前调用 |
| onTextChanged | 在文本变化后调用 |
| afterTextChanged | 在文本变化后调用 |


* **编码工具类 ->** [EncodeUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/EncodeUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| urlEncode | url编码 - UTF-8 |
| urlDecode | url 解码 - UTF-8 |
| base64Encode | base64 编码 => 去除\n 替换 + 和 - 号 |
| base64Encode2String | base64 编码 |
| base64Decode | base64 解码 |
| base64DecodeToString | base64 解码 |
| htmlEncode | html编码 |
| htmlDecode | html解码 |


* **错误信息处理工具类 ->** [ErrorUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/ErrorUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| getErrorMsg | 获取错误信息 |
| getThrowableMsg | 将异常栈信息转为字符串 |
| getThrowableNewLinesMsg | 获取错误信息(有换行) |


* **App 文件记录工具类 ->** [FileRecordUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/FileRecordUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| appInit | App初始化调用方法 |
| saveErrorLog | 保存app错误日志 |
| saveLog | 保存app日志 |
| handleVariable | 处理可变参数 |


* **手电筒工具类 ->** [FlashlightUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/FlashlightUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| getInstance | getInstance |
| register | 注册摄像头 |
| unregister | 注销摄像头 |
| setFlashlightOn | 打开闪光灯 |
| setFlashlightOff | 关闭闪光灯 |
| isFlashlightOn | 是否打开闪光灯 |
| isFlashlightEnable | 是否支持手机闪光灯 |


* **Handler 工具类, 默认开启一个 Handler，方便在各个地方随时执行主线程任务 ->** [HandlerUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/HandlerUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| init | 初始化操作 |
| getMainHandler | 获取主线程 Handler |
| postRunnable | 在主线程 Handler 中执行任务 |
| removeRunnable | 在主线程 Handler 中清除任务 |


* **Html 工具类 ->** [HtmlUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/HtmlUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| setHtmlText | 设置内容, 最终做处理 |
| addHtmlColor | 为给定的字符串添加HTML颜色标记 |
| addHtmlBold | 为给定的字符串添加HTML加粗标记 |
| addHtmlColorAndBlod | 为给定的字符串添加HTML颜色标记,以及加粗 |
| addHtmlUnderline | 为给定的字符串添加HTML下划线 |
| addHtmlIncline | 为给定的字符串添加HTML 字体倾斜 |
| keywordMadeRed | 将给定的字符串中所有给定的关键字标色 |
| keywordReplaceAll | 将给定的字符串中所有给定的关键字进行替换内容 |


* **Intent(意图) 相关工具类 ->** [IntentUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/IntentUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| getInstallAppIntent | 获取安装 App（支持 8.0）的意图 |
| getUninstallAppIntent | 获取卸载 App 的意图 |
| getLaunchAppIntent | 获取打开 App 的意图 |
| getLaunchAppDetailsSettingsIntent | 获取 App 具体设置的意图 |
| getlaunchAppDetailIntent | 获取 到应用商店app详情界面的意图 |
| getShareTextIntent | 获取分享文本的意图 |
| getShareImageIntent | 获取分享图片的意图 |
| getComponentIntent | 获取其他应用组件的意图 |
| getShutdownIntent | 获取关机的意图 |
| getDialIntent | 获取跳至拨号界面意图 |
| getCallIntent | 获取拨打电话意图 |
| getSendSmsIntent | 获取发送短信界面的意图 |
| getCaptureIntent | 获取拍照的意图 |
| startSysSetting | 跳转到系统设置页面 |
| openWirelessSettings | 打开网络设置界面 - 3.0以下打开设置界面 |


* **软键盘相关辅助类 ->** [KeyBoardUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/KeyBoardUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| setDelayMillis | 设置延迟时间 |
| openKeyboard | 打开软键盘 |
| closeKeyboard | 关闭软键盘 |
| closeKeyBoardSpecial | 关闭软键盘 - 特殊处理 |
| toggleKeyboard | 自动切换键盘状态，如果键盘显示了则隐藏，隐藏着显示 |
| judgeView | 某个View里面的子View的View判断 |
| isSoftInputVisible | 判断软键盘是否可见 |
| registerSoftInputChangedListener | 注册软键盘改变监听器 |
| registerSoftInputChangedListener2 | 注册软键盘改变监听器 |
| fixSoftInputLeaks | 修复软键盘内存泄漏 在 Activity.onDestroy() 中使用 |
| onSoftInputChanged | onSoftInputChanged |


* **锁屏工具类 - 锁屏管理， 锁屏、禁用锁屏，判断是否锁屏 ->** [KeyguardUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/KeyguardUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| getInstance | 获取 KeyguardUtils 实例 ,单例模式 |
| isKeyguardLocked | 是否锁屏 - android 4.1以上支持 |
| isKeyguardSecure | 是否有锁屏密码 - android 4.1以上支持 |
| inKeyguardRestrictedInputMode | 是否锁屏 - android 4.1 之前 |
| getKeyguardManager | getKeyguardManager |
| setKeyguardManager | setKeyguardManager |
| disableKeyguard | 屏蔽系统的屏保 |
| reenableKeyguard | 使能显示锁屏界面，如果你之前调用了disableKeyguard()方法取消锁屏界面，那么会马上显示锁屏界面。 |
| release | 释放资源 |
| newKeyguardLock | newKeyguardLock |
| getKeyguardLock | getKeyguardLock |
| setKeyguardLock | setKeyguardLock |


* **事件工具类 => AppReflectUtils(可以删除) ->** [ListenerUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/ListenerUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| getTouchListener | 获取 View 设置的 OnTouchListener |
| getListenerInfo | 获取 View ListenerInfo 对象(内部类) |
| getListenerInfoListener | 获取 View ListenerInfo 对象内部事件对象 |
| setOnClicks | 设置点击事件 |


* **定位相关工具类 ->** [LocationUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/LocationUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| getLocation | 获取位置, 需要先判断是否开启了定位 |
| isGpsEnabled | 判断Gps是否可用 |
| isLocationEnabled | 判断定位是否可用 |
| openGpsSettings | 打开Gps设置界面 |
| register | 注册 |
| unregister | 注销 |
| getAddress | 根据经纬度获取地理位置 |
| getCountryName | 根据经纬度获取所在国家 |
| getLocality | 根据经纬度获取所在地 |
| getStreet | 根据经纬度获取所在街道 |
| isBetterLocation | 是否更好的位置 |
| isSameProvider | 是否相同的提供者 |
| getLastKnownLocation | 获取最后一次保留的坐标 |
| onLocationChanged | 当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发 |
| onStatusChanged | provider的在可用、暂时不可用和无服务三个状态直接切换时触发此函数 |


* **Android Manifest工具类 ->** [ManifestUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/ManifestUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| getMetaData | 获取 Manifest Meta Data |
| getAppVersion | 获取app版本信息 |
| getAppVersionCode | 获取app版本号 |
| getAppVersionName | 获取app版本信息 |


* **获取内存信息 ->** [MemoryUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/MemoryUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| getMemoryInfo | 获取内存信息 |
| printMemoryInfo | Print Memory info. |
| getAvailMemory | Get available memory info. |
| getTotalMemory | 获取总内存大小 |
| getMemoryAvailable | 获取可用内存大小 |
| getMemInfoIype | 获取 type info |


* **网络管理工具类 ->** [NetWorkUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/NetWorkUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| getMobileDataEnabled | 获取移动网络打开状态(默认属于未打开) |
| setMobileDataEnabled | 设置移动网络开关(无判断是否已开启移动网络) - 实际无效果, 非系统应用无权限 |
| isConnect | 判断是否连接了网络 |
| getConnectType | 获取连接的网络类型 |
| isConnWifi | 判断是否连接Wifi(连接上、连接中) |
| isConnMobileData | 判断是否连接移动网络(连接上、连接中) |
| isAvailable | 判断网络是否可用 |
| is4G | 判断是否4G网络 |
| getWifiEnabled | 判断wifi是否打开 |
| isWifiAvailable | 判断wifi数据是否可用 |
| getNetworkOperatorName | 获取网络运营商名称 - 中国移动、如中国联通、中国电信 |
| getNetworkType | 获取当前网络类型 |
| getDomainAddress | 获取域名ip地址 |
| getIPAddress | 获取IP地址 |


* **通知栏管理类 ->** [NotificationUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/NotificationUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| getNotificationManager | 获取通知栏管理类 |
| cancelAll | 移除通知 - 移除所有通知(只是针对当前Context下的Notification) |
| cancel | 移除通知 - 移除标记为id的通知 (只是针对当前Context下的所有Notification) |
| notify | 进行通知 |
| crePendingIntent | 获取跳转id |
| creNotification | 创建通知栏对象 |
| obtain | 获取 Led 配置参数 |
| isEmpty | 判断是否为null |


* **工具类: OS 系统相关 ->** [OSUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/OSUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| getRomType | 获取 ROM 类型 |
| getBaseVersion | getBaseVersion |
| getVersion | getVersion |


* **路径相关工具类 ->** [PathUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/PathUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| getRootPath | 获取 Android 系统根目录 path: /system |
| getDataPath | 获取 data 目录 path: /data |
| getIntDownloadCachePath | 获取缓存目录 path: data/cache |
| getAppIntCachePath | 获取此应用的缓存目录 path: /data/data/package/cache |
| getAppIntFilesPath | 获取此应用的文件目录 path: /data/data/package/files |
| getAppIntDbPath | 获取此应用的数据库文件目录 path: /data/data/package/databases/name |
| getExtStoragePath | 获取 Android 外置储存的根目录 path: /storage/emulated/0 |
| getExtAlarmsPath | 获取闹钟铃声目录 path: /storage/emulated/0/Alarms |
| getExtDcimPath | 获取相机拍摄的照片和视频的目录 path: /storage/emulated/0/DCIM |
| getExtDocumentsPath | 获取文档目录 path: /storage/emulated/0/Documents |
| getExtDownloadsPath | 获取下载目录 path: /storage/emulated/0/Download |
| getExtMoviesPath | 获取视频目录 path: /storage/emulated/0/Movies |
| getExtMusicPath | 获取音乐目录 path: /storage/emulated/0/Music |
| getExtNotificationsPath | 获取提示音目录 path: /storage/emulated/0/Notifications |
| getExtPicturesPath | 获取图片目录 path: /storage/emulated/0/Pictures |
| getExtPodcastsPath | 获取 Podcasts 目录 path: /storage/emulated/0/Podcasts |
| getExtRingtonesPath | 获取铃声目录 path: /storage/emulated/0/Ringtones |
| getAppExtCachePath | 获取此应用在外置储存中的缓存目录 path: /storage/emulated/0/Android/data/package/cache |
| getAppExtFilePath | 获取此应用在外置储存中的文件目录 path: /storage/emulated/0/Android/data/package/files |
| getAppExtAlarmsPath | 获取此应用在外置储存中的闹钟铃声目录 path: /storage/emulated/0/Android/data/package/files/Alarms |
| getAppExtDcimPath | 获取此应用在外置储存中的相机目录 path: /storage/emulated/0/Android/data/package/files/DCIM |
| getAppExtDocumentsPath | 获取此应用在外置储存中的文档目录 path: /storage/emulated/0/Android/data/package/files/Documents |
| getAppExtDownloadPath | 获取此应用在外置储存中的闹钟目录 path: /storage/emulated/0/Android/data/package/files/Download |
| getAppExtMoviesPath | 获取此应用在外置储存中的视频目录 path: /storage/emulated/0/Android/data/package/files/Movies |
| getAppExtMusicPath | 获取此应用在外置储存中的音乐目录 path: /storage/emulated/0/Android/data/package/files/Music |
| getAppExtNotificationsPath | 获取此应用在外置储存中的提示音目录 path: /storage/emulated/0/Android/data/package/files/Notifications |
| getAppExtPicturesPath | 获取此应用在外置储存中的图片目录 path: /storage/emulated/0/Android/data/package/files/Pictures |
| getAppExtPodcastsPath | 获取此应用在外置储存中的 Podcasts 目录 path: /storage/emulated/0/Android/data/package/files/Podcasts |
| getAppExtRingtonesPath | 获取此应用在外置储存中的铃声目录 path: /storage/emulated/0/Android/data/package/files/Ringtones |
| getObbPath | 获取此应用的 Obb 目录 path: /storage/emulated/0/Android/obb/package 一般用来存放游戏数据包 |
| getFilePathByUri | getFilePathByUri |


* **权限请求工具类 ->** [PermissionUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/PermissionUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| isGranted | 判断是否授予了权限 |
| shouldShowRequestPermissionRationale | 是否拒绝了权限 - 拒绝过一次, 再次申请时, 弹出选择不再提醒并拒绝才会触发 true |
| permission | 申请权限初始化 |
| callBack | 设置回调方法 |
| request | 请求权限 |
| onRequestPermissionsResult | 请求权限回调 - 需要在 onRequestPermissionsResult 回调里面调用 |
| onGranted | 授权通过权限 |
| onDenied | 授权未通过权限 |
| start | start |
| onCreate | onCreate |


* **手机相关工具类 ->** [PhoneUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/PhoneUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| isSimReady | 判断是否装载sim卡 |
| getUserCountry | 获取Sim卡所属地区，非国内地区暂不支持播放 |
| judgeArea | 判断地区，是否属于国内 |
| isPhone | 判断设备是否是手机 |
| getIMEI | 获取 IMEI 码 |
| getIMSI | 获取 IMSI 码 |
| getIMSIIDName | 获取IMSI处理过后的简称 |
| getPhoneType | 获取移动终端类型 |
| isSimCardReady | 判断 sim 卡是否准备好 |
| getSimOperatorName | 获取 Sim 卡运营商名称 => 中国移动、如中国联通、中国电信 |
| getSimOperatorByMnc | 获取 Sim 卡运营商名称 => 中国移动、如中国联通、中国电信 |
| getDeviceId | 获取设备id |
| getSerialNumber | 返回设备序列化 |
| getAndroidId | 获取Android id |
| getUUID | 获取设备唯一id |
| getPhoneStatus | 获取手机状态信息 |
| dial | 跳至拨号界面 |
| call | 拨打电话 |
| sendSms | 跳至发送短信界面 |
| sendSmsSilent | 发送短信 |
| getAllContactInfo | 获取手机联系人 |
| getAllContactInfo2 | 获取手机联系人 |
| getContactNum | 打开手机联系人界面点击联系人后便获取该号码 |
| getAllSMS | 获取手机短信并保存到 xml 中 |
| getMtkTeleInfo | MTK Phone. 获取 MTK 神机的双卡 IMSI、IMSI 信息 |
| getMtkTeleInfo2 | MTK Phone. 获取 MTK 神机的双卡 IMSI、IMSI 信息 |
| getQualcommTeleInfo | Qualcomm Phone. 获取 高通 神机的双卡 IMSI、IMSI 信息 |
| getSpreadtrumTeleInfo | Spreadtrum Phone. 获取 展讯 神机的双卡 IMSI、IMSI 信息 |
| toString | toString |


* **轮询工具类 ->** [PollingUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/PollingUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| startPolling | 开启轮询 |
| stopPolling | 停止轮询 |
| startPollingService | 开启轮询服务 |
| stopPollingService | 停止启轮询服务 |


* **电源管理工具类 ->** [PowerManagerUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/PowerManagerUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| getInstance | 获取 PowerManagerUtils 实例 ,单例模式 |
| isScreenOn | 屏幕是否打开(亮屏) |
| turnScreenOn | 唤醒屏幕/点亮亮屏 |
| turnScreenOff | 释放屏幕锁, 允许休眠时间自动黑屏 |
| getWakeLock | getWakeLock |
| setWakeLock | setWakeLock |
| getPowerManager | getPowerManager |
| setPowerManager | setPowerManager |
| setBright | 设置屏幕常亮 |
| setWakeLockToBright | 设置WakeLock 常亮 |


* **进程相关工具类 ->** [ProcessUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/ProcessUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| getProcessName | 获取进程号对应的进程名 |
| getCurProcessName | 获得当前进程的名字 |
| getForegroundProcessName | 获取前台线程包名 |
| getAllBackgroundProcesses | 获取后台服务进程 |
| killAllBackgroundProcesses | 杀死所有的后台服务进程 |
| killBackgroundProcesses | 杀死后台服务进程 |


* **资源文件工具类 ->** [ResourceUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/ResourceUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| getString | 获得字符串 |
| getColor | 获得颜色 |
| getDrawable | 获得Drawable |
| getResourceId | 根据资源名获得资源id |
| getDrawableId2 | 获取资源id |
| getLayoutId | 获取 layout 布局文件 |
| getStringId | 获取 string 值 |
| getDrawableId | 获取 drawable |
| getMipmapId | 获取 mipmap |
| getStyleId | 获取 style |
| getStyleableId | 获取 styleable |
| getAnimId | 获取 anim |
| getId | 获取 id |
| getColorId | color |
| readBytesFromAssets | 获取 Assets 资源文件数据 |
| readStringFromAssets | 读取字符串 来自 Assets文件 |
| readBytesFromRaw | 从res/raw 中获取内容。 |
| readStringFromRaw | 读取字符串 来自Raw 文件 |
| geFileToListFromAssets | 获取 Assets 资源文件数据(返回ArrayList<String> 一行的全部内容属于一个索引) |
| geFileToListFromRaw | 从res/raw 中获取内容。(返回ArrayList<String> 一行的全部内容属于一个索引) |
| saveAssetsFormFile | 从Assets 资源中获取内容并保存到本地 |
| saveRawFormFile | 从res/raw 中获取内容并保存到本地 |


* **屏幕相关工具类 ->** [ScreenUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/ScreenUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| getScreenWidth | 获取屏幕的宽度（单位：px） |
| getScreenHeight | 获取屏幕的高度（单位：px） |
| getScreenWidthHeightToPoint | 通过上下文获取屏幕宽度高度 |
| getScreenWidthHeight | 通过上下文获取屏幕宽度高度 |
| getScreenSize | 获取屏幕分辨率 |
| getScreenSizeOfDevice | 获取屏幕英寸 例5.5英寸 |
| getDensity | 通过上下文获取屏幕密度 |
| getDensityDpi | 通过上下文获取屏幕密度Dpi |
| getScaledDensity | 通过上下文获取屏幕缩放密度 |
| getXDpi | 获取 X轴 dpi |
| getYDpi | 获取 Y轴 dpi |
| getWidthDpi | 获取 宽度比例 dpi 基准 |
| getHeightDpi | 获取 高度比例 dpi 基准 |
| getScreenInfo | 获取屏幕信息 |
| setFullScreen | 设置屏幕为全屏 |
| setLandscape | 设置屏幕为横屏 |
| setPortrait | 设置屏幕为竖屏 |
| isLandscape | 判断是否横屏 |
| isPortrait | 判断是否竖屏 |
| getScreenRotation | 获取屏幕旋转角度 |
| isScreenLock | 判断是否锁屏 |
| isTablet | 判断是否是平板 |
| getStatusHeight | 获得状态栏的高度(无关 android:theme 获取状态栏高度) |
| getStatusBarHeight | 获取应用区域 TitleBar 高度 （顶部灰色TitleBar高度，没有设置 android:theme 的 NoTitleBar 时会显示） |
| setSleepDuration | 设置进入休眠时长 - 需添加权限 <uses-permission android:name="android.permission.WRITE_SETTINGS" /> |
| getSleepDuration | 获取进入休眠时长 |
| snapShotWithStatusBar | 获取当前屏幕截图，包含状态栏 （顶部灰色TitleBar高度，没有设置 android:theme 的 NoTitleBar 时会显示） |
| snapShotWithoutStatusBar | 获取当前屏幕截图，不包含状态栏 (如果 android:theme 全屏了，则截图无状态栏) |
| getNavigationBarHeight | 获取底部导航栏高度 |
| checkDeviceHasNavigationBar | 检测是否具有底部导航栏 |


* **SD卡相关辅助类 ->** [SDCardUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/SDCardUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| isSDCardEnable | 判断SDCard是否正常挂载 |
| getSDCardFile | 获取SD卡路径（File对象） |
| getSDCardPath | 获取SD卡路径（无添加  -> / -> File.separator） |
| isSDCardEnablePath | 判断 SD 卡是否可用 |
| getSDCardPaths | 获取 SD 卡路径 |
| getAllBlockSize | 返回对应路径的空间总大小 |
| getAvailableBlocks | 返回对应路径的空闲空间(byte 字节大小) |
| getAlreadyBlock | 返回对应路径,已使用的空间大小 |
| getBlockSizeInfos | 返回对应路径的空间大小信息 |
| getSDTotalSize | 获得 SD 卡总大小 |
| getSDAvailableSize | 获得 SD 卡剩余容量，即可用大小 |
| getRomTotalSize | 获得机身内存总大小 |
| getRomAvailableSize | 获得机身可用内存 |
| getDiskCacheDir | 获取缓存地址 |
| getCacheFile | 获取缓存资源地址 |
| getCachePath | 获取缓存资源地址 |


* **服务相关工具类 ->** [ServiceUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/ServiceUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| isServiceRunning | 判断服务是否运行 |
| getAllRunningService | 获取所有运行的服务 |
| startService | 启动服务 |
| stopService | 停止服务 |
| bindService | 绑定服务 |
| unbindService | 解绑服务 |


* **Shape 工具类 ->** [ShapeUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/ShapeUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| getDrawable | getDrawable |
| setDrawable | setDrawable |
| newBuilder | 创建新的 Shape Builder 对象 |
| newBuilderToLeft | 创建新的 Shape Builder 对象 |
| newBuilderToRight | 创建新的 Shape Builder 对象 |
| newBuilderToGradient | 创建渐变的 Shape Builder 对象 |
| build | 获取 Shape 工具类 |
| setRadius | 设置圆角 |
| setRadiusLeft | 设置圆角 |
| setRadiusRight | 设置圆角 |
| setCornerRadii | 内部处理方法 |
| setColor | 设置背景色(填充铺满) |
| setStroke | 设置边框颜色 |
| setSize | 设置大小 |


* **Shell 相关工具类 ->** [ShellUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/ShellUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| execCmd | 是否是在 root 下执行命令 |


* **创建删除快捷图标工具类 ->** [ShortCutUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/ShortCutUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| hasShortcut | 检测是否存在桌面快捷方式 |
| addShortcut | 为程序创建桌面快捷方式 |
| delShortcut | 删除程序的快捷方式 |


* **签名工具类（获取app，签名信息） ->** [SignaturesUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/SignaturesUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| toHexString | 进行转换 |
| signatureMD5 | 返回MD5 |
| signatureSHA1 | SHA1 |
| signatureSHA256 | SHA256 |
| isDebuggable | 判断签名是debug签名还是release签名 |
| getX509Certificate | 获取App 证书对象 |
| printSignatureName | 打印签名信息 |
| getSignaturesFromApk | 从APK中读取签名 |
| getCertificateFromApk | 从APK中读取签名 |


* **dp，px，sp转换、View获取宽高等 ->** [SizeUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/SizeUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| dipConvertPx | 根据手机的分辨率从 dp 的单位 转成为 px(像素) |
| pxConvertDip | 根据手机的分辨率从 px(像素) 的单位 转成为 dp |
| pxConvertSp | 根据手机的分辨率从 px(像素) 的单位 转成为 sp |
| spConvertPx | 根据手机的分辨率从 sp 的单位 转成为 px |
| dipConvertPx2 | 根据手机的分辨率从 dp 的单位 转成为 px(像素) 第二种 |
| spConvertPx2 | 根据手机的分辨率从 sp 的单位 转成为 px 第二种 |
| applyDimension | 各种单位转换 - 该方法存在于 TypedValue |
| forceGetViewSize | 获取视图的尺寸 |
| measureView | 测量视图尺寸 |
| getMeasuredWidth | 获取测量视图宽度 |
| getMeasuredHeight | 获取测量视图高度 |
| onGetSize | onGetSize |


* **颜色状态列表 工具类 ->** [StateListUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/StateListUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| getColorStateList | 通过上下文获取 |
| createColorStateList | 创建 颜色状态列表 => createColorStateList("#ffffffff", "#ff44e6ff") |
| newSelector | 创建 Drawable选择切换 list => view.setBackground(Drawable) |


* **TextView 工具类 ->** [TextViewUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/TextViewUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| getTextView | 获取TextView |
| getText | 获取文本 |
| setTextColor | 设置字体颜色 |
| setText | 设置内容 |
| setHtmlText | 设置 Html 内容 |
| setTVUnderLine | 给TextView设置下划线 |
| getTextHeight | 获取字体高度 |
| getTextTopOffsetHeight | 获取字体顶部偏移高度 |
| getTextWidth | 计算字体宽度 |
| getCenterRectY | 获取画布中间居中位置 |
| reckonTextSize | 通过需要的高度, 计算字体大小 |
| calcTextWidth | 计算第几位超过宽度 |


* **Uri 工具类 ->** [UriUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/UriUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| getUriForFileToName | 返回处理后的Uri, 单独传递名字, 自动添加包名 ${applicationId} |
| getUriForFile | Return a content URI for a given file. |


* **震动相关工具类 ->** [VibrationUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/VibrationUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| vibrate | 震动 |
| cancel | 取消振动 |


* **View 操作相关工具类 ->** [ViewUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/ViewUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| getContext | 获取上下文 |
| isEmpty | 判断View 是否为null |
| isVisibility | 判断View 是否显示 |
| isVisibilitys | 判断 View 是否都显示显示 |
| isVisibilityIN | 判断View 是否隐藏占位 |
| isVisibilityGone | 判断View 是否隐藏 |
| getVisibility | 获取显示的状态 (View.VISIBLE : View.GONE) |
| getVisibilityIN | 获取显示的状态 (View.VISIBLE : View.INVISIBLE) |
| setVisibility | 设置View显示状态 |
| setVisibilitys | 设置View 显示的状态 |
| toggleVisibilitys | 切换View 显示的状态 |
| toogleView | 切换View状态 |
| setViewImageRes | 设置View 图片资源 |
| findViewById | 初始化View |
| removeSelfFromParent | 把自身从父View中移除 |
| isTouchInView | 判断触点是否落在该View上 |
| requestLayoutParent | View 改变请求 |
| measureView | 测量 view |
| getViewWidth | 获取view的宽度 |
| getViewHeight | 获取view的高度 |
| getActivity | 获取view的上下文 |
| calcListViewItemHeight | 计算ListView Item 高度 |
| calcGridViewItemHeight | 计算GridView Item 高度 |
| getItemHeighet | 获取单独一个Item 高度 |