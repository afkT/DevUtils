package dev.utils.app;

import android.app.KeyguardManager;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import dev.DevUtils;

/**
 * detail: 锁屏工具类 - 锁屏管理， 锁屏、禁用锁屏，判断是否锁屏
 * Created by Ttt
 * <uses-permission android:name="android.permission.DISABLE_KEYGUARD"/>
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public final class KeyguardUtils {

    // KeyguardUtils 实例
    private static KeyguardUtils INSTANCE;

    /** 获取 KeyguardUtils 实例 ,单例模式 */
    public static KeyguardUtils getInstance() {
        if (INSTANCE == null){
            INSTANCE = new KeyguardUtils();
        }
        return INSTANCE;
    }

    // 锁屏管理类
    KeyguardManager keyguardManager;
    // android-26 开始过时
    KeyguardManager.KeyguardLock keyguardLock;

    /** 构造函数 */
    private KeyguardUtils() {
        // 获取系统服务
        keyguardManager = (KeyguardManager) DevUtils.getContext().getSystemService(Context.KEYGUARD_SERVICE);
        // 初始化锁
        keyguardLock = keyguardManager.newKeyguardLock("KeyguardUtils");
    }

    /**
     * 是否锁屏 - android 4.1以上支持
     * @return
     */
    public boolean isKeyguardLocked() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            return false;
        } else {
            return keyguardManager.isKeyguardLocked();
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
            return keyguardManager.isKeyguardSecure();
        }
    }

    /**
     * 是否锁屏 - android 4.1 之前
     * @return
     */
    public boolean inKeyguardRestrictedInputMode() {
        return keyguardManager.inKeyguardRestrictedInputMode();
    }

    public KeyguardManager getKeyguardManager() {
        return keyguardManager;
    }

    public void setKeyguardManager(KeyguardManager keyguardManager) {
        this.keyguardManager = keyguardManager;
    }

    // ==

    /**
     * 屏蔽系统的屏保
     * 利用 disableKeyguard 解锁, 解锁并不是真正的解锁, 只是把锁屏的界面隐藏掉而已
     */
    public void disableKeyguard() {
        keyguardLock.disableKeyguard();
    }

    /**
     * 使能显示锁屏界面，如果你之前调用了disableKeyguard()方法取消锁屏界面，那么会马上显示锁屏界面。
     */
    public void reenableKeyguard() {
        keyguardLock.reenableKeyguard();
    }

    /**
     * 释放资源
     */
    public void release() {
        if (keyguardLock != null) {
            // 禁用显示键盘锁定
            keyguardLock.reenableKeyguard();
        }
    }

    public void newKeyguardLock (String tag){
        keyguardLock = keyguardManager.newKeyguardLock(tag);
    }

    public KeyguardManager.KeyguardLock getKeyguardLock() {
        return keyguardLock;
    }

    public void setKeyguardLock(KeyguardManager.KeyguardLock keyguardLock) {
        this.keyguardLock = keyguardLock;
    }
}
