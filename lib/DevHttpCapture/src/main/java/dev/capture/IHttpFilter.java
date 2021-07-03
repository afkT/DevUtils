package dev.capture;

import okhttp3.Headers;
import okhttp3.Protocol;

/**
 * detail: Http 拦截过滤器
 * @author Ttt
 */
public interface IHttpFilter {

    /**
     * 是否过滤该 Http 请求不进行抓包存储
     * <pre>
     *     返回 true 则不进行存储 Http 请求信息
     * </pre>
     * @param url      请求链接
     * @param method   请求方法
     * @param protocol 请求协议
     * @param headers  请求头部信息
     * @return {@code true} yes, {@code false} no
     */
    boolean filter(
            String url,
            String method,
            Protocol protocol,
            Headers headers
    );
}