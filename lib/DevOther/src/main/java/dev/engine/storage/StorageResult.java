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
    private final boolean   mPreCheck;
    // 存储文件地址
    private       File      mFile;
    // 存储文件 Uri
    private       Uri       mUri;
    // 异常信息
    private       Exception mError;
    // 存储类型
    private       TYPE      mType;

    public StorageResult(boolean correct) {
        this.mPreCheck = correct;
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
        return new StorageResult(false);
    }

    // =============
    // = 对外公开方法 =
    // =============

    public boolean isCorrect() {
        return mPreCheck;
    }

    // =

    public File getFile() {
        return mFile;
    }

    public StorageResult setFile(File file) {
        this.mFile = file;
        return this;
    }

    public Uri getUri() {
        return mUri;
    }

    public StorageResult setUri(Uri uri) {
        this.mUri = uri;
        return this;
    }

    public Exception getError() {
        return mError;
    }

    public StorageResult setError(Exception error) {
        this.mError = error;
        return this;
    }

    public TYPE getType() {
        return mType;
    }

    public StorageResult setType(TYPE type) {
        this.mType = type;
        return this;
    }

    // ========
    // = Type =
    // ========

    /**
     * detail: 存储类型
     * @author Ttt
     */
    public enum TYPE {
        IMAGE,
        VIDEO,
        AUDIO,
        DOWNLOAD,
        NONE
    }
}