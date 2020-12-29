package dev.other.okgo;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okgo.exception.OkGoException;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import org.json.JSONObject;

import java.lang.reflect.Type;

import dev.utils.LogPrintUtils;
import dev.utils.app.JSONObjectUtils;
import dev.utils.app.toast.ToastUtils;
import dev.utils.common.ClassUtils;

/**
 * detail: 请求回调统一处理类
 * @author Ttt
 * <pre>
 *     高级自定义 Callback
 *     @see <a href="https://github.com/jeasonlzy/okhttp-OkGo/wiki/Callback#%e9%ab%98%e7%ba%a7%e8%87%aa%e5%ae%9a%e4%b9%89callback"/>
 * </pre>
 */
public abstract class OkGoCallback<T>
        extends AbsCallback<String> {

    // 日志 TAG
    private final String TAG = OkGoCallback.class.getSimpleName();

    // 请求链接
    private String  url;
    // 是否需要进行 Toast 提示
    private boolean toast = true;

    public OkGoCallback() {
    }

    public OkGoCallback(boolean toast) {
        this.toast = toast;
    }

    // =============
    // = 非必须方法 =
    // =============

    /**
     * 请求网络开始前 ( UI 线程 )
     */
    @Override
    public void onStart(Request<String, ? extends Request> request) {
        super.onStart(request);

        url = request.getUrl();

//        StringBuilder builder = new StringBuilder();
//        builder.append("请求网络开始前");
//        builder.append("请求链接").append(request.getUrl());
//        builder.append("请求参数").append(request.getParams());

        LogPrintUtils.dTag(TAG, "请求网络开始前: %s", url);
    }

    /**
     * 请求网络结束后, UI 线程
     */
    @Override
    public void onFinish() {
        super.onFinish();

        LogPrintUtils.dTag(TAG, "请求网络结束后: %s", url);
    }

    /**
     * 上传过程中的进度回调, get 请求不回调 ( UI 线程 )
     */
    @Override
    public void uploadProgress(Progress progress) {
        super.uploadProgress(progress);

        LogPrintUtils.dTag(TAG, "上传过程中的进度回调: %s, progress: %s", url, progress);
    }

    /**
     * 下载过程中的进度回调 ( UI 线程 )
     */
    @Override
    public void downloadProgress(Progress progress) {
        super.downloadProgress(progress);

        LogPrintUtils.dTag(TAG, "下载过程中的进度回调: %s, progress: %s", url, progress);
    }

    /**
     * 缓存成功的回调 ( UI 线程 )
     */
    @Override
    public void onCacheSuccess(Response<String> response) {
        super.onCacheSuccess(response);

        // response.getRawCall().request().url()

        LogPrintUtils.dTag(TAG, "缓存成功的回调: %s", url);
    }

    /**
     * 请求失败、响应错误、数据解析错误等, 都会回调该方法 ( UI 线程 )
     */
    @Override
    public void onError(Response<String> response) {
        super.onError(response);

        LogPrintUtils.dTag(TAG, "请求失败: %s", url);

        if (response != null) {
            onErrorResponse(
                    new OkGoResponse.Builder<T>()
                            .setToast(toast)
                            .setCode(String.valueOf(response.code()))
                            .setMessage(response.message())
                            .setOriginal(response.body())
                            .setException(response.getException())
                            .build()
            );
        } else {
            onErrorResponse(
                    new OkGoResponse.Builder<T>()
                            .setToast(toast)
                            .setCode(String.valueOf(Integer.MAX_VALUE))
                            .setMessage("response is null")
                            .setException(new OkGoException("response is null"))
                            .build()
            );
        }
    }

    // ===========
    // = 必须重写 =
    // ===========

    /**
     * 对返回数据进行操作的回调 ( UI 线程 )
     */
    @Override
    public void onSuccess(Response<String> response) {
        LogPrintUtils.dTag(TAG, "请求成功: %s", url);

        try {
            _response(response);
        } catch (Exception e) {
            if (response != null) {
                response.setException(e);
            }
            onError(response);
        }
    }

    /**
     * 拿到响应后, 将数据转换成需要的格式 ( 子线程中执行, 可以是耗时操作 )
     */
    @Override
    public String convertResponse(okhttp3.Response response)
            throws Throwable {
//        HttpUrl httpUrl = response.request().url();
//        LogPrintUtils.e("url: %s", httpUrl);
        LogPrintUtils.dTag(TAG, "响应成功, 转换数据: %s", url);
        String json = new StringConvert().convertResponse(response);
        LogPrintUtils.json(json);
        response.close(); // CloseUtils.closeIO(response);
        return json;
    }

    // ===============
    // = 结果回调方法 =
    // ===============

    /**
     * 请求响应并处理数据无误
     * @param response {@link OkGoResponse}
     */
    abstract public void onSuccessResponse(OkGoResponse<T> response);

    /**
     * 请求失败、响应错误、数据解析错误等, 都会回调该方法 ( UI 线程 )
     * @param response {@link OkGoResponse}
     */
    abstract public void onErrorResponse(OkGoResponse<T> response);

    // ===========
    // = 内部处理 =
    // ===========

    /**
     * 内部处理请求响应数据
     * @param response 请求响应数据
     */
    private void _response(Response<String> response) {
        String body = response.body();
        // 转换 JSON 对象
        JSONObject jsonObject = JSONObjectUtils.getJSONObject(body);
        if (jsonObject == null) {
            response.setException(new OkGoException("data format exception"));
            onError(response);
            return;
        }

        String  data    = jsonObject.optString(OkGoResponse.KEY_DATA);
        String  code    = jsonObject.optString(OkGoResponse.KEY_CODE);
        String  message = jsonObject.optString(OkGoResponse.KEY_MESSAGE);
        boolean result  = isSuccess(code);

        OkGoResponse.Builder<T> builder = new OkGoResponse.Builder<T>()
                .setToast(toast)
                .setOriginal(body).setCode(code)
                .setMessage(message).setResult(result);

        Type type = ClassUtils.getGenericSuperclass(getClass(), 0);
        if (result && type != null) {
            try {
                builder.setData(
                        new Gson().fromJson(data, type)
                );
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "_response setData");
                builder.setResult(false).setException(e);
            }
        }

        OkGoResponse<T> okResponse = builder.build();
        if (okResponse.result) {
            onSuccessResponse(okResponse);
        } else {
            onErrorResponse(okResponse);
        }
    }

    // =

    /**
     * 通过 code 判断请求是否正确
     * @param code Code
     * @return {@code true} yes, {@code false} no
     */
    private boolean isSuccess(String code) {
        if (!TextUtils.isEmpty(code)) {
            if (code.equals("0000")) { // 自行判断
                return true;
            } else if (code.equals("xxx")) {
                if (toast) ToastUtils.showShort("xxx");
            }
        }
        return false;
    }
}