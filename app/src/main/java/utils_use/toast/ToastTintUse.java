package utils_use.toast;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.Gravity;

import dev.utils.app.toast.ToastTintUtils;

/**
 * detail: Toast 使用方法
 * @author Ttt
 */
public final class ToastTintUse {

    private ToastTintUse() {
    }

    // ========
    // = 配置 =
    // ========

    private static ToastTintUtils.Style style;
    private static Drawable             iconDrawable;

    /**
     * Toast 配置相关
     */
    private void toastConfig() {
        // 获取默认样式
        ToastTintUtils.getDefaultStyle();

        // 获取 Normal 样式
        ToastTintUtils.getNormalStyle();
        // 设置 Normal 样式
        ToastTintUtils.setNormalStyle(style);

        // 获取 Error 样式
        ToastTintUtils.getErrorStyle();
        // 设置 Error 样式
        ToastTintUtils.setErrorStyle(style);
        // 获取 Error 样式 小图标
        ToastTintUtils.getErrorDrawable();

        // 获取 Warning 样式
        ToastTintUtils.getWarningStyle();
        // 设置 Warning 样式
        ToastTintUtils.setWarningStyle(style);
        // 获取 Warning 样式 小图标
        ToastTintUtils.getWarningDrawable();

        // 获取 Success 样式
        ToastTintUtils.getSuccessStyle();
        // 设置 Success 样式
        ToastTintUtils.setSuccessStyle(style);
        // 获取 Success 样式 小图标
        ToastTintUtils.getSuccessDrawable();

        // 获取 Info 样式
        ToastTintUtils.getInfoStyle();
        // 设置 Info 样式
        ToastTintUtils.setInfoStyle(style);
        // 获取 Info 样式 小图标
        ToastTintUtils.getInfoDrawable();

        // 是否使用配置 - 如 Gravity、HorizontalMargin、VerticalMargin
        ToastTintUtils.setUseConfig(true);

        // 设置 Gravity
        ToastTintUtils.setGravity(Gravity.BOTTOM, 0, 0);

        // 当 Toast 内容为 null 时, 显示的内容
        ToastTintUtils.setNullText("text is null");

        // 是否设置 Handler 显示 Toast - 默认 true, 支持子线程显示 Toast
        ToastTintUtils.setIsHandler(true);

        // 设置 HorizontalMargin、VerticalMargin 边距
        ToastTintUtils.setMargin(0f, 0f);

        // 配置 Toast 过滤, 判断是否显示 Toast、以及内容处理
        // ToastTintUtils.setToastFilter(new ToastTintUtils.Filter() {});

        // 恢复默认配置
        ToastTintUtils.reset();
    }

    // = 使用 =

    /**
     * Toast 使用方法
     */
    public static void toastUse() {
        // 显示 Success 样式 Toast
        ToastTintUtils.success("Success Style Toast");

        // 显示 Error 样式 Toast
        ToastTintUtils.error("Error Style Toast");

        // 显示 Info 样式 Toast
        ToastTintUtils.info("Info Style Toast");

        // 显示 Normal 样式 Toast
        ToastTintUtils.normal("Normal Style Toast");

        // 显示 Warning 样式 Toast
        ToastTintUtils.warning("Warning Style Toast");

        // 显示 Custom 样式 Toast
        ToastTintUtils.custom(style, "Custom Style Toast");

        // 显示 Custom 样式 Toast, 自定义小图标
        ToastTintUtils.custom(new TempStyle(), "Custom Style Toast", iconDrawable);
    }

    /**
     * 自定义实现样式
     * {@link ToastTintUtils.SuccessStyle}
     * {@link ToastTintUtils.ErrorStyle}
     * {@link ToastTintUtils.InfoStyle}
     * {@link ToastTintUtils.WarningStyle}
     * {@link ToastTintUtils.NormalStyle}
     * {@link ToastTintUtils.DefaultStyle}
     */
    private static class TempStyle
            implements ToastTintUtils.Style {

        /**
         * 文本颜色
         * @return
         */
        @Override
        public int getTextColor() {
            return Color.WHITE;
        }

        /**
         * 字体大小
         * @return
         */
        @Override
        public float getTextSize() {
            return 16f;
        }

        /**
         * 背景着色颜色
         * @return
         */
        @Override
        public int getBackgroundTintColor() {
            return 0;
        }

        /**
         * 背景图片
         * @return
         */
        @Override
        public Drawable getBackground() {
            return null;
        }

        /**
         * 最大行数
         * @return
         */
        @Override
        public int getMaxLines() {
            return 0;
        }

        /**
         * Ellipsize 效果
         * @return
         */
        @Override
        public TextUtils.TruncateAt getEllipsize() {
            return null;
        }

        /**
         * 字体样式
         * @return
         */
        @Override
        public Typeface getTypeface() {
            // return Typeface.create("sans-serif-condensed", Typeface.NORMAL);
            return null;
        }

        /**
         * 获取图标着色颜色
         * @return
         */
        @Override
        public int getTintIconColor() {
            return Color.WHITE;
        }

        /**
         * 是否渲染图标 ( getTintIconColor() 着色渲染 )
         * @return {@code true} yes, {@code false} no
         */
        @Override
        public boolean isTintIcon() {
            return false;
        }
    }
}