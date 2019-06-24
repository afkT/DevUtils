package dev.utils.app;

import android.app.KeyguardManager;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.annotation.RequiresPermission;

import dev.DevUtils;

/**
 * detail: 锁屏管理工具类(锁屏、禁用锁屏, 判断是否锁屏)
 * @author Ttt
 * <pre>
 *     需要的权限:
 *     <uses-permission android:name="android.permission.DISABLE_KEYGUARD"/>
 * </pre>
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public final class KeyguardUtils {

    // KeyguardUtils 实例
    private static KeyguardUtils sInstance;

    /**
     * 获取 KeyguardUtils 实例
     * @return {@link KeyguardUtils}
     */
    public static KeyguardUtils getInstance() {
        if (sInstance == null) {
            sInstance = new KeyguardUtils();
        }
        return sInstance;
    }

    // 锁屏管理类
    private KeyguardManager mKeyguardManager;
    // android-26 开始过时
    private KeyguardManager.KeyguardLock mKeyguardLock;

    /**
     * 构造函数
     */
    private KeyguardUtils() {
        // 获取系统服务
        mKeyguardManager = (KeyguardManager) DevUtils.getContext().getSystemService(Context.KEYGUARD_SERVICE);
        // 初始化锁
        mKeyguardLock = mKeyguardManager.newKeyguardLock("KeyguardUtils");
    }

    /**
     * 是否锁屏 - android 4.1以上支持
     * @return
     */
    public boolean isKeyguardLocked() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            return false;
        } else {
            return mKeyguardManager.isKeyguardLocked();
        }
    }

    /**
     * 是否有锁屏密码 - android 4.1以上支持
     * @return
     */
    public boolean isKeyguardSecure() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            return false;
        } else {
            return mKeyguardManager.isKeyguardSecure();
        }
    }

    /**
     * 是否锁屏 - android 4.1 之前
     * @return
     */
    public boolean inKeyguardRestrictedInputMode() {
        return mKeyguardManager.inKeyguardRestrictedInputMode();
    }

    /**
     * 获取 KeyguardManager
     * @return
     */
    public KeyguardManager getKeyguardManager() {
        return mKeyguardManager;
    }

    /**
     * 设置 KeyguardManager
     * @param keyguardManager
     */
    public void setKeyguardManager(final KeyguardManager keyguardManager) {
        this.mKeyguardManager = keyguardManager;
    }

    // =

    /**
     * 屏蔽系统的屏保
     * 利用 disableKeyguard 解锁, 解锁并不是真正的解锁, 只是把锁屏的界面隐藏掉而已
     * <uses-permission android:name="android.permission.DISABLE_KEYGUARD"/>
     */
    @RequiresPermission(android.Manifest.permission.DISABLE_KEYGUARD)
    public void disableKeyguard() {
        mKeyguardLock.disableKeyguard();
    }

    /**
     * 使能显示锁屏界面, 如果你之前调用了disableKeyguard()方法取消锁屏界面, 那么会马上显示锁屏界面
     * <uses-permission android:name="android.permission.DISABLE_KEYGUARD"/>
     */
    @RequiresPermission(android.Manifest.permission.DISABLE_KEYGUARD)
    public void reenableKeyguard() {
        mKeyguardLock.reenableKeyguard();
    }

    /**
     * 释放资源
     * <uses-permission android:name="android.permission.DISABLE_KEYGUARD"/>
     */
    @RequiresPermission(android.Manifest.permission.DISABLE_KEYGUARD)
    public void release() {
        if (mKeyguardLock != null) {
            // 禁用显示键盘锁定
            mKeyguardLock.reenableKeyguard();
        }
    }

    /**
     * 生成一个新的 KeyguardLock, 并且设置 TAG
     * @param tag
     */
    public void newKeyguardLock(final String tag) {
        mKeyguardLock = mKeyguardManager.newKeyguardLock(tag);
    }

    /**
     * 获取 KeyguardManager.KeyguardLock
     * @return
     */
    public KeyguardManager.KeyguardLock getKeyguardLock() {
        return mKeyguardLock;
    }

    /**
     * 设置 KeyguardManager.KeyguardLock
     * @param keyguardLock
     */
    public void setKeyguardLock(final KeyguardManager.KeyguardLock keyguardLock) {
        this.mKeyguardLock = keyguardLock;
    }
}
