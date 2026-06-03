package dev.engine.core.media

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment
import com.luck.picture.lib.basic.*
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.manager.PictureCacheManager
import dev.engine.media.IMediaEngine
import dev.utils.LogPrintUtils
import dev.utils.app.UriUtils
import dev.utils.common.ConvertUtils

// ===================
// = PictureSelector =
// ===================

/**
 * detail: PictureSelector Media Selector Engine 实现
 * @author Ttt
 * 功能配置文档
 * @see https://github.com/LuckSiege/PictureSelector
 * 尽量不使用 isCompressed 压缩, 通过获取选中的路径后自行进行压缩
 * 防止需要适配 Android 11 ( R ) 进行转存文件需判断文件路径
 */
open class PictureSelectorEngineImpl : IMediaEngine<MediaConfig, MediaData> {

    // 日志 TAG
    @JvmField
    protected val TAG = javaClass.simpleName

    // 全局请求跳转回传 code
    @JvmField
    protected val PIC_REQUEST_CODE = 159857

    // 全局配置信息
    @JvmField
    protected val PIC_CONFIG = MediaConfig()

    // ==========
    // = 配置方法 =
    // ==========

    /**
     * 获取全局配置
     * @return 全局配置信息
     */
    override fun getConfig(): MediaConfig {
        return PIC_CONFIG
    }

    /**
     * 设置全局配置
     * @param config 新的配置信息
     */
    override fun setConfig(config: MediaConfig?) {
        PIC_CONFIG.set(config)
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 打开相册拍照
     * @param activity Activity
     * @return `true` success, `false` fail
     */
    override fun openCamera(activity: Activity?): Boolean {
        return openCamera(activity, PIC_CONFIG)
    }

    /**
     * 打开相册拍照
     * @param activity Activity
     * @param config 配置信息
     * @return `true` success, `false` fail
     */
    override fun openCamera(
        activity: Activity?,
        config: MediaConfig?
    ): Boolean {
        return startCameraModel(config)
    }

    /**
     * 打开相册拍照
     * @param fragment Fragment
     * @return `true` success, `false` fail
     */
    override fun openCamera(fragment: Fragment?): Boolean {
        return openCamera(fragment, PIC_CONFIG)
    }

    /**
     * 打开相册拍照
     * @param fragment Fragment
     * @param config 配置信息
     * @return `true` success, `false` fail
     */
    override fun openCamera(
        fragment: Fragment?,
        config: MediaConfig?
    ): Boolean {
        return startCameraModel(config)
    }

    // =

    /**
     * 打开相册选择
     * @param activity Activity
     * @return `true` success, `false` fail
     */
    override fun openGallery(activity: Activity?): Boolean {
        return openGallery(activity, PIC_CONFIG)
    }

    /**
     * 打开相册选择
     * @param activity Activity
     * @param config 配置信息
     * @return `true` success, `false` fail
     */
    override fun openGallery(
        activity: Activity?,
        config: MediaConfig?
    ): Boolean {
        return startGalleryModel(config)
    }

    /**
     * 打开相册选择
     * @param fragment Fragment
     * @return `true` success, `false` fail
     */
    override fun openGallery(fragment: Fragment?): Boolean {
        return openGallery(fragment, PIC_CONFIG)
    }

    /**
     * 打开相册选择
     * @param fragment Fragment
     * @param config 配置信息
     * @return `true` success, `false` fail
     */
    override fun openGallery(
        fragment: Fragment?,
        config: MediaConfig?
    ): Boolean {
        return startGalleryModel(config)
    }

    // =

    /**
     * 打开相册预览
     * @param activity Activity
     * @return `true` success, `false` fail
     */
    override fun openPreview(activity: Activity?): Boolean {
        return openPreview(activity, PIC_CONFIG)
    }

    /**
     * 打开相册预览
     * @param activity Activity
     * @param config 配置信息
     * @return `true` success, `false` fail
     */
    override fun openPreview(
        activity: Activity?,
        config: MediaConfig?
    ): Boolean {
        return startPreviewModel(config)
    }

    /**
     * 打开相册预览
     * @param fragment Fragment
     * @return `true` success, `false` fail
     */
    override fun openPreview(fragment: Fragment?): Boolean {
        return openPreview(fragment, PIC_CONFIG)
    }

    /**
     * 打开相册预览
     * @param fragment Fragment
     * @param config 配置信息
     * @return `true` success, `false` fail
     */
    override fun openPreview(
        fragment: Fragment?,
        config: MediaConfig?
    ): Boolean {
        return startPreviewModel(config)
    }

    // ==========
    // = 其他方法 =
    // ==========

    /**
     * 删除缓存文件
     * @param context Context
     * @param type 类型 ( 图片、视频 )
     */
    override fun deleteCacheDirFile(
        context: Context?,
        type: Int
    ) {
        try {
            PictureCacheManager.deleteCacheDirFile(context, type)
        } catch (e: Exception) {
            LogPrintUtils.eTag(TAG, e, "deleteCacheDirFile")
        }
    }

    /**
     * 删除全部缓存文件
     * @param context Context
     */
    override fun deleteAllCacheDirFile(context: Context?) {
        try {
            PictureCacheManager.deleteAllCacheDirFile(context)
        } catch (e: Exception) {
            LogPrintUtils.eTag(TAG, e, "deleteAllCacheDirFile")
        }
    }

    /**
     * 是否图片选择 ( onActivityResult )
     * @param requestCode 请求 code
     * @param resultCode resultCode
     * @return `true` success, `false` fail
     */
    override fun isMediaSelectorResult(
        requestCode: Int,
        resultCode: Int
    ): Boolean {
        return requestCode == PIC_REQUEST_CODE && resultCode == Activity.RESULT_OK
    }

    // =

    /**
     * 获取 Media Selector Data List
     * @param intent onActivityResult Intent data
     * @return Media Selector Data List
     */
    override fun getSelectors(intent: Intent?): MutableList<MediaData> {
        val lists = mutableListOf<MediaData>()
        val result = PictureSelector.obtainSelectorList(intent)
        result.forEach {
            it?.let { libData ->
                lists.add(createMediaData(libData))
            }
        }
        return lists
    }

    /**
     * 获取 Media Selector Uri List
     * @param intent onActivityResult Intent data
     * @param original 是否使用原图
     * @return Media Selector Uri List
     */
    override fun getSelectorUris(
        intent: Intent?,
        original: Boolean
    ): MutableList<Uri> {
        val lists = mutableListOf<Uri>()
        val result = getSelectors(intent)
        result.forEach { media ->
            val uri = if (original) {
                media.getOriginalUri()
            } else {
                val availableUri = media.getAvailableUri()
                if (UriUtils.isUriExists(availableUri)) {
                    availableUri
                } else {
                    media.getOriginalUri()
                }
            }
            uri?.let { lists.add(it) }
        }
        return lists
    }

    /**
     * 获取 Single Media Selector Data
     * @param intent onActivityResult Intent data
     * @return Single Media Selector Data
     */
    override fun getSingleSelector(intent: Intent?): MediaData? {
        val lists = getSelectors(intent)
        return if (lists.size > 0) lists[0] else null
    }

    /**
     * 获取 Single Media Selector Uri
     * @param intent onActivityResult Intent data
     * @param original 是否使用原图
     * @return Single Media Selector Uri
     */
    override fun getSingleSelectorUri(
        intent: Intent?,
        original: Boolean
    ): Uri? {
        val lists = getSelectorUris(intent, original)
        return if (lists.size > 0) lists[0] else null
    }

    // ==========
    // = 内部方法 =
    // ==========

    // ==========
    // = 转换对象 =
    // ==========

    /**
     * 转换 List
     * @param lists [MediaData] list
     * @return [LocalMedia] list
     */
    protected open fun convertList(lists: List<MediaData>?): ArrayList<LocalMedia> {
        val medias = ArrayList<LocalMedia>()
        if (lists != null) {
            for (media in lists) {
                val libData = media.getLibOriginalData()
                if (libData is LocalMedia) {
                    medias.add(libData)
                } else {
                    media.getOriginalUri()?.let { uri ->
                        val localMedia = LocalMedia()
                        localMedia.path = uri.toString()
                        localMedia.originalPath = uri.toString()
                        medias.add(localMedia)
                    }
                }
            }
        }
        return medias
    }

    /**
     * 创建 MediaData 并填充数据
     * @param libData 第三方库选择数据实体类
     * @return MediaData
     */
    protected open fun createMediaData(libData: LocalMedia): MediaData {
        val media = MediaData()
        media.setLibOriginalData(libData)

        // ============
        // = 初始化数据 =
        // ============

        // 资源路径 Uri
        media.setOriginalUri(UriUtils.ofUri(libData.originalPath))
            .setSandboxUri(UriUtils.ofUri(libData.sandboxPath))
            .setCompressUri(UriUtils.ofUri(libData.compressPath))
            .setThumbnailUri(UriUtils.ofUri(libData.videoThumbnailPath))
            .setWatermarkUri(UriUtils.ofUri(libData.watermarkPath))
            .setCropUri(UriUtils.ofUri(libData.cutPath))

        // 资源信息
        media.setMimeType(libData.mimeType)
            .setDuration(libData.duration)
            .setWidth(libData.width)
            .setHeight(libData.height)

        // 资源裁剪信息
        media.setCropImageWidth(libData.cropImageWidth)
            .setCropImageHeight(libData.cropImageHeight)
            .setCropOffsetX(libData.cropOffsetX)
            .setCropOffsetY(libData.cropOffsetY)
            .setCropAspectRatio(libData.cropResultAspectRatio)

        // 状态信息
        media.setCropState(libData.isCut)
            .setCompressState(libData.isCompressed)

        // =================
        // = 资源路径最后校验 =
        // =================

        // 判断是否存在有效 Uri
        val availableUri = media.getAvailableUri()
        if (!UriUtils.isUriExists(availableUri)) {
            // 上述已设置路径都不存在则设置原图路径
            media.setOriginalUri(UriUtils.ofUri(libData.path))
        }
        return media
    }

    // ==========================
    // = PictureSelection Model =
    // ==========================

    /**
     * 跳转 Camera PictureSelection Model
     * @param config [MediaConfig]
     * @return `true` success, `false` fail
     */
    protected open fun startCameraModel(config: MediaConfig?): Boolean {
        if (config != null) {
            try {
                val libConfig = config.getLibCustomConfig()
                if (libConfig is PictureSelectionCameraModel) {
                    libConfig.forResultActivity(
                        PIC_REQUEST_CODE
                    )
                    return true
                }
                throw Exception("MediaConfig libCustomConfig is null")
            } catch (e: Exception) {
                LogPrintUtils.eTag(TAG, e, "startCameraModel")
            }
        }
        return false
    }

    /**
     * 跳转 Gallery PictureSelection Model
     * @param config [MediaConfig]
     * @return `true` success, `false` fail
     */
    protected open fun startGalleryModel(config: MediaConfig?): Boolean {
        if (config != null) {
            try {
                val libConfig = config.getLibCustomConfig()
                if (libConfig is PictureSelectionModel) {
                    libConfig.forResult(
                        PIC_REQUEST_CODE
                    )
                    return true
                }
                if (libConfig is PictureSelectionSystemModel) {
                    libConfig.forSystemResultActivity(
                        PIC_REQUEST_CODE
                    )
                    return true
                }
                throw Exception("MediaConfig libCustomConfig is null")
            } catch (e: Exception) {
                LogPrintUtils.eTag(TAG, e, "startGalleryModel")
            }
        }
        return false
    }

    /**
     * 跳转 Preview PictureSelection Model
     * @param config [MediaConfig]
     * @return `true` success, `false` fail
     */
    protected open fun startPreviewModel(config: MediaConfig?): Boolean {
        if (config != null) {
            try {
                val libConfig = config.getLibCustomConfig()
                if (libConfig is PictureSelectionPreviewModel) {
                    libConfig.startActivityPreview(
                        ConvertUtils.toInt(config.getCustomData(), 0),
                        false, convertList(config.getMediaDatas())
                    )
                    return true
                }
                throw Exception("MediaConfig libCustomConfig is null")
            } catch (e: Exception) {
                LogPrintUtils.eTag(TAG, e, "startPreviewModel")
            }
        }
        return false
    }
}