
## Gradle

```gradle
// DevDeprecated - Dev 系列库弃用代码统一存储库
implementation 'io.github.afkt:DevDeprecated:1.0.0'
```

## 目录结构

```
- dev.utils                     | 根目录
   - app                        | APP 相关工具类
      - activity_result         | Activity Result API
      - camera                  | 摄像头相关
         - camera1              | android.hardware.Camera ( Camera1 相关 )
      - image                   | 图片相关处理
      - permission              | 权限工具类
      - toast                   | Toast
         - toaster              | Toaster 处理无通知权限
      - wifi                    | Wifi、热点工具类
```


## 初始化

> ~~该库无需初始化~~，基于 DevApp 库，直接使用即可

## 事项

- 该库属于 `Dev 系列库弃用代码统一存储库` 因本人的时间与精力有限，故迁移部分代码至此，并推荐使用 Github 其他同功能优秀库。

- 迁移的代码**包名目录结构**照旧，如果还想继续使用历史代码，依赖该库即可，无需做任何变更。

- 部分 API 更新不及时或有遗漏等，`具体以对应的工具类为准`

- [检测代码规范、注释内容排版，API 文档生成](https://github.com/afkT/JavaDoc)

- [Change Log](https://github.com/afkT/DevUtils/blob/master/lib/DevDeprecated/CHANGELOG.md)

- 工具类部分模块配置与使用 - [Use and Config](https://github.com/afkT/Resources/blob/main/utils_readme/USE_CONFIG.md)

## API


- dev.utils                                                                | 根目录
   - [app](#devutilsapp)                                                   | APP 相关工具类
      - [activity_result](#devutilsappactivity_result)                     | Activity Result API
      - [camera](#devutilsappcamera)                                       | 摄像头相关
         - [camera1](#devutilsappcameracamera1)                            | android.hardware.Camera ( Camera1 相关 )
      - [image](#devutilsappimage)                                         | 图片相关处理
      - [permission](#devutilsapppermission)                               | 权限工具类
      - [toast](#devutilsapptoast)                                         | Toast
         - [toaster](#devutilsapptoasttoaster)                             | Toaster 处理无通知权限
      - [wifi](#devutilsappwifi)                                           | Wifi、热点工具类


## <span id="devutilsapp">**`dev.utils.app`**</span>


## <span id="devutilsappactivity_result">**`dev.utils.app.activity_result`**</span>


* **Activity Result 封装辅助类 ->** [DefaultActivityResult.java](https://github.com/afkT/DevUtils/blob/master/lib/DevDeprecated/src/main/java/dev/utils/app/activity_result/DefaultActivityResult.java)

| 方法 | 注释 |
| :- | :- |
| getInstance | 获取 DefaultActivityResult 实例 |
| startActivityForResult | Activity 跳转回传 |
| onStartActivityForResult | 跳转 Activity 操作 |
| onActivityResult | 回传处理 |
| start | 跳转回传结果处理 Activity 内部方法 |
| onCreate | onCreate |
| onDestroy | onDestroy |


## <span id="devutilsappcamera">**`dev.utils.app.camera`**</span>


## <span id="devutilsappcameracamera1">**`dev.utils.app.camera.camera1`**</span>


* **摄像头自动获取焦点辅助类 ->** [AutoFocusAssist.java](https://github.com/afkT/DevUtils/blob/master/lib/DevDeprecated/src/main/java/dev/utils/app/camera/camera1/AutoFocusAssist.java)

| 方法 | 注释 |
| :- | :- |
| setFocusModes | 设置对焦模式 |
| isAutoFocus | 是否允许自动对焦 |
| setAutoFocus | 设置是否开启自动对焦 |
| start | 开始对焦 |
| stop | 停止对焦 |


* **摄像头辅助类 ->** [CameraAssist.java](https://github.com/afkT/DevUtils/blob/master/lib/DevDeprecated/src/main/java/dev/utils/app/camera/camera1/CameraAssist.java)

| 方法 | 注释 |
| :- | :- |
| openDriver | 打开摄像头程序 |
| closeDriver | 关闭摄像头程序 |
| startPreview | 开始将 Camera 画面预览到手机上 |
| stopPreview | 停止 Camera 画面预览 |
| getCameraResolution | 获取相机分辨率 |
| getPreviewSize | 获取预览分辨率 |
| getCameraSizeAssist | 获取 Camera.Size 计算辅助类 |
| getCamera | 获取摄像头 |
| setCamera | 设置摄像头 |
| setPreviewNotify | 设置预览回调 |
| setAutoFocus | 设置是否开启自动对焦 |
| isPreviewing | 是否预览中 |
| setAutoInterval | 设置自动对焦时间间隔 |
| isFlashlightEnable | 是否支持手机闪光灯 |
| setFlashlightOn | 打开闪光灯 |
| setFlashlightOff | 关闭闪光灯 |
| isFlashlightOn | 是否打开闪光灯 |


* **摄像头 ( 预览、输出大小 ) 辅助类 ->** [CameraSizeAssist.java](https://github.com/afkT/DevUtils/blob/master/lib/DevDeprecated/src/main/java/dev/utils/app/camera/camera1/CameraSizeAssist.java)

| 方法 | 注释 |
| :- | :- |
| getCamera | 获取摄像头 |
| setPreviewSize | 设置预览大小 |
| getPreviewSize | 根据手机支持的预览分辨率计算, 设置预览尺寸 |
| setPictureSize | 设置拍照图片大小 |
| getPictureSize | 根据手机支持的拍照分辨率计算 |
| getVideoSize | 根据手机支持的视频录制分辨率计算 |


* **摄像头相关工具类 ->** [CameraUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevDeprecated/src/main/java/dev/utils/app/camera/camera1/CameraUtils.java)

| 方法 | 注释 |
| :- | :- |
| isSupportReverse | 判断是否支持反转摄像头 ( 是否存在前置摄像头 ) |
| checkCameraFacing | 检查是否有指定的摄像头 |
| isFrontCamera | 判断是否使用前置摄像头 |
| isBackCamera | 判断是否使用后置摄像头 |
| isUseCameraFacing | 判断使用的摄像头 |
| freeCameraResource | 释放摄像头资源 |
| initCamera | 初始化摄像头 |
| open | 打开摄像头 |


## <span id="devutilsappimage">**`dev.utils.app.image`**</span>


* **图片格式转换工具类 ->** [ImageConvertUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevDeprecated/src/main/java/dev/utils/app/image/ImageConvertUtils.java)

| 方法 | 注释 |
| :- | :- |
| convertBMP | 图片转换 BMP 格式 byte[] 数据 |


## <span id="devutilsapppermission">**`dev.utils.app.permission`**</span>


* **权限请求工具类 ->** [PermissionUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevDeprecated/src/main/java/dev/utils/app/permission/PermissionUtils.java)

| 方法 | 注释 |
| :- | :- |
| isGranted | 判断是否授予了权限 |
| shouldShowRequestPermissionRationale | 获取拒绝权限询问勾选状态 |
| getDeniedPermissionStatus | 获取拒绝权限询问状态集合 |
| canRequestPackageInstalls | 是否存在 APK 安装权限 |
| getAllPermissionToSet | 获取全部权限 |
| getAllPermissionToList | 获取全部权限 |
| getAppPermissionToList | 获取 APP 注册的权限 |
| getAppPermissionToSet | 获取 APP 注册的权限 |
| getAppPermission | 获取 APP 注册的权限 |
| permission | 申请权限初始化 |
| callback | 设置回调方法 |
| setRequestPermissionsResult | 设置是否需要在 Activity 的 onRequestPermissionsResult 回调中, 调用 PermissionUtils.onRequestPermissionsResult(this); |
| request | 请求权限 |
| onRequestPermissionsResult | 请求权限回调 ( 需要在 Activity 的 onRequestPermissionsResult 回调中, 调用 PermissionUtils.onRequestPermissionsResult(this); ) |
| notifyPermissionsChange | 刷新权限改变处理 ( 清空已拒绝的权限记录 ) |
| againRequest | 再次请求处理操作 |


## <span id="devutilsapptoast">**`dev.utils.app.toast`**</span>


* **自定义 View 着色美化 Toast 工具类 ->** [ToastTintUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevDeprecated/src/main/java/dev/utils/app/toast/ToastTintUtils.java)

| 方法 | 注释 |
| :- | :- |
| reset | 重置默认参数 |
| setToastFilter | 设置 Toast 过滤器 |
| setUseHandler | 设置是否使用 Handler 显示 Toast |
| setNullText | 设置 Text 为 null 的文本 |
| setUseConfig | 设置是否使用配置 |
| setGravity | 设置 Toast 显示在屏幕上的位置 |
| setMargin | 设置边距 |
| getDefaultStyle | 获取默认样式 |
| getNormalStyle | 获取 Normal 样式 |
| getInfoStyle | 获取 Info 样式 |
| getWarningStyle | 获取 Warning 样式 |
| getErrorStyle | 获取 Error 样式 |
| getSuccessStyle | 获取 Success 样式 |
| setNormalStyle | 设置 Normal 样式 |
| setInfoStyle | 设置 Info 样式 |
| setWarningStyle | 设置 Warning 样式 |
| setErrorStyle | 设置 Error 样式 |
| setSuccessStyle | 设置 Success 样式 |
| getInfoDrawable | 获取 Info 样式 icon |
| getWarningDrawable | 获取 Warning 样式 icon |
| getErrorDrawable | 获取 Error 样式 icon |
| getSuccessDrawable | 获取 Success 样式 icon |
| normal | normal 样式 Toast |
| info | info 样式 Toast |
| warning | warning 样式 Toast |
| error | error 样式 Toast |
| success | success 样式 Toast |
| custom | custom Toast |


* **Simple Toast 工具类 ( 简单的 Toast 工具类, 支持子线程弹出 Toast ) ->** [ToastUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevDeprecated/src/main/java/dev/utils/app/toast/ToastUtils.java)

| 方法 | 注释 |
| :- | :- |
| reset | 重置默认参数 |
| setToastFilter | 设置 Toast 过滤器 |
| setUseHandler | 设置是否使用 Handler 显示 Toast |
| setNullText | 设置 Text 为 null 的文本 |
| setUseConfig | 设置是否使用配置 |
| setGravity | 设置 Toast 显示在屏幕上的位置 |
| setMargin | 设置边距 |
| showShort | 显示 LENGTH_SHORT Toast |
| showLong | 显示 LENGTH_LONG Toast |
| showToast | 显示 Toast |
| showShortNew | 显示 new LENGTH_SHORT Toast |
| showLongNew | 显示 new LENGTH_LONG Toast |
| showToastNew | 显示新的 Toast |
| newToastText | 获取一个新的 Text Toast |
| showToastView | 显示 View Toast 方法 |
| newToastView | 获取一个新的 View Toast |


## <span id="devutilsapptoasttoaster">**`dev.utils.app.toast.toaster`**</span>


* **Toast 工具类 ( 支持子线程弹出 Toast, 处理无通知权限 ) ->** [DevToast.java](https://github.com/afkT/DevUtils/blob/master/lib/DevDeprecated/src/main/java/dev/utils/app/toast/toaster/DevToast.java)

| 方法 | 注释 |
| :- | :- |
| reset | 重置默认参数 |
| setUseHandler | 设置是否使用 Handler 显示 Toast |
| setNullText | 设置 Text 为 null 的文本 |
| setTextLength | 设置 Toast 文案长度转换 显示时间 |
| initialize | 初始化调用 ( 内部已调用 ) |
| style | 使用单次 Toast 样式配置 |
| defaultStyle | 使用默认 Toast 样式 |
| getToastStyle | 获取 Toast 样式配置 |
| initStyle | 初始化 Toast 样式配置 |
| initToastFilter | 初始化 Toast 过滤器 |
| setView | 设置 Toast 显示的 View |
| show | 显示 Toast |
| cancel | 取消当前显示的 Toast |


## <span id="devutilsappwifi">**`dev.utils.app.wifi`**</span>


* **Wifi 热点工具类 ->** [WifiHotUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevDeprecated/src/main/java/dev/utils/app/wifi/WifiHotUtils.java)

| 方法 | 注释 |
| :- | :- |
| createWifiConfigToAp | 创建 Wifi 热点配置 ( 支持 无密码 / WPA2 PSK ) |
| startWifiAp | 开启 Wifi 热点 |
| closeWifiAp | 关闭 Wifi 热点 |
| getWifiApState | 获取 Wifi 热点状态 |
| getWifiApConfiguration | 获取 Wifi 热点配置信息 |
| setWifiApConfiguration | 设置 Wifi 热点配置信息 |
| isOpenWifiAp | 判断是否打开 Wifi 热点 |
| closeWifiApCheck | 关闭 Wifi 热点 ( 判断当前状态 ) |
| isConnectHot | 是否有设备连接热点 |
| getHotspotServiceIp | 获取热点主机 IP 地址 |
| getHotspotAllotIp | 获取连接上的子网关热点 IP ( 一个 ) |
| getConnectHotspotMsg | 获取连接的热点信息 |
| getHotspotSplitIpMask | 获取热点拼接后的 IP 网关掩码 |
| getApWifiSSID | 获取 Wifi 热点名 |
| getApWifiPwd | 获取 Wifi 热点密码 |
| setOnWifiAPListener | 设置 Wifi 热点监听事件 |


* **Wifi 工具类 ->** [WifiUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevDeprecated/src/main/java/dev/utils/app/wifi/WifiUtils.java)

| 方法 | 注释 |
| :- | :- |
| isOpenWifi | 判断是否打开 Wifi |
| openWifi | 打开 Wifi |
| closeWifi | 关闭 Wifi |
| toggleWifiEnabled | 自动切换 Wifi 开关状态 |
| getWifiState | 获取当前 Wifi 连接状态 |
| startScan | 开始扫描 Wifi |
| getConfiguration | 获取已配置 ( 连接过 ) 的 Wifi 配置 |
| getWifiList | 获取附近的 Wifi 列表 |
| getWifiInfo | 获取连接的 WifiInfo |
| getMacAddress | 获取 MAC 地址 |
| getBSSID | 获取连接的 BSSID |
| getIPAddress | 获取 IP 地址 |
| getNetworkId | 获取连接的 Network Id |
| getSSID | 获取 Wifi SSID |
| formatSSID | 判断是否存在 \"ssid\", 存在则裁剪返回 |
| getPassword | 获取处理后的密码 |
| isHexWepKey | 判断是否 wep 加密 |
| getWifiType | 获取加密类型 |
| getWifiTypeInt | 获取加密类型 |
| getWifiTypeStr | 获取加密类型 |
| isConnNull | 判断是否连接为 null ( unknown ssid ) |
| isConnectAPHot | 获取连接的 Wifi 热点 SSID |
| getSecurity | 获取 Wifi 加密类型 |
| isExistsPwd | 判断 Wifi 加密类型, 是否为加密类型 |
| isExists | 获取指定的 ssid 网络配置 ( 需连接保存过, 才存在 ) |
| delWifiConfig | 删除指定的 Wifi ( SSID ) 配置信息 |
| quickConnWifi | 快速连接 Wifi ( 不使用静态 IP 方式 ) |
| createWifiConfig | 创建 Wifi 配置信息 |
| removeWifiConfig | 移除 Wifi 配置信息 |
| disconnectWifi | 断开指定 networkId 的网络 |