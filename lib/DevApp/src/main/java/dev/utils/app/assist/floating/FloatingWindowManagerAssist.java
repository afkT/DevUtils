package dev.utils.app.assist.floating;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import dev.utils.LogPrintUtils;
import dev.utils.app.AppUtils;
import dev.utils.app.IntentUtils;

/**
 * detail: 悬浮窗管理辅助类 ( 需权限 )
 * @author Ttt
 * <pre>
 *     所需权限
 *     <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW"/>
 * </pre>
 */
public final class FloatingWindowManagerAssist {

    // 日志 TAG
    private final       String TAG          = FloatingWindowManagerAssist.class.getSimpleName();
    // 请求 Code
    public static final int    REQUEST_CODE = 112233;

    // 悬浮窗管理辅助类实现
    private final AssistIMPL IMPL;

    public FloatingWindowManagerAssist() {
        this(new DevAssistIMPL());
    }

    public FloatingWindowManagerAssist(final AssistIMPL impl) {
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
     * 获取 WindowManager
     * @return {@link WindowManager}
     */
    public WindowManager getWindowManager() {
        if (IMPL != null) return IMPL.getWindowManager();
        return null;
    }

    /**
     * 获取 Window LayoutParams
     * @return {@link WindowManager.LayoutParams}
     */
    public WindowManager.LayoutParams getLayoutParams() {
        if (IMPL != null) return IMPL.getLayoutParams();
        return null;
    }

    // =

    /**
     * 添加悬浮 View
     * @param view {@link View}
     * @return {@code true} success, {@code false} fail
     */
    public boolean addView(final View view) {
        return addView(view, getLayoutParams());
    }

    /**
     * 添加悬浮 View
     * @param view   {@link View}
     * @param params Window LayoutParams
     * @return {@code true} success, {@code false} fail
     */
    public boolean addView(
            final View view,
            final WindowManager.LayoutParams params
    ) {
        if (view == null || params == null) return false;
        WindowManager windowManager = getWindowManager();
        if (windowManager != null) {
            try {
                view.setLayoutParams(params);
                windowManager.addView(view, params);
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "addView");
            }
        }
        return false;
    }

    /**
     * 移除悬浮 View
     * @param view {@link View}
     * @return {@code true} success, {@code false} fail
     */
    public boolean removeView(final View view) {
        if (view == null) return false;
        WindowManager windowManager = getWindowManager();
        if (windowManager != null) {
            try {
                windowManager.removeView(view);
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "removeView");
            }
        }
        return false;
    }

    /**
     * 更新 View Layout
     * @param view   {@link View}
     * @param params Window LayoutParams
     * @return {@code true} success, {@code false} fail
     */
    public boolean updateViewLayout(
            final View view,
            final ViewGroup.LayoutParams params
    ) {
        if (view == null || params == null) return false;
        WindowManager windowManager = getWindowManager();
        if (windowManager != null) {
            try {
                windowManager.updateViewLayout(view, params);
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "updateViewLayout");
            }
        }
        return false;
    }

    /**
     * 更新 View Layout
     * @param view {@link View}
     * @param dx   累加 X 轴坐标
     * @param dy   累加 Y 轴坐标
     * @return {@code true} success, {@code false} fail
     */
    public boolean updateViewLayout(
            final View view,
            final int dx,
            final int dy
    ) {
        if (view != null) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams instanceof WindowManager.LayoutParams) {
                WindowManager.LayoutParams params = (WindowManager.LayoutParams) layoutParams;
                params.x += dx;
                params.y += dy;
                updateViewLayout(view, params);
                return true;
            }
        }
        return false;
    }

    // ==========
    // = 静态方法 =
    // ==========

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