package dev.utils.app;

import android.os.Build;
import android.text.Html;
import android.widget.TextView;

import dev.utils.LogPrintUtils;

/**
 * detail: Html 工具类
 * @author Ttt
 */
public final class HtmlUtils {

    private HtmlUtils() {
    }

    // 日志 TAG
    private static final String TAG = HtmlUtils.class.getSimpleName();

    /**
     * 设置 Html 内容
     * @param textView {@link TextView}
     * @param content  Html content
     * @param <T>      泛型
     */
    public static <T extends TextView> void setHtmlText(final T textView, final String content) {
        if (textView != null && content != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                textView.setText(Html.fromHtml(content, Html.FROM_HTML_MODE_LEGACY));
            } else {
                textView.setText(Html.fromHtml(content));
            }
        }
    }

    // =

    /**
     * 为给定的字符串添加 HTML 颜色标记
     * @param content 给定的字符串
     * @param color   颜色值, 如: #000000
     * @return Html 内容字符串
     */
    public static String addHtmlColor(final String content, final String color) {
        return "<font color=\"" + color + "\">" + content + "</font>";
    }

    /**
     * 为给定的字符串添加 HTML 颜色标记
     * @param format  格式化字符串
     * @param content 给定的字符串
     * @param color   颜色值, 如: #000000
     * @return Html 内容字符串
     */
    public static String addHtmlColor(final String format, final String content, final String color) {
        return String.format(format, addHtmlColor(content, color));
    }

    /**
     * 为给定的字符串添加 HTML 加粗标记
     * @param content 给定的字符串
     * @return Html 内容字符串
     */
    public static String addHtmlBold(final String content) {
        return "<b>" + content + "</b>";
    }

    /**
     * 为给定的字符串添加 HTML 颜色标记并加粗
     * @param content 给定的字符串
     * @param color   颜色值, 如: #000000
     * @return Html 内容字符串
     */
    public static String addHtmlColorAndBlod(final String content, final String color) {
        return addHtmlBold(addHtmlColor(content, color));
    }

    /**
     * 为给定的字符串添加 HTML 下划线
     * @param content 给定的字符串
     * @return Html 内容字符串
     */
    public static String addHtmlUnderline(final String content) {
        return "<u>" + content + "</u>";
    }

    /**
     * 为给定的字符串添加 HTML 字体倾斜
     * <pre>
     *     如果需要倾斜自定义角度, 需要自定义 TextView, 在 onDraw 里面加上
     *     - 倾斜度, 上下左右居中
     *     canvas.rotate( 倾斜角度, getMeasuredWidth() / 3, getMeasuredHeight() / 3);
     * </pre>
     * @param content 给定的字符串
     * @return Html 内容字符串
     */
    public static String addHtmlIncline(final String content) {
        return "<i>" + content + "</i>";
    }

    // =

    /**
     * 将给定的字符串中所有给定的关键字标色
     * @param content 给定的字符串
     * @param keyword 给定的关键字
     * @param color   颜色值, 如: #000000
     * @return Html 内容字符串
     */
    public static String keywordReplaceHtmlColor(final String content, final String keyword, final String color) {
        return replaceStr(content, keyword, addHtmlColor(keyword, color));
    }

    // ======================
    // = 其他工具类实现代码 =
    // ======================

    // ===============
    // = StringUtils =
    // ===============

    /**
     * 判断字符串是否为 null
     * @param str 待校验的字符串
     * @return {@code true} is null, {@code false} not null
     */
    private static boolean isEmpty(final String str) {
        return (str == null || str.length() == 0);
    }

    // ==================
    // = DevCommonUtils =
    // ==================

    /**
     * 替换字符串
     * @param str     待处理字符串
     * @param suffix  匹配判断字符串
     * @param replace 替换的内容
     * @return 处理后的字符串
     */
    private static String replaceStr(final String str, final String suffix, final String replace) {
        // 如果替换的内容或者判断的字符串为 null, 则直接跳过
        if (!isEmpty(str) && !isEmpty(suffix) && replace != null && !suffix.equals(replace)) {
            try {
                return str.replaceAll(suffix, replace);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "replaceStr");
            }
        }
        return str;
    }
}