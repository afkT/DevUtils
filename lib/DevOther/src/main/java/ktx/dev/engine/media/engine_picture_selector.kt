package ktx.dev.engine.media

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.PointF
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.target.ImageViewTarget
import com.luck.picture.lib.PictureSelectionModel
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.engine.ImageEngine
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.listener.OnImageCompleteCallback
import com.luck.picture.lib.tools.MediaUtils
import com.luck.picture.lib.tools.PictureFileUtils
import com.luck.picture.lib.widget.longimage.ImageSource
import com.luck.picture.lib.widget.longimage.ImageViewState
import com.luck.picture.lib.widget.longimage.SubsamplingScaleImageView
import dev.engine.media.IMediaEngine
import dev.other.R
import dev.utils.LogPrintUtils

/**
 * detail: Glide 加载引擎
 * @author: luck
 * @date: 2019-11-13 17:02
 * @describe: Glide 加载引擎
 */
class GlideEngine private constructor() : ImageEngine {

    /**
     * 加载图片
     */
    override fun loadImage(
        context: Context,
        url: String,
        imageView: ImageView
    ) {
        Glide.with(context)
            .load(url)
            .into(imageView)
    }

    /**
     * 加载网络图片适配长图方案
     * 注意: 此方法只有加载网络图片才会回调
     */
    override fun loadImage(
        context: Context,
        url: String,
        imageView: ImageView,
        longImageView: SubsamplingScaleImageView?,
        callback: OnImageCompleteCallback?
    ) {
        Glide.with(context)
            .asBitmap()
            .load(url)
            .into(object : ImageViewTarget<Bitmap?>(imageView) {
                override fun onLoadStarted(placeholder: Drawable?) {
                    super.onLoadStarted(placeholder)
                    callback?.onShowLoading()
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    super.onLoadFailed(errorDrawable)
                    callback?.onHideLoading()
                }

                override fun setResource(resource: Bitmap?) {
                    callback?.onHideLoading()
                    resource?.let {
                        val eqLongImage = MediaUtils.isLongImg(
                            it.width, it.height
                        )
                        longImageView?.visibility = if (eqLongImage) View.VISIBLE else View.GONE
                        imageView.visibility = if (eqLongImage) View.GONE else View.VISIBLE
                        if (eqLongImage) {
                            longImageView?.apply {
                                // 加载长图
                                isQuickScaleEnabled = true
                                isZoomEnabled = true
                                isPanEnabled = true
                                setDoubleTapZoomDuration(100)
                                setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_CROP)
                                setDoubleTapZoomDpi(SubsamplingScaleImageView.ZOOM_FOCUS_CENTER)
                                setImage(
                                    ImageSource.bitmap(it),
                                    ImageViewState(0F, PointF(0F, 0F), 0)
                                )
                            }
                        } else {
                            // 普通图片
                            imageView.setImageBitmap(it)
                        }
                    }
                }
            })
    }

    /**
     * 加载网络图片适配长图方案
     * 注意: 此方法只有加载网络图片才会回调
     * @已废弃
     */
    override fun loadImage(
        context: Context,
        url: String,
        imageView: ImageView,
        longImageView: SubsamplingScaleImageView?
    ) {
        Glide.with(context)
            .asBitmap()
            .load(url)
            .into(object : ImageViewTarget<Bitmap?>(imageView) {
                override fun setResource(resource: Bitmap?) {
                    resource?.let {
                        val eqLongImage = MediaUtils.isLongImg(
                            it.width, it.height
                        )
                        longImageView?.visibility = if (eqLongImage) View.VISIBLE else View.GONE
                        imageView.visibility = if (eqLongImage) View.GONE else View.VISIBLE
                        if (eqLongImage) {
                            longImageView?.apply {
                                // 加载长图
                                isQuickScaleEnabled = true
                                isZoomEnabled = true
                                isPanEnabled = true
                                setDoubleTapZoomDuration(100)
                                setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_CROP)
                                setDoubleTapZoomDpi(SubsamplingScaleImageView.ZOOM_FOCUS_CENTER)
                                setImage(
                                    ImageSource.bitmap(it),
                                    ImageViewState(0F, PointF(0F, 0F), 0)
                                )
                            }
                        } else {
                            // 普通图片
                            imageView.setImageBitmap(it)
                        }
                    }
                }
            })
    }

    /**
     * 加载相册目录
     */
    override fun loadFolderImage(
        context: Context,
        url: String,
        imageView: ImageView
    ) {
        Glide.with(context)
            .asBitmap()
            .load(url)
            .override(180, 180)
            .centerCrop()
            .sizeMultiplier(0.5f)
            .apply(RequestOptions().placeholder(R.drawable.picture_image_placeholder))
            .into(object : BitmapImageViewTarget(imageView) {
                override fun setResource(resource: Bitmap?) {
                    val circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.resources, resource)
                    circularBitmapDrawable.cornerRadius = 8f
                    imageView.setImageDrawable(circularBitmapDrawable)
                }
            })
    }

    /**
     * 加载 gif
     */
    override fun loadAsGifImage(
        context: Context,
        url: String,
        imageView: ImageView
    ) {
        Glide.with(context)
            .asGif()
            .load(url)
            .into(imageView)
    }

    /**
     * 加载图片列表图片
     */
    override fun loadGridImage(
        context: Context,
        url: String,
        imageView: ImageView
    ) {
        Glide.with(context)
            .load(url)
            .override(200, 200)
            .centerCrop()
            .apply(RequestOptions().placeholder(R.drawable.picture_image_placeholder))
            .into(imageView)
    }

    companion object {

        val instance: GlideEngine by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            GlideEngine()
        }
    }
}

// ===================
// = PictureSelector =
// ===================

/**
 * detail: PictureSelector Media Selector Engine 实现
 * @author Ttt
 */
class PictureSelectorEngineImpl : IMediaEngine<MediaConfig, LocalMediaData> {

    // 日志 TAG
    private val TAG = PictureSelectorEngineImpl::class.java.simpleName

    // 全局请求跳转回传 code
    private val PIC_REQUEST_CODE = 159857

    // 全局配置信息
    private val PIC_CONFIG = MediaConfig()

    // 拍照保存地址
    private var CAMERA_SAVE_PATH: String? = null

    // 压缩图片保存地址
    private var COMPRESS_SAVE_PATH: String? = null

    // 图片大于多少才进行压缩 (kb)
    private var MINIMUM_COMPRESS_SIZE = 0

    // ===============
    // = 对外公开方法 =
    // ===============

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

    // ===========
    // = 配置方法 =
    // ===========

    override fun getConfig(): MediaConfig {
        return PIC_CONFIG
    }

    override fun setConfig(config: MediaConfig?) {
        PIC_CONFIG.set(config)
    }

    override fun getCameraSavePath(): String? {
        return CAMERA_SAVE_PATH
    }

    override fun getCompressSavePath(): String? {
        return COMPRESS_SAVE_PATH
    }

    override fun setSavePath(
        cameraSavePath: String?,
        compressSavePath: String?
    ) {
        CAMERA_SAVE_PATH = cameraSavePath
        COMPRESS_SAVE_PATH = compressSavePath
        // 设置配置
        PIC_CONFIG.setCameraSavePath(cameraSavePath)
            .setCompressSavePath(compressSavePath)
    }

    override fun getMinimumCompressSize(): Int {
        return MINIMUM_COMPRESS_SIZE
    }

    override fun setMinimumCompressSize(minimumCompressSize: Int) {
        MINIMUM_COMPRESS_SIZE = minimumCompressSize
        // 设置配置
        PIC_CONFIG.setMinimumCompressSize(minimumCompressSize)
    }

    // ===========
    // = 其他方法 =
    // ===========

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

    override fun getSelectors(data: Intent?): MutableList<LocalMediaData> {
        val lists: MutableList<LocalMediaData> = ArrayList()
        val result = PictureSelector.obtainMultipleResult(data)
        result.forEach {
            it?.let { localMedia ->
                lists.add(LocalMediaData(localMedia))
            }
        }
        return lists
    }

    override fun getSelectorPaths(
        data: Intent?,
        original: Boolean
    ): MutableList<String> {
        val lists: MutableList<String> = ArrayList()
        val result = getSelectors(data)
        result.forEach { media ->
            media.getLocalMediaPath(original)?.apply {
                lists.add(this)
            }
        }
        return lists
    }

    override fun getSingleSelector(data: Intent?): LocalMediaData? {
        val lists = getSelectors(data)
        return if (lists.size > 0) lists[0] else null
    }

    override fun getSingleSelectorPath(
        data: Intent?,
        original: Boolean
    ): String? {
        val lists = getSelectorPaths(data, original)
        return if (lists.size > 0) lists[0] else null
    }

    // ===========
    // = 内部处理 =
    // ===========

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
                .imageEngine(GlideEngine.instance)
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

            // 设置拍照保存地址
            if (!TextUtils.isEmpty(config.getCameraSavePath())) {
                pictureSelectionModel.setOutputCameraPath(config.getCameraSavePath())
            }
            // 设置压缩图片保存地址
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
            it?.localMedia?.let { media ->
                medias.add(media)
            }
        }
        return medias
    }
}