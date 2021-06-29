package dev.capture;

import dev.utils.common.cipher.Encrypt;

/**
 * detail: Http 抓包接口信息获取
 * @author Ttt
 */
public interface IHttpCapture {

    /**
     * 获取模块名 ( 要求唯一性 )
     * @return 模块名
     */
    String getModuleName();

    /**
     * 获取抓包数据加密中间层
     * @return {@link Encrypt}
     */
    Encrypt getEncrypt();

    /**
     * 是否进行 Http 抓包拦截
     * @return {@code true} yes, {@code false} no
     */
    boolean isCapture();

    /**
     * 设置是否进行 Http 抓包拦截
     * @param capture {@code true} yes, {@code false} no
     */
    void setCapture(boolean capture);
}