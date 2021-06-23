package dev.engine.storage;

import android.net.Uri;

import java.io.File;

/**
 * detail: Storage Result
 * @author Ttt
 */
public class StorageResult
        extends IStorageEngine.EngineResult {

    // 前置条件校验结果 ( 如传入参数判 null, 以及 DevSource 是否有效 )
    private final boolean preCheck;
    // 存储文件地址
    private       File    file;
    // 存储文件 Uri
    private       Uri     uri;

    public StorageResult(boolean preCheck) {
        this.preCheck = preCheck;
    }

    // ==========
    // = 静态方法 =
    // ==========

    /**
     * 前置条件校验成功
     * @return {@link StorageResult}
     */
    public static StorageResult success() {
        return new StorageResult(true);
    }

    /**
     * 前置条件校验失败
     * @return {@link StorageResult}
     */
    public static StorageResult failure() {
        return new StorageResult(true);
    }

    // =============
    // = 对外公开方法 =
    // =============

    public boolean isPreCheck() {
        return preCheck;
    }

    // =

    public File getFile() {
        return file;
    }

    public StorageResult setFile(File file) {
        this.file = file;
        return this;
    }

    public Uri getUri() {
        return uri;
    }

    public StorageResult setUri(Uri uri) {
        this.uri = uri;
        return this;
    }
}