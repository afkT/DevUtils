package utils_use.toast;

import android.app.Application;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;

import afkt.project.R;
import dev.utils.app.toast.toaster.DevToast;
import dev.utils.app.toast.toaster.IToast;

/**
 * detail: Toast 使用方法
 * @author Ttt
 */
public final class DevToastUse {

    private DevToastUse() {
    }

    // ========
    // = 配置 =
    // ========

    private static final View view   = null;
    private static final int  viewId = 0;

    /**
     * Toast 配置相关
     */
    private void toastConfig(Application application) {
        // 初始化 Toast - DevUtils 内部已经调用
        DevToast.init(application); // 必须调用

        // 初始化 Toast 样式 - 全局通用
        // DevToast.initStyle(new IToast.Style() {}); // 可以实现 IToast.Style 接口, 参照 DefaultToastStyle

        // 当 Toast 内容为 null 时, 显示的内容
        DevToast.setNullText("text is null");

        // 是否设置 Handler 显示 Toast - 默认 true, 支持子线程显示 Toast
        DevToast.setIsHandler(true);

        // 设置文本长度限制, 超过设置的位数则 为 LENGTH_LONG
        DevToast.setTextLength(15);

        // 支持自定义 View - 可不配置, 默认使用系统 Toast View
        DevToast.setView(view);
        DevToast.setView(viewId);

        // 配置 Toast 过滤, 判断是否显示 Toast、以及内容处理
        // DevToast.initToastFilter(new IToast.Filter() {});

        // 恢复默认配置
        DevToast.reset();
    }

    // = 使用 =

    /**
     * Toast 使用方法
     */
    public static void toastUse() {
        // 显示 Toast
        DevToast.show(view);
        DevToast.show(R.string.app_name);
        DevToast.show("Toast"); // initStyle - Toast

        // 使用特殊样式 - 默认统一全局样式, style 则为 这个 Toast 单独为这个样式
        DevToast.style(new TempStyle()).show("tempStyle - Toast");

        // 获取 当前全局使用的样式
        DevToast.getToastStyle();

        // 获取默认样式
        DevToast.defaultStyle();

        // 关闭正在显示的 Toast
        DevToast.cancel();

        // 不同效果, 可通过实现 IToast.Style 自定义样式并初始化 initStyle/style 查看效果

        // 默认不设置 initStyle, 会使用 defaultStyle
    }

    /**
     * 自定义实现样式
     */
    private static class TempStyle
            implements IToast.Style {

        /**
         * Toast 的重心
         * @return
         */
        @Override
        public int getGravity() {
            return 0;
        }

        /**
         * X 轴偏移
         * @return
         */
        @Override
        public int getXOffset() {
            return 0;
        }

        /**
         * Y 轴偏移
         * @return
         */
        @Override
        public int getYOffset() {
            return 0;
        }

        /**
         * 获取水平边距
         * @return
         */
        @Override
        public int getHorizontalMargin() {
            return 0;
        }

        /**
         * 获取垂直边距
         * @return
         */
        @Override
        public int getVerticalMargin() {
            return 0;
        }

        /**
         * Toast Z 轴坐标阴影
         * @return
         */
        @Override
        public int getZ() {
            return 0;
        }

        /**
         * 圆角大小
         * @return
         */
        @Override
        public float getCornerRadius() {
            return 5f;
        }

        /**
         * 背景着色颜色
         * @return
         */
        @Override
        public int getBackgroundTintColor() {
            return 0xB2000000;
        }

        /**
         * 背景图片
         * @return
         */
        @Override
        public Drawable getBackground() {
            return null;
        }

        // = TextView 相关 =

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
         * TextView padding 边距 - new int[] { left, top, right, bottom }
         * @return
         */
        @Override
        public int[] getPadding() {
            return new int[]{25, 10, 25, 10};
        }
    }
}