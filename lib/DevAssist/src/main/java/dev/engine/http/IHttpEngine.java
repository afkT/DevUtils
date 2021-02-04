package dev.engine.http;

/**
 * detail: Http Engine 接口
 * @author Ttt
 */
public interface IHttpEngine {

    /**
     * detail: Request Object
     * @author Ttt
     */
    class Request {
    }

    /**
     * detail: Request Response
     * @author Ttt
     */
    class Response {

        // Request
        protected Request request;
        // 发送请求时间
        protected long    sentRequestAtMillis;
        // 请求响应时间
        protected long    receivedResponseAtMillis;

        /**
         * 获取 Request Object
         * @param <T> 泛型
         * @return Request Object
         */
        public final <T extends Request> T getRequest() {
            try {
                return (T) request;
            } catch (Exception e) {
            }
            return null;
        }

        /**
         * 获取发送请求时间
         * @return 发送请求时间
         */
        public final long getSentRequestAtMillis() {
            return sentRequestAtMillis;
        }

        /**
         * 获取请求响应时间
         * @return 请求响应时间
         */
        public final long getReceivedResponseAtMillis() {
            return receivedResponseAtMillis;
        }
    }

    /**
     * detail: Request Call
     * @author Ttt
     */
    interface Call {

        /**
         * 获取 Request Object
         * @param <T> 泛型
         * @return Request Object
         */
        <T extends Request> T getRequest();

        // ========
        // = 状态 =
        // ========

        /**
         * 是否取消请求
         * @return {@code true} yes, {@code false} no
         */
        boolean isCanceled();

        /**
         * 是否执行过请求
         * @return {@code true} yes, {@code false} no
         */
        boolean isExecuted();

        /**
         * 是否请求结束
         * @return {@code true} yes, {@code false} no
         */
        boolean isEnd();

        // ===========
        // = 操作方法 =
        // ===========

        /**
         * 取消请求
         */
        void cancel();

        /**
         * 开始请求方法 ( 同步 )
         * @return {@code true} success, {@code false} fail
         */
        boolean start();

        /**
         * 开始请求方法 ( 异步 )
         * @return {@code true} success, {@code false} fail
         */
        boolean startAsync();
    }

    /**
     * detail: Request Callback
     * @author Ttt
     */
    abstract class RequestCallback<T extends Response> {

        /**
         * 开始请求
         * @param call {@link Call}
         */
        public void onStart(Call call) {
        }

        /**
         * 请求取消
         * @param call {@link Call}
         */
        public void onCancel(Call call) {
        }

        /**
         * 请求响应
         * @param call     {@link Call}
         * @param response {@link Response}
         */
        public abstract void onResponse(
                Call call,
                T response
        );

        /**
         * 请求失败
         * @param call      {@link Call}
         * @param throwable {@link Throwable}
         */
        public abstract void onFailure(
                Call call,
                Throwable throwable
        );
    }

    // =============
    // = 获取 Call =
    // =============

    /**
     * 获取 Request Call Object
     * @param request  {@link Request}
     * @param callback {@link RequestCallback}
     * @return {@link Call}
     */
    <Req extends Request, Resp extends Response> Call newCall(
            Req request,
            RequestCallback<Resp> callback
    );

    // ===========
    // = 操作方法 =
    // ===========

    /**
     * 取消请求 ( 全部 )
     */
    void cancelAll();

    /**
     * 取消请求
     * @param call {@link Call}
     */
    void cancelCall(Call call);

    /**
     * 取消请求
     * @param url Request Url
     */
    void cancelUrl(String url);

    /**
     * 取消请求
     * @param object Object Tag
     */
    void cancelTag(Object object);
}