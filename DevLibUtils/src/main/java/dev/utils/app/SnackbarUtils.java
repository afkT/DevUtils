package dev.utils.app;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import dev.DevUtils;
import dev.utils.LogPrintUtils;
import dev.utils.R;

/**
 * detail: Snackbar 工具类
 * Created by Ttt
 */
public final class SnackbarUtils {

    // 日志 TAG
    private final String TAG = SnackbarUtils.class.getSimpleName();
    // 持有 弱引用 Snackbar, 防止所属 View 销毁等
    private static WeakReference<Snackbar> sSnackbarReference;
    // 样式构造对象
    private StyleBuilder mStyleBuilder = new StyleBuilder();

    private SnackbarUtils(View view) {
        if (view != null) {
            sSnackbarReference = new WeakReference<>(Snackbar.make(view, "", Snackbar.LENGTH_SHORT));
        }
    }

    // ============
    // = 构建方法 =
    // ============

    /**
     * 获取 SnackbarUtils 对象
     * @param activity
     * @return
     */
    public static SnackbarUtils with(Activity activity) {
        View view = null;
        if (activity != null && activity.getWindow() != null) {
            return new SnackbarUtils(activity.getWindow().getDecorView());
        }
        return new SnackbarUtils(view);
    }

    /**
     * 获取 SnackbarUtils 对象
     * @param fragment
     * @return
     */
    public static SnackbarUtils with(Fragment fragment) {
        return new SnackbarUtils((fragment != null) ? fragment.getView() : null);
    }

    /**
     * 获取 SnackbarUtils 对象
     * @param window
     * @return
     */
    public static SnackbarUtils with(Window window) {
        return new SnackbarUtils((window != null) ? window.getDecorView() : null);
    }

    /**
     * 获取 SnackbarUtils 对象
     * @param view
     * @return
     */
    public static SnackbarUtils with(View view) {
        return new SnackbarUtils(view);
    }

    // ============
    // = 样式相关 =
    // ============

    /**
     * 获取样式
     * @return
     */
    public StyleBuilder getStyle() {
        return mStyleBuilder;
    }

    /**
     * 设置样式
     * @param style
     * @return
     */
    public SnackbarUtils setStyle(Style style) {
        this.mStyleBuilder = new StyleBuilder(style);
        return this;
    }

    // ============
    // = 对外方法 =
    // ============

    /**
     * 获取 Snackbar
     * @return
     */
    public Snackbar getSnackbar() {
        return (sSnackbarReference != null) ? sSnackbarReference.get() : null;
    }

    /**
     * 获取 Snackbar View
     * @return
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
     * @return
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
     * 右边按钮(如: 撤销)
     * @return
     */
    public Button getActionButton() {
        View view = getSnackbarView();
        if (view != null) {
            return view.findViewById(R.id.snackbar_action);
        }
        return null;
    }

    /**
     * 向 Snackbar 布局中添加View (Google不建议,复杂的布局应该使用DialogFragment进行展示)
     * @param layoutId
     * @param index
     * @return
     */
    public SnackbarUtils addView(int layoutId, int index) {
        Snackbar snackbar = getSnackbar();
        if (snackbar != null) {
            try {
                // 加载布局文件新建View
                View addView = LayoutInflater.from(snackbar.getView().getContext()).inflate(layoutId,null);
                return addView(addView, index);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "addView");
            }
        }
        return this;
    }

    /**
     * 向 Snackbar 布局中添加View (Google不建议,复杂的布局应该使用DialogFragment进行展示)
     * @param view
     * @param index
     * @return
     */
    public SnackbarUtils addView(View view, int index) {
        Snackbar snackbar = getSnackbar();
        if (snackbar != null && view != null) {
            try {
                // 设置新建布局参数
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                // 设置新建 View 在 Snackbar 内垂直居中显示
                params.gravity= Gravity.CENTER_VERTICAL;
                view.setLayoutParams(params);
                ((Snackbar.SnackbarLayout) snackbar.getView()).addView(view,index);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "addView");
            }
        }
        return this;
    }

    /**
     * 设置 Snackbar 展示完成 及 隐藏完成 的监听
     * @param setCallback
     * @return
     */
    public SnackbarUtils setCallback(Snackbar.Callback setCallback) {
        Snackbar snackbar = getSnackbar();
        if (snackbar != null) {
            snackbar.addCallback(setCallback);
        }
        return this;
    }

    // =

    /**
     * 设置 Action 按钮文字内容 及 点击监听
     * @param resId
     * @param objs
     * @return
     */
    public SnackbarUtils setAction(int resId, Object... objs) {
        return setAction(null, resId, objs);
    }

    /**
     * 设置 Action 按钮文字内容 及 点击监听
     * @param listener
     * @param resId
     * @param objs
     * @return
     */
    public SnackbarUtils setAction(View.OnClickListener listener, int resId, Object... objs) {
        Snackbar snackbar = getSnackbar();
        if (snackbar != null) {
            String content = getFormatRes(resId, objs);
            if (!TextUtils.isEmpty(content)) {
                snackbar.setAction(content, listener);
            }
        }
        return this;
    }

    /**
     * 设置 Action 按钮文字内容 及 点击监听
     * @param text
     * @param objs
     * @return
     */
    public SnackbarUtils setAction(String text, Object... objs) {
        return setAction(null, text, objs);
    }

    /**
     * 设置 Action 按钮文字内容 及 点击监听
     * @param listener
     * @param text
     * @param objs
     * @return
     */
    public SnackbarUtils setAction(View.OnClickListener listener, String text, Object... objs) {
        Snackbar snackbar = getSnackbar();
        if (snackbar != null) {
            String content = getFormatString(text, objs);
            if (!TextUtils.isEmpty(content)) {
                snackbar.setAction(content, listener);
            }
        }
        return this;
    }

    /**
     * 关闭 Snackbar
     */
    public void dismiss() {
        dismiss(true);
    }


    /**
     * 关闭 Snackbar
     * @param setNull
     */
    public void dismiss(boolean setNull) {
        Snackbar snackbar = getSnackbar();
        if (snackbar != null) {
            snackbar.dismiss();
            if (setNull)
                sSnackbarReference = null;
        }
    }

    // ==============
    // == 显示方法 ==
    // ==============

    /**
     * 显示 Short Snackbar
     * @param resId
     * @param objs
     */
    public void showShort(int resId, Object... objs) {
        priShow(getFormatRes(resId, objs), Snackbar.LENGTH_SHORT);
    }

    /**
     * 显示 Long Snackbar
     * @param resId
     * @param objs
     */
    public void showLong(int resId, Object... objs) {
        priShow(getFormatRes(resId, objs), Snackbar.LENGTH_LONG);
    }

    /**
     * 显示 Indefinite Snackbar (无限时, 一直显示)
     * @param resId
     * @param objs
     */
    public void showIndefinite(int resId, Object... objs) {
        priShow(getFormatRes(resId, objs), Snackbar.LENGTH_INDEFINITE);
    }

    // =

    /**
     * 显示 Short Snackbar
     * @param text
     * @param objs
     */
    public void showShort(String text, Object... objs) {
        priShow(getFormatString(text, objs), Snackbar.LENGTH_SHORT);
    }

    /**
     * 显示 Long Snackbar
     * @param text
     * @param objs
     */
    public void showLong(String text, Object... objs) {
        priShow(getFormatString(text, objs), Snackbar.LENGTH_LONG);
    }

    /**
     * 显示 Indefinite Snackbar (无限时, 一直显示)
     * @param text
     * @param objs
     */
    public void showIndefinite(String text, Object... objs) {
        priShow(getFormatString(text, objs), Snackbar.LENGTH_INDEFINITE);
    }

    // ============
    // = 内部方法 =
    // ============

    /**
     * 内部显示方法
     * @param text
     * @param duration
     */
    private void priShow(String text, int duration){
        Snackbar snackbar = getSnackbar();
        if (snackbar != null) {
            if (!snackbar.isShownOrQueued()) {
                // 防止内容为null
                if (!TextUtils.isEmpty(text)) {
                    setSnackbarStyle(snackbar);
                    if (snackbar != null){
                        // 设置坐标位置
                        setSnackbarLocation(snackbar);
                        // 显示 SnackBar
                        snackbar.setText(text).setDuration(duration).show();
                    }
                }
            }
        }
    }

    // =

    /**
     * 获取格式化字符串
     * @param format
     * @param args
     * @return
     */
    private String getFormatString(String format, Object...  args) {
        try {
            if (args != null && args.length != 0) {
                return String.format(format, args);
            } else {
                return format;
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getFormatString");
        }
        return null;
    }

    /**
     * 获取 R.string 资源的格式化字符串
     * @param resId
     * @param objs
     */
    private String getFormatRes(int resId, Object...  objs) {
        try {
            // 获取字符串并且进行格式化
            if (objs != null && objs.length != 0) {
                return DevUtils.getContext().getString(resId, objs);
            } else {
                return DevUtils.getContext().getString(resId);
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getFormatRes");
        }
        return null;
    }

    /**
     * 设置背景
     * @param view
     * @param drawable
     */
    private void setBackground(View view, Drawable drawable) {
        if (view != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                view.setBackground(drawable);
            else
                view.setBackgroundDrawable(drawable);
        }
    }

    // =

    /**
     * detail:  Snackbar 样式配置构造类
     * Created by Ttt
     */
    public static final class StyleBuilder extends Style {

        // == RootView ==
        // RootView 的重心
        private int rootGravity;
        // RootView 背景圆角大小
        private float rootCornerRadius;
        // RootView 背景着色颜色
        private int rootBackgroundTintColor;
        // RootView 背景图片
        private Drawable rootBackground;
        // RootView margin 边距 - new int[] { left, top, right, bottom }
        private int[] rootMargin;
        // RootView 透明度
        private float rootAlpha = 1.0f;

        // == snackbar_text TextView 相关 ==
        // TextView 的重心
        private int textGravity;
        // TextView 文本颜色
        private int textColor;
        // TextView 字体大小
        private float textSize;
        // TextView 最大行数
        private int textMaxLines;
        // TextView Ellipsize 效果
        private TextUtils.TruncateAt textEllipsize;
        // TextView 字体样式
        private Typeface textTypeface;
        // TextView padding 边距 - new int[] { left, top, right, bottom }
        private int[] textPadding;

        // == snackbar_action Button 相关 ==
        // Action Button 的重心
        private int actionGravity;
        // Action Button 文本颜色
        private int actionColor;
        // Action Button 字体大小
        private float actionSize;
        // Action Button padding 边距 - new int[] { left, top, right, bottom }
        private int[] actionPadding;
        // RootView 背景圆角大小
        private float actionCornerRadius;
        // RootView 背景着色颜色
        private int actionBackgroundTintColor;
        // RootView 背景图片
        private Drawable actionBackground;

        // ================

        public StyleBuilder() {
        }

        public StyleBuilder(SnackbarUtils.Style style) {
            if (style != null) {
                // == RootView ==
                // RootView 的重心
                this.rootGravity = style.getRootGravity();
                // RootView 背景圆角大小
                this.rootCornerRadius = style.getRootCornerRadius();
                // RootView 背景着色颜色
                this.rootBackgroundTintColor = style.getRootBackgroundTintColor();
                // RootView 背景图片
                this.rootBackground = style.getRootBackground();
                // RootView margin 边距 - new int[] { left, top, right, bottom }
                this.rootMargin = style.getRootMargin();
                // RootView 透明度
                this.rootAlpha = style.getRootAlpha();

                // == snackbar_text TextView 相关 ==
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
                // TextView padding 边距 - new int[] { left, top, right, bottom }
                this.textPadding = style.getTextPadding();

                // == snackbar_action Button 相关 ==
                // Action Button 的重心
                this.actionGravity = style.getActionGravity();
                // Action Button 文本颜色
                this.actionColor = style.getActionColor();
                // Action Button 字体大小
                this.actionSize = style.getActionSize();
                // Action Button padding 边距 - new int[] { left, top, right, bottom }
                this.actionPadding = style.getActionPadding();
                // RootView 背景圆角大小
                this.actionCornerRadius = style.getActionCornerRadius();
                // RootView 背景着色颜色
                this.actionBackgroundTintColor = style.getActionBackgroundTintColor();
                // RootView 背景图片
                this.actionBackground = style.getActionBackground();
            }
        }

        // == get/set ==

        @Override
        public int getRootGravity() {
            return rootGravity;
        }

        public StyleBuilder setRootGravity(int rootGravity) {
            this.rootGravity = rootGravity;
            return this;
        }

        @Override
        public float getRootCornerRadius() {
            return rootCornerRadius;
        }

        public StyleBuilder setRootCornerRadius(float rootCornerRadius) {
            this.rootCornerRadius = rootCornerRadius;
            return this;
        }

        @Override
        public int getRootBackgroundTintColor() {
            return rootBackgroundTintColor;
        }

        public StyleBuilder setRootBackgroundTintColor(int rootBackgroundTintColor) {
            this.rootBackgroundTintColor = rootBackgroundTintColor;
            return this;
        }

        @Override
        public Drawable getRootBackground() {
            return rootBackground;
        }

        public StyleBuilder setRootBackground(Drawable rootBackground) {
            this.rootBackground = rootBackground;
            return this;
        }

        @Override
        public int[] getRootMargin() {
            return rootMargin;
        }

        public StyleBuilder setRootMargin(int[] rootMargin) {
            this.rootMargin = rootMargin;
            return this;
        }

        @Override
        public float getRootAlpha() {
            return rootAlpha;
        }

        public void setRootAlpha(float rootAlpha) {
            this.rootAlpha = rootAlpha;
        }

        @Override
        public int getTextGravity() {
            return textGravity;
        }

        public StyleBuilder setTextGravity(int textGravity) {
            this.textGravity = textGravity;
            return this;
        }

        @Override
        public int getTextColor() {
            return textColor;
        }

        public StyleBuilder setTextColor(int textColor) {
            this.textColor = textColor;
            return this;
        }

        @Override
        public float getTextSize() {
            return textSize;
        }

        public StyleBuilder setTextSize(float textSize) {
            this.textSize = textSize;
            return this;
        }

        @Override
        public int getTextMaxLines() {
            return textMaxLines;
        }

        public StyleBuilder setTextMaxLines(int textMaxLines) {
            this.textMaxLines = textMaxLines;
            return this;
        }

        @Override
        public TextUtils.TruncateAt getTextEllipsize() {
            return textEllipsize;
        }

        public StyleBuilder setTextEllipsize(TextUtils.TruncateAt textEllipsize) {
            this.textEllipsize = textEllipsize;
            return this;
        }

        @Override
        public Typeface getTextTypeface() {
            return textTypeface;
        }

        public StyleBuilder setTextTypeface(Typeface textTypeface) {
            this.textTypeface = textTypeface;
            return this;
        }

        @Override
        public int[] getTextPadding() {
            return textPadding;
        }

        public StyleBuilder setTextPadding(int[] textPadding) {
            this.textPadding = textPadding;
            return this;
        }

        @Override
        public int getActionGravity() {
            return actionGravity;
        }

        public StyleBuilder setActionGravity(int actionGravity) {
            this.actionGravity = actionGravity;
            return this;
        }

        @Override
        public int getActionColor() {
            return actionColor;
        }

        public StyleBuilder setActionColor(int actionColor) {
            this.actionColor = actionColor;
            return this;
        }

        @Override
        public float getActionSize() {
            return actionSize;
        }

        public StyleBuilder setActionSize(float actionSize) {
            this.actionSize = actionSize;
            return this;
        }

        @Override
        public int[] getActionPadding() {
            return actionPadding;
        }

        public StyleBuilder setActionPadding(int[] actionPadding) {
            this.actionPadding = actionPadding;
            return this;
        }

        @Override
        public float getActionCornerRadius() {
            return actionCornerRadius;
        }

        public StyleBuilder setActionCornerRadius(float actionCornerRadius) {
            this.actionCornerRadius = actionCornerRadius;
            return this;
        }

        @Override
        public int getActionBackgroundTintColor() {
            return actionBackgroundTintColor;
        }

        public StyleBuilder setActionBackgroundTintColor(int actionBackgroundTintColor) {
            this.actionBackgroundTintColor = actionBackgroundTintColor;
            return this;
        }

        @Override
        public Drawable getActionBackground() {
            return actionBackground;
        }

        public StyleBuilder setActionBackground(Drawable actionBackground) {
            this.actionBackground = actionBackground;
            return this;
        }
    }

    // ======================
    // ====== 其他接口 ======
    // ======================

    /**
     * detail: Snackbar 样式配置
     * Created by Ttt
     */
    public static abstract class Style {

        // == RootView ==

        /**
         * RootView 的重心
         * @return
         */
        public int getRootGravity() {
            return 0;
        }

        /**
         * RootView 背景圆角大小
         * @return
         */
        public float getRootCornerRadius() {
            return 0f;
        }

        /**
         * RootView 背景着色颜色
         * @return
         */
        public int getRootBackgroundTintColor() {
            return 0;
        }

        /**
         * RootView 背景图片
         * @return
         */
        public Drawable getRootBackground() {
            return null;
        }

        /**
         * RootView margin 边距 - new int[] { left, top, right, bottom }
         * @return
         */
        public int[] getRootMargin() {
            return null;
        }

        /**
         * RootView 透明度
         * @return
         */
        public float getRootAlpha() {
            return 1.0f;
        }

        // == snackbar_text TextView 相关 ==

        /**
         * TextView 的重心
         * @return
         */
        public int getTextGravity() {
            return 0;
        }

        /**
         * TextView 文本颜色
         * @return
         */
        public int getTextColor() {
            return 0;
        }

        /**
         * TextView 字体大小
         * @return
         */
        public float getTextSize() {
            return 0f;
        }

        /**
         * TextView 最大行数
         * @return
         */
        public int getTextMaxLines() {
            return 0;
        }

        /**
         * TextView Ellipsize 效果
         * @return
         */
        public TextUtils.TruncateAt getTextEllipsize() {
            return null;
        }

        /**
         * TextView 字体样式
         * @return
         */
        public Typeface getTextTypeface() {
            return null;
        }

        /**
         * TextView padding 边距 - new int[] { left, top, right, bottom }
         * @return
         */
        public int[] getTextPadding() {
            return null;
        }

        // == snackbar_action Button 相关 ==

        /**
         * Action Button 的重心
         * @return
         */
        public int getActionGravity() {
            return 0;
        }

        /**
         * Action Button 文本颜色
         * @return
         */
        public int getActionColor() {
            return 0;
        }

        /**
         * Action Button 字体大小
         * @return
         */
        public float getActionSize() {
            return 0f;
        }

        /**
         * Action Button padding 边距 - new int[] { left, top, right, bottom }
         * @return
         */
        public int[] getActionPadding() {
            return null;
        }

        /**
         * Action Button 背景圆角大小
         * @return
         */
        public float getActionCornerRadius() {
            return 0f;
        }

        /**
         * Action Button 背景着色颜色
         * @return
         */
        public int getActionBackgroundTintColor() {
            return 0;
        }

        /**
         * Action Button 背景图片
         * @return
         */
        public Drawable getActionBackground() {
            return null;
        }
    }

    // ==============
    // == 设置样式 ==
    // ==============

    /**
     * 设置 Snackbar 样式配置
     * @param snackbar
     * @return
     */
    public Snackbar setSnackbarStyle(Snackbar snackbar) {
        return setSnackbarStyle(snackbar, mStyleBuilder);
    }

    /**
     * 设置 Snackbar 样式配置
     * @param snackbar
     * @param style
     * @return
     */
    public Snackbar setSnackbarStyle(Snackbar snackbar, SnackbarUtils.Style style) {
        if (snackbar == null) { // 防止 Snackbar 为 null
            return snackbar;
        } else if (style == null) { // 防止 Style 为null
            return snackbar;
        }
        // 获取显示的View
        View rootView = snackbar.getView();
        if (rootView != null) {

            // == RootView ==

            // 设置 RootView Gravity 处理
            if (style.getRootGravity() != 0) {
                try {
                    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(rootView.getLayoutParams().width, rootView.getLayoutParams().height);
                    params.gravity = style.getRootGravity();
                    rootView.setLayoutParams(params);
                } catch (Exception e) {
                }
            }

            // 设置 RootView margin 边距
            int[] rootMargin = style.getRootMargin();
            if (rootMargin != null && rootMargin.length == 4) {
                try {
                    ViewGroup.LayoutParams params = rootView.getLayoutParams();
                    ((ViewGroup.MarginLayoutParams) params).setMargins(rootMargin[0], rootMargin[1], rootMargin[2], rootMargin[3]);
                    rootView.setLayoutParams(params);
                } catch (Exception e) {
                }
            }

            // 设置 RootView 透明度
            if (style.getRootAlpha() >= 0f) {
                float rootAlpha = style.getRootAlpha();
                rootAlpha = rootAlpha >= 1.0f ? 1.0f :(rootAlpha <= 0.0f ? 0.0f : rootAlpha);
                rootView.setAlpha(rootAlpha);
            }

            // 设置 RootView 背景相关
            // 获取背景图片
            Drawable rootBackgroundDrawable = style.getRootBackground();
            // 如果等于 null
            if (rootBackgroundDrawable != null) {
                // 设置背景
                setBackground(rootView, rootBackgroundDrawable);
            } else {
                if (style.getRootBackgroundTintColor() != 0) {
                    GradientDrawable drawable = new GradientDrawable();
                    // 设置背景色
                    drawable.setColor(style.getRootBackgroundTintColor());
                    // 设置圆角大小
                    drawable.setCornerRadius(style.getRootCornerRadius());
                    // 设置背景
                    setBackground(rootView, drawable);
                }
            }

            // == snackbar_text TextView 相关 ==

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

            // == snackbar_action Button 相关 ==

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
                    setBackground(actionButton, actionBackgroundDrawable);
                } else {
                    if (style.getActionBackgroundTintColor() != 0) {
                        GradientDrawable drawable = new GradientDrawable();
                        // 设置背景色
                        drawable.setColor(style.getActionBackgroundTintColor());
                        // 设置圆角大小
                        drawable.setCornerRadius(style.getActionCornerRadius());
                        // 设置背景
                        setBackground(actionButton, drawable);
                    }
                }
            }
        }
        return snackbar;
    }

    // ==

    // View 坐标
    private int[] mViewLocations = null;
    // View 高度
    private int mViewHeight = 0;
    // 指定 View 坐标, 显示的重心方向 (只有 TOP、BOTTOM)
    private int mViewGravity = -1;
    // 追加向上边距 (如: 状态栏高度)
    private int mAppendTopMargin = 0;
    // View 阴影
    private int mShadowMargin = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) ? 2 : 0;
    // 判断是否自动计算 (如: 显示在 View 下面, 但是下方距离不够, 自动设置为在 View 上方显示)
    private boolean mAutoCalc = true;

    /**
     * 获取阴影边距
     * @return
     */
    public int getShadowMargin() {
        return mShadowMargin;
    }

    /**
     * 设置阴影边距
     * @param shadowMargin
     * @return
     */
    public SnackbarUtils setShadowMargin(int shadowMargin) {
        this.mShadowMargin = shadowMargin;
        return this;
    }

    /**
     * 判断是否自动计算边距 (如: 显示在 View 下面, 但是下方距离不够, 自动设置为在 View 上方显示)
     * @return
     */
    public boolean isAutoCalc() {
        return mAutoCalc;
    }

    /**
     * 设置是否自动计算边距 (如: 显示在 View 下面, 但是下方距离不够, 自动设置为在 View 上方显示)
     * @param autoCalc
     * @return
     */
    public SnackbarUtils setAutoCalc(boolean autoCalc) {
        this.mAutoCalc = autoCalc;
        return this;
    }

    /**
     * 清空坐标相关信息
     */
    private void clearLocations(){
        // 重置处理
        mViewHeight = 0;
        mViewGravity = -1;
        mViewLocations = null;
        mAppendTopMargin = 0;
    }

    /**
     * 设置 Snackbar 显示在指定 View 的上方
     * @param targetView
     * @param appendTopMargin 追加边距(如: 状态栏高度) {@link ScreenUtils#getStatusBarHeight}
     * @return
     */
    public SnackbarUtils above(View targetView, int appendTopMargin){
        // 清空重置处理
        clearLocations();
        // 防止为 null
        if (targetView != null){
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
     * @param targetView
     * @param appendTopMargin 追加边距(如: 状态栏高度) {@link ScreenUtils#getStatusBarHeight}
     * @return
     */
    public SnackbarUtils bellow(View targetView, int appendTopMargin){
        // 清空重置处理
        clearLocations();
        // 防止为 null
        if (targetView != null){
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
     * @param snackbar
     */
    private void setSnackbarLocation(Snackbar snackbar){
        // 获取显示的View
        View rootView = snackbar.getView();
        if (rootView != null) {
            // == 特殊处理 ==
            // 属于显示在指定 View 坐标, 对应重心方向
            if (mViewLocations != null && mViewGravity != -1 && mViewHeight > 0) {
                // View (坐标)边距
                int[] margin = new int[4];
                // 判断 Style 是否为null
                if (mStyleBuilder != null){
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
                int screenHeight = getScreenHeight(DevUtils.getContext());
                // 防止等于 0
                if (screenHeight != 0) {
                    // 获取测量高度(不一定准确)
                    int measuredHeight = getMeasuredHeight(rootView);
                    // 判断方向, 在指定坐标上方, 判断是否够空间
                    if (mViewGravity == Gravity.TOP){
                        // 判断是否超出可显示高度
                        if (mViewTop - mShadowMargin - mAppendTopMargin >= measuredHeight){
                            // 思路: 没有超出高度, 则正常显示在指定View 上方
                            // 改为布局居下(相反方向), 然后设置 bottomMargin 为 屏幕高度 - view mWindowTop + 阴影大小
                            // 这样的思路，主要是只用知道 view 的 Y 轴位置, 然后用屏幕高度减去 y 得到的就是需要向下的边距, 不需要计算 Snackbar View 高度
                            try {
                                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(rootView.getLayoutParams().width, rootView.getLayoutParams().height);
                                params.gravity = Gravity.BOTTOM;
                                rootView.setLayoutParams(params);
                            } catch (Exception e) {
                            }
                            try {
                                ViewGroup.LayoutParams params = rootView.getLayoutParams();
                                ((ViewGroup.MarginLayoutParams) params).setMargins(margin[0], 0, margin[2], screenHeight - mViewTop + mShadowMargin);
                                rootView.setLayoutParams(params);
                            } catch (Exception e) {
                            }
                        } else { // 超出可视范围
                            // 判断是否自动计算处理
                            if (mAutoCalc) {
                                // 思路如上: 超出高度后, 则直接设置居上, 计算边距则 view mWindowTop - 追加边距(状态栏高度) + view height, 设置到View 的下方
                                // 计算处理主要是, 只需要知道 view Y 轴位置 + view height - 追加边距(状态栏高度) = 需要的边距
                                // 为什么需要减 状态栏高度, 是因为 view Y （view mWindowTop） 就包含状态栏的高度信息
                                try {
                                    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(rootView.getLayoutParams().width, rootView.getLayoutParams().height);
                                    params.gravity = Gravity.TOP;
                                    rootView.setLayoutParams(params);
                                } catch (Exception e) {
                                }
                                try {
                                    ViewGroup.LayoutParams params = rootView.getLayoutParams();
                                    ((ViewGroup.MarginLayoutParams) params).setMargins(margin[0], mViewTop - mAppendTopMargin + mViewHeight, margin[2], 0);
                                    rootView.setLayoutParams(params);
                                } catch (Exception e) {
                                }
                            }
                        }
                    } else { // 在指定坐标下方
                        // 判断是否超出可显示高度
                        if (screenHeight - (mViewTop + mShadowMargin + mAppendTopMargin + mViewHeight) >= measuredHeight){
                            // 思路: 没有超出高度, 则正常显示在指定View 下方
                            // 并且改为布局居上, 然后设置 topMargin 为 view mWindowTop - (阴影大小 + 追加边距(状态栏高度))
                            // 这样的思路，主要是不居下，不用知道 Snackbar view 高度, 导致向下边距计算错误，转换思路从上处理
                            try {
                                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(rootView.getLayoutParams().width, rootView.getLayoutParams().height);
                                params.gravity = Gravity.TOP;
                                rootView.setLayoutParams(params);
                            } catch (Exception e) {
                            }
                            try {
                                ViewGroup.LayoutParams params = rootView.getLayoutParams();
                                ((ViewGroup.MarginLayoutParams) params).setMargins(margin[0], mViewTop - (mShadowMargin + mAppendTopMargin), margin[2], 0);
                                rootView.setLayoutParams(params);
                            } catch (Exception e) {
                            }
                        } else { // 超出可视范围
                            // 判断是否自动计算处理
                            if (mAutoCalc){
                                // 思路如上: 超出高度后, 则直接设置居下, 计算边距则 用屏幕高度 - view mWindowTop + 阴影边距
                                // 计算处理的值则是 view mWindowTop 距离底部的边距, 刚好设置 bottomMargin, 实现思路转换处理
                                try {
                                    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(rootView.getLayoutParams().width, rootView.getLayoutParams().height);
                                    params.gravity = Gravity.BOTTOM;
                                    rootView.setLayoutParams(params);
                                } catch (Exception e) {
                                }
                                try {
                                    ViewGroup.LayoutParams params = rootView.getLayoutParams();
                                    ((ViewGroup.MarginLayoutParams) params).setMargins(margin[0], 0, margin[2], screenHeight - mViewTop + mShadowMargin);
                                    rootView.setLayoutParams(params);
                                } catch (Exception e) {
                                }
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
     * 获取屏幕的高度(单位：px)
     * @param context
     * @return 屏幕高
     */
    private int getScreenHeight(Context context) {
        try {
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            if (windowManager == null) {
                return context.getResources().getDisplayMetrics().heightPixels;
            }
            Point point = new Point();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                windowManager.getDefaultDisplay().getRealSize(point);
            } else {
                windowManager.getDefaultDisplay().getSize(point);
            }
            return point.y;
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * 测量视图尺寸
     * @param view 视图
     * @return arr[0]: 视图宽度, arr[1]: 视图高度
     */
    private int[] measureView(final View view) {
        try {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams == null) {
                layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
            int widthSpec = ViewGroup.getChildMeasureSpec(0, 0, layoutParams.width);
            int lpHeight = layoutParams.height;
            int heightSpec;
            if (lpHeight > 0) {
                heightSpec = View.MeasureSpec.makeMeasureSpec(lpHeight, View.MeasureSpec.EXACTLY);
            } else {
                heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            }
            view.measure(widthSpec, heightSpec);
            return new int[]{ view.getMeasuredWidth(), view.getMeasuredHeight() };
        } catch (Exception e) {
            return new int[] { 0, 0 };
        }
    }

    /**
     * 获取测量视图高度
     * @param view 视图
     * @return 视图高度
     */
    private int getMeasuredHeight(final View view) {
        return measureView(view)[1];
    }
}
