package dev.engine.storage

import android.graphics.Bitmap.CompressFormat
import android.net.Uri
import android.text.TextUtils
import dev.utils.app.MediaStoreUtils
import dev.utils.app.PathUtils
import dev.utils.common.FileUtils
import dev.utils.common.StringUtils
import java.io.File

/**
 * detail: Storage Item Params
 * @author Ttt
 * 可传入输出 Uri 或通过拼接路径创建 Uri 二选一
 * 内部存储时:
 * 必须传入 [.mFilePath]、[.mFileName]
 * [.mFileName] 需携带后缀
 * 可以使用快捷创建方法 [.createInternalItem]
 * 外部存储时:
 * 必须传入 [.mFileName]、[.mMimeType]、[.mFolder]
 * [.mFileName] 存储文件名是否需要后缀视 [.mMimeType] 情况而定
 * ( 正常无需后缀, 根据 mimeType 决定, 如果 mimeType 用了 xxx/ * 则需指定后缀 )
 * 可以使用快捷创建方法 [.createExternalItem]
 */
class StorageItem private constructor() : IStorageEngine.EngineItem() {

    // 输出 Uri ( 可以自行指定输出 Uri 优先使用该值 )
    @Transient
    private var mOutputUri: Uri? = null

    // =============
    // = 内外存储通用 =
    // =============

    // 存储文件名 ( 无需后缀, 根据 mimeType 决定, 如果 mimeType 用了 xxx/* 则需指定后缀 )
    private var mFileName: String? = null

    // =============
    // = 内部存储独有 =
    // =============

    // 存储路径 ( 不包含文件名, 纯路径 ) 只会在内部存储时使用
    private var mFilePath: String? = null

    // =============
    // = 外部存储独有 =
    // =============

    // 存储文件夹 ( 不包含完整路径, 只需要文件夹名 ) RELATIVE_PATH
    private var mFolder: String? = null // 例: /Pictures

    // 资源类型
    private var mMimeType: String? = null

    // ==============================================
    // = Storage DevSource 属于 Bitmap、Drawable 使用 =
    // ==============================================

    // 图片格式
    private var mFormat = CompressFormat.PNG

    // 图片质量
    private var mQuality = 100

    // =============
    // = 对外公开方法 =
    // =============

    fun getOutputUri(): Uri? {
        return mOutputUri
    }

    fun getFileName(): String? {
        return mFileName
    }

    fun getFilePath(): String? {
        return mFilePath
    }

    fun getFolder(): String? {
        return mFolder
    }

    fun getMimeType(): String? {
        return mMimeType
    }

    // =

    fun getFormat(): CompressFormat {
        return mFormat
    }

    fun setFormat(format: CompressFormat): StorageItem {
        mFormat = format
        return this
    }

    fun getQuality(): Int {
        return mQuality
    }

    fun setQuality(quality: Int): StorageItem {
        mQuality = quality
        return this
    }

    // =

    private fun setOutputUri(outputUri: Uri?): StorageItem {
        mOutputUri = outputUri
        return this
    }

    private fun setFileName(fileName: String?): StorageItem {
        mFileName = fileName
        return this
    }

    private fun setFilePath(filePath: String?): StorageItem {
        mFilePath = filePath
        return this
    }

    private fun setFolder(folder: String?): StorageItem {
        mFolder = folder
        return this
    }

    private fun setMimeType(mimeType: String?): StorageItem {
        mMimeType = mimeType
        return this
    }

    // ==========
    // = 快捷方法 =
    // ==========

    // ==========
    // = 路径获取 =
    // ==========

    /**
     * 获取内部存储完整路径
     * @return 内部存储完整路径
     */
    fun getInternalFile(): File? {
        // 创建文件夹
        FileUtils.createFolder(mFilePath)
        // filePath + fileName
        return FileUtils.getFile(mFilePath, mFileName)
    }

    // =

    /**
     * 获取外部存储完整路径
     * @return 外部存储完整路径
     */
    fun getExternalFile(): File? {
        return getExternalFile(mFileName)
    }

    /**
     * 获取外部存储完整路径
     * @param fileName 文件名
     * @return 外部存储完整路径
     */
    fun getExternalFile(fileName: String?): File? {
        val path = PathUtils.getSDCard().getSDCardPath(mFolder)
        // 创建文件夹
        FileUtils.createFolder(path)
        // SDCard/folder/fileName
        return FileUtils.getFile(path, fileName)
    }

    /**
     * 获取外部存储文件夹路径
     * @return 外部存储文件夹路径
     */
    fun getExternalFolder(): File? {
        val path = PathUtils.getSDCard().getSDCardPath(mFolder)
        // 创建文件夹
        FileUtils.createFolder(path)
        // SDCard/folder
        return FileUtils.getFile(path)
    }

    // ==========
    // = 快捷方法 =
    // ==========

    companion object {

        /**
         * 创建指定输出 Uri Item
         * @param outputUri 输出 Uri
         * @return [StorageItem]
         */
        fun createUriItem(outputUri: Uri?): StorageItem {
            return StorageItem().setOutputUri(outputUri)
        }

        // ==========
        // = 内部存储 =
        // ==========

        /**
         * 创建内部存储路径信息 Item
         * @param filePath 存储路径 ( 不包含文件名, 纯路径 ) 只会在内部存储时使用
         * @param fileName 存储文件名 ( 可不携带后缀 )
         * @return [StorageItem]
         */
        fun createInternalItem(
            filePath: String?,
            fileName: String?
        ): StorageItem {
            return StorageItem().setFilePath(filePath)
                .setFileName(fileName)
        }

        // ==========
        // = 外部存储 =
        // ==========

        /**
         * 创建外部存储路径信息 Item
         * @param fileName 存储文件名 ( 必须携带后缀 )
         * @return [StorageItem]
         * 根据 fileName 获取后缀推导出 mimeType
         * 如果系统不支持的格式、文件名不含后缀则可能获取失败 ( 将直接返回 null Item )
         */
        fun createExternalItem(fileName: String?): StorageItem? {
            val fileExtension = FileUtils.getFileExtension(fileName)
            val mimeType = MediaStoreUtils.getMimeTypeFromExtension(fileExtension) ?: return null
            val name = FileUtils.getFileNameNoExtension(fileName)
            return createExternalItem(name, mimeType)
        }

        /**
         * 创建外部存储路径信息 Item
         * @param fileName 存储文件名 ( 无需后缀, 根据 mimeType 决定, 如果 mimeType 用了 xxx/ * 则需指定后缀 )
         * @param mimeType 资源类型
         * @return [StorageItem]
         */
        fun createExternalItem(
            fileName: String?,
            mimeType: String?
        ): StorageItem? {
            return createExternalItem(
                fileName, mimeType,
                StorageType.getTypeRelativePath(mimeType)
            )
        }

        /**
         * 创建外部存储路径信息 Item
         * @param fileName 存储文件名 ( 无需后缀, 根据 mimeType 决定, 如果 mimeType 用了 xxx/ * 则需指定后缀 )
         * @param mimeType 资源类型
         * @param folder   存储文件夹 ( 不包含完整路径, 只需要文件夹名 )
         * @return [StorageItem]
         */
        fun createExternalItem(
            fileName: String?,
            mimeType: String?,
            folder: String?
        ): StorageItem? {
            if (TextUtils.isEmpty(fileName)) return null
            if (TextUtils.isEmpty(mimeType)) return null
            if (TextUtils.isEmpty(folder)) return null
            return StorageItem().setFileName(fileName)
                .setMimeType(mimeType).setFolder(folder)
        }

        // ==========
        // = 额外方法 =
        // ==========

        /**
         * 创建外部存储路径信息 Item
         * @param fileName 存储文件名 ( 必须携带后缀 )
         * @param folder   存储文件夹 ( 不包含完整路径, 只需要文件夹名 )
         * @return [StorageItem]
         * 根据 fileName 获取后缀推导出 mimeType
         * 如果系统不支持的格式、文件名不含后缀则可能获取失败 ( 将直接返回 null Item )
         */
        fun createExternalItemFolder(
            fileName: String?,
            folder: String?
        ): StorageItem? {
            val fileExtension = FileUtils.getFileExtension(fileName)
            val mimeType = MediaStoreUtils.getMimeTypeFromExtension(fileExtension) ?: return null
            val name = FileUtils.getFileNameNoExtension(fileName)
            return createExternalItem(name, mimeType, folder)
        }
    }
}