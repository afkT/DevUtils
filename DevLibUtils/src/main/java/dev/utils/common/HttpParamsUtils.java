package dev.utils.common;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import dev.utils.JCLogUtils;

/**
 * detail: Http 参数工具类
 * Created by Ttt
 */
public final class HttpParamsUtils {

    private HttpParamsUtils() {
    }

    // 日志TAG
    private static final String TAG = HttpParamsUtils.class.getSimpleName();

    /**
     * 拆分参数
     * @param params
     * @return
     */
    public static Map<String, String> splitParams(final String params) {
        return splitParams(params, false);
    }

    /**
     * 拆分参数
     * @param params
     * @param urlEncode 是否需要编码
     * @return
     */
    public static Map<String, String> splitParams(final String params, boolean urlEncode) {
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

    // -

    /**
     * 拼接请求参数 - value => String
     * @param mapParams
     * @return
     */
    public static String joinReqParams(final Map<String, String> mapParams) {
        return joinReqParams(mapParams, false);
    }

    /**
     * 拼接请求参数 - value => String
     * @param mapParams
     * @param urlEncode 是否需要编码
     * @return
     */
    public static String joinReqParams(final Map<String, String> mapParams, boolean urlEncode) {
        if (mapParams != null) {
            int index = 0;
            // --
            StringBuilder sBuilder = new StringBuilder();
            Iterator<Map.Entry<String, String>> iterator = mapParams.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                // --
                if (index > 0) sBuilder.append('&');
                sBuilder.append(entry.getKey());
                sBuilder.append('=');
                sBuilder.append(urlEncode ? urlEncode(entry.getValue()) : entry.getValue());
                index++;
            }
            return sBuilder.toString();
        }
        return null;
    }

    // -

    /**
     * 拼接请求参数 - value => Object
     * @param mapParams
     * @return
     */
    public static String joinReqParamsObj(final Map<String, Object> mapParams) {
        return joinReqParamsObj(mapParams, false);
    }

    /**
     * 拼接请求参数 - value => Object
     * @param mapParams
     * @param urlEncode 是否需要编码
     * @return
     */
    public static String joinReqParamsObj(final Map<String, Object> mapParams, boolean urlEncode) {
        if (mapParams != null) {
            int index = 0;
            // --
            StringBuilder sBuilder = new StringBuilder();
            Iterator<Map.Entry<String, Object>> iterator = mapParams.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> entry = iterator.next();
                // --
                if (index > 0) sBuilder.append('&');
                sBuilder.append(entry.getKey());
                sBuilder.append('=');
                if (urlEncode) {
                    if (entry.getValue() instanceof String) {
                        sBuilder.append(urlEncode((String) entry.getValue()));
                    }
                } else {
                    sBuilder.append(entry.getValue());
                }
                index++;
            }
            return sBuilder.toString();
        }
        return null;
    }

    // -

    /**
     * toString 快捷方法, 拼接打印 String
     * @param mapParams
     * @return
     */
    public static String toStringMap(final Map<String, String> mapParams) {
        return toStringMap(mapParams, false);
    }

    /**
     * toString 快捷方法, 拼接打印 String
     * @param mapParams
     * @param urlEncode 是否需要编码
     * @return
     */
    public static String toStringMap(final Map<String, String> mapParams, boolean urlEncode) {
        if (mapParams != null) {
            StringBuilder sBuilder = new StringBuilder();
            // --
            Iterator<Map.Entry<String, String>> iterator = mapParams.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                sBuilder.append(entry.getKey());
                sBuilder.append(" => ");
                sBuilder.append(urlEncode ? urlEncode(entry.getValue()) : entry.getValue());
                sBuilder.append("\n");
            }
            return sBuilder.toString();
        }
        return null;
    }

    // == 拼接成, 模拟 JavaScript 传递对象数组格式 ==

    // 正常数据
    // objStr[key] => sex=男&name=Ttt
    // 方法会进行 url编码
    // objStr[key] => sex%3D%E7%94%B7%26name%3DTtt

    /**
     * 进行转换对象处理(请求发送对象)
     * @param mapParams
     * @param objStr
     * @param key
     * @param value
     */
    public static void toConvertObjToMS(Map<String, String> mapParams, String objStr, String key, String value) {
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
     * @param mapParams
     * @param objStr
     * @param key
     * @param value
     */
    public static void toConvertObjToMO(Map<String, Object> mapParams, String objStr, String key, Object value) {
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
     * url编码 - utf-8
     * @param input The input.
     * @return the urlencoded string
     */
    public static String urlEncode(final String input) {
        return urlEncode(input, "UTF-8");
    }

    /**
     * url编码
     * @param input The input.
     * @param charsetName The name of charset.
     * @return the urlencoded string
     */
    public static String urlEncode(final String input, final String charsetName) {
        try {
            return URLEncoder.encode(input, charsetName);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "urlEncode");
            return input;
        }
    }
}
