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

