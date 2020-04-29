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

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import dev.utils.JCLogUtils;
import dev.utils.app.JSONObjectUtils;
import dev.utils.app.logger.DevLogger;

/**
 * detail: OkGo 请求统一回调处理类
 * @author Ttt
 * <pre>
 *     高级自定义 Callback
 *     @see <a href="https://github.com/jeasonlzy/okhttp-OkGo/wiki/Callback#%e9%ab%98%e7%ba%a7%e8%87%aa%e5%ae%9a%e4%b9%89callback"/>
 * </pre>
 */
public abstract class OKCallback<T> extends AbsCallback<String> {

    // 日志 TAG
    private final String TAG = OKCallback.class.getSimpleName();
    // 请求链接
    private       String url;

    // ==============
    // = 非必须方法 =
    // ==============

    /**
     * 请求网络开始前, UI 线程
     */
    @Override
    public void onStart(Request<String, ? extends Request> request) {
        super.onStart(request);

        url = request.getUrl();

//        StringBuilder builder = new StringBuilder();
//        builder.append("请求网络开始前");
//        builder.append("请求链接" + request.getUrl());
//        builder.append("请求参数" + request.getParams().toString());

        DevLogger.dTag(TAG, "请求网络开始前: " + url);
    }

    /**
     * 请求网络结束后, UI 线程
     */
    @Override
    public void onFinish() {
        super.onFinish();

        DevLogger.dTag(TAG, "请求网络结束后: " + url);
    }

    /**
     * 上传过程中的进度回调, get请求不回调, UI 线程
     */
    @Override
    public void uploadProgress(Progress progress) {
        super.uploadProgress(progress);

        DevLogger.dTag(TAG, "上传过程中的进度回调: " + url + ", progress: " + progress);
    }

    /**
     * 下载过程中的进度回调, UI 线程
     */
    @Override
    public void downloadProgress(Progress progress) {
        super.downloadProgress(progress);

        DevLogger.dTag(TAG, "下载过程中的进度回调: " + url + ", progress: " + progress);
    }

    /**
     * 缓存成功的回调,UI 线程
     */
    @Override
    public void onCacheSuccess(Response<String> response) {
        super.onCacheSuccess(response);

        // response.getRawCall().request().url()

        DevLogger.dTag(TAG, "缓存成功的回调: " + url);
    }

    /**
     * 请求失败, 响应错误, 数据解析错误等, 都会回调该方法,  UI 线程
     */
    @Override
    public void onError(Response<String> response) {
        super.onError(response);

        DevLogger.dTag(TAG, "请求失败: " + url);

        if (response != null) {
            onErrorResponse(
                    new OKResponse.Builder<T>()
                            .setCode(response.code() + "")
                            .setMessage(response.message())
                            .setOriginal(response.body())
                            .setException(response.getException())
                            .build()
            );
        } else {
            onErrorResponse(
                    new OKResponse.Builder<T>()
                            .setCode(Integer.MAX_VALUE + "")
                            .setMessage("response is null")
                            .setException(new OkGoException("response is null"))
                            .build()
            );
        }
    }

    // ============
    // = 必须重写 =
    // ============

    /**
     * 对返回数据进行操作的回调,  UI 线程
     */
    @Override
    public void onSuccess(Response<String> response) {
        DevLogger.dTag(TAG, "请求成功: " + url);

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
     * 拿到响应后, 将数据转换成需要的格式, 子线程中执行, 可以是耗时操作
     */
    @Override
    public String convertResponse(okhttp3.Response response) throws Throwable {
//        HttpUrl httpUrl = response.request().url();
//        DevLogger.e("url: " + httpUrl);
        DevLogger.dTag(TAG, "响应成功，转换数据: " + url);
        String json = new StringConvert().convertResponse(response);
        DevLogger.json(json);
        response.close();
        return json;
    }

    // ===============
    // = 结果回调方法 =
    // ===============

    /**
     * 请求响应并处理数据无误
     * @param response {@link OKResponse}
     */
    abstract public void onSuccessResponse(OKResponse<T> response);

    /**
     * 请求失败, 响应错误, 数据解析错误等, 都会回调该方法,  UI 线程
     * @param response {@link OKResponse}
     */
    abstract public void onErrorResponse(OKResponse<T> response);

    // ============
    // = 内部处理 =
    // ============

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

        String data = jsonObject.optString(OKResponse.KEY_DATA);
        String code = jsonObject.optString(OKResponse.KEY_CODE);
        String message = jsonObject.optString(OKResponse.KEY_MESSAGE);
        boolean result = isSuccess(code);

        OKResponse.Builder<T> builder = new OKResponse.Builder<T>()
                .setOriginal(body).setCode(code)
                .setMessage(message).setResult(result);

        Type type = getGenericSuperclass(getClass(), 0);
        if (result && type != null) {
            try {
                builder.setData(
                        new Gson().fromJson(data, type)
                );
            } catch (Exception e) {
                DevLogger.eTag(TAG, e, "_response setData");
                builder.setResult(false).setException(e);
            }
        }

        OKResponse<T> okResponse = builder.build();
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
            if (code.equals("0000")) {
                return true;
            }
        }
        return false;
    }


    /**
     * 获取父类泛型类型
     * @param clazz {@link Class}
     * @param pos   泛型参数索引
     * @return 泛型类型
     */
    private Type getGenericSuperclass(final Class clazz, final int pos) {
        if (clazz != null && pos >= 0) {
            try {
                return ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()[pos];
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "getGenericSuperclass");
            }
        }
        return null;
    }
}