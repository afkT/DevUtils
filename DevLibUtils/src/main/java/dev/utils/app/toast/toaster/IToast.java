package dev.utils.app.toast.toaster;

import android.app.Application;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;

/**
 * detail: Toast 对外提供接口方法
 * @author Ttt
 */
public final class IToast {

    private IToast() {
    }

    /**
     * detail: Toast 对外公开操作方法
     * @author Ttt
     */
    public interface Operate {

        /**
         * 重置默认参数
         */
        void reset();

        /**
         * 设置是否使用 Handler 显示 Toast
         * @param isHandler
         */
        void setIsHandler(boolean isHandler);

        /**
         * 设置 Text 为 null 的文本
         * @param nullText
         */
        void setNullText(String nullText);

        /**
         * 设置 Toast 文案长度转换 显示时间
         * @param textLengthConvertDuration
         */
        void setTextLength(int textLengthConvertDuration);

        // =

        /**
         * Application 初始化调用
         * @param application
         */
        void init(Application application);

        // ============
        // = 配置方法 =
        // ============

        /**
         * 使用单次 Toast 样式配置
         * @param toastStyle Toast 样式
         * @return
         */
        IToast.Operate style(IToast.Style toastStyle);

        /**
         * 使用默认 Toast 样式
         * @return
         */
        IToast.Operate defaultStyle();

        /**
         * 获取 Toast 样式配置
         * @return Toast 样式配置
         */
        IToast.Style getToastStyle();

        /**
         * 初始化 Toast 样式配置(非单次,一直持续)
         * @param toastStyle Toast 样式配置
         */
        void initStyle(IToast.Style toastStyle);

        /**
         * 初始化 Toast 过滤器
         * @param toastFilter
         */
        void initToastFilter(IToast.Filter toastFilter);

        /**
         * 设置 Toast 显示的View
         * @param view
         */
        void setView(View view);

        /**
         * 设置 Toast 显示的View
         * @param layoutId
         */
        void setView(int layoutId);

        // ============
        // = 操作方法 =
        // ============

        /**
         * 显示 Toast
         * @param content
         * @param args
         */
        void show(String content, Object... args);

        /**
         * 显示 R.string.resId Toast
         * @param resId
         * @param args
         */
        void show(int resId, Object... args);

        // =

        /**
         * 通过 View 显示 Toast
         * @param view
         */
        void show(View view);

        /**
         * 通过 View 显示 Toast
         * @param view
         * @param duration
         */
        void show(View view, int duration);

        // =

        /**
         * 取消当前显示的 Toast
         */
        void cancel();
    }

    // ============
    // = 其他接口 =
    // ============

    /**
     * detail: Toast 样式配置
     * @author Ttt
     */
    public interface Style {

        /**
         * Toast 的重心
         * @return
         */
        int getGravity();

        /**
         * X轴偏移
         * @return
         */
        int getXOffset();

        /**
         * Y轴偏移
         * @return
         */
        int getYOffset();

        /**
         * 获取水平边距
         * @return
         */
        int getHorizontalMargin();

        /**
         * 获取垂直边距
         * @return
         */
        int getVerticalMargin();

        /**
         * Toast Z轴坐标阴影
         * @return
         */
        int getZ();

        /**
         * 圆角大小
         * @return
         */
        float getCornerRadius();

        /**
         * 背景着色颜色
         * @return
         */
        int getBackgroundTintColor();

        /**
         * 背景图片
         * @return
         */
        Drawable getBackground();

        // =================
        // = TextView 相关 =
        // =================

        /**
         * 文本颜色
         * @return
         */
        int getTextColor();

        /**
         * 字体大小
         * @return
         */
        float getTextSize();

        /**
         * 最大行数
         * @return
         */
        int getMaxLines();

        /**
         * Ellipsize 效果
         * @return
         */
        TextUtils.TruncateAt getEllipsize();

        /**
         * 字体样式
         * @return
         */
        Typeface getTypeface();

        /**
         * TextView padding 边距 - new int[] { left, top, right, bottom }
         * @return
         */
        int[] getPadding();
    }

    /**
     * detail: Toast 过滤器
     * @author Ttt
     */
    public interface Filter {

        /**
         * 判断是否显示
         * @param view
         * @return {@code true} 接着执行, {@code false} 过滤不处理
         */
        boolean filter(View view);

        /**
         * 判断是否显示
         * @param content
         * @return {@code true} 接着执行, {@code false} 过滤不处理
         */
        boolean filter(String content);

        /**
         * 获取 Toast 显示的文案
         * @param content
         * @return 处理后的内容
         */
        String handlerContent(String content);
    }
}
