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
    // 换行字符串
    private static final String NEW_LINE_STR = System.getProperty("line.separator");

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
    public static Map<String, String> splitParams(final String params, final boolean urlEncode) {
        Map<String, String> mapParams = new HashMap<>();
        if (params != null) {
            // 拆分数据
            String[] keyValues = params.split("&");
            // 数据长度
            int valLength = 0;
            // 进行循环遍历
            for (String val : keyValues) {
                // 数据不为null
                if (val != null && (valLength = val.length()) != 0) {
                    // 获取首位 = 索引
                    int indexOf = val.indexOf('=');
                    // 不存在则不处理
                    if (indexOf != -1) {
                        // 获取key
                        String key = val.substring(0, indexOf);
                        // 获取value
                        String value = null;
                        // 防止资源浪费
                        if (indexOf + 1 == valLength) {
                            value = "";
                        } else {
                            value = val.substring(indexOf + 1, valLength);
                        }
                        // 判断是否编码
                        if (urlEncode) {
                            mapParams.put(key, toUrlEncode(value));
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
     * 拼接请求参数 - value => String
     * @param mapParams Map 请求参数
     * @return 拼接后的参数
     */
    public static String joinParams(final Map<String, String> mapParams) {
        return joinParams(mapParams, false);
    }

    /**
     * 拼接请求参数 - value => String
     * @param mapParams Map 请求参数
     * @param urlEncode 是否需要 URL 编码
     * @return 拼接后的参数
     */
    public static String joinParams(final Map<String, String> mapParams, final boolean urlEncode) {
        if (mapParams != null) {
            int index = 0;
            // =
            StringBuilder builder = new StringBuilder();
            Iterator<Map.Entry<String, String>> iterator = mapParams.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                // =
                if (index > 0) builder.append('&');
                builder.append(entry.getKey());
                builder.append('=');
                builder.append(urlEncode ? toUrlEncode(entry.getValue()) : entry.getValue());
                index++;
            }
            return builder.toString();
        }
        return null;
    }

    // =

    /**
     * 拼接请求参数 - value => Object
     * @param mapParams Map 请求参数
     * @return 拼接后的参数
     */
    public static String joinParamsObj(final Map<String, Object> mapParams) {
        return joinParamsObj(mapParams, false);
    }

    /**
     * 拼接请求参数 - value => Object
     * @param mapParams Map 请求参数
     * @param urlEncode 是否需要 URL 编码
     * @return 拼接后的参数
     */
    public static String joinParamsObj(final Map<String, Object> mapParams, final boolean urlEncode) {
        if (mapParams != null) {
            int index = 0;
            // =
            StringBuilder builder = new StringBuilder();
            Iterator<Map.Entry<String, Object>> iterator = mapParams.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> entry = iterator.next();
                // =
                if (index > 0) builder.append('&');
                builder.append(entry.getKey());
                builder.append('=');
                if (urlEncode) {
                    if (entry.getValue() instanceof String) {
                        builder.append(toUrlEncode((String) entry.getValue()));
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

    // =

    /**
     * 拼接打印 Map 参数
     * @param mapParams Map 请求参数
     * @return 拼接后的参数
     */
    public static String printMapParams(final Map<String, String> mapParams) {
        return printMapParams(mapParams, false);
    }

    /**
     * 拼接打印 Map 参数
     * @param mapParams Map 请求参数
     * @param urlEncode 是否需要 URL 编码
     * @return 拼接后的参数
     */
    public static String printMapParams(final Map<String, String> mapParams, final boolean urlEncode) {
        if (mapParams != null) {
            StringBuilder builder = new StringBuilder();
            Iterator<Map.Entry<String, String>> iterator = mapParams.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                builder.append(entry.getKey());
                builder.append(" => ");
                builder.append(urlEncode ? toUrlEncode(entry.getValue()) : entry.getValue());
                builder.append(NEW_LINE_STR);
            }
            return builder.toString();
        }
        return null;
    }

    // ============================================
    // = 拼接成, 模拟 JavaScript 传递对象数组格式 =
    // ============================================

    // 正常数据
    // objStr[key] => sex=男&name=Ttt
    // 方法会进行 url编码
    // objStr[key] => sex%3D%E7%94%B7%26name%3DTtt

    /**
     * 进行转换对象处理(请求发送对象)
     * @param mapParams Map 请求参数
     * @param objStr    数组名
     * @param key       数组 key
     * @param value     数组[key] 保存值
     */
    public static void toConvertObjToMS(final Map<String, String> mapParams, final String objStr, final String key, final String value) {
        if (mapParams != null) {
            String data = null;
            try {
                data = URLEncoder.encode(value, "UTF-8");
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "toConvertObjToMS");
            }
            mapParams.put(objStr + "[" + key + "]", data);
        }
    }

    /**
     * 进行转换对象处理(请求发送对象)
     * @param mapParams Map 请求参数
     * @param objStr    数组名
     * @param key       数组 key
     * @param value     数组[key] 保存值
     */
    public static void toConvertObjToMO(final Map<String, Object> mapParams, final String objStr, final String key, final Object value) {
        if (mapParams != null) {
            Object data = null;
            try {
                data = URLEncoder.encode(value.toString(), "UTF-8");
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "toConvertObjToMO");
            }
            mapParams.put(objStr + "[" + key + "]", data);
        }
    }

    // =

    /**
     * 进行 URL 编码,默认UTF-8
     * @param str 待处理字符串
     * @return 字符串 UTF-8 编码后, 再进行 Url 编码后的字符串
     */
    public static String toUrlEncode(final String str) {
        return toUrlEncode(str, "UTF-8");
    }

    /**
     * 进行 URL 编码
     * @param str 待处理字符串
     * @param enc 编码格式
     * @return 指定编码格式编码后, 再进行 Url 编码后的字符串
     */
    public static String toUrlEncode(final String str, final String enc) {
        if (str == null || enc == null) return null;
        try {
            return URLEncoder.encode(str, enc);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toUrlEncode");
        }
        return null;
    }
}
