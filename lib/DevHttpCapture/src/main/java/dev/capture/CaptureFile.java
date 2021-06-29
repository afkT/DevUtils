package dev.capture;

import java.io.File;

import dev.utils.common.FileUtils;

/**
 * detail: 抓包存储文件
 * @author Ttt
 * <pre>
 *     加密情况下, 抓包数据不会进行解析展示, 只能自行导出进行解密
 *     非加密情况下 {@link #httpCaptureData} 则会映射成 {@link CaptureInfo} 实体类
 * </pre>
 */
public final class CaptureFile {

    // 请求链接
    private String  url;
    // 请求方法
    private String  method;
    // 请求数据 ( 抓包数据 )
    private String  httpCaptureData;
    // 是否加密
    private boolean isEncrypt;
    // 创建时间 ( 本地时间戳 )
    private long    time;
    // 文件名
    private String  fileName;
    // 模块名
    private String  moduleName;

    // =======
    // = get =
    // =======

    public String getUrl() {
        return url;
    }

    public String getMethod() {
        return method;
    }

    public String getHttpCaptureData() {
        return httpCaptureData;
    }

    public boolean isEncrypt() {
        return isEncrypt;
    }

    public long getTime() {
        return time;
    }

    public String getFileName() {
        return fileName;
    }

    public String getModuleName() {
        return moduleName;
    }

    // =======
    // = set =
    // =======

    CaptureFile setUrl(String url) {
        this.url = url;
        return this;
    }

    CaptureFile setMethod(String method) {
        this.method = method;
        return this;
    }

    CaptureFile setHttpCaptureData(String httpCaptureData) {
        this.httpCaptureData = httpCaptureData;
        return this;
    }

    CaptureFile setEncrypt(boolean encrypt) {
        isEncrypt = encrypt;
        return this;
    }

    CaptureFile setTime(long time) {
        this.time = time;
        return this;
    }

    CaptureFile setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    CaptureFile setModuleName(String moduleName) {
        this.moduleName = moduleName;
        return this;
    }

    // ==========
    // = 其他处理 =
    // ==========

    // 抓包数据
    private transient CaptureInfo captureInfo = null;

    /**
     * 获取抓包数据实体类
     * @return 抓包数据实体类
     */
    public CaptureInfo getCaptureInfo() {
        if (!isEncrypt) {
            captureInfo = Utils.fromJson(
                    httpCaptureData, CaptureInfo.class
            );
        }
        return captureInfo;
    }

    /**
     * 将对象转换为 JSON String
     * @return JSON String
     */
    public String toJson() {
        return Utils.toJson(this);
    }

    // =============
    // = 文件操作相关 =
    // =============

    /**
     * 删除该对象抓包存储文件
     * @return {@code true} success, {@code false} fail
     */
    public boolean deleteFile() {
        return FileUtils.deleteFile(getFile());
    }

    /**
     * 获取该对象抓包存储文件
     * @return 该对象抓包存储文件
     */
    public File getFile() {
        return Utils.getModuleHttpCaptureFile(this);
    }
}