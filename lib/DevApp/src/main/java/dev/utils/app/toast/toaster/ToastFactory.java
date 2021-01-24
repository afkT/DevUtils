package dev.utils.app.toast.toaster;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;

import dev.DevUtils;
import dev.utils.LogPrintUtils;
import dev.utils.app.NotificationUtils;

/**
 * detail: Toast 工厂模式
 * @author Ttt
 */
final class ToastFactory {

    private ToastFactory() {
    }

    // 日志 TAG
    private static final String TAG = ToastFactory.class.getSimpleName();

    /**
     * detail: Toast 基类
     * @author Ttt
     */
    static class BaseToast
            extends Toast {

        // Toast 消息 View
        private TextView mMessageView;

        /**
         * 构造函数
         * @param context {@link Context}
         */
        public BaseToast(Context context) {
            super(context);
        }

        @Override
        public final void setView(View view) {
            super.setView(view);
            if (view instanceof TextView) {
                mMessageView = (TextView) view;
                return;
            } else if (view.findViewById(android.R.id.message) instanceof TextView) {
                mMessageView = view.findViewById(android.R.id.message);
                return;
            } else if (view instanceof ViewGroup) {
                if ((mMessageView = findTextView((ViewGroup) view)) != null) return;
            }
        }

        @Override
        public final void setText(CharSequence s) {
            if (mMessageView != null) {
                mMessageView.setText(s);
            }
        }

        /**
         * 递归获取 ViewGroup 中的 TextView 对象
         * @param group {@link ViewGroup}
         * @return {@link TextView}
         */
        private TextView findTextView(final ViewGroup group) {
            for (int i = 0; i < group.getChildCount(); i++) {
                View view = group.getChildAt(i);
                if ((view instanceof TextView)) {
                    return (TextView) view;
                } else if (view instanceof ViewGroup) {
                    TextView textView = findTextView((ViewGroup) view);
                    if (textView != null) return textView;
                }
            }
            return null;
        }

        /**
         * 判断是否 null 的 Message View
         * @return {@code true} yes, {@code false} no
         */
        public final boolean isEmptyMessageView() {
            return mMessageView == null;
        }

        /**
         * 获取 TextView
         * @return {@link TextView}
         */
        public TextView getMessageView() {
            return mMessageView;
        }

//        // =
//
//        /**
//         * 获取系统 Toast View
//         * @param context {@link Context}
//         * @return {@link View}
//         */
//        public static final View getToastSystemView(Context context) {
//            return Toast.makeText(context, "", Toast.LENGTH_SHORT).getView();
//        }
//
//        /**
//         * 设置系统 Toast View
//         * @param context {@link Context}
//         * @return {@link BaseToast}
//         */
//        public final BaseToast setToastSystemView(Context context) {
//            setView(getToastSystemView(context));
//            return this;
//        }
    }

    /**
     * detail: 解决 Android 7.1 Toast 崩溃问题
     * @author Ttt
     */
    static final class SafeToast
            extends BaseToast {

        /**
         * 构造函数
         * @param context {@link Context}
         */
        public SafeToast(Context context) {
            super(context);
            // 反射设置 Toast Handler 解决 Android 7.1.1 Toast 崩溃问题
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1) {
                try {
                    Field field_tn = Toast.class.getDeclaredField("mTN");
                    field_tn.setAccessible(true);

                    Object mTN           = field_tn.get(this);
                    Field  field_handler = field_tn.getType().getDeclaredField("mHandler");
                    field_handler.setAccessible(true);

                    Handler handler = (Handler) field_handler.get(mTN);
                    field_handler.set(mTN, new SafeHandler(handler));
                } catch (Exception ignore) {
                }
            }
        }

        /**
         * detail: Toast 安全显示 Handler
         * @author Ttt
         */
        static final class SafeHandler
                extends Handler {

            private final Handler mHandler;

            SafeHandler(Handler handler) {
                mHandler = handler;
            }

            @Override
            public void handleMessage(Message msg) {
                mHandler.handleMessage(msg);
            }

            @Override
            public void dispatchMessage(Message msg) {
                try {
                    mHandler.dispatchMessage(msg);
                } catch (Exception ignore) {
                }
            }
        }
    }

    /**
     * detail: 通知栏显示 Toast
     * @author Ttt
     */
    static final class NotificationToast
            extends BaseToast {

        // Toast Window 显示辅助类
        private final ToastHelper mToastHelper;

        /**
         * 构造函数
         * @param context {@link Context}
         */
        public NotificationToast(Context context) {
            super(context);
            // 初始化操作
            mToastHelper = new ToastHelper(this);
        }

        @Override
        public void show() {
            // 显示 Toast
            mToastHelper.show();
        }

        @Override
        public void cancel() {
            // 取消显示
            mToastHelper.cancel();
        }
    }

    // =

    /**
     * 创建 Toast
     * @param context {@link Context}
     * @return {@link BaseToast}
     */
    public static BaseToast create(final Context context) {
        if (NotificationUtils.isNotificationEnabled()) {
            return new SafeToast(context);
        }
        return new NotificationToast(context);
    }

    // =

    /**
     * detail: Toast Window 显示辅助类
     * @author Ttt
     * <pre>
     *     参考 Toast.TN 实现方式
     * </pre>
     */
    static final class ToastHelper
            extends Handler {

        // 当前 Toast 对象
        private final Toast   mToast;
        // 判断是否显示中
        private       boolean mShow;

        ToastHelper(Toast toast) {
            super(Looper.getMainLooper());
            mToast = toast;
        }

        @Override
        public void handleMessage(Message msg) {
            cancel();
        }

        /***
         * 显示 Toast 弹窗
         */
        void show() {
            if (!mShow) {
                try {
                    if (mToast == null) {
                        return;
                    }
                    // 获取 View
                    View view = mToast.getView();
                    // 防止 View 为 null
                    if (view == null) {
                        return;
                    }
                    // 获取 Context
                    Context context = view.getContext().getApplicationContext();
                    if (context == null) {
                        context = view.getContext();
                    }
                    // 获取包名
                    String packageName = context.getPackageName();

                    // =

                    WindowManager.LayoutParams params = new WindowManager.LayoutParams();
                    // 设置参数
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N_MR1) {
                        params.type = WindowManager.LayoutParams.TYPE_TOAST;
                    } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1) {
                        params.type = WindowManager.LayoutParams.LAST_APPLICATION_WINDOW;
                    } else {
                        params.type = WindowManager.LayoutParams.FIRST_SYSTEM_WINDOW + 37;
                    }

                    params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    params.width = WindowManager.LayoutParams.WRAP_CONTENT;
                    params.format = PixelFormat.TRANSLUCENT;
                    params.windowAnimations = android.R.style.Animation_Toast;
                    params.setTitle(Toast.class.getSimpleName());
                    params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                            | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                            | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;

                    // Toast 的重心
                    int gravity = mToast.getGravity();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        Configuration config = context.getResources().getConfiguration();
                        gravity = Gravity.getAbsoluteGravity(gravity, config.getLayoutDirection());
                    }
                    if (gravity != 0) {
                        params.gravity = gravity;
                        // 判断是否铺满整个方向
                        if ((gravity & Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.FILL_HORIZONTAL) {
                            params.horizontalWeight = 1.0f;
                        }
                        if ((gravity & Gravity.VERTICAL_GRAVITY_MASK) == Gravity.FILL_VERTICAL) {
                            params.verticalWeight = 1.0f;
                        }
                    }
                    // 设置 X、Y 轴偏移
                    params.x = mToast.getXOffset();
                    params.y = mToast.getYOffset();
                    // 设置水平边距、垂直边距
                    params.verticalMargin = mToast.getVerticalMargin();
                    params.horizontalMargin = mToast.getHorizontalMargin();
                    // 设置包名
                    params.packageName = packageName;

                    // View 对象不能重复添加, 否则会抛出异常
                    getWindowManager(DevUtils.getTopActivity()).addView(mToast.getView(), params);
                    // 当前已经显示
                    mShow = true;
                    // 添加一个移除 Toast 的任务
                    sendEmptyMessageDelayed(0, mToast.getDuration() == Toast.LENGTH_LONG ? 3500 : 2000);
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "ToastHelper - show");
                }
            }
        }

        /**
         * 取消 Toast 弹窗
         */
        void cancel() {
            // 移除之前移除 Toast 的任务
            removeMessages(0);
            // 如果显示中, 则移除 View
            if (mShow) {
                try {
                    getWindowManager(DevUtils.getTopActivity()).removeView(mToast.getView());
                } catch (Exception ignore) {
                }
                // 当前没有显示
                mShow = false;
            }
        }
    }

    // =

    /**
     * 获取一个 WindowManager 对象
     * @param activity {@link Activity}
     * @return {@link WindowManager}
     */
    private static WindowManager getWindowManager(final Activity activity) {
        // 如果使用的 WindowManager 对象不是当前 Activity 创建的, 则会抛出异常
        // android.view.WindowManager$BadTokenException: Unable to add window - token null is not for an application
        if (activity != null) {
            try {
                return ((WindowManager) activity.getSystemService(Context.WINDOW_SERVICE));
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getWindowManager");
            }
        }
        return null;
    }
}