package dev.utils.common;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

import dev.utils.JCLogUtils;

/**
 * detail: HttpURLConnection 网络工具类
 * @author Ttt
 * <pre>
 *     详细解释 HttpURLConnection 类
 *     @see <a href="https://www.cnblogs.com/jiuhaoyun/p/8040028.html"/>
 *     @see <a href="https://blog.csdn.net/you18131371836/article/details/53189966"/>
 * </pre>
 */
public final class HttpURLConnectionUtils {

    private HttpURLConnectionUtils() {
    }

    // 日志 TAG
    private static final String TAG = HttpURLConnectionUtils.class.getSimpleName();

    // 请求超时时间
    private static final int TIMEOUT_IN_MILLIONS = 5000;

    /**
     * detail: 请求回调
     * @author Ttt
     */
    public interface Callback {

        /**
         * 请求响应回调
         * @param result   请求结果
         * @param response 请求响应时间
         */
        void onResponse(
                String result,
                long response
        );

        /**
         * 请求失败
         * @param e 失败异常
         */
        void onFail(Exception e);
    }

    /**
     * 异步的 Get 请求
     * @param urlStr   请求地址
     * @param callback 请求回调接口
     */
    public static void doGetAsync(
            final String urlStr,
            final Callback callback
    ) {
        new Thread() {
            public void run() {
                try {
                    request("GET", urlStr, null, null, callback);
                } catch (Exception e) {
                    JCLogUtils.eTag(TAG, e, "doGetAsync");
                }
            }
        }.start();
    }

    /**
     * 异步的 Post 请求
     * @param urlStr   请求地址
     * @param params   请求参数
     * @param callback 请求回调接口
     */
    public static void doPostAsync(
            final String urlStr,
            final String params,
            final Callback callback
    ) {
        new Thread() {
            public void run() {
                try {
                    request("POST", urlStr, null, params, callback);
                } catch (Exception e) {
                    JCLogUtils.eTag(TAG, e, "doPostAsync");
                }
            }
        }.start();
    }

    /**
     * 发送请求
     * @param method   请求方法
     * @param urlStr   请求地址字符串
     * @param headers  请求头信息
     * @param params   请求参数
     * @param callback 请求回调接口
     */
    public static void request(
            final String method,
            final String urlStr,
            final Map<String, String> headers,
            final String params,
            final Callback callback
    ) {
        // 获取连接对象
        HttpURLConnection     connection = null;
        InputStream           is         = null;
        ByteArrayOutputStream baos       = null;
        try {
            // 请求路径
            URL url = new URL(urlStr);
            // 获取连接对象
            connection = (HttpURLConnection) url.openConnection();
            // 设置请求方法
            connection.setRequestMethod(method);
            // 设置请求头信息
            if (headers != null) {
                Iterator<Map.Entry<String, String>> iterator = headers.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, String> entry = iterator.next();
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            // 判断是否需要写入数据
            if (params != null && params.length() != 0) {
                // 允许写入
                connection.setDoInput(true);
                // 设置是否向 connection 输出, 如果是 post 请求, 参数要放在 http 正文内, 因此需要设为 true
                connection.setDoOutput(true);
                // post 请求不能使用缓存
                connection.setUseCaches(false);
                // 写入数据
                OutputStream os = connection.getOutputStream();
                os.write(params.getBytes());
                os.flush();
                os.close();
            }
            // 单位是毫秒
            connection.setConnectTimeout(TIMEOUT_IN_MILLIONS); // 设置连接超时
            connection.setReadTimeout(TIMEOUT_IN_MILLIONS); // 设置读取超时
            // 获取请求状态码
            int responseCode = connection.getResponseCode();
            // 判断请求码是否是 200
            if (responseCode >= 200 && responseCode < 300) {
                // 输入流
                is = connection.getInputStream();
                baos = new ByteArrayOutputStream();
                // 设置缓存流大小
                byte[] buffer = new byte[1024];
                int    len;
                while (((len = is.read(buffer)) != -1)) {
                    baos.write(buffer, 0, len);
                }
                // 获取请求结果
                String result = new String(baos.toByteArray());
                // 判断是否回调
                if (callback != null) {
                    // 请求成功, 触发回调
                    callback.onResponse(result, connection.getDate());
                }
            } else {
                // 响应成功, 非 200 直接返回 null
                if (callback != null) {
                    callback.onFail(new Exception("responseCode not >= 200 or < 300, code: " + responseCode));
                }
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "request");
            if (callback != null) {
                callback.onFail(e);
            }
        } finally {
            CloseUtils.closeIOQuietly(baos, is);

            if (connection != null) {
                try { // 关闭底层连接 Socket
                    connection.disconnect();
                } catch (Exception ignore) {
                }
            }
        }
    }

    // ===================
    // = 获取网络时间处理 =
    // ===================

    public static final String BAIDU_URL = "https://www.baidu.com";

    /**
     * detail: 时间回调
     * @author Ttt
     */
    public interface TimeCallback {

        /**
         * 请求响应回调
         * @param time 响应时间 ( 毫秒 )
         */
        void onResponse(long time);

        /**
         * 请求失败
         * @param e 失败异常
         */
        void onFail(Exception e);
    }

    /**
     * 获取网络时间 ( 默认使用百度链接 )
     * @param callback 请求时间回调接口
     */
    public static void getNetTime(final TimeCallback callback) {
        getNetTime(BAIDU_URL, callback);
    }

    /**
     * 获取网络时间
     * @param urlStr   请求地址
     * @param callback 请求时间回调接口
     */
    public static void getNetTime(
            final String urlStr,
            final TimeCallback callback
    ) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                reqNetTime(urlStr, callback);
            }
        }).start();
    }

    /**
     * 请求网络时间 ( 内部私有 )
     * @param urlStr   请求地址
     * @param callback 请求时间回调接口
     */
    private static void reqNetTime(
            final String urlStr,
            final TimeCallback callback
    ) {
        // 获取连接对象
        HttpURLConnection connection = null;
        try {
            // 请求路径
            URL url = new URL(urlStr);
            // 获取连接对象
            connection = (HttpURLConnection) url.openConnection();
            // 获取时间
            long date = connection.getDate();
            // 获取失败, 则进行修改
            if (date <= 0) {
                date = -1L;
            }
            // 触发回调
            if (callback != null) {
                callback.onResponse(date);
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getNetTime");
            // 触发回调
            if (callback != null) {
                callback.onFail(e);
            }
        } finally {
            if (connection != null) {
                try { // 关闭底层连接 Socket
                    connection.disconnect();
                } catch (Exception ignore) {
                }
            }
        }
    }
}