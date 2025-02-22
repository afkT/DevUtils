package dev.utils.app;

import android.Manifest;
import android.app.KeyguardManager;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;

import dev.utils.LogPrintUtils;

/**
 * detail: 锁屏管理工具类 ( 锁屏、禁用锁屏, 判断是否锁屏 )
 * @author Ttt
 * <pre>
 *     所需权限
 *     <uses-permission android:name="android.permission.DISABLE_KEYGUARD"/>
 * </pre>
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public final class KeyguardUtils {

    private KeyguardUtils() {
    }

    // 日志 TAG
    private static final String TAG = KeyguardUtils.class.getSimpleName();

    // KeyguardUtils 实例
    private static volatile KeyguardUtils sInstance;

    /**
     * 获取 KeyguardUtils 实例
     * @return {@link KeyguardUtils}
     */
    public static KeyguardUtils getInstance() {
        if (sInstance == null) {
            synchronized (KeyguardUtils.class) {
                if (sInstance == null) {
                    sInstance = new KeyguardUtils();
                }
            }
        }
        return sInstance;
    }

    /**
     * 是否锁屏 ( android 4.1 以上支持 )
     * @return {@code true} yes, {@code false} no
     */
    public boolean isKeyguardLocked() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            try {
                return AppUtils.getKeyguardManager().isKeyguardLocked();
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "isKeyguardLocked");
            }
        }
        return false;
    }

    /**
     * 是否有锁屏密码 ( android 4.1 以上支持 )
     * @return {@code true} yes, {@code false} no
     */
    public boolean isKeyguardSecure() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            try {
                return AppUtils.getKeyguardManager().isKeyguardSecure();
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "isKeyguardSecure");
            }
        }
        return false;
    }

    /**
     * 是否锁屏
     * @return {@code true} yes, {@code false} no
     */
    public boolean inKeyguardRestrictedInputMode() {
        try {
            return AppUtils.getKeyguardManager().inKeyguardRestrictedInputMode();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "inKeyguardRestrictedInputMode");
        }
        return false;
    }

    // =

    /**
     * 创建 KeyguardManager.KeyguardLock ( 通过 TAG 生成 )
     * @param tag TAG
     * @return {@link KeyguardManager.KeyguardLock}
     */
    public KeyguardManager.KeyguardLock newKeyguardLock(final String tag) {
        KeyguardManager keyguardManager = AppUtils.getKeyguardManager();
        if (keyguardManager != null && tag != null) {
            try {
                return keyguardManager.newKeyguardLock(tag);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "newKeyguardLock");
            }
        }
        return null;
    }

    /**
     * 屏蔽系统的屏保
     * 利用 disableKeyguard 解锁, 解锁并不是真正的解锁, 只是把锁屏的界面隐藏掉而已
     * @param keyguardLock {@link KeyguardManager.KeyguardLock}
     * @return {@code true} success, {@code false} fail
     */
    @RequiresPermission(Manifest.permission.DISABLE_KEYGUARD)
    public boolean disableKeyguard(final KeyguardManager.KeyguardLock keyguardLock) {
        if (keyguardLock != null) {
            try {
                keyguardLock.disableKeyguard();
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "disableKeyguard");
            }
        }
        return false;
    }

    /**
     * 使能显示锁屏界面, 如果你之前调用了 disableKeyguard() 方法取消锁屏界面, 那么会马上显示锁屏界面
     * @param keyguardLock {@link KeyguardManager.KeyguardLock}
     * @return {@code true} success, {@code false} fail
     */
    @RequiresPermission(Manifest.permission.DISABLE_KEYGUARD)
    public boolean reenableKeyguard(final KeyguardManager.KeyguardLock keyguardLock) {
        if (keyguardLock != null) {
            try {
                keyguardLock.reenableKeyguard();
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "reenableKeyguard");
            }
        }
        return false;
    }

    // ========================
    // = Default KeyguardLock =
    // ========================

    // android 26 开始过时
    private KeyguardManager.KeyguardLock mKeyguardLock;

    /**
     * 获取默认 KeyguardManager.KeyguardLock
     * @return {@link KeyguardManager.KeyguardLock}
     */
    public KeyguardManager.KeyguardLock getDefaultKeyguardLock() {
        if (mKeyguardLock != null) return mKeyguardLock;
        mKeyguardLock = newKeyguardLock(TAG);
        return mKeyguardLock;
    }

    /**
     * 设置默认 KeyguardManager.KeyguardLock
     * @param keyguardLock {@link KeyguardManager.KeyguardLock}
     * @return {@link KeyguardUtils}
     */
    public KeyguardUtils setDefaultKeyguardLock(final KeyguardManager.KeyguardLock keyguardLock) {
        this.mKeyguardLock = keyguardLock;
        return this;
    }

    /**
     * 屏蔽系统的屏保
     * 利用 disableKeyguard 解锁, 解锁并不是真正的解锁, 只是把锁屏的界面隐藏掉而已
     * @return {@code true} success, {@code false} fail
     */
    @RequiresPermission(Manifest.permission.DISABLE_KEYGUARD)
    public boolean disableKeyguard() {
        return disableKeyguard(getDefaultKeyguardLock());
    }

    /**
     * 使能显示锁屏界面, 如果你之前调用了 disableKeyguard() 方法取消锁屏界面, 那么会马上显示锁屏界面
     * @return {@code true} success, {@code false} fail
     */
    @RequiresPermission(Manifest.permission.DISABLE_KEYGUARD)
    public boolean reenableKeyguard() {
        return reenableKeyguard(getDefaultKeyguardLock());
    }
}