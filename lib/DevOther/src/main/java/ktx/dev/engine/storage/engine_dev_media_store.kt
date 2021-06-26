package ktx.dev.engine.storage

import android.net.Uri
import android.os.Build
import dev.base.DevSource
import dev.engine.storage.IStorageEngine
import dev.engine.storage.listener.OnInsertListener
import dev.utils.app.MediaStoreUtils
import dev.utils.app.UriUtils
import dev.utils.app.image.ImageUtils
import dev.utils.common.FileIOUtils
import dev.utils.common.FileUtils
import dev.utils.common.StreamUtils
import dev.utils.common.StringUtils
import java.io.File

/**
 * detail: 插入多媒体资源事件 封装接口
 * @author Ttt
 * 减少需要 OnInsertListener<StorageItem, StorageResult> 声明使用
 * 内部直接封装具体泛型类
 */
interface OnDevInsertListener : OnInsertListener<StorageItem, StorageResult>

/**
 * detail: DevUtils MediaStore Engine 实现
 * @author Ttt
 */
class DevMediaStoreEngineImpl : IStorageEngine<StorageItem, StorageResult> {

    // ==========
    // = 外部存储 =
    // ==========

    /**
     * 插入一张图片到外部存储空间 ( SDCard )
     * @param params   {@link EngineItem}
     * @param source   {@link DevSource}
     * @param listener {@link OnInsertListener}
     */
    override fun insertImageToExternal(
        params: StorageItem?,
        source: DevSource?,
        listener: OnInsertListener<StorageItem, StorageResult>?
    ) {
        if (insidePreCheck(params, source, listener, true, StorageType.IMAGE)) {
            insideInsertToExternal(params, source, listener, StorageType.IMAGE)
        }
    }

    /**
     * 插入一条视频到外部存储空间 ( SDCard )
     * @param params   {@link EngineItem}
     * @param source   {@link DevSource}
     * @param listener {@link OnInsertListener}
     */
    override fun insertVideoToExternal(
        params: StorageItem?,
        source: DevSource?,
        listener: OnInsertListener<StorageItem, StorageResult>?
    ) {
        if (insidePreCheck(params, source, listener, true, StorageType.VIDEO)) {
            insideInsertToExternal(params, source, listener, StorageType.VIDEO)
        }
    }

    /**
     * 插入一条音频到外部存储空间 ( SDCard )
     * @param params   {@link EngineItem}
     * @param source   {@link DevSource}
     * @param listener {@link OnInsertListener}
     */
    override fun insertAudioToExternal(
        params: StorageItem?,
        source: DevSource?,
        listener: OnInsertListener<StorageItem, StorageResult>?
    ) {
        if (insidePreCheck(params, source, listener, true, StorageType.AUDIO)) {
            insideInsertToExternal(params, source, listener, StorageType.AUDIO)
        }
    }

    /**
     * 插入一条文件资源到外部存储空间 ( SDCard )
     * @param params   {@link EngineItem}
     * @param source   {@link DevSource}
     * @param listener {@link OnInsertListener}
     */
    override fun insertDownloadToExternal(
        params: StorageItem?,
        source: DevSource?,
        listener: OnInsertListener<StorageItem, StorageResult>?
    ) {
        if (insidePreCheck(params, source, listener, true, StorageType.DOWNLOAD)) {
            insideInsertToExternal(params, source, listener, StorageType.DOWNLOAD)
        }
    }

    /**
     * 插入一条多媒体资源到外部存储空间 ( SDCard )
     * @param params   {@link EngineItem}
     * @param source   {@link DevSource}
     * @param listener {@link OnInsertListener}
     * 并不局限于多媒体, 如文本存储、其他文件写入等
     */
    override fun insertMediaToExternal(
        params: StorageItem?,
        source: DevSource?,
        listener: OnInsertListener<StorageItem, StorageResult>?
    ) {
        if (insidePreCheck(params, source, listener, true, StorageType.NONE)) {
            insideInsertToExternal(params, source, listener, StorageType.NONE)
        }
    }

    // ==========
    // = 内部存储 =
    // ==========

    /**
     * 插入一张图片到内部存储空间
     * @param params   {@link EngineItem}
     * @param source   {@link DevSource}
     * @param listener {@link OnInsertListener}
     */
    override fun insertImageToInternal(
        params: StorageItem?,
        source: DevSource?,
        listener: OnInsertListener<StorageItem, StorageResult>?
    ) {
        if (insidePreCheck(params, source, listener, false, StorageType.IMAGE)) {
            insideInsertToInternal(params, source, listener, StorageType.IMAGE)
        }
    }

    /**
     * 插入一条视频到内部存储空间
     * @param params   {@link EngineItem}
     * @param source   {@link DevSource}
     * @param listener {@link OnInsertListener}
     */
    override fun insertVideoToInternal(
        params: StorageItem?,
        source: DevSource?,
        listener: OnInsertListener<StorageItem, StorageResult>?
    ) {
        if (insidePreCheck(params, source, listener, false, StorageType.VIDEO)) {
            insideInsertToInternal(params, source, listener, StorageType.VIDEO)
        }
    }

    /**
     * 插入一条音频到内部存储空间
     * @param params   {@link EngineItem}
     * @param source   {@link DevSource}
     * @param listener {@link OnInsertListener}
     */
    override fun insertAudioToInternal(
        params: StorageItem?,
        source: DevSource?,
        listener: OnInsertListener<StorageItem, StorageResult>?
    ) {
        if (insidePreCheck(params, source, listener, false, StorageType.AUDIO)) {
            insideInsertToInternal(params, source, listener, StorageType.AUDIO)
        }
    }

    /**
     * 插入一条文件资源到内部存储空间
     * @param params   {@link EngineItem}
     * @param source   {@link DevSource}
     * @param listener {@link OnInsertListener}
     */
    override fun insertDownloadToInternal(
        params: StorageItem?,
        source: DevSource?,
        listener: OnInsertListener<StorageItem, StorageResult>?
    ) {
        if (insidePreCheck(params, source, listener, false, StorageType.DOWNLOAD)) {
            insideInsertToInternal(params, source, listener, StorageType.DOWNLOAD)
        }
    }

    /**
     * 插入一条多媒体资源到内部存储空间
     * @param params   {@link EngineItem}
     * @param source   {@link DevSource}
     * @param listener {@link OnInsertListener}
     * 并不局限于多媒体, 如文本存储、其他文件写入等
     */
    override fun insertMediaToInternal(
        params: StorageItem?,
        source: DevSource?,
        listener: OnInsertListener<StorageItem, StorageResult>?
    ) {
        if (insidePreCheck(params, source, listener, false, StorageType.NONE)) {
            insideInsertToInternal(params, source, listener, StorageType.NONE)
        }
    }

    // ==========
    // = 内部方法 =
    // ==========

    // ========
    // = Type =
    // ========

    /**
     * 当属于 NONE 类型则进行只能校验
     * @param params 原始参数
     * @param source 原始数据
     * @param type   存储类型
     * @return 输出 Uri
     */
    private fun convertType(
        params: StorageItem?,
        source: DevSource?,
        type: StorageType
    ): StorageType {
        if (type == StorageType.NONE) {
            params?.let {
                var typeResult = StorageType.convertTypeByMimeType(
                    it.getMimeType()
                )
                if (!typeResult.isNone()) return typeResult

                typeResult = StorageType.convertTypeByFileName(
                    it.getFileName()
                )
                if (!typeResult.isNone()) return typeResult
            }
            // 其他未知都放到 Download 文件夹下
            return StorageType.DOWNLOAD;
        }
        return type
    }

    /**
     * 通用内部预校验
     * @param params   原始参数
     * @param source   原始数据
     * @param listener 回调接口
     * @param external 是否外部存储
     * @param type     存储类型
     * @return `true` 校验通过, `false` 校验失败
     */
    private fun insidePreCheck(
        params: StorageItem?,
        source: DevSource?,
        listener: OnInsertListener<StorageItem, StorageResult>?,
        external: Boolean,
        type: StorageType
    ): Boolean {
        // 判断参数是否有效
        if (params != null && source != null && source.isSource) {
            return true
        }
        // 无效数据触发回调
        if (listener != null) {
            val result = StorageResult.failure()
                .setExternal(external)
                .setType(convertType(params, source, type))
            if (params == null) {
                result.setError(Exception("params is null"))
            }
            if (source == null || !source.isSource) {
                if (params == null) {
                    result.setError(Exception("params、source is null"))
                } else {
                    result.setError(Exception("source is null"))
                }
            }
            listener.onResult(result, params, source)
        }
        return false
    }

    // ============
    // = Uri、File =
    // ============

    /**
     * 获取输出 Uri ( 存储文件 Uri )
     * @param params   原始参数
     * @param source   原始数据
     * @param external 是否外部存储
     * @param type     存储类型
     * @return 输出 Uri
     */
    private fun getOutputUri(
        params: StorageItem,
        source: DevSource,
        external: Boolean,
        type: StorageType
    ): Uri? {
        if (params.getOutputUri() != null) {
            return params.getOutputUri()
        }
        // 外部存储才需要进行适配
        if (external) {
            // Android 9.0 及以下版本则直接使用 File 读写
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
//                if (type == StorageType.DOWNLOAD) {
//                    // 低版本直接创建 SDCard/Download File
//                    val file = getOutputFile(params, source, external, type);
//                    return MediaStoreUtils.createUriByFile(file);
//                }
                // 目前属于 IMAGE、VIDEO、AUDIO、DOWNLOAD 低版本都通过 File 读写
                // 如果需要 IMAGE、VIDEO、AUDIO 使用 MediaStore 则放开上面注释
                // 低版本直接创建 SDCard/Pictures、DCIM、Music、Download File
                val file = getOutputFile(params, source, external, type)
                return MediaStoreUtils.createUriByFile(file)
            }
            when (type) {
                // 创建 Image Uri
                StorageType.IMAGE -> {
                    return MediaStoreUtils.createImageUri(
                        params.getFileName(),
                        params.getMimeType(),
                        params.getFolder()
                    )
                }
                // 创建 Video Uri
                StorageType.VIDEO -> {
                    return MediaStoreUtils.createVideoUri(
                        params.getFileName(),
                        params.getMimeType(),
                        params.getFolder()
                    )
                }
                // 创建 Audio Uri
                StorageType.AUDIO -> {
                    return MediaStoreUtils.createAudioUri(
                        params.getFileName(),
                        params.getMimeType(),
                        params.getFolder()
                    )
                }
                // 创建 Download Uri
                StorageType.DOWNLOAD -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        return MediaStoreUtils.createDownloadUri(
                            params.getFileName(),
                            params.getMimeType(),
                            params.getFolder()
                        )
                    }
                }
            }
        } else { // 内部存储路径
            val file = getOutputFile(params, source, external, type)
            // FileProvider File Path Uri
            return UriUtils.getUriForFile(file)
        }
        return null
    }

    /**
     * 获取输出文件路径
     * @param params   原始参数
     * @param source   原始数据
     * @param external 是否外部存储
     * @param type     存储类型
     * @return 输出文件路径
     */
    private fun getOutputFile(
        params: StorageItem,
        source: DevSource,
        external: Boolean,
        type: StorageType
    ): File? {
        if (params.getOutputUri() != null) {
            val uriPath = UriUtils.getFilePathByUri(
                params.getOutputUri()
            )
            if (uriPath != null) {
                return FileUtils.getFile(uriPath)
            }
        }
        if (external) { // 外部存储路径
            when (type) {
                StorageType.IMAGE, StorageType.VIDEO, StorageType.AUDIO, StorageType.DOWNLOAD -> {
                    // 需考虑 fileName 是否存在后缀情况 ( 如果 mimeType 用了 xxx/* 则需指定后缀 )
                    params.getMimeType()?.let { mimeType ->
                        // 属于 * 自动识别, 则 fileName 要求指定后缀 ( 直接拼接即可 )
                        if (mimeType.contains("*")) {
                            // SDCard/folder/fileName
                            return params.getExternalFile()
                        } else {
                            // 进行获取文件后缀 ( 不含 . )
                            val extension = MediaStoreUtils.getExtensionFromMimeType(
                                mimeType
                            )
                            // 存在后缀才进行拼接
                            if (StringUtils.isNotEmpty(extension)) {
                                // fileName.extension ( 小写后缀 )
                                val fileName =
                                    params.getFileName() + "." + extension.toLowerCase()
                                // SDCard/folder/fileName.extension
                                return params.getExternalFile(fileName)
                            }
                            // 无法获取到后缀, 可考虑进行拼接返回
                            // SDCard/folder/fileName
                            return params.getExternalFile()
                        }
                    }
                }
            }
        } else { // 内部存储路径
            return params.getInternalFile()
        }
        return null
    }

    // =================
    // = 内部插入数据方法 =
    // =================

    /**
     * 内部插入数据方法 ( 外部存储空间 )
     * @param params   原始参数
     * @param source   原始数据
     * @param listener 回调接口
     * @param type     存储类型
     */
    private fun insideInsertToExternal(
        params: StorageItem?,
        source: DevSource?,
        listener: OnInsertListener<StorageItem, StorageResult>?,
        type: StorageType
    ) {
        insideInsertThread(
            params!!, source!!, listener, true,
            convertType(params, source, type)
        )
    }

    /**
     * 内部插入数据方法 ( 内部存储空间 )
     * @param params   原始参数
     * @param source   原始数据
     * @param listener 回调接口
     * @param type     存储类型
     */
    private fun insideInsertToInternal(
        params: StorageItem?,
        source: DevSource?,
        listener: OnInsertListener<StorageItem, StorageResult>?,
        type: StorageType
    ) {
        insideInsertThread(
            params!!, source!!, listener, false,
            convertType(params, source, type)
        )
    }

    /**
     * 内部插入数据方法 ( 后台运行 )
     * @param params   原始参数
     * @param source   原始数据
     * @param listener 回调接口
     * @param external 是否外部存储
     * @param type     存储类型
     */
    private fun insideInsertThread(
        params: StorageItem,
        source: DevSource,
        listener: OnInsertListener<StorageItem, StorageResult>?,
        external: Boolean,
        type: StorageType
    ) {
        Thread {
            try {
                insideInsertFinal(params, source, listener, external, type)
            } catch (e: java.lang.Exception) {
                listener?.onResult(
                    StorageResult.failure().setError(e),
                    params, source
                )
            }
        }.start()
    }

    /**
     * 内部插入数据方法 ( 最终方法 )
     * @param params   原始参数
     * @param source   原始数据
     * @param listener 回调接口
     * @param external 是否外部存储
     * @param type     存储类型
     */
    private fun insideInsertFinal(
        params: StorageItem,
        source: DevSource,
        listener: OnInsertListener<StorageItem, StorageResult>?,
        external: Boolean,
        type: StorageType
    ) {
        // 获取输出 Uri
        val outputUri = getOutputUri(params, source, external, type)
        // 获取输出文件路径
        val outputFile = getOutputFile(params, source, external, type)

        // =============
        // = 数据类型判断 =
        // =============

        // 存储结果
        var insertResult = false

        if (outputUri != null) {
            // 属于 Bitmap、Drawable
            if (source.isBitmap || source.isDrawable) {
                var bitmap = source.mBitmap
                if (source.isDrawable) {
                    bitmap = ImageUtils.drawableToBitmap(source.mDrawable)
                }
                insertResult = MediaStoreUtils.insertMedia(
                    outputUri, bitmap, params.getFormat(), params.getQuality()
                )
            }

            // 属于 byte[]、File、InputStream
            if (source.isBytes || source.isFile || source.isInputStream) {
                var inputStream = source.mInputStream
                if (source.isBytes) {
                    inputStream = StreamUtils.bytesToInputStream(source.mBytes)
                } else if (source.isFile) {
                    inputStream = FileIOUtils.getFileInputStream(source.mFile)
                }
                insertResult = MediaStoreUtils.insertMedia(
                    outputUri, inputStream
                )
            }

            // 属于 Uri
            if (source.isUri) {
                insertResult = MediaStoreUtils.insertMedia(
                    outputUri, source.mUri
                )
            }

            // url: 暂不处理 Url 文件 ( 涉及下载 ) 自行下载后传入 File
            // resource Id: 无法确认属于 drawable、raw、assets 等 id ( 自行传入 Bitmap、InputStream、byte[] )
            if (source.isUrl || source.isResource) {
            }
        }

        // ==========
        // = 结果回调 =
        // ==========

        val result =
            if (insertResult) StorageResult.success() else StorageResult.failure()
        // 保存结果信息
        result.setUri(outputUri).setFile(outputFile)
            .setExternal(external).setType(type)
        // 输出 Uri 为 null, 则设置 Error
        if (outputUri == null) {
            result.setError(java.lang.Exception("outputUri is null"))
        }
        // 属于外部存储并且存储成功
        if (external && insertResult) {
            val uriPath = UriUtils.getFilePathByUri(outputUri)
            if (StringUtils.isNotEmpty(uriPath)) {
                result.setFile(FileUtils.getFile(uriPath))
            }
        }
        listener?.onResult(result, params, source)
    }
}