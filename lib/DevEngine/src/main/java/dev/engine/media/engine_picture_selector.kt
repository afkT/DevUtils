package dev.engine.media

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import androidx.fragment.app.Fragment
import com.luck.picture.lib.PictureSelectionModel
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.tools.PictureFileUtils
import dev.engine.media.luck_lib_engine.LuckGlideEngineImpl
import dev.utils.LogPrintUtils
import dev.utils.app.UriUtils

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
class PictureSelectorEngineImpl : IMediaEngine<MediaConfig, LocalMediaData> {

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
        val pictureSelectionModel = getPictureSelectionModel(
            getPictureSelector(activity, null), config, true
        )
        return forResult(pictureSelectionModel)
    }

    override fun openCamera(fragment: Fragment?): Boolean {
        return openCamera(fragment, PIC_CONFIG)
    }

    override fun openCamera(
        fragment: Fragment?,
        config: MediaConfig?
    ): Boolean {
        val pictureSelectionModel = getPictureSelectionModel(
            getPictureSelector(null, fragment), config, true
        )
        return forResult(pictureSelectionModel)
    }

    // =

    override fun openGallery(activity: Activity?): Boolean {
        return openGallery(activity, PIC_CONFIG)
    }

    override fun openGallery(
        activity: Activity?,
        config: MediaConfig?
    ): Boolean {
        val pictureSelectionModel = getPictureSelectionModel(
            getPictureSelector(activity, null), config, false
        )
        return forResult(pictureSelectionModel)
    }

    override fun openGallery(fragment: Fragment?): Boolean {
        return openGallery(fragment, PIC_CONFIG)
    }

    override fun openGallery(
        fragment: Fragment?,
        config: MediaConfig?
    ): Boolean {
        val pictureSelectionModel = getPictureSelectionModel(
            getPictureSelector(null, fragment), config, false
        )
        return forResult(pictureSelectionModel)
    }

    // ==========
    // = 其他方法 =
    // ==========

    override fun deleteCacheDirFile(
        context: Context?,
        type: Int
    ) {
        try {
            PictureFileUtils.deleteCacheDirFile(context, type)
        } catch (e: Exception) {
            LogPrintUtils.eTag(TAG, e, "deleteCacheDirFile")
        }
    }

    override fun deleteAllCacheDirFile(context: Context?) {
        try {
            PictureFileUtils.deleteAllCacheDirFile(context)
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

    override fun getSelectors(intent: Intent?): MutableList<LocalMediaData> {
        val lists: MutableList<LocalMediaData> = ArrayList()
        val result = PictureSelector.obtainMultipleResult(intent)
        result.forEach {
            it?.let { localMedia ->
                lists.add(LocalMediaData(localMedia))
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
            media.getLocalMediaPath(original)?.apply {
                UriUtils.getUriForPath(this)?.let { uri ->
                    lists.add(uri)
                }
            }
        }
        return lists
    }

    override fun getSingleSelector(intent: Intent?): LocalMediaData? {
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

    /**
     * 获取图片选择器对象
     * @param activity [Activity]
     * @param fragment [Fragment]
     * @return [PictureSelector]
     */
    private fun getPictureSelector(
        activity: Activity?,
        fragment: Fragment?
    ): PictureSelector? {
        if (activity != null) {
            return PictureSelector.create(activity)
        } else if (fragment != null) {
            return PictureSelector.create(fragment)
        }
        return null
    }

    /**
     * 是否跳转成功
     * @param pictureSelectionModel 图片选择配置模型
     * @return `true` success, `false` fail
     */
    private fun forResult(pictureSelectionModel: PictureSelectionModel?): Boolean {
        pictureSelectionModel?.let {
            it.forResult(PIC_REQUEST_CODE)
            return true
        }
        return false
    }

    /**
     * 获取图片选择配置模型
     * @param pictureSelector [PictureSelector]
     * @param config          [MediaConfig]
     * @param isCamera        是否拍照
     * @return [PictureSelectionModel]
     * 结果回调 onActivityResult requestCode
     * pictureSelectionModel.forResult(requestCode)
     */
    private fun getPictureSelectionModel(
        pictureSelector: PictureSelector?,
        config: MediaConfig?,
        isCamera: Boolean
    ): PictureSelectionModel? {
        if (pictureSelector != null && config != null) {
            // 图片选择配置模型
            val pictureSelectionModel = if (isCamera) {
                pictureSelector.openCamera(config.getMimeType())
            } else {
                pictureSelector.openGallery(config.getMimeType())
            }
            // 是否裁减
            val isCrop = config.isCrop()
            // 是否圆形裁减
            val isCircleCrop = config.isCircleCrop()
            // 多选 or 单选 MediaConfig.MULTIPLE or MediaConfig.SINGLE
            pictureSelectionModel.selectionMode(config.getSelectionMode())
                .imageEngine(LuckGlideEngineImpl.instance)
                .isPreviewImage(true) // 是否可预览图片 true or false
                .isPreviewVideo(true) // 是否可以预览视频 true or false
                .isEnablePreviewAudio(true) // 是否可播放音频 true or false
                .isZoomAnim(true) // 图片列表点击 缩放效果 默认 true
                .isPreviewEggs(true) // 预览图片时是否增强左右滑动图片体验 ( 图片滑动一半即可看到上一张是否选中 ) true or false
                .imageSpanCount(config.getImageSpanCount()) // 每行显示个数 int
                .minSelectNum(config.getMinSelectNum()) // 最小选择数量 int
                .maxSelectNum(config.getMaxSelectNum()) // 最大图片选择数量 int
                .isCamera(config.isCamera()) // 是否显示拍照按钮 true or false
                .isGif(config.isGif()) // 是否显示 Gif true or false
                // = 压缩相关 =
                .isCompress(config.isCompress()) // 是否压缩 true or false
                .minimumCompressSize(config.getMinimumCompressSize()) // 小于 xxkb 的图片不压缩
                .withAspectRatio(
                    config.getWithAspectRatio()[0],
                    config.getWithAspectRatio()[1]
                ) // 裁剪比例 如 16:9 3:2 3:4 1:1 可自定义
                // = 裁减相关 =
                // 判断是否显示圆形裁减
                .circleDimmedLayer(isCircleCrop) // = 裁减配置 =
                .isEnableCrop(isCrop) // 是否裁剪 true or false
                .freeStyleCropEnabled(isCrop) // 裁剪框是否可拖拽 true or false
                .showCropFrame(!isCircleCrop && isCrop) // 是否显示裁剪矩形边框 圆形裁剪时建议设为 false
                .showCropGrid(!isCircleCrop && isCrop) // 是否显示裁剪矩形网格 圆形裁剪时建议设为 false
                .rotateEnabled(isCrop) // 裁剪是否可旋转图片 true or false
                .scaleEnabled(isCrop) // 裁剪是否可放大缩小图片 true or false

            // 设置拍照存储地址
            if (!TextUtils.isEmpty(config.getCameraSavePath())) {
                pictureSelectionModel.setOutputCameraPath(config.getCameraSavePath())
            }
            // 设置压缩图片存储地址
            if (!TextUtils.isEmpty(config.getCompressSavePath())) {
                pictureSelectionModel.compressSavePath(config.getCompressSavePath())
            }
            // 判断是否存在选中资源
            config.getLocalMedia()?.let {
                if (it.isNotEmpty()) {
                    pictureSelectionModel.selectionData(convertList(it))
                }
            }
            return pictureSelectionModel
        }
        return null
    }

    /**
     * 转换 List
     * @param lists [LocalMediaData] list
     * @return [LocalMedia] list
     */
    private fun convertList(lists: List<LocalMediaData?>?): List<LocalMedia?> {
        val medias: MutableList<LocalMedia?> = ArrayList()
        lists?.forEach {
            it?.getLocalMedia()?.let { media ->
                medias.add(media)
            }
        }
        return medias
    }
}