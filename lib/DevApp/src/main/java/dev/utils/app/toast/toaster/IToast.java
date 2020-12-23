package dev.utils.app.toast.toaster;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.LayoutRes;
import androidx.annotation.StringRes;

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
         * @param isHandler {@code true} 使用, {@code false} 不使用
         */
        void setIsHandler(boolean isHandler);

        /**
         * 设置 Text 为 null 的文本
         * @param nullText 显示内容为 null 时, 使用的提示值
         */
        void setNullText(String nullText);

        /**
         * 设置 Toast 文案长度转换 显示时间
         * @param textLengthConvertDuration Toast 文案长度转换界限
         */
        void setTextLength(int textLengthConvertDuration);

        // =

        /**
         * 初始化调用
         * @param context {@link Context}
         */
        void init(Context context);

        // ===========
        // = 配置方法 =
        // ===========

        /**
         * 使用单次 Toast 样式配置
         * @param toastStyle Toast 样式
         * @return {@link IToast.Operate}
         */
        IToast.Operate style(IToast.Style toastStyle);

        /**
         * 使用默认 Toast 样式
         * @return {@link IToast.Operate}
         */
        IToast.Operate defaultStyle();

        /**
         * 获取 Toast 样式配置
         * @return Toast 样式配置
         */
        IToast.Style getToastStyle();

        /**
         * 初始化 Toast 样式配置
         * @param toastStyle Toast 样式配置
         */
        void initStyle(IToast.Style toastStyle);

        /**
         * 初始化 Toast 过滤器
         * @param toastFilter Toast 过滤器
         */
        void initToastFilter(IToast.Filter toastFilter);

        /**
         * 设置 Toast 显示的 View
         * @param view Toast 显示的 View
         */
        void setView(View view);

        /**
         * 设置 Toast 显示的 View
         * @param layoutId R.layout.id
         */
        void setView(@LayoutRes int layoutId);

        // ===========
        // = 操作方法 =
        // ===========

        /**
         * 显示 Toast
         * @param text       Toast 提示文本
         * @param formatArgs 格式化参数
         */
        void show(
                String text,
                Object... formatArgs
        );

        /**
         * 显示 R.string.id Toast
         * @param resId      R.string.id
         * @param formatArgs 格式化参数
         */
        void show(
                @StringRes int resId,
                Object... formatArgs
        );

        // =

        /**
         * 通过 View 显示 Toast
         * @param view Toast 显示的 View
         */
        void show(View view);

        /**
         * 通过 View 显示 Toast
         * @param view     Toast 显示的 View
         * @param duration Toast 显示时长 {@link Toast#LENGTH_SHORT}、{@link Toast#LENGTH_LONG}
         */
        void show(
                View view,
                int duration
        );

        // =

        /**
         * 取消当前显示的 Toast
         */
        void cancel();
    }

    // ===========
    // = 其他接口 =
    // ===========

    /**
     * detail: Toast 样式配置
     * @author Ttt
     */
    public interface Style {

        /**
         * 获取 Toast 的重心
         * @return Toast 的重心
         */
        int getGravity();

        /**
         * 获取 X 轴偏移
         * @return X 轴偏移
         */
        int getXOffset();

        /**
         * 获取 Y 轴偏移
         * @return Y 轴偏移
         */
        int getYOffset();

        /**
         * 获取水平边距
         * @return 水平边距
         */
        int getHorizontalMargin();

        /**
         * 获取垂直边距
         * @return 垂直边距
         */
        int getVerticalMargin();

        /**
         * 获取 Toast Z 轴坐标阴影
         * @return Toast Z 轴坐标阴影
         */
        int getZ();

        /**
         * 获取圆角大小
         * @return 圆角大小
         */
        float getCornerRadius();

        /**
         * 获取背景着色颜色
         * @return 背景着色颜色
         */
        @ColorInt
        int getBackgroundTintColor();

        /**
         * 获取背景图片
         * @return 背景图片
         */
        Drawable getBackground();

        // ================
        // = TextView 相关 =
        // ================

        /**
         * 获取文本颜色
         * @return 文本颜色
         */
        @ColorInt
        int getTextColor();

        /**
         * 获取字体大小
         * @return 字体大小
         */
        float getTextSize();

        /**
         * 获取最大行数
         * @return 最大行数
         */
        int getMaxLines();

        /**
         * 获取 Ellipsize 效果
         * @return Ellipsize 效果
         */
        TextUtils.TruncateAt getEllipsize();

        /**
         * 获取字体样式
         * @return 字体样式
         */
        Typeface getTypeface();

        /**
         * 获取 TextView padding 边距 ( new int[] { left, top, right, bottom } )
         * @return TextView padding 边距
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
         * @param view Toast 显示的 View
         * @return {@code true} 接着执行, {@code false} 过滤不处理
         */
        boolean filter(View view);

        /**
         * 判断是否显示
         * @param content Toast 显示文案
         * @return {@code true} 接着执行, {@code false} 过滤不处理
         */
        boolean filter(String content);

        /**
         * 获取 Toast 显示的文案
         * @param content Toast 显示文案
         * @return 处理后的内容
         */
        String handlerContent(String content);
    }
}