package dev.engine.media

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment
import com.luck.picture.lib.basic.*
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.manager.PictureCacheManager
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
class PictureSelectorEngineImpl : IMediaEngine<MediaConfig, MediaData> {

    // 日志 TAG
    private val TAG = PictureSelectorEngineImpl::class.java.simpleName

    // 全局请求跳转回传 code
    private val PIC_REQUEST_CODE = 159857

    // 全局配置信息
    private val PIC_CONFIG = MediaConfig()

    // ==========
    // = 配置方法 =
    // ==========

    override fun getConfig(): MediaConfig {
        return PIC_CONFIG
    }

    override fun setConfig(config: MediaConfig?) {
        PIC_CONFIG.set(config)
    }

    // =============
    // = 对外公开方法 =
    // =============

    override fun openCamera(activity: Activity?): Boolean {
        return openCamera(activity, PIC_CONFIG)
    }

    override fun openCamera(
        activity: Activity?,
        config: MediaConfig?
    ): Boolean {
        return startCameraModel(config)
    }

    override fun openCamera(fragment: Fragment?): Boolean {
        return openCamera(fragment, PIC_CONFIG)
    }

    override fun openCamera(
        fragment: Fragment?,
        config: MediaConfig?
    ): Boolean {
        return startCameraModel(config)
    }

    // =

    override fun openGallery(activity: Activity?): Boolean {
        return openGallery(activity, PIC_CONFIG)
    }

    override fun openGallery(
        activity: Activity?,
        config: MediaConfig?
    ): Boolean {
        return startGalleryModel(config)
    }

    override fun openGallery(fragment: Fragment?): Boolean {
        return openGallery(fragment, PIC_CONFIG)
    }

    override fun openGallery(
        fragment: Fragment?,
        config: MediaConfig?
    ): Boolean {
        return startGalleryModel(config)
    }

    // =

    override fun openPreview(activity: Activity?): Boolean {
        return openPreview(activity, PIC_CONFIG)
    }

    override fun openPreview(
        activity: Activity?,
        config: MediaConfig?
    ): Boolean {
        return startPreviewModel(config)
    }

    override fun openPreview(fragment: Fragment?): Boolean {
        return openPreview(fragment, PIC_CONFIG)
    }

    override fun openPreview(
        fragment: Fragment?,
        config: MediaConfig?
    ): Boolean {
        return startPreviewModel(config)
    }

    // ==========
    // = 其他方法 =
    // ==========

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

    override fun deleteAllCacheDirFile(context: Context?) {
        try {
            PictureCacheManager.deleteAllCacheDirFile(context)
        } catch (e: Exception) {
            LogPrintUtils.eTag(TAG, e, "deleteAllCacheDirFile")
        }
    }

    override fun isMediaSelectorResult(
        requestCode: Int,
        resultCode: Int
    ): Boolean {
        return requestCode == PIC_REQUEST_CODE && resultCode == Activity.RESULT_OK
    }

    // =

    override fun getSelectors(intent: Intent?): MutableList<MediaData> {
        val lists: MutableList<MediaData> = ArrayList()
        val result = PictureSelector.obtainSelectorList(intent)
        result.forEach {
            it?.let { libData ->
                lists.add(createMediaData(libData))
            }
        }
        return lists
    }

    override fun getSelectorUris(
        intent: Intent?,
        original: Boolean
    ): MutableList<Uri> {
        val lists: MutableList<Uri> = ArrayList()
        val result = getSelectors(intent)
        result.forEach { media ->
            val uri: Uri? = if (original) {
                media.getOriginalUri()
            } else {
                media.getAvailableUri()
            }
            uri?.let { lists.add(it) }
        }
        return lists
    }

    override fun getSingleSelector(intent: Intent?): MediaData? {
        val lists = getSelectors(intent)
        return if (lists.size > 0) lists[0] else null
    }

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
    private fun convertList(lists: List<MediaData>?): ArrayList<LocalMedia> {
        val medias = ArrayList<LocalMedia>()
        if (lists != null) {
            for (media in lists) {
                val libData: Any? = media.getLibOriginalData()
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
    private fun createMediaData(libData: LocalMedia): MediaData {
        val media = MediaData()
        media.setLibOriginalData(libData)

        // ============
        // = 初始化数据 =
        // ============

        // 资源路径 Uri
        media.setOriginalUri(UriUtils.getUriForPath(libData.originalPath))
            .setSandboxUri(UriUtils.getUriForPath(libData.sandboxPath))
            .setCompressUri(UriUtils.getUriForPath(libData.compressPath))
            .setThumbnailUri(UriUtils.getUriForPath(libData.videoThumbnailPath))
            .setWatermarkUri(UriUtils.getUriForPath(libData.watermarkPath))
            .setCropUri(UriUtils.getUriForPath(libData.cutPath))

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
    private fun startCameraModel(config: MediaConfig?): Boolean {
        if (config != null) {
            try {
                val libConfig: Any? = config.getLibCustomConfig()
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
    private fun startGalleryModel(config: MediaConfig?): Boolean {
        if (config != null) {
            try {
                val libConfig: Any? = config.getLibCustomConfig()
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
    private fun startPreviewModel(config: MediaConfig?): Boolean {
        if (config != null) {
            try {
                val libConfig: Any? = config.getLibCustomConfig()
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