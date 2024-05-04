package dev.utils.app;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;

import androidx.annotation.RequiresApi;

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

    // 电池信息粘性 Intent
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
    public static Intent batteryStatus() {
        if (sBatteryStatus == null) {
            sBatteryStatus = AppUtils.registerReceiver(null, sIntentFilter);
        }
        return sBatteryStatus;
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 是否存在电池
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isPresent() {
        Intent intent = batteryStatus();
        if (intent != null) {
            return intent.getBooleanExtra(BatteryManager.EXTRA_PRESENT, false);
        }
        return false;
    }

    /**
     * 是否低电量
     * @return {@code true} yes, {@code false} no
     */
    @RequiresApi(api = Build.VERSION_CODES.P)
    public static boolean isBatteryLow() {
        Intent intent = batteryStatus();
        if (intent != null) {
            return intent.getBooleanExtra(BatteryManager.EXTRA_BATTERY_LOW, false);
        }
        return false;
    }

    /**
     * 是否低电量
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isBatteryLow20() {
        return isBatteryLow(20);
    }

    /**
     * 是否低电量
     * @param minimum 最低电量百分比
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isBatteryLow(final int minimum) {
        int level = getLevelPercent();
        // 小于等于自定义最低电量百分比则表示为低电量
        return level <= minimum;
    }

    /**
     * 是否高电量
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isBatteryHigh() {
        return isBatteryHigh(70);
    }

    /**
     * 是否高电量
     * @param minimum 最高电量百分比
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isBatteryHigh(final int minimum) {
        int level = getLevelPercent();
        // 大于等于自定义最高电量百分比则表示为高电量
        return level >= minimum;
    }

    /**
     * 获取当前电量百分比
     * @return 当前电量百分比
     */
    public static int getLevelPercent() {
        Intent intent = batteryStatus();
        if (intent != null) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            if (level > 0 && scale > 0) {
                double batteryPercent = (double) level / (double) scale;
                return (int) (batteryPercent * 100D);
            }
        }
        return -1;
    }

    /**
     * 获取当前电量
     * <pre>
     *     需搭配 {@link #getScale()} 使用
     * </pre>
     * @return 当前电量
     */
    public static int getLevel() {
        Intent intent = batteryStatus();
        if (intent != null) {
            return intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        }
        return -1;
    }

    /**
     * 获取电池最大电量
     * @return 电池最大电量
     */
    public static int getScale() {
        Intent intent = batteryStatus();
        if (intent != null) {
            return intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        }
        return -1;
    }

    /**
     * 获取电池充电周期
     * @return 电池充电周期
     */
    @RequiresApi(api = Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    public static int getCycleCount() {
        Intent intent = batteryStatus();
        if (intent != null) {
            return intent.getIntExtra(BatteryManager.EXTRA_CYCLE_COUNT, -1);
        }
        return -1;
    }

    /**
     * 获取电池温度
     * @return 电池温度
     */
    public static int getTemperature() {
        Intent intent = batteryStatus();
        if (intent != null) {
            return intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1);
        }
        return -1;
    }

    /**
     * 获取电池电压
     * @return 电池电压
     */
    public static int getVoltage() {
        Intent intent = batteryStatus();
        if (intent != null) {
            return intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1);
        }
        return -1;
    }

    /**
     * 获取电池技术
     * @return 电池技术
     */
    public static String getTechnology() {
        Intent intent = batteryStatus();
        if (intent != null) {
            return intent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY);
        }
        return null;
    }

    // ==========
    // = 充电方式 =
    // ==========

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
     * @return {@code true} yes, {@code false} no
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

    /**
     * 是否 USB 充电方式
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isPluggedUSB() {
        return getChargePlug() == BatteryManager.BATTERY_PLUGGED_USB;
    }

    /**
     * 是否无线充电方式
     * @return {@code true} yes, {@code false} no
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean isPluggedWireless() {
        return getChargePlug() == BatteryManager.BATTERY_PLUGGED_WIRELESS;
    }

    /**
     * 是否 DOCK 充电方式
     * @return {@code true} yes, {@code false} no
     */
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public static boolean isPluggedDock() {
        return getChargePlug() == BatteryManager.BATTERY_PLUGGED_DOCK;
    }

    // ==========
    // = 充电状态 =
    // ==========

    /**
     * 获取充电状态
     * @return 充电状态
     */
    public static int getChargeStatus() {
        Intent intent = batteryStatus();
        if (intent != null) {
            return intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        }
        return -1;
    }

    /**
     * 是否充电状态
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isChargingStatus() {
        return getChargeStatus() == BatteryManager.BATTERY_STATUS_CHARGING;
    }

    /**
     * 是否放电状态
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isDisChargingStatus() {
        return getChargeStatus() == BatteryManager.BATTERY_STATUS_DISCHARGING;
    }

    /**
     * 是否充满电状态
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isFullStatus() {
        return getChargeStatus() == BatteryManager.BATTERY_STATUS_FULL;
    }

    /**
     * 是否不在充电状态
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNotChargingStatus() {
        return getChargeStatus() == BatteryManager.BATTERY_STATUS_NOT_CHARGING;
    }

    /**
     * 是否未知充电状态
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isUnknownStatus() {
        return getChargeStatus() == BatteryManager.BATTERY_STATUS_UNKNOWN;
    }

    // ==============
    // = 电池健康状况 =
    // ==============

    /**
     * 获取电池健康状况
     * @return 电池健康状况
     */
    public static int getHealth() {
        Intent intent = batteryStatus();
        if (intent != null) {
            return intent.getIntExtra(BatteryManager.EXTRA_HEALTH, -1);
        }
        return -1;
    }

    /**
     * 是否电池状况良好
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isHealthGood() {
        return getHealth() == BatteryManager.BATTERY_HEALTH_GOOD;
    }

    /**
     * 是否电池状况过热
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isHealthOverheat() {
        return getHealth() == BatteryManager.BATTERY_HEALTH_OVERHEAT;
    }

    /**
     * 是否电池状况低温
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isHealthCold() {
        return getHealth() == BatteryManager.BATTERY_HEALTH_COLD;
    }

    /**
     * 是否电池状况死机
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isHealthDead() {
        return getHealth() == BatteryManager.BATTERY_HEALTH_DEAD;
    }

    /**
     * 是否电池状况电压过载
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isHealthOverVoltage() {
        return getHealth() == BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE;
    }

    /**
     * 是否电池状况不明故障
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isHealthUnspecifiedFailure() {
        return getHealth() == BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE;
    }

    /**
     * 是否电池状况未知
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isHealthUnknown() {
        return getHealth() == BatteryManager.BATTERY_HEALTH_UNKNOWN;
    }
}