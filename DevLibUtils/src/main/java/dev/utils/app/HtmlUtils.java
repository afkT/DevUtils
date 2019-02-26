package dev.utils.app;

import android.text.Html;
import android.widget.TextView;

/**
 * detail: Html 工具类
 * Created by Ttt
 */
public final class HtmlUtils {

    private HtmlUtils() {
    }

    /**
     * 设置内容, 最终做处理
     * @param textView
     * @param content
     */
    public static void setHtmlText(TextView textView, String content) {
        if (textView != null && content != null) {
            textView.setText(Html.fromHtml(content));
        }
    }

    /**
     * 为给定的字符串添加HTML颜色标记
     * @param content 给定的字符串
     * @return 最后放在 Html.fromHtml();内
     */
    public static String addHtmlColor(String content, String color) {
        return "<font color=\""+ color +"\">" + content + "</font>";
    }

    /**
     * 为给定的字符串添加HTML颜色标记
     * @param content 给定的字符串
     * @param fString 格式化的内容
     * @return 最后放在 Html.fromHtml();内
     */
    public static String addHtmlColor(String content, String fString, String color) {
        return String.format(content, ("<font color=\"" + color + "\">"+ fString +"</font>"));
    }

    /**
     * 为给定的字符串添加HTML加粗标记
     * @param content 给定的字符串
     * @return 最后放在 Html.fromHtml();内
     * ======================
     * 这种方式也可以加粗,但是是所有字体加粗(非部分字体加粗)
     * android.text.TextPaint tp = textView.getPaint();
     * tp.setFakeBoldText(true);
     */
    public static String addHtmlBold(String content) {
        return "<b>" + content + "</b>";
    }

    /**
     * 为给定的字符串添加HTML颜色标记,以及加粗
     * @param content 给定的字符串
     * @return 最后放在 Html.fromHtml();内
     */
    public static String addHtmlColorAndBlod(String content, String color) {
        return "<b><font color=\""+ color +"\">" + content + "</font></b>";
    }

    /**
     * 为给定的字符串添加HTML下划线
     * @param content 给定的字符串
     * @return 最后放在 Html.fromHtml();内
     */
    public static String addHtmlUnderline(String content) {
        return "<u>" + content + "</u>";
    }

    /**
     * 为给定的字符串添加HTML 字体倾斜
     * @param content 给定的字符串
     * @return 最后放在 Html.fromHtml();内
     * =================================
     * 如果需要倾斜自定义角度，需要自定义TextView,在onDraw里面加上
     * - 倾斜度,上下左右居中
     * canvas.rotate(倾斜角度, getMeasuredWidth() / 3, getMeasuredHeight() / 3);
     */
    public static String addHtmlIncline(String content) {
        return "<i>" + content + "</i>";
    }

    /**
     * 将给定的字符串中所有给定的关键字标色
     * @param source 给定的字符串
     * @param keyword 给定的关键字
     * @param color 需要标记的颜色
     * @return
     */
    public static String keywordMadeRed(String source, String keyword, String color) {
        return keywordReplaceAll(source, keyword, ("<font color=\"" + color + "\">" + keyword + "</font>"));
    }

    /**
     * 将给定的字符串中所有给定的关键字进行替换内容
     * @param source 给定的字符串
     * @param keyword 给定的关键字
     * @param replacement 替换的内容
     * @return 返回的是带Html标签的字符串，在使用时要通过Html.fromHtml() 转换为Spanned对象再传递给TextView对象
     */
    public static String keywordReplaceAll(String source, String keyword, String replacement) {
        try {
            if (source != null && source.trim().length() != 0) {
                if (keyword != null && keyword.trim().length() != 0) {
                    if (replacement != null && replacement.trim().length() != 0) {
                        return source.replaceAll(keyword , replacement);
                    }
                }
            }
        } catch (Exception e) {
        }
        return source;
    }
}
