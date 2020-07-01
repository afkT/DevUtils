package dev.other.retrofit.response;

import dev.other.GsonUtils;

/**
 * detail: 请求响应统一解析类
 * @author Ttt
 * <pre>
 *     主要用于统一解析后台返回 data、message、code 等
 * </pre>
 */
public class BaseResponse<T> {

    // 返回数据
    public            T         data;
    // 返回消息
    public            String    message;
    // 返回结果状态 ( 内部定义 )
    public            String    code;
    // 请求结果
    public transient  boolean   result;
    // 返回原始数据
    private transient String    original;
    // 请求异常
    public transient  Throwable exception;

    public String getOriginal() {
        if (original != null) return original;
        original = GsonUtils.toJson(this);
        return original;
    }
}