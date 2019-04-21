# Wifi 热点工具类

#### 使用演示类 [WifiHotUse](https://github.com/afkT/DevUtils/blob/master/app/src/main/java/com/dev/utils/wifi/WifiHotUse.java) 介绍了配置参数及使用

> 1. Android 8.0 开始, 热点操作方法, 已经变更 - https://blog.csdn.net/bukker/article/details/78649504
> 2. Android 7.1 系统以上不支持自动开启热点,需要手动开启热点 - https://www.jianshu.com/p/9dbb02c3e21f

#### 项目类结构

* Wifi 热点工具类（[WifiHotUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/wifi/WifiHotUtils.java)）：Wifi 热点工具类, 内部适配不同 Android 版本 api

## API 文档

| 方法 | 注释 |
| :- | :- |
| createWifiConfigToAp | 创建Wifi配置信息(无其他操作，单独返回WifiConfig) => Wifi热点 (就支持 无密码/WPA2 PSK) |
| stratWifiAp | 开启Wifi热点 |
| closeWifiAp | 关闭Wifi热点 |
| getWifiApState | 获取Wifi热点状态 |
| getWifiApConfiguration | 获取Wifi热点配置信息 |
| setWifiApConfiguration | 设置Wifi热点配置信息 |
| isOpenWifiAp | 判断是否打开Wifi热点 |
| closeWifiApCheck | 关闭Wifi热点(判断当前状态) |
| isConnectHot | 是否有连接热点 |
| getHotspotServiceIp | 获取热点主机ip地址 |
| getHotspotAllotIp | 获取连接上的子网关热点IP(一个) |
| getHotspotSplitIpMask | 获取热点拼接后的ip网关掩码 |
| getApWifiSSID | 获取Wifi 热点名 |
| getApWifiPwd | 获取Wifi 热点密码 |
| setOnWifiAPListener | 设置 Wifi 热点监听 |
| onStarted | 开启热点触发 |
| onStopped | 关闭热点回调 |
| onFailed | 失败回调 |

#### 使用方法
```java
// 需要的权限:
// <uses-permission android:name="android.permission.WRITE_SETTINGS" />
// <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
// <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
// <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
// <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
// <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>

final WifiHotUtils wifiHotUtils = new WifiHotUtils(DevUtils.getContext());

// 有密码
WifiConfiguration wifiConfiguration = WifiHotUtils.createWifiConfigToAp("WifiHot_AP", "123456789");

// 无密码
wifiConfiguration = WifiHotUtils.createWifiConfigToAp("WifiHot_AP", null);

// 开启热点(兼容8.0)  7.1 跳转到热点页面, 需手动开启(但是配置信息使用上面的 WifiConfig)
wifiHotUtils.stratWifiAp(wifiConfiguration);

// 关闭热点
wifiHotUtils.closeWifiAp();

// = 8.0 特殊处理 =

// 8.0 以后热点是针对应用开启, 并且必须强制使用随机生成的 WifiConfig 信息, 无法替换

// 如果应用开启了热点, 然后后台清空内存, 对应的热点会关闭, 应用开启的热点是系统随机的，不影响系统设置中的热点配置信息

if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
    wifiHotUtils.setOnWifiAPListener(new WifiHotUtils.onWifiAPListener() {
        @Override
        public void onStarted(WifiConfiguration wifiConfig) {
            String ssid = wifiHotUtils.getApWifiSSID();
            String pwd = wifiHotUtils.getApWifiPwd();
        }

        @Override
        public void onStopped() {

        }

        @Override
        public void onFailed(int reason) {

        }
    });
}
```