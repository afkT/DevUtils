
## Gradle

```gradle
// Android ( 1.9.4 以后只更新 AndroidX ) JCenter
//implementation 'com.afkt:DevApp:1.9.4'

// AndroidX ( Maven Central ) 
// DevApp - Android 工具类库
implementation 'io.github.afkt:DevAppX:2.4.8'
```

## 目录结构

```
- dev.utils                     | 根目录
   - app                        | APP 相关工具类
      - activity_result         | Activity Result API
      - anim                    | 动画工具类
      - assist                  | 辅助类
         - exif                 | 图片 EXIF 读写辅助类
         - floating             | 悬浮窗实现方案辅助类
         - lifecycle            | Activity 生命周期监听辅助类
            - current           | 当前 Activity、Fragment 生命周期辅助类
            - fragment          | Fragment 生命周期辅助类
         - record               | 文件记录分析类
         - url                  | Url 携带信息解析
      - cache                   | 缓存工具类
      - helper                  | 功能 Helper 辅助类
         - dev                  | Dev 工具类链式调用 Helper 类
         - flow                 | 流式 ( 链式 ) 连接 Helper 类
         - quick                | 简化链式设置 View Quick Helper 类
         - version              | Android 版本适配 Helper 类
         - view                 | View 链式调用快捷设置 Helper 类
      - image                   | 图片相关处理
      - info                    | APP 信息、PackageInfo 等
      - logger                  | 日志库 DevLogger
      - player                  | 多媒体 ( 视频、音频 ) 播放封装
      - share                   | SharedPreferences 封装
      - timer                   | 定时器
   - common                     | Java 工具类, 不依赖 android api
      - able                    | 通用接口定义
      - assist                  | 各种快捷辅助类
         - record               | 文件记录分析类
         - search               | 搜索相关 ( 文件搜索等 )
         - url                  | Url 携带信息解析
      - cipher                  | 编 / 解码工具类
      - comparator              | 排序比较器
         - sort                 | 各种类型比较器排序实现
      - encrypt                 | 加密工具类
      - file                    | 文件分片相关
      - format                  | 格式化相关
      - random                  | 随机概率算法工具类
      - thread                  | 线程相关
      - validator               | 数据校验工具类
```


## 初始化

> ##### ~~只需要在 Application 中调用 `DevUtils.init()` 进行初始化~~，在 DevUtils FileProviderDevApp 中已初始化，无需主动调用

> 视情况决定是否主动调用 `DevUtils.init()` 方法 ( 可自行查阅 FileProvider onCreate() 方法什么时候被调用及 Application 初始化顺序 )

## 事项

- **`Permission`、`Toast`、`Wifi`、`Camera`** 相关代码迁移至 [DevDeprecated](https://github.com/afkT/DevUtils/blob/master/lib/DevDeprecated/README.md)，如需使用依赖该库即可，无需做任何变更。

- 部分 API 更新不及时或有遗漏等，`具体以对应的工具类为准`

- [检测代码规范、注释内容排版，API 文档生成](https://github.com/afkT/JavaDoc)

- [Change Log](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/CHANGELOG.md)

- 内部存在两个日志工具类 ( 工具类内部调用 )，对外使用 [DevLogger](https://github.com/afkT/Resources/blob/main/utils_readme/logger/DevLogger.md)

```java
// 整个工具类内部日志信息，都通过以下两个工具类输出打印，并且通过 DevUtils.openLog() 控制开关

// dev.utils.app - APP 日志打印工具类
LogPrintUtils
// dev.utils.common - Java Common 日志打印工具类
JCLogUtils
```

- 开启日志
```java
// 打开 lib 内部日志 - 线上 (release) 环境，不调用方法
DevUtils.openLog();
// 标记 Debug 模式
DevUtils.openDebug();
```

- 工具类部分模块配置与使用 - [Use and Config](https://github.com/afkT/Resources/blob/main/utils_readme/USE_CONFIG.md)

- [View 链式调用快捷设置 Helper 类](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/helper/view/ViewHelper.java)

- [Dev 工具类链式调用 Helper 类](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/helper/dev/DevHelper.java)

- [Android 版本适配 Helper 类](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/helper/version/VersionHelper.java)

## API


- dev.utils                                                                | 根目录
   - [app](#devutilsapp)                                                   | APP 相关工具类
      - [activity_result](#devutilsappactivity_result)                     | Activity Result API
      - [anim](#devutilsappanim)                                           | 动画工具类
      - [assist](#devutilsappassist)                                       | 辅助类
         - [exif](#devutilsappassistexif)                                  | 图片 EXIF 读写辅助类
         - [floating](#devutilsappassistfloating)                          | 悬浮窗实现方案辅助类
         - [lifecycle](#devutilsappassistlifecycle)                        | Activity 生命周期监听辅助类
            - [current](#devutilsappassistlifecyclecurrent)                | 当前 Activity、Fragment 生命周期辅助类
            - [fragment](#devutilsappassistlifecyclefragment)              | Fragment 生命周期辅助类
         - [record](#devutilsappassistrecord)                              | 文件记录分析类
         - [url](#devutilsappassisturl)                                    | Url 携带信息解析
      - [cache](#devutilsappcache)                                         | 缓存工具类
      - [helper](#devutilsapphelper)                                       | 功能 Helper 辅助类
         - [dev](#devutilsapphelperdev)                                    | Dev 工具类链式调用 Helper 类
         - [flow](#devutilsapphelperflow)                                  | 流式 ( 链式 ) 连接 Helper 类
         - [quick](#devutilsapphelperquick)                                | 简化链式设置 View Quick Helper 类
         - [version](#devutilsapphelperversion)                            | Android 版本适配 Helper 类
         - [view](#devutilsapphelperview)                                  | View 链式调用快捷设置 Helper 类
      - [image](#devutilsappimage)                                         | 图片相关处理
      - [info](#devutilsappinfo)                                           | APP 信息、PackageInfo 等
      - [logger](#devutilsapplogger)                                       | 日志库 DevLogger
      - [player](#devutilsappplayer)                                       | 多媒体 ( 视频、音频 ) 播放封装
      - [share](#devutilsappshare)                                         | SharedPreferences 封装
      - [timer](#devutilsapptimer)                                         | 定时器
   - [common](#devutilscommon)                                             | Java 工具类, 不依赖 android api
      - [able](#devutilscommonable)                                        | 通用接口定义
      - [assist](#devutilscommonassist)                                    | 各种快捷辅助类
         - [record](#devutilscommonassistrecord)                           | 文件记录分析类
         - [search](#devutilscommonassistsearch)                           | 搜索相关 ( 文件搜索等 )
         - [url](#devutilscommonassisturl)                                 | Url 携带信息解析
      - [cipher](#devutilscommoncipher)                                    | 编 / 解码工具类
      - [comparator](#devutilscommoncomparator)                            | 排序比较器
         - [sort](#devutilscommoncomparatorsort)                           | 各种类型比较器排序实现
      - [encrypt](#devutilscommonencrypt)                                  | 加密工具类
      - [file](#devutilscommonfile)                                        | 文件分片相关
      - [format](#devutilscommonformat)                                    | 格式化相关
      - [random](#devutilscommonrandom)                                    | 随机概率算法工具类
      - [thread](#devutilscommonthread)                                    | 线程相关
      - [validator](#devutilscommonvalidator)                              | 数据校验工具类


## <span id="devutilsapp">**`dev.utils.app`**</span>


* **无障碍功能工具类 ->** [AccessibilityUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/AccessibilityUtils.java)

| 方法 | 注释 |
| :- | :- |
| getService | 获取 AccessibilityService 对象 |
| setService | 设置 AccessibilityService 对象 |
| checkAccessibility | 检查是否开启无障碍功能 |
| isAccessibilitySettingsOn | 判断是否开启无障碍功能 |
| disableSelf | 禁用无障碍服务 |
| getServiceInfo | 获取无障碍服务信息 |
| setServiceInfo | 设置无障碍服务信息 ( 动态配置方式 ) |
| getRootInActiveWindow | 获取根节点 |
| operation | 获取 Operation |
| node | 获取 Node |
| performGlobalAction | 模拟全局对应 Action 操作 |
| dispatchGesture | 模拟手势操作 |
| performActionBack | 触发返回键 |
| performActionHome | 触发 Home 键 |
| performActionPowerDialog | 启动长按电源按钮 Dialog |
| performActionLockScreen | 锁定屏幕 ( 非锁屏 ) |
| performActionTakeScreenshot | 截屏 |
| performActionNotifications | 打开通知栏 |
| performActionRecents | 最近打开应用列表 |
| performActionQuickSettings | 打开设置 |
| performActionSplitScreen | 分屏 |
| accept | 是否允许添加 |
| getNodeInfo | 获取无障碍节点 |
| performAction | 模拟对应 Action 操作 |
| performClick | 点击指定节点 |
| performLongClick | 长按指定节点 |
| inputText | 指定节点输入文本 |
| findFocus | 查找符合条件的节点 |
| findAccessibilityNodeInfosByText | 查找符合条件的节点 |
| findAccessibilityNodeInfosByViewId | 查找符合条件的节点 |
| findByFilter | 查找全部子节点并进行筛选 |
| logEvent | 拼接 AccessibilityEvent 信息日志 |
| logNodeInfo | 拼接 AccessibilityNodeInfo 信息日志 |
| logComplete | 拼接 AccessibilityEvent、AccessibilityService 完整信息日志 |
| logNodeInfoChild | 拼接 AccessibilityNodeInfo 以及 Child 信息日志 |
| contentChangeTypesToString | copy AccessibilityEvent singleContentChangeTypeToString |
| windowChangeTypesToString | copy AccessibilityEvent singleWindowChangeTypeToString |
| movementGranularitiesToString | copy AccessibilityNodeInfo getMovementGranularitySymbolicName |
| getMovementGranularitySymbolicName | 封装 AccessibilityNodeInfo#toString() granularity 拼接代码 |


* **Activity Result 工具类 ->** [ActivityResultUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/ActivityResultUtils.java)

| 方法 | 注释 |
| :- | :- |
| launch | 执行 ActivityResultContract createIntent 并进行跳转 |
| unregister | 取消启动器注册, 并释放回调监听 |
| getContract | 获取创建启动器对应 ActivityResultContract |
| registerForActivityResult | 注册创建跳转回传值启动器并返回 |
| register | 注册创建跳转回传值启动器并返回 |


* **Activity 工具类 ( 包含 Activity 控制管理 ) ->** [ActivityUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/ActivityUtils.java)

| 方法 | 注释 |
| :- | :- |
| getActivity | 通过 Context 获取 Activity |
| isFinishing | 判断 Activity 是否关闭 |
| isNotFinishing | 判断 Activity 是否未关闭 |
| isDestroyed | 判断 Activity 是否销毁 |
| isNotDestroyed | 判断 Activity 是否未销毁 |
| assertValidActivity | 判断 Activity 是否有效 |
| isActivityExists | 判断是否存在指定的 Activity |
| startHomeActivity | 回到桌面 ( 同点击 Home 键效果 ) |
| getLauncherActivity | 获取 Launcher activity |
| getActivityIcon | 获取 Activity 对应的 icon |
| getActivityLogo | 获取 Activity 对应的 logo |
| getActivityToLauncher | 获取对应包名应用启动的 Activity |
| getLauncherCategoryHomeToResolveInfo | 获取系统桌面信息 |
| getLauncherCategoryHomeToPackageName | 获取系统桌面信息 ( packageName ) |
| getLauncherCategoryHomeToActivityName | 获取系统桌面信息 ( activityName ) |
| getLauncherCategoryHomeToPackageAndName | 获取系统桌面信息 ( package/activityName ) |
| getOptionsBundle | 设置跳转动画 |
| getManager | 获取 ActivityManagerAssist 管理实例 |


* **ADB shell 工具类 ->** [ADBUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/ADBUtils.java)

| 方法 | 注释 |
| :- | :- |
| isDeviceRooted | 判断设备是否 root |
| requestRoot | 请求 Root 权限 |
| isGrantedRoot | 判断 APP 是否授权 Root 权限 |
| getAppList | 获取 APP 列表 ( 包名 ) |
| getInstallAppList | 获取 APP 安装列表 ( 包名 ) |
| getUserAppList | 获取用户安装的应用列表 ( 包名 ) |
| getSystemAppList | 获取系统应用列表 ( 包名 ) |
| getEnableAppList | 获取启用的应用列表 ( 包名 ) |
| getDisableAppList | 获取禁用的应用列表 ( 包名 ) |
| getAppListToFilter | 获取包名包含字符串 xxx 的应用列表 |
| isInstalledApp | 判断是否安装应用 |
| getAppInstallPath | 查看应用安装路径 |
| clearAppDataCache | 清除应用数据与缓存 ( 相当于在设置里的应用信息界面点击了「清除缓存」和「清除数据」 ) |
| getAppMessage | 查看应用详细信息 |
| getVersionCode | 获取 APP versionCode |
| getVersionName | 获取 APP versionName |
| installApp | 安装应用 |
| installAppSilent | 静默安装应用 |
| uninstallApp | 卸载应用 |
| uninstallAppSilent | 静默卸载应用 |
| getActivityToLauncher | 获取对应包名应用启动的 Activity |
| getWindowCurrent | 获取当前显示的 Window |
| getWindowCurrent2 | 获取当前显示的 Window |
| getWindowCurrentToPackage | 获取对应包名显示的 Window |
| getActivityCurrent | 获取当前显示的 Activity |
| getActivitys | 获取 Activity 栈 |
| getActivitysToPackage | 获取对应包名的 Activity 栈 |
| getActivitysToPackageLists | 获取对应包名的 Activity 栈 ( 最新的 Activity 越靠后 ) |
| isActivityTopRepeat | 判断 Activity 栈顶是否重复 |
| getActivityTopRepeatCount | 获取 Activity 栈顶重复总数 |
| getServices | 查看正在运行的 Services |
| startSelfApp | 启动自身应用 |
| startActivity | 跳转页面 Activity |
| startService | 启动服务 |
| stopService | 停止服务 |
| sendBroadcastToAll | 发送广播 ( 向所有组件发送 ) |
| sendBroadcast | 发送广播 |
| kill | 销毁进程 |
| sendTrimMemory | 收紧内存 |
| tap | 点击某个区域 |
| swipeClick | 按压某个区域 ( 点击 ) |
| swipe | 滑动到某个区域 |
| text | 输入文本 ( 不支持中文 ) |
| keyevent | 触发某些按键 |
| screencap | 屏幕截图 |
| screenrecord | 录制屏幕 ( 以 mp4 格式保存 ) |
| wifiConf | 查看连接过的 Wifi 密码 |
| wifiSwitch | 开启 / 关闭 Wifi |
| setSystemTime | 设置系统时间 |
| setSystemTime2 | 设置系统时间 |
| shutdown | 关机 ( 需要 root 权限 ) |
| reboot | 重启设备 ( 需要 root 权限 ) |
| rebootToRecovery | 重启引导到 recovery ( 需要 root 权限 ) |
| rebootToBootloader | 重启引导到 bootloader ( 需要 root 权限 ) |
| sendEventSlide | 发送事件滑动 |
| getSDKVersion | 获取 SDK 版本 |
| getAndroidVersion | 获取 Android 系统版本 |
| getModel | 获取设备型号 ( 如 RedmiNote4X ) |
| getBrand | 获取设备品牌 |
| getDeviceName | 获取设备名 |
| getCpuAbiList | 获取 CPU 支持的 abi 列表 |
| getAppHeapsize | 获取每个应用程序的内存上限 |
| getBattery | 获取电池状况 |
| getDensity | 获取屏幕密度 |
| getScreenSize | 获取屏幕分辨率 |
| getDisplays | 获取显示屏参数 |
| getAndroidId | 获取 Android id |
| getIMEI | 获取 IMEI 码 |
| getIPAddress | 获取 IP 地址 |
| getMac | 获取 Mac 地址 |
| getCPU | 获取 CPU 信息 |
| getMemInfo | 获取内存信息 |
| setScreenSize | 设置屏幕大小 |
| resetScreen | 恢复原分辨率命令 |
| setDensity | 设置屏幕密度 |
| resetDensity | 恢复原屏幕密度 |
| setOverscan | 显示区域 ( 设置留白边距 ) |
| resetOverscan | 恢复原显示区域 |
| getScreenBrightnessMode | 获取亮度是否为自动获取 ( 自动调节亮度 ) |
| setScreenBrightnessMode | 设置亮度是否为自动获取 ( 自动调节亮度 ) |
| getScreenBrightness | 获取屏幕亮度值 |
| setScreenBrightness | 更改屏幕亮度值 ( 亮度值在 0-255 之间 ) |
| getScreenOffTimeout | 获取自动锁屏休眠时间 ( 单位毫秒 ) |
| setScreenOffTimeout | 设置自动锁屏休眠时间 ( 单位毫秒 ) |
| getGlobalAutoTime | 获取日期时间选项中通过网络获取时间的状态 |
| setGlobalAutoTime | 修改日期时间选项中通过网络获取时间的状态, 设置是否开启 |
| disableADB | 关闭 USB 调试模式 |
| putHiddenApi | 允许访问非 SDK API |
| deleteHiddenApi | 禁止访问非 SDK API |
| openAccessibility | 开启无障碍辅助功能 |
| closeAccessibility | 关闭无障碍辅助功能 |


* **AlarmManager ( 全局定时器、闹钟 ) 工具类 ->** [AlarmUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/AlarmUtils.java)

| 方法 | 注释 |
| :- | :- |
| startAlarmIntent | 开启一次性闹钟 |
| stopAlarmIntent | 关闭闹钟 |
| startAlarmService | 开启 Service 闹钟 |
| stopAlarmService | 关闭 Service 闹钟 |
| startAlarmForegroundService | 开启 ForegroundService 闹钟 |
| stopAlarmForegroundService | 关闭 ForegroundService 闹钟 |
| startAlarmBroadcast | 开启 Receiver 闹钟 |
| stopAlarmBroadcast | 关闭 Receiver 闹钟 |
| startAlarmActivity | 开启 Activity 闹钟 |
| stopAlarmActivity | 关闭 Activity 闹钟 |


* **APP ( Android ) 工具类 ->** [AppUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/AppUtils.java)

| 方法 | 注释 |
| :- | :- |
| getSystemService | 获取 SystemService |
| getLocalBroadcastManager | 获取 LocalBroadcastManager |
| getWindowManager | 获取 WindowManager |
| getAudioManager | 获取 AudioManager |
| getStatusBarManager | 获取 StatusBarManager |
| getSensorManager | 获取 SensorManager |
| getStorageManager | 获取 StorageManager |
| getWifiManager | 获取 WifiManager |
| getConnectivityManager | 获取 ConnectivityManager |
| getTelephonyManager | 获取 TelephonyManager |
| getAppOpsManager | 获取 AppOpsManager |
| getNotificationManager | 获取 NotificationManager |
| getShortcutManager | 获取 ShortcutManager |
| getActivityManager | 获取 ActivityManager |
| getPowerManager | 获取 PowerManager |
| getBatteryManager | 获取 BatteryManager |
| getKeyguardManager | 获取 KeyguardManager |
| getInputMethodManager | 获取 InputMethodManager |
| getClipboardManager | 获取 ClipboardManager |
| getUsageStatsManager | 获取 UsageStatsManager |
| getAlarmManager | 获取 AlarmManager |
| getLocationManager | 获取 LocationManager |
| getVibrator | 获取 Vibrator |
| getDevicePolicyManager | 获取 DevicePolicyManager |
| getDownloadManager | 获取 DownloadManager |
| getSensorPrivacyManager | 获取 SensorPrivacyManager |
| getWallpaperManager | 获取 WallpaperManager |
| getPackageManager | 获取 PackageManager |
| getCurrentWindowMetrics | 获取 Current WindowMetrics |
| getMaximumWindowMetrics | 获取 Maximum WindowMetrics |
| getApplicationInfo | 获取 ApplicationInfo |
| getPackageInfo | 获取 PackageInfo |
| getSharedPreferences | 获取 SharedPreferences |
| deleteDatabase | 根据名称清除数据库 |
| getPackageName | 获取 APP 包名 |
| getAppIcon | 获取 APP 图标 |
| getAppName | 获取 APP 应用名 |
| getAppVersionName | 获取 APP versionName |
| getAppVersionCode | 获取 APP versionCode |
| getAppPath | 获取 APP 安装包路径 /data/data/packageName/.apk |
| getAppSignature | 获取 APP Signature |
| getAppSignatureMD5 | 获取 APP 签名 MD5 值 |
| getAppSignatureSHA1 | 获取 APP 签名 SHA1 值 |
| getAppSignatureSHA256 | 获取 APP 签名 SHA256 值 |
| getAppSignatureHash | 获取应用签名 Hash 值 |
| isAppDebug | 判断 APP 是否 debug 模式 |
| isAppRelease | 判断 APP 是否 release 模式 |
| isAppSystem | 判断 APP 是否系统 app |
| isAppForeground | 判断 APP 是否在前台 |
| isInstalledApp | 判断是否安装了 APP |
| isInstalledApp2 | 判断是否安装了 APP |
| startActivity | Activity 跳转 |
| startActivityForResult | Activity 跳转回传 |
| startIntentSenderForResult | Activity 请求权限跳转回传 |
| registerReceiverBool | 注册广播监听 |
| registerReceiver | 注册广播监听 |
| unregisterReceiver | 注销广播监听 |
| sendBroadcast | 发送广播 ( 无序 ) |
| sendOrderedBroadcast | 发送广播 ( 有序 ) |
| startService | 启动服务 |
| stopService | 停止服务 |
| installApp | 安装 APP ( 支持 8.0 ) 的意图 |
| installAppSilent | 静默安装应用 |
| uninstallApp | 卸载应用 |
| uninstallAppSilent | 静默卸载应用 |
| launchApp | 打开 APP |
| launchApp2 | 打开 APP |
| launchAppDetailsSettings | 跳转到 APP 设置详情页面 |
| launchAppDetails | 跳转到 APP 应用商城详情页面 |
| launchAppInstallPermissionSettings | 跳转设置页面, 开启安装未知应用权限 |
| launchManageAppAllFilesAccessPermission | 跳转设置页面, 开启 APP 授予所有文件管理权限 |
| openFile | 打开文件 |
| openFileByApp | 打开文件 ( 指定应用 ) |
| openPDFFile | 打开 PDF 文件 |
| openWordFile | 打开 Word 文件 |
| openOfficeByWPS | 调用 WPS 打开 office 文档 |
| startSysSetting | 跳转到系统设置页面 |
| openWirelessSettings | 打开网络设置界面 |
| openGpsSettings | 打开 GPS 设置界面 |


* **音频管理工具类 ->** [AudioManagerUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/AudioManagerUtils.java)

| 方法 | 注释 |
| :- | :- |
| getStreamMaxVolume | 获取指定声音流最大音量大小 |
| getStreamVolume | 获取指定声音流音量大小 |
| setStreamVolume | 设置指定声音流音量大小 |
| adjustVolumeLower | 控制手机音量, 调小一个单位 |
| adjustVolumeRaise | 控制手机音量, 调大一个单位 |
| adjustVolume | 控制手机音量, 调大或者调小一个单位 |
| adjustStreamVolumeLower | 控制指定声音流音量, 调小一个单位 |
| adjustStreamVolumeRaise | 控制指定声音流音量, 调大一个单位 |
| adjustStreamVolume | 控制指定声音流音量, 调大或者调小一个单位 |
| setStreamMuteByMusic | 设置媒体声音静音状态 |
| setStreamMuteByVoiceCall | 设置通话声音静音状态 |
| setStreamMuteBySystem | 设置系统声音静音状态 |
| setStreamMuteByRing | 设置来电响铃静音状态 |
| setStreamMuteByAlarm | 设置闹钟声音静音状态 |
| setStreamMuteByNotification | 设置通知声音静音状态 |
| setStreamMute | 设置指定声音流静音状态 |
| getMode | 获取当前的音频模式 |
| setMode | 设置当前的音频模式 |
| getRingerMode | 获取当前的铃声模式 |
| setRingerMode | 获取当前的铃声模式 |
| ringerSilent | 设置静音模式 ( 静音, 且无振动 ) |
| ringerVibrate | 设置震动模式 ( 静音, 但有振动 ) |
| ringerNormal | 设置正常模式 ( 正常声音, 振动开关由 setVibrateSetting 决定 ) |
| isDoNotDisturb | 判断是否授权 Do not disturb 权限 |
| setSpeakerphoneOn | 设置是否打开扩音器 ( 扬声器 ) |
| setMicrophoneMute | 设置是否让麦克风静音 |
| isSpeakerphoneOn | 判断是否打开扩音器 ( 扬声器 ) |
| isMicrophoneMute | 判断麦克风是否静音 |
| isMusicActive | 判断是否有音乐处于活跃状态 |
| isWiredHeadsetOn | 判断是否插入了耳机 |
| isBluetoothA2dpOn | 检查蓝牙 A2DP 音频外设是否已连接 |
| isBluetoothScoAvailableOffCall | 检查当前平台是否支持使用 SCO 的关闭调用用例 |
| isBluetoothScoOn | 检查通信是否使用蓝牙 SCO |
| setBluetoothScoOn | 设置是否使用蓝牙 SCO 耳机进行通讯 |
| startBluetoothSco | 启动蓝牙 SCO 音频连接 |
| stopBluetoothSco | 停止蓝牙 SCO 音频连接 |
| loadSoundEffects | 加载音效 |
| unloadSoundEffects | 卸载音效 |
| playSoundEffect | 播放音效 |
| abandonAudioFocus | 放弃音频焦点, 使上一个焦点所有者 ( 如果有 ) 接收焦点 |
| adjustSuggestedStreamVolume | 调整最相关的流的音量, 或者给定的回退流 |
| getParameters | 获取音频硬件指定 key 的参数值 |
| getVibrateSetting | 获取用户对振动类型的振动设置 |


* **Bar 相关工具类 ->** [BarUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/BarUtils.java)

| 方法 | 注释 |
| :- | :- |
| getStatusBarHeight | 获取 StatusBar 高度 |
| getStatusBarHeight2 | 获取 StatusBar 高度 |
| isStatusBarVisible | 判断 StatusBar 是否显示 |
| setStatusBarVisibility | 设置 StatusBar 是否显示 |
| setStatusBarLightMode | 设置 StatusBar 是否高亮模式 |
| isStatusBarLightMode | 获取 StatusBar 是否高亮模式 |
| addMarginTopEqualStatusBarHeight | 添加 View 向上 StatusBar 同等高度边距 |
| subtractMarginTopEqualStatusBarHeight | 移除 View 向上 StatusBar 同等高度边距 |
| setStatusBarColor | 设置 StatusBar 颜色 |
| setStatusBarCustom | 设置自定义 StatusBar View |
| setStatusBarColorDrawer | 设置 DrawerLayout StatusBar 颜色 |
| transparentStatusBar | 设置透明 StatusBar |
| getActionBarHeight | 获取 ActionBar 高度 |
| setNotificationBarVisibility | 设置 Notification Bar 是否显示 |
| getNavBarHeight | 获取 Navigation Bar 高度 |
| setNavBarVisibility | 设置 Navigation Bar 是否可见 |
| isNavBarVisible | 判断 Navigation Bar 是否可见 |
| isSupportNavBar | 判断是否支持 Navigation Bar |
| setNavBarColor | 设置 Navigation Bar 颜色 |
| getNavBarColor | 获取 Navigation Bar 颜色 |
| setNavBarLightMode | 设置 Navigation Bar 是否高亮模式 |
| isNavBarLightMode | 获取 Navigation Bar 是否高亮模式 |


* **电量管理工具类 ->** [BatteryUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/BatteryUtils.java)

| 方法 | 注释 |
| :- | :- |
| isPowerSaveMode | 是否省电模式 |
| getInfo | 获取电池信息获取包装类 |
| refreshBatteryStatus | 刷新电池信息粘性 Intent |
| isPresent | 是否存在电池 |
| isBatteryLow | 是否低电量 |
| isBatteryLow20 | 是否低电量 |
| isBatteryHigh | 是否高电量 |
| getLevelPercent | 获取当前电量百分比 |
| getLevel | 获取当前电量 |
| getScale | 获取电池最大电量 |
| getCycleCount | 获取电池充电周期 |
| getTemperature | 获取电池温度 |
| getVoltage | 获取电池电压 |
| getTechnology | 获取电池技术 |
| getChargePlug | 获取充电方式 |
| isCharge | 是否充电中 |
| isPluggedAC | 是否 AC 充电方式 |
| isPluggedUSB | 是否 USB 充电方式 |
| isPluggedWireless | 是否无线充电方式 |
| isPluggedDock | 是否 DOCK 充电方式 |
| getChargeStatus | 获取充电状态 |
| isChargingStatus | 是否充电状态 |
| isDisChargingStatus | 是否放电状态 |
| isFullStatus | 是否充满电状态 |
| isNotChargingStatus | 是否不在充电状态 |
| isUnknownStatus | 是否未知充电状态 |
| getHealth | 获取电池健康状况 |
| isHealthGood | 是否电池状况良好 |
| isHealthOverheat | 是否电池状况过热 |
| isHealthCold | 是否电池状况低温 |
| isHealthDead | 是否电池状况死机 |
| isHealthOverVoltage | 是否电池状况电压过载 |
| isHealthUnspecifiedFailure | 是否电池状况不明故障 |
| isHealthUnknown | 是否电池状况未知 |
| isRefreshEnabled | 是否启用 Intent 每次刷新 |
| setRefreshEnabled | 启用 Intent 每次刷新 |
| setRefreshDisabled | 禁用 Intent 每次刷新 |
| batteryStatus | 获取电池信息粘性 Intent |


* **亮度相关工具类 ->** [BrightnessUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/BrightnessUtils.java)

| 方法 | 注释 |
| :- | :- |
| isAutoBrightnessEnabled | 判断是否开启自动调节亮度 |
| setAutoBrightnessEnabled | 设置是否开启自动调节亮度 |
| getBrightness | 获取屏幕亮度 0-255 |
| setBrightness | 设置屏幕亮度 |
| setWindowBrightness | 设置窗口亮度 |
| getWindowBrightness | 获取窗口亮度 |


* **截图工具类 ->** [CapturePictureUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/CapturePictureUtils.java)

| 方法 | 注释 |
| :- | :- |
| setBitmapConfig | 设置 Bitmap Config |
| setBackgroundColor | 设置 Canvas 背景色 |
| setPaint | 设置画笔 |
| snapshotWithStatusBar | 获取当前屏幕截图, 包含状态栏 ( 顶部灰色 TitleBar 高度, 没有设置 android:theme 的 NoTitleBar 时会显示 ) |
| snapshotWithoutStatusBar | 获取当前屏幕截图, 不包含状态栏 ( 如果 android:theme 全屏, 则截图无状态栏 ) |
| enableSlowWholeDocumentDraw | 关闭 WebView 优化 |
| snapshotByWebView | 截图 WebView |
| snapshotByView | 通过 View 绘制为 Bitmap |
| snapshotByViewCache | 通过 View Cache 绘制为 Bitmap |
| snapshotByLinearLayout | 通过 LinearLayout 绘制为 Bitmap |
| snapshotByFrameLayout | 通过 FrameLayout 绘制为 Bitmap |
| snapshotByRelativeLayout | 通过 RelativeLayout 绘制为 Bitmap |
| snapshotByScrollView | 通过 ScrollView 绘制为 Bitmap |
| snapshotByHorizontalScrollView | 通过 HorizontalScrollView 绘制为 Bitmap |
| snapshotByNestedScrollView | 通过 NestedScrollView 绘制为 Bitmap |
| snapshotByListView | 通过 ListView 绘制为 Bitmap |
| snapshotByGridView | 通过 GridView 绘制为 Bitmap |
| snapshotByRecyclerView | 通过 RecyclerView 绘制为 Bitmap |


* **本应用数据清除管理工具类 ->** [CleanUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/CleanUtils.java)

| 方法 | 注释 |
| :- | :- |
| cleanCache | 清除外部缓存 ( path /storage/emulated/0/android/data/package/cache ) |
| cleanAppCache | 清除内部缓存 ( path /data/data/package/cache ) |
| cleanAppFiles | 清除内部文件 ( path /data/data/package/files ) |
| cleanAppSp | 清除内部 SP ( path /data/data/package/shared_prefs ) |
| cleanAppDbs | 清除内部数据库 ( path /data/data/package/databases ) |
| cleanAppDbByName | 根据名称清除数据库 ( path /data/data/package/databases/dbName ) |
| cleanCustomDir | 清除自定义路径下的文件 |
| cleanApplicationData | 清除本应用所有的数据 |


* **点击 ( 双击 ) 工具类 ->** [ClickUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/ClickUtils.java)

| 方法 | 注释 |
| :- | :- |
| addTouchArea | 增加控件的触摸范围, 最大范围只能是父布局所包含的的区域 |
| setCheckViewId | 设置全局是否校验 viewId |
| getGlobalIntervalTime | 获取全局双击间隔时间 |
| setGlobalIntervalTime | 设置全局双击间隔时间 |
| get | 获取对应功能模块点击辅助类 |
| remove | 移除对应功能模块点击辅助类 |
| isFastDoubleClick | 判断是否双击 ( 无效点击, 短时间内多次点击 ) |
| initConfig | 初始化配置信息 |
| putConfig | 添加配置信息 |
| removeConfig | 移除配置信息 |
| getConfigTime | 获取配置时间 |
| removeRecord | 移除点击记录 |
| clearRecord | 清空全部点击记录 |
| getIntervalTime | 获取默认点击时间间隔 |
| setIntervalTime | 设置默认点击时间间隔 |
| reset | 重置处理 |
| setOnClick | 设置点击事件 |
| setOnLongClick | 设置长按事件 |
| setOnTouch | 设置触摸事件 |


* **剪贴板相关工具类 ->** [ClipboardUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/ClipboardUtils.java)

| 方法 | 注释 |
| :- | :- |
| copyText | 复制文本到剪贴板 |
| getText | 获取剪贴板文本 |
| copyUri | 复制 URI 到剪贴板 |
| getUri | 获取剪贴板 URI |
| copyIntent | 复制意图到剪贴板 |
| getIntent | 获取剪贴板意图 |


* **ContentResolver 工具类 ->** [ContentResolverUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/ContentResolverUtils.java)

| 方法 | 注释 |
| :- | :- |
| getDataColumn | 获取 Uri Cursor 对应条件的数据行 data 字段 |
| getDisplayNameColumn | 获取 Uri Cursor 对应条件的数据行 display_name 字段 |
| delete | 删除多媒体资源 |
| update | 更新多媒体资源 |
| deleteDocument | 删除文件 |
| query | 获取 Uri Cursor |
| getMediaUri | 通过 File 获取 Media Uri |
| mediaQuery | 通过 File 获取 Media 信息 |
| getResult | 获取查询结果 |
| getProjection | 获取查询的字段 |
| getSelection | 获取查询条件 |
| getSelectionArgs | 获取查询条件的参数 |
| getSortOrder | 获取排序方式 |


* **获取 CPU 信息工具类 ->** [CPUUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/CPUUtils.java)

| 方法 | 注释 |
| :- | :- |
| getProcessorsCount | 获取处理器的 Java 虚拟机的数量 |
| getSysCPUSerialNum | 获取手机 CPU 序列号 |
| getCpuInfo | 获取 CPU 信息 |
| getCpuModel | 获取 CPU 型号 |
| getMaxCpuFreq | 获取 CPU 最大频率 ( 单位 KHZ ) |
| getMinCpuFreq | 获取 CPU 最小频率 ( 单位 KHZ ) |
| getCurCpuFreq | 获取 CPU 当前频率 ( 单位 KHZ ) |
| getCoresNumbers | 获取 CPU 核心数 |
| getCpuName | 获取 CPU 名字 |
| getCMDOutputString | 获取 CMD 指令回调数据 |


* **UncaughtException 处理工具类 ->** [CrashUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/CrashUtils.java)

| 方法 | 注释 |
| :- | :- |
| getInstance | 获取 CrashUtils 实例 |
| initialize | 初始化方法 |
| uncaughtException | 当 UncaughtException 发生时会转入该函数来处理 |
| handleException | 处理异常 |


* **Cursor 游标工具类 ->** [CursorUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/CursorUtils.java)

| 方法 | 注释 |
| :- | :- |
| closeIO | 关闭 IO |
| closeIOQuietly | 安静关闭 IO |
| existsCount | 对应游标是否存在数据 |
| getColumnIndex | 获取对应列名的索引 |
| getColumnName | 获取对应列索引列名 |
| getColumnNames | 获取所有列名 |
| getColumnCount | 获取所有列数量 |
| isNull | 判断对应列索引值是否为 null |
| isNullByName | 判断对应列名的值是否为 null |
| getType | 获取对应列索引值类型 |
| getTypeByName | 获取对应列名值类型 |
| getInt | 获取对应列索引值 |
| getLong | 获取对应列索引值 |
| getFloat | 获取对应列索引值 |
| getDouble | 获取对应列索引值 |
| getString | 获取对应列索引值 |
| getShort | 获取对应列索引值 |
| getBlob | 获取对应列索引值 |
| getIntByName | 获取对应列名值 |
| getLongByName | 获取对应列名值 |
| getFloatByName | 获取对应列名值 |
| getDoubleByName | 获取对应列名值 |
| getStringByName | 获取对应列名值 |
| getShortByName | 获取对应列名值 |
| getBlobByName | 获取对应列名值 |
| getIntThrows | 获取对应列索引值 |
| getLongThrows | 获取对应列索引值 |
| getFloatThrows | 获取对应列索引值 |
| getDoubleThrows | 获取对应列索引值 |
| getStringThrows | 获取对应列索引值 |
| getShortThrows | 获取对应列索引值 |
| getBlobThrows | 获取对应列索引值 |
| getIntByNameThrows | 获取对应列名值 |
| getLongByNameThrows | 获取对应列名值 |
| getFloatByNameThrows | 获取对应列名值 |
| getDoubleByNameThrows | 获取对应列名值 |
| getStringByNameThrows | 获取对应列名值 |
| getShortByNameThrows | 获取对应列名值 |
| getBlobByNameThrows | 获取对应列名值 |


* **数据库工具类 ( 导入导出等 ) ->** [DBUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/DBUtils.java)

| 方法 | 注释 |
| :- | :- |
| getAppDbsPath | 获取应用内部存储数据库路径 ( path /data/data/package/databases ) |
| getAppDbPath | 获取应用内部存储数据库路径 ( path /data/data/package/databases/name ) |
| deleteDatabase | 根据名称清除数据库 |
| startExportDatabase | 导出数据库 |
| startImportDatabase | 导入数据库 |


* **设备管理工具类 ->** [DevicePolicyUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/DevicePolicyUtils.java)

| 方法 | 注释 |
| :- | :- |
| getInstance | 获取 DevicePolicyUtils 实例 |
| isAdminActive | 判断给定的组件是否启动 ( 活跃 ) 中 |
| getActiveIntent | 获取激活跳转 Intent |
| activeAdmin | 激活给定的组件 |
| removeActiveAdmin | 移除激活组件 |
| startLockPassword | 设置锁屏密码 ( 不需要激活就可以运行 ) |
| setLockPassword | 设置锁屏密码 |
| lockNow | 立刻锁屏 |
| lockByTime | 设置多长时间后锁屏 |
| wipeData | 清除所有数据 ( 恢复出厂设置 ) |
| resetPassword | 设置新解锁密码 |
| setStorageEncryption | 设置存储设备加密 |
| setCameraDisabled | 设置停用相机 |
| getComponentName | 获取 ComponentName |
| setComponentName | 设置 ComponentName |


* **设备相关工具类 ->** [DeviceUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/DeviceUtils.java)

| 方法 | 注释 |
| :- | :- |
| getAppDeviceInfo | 获取应用、设备信息 |
| refreshAppDeviceInfo | 刷新应用、设备信息 |
| getUUID | 获取设备唯一 UUID |
| getUUIDDevice | 获取设备唯一 UUID ( 使用硬件信息拼凑出来的 ) |
| getDeviceInfo | 获取设备信息 |
| handlerDeviceInfo | 处理设备信息 |
| getBoard | 获取设备基板名称 |
| getBootloader | 获取设备引导程序版本号 |
| getBrand | 获取设备品牌 |
| getCPU_ABI | 获取支持的第一个指令集 |
| getCPU_ABI2 | 获取支持的第二个指令集 |
| getABIs | 获取支持的指令集 如: [arm64-v8a, armeabi-v7a, armeabi] |
| getSUPPORTED_32_BIT_ABIS | 获取支持的 32 位指令集 |
| getSUPPORTED_64_BIT_ABIS | 获取支持的 64 位指令集 |
| getDevice | 获取设备驱动名称 |
| getDisplay | 获取设备显示的版本包 ( 在系统设置中显示为版本号 ) 和 ID 一样 |
| getFingerprint | 获取设备的唯一标识, 由设备的多个信息拼接合成 |
| getHardware | 获取设备硬件名称, 一般和基板名称一样 ( BOARD ) |
| getHost | 获取设备主机地址 |
| getID | 获取设备版本号 |
| getModel | 获取设备型号 如 RedmiNote4X |
| getManufacturer | 获取设备厂商 如 Xiaomi |
| getProduct | 获取整个产品的名称 |
| getRadio | 获取无线电固件版本号, 通常是不可用的 显示 unknown |
| getTags | 获取设备标签, 如 release-keys 或测试的 test-keys |
| getTime | 获取设备时间 |
| getType | 获取设备版本类型 主要为 "user" 或 "eng". |
| getUser | 获取设备用户名 基本上都为 android-build |
| getSDKVersion | 获取 SDK 版本号 |
| getRelease | 获取系统版本号, 如 4.1.2 或 2.2 或 2.3 等 |
| getCodename | 获取设备当前的系统开发代号, 一般使用 REL 代替 |
| getIncremental | 获取系统源代码控制值, 一个数字或者 git hash 值 |
| getAndroidId | 获取 Android id |
| getBaseband_Ver | 获取基带版本 BASEBAND-VER |
| getLinuxCore_Ver | 获取内核版本 CORE-VER |
| isDeviceRooted | 判断设备是否 root |
| isAdbEnabled | 获取是否启用 ADB |
| isDevelopmentSettingsEnabled | 是否打开开发者选项 |
| getMacAddress | 获取设备 MAC 地址 |
| shutdown | 关机 ( 需要 root 权限 ) |
| reboot | 重启设备 ( 需要 root 权限 ) |
| rebootToRecovery | 重启引导到 recovery ( 需要 root 权限 ) |
| rebootToBootloader | 重启引导到 bootloader ( 需要 root 权限 ) |
| isTablet | 判断是否是平板 |


* **Dialog 操作相关工具类 ->** [DialogUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/DialogUtils.java)

| 方法 | 注释 |
| :- | :- |
| setStatusBarColor | 设置 Dialog 状态栏颜色 |
| setSemiTransparentStatusBarColor | 设置 Dialog 高版本状态栏蒙层 |
| setStatusBarColorAndFlag | 设置 Dialog 状态栏颜色、高版本状态栏蒙层 |
| getAttributes | 获取 Dialog Window LayoutParams |
| setAttributes | 设置 Dialog Window LayoutParams |
| setWidth | 设置 Dialog 宽度 |
| setHeight | 设置 Dialog 高度 |
| setWidthHeight | 设置 Dialog 宽度、高度 |
| setX | 设置 Dialog X 轴坐标 |
| setY | 设置 Dialog Y 轴坐标 |
| setXY | 设置 Dialog X、Y 轴坐标 |
| setGravity | 设置 Dialog Gravity |
| setDimAmount | 设置 Dialog 透明度 |
| setCancelable | 设置是否允许返回键关闭 |
| setCanceledOnTouchOutside | 设置是否允许点击其他地方自动关闭 |
| setCancelableAndTouchOutside | 设置是否允许 返回键关闭、点击其他地方自动关闭 |
| isShowing | 获取 Dialog 是否显示 |
| showDialog | 显示 Dialog |
| closeDialog | 关闭 Dialog |
| closeDialogs | 关闭多个 Dialog |
| closePopupWindow | 关闭 PopupWindow |
| closePopupWindows | 关闭多个 PopupWindow |
| showDialogAndCloses | 显示 Dialog 并关闭其他 Dialog |
| createAlertDialog | 创建提示 Dialog ( 原生样式 ) |
| createProgressDialog | 创建加载中 Dialog ( 原生样式 ) |
| autoCloseDialog | 自动关闭 dialog |
| autoClosePopupWindow | 自动关闭 PopupWindow |
| createSingleChoiceListDialog | 创建单选列表样式 Dialog |
| createSingleChoiceDialog | 创建单选样式 Dialog |
| createMultiChoiceDialog | 创建多选样式 Dialog |
| createViewDialog | 创建自定义 View 样式 Dialog |


* **DownloadManager 工具类 ->** [DownloadUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/DownloadUtils.java)

| 方法 | 注释 |
| :- | :- |
| enqueue | 加入队列进行下载 |
| remove | 移除下载 |
| query | 查询下载信息 |
| openDownloadedFile | 打开下载文件 ( 下载成功才能打开 ) |
| getUriForDownloadedFile | 获取下载文件 Uri |
| getMimeTypeForDownloadedFile | 获取下载文件 MimeType 类型 |
| createQueryById | 通过下载 ID 创建 Query 查询配置对象 |
| createQueryByStatus | 通过下载状态创建 Query 查询配置对象 |
| queryById | 通过下载 ID 查询下载信息 |
| removeResult | 移除下载结果 ( 不对比 ids 数量和成功数量 ) |
| removeResultEqual | 移除下载结果 ( 对比 ids 数量和成功数量 ) |
| queryUriById | 查询下载地址 ( 通过下载 ID ) |
| queryBytesById | 查询下载进度信息 ( 通过下载 ID ) |
| queryStatusById | 查询下载状态 ( 通过下载 ID ) |
| queryReasonById | 查询下载失败原因 ( 通过下载 ID ) |
| queryMediaTypeById | 查询下载文件类型 ( 通过下载 ID ) |
| queryLastModifiedById | 查询最近下载变更时间 ( 通过下载 ID ) |
| queryTitleById | 查询下载标题 ( 通过下载 ID ) |
| queryDescriptionById | 查询下载描述 ( 通过下载 ID ) |
| queryCursorSingleById | 查询单条下载信息 ( 通过下载 ID ) |
| queryCursorSingle | 查询单条下载信息 |
| queryCursorMultipleByQuery | 查询多条下载信息 |
| queryCursorMultiple | 查询多条下载信息 |
| isValidStatus | 是否有效下载状态 ( 判断非 -1 ) |
| isPendingStatus | 是否等待下载状态 |
| isRunningStatus | 是否下载中状态 |
| isPausedStatus | 是否暂停下载状态 |
| isSuccessfulStatus | 是否下载成功状态 |
| isFailedStatus | 是否下载失败状态 |
| isValidStatusById | 是否有效下载状态 ( 判断非 -1 ) |
| isPendingStatusById | 是否等待下载状态 |
| isRunningStatusById | 是否下载中状态 |
| isPausedStatusById | 是否暂停下载状态 |
| isSuccessfulStatusById | 是否下载成功状态 |
| isFailedStatusById | 是否下载失败状态 |
| intercept | 拦截器回调方法 |


* **EditText 工具类 ->** [EditTextUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/EditTextUtils.java)

| 方法 | 注释 |
| :- | :- |
| getEditText | 获取 EditText |
| getText | 获取输入的内容 |
| getTextLength | 获取输入的内容长度 |
| setText | 设置内容 |
| setTexts | 设置多个 EditText 文本 |
| insert | 追加内容 ( 当前光标位置追加 ) |
| setMaxLength | 设置长度限制 |
| setMaxLengthAndText | 设置长度限制, 并且设置内容 |
| isCursorVisible | 是否显示光标 |
| setCursorVisible | 设置是否显示光标 |
| setTextCursorDrawable | 设置光标 |
| getSelectionStart | 获取光标位置 |
| setSelectionToTop | 设置光标在第一位 |
| setSelectionToBottom | 设置光标在最后一位 |
| setSelection | 设置光标位置 |
| getInputType | 设置输入类型 |
| setInputType | 设置输入类型 |
| getImeOptions | 设置软键盘右下角按钮类型 |
| setImeOptions | 设置软键盘右下角按钮类型 |
| getTransformationMethod | 获取文本视图显示转换 |
| setTransformationMethod | 设置文本视图显示转换 |
| addTextChangedListener | 添加输入监听事件 |
| removeTextChangedListener | 移除输入监听事件 |
| setKeyListener | 设置 KeyListener |
| getLettersKeyListener | 获取 DigitsKeyListener ( 限制只能输入字母, 默认弹出英文软键盘 ) |
| getNumberAndLettersKeyListener | 获取 DigitsKeyListener ( 限制只能输入字母和数字, 默认弹出英文软键盘 ) |
| getNumberKeyListener | 获取 DigitsKeyListener ( 限制只能输入数字, 默认弹出数字列表 ) |
| createDigitsKeyListener | 创建 DigitsKeyListener |


* **手电筒工具类 ->** [FlashlightUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/FlashlightUtils.java)

| 方法 | 注释 |
| :- | :- |
| getInstance | 获取 FlashlightUtils 实例 |
| register | 注册摄像头 |
| unregister | 注销摄像头 |
| isFlashlightEnable | 是否支持手机闪光灯 |
| setFlashlightOn | 打开闪光灯 |
| setFlashlightOff | 关闭闪光灯 |
| isFlashlightOn | 是否打开闪光灯 |


* **Fragment 工具类 ->** [FragmentUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/FragmentUtils.java)

| 方法 | 注释 |
| :- | :- |
| add | 添加 Fragment |
| show | 显示 Fragment |
| hide | Hide fragment |
| showHide | 显示 Fragment 并隐藏其他 Fragment |
| replace | 替换 Fragment |
| pop | 回退 Fragment |
| popTo | 回退到指定 Fragment |
| popAll | 回退所有 Fragment |
| remove | 移除 Fragment |
| removeTo | 移除 Fragment |
| removeAll | 移除全部 Fragment |
| findFragment | 查找 Fragment |
| getTop | 获取顶部 Fragment |
| getTopInStack | 获取栈顶 Fragment |
| getTopShow | 获取顶部显示的 Fragment |
| getTopShowInStack | 获取栈顶显示的 Fragment |
| getFragments | 获取 FragmentManager 全部 Fragment |
| getFragmentsInStack | 获取 FragmentManager 全部栈顶 Fragment |
| getAllFragments | 获取 FragmentManager 全部 Fragment |
| getAllFragmentsInStack | 获取 FragmentManager 全部栈顶 Fragment |
| getSimpleName | 获取 Fragment SimpleName |
| dispatchBackPress | 调用 Fragment OnBackClickListener 校验是否进行消费 |
| setBackgroundColor | 设置 Fragment View 背景 |
| setBackgroundResource | 设置 Fragment View 背景 |
| setBackground | 设置 Fragment View 背景 |
| getFragment | getFragment |
| getNext | getNext |
| toString | toString |
| onBackClick | 是否允许处理返回键 |


* **Handler 工具类 ->** [HandlerUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/HandlerUtils.java)

| 方法 | 注释 |
| :- | :- |
| getMainHandler | 获取主线程 Handler |
| isMainThread | 当前线程是否主线程 |
| postRunnable | 在主线程 Handler 中执行任务 |
| removeRunnable | 在主线程 Handler 中清除任务 |
| getRunnableMaps | 获取 Key Runnable Map |
| clearRunnableMaps | 清空 Key Runnable Map |
| containsKey | 判断 Map 是否存储 key Runnable |
| put | 通过 Key 存储 Runnable |
| remove | 通过 Key 移除 Runnable |


* **ImageView 工具类 ->** [ImageViewUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/ImageViewUtils.java)

| 方法 | 注释 |
| :- | :- |
| getImageView | 获取 ImageView |
| getAdjustViewBounds | 获取 ImageView 是否保持宽高比 |
| setAdjustViewBounds | 设置 ImageView 是否保持宽高比 |
| getMaxHeight | 获取 ImageView 最大高度 |
| setMaxHeight | 设置 ImageView 最大高度 |
| getMaxWidth | 获取 ImageView 最大宽度 |
| setMaxWidth | 设置 ImageView 最大宽度 |
| setImageLevel | 设置 ImageView Level |
| setImageBitmap | 设置 ImageView Bitmap |
| setImageDrawable | 设置 ImageView Drawable |
| setImageResource | 设置 ImageView 资源 |
| setImageMatrix | 设置 ImageView Matrix |
| setImageTintList | 设置 ImageView 着色颜色 |
| setImageTintMode | 设置 ImageView 着色模式 |
| removeImageBitmap | 移除 ImageView Bitmap |
| removeImageDrawable | 移除 ImageView Drawable |
| setScaleType | 设置 ImageView 缩放类型 |
| getImageMatrix | 获取 ImageView Matrix |
| getImageTintList | 获取 ImageView 着色颜色 |
| getImageTintMode | 获取 ImageView 着色模式 |
| getScaleType | 获取 ImageView 缩放模式 |
| getDrawable | 获取 ImageView Drawable |
| setBackgroundResources | 设置 View 图片资源 |
| setImageResources | 设置 View 图片资源 |
| setImageBitmaps | 设置 View Bitmap |
| setImageDrawables | 设置 View Drawable |
| removeImageBitmaps | 移除 View Bitmap |
| removeImageDrawables | 移除 View Drawable |
| setScaleTypes | 设置 View 缩放模式 |
| getImageViewSize | 根据 ImageView 获适当的宽高 |


* **Intent 相关工具类 ->** [IntentUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/IntentUtils.java)

| 方法 | 注释 |
| :- | :- |
| getIntent | 获取 Intent |
| isIntentAvailable | 判断 Intent 是否可用 |
| getCategoryLauncherIntent | 获取 CATEGORY_LAUNCHER Intent |
| getInstallAppIntent | 获取安装 APP ( 支持 8.0 ) 的意图 |
| getUninstallAppIntent | 获取卸载 APP 的意图 |
| getLaunchAppIntent | 获取打开 APP 的意图 |
| getSystemSettingIntent | 获取跳转到系统设置的意图 |
| getLaunchAppInstallPermissionSettingsIntent | 获取 APP 安装权限设置的意图 |
| getLaunchAppNotificationSettingsIntent | 获取 APP 通知权限设置的意图 |
| getLaunchAppNotificationListenSettingsIntent | 获取 APP 通知使用权页面 |
| getManageOverlayPermissionIntent | 获取悬浮窗口权限列表的意图 |
| getManageAppAllFilesAccessPermissionIntent | 获取 APP 授予所有文件管理权限的意图 |
| getManageAllFilesAccessPermissionIntent | 获取授予所有文件管理权限列表的意图 |
| getLaunchAppDetailsSettingsIntent | 获取 APP 具体设置的意图 |
| getLaunchAppDetailIntent | 获取到应用商店 APP 详情界面的意图 |
| getShareTextIntent | 获取分享文本的意图 |
| getShareImageIntent | 获取分享图片的意图 |
| getComponentIntent | 获取其他应用组件的意图 |
| getShutdownIntent | 获取关机的意图 |
| getDialIntent | 获取跳至拨号界面意图 |
| getCallIntent | 获取拨打电话意图 |
| getSendSmsIntent | 获取发送短信界面的意图 |
| getImageCaptureIntent | 获取图片拍摄的意图 |
| getVideoCaptureIntent | 获取视频拍摄的意图 |
| getOpenDocumentIntent | 获取存储访问框架的意图 |
| getCreateDocumentIntent | 获取创建文件的意图 |
| getOpenBrowserIntent | 获取打开浏览器的意图 |
| getOpenAndroidBrowserIntent | 获取打开 Android 浏览器的意图 |


* **Android 原生 JSONObject 工具类 ->** [JSONObjectUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/JSONObjectUtils.java)

| 方法 | 注释 |
| :- | :- |
| toJson | 转换为 JSON 格式字符串 |
| fromJson | Object 转换 JSON 对象 |
| wrap | 包装转换 Object |
| stringJSONEscape | 字符串 JSON 转义处理 |
| isJSON | 判断字符串是否 JSON 格式 |
| isJSONObject | 判断字符串是否 JSON Object 格式 |
| isJSONArray | 判断字符串是否 JSON Array 格式 |
| jsonToMap | 将 JSON 格式字符串转化为 Map |
| jsonToList | 将 JSON 格式字符串转化为 List |
| getJSONObject | 获取 JSONObject |
| getJSONArray | 获取 JSONArray |
| get | 获取指定 key 数据 |
| opt | 获取指定 key 数据 |


* **软键盘相关工具类 ->** [KeyBoardUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/KeyBoardUtils.java)

| 方法 | 注释 |
| :- | :- |
| setDelayMillis | 设置延迟时间 |
| setSoftInputMode | 设置 Window 软键盘是否显示 |
| judgeView | 设置某个 View 内所有非 EditText 的子 View OnTouchListener 事件 |
| isSoftInputVisible | 判断软键盘是否可见 |
| registerSoftInputChangedListener | 注册软键盘改变监听 |
| registerSoftInputChangedListener2 | 注册软键盘改变监听 |
| fixSoftInputLeaks | 修复软键盘内存泄漏 在 Activity.onDestroy() 中使用 |
| toggleKeyboard | 自动切换键盘状态, 如果键盘显示则隐藏反之显示 |
| openKeyboard | 打开软键盘 |
| openKeyboardDelay | 延时打开软键盘 |
| openKeyboardByFocus | 打开软键盘 |
| closeKeyboard | 关闭软键盘 |
| closeKeyBoardSpecial | 关闭软键盘 |
| closeKeyBoardSpecialDelay | 延时关闭软键盘 |
| closeKeyboardDelay | 延时关闭软键盘 |


* **锁屏管理工具类 ( 锁屏、禁用锁屏, 判断是否锁屏 ) ->** [KeyguardUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/KeyguardUtils.java)

| 方法 | 注释 |
| :- | :- |
| getInstance | 获取 KeyguardUtils 实例 |
| isKeyguardLocked | 是否锁屏 ( android 4.1 以上支持 ) |
| isKeyguardSecure | 是否有锁屏密码 ( android 4.1 以上支持 ) |
| inKeyguardRestrictedInputMode | 是否锁屏 |
| newKeyguardLock | 创建 KeyguardManager.KeyguardLock ( 通过 TAG 生成 ) |
| disableKeyguard | 屏蔽系统的屏保 |
| reenableKeyguard | 使能显示锁屏界面, 如果你之前调用了 disableKeyguard() 方法取消锁屏界面, 那么会马上显示锁屏界面 |
| getDefaultKeyguardLock | 获取默认 KeyguardManager.KeyguardLock |
| setDefaultKeyguardLock | 设置默认 KeyguardManager.KeyguardLock |


* **语言工具类 ->** [LanguageUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/LanguageUtils.java)

| 方法 | 注释 |
| :- | :- |
| getSystemLanguage | 获取系统语言 |
| getSystemCountry | 获取系统语言区域 |
| getSystemPreferredLanguage | 获取系统首选语言 |
| applyLanguage | 修改系统语言 ( APP 多语言, 单独改变 APP 语言 ) |
| getSupportLanguages | 获取支持的语言 |
| putSupportLanguage | 添加支持的语言 |
| removeSupportLanguage | 移除支持的语言 |
| isSupportLanguage | 是否支持此语言 |
| getSupportLanguage | 获取支持语言 |
| isEn | 判断是否为英文语言环境 |
| isZh | 判断是否为中文语言环境 |
| isZhCN | 判断是否为中文简体语言环境 |
| isZhTW | 判断是否为中文繁体语言环境 |
| isLanguage | 判断是否为指定语言环境 |
| isRegion | 判断是否为指定区域语言环境 |


* **事件工具类 ->** [ListenerUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/ListenerUtils.java)

| 方法 | 注释 |
| :- | :- |
| getTouchListener | 获取 View 设置的 OnTouchListener 事件对象 |
| getClickListener | 获取 View 设置的 OnClickListener 事件对象 |
| getListenerInfo | 获取 View ListenerInfo 对象 ( 内部类 ) |
| getListenerInfoListener | 获取 View ListenerInfo 对象内部事件对象 |
| setOnClicks | 设置点击事件 |
| setOnLongClicks | 设置长按事件 |
| setOnTouchs | 设置触摸事件 |


* **List View ( 列表 View ) 相关工具类 ->** [ListViewUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/ListViewUtils.java)

| 方法 | 注释 |
| :- | :- |
| getItemCount | 获取 Adapter Item 总数 |
| getItemView | 获取指定索引 Item View |
| smoothScrollToPosition | 滑动到指定索引 ( 有滚动过程 ) |
| scrollToPosition | 滑动到指定索引 ( 无滚动过程 ) |
| smoothScrollToTop | 滑动到顶部 ( 有滚动过程 ) |
| scrollToTop | 滑动到顶部 ( 无滚动过程 ) |
| smoothScrollToBottom | 滑动到底部 ( 有滚动过程 ) |
| scrollToBottom | 滑动到底部 ( 无滚动过程 ) |
| smoothScrollTo | 滚动到指定位置 ( 有滚动过程, 相对于初始位置移动 ) |
| smoothScrollBy | 滚动到指定位置 ( 有滚动过程, 相对于上次移动的最后位置移动 ) |
| fullScroll | 滚动方向 ( 有滚动过程 ) |
| scrollTo | View 内容滚动位置 ( 相对于初始位置移动 ) |
| scrollBy | View 内部滚动位置 ( 相对于上次移动的最后位置移动 ) |
| setScrollX | 设置 View 滑动的 X 轴坐标 |
| setScrollY | 设置 View 滑动的 Y 轴坐标 |
| getScrollX | 获取 View 滑动的 X 轴坐标 |
| getScrollY | 获取 View 滑动的 Y 轴坐标 |
| setDescendantFocusability | 设置 ViewGroup 和其子控件两者之间的关系 |
| setOverScrollMode | 设置 View 滚动模式 |
| calcListViewHeight | 计算 ListView 高度 |
| calcGridViewHeight | 计算 GridView 高度 |


* **定位相关工具类 ->** [LocationUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/LocationUtils.java)

| 方法 | 注释 |
| :- | :- |
| isGpsEnabled | 判断 GPS 是否可用 |
| isLocationEnabled | 判断定位是否可用 |
| isLocationEnabled2 | 判断定位是否可用 |
| isPassiveEnable | 判断定位是否可用 |
| openGpsSettings | 打开 GPS 设置界面 |
| register | 注册 |
| unregister | 注销监听 |
| getLocation | 获取位置 ( 需要先判断是否开启了定位 ) |
| getAddress | 根据经纬度获取地理位置 |
| getCountryName | 根据经纬度获取所在国家 |
| getLocality | 根据经纬度获取所在地 |
| getStreet | 根据经纬度获取所在街道 |
| isBetterLocation | 判断是否更好的位置 |
| isSameProvider | 是否相同的提供者 |
| getLastKnownLocation | 获取最后一次保留的坐标 |
| onLocationChanged | 当坐标改变时触发此函数, 如果 Provider 传进相同的坐标, 它就不会被触发 |
| onStatusChanged | provider 的在可用、暂时不可用和无服务三个状态直接切换时触发此函数 |


* **Android Manifest 工具类 ->** [ManifestUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/ManifestUtils.java)

| 方法 | 注释 |
| :- | :- |
| getMetaData | 获取 Application meta Data |
| getMetaDataInActivity | 获取 Activity meta Data |
| getMetaDataInService | 获取 Service meta Data |
| getMetaDataInReceiver | 获取 Receiver meta Data |
| getMetaDataInProvider | 获取 ContentProvider meta Data |
| getAppVersion | 获取 APP 版本信息 |
| getAppVersionCode | 获取 APP versionCode |
| getAppVersionName | 获取 APP versionName |


* **MediaStore 工具类 ->** [MediaStoreUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/MediaStoreUtils.java)

| 方法 | 注释 |
| :- | :- |
| notifyMediaStore | 通知刷新本地资源 |
| getDisplayName | 获取待显示名 |
| getImageDisplayName | 获取 Image 显示名 |
| getVideoDisplayName | 获取 Video 显示名 |
| getAudioDisplayName | 获取 Audio 显示名 |
| createImageUri | 创建图片 Uri |
| createVideoUri | 创建视频 Uri |
| createAudioUri | 创建音频 Uri |
| createDownloadUri | 创建 Download Uri |
| createMediaUri | 创建预存储 Media Uri |
| createUriByPath | 通过 File Path 创建 Uri |
| createUriByFile | 通过 File Path 创建 Uri |
| insertImage | 插入一张图片 |
| insertVideo | 插入一条视频 |
| insertAudio | 插入一条音频 |
| insertDownload | 插入一条文件资源 |
| insertMedia | 插入一条多媒体资源 |
| getVideoDuration | 获取本地视频时长 |
| getVideoSize | 获取本地视频宽高 |
| getImageWidthHeight | 获取本地图片宽高 |
| getMediaInfo | 获取多媒体资源信息 |
| createWriteRequest | 获取用户向应用授予对指定媒体文件组的写入访问权限的请求 |
| createFavoriteRequest | 获取用户将设备上指定的媒体文件标记为收藏的请求 |
| createTrashRequest | 获取用户将指定的媒体文件放入设备垃圾箱的请求 |
| createDeleteRequest | 获取用户立即永久删除指定的媒体文件 ( 而不是先将其放入垃圾箱 ) 的请求 |
| getMimeTypeFromExtension | 通过后缀获取 MimeType |
| getExtensionFromMimeType | 通过 MimeType 获取后缀 ( 不含 . ) |
| getFileExtensionFromUrl | 通过 Url 获取文件后缀 |
| hasMimeType | 判断 MimeMap 是否存在指定的 MimeType |
| hasExtension | 判断是否支持的 MimeType 后缀 |


* **内存信息工具类 ->** [MemoryUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/MemoryUtils.java)

| 方法 | 注释 |
| :- | :- |
| printMemoryInfo | 获取内存信息 |
| printMemoryInfo2 | 获取内存信息 |
| getMemoryInfo | 获取内存信息 |
| getAvailMemory | 获取可用内存信息 |
| getAvailMemoryFormat | 获取可用内存信息 ( 格式化 ) |
| getTotalMemory | 获取总内存大小 |
| getTotalMemoryFormat | 获取总内存大小 ( 格式化 ) |
| getMemoryAvailable | 获取可用内存大小 |
| getMemoryAvailableFormat | 获取可用内存大小 ( 格式化 ) |
| getMemInfoType | 通过不同 type 获取对应的内存信息 |


* **网络管理工具类 ->** [NetWorkUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/NetWorkUtils.java)

| 方法 | 注释 |
| :- | :- |
| getMobileDataEnabled | 获取移动网络打开状态 ( 默认属于未打开 ) |
| setMobileDataEnabled | 设置移动网络开关 ( 无判断是否已开启移动网络 ) |
| isConnect | 判断是否连接了网络 |
| getConnectType | 获取连接的网络类型 |
| isConnWifi | 判断是否连接 Wifi ( 连接上、连接中 ) |
| isConnMobileData | 判断是否连接移动网络 ( 连接上、连接中 ) |
| isAvailable | 判断网络是否可用 |
| isAvailableByPing | 使用 ping ip 方式判断网络是否可用 |
| getActiveNetworkInfo | 获取活动网络信息 |
| getActiveNetwork | 获取活动网络 |
| is4G | 判断是否 4G 网络 |
| getWifiEnabled | 判断 Wifi 是否打开 |
| isWifiAvailable | 判断 Wifi 数据是否可用 |
| getNetworkOperatorName | 获取网络运营商名称 ( 中国移动、如中国联通、中国电信 ) |
| getNetworkType | 获取当前网络类型 |
| getNetworkClass | 获取移动网络连接类型 |
| getBroadcastIpAddress | 获取广播 IP 地址 |
| getDomainAddress | 获取域名 IP 地址 |
| getIPAddress | 获取 IP 地址 |
| getIpAddressByWifi | 根据 Wifi 获取网络 IP 地址 |
| getGatewayByWifi | 根据 Wifi 获取网关 IP 地址 |
| getNetMaskByWifi | 根据 Wifi 获取子网掩码 IP 地址 |
| getServerAddressByWifi | 根据 Wifi 获取服务端 IP 地址 |


* **通知栏管理工具类 ->** [NotificationUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/NotificationUtils.java)

| 方法 | 注释 |
| :- | :- |
| isNotificationEnabled | 检查通知栏权限是否开启 |
| checkAndIntentSetting | 检查是否有获取通知栏信息权限并跳转设置页面 |
| isNotificationListenerEnabled | 判断是否有获取通知栏信息权限 |
| startNotificationListenSettings | 跳转到设置页面, 开启获取通知栏信息权限 |
| cancelAll | 移除通知 ( 移除所有通知 ) |
| cancel | 移除通知 ( 移除标记为 id 的通知 ) |
| notify | 进行通知 |
| getNotificationChannel | 获取 NotificationChannel |
| createNotificationChannel | 创建 NotificationChannel |
| createPendingIntent | 获取 PendingIntent |
| createNotification | 创建通知栏对象 |
| createNotificationBuilder | 创建通知栏 Builder 对象 |
| get | 获取 Led 配置参数 |
| isEmpty | 判断是否为 null |


* **路径相关工具类 ->** [PathUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/PathUtils.java)

| 方法 | 注释 |
| :- | :- |
| getInternal | 获取内部存储路径类 |
| getAppExternal | 获取应用外部存储路径类 |
| getSDCard | 获取 SDCard 外部存储路径类 |
| isExternalStorageManager | 是否获得 MANAGE_EXTERNAL_STORAGE 权限 |
| checkExternalStorageAndIntentSetting | 检查是否有 MANAGE_EXTERNAL_STORAGE 权限并跳转设置页面 |
| isSDCardEnable | 判断 SDCard 是否正常挂载 |
| getSDCardFile | 获取 SDCard 外部存储路径 ( path /storage/emulated/0/ ) |
| getSDCardPath | 获取 SDCard 外部存储路径 ( path /storage/emulated/0/ ) |
| getExternalStoragePublicPath | 获取 SDCard 外部存储文件路径 ( path /storage/emulated/0/ ) |
| getExternalStoragePublicDir | 获取 SDCard 外部存储文件路径 ( path /storage/emulated/0/ ) |
| getMusicPath | 获取 SDCard 外部存储音乐路径 ( path /storage/emulated/0/Music ) |
| getMusicDir | 获取 SDCard 外部存储音乐路径 ( path /storage/emulated/0/Music ) |
| getPodcastsPath | 获取 SDCard 外部存储播客路径 ( path /storage/emulated/0/Podcasts ) |
| getPodcastsDir | 获取 SDCard 外部存储播客路径 ( path /storage/emulated/0/Podcasts ) |
| getRingtonesPath | 获取 SDCard 外部存储铃声路径 ( path /storage/emulated/0/Ringtones ) |
| getRingtonesDir | 获取 SDCard 外部存储铃声路径 ( path /storage/emulated/0/Ringtones ) |
| getAlarmsPath | 获取 SDCard 外部存储闹铃路径 ( path /storage/emulated/0/Alarms ) |
| getAlarmsDir | 获取 SDCard 外部存储闹铃路径 ( path /storage/emulated/0/Alarms ) |
| getNotificationsPath | 获取 SDCard 外部存储通知路径 ( path /storage/emulated/0/Notifications ) |
| getNotificationsDir | 获取 SDCard 外部存储通知路径 ( path /storage/emulated/0/Notifications ) |
| getPicturesPath | 获取 SDCard 外部存储图片路径 ( path /storage/emulated/0/Pictures ) |
| getPicturesDir | 获取 SDCard 外部存储图片路径 ( path /storage/emulated/0/Pictures ) |
| getMoviesPath | 获取 SDCard 外部存储影片路径 ( path /storage/emulated/0/Movies ) |
| getMoviesDir | 获取 SDCard 外部存储影片路径 ( path /storage/emulated/0/Movies ) |
| getDownloadPath | 获取 SDCard 外部存储下载路径 ( path /storage/emulated/0/Download ) |
| getDownloadDir | 获取 SDCard 外部存储下载路径 ( path /storage/emulated/0/Download ) |
| getDCIMPath | 获取 SDCard 外部存储数码相机图片路径 ( path /storage/emulated/0/DCIM ) |
| getDCIMDir | 获取 SDCard 外部存储数码相机图片路径 ( path /storage/emulated/0/DCIM ) |
| getDocumentsPath | 获取 SDCard 外部存储文档路径 ( path /storage/emulated/0/Documents ) |
| getDocumentsDir | 获取 SDCard 外部存储文档路径 ( path /storage/emulated/0/Documents ) |
| getAudiobooksPath | 获取 SDCard 外部存储有声读物路径 ( path /storage/emulated/0/Audiobooks ) |
| getAudiobooksDir | 获取 SDCard 外部存储有声读物路径 ( path /storage/emulated/0/Audiobooks ) |
| getAppDataPath | 获取应用外部存储数据路径 ( path /storage/emulated/0/Android/data/package ) |
| getAppDataDir | 获取应用外部存储数据路径 ( path /storage/emulated/0/Android/data/package ) |
| getAppCachePath | 获取应用外部存储缓存路径 ( path /storage/emulated/0/Android/data/package/cache ) |
| getAppCacheDir | 获取应用外部存储缓存路径 ( path /storage/emulated/0/Android/data/package/cache ) |
| getExternalFilesPath | 获取应用外部存储文件路径 ( path /storage/emulated/0/Android/data/package/files ) |
| getExternalFilesDir | 获取应用外部存储文件路径 ( path /storage/emulated/0/Android/data/package/files ) |
| getAppFilesPath | 获取应用外部存储文件路径 ( path /storage/emulated/0/Android/data/package/files ) |
| getAppFilesDir | 获取应用外部存储文件路径 ( path /storage/emulated/0/Android/data/package/files ) |
| getAppMusicPath | 获取应用外部存储音乐路径 ( path /storage/emulated/0/Android/data/package/files/Music ) |
| getAppMusicDir | 获取应用外部存储音乐路径 ( path /storage/emulated/0/Android/data/package/files/Music ) |
| getAppPodcastsPath | 获取应用外部存储播客路径 ( path /storage/emulated/0/Android/data/package/files/Podcasts ) |
| getAppPodcastsDir | 获取应用外部存储播客路径 ( path /storage/emulated/0/Android/data/package/files/Podcasts ) |
| getAppRingtonesPath | 获取应用外部存储铃声路径 ( path /storage/emulated/0/Android/data/package/files/Ringtones ) |
| getAppRingtonesDir | 获取应用外部存储铃声路径 ( path /storage/emulated/0/Android/data/package/files/Ringtones ) |
| getAppAlarmsPath | 获取应用外部存储闹铃路径 ( path /storage/emulated/0/Android/data/package/files/Alarms ) |
| getAppAlarmsDir | 获取应用外部存储闹铃路径 ( path /storage/emulated/0/Android/data/package/files/Alarms ) |
| getAppNotificationsPath | 获取应用外部存储通知路径 ( path /storage/emulated/0/Android/data/package/files/Notifications ) |
| getAppNotificationsDir | 获取应用外部存储通知路径 ( path /storage/emulated/0/Android/data/package/files/Notifications ) |
| getAppPicturesPath | 获取应用外部存储图片路径 ( path /storage/emulated/0/Android/data/package/files/Pictures ) |
| getAppPicturesDir | 获取应用外部存储图片路径 ( path /storage/emulated/0/Android/data/package/files/Pictures ) |
| getAppMoviesPath | 获取应用外部存储影片路径 ( path /storage/emulated/0/Android/data/package/files/Movies ) |
| getAppMoviesDir | 获取应用外部存储影片路径 ( path /storage/emulated/0/Android/data/package/files/Movies ) |
| getAppDownloadPath | 获取应用外部存储下载路径 ( path /storage/emulated/0/Android/data/package/files/Download ) |
| getAppDownloadDir | 获取应用外部存储下载路径 ( path /storage/emulated/0/Android/data/package/files/Download ) |
| getAppDCIMPath | 获取应用外部存储数码相机图片路径 ( path /storage/emulated/0/Android/data/package/files/DCIM ) |
| getAppDCIMDir | 获取应用外部存储数码相机图片路径 ( path /storage/emulated/0/Android/data/package/files/DCIM ) |
| getAppDocumentsPath | 获取应用外部存储文档路径 ( path /storage/emulated/0/Android/data/package/files/Documents ) |
| getAppDocumentsDir | 获取应用外部存储文档路径 ( path /storage/emulated/0/Android/data/package/files/Documents ) |
| getAppAudiobooksPath | 获取应用外部存储有声读物路径 ( path /storage/emulated/0/Android/data/package/files/Audiobooks ) |
| getAppAudiobooksDir | 获取应用外部存储有声读物路径 ( path /storage/emulated/0/Android/data/package/files/Audiobooks ) |
| getAppObbPath | 获取应用外部存储 OBB 路径 ( path /storage/emulated/0/Android/obb/package ) |
| getAppObbDir | 获取应用外部存储 OBB 路径 ( path /storage/emulated/0/Android/obb/package ) |
| getRootPath | 获取 Android 系统根目录 ( path /system ) |
| getRootDirectory | 获取 Android 系统根目录 ( path /system ) |
| getDataPath | 获取 data 目录 ( path /data ) |
| getDataDirectory | 获取 data 目录 ( path /data ) |
| getDownloadCachePath | 获取下载缓存目录 ( path data/cache ) |
| getDownloadCacheDirectory | 获取下载缓存目录 ( path data/cache ) |
| getAppCodeCachePath | 获取应用内部存储代码缓存路径 ( path /data/data/package/code_cache ) |
| getAppCodeCacheDir | 获取应用内部存储代码缓存路径 ( path /data/data/package/code_cache ) |
| getAppDbsPath | 获取应用内部存储数据库路径 ( path /data/data/package/databases ) |
| getAppDbsDir | 获取应用内部存储数据库路径 ( path /data/data/package/databases ) |
| getAppDbPath | 获取应用内部存储数据库路径 ( path /data/data/package/databases/name ) |
| getAppDbFile | 获取应用内部存储数据库路径 ( path /data/data/package/databases/name ) |
| getAppSpPath | 获取应用内部存储 SP 路径 ( path /data/data/package/shared_prefs ) |
| getAppSpDir | 获取应用内部存储 SP 路径 ( path /data/data/package/shared_prefs ) |
| getAppSpFile | 获取应用内部存储 SP 路径 ( path /data/data/package/shared_prefs ) |
| getAppNoBackupFilesPath | 获取应用内部存储未备份文件路径 ( path /data/data/package/no_backup ) |
| getAppNoBackupFilesDir | 获取应用内部存储未备份文件路径 ( path /data/data/package/no_backup ) |


* **手机相关工具类 ->** [PhoneUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/PhoneUtils.java)

| 方法 | 注释 |
| :- | :- |
| isPhone | 判断设备是否是手机 |
| getSimState | 获取 SIM 卡状态 |
| isSimReady | 判断是否装载 SIM 卡 |
| getSimCountryIso | 获取 SIM 卡运营商的国家代码 |
| getNetworkCountryIso | 获取 SIM 卡注册的网络运营商的国家代码 |
| getSimCountry | 获取 SIM 卡运营商的国家代码 |
| checkSimCountry | 判断 SIM 卡运营商是否国内 |
| getMEID | 获取 MEID 码 |
| getIMEI | 获取 IMEI 码 |
| getIMSI | 获取 IMSI 码 |
| getSimOperatorName | 获取 SIM 卡运营商名称 ( 如: 中国移动、如中国联通、中国电信 ) |
| getSimOperator | 获取 SIM 卡运营商 MCC + MNC |
| getChinaOperatorByIMSI | 通过 IMSI 获取中国运营商简称 |
| getChinaOperatorBySimOperator | 获取 SIM 卡中国运营商简称 |
| getPhoneType | 获取手机类型 |
| getDeviceId | 获取设备 id |
| getAndroidId | 获取 Android id |
| getSerialNumber | 获取设备序列号 |
| getSimSerialNumber | 获取 SIM 卡序列号 |
| getUUID | 获取设备唯一 UUID |
| getPhoneStatus | 获取手机状态信息 |
| dial | 跳至拨号界面 |
| call | 拨打电话 |
| sendSms | 跳至发送短信界面 |
| sendSmsSilent | 发送短信 |
| getContactNum | 打开手机联系人界面点击联系人后便获取该号码 |


* **电源管理工具类 ->** [PowerManagerUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/PowerManagerUtils.java)

| 方法 | 注释 |
| :- | :- |
| getInstance | 获取 PowerManagerUtils 实例 |
| acquire | 唤醒屏幕锁 |
| release | 释放屏幕锁 ( 允许休眠时间自动黑屏 ) |
| isScreenOn | 屏幕是否打开 ( 亮屏 ) |
| setBright | 设置屏幕常亮 |
| createWakeLockToBright | 创建 WakeLock 常亮配置 |
| getDefaultWakeLock | 获取默认 PowerManager.WakeLock |
| setDefaultWakeLock | 设置默认 PowerManager.WakeLock |
| turnScreenOn | 唤醒 / 点亮 屏幕 |
| turnScreenOff | 释放屏幕锁 ( 允许休眠时间自动黑屏 ) |


* **进程相关工具类 ->** [ProcessUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/ProcessUtils.java)

| 方法 | 注释 |
| :- | :- |
| kill | 销毁自身进程 |
| myPid | 获取自身进程 id |
| isCurProcess | 判断是否当前进程 |
| getCurProcessName | 获取当前进程名 |
| getProcessName | 获取进程 id 对应的进程名 |
| getPid | 根据包名获取进程 id |
| getRunningAppProcessInfo | 根据进程 id 获取进程信息 |
| getForegroundProcessName | 获取前台线程包名 |
| getAllBackgroundProcesses | 获取后台服务进程 |
| killAllBackgroundProcesses | 杀死所有的后台服务进程 |
| killBackgroundProcesses | 杀死后台服务进程 |


* **广播相关工具类 ->** [ReceiverUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/ReceiverUtils.java)

| 方法 | 注释 |
| :- | :- |
| getLocalBroadcastManager | 获取 LocalBroadcastManager |
| local_registerReceiver | 注册广播监听 ( 应用内广播 ) |
| local_unregisterReceiver | 注销广播监听 ( 应用内广播 ) |
| local_sendBroadcast | 发送广播 ( 应用内广播 ) |
| local_sendBroadcastSync | 发送广播 ( 同步 ) ( 应用内广播 ) |
| registerReceiverBool | 注册广播监听 |
| registerReceiver | 注册广播监听 |
| unregisterReceiver | 注销广播监听 |
| sendBroadcast | 发送广播 ( 无序 ) |
| sendOrderedBroadcast | 发送广播 ( 有序 ) |


* **RecyclerView 工具类 ->** [RecyclerViewUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/RecyclerViewUtils.java)

| 方法 | 注释 |
| :- | :- |
| getRecyclerView | 获取 RecyclerView |
| getLayoutParams | 获取 RecyclerView Item View LayoutParams |
| setLayoutManager | 设置 RecyclerView LayoutManager |
| getLayoutManager | 获取 RecyclerView LayoutManager |
| getLinearLayoutManager | 获取 LinearLayoutManager |
| getGridLayoutManager | 获取 GridLayoutManager |
| getStaggeredGridLayoutManager | 获取 StaggeredGridLayoutManager |
| setSpanCount | 设置 GridLayoutManager SpanCount |
| getSpanCount | 获取 GridLayoutManager SpanCount |
| getPosition | 获取 RecyclerView 对应 Item View 索引 |
| findViewByPosition | 获取 RecyclerView 对应索引 Item View |
| findFirstCompletelyVisibleItemPosition | 获取 RecyclerView 第一条完全显示 Item 索引 |
| findFirstCompletelyVisibleItemPositions | 获取 RecyclerView 第一条完全显示 Item 索引数组 |
| findLastCompletelyVisibleItemPosition | 获取 RecyclerView 最后一条完全显示 Item 索引 |
| findLastCompletelyVisibleItemPositions | 获取 RecyclerView 最后一条完全显示 Item 索引数组 |
| findFirstVisibleItemPosition | 获取 RecyclerView 第一条显示 Item 索引 |
| findFirstVisibleItemPositions | 获取 RecyclerView 第一条显示 Item 索引数组 |
| findLastVisibleItemPosition | 获取 RecyclerView 最后一条显示 Item 索引 |
| findLastVisibleItemPositions | 获取 RecyclerView 最后一条显示 Item 索引数组 |
| setOrientation | 设置 RecyclerView Orientation |
| getOrientation | 获取 RecyclerView Orientation |
| canScrollVertically | 校验 RecyclerView Orientation 是否为 VERTICAL |
| canScrollHorizontally | 校验 RecyclerView Orientation 是否为 HORIZONTAL |
| setAdapter | 设置 RecyclerView Adapter |
| getAdapter | 获取 RecyclerView Adapter |
| getItemCount | 获取 Adapter ItemCount |
| getItemId | 获取 Adapter 指定索引 Item Id |
| getItemViewType | 获取 Adapter 指定索引 Item Type |
| notifyItemRemoved | RecyclerView notifyItemRemoved |
| notifyItemInserted | RecyclerView notifyItemInserted |
| notifyItemMoved | RecyclerView notifyItemMoved |
| notifyDataSetChanged | RecyclerView notifyDataSetChanged |
| attachLinearSnapHelper | 设置 RecyclerView LinearSnapHelper |
| attachPagerSnapHelper | 设置 RecyclerView PagerSnapHelper |
| getItemDecorationCount | 获取 RecyclerView ItemDecoration 总数 |
| getItemDecorationAt | 获取 RecyclerView ItemDecoration |
| addItemDecoration | 添加 RecyclerView ItemDecoration |
| removeItemDecoration | 移除 RecyclerView ItemDecoration |
| removeItemDecorationAt | 移除 RecyclerView ItemDecoration |
| removeAllItemDecoration | 移除 RecyclerView 全部 ItemDecoration |
| setOnScrollListener | 设置 RecyclerView ScrollListener |
| addOnScrollListener | 添加 RecyclerView ScrollListener |
| removeOnScrollListener | 移除 RecyclerView ScrollListener |
| clearOnScrollListeners | 清空 RecyclerView ScrollListener |
| getScrollState | 获取 RecyclerView 滑动状态 |
| isNestedScrollingEnabled | 获取 RecyclerView 嵌套滚动开关 |
| setNestedScrollingEnabled | 设置 RecyclerView 嵌套滚动开关 |
| requestChildRectangleOnScreen | requestChildRectangleOnScreen |


* **APK Resource 工具类 ->** [ResourcePluginUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/ResourcePluginUtils.java)

| 方法 | 注释 |
| :- | :- |
| invokeByPackageName | 通过 packageName 获取 APK Resources |
| invokeByAPKPath | 通过 APK 文件获取 APK Resources |
| getResourceAssist | 获取 Resources 辅助类 |
| getResources | 获取 Resources |
| getPackageName | 获取 APK 包名 |
| getAPKPath | 获取 APK 文件路径 |
| getApkInfoItem | 获取 APK 信息 Item |
| getDisplayMetrics | 获取 DisplayMetrics |
| getConfiguration | 获取 Configuration |
| getAssets | 获取 AssetManager |
| getIdentifier | 获取资源 id |
| getResourceName | 获取给定资源标识符的全名 |
| getStringId | 获取 String id |
| getString | 获取 String |
| getDimenId | 获取 Dimension id |
| getDimension | 获取 Dimension |
| getDimensionInt | 获取 Dimension |
| getColorId | 获取 Color id |
| getColor | 获取 Color |
| getDrawableId | 获取 Drawable id |
| getDrawable | 获取 Drawable |
| getNinePatchDrawable | 获取 .9 Drawable |
| getBitmap | 获取 Bitmap |
| getMipmapId | 获取 Mipmap id |
| getDrawableMipmap | 获取 Mipmap Drawable |
| getNinePatchDrawableMipmap | 获取 Mipmap .9 Drawable |
| getBitmapMipmap | 获取 Mipmap Bitmap |
| getAnimId | 获取 Anim id |
| getAnimationXml | 获取 Animation Xml |
| getAnimation | 获取 Animation |
| getBooleanId | 获取 Boolean id |
| getBoolean | 获取 Boolean |
| getIntegerId | 获取 Integer id |
| getInteger | 获取 Integer |
| getArrayId | 获取 Array id |
| getIntArray | 获取 int[] |
| getStringArray | 获取 String[] |
| getTextArray | 获取 CharSequence[] |
| getId | 获取 id ( view ) |
| getLayoutId | 获取 Layout id |
| getMenuId | 获取 Menu id |
| getRawId | 获取 Raw id |
| getAttrId | 获取 Attr id |
| getStyleId | 获取 Style id |
| getStyleableId | 获取 Styleable id |
| getAnimatorId | 获取 Animator id |
| getXmlId | 获取 Xml id |
| getInterpolatorId | 获取 Interpolator id |
| getPluralsId | 获取 Plurals id |
| getColorStateList | 获取 ColorStateList |
| getColorDrawable | 获取十六进制颜色值 Drawable |
| open | 获取 AssetManager 指定资源 InputStream |
| openFd | 获取 AssetManager 指定资源 AssetFileDescriptor |
| openNonAssetFd | 获取 AssetManager 指定资源 AssetFileDescriptor |
| openRawResource | 获取对应资源 InputStream |
| openRawResourceFd | 获取对应资源 AssetFileDescriptor |
| readBytesFromAssets | 获取 Assets 资源文件数据 |
| readStringFromAssets | 获取 Assets 资源文件数据 |
| readBytesFromRaw | 获取 Raw 资源文件数据 |
| readStringFromRaw | 获取 Raw 资源文件数据 |
| geFileToListFromAssets | 获取 Assets 资源文件数据 ( 返回 List<String> 一行的全部内容属于一个索引 ) |
| geFileToListFromRaw | 获取 Raw 资源文件数据 ( 返回 List<String> 一行的全部内容属于一个索引 ) |
| saveAssetsFormFile | 获取 Assets 资源文件数据并保存到本地 |
| saveRawFormFile | 获取 Raw 资源文件数据并保存到本地 |
| createFromAsset | 获取 Assets 字体资源文件并创建 Typeface |
| createFromFile | 通过字体资源文件并创建 Typeface |
| create | 获取系统字体并设置字体样式 |


* **资源文件工具类 ->** [ResourceUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/ResourceUtils.java)

| 方法 | 注释 |
| :- | :- |
| getResources | 获取 Resources |
| getTheme | 获取 Resources.Theme |
| getContentResolver | 获取 ContentResolver |
| getDisplayMetrics | 获取 DisplayMetrics |
| getConfiguration | 获取 Configuration |
| getAssets | 获取 AssetManager |
| getIdentifier | 获取资源 id |
| getResourceName | 获取给定资源标识符的全名 |
| getStringId | 获取 String id |
| getString | 获取 String |
| getDimenId | 获取 Dimension id |
| getDimension | 获取 Dimension |
| getDimensionInt | 获取 Dimension |
| getColorId | 获取 Color id |
| getColor | 获取 Color |
| getDrawableId | 获取 Drawable id |
| getDrawable | 获取 Drawable |
| getNinePatchDrawable | 获取 .9 Drawable |
| getBitmap | 获取 Bitmap |
| getMipmapId | 获取 Mipmap id |
| getDrawableMipmap | 获取 Mipmap Drawable |
| getNinePatchDrawableMipmap | 获取 Mipmap .9 Drawable |
| getBitmapMipmap | 获取 Mipmap Bitmap |
| getAnimId | 获取 Anim id |
| getAnimationXml | 获取 Animation Xml |
| getAnimation | 获取 Animation |
| getBooleanId | 获取 Boolean id |
| getBoolean | 获取 Boolean |
| getIntegerId | 获取 Integer id |
| getInteger | 获取 Integer |
| getArrayId | 获取 Array id |
| getIntArray | 获取 int[] |
| getStringArray | 获取 String[] |
| getTextArray | 获取 CharSequence[] |
| getId | 获取 id ( view ) |
| getLayoutId | 获取 Layout id |
| getMenuId | 获取 Menu id |
| getRawId | 获取 Raw id |
| getAttrId | 获取 Attr id |
| getStyleId | 获取 Style id |
| getStyleableId | 获取 Styleable id |
| getAnimatorId | 获取 Animator id |
| getXmlId | 获取 Xml id |
| getInterpolatorId | 获取 Interpolator id |
| getPluralsId | 获取 Plurals id |
| getColorStateList | 获取 ColorStateList |
| getColorDrawable | 获取十六进制颜色值 Drawable |
| openInputStream | 获取 Uri InputStream |
| openOutputStream | 获取 Uri OutputStream |
| openFileDescriptor | 获取 Uri ParcelFileDescriptor |
| openAssetFileDescriptor | 获取 Uri AssetFileDescriptor |
| open | 获取 AssetManager 指定资源 InputStream |
| openFd | 获取 AssetManager 指定资源 AssetFileDescriptor |
| openNonAssetFd | 获取 AssetManager 指定资源 AssetFileDescriptor |
| openRawResource | 获取对应资源 InputStream |
| openRawResourceFd | 获取对应资源 AssetFileDescriptor |
| readBytesFromAssets | 获取 Assets 资源文件数据 |
| readStringFromAssets | 获取 Assets 资源文件数据 |
| readBytesFromRaw | 获取 Raw 资源文件数据 |
| readStringFromRaw | 获取 Raw 资源文件数据 |
| geFileToListFromAssets | 获取 Assets 资源文件数据 ( 返回 List<String> 一行的全部内容属于一个索引 ) |
| geFileToListFromRaw | 获取 Raw 资源文件数据 ( 返回 List<String> 一行的全部内容属于一个索引 ) |
| saveAssetsFormFile | 获取 Assets 资源文件数据并保存到本地 |
| saveRawFormFile | 获取 Raw 资源文件数据并保存到本地 |
| createFromAsset | 获取 Assets 字体资源文件并创建 Typeface |
| createFromFile | 通过字体资源文件并创建 Typeface |
| create | 获取系统字体并设置字体样式 |


* **ROM 相关工具类 ->** [ROMUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/ROMUtils.java)

| 方法 | 注释 |
| :- | :- |
| isHuawei | 判断 ROM 是否 Huawei ( 华为 ) |
| isHarmonyOS | 判断 ROM 是否 HarmonyOS ( 鸿蒙 ) |
| isHonor | 判断 ROM 是否 Honor ( 荣耀 ) |
| isVivo | 判断 ROM 是否 Vivo ( VIVO ) |
| isXiaomi | 判断 ROM 是否 Xiaomi ( 小米 ) |
| isOppo | 判断 ROM 是否 Oppo ( OPPO ) |
| isLeeco | 判断 ROM 是否 Leeco ( 乐视 ) |
| is360 | 判断 ROM 是否 360 ( 360 ) |
| isZte | 判断 ROM 是否 Zte ( 中兴 ) |
| isOneplus | 判断 ROM 是否 Oneplus ( 一加 ) |
| isNubia | 判断 ROM 是否 Nubia ( 努比亚 ) |
| isCoolpad | 判断 ROM 是否 Coolpad ( 酷派 ) |
| isLg | 判断 ROM 是否 Lg ( LG ) |
| isGoogle | 判断 ROM 是否 Google ( 谷歌 ) |
| isSamsung | 判断 ROM 是否 Samsung ( 三星 ) |
| isMeizu | 判断 ROM 是否 Meizu ( 魅族 ) |
| isLenovo | 判断 ROM 是否 Lenovo ( 联想 ) |
| isSmartisan | 判断 ROM 是否 Smartisan ( 锤子 ) |
| isHtc | 判断 ROM 是否 Htc ( HTC ) |
| isSony | 判断 ROM 是否 Sony ( 索尼 ) |
| isGionee | 判断 ROM 是否 Gionee ( 金立 ) |
| isMotorola | 判断 ROM 是否 Motorola ( 摩托罗拉 ) |
| getRomInfo | 获取 ROM 信息 |
| isRightRom | 是否匹配正确 ROM |


* **截图监听工具类 ->** [ScreenshotUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/ScreenshotUtils.java)

| 方法 | 注释 |
| :- | :- |
| getInstance | 获取 ScreenshotUtils 实例 |
| getStartListenTime | 获取开始监听时间 |
| isCheckPrefix | 是否判断文件名前缀 |
| setCheckPrefix | 设置是否判断文件名前缀 |
| getScreenshotChecker | 获取截图校验接口 |
| setScreenshotChecker | 设置截图校验接口 |
| getListener | 获取截图校验成功回调接口 |
| setListener | 设置截图校验成功回调接口 |
| startListener | 启动截图监听 |
| stopListener | 停止截图监听 |
| handleMediaContentChange | 内容变更处理 |
| handleMediaChecker | 内容校验处理 |


* **屏幕相关工具类 ->** [ScreenUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/ScreenUtils.java)

| 方法 | 注释 |
| :- | :- |
| getDisplayMetrics | 获取 DisplayMetrics |
| getScreenWidth | 获取屏幕宽度 |
| getScreenHeight | 获取屏幕高度 |
| getScreenWidthHeight | 获取屏幕宽高 |
| getScreenWidthHeightToPoint | 获取屏幕宽高 |
| getScreenSize | 获取屏幕分辨率 |
| getScreenSizeOfDevice | 获取屏幕英寸 ( 例 5.5 英寸 ) |
| getDensity | 获取屏幕密度 |
| getDensityDpi | 获取屏幕密度 dpi |
| getScaledDensity | 获取屏幕缩放密度 |
| getXDpi | 获取 X 轴 dpi |
| getYDpi | 获取 Y 轴 dpi |
| getWidthDpi | 获取宽度比例 dpi 基准 |
| getHeightDpi | 获取高度比例 dpi 基准 |
| getScreenInfo | 获取屏幕信息 |
| setWindowSecure | 设置禁止截屏 |
| isFullScreen | 是否屏幕为全屏 |
| setFullScreen | 设置屏幕为全屏 |
| setFullScreenNoTitle | 设置屏幕为全屏无标题 |
| setLandscape | 设置屏幕为横屏 |
| setPortrait | 设置屏幕为竖屏 |
| isLandscape | 判断是否横屏 |
| isPortrait | 判断是否竖屏 |
| toggleScreenOrientation | 切换屏幕方向 |
| getScreenRotation | 获取屏幕旋转角度 |
| isScreenLock | 判断是否锁屏 |
| isTablet | 判断是否是平板 |
| getStatusBarHeight | 获取 StatusBar 高度 |
| getStatusBarHeight2 | 获取 StatusBar 高度 |
| setSleepDuration | 设置进入休眠时长 |
| getSleepDuration | 获取进入休眠时长 |
| getNavigationBarHeight | 获取底部导航栏高度 |
| checkDeviceHasNavigationBar | 检测是否具有底部导航栏 |


* **SDCard 工具类 ->** [SDCardUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/SDCardUtils.java)

| 方法 | 注释 |
| :- | :- |
| isSDCardEnable | 判断 SDCard 是否正常挂载 |
| getSDCardFile | 获取 SDCard 外部存储路径 ( path /storage/emulated/0/ ) |
| getSDCardPath | 获取 SDCard 外部存储路径 ( path /storage/emulated/0/ ) |
| isSDCardEnablePath | 判断 SDCard 是否可用 |
| getSDCardPaths | 获取 SDCard 路径 |
| getAllBlockSize | 获取内置 SDCard 空间总大小 |
| getAllBlockSizeFormat | 获取内置 SDCard 空间总大小 |
| getAvailableBlocks | 获取内置 SDCard 空闲空间大小 |
| getAvailableBlocksFormat | 获取内置 SDCard 空闲空间大小 |
| getUsedBlocks | 获取内置 SDCard 已使用空间大小 |
| getUsedBlocksFormat | 获取内置 SDCard 已使用空间大小 |
| getBlockSizeInfos | 返回内置 SDCard 空间大小信息 |


* **服务相关工具类 ->** [ServiceUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/ServiceUtils.java)

| 方法 | 注释 |
| :- | :- |
| isServiceRunning | 判断服务是否运行 |
| getAllRunningService | 获取所有运行的服务 |
| startService | 启动服务 |
| stopService | 停止服务 |
| bindService | 绑定服务 |
| unbindService | 解绑服务 |


* **Shape 工具类 ->** [ShapeUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/ShapeUtils.java)

| 方法 | 注释 |
| :- | :- |
| getDrawable | 获取 GradientDrawable |
| setDrawable | 设置 Drawable 背景 |
| getOrientation | 获取渐变角度 |
| newShape | 创建 Shape |
| setAlpha | 设置透明度 |
| setShape | 设置形状类型 |
| setInnerRadius | 设置内环半径 |
| setInnerRadiusRatio | 设置内环半径相对于环的宽度比例 |
| setThickness | 设置环厚度 |
| setThicknessRatio | 设置环厚度相对于环的宽度比例 |
| setColor | 设置背景填充颜色 |
| setStroke | 设置描边 |
| setCornerRadius | 设置圆角 |
| setCornerRadiusLeft | 设置 leftTop、leftBottom 圆角 |
| setCornerRadiusRight | 设置 rightTop、rightBottom 圆角 |
| setCornerRadiusTop | 设置 leftTop、rightTop 圆角 |
| setCornerRadiusBottom | 设置 leftBottom、rightBottom 圆角 |
| setColors | 设置渐变颜色 |
| setGradientType | 设置渐变类型 |
| setOrientation | 设置渐变角度 |
| setGradientCenter | 设置渐变中心坐标值 |
| setGradientRadius | 设置渐变色半径 |
| setUseLevel | 是否使用 LevelListDrawable |
| setPadding | 设置内边距 |
| setSize | 设置 shape drawable 宽高 |


* **Shell 命令工具类 ->** [ShellUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/ShellUtils.java)

| 方法 | 注释 |
| :- | :- |
| execCmd | 执行 shell 命令 |
| isSuccess | 判断是否执行成功 |
| isSuccess2 | 判断是否执行成功 ( 判断 errorMsg ) |
| isSuccess3 | 判断是否执行成功 ( 判断 successMsg ) |
| isSuccess4 | 判断是否执行成功 ( 判断 successMsg ) , 并且 successMsg 是否包含某个字符串 |


* **快捷方式工具类 ->** [ShortCutUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/ShortCutUtils.java)

| 方法 | 注释 |
| :- | :- |
| hasShortcut | 检测是否存在桌面快捷方式 |
| getShortCutIntent | 获取桌面快捷方式点击 Intent |
| addShortcut | 创建桌面快捷方式 |
| deleteShortcut | 删除桌面快捷方式 |


* **签名工具类 ( 获取 APP 签名信息 ) ->** [SignaturesUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/SignaturesUtils.java)

| 方法 | 注释 |
| :- | :- |
| getAppSignature | 获取 APP Signature |
| signatureMD5 | 获取 MD5 签名 |
| signatureSHA1 | 获取签名 SHA1 加密字符串 |
| signatureSHA256 | 获取签名 SHA256 加密字符串 |
| isDebuggable | 判断 debug 签名还是 release 签名 |
| getX509Certificate | 获取证书对象 |
| printSignatureInfo | 打印签名信息 |
| getSignaturesFromApk | 从 APK 中读取签名 |
| getCertificateFromApk | 从 APK 中读取签名 |


* **大小工具类 ( dp, px, sp 转换、View 获取宽高等 ) ->** [SizeUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/SizeUtils.java)

| 方法 | 注释 |
| :- | :- |
| dp2px | dp 转 px |
| dp2pxf | dp 转 px ( float ) |
| px2dp | px 转 dp |
| px2dpf | px 转 dp ( float ) |
| sp2px | sp 转 px |
| sp2pxf | sp 转 px ( float ) |
| px2sp | px 转 sp |
| px2spf | px 转 sp ( float ) |
| applyDimension | 各种单位转换 ( 该方法存在于 TypedValue.applyDimension ) |
| forceGetViewSize | 在 onCreate 中获取视图的尺寸 ( 需回调 onGetSizeListener 接口, 在 onGetSize 中获取 View 宽高 ) |
| measureView | 测量 View |
| getMeasuredWidth | 获取 View 的宽度 |
| getMeasuredHeight | 获取 View 的高度 |


* **Snackbar 工具类 ->** [SnackbarUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/SnackbarUtils.java)

| 方法 | 注释 |
| :- | :- |
| with | 获取 SnackbarUtils 对象 |
| getStyle | 获取样式 |
| setStyle | 设置样式 |
| getSnackbar | 获取 Snackbar |
| getSnackbarView | 获取 Snackbar View |
| getTextView | 获取 Snackbar TextView ( snackbar_text ) |
| getActionButton | 获取 Snackbar Action Button ( snackbar_action ) |
| getSnackbarLayout | 获取 Snackbar.SnackbarLayout ( FrameLayout ) |
| getSnackbarContentLayout | 获取 SnackbarContentLayout ( LinearLayout ( messageView、actionView ) ) |
| addView | 向 Snackbar 布局中添加 View ( Google 不建议, 复杂的布局应该使用 DialogFragment 进行展示 ) |
| setCallback | 设置 Snackbar 展示完成、隐藏完成 的监听 |
| setAction | 设置 Action 按钮文字内容及点击监听 |
| dismiss | 关闭 Snackbar |
| showShort | 显示 Short Snackbar |
| showLong | 显示 Long Snackbar |
| showIndefinite | 显示 Indefinite Snackbar ( 无限时, 一直显示 ) |
| setSnackbarStyle | 设置 Snackbar 样式配置 |
| getShadowMargin | 获取阴影边距 |
| setShadowMargin | 设置阴影边距 |
| isAutoCalc | 判断是否自动计算边距 ( 如: 显示在 View 下面, 但是下方距离不够, 自动设置为在 View 上方显示 ) |
| setAutoCalc | 设置是否自动计算边距 ( 如: 显示在 View 下面, 但是下方距离不够, 自动设置为在 View 上方显示 ) |
| above | 设置 Snackbar 显示在指定 View 的上方 |
| bellow | 设置 Snackbar 显示在指定 View 的下方 |


* **SpannableString 工具类 ->** [SpanUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/SpanUtils.java)

| 方法 | 注释 |
| :- | :- |
| with | 获取持有 TextView SpannableString Utils |
| setFlag | 设置标识 |
| setForegroundColor | 设置前景色 |
| setBackgroundColor | 设置背景色 |
| setLineHeight | 设置行高 |
| setQuoteColor | 设置引用线的颜色 |
| setLeadingMargin | 设置缩进 |
| setBullet | 设置列表标记 |
| setFontSize | 设置字体尺寸 |
| setFontProportion | 设置字体比例 |
| setFontXProportion | 设置字体横向比例 |
| setStrikethrough | 设置删除线 |
| setUnderline | 设置下划线 |
| setSuperscript | 设置上标 |
| setSubscript | 设置下标 |
| setBold | 设置粗体 |
| setItalic | 设置斜体 |
| setBoldItalic | 设置粗斜体 |
| setFontFamily | 设置字体系列 |
| setTypeface | 设置字体 |
| setHorizontalAlign | 设置水平对齐 |
| setVerticalAlign | 设置垂直对齐 |
| setClickSpan | 设置点击事件 |
| setUrl | 设置超链接 |
| setBlur | 设置模糊 |
| setShader | 设置着色器 |
| setShadow | 设置阴影 |
| setSpans | 自定义 setSpan 参数 |
| append | 追加文本 |
| appendLine | 追加换行 |
| appendImage | 追加 Image |
| appendSpace | 追加空格 |
| get | 获取 SpannableStringBuilder |
| create | 创建 SpannableStringBuilder |


* **颜色状态列表工具类 ->** [StateListUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/StateListUtils.java)

| 方法 | 注释 |
| :- | :- |
| getColorStateList | 获取 ColorStateList |
| createColorStateList | 创建 ColorStateList |
| newSelector | 创建 StateListDrawable |


* **TextView 工具类 ->** [TextViewUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/TextViewUtils.java)

| 方法 | 注释 |
| :- | :- |
| getTextView | 获取 TextView |
| getHint | 获取 Hint 文本 |
| getHints | 获取多个 TextView Hint 文本 |
| setHint | 设置 Hint 文本 |
| getHintTextColors | 获取 Hint 字体颜色 |
| setHintTextColor | 设置 Hint 字体颜色 |
| setHintTextColors | 设置多个 TextView Hint 字体颜色 |
| getText | 获取文本 |
| getTexts | 获取多个 TextView 文本 |
| setText | 设置文本 |
| setTexts | 设置多个 TextView 文本 |
| getTextColors | 获取字体颜色 |
| setTextColor | 设置字体颜色 |
| setTextColors | 设置多个 TextView 字体颜色 |
| setHtmlText | 设置 Html 内容 |
| setHtmlTexts | 设置多个 TextView Html 内容 |
| getTypeface | 获取字体 |
| setTypeface | 设置字体 |
| setTextSizeByPx | 设置字体大小 ( px 像素 ) |
| setTextSizeBySp | 设置字体大小 ( sp 缩放像素 ) |
| setTextSizeByDp | 设置字体大小 ( dp 与设备无关的像素 ) |
| setTextSizeByIn | 设置字体大小 ( inches 英寸 ) |
| setTextSize | 设置字体大小 |
| setTextSizes | 设置多个 TextView 字体大小 |
| getTextSize | 获取 TextView 字体大小 ( px ) |
| clearFlags | 清空 flags |
| setPaintFlags | 设置 TextView flags |
| setAntiAliasFlag | 设置 TextView 抗锯齿 flags |
| setBold | 设置 TextView 是否加粗 |
| setUnderlineText | 设置下划线 |
| setStrikeThruText | 设置中划线 |
| getLetterSpacing | 获取文字水平间距 |
| setLetterSpacing | 设置文字水平间距 |
| getLineSpacingExtra | 获取文字行间距 ( 行高 ) |
| getLineSpacingMultiplier | 获取文字行间距倍数 |
| setLineSpacing | 设置文字行间距 ( 行高 ) |
| setLineSpacingAndMultiplier | 设置文字行间距 ( 行高 ) 、行间距倍数 |
| getTextScaleX | 获取字体水平方向的缩放 |
| setTextScaleX | 设置字体水平方向的缩放 |
| getIncludeFontPadding | 是否保留字体留白间隙区域 |
| setIncludeFontPadding | 设置是否保留字体留白间隙区域 |
| getInputType | 获取输入类型 |
| setInputType | 设置输入类型 |
| getImeOptions | 获取软键盘右下角按钮类型 |
| setImeOptions | 设置软键盘右下角按钮类型 |
| setLines | 设置行数 |
| getMaxLines | 获取最大行数 |
| setMaxLines | 设置最大行数 |
| getMinLines | 获取最小行数 |
| setMinLines | 设置最小行数 |
| getMaxEms | 获取最大字符宽度限制 |
| setMaxEms | 设置最大字符宽度限制 |
| getMinEms | 获取最小字符宽度限制 |
| setMinEms | 设置最小字符宽度限制 |
| setEms | 设置指定字符宽度 |
| setMaxLength | 设置长度限制 |
| setMaxLengthAndText | 设置长度限制, 并且设置内容 |
| getEllipsize | 获取 Ellipsize 效果 |
| setEllipsize | 设置 Ellipsize 效果 |
| getAutoLinkMask | 获取自动识别文本类型 |
| setAutoLinkMask | 设置自动识别文本链接 |
| setAllCaps | 设置文本全为大写 |
| getGravity | 获取 Gravity |
| setGravity | 设置 Gravity |
| getTransformationMethod | 获取文本视图显示转换 |
| setTransformationMethod | 设置文本视图显示转换 |
| getPaint | 获取 TextView Paint |
| getTextHeight | 获取字体高度 |
| getTextTopOffsetHeight | 获取字体顶部偏移高度 |
| getTextWidth | 计算字体宽度 |
| getCenterRectY | 获取画布中间居中位置 |
| reckonTextSizeByHeight | 通过需要的高度, 计算字体大小 |
| reckonTextSizeByWidth | 通过需要的宽度, 计算字体大小 ( 最接近该宽度的字体大小 ) |
| calcTextWidth | 计算第几位超过宽度 |
| calcTextLine | 计算文本换行行数 |
| getCompoundDrawables | 获取 CompoundDrawables |
| getCompoundDrawablePadding | 获取 CompoundDrawables Padding |
| setCompoundDrawablePadding | 设置 CompoundDrawables Padding |
| setCompoundDrawablesByLeft | 设置 Left CompoundDrawables |
| setCompoundDrawablesByTop | 设置 Top CompoundDrawables |
| setCompoundDrawablesByRight | 设置 Right CompoundDrawables |
| setCompoundDrawablesByBottom | 设置 Bottom CompoundDrawables |
| setCompoundDrawables | 设置 CompoundDrawables |
| setCompoundDrawablesWithIntrinsicBoundsByLeft | 设置 Left CompoundDrawables ( 按照原有比例大小显示图片 ) |
| setCompoundDrawablesWithIntrinsicBoundsByTop | 设置 Top CompoundDrawables ( 按照原有比例大小显示图片 ) |
| setCompoundDrawablesWithIntrinsicBoundsByRight | 设置 Right CompoundDrawables ( 按照原有比例大小显示图片 ) |
| setCompoundDrawablesWithIntrinsicBoundsByBottom | 设置 Bottom CompoundDrawables ( 按照原有比例大小显示图片 ) |
| setCompoundDrawablesWithIntrinsicBounds | 设置 CompoundDrawables ( 按照原有比例大小显示图片 ) |
| setAutoSizeTextTypeWithDefaults | 通过设置默认的自动调整大小配置, 决定是否自动缩放文本 |
| setAutoSizeTextTypeUniformWithConfiguration | 设置 TextView 自动调整字体大小配置 |
| setAutoSizeTextTypeUniformWithPresetSizes | 设置 TextView 自动调整如果预设字体大小范围有效则修改类型为 AUTO_SIZE_TEXT_TYPE_UNIFORM |
| getAutoSizeTextType | 获取 TextView 自动调整大小类型 |
| getAutoSizeStepGranularity | 获取 TextView 自动调整大小变动粒度 ( 跨度区间值 ) |
| getAutoSizeMinTextSize | 获取 TextView 自动调整最小字体大小 |
| getAutoSizeMaxTextSize | 获取 TextView 自动调整最大字体大小 |
| getAutoSizeTextAvailableSizes | 获取 TextView 自动调整大小预设范围数组 |


* **Uri 工具类 ->** [UriUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/UriUtils.java)

| 方法 | 注释 |
| :- | :- |
| getUriForFile | 获取 FileProvider File Uri |
| getUriForPath | 获取 FileProvider File Path Uri |
| getUriForFileToName | 获取 FileProvider File Path Uri ( 自动添加包名 ${applicationId} ) |
| getUriForString | 通过 String 获取 Uri |
| fromFile | 通过 File Path 创建 Uri |
| ofUri | 通过 String 获取 Uri |
| isUri | 判断是否 Uri |
| getUriScheme | 获取 Uri Scheme |
| isUriExists | 判断 Uri 路径资源是否存在 |
| getMediaUri | 通过 File 获取 Media Uri |
| copyByUri | 通过 Uri 复制文件 |
| getFilePathByUri | 通过 Uri 获取文件路径 |
| isExternalStorageDocument | 判读 Uri authority 是否为 ExternalStorage Provider |
| isDownloadsDocument | 判读 Uri authority 是否为 Downloads Provider |
| isMediaDocument | 判断 Uri authority 是否为 Media Provider |
| isGooglePhotosUri | 判断 Uri authority 是否为 Google Photos Provider |
| isAndroidResourceScheme | 判断 Uri Scheme 是否 ContentResolver.SCHEME_ANDROID_RESOURCE |
| isFileScheme | 判断 Uri Scheme 是否 ContentResolver.SCHEME_FILE |
| isContentScheme | 判断 Uri Scheme 是否 ContentResolver.SCHEME_CONTENT |
| isUriScheme | 判断是否指定的 Uri Scheme |


* **版本工具类 ->** [VersionUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/VersionUtils.java)

| 方法 | 注释 |
| :- | :- |
| getSDKVersion | 获取 SDK 版本 |
| isEclair | 是否在 2.1 版本及以上 |
| isFroyo | 是否在 2.2 版本及以上 |
| isGingerbread | 是否在 2.3 版本及以上 |
| isGingerbreadMR1 | 是否在 2.3.3 版本及以上 |
| isHoneycomb | 是否在 3.0 版本及以上 |
| isHoneycombMR1 | 是否在 3.1 版本及以上 |
| isIceCreamSandwich | 是否在 4.0 版本及以上 |
| isIceCreamSandwichMR1 | 是否在 4.0.3 版本及以上 |
| isJellyBean | 是否在 4.1 版本及以上 |
| isJellyBeanMR1 | 是否在 4.2 版本及以上 |
| isJellyBeanMR2 | 是否在 4.3 版本及以上 |
| isKitkat | 是否在 4.4.2 版本及以上 |
| isKitkat_Watch | 是否在 4.4W 版本及以上 |
| isLollipop | 是否在 5.0 版本及以上 |
| isLollipop_MR1 | 是否在 5.1 版本及以上 |
| isM | 是否在 6.0 版本及以上 |
| isN | 是否在 7.0 版本及以上 |
| isN_MR1 | 是否在 7.1.1 版本及以上 |
| isO | 是否在 8.0 版本及以上 |
| isO_MR1 | 是否在 8.1 版本及以上 |
| isP | 是否在 9.0 版本及以上 |
| isQ | 是否在 10.0 版本及以上 |
| isR | 是否在 11.0 版本及以上 |
| isS | 是否在 12.0 版本及以上 |
| isSV2 | 是否在 12.0 L 版本及以上 |
| isTiramisu | 是否在 13.0 版本及以上 |
| isUpsideDownCake | 是否在 14.0 版本及以上 |
| isVanillaIceCream | 是否在 15.0 版本及以上 |
| convertSDKVersion | 转换 SDK 版本 convertSDKVersion(31) = Android 12.0 |
| convertSDKVersionName | 转换 SDK 版本名字 convertSDKVersionName(31) = Android S |


* **震动相关工具类 ->** [VibrationUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/VibrationUtils.java)

| 方法 | 注释 |
| :- | :- |
| vibrate | 震动 |
| cancel | 取消震动 |


* **View 操作相关工具类 ->** [ViewUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/ViewUtils.java)

| 方法 | 注释 |
| :- | :- |
| getContext | 获取 View Context |
| getActivity | 获取 View Context 所属的 Activity |
| inflate | 获取 View |
| getId | 获取 View Id |
| setId | 设置 View Id |
| getParent | 获取指定 View 父布局 |
| getContentView | 获取 android.R.id.content View |
| getRootParent | 获取指定 View 根布局 ( 最底层布局 ) |
| getClipChildren | 获取是否限制子 View 在其边界内绘制 |
| setClipChildren | 设置是否限制子 View 在其边界内绘制 |
| getChildCount | 获取子 View 总数 |
| getChildAt | 获取指定索引 View |
| getChildAtLast | 获取最后一个索引 View |
| removeAllViews | 移除全部子 View |
| getChilds | 获取全部子 View |
| addView | 添加 View |
| getLayoutParams | 获取 LayoutParams |
| setLayoutParams | 设置 View LayoutParams |
| findViewById | 初始化 View |
| convertView | 转换 View |
| convertViewGroup | 转换 ViewGroup |
| isEmpty | 判断 View 是否为 null |
| isNotEmpty | 判断 View 是否不为 null |
| getWidthHeight | 获取 View 宽高 |
| setWidthHeight | 设置 View 宽度、高度 |
| setWeight | 设置 View weight 权重 |
| getWidthHeightExact | 获取 View 宽高 ( 准确 ) |
| getWidthHeightExact2 | 获取 View 宽高 ( 准确 ) |
| getLocationOnScreen | 获取 View 在屏幕中坐标区域 |
| getLocationInWindow | 获取 View 在父窗口中坐标区域 |
| getGlobalVisibleRect | 获取 View 在屏幕中可见的坐标区域 |
| getLocalVisibleRect | 获取 View 本身可见的坐标区域 |
| isCompletelyVisible | 判断 View 是否完全显示 |
| getWidth | 获取 View 宽度 |
| setWidth | 设置 View 宽度 |
| getHeight | 获取 View 高度 |
| setHeight | 设置 View 高度 |
| getMinimumHeight | 获取 View 最小高度 |
| setMinimumHeight | 设置 View 最小高度 |
| getMinimumWidth | 获取 View 最小宽度 |
| setMinimumWidth | 设置 View 最小宽度 |
| getAlpha | 获取 View 透明度 |
| setAlpha | 设置 View 透明度 |
| getTag | 获取 View TAG |
| setTag | 设置 View TAG |
| scrollTo | View 内容滚动位置 ( 相对于初始位置移动 ) |
| scrollBy | View 内部滚动位置 ( 相对于上次移动的最后位置移动 ) |
| setScrollX | 设置 View 滑动的 X 轴坐标 |
| setScrollY | 设置 View 滑动的 Y 轴坐标 |
| getScrollX | 获取 View 滑动的 X 轴坐标 |
| getScrollY | 获取 View 滑动的 Y 轴坐标 |
| setDescendantFocusability | 设置 ViewGroup 和其子控件两者之间的关系 |
| setOverScrollMode | 设置 View 滚动模式 |
| isHorizontalScrollBarEnabled | 是否绘制横向滚动条 |
| setHorizontalScrollBarEnabled | 设置是否绘制横向滚动条 |
| isVerticalScrollBarEnabled | 是否绘制垂直滚动条 |
| setVerticalScrollBarEnabled | 设置是否绘制垂直滚动条 |
| isScrollContainer | 获取 View 是否需要滚动效应 |
| setScrollContainer | 设置 View 滚动效应 |
| getClipToOutline | 获取 View 是否使用 Outline 来裁剪 |
| setClipToOutline | 设置 View 是否使用 Outline 来裁剪 |
| getOutlineProvider | 获取 View 轮廓裁剪、绘制 |
| setOutlineProvider | 设置 View 轮廓裁剪、绘制 |
| setOutlineProviderClip | 设置 View 轮廓裁剪、绘制并进行裁剪 |
| getNextFocusForwardId | 下一个获取焦点的 View id |
| setNextFocusForwardId | 设置下一个获取焦点的 View id |
| getNextFocusDownId | 向下移动焦点时, 下一个获取焦点的 View id |
| setNextFocusDownId | 设置向下移动焦点时, 下一个获取焦点的 View id |
| getNextFocusLeftId | 向左移动焦点时, 下一个获取焦点的 View id |
| setNextFocusLeftId | 设置向左移动焦点时, 下一个获取焦点的 View id |
| getNextFocusRightId | 向右移动焦点时, 下一个获取焦点的 View id |
| setNextFocusRightId | 设置向右移动焦点时, 下一个获取焦点的 View id |
| getNextFocusUpId | 向上移动焦点时, 下一个获取焦点的 View id |
| setNextFocusUpId | 设置向上移动焦点时, 下一个获取焦点的 View id |
| getRotation | 获取 View 旋转度数 |
| setRotation | 设置 View 旋转度数 |
| getRotationX | 获取 View 水平旋转度数 |
| setRotationX | 设置 View 水平旋转度数 |
| getRotationY | 获取 View 竖直旋转度数 |
| setRotationY | 设置 View 竖直旋转度数 |
| getScaleX | 获取 View 水平方向缩放比例 |
| setScaleX | 设置 View 水平方向缩放比例 |
| getScaleY | 获取 View 竖直方向缩放比例 |
| setScaleY | 设置 View 竖直方向缩放比例 |
| getTextAlignment | 获取文本的显示方式 |
| setTextAlignment | 设置文本的显示方式 |
| getTextDirection | 获取文本的显示方向 |
| setTextDirection | 设置文本的显示方向 |
| getPivotX | 获取水平方向偏转量 |
| setPivotX | 设置水平方向偏转量 |
| getPivotY | 获取竖直方向偏转量 |
| setPivotY | 设置竖直方向偏转量 |
| getTranslationX | 获取水平方向的移动距离 |
| setTranslationX | 设置水平方向的移动距离 |
| getTranslationY | 获取竖直方向的移动距离 |
| setTranslationY | 设置竖直方向的移动距离 |
| getX | 获取 X 轴位置 |
| setX | 设置 X 轴位置 |
| getY | 获取 Y 轴位置 |
| setY | 设置 Y 轴位置 |
| getLayerType | 获取 View 硬件加速类型 |
| setLayerType | 设置 View 硬件加速类型 |
| requestLayout | 请求重新对 View 布局 |
| requestFocus | View 请求获取焦点 |
| clearFocus | View 清除焦点 |
| findFocus | 获取 View 里获取焦点的 View |
| isFocused | 获取是否当前 View 就是焦点 View |
| hasFocus | 获取当前 View 是否是焦点 View 或者子 View 里面有焦点 View |
| hasFocusable | 获取当前 View 或者子 View 是否可以获取焦点 |
| isFocusableInTouchMode | 获取 View 是否在触摸模式下获得焦点 |
| setFocusableInTouchMode | 设置 View 是否在触摸模式下获得焦点 |
| isFocusable | 获取 View 是否可以获取焦点 |
| setFocusable | 设置 View 是否可以获取焦点 |
| toggleFocusable | 切换获取焦点状态 |
| isSelected | 获取 View 是否选中 |
| setSelected | 设置 View 是否选中 |
| toggleSelected | 切换选中状态 |
| isEnabled | 获取 View 是否启用 |
| setEnabled | 设置 View 是否启用 |
| toggleEnabled | 切换 View 是否启用状态 |
| isClickable | 获取 View 是否可以点击 |
| setClickable | 设置 View 是否可以点击 |
| toggleClickable | 切换 View 是否可以点击状态 |
| isLongClickable | 获取 View 是否可以长按 |
| setLongClickable | 设置 View 是否可以长按 |
| toggleLongClickable | 切换 View 是否可以长按状态 |
| isShown | 判断 View 是否显示 ( 如果存在父级则判断父级 ) |
| isShowns | 判断 View 是否都显示 ( 如果存在父级则判断父级 ) |
| isVisibility | 判断 View 是否显示 |
| isVisibilitys | 判断 View 是否都显示 |
| isVisibilityIN | 判断 View 是否隐藏占位 |
| isVisibilityINs | 判断 View 是否都隐藏占位 |
| isVisibilityGone | 判断 View 是否隐藏 |
| isVisibilityGones | 判断 View 是否都隐藏 |
| getVisibility | 获取显示的状态 ( View.VISIBLE : View.GONE ) |
| getVisibilityIN | 获取显示的状态 ( View.VISIBLE : View.INVISIBLE ) |
| setVisibility | 设置 View 显示的状态 |
| setVisibilityIN | 设置 View 显示的状态 |
| setVisibilitys | 设置 View 显示的状态 |
| setVisibilityINs | 设置 View 显示的状态 |
| toggleVisibilitys | 切换 View 显示的状态 |
| reverseVisibilitys | 反转 View 显示的状态 |
| toggleView | 切换 View 状态 |
| toggleViews | 切换 View 状态 |
| viewVisible | 设置 View 显示状态 |
| viewVisibles | 设置 View 显示状态 |
| viewGone | 设置 View 隐藏状态 |
| viewGones | 设置 View 隐藏状态 |
| viewInVisible | 设置 View 隐藏占位状态 |
| viewInVisibles | 设置 View 隐藏占位状态 |
| removeSelfFromParent | 把自身从父 View 中移除 |
| isTouchInView | 判断触点是否落在该 View 上 |
| requestLayoutParent | View 请求更新 |
| measureView | 测量 View |
| getMeasuredWidth | 获取 View 的宽度 |
| getMeasuredHeight | 获取 View 的高度 |
| getLayoutGravity | 获取 View Layout Gravity |
| setLayoutGravity | 设置 View Layout Gravity |
| getMarginLeft | 获取 View Left Margin |
| getMarginTop | 获取 View Top Margin |
| getMarginRight | 获取 View Right Margin |
| getMarginBottom | 获取 View Bottom Margin |
| getMargin | 获取 View Margin |
| setMarginLeft | 设置 View Left Margin |
| setMarginTop | 设置 View Top Margin |
| setMarginRight | 设置 View Right Margin |
| setMarginBottom | 设置 View Bottom Margin |
| setMargin | 设置 Margin 边距 |
| getPaddingLeft | 获取 View Left Padding |
| getPaddingTop | 获取 View Top Padding |
| getPaddingRight | 获取 View Right Padding |
| getPaddingBottom | 获取 View Bottom Padding |
| getPadding | 获取 View Padding |
| setPaddingLeft | 设置 View Left Padding |
| setPaddingTop | 设置 View Top Padding |
| setPaddingRight | 设置 View Right Padding |
| setPaddingBottom | 设置 View Bottom Padding |
| setPadding | 设置 Padding 边距 |
| addRule | 设置 RelativeLayout View 布局规则 |
| removeRule | 移除 RelativeLayout View 布局规则 |
| getRule | 获取 RelativeLayout View 指定布局位置 View id |
| addRules | 设置多个 RelativeLayout View 布局规则 |
| removeRules | 移除多个 RelativeLayout View 布局规则 |
| setAnimation | 设置动画 |
| getAnimation | 获取动画 |
| clearAnimation | 清空动画 |
| startAnimation | 启动动画 |
| cancelAnimation | 取消动画 |
| setBackground | 设置背景图片 |
| setBackgroundColor | 设置背景颜色 |
| setBackgroundResource | 设置背景资源 |
| setBackgroundTintList | 设置背景着色颜色 |
| setBackgroundTintMode | 设置背景着色模式 |
| setForeground | 设置前景图片 |
| setForegroundGravity | 设置前景重心 |
| setForegroundTintList | 设置前景着色颜色 |
| setForegroundTintMode | 设置前景着色模式 |
| getBackground | 获取 View 背景 Drawable |
| getBackgroundTintList | 获取 View 背景着色颜色 |
| getBackgroundTintMode | 获取 View 背景着色模式 |
| getForeground | 获取 View 前景 Drawable |
| getForegroundGravity | 获取 View 前景重心 |
| getForegroundTintList | 获取 View 前景着色颜色 |
| getForegroundTintMode | 获取 View 前景着色模式 |
| removeBackground | 移除背景图片 |
| removeAllBackground | 移除背景图片 ( background、imageDrawable ) |
| removeForeground | 移除前景图片 |
| setColorFilter | View 着色处理 |
| setProgressDrawable | 设置 ProgressBar 进度条样式 |
| setBarProgress | 设置 ProgressBar 进度值 |
| setBarMax | 设置 ProgressBar 最大值 |
| setBarValue | 设置 ProgressBar 最大值 |
| onWidthHeight | 获取宽高回调 |


* **壁纸工具类 ->** [WallpaperUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/WallpaperUtils.java)

| 方法 | 注释 |
| :- | :- |
| isWallpaperSupported | 是否支持壁纸 |
| isSetWallpaperAllowed | 是否支持设置壁纸 |
| hasResourceWallpaper | 判断当前壁纸是否使用给定的资源 Id |
| forgetLoadedWallpaper | 删除所有内部引用到最后加载的壁纸 |
| clear | 删除壁纸 ( 恢复为系统内置桌面壁纸 ) |
| clearWallpaper | 删除壁纸 ( 恢复为系统内置壁纸 ) |
| getWallpaperId | 获取当前壁纸 Id |
| getWallpaperInfo | 获取动态壁纸信息 |
| getWallpaperColors | 获取壁纸颜色信息 |
| getDesiredMinimumHeight | 获取壁纸所需最小高度 |
| getDesiredMinimumWidth | 获取壁纸所需最小宽度 |
| getBuiltInDrawable | 获取系统内置静态壁纸 ( 桌面壁纸 ) |
| getDrawable | 获取当前壁纸 ( 桌面壁纸 ) |
| getFastDrawable | 获取当前壁纸 ( 桌面壁纸 ) |
| peekDrawable | 获取当前壁纸 ( 桌面壁纸 ) |
| peekFastDrawable | 获取当前壁纸 ( 桌面壁纸 ) |
| setBitmap | 通过 Bitmap 设置壁纸 ( 桌面壁纸 ) |
| setResource | 通过 res 设置壁纸 |
| setStream | 通过 InputStream 设置壁纸 |
| setUri | 通过 Uri 设置壁纸 ( 跳转到设置页 ) |
| callback | 非适配 ROM 则触发回调 |


* **控件工具类 ->** [WidgetUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/WidgetUtils.java)

| 方法 | 注释 |
| :- | :- |
| viewMeasure | View Measure |
| calculateSize | 计算大小 |
| getSize | 从提供的测量规范中提取大小 |
| getMode | 从提供的测量规范中提取模式 |
| measureView | 测量 View |
| getMeasuredWidth | 获取 View 的宽度 |
| getMeasuredHeight | 获取 View 的高度 |


* **Window 工具类 ->** [WindowUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/WindowUtils.java)

| 方法 | 注释 |
| :- | :- |
| getWindow | 获取 Window |
| get | 获取 WindowAssist |


## <span id="devutilsappactivity_result">**`dev.utils.app.activity_result`**</span>


* **Activity Result 封装辅助类 ->** [ActivityResultAssist.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/activity_result/ActivityResultAssist.java)

| 方法 | 注释 |
| :- | :- |
| isLauncherEmpty | 判断启动器是否为 null |
| isLauncherNotEmpty | 判断启动器是否不为 null |
| setOperateCallback | 设置操作回调 |
| getInputValue | 获取启动输入参数值 |
| getOptionsValue | 获取 Activity 启动选项值 |
| getMethodType | 获取对应 Type 所属方法 |
| launch | launch |
| unregister | unregister |
| getContract | getContract |
| onStart | 操作前回调 |
| onState | 操作状态回调 |


## <span id="devutilsappanim">**`dev.utils.app.anim`**</span>


* **动画工具类 ->** [AnimationUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/anim/AnimationUtils.java)

| 方法 | 注释 |
| :- | :- |
| setAnimationRepeat | 设置动画重复处理 |
| setAnimationListener | 设置动画事件 |
| setAnimation | 设置动画 |
| getAnimation | 获取动画 |
| clearAnimation | 清空动画 |
| startAnimation | 启动动画 |
| cancelAnimation | 取消动画 |
| getRotateAnimation | 获取一个旋转动画 |
| getRotateAnimationByCenter | 获取一个根据视图自身中心点旋转的动画 |
| getAlphaAnimation | 获取一个透明度渐变动画 |
| getHiddenAlphaAnimation | 获取一个由完全显示变为不可见的透明度渐变动画 |
| getShowAlphaAnimation | 获取一个由不可见变为完全显示的透明度渐变动画 |
| getScaleAnimation | 获取一个缩放动画 |
| getScaleAnimationCenter | 获取一个中心点缩放动画 |
| getLessenScaleAnimation | 获取一个缩小动画 |
| getAmplificationAnimation | 获取一个放大动画 |
| getTranslateAnimation | 获取一个视图移动动画 |
| getShakeAnimation | 获取一个视图摇晃动画 |


* **View 动画工具类 ->** [ViewAnimationUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/anim/ViewAnimationUtils.java)

| 方法 | 注释 |
| :- | :- |
| invisibleViewByAlpha | 将给定视图渐渐隐去 |
| goneViewByAlpha | 将给定视图渐渐隐去最后从界面中移除 |
| visibleViewByAlpha | 将给定视图渐渐显示出来 |
| translate | 视图移动 |
| shake | 视图摇晃 |


## <span id="devutilsappassist">**`dev.utils.app.assist`**</span>


* **Activity 栈管理辅助类 ->** [ActivityManagerAssist.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/assist/ActivityManagerAssist.java)

| 方法 | 注释 |
| :- | :- |
| getActivityStacks | 获取 Activity 栈 |
| addActivity | 添加 Activity |
| removeActivity | 移除 Activity |
| currentActivity | 获取最后一个 ( 当前 ) Activity |
| finishActivity | 关闭最后一个 ( 当前 ) Activity |
| existActivitys | 检测是否包含指定的 Activity |
| finishAllActivityToIgnore | 结束全部 Activity 除忽略的 Activity 外 |
| finishAllActivity | 结束所有 Activity |
| exitApplication | 退出应用程序 |
| restartApplication | 重启 APP |


* **播放「bee」的声音, 并且震动辅助类 ->** [BeepVibrateAssist.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/assist/BeepVibrateAssist.java)

| 方法 | 注释 |
| :- | :- |
| isPlayBeep | 判断是否允许播放声音 |
| isVibrate | 判断是否允许震动 |
| setVibrate | 设置是否允许震动 |
| setMediaPlayer | 设置播放资源对象 |
| playBeepSoundAndVibrate | 进行播放声音, 并且震动 |
| close | 关闭震动、提示声, 并释放资源 |
| buildMediaPlayer | 创建 MediaPlayer 对象 |


* **延迟触发辅助类 ->** [DelayAssist.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/assist/DelayAssist.java)

| 方法 | 注释 |
| :- | :- |
| remove | 移除消息 |
| post | 发送消息 ( 功能由该方法实现 ) |
| setDelayMillis | 设置搜索延迟时间 |
| setCallback | 设置搜索回调接口 |
| callback | 回调方法 |


* **Activity 无操作定时辅助类 ->** [InactivityTimerAssist.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/assist/InactivityTimerAssist.java)

| 方法 | 注释 |
| :- | :- |
| onPause | 暂停检测 |
| onResume | 回到 Activity 处理 |
| onDestroy | Activity 销毁处理 |


* **Resources 辅助类 ->** [ResourceAssist.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/assist/ResourceAssist.java)

| 方法 | 注释 |
| :- | :- |
| get | 获取 ResourceAssist |
| staticResources | 获取 Resources |
| staticTheme | 获取 Resources.Theme |
| staticContentResolver | 获取 ContentResolver |
| staticDisplayMetrics | 获取 DisplayMetrics |
| staticConfiguration | 获取 Configuration |
| staticAssets | 获取 AssetManager |
| reset | 重置操作 |
| getPackageName | 获取应用包名 |
| getResources | 获取 Resources |
| getTheme | 获取 Resources.Theme |
| getContentResolver | 获取 ContentResolver |
| getDisplayMetrics | 获取 DisplayMetrics |
| getConfiguration | 获取 Configuration |
| getAssets | 获取 AssetManager |
| getIdentifier | 获取资源 id |
| getResourceName | 获取给定资源标识符的全名 |
| getStringId | 获取 String id |
| getString | 获取 String |
| getDimenId | 获取 Dimension id |
| getDimension | 获取 Dimension |
| getDimensionInt | 获取 Dimension |
| getColorId | 获取 Color id |
| getColor | 获取 Color |
| getDrawableId | 获取 Drawable id |
| getDrawable | 获取 Drawable |
| getNinePatchDrawable | 获取 .9 Drawable |
| getBitmap | 获取 Bitmap |
| getMipmapId | 获取 Mipmap id |
| getDrawableMipmap | 获取 Mipmap Drawable |
| getNinePatchDrawableMipmap | 获取 Mipmap .9 Drawable |
| getBitmapMipmap | 获取 Mipmap Bitmap |
| getAnimId | 获取 Anim id |
| getAnimationXml | 获取 Animation Xml |
| getAnimation | 获取 Animation |
| getBooleanId | 获取 Boolean id |
| getBoolean | 获取 Boolean |
| getIntegerId | 获取 Integer id |
| getInteger | 获取 Integer |
| getArrayId | 获取 Array id |
| getIntArray | 获取 int[] |
| getStringArray | 获取 String[] |
| getTextArray | 获取 CharSequence[] |
| getId | 获取 id ( view ) |
| getLayoutId | 获取 Layout id |
| getMenuId | 获取 Menu id |
| getRawId | 获取 Raw id |
| getAttrId | 获取 Attr id |
| getStyleId | 获取 Style id |
| getStyleableId | 获取 Styleable id |
| getAnimatorId | 获取 Animator id |
| getXmlId | 获取 Xml id |
| getInterpolatorId | 获取 Interpolator id |
| getPluralsId | 获取 Plurals id |
| getColorStateList | 获取 ColorStateList |
| getColorDrawable | 获取十六进制颜色值 Drawable |
| openInputStream | 获取 Uri InputStream |
| openOutputStream | 获取 Uri OutputStream |
| openFileDescriptor | 获取 Uri ParcelFileDescriptor |
| openAssetFileDescriptor | 获取 Uri AssetFileDescriptor |
| open | 获取 AssetManager 指定资源 InputStream |
| openFd | 获取 AssetManager 指定资源 AssetFileDescriptor |
| openNonAssetFd | 获取 AssetManager 指定资源 AssetFileDescriptor |
| openRawResource | 获取对应资源 InputStream |
| openRawResourceFd | 获取对应资源 AssetFileDescriptor |
| readBytesFromAssets | 获取 Assets 资源文件数据 |
| readStringFromAssets | 获取 Assets 资源文件数据 |
| readBytesFromRaw | 获取 Raw 资源文件数据 |
| readStringFromRaw | 获取 Raw 资源文件数据 |
| geFileToListFromAssets | 获取 Assets 资源文件数据 ( 返回 List<String> 一行的全部内容属于一个索引 ) |
| geFileToListFromRaw | 获取 Raw 资源文件数据 ( 返回 List<String> 一行的全部内容属于一个索引 ) |
| saveAssetsFormFile | 获取 Assets 资源文件数据并保存到本地 |
| saveRawFormFile | 获取 Raw 资源文件数据并保存到本地 |
| createFromAsset | 获取 Assets 字体资源文件并创建 Typeface |
| createFromFile | 通过字体资源文件并创建 Typeface |
| create | 获取系统字体并设置字体样式 |


* **屏幕传感器辅助类 ( 监听是否横竖屏 ) ->** [ScreenSensorAssist.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/assist/ScreenSensorAssist.java)

| 方法 | 注释 |
| :- | :- |
| start | 开始监听 |
| stop | 停止监听 |
| isPortrait | 是否竖屏 |
| isAllowChange | 是否允许切屏 |


* **Window 辅助类 ->** [WindowAssist.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/assist/WindowAssist.java)

| 方法 | 注释 |
| :- | :- |
| getWindow | 获取 Window |
| get | 获取 WindowAssist |
| getDecorView | 获取 Window DecorView |
| peekDecorView | 获取 Window DecorView |
| getCurrentFocus | 获取 Window 当前获取焦点 View |
| setSystemUiVisibility | 设置 Window System UI 可见性 |
| getSystemUiVisibility | 获取 Window System UI 可见性 |
| setSystemUiVisibilityByAdd | 设置 Window System UI 可见性 ( 原来基础上进行追加 ) |
| setSystemUiVisibilityByClear | 设置 Window System UI 可见性 ( 原来基础上进行清除 ) |
| getAttributes | 获取 Window LayoutParams |
| setAttributes | 设置 Window LayoutParams |
| refreshSelfAttributes | 刷新自身 Window LayoutParams |
| clearFlags | 清除 Window flags |
| addFlags | 添加 Window flags |
| setFlags | 设置 Window flags |
| hasFlags | Window 是否设置指定 flags 值 |
| notHasFlags | Window 是否没有设置指定 flags 值 |
| requestFeature | 启用 Window Extended Feature |
| hasFeature | Window 是否开启指定 Extended Feature |
| notHasFeature | Window 是否没有开启指定 Extended Feature |
| setSoftInputMode | 设置 Window 输入模式 |
| setStatusBarColor | 设置 StatusBar Color |
| getStatusBarColor | 获取 StatusBar Color |
| setNavigationBarColor | 设置 NavigationBar Color |
| getNavigationBarColor | 获取 NavigationBar Color |
| setNavigationBarDividerColor | 设置 NavigationBar Divider Color |
| getNavigationBarDividerColor | 获取 NavigationBar Divider Color |
| setWidthByParams | 设置 Dialog 宽度 |
| setHeightByParams | 设置 Dialog 高度 |
| setWidthHeightByParams | 设置 Dialog 宽度、高度 |
| setXByParams | 设置 Dialog X 轴坐标 |
| setYByParams | 设置 Dialog Y 轴坐标 |
| setXYByParams | 设置 Dialog X、Y 轴坐标 |
| setGravityByParams | 设置 Dialog Gravity |
| setDimAmountByParams | 设置 Dialog 透明度 |
| setWindowBrightness | 设置窗口亮度 |
| getWindowBrightness | 获取窗口亮度 |
| setKeyBoardSoftInputMode | 设置 Window 软键盘是否显示 |
| isKeepScreenOnFlag | 是否屏幕常亮 |
| setFlagKeepScreenOn | 设置屏幕常亮 |
| clearFlagKeepScreenOn | 移除屏幕常亮 |
| isSecureFlag | 是否禁止截屏 |
| setFlagSecure | 设置禁止截屏 |
| clearFlagSecure | 移除禁止截屏 |
| isFullScreenFlag | 是否屏幕为全屏 |
| setFlagFullScreen | 设置屏幕为全屏 |
| clearFlagFullScreen | 移除屏幕全屏 |
| isTranslucentStatusFlag | 是否透明状态栏 |
| setFlagTranslucentStatus | 设置透明状态栏 |
| clearFlagTranslucentStatus | 移除透明状态栏 |
| isDrawsSystemBarBackgroundsFlag | 是否系统状态栏背景绘制 |
| setFlagDrawsSystemBarBackgrounds | 设置系统状态栏背景绘制 |
| clearFlagDrawsSystemBarBackgrounds | 移除系统状态栏背景绘制 |
| isNoTitleFeature | 是否屏幕页面为无标题 |
| setFeatureNoTitle | 设置屏幕页面无标题 |
| setFlagFullScreenAndNoTitle | 设置屏幕为全屏无标题 |
| setSemiTransparentStatusBarColor | 设置高版本状态栏蒙层 |
| setStatusBarColorAndFlag | 设置状态栏颜色、高版本状态栏蒙层 |


## <span id="devutilsappassistexif">**`dev.utils.app.assist.exif`**</span>


* **图片 EXIF 读写辅助类 ->** [ExifAssist.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/assist/exif/ExifAssist.java)

| 方法 | 注释 |
| :- | :- |
| get | get |
| getByRequire | 创建可获取 EXIF 敏感信息辅助类 |
| requireOriginal | 获取 EXIF 敏感信息, 请求获取原始 Uri |
| isSupportedMimeType | 判断是否支持读取的资源类型 |
| clone | 克隆图片 EXIF 读写信息 |
| getExif | 获取图片 EXIF 操作接口 |
| getExifError | 获取 EXIF 初始化异常信息 |
| isExifNull | 是否图片 EXIF 为 null |
| isExifNotNull | 是否图片 EXIF 不为 null |
| isExifError | 是否 EXIF 初始化异常 |
| getAttributeInt | 根据 TAG 获取对应值 |
| getAttributeDouble | 根据 TAG 获取对应值 |
| getAttribute | 根据 TAG 获取对应值 |
| getAttributeBytes | 根据 TAG 获取对应值 |
| getAttributeRange | 根据 TAG 获取对应值 |
| hasAttribute | 是否存在指定 TAG 值 |
| setAttribute | 设置对应 TAG 值 |
| saveAttributes | 将标签数据存储到图片中 ( 最终必须调用 ) |
| eraseAllExif | 擦除图像 Exif 信息 ( 全部 ) |
| eraseExifByList | 擦除图像 Exif 信息 ( 指定集合 ) |
| eraseExifByArray | 擦除图像 Exif 信息 ( 指定数组 ) |
| eraseExifLocation | 擦除图像所有 GPS 位置信息 |
| existLocation | 是否存在 GPS 位置信息 |
| getLatLong | 获取经纬度信息 |
| setLatLong | 设置经纬度信息 |
| getGpsInfo | 获取 GPS 信息 |
| setGpsInfo | 设置 GPS 信息 |
| getGpsDateTime | 获取 GPS 定位时间信息 |
| getAltitude | 获取海拔高度信息 ( 单位米 ) |
| setAltitude | 设置海拔高度信息 |
| hasThumbnail | 是否存在缩略图 |
| isThumbnailCompressed | 是否存在 JPEG 压缩缩略图 |
| getThumbnail | 获取 JPEG 压缩缩略图 |
| getThumbnailBytes | 获取 Exif 缩略图 |
| getThumbnailBitmap | 获取 Exif 缩略图 |
| getThumbnailRange | 获取缩略图数据偏移量位置和长度信息 |
| isFlipped | 当前图片是否翻转 |
| flipHorizontally | 进行水平翻转图片 |
| flipVertically | 进行垂直翻转图片 |
| getRotationDegrees | 获取图片旋转角度 |
| rotate | 将图片顺时针旋转给定度数 |
| resetOrientation | 重置图片方向为默认方向 |
| getAttributeByGroup | 获取 Exif 信息 ( ExifTag Group ) |
| getAttributeByList | 获取 Exif 信息 ( 指定集合 ) |
| getAttributeByArray | 获取 Exif 信息 ( 指定数组 ) |


* **图片 EXIF Tag Group 常量类 ->** [ExifTag.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/assist/exif/ExifTag.java)

| 方法 | 注释 |
| :- | :- |
| asList | 快捷创建 List 简化 add 操作 |


## <span id="devutilsappassistfloating">**`dev.utils.app.assist.floating`**</span>


* **悬浮窗通用代码 ->** [DevFloatingCommon.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/assist/floating/DevFloatingCommon.java)

| 方法 | 注释 |
| :- | :- |
| update | 实时更新方法 |
| getView | getView |
| getEvent | getEvent |
| getListener | getListener |
| getDownTime | getDownTime |
| getPoint | getPoint |
| getFirstPoint | getFirstPoint |
| getDelayAssist | getDelayAssist |
| actionDown | 手势按下 |
| actionMove | 手势移动 |
| actionUp | 手势抬起 |
| onClick | 悬浮窗 View 点击事件 |
| onLongClick | 悬浮窗 View 长按事件 |
| getDiffTime | 获取时间差 ( 当前时间 - 触摸时间 ) |
| isValidTime | 是否有效间隔时间 |
| isValidClickByTime | 通过时间判断点击是否有效 |
| isValidLongClickByTime | 通过时间判断长按是否有效 |
| isValidEvent | 是否有效事件 ( 是否在小范围内移动 ) |
| isTouchInView | 判断触点是否落在该 View 上 |
| postLongClick | 开始校验长按 |
| callback | callback |


* **DevApp 悬浮窗边缘检测辅助类实现 ->** [DevFloatingEdgeIMPL.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/assist/floating/DevFloatingEdgeIMPL.java)

| 方法 | 注释 |
| :- | :- |
| calculateEdge | calculateEdge |
| getMaxHeight | 获取 View 最大显示高度 |
| setMaxHeight | 设置 View 最大显示高度 |
| getMarginTop | 获取向上边距 |
| setMarginTop | 设置向上边距 |
| getMarginBottom | 获取向下边距 |
| setMarginBottom | 设置向下边距 |
| setStatusBarHeightMargin | 设置向上边距为状态栏高度 |
| setNavigationBarHeightMargin | 设置向下边距为底部导航栏高度 |


* **悬浮窗触摸事件接口实现 ->** [DevFloatingListener.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/assist/floating/DevFloatingListener.java)

| 方法 | 注释 |
| :- | :- |
| getClickIntervalTime | 获取点击事件间隔时间 |
| setClickIntervalTime | 获取点击事件间隔时间 |
| getLongClickIntervalTime | 获取长按事件间隔时间 |
| setLongClickIntervalTime | 获取长按事件间隔时间 |


* **DevApp 悬浮窗触摸辅助类实现 ->** [DevFloatingTouchIMPL.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/assist/floating/DevFloatingTouchIMPL.java)

| 方法 | 注释 |
| :- | :- |
| onTouchEvent | onTouchEvent |
| getFloatingListener | 获取悬浮窗触摸事件接口 |
| setFloatingListener | 获取悬浮窗触摸事件接口 |
| getCommon | 获取悬浮窗通用代码 |


* **DevApp 悬浮窗触摸辅助类实现 ->** [DevFloatingTouchIMPL2.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/assist/floating/DevFloatingTouchIMPL2.java)

| 方法 | 注释 |
| :- | :- |
| onTouchEvent | onTouchEvent |
| updateViewLayout | 更新 View Layout |
| getX | 获取 X 轴坐标 |
| setX | 设置 X 轴坐标 |
| getY | 获取 Y 轴坐标 |
| setY | 设置 Y 轴坐标 |
| getFloatingEdge | 获取悬浮窗边缘检测接口实现 |
| setFloatingEdge | 设置悬浮窗边缘检测接口实现 |
| getFloatingListener | 获取悬浮窗触摸事件接口 |
| setFloatingListener | 获取悬浮窗触摸事件接口 |
| getCommon | 获取悬浮窗通用代码 |


* **悬浮窗管理辅助类 ( 需权限 ) ->** [FloatingWindowManagerAssist.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/assist/floating/FloatingWindowManagerAssist.java)

| 方法 | 注释 |
| :- | :- |
| getIMPL | 获取悬浮窗管理辅助类实现 |
| getWindowManager | 获取 WindowManager |
| getLayoutParams | 获取 Window LayoutParams |
| addView | 添加悬浮 View |
| removeView | 移除悬浮 View |
| updateViewLayout | 更新 View Layout |
| canDrawOverlays | 是否存在悬浮窗权限 |
| checkOverlayPermission | 检测是否存在悬浮窗权限并跳转 |
| isOverlayRequestCode | 是否悬浮窗请求回调 code |


* **悬浮窗管理辅助类 ( 无需权限依赖 Activity ) ->** [FloatingWindowManagerAssist2.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/assist/floating/FloatingWindowManagerAssist2.java)

| 方法 | 注释 |
| :- | :- |
| removeFloatingView | 移除悬浮窗 View |
| addFloatingView | 添加悬浮窗 View |
| removeAllFloatingView | 移除所有悬浮窗 View |
| updateViewLayout | 更新悬浮窗 View Layout |
| isNeedsAdd | 是否处理悬浮 View 添加操作 |
| setNeedsAdd | 设置是否处理悬浮 View 添加操作 |


* **悬浮窗辅助类接口 ->** [IFloatingActivity.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/assist/floating/IFloatingActivity.java)

| 方法 | 注释 |
| :- | :- |
| getAttachActivity | 获取悬浮窗依附的 Activity |
| getMapFloatingKey | 获取悬浮窗 Map Key |
| getMapFloatingView | 获取悬浮窗 Map Value View |
| getMapFloatingViewLayoutParams | 获取悬浮窗 View LayoutParams |


* **悬浮窗边缘检测接口 ->** [IFloatingEdge.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/assist/floating/IFloatingEdge.java)

| 方法 | 注释 |
| :- | :- |
| calculateEdge | 计算悬浮窗边缘检测坐标 |


* **悬浮窗触摸事件接口 ->** [IFloatingListener.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/assist/floating/IFloatingListener.java)

| 方法 | 注释 |
| :- | :- |
| onClick | 悬浮窗 View 点击事件 |
| onLongClick | 悬浮窗 View 长按事件 |
| getClickIntervalTime | 获取点击事件间隔时间 |
| setClickIntervalTime | 获取点击事件间隔时间 |
| getLongClickIntervalTime | 获取长按事件间隔时间 |
| setLongClickIntervalTime | 获取长按事件间隔时间 |


* **悬浮窗操作辅助类接口 ->** [IFloatingOperate.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/assist/floating/IFloatingOperate.java)

| 方法 | 注释 |
| :- | :- |
| removeFloatingView | 移除悬浮窗 View |
| addFloatingView | 添加悬浮窗 View |
| removeAllFloatingView | 移除所有悬浮窗 View |
| updateViewLayout | 更新悬浮窗 View Layout |
| isNeedsAdd | 是否处理悬浮 View 添加操作 |
| setNeedsAdd | 设置是否处理悬浮 View 添加操作 |


* **悬浮窗触摸辅助类接口 ->** [IFloatingTouch.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/assist/floating/IFloatingTouch.java)

| 方法 | 注释 |
| :- | :- |
| onTouchEvent | 悬浮窗 View 触摸事件 |
| updateViewLayout | 更新 View Layout |
| getFloatingListener | 获取悬浮窗触摸事件接口 |
| setFloatingListener | 获取悬浮窗触摸事件接口 |


## <span id="devutilsappassistlifecycle">**`dev.utils.app.assist.lifecycle`**</span>


* **Activity LifecycleCallbacks 抽象类 ->** [AbstractActivityLifecycle.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/assist/lifecycle/AbstractActivityLifecycle.java)

| 方法 | 注释 |
| :- | :- |
| onActivityCreated | onActivityCreated |
| onActivityStarted | onActivityStarted |
| onActivityResumed | onActivityResumed |
| onActivityPaused | onActivityPaused |
| onActivityStopped | onActivityStopped |
| onActivitySaveInstanceState | onActivitySaveInstanceState |
| onActivityDestroyed | onActivityDestroyed |


* **Activity 生命周期辅助类 ->** [ActivityLifecycleAssist.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/assist/lifecycle/ActivityLifecycleAssist.java)

| 方法 | 注释 |
| :- | :- |
| getActivityLifecycleGet | 获取 Activity 生命周期 相关信息获取接口类 |
| getActivityLifecycleNotify | 获取 Activity 生命周期 事件监听接口类 |
| getTopActivity | 获取 Top Activity |
| setActivityLifecycleFilter | 设置 Activity 生命周期 过滤判断接口 |
| setAbstractActivityLifecycle | 设置 ActivityLifecycle 监听回调 |
| registerActivityLifecycleCallbacks | 注册绑定 Activity 生命周期事件处理 |
| unregisterActivityLifecycleCallbacks | 解除注册 Activity 生命周期事件处理 |


* **Activity 生命周期 过滤判断接口 ->** [ActivityLifecycleFilter.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/assist/lifecycle/ActivityLifecycleFilter.java)

| 方法 | 注释 |
| :- | :- |
| filter | 判断是否过滤该类 ( 不进行添加等操作 ) |


* **Activity 生命周期 相关信息获取接口 ->** [ActivityLifecycleGet.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/assist/lifecycle/ActivityLifecycleGet.java)

| 方法 | 注释 |
| :- | :- |
| getTopActivity | 获取最顶部 ( 当前或最后一个显示 ) Activity |
| isTopActivity | 判断某个 Activity 是否 Top Activity |
| isBackground | 判断应用是否在后台 ( 不可见 ) |
| getActivityCount | 获取 Activity 总数 |


* **Activity 生命周期 通知接口 ->** [ActivityLifecycleNotify.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/assist/lifecycle/ActivityLifecycleNotify.java)

| 方法 | 注释 |
| :- | :- |
| addOnAppStatusChangedListener | 添加 APP 状态改变事件监听 |
| removeOnAppStatusChangedListener | 移除 APP 状态改变事件监听 |
| removeAllOnAppStatusChangedListener | 移除全部 APP 状态改变事件监听 |
| addOnActivityDestroyedListener | 添加 Activity 销毁通知事件 |
| removeOnActivityDestroyedListener | 移除 Activity 销毁通知事件 |
| removeAllOnActivityDestroyedListener | 移除全部 Activity 销毁通知事件 |


* **Activity 销毁事件 ->** [OnActivityDestroyedListener.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/assist/lifecycle/OnActivityDestroyedListener.java)

| 方法 | 注释 |
| :- | :- |
| onActivityDestroyed | Activity 销毁通知 |


* **APP 状态改变事件 ->** [OnAppStatusChangedListener.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/assist/lifecycle/OnAppStatusChangedListener.java)

| 方法 | 注释 |
| :- | :- |
| onForeground | 切换到前台 |
| onBackground | 切换到后台 |


## <span id="devutilsappassistlifecyclecurrent">**`dev.utils.app.assist.lifecycle.current`**</span>


* **当前 Activity 生命周期辅助类 ->** [ThisActivityLifecycleAssist.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/assist/lifecycle/current/ThisActivityLifecycleAssist.java)

| 方法 | 注释 |
| :- | :- |
| ofListener | 获取 Activity 生命周期通知事件 |
| addListener | 添加 Activity 生命周期通知事件 |
| removeListener | 移除 Activity 生命周期通知事件 |
| removeAllListener | 移除全部 Activity 生命周期通知事件 |
| registerActivityLifecycleCallbacks | 注册绑定 Activity 生命周期事件处理 |
| unregisterActivityLifecycleCallbacks | 解除注册 Activity 生命周期事件处理 |


## <span id="devutilsappassistlifecyclefragment">**`dev.utils.app.assist.lifecycle.fragment`**</span>


* **Fragment 生命周期辅助类 ->** [FragmentLifecycleAssist.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/assist/lifecycle/fragment/FragmentLifecycleAssist.java)

| 方法 | 注释 |
| :- | :- |
| setFragmentLifecycleFilter | 设置 Fragment 生命周期 过滤判断接口 |
| setAbstractFragmentLifecycle | 设置 FragmentLifecycle 监听回调 |
| registerFragmentLifecycleCallbacks | 注册绑定 Fragment 生命周期事件处理 |
| unregisterFragmentLifecycleCallbacks | 解除注册 Fragment 生命周期事件处理 |


* **Fragment 生命周期 过滤判断接口 ->** [FragmentLifecycleFilter.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/assist/lifecycle/fragment/FragmentLifecycleFilter.java)

| 方法 | 注释 |
| :- | :- |
| filter | 判断是否过滤该类 ( 不进行添加等操作 ) |


## <span id="devutilsappassistrecord">**`dev.utils.app.assist.record`**</span>


* **App 日志记录插入信息 ->** [AppRecordInsert.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/assist/record/AppRecordInsert.java)

| 方法 | 注释 |
| :- | :- |
| setFileInfo | setFileInfo |
| getFileInfo | getFileInfo |


## <span id="devutilsappassisturl">**`dev.utils.app.assist.url`**</span>


* **Android Api 实现 Url 解析器 ->** [AndroidUrlParser.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/assist/url/AndroidUrlParser.java)

| 方法 | 注释 |
| :- | :- |
| reset | reset |
| setUrl | setUrl |
| getUrl | getUrl |
| getUrlByPrefix | getUrlByPrefix |
| getUrlByParams | getUrlByParams |
| getUrlParams | getUrlParams |
| getUrlParamsDecode | getUrlParamsDecode |
| isConvertMap | isConvertMap |
| setConvertMap | setConvertMap |


## <span id="devutilsappcache">**`dev.utils.app.cache`**</span>


* **缓存类 ->** [DevCache.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/cache/DevCache.java)

| 方法 | 注释 |
| :- | :- |
| newCache | 获取 DevCache |
| getCachePath | 获取缓存地址 |
| remove | 移除数据 |
| removeForKeys | 删除 Key[] 配置、数据文件 |
| contains | 是否存在 key |
| isDue | 判断某个 key 是否过期 |
| clear | 清除全部数据 |
| clearDue | 清除过期数据 |
| clearType | 清除某个类型的全部数据 |
| getItemByKey | 通过 Key 获取 Item |
| getKeys | 获取有效 Key 集合 |
| getPermanentKeys | 获取永久有效 Key 集合 |
| getCount | 获取有效 Key 数量 |
| getSize | 获取有效 Key 占用总大小 |
| put | 保存 int 类型的数据 |
| getInt | 获取 int 类型的数据 |
| getLong | 获取 long 类型的数据 |
| getFloat | 获取 float 类型的数据 |
| getDouble | 获取 double 类型的数据 |
| getBoolean | 获取 boolean 类型的数据 |
| getString | 获取 String 类型的数据 |
| getBytes | 获取 byte[] 类型的数据 |
| getBitmap | 获取 Bitmap 类型的数据 |
| getDrawable | 获取 Drawable 类型的数据 |
| getSerializable | 获取 Serializable 类型的数据 |
| getParcelable | 获取 Parcelable 类型的数据 |
| getJSONObject | 获取 JSONObject 类型的数据 |
| getJSONArray | 获取 JSONArray 类型的数据 |
| getKey | 获取存储 Key |
| isPermanent | 是否永久有效 |
| getType | 获取数据存储类型 |
| getSaveTime | 获取保存时间 ( 毫秒 ) |
| getValidTime | 获取有效期 ( 毫秒 ) |
| setType | setType |
| setSaveTime | setSaveTime |
| setValidTime | setValidTime |
| isInt | isInt |
| isLong | isLong |
| isFloat | isFloat |
| isDouble | isDouble |
| isBoolean | isBoolean |
| isString | isString |
| isBytes | isBytes |
| isBitmap | isBitmap |
| isDrawable | isDrawable |
| isSerializable | isSerializable |
| isParcelable | isParcelable |
| isJSONObject | isJSONObject |
| isJSONArray | isJSONArray |


## <span id="devutilsapphelper">**`dev.utils.app.helper`**</span>


* **基础 Helper 通用实现类 ->** [BaseHelper.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/helper/BaseHelper.java)

| 方法 | 注释 |
| :- | :- |
| devHelper | 获取 DevHelper |
| quickHelper | 获取 QuickHelper |
| viewHelper | 获取 ViewHelper |
| flowHelper | 获取 FlowHelper |
| flow | 执行 Action 流方法 |
| flowValue | 流式返回传入值 |
| postRunnable | 在主线程 Handler 中执行任务 |
| removeRunnable | 在主线程 Handler 中清除任务 |


## <span id="devutilsapphelperdev">**`dev.utils.app.helper.dev`**</span>


* **Dev 工具类链式调用 Helper 类 ->** [DevHelper.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/helper/dev/DevHelper.java)

| 方法 | 注释 |
| :- | :- |
| get | 获取单例 DevHelper |
| flow | 执行 Action 流方法 |
| postRunnable | 在主线程 Handler 中执行任务 |
| removeRunnable | 在主线程 Handler 中清除任务 |
| setAnimationRepeat | 设置动画重复处理 |
| setAnimationListener | 设置动画事件 |
| startAnimation | 启动动画 |
| cancelAnimation | 取消动画 |
| recycle | Bitmap 通知回收 |
| startTimer | 运行定时器 |
| stopTimer | 关闭定时器 |
| recycleTimer | 回收定时器资源 |
| closeAllTimer | 关闭全部定时器 |
| closeAllNotRunningTimer | 关闭所有未运行的定时器 |
| closeAllInfiniteTimer | 关闭所有无限循环的定时器 |
| closeAllTagTimer | 关闭所有对应 TAG 定时器 |
| closeAllUUIDTimer | 关闭所有对应 UUID 定时器 |
| addTouchArea | 增加控件的触摸范围, 最大范围只能是父布局所包含的的区域 |
| setOnClick | 设置点击事件 |
| setOnLongClick | 设置长按事件 |
| setOnTouch | 设置触摸事件 |
| copyText | 复制文本到剪贴板 |
| copyUri | 复制 URI 到剪贴板 |
| copyIntent | 复制意图到剪贴板 |
| setDialogStatusBarColor | 设置 Dialog 状态栏颜色 |
| setDialogSemiTransparentStatusBarColor | 设置 Dialog 高版本状态栏蒙层 |
| setDialogStatusBarColorAndFlag | 设置 Dialog 状态栏颜色、高版本状态栏蒙层 |
| setDialogAttributes | 设置 Dialog Window LayoutParams |
| setDialogWidth | 设置 Dialog 宽度 |
| setDialogHeight | 设置 Dialog 高度 |
| setDialogWidthHeight | 设置 Dialog 宽度、高度 |
| setDialogX | 设置 Dialog X 轴坐标 |
| setDialogY | 设置 Dialog Y 轴坐标 |
| setDialogXY | 设置 Dialog X、Y 轴坐标 |
| setDialogGravity | 设置 Dialog Gravity |
| setDialogDimAmount | 设置 Dialog 透明度 |
| setDialogCancelable | 设置是否允许返回键关闭 |
| setDialogCanceledOnTouchOutside | 设置是否允许点击其他地方自动关闭 |
| setDialogCancelableAndTouchOutside | 设置是否允许 返回键关闭、点击其他地方自动关闭 |
| showDialog | 显示 Dialog |
| closeDialogs | 关闭多个 Dialog |
| closePopupWindows | 关闭多个 PopupWindow |
| autoCloseDialog | 自动关闭 dialog |
| autoClosePopupWindow | 自动关闭 PopupWindow |
| setSoftInputMode | 设置 Window 软键盘是否显示 |
| judgeView | 设置某个 View 内所有非 EditText 的子 View OnTouchListener 事件 |
| registerSoftInputChangedListener | 注册软键盘改变监听 |
| registerSoftInputChangedListener2 | 注册软键盘改变监听 |
| fixSoftInputLeaks | 修复软键盘内存泄漏 在 Activity.onDestroy() 中使用 |
| toggleKeyboard | 自动切换键盘状态, 如果键盘显示则隐藏反之显示 |
| openKeyboard | 打开软键盘 |
| openKeyboardDelay | 延时打开软键盘 |
| openKeyboardByFocus | 打开软键盘 |
| closeKeyboard | 关闭软键盘 |
| closeKeyBoardSpecial | 关闭软键盘 |
| closeKeyBoardSpecialDelay | 延时关闭软键盘 |
| closeKeyboardDelay | 延时关闭软键盘 |
| applyLanguage | 修改系统语言 ( APP 多语言, 单独改变 APP 语言 ) |
| cancelAllNotification | 移除通知 ( 移除所有通知 ) |
| cancelNotification | 移除通知 ( 移除标记为 id 的通知 ) |
| notifyNotification | 进行通知 |
| createNotificationChannel | 创建 NotificationChannel |
| dial | 跳至拨号界面 |
| call | 拨打电话 |
| sendSms | 跳至发送短信界面 |
| sendSmsSilent | 发送短信 |
| setBright | 设置屏幕常亮 |
| setWindowSecure | 设置禁止截屏 |
| setFullScreen | 设置屏幕为全屏 |
| setFullScreenNoTitle | 设置屏幕为全屏无标题 |
| setLandscape | 设置屏幕为横屏 |
| setPortrait | 设置屏幕为竖屏 |
| toggleScreenOrientation | 切换屏幕方向 |
| setSleepDuration | 设置进入休眠时长 |
| forceGetViewSize | 在 onCreate 中获取视图的尺寸 ( 需回调 onGetSizeListener 接口, 在 onGetSize 中获取 View 宽高 ) |
| vibrate | 震动 |
| cancelVibrate | 取消震动 |
| getWidthHeightExact | 获取 View 宽高 ( 准确 ) |
| getWidthHeightExact2 | 获取 View 宽高 ( 准确 ) |
| measureView | 测量 View |
| closeIO | 关闭 IO |
| closeIOQuietly | 安静关闭 IO |
| flush | 将缓冲区数据输出 |
| flushQuietly | 安静将缓冲区数据输出 |
| flushCloseIO | 将缓冲区数据输出并关闭流 |
| flushCloseIOQuietly | 安静将缓冲区数据输出并关闭流 |
| setSystemUiVisibility | 设置 Window System UI 可见性 |
| setSystemUiVisibilityByAdd | 设置 Window System UI 可见性 ( 原来基础上进行追加 ) |
| setSystemUiVisibilityByClear | 设置 Window System UI 可见性 ( 原来基础上进行清除 ) |
| setAttributes | 设置 Window LayoutParams |
| refreshSelfAttributes | 刷新自身 Window LayoutParams |
| clearFlags | 清除 Window flags |
| addFlags | 添加 Window flags |
| setFlags | 设置 Window flags |
| requestFeature | 启用 Window Extended Feature |
| setStatusBarColor | 设置 StatusBar Color |
| setNavigationBarColor | 设置 NavigationBar Color |
| setNavigationBarDividerColor | 设置 NavigationBar Divider Color |
| setWidthByParams | 设置 Dialog 宽度 |
| setHeightByParams | 设置 Dialog 高度 |
| setWidthHeightByParams | 设置 Dialog 宽度、高度 |
| setXByParams | 设置 Dialog X 轴坐标 |
| setYByParams | 设置 Dialog Y 轴坐标 |
| setXYByParams | 设置 Dialog X、Y 轴坐标 |
| setGravityByParams | 设置 Dialog Gravity |
| setDimAmountByParams | 设置 Dialog 透明度 |
| setWindowBrightness | 设置窗口亮度 |
| setKeyBoardSoftInputMode | 设置 Window 软键盘是否显示 |
| setFlagKeepScreenOn | 设置屏幕常亮 |
| clearFlagKeepScreenOn | 移除屏幕常亮 |
| setFlagSecure | 设置禁止截屏 |
| clearFlagSecure | 移除禁止截屏 |
| setFlagFullScreen | 设置屏幕为全屏 |
| clearFlagFullScreen | 移除屏幕全屏 |
| setFlagTranslucentStatus | 设置透明状态栏 |
| clearFlagTranslucentStatus | 移除透明状态栏 |
| setFlagDrawsSystemBarBackgrounds | 设置系统状态栏背景绘制 |
| clearFlagDrawsSystemBarBackgrounds | 移除系统状态栏背景绘制 |
| setFeatureNoTitle | 设置屏幕页面无标题 |
| setFlagFullScreenAndNoTitle | 设置屏幕为全屏无标题 |
| setSemiTransparentStatusBarColor | 设置高版本状态栏蒙层 |
| setStatusBarColorAndFlag | 设置状态栏颜色、高版本状态栏蒙层 |


* **DevHelper 接口 ->** [IHelperByDev.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/helper/dev/IHelperByDev.java)

| 方法 | 注释 |
| :- | :- |
| setAnimationRepeat | 设置动画重复处理 |
| setAnimationListener | 设置动画事件 |
| startAnimation | 启动动画 |
| cancelAnimation | 取消动画 |
| recycle | Bitmap 通知回收 |
| startTimer | 运行定时器 |
| stopTimer | 关闭定时器 |
| recycleTimer | 回收定时器资源 |
| closeAllTimer | 关闭全部定时器 |
| closeAllNotRunningTimer | 关闭所有未运行的定时器 |
| closeAllInfiniteTimer | 关闭所有无限循环的定时器 |
| closeAllTagTimer | 关闭所有对应 TAG 定时器 |
| closeAllUUIDTimer | 关闭所有对应 UUID 定时器 |
| addTouchArea | 增加控件的触摸范围, 最大范围只能是父布局所包含的的区域 |
| setOnClick | 设置点击事件 |
| setOnLongClick | 设置长按事件 |
| setOnTouch | 设置触摸事件 |
| copyText | 复制文本到剪贴板 |
| copyUri | 复制 URI 到剪贴板 |
| copyIntent | 复制意图到剪贴板 |
| setDialogStatusBarColor | 设置 Dialog 状态栏颜色 |
| setDialogSemiTransparentStatusBarColor | 设置 Dialog 高版本状态栏蒙层 |
| setDialogStatusBarColorAndFlag | 设置 Dialog 状态栏颜色、高版本状态栏蒙层 |
| setDialogAttributes | 设置 Dialog Window LayoutParams |
| setDialogWidth | 设置 Dialog 宽度 |
| setDialogHeight | 设置 Dialog 高度 |
| setDialogWidthHeight | 设置 Dialog 宽度、高度 |
| setDialogX | 设置 Dialog X 轴坐标 |
| setDialogY | 设置 Dialog Y 轴坐标 |
| setDialogXY | 设置 Dialog X、Y 轴坐标 |
| setDialogGravity | 设置 Dialog Gravity |
| setDialogDimAmount | 设置 Dialog 透明度 |
| setDialogCancelable | 设置是否允许返回键关闭 |
| setDialogCanceledOnTouchOutside | 设置是否允许点击其他地方自动关闭 |
| setDialogCancelableAndTouchOutside | 设置是否允许 返回键关闭、点击其他地方自动关闭 |
| showDialog | 显示 Dialog |
| closeDialogs | 关闭多个 Dialog |
| closePopupWindows | 关闭多个 PopupWindow |
| autoCloseDialog | 自动关闭 dialog |
| autoClosePopupWindow | 自动关闭 PopupWindow |
| setSoftInputMode | 设置 Window 软键盘是否显示 |
| judgeView | 设置某个 View 内所有非 EditText 的子 View OnTouchListener 事件 |
| registerSoftInputChangedListener | 注册软键盘改变监听 |
| registerSoftInputChangedListener2 | 注册软键盘改变监听 |
| fixSoftInputLeaks | 修复软键盘内存泄漏 在 Activity.onDestroy() 中使用 |
| toggleKeyboard | 自动切换键盘状态, 如果键盘显示则隐藏反之显示 |
| openKeyboard | 打开软键盘 |
| openKeyboardDelay | 延时打开软键盘 |
| openKeyboardByFocus | 打开软键盘 |
| closeKeyboard | 关闭软键盘 |
| closeKeyBoardSpecial | 关闭软键盘 |
| closeKeyBoardSpecialDelay | 延时关闭软键盘 |
| closeKeyboardDelay | 延时关闭软键盘 |
| applyLanguage | 修改系统语言 ( APP 多语言, 单独改变 APP 语言 ) |
| cancelAllNotification | 移除通知 ( 移除所有通知 ) |
| cancelNotification | 移除通知 ( 移除标记为 id 的通知 ) |
| notifyNotification | 进行通知 |
| createNotificationChannel | 创建 NotificationChannel |
| dial | 跳至拨号界面 |
| call | 拨打电话 |
| sendSms | 跳至发送短信界面 |
| sendSmsSilent | 发送短信 |
| setBright | 设置屏幕常亮 |
| setWindowSecure | 设置禁止截屏 |
| setFullScreen | 设置屏幕为全屏 |
| setFullScreenNoTitle | 设置屏幕为全屏无标题 |
| setLandscape | 设置屏幕为横屏 |
| setPortrait | 设置屏幕为竖屏 |
| toggleScreenOrientation | 切换屏幕方向 |
| setSleepDuration | 设置进入休眠时长 |
| forceGetViewSize | 在 onCreate 中获取视图的尺寸 ( 需回调 onGetSizeListener 接口, 在 onGetSize 中获取 View 宽高 ) |
| vibrate | 震动 |
| cancelVibrate | 取消震动 |
| getWidthHeightExact | 获取 View 宽高 ( 准确 ) |
| getWidthHeightExact2 | 获取 View 宽高 ( 准确 ) |
| measureView | 测量 View |
| closeIO | 关闭 IO |
| closeIOQuietly | 安静关闭 IO |
| flush | 将缓冲区数据输出 |
| flushQuietly | 安静将缓冲区数据输出 |
| flushCloseIO | 将缓冲区数据输出并关闭流 |
| flushCloseIOQuietly | 安静将缓冲区数据输出并关闭流 |
| setSystemUiVisibility | 设置 Window System UI 可见性 |
| setSystemUiVisibilityByAdd | 设置 Window System UI 可见性 ( 原来基础上进行追加 ) |
| setSystemUiVisibilityByClear | 设置 Window System UI 可见性 ( 原来基础上进行清除 ) |
| setAttributes | 设置 Window LayoutParams |
| refreshSelfAttributes | 刷新自身 Window LayoutParams |
| clearFlags | 清除 Window flags |
| addFlags | 添加 Window flags |
| setFlags | 设置 Window flags |
| requestFeature | 启用 Window Extended Feature |
| setStatusBarColor | 设置 StatusBar Color |
| setNavigationBarColor | 设置 NavigationBar Color |
| setNavigationBarDividerColor | 设置 NavigationBar Divider Color |
| setWidthByParams | 设置 Dialog 宽度 |
| setHeightByParams | 设置 Dialog 高度 |
| setWidthHeightByParams | 设置 Dialog 宽度、高度 |
| setXByParams | 设置 Dialog X 轴坐标 |
| setYByParams | 设置 Dialog Y 轴坐标 |
| setXYByParams | 设置 Dialog X、Y 轴坐标 |
| setGravityByParams | 设置 Dialog Gravity |
| setDimAmountByParams | 设置 Dialog 透明度 |
| setWindowBrightness | 设置窗口亮度 |
| setKeyBoardSoftInputMode | 设置 Window 软键盘是否显示 |
| setFlagKeepScreenOn | 设置屏幕常亮 |
| clearFlagKeepScreenOn | 移除屏幕常亮 |
| setFlagSecure | 设置禁止截屏 |
| clearFlagSecure | 移除禁止截屏 |
| setFlagFullScreen | 设置屏幕为全屏 |
| clearFlagFullScreen | 移除屏幕全屏 |
| setFlagTranslucentStatus | 设置透明状态栏 |
| clearFlagTranslucentStatus | 移除透明状态栏 |
| setFlagDrawsSystemBarBackgrounds | 设置系统状态栏背景绘制 |
| clearFlagDrawsSystemBarBackgrounds | 移除系统状态栏背景绘制 |
| setFeatureNoTitle | 设置屏幕页面无标题 |
| setFlagFullScreenAndNoTitle | 设置屏幕为全屏无标题 |
| setSemiTransparentStatusBarColor | 设置高版本状态栏蒙层 |
| setStatusBarColorAndFlag | 设置状态栏颜色、高版本状态栏蒙层 |


## <span id="devutilsapphelperflow">**`dev.utils.app.helper.flow`**</span>


* **流式 ( 链式 ) 连接 Helper 类 ->** [FlowHelper.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/helper/flow/FlowHelper.java)

| 方法 | 注释 |
| :- | :- |
| get | 获取单例 FlowHelper |
| flow | 执行 Action 流方法 |
| postRunnable | 在主线程 Handler 中执行任务 |
| removeRunnable | 在主线程 Handler 中清除任务 |
| action | 操作方法 |


## <span id="devutilsapphelperquick">**`dev.utils.app.helper.quick`**</span>


* **QuickHelper 接口 ->** [IHelperByQuick.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/helper/quick/IHelperByQuick.java)

| 方法 | 注释 |
| :- | :- |
| addTouchArea | 增加控件的触摸范围, 最大范围只能是父布局所包含的的区域 |
| setOnClick | 设置点击事件 |
| setOnLongClick | 设置长按事件 |
| setOnTouch | 设置触摸事件 |
| setId | 设置 View Id |
| setClipChildren | 设置是否限制子 View 在其边界内绘制 |
| removeAllViews | 移除全部子 View |
| addView | 添加 View |
| setLayoutParams | 设置 View LayoutParams |
| setWidthHeight | 设置 View[] 宽度、高度 |
| setWeight | 设置 View weight 权重 |
| setWidth | 设置 View 宽度 |
| setHeight | 设置 View 高度 |
| setMinimumWidth | 设置 View 最小宽度 |
| setMinimumHeight | 设置 View 最小高度 |
| setAlpha | 设置 View 透明度 |
| setTag | 设置 View TAG |
| setScrollX | 设置 View 滑动的 X 轴坐标 |
| setScrollY | 设置 View 滑动的 Y 轴坐标 |
| setDescendantFocusability | 设置 ViewGroup 和其子控件两者之间的关系 |
| setOverScrollMode | 设置 View 滚动模式 |
| setHorizontalScrollBarEnabled | 设置是否绘制横向滚动条 |
| setVerticalScrollBarEnabled | 设置是否绘制垂直滚动条 |
| setScrollContainer | 设置 View 滚动效应 |
| setClipToOutline | 设置 View 是否使用 Outline 来裁剪 |
| setOutlineProvider | 设置 View 轮廓裁剪、绘制 |
| setOutlineProviderClip | 设置 View 轮廓裁剪、绘制并进行裁剪 |
| setNextFocusForwardId | 设置下一个获取焦点的 View id |
| setNextFocusDownId | 设置向下移动焦点时, 下一个获取焦点的 View id |
| setNextFocusLeftId | 设置向左移动焦点时, 下一个获取焦点的 View id |
| setNextFocusRightId | 设置向右移动焦点时, 下一个获取焦点的 View id |
| setNextFocusUpId | 设置向上移动焦点时, 下一个获取焦点的 View id |
| setRotation | 设置 View 旋转度数 |
| setRotationX | 设置 View 水平旋转度数 |
| setRotationY | 设置 View 竖直旋转度数 |
| setScaleX | 设置 View 水平方向缩放比例 |
| setScaleY | 设置 View 竖直方向缩放比例 |
| setTextAlignment | 设置文本的显示方式 |
| setTextDirection | 设置文本的显示方向 |
| setPivotX | 设置水平方向偏转量 |
| setPivotY | 设置竖直方向偏转量 |
| setTranslationX | 设置水平方向的移动距离 |
| setTranslationY | 设置竖直方向的移动距离 |
| setX | 设置 X 轴位置 |
| setY | 设置 Y 轴位置 |
| setLayerType | 设置 View 硬件加速类型 |
| requestLayout | 请求重新对 View 布局 |
| requestFocus | View 请求获取焦点 |
| clearFocus | View 清除焦点 |
| setFocusableInTouchMode | 设置 View 是否在触摸模式下获得焦点 |
| setFocusable | 设置 View 是否可以获取焦点 |
| toggleFocusable | 切换获取焦点状态 |
| setSelected | 设置 View 是否选中 |
| toggleSelected | 切换选中状态 |
| setEnabled | 设置 View 是否启用 |
| toggleEnabled | 切换 View 是否启用状态 |
| setClickable | 设置 View 是否可以点击 |
| toggleClickable | 切换 View 是否可以点击状态 |
| setLongClickable | 设置 View 是否可以长按 |
| toggleLongClickable | 切换 View 是否可以长按状态 |
| setVisibilitys | 设置 View 显示的状态 |
| setVisibilityINs | 设置 View 显示的状态 |
| toggleVisibilitys | 切换 View 显示的状态 |
| reverseVisibilitys | 反转 View 显示的状态 |
| toggleViews | 切换 View 状态 |
| viewVisible | 设置 View 显示状态 |
| viewVisibles | 设置 View 显示状态 |
| viewGone | 设置 View 隐藏状态 |
| viewGones | 设置 View 隐藏状态 |
| viewInVisible | 设置 View 隐藏占位状态 |
| viewInVisibles | 设置 View 隐藏占位状态 |
| removeSelfFromParent | 把自身从父 View 中移除 |
| requestLayoutParent | View 请求更新 |
| measureView | 测量 View |
| setLayoutGravity | 设置 View Layout Gravity |
| setMarginLeft | 设置 View Left Margin |
| setMarginTop | 设置 View Top Margin |
| setMarginRight | 设置 View Right Margin |
| setMarginBottom | 设置 View Bottom Margin |
| setMargin | 设置 Margin 边距 |
| setPaddingLeft | 设置 View Left Padding |
| setPaddingTop | 设置 View Top Padding |
| setPaddingRight | 设置 View Right Padding |
| setPaddingBottom | 设置 View Bottom Padding |
| setPadding | 设置 Padding 边距 |
| addRules | 设置多个 RelativeLayout View 布局规则 |
| removeRules | 移除多个 RelativeLayout View 布局规则 |
| setAnimation | 设置动画 |
| clearAnimation | 清空动画 |
| startAnimation | 启动动画 |
| cancelAnimation | 取消动画 |
| setBackground | 设置背景图片 |
| setBackgroundColor | 设置背景颜色 |
| setBackgroundResource | 设置背景资源 |
| setBackgroundTintList | 设置背景着色颜色 |
| setBackgroundTintMode | 设置背景着色模式 |
| setForeground | 设置前景图片 |
| setForegroundGravity | 设置前景重心 |
| setForegroundTintList | 设置前景着色颜色 |
| setForegroundTintMode | 设置前景着色模式 |
| removeBackground | 移除背景图片 |
| removeAllBackground | 移除背景图片 ( background、imageDrawable ) |
| removeForeground | 移除前景图片 |
| setColorFilter | View 着色处理 |
| setProgressDrawable | 设置 ProgressBar 进度条样式 |
| setBarProgress | 设置 ProgressBar 进度值 |
| setBarMax | 设置 ProgressBar 最大值 |
| setBarValue | 设置 ProgressBar 最大值 |
| smoothScrollToPosition | 滑动到指定索引 ( 有滚动过程 ) |
| scrollToPosition | 滑动到指定索引 ( 无滚动过程 ) |
| smoothScrollToTop | 滑动到顶部 ( 有滚动过程 ) |
| scrollToTop | 滑动到顶部 ( 无滚动过程 ) |
| smoothScrollToBottom | 滑动到底部 ( 有滚动过程 ) |
| scrollToBottom | 滑动到底部 ( 无滚动过程 ) |
| smoothScrollTo | 滚动到指定位置 ( 有滚动过程, 相对于初始位置移动 ) |
| smoothScrollBy | 滚动到指定位置 ( 有滚动过程, 相对于上次移动的最后位置移动 ) |
| fullScroll | 滚动方向 ( 有滚动过程 ) |
| scrollTo | View 内容滚动位置 ( 相对于初始位置移动 ) |
| scrollBy | View 内部滚动位置 ( 相对于上次移动的最后位置移动 ) |
| setAdjustViewBounds | 设置 ImageView 是否保持宽高比 |
| setMaxHeight | 设置 ImageView 最大高度 |
| setMaxWidth | 设置 ImageView 最大宽度 |
| setImageLevel | 设置 ImageView Level |
| setImageBitmap | 设置 ImageView Bitmap |
| setImageDrawable | 设置 ImageView Drawable |
| setImageResource | 设置 ImageView 资源 |
| setImageMatrix | 设置 ImageView Matrix |
| setImageTintList | 设置 ImageView 着色颜色 |
| setImageTintMode | 设置 ImageView 着色模式 |
| removeImageBitmap | 移除 ImageView Bitmap |
| removeImageDrawable | 移除 ImageView Drawable |
| setScaleType | 设置 ImageView 缩放类型 |
| setBackgroundResources | 设置 View 图片资源 |
| setImageResources | 设置 View 图片资源 |
| setImageBitmaps | 设置 View Bitmap |
| setImageDrawables | 设置 View Drawable |
| removeImageBitmaps | 移除 View Bitmap |
| removeImageDrawables | 移除 View Drawable |
| setScaleTypes | 设置 View 缩放模式 |
| setText | 设置文本 |
| setMaxLength | 设置长度限制 |
| setMaxLengthAndText | 设置长度限制, 并且设置内容 |
| setInputType | 设置输入类型 |
| setImeOptions | 设置软键盘右下角按钮类型 |
| setTransformationMethod | 设置文本视图显示转换 |
| insert | 追加内容 ( 当前光标位置追加 ) |
| setCursorVisible | 设置是否显示光标 |
| setTextCursorDrawable | 设置光标 |
| setSelectionToTop | 设置光标在第一位 |
| setSelectionToBottom | 设置光标在最后一位 |
| setSelection | 设置光标位置 |
| addTextChangedListener | 添加输入监听事件 |
| removeTextChangedListener | 移除输入监听事件 |
| setKeyListener | 设置 KeyListener |
| setHint | 设置 Hint 文本 |
| setHintTextColors | 设置多个 TextView Hint 字体颜色 |
| setTextColors | 设置多个 TextView 字体颜色 |
| setHtmlTexts | 设置多个 TextView Html 内容 |
| setTypeface | 设置字体 |
| setTextSizeByPx | 设置字体大小 ( px 像素 ) |
| setTextSizeBySp | 设置字体大小 ( sp 缩放像素 ) |
| setTextSizeByDp | 设置字体大小 ( dp 与设备无关的像素 ) |
| setTextSizeByIn | 设置字体大小 ( inches 英寸 ) |
| setTextSize | 设置字体大小 |
| clearFlags | 清空 flags |
| setPaintFlags | 设置 TextView flags |
| setAntiAliasFlag | 设置 TextView 抗锯齿 flags |
| setBold | 设置 TextView 是否加粗 |
| setUnderlineText | 设置下划线 |
| setStrikeThruText | 设置中划线 |
| setLetterSpacing | 设置文字水平间距 |
| setLineSpacing | 设置文字行间距 ( 行高 ) |
| setLineSpacingAndMultiplier | 设置文字行间距 ( 行高 ) 、行间距倍数 |
| setTextScaleX | 设置字体水平方向的缩放 |
| setIncludeFontPadding | 设置是否保留字体留白间隙区域 |
| setLines | 设置行数 |
| setMaxLines | 设置最大行数 |
| setMinLines | 设置最小行数 |
| setMaxEms | 设置最大字符宽度限制 |
| setMinEms | 设置最小字符宽度限制 |
| setEms | 设置指定字符宽度 |
| setEllipsize | 设置 Ellipsize 效果 |
| setAutoLinkMask | 设置自动识别文本链接 |
| setAllCaps | 设置文本全为大写 |
| setGravity | 设置 Gravity |
| setCompoundDrawablePadding | 设置 CompoundDrawables Padding |
| setCompoundDrawablesByLeft | 设置 Left CompoundDrawables |
| setCompoundDrawablesByTop | 设置 Top CompoundDrawables |
| setCompoundDrawablesByRight | 设置 Right CompoundDrawables |
| setCompoundDrawablesByBottom | 设置 Bottom CompoundDrawables |
| setCompoundDrawables | 设置 CompoundDrawables |
| setCompoundDrawablesWithIntrinsicBoundsByLeft | 设置 Left CompoundDrawables ( 按照原有比例大小显示图片 ) |
| setCompoundDrawablesWithIntrinsicBoundsByTop | 设置 Top CompoundDrawables ( 按照原有比例大小显示图片 ) |
| setCompoundDrawablesWithIntrinsicBoundsByRight | 设置 Right CompoundDrawables ( 按照原有比例大小显示图片 ) |
| setCompoundDrawablesWithIntrinsicBoundsByBottom | 设置 Bottom CompoundDrawables ( 按照原有比例大小显示图片 ) |
| setCompoundDrawablesWithIntrinsicBounds | 设置 CompoundDrawables ( 按照原有比例大小显示图片 ) |
| setAutoSizeTextTypeWithDefaults | 通过设置默认的自动调整大小配置, 决定是否自动缩放文本 |
| setAutoSizeTextTypeUniformWithConfiguration | 设置 TextView 自动调整字体大小配置 |
| setAutoSizeTextTypeUniformWithPresetSizes | 设置 TextView 自动调整如果预设字体大小范围有效则修改类型为 AUTO_SIZE_TEXT_TYPE_UNIFORM |
| setLayoutManager | 设置 RecyclerView LayoutManager |
| setSpanCount | 设置 GridLayoutManager SpanCount |
| setOrientation | 设置 RecyclerView Orientation |
| setAdapter | 设置 RecyclerView Adapter |
| notifyItemRemoved | RecyclerView notifyItemRemoved |
| notifyItemInserted | RecyclerView notifyItemInserted |
| notifyItemMoved | RecyclerView notifyItemMoved |
| notifyDataSetChanged | RecyclerView notifyDataSetChanged |
| attachLinearSnapHelper | 设置 RecyclerView LinearSnapHelper |
| attachPagerSnapHelper | 设置 RecyclerView PagerSnapHelper |
| addItemDecoration | 添加 RecyclerView ItemDecoration |
| removeItemDecoration | 移除 RecyclerView ItemDecoration |
| removeItemDecorationAt | 移除 RecyclerView ItemDecoration |
| removeAllItemDecoration | 移除 RecyclerView 全部 ItemDecoration |
| setOnScrollListener | 设置 RecyclerView ScrollListener |
| addOnScrollListener | 添加 RecyclerView ScrollListener |
| removeOnScrollListener | 移除 RecyclerView ScrollListener |
| clearOnScrollListeners | 清空 RecyclerView ScrollListener |
| setNestedScrollingEnabled | 设置 RecyclerView 嵌套滚动开关 |
| forceGetViewSize | 在 onCreate 中获取视图的尺寸 ( 需回调 onGetSizeListener 接口, 在 onGetSize 中获取 View 宽高 ) |


* **简化链式设置 View Quick Helper 类 ->** [QuickHelper.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/helper/quick/QuickHelper.java)

| 方法 | 注释 |
| :- | :- |
| get | 获取 QuickHelper |
| getView | 获取 View |
| targetView | 获取目标 View |
| targetViewGroup | 获取目标 View ( 转 ViewGroup ) |
| targetImageView | 获取目标 View ( 转 ImageView ) |
| targetTextView | 获取目标 View ( 转 TextView ) |
| targetEditText | 获取目标 View ( 转 EditText ) |
| targetRecyclerView | 获取目标 View ( 转 RecyclerView ) |
| flow | 执行 Action 流方法 |
| postRunnable | 在主线程 Handler 中执行任务 |
| removeRunnable | 在主线程 Handler 中清除任务 |
| addTouchArea | 增加控件的触摸范围, 最大范围只能是父布局所包含的的区域 |
| setOnClick | 设置点击事件 |
| setOnLongClick | 设置长按事件 |
| setOnTouch | 设置触摸事件 |
| setId | 设置 View Id |
| setClipChildren | 设置是否限制子 View 在其边界内绘制 |
| removeAllViews | 移除全部子 View |
| addView | 添加 View |
| setLayoutParams | 设置 View LayoutParams |
| setWidthHeight | 设置 View[] 宽度、高度 |
| setWeight | 设置 View weight 权重 |
| setWidth | 设置 View 宽度 |
| setHeight | 设置 View 高度 |
| setMinimumWidth | 设置 View 最小宽度 |
| setMinimumHeight | 设置 View 最小高度 |
| setAlpha | 设置 View 透明度 |
| setTag | 设置 View TAG |
| setScrollX | 设置 View 滑动的 X 轴坐标 |
| setScrollY | 设置 View 滑动的 Y 轴坐标 |
| setDescendantFocusability | 设置 ViewGroup 和其子控件两者之间的关系 |
| setOverScrollMode | 设置 View 滚动模式 |
| setHorizontalScrollBarEnabled | 设置是否绘制横向滚动条 |
| setVerticalScrollBarEnabled | 设置是否绘制垂直滚动条 |
| setScrollContainer | 设置 View 滚动效应 |
| setClipToOutline | 设置 View 是否使用 Outline 来裁剪 |
| setOutlineProvider | 设置 View 轮廓裁剪、绘制 |
| setOutlineProviderClip | 设置 View 轮廓裁剪、绘制并进行裁剪 |
| setNextFocusForwardId | 设置下一个获取焦点的 View id |
| setNextFocusDownId | 设置向下移动焦点时, 下一个获取焦点的 View id |
| setNextFocusLeftId | 设置向左移动焦点时, 下一个获取焦点的 View id |
| setNextFocusRightId | 设置向右移动焦点时, 下一个获取焦点的 View id |
| setNextFocusUpId | 设置向上移动焦点时, 下一个获取焦点的 View id |
| setRotation | 设置 View 旋转度数 |
| setRotationX | 设置 View 水平旋转度数 |
| setRotationY | 设置 View 竖直旋转度数 |
| setScaleX | 设置 View 水平方向缩放比例 |
| setScaleY | 设置 View 竖直方向缩放比例 |
| setTextAlignment | 设置文本的显示方式 |
| setTextDirection | 设置文本的显示方向 |
| setPivotX | 设置水平方向偏转量 |
| setPivotY | 设置竖直方向偏转量 |
| setTranslationX | 设置水平方向的移动距离 |
| setTranslationY | 设置竖直方向的移动距离 |
| setX | 设置 X 轴位置 |
| setY | 设置 Y 轴位置 |
| setLayerType | 设置 View 硬件加速类型 |
| requestLayout | 请求重新对 View 布局 |
| requestFocus | View 请求获取焦点 |
| clearFocus | View 清除焦点 |
| setFocusableInTouchMode | 设置 View 是否在触摸模式下获得焦点 |
| setFocusable | 设置 View 是否可以获取焦点 |
| toggleFocusable | 切换获取焦点状态 |
| setSelected | 设置 View 是否选中 |
| toggleSelected | 切换选中状态 |
| setEnabled | 设置 View 是否启用 |
| toggleEnabled | 切换 View 是否启用状态 |
| setClickable | 设置 View 是否可以点击 |
| toggleClickable | 切换 View 是否可以点击状态 |
| setLongClickable | 设置 View 是否可以长按 |
| toggleLongClickable | 切换 View 是否可以长按状态 |
| setVisibilitys | 设置 View 显示的状态 |
| setVisibilityINs | 设置 View 显示的状态 |
| toggleVisibilitys | 切换 View 显示的状态 |
| reverseVisibilitys | 反转 View 显示的状态 |
| toggleViews | 切换 View 状态 |
| viewVisible | 设置 View 显示状态 |
| viewVisibles | 设置 View 显示状态 |
| viewGone | 设置 View 隐藏状态 |
| viewGones | 设置 View 隐藏状态 |
| viewInVisible | 设置 View 隐藏占位状态 |
| viewInVisibles | 设置 View 隐藏占位状态 |
| removeSelfFromParent | 把自身从父 View 中移除 |
| requestLayoutParent | View 请求更新 |
| measureView | 测量 View |
| setLayoutGravity | 设置 View Layout Gravity |
| setMarginLeft | 设置 View Left Margin |
| setMarginTop | 设置 View Top Margin |
| setMarginRight | 设置 View Right Margin |
| setMarginBottom | 设置 View Bottom Margin |
| setMargin | 设置 Margin 边距 |
| setPaddingLeft | 设置 View Left Padding |
| setPaddingTop | 设置 View Top Padding |
| setPaddingRight | 设置 View Right Padding |
| setPaddingBottom | 设置 View Bottom Padding |
| setPadding | 设置 Padding 边距 |
| addRules | 设置多个 RelativeLayout View 布局规则 |
| removeRules | 移除多个 RelativeLayout View 布局规则 |
| setAnimation | 设置动画 |
| clearAnimation | 清空动画 |
| startAnimation | 启动动画 |
| cancelAnimation | 取消动画 |
| setBackground | 设置背景图片 |
| setBackgroundColor | 设置背景颜色 |
| setBackgroundResource | 设置背景资源 |
| setBackgroundTintList | 设置背景着色颜色 |
| setBackgroundTintMode | 设置背景着色模式 |
| setForeground | 设置前景图片 |
| setForegroundGravity | 设置前景重心 |
| setForegroundTintList | 设置前景着色颜色 |
| setForegroundTintMode | 设置前景着色模式 |
| removeBackground | 移除背景图片 |
| removeAllBackground | 移除背景图片 ( background、imageDrawable ) |
| removeForeground | 移除前景图片 |
| setColorFilter | View 着色处理 |
| setProgressDrawable | 设置 ProgressBar 进度条样式 |
| setBarProgress | 设置 ProgressBar 进度值 |
| setBarMax | 设置 ProgressBar 最大值 |
| setBarValue | 设置 ProgressBar 最大值 |
| smoothScrollToPosition | 滑动到指定索引 ( 有滚动过程 ) |
| scrollToPosition | 滑动到指定索引 ( 无滚动过程 ) |
| smoothScrollToTop | 滑动到顶部 ( 有滚动过程 ) |
| scrollToTop | 滑动到顶部 ( 无滚动过程 ) |
| smoothScrollToBottom | 滑动到底部 ( 有滚动过程 ) |
| scrollToBottom | 滑动到底部 ( 无滚动过程 ) |
| smoothScrollTo | 滚动到指定位置 ( 有滚动过程, 相对于初始位置移动 ) |
| smoothScrollBy | 滚动到指定位置 ( 有滚动过程, 相对于上次移动的最后位置移动 ) |
| fullScroll | 滚动方向 ( 有滚动过程 ) |
| scrollTo | View 内容滚动位置 ( 相对于初始位置移动 ) |
| scrollBy | View 内部滚动位置 ( 相对于上次移动的最后位置移动 ) |
| setAdjustViewBounds | 设置 ImageView 是否保持宽高比 |
| setMaxHeight | 设置 ImageView 最大高度 |
| setMaxWidth | 设置 ImageView 最大宽度 |
| setImageLevel | 设置 ImageView Level |
| setImageBitmap | 设置 ImageView Bitmap |
| setImageDrawable | 设置 ImageView Drawable |
| setImageResource | 设置 ImageView 资源 |
| setImageMatrix | 设置 ImageView Matrix |
| setImageTintList | 设置 ImageView 着色颜色 |
| setImageTintMode | 设置 ImageView 着色模式 |
| removeImageBitmap | 移除 ImageView Bitmap |
| removeImageDrawable | 移除 ImageView Drawable |
| setScaleType | 设置 ImageView 缩放类型 |
| setBackgroundResources | 设置 View 图片资源 |
| setImageResources | 设置 View 图片资源 |
| setImageBitmaps | 设置 View Bitmap |
| setImageDrawables | 设置 View Drawable |
| removeImageBitmaps | 移除 View Bitmap |
| removeImageDrawables | 移除 View Drawable |
| setScaleTypes | 设置 View 缩放模式 |
| setText | 设置文本 |
| setMaxLength | 设置长度限制 |
| setMaxLengthAndText | 设置长度限制, 并且设置内容 |
| setInputType | 设置输入类型 |
| setImeOptions | 设置软键盘右下角按钮类型 |
| setTransformationMethod | 设置文本视图显示转换 |
| insert | 追加内容 ( 当前光标位置追加 ) |
| setCursorVisible | 设置是否显示光标 |
| setTextCursorDrawable | 设置光标 |
| setSelectionToTop | 设置光标在第一位 |
| setSelectionToBottom | 设置光标在最后一位 |
| setSelection | 设置光标位置 |
| addTextChangedListener | 添加输入监听事件 |
| removeTextChangedListener | 移除输入监听事件 |
| setKeyListener | 设置 KeyListener |
| setHint | 设置 Hint 文本 |
| setHintTextColors | 设置多个 TextView Hint 字体颜色 |
| setTextColors | 设置多个 TextView 字体颜色 |
| setHtmlTexts | 设置多个 TextView Html 内容 |
| setTypeface | 设置字体 |
| setTextSizeByPx | 设置字体大小 ( px 像素 ) |
| setTextSizeBySp | 设置字体大小 ( sp 缩放像素 ) |
| setTextSizeByDp | 设置字体大小 ( dp 与设备无关的像素 ) |
| setTextSizeByIn | 设置字体大小 ( inches 英寸 ) |
| setTextSize | 设置字体大小 |
| clearFlags | 清空 flags |
| setPaintFlags | 设置 TextView flags |
| setAntiAliasFlag | 设置 TextView 抗锯齿 flags |
| setBold | 设置 TextView 是否加粗 |
| setUnderlineText | 设置下划线 |
| setStrikeThruText | 设置中划线 |
| setLetterSpacing | 设置文字水平间距 |
| setLineSpacing | 设置文字行间距 ( 行高 ) |
| setLineSpacingAndMultiplier | 设置文字行间距 ( 行高 ) 、行间距倍数 |
| setTextScaleX | 设置字体水平方向的缩放 |
| setIncludeFontPadding | 设置是否保留字体留白间隙区域 |
| setLines | 设置行数 |
| setMaxLines | 设置最大行数 |
| setMinLines | 设置最小行数 |
| setMaxEms | 设置最大字符宽度限制 |
| setMinEms | 设置最小字符宽度限制 |
| setEms | 设置指定字符宽度 |
| setEllipsize | 设置 Ellipsize 效果 |
| setAutoLinkMask | 设置自动识别文本链接 |
| setAllCaps | 设置文本全为大写 |
| setGravity | 设置 Gravity |
| setCompoundDrawablePadding | 设置 CompoundDrawables Padding |
| setCompoundDrawablesByLeft | 设置 Left CompoundDrawables |
| setCompoundDrawablesByTop | 设置 Top CompoundDrawables |
| setCompoundDrawablesByRight | 设置 Right CompoundDrawables |
| setCompoundDrawablesByBottom | 设置 Bottom CompoundDrawables |
| setCompoundDrawables | 设置 CompoundDrawables |
| setCompoundDrawablesWithIntrinsicBoundsByLeft | 设置 Left CompoundDrawables ( 按照原有比例大小显示图片 ) |
| setCompoundDrawablesWithIntrinsicBoundsByTop | 设置 Top CompoundDrawables ( 按照原有比例大小显示图片 ) |
| setCompoundDrawablesWithIntrinsicBoundsByRight | 设置 Right CompoundDrawables ( 按照原有比例大小显示图片 ) |
| setCompoundDrawablesWithIntrinsicBoundsByBottom | 设置 Bottom CompoundDrawables ( 按照原有比例大小显示图片 ) |
| setCompoundDrawablesWithIntrinsicBounds | 设置 CompoundDrawables ( 按照原有比例大小显示图片 ) |
| setAutoSizeTextTypeWithDefaults | 通过设置默认的自动调整大小配置, 决定是否自动缩放文本 |
| setAutoSizeTextTypeUniformWithConfiguration | 设置 TextView 自动调整字体大小配置 |
| setAutoSizeTextTypeUniformWithPresetSizes | 设置 TextView 自动调整如果预设字体大小范围有效则修改类型为 AUTO_SIZE_TEXT_TYPE_UNIFORM |
| setLayoutManager | 设置 RecyclerView LayoutManager |
| setSpanCount | 设置 GridLayoutManager SpanCount |
| setOrientation | 设置 RecyclerView Orientation |
| setAdapter | 设置 RecyclerView Adapter |
| notifyItemRemoved | RecyclerView notifyItemRemoved |
| notifyItemInserted | RecyclerView notifyItemInserted |
| notifyItemMoved | RecyclerView notifyItemMoved |
| notifyDataSetChanged | RecyclerView notifyDataSetChanged |
| attachLinearSnapHelper | 设置 RecyclerView LinearSnapHelper |
| attachPagerSnapHelper | 设置 RecyclerView PagerSnapHelper |
| addItemDecoration | 添加 RecyclerView ItemDecoration |
| removeItemDecoration | 移除 RecyclerView ItemDecoration |
| removeItemDecorationAt | 移除 RecyclerView ItemDecoration |
| removeAllItemDecoration | 移除 RecyclerView 全部 ItemDecoration |
| setOnScrollListener | 设置 RecyclerView ScrollListener |
| addOnScrollListener | 添加 RecyclerView ScrollListener |
| removeOnScrollListener | 移除 RecyclerView ScrollListener |
| clearOnScrollListeners | 清空 RecyclerView ScrollListener |
| setNestedScrollingEnabled | 设置 RecyclerView 嵌套滚动开关 |
| forceGetViewSize | 在 onCreate 中获取视图的尺寸 ( 需回调 onGetSizeListener 接口, 在 onGetSize 中获取 View 宽高 ) |


## <span id="devutilsapphelperversion">**`dev.utils.app.helper.version`**</span>


* **VersionHelper 接口 ->** [IHelperByVersion.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/helper/version/IHelperByVersion.java)

| 方法 | 注释 |
| :- | :- |
| getUriForFile | 获取 FileProvider File Uri |
| getUriForPath | 获取 FileProvider File Path Uri |
| getUriForFileToName | 获取 FileProvider File Path Uri ( 自动添加包名 ${applicationId} ) |
| getUriForString | 通过 String 获取 Uri |
| fromFile | 通过 File Path 创建 Uri |
| isUri | 判断是否 Uri |
| getUriScheme | 获取 Uri Scheme |
| isUriExists | 判断 Uri 路径资源是否存在 |
| copyByUri | 通过 Uri 复制文件 |
| getFilePathByUri | 通过 Uri 获取文件路径 |
| getMediaUri | 通过 File 获取 Media Uri |
| mediaQuery | 通过 File 获取 Media 信息 |
| notifyMediaStore | 通知刷新本地资源 |
| createImageUri | 创建图片 Uri |
| createVideoUri | 创建视频 Uri |
| createAudioUri | 创建音频 Uri |
| createDownloadUri | 创建 Download Uri |
| createMediaUri | 创建预存储 Media Uri |
| createUriByPath | 通过 File Path 创建 Uri |
| createUriByFile | 通过 File Path 创建 Uri |
| insertImage | 插入一张图片 |
| insertVideo | 插入一条视频 |
| insertAudio | 插入一条音频 |
| insertDownload | 插入一条文件资源 |
| insertMedia | 插入一条多媒体资源 |
| createWriteRequest | 获取用户向应用授予对指定媒体文件组的写入访问权限的请求 |
| createFavoriteRequest | 获取用户将设备上指定的媒体文件标记为收藏的请求 |
| createTrashRequest | 获取用户将指定的媒体文件放入设备垃圾箱的请求 |
| createDeleteRequest | 获取用户立即永久删除指定的媒体文件 ( 而不是先将其放入垃圾箱 ) 的请求 |
| isExternalStorageManager | 是否获得 MANAGE_EXTERNAL_STORAGE 权限 |
| checkExternalStorageAndIntentSetting | 检查是否有 MANAGE_EXTERNAL_STORAGE 权限并跳转设置页面 |


* **Android 版本适配 Helper 类 ->** [VersionHelper.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/helper/version/VersionHelper.java)

| 方法 | 注释 |
| :- | :- |
| get | 获取单例 VersionHelper |
| getUriForFile | 获取 FileProvider File Uri |
| getUriForPath | 获取 FileProvider File Path Uri |
| getUriForFileToName | 获取 FileProvider File Path Uri ( 自动添加包名 ${applicationId} ) |
| getUriForString | 通过 String 获取 Uri |
| fromFile | 通过 File Path 创建 Uri |
| isUri | 判断是否 Uri |
| getUriScheme | 获取 Uri Scheme |
| isUriExists | 判断 Uri 路径资源是否存在 |
| copyByUri | 通过 Uri 复制文件 |
| getFilePathByUri | 通过 Uri 获取文件路径 |
| getMediaUri | 通过 File 获取 Media Uri |
| mediaQuery | 通过 File 获取 Media 信息 |
| notifyMediaStore | 通知刷新本地资源 |
| createImageUri | 创建图片 Uri |
| createVideoUri | 创建视频 Uri |
| createAudioUri | 创建音频 Uri |
| createDownloadUri | 创建 Download Uri |
| createMediaUri | 创建预存储 Media Uri |
| createUriByPath | 通过 File Path 创建 Uri |
| createUriByFile | 通过 File Path 创建 Uri |
| insertImage | 插入一张图片 |
| insertVideo | 插入一条视频 |
| insertAudio | 插入一条音频 |
| insertDownload | 插入一条文件资源 |
| insertMedia | 插入一条多媒体资源 |
| createWriteRequest | 获取用户向应用授予对指定媒体文件组的写入访问权限的请求 |
| createFavoriteRequest | 获取用户将设备上指定的媒体文件标记为收藏的请求 |
| createTrashRequest | 获取用户将指定的媒体文件放入设备垃圾箱的请求 |
| createDeleteRequest | 获取用户立即永久删除指定的媒体文件 ( 而不是先将其放入垃圾箱 ) 的请求 |
| isExternalStorageManager | 是否获得 MANAGE_EXTERNAL_STORAGE 权限 |
| checkExternalStorageAndIntentSetting | 检查是否有 MANAGE_EXTERNAL_STORAGE 权限并跳转设置页面 |


## <span id="devutilsapphelperview">**`dev.utils.app.helper.view`**</span>


* **ViewHelper 接口 ->** [IHelperByView.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/helper/view/IHelperByView.java)

| 方法 | 注释 |
| :- | :- |
| addTouchArea | 增加控件的触摸范围, 最大范围只能是父布局所包含的的区域 |
| setOnClick | 设置点击事件 |
| setOnLongClick | 设置长按事件 |
| setOnTouch | 设置触摸事件 |
| setId | 设置 View Id |
| setClipChildren | 设置是否限制子 View 在其边界内绘制 |
| removeAllViews | 移除全部子 View |
| addView | 添加 View |
| setLayoutParams | 设置 View LayoutParams |
| setWidthHeight | 设置 View[] 宽度、高度 |
| setWeight | 设置 View weight 权重 |
| setWidth | 设置 View 宽度 |
| setHeight | 设置 View 高度 |
| setMinimumWidth | 设置 View 最小宽度 |
| setMinimumHeight | 设置 View 最小高度 |
| setAlpha | 设置 View 透明度 |
| setTag | 设置 View TAG |
| setScrollX | 设置 View 滑动的 X 轴坐标 |
| setScrollY | 设置 View 滑动的 Y 轴坐标 |
| setDescendantFocusability | 设置 ViewGroup 和其子控件两者之间的关系 |
| setOverScrollMode | 设置 View 滚动模式 |
| setHorizontalScrollBarEnabled | 设置是否绘制横向滚动条 |
| setVerticalScrollBarEnabled | 设置是否绘制垂直滚动条 |
| setScrollContainer | 设置 View 滚动效应 |
| setClipToOutline | 设置 View 是否使用 Outline 来裁剪 |
| setOutlineProvider | 设置 View 轮廓裁剪、绘制 |
| setOutlineProviderClip | 设置 View 轮廓裁剪、绘制并进行裁剪 |
| setNextFocusForwardId | 设置下一个获取焦点的 View id |
| setNextFocusDownId | 设置向下移动焦点时, 下一个获取焦点的 View id |
| setNextFocusLeftId | 设置向左移动焦点时, 下一个获取焦点的 View id |
| setNextFocusRightId | 设置向右移动焦点时, 下一个获取焦点的 View id |
| setNextFocusUpId | 设置向上移动焦点时, 下一个获取焦点的 View id |
| setRotation | 设置 View 旋转度数 |
| setRotationX | 设置 View 水平旋转度数 |
| setRotationY | 设置 View 竖直旋转度数 |
| setScaleX | 设置 View 水平方向缩放比例 |
| setScaleY | 设置 View 竖直方向缩放比例 |
| setTextAlignment | 设置文本的显示方式 |
| setTextDirection | 设置文本的显示方向 |
| setPivotX | 设置水平方向偏转量 |
| setPivotY | 设置竖直方向偏转量 |
| setTranslationX | 设置水平方向的移动距离 |
| setTranslationY | 设置竖直方向的移动距离 |
| setX | 设置 X 轴位置 |
| setY | 设置 Y 轴位置 |
| setLayerType | 设置 View 硬件加速类型 |
| requestLayout | 请求重新对 View 布局 |
| requestFocus | View 请求获取焦点 |
| clearFocus | View 清除焦点 |
| setFocusableInTouchMode | 设置 View 是否在触摸模式下获得焦点 |
| setFocusable | 设置 View 是否可以获取焦点 |
| toggleFocusable | 切换获取焦点状态 |
| setSelected | 设置 View 是否选中 |
| toggleSelected | 切换选中状态 |
| setEnabled | 设置 View 是否启用 |
| toggleEnabled | 切换 View 是否启用状态 |
| setClickable | 设置 View 是否可以点击 |
| toggleClickable | 切换 View 是否可以点击状态 |
| setLongClickable | 设置 View 是否可以长按 |
| toggleLongClickable | 切换 View 是否可以长按状态 |
| setVisibilitys | 设置 View 显示的状态 |
| setVisibilityINs | 设置 View 显示的状态 |
| toggleVisibilitys | 切换 View 显示的状态 |
| reverseVisibilitys | 反转 View 显示的状态 |
| toggleViews | 切换 View 状态 |
| viewVisible | 设置 View 显示状态 |
| viewVisibles | 设置 View 显示状态 |
| viewGone | 设置 View 隐藏状态 |
| viewGones | 设置 View 隐藏状态 |
| viewInVisible | 设置 View 隐藏占位状态 |
| viewInVisibles | 设置 View 隐藏占位状态 |
| removeSelfFromParent | 把自身从父 View 中移除 |
| requestLayoutParent | View 请求更新 |
| measureView | 测量 View |
| setLayoutGravity | 设置 View Layout Gravity |
| setMarginLeft | 设置 View Left Margin |
| setMarginTop | 设置 View Top Margin |
| setMarginRight | 设置 View Right Margin |
| setMarginBottom | 设置 View Bottom Margin |
| setMargin | 设置 Margin 边距 |
| setPaddingLeft | 设置 View Left Padding |
| setPaddingTop | 设置 View Top Padding |
| setPaddingRight | 设置 View Right Padding |
| setPaddingBottom | 设置 View Bottom Padding |
| setPadding | 设置 Padding 边距 |
| addRules | 设置多个 RelativeLayout View 布局规则 |
| removeRules | 移除多个 RelativeLayout View 布局规则 |
| setAnimation | 设置动画 |
| clearAnimation | 清空动画 |
| startAnimation | 启动动画 |
| cancelAnimation | 取消动画 |
| setBackground | 设置背景图片 |
| setBackgroundColor | 设置背景颜色 |
| setBackgroundResource | 设置背景资源 |
| setBackgroundTintList | 设置背景着色颜色 |
| setBackgroundTintMode | 设置背景着色模式 |
| setForeground | 设置前景图片 |
| setForegroundGravity | 设置前景重心 |
| setForegroundTintList | 设置前景着色颜色 |
| setForegroundTintMode | 设置前景着色模式 |
| removeBackground | 移除背景图片 |
| removeAllBackground | 移除背景图片 ( background、imageDrawable ) |
| removeForeground | 移除前景图片 |
| setColorFilter | View 着色处理 |
| setProgressDrawable | 设置 ProgressBar 进度条样式 |
| setBarProgress | 设置 ProgressBar 进度值 |
| setBarMax | 设置 ProgressBar 最大值 |
| setBarValue | 设置 ProgressBar 最大值 |
| smoothScrollToPosition | 滑动到指定索引 ( 有滚动过程 ) |
| scrollToPosition | 滑动到指定索引 ( 无滚动过程 ) |
| smoothScrollToTop | 滑动到顶部 ( 有滚动过程 ) |
| scrollToTop | 滑动到顶部 ( 无滚动过程 ) |
| smoothScrollToBottom | 滑动到底部 ( 有滚动过程 ) |
| scrollToBottom | 滑动到底部 ( 无滚动过程 ) |
| smoothScrollTo | 滚动到指定位置 ( 有滚动过程, 相对于初始位置移动 ) |
| smoothScrollBy | 滚动到指定位置 ( 有滚动过程, 相对于上次移动的最后位置移动 ) |
| fullScroll | 滚动方向 ( 有滚动过程 ) |
| scrollTo | View 内容滚动位置 ( 相对于初始位置移动 ) |
| scrollBy | View 内部滚动位置 ( 相对于上次移动的最后位置移动 ) |
| setAdjustViewBounds | 设置 ImageView 是否保持宽高比 |
| setMaxHeight | 设置 ImageView 最大高度 |
| setMaxWidth | 设置 ImageView 最大宽度 |
| setImageLevel | 设置 ImageView Level |
| setImageBitmap | 设置 ImageView Bitmap |
| setImageDrawable | 设置 ImageView Drawable |
| setImageResource | 设置 ImageView 资源 |
| setImageMatrix | 设置 ImageView Matrix |
| setImageTintList | 设置 ImageView 着色颜色 |
| setImageTintMode | 设置 ImageView 着色模式 |
| removeImageBitmap | 移除 ImageView Bitmap |
| removeImageDrawable | 移除 ImageView Drawable |
| setScaleType | 设置 ImageView 缩放类型 |
| setBackgroundResources | 设置 View 图片资源 |
| setImageResources | 设置 View 图片资源 |
| setImageBitmaps | 设置 View Bitmap |
| setImageDrawables | 设置 View Drawable |
| removeImageBitmaps | 移除 View Bitmap |
| removeImageDrawables | 移除 View Drawable |
| setScaleTypes | 设置 View 缩放模式 |
| setText | 设置文本 |
| setMaxLength | 设置长度限制 |
| setMaxLengthAndText | 设置长度限制, 并且设置内容 |
| setInputType | 设置输入类型 |
| setImeOptions | 设置软键盘右下角按钮类型 |
| setTransformationMethod | 设置文本视图显示转换 |
| insert | 追加内容 ( 当前光标位置追加 ) |
| setCursorVisible | 设置是否显示光标 |
| setTextCursorDrawable | 设置光标 |
| setSelectionToTop | 设置光标在第一位 |
| setSelectionToBottom | 设置光标在最后一位 |
| setSelection | 设置光标位置 |
| addTextChangedListener | 添加输入监听事件 |
| removeTextChangedListener | 移除输入监听事件 |
| setKeyListener | 设置 KeyListener |
| setHint | 设置 Hint 文本 |
| setHintTextColors | 设置多个 TextView Hint 字体颜色 |
| setTextColors | 设置多个 TextView 字体颜色 |
| setHtmlTexts | 设置多个 TextView Html 内容 |
| setTypeface | 设置字体 |
| setTextSizeByPx | 设置字体大小 ( px 像素 ) |
| setTextSizeBySp | 设置字体大小 ( sp 缩放像素 ) |
| setTextSizeByDp | 设置字体大小 ( dp 与设备无关的像素 ) |
| setTextSizeByIn | 设置字体大小 ( inches 英寸 ) |
| setTextSize | 设置字体大小 |
| clearFlags | 清空 flags |
| setPaintFlags | 设置 TextView flags |
| setAntiAliasFlag | 设置 TextView 抗锯齿 flags |
| setBold | 设置 TextView 是否加粗 |
| setUnderlineText | 设置下划线 |
| setStrikeThruText | 设置中划线 |
| setLetterSpacing | 设置文字水平间距 |
| setLineSpacing | 设置文字行间距 ( 行高 ) |
| setLineSpacingAndMultiplier | 设置文字行间距 ( 行高 ) 、行间距倍数 |
| setTextScaleX | 设置字体水平方向的缩放 |
| setIncludeFontPadding | 设置是否保留字体留白间隙区域 |
| setLines | 设置行数 |
| setMaxLines | 设置最大行数 |
| setMinLines | 设置最小行数 |
| setMaxEms | 设置最大字符宽度限制 |
| setMinEms | 设置最小字符宽度限制 |
| setEms | 设置指定字符宽度 |
| setEllipsize | 设置 Ellipsize 效果 |
| setAutoLinkMask | 设置自动识别文本链接 |
| setAllCaps | 设置文本全为大写 |
| setGravity | 设置 Gravity |
| setCompoundDrawablePadding | 设置 CompoundDrawables Padding |
| setCompoundDrawablesByLeft | 设置 Left CompoundDrawables |
| setCompoundDrawablesByTop | 设置 Top CompoundDrawables |
| setCompoundDrawablesByRight | 设置 Right CompoundDrawables |
| setCompoundDrawablesByBottom | 设置 Bottom CompoundDrawables |
| setCompoundDrawables | 设置 CompoundDrawables |
| setCompoundDrawablesWithIntrinsicBoundsByLeft | 设置 Left CompoundDrawables ( 按照原有比例大小显示图片 ) |
| setCompoundDrawablesWithIntrinsicBoundsByTop | 设置 Top CompoundDrawables ( 按照原有比例大小显示图片 ) |
| setCompoundDrawablesWithIntrinsicBoundsByRight | 设置 Right CompoundDrawables ( 按照原有比例大小显示图片 ) |
| setCompoundDrawablesWithIntrinsicBoundsByBottom | 设置 Bottom CompoundDrawables ( 按照原有比例大小显示图片 ) |
| setCompoundDrawablesWithIntrinsicBounds | 设置 CompoundDrawables ( 按照原有比例大小显示图片 ) |
| setAutoSizeTextTypeWithDefaults | 通过设置默认的自动调整大小配置, 决定是否自动缩放文本 |
| setAutoSizeTextTypeUniformWithConfiguration | 设置 TextView 自动调整字体大小配置 |
| setAutoSizeTextTypeUniformWithPresetSizes | 设置 TextView 自动调整如果预设字体大小范围有效则修改类型为 AUTO_SIZE_TEXT_TYPE_UNIFORM |
| setLayoutManager | 设置 RecyclerView LayoutManager |
| setSpanCount | 设置 GridLayoutManager SpanCount |
| setOrientation | 设置 RecyclerView Orientation |
| setAdapter | 设置 RecyclerView Adapter |
| notifyItemRemoved | RecyclerView notifyItemRemoved |
| notifyItemInserted | RecyclerView notifyItemInserted |
| notifyItemMoved | RecyclerView notifyItemMoved |
| notifyDataSetChanged | RecyclerView notifyDataSetChanged |
| attachLinearSnapHelper | 设置 RecyclerView LinearSnapHelper |
| attachPagerSnapHelper | 设置 RecyclerView PagerSnapHelper |
| addItemDecoration | 添加 RecyclerView ItemDecoration |
| removeItemDecoration | 移除 RecyclerView ItemDecoration |
| removeItemDecorationAt | 移除 RecyclerView ItemDecoration |
| removeAllItemDecoration | 移除 RecyclerView 全部 ItemDecoration |
| setOnScrollListener | 设置 RecyclerView ScrollListener |
| addOnScrollListener | 添加 RecyclerView ScrollListener |
| removeOnScrollListener | 移除 RecyclerView ScrollListener |
| clearOnScrollListeners | 清空 RecyclerView ScrollListener |
| setNestedScrollingEnabled | 设置 RecyclerView 嵌套滚动开关 |
| forceGetViewSize | 在 onCreate 中获取视图的尺寸 ( 需回调 onGetSizeListener 接口, 在 onGetSize 中获取 View 宽高 ) |


* **View 链式调用快捷设置 Helper 类 ->** [ViewHelper.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/helper/view/ViewHelper.java)

| 方法 | 注释 |
| :- | :- |
| get | 获取单例 ViewHelper |
| flow | 执行 Action 流方法 |
| postRunnable | 在主线程 Handler 中执行任务 |
| removeRunnable | 在主线程 Handler 中清除任务 |
| addTouchArea | 增加控件的触摸范围, 最大范围只能是父布局所包含的的区域 |
| setOnClick | 设置点击事件 |
| setOnLongClick | 设置长按事件 |
| setOnTouch | 设置触摸事件 |
| setId | 设置 View Id |
| setClipChildren | 设置是否限制子 View 在其边界内绘制 |
| removeAllViews | 移除全部子 View |
| addView | 添加 View |
| setLayoutParams | 设置 View LayoutParams |
| setWidthHeight | 设置 View[] 宽度、高度 |
| setWeight | 设置 View weight 权重 |
| setWidth | 设置 View 宽度 |
| setHeight | 设置 View 高度 |
| setMinimumWidth | 设置 View 最小宽度 |
| setMinimumHeight | 设置 View 最小高度 |
| setAlpha | 设置 View 透明度 |
| setTag | 设置 View TAG |
| setScrollX | 设置 View 滑动的 X 轴坐标 |
| setScrollY | 设置 View 滑动的 Y 轴坐标 |
| setDescendantFocusability | 设置 ViewGroup 和其子控件两者之间的关系 |
| setOverScrollMode | 设置 View 滚动模式 |
| setHorizontalScrollBarEnabled | 设置是否绘制横向滚动条 |
| setVerticalScrollBarEnabled | 设置是否绘制垂直滚动条 |
| setScrollContainer | 设置 View 滚动效应 |
| setClipToOutline | 设置 View 是否使用 Outline 来裁剪 |
| setOutlineProvider | 设置 View 轮廓裁剪、绘制 |
| setOutlineProviderClip | 设置 View 轮廓裁剪、绘制并进行裁剪 |
| setNextFocusForwardId | 设置下一个获取焦点的 View id |
| setNextFocusDownId | 设置向下移动焦点时, 下一个获取焦点的 View id |
| setNextFocusLeftId | 设置向左移动焦点时, 下一个获取焦点的 View id |
| setNextFocusRightId | 设置向右移动焦点时, 下一个获取焦点的 View id |
| setNextFocusUpId | 设置向上移动焦点时, 下一个获取焦点的 View id |
| setRotation | 设置 View 旋转度数 |
| setRotationX | 设置 View 水平旋转度数 |
| setRotationY | 设置 View 竖直旋转度数 |
| setScaleX | 设置 View 水平方向缩放比例 |
| setScaleY | 设置 View 竖直方向缩放比例 |
| setTextAlignment | 设置文本的显示方式 |
| setTextDirection | 设置文本的显示方向 |
| setPivotX | 设置水平方向偏转量 |
| setPivotY | 设置竖直方向偏转量 |
| setTranslationX | 设置水平方向的移动距离 |
| setTranslationY | 设置竖直方向的移动距离 |
| setX | 设置 X 轴位置 |
| setY | 设置 Y 轴位置 |
| setLayerType | 设置 View 硬件加速类型 |
| requestLayout | 请求重新对 View 布局 |
| requestFocus | View 请求获取焦点 |
| clearFocus | View 清除焦点 |
| setFocusableInTouchMode | 设置 View 是否在触摸模式下获得焦点 |
| setFocusable | 设置 View 是否可以获取焦点 |
| toggleFocusable | 切换获取焦点状态 |
| setSelected | 设置 View 是否选中 |
| toggleSelected | 切换选中状态 |
| setEnabled | 设置 View 是否启用 |
| toggleEnabled | 切换 View 是否启用状态 |
| setClickable | 设置 View 是否可以点击 |
| toggleClickable | 切换 View 是否可以点击状态 |
| setLongClickable | 设置 View 是否可以长按 |
| toggleLongClickable | 切换 View 是否可以长按状态 |
| setVisibilitys | 设置 View 显示的状态 |
| setVisibilityINs | 设置 View 显示的状态 |
| toggleVisibilitys | 切换 View 显示的状态 |
| reverseVisibilitys | 反转 View 显示的状态 |
| toggleViews | 切换 View 状态 |
| viewVisible | 设置 View 显示状态 |
| viewVisibles | 设置 View 显示状态 |
| viewGone | 设置 View 隐藏状态 |
| viewGones | 设置 View 隐藏状态 |
| viewInVisible | 设置 View 隐藏占位状态 |
| viewInVisibles | 设置 View 隐藏占位状态 |
| removeSelfFromParent | 把自身从父 View 中移除 |
| requestLayoutParent | View 请求更新 |
| measureView | 测量 View |
| setLayoutGravity | 设置 View Layout Gravity |
| setMarginLeft | 设置 View Left Margin |
| setMarginTop | 设置 View Top Margin |
| setMarginRight | 设置 View Right Margin |
| setMarginBottom | 设置 View Bottom Margin |
| setMargin | 设置 Margin 边距 |
| setPaddingLeft | 设置 View Left Padding |
| setPaddingTop | 设置 View Top Padding |
| setPaddingRight | 设置 View Right Padding |
| setPaddingBottom | 设置 View Bottom Padding |
| setPadding | 设置 Padding 边距 |
| addRules | 设置多个 RelativeLayout View 布局规则 |
| removeRules | 移除多个 RelativeLayout View 布局规则 |
| setAnimation | 设置动画 |
| clearAnimation | 清空动画 |
| startAnimation | 启动动画 |
| cancelAnimation | 取消动画 |
| setBackground | 设置背景图片 |
| setBackgroundColor | 设置背景颜色 |
| setBackgroundResource | 设置背景资源 |
| setBackgroundTintList | 设置背景着色颜色 |
| setBackgroundTintMode | 设置背景着色模式 |
| setForeground | 设置前景图片 |
| setForegroundGravity | 设置前景重心 |
| setForegroundTintList | 设置前景着色颜色 |
| setForegroundTintMode | 设置前景着色模式 |
| removeBackground | 移除背景图片 |
| removeAllBackground | 移除背景图片 ( background、imageDrawable ) |
| removeForeground | 移除前景图片 |
| setColorFilter | View 着色处理 |
| setProgressDrawable | 设置 ProgressBar 进度条样式 |
| setBarProgress | 设置 ProgressBar 进度值 |
| setBarMax | 设置 ProgressBar 最大值 |
| setBarValue | 设置 ProgressBar 最大值 |
| smoothScrollToPosition | 滑动到指定索引 ( 有滚动过程 ) |
| scrollToPosition | 滑动到指定索引 ( 无滚动过程 ) |
| smoothScrollToTop | 滑动到顶部 ( 有滚动过程 ) |
| scrollToTop | 滑动到顶部 ( 无滚动过程 ) |
| smoothScrollToBottom | 滑动到底部 ( 有滚动过程 ) |
| scrollToBottom | 滑动到底部 ( 无滚动过程 ) |
| smoothScrollTo | 滚动到指定位置 ( 有滚动过程, 相对于初始位置移动 ) |
| smoothScrollBy | 滚动到指定位置 ( 有滚动过程, 相对于上次移动的最后位置移动 ) |
| fullScroll | 滚动方向 ( 有滚动过程 ) |
| scrollTo | View 内容滚动位置 ( 相对于初始位置移动 ) |
| scrollBy | View 内部滚动位置 ( 相对于上次移动的最后位置移动 ) |
| setAdjustViewBounds | 设置 ImageView 是否保持宽高比 |
| setMaxHeight | 设置 ImageView 最大高度 |
| setMaxWidth | 设置 ImageView 最大宽度 |
| setImageLevel | 设置 ImageView Level |
| setImageBitmap | 设置 ImageView Bitmap |
| setImageDrawable | 设置 ImageView Drawable |
| setImageResource | 设置 ImageView 资源 |
| setImageMatrix | 设置 ImageView Matrix |
| setImageTintList | 设置 ImageView 着色颜色 |
| setImageTintMode | 设置 ImageView 着色模式 |
| removeImageBitmap | 移除 ImageView Bitmap |
| removeImageDrawable | 移除 ImageView Drawable |
| setScaleType | 设置 ImageView 缩放类型 |
| setBackgroundResources | 设置 View 图片资源 |
| setImageResources | 设置 View 图片资源 |
| setImageBitmaps | 设置 View Bitmap |
| setImageDrawables | 设置 View Drawable |
| removeImageBitmaps | 移除 View Bitmap |
| removeImageDrawables | 移除 View Drawable |
| setScaleTypes | 设置 View 缩放模式 |
| setText | 设置文本 |
| setMaxLength | 设置长度限制 |
| setMaxLengthAndText | 设置长度限制, 并且设置内容 |
| setInputType | 设置输入类型 |
| setImeOptions | 设置软键盘右下角按钮类型 |
| setTransformationMethod | 设置文本视图显示转换 |
| insert | 追加内容 ( 当前光标位置追加 ) |
| setCursorVisible | 设置是否显示光标 |
| setTextCursorDrawable | 设置光标 |
| setSelectionToTop | 设置光标在第一位 |
| setSelectionToBottom | 设置光标在最后一位 |
| setSelection | 设置光标位置 |
| addTextChangedListener | 添加输入监听事件 |
| removeTextChangedListener | 移除输入监听事件 |
| setKeyListener | 设置 KeyListener |
| setHint | 设置 Hint 文本 |
| setHintTextColors | 设置多个 TextView Hint 字体颜色 |
| setTextColors | 设置多个 TextView 字体颜色 |
| setHtmlTexts | 设置多个 TextView Html 内容 |
| setTypeface | 设置字体 |
| setTextSizeByPx | 设置字体大小 ( px 像素 ) |
| setTextSizeBySp | 设置字体大小 ( sp 缩放像素 ) |
| setTextSizeByDp | 设置字体大小 ( dp 与设备无关的像素 ) |
| setTextSizeByIn | 设置字体大小 ( inches 英寸 ) |
| setTextSize | 设置字体大小 |
| clearFlags | 清空 flags |
| setPaintFlags | 设置 TextView flags |
| setAntiAliasFlag | 设置 TextView 抗锯齿 flags |
| setBold | 设置 TextView 是否加粗 |
| setUnderlineText | 设置下划线 |
| setStrikeThruText | 设置中划线 |
| setLetterSpacing | 设置文字水平间距 |
| setLineSpacing | 设置文字行间距 ( 行高 ) |
| setLineSpacingAndMultiplier | 设置文字行间距 ( 行高 ) 、行间距倍数 |
| setTextScaleX | 设置字体水平方向的缩放 |
| setIncludeFontPadding | 设置是否保留字体留白间隙区域 |
| setLines | 设置行数 |
| setMaxLines | 设置最大行数 |
| setMinLines | 设置最小行数 |
| setMaxEms | 设置最大字符宽度限制 |
| setMinEms | 设置最小字符宽度限制 |
| setEms | 设置指定字符宽度 |
| setEllipsize | 设置 Ellipsize 效果 |
| setAutoLinkMask | 设置自动识别文本链接 |
| setAllCaps | 设置文本全为大写 |
| setGravity | 设置 Gravity |
| setCompoundDrawablePadding | 设置 CompoundDrawables Padding |
| setCompoundDrawablesByLeft | 设置 Left CompoundDrawables |
| setCompoundDrawablesByTop | 设置 Top CompoundDrawables |
| setCompoundDrawablesByRight | 设置 Right CompoundDrawables |
| setCompoundDrawablesByBottom | 设置 Bottom CompoundDrawables |
| setCompoundDrawables | 设置 CompoundDrawables |
| setCompoundDrawablesWithIntrinsicBoundsByLeft | 设置 Left CompoundDrawables ( 按照原有比例大小显示图片 ) |
| setCompoundDrawablesWithIntrinsicBoundsByTop | 设置 Top CompoundDrawables ( 按照原有比例大小显示图片 ) |
| setCompoundDrawablesWithIntrinsicBoundsByRight | 设置 Right CompoundDrawables ( 按照原有比例大小显示图片 ) |
| setCompoundDrawablesWithIntrinsicBoundsByBottom | 设置 Bottom CompoundDrawables ( 按照原有比例大小显示图片 ) |
| setCompoundDrawablesWithIntrinsicBounds | 设置 CompoundDrawables ( 按照原有比例大小显示图片 ) |
| setAutoSizeTextTypeWithDefaults | 通过设置默认的自动调整大小配置, 决定是否自动缩放文本 |
| setAutoSizeTextTypeUniformWithConfiguration | 设置 TextView 自动调整字体大小配置 |
| setAutoSizeTextTypeUniformWithPresetSizes | 设置 TextView 自动调整如果预设字体大小范围有效则修改类型为 AUTO_SIZE_TEXT_TYPE_UNIFORM |
| setLayoutManager | 设置 RecyclerView LayoutManager |
| setSpanCount | 设置 GridLayoutManager SpanCount |
| setOrientation | 设置 RecyclerView Orientation |
| setAdapter | 设置 RecyclerView Adapter |
| notifyItemRemoved | RecyclerView notifyItemRemoved |
| notifyItemInserted | RecyclerView notifyItemInserted |
| notifyItemMoved | RecyclerView notifyItemMoved |
| notifyDataSetChanged | RecyclerView notifyDataSetChanged |
| attachLinearSnapHelper | 设置 RecyclerView LinearSnapHelper |
| attachPagerSnapHelper | 设置 RecyclerView PagerSnapHelper |
| addItemDecoration | 添加 RecyclerView ItemDecoration |
| removeItemDecoration | 移除 RecyclerView ItemDecoration |
| removeItemDecorationAt | 移除 RecyclerView ItemDecoration |
| removeAllItemDecoration | 移除 RecyclerView 全部 ItemDecoration |
| setOnScrollListener | 设置 RecyclerView ScrollListener |
| addOnScrollListener | 添加 RecyclerView ScrollListener |
| removeOnScrollListener | 移除 RecyclerView ScrollListener |
| clearOnScrollListeners | 清空 RecyclerView ScrollListener |
| setNestedScrollingEnabled | 设置 RecyclerView 嵌套滚动开关 |
| forceGetViewSize | 在 onCreate 中获取视图的尺寸 ( 需回调 onGetSizeListener 接口, 在 onGetSize 中获取 View 宽高 ) |


## <span id="devutilsappimage">**`dev.utils.app.image`**</span>


* **Bitmap 工具类 ->** [BitmapUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/image/BitmapUtils.java)

| 方法 | 注释 |
| :- | :- |
| isEmpty | 判断 Bitmap 对象是否为 null |
| isNotEmpty | 判断 Bitmap 对象是否不为 null |
| isImage | 根据文件判断是否为图片 |
| getBitmapWidth | 获取 Bitmap 宽度 |
| getBitmapHeight | 获取 Bitmap 高度 |
| getBitmapWidthHeight | 获取 Bitmap 宽高 |
| copy | 复制 Bitmap |
| extractAlpha | 获取 Alpha 位图 ( 获取源图片的轮廓 rgb 为 0 ) |
| recode | 重新编码 Bitmap |
| recycle | Bitmap 通知回收 |
| rotate | 旋转图片 |
| getRotationDegrees | 读取图片属性, 获取图片旋转角度 |
| reverseByHorizontal | 水平翻转图片 ( 左右颠倒 ) |
| reverseByVertical | 垂直翻转图片 ( 上下颠倒 ) |
| reverse | 翻转图片 |
| zoom | 缩放图片 ( 指定所需宽高 ) |
| scale | 缩放图片 ( 比例缩放 ) |
| skew | 倾斜图片 |
| clip | 裁剪图片 |
| crop | 裁剪图片 ( 返回指定比例图片 ) |
| combine | 合并图片 |
| combineToCenter | 合并图片 ( 居中 ) |
| combineToSameSize | 合并图片 ( 转为相同大小 ) |
| reflection | 图片倒影处理 |
| roundCorner | 图片圆角处理 ( 非圆形 ) |
| roundCornerTop | 图片圆角处理 ( 非圆形, 只有 leftTop、rightTop ) |
| roundCornerBottom | 图片圆角处理 ( 非圆形, 只有 leftBottom、rightBottom ) |
| round | 图片圆形处理 |
| addCornerBorder | 添加圆角边框 |
| addCircleBorder | 添加圆形边框 |
| addBorder | 添加边框 |
| addTextWatermark | 添加文字水印 |
| addImageWatermark | 添加图片水印 |
| compressByZoom | 按缩放宽高压缩 |
| compressByScale | 按缩放比例压缩 |
| compressByQuality | 按质量压缩 |
| compressByByteSize | 按质量压缩 ( 图片大小 ) |
| compressBySampleSize | 按采样大小压缩 |
| calculateInSampleSize | 计算采样大小 |
| calculateQuality | 计算最佳压缩质量值 |
| getVideoThumbnail | 获取视频缩略图 |


* **图片 ( 滤镜、效果 ) 工具类 ->** [ImageFilterUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/image/ImageFilterUtils.java)

| 方法 | 注释 |
| :- | :- |
| blur | 图片模糊处理 ( Android RenderScript 实现, 效率最高 ) |
| fastBlur | 图片模糊处理 ( 毛玻璃化 FastBlur Java 实现 ) |
| nostalgic | 怀旧效果处理 |
| sunshine | 光照效果处理 |
| film | 底片效果处理 |
| soften | 柔化效果处理 |
| sharpen | 锐化效果处理 |
| emboss | 浮雕效果处理 |
| gray | 转为灰度图片 |
| saturation | 饱和度处理 |
| lum | 亮度处理 |
| hue | 色相处理 |
| lumHueSaturation | 亮度、色相、饱和度处理 |


* **Image ( Bitmap、Drawable 等 ) 工具类 ->** [ImageUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/image/ImageUtils.java)

| 方法 | 注释 |
| :- | :- |
| isEmpty | 判断 Bitmap 对象是否为 null |
| isNotEmpty | 判断 Bitmap 对象是否不为 null |
| isImageFormats | 根据文件名判断文件是否为图片 |
| getImageType | 获取图片类型 |
| isPNG | 判断是否 PNG 图片 |
| isJPEG | 判断是否 JPG 图片 |
| isBMP | 判断是否 BMP 图片 |
| isGif | 判断是否 GIF 图片 |
| isWEBP | 判断是否 WEBP 图片 |
| isICO | 判断是否 ICO 图片 |
| isTIFF | 判断是否 TIFF 图片 |
| decodeFile | 获取 Bitmap |
| decodeResource | 获取 Bitmap |
| decodeStream | 获取 Bitmap |
| decodeFileDescriptor | 获取 Bitmap |
| decodeByteArray | 获取 Bitmap |
| saveBitmapToSDCardJPEG | 保存图片到 SDCard ( JPEG ) |
| saveBitmapToSDCardPNG | 保存图片到 SDCard ( PNG ) |
| saveBitmapToSDCardWEBP | 保存图片到 SDCard ( WEBP ) |
| saveBitmapToSDCard | 保存图片到 SDCard |
| saveBitmapToStreamJPEG | 保存 JPEG 图片 |
| saveBitmapToStreamPNG | 保存 PNG 图片 |
| saveBitmapToStreamWEBP | 保存 WEBP 图片 |
| saveBitmapToStream | 保存图片 |
| get9PatchDrawable | 获取 .9 Drawable |
| setColorFilter | 图片着色 ( tint ) |
| getBitmap | 获取 Bitmap |
| getBitmapFromView | 通过 View 绘制为 Bitmap |
| getBitmapFromViewCache | 通过 View Cache 绘制为 Bitmap |
| bitmapToByte | Bitmap 转换成 byte[] |
| drawableToByte | Drawable 转换成 byte[] |
| byteToBitmap | byte[] 转 Bitmap |
| bitmapToDrawable | Bitmap 转 Drawable |
| byteToDrawable | byte[] 转 Drawable |
| drawableToBitmap | Drawable 转 Bitmap |
| colorDrawableToBitmap | ColorDrawable 转 Bitmap |
| setBounds | 设置 Drawable 绘制区域 |


## <span id="devutilsappinfo">**`dev.utils.app.info`**</span>


* **APK 信息 Item ->** [ApkInfoItem.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/info/ApkInfoItem.java)

| 方法 | 注释 |
| :- | :- |
| get | 获取 ApkInfoItem |
| getAppInfoBean | 获取 AppInfoBean |
| getListKeyValues | 获取 List 信息键对值集合 |
| getAppMD5 | 获取 APP MD5 签名 |
| getAppSHA1 | 获取 APP SHA1 签名 |
| getAppSHA256 | 获取 APP SHA256 签名 |
| getMinSdkVersion | 获取 APP 最低支持 Android SDK 版本 |
| getTargetSdkVersion | 获取 APP 兼容 SDK 版本 |
| getApkLength | 获取 APP 安装包大小 |
| getX509Certificate | 获取证书对象 |
| getNotBefore | 获取证书生成日期 |
| getNotAfter | 获取证书有效期 |
| isEffective | 获取证书是否过期 |
| getCertPrincipal | 获取证书发布方 |
| getCertVersion | 获取证书版本号 |
| getCertSigAlgName | 获取证书算法名称 |
| getCertSigAlgOID | 获取证书算法 OID |
| getCertSerialnumber | 获取证书机器码 |
| getCertDERCode | 获取证书 DER 编码 |


* **APP 信息实体类 ->** [AppInfoBean.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/info/AppInfoBean.java)

| 方法 | 注释 |
| :- | :- |
| get | 获取 AppInfoBean |
| getAppPackName | 获取 APP 包名 |
| getAppName | 获取 APP 应用名 |
| getAppIcon | 获取 APP 图标 |
| getAppType | 获取 APP 类型 |
| getVersionCode | 获取 versionCode |
| getVersionName | 获取 versionName |
| getFirstInstallTime | 获取 APP 首次安装时间 |
| getLastUpdateTime | 获取 APP 最后更新时间 |
| getSourceDir | 获取 APK 地址 |
| getApkSize | 获取 APK 大小 |
| isSystemApp | 是否系统程序 |
| isSystemUpdateApp | 是否系统程序被手动更新后, 也成为第三方应用程序 |


* **APP 信息 Item ->** [AppInfoItem.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/info/AppInfoItem.java)

| 方法 | 注释 |
| :- | :- |
| get | 获取 AppInfoItem |
| getAppInfoBean | 获取 AppInfoBean |
| getListKeyValues | 获取 List 信息键对值集合 |
| getAppMD5 | 获取 APP MD5 签名 |
| getAppSHA1 | 获取 APP SHA1 签名 |
| getAppSHA256 | 获取 APP SHA256 签名 |
| getMinSdkVersion | 获取 APP 最低支持 Android SDK 版本 |
| getTargetSdkVersion | 获取 APP 兼容 SDK 版本 |
| getApkLength | 获取 APP 安装包大小 |
| getX509Certificate | 获取证书对象 |
| getNotBefore | 获取证书生成日期 |
| getNotAfter | 获取证书有效期 |
| isEffective | 获取证书是否过期 |
| getCertPrincipal | 获取证书发布方 |
| getCertVersion | 获取证书版本号 |
| getCertSigAlgName | 获取证书算法名称 |
| getCertSigAlgOID | 获取证书算法 OID |
| getCertSerialnumber | 获取证书机器码 |
| getCertDERCode | 获取证书 DER 编码 |


* **APP 信息获取工具类 ->** [AppInfoUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/info/AppInfoUtils.java)

| 方法 | 注释 |
| :- | :- |
| getPackageInfoToFile | 通过 APK 路径 初始化 PackageInfo |
| getPackageInfoToPath | 通过 APK 路径 初始化 PackageInfo |
| getPackageInfo | 获取当前应用 PackageInfo |
| getAppInfoBeanToFile | 通过 APK 路径 获取 AppInfoBean |
| getAppInfoBeanToPath | 通过 APK 路径 获取 AppInfoBean |
| getAppInfoBean | 获取当前应用 AppInfoBean |
| getApkInfoItem | 获取 APK 详细信息 |
| getAppInfoItem | 获取 APP 详细信息 |
| getAppLists | 获取全部 APP 列表 |
| getAppPermissionToList | 获取 APP 注册的权限 |
| getAppPermissionToSet | 获取 APP 注册的权限 |
| getAppPermission | 获取 APP 注册的权限 |
| printAppPermission | 打印 APP 注册的权限 |
| getAllLauncherIconPackages | 获取所有能够显示在桌面上的应用 |
| getAllLauncherIconPackageNames | 获取所有能够显示在桌面上的应用包名 |


* **键对值实体类 ->** [KeyValue.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/info/KeyValue.java)

| 方法 | 注释 |
| :- | :- |
| getKey | 获取 key |
| getValue | 获取 value |
| get | 通过 resId 设置 key |


## <span id="devutilsapplogger">**`dev.utils.app.logger`**</span>


* **日志操作类 ( 对外公开直接调用 ) ->** [DevLogger.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/logger/DevLogger.java)

| 方法 | 注释 |
| :- | :- |
| other | 使用单次其他日志配置 |
| getLogConfig | 获取日志配置信息 |
| initialize | 初始化日志配置信息 ( 使用默认配置 ) |
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
| setPrint | 设置日志输出接口 |
| printLog | 日志打印 |


* **日志配置类 ->** [LogConfig.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/logger/LogConfig.java)

| 方法 | 注释 |
| :- | :- |
| getReleaseLogConfig | 获取 Release Log 配置 ( 打印线程信息、显示方法总数 3、从 0 开始、不进行排序、默认只打印 ERROR 级别日志 ) |
| getDebugLogConfig | 获取 Debug Log 配置 ( 打印线程信息、显示方法总数 3、从 0 开始、不进行排序、默认只打印 ERROR 级别日志 ) |
| getSortLogConfig | 获取 Log 配置 ( 打印线程信息、显示方法总数 3、从 0 开始、并且美化日志信息、默认打印 DEBUG 级别及以上日志 ) |
| getLogConfig | 获取 Log 配置 |
| methodCount | 设置堆栈方法总数 |
| methodOffset | 设置堆栈方法索引偏移 |
| outputMethodAll | 设置是否输出全部方法 |
| displayThreadInfo | 设置是否显示日志线程信息 |
| sortLog | 设置是否排序日志 |
| logLevel | 设置日志级别 |
| tag | 设置 TAG |


## <span id="devutilsappplayer">**`dev.utils.app.player`**</span>


* **MediaPlayer 统一管理类 ->** [DevMediaManager.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/player/DevMediaManager.java)

| 方法 | 注释 |
| :- | :- |
| getInstance | 获取 DevMediaManager 实例 |
| setAudioStreamType | 设置流类型 |
| playPrepareRaw | 播放 Raw 资源 |
| playPrepareAssets | 播放 Assets 资源 |
| playPrepare | 预加载播放 ( file-path or http/rtsp URL ) http 资源、本地资源 |
| isPlaying | 是否播放中 |
| pause | 暂停操作 |
| stop | 停止操作 ( 销毁 MediaPlayer ) |
| isIgnoreWhat | 是否忽略错误类型 |
| setMediaListener | 设置 MediaPlayer 回调事件 |
| isNullMediaPlayer | 判断 MediaPlayer 是否为 null |
| isNotNullMediaPlayer | 判断 MediaPlayer 是否不为 null |
| getMediaPlayer | 获取 MediaPlayer 对象 |
| setMediaPlayer | 设置 MediaPlayer 对象 |
| setTAG | 设置日志打印 TAG |
| getVolume | 获取播放音量 |
| setVolume | 设置播放音量 |
| getPlayRawId | 获取播放资源 id |
| getPlayUri | 获取播放地址 |
| getVideoWidth | 获取视频宽度 |
| getVideoHeight | 获取视频高度 |
| getCurrentPosition | 获取播放时间 |
| getDuration | 获取资源总时间 |
| getPlayPercent | 获取播放进度百分比 |


* **视频播放控制器 ->** [DevVideoPlayerControl.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/player/DevVideoPlayerControl.java)

| 方法 | 注释 |
| :- | :- |
| setMediaListener | 设置播放监听事件 |
| pausePlayer | 暂停播放 |
| stopPlayer | 停止播放 |
| startPlayer | 开始播放 |
| getSurfaceView | 获取 SurfaceView |
| isPlaying | 是否播放中 |
| isAutoPlay | 判断是否自动播放 |
| setAutoPlay | 设置自动播放 |
| getPlayUri | 获取播放地址 |
| getVideoWidth | 获取视频宽度 |
| getVideoHeight | 获取视频高度 |
| getCurrentPosition | 获取播放时间 |
| getDuration | 获取资源总时间 |
| getPlayPercent | 获取播放进度百分比 |


## <span id="devutilsappshare">**`dev.utils.app.share`**</span>


* **SharedPreferences 操作监听器 ->** [OnSPOperateListener.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/share/OnSPOperateListener.java)

| 方法 | 注释 |
| :- | :- |
| onPut | put 操作回调 |
| onPutByMap | put 操作回调 ( 循环 Map 触发 ) |
| onRemove | remove 操作回调 |
| onRemoveByList | remove 操作回调 ( 循环 List 触发 ) |
| clear | 清除全部数据 |
| onGet | get 操作回调 |


* **SPUtils 快捷工具类 ->** [SharedUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/share/SharedUtils.java)

| 方法 | 注释 |
| :- | :- |
| registerListener | 注册 SharedPreferences 操作监听器 |
| unregisterListener | 注销 SharedPreferences 操作监听器 |
| put | 保存数据 |
| putAll | 保存 Map 集合 ( 只能是 Integer、Long、Boolean、Float、String、Set ) |
| get | 根据 key 获取数据 |
| getAll | 获取全部数据 |
| remove | 移除数据 |
| removeAll | 移除集合的数据 |
| contains | 是否存在 key |
| clear | 清除全部数据 |
| getInt | 获取 int 类型的数据 |
| getLong | 获取 long 类型的数据 |
| getFloat | 获取 float 类型的数据 |
| getDouble | 获取 double 类型的数据 |
| getBoolean | 获取 boolean 类型的数据 |
| getString | 获取 String 类型的数据 |
| getSet | 获取 Set 类型的数据 |


## <span id="devutilsapptimer">**`dev.utils.app.timer`**</span>


* **定时器 ->** [DevTimer.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/timer/DevTimer.java)

| 方法 | 注释 |
| :- | :- |
| getTag | 获取 TAG |
| getUUID | 获取 UUID HashCode |
| getDelay | 获取延迟时间 ( 多少毫秒后开始执行 ) |
| getPeriod | 获取循环时间 ( 每隔多少毫秒执行一次 ) |
| isRunning | 判断是否运行中 |
| isMarkSweep | 是否标记清除 |
| getTriggerNumber | 获取已经触发的次数 |
| getTriggerLimit | 获取允许触发的上限次数 |
| isTriggerEnd | 是否触发结束 ( 到达最大次数 ) |
| isInfinite | 是否无限循环 |
| setHandler | 设置 UI Handler |
| setCallback | 设置回调事件 |
| start | 运行定时器 |
| stop | 关闭定时器 |
| setTag | setTag |
| setDelay | setDelay |
| setPeriod | setPeriod |
| getLimit | getLimit |
| setLimit | setLimit |
| build | build |
| callback | 触发回调方法 |


* **定时器管理类 ->** [TimerManager.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/timer/TimerManager.java)

| 方法 | 注释 |
| :- | :- |
| addContainsChecker | 添加包含校验 |
| getSize | 获取全部定时器总数 |
| recycle | 回收定时器资源 |
| getTimer | 获取对应 TAG 定时器 ( 优先获取符合的 ) |
| getTimers | 获取对应 TAG 定时器集合 |
| closeAll | 关闭全部定时器 |
| closeAllNotRunning | 关闭所有未运行的定时器 |
| closeAllInfinite | 关闭所有无限循环的定时器 |
| closeAllTag | 关闭所有对应 TAG 定时器 |
| closeAllUUID | 关闭所有对应 UUID 定时器 |
| startTimer | 运行定时器 |
| stopTimer | 关闭定时器 |


## <span id="devutilscommon">**`dev.utils.common`**</span>


* **Array 数组工具类 ->** [ArrayUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/ArrayUtils.java)

| 方法 | 注释 |
| :- | :- |
| isEmpty | 判断数组是否为 null |
| isNotEmpty | 判断数组是否不为 null |
| length | 获取数组长度 |
| isLength | 判断数组长度是否等于期望长度 |
| getCount | 获取数组长度总和 |
| getByArray | 获取数组对应索引数据 |
| get | 获取数组对应索引数据 |
| getFirst | 获取数组第一条数据 |
| getLast | 获取数组最后一条数据 |
| getPosition | 根据指定值获取 value 所在位置 + 偏移量的索引 |
| getNotNull | 根据指定 value 获取 value 所在位置 + 偏移量的值, 不允许值为 null |
| getPositionNotNull | 根据指定 value 获取索引, 不允许值为 null |
| intsToIntegers | int[] 转换 Integer[] |
| bytesToBytes | byte[] 转换 Byte[] |
| charsToCharacters | char[] 转换 Character[] |
| shortsToShorts | short[] 转换 Short[] |
| longsToLongs | long[] 转换 Long[] |
| floatsToFloats | float[] 转换 Float[] |
| doublesToDoubles | double[] 转换 Double[] |
| booleansToBooleans | boolean[] 转换 Boolean[] |
| integersToInts | Integer[] 转换 int[] |
| charactersToChars | Character[] 转换 char[] |
| asList | 转换数组为集合 |
| asListArgs | 转换数组为集合 |
| asListArgsInt | 转换数组为集合 |
| asListArgsByte | 转换数组为集合 |
| asListArgsChar | 转换数组为集合 |
| asListArgsShort | 转换数组为集合 |
| asListArgsLong | 转换数组为集合 |
| asListArgsFloat | 转换数组为集合 |
| asListArgsDouble | 转换数组为集合 |
| asListArgsBoolean | 转换数组为集合 |
| equals | 判断两个值是否一样 |
| arrayCopy | 拼接数组 |
| newArray | 创建指定长度数组 |
| subArray | 从数组上截取一段 |
| appendToString | 追加数组内容字符串 |
| getMinimumIndex | 获取数组中最小值索引 |
| getMaximumIndex | 获取数组中最大值索引 |
| getMinimum | 获取数组中最小值 |
| getMaximum | 获取数组中最大值 |
| sumArray | 计算数组总和 |


* **资金运算工具类 ->** [BigDecimalUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/BigDecimalUtils.java)

| 方法 | 注释 |
| :- | :- |
| setScale | 设置全局小数点保留位数、舍入模式 |
| getBigDecimal | 获取 BigDecimal |
| operation | 获取 Operation |
| adjustDouble | 获取自己想要的数据格式 |
| compareTo | 比较大小 |
| compareToThrow | 比较大小 ( 抛出异常 ) |
| add | 提供精确的加法运算 |
| subtract | 提供精确的减法运算 |
| multiply | 提供精确的乘法运算 |
| divide | 提供精确的除法运算 |
| remainder | 提供精确的取余运算 |
| round | 提供精确的小数位四舍五入处理 |
| addThrow | 提供精确的加法运算 ( 抛出异常 ) |
| subtractThrow | 提供精确的减法运算 ( 抛出异常 ) |
| multiplyThrow | 提供精确的乘法运算 ( 抛出异常 ) |
| divideThrow | 提供精确的除法运算 ( 抛出异常 ) |
| remainderThrow | 提供精确的取余运算 ( 抛出异常 ) |
| roundThrow | 提供精确的小数位四舍五入处理 ( 抛出异常 ) |
| getScale | 获取小数点保留位数 |
| getRoundingMode | 获取舍入模式 |
| requireNonNull | 检查 Value 是否为 null, 为 null 则抛出异常 |
| setBigDecimal | 设置 Value |
| getConfig | 获取配置信息 |
| setConfig | 设置配置信息 |
| removeConfig | 移除配置信息 |
| setScaleByConfig | 设置小数点保留位数、舍入模式 |
| isThrowError | 是否抛出异常 |
| setThrowError | 设置是否抛出异常 |
| clone | 克隆对象 |
| toString | 获取此 BigDecimal 的字符串表示形式科学记数法 |
| toPlainString | 获取此 BigDecimal 的字符串表示形式不带指数字段 |
| toEngineeringString | 获取此 BigDecimal 的字符串表示形式工程计数法 |
| intValue | 获取指定类型值 |
| floatValue | 获取指定类型值 |
| longValue | 获取指定类型值 |
| doubleValue | 获取指定类型值 |
| formatMoney | 金额分割, 四舍五入金额 |


* **日历工具类 ->** [CalendarUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/CalendarUtils.java)

| 方法 | 注释 |
| :- | :- |
| isSupportLunar | 是否支持农历年份计算 |
| isSupportSolar | 是否支持公历年份计算 |
| solarToLunar | 公历转农历 |
| lunarToSolar | 农历转公历 |
| getLunarYearDays | 获取农历年份总天数 |
| getLunarLeapDays | 获取农历年份闰月天数 |
| getLunarLeapMonth | 获取农历年份哪个月是闰月 |
| getLunarMonthDays | 获取农历年份与月份总天数 |
| getLunarGanZhi | 获取干支历 |
| getLunarMonthChinese | 获取农历中文月份 |
| getLunarDayChinese | 获取农历中文天数 |
| getSolarTermsIndex | 获取二十四节气 ( 公历 ) 索引 |
| getSolarTerms | 获取二十四节气 ( 公历 ) |
| getSolarTermsDate | 获取二十四节气 ( 公历 ) 时间 |
| isFestival | 校验是否相同节日 |
| getFestival | 获取符合条件的节日信息 |
| getSolarFestival | 获取公历符合条件的节日信息 |
| getLunarFestival | 获取农历符合条件的节日信息 |
| getFestivalHook | 获取节日 Hook 接口 |
| setFestivalHook | 设置节日 Hook 接口 |


* **中文工具类 ->** [ChineseUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/ChineseUtils.java)

| 方法 | 注释 |
| :- | :- |
| randomWord | 随机生成汉字 |
| randomName | 随机生成名字 |
| numberToCHN | 数字转中文数值 |


* **类 ( Class ) 工具类 ->** [ClassUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/ClassUtils.java)

| 方法 | 注释 |
| :- | :- |
| newInstance | 根据类获取对象, 不再必须一个无参构造 |
| getDefaultPrimitiveValue | 获取 Class 原始类型值 |
| getClass | 获取 Object Class |
| isPrimitive | 判断 Class 是否为原始类型 |
| isCollection | 判断是否 Collection 类型 |
| isMap | 判断是否 Map 类型 |
| isArray | 判断是否 Array 类型 |
| isGenericParamType | 判断是否参数类型 |
| getGenericParamType | 获取参数类型 |
| getGenericSuperclass | 获取父类泛型类型 |
| getGenericInterfaces | 获取接口泛型类型 |


* **克隆工具类 ->** [CloneUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/CloneUtils.java)

| 方法 | 注释 |
| :- | :- |
| deepClone | 进行克隆 |
| serializableToBytes | 通过序列化实体类, 获取对应的 byte[] 数据 |


* **关闭 ( IO 流 ) 工具类 ->** [CloseUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/CloseUtils.java)

| 方法 | 注释 |
| :- | :- |
| closeIO | 关闭 IO |
| closeIOQuietly | 安静关闭 IO |
| flush | 将缓冲区数据输出 |
| flushQuietly | 安静将缓冲区数据输出 |
| flushCloseIO | 将缓冲区数据输出并关闭流 |
| flushCloseIOQuietly | 安静将缓冲区数据输出并关闭流 |


* **集合工具类 ( Collection - List、Set、Queue ) 等 ->** [CollectionUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/CollectionUtils.java)

| 方法 | 注释 |
| :- | :- |
| isEmpty | 判断 Collection 是否为 null |
| isNotEmpty | 判断 Collection 是否不为 null |
| length | 获取 Collection 长度 |
| isLength | 获取长度 Collection 是否等于期望长度 |
| greaterThan | 判断 Collection 长度是否大于指定长度 |
| greaterThanOrEqual | 判断 Collection 长度是否大于等于指定长度 |
| lessThan | 判断 Collection 长度是否小于指定长度 |
| lessThanOrEqual | 判断 Collection 长度是否小于等于指定长度 |
| getCount | 获取 Collection 数组长度总和 |
| get | 获取数据 |
| getFirst | 获取第一条数据 |
| getLast | 获取最后一条数据 |
| getPosition | 根据指定 value 获取 value 所在位置 + 偏移量的索引 |
| getPositionNotNull | 根据指定 value 获取索引, 不允许值为 null |
| getNext | 根据指定 value 获取 value 所在位置的下一个值 |
| getNextNotNull | 根据指定 value 获取 value 所在位置的下一个值, 不允许值为 null |
| getPrevious | 根据指定 value 获取 value 所在位置的上一个值 |
| getPreviousNotNull | 根据指定 value 获取 value 所在位置的上一个值, 不允许值为 null |
| add | 添加一条数据 |
| addNotNull | 添加一条数据 ( value 不允许为 null ) |
| addAll | 添加集合数据 |
| addAllNotNull | 添加集合数据 ( values 内的值不允许为 null ) |
| clearAndAddAll | 移除全部数据并添加集合数据 |
| clearAndAddAllNotNull | 移除全部数据并添加集合数据 ( values 内的值不允许为 null ) |
| remove | 移除一条数据 |
| removeAll | 移除集合数据 |
| clear | 清空集合中符合指定 value 的全部数据 |
| clearNotBelong | 保留集合中符合指定 value 的全部数据 |
| clearAll | 清空集合全部数据 |
| clearNull | 清空集合中为 null 的值 |
| isEqualCollection | 判断两个集合是否相同 |
| isEqualCollections | 判断多个集合是否相同 |
| union | 两个集合并集处理 |
| unions | 多个集合并集处理 |
| intersection | 两个集合交集处理 |
| disjunction | 两个集合交集的补集处理 |
| subtract | 两个集合差集 ( 扣除 ) 处理 |
| equals | 判断两个值是否一样 |
| toArray | 转换数组 to Object |
| toArrayT | 转换数组 to T |
| reverse | 集合翻转处理 |
| getMinimumIndexI | 获取集合中最小值索引 |
| getMinimumIndexL | 获取集合中最小值索引 |
| getMinimumIndexF | 获取集合中最小值索引 |
| getMinimumIndexD | 获取集合中最小值索引 |
| getMaximumIndexI | 获取集合中最大值索引 |
| getMaximumIndexL | 获取集合中最大值索引 |
| getMaximumIndexF | 获取集合中最大值索引 |
| getMaximumIndexD | 获取集合中最大值索引 |
| getMinimumI | 获取集合中最小值 |
| getMinimumL | 获取集合中最小值 |
| getMinimumF | 获取集合中最小值 |
| getMinimumD | 获取集合中最小值 |
| getMaximumI | 获取集合中最大值 |
| getMaximumL | 获取集合中最大值 |
| getMaximumF | 获取集合中最大值 |
| getMaximumD | 获取集合中最大值 |
| sumlistI | 计算集合总和 |
| sumlistL | 计算集合总和 |
| sumlistF | 计算集合总和 |
| sumlistD | 计算集合总和 |


* **颜色工具类 ( 包括常用的色值 ) ->** [ColorUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/ColorUtils.java)

| 方法 | 注释 |
| :- | :- |
| hexAlpha | 获取十六进制透明度字符串 |
| getARGB | 返回一个颜色 ARGB 色值数组 ( 返回十进制 ) |
| alpha | 返回一个颜色中的透明度值 ( 返回十进制 ) |
| alphaPercent | 返回一个颜色中的透明度百分比值 |
| red | 返回一个颜色中红色的色值 ( 返回十进制 ) |
| redPercent | 返回一个颜色中红色的百分比值 |
| green | 返回一个颜色中绿色的色值 ( 返回十进制 ) |
| greenPercent | 返回一个颜色中绿色的百分比值 |
| blue | 返回一个颜色中蓝色的色值 ( 返回十进制 ) |
| bluePercent | 返回一个颜色中蓝色的百分比值 |
| rgb | 根据对应的 red、green、blue 生成一个颜色值 |
| argb | 根据对应的 alpha、red、green、blue 生成一个颜色值 ( 含透明度 ) |
| isRGB | 判断颜色 RGB 是否有效 |
| isARGB | 判断颜色 ARGB 是否有效 |
| setAlpha | 设置透明度 |
| setRed | 改变颜色值中的红色色值 |
| setGreen | 改变颜色值中的绿色色值 |
| setBlue | 改变颜色值中的蓝色色值 |
| parseColor | 解析颜色字符串, 返回对应的颜色值 |
| intToRgbString | 颜色值 转换 RGB 颜色字符串 |
| intToArgbString | 颜色值 转换 ARGB 颜色字符串 |
| getRandomColor | 获取随机颜色值 |
| getRandomColorString | 获取随机颜色值字符串 |
| judgeColorString | 判断是否为 ARGB 格式的十六进制颜色, 例如: FF990587 |
| setDark | 颜色加深 ( 单独修改 RGB 值, 不变动透明度 ) |
| setLight | 颜色变浅, 变亮 ( 单独修改 RGB 值, 不变动透明度 ) |
| setAlphaDark | 设置透明度加深 |
| setAlphaLight | 设置透明度变浅 |
| grayLevel | 获取灰度值 |
| setParser | 设置 Color 解析器 |
| sortGray | 灰度值排序 |
| sortHUE | HSB ( HSV ) HUE 色相排序 |
| sortSaturation | HSB ( HSV ) Saturation 饱和度排序 |
| sortBrightness | HSB ( HSV ) Brightness 亮度排序 |
| blendColor | 使用给定的比例在两种 ARGB 颜色之间进行混合 |
| transitionColor | 计算从 startColor 过渡到 endColor 过程中百分比为 ratio 时的颜色值 |
| getKey | 获取 Key |
| getValue | 获取 Value |
| getValueParser | 获取 Value 解析后的值 ( 如: #000 => #000000 ) |
| getValueColor | 获取 ARGB/RGB color |
| getAlpha | 返回颜色中的透明度值 ( 返回十进制 ) |
| getRed | 返回颜色中红色的色值 ( 返回十进制 ) |
| getGreen | 返回颜色中绿色的色值 ( 返回十进制 ) |
| getBlue | 返回颜色中蓝色的色值 ( 返回十进制 ) |
| getGrayLevel | 获取灰度值 |
| getHue | 获取颜色色调 |
| getSaturation | 获取颜色饱和度 |
| getBrightness | 获取颜色亮度 |
| handleColor | 处理 color |


* **转换工具类 ( Byte、Hex 等 ) ->** [ConvertUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/ConvertUtils.java)

| 方法 | 注释 |
| :- | :- |
| convert | Object 转换所需类型对象 |
| newString | Object 转 String |
| newStringNotArrayDecode | Object 转 String ( 不进行 Array 解码转 String ) |
| toString | Object 转 String |
| toInt | Object 转 Integer |
| toBoolean | Object 转 Boolean |
| toFloat | Object 转 Float |
| toDouble | Object 转 Double |
| toLong | Object 转 Long |
| toShort | Object 转 Short |
| toChar | Object 转 Character |
| toByte | Object 转 Byte |
| toBigDecimal | Object 转 BigDecimal |
| toBigInteger | Object 转 BigInteger |
| toChars | Object 获取 char[] |
| toBytes | Object 获取 byte[] |
| toCharInt | char 转换 unicode 编码 |
| charAt | Object 获取 char ( 默认第一位 ) |
| parseInt | 字符串转换对应的进制 |
| parseLong | 字符串转换对应的进制 |
| bytesToObject | byte[] 转为 Object |
| objectToBytes | Object 转为 byte[] |
| bytesToChars | byte[] 转换 char[], 并且进行补码 |
| charsToBytes | char[] 转换 byte[] |
| intsToStrings | int[] 转换 string[] |
| doublesToStrings | double[] 转换 string[] |
| longsToStrings | long[] 转换 string[] |
| floatsToStrings | float[] 转换 string[] |
| intsToDoubles | int[] 转换 double[] |
| intsToLongs | int[] 转换 long[] |
| intsToFloats | int[] 转换 float[] |
| stringsToInts | string[] 转换 int[] |
| stringsToDoubles | string[] 转换 double[] |
| stringsToLongs | string[] 转换 long[] |
| stringsToFloats | string[] 转换 float[] |
| doublesToInts | double[] 转换 int[] |
| longsToInts | long[] 转换 int[] |
| floatsToInts | float[] 转换 int[] |
| toBinaryString | 将 字节转换 为 二进制字符串 |
| decodeBinary | 二进制字符串 转换 byte[] 解码 |
| isHex | 判断是否十六进制数据 |
| decodeHex | 将十六进制字节数组解码 |
| hexToInt | 十六进制 char 转换 int |
| toHexString | int 转换十六进制 |
| toHexChars | 将 string 转换为 十六进制 char[] |
| bytesBitwiseAND | 按位求补 byte[] 位移编解码 ( 共用同一个方法 ) |


* **坐标 ( GPS 纠偏 ) 相关工具类 ->** [CoordinateUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/CoordinateUtils.java)

| 方法 | 注释 |
| :- | :- |
| bd09ToGcj02 | BD09 坐标转 GCJ02 坐标 |
| gcj02ToBd09 | GCJ02 坐标转 BD09 坐标 |
| gcj02ToWGS84 | GCJ02 坐标转 WGS84 坐标 |
| wgs84ToGcj02 | WGS84 坐标转 GCJ02 坐标 |
| bd09ToWGS84 | BD09 坐标转 WGS84 坐标 |
| wgs84ToBd09 | WGS84 坐标转 BD09 坐标 |
| outOfChina | 判断是否中国境外 |
| getDistance | 计算两个坐标相距距离 ( 单位: 米 ) |
| getAngle | 计算两个坐标的方向角度 |
| getDirection | 计算两个坐标的方向 |
| getValue | 获取中文方向值 |


* **日期工具类 ->** [DateUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/DateUtils.java)

| 方法 | 注释 |
| :- | :- |
| getDefaultFormat | 获取默认 SimpleDateFormat ( yyyy-MM-dd HH:mm:ss ) |
| getSafeDateFormat | 获取对应时间格式线程安全 SimpleDateFormat |
| getCalendar | 获取 Calendar |
| getCurrentTime | 获取当前时间 Date |
| getCurrentTimeMillis | 获取当前时间毫秒 |
| getDateTime | 获取 Date Time |
| getDateNow | 获取当前时间的字符串 |
| formatDate | 将 Date 转换日期字符串 |
| formatTime | 将时间毫秒转换日期字符串 |
| parseDate | 将时间毫秒转换成 Date |
| parseLong | 解析时间字符串转换为 long 毫秒 |
| parseStringDefault | 解析时间字符串转换为指定格式字符串 |
| parseString | 解析时间字符串转换为指定格式字符串 |
| getYear | 获取年份 |
| getMonth | 获取月份 ( 0 - 11 ) + 1 |
| getDay | 获取天数 |
| getWeek | 获取星期数 ( 1 - 7、日 - 六 ) |
| get24Hour | 获取小时 ( 24 ) |
| get12Hour | 获取小时 ( 12 ) |
| getMinute | 获取分钟 |
| getSecond | 获取秒数 |
| isAM | 是否上午 |
| isPM | 是否下午 |
| isYear | 是否对应年份 |
| isMonth | 是否对应月份 |
| isDay | 是否对应天数 |
| isWeek | 是否对应星期 |
| isHour | 是否对应小时 |
| isMinute | 是否对应分钟 |
| isSecond | 是否对应秒数 |
| getSecondMultiple | 获取秒数倍数 |
| getMinuteMultiple | 获取分钟倍数 |
| getHourMultiple | 获取小时倍数 |
| getDayMultiple | 获取天数倍数 |
| getWeekMultiple | 获取周数倍数 |
| getMillisMultiple | 获取对应单位倍数 |
| getTimeDiffByCurrent | 获取时间差 ( 传入时间 - 当前时间 ) |
| getTimeDiff | 获取时间差 |
| isLeapYear | 判断是否闰年 |
| getMonthDayNumberAll | 根据年份、月份, 获取对应的天数 ( 完整天数, 无判断是否属于未来日期 ) |
| getYearMonthNumber | 根据年份, 获取对应的月份 |
| getMonthDayNumber | 根据年份、月份, 获取对应的天数 |
| timeAddZero | 时间补 0 处理 ( 小于 10, 则自动补充 0x ) |
| getArrayToHH | 生成 HH 按时间排序数组 |
| getListToHH | 生成 HH 按时间排序集合 |
| getArrayToMM | 生成 MM 按时间排序数组 |
| getListToMM | 生成 MM 按时间排序集合 |
| getArrayToHHMM | 生成 HH:mm 按间隔时间排序数组 |
| getListToHHMM | 生成 HH:mm 按间隔时间排序集合 |
| getListToHHMMPosition | 获取 HH:mm 按间隔时间排序的集合中, 指定时间所在索引 |
| millisToFitTimeSpan | 转换时间 |
| millisToTimeArrays | 转换时间为数组 |
| timeConvertByMillis | 传入时间毫秒, 获取 00:00:00 格式 ( 不处理大于一天 ) |
| timeConvertBySecond | 传入时间秒, 获取 00:00:00 格式 ( 不处理大于一天 ) |
| isInTime | 判断时间是否在 [startTime, endTime] 区间 |
| isInTimeFormat | 判断时间是否在 [startTime, endTime] 区间 ( 自定义格式 ) |
| isInTimeHHmm | 判断时间是否在 [startTime, endTime] 区间 ( HHmm 格式 ) |
| isInTimeHHmmss | 判断时间是否在 [startTime, endTime] 区间 ( HHmmss 格式 ) |
| getEndTimeDiffHHmm | 获取指定时间距离该时间第二天的指定时段的时间 ( 判断凌晨情况 ) |
| getEndTimeDiff | 获取指定时间距离该时间第二天的指定时段的时间差 ( 判断凌晨情况 ) |
| getZodiac | 获取生肖 |
| getConstellation | 获取星座 |
| getConstellationDate | 获取星座日期 |


* **开发常用方法工具类 ->** [DevCommonUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/DevCommonUtils.java)

| 方法 | 注释 |
| :- | :- |
| timeRecord | 耗时时间记录 |
| getOperateTime | 获取操作时间 |
| sleepOperate | 堵塞操作 |
| isHttpRes | 判断是否网络资源 |
| whileMD5 | 循环 MD5 加密处理 |
| randomUUID | 获取随机唯一数 |
| randomUUIDToHashCode | 获取随机唯一数 HashCode |
| getRandomUUID | 获取随机规则生成 UUID |
| getRandomUUIDToString | 获取随机规则生成 UUID 字符串 |


* **编码工具类 ->** [EncodeUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/EncodeUtils.java)

| 方法 | 注释 |
| :- | :- |
| base64Encode | Base64 编码 |
| base64EncodeToString | Base64 编码 |
| base64Decode | Base64 解码 |
| base64DecodeToString | Base64 解码 |
| htmlEncode | Html 字符串编码 |


* **变量字段工具类 ->** [FieldUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/FieldUtils.java)

| 方法 | 注释 |
| :- | :- |
| getField | 获取变量对象 |
| getDeclaredField | 获取变量对象 |
| getFields | 获取变量对象数组 |
| getDeclaredFields | 获取变量对象数组 |
| set | 设置字段的值 |
| get | 获取字段的值 |
| isLong | 是否 long/Long 类型 |
| isFloat | 是否 float/Float 类型 |
| isDouble | 是否 double/Double 类型 |
| isInteger | 是否 int/Integer 类型 |
| isBoolean | 是否 boolean/Boolean 类型 |
| isCharacter | 是否 char/Character 类型 |
| isByte | 是否 byte/Byte 类型 |
| isShort | 是否 short/Short 类型 |
| isString | 是否 String 类型 |
| isSerializable | 判断是否序列化 |
| isInvalid | 是否静态常量或者内部结构属性 |
| isStatic | 是否静态变量 |
| isFinal | 是否常量 |
| isStaticFinal | 是否静态变量 |
| isSynthetic | 是否内部结构属性 |
| getGenericType | 获取字段的泛型类型, 如果不带泛型返回 null |
| getComponentType | 获取数组的类型 |
| getAllDeclaredFields | 获取全部 Field, 包括父类 |


* **文件 ( IO 流 ) 工具类 ->** [FileIOUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/FileIOUtils.java)

| 方法 | 注释 |
| :- | :- |
| setBufferSize | 设置缓冲区的大小, 默认大小等于 8192 字节 |
| getFileInputStream | 获取输入流 |
| getFileOutputStream | 获取输出流 |
| writeFileFromIS | 通过输入流写入文件 |
| writeFileFromBytesByStream | 通过字节流写入文件 |
| writeFileFromBytesByChannel | 通过 FileChannel 把字节流写入文件 |
| writeFileFromBytesByMap | 通过 MappedByteBuffer 把字节流写入文件 |
| writeFileFromString | 通过字符串写入文件 |
| readFileToList | 读取文件内容, 返回换行 List |
| readFileToString | 读取文件内容, 返回字符串 |
| readFileToBytesByStream | 读取文件内容, 返回 byte[] |
| readFileToBytesByChannel | 通过 FileChannel, 读取文件内容, 返回 byte[] |
| readFileToBytesByMap | 通过 MappedByteBuffer, 读取文件内容, 返回 byte[] |
| copyLarge | 复制 InputStream 到 OutputStream |


* **文件操作工具类 ->** [FileUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/FileUtils.java)

| 方法 | 注释 |
| :- | :- |
| getFile | 获取文件 |
| getFileByPath | 获取文件 |
| getFileCreateFolder | 获取路径, 并且进行创建目录 |
| getFilePathCreateFolder | 获取路径, 并且进行创建目录 |
| createFolder | 判断某个文件夹是否创建, 未创建则创建 ( 纯路径无文件名 ) |
| createFolderByPath | 创建文件夹目录 ( 可以传入文件名 ) |
| createFolderByPaths | 创建多个文件夹, 如果不存在则创建 |
| createOrExistsDir | 判断目录是否存在, 不存在则判断是否创建成功 |
| createOrExistsFile | 判断文件是否存在, 不存在则判断是否创建成功 |
| createFileByDeleteOldFile | 判断文件是否存在, 存在则在创建之前删除 |
| createTimestampFileName | 通过文件后缀创建时间戳文件名 |
| createTimestampFileNameByName | 通过文件名创建时间戳文件名 |
| createTimestampFileNameByFile | 通过文件创建时间戳文件名 |
| createTimestampFileNameByPath | 通过文件路径创建时间戳文件名 |
| convertFiles | Path List 转 File List |
| convertPaths | File List 转 Path List |
| getPath | 获取文件路径 |
| getAbsolutePath | 获取文件绝对路径 |
| getName | 获取文件名 |
| getFileSuffix | 获取文件后缀名 ( 无 "." 单独后缀 ) |
| getFileNotSuffix | 获取文件名 ( 无后缀 ) |
| getFileNotSuffixToPath | 获取文件名 ( 无后缀 ) |
| getFileNameNoExtension | 获取路径中的不带扩展名的文件名 |
| getFileExtension | 获取路径中的文件扩展名 |
| isFileExists | 检查是否存在某个文件 |
| isFile | 判断是否文件 |
| isDirectory | 判断是否文件夹 |
| isHidden | 判断是否隐藏文件 |
| isHidden2 | 判断是否隐藏文件 |
| isBuild | 是否 Build 文件、文件夹判断 |
| canRead | 文件是否可读 |
| canWrite | 文件是否可写 |
| canReadWrite | 文件是否可读写 |
| getFileLastModified | 获取文件最后修改的毫秒时间戳 |
| getFileCharsetSimple | 获取文件编码格式 |
| getFileLines | 获取文件行数 |
| getFileSize | 获取文件大小 |
| getDirSize | 获取目录大小 |
| getFileLength | 获取文件大小 |
| getDirLength | 获取目录全部文件大小 |
| getFileLengthNetwork | 获取文件大小 ( 网络资源 ) |
| getFileName | 获取路径中的文件名 |
| getDirName | 获取路径中的最长目录地址 |
| rename | 重命名文件 ( 同个目录下, 修改文件名 ) |
| formatFileSize | 传入文件路径, 返回对应的文件大小 |
| formatByteMemorySize | 字节数转合适内存大小 保留 3 位小数 |
| deleteFile | 删除文件 |
| deleteFiles | 删除多个文件 |
| deleteFolder | 删除文件夹 |
| saveFile | 保存文件 |
| appendFile | 追加文件 |
| readFileBytes | 读取文件 |
| readFile | 读取文件 |
| copyFile | 复制单个文件 |
| copyFolder | 复制文件夹 |
| moveFile | 移动 ( 剪切 ) 文件 |
| moveFolder | 移动 ( 剪切 ) 文件夹 |
| copyOrMoveDir | 复制或移动目录 |
| copyOrMoveFile | 复制或移动文件 |
| copyDir | 复制目录 |
| moveDir | 移动目录 |
| deleteDir | 删除目录 |
| deleteAllInDir | 删除目录下所有文件 |
| deleteFilesInDir | 删除目录下所有文件 |
| deleteFilesInDirWithFilter | 删除目录下所有过滤的文件 |
| listFilesInDir | 获取目录下所有文件 ( 不递归进子目录 ) |
| listFilesInDirWithFilter | 获取目录下所有过滤的文件 ( 不递归进子目录 ) |
| listFilesInDirBean | 获取目录下所有文件 ( 不递归进子目录 ) |
| listFilesInDirWithFilterBean | 获取目录下所有过滤的文件 ( 不递归进子目录 ) |
| listOrEmpty | 获取文件夹下的文件目录列表 ( 非全部子目录 ) |
| listFilesOrEmpty | 获取文件夹下的文件目录列表 ( 非全部子目录 ) |
| isImageFormats | 根据文件名判断文件是否为图片 |
| isAudioFormats | 根据文件名判断文件是否为音频 |
| isVideoFormats | 根据文件名判断文件是否为视频 |
| isFileFormats | 根据文件名判断文件是否为指定格式 |
| getFileMD5 | 获取文件 MD5 值 |
| getFileMD5ToHexString | 获取文件 MD5 值 |


* **格式化工具类 ->** [FormatUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/FormatUtils.java)

| 方法 | 注释 |
| :- | :- |
| format | 字符串格式化 |
| unitSpanOf | 获取 UnitSpanFormatter |
| argsOf | 获取 ArgsFormatter |


* **循环工具类 ->** [ForUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/ForUtils.java)

| 方法 | 注释 |
| :- | :- |
| forArgs | 循环可变数组 |
| forSimpleArgs | 循环可变数组 |
| forList | 循环集合 |
| forListIterator | 循环集合 |
| forSet | 循环集合 |
| forMap | 循环集合 |
| forInts | 循环可变数组 |
| forDoubles | 循环可变数组 |
| forFloats | 循环可变数组 |
| forLongs | 循环可变数组 |
| forBooleans | 循环可变数组 |
| forBytes | 循环可变数组 |
| forChars | 循环可变数组 |
| forShorts | 循环可变数组 |
| accept | 循环消费方法 |


* **Html 工具类 ->** [HtmlUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/HtmlUtils.java)

| 方法 | 注释 |
| :- | :- |
| addRemovePaddingMargin | 为给定的 Html 移除 padding、margin |
| addHtmlColor | 为给定的字符串添加 HTML 颜色标记 |
| addHtmlBold | 为给定的字符串添加 HTML 加粗标记 |
| addHtmlColorAndBold | 为给定的字符串添加 HTML 颜色标记并加粗 |
| addHtmlUnderline | 为给定的字符串添加 HTML 下划线 |
| addHtmlStrikeThruLine | 为给定的字符串添加 HTML 中划线 |
| addHtmlOverLine | 为给定的字符串添加 HTML 上划线 |
| addHtmlIncline | 为给定的字符串添加 HTML 字体倾斜 |
| addHtmlSPAN | 为给定的字符串添加 HTML SPAN 标签 |
| addHtmlP | 为给定的字符串添加 HTML P 标签 |
| addHtmlIMG | 为给定的字符串添加 HTML IMG 标签 |
| addHtmlIMGByWidth | 为给定的字符串添加 HTML IMG 标签 |
| addHtmlIMGByHeight | 为给定的字符串添加 HTML IMG 标签 |
| addHtmlDIV | 为给定的字符串添加 HTML DIV 标签 |
| addHtmlDIVByMargin | 为给定的字符串添加 HTML DIV 标签 |
| addHtmlDIVByPadding | 为给定的字符串添加 HTML DIV 标签 |
| addHtmlDIVByMarginPadding | 为给定的字符串添加 HTML DIV 标签 |
| keywordReplaceHtmlColor | 将给定的字符串中所有给定的关键字标色 |


* **Http 参数工具类 ->** [HttpParamsUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/HttpParamsUtils.java)

| 方法 | 注释 |
| :- | :- |
| getUrlParams | 获取 Url 携带参数 |
| getUrlParamsArray | 获取 Url、携带参数 数组 |
| existsParams | 判断是否存在参数 |
| existsParamsByURL | 通过 Url 判断是否存在参数 |
| joinUrlParams | 拼接 Url 及携带参数 |
| getUrlParamsJoinSymbol | 获取 Url 及携带参数 拼接符号 |
| splitParamsByUrl | 通过 Url 拆分参数 |
| splitParams | 拆分参数 |
| joinParams | 拼接请求参数 |
| joinParamsObj | 拼接请求参数 |
| convertObjToMS | 进行转换对象处理 ( 请求发送对象 ) |
| convertObjToMO | 进行转换对象处理 ( 请求发送对象 ) |
| urlEncode | 进行 URL 编码, 默认 UTF-8 |


* **HttpURLConnection 网络工具类 ->** [HttpURLConnectionUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/HttpURLConnectionUtils.java)

| 方法 | 注释 |
| :- | :- |
| doGetAsync | 异步的 Get 请求 |
| doPostAsync | 异步的 Post 请求 |
| request | 发送请求 |
| getNetTime | 获取网络时间 ( 默认使用百度链接 ) |


* **Map 工具类 ->** [MapUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/MapUtils.java)

| 方法 | 注释 |
| :- | :- |
| isEmpty | 判断 Map 是否为 null |
| isNotEmpty | 判断 Map 是否不为 null |
| length | 获取 Map 长度 |
| isLength | 获取长度 Map 是否等于期望长度 |
| greaterThan | 判断 Map 长度是否大于指定长度 |
| greaterThanOrEqual | 判断 Map 长度是否大于等于指定长度 |
| lessThan | 判断 Map 长度是否小于指定长度 |
| lessThanOrEqual | 判断 Map 长度是否小于等于指定长度 |
| getCount | 获取 Map 数组长度总和 |
| get | 获取 value |
| getKeyByValue | 通过 value 获取 key |
| getKeysByValue | 通过 value 获取 key 集合 ( 返回等于 value 的 key 集合 ) |
| getKeys | 通过 Map 获取 key 集合 |
| getKeysToArrays | 通过 Map 获取 key 数组 |
| getValues | 通过 Map 获取 value 集合 |
| getValuesToArrays | 通过 Map 获取 value 数组 |
| getFirst | 获取第一条数据 |
| getLast | 获取最后一条数据 |
| getNext | 根据指定 key 获取 key 所在位置的下一条数据 |
| getPrevious | 根据指定 key 获取 key 所在位置的上一条数据 |
| put | 添加一条数据 |
| putNotNull | 添加一条数据 ( 不允许 key 为 null ) |
| putAll | 添加多条数据 |
| putAllNotNull | 添加多条数据, 不允许 key 为 null |
| remove | 移除一条数据 |
| removeToKeys | 移除多条数据 |
| removeToValue | 移除等于 value 的所有数据 |
| removeToValues | 移除等于 value 的所有数据 ( Collection<Value> ) |
| equals | 判断两个值是否一样 |
| toggle | 切换保存状态 |
| isNullToValue | 判断指定 key 的 value 是否为 null |
| containsKey | 判断 Map 是否存储 key |
| containsValue | 判断 Map 是否存储 value |
| putToList | 添加一条数据 |
| removeToList | 移除一条数据 |
| removeToLists | 移除多条数据 |
| removeToMap | 移除多条数据 ( 通过 Map 进行移除 ) |
| mapToString | 键值对拼接 |


* **数字 ( 计算 ) 工具类 ->** [NumberUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/NumberUtils.java)

| 方法 | 注释 |
| :- | :- |
| addZero | 补 0 处理 ( 小于 10, 则自动补充 0x ) |
| subZeroAndDot | 去掉结尾多余的 . 与 0 |
| calculateUnitD | 计算指定单位倍数 |
| calculateUnitI | 计算指定单位倍数 |
| calculateUnitL | 计算指定单位倍数 |
| calculateUnitF | 计算指定单位倍数 |
| percentD | 计算百分比值 ( 最大 100% ) |
| percentI | 计算百分比值 ( 最大 100% ) |
| percentL | 计算百分比值 ( 最大 100% ) |
| percentF | 计算百分比值 ( 最大 100% ) |
| percentD2 | 计算百分比值 ( 可超出 100% ) |
| percentI2 | 计算百分比值 ( 可超出 100% ) |
| percentL2 | 计算百分比值 ( 可超出 100% ) |
| percentF2 | 计算百分比值 ( 可超出 100% ) |
| multipleD | 获取倍数 |
| multipleI | 获取倍数 |
| multipleL | 获取倍数 |
| multipleF | 获取倍数 |
| multiple | 获取整数倍数 ( 自动补 1 ) |
| clamp | 返回的 value 介于 max、min 之间, 若 value 小于 min, 返回 min, 若大于 max, 返回 max |
| numberToCHN | 数字转中文数值 |
| isNumber | 检验数字 |
| isNumberDecimal | 检验数字或包含小数点 |


* **对象相关工具类 ->** [ObjectUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/ObjectUtils.java)

| 方法 | 注释 |
| :- | :- |
| isEmpty | 判断对象是否为空 |
| isNotEmpty | 判断对象是否非空 |
| equals | 判断两个值是否一样 |
| getOrDefault | 获取非空或默认对象 |
| hashCode | 获取对象哈希值 |
| getObjectTag | 获取一个对象的独一无二的标记 |
| convert | Object 转换所需类型对象 |
| requireNonNull | 检查对象是否为 null, 为 null 则抛出异常, 不为 null 则返回该对象 |
| requireNonNullArgs | 检查对象是否为 null, 为 null 则抛出异常 |
| requireNonNullBool | 检查对象是否非 null |
| requireNonNullBoolArgs | 检查对象是否非 null |


* **随机工具类 ->** [RandomUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/RandomUtils.java)

| 方法 | 注释 |
| :- | :- |
| nextBoolean | 获取伪随机 boolean 值 |
| nextBytes | 获取伪随机 byte[] |
| nextDouble | 获取伪随机 double 值 |
| nextGaussian | 获取伪随机高斯分布值 |
| nextFloat | 获取伪随机 float 值 |
| nextInt | 获取伪随机 int 值 |
| nextLong | 获取伪随机 long 值 |
| getRandomNumbers | 获取数字自定义长度的随机数 |
| getRandomLowerCaseLetters | 获取小写字母自定义长度的随机数 |
| getRandomCapitalLetters | 获取大写字母自定义长度的随机数 |
| getRandomLetters | 获取大小写字母自定义长度的随机数 |
| getRandomNumbersAndLetters | 获取数字、大小写字母自定义长度的随机数 |
| getRandom | 获取自定义数据自定义长度的随机数 |
| shuffle | 洗牌算法 ( 第一种 ) 随机置换指定的数组使用的默认源的随机性 ( 随机数据源小于三个, 则无效 ) |
| shuffle2 | 洗牌算法 ( 第二种 ) 随机置换指定的数组使用的默认源的随机性 |
| nextIntRange | 获取指定范围 int 值 |
| nextLongRange | 获取指定范围 long 值 |
| nextDoubleRange | 获取指定范围 double 值 |
| ints | 获取随机 int[] |
| longs | 获取随机 long[] |
| doubles | 获取随机 double[] |


* **反射相关工具类 ->** [Reflect2Utils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/Reflect2Utils.java)

| 方法 | 注释 |
| :- | :- |
| setProperty | 设置某个对象变量值 ( 可设置静态变量 ) |
| getProperty | 获取某个对象的变量 ( 可获取静态变量 ) |
| getStaticProperty | 获取某个类的静态变量 ( 只能获取静态变量 ) |
| invokeMethod | 执行某个对象方法 ( 可执行静态方法 ) |
| invokeStaticMethod | 执行某个类的静态方法 ( 只能执行静态方法 ) |
| newInstance | 新建实例 ( 构造函数创建 ) |
| isInstance | 是不是某个类的实例 |
| getArgsClass | 获取参数类型 |
| getPropertyByParent | 获取父类中的变量对象 |
| getDeclaredFieldParent | 获取父类中的变量对象 ( 循环向上转型, 获取对象的 DeclaredField ) |


* **反射相关工具类 ->** [ReflectUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/ReflectUtils.java)

| 方法 | 注释 |
| :- | :- |
| reflect | 设置要反射的类 |
| newInstance | 实例化反射对象 |
| field | 设置反射的字段 |
| setEnumVal | 设置枚举值 |
| method | 设置反射的方法 |
| proxy | 根据类, 代理创建并返回对象 |
| type | 获取类型 |
| get | 获取反射想要获取的 |
| hashCode | 获取 HashCode |
| equals | 判断反射的两个对象是否一样 |
| toString | 获取反射获取的对象 |


* **计算比例工具类 ->** [ScaleUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/ScaleUtils.java)

| 方法 | 注释 |
| :- | :- |
| calcScale | 计算比例 ( 商 ) |
| calcScaleToMath | 计算比例 ( 被除数 ( 最大值 ) / 除数 ( 最小值 ) ) |
| calcScaleToWidth | 计算缩放比例 ( 根据宽度比例转换高度 ) |
| calcScaleToHeight | 计算缩放比例 ( 根据高度比例转换宽度 ) |
| calcWidthHeightToScale | 通过宽度、高度根据对应的比例, 转换成对应的比例宽度高度 ( 智能转换 ) |
| calcWidthToScale | 以宽度为基准, 转换对应比例的高度 |
| calcHeightToScale | 以高度为基准, 转换对应比例的宽度 |
| calcScaleToWidthI | 计算缩放比例 ( 根据宽度比例转换高度 ) |
| calcScaleToHeightI | 计算缩放比例 ( 根据高度比例转换宽度 ) |
| calcWidthHeightToScaleI | 通过宽度、高度根据对应的比例, 转换成对应的比例宽度高度 ( 智能转换 ) |
| calcWidthToScaleI | 以宽度为基准, 转换对应比例的高度 |
| calcHeightToScaleI | 以高度为基准, 转换对应比例的宽度 |
| calcScaleToWidthL | 计算缩放比例 ( 根据宽度比例转换高度 ) |
| calcScaleToHeightL | 计算缩放比例 ( 根据高度比例转换宽度 ) |
| calcWidthHeightToScaleL | 通过宽度、高度根据对应的比例, 转换成对应的比例宽度高度 ( 智能转换 ) |
| calcWidthToScaleL | 以宽度为基准, 转换对应比例的高度 |
| calcHeightToScaleL | 以高度为基准, 转换对应比例的宽度 |
| calcScaleToWidthF | 计算缩放比例 ( 根据宽度比例转换高度 ) |
| calcScaleToHeightF | 计算缩放比例 ( 根据高度比例转换宽度 ) |
| calcWidthHeightToScaleF | 通过宽度、高度根据对应的比例, 转换成对应的比例宽度高度 ( 智能转换 ) |
| calcWidthToScaleF | 以宽度为基准, 转换对应比例的高度 |
| calcHeightToScaleF | 以高度为基准, 转换对应比例的宽度 |
| calcXY | 计算 XY 比 |


* **流操作工具类 ->** [StreamUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/StreamUtils.java)

| 方法 | 注释 |
| :- | :- |
| inputToOutputStream | 输入流转输出流 |
| outputToInputStream | 输出流转输入流 |
| inputStreamToBytes | 输入流转 byte[] |
| bytesToInputStream | byte[] 转输出流 |
| outputStreamToBytes | 输出流转 byte[] |
| bytesToOutputStream | byte[] 转 输出流 |
| inputStreamToString | 输入流转 String |
| stringToInputStream | String 转换输入流 |
| outputStreamToString | 输出流转 String |
| stringToOutputStream | String 转 输出流 |


* **字符串工具类 ->** [StringUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/StringUtils.java)

| 方法 | 注释 |
| :- | :- |
| isEmpty | 判断字符串是否为 null |
| isEmptyClear | 判断字符串是否为 null ( 调用 clearSpaceTabLineTrim ) |
| isNotEmpty | 判断字符串是否不为 null |
| isNotEmptyClear | 判断字符串是否不为 null ( 调用 clearSpaceTabLineTrim ) |
| isNull | 判断字符串是否为 "null" |
| isNullClear | 判断字符串是否为 "null" ( 调用 clearSpaceTabLineTrim ) |
| isNotNull | 判断字符串是否不为 "null" |
| isNotNullClear | 判断字符串是否不为 "null" ( 调用 clearSpaceTabLineTrim ) |
| length | 获取字符串长度 |
| isLength | 获取字符串长度 是否等于期望长度 |
| equals | 判断两个值是否一样 |
| equalsNotNull | 判断两个值是否一样 ( 非 null 判断 ) |
| equalsIgnoreCase | 判断两个值是否一样 ( 忽略大小写 ) |
| equalsIgnoreCaseNotNull | 判断两个值是否一样 ( 忽略大小写 ) |
| isEquals | 判断多个字符串是否相等, 只有全相等才返回 true ( 对比大小写 ) |
| isOrEquals | 判断多个字符串, 只要有一个符合条件则通过 |
| isContains | 判断一堆值中, 是否存在符合该条件的 ( 包含 ) |
| isStartsWith | 判断内容, 是否属于特定字符串开头 ( 对比大小写 ) |
| isEndsWith | 判断内容, 是否属于特定字符串结尾 ( 对比大小写 ) |
| countMatches | 统计字符串匹配个数 |
| countMatches2 | 统计字符串匹配个数 |
| isSpace | 判断字符串是否为 null 或全为空白字符 |
| getBytes | 字符串 转 byte[] |
| clearSpace | 清空字符串全部空格 |
| clearTab | 清空字符串全部 Tab |
| clearLine | 清空字符串全部换行符 |
| clearLine2 | 清空字符串全部换行符 |
| clearSpaceTrim | 清空字符串前后全部空格 |
| clearTabTrim | 清空字符串前后全部 Tab |
| clearLineTrim | 清空字符串前后全部换行符 |
| clearLineTrim2 | 清空字符串前后全部换行符 |
| clearSpaceTabLine | 清空字符串全部空格、Tab、换行符 |
| clearSpaceTabLineTrim | 清空字符串前后全部空格、Tab、换行符 |
| appendSpace | 追加空格 |
| appendTab | 追加 Tab |
| appendLine | 追加换行 |
| appendLine2 | 追加换行 |
| forString | 循环指定数量字符串 |
| joinArgs | 循环拼接 |
| join | 循环拼接 |
| colonSplit | 冒号分割处理 |
| getString | 获取字符串 ( 判 null ) |
| checkValue | 检查字符串 |
| checkValues | 检查字符串 ( 多个值 ) |
| checkValuesSpace | 检查字符串 ( 多个值, 删除前后空格对比判断 ) |
| format | 字符串格式化 |
| argsFormat | 根据可变参数数量自动格式化 |
| concat | 字符串连接, 将参数列表拼接为一个字符串 |
| concatSpiltWith | 字符串连接, 将参数列表拼接为一个字符串 |
| concatSpiltWithIgnoreLast | 字符串连接, 将参数列表拼接为一个字符串 ( 最后一个不追加间隔 ) |
| appends | StringBuilder 拼接处理 |
| appendsIgnoreLast | StringBuilder 拼接处理 ( 最后一个不追加间隔 ) |
| gbkEncode | 字符串进行 GBK 编码 |
| gbk2312Encode | 字符串进行 GBK2312 编码 |
| utf8Encode | 字符串进行 UTF-8 编码 |
| strEncode | 进行字符串编码 |
| urlEncode | 进行 URL 编码, 默认 UTF-8 |
| urlDecode | 进行 URL 解码, 默认 UTF-8 |
| urlDecodeWhile | 进行 URL 解码, 默认 UTF-8 ( 循环到非 URL 编码为止 ) |
| ascii | 将字符串转移为 ASCII 码 |
| unicode | 将字符串转移为 Unicode 码 |
| unicodeString | 将字符数组转移为 Unicode 码 |
| dbc | 转化为半角字符 |
| sbc | 转化为全角字符 如: a = ａ, A = Ａ |
| checkChineseToString | 检测字符串是否全是中文 |
| isChinese | 判断输入汉字 |
| upperFirstLetter | 首字母大写 |
| lowerFirstLetter | 首字母小写 |
| reverse | 反转字符串 |
| underScoreCaseToCamelCase | 下划线命名转为驼峰命名 |
| camelCaseToUnderScoreCase | 驼峰命名法转为下划线命名 |
| sqliteEscape | 字符串数据库字符转义 |
| convertHideMobile | 转换手机号 |
| convertSymbolHide | 转换符号处理 |
| subEllipsize | 裁剪超出的内容, 并且追加符号 ( 如 ... ) |
| subSymbolHide | 裁剪符号处理 |
| subSetSymbol | 裁剪内容 ( 设置符号处理 ) |
| substring | 裁剪字符串 |
| replaceSEWith | 替换特定字符串开头、结尾的字符串 |
| replaceStartsWith | 替换开头字符串 |
| replaceEndsWith | 替换结尾字符串 |
| clearSEWiths | 清空特定字符串开头、结尾的字符串 |
| clearStartsWith | 清空特定字符串开头的字符串 |
| clearEndsWith | 清空特定字符串结尾的字符串 |
| replaceAll | 替换字符串 |
| replaceAllToNull | 替换字符串 |
| replaceAlls | 替换字符串 |
| split | 拆分字符串 |


* **异常处理工具类 ->** [ThrowableUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/ThrowableUtils.java)

| 方法 | 注释 |
| :- | :- |
| getThrowable | 获取异常信息 |
| getThrowableStackTrace | 获取异常栈信息 |


* **类型工具类 ->** [TypeUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/TypeUtils.java)

| 方法 | 注释 |
| :- | :- |
| getArrayType | 获取 Array Type |
| getListType | 获取 List Type |
| getSetType | 获取 Set Type |
| getMapType | 获取 Map Type |
| getType | 获取 Type |


* **压缩相关工具类 ->** [ZipUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/ZipUtils.java)

| 方法 | 注释 |
| :- | :- |
| zipFiles | 批量压缩文件 |
| zipFile | 压缩文件 |
| unzipFile | 解压文件 |
| unzipFileByKeyword | 解压带有关键字的文件 |
| getFilesPath | 获取压缩文件中的文件路径链表 |
| getComments | 获取压缩文件中的注释链表 |


## <span id="devutilscommonable">**`dev.utils.common.able`**</span>


## <span id="devutilscommonassist">**`dev.utils.common.assist`**</span>


* **均值计算 ( 用以统计平均数 ) 辅助类 ->** [Averager.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/assist/Averager.java)

| 方法 | 注释 |
| :- | :- |
| add | 添加一个数字 |
| clear | 清除全部 |
| size | 获取参与均值计算的数字个数 |
| getAverage | 获取平均数 |
| print | 输出参与均值计算的数字 |


* **标记值计算存储 ( 位运算符 ) ->** [FlagsValue.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/assist/FlagsValue.java)

| 方法 | 注释 |
| :- | :- |
| getFlags | 获取 flags value |
| setFlags | 设置 flags value |
| addFlags | 添加 flags value |
| clearFlags | 移除 flags value |
| hasFlags | 是否存在 flags value |
| notHasFlags | 是否不存在 flags value |


* **键值对 Assist ->** [KeyValueAssist.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/assist/KeyValueAssist.java)

| 方法 | 注释 |
| :- | :- |
| getValue | 获取 Value |
| setValue | 设置 Value |
| removeValue | 移除 Value |
| getKeyValueMaps | 获取 Key Value Map |
| containsKey | 是否存在 Key |
| containsValue | 是否存在 Value |
| isEmpty | 判断 Value 是否为 null |


* **时间均值计算辅助类 ->** [TimeAverager.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/assist/TimeAverager.java)

| 方法 | 注释 |
| :- | :- |
| start | 开始计时 ( 毫秒 ) |
| end | 结束计时 ( 毫秒 ) |
| endAndRestart | 结束计时, 并重新启动新的计时 |
| average | 求全部计时均值 |
| print | 输出全部时间值 |
| clear | 清除计时数据 |


* **时间计时辅助类 ->** [TimeCounter.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/assist/TimeCounter.java)

| 方法 | 注释 |
| :- | :- |
| start | 开始计时 ( 毫秒 ) |
| durationRestart | 获取持续的时间并重新启动 ( 毫秒 ) |
| duration | 获取持续的时间 ( 毫秒 ) |
| getStartTime | 获取开始时间 ( 毫秒 ) |


* **堵塞时间辅助类 ->** [TimeKeeper.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/assist/TimeKeeper.java)

| 方法 | 注释 |
| :- | :- |
| waitForEndAsync | 设置等待一段时间后, 通知方法 ( 异步 ) |
| waitForEnd | 设置等待一段时间后, 通知方法 ( 同步 ) |


* **弱引用辅助类 ->** [WeakReferenceAssist.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/assist/WeakReferenceAssist.java)

| 方法 | 注释 |
| :- | :- |
| getSingleWeak | 获取单个弱引用对象 |
| getSingleWeakValue | 获取单个弱引用对象值 |
| setSingleWeakValue | 保存单个弱引用对象值 |
| removeSingleWeak | 移除单个弱引用持有对象 |
| getWeak | 获取弱引用对象 |
| getWeakValue | 获取弱引用对象值 |
| setWeakValue | 保存弱引用对象值 |
| removeWeak | 移除指定弱引用持有对象 |
| clear | 清空全部弱引用持有对象 |


## <span id="devutilscommonassistrecord">**`dev.utils.common.assist.record`**</span>


* **文件记录分析工具类 ->** [FileRecordUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/assist/record/FileRecordUtils.java)

| 方法 | 注释 |
| :- | :- |
| isSuccessful | 校验记录方法返回字符串是否成功 |
| isHandler | 是否处理记录 |
| setHandler | 设置是否处理记录 |
| getRecordInsert | 获取日志记录插入信息 |
| setRecordInsert | 设置日志记录插入信息 |
| setCallback | 设置文件记录回调 |
| getLogContent | 获取日志内容 |
| record | 记录方法 |


* **日志记录配置信息 ->** [RecordConfig.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/assist/record/RecordConfig.java)

| 方法 | 注释 |
| :- | :- |
| get | 获取配置信息 |
| getStoragePath | 获取存储路径 |
| getFileName | 获取文件名 ( 固定 ) |
| getFolderName | 获取文件夹名 ( 模块名 ) |
| getFileIntervalTime | 获取文件记录间隔时间 |
| isHandler | 是否处理记录 |
| setHandler | 设置是否处理记录 |
| isInsertHeaderData | 是否插入头数据 |
| setInsertHeaderData | 设置是否插入头数据 |
| getRecordInsert | 获取日志记录插入信息 |
| setRecordInsert | 设置日志记录插入信息 |
| getFinalPath | 获取文件地址 |


* **日志记录插入信息 ->** [RecordInsert.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/assist/record/RecordInsert.java)

| 方法 | 注释 |
| :- | :- |
| getFileInfo | getFileInfo |
| setFileInfo | setFileInfo |
| getLogHeader | getLogHeader |
| setLogHeader | setLogHeader |
| getLogTail | getLogTail |
| setLogTail | setLogTail |


## <span id="devutilscommonassistsearch">**`dev.utils.common.assist.search`**</span>


* **文件广度优先搜索算法 ( 多线程 + 队列, 搜索某个目录下的全部文件 ) ->** [FileBreadthFirstSearchUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/assist/search/FileBreadthFirstSearchUtils.java)

| 方法 | 注释 |
| :- | :- |
| setSearchHandler | 设置搜索处理接口 |
| getQueueSameTimeNumber | 获取任务队列同时进行数量 |
| setQueueSameTimeNumber | 任务队列同时进行数量 |
| isRunning | 是否搜索中 |
| stop | 停止搜索 |
| isStop | 是否停止搜索 |
| getStartTime | 获取开始搜索时间 ( 毫秒 ) |
| getEndTime | 获取结束搜索时间 ( 毫秒 ) |
| getDelayTime | 获取延迟校验时间 ( 毫秒 ) |
| setDelayTime | 设置延迟校验时间 ( 毫秒 ) |
| query | 搜索目录 |


* **文件深度优先搜索算法 ( 递归搜索某个目录下的全部文件 ) ->** [FileDepthFirstSearchUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/assist/search/FileDepthFirstSearchUtils.java)

| 方法 | 注释 |
| :- | :- |
| setSearchHandler | 设置搜索处理接口 |
| isRunning | 是否搜索中 |
| stop | 停止搜索 |
| isStop | 是否停止搜索 |
| getStartTime | 获取开始搜索时间 ( 毫秒 ) |
| getEndTime | 获取结束搜索时间 ( 毫秒 ) |
| query | 搜索目录 |


## <span id="devutilscommonassisturl">**`dev.utils.common.assist.url`**</span>


* **Dev 库 Java 通用 Url 解析器 ->** [DevJavaUrlParser.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/assist/url/DevJavaUrlParser.java)

| 方法 | 注释 |
| :- | :- |
| reset | reset |
| setUrl | setUrl |
| getUrl | getUrl |
| getUrlByPrefix | getUrlByPrefix |
| getUrlByParams | getUrlByParams |
| getUrlParams | getUrlParams |
| getUrlParamsDecode | getUrlParamsDecode |
| isConvertMap | isConvertMap |
| setConvertMap | setConvertMap |


* **Url 携带信息解析 ->** [UrlExtras.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/assist/url/UrlExtras.java)

| 方法 | 注释 |
| :- | :- |
| getUrl | 获取完整 Url |
| getUrlByPrefix | 获取 Url 前缀 ( 去除参数部分 ) |
| getUrlByParams | 获取 Url 参数部分字符串 |
| getUrlParams | 获取 Url Params Map |
| getUrlParamsDecode | 获取 Url Params Map ( 参数值进行 UrlDecode ) |
| getParser | 获取 Url 解析器 |
| setParser | 设置 Url 解析器 |
| reset | 重置并返回一个新的解析器 |
| setUrl | 设置完整 Url |
| isConvertMap | 是否解析、转换 Param Map |
| setConvertMap | 设置是否解析、转换 Param Map |


## <span id="devutilscommoncipher">**`dev.utils.common.cipher`**</span>


* **Base64 工具类 ->** [Base64.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/cipher/Base64.java)

| 方法 | 注释 |
| :- | :- |
| decode | Decode the Base64-encoded data in input and return the data in |
| encodeToString | Base64-encode the given data and return a newly allocated |
| encode | Base64-encode the given data and return a newly allocated |


* **Base64 编解码 ( 并进行 ) 加解密 ->** [Base64Cipher.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/cipher/Base64Cipher.java)

| 方法 | 注释 |
| :- | :- |
| decrypt | 解码 |
| encrypt | 编码 |


* **加密工具类 ->** [CipherUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/cipher/CipherUtils.java)

| 方法 | 注释 |
| :- | :- |
| encrypt | 加密方法 |
| decrypt | 解密方法 |


## <span id="devutilscommoncomparator">**`dev.utils.common.comparator`**</span>


* **排序比较器工具类 ->** [ComparatorUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/comparator/ComparatorUtils.java)

| 方法 | 注释 |
| :- | :- |
| reverse | List 反转处理 |
| sort | List 排序处理 |
| sortAsc | List 升序处理 |
| sortDesc | List 降序处理 |
| sortFileLastModifiedAsc | 文件修改时间升序排序 |
| sortFileLastModifiedDesc | 文件修改时间降序排序 |
| sortFileLengthAsc | 文件大小升序排序 |
| sortFileLengthDesc | 文件大小降序排序 |
| sortFileNameAsc | 文件名升序排序 |
| sortFileNameDesc | 文件名降序排序 |
| sortFileAsc | 文件升序排序 |
| sortFileDesc | 文件降序排序 |
| sortDateAsc | Date 升序排序 |
| sortDateDesc | Date 降序排序 |
| sortDoubleAsc | Double 升序排序 |
| sortDoubleDesc | Double 降序排序 |
| sortFloatAsc | Float 升序排序 |
| sortFloatDesc | Float 降序排序 |
| sortIntAsc | Int 升序排序 |
| sortIntDesc | Int 降序排序 |
| sortLongAsc | Long 升序排序 |
| sortLongDesc | Long 降序排序 |
| sortStringAsc | String 升序排序 |
| sortStringDesc | String 降序排序 |
| sortStringWindowsSimpleAsc | String Windows 排序比较器简单实现升序排序 |
| sortStringWindowsSimpleDesc | String Windows 排序比较器简单实现降序排序 |
| sortStringWindowsSimple2Asc | String Windows 排序比较器简单实现升序排序 ( 实现方式二 ) |
| sortStringWindowsSimple2Desc | String Windows 排序比较器简单实现降序排序 ( 实现方式二 ) |
| sortWindowsExplorerFileSimpleComparatorAsc | Windows 目录资源文件升序排序 |
| sortWindowsExplorerFileSimpleComparatorDesc | Windows 目录资源文件降序排序 |
| sortWindowsExplorerFileSimpleComparator2Asc | Windows 目录资源文件升序排序 ( 实现方式二 ) |
| sortWindowsExplorerFileSimpleComparator2Desc | Windows 目录资源文件降序排序 ( 实现方式二 ) |
| sortWindowsExplorerStringSimpleComparatorAsc | Windows 目录资源文件名升序排序 |
| sortWindowsExplorerStringSimpleComparatorDesc | Windows 目录资源文件名降序排序 |
| sortWindowsExplorerStringSimpleComparator2Asc | Windows 目录资源文件名升序排序 ( 实现方式二 ) |
| sortWindowsExplorerStringSimpleComparator2Desc | Windows 目录资源文件名降序排序 ( 实现方式二 ) |


## <span id="devutilscommoncomparatorsort">**`dev.utils.common.comparator.sort`**</span>


* **Date 排序值 ->** [DateSort.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/comparator/sort/DateSort.java)

| 方法 | 注释 |
| :- | :- |
| getDateSortValue | getDateSortValue |


* **Date 升序排序 ->** [DateSortAsc.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/comparator/sort/DateSortAsc.java)

| 方法 | 注释 |
| :- | :- |
| compare | compare |


* **Date 降序排序 ->** [DateSortDesc.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/comparator/sort/DateSortDesc.java)

| 方法 | 注释 |
| :- | :- |
| compare | compare |


* **Double 排序值 ->** [DoubleSort.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/comparator/sort/DoubleSort.java)

| 方法 | 注释 |
| :- | :- |
| getDoubleSortValue | getDoubleSortValue |


* **Double 升序排序 ->** [DoubleSortAsc.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/comparator/sort/DoubleSortAsc.java)

| 方法 | 注释 |
| :- | :- |
| compare | compare |


* **Double 降序排序 ->** [DoubleSortDesc.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/comparator/sort/DoubleSortDesc.java)

| 方法 | 注释 |
| :- | :- |
| compare | compare |


* **文件修改时间升序排序 ->** [FileLastModifiedSortAsc.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/comparator/sort/FileLastModifiedSortAsc.java)

| 方法 | 注释 |
| :- | :- |
| compare | compare |


* **文件修改时间降序排序 ->** [FileLastModifiedSortDesc.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/comparator/sort/FileLastModifiedSortDesc.java)

| 方法 | 注释 |
| :- | :- |
| compare | compare |


* **文件大小升序排序 ->** [FileLengthSortAsc.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/comparator/sort/FileLengthSortAsc.java)

| 方法 | 注释 |
| :- | :- |
| compare | compare |


* **文件大小降序排序 ->** [FileLengthSortDesc.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/comparator/sort/FileLengthSortDesc.java)

| 方法 | 注释 |
| :- | :- |
| compare | compare |


* **文件名升序排序 ->** [FileNameSortAsc.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/comparator/sort/FileNameSortAsc.java)

| 方法 | 注释 |
| :- | :- |
| compare | compare |


* **文件名降序排序 ->** [FileNameSortDesc.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/comparator/sort/FileNameSortDesc.java)

| 方法 | 注释 |
| :- | :- |
| compare | compare |


* **文件升序排序 ->** [FileSortAsc.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/comparator/sort/FileSortAsc.java)

| 方法 | 注释 |
| :- | :- |
| compare | compare |


* **文件降序排序 ->** [FileSortDesc.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/comparator/sort/FileSortDesc.java)

| 方法 | 注释 |
| :- | :- |
| compare | compare |


* **Float 排序值 ->** [FloatSort.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/comparator/sort/FloatSort.java)

| 方法 | 注释 |
| :- | :- |
| getFloatSortValue | getFloatSortValue |


* **Float 升序排序 ->** [FloatSortAsc.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/comparator/sort/FloatSortAsc.java)

| 方法 | 注释 |
| :- | :- |
| compare | compare |


* **Float 降序排序 ->** [FloatSortDesc.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/comparator/sort/FloatSortDesc.java)

| 方法 | 注释 |
| :- | :- |
| compare | compare |


* **Int 排序值 ->** [IntSort.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/comparator/sort/IntSort.java)

| 方法 | 注释 |
| :- | :- |
| getIntSortValue | getIntSortValue |


* **Int 升序排序 ->** [IntSortAsc.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/comparator/sort/IntSortAsc.java)

| 方法 | 注释 |
| :- | :- |
| compare | compare |


* **Int 降序排序 ->** [IntSortDesc.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/comparator/sort/IntSortDesc.java)

| 方法 | 注释 |
| :- | :- |
| compare | compare |


* **Long 排序值 ->** [LongSort.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/comparator/sort/LongSort.java)

| 方法 | 注释 |
| :- | :- |
| getLongSortValue | getLongSortValue |


* **Long 升序排序 ->** [LongSortAsc.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/comparator/sort/LongSortAsc.java)

| 方法 | 注释 |
| :- | :- |
| compare | compare |


* **Long 降序排序 ->** [LongSortDesc.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/comparator/sort/LongSortDesc.java)

| 方法 | 注释 |
| :- | :- |
| compare | compare |


* **String 排序值 ->** [StringSort.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/comparator/sort/StringSort.java)

| 方法 | 注释 |
| :- | :- |
| getStringSortValue | getStringSortValue |


* **String 升序排序 ->** [StringSortAsc.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/comparator/sort/StringSortAsc.java)

| 方法 | 注释 |
| :- | :- |
| compare | compare |


* **String 降序排序 ->** [StringSortDesc.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/comparator/sort/StringSortDesc.java)

| 方法 | 注释 |
| :- | :- |
| compare | compare |


* **String Windows 排序比较器简单实现 ->** [StringSortWindowsSimple.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/comparator/sort/StringSortWindowsSimple.java)

| 方法 | 注释 |
| :- | :- |
| compare | compare |


* **String Windows 排序比较器简单实现 ->** [StringSortWindowsSimple2.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/comparator/sort/StringSortWindowsSimple2.java)

| 方法 | 注释 |
| :- | :- |
| compare | compare |


* **Windows 目录资源文件排序比较器 ->** [WindowsExplorerFileSimpleComparator.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/comparator/sort/WindowsExplorerFileSimpleComparator.java)

| 方法 | 注释 |
| :- | :- |
| compare | compare |


* **Windows 目录资源文件排序比较器 ->** [WindowsExplorerFileSimpleComparator2.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/comparator/sort/WindowsExplorerFileSimpleComparator2.java)

| 方法 | 注释 |
| :- | :- |
| compare | compare |


* **Windows 目录资源文件名排序比较器 ->** [WindowsExplorerStringSimpleComparator.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/comparator/sort/WindowsExplorerStringSimpleComparator.java)

| 方法 | 注释 |
| :- | :- |
| compare | compare |


* **Windows 目录资源文件名排序比较器 ->** [WindowsExplorerStringSimpleComparator2.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/comparator/sort/WindowsExplorerStringSimpleComparator2.java)

| 方法 | 注释 |
| :- | :- |
| compare | compare |


## <span id="devutilscommonencrypt">**`dev.utils.common.encrypt`**</span>


* **AES 对称加密工具类 ->** [AESUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/encrypt/AESUtils.java)

| 方法 | 注释 |
| :- | :- |
| initKey | 生成密钥 |
| encrypt | AES 加密 |
| decrypt | AES 解密 |


* **CRC 工具类 ->** [CRCUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/encrypt/CRCUtils.java)

| 方法 | 注释 |
| :- | :- |
| getCRC32 | 获取 CRC32 值 |
| getCRC32ToHexString | 获取 CRC32 值 |
| getFileCRC32 | 获取文件 CRC32 值 |


* **DES 对称加密工具类 ->** [DESUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/encrypt/DESUtils.java)

| 方法 | 注释 |
| :- | :- |
| getDESKey | 获取可逆算法 DES 的密钥 |
| encrypt | DES 加密 |
| decrypt | DES 解密 |


* **加解密通用工具类 ->** [EncryptUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/encrypt/EncryptUtils.java)

| 方法 | 注释 |
| :- | :- |
| encryptMD2 | MD2 加密 |
| encryptMD2ToHexString | MD2 加密 |
| encryptMD5 | MD5 加密 |
| encryptMD5ToHexString | MD5 加密 |
| encryptMD5File | 获取文件 MD5 值 |
| encryptMD5FileToHexString | 获取文件 MD5 值 |
| encryptSHA1 | SHA1 加密 |
| encryptSHA1ToHexString | SHA1 加密 |
| encryptSHA224 | SHA224 加密 |
| encryptSHA224ToHexString | SHA224 加密 |
| encryptSHA256 | SHA256 加密 |
| encryptSHA256ToHexString | SHA256 加密 |
| encryptSHA384 | SHA384 加密 |
| encryptSHA384ToHexString | SHA384 加密 |
| encryptSHA512 | SHA512 加密 |
| encryptSHA512ToHexString | SHA512 加密 |
| hashTemplate | Hash 加密模版方法 |
| encryptHmacMD5 | HmacMD5 加密 |
| encryptHmacMD5ToHexString | HmacMD5 加密 |
| encryptHmacSHA1 | HmacSHA1 加密 |
| encryptHmacSHA1ToHexString | HmacSHA1 加密 |
| encryptHmacSHA224 | HmacSHA224 加密 |
| encryptHmacSHA224ToHexString | HmacSHA224 加密 |
| encryptHmacSHA256 | HmacSHA256 加密 |
| encryptHmacSHA256ToHexString | HmacSHA256 加密 |
| encryptHmacSHA384 | HmacSHA384 加密 |
| encryptHmacSHA384ToHexString | HmacSHA384 加密 |
| encryptHmacSHA512 | HmacSHA512 加密 |
| encryptHmacSHA512ToHexString | HmacSHA512 加密 |
| hmacTemplate | Hmac 加密模版方法 |
| encryptDES | DES 加密 |
| encryptDESToBase64 | DES 加密 |
| encryptDESToHexString | DES 加密 |
| decryptDES | DES 解密 |
| decryptDESToBase64 | DES 解密 |
| decryptDESToHexString | DES 解密 |
| encrypt3DES | 3DES 加密 |
| encrypt3DESToBase64 | 3DES 加密 |
| encrypt3DESToHexString | 3DES 加密 |
| decrypt3DES | 3DES 解密 |
| decrypt3DESToBase64 | 3DES 解密 |
| decrypt3DESToHexString | 3DES 解密 |
| encryptAES | AES 加密 |
| encryptAESToBase64 | AES 加密 |
| encryptAESToHexString | AES 加密 |
| decryptAES | AES 解密 |
| decryptAESToBase64 | AES 解密 |
| decryptAESToHexString | AES 解密 |
| symmetricTemplate | 对称加密模版方法 |
| encryptRSA | RSA 加密 |
| encryptRSAToBase64 | RSA 加密 |
| encryptRSAToHexString | RSA 加密 |
| decryptRSA | RSA 解密 |
| decryptRSAToBase64 | RSA 解密 |
| decryptRSAToHexString | RSA 解密 |
| rsaTemplate | RSA 加解密模版方法 |


* **字符串 ( 编解码 ) 工具类 ->** [EscapeUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/encrypt/EscapeUtils.java)

| 方法 | 注释 |
| :- | :- |
| escape | 编码 |
| unescape | 解码 |


* **MD5 加密工具类 ->** [MD5Utils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/encrypt/MD5Utils.java)

| 方法 | 注释 |
| :- | :- |
| md5 | 加密内容 ( 32 位小写 MD5 ) |
| md5Upper | 加密内容 ( 32 位大写 MD5 ) |
| getFileMD5 | 获取文件 MD5 值 |
| getFileMD5ToHexString | 获取文件 MD5 值 |


* **SHA 加密工具类 ->** [SHAUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/encrypt/SHAUtils.java)

| 方法 | 注释 |
| :- | :- |
| sha1 | 加密内容 SHA1 |
| sha224 | 加密内容 SHA224 |
| sha256 | 加密内容 SHA256 |
| sha384 | 加密内容 SHA384 |
| sha512 | 加密内容 SHA512 |
| getFileSHA1 | 获取文件 SHA1 值 |
| getFileSHA256 | 获取文件 SHA256 值 |
| shaHex | 加密内容 SHA 模板 |
| getFileSHA | 获取文件 SHA 值 |


* **3DES 对称加密工具类 ->** [TripleDESUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/encrypt/TripleDESUtils.java)

| 方法 | 注释 |
| :- | :- |
| initKey | 生成密钥 |
| encrypt | 3DES 加密 |
| decrypt | 3DES 解密 |


* **异或工具类 ->** [XorUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/encrypt/XorUtils.java)

| 方法 | 注释 |
| :- | :- |
| encryptAsFix | 加解密 ( 固定 Key 方式 ) 这种方式 加解密 方法共用 |
| encrypt | 加密 ( 非固定 Key 方式 ) |
| decrypt | 解密 ( 非固定 Key 方式 ) |
| xorChecksum | 数据异或校验位计算 |


## <span id="devutilscommonfile">**`dev.utils.common.file`**</span>


* **文件分片辅助类 ->** [FilePartAssist.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/file/FilePartAssist.java)

| 方法 | 注释 |
| :- | :- |
| getFile | 获取文件 |
| getFileName | 获取文件名 |
| getFilePartItems | 获取文件分片信息集合 |
| getFilePartItem | 获取指定索引文件分片信息 |
| getPartCount | 获取分片总数 |
| existsPart | 是否存在分片 |
| isOnlyOne | 是否只有一个分片 |
| getPartName | 获取分片文件名 ( 后缀索引拼接 ) |


* **文件分片信息 Item ->** [FilePartItem.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/file/FilePartItem.java)

| 方法 | 注释 |
| :- | :- |
| isFirstItem | 判断是否 First Item |
| isLastItem | 判断是否 Last Item |
| existsPart | 是否存在分片 |
| isOnlyOne | 是否只有一个分片 |
| getPartName | 获取分片文件名 ( 后缀索引拼接 ) |


* **文件分片工具类 ->** [FilePartUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/file/FilePartUtils.java)

| 方法 | 注释 |
| :- | :- |
| getPartName | 获取分片文件名 ( 后缀索引拼接 ) |
| getFilePartAssist | 获取文件分片辅助类 |
| isFilePart | 是否符合文件分片条件 |
| fileSplit | 文件拆分 |
| fileSplitSave | 文件拆分并存储 |
| fileSplitSaves | 文件拆分并存储 |
| fileSplitDelete | 删除拆分文件 |
| fileSplitDeletes | 删除拆分文件 |
| fileSplitMergePaths | 分片合并 |
| fileSplitMergeFiles | 分片合并 |
| fileSplitMerge | 分片合并 |


## <span id="devutilscommonformat">**`dev.utils.common.format`**</span>


* **可变数组格式化 ->** [ArgsFormatter.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/format/ArgsFormatter.java)

| 方法 | 注释 |
| :- | :- |
| get | 获取 ArgsFormatter |
| getStartSpecifier | 获取开始占位说明符 |
| getMiddleSpecifier | 获取中间占位说明符 |
| getEndSpecifier | 获取结尾占位说明符 |
| isThrowError | 是否抛出异常 |
| getDefaultValue | 获取格式化异常默认值 |
| format | 根据可变参数数量自动格式化 |
| formatByArray | 根据可变参数数量自动格式化 |


* **单位数组范围格式化 ->** [UnitSpanFormatter.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/format/UnitSpanFormatter.java)

| 方法 | 注释 |
| :- | :- |
| get | 获取 UnitSpanFormatter |
| getPrecision | 获取单位格式化精度 |
| isAppendZero | 是否自动补 0 |
| getDefaultValue | 获取格式化异常默认值 |
| format | 格式化 |
| formatBySpan | 计算指定单位倍数格式化 |


## <span id="devutilscommonrandom">**`dev.utils.common.random`**</span>


* **随机概率采样算法 ->** [AliasMethod.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/random/AliasMethod.java)

| 方法 | 注释 |
| :- | :- |
| next | 获取随机索引 ( 对应几率索引 ) |


## <span id="devutilscommonthread">**`dev.utils.common.thread`**</span>


* **线程池管理工具类 ->** [DevThreadManager.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/thread/DevThreadManager.java)

| 方法 | 注释 |
| :- | :- |
| getInstance | 获取 DevThreadManager 实例 |
| initConfig | 初始化配置信息 |
| putConfig | 添加配置信息 |
| removeConfig | 移除配置信息 |


* **线程池 ( 构建类 ) ->** [DevThreadPool.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/thread/DevThreadPool.java)

| 方法 | 注释 |
| :- | :- |
| getThreads | 获取线程数 |
| getCalcThreads | 获取线程数 |
| execute | 加入到线程池任务队列 |
| shutdown | shutdown 会等待所有提交的任务执行完成, 不管是正在执行还是保存在任务队列中的已提交任务 |
| shutdownNow | shutdownNow 会尝试中断正在执行的任务 ( 其主要是中断一些指定方法如 sleep 方法 ) , 并且停止执行等待队列中提交的任务 |
| isShutdown | 判断线程池是否已关闭 ( isShutDown 当调用 shutdown() 方法后返回为 true ) |
| isTerminated | 若关闭后所有任务都已完成, 则返回 true |
| awaitTermination | 请求关闭、发生超时或者当前线程中断 |
| submit | 提交一个 Callable 任务用于执行 |
| invokeAll | 执行给定的任务 |
| invokeAny | 执行给定的任务 |
| schedule | 延迟执行 Runnable 命令 |
| scheduleWithFixedRate | 延迟并循环执行命令 |
| scheduleWithFixedDelay | 延迟并以固定休息时间循环执行命令 |


## <span id="devutilscommonvalidator">**`dev.utils.common.validator`**</span>


* **银行卡管理工具类 ->** [BankCheckUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/validator/BankCheckUtils.java)

| 方法 | 注释 |
| :- | :- |
| checkBankCard | 校验银行卡卡号是否合法 |
| getBankCardCheckCode | 从不含校验位的银行卡卡号采用 Luhn 校验算法获取校验位 |
| getNameOfBank | 通过银行卡的 前六位确定 判断银行开户行及卡种 |


* **居民身份证工具类 ->** [IDCardUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/validator/IDCardUtils.java)

| 方法 | 注释 |
| :- | :- |
| validateIdCard15 | 身份证校验规则, 验证 15 位身份编码是否合法 |
| validateIdCard18 | 身份证校验规则, 验证 18 位身份编码是否合法 |
| convert15CardTo18 | 将 15 位身份证号码转换为 18 位 |
| validateTWCard | 验证台湾身份证号码 |
| validateHKCard | 验证香港身份证号码 ( 部份特殊身份证无法检查 ) |
| validateIdCard10 | 判断 10 位数的身份证号, 是否合法 |
| validateCard | 验证身份证是否合法 |
| getAgeByIdCard | 根据身份编号获取年龄 |
| getBirthByIdCard | 根据身份编号获取生日 |
| getBirthdayByIdCard | 根据身份编号获取生日 |
| getYearByIdCard | 根据身份编号获取生日 ( 年份 ) |
| getMonthByIdCard | 根据身份编号获取生日 ( 月份 ) |
| getDateByIdCard | 根据身份编号获取生日 ( 天数 ) |
| getGenderByIdCard | 根据身份编号获取性别 |
| getProvinceByIdCard | 根据身份编号获取户籍省份 |
| getPowerSum | 将身份证的每位和对应位的加权因子相乘之后, 再获取和值 |
| getCheckCode18 | 将 POWER 和值与 11 取模获取余数进行校验码判断 |


* **校验工具类 ->** [ValidatorUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/validator/ValidatorUtils.java)

| 方法 | 注释 |
| :- | :- |
| match | 通用匹配函数 |
| isNumber | 检验数字 |
| isNumberDecimal | 检验数字或包含小数点 |
| isLetter | 判断字符串是不是全是字母 |
| isContainNumber | 判断字符串是不是包含数字 |
| isNumberLetter | 判断字符串是不是只含字母和数字 |
| isSpec | 检验特殊符号 |
| isWx | 检验微信号 |
| isRealName | 检验真实姓名 |
| isNickName | 校验昵称 |
| isUserName | 校验用户名 |
| isPassword | 校验密码 |
| isEmail | 校验邮箱 |
| isUrl | 校验 URL |
| isIPAddress | 校验 IP 地址 |
| isChinese | 校验汉字 ( 无符号, 纯汉字 ) |
| isChineseAll | 判断字符串是不是全是中文 |
| isContainChinese | 判断字符串中包含中文、包括中文字符标点等 |


* **检验联系 ( 手机号码、座机 ) 工具类 ->** [ValiToPhoneUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/validator/ValiToPhoneUtils.java)

| 方法 | 注释 |
| :- | :- |
| isPhoneSimple | 中国手机号码格式验证 ( 简单手机号码校验 ) |
| isPhone | 是否中国手机号码 |
| isPhoneToChinaMobile | 是否中国移动手机号码 |
| isPhoneToChinaUnicom | 是否中国联通手机号码 |
| isPhoneToChinaTelecom | 是否中国电信手机号码 |
| isPhoneToChinaBroadcast | 是否中国广电手机号码 |
| isPhoneToChinaVirtual | 是否中国虚拟运营商手机号码 |
| isPhoneToChinaHkMobile | 是否中国香港手机号码 |
| isPhoneCallNum | 验证电话号码的格式 |