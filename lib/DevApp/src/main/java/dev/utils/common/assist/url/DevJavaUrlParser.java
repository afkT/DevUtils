package dev.utils.common.assist.url;

import java.util.LinkedHashMap;
import java.util.Map;

import dev.utils.common.HttpParamsUtils;
import dev.utils.common.StringUtils;

/**
 * detail: Dev 库 Java 通用 Url 解析器
 * @author Ttt
 * <pre>
 *     不依赖 android api
 * </pre>
 */
public class DevJavaUrlParser
        implements UrlExtras.Parser {

    // 完整 Url
    private String              mUrl;
    // Url 前缀 ( 去除参数部分 )
    private String              mUrlPrefix;
    // Url 参数部分字符串
    private String              mUrlParams;
    // Url Params Map
    private Map<String, String> mUrlParamsMap;
    // Url Params Map ( 参数值进行 UrlDecode )
    private Map<String, String> mUrlParamsDecodeMap;

    // ====================
    // = UrlExtras.Parser =
    // ====================

    @Override
    public UrlExtras.Parser reset(String url) {
        return new DevJavaUrlParser().setUrl(url);
    }

    @Override
    public UrlExtras.Parser setUrl(String url) {
        initialize(url);
        return this;
    }

    @Override
    public String getUrl() {
        return this.mUrl;
    }

    @Override
    public String getUrlByPrefix() {
        return this.mUrlPrefix;
    }

    @Override
    public String getUrlByParams() {
        return this.mUrlParams;
    }

    @Override
    public Map<String, String> getUrlParams() {
        return this.mUrlParamsMap;
    }

    @Override
    public Map<String, String> getUrlParamsDecode() {
        return this.mUrlParamsDecodeMap;
    }

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * 初始化方法
     * <pre>
     *     会清空 url 字符串全部空格、Tab、换行符, 如有特殊符号需提前自行转义
     * </pre>
     * @param url 待处理完整 Url
     */
    private void initialize(String url) {
        this.mUrl                = StringUtils.clearSpaceTabLine(url);
        this.mUrlPrefix          = null;
        this.mUrlParams          = null;
        this.mUrlParamsMap       = null;
        this.mUrlParamsDecodeMap = null;

        if (StringUtils.isNotEmpty(mUrl)) {
            String[] array = HttpParamsUtils.getUrlParamsArray(mUrl);
            this.mUrlPrefix = array[0];
            this.mUrlParams = array[1];

            if (StringUtils.isNotEmpty(mUrlParams)) {
                this.mUrlParamsMap       = HttpParamsUtils.splitParams(
                        mUrlParams, false
                );
                this.mUrlParamsDecodeMap = new LinkedHashMap<>();
                for (Map.Entry<String, String> entry : mUrlParamsMap.entrySet()) {
                    String key   = entry.getKey();
                    String value = entry.getValue();

                    String decode = StringUtils.urlDecode(value);
                    this.mUrlParamsDecodeMap.put(
                            key, StringUtils.checkValue(value, decode)
                    );
                }
            }
        }
    }
}