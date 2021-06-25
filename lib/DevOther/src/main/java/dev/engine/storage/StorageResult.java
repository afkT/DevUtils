package dev.engine.storage;

import android.net.Uri;

import java.io.File;

/**
 * detail: Storage Result
 * @author Ttt
 * <pre>
 *     外部存储时以 Uri 为准, 可在存储成功通过 {@link dev.utils.app.UriUtils#getFilePathByUri(Uri)}
 *     获取真实 File 存储地址 ( 部分 ROM 传入 RELATIVE_PATH 无效 ) 只会存储在对应 MimeType 根目录下
 * </pre>
 */
public final class StorageResult
        extends IStorageEngine.EngineResult {

    // 存储结果
    private final     boolean     mInsertResult;
    // 存储文件 Uri
    private transient Uri         mUri;
    // 存储文件地址
    private           File        mFile;
    // 异常信息
    private           Exception   mError;
    // 存储类型
    private           StorageType mType;
    // 是否外部存储
    private           boolean     mExternal;

    public StorageResult(boolean result) {
        this.mInsertResult = result;
    }

    // ==========
    // = 静态方法 =
    // ==========

    /**
     * 存储成功
     * @return {@link StorageResult}
     */
    public static StorageResult success() {
        return new StorageResult(true);
    }

    /**
     * 存储失败
     * @return {@link StorageResult}
     */
    public static StorageResult failure() {
        return new StorageResult(false);
    }

    // =============
    // = 对外公开方法 =
    // =============

    public boolean isSuccess() {
        return mInsertResult;
    }

    // =

    public Uri getUri() {
        return mUri;
    }

    public File getFile() {
        return mFile;
    }

    public Exception getError() {
        return mError;
    }

    public StorageType getType() {
        return mType;
    }

    public boolean isExternal() {
        return mExternal;
    }

    // =

    protected StorageResult setUri(Uri uri) {
        this.mUri = uri;
        return this;
    }

    protected StorageResult setFile(File file) {
        this.mFile = file;
        return this;
    }

    protected StorageResult setError(Exception error) {
        this.mError = error;
        return this;
    }

    protected StorageResult setType(StorageType type) {
        this.mType = type;
        return this;
    }

    protected StorageResult setExternal(boolean external) {
        this.mExternal = external;
        return this;
    }
}