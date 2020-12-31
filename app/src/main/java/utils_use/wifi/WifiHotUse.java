package utils_use.wifi;

import android.Manifest;
import android.net.wifi.WifiConfiguration;
import android.os.Build;

import androidx.annotation.RequiresPermission;

import dev.utils.app.wifi.WifiHotUtils;

/**
 * detail: Wifi 热点使用方法
 * @author Ttt
 */
public final class WifiHotUse {

    private WifiHotUse() {
    }

    /**
     * Wifi 热点使用方法
     */
    @RequiresPermission(allOf = {
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION
    })
    private void wifiHotUse() {

        // 所需权限
        // <uses-permission android:name="android.permission.WRITE_SETTINGS" />
        // <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
        // <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
        // <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
        // <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
        // <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />

        final WifiHotUtils wifiHotUtils = new WifiHotUtils();

        // 有密码
        WifiConfiguration wifiConfiguration = WifiHotUtils.createWifiConfigToAp("WifiHot_AP", "123456789");

        // 无密码
        wifiConfiguration = WifiHotUtils.createWifiConfigToAp("WifiHot_AP", null);

        // 开启热点 ( 兼容 8.0) 7.1 跳转到热点页面, 需手动开启 ( 但是配置信息使用上面的 WifiConfig)
        wifiHotUtils.startWifiAp(wifiConfiguration);

        // 关闭热点
        wifiHotUtils.closeWifiAp();

        // = 8.0 特殊处理 =

        // 8.0 以后热点是针对应用开启, 并且必须强制使用随机生成的 WifiConfig 信息, 无法替换

        // 如果应用开启了热点, 然后后台清空内存, 对应的热点会关闭, 应用开启的热点是系统随机的, 不影响系统设置中的热点配置信息

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            wifiHotUtils.setOnWifiAPListener(new WifiHotUtils.OnWifiAPListener() {
                @Override
                public void onStarted(WifiConfiguration wifiConfig) {
                    String ssid = wifiHotUtils.getApWifiSSID();
                    String pwd  = wifiHotUtils.getApWifiPwd();
                }

                @Override
                public void onStopped() {

                }

                @Override
                public void onFailed(int reason) {

                }
            });
        }
    }
}