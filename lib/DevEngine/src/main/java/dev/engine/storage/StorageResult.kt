package dev.engine.storage

import android.net.Uri
import java.io.File

/**
 * detail: Storage Result
 * @author Ttt
 * 外部存储时以 Uri 为准, 可在存储成功通过 [dev.utils.app.UriUtils.getFilePathByUri]
 * 获取真实 File 存储地址 ( 部分 ROM 传入 RELATIVE_PATH 无效 ) 只会存储在对应 MimeType 根目录下
 */
class StorageResult(
    // 存储结果
    private val mInsertResult: Boolean
) : IStorageEngine.EngineResult() {

    // 存储文件 Uri
    @Transient
    private var mUri: Uri? = null

    // 存储文件地址
    private var mFile: File? = null

    // 异常信息
    private var mError: Exception? = null

    // 存储类型
    private var mType: StorageType? = null

    // 是否外部存储
    private var mExternal = false

    // ==========
    // = 静态方法 =
    // ==========

    companion object {

        /**
         * 存储成功
         * @return [StorageResult]
         */
        fun success(): StorageResult {
            return StorageResult(true)
        }

        /**
         * 存储失败
         * @return [StorageResult]
         */
        fun failure(): StorageResult {
            return StorageResult(false)
        }
    }

    // =============
    // = 对外公开方法 =
    // =============

    fun isSuccess(): Boolean {
        return mInsertResult
    }

    // =

    fun getUri(): Uri? {
        return mUri
    }

    fun getFile(): File? {
        return mFile
    }

    fun getError(): Exception? {
        return mError
    }

    fun getType(): StorageType? {
        return mType
    }

    fun isExternal(): Boolean {
        return mExternal
    }

    // =

    fun setUri(uri: Uri?): StorageResult {
        mUri = uri
        return this
    }

    fun setFile(file: File?): StorageResult {
        mFile = file
        return this
    }

    fun setError(error: Exception?): StorageResult {
        mError = error
        return this
    }

    fun setType(type: StorageType?): StorageResult {
        mType = type
        return this
    }

    fun setExternal(external: Boolean): StorageResult {
        mExternal = external
        return this
    }
}