package dev.utils.app.wifi;

import android.net.wifi.ScanResult;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Keep;

import java.util.List;

import dev.utils.LogPrintUtils;

/**
 * detail: Wifi 信息实体类
 * @author Ttt
 */
public class WifiVo implements Parcelable {

    // 日志 TAG
    private static final String TAG = WifiVo.class.getSimpleName();

    @Keep // wifi ssid
    public String wSSID = null;
    @Keep // wifi 密码
    public String wPwd = null;
    @Keep // wifi 加密类型
    public int wType = WifiUtils.NOPWD;
    @Keep // wifi 信号等级
    public int wLevel = 0;

    public WifiVo() {
    }

    /**
     * 获取 wifi 信息
     * @param scanResult 扫描的 wifi 信息
     * @return {@link WifiVo}
     */
    public static WifiVo createWifiVo(final ScanResult scanResult) {
        return createWifiVo(scanResult, false);
    }

    /**
     * 获取 wifi 信息
     * @param scanResult 扫描的 wifi 信息
     * @param isAppend   {@code true} 添加引号, {@code false} 删除引号
     * @return {@link WifiVo}
     */
    public static WifiVo createWifiVo(final ScanResult scanResult, final boolean isAppend) {
        if (scanResult != null) {
            try {
                // 防止 wifi 名长度为 0
                if (scanResult.SSID.length() == 0) {
                    return null;
                }
                WifiVo wifiVo = new WifiVo();
                // wifi ssid
                wifiVo.wSSID = WifiUtils.formatSSID(scanResult.SSID, isAppend);
                // wifi 加密类型
                wifiVo.wType = WifiUtils.getWifiType(scanResult.capabilities);
                // wifi 信号等级
                wifiVo.wLevel = scanResult.level;
                return wifiVo;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "createWifiVo");
            }
        }
        return null;
    }

    /**
     * 扫描 wifi 信息
     * @param listWifiVos     数据源
     * @param listScanResults 扫描返回的数据
     */
    public static void scanWifiVos(final List<WifiVo> listWifiVos, final List<ScanResult> listScanResults) {
        if (listWifiVos == null || listScanResults == null) return;
        // 清空旧数据
        listWifiVos.clear();
        // 遍历 wifi 列表数据
        for (int i = 0, len = listScanResults.size(); i < len; i++) {
            // 如果出现异常、或者失败, 则无视当前的索引 wifi 信息
            try {
                // 获取当前索引的 wifi 信息
                ScanResult scanResult = listScanResults.get(i);
                // 防止 wifi 名长度为 0
                if (scanResult.SSID.length() == 0) {
                    continue;
                }
                // 保存 wifi 信息
                listWifiVos.add(createWifiVo(scanResult));
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "scanWifiVos");
            }
        }
    }

    // ==============
    // = Parcelable =
    // ==============

    protected WifiVo(Parcel in) {
        wSSID = in.readString();
        wPwd = in.readString();
        wType = in.readInt();
        wLevel = in.readInt();
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
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeString(wSSID);
        dest.writeString(wPwd);
        dest.writeInt(wType);
        dest.writeInt(wLevel);
    }
}
