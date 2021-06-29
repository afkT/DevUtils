package dev.capture;

/**
 * detail: 抓包数据
 * @author Ttt
 */
public final class CaptureInfo {

    // 请求链接
    private String requestUrl;
    // 请求方法
    private String requestMethod;
    // 请求头信息
    private String requestHeader;
    // 请求数据
    private String requestBody;

    // 响应状态
    private String responseStatus;
    // 响应头信息
    private String responseHeader;
    // 响应数据
    private String responseBody;

    // =======
    // = get =
    // =======

    public String getRequestUrl() {
        return requestUrl;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public String getRequestHeader() {
        return requestHeader;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public String getResponseStatus() {
        return responseStatus;
    }

    public String getResponseHeader() {
        return responseHeader;
    }

    public String getResponseBody() {
        return responseBody;
    }

    // =======
    // = set =
    // =======

    CaptureInfo setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
        return this;
    }

    CaptureInfo setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
        return this;
    }

    CaptureInfo setRequestHeader(String requestHeader) {
        this.requestHeader = requestHeader;
        return this;
    }

    CaptureInfo setRequestBody(String requestBody) {
        this.requestBody = requestBody;
        return this;
    }

    CaptureInfo setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
        return this;
    }

    CaptureInfo setResponseHeader(String responseHeader) {
        this.responseHeader = responseHeader;
        return this;
    }

    CaptureInfo setResponseBody(String responseBody) {
        this.responseBody = responseBody;
        return this;
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