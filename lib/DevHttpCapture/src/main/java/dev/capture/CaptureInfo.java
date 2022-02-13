package dev.capture;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * detail: 抓包信息封装类
 * @author Ttt
 */
public final class CaptureInfo {

    // 请求链接
    protected String              requestUrl;
    // 请求方法
    protected String              requestMethod;
    // 请求头信息
    protected Map<String, String> requestHeader = new LinkedHashMap<>();
    // 请求数据
    protected Map<String, String> requestBody   = new LinkedHashMap<>();

    // 响应状态
    protected Map<String, String> responseStatus = new LinkedHashMap<>();
    // 响应头信息
    protected Map<String, String> responseHeader = new LinkedHashMap<>();
    // 响应数据
    protected String              responseBody;

    // =======
    // = get =
    // =======

    public String getRequestUrl() {
        return requestUrl;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public Map<String, String> getRequestHeader() {
        return requestHeader;
    }

    public Map<String, String> getRequestBody() {
        return requestBody;
    }

    public Map<String, String> getResponseStatus() {
        return responseStatus;
    }

    public Map<String, String> getResponseHeader() {
        return responseHeader;
    }

    public String getResponseBody() {
        return responseBody;
    }

    // ==========
    // = 其他处理 =
    // ==========

    /**
     * 将对象转换为 JSON String
     * @return JSON String
     */
    public String toJson() {
        return Utils.toJson(this);
    }
}