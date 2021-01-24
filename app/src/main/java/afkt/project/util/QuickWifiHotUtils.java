package afkt.project.util;

import android.annotation.SuppressLint;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;

import dev.engine.log.DevLogEngine;
import dev.utils.app.wifi.WifiHotUtils;
import dev.utils.app.wifi.WifiUtils;

/**
 * detail: 封装 Wifi 热点快捷操作工具类
 * @author Ttt
 * <pre>
 *     开启热点前必须获取到所需权限
 * </pre>
 */
public class QuickWifiHotUtils {

    // 日志 TAG
    private final        String       TAG                    = QuickWifiHotUtils.class.getSimpleName();
    // Wifi 工具类
    private final        WifiUtils    wifiUtils;
    // Wifi 热点工具类
    private final        WifiHotUtils wifiHotUtils;
    // 是否停止线程检查
    private              boolean      isStop                 = false;
    // 是否停止开启检查
    private              boolean      isCheck                = false;
    // 是否线程检查热点连接状态
    private              boolean      isThreadCheckHot       = false;
    // 成功关闭 Wifi 准备开启热点
    private static final int          CLOSE_WIFI_SUCCESS     = 103;
    // 开启热点成功
    private static final int          START_WIFISPOT_SUCCESS = 104;
    // 检查是否连接热点
    private static final int          CHECK_HOT_CONN         = 105;
    // 热点 SSID
    private              String       hotSSID;
    // 热点密码
    private              String       hotPwd;

    /**
     * 构造函数
     * @param wifiUtils    Wifi 工具类
     * @param wifiHotUtils Wifi 热点工具类
     */
    public QuickWifiHotUtils(
            WifiUtils wifiUtils,
            WifiHotUtils wifiHotUtils
    ) {
        this.wifiUtils = wifiUtils;
        this.wifiHotUtils = wifiHotUtils;
    }

    private final Handler hotHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // 如果属于需要销毁, 则全部不处理
            if (operate != null && operate.isFinishing()) {
                isStop = true;
                isCheck = false;
                isThreadCheckHot = false;
                return;
            }

            switch (msg.what) {
                case CLOSE_WIFI_SUCCESS: // 成功关闭 Wifi 准备开启热点
                    // 打印日志
                    DevLogEngine.getEngine().dTag(TAG, "hotHandler 关闭 Wifi 成功, 开启热点中");
                    // 停止线程检查
                    setWifiCheck(false);
                    // 开始进行线程检查
                    setWifiApCheck(true);
                    // 开启热点
                    new Thread(startWifiSpotThread).start();
                    break;
                case START_WIFISPOT_SUCCESS: // 开启热点成功
                    // 打印日志
                    DevLogEngine.getEngine().dTag(TAG, "hotHandler 开启热点成功");
                    // 停止线程检查
                    setWifiApCheck(false);
                    // 需要检查连接状态
                    isThreadCheckHot = true;
                    // 开启线程检查
                    new Thread(hotCheckThread).start();
                    break;
                case CHECK_HOT_CONN: // 检查是否连接热点
                    // 打印日志
                    DevLogEngine.getEngine().dTag(TAG, "hotHandler 检查是否连接热点");
                    // 判断是否存在设备连接热点
                    boolean isConnectHot = wifiHotUtils.isConnectHot();
                    // 如果存在, 则尝试连接
                    if (isConnectHot && isThreadCheckHot) {
                        // 表示不需要进行检查了
                        isThreadCheckHot = false;
                        // 通过获取是否存在其他设备 - 手机连接上该热点
                        // 打印日志
                        DevLogEngine.getEngine().dTag(TAG, "存在设备连接热点");
                    }
                    break;
            }
        }
    };

    /**
     * 设置 Wifi 线程检查状态
     * @param isCheck 是否检查
     */
    private void setWifiCheck(boolean isCheck) {
        if (isCheck) {
            // 需要进行线程检查
            isStop = false;
        } else {
            // 停止线程检查
            isStop = true;
            // 删除上一个任务, 并且重新绑定任务
            hotHandler.removeCallbacks(closeWifiThread);
        }
    }

    /**
     * 设置 Wifi 热点线程检查状态
     * @param isCheck 是否检查
     */
    private void setWifiApCheck(boolean isCheck) {
        if (isCheck) {
            // 需要进行线程检查
            this.isCheck = false;
        } else {
            // 停止线程检查
            this.isCheck = true;
            // 删除上一个任务, 并且重新绑定任务
            hotHandler.removeCallbacks(startWifiSpotThread);
        }
    }

    /**
     * 关闭 Wifi 检查线程
     */
    private final Thread closeWifiThread = new Thread() {
        @Override
        public void run() {
            if (isStop) return;
            // 如果属于需要销毁, 则全部不处理
            if (operate != null && operate.isFinishing()) {
                isStop = true;
                isCheck = false;
                isThreadCheckHot = false;
                return;
            }

            // 是否延时检查
            boolean isPostDelayed = false;
            // 获取 Wifi 连接状态
            switch (wifiUtils.getWifiState()) {
                case WifiManager.WIFI_STATE_ENABLED: // 已打开
                case WifiManager.WIFI_STATE_ENABLING: // 正在打开
                    // case WifiManager.WIFI_STATE_UNKNOWN: // 未知
                    isPostDelayed = true;
                    DevLogEngine.getEngine().dTag(TAG, "Wifi 已打开、正在打开");
                    wifiUtils.closeWifi(); // 关闭 Wifi
                    break;
                case WifiManager.WIFI_STATE_DISABLED: // 已关闭
                    isPostDelayed = false;
                    DevLogEngine.getEngine().dTag(TAG, "Wifi 已关闭");
                    break;
                case WifiManager.WIFI_STATE_DISABLING: // 正在关闭
                    isPostDelayed = true;
                    DevLogEngine.getEngine().dTag(TAG, "Wifi 正在关闭");
                    break;
            }
            // 判断是否延时 0.4 秒进行开启热点
            if (isPostDelayed) {
                // 删除上一个任务, 并且重新绑定任务
                hotHandler.removeCallbacks(closeWifiThread);
                hotHandler.postDelayed(closeWifiThread, 400);
            } else { // 开启热点
                hotHandler.sendEmptyMessage(CLOSE_WIFI_SUCCESS);
            }
        }
    };

    /**
     * 开启 Wifi 热点线程
     */
    private final Thread startWifiSpotThread = new Thread() {
        @SuppressLint("MissingPermission")
        @Override
        public void run() {
            if (isCheck) return;
            // 如果属于需要销毁, 则全部不处理
            if (operate != null && operate.isFinishing()) {
                isStop = true;
                isCheck = false;
                isThreadCheckHot = false;
                return;
            }
            // 是否延时检查
            boolean isPostDelayed = true;
            // 获取 Wifi 热点状态
            switch (wifiHotUtils.getWifiApState()) {
                case WifiHotUtils.WIFI_AP_STATE_DISABLING: // Wifi 热点正在关闭
                    DevLogEngine.getEngine().dTag(TAG, "Wifi 热点正在关闭");
                    break;
                case WifiHotUtils.WIFI_AP_STATE_DISABLED: // Wifi 热点已关闭
                    DevLogEngine.getEngine().dTag(TAG, "Wifi 热点已关闭");
                    // 开启热点
                    WifiConfiguration wifiConfiguration = WifiHotUtils.createWifiConfigToAp(hotSSID, hotPwd);
                    wifiHotUtils.startWifiAp(wifiConfiguration);
                    break;
                case WifiHotUtils.WIFI_AP_STATE_ENABLING: // Wifi 热点正在打开
                    DevLogEngine.getEngine().dTag(TAG, "Wifi 热点正在打开");
                    break;
                case WifiHotUtils.WIFI_AP_STATE_ENABLED: // Wifi 热点已打开
                    DevLogEngine.getEngine().dTag(TAG, "Wifi 热点已打开");
                    String wifiap = "ssid: " + wifiHotUtils.getApWifiSSID() + "\npwd: " + wifiHotUtils.getApWifiSSID();
                    DevLogEngine.getEngine().dTag(TAG, wifiap);
                    break;
                case WifiHotUtils.WIFI_AP_STATE_FAILED: // Wifi 热点状态未知
                    DevLogEngine.getEngine().dTag(TAG, "Wifi热点状态未知");
                    break;
            }
            // 判断是否延时 0.4 秒进行开启热点
            if (isPostDelayed) {
                // 删除上一个任务, 并且重新绑定任务
                hotHandler.removeCallbacks(startWifiSpotThread);
                hotHandler.postDelayed(startWifiSpotThread, 400);
            } else { // 开启热点成功
                hotHandler.sendEmptyMessage(START_WIFISPOT_SUCCESS);
            }
        }
    };

    /**
     * 检查热点连接线程
     */
    private final Thread hotCheckThread = new Thread() {
        @Override
        public void run() {
            while (isThreadCheckHot) {
                // 如果属于需要销毁, 则全部不处理
                if (operate != null && operate.isFinishing()) {
                    isStop = true;
                    isCheck = false;
                    isThreadCheckHot = false;
                    return;
                }
                // 检查是否连接热点
                hotHandler.sendEmptyMessage(CHECK_HOT_CONN);
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                }
            }
        }
    };

    // ===============
    // = 对外公开方法 =
    // ===============

    /**
     * 开启热点
     * @param ssid Wifi 名
     * @param pwd  Wifi 密码
     */
    public void openHotspot(
            String ssid,
            String pwd
    ) {
        // 打印日志
        DevLogEngine.getEngine().dTag(TAG, "openHotspot 开启热点 ssid: %s, pwd: %s", ssid, pwd);
        hotSSID = ssid;
        hotPwd = pwd;
        // 如果开启了 Wifi 则进行关闭 Wifi
        if (wifiUtils.isOpenWifi()) {
            // 开始进行线程检查
            setWifiCheck(true);
            // 开启线程
            new Thread(closeWifiThread).start();
        } else { // 开启热点
            hotHandler.sendEmptyMessage(CLOSE_WIFI_SUCCESS);
        }
    }

    /**
     * 关闭热点
     */
    public void closeHotspot() {
        // 全部不处理
        isStop = true;
        isCheck = false;
        isThreadCheckHot = false;
        // 关闭热点
        wifiHotUtils.closeWifiAp();
    }

    // 操作接口
    public Operate operate;

    /**
     * 设置操作接口
     * @param operate {@link Operate}
     * @return {@link QuickWifiHotUtils}
     */
    public QuickWifiHotUtils setOperate(Operate operate) {
        this.operate = operate;
        return this;
    }

    /**
     * detail: 操作接口
     * @author Ttt
     */
    public interface Operate {

        /**
         * 是否需要销毁
         * @return {@code true} yes, {@code false} no
         */
        boolean isFinishing();
    }
}