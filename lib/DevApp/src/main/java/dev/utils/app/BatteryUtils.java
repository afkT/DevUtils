package dev.utils.app;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.os.PowerManager;

import androidx.annotation.RequiresApi;

import dev.utils.LogPrintUtils;

/**
 * detail: 电量管理工具类
 * @author Ttt
 * <pre>
 *     监控电池电量和充电状态
 *     @see <a href="https://developer.android.com/training/monitoring-device-state/battery-monitoring?hl=zh-cn#java"/>
 * </pre>
 */
public final class BatteryUtils {

    private BatteryUtils() {
    }

    // 日志 TAG
    private static final String TAG = BatteryUtils.class.getSimpleName();

    // 电池信息获取包装类
    private static       Info         sInfo         = null;
    // 电池信息 Intent
    private static final IntentFilter sIntentFilter = new IntentFilter(
            Intent.ACTION_BATTERY_CHANGED
    );

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 是否省电模式
     * @return {@code true} yes, {@code false} no
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static boolean isPowerSaveMode() {
        PowerManager powerManager = AppUtils.getPowerManager();
        if (powerManager != null) {
            try {
                return powerManager.isPowerSaveMode();
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "isPowerSaveMode");
            }
        }
        return false;
    }

    // ========
    // = Info =
    // ========

    /**
     * 获取电池信息获取包装类
     * @return 电池信息获取包装类
     */
    public static Info getInfo() {
        if (sInfo == null) {
            sInfo = new Info();
        }
        return sInfo;
    }

    /**
     * 刷新电池信息粘性 Intent
     * @return {@link Info}
     */
    public static Info refreshBatteryStatus() {
        return getInfo().refreshBatteryStatus();
    }

    // ==========
    // = 信息获取 =
    // ==========

    /**
     * 是否存在电池
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isPresent() {
        return getInfo().isPresent();
    }

    /**
     * 是否低电量
     * @return {@code true} yes, {@code false} no
     */
    @RequiresApi(api = Build.VERSION_CODES.P)
    public static boolean isBatteryLow() {
        return getInfo().isBatteryLow();
    }

    /**
     * 是否低电量
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isBatteryLow20() {
        return getInfo().isBatteryLow20();
    }

    /**
     * 是否低电量
     * @param minimum 最低电量百分比
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isBatteryLow(final int minimum) {
        return getInfo().isBatteryLow(minimum);
    }

    /**
     * 是否高电量
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isBatteryHigh() {
        return getInfo().isBatteryHigh();
    }

    /**
     * 是否高电量
     * @param maximum 最高电量百分比
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isBatteryHigh(final int maximum) {
        return getInfo().isBatteryHigh(maximum);
    }

    /**
     * 获取当前电量百分比
     * @return 当前电量百分比
     */
    public static int getLevelPercent() {
        return getInfo().getLevelPercent();
    }

    /**
     * 获取当前电量
     * <pre>
     *     需搭配 {@link #getScale()} 使用
     * </pre>
     * @return 当前电量
     */
    public static int getLevel() {
        return getInfo().getLevel();
    }

    /**
     * 获取电池最大电量
     * @return 电池最大电量
     */
    public static int getScale() {
        return getInfo().getScale();
    }

    /**
     * 获取电池充电周期
     * @return 电池充电周期
     */
    @RequiresApi(api = Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    public static int getCycleCount() {
        return getInfo().getCycleCount();
    }

    /**
     * 获取电池温度
     * @return 电池温度
     */
    public static int getTemperature() {
        return getInfo().getTemperature();
    }

    /**
     * 获取电池电压
     * @return 电池电压
     */
    public static int getVoltage() {
        return getInfo().getVoltage();
    }

    /**
     * 获取电池技术
     * @return 电池技术
     */
    public static String getTechnology() {
        return getInfo().getTechnology();
    }

    // ==========
    // = 充电方式 =
    // ==========

    /**
     * 获取充电方式
     * @return 充电方式 ( USB、AC 充电、无线充电等 )
     */
    public static int getChargePlug() {
        return getInfo().getChargePlug();
    }

    /**
     * 是否充电中
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isCharge() {
        return getInfo().isCharge();
    }

    /**
     * 是否 AC 充电方式
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isPluggedAC() {
        return getInfo().isPluggedAC();
    }

    /**
     * 是否 USB 充电方式
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isPluggedUSB() {
        return getInfo().isPluggedUSB();
    }

    /**
     * 是否无线充电方式
     * @return {@code true} yes, {@code false} no
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean isPluggedWireless() {
        return getInfo().isPluggedWireless();
    }

    /**
     * 是否 DOCK 充电方式
     * @return {@code true} yes, {@code false} no
     */
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public static boolean isPluggedDock() {
        return getInfo().isPluggedDock();
    }

    // ==========
    // = 充电状态 =
    // ==========

    /**
     * 获取充电状态
     * @return 充电状态
     */
    public static int getChargeStatus() {
        return getInfo().getChargeStatus();
    }

    /**
     * 是否充电状态
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isChargingStatus() {
        return getInfo().isChargingStatus();
    }

    /**
     * 是否放电状态
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isDisChargingStatus() {
        return getInfo().isDisChargingStatus();
    }

    /**
     * 是否充满电状态
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isFullStatus() {
        return getInfo().isFullStatus();
    }

    /**
     * 是否不在充电状态
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNotChargingStatus() {
        return getInfo().isNotChargingStatus();
    }

    /**
     * 是否未知充电状态
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isUnknownStatus() {
        return getInfo().isUnknownStatus();
    }

    // ==============
    // = 电池健康状况 =
    // ==============

    /**
     * 获取电池健康状况
     * @return 电池健康状况
     */
    public static int getHealth() {
        return getInfo().getHealth();
    }

    /**
     * 是否电池状况良好
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isHealthGood() {
        return getInfo().isHealthGood();
    }

    /**
     * 是否电池状况过热
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isHealthOverheat() {
        return getInfo().isHealthOverheat();
    }

    /**
     * 是否电池状况低温
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isHealthCold() {
        return getInfo().isHealthCold();
    }

    /**
     * 是否电池状况死机
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isHealthDead() {
        return getInfo().isHealthDead();
    }

    /**
     * 是否电池状况电压过载
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isHealthOverVoltage() {
        return getInfo().isHealthOverVoltage();
    }

    /**
     * 是否电池状况不明故障
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isHealthUnspecifiedFailure() {
        return getInfo().isHealthUnspecifiedFailure();
    }

    /**
     * 是否电池状况未知
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isHealthUnknown() {
        return getInfo().isHealthUnknown();
    }

    // =========
    // = 包装类 =
    // =========

    /**
     * detail: 电池信息获取包装类
     * @author Ttt
     * <pre>
     *     防止数据非最新, 需要自行根据时机调用 {@link #refreshBatteryStatus()}
     *     也可以强制设置为每次进行刷新 {@link #setRefreshEnabled()}
     *     推荐自行根据时机进行刷新调用
     * </pre>
     */
    public static final class Info {

        // 电池信息粘性 Intent
        private Intent  mBatteryStatus = null;
        // 是否总是进行刷新
        private boolean mRefreshIntent = false;

        // =======
        // = 开关 =
        // =======

        /**
         * 是否启用 Intent 每次刷新
         * @return {@code true} yes, {@code false} no
         */
        public boolean isRefreshEnabled() {
            return mRefreshIntent;
        }

        /**
         * 启用 Intent 每次刷新
         * @return {@link Info}
         */
        public Info setRefreshEnabled() {
            mRefreshIntent = true;
            return this;
        }

        /**
         * 禁用 Intent 每次刷新
         * @return {@link Info}
         */
        public Info setRefreshDisabled() {
            mRefreshIntent = false;
            return this;
        }

        // ==========
        // = Intent =
        // ==========

        /**
         * 刷新电池信息粘性 Intent
         * @return {@link Info}
         */
        public Info refreshBatteryStatus() {
            batteryStatus(true);
            return this;
        }

        /**
         * 获取电池信息粘性 Intent
         * @return 电池信息粘性 Intent
         */
        public Intent batteryStatus() {
            return batteryStatus(mRefreshIntent);
        }

        /**
         * 获取电池信息粘性 Intent
         * @param refresh 是否刷新
         * @return 电池信息粘性 Intent
         */
        public Intent batteryStatus(final boolean refresh) {
            if (mBatteryStatus == null || refresh) {
                mBatteryStatus = AppUtils.registerReceiver(null, sIntentFilter);
            }
            return mBatteryStatus;
        }

        // ==========
        // = 信息获取 =
        // ==========

        /**
         * 是否存在电池
         * @return {@code true} yes, {@code false} no
         */
        public boolean isPresent() {
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
        public boolean isBatteryLow() {
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
        public boolean isBatteryLow20() {
            return isBatteryLow(20);
        }

        /**
         * 是否低电量
         * @param minimum 最低电量百分比
         * @return {@code true} yes, {@code false} no
         */
        public boolean isBatteryLow(final int minimum) {
            int level = getLevelPercent();
            // 小于等于自定义最低电量百分比则表示为低电量
            return level <= minimum;
        }

        /**
         * 是否高电量
         * @return {@code true} yes, {@code false} no
         */
        public boolean isBatteryHigh() {
            return isBatteryHigh(70);
        }

        /**
         * 是否高电量
         * @param maximum 最高电量百分比
         * @return {@code true} yes, {@code false} no
         */
        public boolean isBatteryHigh(final int maximum) {
            int level = getLevelPercent();
            // 大于等于自定义最高电量百分比则表示为高电量
            return level >= maximum;
        }

        /**
         * 获取当前电量百分比
         * @return 当前电量百分比
         */
        public int getLevelPercent() {
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
        public int getLevel() {
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
        public int getScale() {
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
        public int getCycleCount() {
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
        public int getTemperature() {
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
        public int getVoltage() {
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
        public String getTechnology() {
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
        public int getChargePlug() {
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
        public boolean isCharge() {
            return getChargePlug() > 0;
        }

        /**
         * 是否 AC 充电方式
         * @return {@code true} yes, {@code false} no
         */
        public boolean isPluggedAC() {
            return getChargePlug() == BatteryManager.BATTERY_PLUGGED_AC;
        }

        /**
         * 是否 USB 充电方式
         * @return {@code true} yes, {@code false} no
         */
        public boolean isPluggedUSB() {
            return getChargePlug() == BatteryManager.BATTERY_PLUGGED_USB;
        }

        /**
         * 是否无线充电方式
         * @return {@code true} yes, {@code false} no
         */
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        public boolean isPluggedWireless() {
            return getChargePlug() == BatteryManager.BATTERY_PLUGGED_WIRELESS;
        }

        /**
         * 是否 DOCK 充电方式
         * @return {@code true} yes, {@code false} no
         */
        @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
        public boolean isPluggedDock() {
            return getChargePlug() == BatteryManager.BATTERY_PLUGGED_DOCK;
        }

        // ==========
        // = 充电状态 =
        // ==========

        /**
         * 获取充电状态
         * @return 充电状态
         */
        public int getChargeStatus() {
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
        public boolean isChargingStatus() {
            return getChargeStatus() == BatteryManager.BATTERY_STATUS_CHARGING;
        }

        /**
         * 是否放电状态
         * @return {@code true} yes, {@code false} no
         */
        public boolean isDisChargingStatus() {
            return getChargeStatus() == BatteryManager.BATTERY_STATUS_DISCHARGING;
        }

        /**
         * 是否充满电状态
         * @return {@code true} yes, {@code false} no
         */
        public boolean isFullStatus() {
            return getChargeStatus() == BatteryManager.BATTERY_STATUS_FULL;
        }

        /**
         * 是否不在充电状态
         * @return {@code true} yes, {@code false} no
         */
        public boolean isNotChargingStatus() {
            return getChargeStatus() == BatteryManager.BATTERY_STATUS_NOT_CHARGING;
        }

        /**
         * 是否未知充电状态
         * @return {@code true} yes, {@code false} no
         */
        public boolean isUnknownStatus() {
            return getChargeStatus() == BatteryManager.BATTERY_STATUS_UNKNOWN;
        }

        // ==============
        // = 电池健康状况 =
        // ==============

        /**
         * 获取电池健康状况
         * @return 电池健康状况
         */
        public int getHealth() {
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
        public boolean isHealthGood() {
            return getHealth() == BatteryManager.BATTERY_HEALTH_GOOD;
        }

        /**
         * 是否电池状况过热
         * @return {@code true} yes, {@code false} no
         */
        public boolean isHealthOverheat() {
            return getHealth() == BatteryManager.BATTERY_HEALTH_OVERHEAT;
        }

        /**
         * 是否电池状况低温
         * @return {@code true} yes, {@code false} no
         */
        public boolean isHealthCold() {
            return getHealth() == BatteryManager.BATTERY_HEALTH_COLD;
        }

        /**
         * 是否电池状况死机
         * @return {@code true} yes, {@code false} no
         */
        public boolean isHealthDead() {
            return getHealth() == BatteryManager.BATTERY_HEALTH_DEAD;
        }

        /**
         * 是否电池状况电压过载
         * @return {@code true} yes, {@code false} no
         */
        public boolean isHealthOverVoltage() {
            return getHealth() == BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE;
        }

        /**
         * 是否电池状况不明故障
         * @return {@code true} yes, {@code false} no
         */
        public boolean isHealthUnspecifiedFailure() {
            return getHealth() == BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE;
        }

        /**
         * 是否电池状况未知
         * @return {@code true} yes, {@code false} no
         */
        public boolean isHealthUnknown() {
            return getHealth() == BatteryManager.BATTERY_HEALTH_UNKNOWN;
        }
    }
}