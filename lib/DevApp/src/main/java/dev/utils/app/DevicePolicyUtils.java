package dev.utils.app;

import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.text.TextUtils;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: 设备管理工具类
 * @author Ttt
 */
public final class DevicePolicyUtils {

    private DevicePolicyUtils() {
    }

    // 日志 TAG
    private static final String TAG = DevicePolicyUtils.class.getSimpleName();

    // DevicePolicyUtils 实例
    private static volatile DevicePolicyUtils sInstance;

    /**
     * 获取 DevicePolicyUtils 实例
     * @return {@link DevicePolicyUtils}
     */
    public static DevicePolicyUtils getInstance() {
        if (sInstance == null) {
            synchronized (DevicePolicyUtils.class) {
                if (sInstance == null) {
                    sInstance = new DevicePolicyUtils();
                }
            }
        }
        return sInstance;
    }

    // 设备策略管理器
    private DevicePolicyManager mDevicePolicyManager;

    /**
     * 获取 DevicePolicyManager
     * @return {@link DevicePolicyManager}
     */
    public DevicePolicyManager getDevicePolicyManager() {
        if (mDevicePolicyManager == null) {
            mDevicePolicyManager = AppUtils.getDevicePolicyManager();
        }
        return mDevicePolicyManager;
    }

    // =================
    // = ComponentName =
    // =================

    /**
     * 判断给定的组件是否启动 ( 活跃 ) 中
     * @param admin {@link ComponentName}
     * @return {@code true} yes, {@code false} no
     */
    public boolean isAdminActive(final ComponentName admin) {
        if (admin == null) return false;
        try {
            return getDevicePolicyManager().isAdminActive(admin);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "isAdminActive");
        }
        return false;
    }

    /**
     * 获取激活跳转 Intent
     * @param admin {@link ComponentName}
     * @param tips  提示文案
     * @return {@link Intent}
     */
    public Intent getActiveIntent(
            final ComponentName admin,
            final String tips
    ) {
        if (admin == null || TextUtils.isEmpty(tips)) return null;
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, admin);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, tips);
        return intent;
    }

    /**
     * 激活给定的组件
     * @param admin {@link ComponentName}
     * @param tips  提示文案
     * @return {@code true} success, {@code false} fail
     */
    public boolean activeAdmin(
            final ComponentName admin,
            final String tips
    ) {
        if (admin == null) return false;
        if (TextUtils.isEmpty(tips)) return false;
        if (isAdminActive(admin)) return true;
        return AppUtils.startActivity(getActiveIntent(admin, tips));
    }

    /**
     * 移除激活组件
     * @param admin {@link ComponentName}
     * @return {@code true} success, {@code false} fail
     */
    public boolean removeActiveAdmin(final ComponentName admin) {
        try {
            getDevicePolicyManager().removeActiveAdmin(admin);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "removeActiveAdmin");
        }
        return false;
    }

    /**
     * 设置锁屏密码 ( 不需要激活就可以运行 )
     * @return {@code true} success, {@code false} fail
     */
    public boolean startLockPassword() {
        return AppUtils.startActivity(new Intent(DevicePolicyManager.ACTION_SET_NEW_PASSWORD));
    }

    /**
     * 设置锁屏密码
     * <pre>
     *     用户输入的密码必须要有字母 ( 或者其他字符 )
     *     {@link DevicePolicyManager#PASSWORD_QUALITY_ALPHABETIC}
     *     用户输入的密码必须要有字母和数字
     *     {@link DevicePolicyManager#PASSWORD_QUALITY_ALPHANUMERIC}
     *     用户输入的密码必须要有数字
     *     {@link DevicePolicyManager#PASSWORD_QUALITY_NUMERIC}
     *     由设计人员决定的
     *     {@link DevicePolicyManager#PASSWORD_QUALITY_SOMETHING}
     *     对密码没有要求
     *     {@link DevicePolicyManager#PASSWORD_QUALITY_UNSPECIFIED}
     *     ....
     * </pre>
     * @param admin        {@link ComponentName}
     * @param passwordType 密码类型
     * @return {@code true} success, {@code false} fail
     */
    public boolean setLockPassword(
            final ComponentName admin,
            final int passwordType
    ) {
        if (!isAdminActive(admin)) return false;
        try {
            Intent intent = new Intent(DevicePolicyManager.ACTION_SET_NEW_PASSWORD);
            getDevicePolicyManager().setPasswordQuality(admin, passwordType);
            return AppUtils.startActivity(intent);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "setLockPassword");
        }
        return false;
    }

    /**
     * 立刻锁屏
     * @param admin {@link ComponentName}
     * @return {@code true} success, {@code false} fail
     */
    public boolean lockNow(final ComponentName admin) {
        if (!isAdminActive(admin)) return false;
        try {
            getDevicePolicyManager().lockNow();
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "lockNow");
        }
        return false;
    }

    /**
     * 设置多长时间后锁屏
     * @param admin       {@link ComponentName}
     * @param delayMillis 延时执行时间
     * @return {@code true} success, {@code false} fail
     */
    public boolean lockByTime(
            final ComponentName admin,
            final long delayMillis
    ) {
        if (!isAdminActive(admin)) return false;
        try {
            getDevicePolicyManager().setMaximumTimeToLock(admin, delayMillis);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "lockByTime");
        }
        return false;
    }

    /**
     * 清除所有数据 ( 恢复出厂设置 )
     * @param admin {@link ComponentName}
     * @return {@code true} success, {@code false} fail
     */
    public boolean wipeData(final ComponentName admin) {
        if (!isAdminActive(admin)) return false;
        try {
            getDevicePolicyManager().wipeData(DevicePolicyManager.WIPE_EXTERNAL_STORAGE);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "wipeData");
        }
        return false;
    }

    /**
     * 设置新解锁密码
     * @param admin    {@link ComponentName}
     * @param password 密码
     * @return {@code true} success, {@code false} fail
     */
    public boolean resetPassword(
            final ComponentName admin,
            final String password
    ) {
        if (!isAdminActive(admin)) return false;
        try {
            return getDevicePolicyManager().resetPassword(password,
                    DevicePolicyManager.RESET_PASSWORD_REQUIRE_ENTRY);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "resetPassword");
        }
        return false;
    }

    /**
     * 设置存储设备加密
     * @param admin   {@link ComponentName}
     * @param encrypt 是否加密
     * @return {@code true} success, {@code false} fail
     */
    public boolean setStorageEncryption(
            final ComponentName admin,
            final boolean encrypt
    ) {
        if (!isAdminActive(admin)) return false;
        try {
            getDevicePolicyManager().setStorageEncryption(admin, encrypt);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "setStorageEncryption");
        }
        return false;
    }

    /**
     * 设置停用相机
     * @param admin    {@link ComponentName}
     * @param disabled 是否禁用相机
     * @return {@code true} success, {@code false} fail
     */
    public boolean setCameraDisabled(
            final ComponentName admin,
            final boolean disabled
    ) {
        if (!isAdminActive(admin)) return false;
        try {
            getDevicePolicyManager().setCameraDisabled(admin, disabled);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "setCameraDisabled");
        }
        return false;
    }

    // =========================
    // = Default ComponentName =
    // =========================

    // ComponentName
    private ComponentName mComponentName;

    /**
     * 获取 ComponentName
     * @return {@link ComponentName}
     */
    public ComponentName getComponentName() {
        return mComponentName;
    }

    /**
     * 设置 ComponentName
     * @param admin {@link ComponentName}
     * @return {@link DevicePolicyUtils}
     */
    public DevicePolicyUtils setComponentName(final ComponentName admin) {
        this.mComponentName = admin;
        return this;
    }

    /**
     * 设置 ComponentName
     * @param clazz 继承 {@link DeviceAdminReceiver}
     * @return {@link DevicePolicyUtils}
     */
    public DevicePolicyUtils setComponentName(final Class clazz) {
        try {
            setComponentName(new ComponentName(DevUtils.getContext(), clazz));
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "setComponentName");
        }
        return this;
    }

    // =

    /**
     * 判断给定的组件是否启动 ( 活跃 ) 中
     * @return {@code true} yes, {@code false} no
     */
    public boolean isAdminActive() {
        return isAdminActive(mComponentName);
    }

    /**
     * 获取激活跳转 Intent
     * @param tips 提示文案
     * @return {@link Intent}
     */
    public Intent getActiveIntent(final String tips) {
        return getActiveIntent(mComponentName, tips);
    }

    /**
     * 激活给定的组件
     * @param tips 提示文案
     * @return {@code true} success, {@code false} fail
     */
    public boolean activeAdmin(final String tips) {
        return activeAdmin(mComponentName, tips);
    }

    /**
     * 移除激活组件
     * @return {@code true} success, {@code false} fail
     */
    public boolean removeActiveAdmin() {
        return removeActiveAdmin(mComponentName);
    }

    /**
     * 设置锁屏密码
     * @param passwordType 密码类型
     * @return {@code true} success, {@code false} fail
     */
    public boolean setLockPassword(final int passwordType) {
        return setLockPassword(mComponentName, passwordType);
    }

    /**
     * 立刻锁屏
     * @return {@code true} success, {@code false} fail
     */
    public boolean lockNow() {
        return lockNow(mComponentName);
    }

    /**
     * 设置多长时间后锁屏
     * @param delayMillis 延时执行时间
     * @return {@code true} success, {@code false} fail
     */
    public boolean lockByTime(final long delayMillis) {
        return lockByTime(mComponentName, delayMillis);
    }

    /**
     * 清除所有数据 ( 恢复出厂设置 )
     * @return {@code true} success, {@code false} fail
     */
    public boolean wipeData() {
        return wipeData(mComponentName);
    }

    /**
     * 设置新解锁密码
     * @param password 密码
     * @return {@code true} success, {@code false} fail
     */
    public boolean resetPassword(final String password) {
        return resetPassword(mComponentName, password);
    }

    /**
     * 设置存储设备加密
     * @param encrypt 是否加密
     * @return {@code true} success, {@code false} fail
     */
    public boolean setStorageEncryption(final boolean encrypt) {
        return setStorageEncryption(mComponentName, encrypt);
    }

    /**
     * 设置停用相机
     * @param disabled 是否禁用相机
     * @return {@code true} success, {@code false} fail
     */
    public boolean setCameraDisabled(final boolean disabled) {
        return setCameraDisabled(mComponentName, disabled);
    }
}