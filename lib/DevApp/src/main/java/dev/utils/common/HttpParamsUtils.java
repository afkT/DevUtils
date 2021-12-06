package dev.utils.common;

import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

import dev.utils.DevFinal;
import dev.utils.JCLogUtils;

/**
 * detail: Http 参数工具类
 * @author Ttt
 */
public final class HttpParamsUtils {

    private HttpParamsUtils() {
    }

    // 日志 TAG
    private static final String TAG = HttpParamsUtils.class.getSimpleName();

    /**
     * 获取 Url 携带参数
     * @param url URL 链接
     * @return Url 携带参数
     */
    public static String getUrlParams(final String url) {
        return getUrlParamsArray(url)[1];
    }

    /**
     * 获取 Url、携带参数 数组
     * @param url URL 链接
     * @return 0 = url, 1 = params
     */
    public static String[] getUrlParamsArray(final String url) {
        String[] result = new String[2];
        if (StringUtils.isNotEmpty(url)) {
            // 清除掉前后空格
            String urlClean = StringUtils.clearSEWiths(url, " ");
            // 清除掉结尾的 ?
            urlClean = StringUtils.clearEndsWith(urlClean, "?");
            // 进行拆分
            int index = urlClean.indexOf("?");
            if (index != -1) {
                result[0] = urlClean.substring(0, index);
                result[1] = urlClean.substring(index + 1);
            } else {
                result[0] = urlClean;
            }
        }
        return result;
    }

    /**
     * 判断是否存在参数
     * @param params 请求参数字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean existsParams(final String params) {
        return splitParams(params).size() != 0;
    }

    /**
     * 通过 Url 判断是否存在参数
     * @param url URL 链接
     * @return {@code true} yes, {@code false} no
     */
    public static boolean existsParamsByURL(final String url) {
        return splitParams(getUrlParams(url)).size() != 0;
    }

    /**
     * 拼接 Url 及携带参数
     * @param url    URL 链接
     * @param params 请求参数字符串
     * @return {@code true} yes, {@code false} no
     */
    public static String joinUrlParams(
            final String url,
            final String params
    ) {
        if (StringUtils.isEmpty(params)) return url;
        // 获取拼接符号
        String symbol = getUrlParamsJoinSymbol(url, params);
        return url + symbol + params;
    }

    /**
     * 获取 Url 及携带参数 拼接符号
     * @param url    URL 链接
     * @param params 请求参数字符串
     * @return {@code true} yes, {@code false} no
     */
    public static String getUrlParamsJoinSymbol(
            final String url,
            final String params
    ) {
        if (StringUtils.isEmpty(params)) return "";
        // 判断是否存在参数
        if (existsParamsByURL(url)) {
            return "&";
        } else {
            return "?";
        }
    }

    // =

    /**
     * 通过 Url 拆分参数
     * @param url URL 链接
     * @return 拆分后的参数 Map
     */
    public static Map<String, String> splitParamsByUrl(final String url) {
        return splitParamsByUrl(url, false);
    }

    /**
     * 通过 Url 拆分参数
     * @param url       URL 链接
     * @param urlEncode 是否需要 URL 编码
     * @return 拆分后的参数 Map
     */
    public static Map<String, String> splitParamsByUrl(
            final String url,
            final boolean urlEncode
    ) {
        return splitParams(getUrlParams(url), urlEncode);
    }

    /**
     * 拆分参数
     * @param params 请求参数字符串
     * @return 拆分后的参数 Map
     */
    public static Map<String, String> splitParams(final String params) {
        return splitParams(params, false);
    }

    /**
     * 拆分参数
     * @param params    请求参数字符串
     * @param urlEncode 是否需要 URL 编码
     * @return 拆分后的参数 Map
     */
    public static Map<String, String> splitParams(
            final String params,
            final boolean urlEncode
    ) {
        Map<String, String> mapParams = new LinkedHashMap<>();
        if (StringUtils.isNotEmpty(params)) {
            // 拆分数据
            String[] keyValues = params.split("&");
            // 数据长度
            int valLength;
            // 进行循环遍历
            for (String val : keyValues) {
                // 数据不为 null
                if (val != null && (valLength = val.length()) != 0) {
                    // 获取首位 = 索引
                    int indexOf = val.indexOf('=');
                    // 不存在则不处理
                    if (indexOf != -1) {
                        // 获取 key
                        String key = val.substring(0, indexOf);
                        // 获取 value
                        String value;
                        // 防止资源浪费
                        if (indexOf + 1 == valLength) {
                            value = "";
                        } else {
                            value = val.substring(indexOf + 1, valLength);
                        }
                        // 判断是否编码
                        if (urlEncode) {
                            mapParams.put(key, urlEncode(value));
                        } else {
                            mapParams.put(key, value);
                        }
                    }
                }
            }
        }
        return mapParams;
    }

    // =

    /**
     * 拼接请求参数
     * @param mapParams Map 请求参数
     * @return 拼接后的参数
     */
    public static String joinParams(final Map<String, String> mapParams) {
        return joinParams(mapParams, false);
    }

    /**
     * 拼接请求参数
     * @param mapParams Map 请求参数
     * @param urlEncode 是否需要 URL 编码
     * @return 拼接后的参数
     */
    public static String joinParams(
            final Map<String, String> mapParams,
            final boolean urlEncode
    ) {
        if (mapParams != null) {
            int           index   = 0;
            StringBuilder builder = new StringBuilder();
            for (Map.Entry<String, String> entry : mapParams.entrySet()) {
                if (index > 0) builder.append('&');
                builder.append(entry.getKey());
                builder.append('=');
                builder.append(urlEncode ? urlEncode(entry.getValue()) : entry.getValue());
                index++;
            }
            return builder.toString();
        }
        return null;
    }

    // =

    /**
     * 拼接请求参数
     * @param mapParams Map 请求参数
     * @return 拼接后的参数
     */
    public static String joinParamsObj(final Map<String, Object> mapParams) {
        return joinParamsObj(mapParams, false);
    }

    /**
     * 拼接请求参数
     * @param mapParams Map 请求参数
     * @param urlEncode 是否需要 URL 编码
     * @return 拼接后的参数
     */
    public static String joinParamsObj(
            final Map<String, Object> mapParams,
            final boolean urlEncode
    ) {
        if (mapParams != null) {
            int           index   = 0;
            StringBuilder builder = new StringBuilder();
            for (Map.Entry<String, Object> entry : mapParams.entrySet()) {
                if (index > 0) builder.append('&');
                builder.append(entry.getKey());
                builder.append('=');
                if (urlEncode) {
                    if (entry.getValue() instanceof String) {
                        builder.append(urlEncode((String) entry.getValue()));
                    }
                } else {
                    builder.append(entry.getValue());
                }
                index++;
            }
            return builder.toString();
        }
        return null;
    }

    // ======================================
    // = 拼接成, 模拟 JavaScript 传递对象数组格式 =
    // ======================================

    /**
     * 进行转换对象处理 ( 请求发送对象 )
     * @param mapParams Map 请求参数
     * @param objStr    数组名
     * @param key       数组 key
     * @param value     数组 [key] 保存值
     * @return {@code true} success, {@code false} fail
     */
    public static boolean convertObjToMS(
            final Map<String, String> mapParams,
            final String objStr,
            final String key,
            final String value
    ) {
        if (mapParams != null) {
            String data = null;
            try {
                data = URLEncoder.encode(value, DevFinal.ENCODE.UTF_8);
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "convertObjToMS");
            }
            mapParams.put(objStr + "[" + key + "]", data);
            return true;
        }
        return false;
    }

    /**
     * 进行转换对象处理 ( 请求发送对象 )
     * @param mapParams Map 请求参数
     * @param objStr    数组名
     * @param key       数组 key
     * @param value     数组 [key] 保存值
     * @return {@code true} success, {@code false} fail
     */
    public static boolean convertObjToMO(
            final Map<String, Object> mapParams,
            final String objStr,
            final String key,
            final Object value
    ) {
        if (mapParams != null) {
            Object data = null;
            try {
                data = URLEncoder.encode(value.toString(), DevFinal.ENCODE.UTF_8);
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "convertObjToMO");
            }
            mapParams.put(objStr + "[" + key + "]", data);
            return true;
        }
        return false;
    }

    // ===============
    // = StringUtils =
    // ===============

    /**
     * 进行 URL 编码, 默认 UTF-8
     * @param str 待处理字符串
     * @return UTF-8 编码格式 URL 编码后的字符串
     */
    public static String urlEncode(final String str) {
        return StringUtils.urlEncode(str);
    }

    /**
     * 进行 URL 编码
     * @param str 待处理字符串
     * @param enc 编码格式
     * @return 指定编码格式 URL 编码后的字符串
     */
    public static String urlEncode(
            final String str,
            final String enc
    ) {
        return StringUtils.urlEncode(str, enc);
    }
}