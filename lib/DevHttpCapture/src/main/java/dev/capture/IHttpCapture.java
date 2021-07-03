package dev.capture;

import java.util.List;

import dev.utils.common.cipher.Encrypt;

/**
 * detail: Http 抓包接口信息获取
 * @author Ttt
 */
public interface IHttpCapture {

    // ==========
    // = 基础方法 =
    // ==========

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
     * 获取 Http 拦截过滤器
     * @return {@link IHttpFilter}
     */
    IHttpFilter getHttpFilter();

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

    // ==========
    // = 获取操作 =
    // ==========

    /**
     * 获取模块抓包存储路径
     * @return 模块抓包存储路径
     */
    String getModulePath();

    /**
     * 获取模块所有抓包数据
     * @return 模块所有抓包数据集合
     */
    List<CaptureFile> getModuleHttpCaptures();
}