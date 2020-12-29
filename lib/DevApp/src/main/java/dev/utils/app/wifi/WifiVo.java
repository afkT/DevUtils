package dev.utils.app.wifi;

import android.net.wifi.ScanResult;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Keep;

import java.util.ArrayList;
import java.util.List;

import dev.utils.LogPrintUtils;

/**
 * detail: Wifi 信息实体类
 * @author Ttt
 */
public class WifiVo
        implements Parcelable {

    // 日志 TAG
    private static final String TAG = WifiVo.class.getSimpleName();

    @Keep // Wifi ssid
    public String wifiSSID  = null;
    @Keep // Wifi 密码
    public String wifiPwd   = null;
    @Keep // Wifi 加密类型
    public int    wifiType  = WifiUtils.NOPWD;
    @Keep // Wifi 信号等级
    public int    wifiLevel = 0;

    public WifiVo() {
    }

    /**
     * 获取 Wifi 信息
     * @param scanResult 扫描的 Wifi 信息
     * @return {@link WifiVo}
     */
    public static WifiVo createWifiVo(final ScanResult scanResult) {
        return createWifiVo(scanResult, false);
    }

    /**
     * 获取 Wifi 信息
     * @param scanResult 扫描的 Wifi 信息
     * @param isAppend   {@code true} 添加引号, {@code false} 删除引号
     * @return {@link WifiVo}
     */
    public static WifiVo createWifiVo(
            final ScanResult scanResult,
            final boolean isAppend
    ) {
        if (scanResult != null) {
            try {
                // 防止 Wifi 名长度为 0
                if (scanResult.SSID.length() == 0) {
                    return null;
                }
                WifiVo wifiVo = new WifiVo();
                // Wifi ssid
                wifiVo.wifiSSID = WifiUtils.formatSSID(scanResult.SSID, isAppend);
                // Wifi 加密类型
                wifiVo.wifiType = WifiUtils.getWifiType(scanResult.capabilities);
                // Wifi 信号等级
                wifiVo.wifiLevel = scanResult.level;
                return wifiVo;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "createWifiVo");
            }
        }
        return null;
    }

    /**
     * 扫描 Wifi 信息
     * @param listScanResults 扫描返回的数据
     * @return {@link List<WifiVo>}
     */
    public static List<WifiVo> scanWifiVos(final List<ScanResult> listScanResults) {
        List<WifiVo> listWifiVos = new ArrayList<>();
        scanWifiVos(listWifiVos, listScanResults);
        return listWifiVos;
    }

    /**
     * 扫描 Wifi 信息
     * @param listWifiVos     数据源
     * @param listScanResults 扫描返回的数据
     * @return {@code true} success, {@code false} fail
     */
    public static boolean scanWifiVos(
            final List<WifiVo> listWifiVos,
            final List<ScanResult> listScanResults
    ) {
        if (listWifiVos == null || listScanResults == null) return false;
        // 清空旧数据
        listWifiVos.clear();
        // 遍历 Wifi 列表数据
        for (int i = 0, len = listScanResults.size(); i < len; i++) {
            // 如果出现异常、或者失败, 则无视当前的索引 Wifi 信息
            try {
                // 获取当前索引的 Wifi 信息
                ScanResult scanResult = listScanResults.get(i);
                // 防止 Wifi 名长度为 0
                if (scanResult.SSID.length() == 0) {
                    continue;
                }
                // 保存 Wifi 信息
                listWifiVos.add(createWifiVo(scanResult));
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "scanWifiVos");
            }
        }
        return true;
    }

    // ==============
    // = Parcelable =
    // ==============

    protected WifiVo(Parcel in) {
        wifiSSID = in.readString();
        wifiPwd = in.readString();
        wifiType = in.readInt();
        wifiLevel = in.readInt();
    }

    public static final Creator<WifiVo> CREATOR = new Creator<WifiVo>() {
        @Override
        public WifiVo createFromParcel(Parcel in) {
            return new WifiVo(in);
        }

        @Override
        public WifiVo[] newArray(int size) {
            return new WifiVo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(
            final Parcel dest,
            final int flags
    ) {
        dest.writeString(wifiSSID);
        dest.writeString(wifiPwd);
        dest.writeInt(wifiType);
        dest.writeInt(wifiLevel);
    }
}