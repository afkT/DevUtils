package dev.utils.app;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

/**
 * detail: 电量管理工具类
 * @author Ttt
 * <pre>
 *     监控电池电量和充电状态
 *     @see <a href="https://developer.android.com/training/monitoring-device-state/battery-monitoring?hl=zh-cn#java"/>
 *     BatteryReceiver
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/LocalModules/DevOther/src/main/java/dev/receiver/BatteryReceiver.kt"/>
 * </pre>
 */
public final class BatteryUtils {

    private BatteryUtils() {
    }

    // 日志 TAG
    private static final String TAG = BatteryUtils.class.getSimpleName();

    // 电池信息 粘性 Intent
    private static       Intent       sBatteryStatus = null;
    // 电池信息 Intent
    private static final IntentFilter sIntentFilter  = new IntentFilter(
            Intent.ACTION_BATTERY_CHANGED
    );

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * 获取电池信息 粘性 Intent
     * @return 电池信息 粘性 Intent
     */
    private static Intent batteryStatus() {
        if (sBatteryStatus == null) {
            sBatteryStatus = AppUtils.registerReceiver(null, sIntentFilter);
        }
        return sBatteryStatus;
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 获取充电方式
     * @return 充电方式 ( USB、AC 充电、无线充电等 )
     */
    public static int getChargePlug() {
        Intent intent = batteryStatus();
        if (intent != null) {
            return intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        }
        return -1;
    }

    /**
     * 是否充电中
     * @return {@code true} success, {@code false} fail
     */
    public static boolean isCharge() {
        return getChargePlug() > 0;
    }

    /**
     * 是否 AC 充电方式
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isPluggedAC() {
        return getChargePlug() == BatteryManager.BATTERY_PLUGGED_AC;
    }

}