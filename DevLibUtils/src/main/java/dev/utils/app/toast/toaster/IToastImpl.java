package dev.utils.app.toast.toaster;

import android.app.Application;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: Toast 接口实现方法 (处理方法)
 * @author Ttt
 */
final class IToastImpl implements IToast.Operate, IToast.Filter {

    // 日志 TAG
    private final String TAG = IToastImpl.class.getSimpleName();
    // Application
    private Application mApplication;
    // 内部保存配置 Toast
    private ToastFactory.BaseToast sConfigToast = null;
    // 当前显示的 Toast
    private ToastFactory.BaseToast mToast = null;
    // Toast 样式信息
    private IToast.Style sToastStyle = null;
    // Toast 判断过滤
    private IToast.Filter sToastFilter = null;
    // Toast 默认样式
    private final IToast.Style sDefaultStyle = new DefaultToastStyle();
    // 每个线程的 Toast 样式
    private final ThreadLocal<IToast.Style> LOCAL_TOAST_STYLES = new ThreadLocal<>();
    // 判断是否使用 Handler
    private boolean mIsHandler = true;
    // 内部 Handler
    private final Handler sHandler = new Handler(Looper.getMainLooper());
    // Null 值
    private String mNullText = "text is null";
    // Toast 文案长度转换 显示时间
    private int mTextLengthConvertDuration = 15;

    /**
     * 重置默认参数
     */
    @Override
    public void reset() {
        // 重新初始化
        init(mApplication);
    }

    /**
     * 设置是否使用 Handler 显示 Toast
     * @param isHandler
     */
    @Override
    public void setIsHandler(final boolean isHandler) {
        this.mIsHandler = isHandler;
    }

    /**
     * 设置 Text 为 null 的文本
     * @param nullText
     */
    @Override
    public void setNullText(final String nullText) {
        this.mNullText = nullText;
    }

    /**
     * 设置 Toast 文案长度转换 显示时间
     * @param textLengthConvertDuration
     */
    @Override
    public void setTextLength(final int textLengthConvertDuration) {
        this.mTextLengthConvertDuration = textLengthConvertDuration;
    }

    // ========================
    // = Application 中初始化 =
    // ========================

    /**
     * Application 初始化调用
     * @param application
     */
    @Override
    public void init(final Application application) {
        if (application != null) {
            this.mApplication = application;
            // 初始化默认参数
            mIsHandler = true;
            mNullText = "text is null";
            // 初始化 Toast
            sConfigToast = new ToastFactory.BaseToast(mApplication);
            sConfigToast.setView(createView());
            // 初始化默认样式
            getToastStyle();
        }
    }

    // ===============================
    // = 实现IToast接口,对外公开方法 =
    // ===============================

    /**
     * 使用单次 Toast 样式配置
     * @param toastStyle Toast 样式
     * @return
     */
    @Override
    public IToast.Operate style(final IToast.Style toastStyle) {
        if (toastStyle != null) {
            LOCAL_TOAST_STYLES.set(toastStyle);
        }
        return this;
    }

    /**
     * 使用默认 Toast 样式
     * @return
     */
    @Override
    public IToast.Operate defaultStyle() {
        return style(sDefaultStyle);
    }

    /**
     * 获取 Toast 样式配置
     * @return Toast 样式配置
     */
    @Override
    public IToast.Style getToastStyle() {
        if (sToastStyle == null) {
            sToastStyle = sDefaultStyle;
        }
        return sToastStyle;
    }

    /**
     * 初始化 Toast 样式配置(非单次,一直持续)
     * @param toastStyle Toast 样式配置
     */
    @Override
    public void initStyle(final IToast.Style toastStyle) {
        sToastStyle = toastStyle;
        // 防止样式为null
        getToastStyle();
    }

    /**
     * 初始化 Toast 过滤器
     * @param toastFilter
     */
    @Override
    public void initToastFilter(final IToast.Filter toastFilter) {
        sToastFilter = toastFilter;
    }

    /**
     * 设置 Toast 显示的View
     * @param view
     */
    @Override
    public void setView(final View view) {
        if (sConfigToast != null && view != null) {
            sConfigToast.setView(view);
            // 如果是 null, 则抛出异常
            if (sConfigToast.isEmptyMessageView()) {
                // 如果设置的布局没有包含一个 TextView 则抛出异常，必须要包含一个 TextView 作为 Message View
                throw new IllegalArgumentException("The layout must contain a TextView");
            }
        }
    }

    /**
     * 设置 Toast 显示的View
     * @param layoutId
     */
    @Override
    public void setView(final int layoutId) {
        if (sConfigToast != null) {
            try {
                setView(View.inflate(sConfigToast.getView().getContext().getApplicationContext(), layoutId, null));
            } catch (Exception e) {
            }
            // 如果是 null, 则抛出异常
            if (sConfigToast.isEmptyMessageView()) {
                // 如果设置的布局没有包含一个 TextView 则抛出异常，必须要包含一个 TextView 作为 Message View
                throw new IllegalArgumentException("The layout must contain a TextView");
            }
        }
    }

    // ============
    // = 操作方法 =
    // ============

    /**
     * 显示 Toast
     * @param content
     * @param args
     */
    @Override
    public void show(final String content, final Object... args) {
        String text = Utils.getFormatString(content, args);
        if (filter(text)) {
            priShowToastText(handlerContent(text));
        }
    }

    /**
     * 显示 R.string.resId Toast
     * @param resId
     * @param args
     */
    @Override
    public void show(final int resId, final Object... args) {
        String text = Utils.getFormatRes(resId, args);
        if (filter(text)) {
            // 获取处理的内容
            priShowToastText(handlerContent(text));
        }
    }

    /**
     * 通过 View 显示 Toast
     * @param view
     */
    @Override
    public void show(final View view) {
        if (filter(view)) {
            priShowToastView(view, Toast.LENGTH_SHORT);
        }
    }

    /**
     * 通过 View 显示 Toast
     * @param view
     * @param duration
     */
    @Override
    public void show(final View view, final int duration) {
        if (filter(view)) {
            priShowToastView(view, duration);
        }
    }

    // =

    @Override
    public void cancel() {
        if (mToast != null) {
            try {
                mToast.cancel();
            } catch (Exception e) {
            }
        }
    }

    // ==========================
    // = IToast.Filter 实现方法 =
    // ==========================

    /**
     * 判断是否显示
     * @param view
     * @return {@code true} 接着执行, {@code false} 过滤不处理
     */
    @Override
    public boolean filter(View view) {
        if (sToastFilter != null) {
            return sToastFilter.filter(view);
        }
        return true;
    }

    /**
     * 判断是否显示
     * @param content
     * @return {@code true} 接着执行, {@code false} 过滤不处理
     */
    @Override
    public boolean filter(String content) {
        if (sToastFilter != null) {
            return sToastFilter.filter(content);
        }
        return true;
    }

    /**
     * 获取 Toast 显示的文案
     * @param content
     * @return 处理后的内容
     */
    @Override
    public String handlerContent(String content) {
        if (sToastFilter != null) {
            return sToastFilter.handlerContent(content);
        }
        return content;
    }

    // ============
    // = 内部处理 =
    // ============

    /**
     * 返回对应线程的 Toast 样式信息
     */
    private IToast.Style getThreadToastStyle() {
        // 获取当前线程的线程的 Toast 样式
        IToast.Style toastStyle = LOCAL_TOAST_STYLES.get();
        // 如果等于null,则返回默认配置信息
        if (toastStyle == null) {
            return getToastStyle();
        } else {
            LOCAL_TOAST_STYLES.remove();
        }
        // 如果存在当前线程的配置信息,则返回
        return toastStyle;
    }

    /**
     * 默认创建View
     * @return
     */
    private TextView createView() {
        TextView textView = new TextView(mApplication);
        textView.setId(android.R.id.message);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return textView;
    }

    /**
     * 内部私有方法, 最终显示 Toast
     * @param text
     * @Toast
     */
    private void priShowToastText(final String text) {
        // 获取样式
        final IToast.Style style = getThreadToastStyle();
        if (mIsHandler) {
            sHandler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        Toast toast = newToastText(style, text);
                        if (toast != null) {
                            toast.show();
                        }
                    } catch (Exception e) {
                        LogPrintUtils.eTag(TAG, e, "priShowToastText");
                    }
                }
            });
        } else {
            try {
                Toast toast = newToastText(style, text);
                if (toast != null) {
                    toast.show();
                }
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "priShowToastText");
            }
        }
    }

    /**
     * 获取一个新的 Text Toast
     * @param style
     * @param text
     * @return
     */
    private Toast newToastText(final IToast.Style style, String text) {
        if (style == null) return null;
        // 设置为null, 便于提示排查
        if (TextUtils.isEmpty(text)) {
            text = mNullText;
            // 如果还是为null, 则不处理
            if (TextUtils.isEmpty(text)) {
                return null;
            }
        }
        try {
            // 关闭旧的 Toast
            if (mToast != null) {
                mToast.cancel();
                mToast = null;
            }
            // 如果不存在 TextView, 直接跳过
            if (sConfigToast.isEmptyMessageView()) {
                return null;
            }
            View view = sConfigToast.getView();
            // 获取 Toast TextView
            TextView toastTextView = sConfigToast.getMessageView();
            // 设置文案
            toastTextView.setText(text);
            // 设置字体颜色
            if (style.getTextColor() != 0) {
                toastTextView.setTextColor(style.getTextColor());
            }
            // 设置字体大小
            if (style.getTextSize() != 0f) {
                toastTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, style.getTextSize());
            }
            // 设置最大行数
            if (style.getMaxLines() >= 1) {
                toastTextView.setMaxLines(style.getMaxLines());
            }
            // 设置Ellipsize 效果
            if (style.getEllipsize() != null) {
                toastTextView.setEllipsize(style.getEllipsize());
            }
            // 设置字体样式
            if (style.getTypeface() != null) {
                toastTextView.setTypeface(style.getTypeface());
            }
            // 设置 Z 轴阴影
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                // 设置 Z 轴阴影
                toastTextView.setZ(style.getZ());
            }
            // 设置边距
            if (style.getPadding() != null && style.getPadding().length == 4) {
                int[] padding = style.getPadding();
                toastTextView.setPadding(padding[0], padding[1], padding[2], padding[3]);
            }

            // 获取背景图片
            Drawable backgroundDrawable = style.getBackground();
            // 如果等于 null
            if (backgroundDrawable != null) {
                // 设置背景
                Utils.setBackground(view, backgroundDrawable);
            } else {
                if (style.getBackgroundTintColor() != 0) {
                    GradientDrawable drawable = new GradientDrawable();
                    // 设置背景色
                    drawable.setColor(style.getBackgroundTintColor());
                    // 设置圆角大小
                    drawable.setCornerRadius(style.getCornerRadius());
                    // 设置背景
                    Utils.setBackground(view, drawable);
                }
            }

            // 创建 Toast
            mToast = ToastFactory.create(DevUtils.getContext());
            mToast.setView(view);
            // 设置属性配置
            if (style.getGravity() != 0) {
                // 设置 Toast 的重心、X、Y 轴偏移
                mToast.setGravity(style.getGravity(), style.getXOffset(), style.getYOffset());
            }
            // 设置边距
            mToast.setMargin(style.getHorizontalMargin(), style.getVerticalMargin());
            // 设置显示时间
            mToast.setDuration((text.length() < mTextLengthConvertDuration) ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "newToastText");
        }
        return mToast;
    }

    /**
     * 显示 View Toast 方法
     * @param view
     * @param duration
     */
    private void priShowToastView(final View view, final int duration) {
        if (view == null) return;
        // 获取样式
        final IToast.Style style = getThreadToastStyle();
        // =
        if (mIsHandler) {
            sHandler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        Toast toast = newToastView(style, view, duration);
                        if (toast != null) {
                            toast.show();
                        }
                    } catch (Exception e) {
                        LogPrintUtils.eTag(TAG, e, "priShowToastView");
                    }
                }
            });
        } else {
            try {
                Toast toast = newToastView(style, view, duration);
                if (toast != null) {
                    toast.show();
                }
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "priShowToastView");
            }
        }
    }

    /**
     * 获取一个新的 View Toast
     * @param style
     * @param view
     * @param duration
     * @return
     */
    private Toast newToastView(final IToast.Style style, final View view, final int duration) {
        if (style == null) {
            return null;
        } else if (view == null) { // 防止显示的View 为null
            return null;
        }
        try {
            // 关闭旧的 Toast
            if (mToast != null) {
                mToast.cancel();
                mToast = null;
            }
            // 获取背景图片
            Drawable backgroundDrawable = style.getBackground();
            // 如果等于 null
            if (backgroundDrawable != null) {
                // 设置背景
                Utils.setBackground(view, backgroundDrawable);
            } else {
                if (style.getBackgroundTintColor() != 0) {
                    GradientDrawable drawable = new GradientDrawable();
                    // 设置背景色
                    drawable.setColor(style.getBackgroundTintColor());
                    // 设置圆角大小
                    drawable.setCornerRadius(style.getCornerRadius());
                    // 设置背景
                    Utils.setBackground(view, drawable);
                }
            }

            // 创建 Toast
            mToast = ToastFactory.create(DevUtils.getContext());
            mToast.setView(view);
            // 设置属性配置
            if (style.getGravity() != 0) {
                // 设置 Toast 的重心、X、Y 轴偏移
                mToast.setGravity(style.getGravity(), style.getXOffset(), style.getYOffset());
            }
            // 设置边距
            mToast.setMargin(style.getHorizontalMargin(), style.getVerticalMargin());
            // 设置显示时间
            mToast.setDuration(duration);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "newToastView");
        }
        return mToast;
    }
}
