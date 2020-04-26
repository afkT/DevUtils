package dev.other.okgo;

import android.text.TextUtils;

import org.json.JSONObject;

/**
 * detail: 请求响应解析基类
 * @author Ttt
 * <pre>
 *     主要用于统一解析后台返回，data、message、code 等
 * </pre>
 */
public class OKResponse<T> {

    // 后台返回 data、message、code 解析 key 常量
    public static final String KEY_DATA    = "data";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_CODE    = "code";

    // 返回数据
    public final T         data;
    // 返回消息
    public final String    message;
    // 返回结果状态 ( 内部定义 )
    public final String    code;
    // 请求结果
    public final boolean   result;
    // 返回原始数据
    public final String    original;
    // 请求异常
    public final Throwable exception;

    public OKResponse(Builder<T> builder) {
        data = builder.data;
        message = builder.message;
        code = builder.code;
        result = builder.result;
        original = builder.original;
        exception = builder.exception;
    }

    /**
     * 获取 Data 字符串
     * @return Data 字符串
     */
    public String getDataString() {
        if (!TextUtils.isEmpty(original)) {
            try {
                JSONObject jsonObject = new JSONObject(original);
                return jsonObject.optString(KEY_DATA);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // =

    /**
     * detail: Builder 模式
     * @author Ttt
     */
    public static final class Builder<T> {

        // 返回数据
        private T         data;
        // 返回消息
        private String    message;
        // 返回结果状态 ( 内部定义 )
        private String    code;
        // 请求结果
        private boolean   result;
        // 后台返回原始数据
        private String    original;
        // 请求异常
        private Throwable exception;

        public Builder() {
        }

        /**
         * build BaseResponse 对象
         * @return {@link OKResponse}
         */
        public OKResponse<T> build() {
            return new OKResponse<>(this);
        }

        // =

        public Builder<T> setData(T data) {
            this.data = data;
            return this;
        }

        public Builder<T> setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder<T> setCode(String code) {
            this.code = code;
            return this;
        }

        public Builder<T> setResult(boolean result) {
            this.result = result;
            return this;
        }

        public Builder<T> setOriginal(String original) {
            this.original = original;
            return this;
        }

        public Builder<T> setException(Throwable exception) {
            this.exception = exception;
            return this;
        }
    }
}
