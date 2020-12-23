package dev.utils.common;

/**
 * detail: Html 工具类
 * @author Ttt
 */
public final class HtmlUtils {

    private HtmlUtils() {
    }

    // 移除 padding、margin style
    public static final String REMOVE_PADDING_MARGIN_STYLE = "<style>*{ margin:0; padding:0; }</style>";

    /**
     * 为给定的 Html 移除 padding、margin
     * @param html HTML 字符串
     * @return Html 内容字符串
     */
    public static String addRemovePaddingMargin(final String html) {
        return REMOVE_PADDING_MARGIN_STYLE + html;
    }

    /**
     * 为给定的字符串添加 HTML 颜色标记
     * @param content 给定的字符串
     * @param color   颜色值, 如: #000000
     * @return Html 内容字符串
     */
    public static String addHtmlColor(
            final String content,
            final String color
    ) {
        return "<font color=\"" + color + "\">" + content + "</font>";
    }

    /**
     * 为给定的字符串添加 HTML 颜色标记
     * @param format  格式化字符串
     * @param content 给定的字符串
     * @param color   颜色值, 如: #000000
     * @return Html 内容字符串
     */
    public static String addHtmlColor(
            final String format,
            final String content,
            final String color
    ) {
        return StringUtils.getFormatString(format, addHtmlColor(content, color));
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
    public static String addHtmlColorAndBold(
            final String content,
            final String color
    ) {
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
     * 为给定的字符串添加 HTML 中划线
     * @param content 给定的字符串
     * @return Html 内容字符串
     */
    public static String addHtmlStrikeThruLine(final String content) {
        return "<s>" + content + "</s>";
    }

    /**
     * 为给定的字符串添加 HTML 上划线
     * @param content 给定的字符串
     * @return Html 内容字符串
     */
    public static String addHtmlOverLine(final String content) {
        return "<span style=\"text-decoration: overline\">" + content + "</span>";
    }

    /**
     * 为给定的字符串添加 HTML 字体倾斜
     * <pre>
     *     如果需要倾斜自定义角度, 需要自定义 TextView, 在 onDraw 里面加上倾斜度, 上下左右居中
     *     canvas.rotate( 倾斜角度, getMeasuredWidth() / 3, getMeasuredHeight() / 3);
     * </pre>
     * @param content 给定的字符串
     * @return Html 内容字符串
     */
    public static String addHtmlIncline(final String content) {
        return "<i>" + content + "</i>";
    }

    /**
     * 为给定的字符串添加 HTML SPAN 标签
     * @param content 给定的字符串
     * @return Html 内容字符串
     */
    public static String addHtmlSPAN(final String content) {
        return "<span>" + content + "</span>";
    }

    /**
     * 为给定的字符串添加 HTML P 标签
     * @param content 给定的字符串
     * @return Html 内容字符串
     */
    public static String addHtmlP(final String content) {
        return "<p>" + content + "</p>";
    }

    /**
     * 为给定的字符串添加 HTML IMG 标签
     * @param url 图片链接
     * @return Html 内容字符串
     */
    public static String addHtmlIMG(final String url) {
        return "<img src=\"" + url + "\"/>";
    }

    /**
     * 为给定的字符串添加 HTML IMG 标签
     * @param url    图片链接
     * @param width  图片宽度
     * @param height 图片高度
     * @return Html 内容字符串
     */
    public static String addHtmlIMG(
            final String url,
            final String width,
            final String height
    ) {
        return "<img src=\"" + url + "\" width=\"" + width + "\" height=\"" + height + "\"/>";
    }

    /**
     * 为给定的字符串添加 HTML IMG 标签
     * @param url   图片链接
     * @param width 图片宽度
     * @return Html 内容字符串
     */
    public static String addHtmlIMGByWidth(
            final String url,
            final String width
    ) {
        return "<img src=\"" + url + "\" width=\"" + width + "\"/>";
    }

    /**
     * 为给定的字符串添加 HTML IMG 标签
     * @param url    图片链接
     * @param height 图片高度
     * @return Html 内容字符串
     */
    public static String addHtmlIMGByHeight(
            final String url,
            final String height
    ) {
        return "<img src=\"" + url + "\" height=\"" + height + "\"/>";
    }

    /**
     * 为给定的字符串添加 HTML DIV 标签
     * @param content 给定的字符串
     * @return Html 内容字符串
     */
    public static String addHtmlDIV(final String content) {
        return "<div>" + content + "</div>";
    }

    /**
     * 为给定的字符串添加 HTML DIV 标签
     * @param content 给定的字符串
     * @param margin  margin 边距
     * @return Html 内容字符串
     */
    public static String addHtmlDIVByMargin(
            final String content,
            final String margin
    ) {
        return "<div style=\"margin: " + margin + "\">" + content + "</div>";
    }

    /**
     * 为给定的字符串添加 HTML DIV 标签
     * @param content 给定的字符串
     * @param padding padding 边距
     * @return Html 内容字符串
     */
    public static String addHtmlDIVByPadding(
            final String content,
            final String padding
    ) {
        return "<div style=\"padding: " + padding + "\">" + content + "</div>";
    }

    /**
     * 为给定的字符串添加 HTML DIV 标签
     * @param content 给定的字符串
     * @param margin  margin 边距
     * @param padding padding 边距
     * @return Html 内容字符串
     */
    public static String addHtmlDIVByMarginPadding(
            final String content,
            final String margin,
            final String padding
    ) {
        return "<div style=\"margin: " + margin + "; padding: " + padding + ";\">" + content + "</div>";
    }

    // =

    /**
     * 将给定的字符串中所有给定的关键字标色
     * @param content 给定的字符串
     * @param keyword 给定的关键字
     * @param color   颜色值, 如: #000000
     * @return Html 内容字符串
     */
    public static String keywordReplaceHtmlColor(
            final String content,
            final String keyword,
            final String color
    ) {
        return StringUtils.replaceAll(content, keyword, addHtmlColor(keyword, color));
    }
}