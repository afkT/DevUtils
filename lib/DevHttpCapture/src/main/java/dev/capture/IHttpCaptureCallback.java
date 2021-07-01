package dev.capture;

/**
 * detail: Http 抓包成功回调接口
 * @author Ttt
 */
public interface IHttpCaptureCallback {

    /**
     * 抓包成功回调
     * @param captureInfo 抓包数据
     */
    void callback(CaptureInfo captureInfo);
}