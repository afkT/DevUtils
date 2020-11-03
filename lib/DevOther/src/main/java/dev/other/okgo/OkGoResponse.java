package dev.other.okgo;

import android.text.TextUtils;

import org.json.JSONObject;

import dev.utils.LogPrintUtils;

/**
 * detail: 请求响应统一解析类
 * @author Ttt
 * <pre>
 *     主要用于统一解析后台返回 data、message、code 等
 * </pre>
 */
public class OkGoResponse<T> {

    // 日志 TAG
    private final String TAG = OkGoResponse.class.getSimpleName();

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
    // 是否需要进行 Toast 提示
    public final boolean   toast;
    // 请求结果
    public final boolean   result;
    // 返回原始数据
    public final String    original;
    // 请求异常
    public final Throwable exception;

    public OkGoResponse(Builder<T> builder) {
        data = builder.data;
        message = builder.message;
        code = builder.code;
        toast = builder.toast;
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
                LogPrintUtils.eTag(TAG, e, "getDataString");
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
        public T         data;
        // 返回消息
        public String    message;
        // 返回结果状态 ( 内部定义 )
        public String    code;
        // 是否需要进行 Toast 提示
        public boolean   toast = true;
        // 请求结果
        public boolean   result;
        // 后台返回原始数据
        public String    original;
        // 请求异常
        public Throwable exception;

        public Builder() {
        }

        public Builder(OkGoResponse<T> response) {
            data = response.data;
            message = response.message;
            code = response.code;
            toast = response.toast;
            result = response.result;
            original = response.original;
            exception = response.exception;
        }

        /**
         * build Response 对象
         * @return {@link OkGoResponse}
         */
        public OkGoResponse<T> build() {
            return new OkGoResponse<>(this);
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

        public Builder<T> setToast(boolean toast) {
            this.toast = toast;
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