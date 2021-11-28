package dev.utils.app.assist;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.provider.Settings;
import android.view.Gravity;
import android.view.WindowManager;

import dev.utils.app.AppUtils;
import dev.utils.app.IntentUtils;

/**
 * detail: 悬浮窗管理辅助类
 * @author Ttt
 * <pre>
 *     所需权限
 *     <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW"/>
 * </pre>
 */
public final class FloatWindowManagerAssist {

    // 请求 Code
    public static final int REQUEST_CODE = 112233;

    // 悬浮窗管理辅助类实现
    private AssistIMPL IMPL;

    public FloatWindowManagerAssist() {
        this(new DevAssistIMPL());
    }

    public FloatWindowManagerAssist(final AssistIMPL impl) {
        this.IMPL = impl;
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 获取悬浮窗管理辅助类实现
     * @return {@link AssistIMPL}
     */
    public AssistIMPL getIMPL() {
        return IMPL;
    }

    /**
     * 是否存在悬浮窗权限
     * @param context {@link Context}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean canDrawOverlays(final Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context == null) return false;
            return Settings.canDrawOverlays(context);
        }
        return true;
    }

    /**
     * 检测是否存在悬浮窗权限并跳转
     * @param activity {@link Activity}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean checkOverlayPermission(final Activity activity) {
        return checkOverlayPermission(activity, false);
    }

    /**
     * 检测是否存在悬浮窗权限并跳转
     * @param activity {@link Activity}
     * @param start    是否跳转权限开启页面
     * @return {@code true} yes, {@code false} no
     */
    public static boolean checkOverlayPermission(
            final Activity activity,
            final boolean start
    ) {
        if (canDrawOverlays(activity)) return true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (start) {
                AppUtils.startActivityForResult(
                        activity, IntentUtils.getManageOverlayPermissionIntent(),
                        REQUEST_CODE
                );
            }
        }
        return false;
    }

    /**
     * 是否悬浮窗请求回调 code
     * @param requestCode 请求 code
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isOverlayRequestCode(final int requestCode) {
        return requestCode == REQUEST_CODE;
    }

    // =======
    // = 接口 =
    // =======

    /**
     * detail: 悬浮窗管理辅助类实现
     * @author Ttt
     * <pre>
     *     防止后续版本适配等差异化预留
     * </pre>
     */
    public interface AssistIMPL {

        /**
         * 获取悬浮窗管理类
         * @return {@link WindowManager}
         */
        WindowManager getWindowManager();

        /**
         * 获取添加到悬浮窗 LayoutParams
         * @return {@link WindowManager.LayoutParams}
         */
        WindowManager.LayoutParams getLayoutParams();
    }

    // =

    /**
     * detail: DevApp 悬浮窗管理辅助类实现
     * @author Ttt
     */
    public static class DevAssistIMPL
            implements AssistIMPL {

        // Window Manager
        private WindowManager              mWindowManager;
        // Window Layout Params
        private WindowManager.LayoutParams mLayoutParams;

        @Override
        public WindowManager getWindowManager() {
            if (mWindowManager == null) {
                mWindowManager = AppUtils.getWindowManager();
            }
            return mWindowManager;
        }

        @Override
        public WindowManager.LayoutParams getLayoutParams() {
            if (mLayoutParams == null) {
                WindowManager.LayoutParams params = new WindowManager.LayoutParams();
                params.x       = 0;
                params.y       = 0;
                params.width   = WindowManager.LayoutParams.WRAP_CONTENT;
                params.height  = WindowManager.LayoutParams.WRAP_CONTENT;
                params.gravity = Gravity.LEFT | Gravity.TOP;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
                } else {
                    params.type = WindowManager.LayoutParams.TYPE_PHONE;
                }
                params.format = PixelFormat.RGBA_8888;
                params.flags  = (
                        WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                );
                mLayoutParams = params;
            }
            return mLayoutParams;
        }
    }
}