package dev.utils.common.assist.url;

import java.util.Map;

/**
 * detail: Url 携带信息解析
 * @author Ttt
 */
public class UrlExtras {

    // 完整 Url
    private String mUrl;
    // Url 解析器
    private Parser mParser;

    // ==========
    // = 构造函数 =
    // ==========

    public UrlExtras(final String url) {
        this(url, new DevJavaUrlParser());
    }

    public UrlExtras(
            final String url,
            final Parser parser
    ) {
        this.mUrl = url;
        setParser(parser);
    }

    // ========
    // = 解析器 =
    // ========

    /**
     * detail: Url 解析器
     * @author Ttt
     */
    public interface Parser {

        /**
         * 重置并返回一个新的解析器
         * @param url 完整 Url
         * @return Parser
         */
        Parser reset(String url);

        /**
         * 设置完整 Url
         * @param url Url
         * @return Parser
         */
        Parser setUrl(String url);

        /**
         * 获取完整 Url
         * @return Url
         */
        String getUrl();

        /**
         * 获取 Url 前缀 ( 去除参数部分 )
         * @return Url 前缀
         */
        String getUrlByPrefix();

        /**
         * 获取 Url 参数部分字符串
         * @return Url 参数部分字符串
         */
        String getUrlByParams();

        /**
         * 获取 Url Params Map
         * @return Url Params Map
         */
        Map<String, String> getUrlParams();

        /**
         * 获取 Url Params Map ( 参数值进行 UrlDecode )
         * @return Url Params Map
         */
        Map<String, String> getUrlParamsDecode();

        // =

        /**
         * 是否解析、转换 Param Map
         * @return {@code true} yes, {@code false} no
         */
        boolean isConvertMap();

        /**
         * 设置是否解析、转换 Param Map
         * @param convertMap {@code true} yes, {@code false} no
         * @return Parser
         */
        Parser setConvertMap(boolean convertMap);
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 获取完整 Url
     * @return Url
     */
    public String getUrl() {
        return mUrl;
    }

    /**
     * 获取 Url 前缀 ( 去除参数部分 )
     * @return Url 前缀
     */
    public String getUrlByPrefix() {
        return (mParser != null) ? mParser.getUrlByPrefix() : null;
    }

    /**
     * 获取 Url 参数部分字符串
     * @return Url 参数部分字符串
     */
    public String getUrlByParams() {
        return (mParser != null) ? mParser.getUrlByParams() : null;
    }

    /**
     * 获取 Url Params Map
     * @return Url Params Map
     */
    public Map<String, String> getUrlParams() {
        return (mParser != null) ? mParser.getUrlParams() : null;
    }

    /**
     * 获取 Url Params Map ( 参数值进行 UrlDecode )
     * @return Url Params Map
     */
    public Map<String, String> getUrlParamsDecode() {
        return (mParser != null) ? mParser.getUrlParamsDecode() : null;
    }

    // =

    /**
     * 获取 Url 解析器
     * @return Parser
     */
    public Parser getParser() {
        return mParser;
    }

    /**
     * 设置 Url 解析器
     * @param parser Parser
     * @return UrlExtras
     */
    public UrlExtras setParser(final Parser parser) {
        if (parser != null) {
            parser.setUrl(mUrl);
        }
        this.mParser = parser;
        return this;
    }
}