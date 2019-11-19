package dev.utils.app;

import android.app.KeyguardManager;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.annotation.RequiresPermission;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: 锁屏管理工具类 ( 锁屏、禁用锁屏, 判断是否锁屏 )
 * @author Ttt
 * <pre>
 *     所需权限
 *     <uses-permission android:name="android.permission.DISABLE_KEYGUARD" />
 * </pre>
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public final class KeyguardUtils {

    // 日志 TAG
    private static final String TAG = KeyguardUtils.class.getSimpleName();
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
    // android 26 开始过时
    private KeyguardManager.KeyguardLock mKeyguardLock;

    /**
     * 构造函数
     */
    private KeyguardUtils() {
        try {
            mKeyguardManager = AppUtils.getKeyguardManager();
            // 初始化锁
            mKeyguardLock = mKeyguardManager.newKeyguardLock(TAG);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "KeyguardUtils");
        }
    }

    /**
     * 是否锁屏 - android 4.1 以上支持
     * @return {@code true} yes, {@code false} no
     */
    public boolean isKeyguardLocked() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (mKeyguardManager != null) return mKeyguardManager.isKeyguardLocked();
        }
        return false;
    }

    /**
     * 是否有锁屏密码 - android 4.1 以上支持
     * @return {@code true} yes, {@code false} no
     */
    public boolean isKeyguardSecure() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (mKeyguardManager != null) return mKeyguardManager.isKeyguardSecure();
        }
        return false;
    }

    /**
     * 是否锁屏
     * @return {@code true} yes, {@code false} no
     */
    public boolean inKeyguardRestrictedInputMode() {
        if (mKeyguardManager != null) return mKeyguardManager.inKeyguardRestrictedInputMode();
        return false;
    }

    /**
     * 获取 KeyguardManager
     * @return {@link KeyguardManager}
     */
    public KeyguardManager getKeyguardManager() {
        return mKeyguardManager;
    }

    /**
     * 设置 KeyguardManager
     * @param keyguardManager {@link KeyguardManager}
     */
    public void setKeyguardManager(final KeyguardManager keyguardManager) {
        this.mKeyguardManager = keyguardManager;
    }

    // =

    /**
     * 屏蔽系统的屏保
     * 利用 disableKeyguard 解锁, 解锁并不是真正的解锁, 只是把锁屏的界面隐藏掉而已
     */
    @RequiresPermission(android.Manifest.permission.DISABLE_KEYGUARD)
    public void disableKeyguard() {
        if (mKeyguardLock != null) {
            mKeyguardLock.disableKeyguard();
        }
    }

    /**
     * 使能显示锁屏界面, 如果你之前调用了 disableKeyguard() 方法取消锁屏界面, 那么会马上显示锁屏界面
     */
    @RequiresPermission(android.Manifest.permission.DISABLE_KEYGUARD)
    public void reenableKeyguard() {
        if (mKeyguardLock != null) {
            mKeyguardLock.reenableKeyguard();
        }
    }

    /**
     * 释放资源
     */
    @RequiresPermission(android.Manifest.permission.DISABLE_KEYGUARD)
    public void release() {
        if (mKeyguardLock != null) {
            mKeyguardLock.reenableKeyguard();
        }
    }

    // =

    /**
     * 获取 KeyguardManager.KeyguardLock
     * @return {@link KeyguardManager.KeyguardLock}
     */
    public KeyguardManager.KeyguardLock getKeyguardLock() {
        return mKeyguardLock;
    }

    /**
     * 设置 KeyguardManager.KeyguardLock
     * @param keyguardLock {@link KeyguardManager.KeyguardLock}
     */
    public void setKeyguardLock(final KeyguardManager.KeyguardLock keyguardLock) {
        this.mKeyguardLock = keyguardLock;
    }

    /**
     * 设置 KeyguardManager.KeyguardLock ( 通过 TAG 生成 )
     * @param tag TAG
     */
    public void setKeyguardLock(final String tag) {
        if (mKeyguardManager != null && tag != null) {
            this.mKeyguardLock = mKeyguardManager.newKeyguardLock(tag);
        }
    }
}