package dev.utils.app;

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
     * @param <T>      范型
     */
    public static <T extends TextView> void setHtmlText(final T textView, final String content) {
        if (textView != null && content != null) {
            textView.setText(Html.fromHtml(content));
        }
    }

    // =

    /**
     * 为给定的字符串添加 HTML 颜色标记
     * @param content 给定的字符串
     * @param color   颜色值, 如: #000000
     * @return 最后放在 Html.fromHtml() 内
     */
    public static String addHtmlColor(final String content, final String color) {
        return "<font color=\"" + color + "\">" + content + "</font>";
    }

    /**
     * 为给定的字符串添加 HTML 颜色标记
     * @param format  格式化字符串
     * @param content 给定的字符串
     * @param color   颜色值, 如: #000000
     * @return 最后放在 Html.fromHtml() 内
     */
    public static String addHtmlColor(final String format, final String content, final String color) {
        return String.format(format, addHtmlColor(content, color));
    }

    /**
     * 为给定的字符串添加 HTML 加粗标记
     * @param content 给定的字符串
     * @return 最后放在 Html.fromHtml() 内
     */
    public static String addHtmlBold(final String content) {
        return "<b>" + content + "</b>";
    }

    /**
     * 为给定的字符串添加 HTML 颜色标记并加粗
     * @param content 给定的字符串
     * @param color   颜色值, 如: #000000
     * @return 最后放在 Html.fromHtml() 内
     */
    public static String addHtmlColorAndBlod(final String content, final String color) {
        return addHtmlBold(addHtmlColor(content, color));
    }

    /**
     * 为给定的字符串添加 HTML 下划线
     * @param content 给定的字符串
     * @return 最后放在 Html.fromHtml() 内
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
     * @return 最后放在 Html.fromHtml() 内
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
     * @return 最后放在 Html.fromHtml() 内
     */
    public static String keywordMadeRed(final String content, final String keyword, final String color) {
        return keywordReplaceAll(content, keyword, addHtmlColor(keyword, color));
    }

    /**
     * 将给定的字符串中所有给定的关键字进行替换内容
     * @param content     给定的字符串
     * @param keyword     给定的关键字
     * @param replacement 替换的内容
     * @return 带 HTML 标签的字符串, 在使用时要通过 Html.fromHtml() 转换为 Spanned 对象再传递给 TextView 对象
     */
    public static String keywordReplaceAll(final String content, final String keyword, final String replacement) {
        try {
            if (content != null && content.trim().length() != 0) {
                if (keyword != null && keyword.trim().length() != 0) {
                    if (replacement != null && replacement.trim().length() != 0) {
                        return content.replaceAll(keyword, replacement);
                    }
                }
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "keywordReplaceAll");
        }
        return content;
    }
}