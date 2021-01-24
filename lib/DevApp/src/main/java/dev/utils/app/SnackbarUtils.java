package dev.utils.app;

import android.app.Activity;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.LayoutRes;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.snackbar.SnackbarContentLayout;

import java.lang.ref.WeakReference;

import dev.utils.LogPrintUtils;
import dev.utils.R;
import dev.utils.common.StringUtils;

/**
 * detail: Snackbar 工具类
 * @author Ttt
 */
public final class SnackbarUtils {

    // 日志 TAG
    private final String TAG = SnackbarUtils.class.getSimpleName();

    // 持有 弱引用 Snackbar, 防止所属 View 销毁等
    private static WeakReference<Snackbar> sSnackbarReference;
    // 样式构造对象
    private        StyleBuilder            mStyleBuilder = new StyleBuilder();

    /**
     * 构造函数
     * @param view {@link View}
     */
    private SnackbarUtils(final View view) {
        if (view != null) {
            try {
                sSnackbarReference = new WeakReference<>(Snackbar.make(view, "", Snackbar.LENGTH_SHORT));
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "SnackbarUtils");
            }
        }
    }

    // ===========
    // = 构建方法 =
    // ===========

    /**
     * 获取 SnackbarUtils 对象
     * @param activity {@link Activity}
     * @return {@link SnackbarUtils}
     */
    public static SnackbarUtils with(final Activity activity) {
        if (activity != null && activity.getWindow() != null) {
            return new SnackbarUtils(activity.getWindow().getDecorView());
        }
        return new SnackbarUtils(null);
    }

    /**
     * 获取 SnackbarUtils 对象
     * @param fragment {@link Fragment}
     * @return {@link SnackbarUtils}
     */
    public static SnackbarUtils with(final Fragment fragment) {
        return new SnackbarUtils((fragment != null) ? fragment.getView() : null);
    }

    /**
     * 获取 SnackbarUtils 对象
     * @param window {@link Window}
     * @return {@link SnackbarUtils}
     */
    public static SnackbarUtils with(final Window window) {
        return new SnackbarUtils((window != null) ? window.getDecorView() : null);
    }

    /**
     * 获取 SnackbarUtils 对象
     * @param view {@link View}
     * @return {@link SnackbarUtils}
     */
    public static SnackbarUtils with(final View view) {
        return new SnackbarUtils(view);
    }

    // ===========
    // = 样式相关 =
    // ===========

    /**
     * 获取样式
     * @return {@link StyleBuilder}
     */
    public StyleBuilder getStyle() {
        return mStyleBuilder;
    }

    /**
     * 设置样式
     * @param style {@link SnackbarUtils.Style}
     * @return {@link SnackbarUtils}
     */
    public SnackbarUtils setStyle(final Style style) {
        this.mStyleBuilder = new StyleBuilder(style);
        return this;
    }

    // ===========
    // = 对外方法 =
    // ===========

    /**
     * 获取 Snackbar
     * @return {@link Snackbar}
     */
    public Snackbar getSnackbar() {
        return (sSnackbarReference != null) ? sSnackbarReference.get() : null;
    }

    /**
     * 获取 Snackbar View
     * @return {@link Snackbar#getView()}
     */
    public View getSnackbarView() {
        Snackbar snackbar = getSnackbar();
        if (snackbar != null) {
            return snackbar.getView();
        }
        return null;
    }

    /**
     * 获取 Snackbar TextView(snackbar_text)
     * @return Snackbar {@link TextView}
     */
    public TextView getTextView() {
        View view = getSnackbarView();
        if (view != null) {
            return view.findViewById(R.id.snackbar_text);
        }
        return null;
    }

    /**
     * 获取 Snackbar Action Button(snackbar_action)
     * <pre>
     *     右边按钮 ( 如: 撤销 )
     * </pre>
     * @return Snackbar {@link Button}
     */
    public Button getActionButton() {
        View view = getSnackbarView();
        if (view != null) {
            return view.findViewById(R.id.snackbar_action);
        }
        return null;
    }

    /**
     * 获取 Snackbar.SnackbarLayout ( FrameLayout )
     * @return {@link Snackbar.SnackbarLayout}
     */
    public Snackbar.SnackbarLayout getSnackbarLayout() {
        try {
            return (Snackbar.SnackbarLayout) getSnackbarView();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getSnackbarLayout");
        }
        return null;
    }

    /**
     * 获取 SnackbarContentLayout ( LinearLayout ( messageView、actionView ) )
     * @return {@link SnackbarContentLayout}
     */
    public SnackbarContentLayout getSnackbarContentLayout() {
        try {
            return (SnackbarContentLayout) getSnackbarLayout().getChildAt(0);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getSnackbarContentLayout");
        }
        return null;
    }

    /**
     * 向 Snackbar 布局中添加 View ( Google 不建议, 复杂的布局应该使用 DialogFragment 进行展示 )
     * @param layoutId R.layout.id
     * @param index    添加索引
     * @return {@link SnackbarUtils}
     */
    public SnackbarUtils addView(
            @LayoutRes final int layoutId,
            final int index
    ) {
        Snackbar snackbar = getSnackbar();
        if (snackbar != null) {
            try {
                // 加载布局文件新建 View
                View view = LayoutInflater.from(snackbar.getView().getContext()).inflate(layoutId, null);
                return addView(view, index);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "addView");
            }
        }
        return this;
    }

    /**
     * 向 Snackbar 布局中添加 View ( Google 不建议, 复杂的布局应该使用 DialogFragment 进行展示 )
     * @param view  {@link View}
     * @param index 添加索引
     * @return {@link SnackbarUtils}
     */
    public SnackbarUtils addView(
            final View view,
            final int index
    ) {
        Snackbar snackbar = getSnackbar();
        if (snackbar != null && view != null) {
            try {
                ((Snackbar.SnackbarLayout) snackbar.getView()).addView(view, index);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "addView");
            }
        }
        return this;
    }

    /**
     * 设置 Snackbar 展示完成、隐藏完成 的监听
     * @param callback {@link Snackbar.Callback}
     * @return {@link SnackbarUtils}
     */
    public SnackbarUtils setCallback(final Snackbar.Callback callback) {
        Snackbar snackbar = getSnackbar();
        if (snackbar != null) {
            snackbar.addCallback(callback);
        }
        return this;
    }

    // =

    /**
     * 设置 Action 按钮文字内容及点击监听
     * @param resId      R.string.id
     * @param formatArgs 格式化参数
     * @return {@link SnackbarUtils}
     */
    public SnackbarUtils setAction(
            @StringRes final int resId,
            final Object... formatArgs
    ) {
        return setAction(ClickUtils.EMPTY_CLICK, resId, formatArgs);
    }

    /**
     * 设置 Action 按钮文字内容及点击监听
     * @param listener   按钮点击事件
     * @param resId      R.string.id
     * @param formatArgs 格式化参数
     * @return {@link SnackbarUtils}
     */
    public SnackbarUtils setAction(
            final View.OnClickListener listener,
            @StringRes final int resId,
            final Object... formatArgs
    ) {
        Snackbar snackbar = getSnackbar();
        if (snackbar != null) {
            String content = ResourceUtils.getString(resId, formatArgs);
            if (!TextUtils.isEmpty(content)) {
                snackbar.setAction(content, listener);
            }
        }
        return this;
    }

    /**
     * 设置 Action 按钮文字内容及点击监听
     * @param text       按钮文本
     * @param formatArgs 格式化参数
     * @return {@link SnackbarUtils}
     */
    public SnackbarUtils setAction(
            final String text,
            final Object... formatArgs
    ) {
        return setAction(ClickUtils.EMPTY_CLICK, text, formatArgs);
    }

    /**
     * 设置 Action 按钮文字内容及点击监听
     * @param listener   按钮点击事件
     * @param text       按钮文本
     * @param formatArgs 格式化参数
     * @return {@link SnackbarUtils}
     */
    public SnackbarUtils setAction(
            final View.OnClickListener listener,
            final String text,
            final Object... formatArgs
    ) {
        Snackbar snackbar = getSnackbar();
        if (snackbar != null) {
            String content = StringUtils.getFormatString(text, formatArgs);
            if (!TextUtils.isEmpty(content)) {
                snackbar.setAction(content, listener);
            }
        }
        return this;
    }

    // =

    /**
     * 关闭 Snackbar
     */
    public void dismiss() {
        dismiss(true);
    }

    /**
     * 关闭 Snackbar
     * @param setNull 是否设置 null
     */
    public void dismiss(final boolean setNull) {
        Snackbar snackbar = getSnackbar();
        if (snackbar != null) {
            snackbar.dismiss();
            if (setNull) {
                sSnackbarReference = null;
            }
        }
    }

    // ===========
    // = 显示方法 =
    // ===========

    /**
     * 显示 Short Snackbar
     * @param resId      R.string.id
     * @param formatArgs 格式化参数
     */
    public void showShort(
            @StringRes final int resId,
            final Object... formatArgs
    ) {
        priShow(ResourceUtils.getString(resId, formatArgs), Snackbar.LENGTH_SHORT);
    }

    /**
     * 显示 Long Snackbar
     * @param resId      R.string.id
     * @param formatArgs 格式化参数
     */
    public void showLong(
            @StringRes final int resId,
            final Object... formatArgs
    ) {
        priShow(ResourceUtils.getString(resId, formatArgs), Snackbar.LENGTH_LONG);
    }

    /**
     * 显示 Indefinite Snackbar ( 无限时, 一直显示 )
     * @param resId      R.string.id
     * @param formatArgs 格式化参数
     */
    public void showIndefinite(
            @StringRes final int resId,
            final Object... formatArgs
    ) {
        priShow(ResourceUtils.getString(resId, formatArgs), Snackbar.LENGTH_INDEFINITE);
    }

    // =

    /**
     * 显示 Short Snackbar
     * @param text       显示文本
     * @param formatArgs 格式化参数
     */
    public void showShort(
            final String text,
            final Object... formatArgs
    ) {
        priShow(StringUtils.getFormatString(text, formatArgs), Snackbar.LENGTH_SHORT);
    }

    /**
     * 显示 Long Snackbar
     * @param text       显示文本
     * @param formatArgs 格式化参数
     */
    public void showLong(
            final String text,
            final Object... formatArgs
    ) {
        priShow(StringUtils.getFormatString(text, formatArgs), Snackbar.LENGTH_LONG);
    }

    /**
     * 显示 Indefinite Snackbar ( 无限时, 一直显示 )
     * @param text       显示文本
     * @param formatArgs 格式化参数
     */
    public void showIndefinite(
            final String text,
            final Object... formatArgs
    ) {
        priShow(StringUtils.getFormatString(text, formatArgs), Snackbar.LENGTH_INDEFINITE);
    }

    // ===========
    // = 内部方法 =
    // ===========

    /**
     * 内部显示方法
     * @param text     显示文本
     * @param duration 显示时长 {@link Snackbar#LENGTH_SHORT}、{@link Snackbar#LENGTH_LONG}、{@link Snackbar#LENGTH_INDEFINITE}
     */
    private void priShow(
            final String text,
            final int duration
    ) {
        Snackbar snackbar = getSnackbar();
        if (snackbar != null && !snackbar.isShownOrQueued()) {
            // 防止内容为 null
            if (!TextUtils.isEmpty(text)) {
                // 设置样式
                setSnackbarStyle(snackbar);
                try {
                    // 设置坐标位置
                    setSnackbarLocation(snackbar);
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "priShow - setSnackbarLocation");
                }
                // 显示 SnackBar
                snackbar.setText(text).setDuration(duration).show();
            }
        }
    }

    // =

    /**
     * detail: Snackbar 样式配置构造类
     * @author Ttt
     */
    public static final class StyleBuilder
            extends Style {

        // ============
        // = RootView =
        // ============

        // RootView 的重心
        private int      rootGravity;
        // RootView 背景圆角大小
        private float    rootCornerRadius;
        @ColorInt // RootView 背景着色颜色
        private int      rootBackgroundTintColor;
        // RootView 背景图片
        private Drawable rootBackground;
        // RootView margin 边距 new int[] { left, top, right, bottom }
        private int[]    rootMargin;
        // RootView 透明度
        private float    rootAlpha = 1.0f;

        // ===============================
        // = snackbar_text TextView 相关 =
        // ===============================

        // TextView 的重心
        private int                  textGravity;
        @ColorInt // TextView 文本颜色
        private int                  textColor;
        // TextView 字体大小
        private float                textSize;
        // TextView 最大行数
        private int                  textMaxLines;
        // TextView Ellipsize 效果
        private TextUtils.TruncateAt textEllipsize;
        // TextView 字体样式
        private Typeface             textTypeface;
        // TextView padding 边距 new int[] { left, top, right, bottom }
        private int[]                textPadding;

        // ===============================
        // = snackbar_action Button 相关 =
        // ===============================

        // Action Button 的重心
        private int      actionGravity;
        @ColorInt // Action Button 文本颜色
        private int      actionColor;
        // Action Button 字体大小
        private float    actionSize;
        // Action Button padding 边距 new int[] { left, top, right, bottom }
        private int[]    actionPadding;
        // RootView 背景圆角大小
        private float    actionCornerRadius;
        @ColorInt // RootView 背景着色颜色
        private int      actionBackgroundTintColor;
        // RootView 背景图片
        private Drawable actionBackground;

        // =

        /**
         * 构造函数
         */
        public StyleBuilder() {
        }

        /**
         * 构造函数
         * @param style {@link SnackbarUtils.Style}
         */
        public StyleBuilder(final SnackbarUtils.Style style) {
            if (style != null) {

                // ============
                // = RootView =
                // ============

                // RootView 的重心
                this.rootGravity = style.getRootGravity();
                // RootView 背景圆角大小
                this.rootCornerRadius = style.getRootCornerRadius();
                // RootView 背景着色颜色
                this.rootBackgroundTintColor = style.getRootBackgroundTintColor();
                // RootView 背景图片
                this.rootBackground = style.getRootBackground();
                // RootView margin 边距 new int[] { left, top, right, bottom }
                this.rootMargin = style.getRootMargin();
                // RootView 透明度
                this.rootAlpha = style.getRootAlpha();

                // ===============================
                // = snackbar_text TextView 相关 =
                // ===============================

                // TextView 的重心
                this.textGravity = style.getTextGravity();
                // TextView 文本颜色
                this.textColor = style.getTextColor();
                // TextView 字体大小
                this.textSize = style.getTextSize();
                // TextView 最大行数
                this.textMaxLines = style.getTextMaxLines();
                // TextView Ellipsize 效果
                this.textEllipsize = style.getTextEllipsize();
                // TextView 字体样式
                this.textTypeface = style.getTextTypeface();
                // TextView padding 边距 new int[] { left, top, right, bottom }
                this.textPadding = style.getTextPadding();

                // ===============================
                // = snackbar_action Button 相关 =
                // ===============================

                // Action Button 的重心
                this.actionGravity = style.getActionGravity();
                // Action Button 文本颜色
                this.actionColor = style.getActionColor();
                // Action Button 字体大小
                this.actionSize = style.getActionSize();
                // Action Button padding 边距 new int[] { left, top, right, bottom }
                this.actionPadding = style.getActionPadding();
                // RootView 背景圆角大小
                this.actionCornerRadius = style.getActionCornerRadius();
                // RootView 背景着色颜色
                this.actionBackgroundTintColor = style.getActionBackgroundTintColor();
                // RootView 背景图片
                this.actionBackground = style.getActionBackground();
            }
        }

        // ===========
        // = get/set =
        // ===========

        // ============
        // = RootView =
        // ============

        /**
         * 获取 RootView 的重心
         * @return RootView 的重心
         */
        @Override
        public int getRootGravity() {
            return rootGravity;
        }

        /**
         * 设置 RootView 的重心
         * @param rootGravity RootView 的重心
         * @return {@link StyleBuilder}
         */
        public StyleBuilder setRootGravity(int rootGravity) {
            this.rootGravity = rootGravity;
            return this;
        }

        /**
         * 获取 RootView 背景圆角大小
         * @return RootView 背景圆角大小
         */
        @Override
        public float getRootCornerRadius() {
            return rootCornerRadius;
        }

        /**
         * 设置 RootView 背景圆角大小
         * @param rootCornerRadius RootView 背景圆角大小
         * @return {@link StyleBuilder}
         */
        public StyleBuilder setRootCornerRadius(float rootCornerRadius) {
            this.rootCornerRadius = rootCornerRadius;
            return this;
        }

        /**
         * 获取 RootView 背景着色颜色
         * @return RootView 背景着色颜色
         */
        @ColorInt
        @Override
        public int getRootBackgroundTintColor() {
            return rootBackgroundTintColor;
        }

        /**
         * 设置 RootView 背景着色颜色
         * @param rootBackgroundTintColor RootView 背景着色颜色
         * @return {@link StyleBuilder}
         */
        public StyleBuilder setRootBackgroundTintColor(@ColorInt int rootBackgroundTintColor) {
            this.rootBackgroundTintColor = rootBackgroundTintColor;
            return this;
        }

        /**
         * 获取 RootView 背景图片
         * @return {@link Drawable}
         */
        @Override
        public Drawable getRootBackground() {
            return rootBackground;
        }

        /**
         * 设置 RootView 背景图片
         * @param rootBackground {@link Drawable}
         * @return {@link StyleBuilder}
         */
        public StyleBuilder setRootBackground(Drawable rootBackground) {
            this.rootBackground = rootBackground;
            return this;
        }

        /**
         * 获取 RootView margin 边距
         * @return RootView margin[]
         */
        @Override
        public int[] getRootMargin() {
            return rootMargin;
        }

        /**
         * 设置 RootView margin 边距
         * @param rootMargin margin[]
         * @return {@link StyleBuilder}
         */
        public StyleBuilder setRootMargin(int[] rootMargin) {
            this.rootMargin = rootMargin;
            return this;
        }

        /**
         * 获取 RootView 透明度
         * @return RootView 透明度
         */
        @Override
        public float getRootAlpha() {
            return rootAlpha;
        }

        /**
         * 设置 RootView 透明度
         * @param rootAlpha RootView 透明度
         * @return {@link StyleBuilder}
         */
        public StyleBuilder setRootAlpha(float rootAlpha) {
            this.rootAlpha = rootAlpha;
            return this;
        }

        // ===============================
        // = snackbar_text TextView 相关 =
        // ===============================

        /**
         * 获取 TextView 的重心
         * @return TextView 的重心
         */
        @Override
        public int getTextGravity() {
            return textGravity;
        }

        /**
         * 设置 TextView 的重心
         * @param textGravity TextView 的重心
         * @return {@link StyleBuilder}
         */
        public StyleBuilder setTextGravity(int textGravity) {
            this.textGravity = textGravity;
            return this;
        }

        /**
         * 获取 TextView 文本颜色
         * @return TextView 文本颜色
         */
        @ColorInt
        @Override
        public int getTextColor() {
            return textColor;
        }

        /**
         * 设置 TextView 文本颜色
         * @param textColor TextView 文本颜色
         * @return {@link StyleBuilder}
         */
        public StyleBuilder setTextColor(@ColorInt int textColor) {
            this.textColor = textColor;
            return this;
        }

        /**
         * 获取 TextView 字体大小
         * @return TextView 字体大小
         */
        @Override
        public float getTextSize() {
            return textSize;
        }

        /**
         * 设置 TextView 字体大小
         * @param textSize TextView 字体大小
         * @return {@link StyleBuilder}
         */
        public StyleBuilder setTextSize(float textSize) {
            this.textSize = textSize;
            return this;
        }

        /**
         * 获取 TextView 最大行数
         * @return TextView 最大行数
         */
        @Override
        public int getTextMaxLines() {
            return textMaxLines;
        }

        /**
         * 设置 TextView 最大行数
         * @param textMaxLines TextView 最大行数
         * @return {@link StyleBuilder}
         */
        public StyleBuilder setTextMaxLines(int textMaxLines) {
            this.textMaxLines = textMaxLines;
            return this;
        }

        /**
         * 获取 TextView Ellipsize 效果
         * @return {@link TextUtils.TruncateAt}
         */
        @Override
        public TextUtils.TruncateAt getTextEllipsize() {
            return textEllipsize;
        }

        /**
         * 设置 TextView Ellipsize 效果
         * @param textEllipsize {@link TextUtils.TruncateAt} Ellipsize
         * @return {@link StyleBuilder}
         */
        public StyleBuilder setTextEllipsize(TextUtils.TruncateAt textEllipsize) {
            this.textEllipsize = textEllipsize;
            return this;
        }

        /**
         * 获取 TextView 字体样式
         * @return {@link Typeface}
         */
        @Override
        public Typeface getTextTypeface() {
            return textTypeface;
        }

        /**
         * 设置 TextView 字体样式
         * @param textTypeface {@link Typeface}
         * @return {@link StyleBuilder}
         */
        public StyleBuilder setTextTypeface(Typeface textTypeface) {
            this.textTypeface = textTypeface;
            return this;
        }

        /**
         * 获取 TextView padding 边距 ( new int[] { left, top, right, bottom } )
         * @return TextView padding[]
         */
        @Override
        public int[] getTextPadding() {
            return textPadding;
        }

        /**
         * 设置 TextView padding 边距
         * @param textPadding TextView padding[]
         * @return {@link StyleBuilder}
         */
        public StyleBuilder setTextPadding(int[] textPadding) {
            this.textPadding = textPadding;
            return this;
        }

        // ===============================
        // = snackbar_action Button 相关 =
        // ===============================

        /**
         * 获取 Action Button 的重心
         * @return Action Button 的重心
         */
        @Override
        public int getActionGravity() {
            return actionGravity;
        }

        /**
         * 设置 Action Button 的重心
         * @param actionGravity Action Button 的重心
         * @return {@link StyleBuilder}
         */
        public StyleBuilder setActionGravity(int actionGravity) {
            this.actionGravity = actionGravity;
            return this;
        }

        /**
         * 获取 Action Button 文本颜色
         * @return Action Button 文本颜色
         */
        @ColorInt
        @Override
        public int getActionColor() {
            return actionColor;
        }

        /**
         * 设置 Action Button 文本颜色
         * @param actionColor Action Button 文本颜色
         * @return {@link StyleBuilder}
         */
        public StyleBuilder setActionColor(@ColorInt int actionColor) {
            this.actionColor = actionColor;
            return this;
        }

        /**
         * 获取 Action Button 字体大小
         * @return Action Button 字体大小
         */
        @Override
        public float getActionSize() {
            return actionSize;
        }

        /**
         * 设置 Action Button 字体大小
         * @param actionSize Action Button 字体大小
         * @return {@link StyleBuilder}
         */
        public StyleBuilder setActionSize(float actionSize) {
            this.actionSize = actionSize;
            return this;
        }

        /**
         * 获取 Action Button padding 边距
         * @return Action Button padding[]
         */
        @Override
        public int[] getActionPadding() {
            return actionPadding;
        }

        /**
         * 设置 Action Button padding 边距
         * @param actionPadding Action Button padding[]
         * @return {@link StyleBuilder}
         */
        public StyleBuilder setActionPadding(int[] actionPadding) {
            this.actionPadding = actionPadding;
            return this;
        }

        /**
         * 获取 Action Button 背景圆角大小
         * @return Action Button 背景圆角大小
         */
        @Override
        public float getActionCornerRadius() {
            return actionCornerRadius;
        }

        /**
         * 设置 Action Button 背景圆角大小
         * @param actionCornerRadius Action Button 背景圆角大小
         * @return {@link StyleBuilder}
         */
        public StyleBuilder setActionCornerRadius(float actionCornerRadius) {
            this.actionCornerRadius = actionCornerRadius;
            return this;
        }

        /**
         * 获取 Action Button 背景着色颜色
         * @return Action Button 背景着色颜色
         */
        @ColorInt
        @Override
        public int getActionBackgroundTintColor() {
            return actionBackgroundTintColor;
        }

        /**
         * 设置 Action Button 背景着色颜色
         * @param actionBackgroundTintColor Action Button 背景着色颜色
         * @return {@link StyleBuilder}
         */
        public StyleBuilder setActionBackgroundTintColor(@ColorInt int actionBackgroundTintColor) {
            this.actionBackgroundTintColor = actionBackgroundTintColor;
            return this;
        }

        /**
         * 获取 Action Button 背景图片
         * @return {@link Drawable}
         */
        @Override
        public Drawable getActionBackground() {
            return actionBackground;
        }

        /**
         * 设置 Action Button 背景图片
         * @param actionBackground {@link Drawable}
         * @return {@link StyleBuilder}
         */
        public StyleBuilder setActionBackground(Drawable actionBackground) {
            this.actionBackground = actionBackground;
            return this;
        }
    }

    // ===========
    // = 其他接口 =
    // ===========

    /**
     * detail: Snackbar 样式配置
     * @author Ttt
     */
    public static abstract class Style {

        // ============
        // = RootView =
        // ============

        /**
         * 获取 RootView 的重心
         * @return RootView 的重心
         */
        public int getRootGravity() {
            return 0;
        }

        /**
         * 获取 RootView 背景圆角大小
         * @return RootView 背景圆角大小
         */
        public float getRootCornerRadius() {
            return 0f;
        }

        /**
         * 获取 RootView 背景着色颜色
         * @return RootView 背景着色颜色
         */
        @ColorInt
        public int getRootBackgroundTintColor() {
            return 0;
        }

        /**
         * 获取 RootView 背景图片
         * @return {@link Drawable}
         */
        public Drawable getRootBackground() {
            return null;
        }

        /**
         * 获取 RootView margin 边距
         * @return RootView margin[]
         */
        public int[] getRootMargin() {
            return null;
        }

        /**
         * 获取 RootView 透明度
         * @return RootView 透明度
         */
        public float getRootAlpha() {
            return 1.0f;
        }

        // ===============================
        // = snackbar_text TextView 相关 =
        // ===============================

        /**
         * 获取 TextView 的重心
         * @return TextView 的重心
         */
        public int getTextGravity() {
            return 0;
        }

        /**
         * 获取 TextView 文本颜色
         * @return TextView 文本颜色
         */
        @ColorInt
        public int getTextColor() {
            return 0;
        }

        /**
         * 获取 TextView 字体大小
         * @return TextView 字体大小
         */
        public float getTextSize() {
            return 0f;
        }

        /**
         * 获取 TextView 最大行数
         * @return TextView 最大行数
         */
        public int getTextMaxLines() {
            return 0;
        }

        /**
         * 获取 TextView Ellipsize 效果
         * @return {@link TextUtils.TruncateAt}
         */
        public TextUtils.TruncateAt getTextEllipsize() {
            return null;
        }

        /**
         * 获取 TextView 字体样式
         * @return {@link Typeface}
         */
        public Typeface getTextTypeface() {
            return null;
        }

        /**
         * 获取 TextView padding 边距 ( new int[] { left, top, right, bottom } )
         * @return TextView padding[]
         */
        public int[] getTextPadding() {
            return null;
        }

        // ===============================
        // = snackbar_action Button 相关 =
        // ===============================

        /**
         * 获取 Action Button 的重心
         * @return Action Button 的重心
         */
        public int getActionGravity() {
            return 0;
        }

        /**
         * 获取 Action Button 文本颜色
         * @return Action Button 文本颜色
         */
        @ColorInt
        public int getActionColor() {
            return 0;
        }

        /**
         * 获取 Action Button 字体大小
         * @return Action Button 字体大小
         */
        public float getActionSize() {
            return 0f;
        }

        /**
         * 获取 Action Button padding 边距
         * @return Action Button padding[]
         */
        public int[] getActionPadding() {
            return null;
        }

        /**
         * 获取 Action Button 背景圆角大小
         * @return Action Button 背景圆角大小
         */
        public float getActionCornerRadius() {
            return 0f;
        }

        /**
         * 获取 Action Button 背景着色颜色
         * @return Action Button 背景着色颜色
         */
        @ColorInt
        public int getActionBackgroundTintColor() {
            return 0;
        }

        /**
         * 获取 Action Button 背景图片
         * @return {@link Drawable}
         */
        public Drawable getActionBackground() {
            return null;
        }
    }

    // ============
    // = 设置样式 =
    // ============

    /**
     * 设置 Snackbar 样式配置
     * @param snackbar {@link Snackbar}
     * @return {@link Snackbar}
     */
    public Snackbar setSnackbarStyle(final Snackbar snackbar) {
        return setSnackbarStyle(snackbar, mStyleBuilder);
    }

    /**
     * 设置 Snackbar 样式配置
     * @param snackbar {@link Snackbar}
     * @param style    {@link SnackbarUtils.Style}
     * @return {@link Snackbar}
     */
    public Snackbar setSnackbarStyle(
            final Snackbar snackbar,
            final SnackbarUtils.Style style
    ) {
        if (snackbar == null || style == null) {
            return snackbar;
        }
        // 获取显示的 View
        View rootView = snackbar.getView();
        if (rootView != null) {

            // ============
            // = RootView =
            // ============

            // 设置 RootView Gravity 处理
            if (style.getRootGravity() != 0) {
                setLayoutGravity(rootView, style.getRootGravity());
            }

            // 设置 RootView margin 边距
            int[] rootMargin = style.getRootMargin();
            if (rootMargin != null && rootMargin.length == 4) {
                setMargin(rootView, rootMargin, rootMargin[1], rootMargin[3]);
            }

            // 设置 RootView 透明度
            if (style.getRootAlpha() >= 0f) {
                float rootAlpha = style.getRootAlpha();
                rootAlpha = rootAlpha >= 1.0f ? 1.0f : (rootAlpha <= 0.0f ? 0.0f : rootAlpha);
                rootView.setAlpha(rootAlpha);
            }

            // 设置 RootView 背景相关
            // 获取背景图片
            Drawable rootBackgroundDrawable = style.getRootBackground();
            // 如果等于 null
            if (rootBackgroundDrawable != null) {
                // 设置背景
                ViewUtils.setBackground(rootView, rootBackgroundDrawable);
            } else {
                if (style.getRootBackgroundTintColor() != 0) {
                    GradientDrawable drawable = new GradientDrawable();
                    // 设置背景色
                    drawable.setColor(style.getRootBackgroundTintColor());
                    // 设置圆角大小
                    drawable.setCornerRadius(style.getRootCornerRadius());
                    // 设置背景
                    ViewUtils.setBackground(rootView, drawable);
                }
            }

            // ===============================
            // = snackbar_text TextView 相关 =
            // ===============================

            TextView textView = getTextView();
            // 防止 snackbar_text 为 null
            if (textView != null) {

                // TextView 的重心
                if (style.getTextGravity() != 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        textView.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
                    }
                    textView.setGravity(style.getTextGravity());
                }

                // TextView 文本颜色
                if (style.getTextColor() != 0) {
                    textView.setTextColor(style.getTextColor());
                }

                // TextView 字体大小
                if (style.getTextSize() != 0f) {
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, style.getTextSize());
                }

                // TextView 最大行数
                if (style.getTextMaxLines() >= 1) {
                    textView.setMaxLines(style.getTextMaxLines());
                }

                // TextView Ellipsize 效果
                if (style.getTextEllipsize() != null) {
                    textView.setEllipsize(style.getTextEllipsize());
                }

                // TextView 字体样式
                if (style.getTextTypeface() != null) {
                    textView.setTypeface(style.getTextTypeface());
                }

                // TextView padding 边距
                int[] textPadding = style.getTextPadding();
                if (textPadding != null && textPadding.length == 4) {
                    textView.setPadding(textPadding[0], textPadding[1], textPadding[2], textPadding[3]);
                }
            }

            // ===============================
            // = snackbar_action Button 相关 =
            // ===============================

            Button actionButton = getActionButton();
            // 防止 snackbar_action Button 为 null
            if (actionButton != null) {

                // Action Button 的重心
                if (style.getActionGravity() != 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        actionButton.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
                    }
                    actionButton.setGravity(style.getActionGravity());
                }

                // Action Button 文本颜色
                if (style.getActionColor() != 0) {
                    actionButton.setTextColor(style.getActionColor());
                }

                // Action Button 字体大小
                if (style.getActionSize() != 0f) {
                    actionButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, style.getActionSize());
                }

                // Action Button padding 边距
                int[] actionPadding = style.getActionPadding();
                if (actionPadding != null && actionPadding.length == 4) {
                    actionButton.setPadding(actionPadding[0], actionPadding[1], actionPadding[2], actionPadding[3]);
                }

                // 设置 Action Button 背景相关
                // 获取背景图片
                Drawable actionBackgroundDrawable = style.getActionBackground();
                // 如果等于 null
                if (actionBackgroundDrawable != null) {
                    // 设置背景
                    ViewUtils.setBackground(actionButton, actionBackgroundDrawable);
                } else {
                    if (style.getActionBackgroundTintColor() != 0) {
                        GradientDrawable drawable = new GradientDrawable();
                        // 设置背景色
                        drawable.setColor(style.getActionBackgroundTintColor());
                        // 设置圆角大小
                        drawable.setCornerRadius(style.getActionCornerRadius());
                        // 设置背景
                        ViewUtils.setBackground(actionButton, drawable);
                    }
                }
            }
        }
        return snackbar;
    }

    // =

    // View 坐标
    private int[]   mViewLocations   = null;
    // View 高度
    private int     mViewHeight      = 0;
    // 指定 View 坐标, 显示的重心方向 ( 只有 TOP、BOTTOM)
    private int     mViewGravity     = -1;
    // 追加向上边距 ( 如: 状态栏高度 )
    private int     mAppendTopMargin = 0;
    // View 阴影
    private int     mShadowMargin    = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) ? 2 : 0;
    // 判断是否自动计算 ( 如: 显示在 View 下面, 但是下方距离不够, 自动设置为在 View 上方显示 )
    private boolean mAutoCalc        = true;

    /**
     * 获取阴影边距
     * @return 阴影边距
     */
    public int getShadowMargin() {
        return mShadowMargin;
    }

    /**
     * 设置阴影边距
     * @param shadowMargin 阴影边距
     * @return {@link SnackbarUtils}
     */
    public SnackbarUtils setShadowMargin(final int shadowMargin) {
        this.mShadowMargin = shadowMargin;
        return this;
    }

    /**
     * 判断是否自动计算边距 ( 如: 显示在 View 下面, 但是下方距离不够, 自动设置为在 View 上方显示 )
     * @return {@code true} yes, {@code false} no
     */
    public boolean isAutoCalc() {
        return mAutoCalc;
    }

    /**
     * 设置是否自动计算边距 ( 如: 显示在 View 下面, 但是下方距离不够, 自动设置为在 View 上方显示 )
     * @param autoCalc 是否自动计算边距
     * @return {@link SnackbarUtils}
     */
    public SnackbarUtils setAutoCalc(final boolean autoCalc) {
        this.mAutoCalc = autoCalc;
        return this;
    }

    /**
     * 清空坐标相关信息
     */
    private void clearLocations() {
        // 重置处理
        mViewHeight = 0;
        mViewGravity = -1;
        mViewLocations = null;
        mAppendTopMargin = 0;
    }

    /**
     * 设置 Snackbar 显示在指定 View 的上方
     * @param targetView      目标 View
     * @param appendTopMargin 追加边距 ( 如: 状态栏高度 ) {@link BarUtils#getStatusBarHeight}
     * @return {@link SnackbarUtils}
     */
    public SnackbarUtils above(
            final View targetView,
            final int appendTopMargin
    ) {
        // 清空重置处理
        clearLocations();
        // 防止为 null
        if (targetView != null) {
            mViewHeight = targetView.getHeight();
            mViewGravity = Gravity.TOP;
            mViewLocations = new int[2];
            mAppendTopMargin = appendTopMargin;
            targetView.getLocationOnScreen(mViewLocations);
        }
        return this;
    }

    /**
     * 设置 Snackbar 显示在指定 View 的下方
     * @param targetView      目标 View
     * @param appendTopMargin 追加边距 ( 如: 状态栏高度 ) {@link BarUtils#getStatusBarHeight}
     * @return {@link SnackbarUtils}
     */
    public SnackbarUtils bellow(
            final View targetView,
            final int appendTopMargin
    ) {
        // 清空重置处理
        clearLocations();
        // 防止为 null
        if (targetView != null) {
            mViewHeight = targetView.getHeight();
            mViewGravity = Gravity.BOTTOM;
            mViewLocations = new int[2];
            mAppendTopMargin = appendTopMargin;
            targetView.getLocationOnScreen(mViewLocations);
        }
        return this;
    }

    /**
     * 设置 Snackbar 显示的坐标位置
     * @param snackbar {@link Snackbar}
     */
    private void setSnackbarLocation(final Snackbar snackbar) {
        if (snackbar == null) return;
        // 获取显示的 View
        View rootView = snackbar.getView();
        if (rootView != null) {
            // = 特殊处理 =
            // 属于显示在指定 View 坐标, 对应重心方向
            if (mViewLocations != null && mViewGravity != -1 && mViewHeight > 0) {
                // View ( 坐标 ) 边距
                int[] margin = new int[4];
                // 判断 Style 是否为 null
                if (mStyleBuilder != null) {
                    // 默认边距
                    int[] rootMargin = mStyleBuilder.getRootMargin();
                    if (rootMargin != null && rootMargin.length == 4) {
                        margin[0] = rootMargin[0];
                        margin[2] = rootMargin[2];
                    }
                }

                // 获取 View 上方距离
                int mViewTop = mViewLocations[1];
                // 获取屏幕高度
                int screenHeight = ScreenUtils.getScreenHeight();
                // 防止等于 0
                if (screenHeight != 0) {
                    // 获取测量高度 ( 不一定准确 )
                    int measuredHeight = WidgetUtils.getMeasuredHeight(rootView);
                    // 判断方向, 在指定坐标上方, 判断是否够空间
                    if (mViewGravity == Gravity.TOP) {
                        // 判断是否超出可显示高度
                        if (mViewTop - mShadowMargin - mAppendTopMargin >= measuredHeight) {
                            // 思路: 没有超出高度, 则正常显示在指定 View 上方
                            // 改为布局居下 ( 相反方向 ), 然后设置 bottomMargin 为 屏幕高度 - view mWindowTop + 阴影大小
                            // 这样的思路, 主要是只用知道 view 的 Y 轴位置, 然后用屏幕高度减去 Y 得到的就是需要向下的边距, 不需要计算 Snackbar View 高度

                            setLayoutGravity(rootView, Gravity.BOTTOM)
                                    .setMargin(rootView, margin, 0, screenHeight - mViewTop + mShadowMargin);
                        } else { // 超出可视范围
                            // 判断是否自动计算处理
                            if (mAutoCalc) {
                                // 思路如上: 超出高度后, 则直接设置居上, 计算边距则 view mWindowTop - 追加边距 ( 状态栏高度 ) + view height, 设置到 View 的下方
                                // 计算处理主要是, 只需要知道 view Y 轴位置 + view height - 追加边距 ( 状态栏高度 ) = 需要的边距
                                // 为什么需要减 状态栏高度, 是因为 view Y (view mWindowTop) 就包含状态栏高度信息

                                setLayoutGravity(rootView, Gravity.TOP)
                                        .setMargin(rootView, margin, mViewTop - mAppendTopMargin + mViewHeight, 0);
                            }
                        }
                    } else { // 在指定坐标下方
                        // 判断是否超出可显示高度
                        if (screenHeight - (mViewTop + mShadowMargin + mAppendTopMargin + mViewHeight) >= measuredHeight) {
                            // 思路: 没有超出高度, 则正常显示在指定 View 下方
                            // 并且改为布局居上, 然后设置 topMargin 为 view mWindowTop - ( 阴影大小 + 追加边距 ( 状态栏高度 ))
                            // 这样的思路, 主要是不居下, 不用知道 Snackbar view 高度, 导致向下边距计算错误, 转换思路从上处理

                            setLayoutGravity(rootView, Gravity.TOP)
                                    .setMargin(rootView, margin, mViewTop - (mShadowMargin + mAppendTopMargin), 0);
                        } else { // 超出可视范围
                            // 判断是否自动计算处理
                            if (mAutoCalc) {
                                // 思路如上: 超出高度后, 则直接设置居下, 计算边距则 用屏幕高度 - view mWindowTop + 阴影边距
                                // 计算处理的值则是 view mWindowTop 距离底部的边距, 刚好设置 bottomMargin, 实现思路转换处理

                                setLayoutGravity(rootView, Gravity.BOTTOM)
                                        .setMargin(rootView, margin, 0, screenHeight - mViewTop + mShadowMargin);
                            }
                        }
                    }
                }
            }
        }
        // 清空重置处理
        clearLocations();
    }

    /**
     * 设置 View Layout Gravity
     * @param view    {@link View}
     * @param gravity Gravity
     * @return {@link SnackbarUtils}
     */
    private SnackbarUtils setLayoutGravity(
            final View view,
            final int gravity
    ) {
        try {
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                    view.getLayoutParams().width, view.getLayoutParams().height
            );
            layoutParams.gravity = gravity;
            view.setLayoutParams(layoutParams);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "setLayoutGravity");
        }
        return this;
    }

    /**
     * 设置 Margin 边距
     * @param view         {@link View}
     * @param margin       left、right margin
     * @param topMargin    top margin
     * @param bottomMargin bottom margin
     * @return {@link SnackbarUtils}
     */
    private SnackbarUtils setMargin(
            final View view,
            final int[] margin,
            final int topMargin,
            final int bottomMargin
    ) {
        try {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            ((ViewGroup.MarginLayoutParams) layoutParams).setMargins(
                    margin[0], topMargin, margin[2], bottomMargin
            );
            view.setLayoutParams(layoutParams);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "setMargin");
        }
        return this;
    }
}