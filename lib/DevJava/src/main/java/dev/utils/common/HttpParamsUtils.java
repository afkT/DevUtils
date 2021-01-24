package dev.utils.common;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
        Map<String, String> mapParams = new HashMap<>();
        if (params != null) {
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
            int index = 0;
            // =
            StringBuilder                       builder  = new StringBuilder();
            Iterator<Map.Entry<String, String>> iterator = mapParams.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                // =
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
            int index = 0;
            // =
            StringBuilder                       builder  = new StringBuilder();
            Iterator<Map.Entry<String, Object>> iterator = mapParams.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> entry = iterator.next();
                // =
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

    // ==========================================
    // = 拼接成, 模拟 JavaScript 传递对象数组格式 =
    // ==========================================

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
                data = URLEncoder.encode(value, "UTF-8");
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
                data = URLEncoder.encode(value.toString(), "UTF-8");
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