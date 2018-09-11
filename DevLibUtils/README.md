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

### `dev.utils.app`

* **View 操作相关工具类 ->** [ViewUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/ViewUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| getContext | 通过 View 获取上下文 |
| isEmpty | 判断 View 是否为null |
| isEmpty | 判断 View... views 是否存在为null |
| isVisibility | 判断 View Visibility 是否 View.VISIBLE |
| isVisibilityIN | 判断 View 是否 View.INVISIBLE |
| isVisibilityGone | 判断 View 是否 View.GONE |
| getVisibility | 获取显示的状态 (View.VISIBLE : View.GONE) |
| getVisibilityIN | 获取显示的状态 (View.VISIBLE : View.INVISIBLE) |
| setVisibility | 设置 View显示状态 visibility |
| setVisibilitys | 设置 View... views 显示状态 visibility |
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


* **震动相关工具类 ->** [VibrationUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/VibrationUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| vibrate | 震动 |
| vibrate | 指定手机以pattern模式振动 |
| cancel | 取消振动 |


* **Uri 工具类 ->** [UriUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/UriUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| getUriForFileToName | 返回处理后的Uri, 单独传递名字, 自动添加包名 ${applicationId} |
| getUriForFile | 返回处理后的Uri |


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


* **状态列表(字体按压、View按压) 工具类 ->** [StateListUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/StateListUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| getColorStateList | 获取ColorStateList |
| createColorStateList | 创建 颜色状态列表 |
| newSelector | 创建 Drawable选择切换 list |


* **dp，px，sp转换、View获取宽高等 工具类 ->** [SizeUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/SizeUtils.java)

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
| loadCertificates | 加载签名 |


* **快捷图标工具类 ->** [ShortCutUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/ShortCutUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| hasShortcut | 检测是否存在桌面快捷方式 |
| addShortcut | 为程序创建桌面快捷方式 |
| delShortcut | 删除程序的快捷方式 |


* **Shell 相关工具类 ->** [ShellUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/ShellUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| execCmd | 执行命令 |


* **Shape 工具类 ->** [ShapeUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/ShapeUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| getDrawable | 获取处理后的 Drawable |
| setDrawable | 设置 View Drawable |
| setRadius | 设置圆角 |
| setRadiusLeft | 设置圆角 |
| setRadiusRight | 设置圆角 |
| setCornerRadii | 设置圆角 |
| setColor | 设置背景色(填充铺满) |
| setStroke | 设置边框颜色 |
| setSize | 设置大小 |
| newBuilder | 创建新的 Shape Builder 对象 |
| newBuilderToLeft | 创建新的 Shape Builder 对象 |
| newBuilderToRight | 创建新的 Shape Builder 对象 |
| newBuilderToGradient | 创建渐变的 Shape Builder 对象 |


* **服务相关工具类 ->** [ServiceUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/ServiceUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| isServiceRunning | 判断服务是否运行 |
| getAllRunningService | 获取所有运行的服务 |
| startService | 启动服务 |
| stopService | 停止服务 |
| bindService | 绑定服务 |
| unbindService | 解绑服务 |


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


* **屏幕相关工具类 ->** [ScreenUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/ScreenUtils.java)

| 方法 | 注释 |
| :-: | :-: |
| getDisplayMetrics | 通过上下文获取 DisplayMetrics (获取关于显示的通用信息，如显示大小，分辨率和字体) |
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
| getStatusBarHeight | 获取应用区域 TitleBar 高度 |
| setSleepDuration | 设置进入休眠时长 |
| getSleepDuration | 获取进入休眠时长 |
| snapShotWithStatusBar | 获取当前屏幕截图，包含状态栏 |
| snapShotWithoutStatusBar | 获取当前屏幕截图，不包含状态栏 |
| getNavigationBarHeight | 获取底部导航栏高度 |
| checkDeviceHasNavigationBar | 检测是否具有底部导航栏 |


