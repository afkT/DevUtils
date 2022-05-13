package dev.utils.app.assist.url;

import android.net.Uri;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import dev.utils.LogPrintUtils;
import dev.utils.common.StringUtils;
import dev.utils.common.assist.url.UrlExtras;

/**
 * detail: Android Api 实现 Url 解析器
 * @author Ttt
 */
public class AndroidUrlParser
        implements UrlExtras.Parser {

    // 日志 TAG
    private static final String TAG = AndroidUrlParser.class.getSimpleName();

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
    // 是否解析、转换 Param Map
    private boolean             mConvertMap = true;

    // ====================
    // = UrlExtras.Parser =
    // ====================

    @Override
    public UrlExtras.Parser reset(final String url) {
        return new AndroidUrlParser().setUrl(url);
    }

    @Override
    public UrlExtras.Parser setUrl(final String url) {
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

    @Override
    public boolean isConvertMap() {
        return this.mConvertMap;
    }

    @Override
    public UrlExtras.Parser setConvertMap(final boolean convertMap) {
        this.mConvertMap = convertMap;
        return this;
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
    private void initialize(final String url) {
        this.mUrl                = StringUtils.clearSpaceTabLine(url);
        this.mUrlPrefix          = null;
        this.mUrlParams          = null;
        this.mUrlParamsMap       = null;
        this.mUrlParamsDecodeMap = null;

        if (StringUtils.isNotEmpty(mUrl)) {
            try {
                Uri uri = Uri.parse(mUrl);
                this.mUrlPrefix = uriToUrlPrefix(uri);
                this.mUrlParams = uri.getEncodedQuery();

                if (mConvertMap && StringUtils.isNotEmpty(mUrlParams)) {
                    this.mUrlParamsMap       = new LinkedHashMap<>();
                    this.mUrlParamsDecodeMap = new LinkedHashMap<>();

                    Set<String> keys = uri.getQueryParameterNames();
                    for (String key : keys) {
                        String value  = uri.getQueryParameter(key);
                        String decode = StringUtils.urlDecodeWhile(value, 10);

                        this.mUrlParamsMap.put(key, value);
                        this.mUrlParamsDecodeMap.put(
                                key, StringUtils.checkValue(value, decode)
                        );
                    }
                }
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "initialize");
            }
        }
    }

    /**
     * 通过 Uri 拼接 Url 前缀 ( 去除参数部分 )
     * <pre>
     *     参照部分 Uri#toSafeString() 代码
     * </pre>
     * @param uri Uri.parse
     * @return Url 前缀
     */
    private String uriToUrlPrefix(final Uri uri) {
        String scheme    = StringUtils.checkValue(uri.getScheme());
        String authority = StringUtils.checkValue(uri.getAuthority());
        String path      = StringUtils.checkValue(uri.getPath());
        return scheme + "://" + authority + path;
    }
}