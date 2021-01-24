package dev.utils.app.toast.toaster;

import android.content.Context;
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

import androidx.annotation.LayoutRes;
import androidx.annotation.StringRes;

import dev.DevUtils;
import dev.utils.LogPrintUtils;
import dev.utils.app.ResourceUtils;
import dev.utils.app.ViewUtils;
import dev.utils.common.StringUtils;

/**
 * detail: Toast 接口实现方法 ( 处理方法 )
 * @author Ttt
 */
final class IToastImpl
        implements IToast.Operate,
        IToast.Filter {

    // 日志 TAG
    private final String TAG = IToastImpl.class.getSimpleName();

    // Context
    private       Context                   mContext;
    // 内部保存配置 Toast
    private       ToastFactory.BaseToast    mConfigToast               = null;
    // 当前显示的 Toast
    private       ToastFactory.BaseToast    mToast                     = null;
    // Toast 样式信息
    private       IToast.Style              mToastStyle                = null;
    // Toast 判断过滤
    private       IToast.Filter             mToastFilter               = null;
    // Toast 默认样式
    private final IToast.Style              mDefaultStyle              = new DefaultToastStyle();
    // 每个线程的 Toast 样式
    private final ThreadLocal<IToast.Style> LOCAL_TOAST_STYLES         = new ThreadLocal<>();
    // 判断是否使用 Handler
    private       boolean                   mIsHandler                 = true;
    // 内部 Handler
    private final Handler                   mHandler                   = new Handler(Looper.getMainLooper());
    // Null 值 ( null 提示值 )
    private       String                    mNullText                  = null;
    // Toast 文案长度转换 显示时间
    private       int                       mTextLengthConvertDuration = 15;

    /**
     * 重置默认参数
     */
    @Override
    public void reset() {
        // 重新初始化
        init(mContext);
    }

    /**
     * 设置是否使用 Handler 显示 Toast
     * @param isHandler {@code true} 使用, {@code false} 不使用
     */
    @Override
    public void setIsHandler(final boolean isHandler) {
        this.mIsHandler = isHandler;
    }

    /**
     * 设置 Text 为 null 的文本
     * @param nullText 显示内容为 null 时, 使用的提示值
     */
    @Override
    public void setNullText(final String nullText) {
        this.mNullText = nullText;
    }

    /**
     * 设置 Toast 文案长度转换 显示时间
     * @param textLengthConvertDuration Toast 文案长度转换界限
     */
    @Override
    public void setTextLength(final int textLengthConvertDuration) {
        this.mTextLengthConvertDuration = textLengthConvertDuration;
    }

    // =========
    // = 初始化 =
    // =========

    /**
     * 初始化调用
     * @param context {@link Context}
     */
    @Override
    public void init(final Context context) {
        if (context != null) {
            this.mContext = context.getApplicationContext();
            // 初始化默认参数
            mIsHandler = true;
            mNullText = null;
            // 初始化 Toast
            mConfigToast = new ToastFactory.BaseToast(mContext);
            mConfigToast.setView(createView());
            // 初始化默认样式
            getToastStyle();
        }
    }

    // ================================
    // = 实现 IToast 接口, 对外公开方法 =
    // ================================

    /**
     * 使用单次 Toast 样式配置
     * @param toastStyle Toast 样式
     * @return {@link IToast.Operate}
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
     * @return {@link IToast.Operate}
     */
    @Override
    public IToast.Operate defaultStyle() {
        return style(mDefaultStyle);
    }

    /**
     * 获取 Toast 样式配置
     * @return Toast 样式配置
     */
    @Override
    public IToast.Style getToastStyle() {
        if (mToastStyle == null) {
            mToastStyle = mDefaultStyle;
        }
        return mToastStyle;
    }

    /**
     * 初始化 Toast 样式配置
     * @param toastStyle Toast 样式配置
     */
    @Override
    public void initStyle(final IToast.Style toastStyle) {
        mToastStyle = toastStyle;
        // 防止样式为 null
        getToastStyle();
    }

    /**
     * 初始化 Toast 过滤器
     * @param toastFilter Toast 过滤器
     */
    @Override
    public void initToastFilter(final IToast.Filter toastFilter) {
        mToastFilter = toastFilter;
    }

    /**
     * 设置 Toast 显示的 View
     * @param view Toast 显示的 View
     */
    @Override
    public void setView(final View view) {
        if (mConfigToast != null && view != null) {
            mConfigToast.setView(view);
            // 如果是 null, 则抛出异常
            if (mConfigToast.isEmptyMessageView()) {
                // 如果设置的布局没有包含一个 TextView 则抛出异常, 必须要包含一个 TextView 作为 Message View
                throw new IllegalArgumentException("The layout must contain a TextView");
            }
        }
    }

    /**
     * 设置 Toast 显示的 View
     * @param layoutId R.layout.id
     */
    @Override
    public void setView(@LayoutRes final int layoutId) {
        if (mConfigToast != null) {
            try {
                setView(View.inflate(mConfigToast.getView().getContext().getApplicationContext(), layoutId, null));
            } catch (Exception e) {
            }
            // 如果是 null, 则抛出异常
            if (mConfigToast.isEmptyMessageView()) {
                // 如果设置的布局没有包含一个 TextView 则抛出异常, 必须要包含一个 TextView 作为 Message View
                throw new IllegalArgumentException("The layout must contain a TextView");
            }
        }
    }

    // ===========
    // = 操作方法 =
    // ===========

    /**
     * 显示 Toast
     * @param text       Toast 提示文本
     * @param formatArgs 格式化参数
     */
    @Override
    public void show(
            final String text,
            final Object... formatArgs
    ) {
        String context = StringUtils.getFormatString(text, formatArgs);
        if (filter(context)) {
            priShowToastText(handlerContent(context));
        }
    }

    /**
     * 显示 R.string.id Toast
     * @param resId      R.string.id
     * @param formatArgs 格式化参数
     */
    @Override
    public void show(
            @StringRes final int resId,
            final Object... formatArgs
    ) {
        String context = ResourceUtils.getString(resId, formatArgs);
        if (filter(context)) {
            // 获取处理的内容
            priShowToastText(handlerContent(context));
        }
    }

    /**
     * 通过 View 显示 Toast
     * @param view Toast 显示的 View
     */
    @Override
    public void show(final View view) {
        if (filter(view)) {
            priShowToastView(view, Toast.LENGTH_SHORT);
        }
    }

    /**
     * 通过 View 显示 Toast
     * @param view     Toast 显示的 View
     * @param duration Toast 显示时长 {@link Toast#LENGTH_SHORT}、{@link Toast#LENGTH_LONG}
     */
    @Override
    public void show(
            final View view,
            final int duration
    ) {
        if (filter(view)) {
            priShowToastView(view, duration);
        }
    }

    // =

    /**
     * 取消当前显示的 Toast
     */
    @Override
    public void cancel() {
        if (mToast != null) {
            try {
                mToast.cancel();
            } catch (Exception e) {
            }
        }
    }

    // =========================
    // = IToast.Filter 实现方法 =
    // =========================

    /**
     * 判断是否显示
     * @param view Toast 显示的 View
     * @return {@code true} 接着执行, {@code false} 过滤不处理
     */
    @Override
    public boolean filter(View view) {
        if (mToastFilter != null) {
            return mToastFilter.filter(view);
        }
        return true;
    }

    /**
     * 判断是否显示
     * @param content Toast 显示文案
     * @return {@code true} 接着执行, {@code false} 过滤不处理
     */
    @Override
    public boolean filter(String content) {
        if (mToastFilter != null) {
            return mToastFilter.filter(content);
        }
        return true;
    }

    /**
     * 获取 Toast 显示的文案
     * @param content Toast 显示文案
     * @return 处理后的内容
     */
    @Override
    public String handlerContent(String content) {
        if (mToastFilter != null) {
            return mToastFilter.handlerContent(content);
        }
        return content;
    }

    // ===========
    // = 内部处理 =
    // ===========

    /**
     * 返回对应线程的 Toast 样式信息
     * @return Toast 样式
     */
    private IToast.Style getThreadToastStyle() {
        // 获取当前线程的线程的 Toast 样式
        IToast.Style toastStyle = LOCAL_TOAST_STYLES.get();
        // 如果等于 null, 则返回默认配置信息
        if (toastStyle == null) {
            return getToastStyle();
        } else {
            LOCAL_TOAST_STYLES.remove();
        }
        // 如果存在当前线程的配置信息, 则返回
        return toastStyle;
    }

    /**
     * 默认创建 View
     * @return {@link TextView}
     */
    private TextView createView() {
        TextView textView = new TextView(mContext);
        textView.setId(android.R.id.message);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return textView;
    }

    /**
     * 内部私有方法, 最终显示 Toast
     * @param text Toast 提示文本
     */
    private void priShowToastText(final String text) {
        // 获取样式
        final IToast.Style style = getThreadToastStyle();
        if (mIsHandler) {
            mHandler.post(new Runnable() {
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
     * @param style Toast 样式 {@link IToast.Style}
     * @param text  Toast 提示文本
     * @return {@link Toast}
     */
    private Toast newToastText(
            final IToast.Style style,
            final String text
    ) {
        if (style == null) return null;
        // 提示文本
        String toastText = text;
        // 设置为 null, 便于提示排查
        if (TextUtils.isEmpty(toastText)) {
            toastText = mNullText;
            // 如果还是为 null, 则不处理
            if (TextUtils.isEmpty(toastText)) {
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
            if (mConfigToast.isEmptyMessageView()) {
                return null;
            }
            View view = mConfigToast.getView();
            // 获取 Toast TextView
            TextView toastTextView = mConfigToast.getMessageView();
            // 设置文案
            toastTextView.setText(toastText);
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
            // 设置 Ellipsize 效果
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
                ViewUtils.setBackground(view, backgroundDrawable);
            } else {
                if (style.getBackgroundTintColor() != 0) {
                    GradientDrawable drawable = new GradientDrawable();
                    // 设置背景色
                    drawable.setColor(style.getBackgroundTintColor());
                    // 设置圆角大小
                    drawable.setCornerRadius(style.getCornerRadius());
                    // 设置背景
                    ViewUtils.setBackground(view, drawable);
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
            mToast.setDuration((toastText.length() < mTextLengthConvertDuration) ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "newToastText");
        }
        return mToast;
    }

    /**
     * 显示 View Toast 方法
     * @param view     Toast 显示的 View
     * @param duration Toast 显示时长 {@link Toast#LENGTH_SHORT}、{@link Toast#LENGTH_LONG}
     */
    private void priShowToastView(
            final View view,
            final int duration
    ) {
        if (view == null) return;
        // 获取样式
        final IToast.Style style = getThreadToastStyle();
        // =
        if (mIsHandler) {
            mHandler.post(new Runnable() {
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
     * @param style    Toast 样式 {@link IToast.Style}
     * @param view     Toast 显示的 View
     * @param duration Toast 显示时长 {@link Toast#LENGTH_SHORT}、{@link Toast#LENGTH_LONG}
     * @return {@link Toast}
     */
    private Toast newToastView(
            final IToast.Style style,
            final View view,
            final int duration
    ) {
        if (style == null) {
            return null;
        } else if (view == null) { // 防止显示的 View 为 null
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
                ViewUtils.setBackground(view, backgroundDrawable);
            } else {
                if (style.getBackgroundTintColor() != 0) {
                    GradientDrawable drawable = new GradientDrawable();
                    // 设置背景色
                    drawable.setColor(style.getBackgroundTintColor());
                    // 设置圆角大小
                    drawable.setCornerRadius(style.getCornerRadius());
                    // 设置背景
                    ViewUtils.setBackground(view, drawable);
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