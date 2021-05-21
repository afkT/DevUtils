package ktx.dev.other

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import com.luck.picture.lib.PictureSelectionModel
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.tools.PictureFileUtils
import dev.engine.media.GlideEngine
import dev.utils.LogPrintUtils
import java.util.*

/**
 * detail: Android 平台下的图片选择器
 * @author Ttt
 * 功能配置文档
 * @see https://github.com/LuckSiege/PictureSelector
 * 所需权限
 * <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
 * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
 * <uses-permission android:name="android.permission.CAMERA"/>
 */
object PictureSelectorUtils {

    // 日志 TAG
    private val TAG = PictureSelectorUtils::class.java.simpleName

    // 全局请求跳转回传 code
    private const val PIC_REQUEST_CODE = 159857

    // 全局配置信息
    private val PIC_CONFIG = MediaConfig()

    /**
     * 获取全局相册配置
     * @return [MediaConfig]
     */
    fun getConfig(): MediaConfig {
        return PIC_CONFIG
    }

    /**
     * 设置全局相册配置
     * @param config 新的相册配置信息
     */
    fun setConfig(config: MediaConfig?) {
        PIC_CONFIG.set(config)
    }

    /**
     * 获取拍照保存地址
     * @return 拍照保存地址
     */
    fun getCameraSavePath(): String? {
        return PIC_CONFIG.getCameraSavePath()
    }

    /**
     * 获取压缩图片保存地址
     * @return 压缩图片保存地址
     */
    fun getCompressSavePath(): String? {
        return PIC_CONFIG.getCompressSavePath()
    }

    /**
     * 设置保存地址
     * @param cameraSavePath   拍照保存地址
     * @param compressSavePath 压缩图片保存地址
     */
    fun setSavePath(
        cameraSavePath: String?,
        compressSavePath: String?
    ) {
        PIC_CONFIG.setCameraSavePath(cameraSavePath)
            .setCompressSavePath(compressSavePath)
    }

    /**
     * 获取图片大于多少才进行压缩 (kb)
     * @return 最小压缩大小
     */
    fun getMinimumCompressSize(): Int {
        return PIC_CONFIG.getMinimumCompressSize()
    }

    /**
     * 设置图片大于多少才进行压缩 (kb)
     * @param minimumCompressSize 最小压缩大小
     */
    fun setMinimumCompressSize(minimumCompressSize: Int) {
        PIC_CONFIG.setMinimumCompressSize(minimumCompressSize)
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 删除缓存文件
     * @param context {@link Context}
     * @param type    类型 ( 图片、视频 )
     * 包括裁剪和压缩后的缓存, 要在上传成功后调用, 注意: 需要系统 SDCard 权限
     * type [PictureMimeType.ofImage]、[PictureMimeType.ofVideo]
     */
    fun deleteCacheDirFile(
        context: Context?,
        type: Int
    ) {
        try {
            PictureFileUtils.deleteCacheDirFile(context, type)
        } catch (e: Exception) {
            LogPrintUtils.eTag(TAG, e, "deleteCacheDirFile")
        }
    }

    /**
     * 删除全部缓存文件
     * @param context {@link Context}
     */
    fun deleteAllCacheDirFile(context: Context?) {
        try {
            PictureFileUtils.deleteAllCacheDirFile(context)
        } catch (e: Exception) {
            LogPrintUtils.eTag(TAG, e, "deleteAllCacheDirFile")
        }
    }

    /**
     * 是否图片选择 ( onActivityResult )
     * @param requestCode 请求 code
     * @param resultCode  resultCode
     * @return {@code true} success, {@code false} fail
     */
    fun isMediaSelectorResult(
        requestCode: Int,
        resultCode: Int
    ): Boolean {
        return requestCode == PIC_REQUEST_CODE && resultCode == Activity.RESULT_OK
    }

    /**
     * 获取选中的资源集合
     * @param data [Intent]
     * @return List<LocalMedia>?
     * 图片、视频、音频选择结果回调
     * List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data)
     * LocalMedia 里面返回三种 path
     * 1.media.getPath() 为原图 path
     * 2.media.getCutPath() 为裁剪后 path 需判断 media.isCut() 是否为 true  注意: 音视频除外
     * 3.media.getCompressPath() 为压缩后 path 需判断 media.isCompressed() 是否为 true  注意: 音视频除外
     * 如果裁剪并压缩了, 以取压缩路径为准, 因为是先裁剪后压缩的
     */
    fun getLocalMedias(data: Intent?): List<LocalMedia?> {
        return PictureSelector.obtainMultipleResult(data)
    }

    /**
     * 获取单独选中的资源
     * @param data [Intent]
     * @return [LocalMedia]
     */
    fun getSingleMedia(data: Intent?): LocalMedia? {
        getLocalMedias(data).let {
            if (it.isNotEmpty()) return it[0]
        }
        return null
    }

    /**
     * 获取本地资源路径
     * @param data [Intent]
     * @return 本地资源路径
     */
    fun getLocalMediaPath(data: Intent?): String? {
        return getLocalMediaPath(getSingleMedia(data), false)
    }

    /**
     * 获取本地资源路径
     * @param localMedia [LocalMedia]
     * @return 本地资源路径
     */
    fun getLocalMediaPath(localMedia: LocalMedia?): String? {
        return getLocalMediaPath(localMedia, false)
    }

    /**
     * 获取本地资源路径
     * @param localMedia [LocalMedia]
     * @param original   是否使用原图地址
     * @return 本地资源路径
     */
    fun getLocalMediaPath(
        localMedia: LocalMedia?,
        original: Boolean
    ): String? {
        localMedia?.let {
            if (original) return it.path
            // 判断资源类型
            val mimeType = it.mimeType
            if (PictureMimeType.isHasImage(mimeType)) { // 图片
                if (it.isCompressed) { // 是否压缩图片
                    return it.compressPath
                } else if (it.isCut) { // 是否裁减图片
                    return it.cutPath
                } else { // 获取原图
                    return it.path
                }
            } else {
                return it.path
            }
        }
        return null
    }

    /**
     * 获取本地资源地址集合
     * @param data [Intent]
     * @return [List]
     */
    fun getLocalMediaPaths(data: Intent?): List<String?> {
        return getLocalMediaPaths(data, false)
    }

    /**
     * 获取本地资源地址集合
     * @param data     [Intent]
     * @param original 是否使用原图地址
     * @return [List]
     */
    fun getLocalMediaPaths(
        data: Intent?,
        original: Boolean
    ): List<String?> {
        val lists: MutableList<String?> = ArrayList()
        val result = getLocalMedias(data)
        for (localMedia in result) {
            val path = getLocalMediaPath(localMedia, original)
            lists.add(path)
        }
        return lists
    }

    // =

    /**
     * 获取图片选择配置模型
     * @param pictureSelector [PictureSelector]
     * @param config          [MediaConfig]
     * @param isCamera        是否拍照
     * @return [PictureSelectionModel]
     * 结果回调 onActivityResult requestCode
     * pictureSelectionModel.forResult(requestCode)
     */
    fun getPictureSelectionModel(
        pictureSelector: PictureSelector?,
        config: MediaConfig?,
        isCamera: Boolean
    ): PictureSelectionModel? {
        pictureSelector?.let { selector ->
            config?.let { config ->
                // 图片选择配置模型
                val pictureSelectionModel: PictureSelectionModel = if (isCamera) {
                    selector.openCamera(config.getMimeType())
                } else {
                    selector.openGallery(config.getMimeType())
                }

                // 是否裁减
                val isCrop = config.isCrop()
                // 是否圆形裁减
                val isCircleCrop = config.isCircleCrop()

                // 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                pictureSelectionModel.selectionMode(config.getSelectionMode())
                    .imageEngine(GlideEngine.createGlideEngine())
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
                config.getLocalMedia()?.let { list ->
                    if (list.isNotEmpty()) {
                        pictureSelectionModel.selectionData(list)
                    }
                }
                return pictureSelectionModel
            }
        }
        return null
    }

    // ==========
    // = 调用方法 =
    // ==========

    /**
     * 打开相册拍照
     * @param pictureSelector [PictureSelector]
     * @return `true` success, `false` fail
     */
    fun openCamera(pictureSelector: PictureSelector?): Boolean {
        return openCamera(pictureSelector, PIC_CONFIG)
    }

    /**
     * 打开相册拍照
     * @param pictureSelector [PictureSelector]
     * @param config          [MediaConfig]
     * @return `true` success, `false` fail
     */
    fun openCamera(
        pictureSelector: PictureSelector?,
        config: MediaConfig?
    ): Boolean {
        val pictureSelectionModel = getPictureSelectionModel(pictureSelector, config, true)
        pictureSelectionModel?.let {
            it.forResult(PIC_REQUEST_CODE)
            return true
        }
        return false
    }

    /**
     * 打开相册选择
     * @param pictureSelector [PictureSelector]
     * @return `true` success, `false` fail
     */
    fun openGallery(pictureSelector: PictureSelector?): Boolean {
        return openGallery(pictureSelector, PIC_CONFIG)
    }

    /**
     * 打开相册选择
     * @param pictureSelector [PictureSelector]
     * @param config          [MediaConfig]
     * @return `true` success, `false` fail
     */
    fun openGallery(
        pictureSelector: PictureSelector?,
        config: MediaConfig?
    ): Boolean {
        val pictureSelectionModel = getPictureSelectionModel(pictureSelector, config, false)
        pictureSelectionModel?.let {
            it.forResult(PIC_REQUEST_CODE)
            return true
        }
        return false
    }

    // ========
    // = 配置 =
    // ========

    /**
     * detail: Media Selector Config
     * @author Ttt
     */
    class MediaConfig {

        // 相册选择类型
        private var mimeType: Int = MimeType.ofImage()

        // 相册选择模式
        private var selectionMode: Int = MimeType.MULTIPLE

        // 是否显示拍照
        private var isCamera = true

        // 是否裁减
        private var isCrop = false

        // 是否圆形裁减 true = 圆形, false = 矩形
        private var isCircleCrop = false

        // 是否压缩
        private var isCompress = true

        // 图片大于多少才进行压缩 (kb)
        private var minimumCompressSize = 2048

        // 裁减比例
        private var withAspectRatio = intArrayOf(0, 0)

        // 是否显示 Gif
        private var isGif = false

        // 每行显示个数
        private var imageSpanCount = 4

        // 最小选择数量
        private var minSelectNum = 1

        // 最大选择数量
        private var maxSelectNum = 9

        // 已选择的本地资源
        private var localMedia: List<LocalMedia>? = null

        // 拍照保存地址
        private var cameraSavePath: String? = null

        // 压缩图片保存地址
        private var compressSavePath: String? = null

        /**
         * detail: 选择模式
         * @author Ttt
         */
        object MimeType {
            const val SINGLE = 1
            const val MULTIPLE = 2
            const val TYPE_ALL = 0
            const val TYPE_IMAGE = 1
            const val TYPE_VIDEO = 2
            const val TYPE_AUDIO = 3

            fun ofAll(): Int {
                return TYPE_ALL
            }

            fun ofImage(): Int {
                return TYPE_IMAGE
            }

            fun ofVideo(): Int {
                return TYPE_VIDEO
            }

            fun ofAudio(): Int {
                return TYPE_AUDIO
            }
        }

        // ===========
        // = get/set =
        // ===========

        /**
         * 获取相册选择类型
         * @return 相册选择类型
         */
        fun getMimeType(): Int {
            return mimeType
        }

        /**
         * 设置相册选择类型
         * @param mimeType 相册选择类型
         * @return [MediaConfig]
         * 全部 ofAll() = 0
         * 图片 ofImage() = 1
         * 视频 ofVideo() = 2
         * 音频 ofAudio() = 3
         */
        fun setMimeType(mimeType: Int): MediaConfig {
            // 超过最大、最小值都默认为全部类型
            if (mimeType > MimeType.ofAudio() || mimeType < MimeType.ofAll()) {
                this.mimeType = MimeType.ofAll()
            } else {
                this.mimeType = mimeType
            }
            return this
        }

        /**
         * 获取相册选择模式
         * @return 相册选择模式
         */
        fun getSelectionMode(): Int {
            return selectionMode
        }

        /**
         * 设置相册选择模式
         * @param selectionMode 相册选择模式
         * @return [MediaConfig]
         * 多选 [MimeType.MULTIPLE]
         * 单选 [MimeType.SINGLE]
         */
        fun setSelectionMode(selectionMode: Int): MediaConfig {
            if (selectionMode >= MimeType.MULTIPLE) {
                this.selectionMode = MimeType.MULTIPLE
            } else if (selectionMode <= MimeType.SINGLE) {
                this.selectionMode = MimeType.SINGLE
            }
            return this
        }

        /**
         * 是否显示拍照
         * @return `true` yes, `false` no
         */
        fun isCamera(): Boolean {
            return isCamera
        }

        /**
         * 设置是否显示拍照
         * @param camera `true` yes, `false` no
         * @return [MediaConfig]
         */
        fun setCamera(camera: Boolean): MediaConfig {
            isCamera = camera
            return this
        }

        /**
         * 是否裁减
         * @return `true` yes, `false` no
         */
        fun isCrop(): Boolean {
            return isCrop
        }

        /**
         * 设置是否裁减
         * @param crop `true` yes, `false` no
         * @return [MediaConfig]
         */
        fun setCrop(crop: Boolean): MediaConfig {
            isCrop = crop
            return this
        }

        /**
         * 是否圆形裁减
         * @return `true` yes, `false` no
         */
        fun isCircleCrop(): Boolean {
            return isCircleCrop
        }

        /**
         * 设置是否圆形裁减
         * @param circleCrop `true` yes, `false` no
         * @return [MediaConfig]
         */
        fun setCircleCrop(circleCrop: Boolean): MediaConfig {
            isCircleCrop = circleCrop
            return this
        }

        /**
         * 是否压缩
         * @return `true` yes, `false` no
         */
        fun isCompress(): Boolean {
            return isCompress
        }

        /**
         * 设置是否压缩
         * @param compress `true` yes, `false` no
         * @return [MediaConfig]
         */
        fun setCompress(compress: Boolean): MediaConfig {
            isCompress = compress
            return this
        }

        /**
         * 获取图片大于多少才进行压缩
         * @return 最小压缩大小
         */
        fun getMinimumCompressSize(): Int {
            return minimumCompressSize
        }

        /**
         * 设置图片大于多少才进行压缩
         * @param minimumCompressSize 最小压缩大小
         * @return [MediaConfig]
         */
        fun setMinimumCompressSize(minimumCompressSize: Int): MediaConfig {
            this.minimumCompressSize = minimumCompressSize
            return this
        }

        /**
         * 获取裁减比例
         * @return int[] 0 = 宽比例, 1 = 高比例
         */
        fun getWithAspectRatio(): IntArray {
            return withAspectRatio
        }

        /**
         * 设置裁减比例
         * @param x 宽比例
         * @param y 高比例
         * @return [MediaConfig]
         */
        fun setWithAspectRatio(
            x: Int,
            y: Int
        ): MediaConfig {
            withAspectRatio[0] = x
            withAspectRatio[1] = y
            return this
        }

        /**
         * 是否显示 Gif
         * @return `true` yes, `false` no
         */
        fun isGif(): Boolean {
            return isGif
        }

        /**
         * 设置是否显示 Gif
         * @param gif `true` yes, `false` no
         * @return [MediaConfig]
         */
        fun setGif(gif: Boolean): MediaConfig {
            isGif = gif
            return this
        }

        /**
         * 获取每行显示个数
         * @return 每行显示个数
         */
        fun getImageSpanCount(): Int {
            return imageSpanCount
        }

        /**
         * 设置每行显示个数
         * @param imageSpanCount 每行显示个数
         * @return [MediaConfig]
         */
        fun setImageSpanCount(imageSpanCount: Int): MediaConfig {
            this.imageSpanCount = Math.max(imageSpanCount, 1)
            return this
        }

        /**
         * 获取最小选择数量
         * @return 最小选择数量
         */
        fun getMinSelectNum(): Int {
            return minSelectNum
        }

        /**
         * 设置最小选择数量
         * @param minSelectNum 最小选择数量
         * @return [MediaConfig]
         */
        fun setMinSelectNum(minSelectNum: Int): MediaConfig {
            this.minSelectNum = minSelectNum
            return this
        }

        /**
         * 获取最大选择数量
         * @return 最大选择数量
         */
        fun getMaxSelectNum(): Int {
            return maxSelectNum
        }

        /**
         * 设置最大选择数量
         * @param maxSelectNum 最大选择数量
         * @return [MediaConfig]
         */
        fun setMaxSelectNum(maxSelectNum: Int): MediaConfig {
            this.maxSelectNum = maxSelectNum
            return this
        }

        /**
         * 获取已选择的本地资源
         * @return 已选择的本地资源 [<]
         */
        fun getLocalMedia(): List<LocalMedia>? {
            return localMedia
        }

        /**
         * 设置已选择的本地资源
         * @param localMedia [<]
         * @return [MediaConfig]
         */
        fun setLocalMedia(localMedia: List<LocalMedia>?): MediaConfig {
            this.localMedia = localMedia
            return this
        }

        /**
         * 获取拍照保存地址
         * @return 拍照保存地址
         */
        fun getCameraSavePath(): String? {
            return cameraSavePath
        }

        /**
         * 设置拍照保存地址
         * @param cameraSavePath 拍照保存地址
         * @return [MediaConfig]
         */
        fun setCameraSavePath(cameraSavePath: String?): MediaConfig {
            this.cameraSavePath = cameraSavePath
            return this
        }

        /**
         * 获取压缩图片保存地址
         * @return 压缩图片保存地址
         */
        fun getCompressSavePath(): String? {
            return compressSavePath
        }

        /**
         * 设置压缩图片保存地址
         * @param compressSavePath 压缩图片保存地址
         * @return [MediaConfig]
         */
        fun setCompressSavePath(compressSavePath: String?): MediaConfig {
            this.compressSavePath = compressSavePath
            return this
        }

        // =

        /**
         * 克隆新的配置信息
         * @return [MediaConfig]
         */
        fun clone(): MediaConfig {
            val config = MediaConfig()
            config.mimeType = mimeType
            config.selectionMode = selectionMode
            config.isCamera = isCamera
            config.isCrop = isCrop
            config.isCircleCrop = isCircleCrop
            config.isCompress = isCompress
            config.minimumCompressSize = minimumCompressSize
            config.withAspectRatio = withAspectRatio
            config.isGif = isGif
            config.imageSpanCount = imageSpanCount
            config.minSelectNum = minSelectNum
            config.maxSelectNum = maxSelectNum
            config.localMedia = localMedia
            config.cameraSavePath = cameraSavePath
            config.compressSavePath = compressSavePath
            return config
        }

        /**
         * 设置新的配置信息
         * @param config 新的配置信息
         * @return [MediaConfig]
         */
        fun set(config: MediaConfig?): MediaConfig {
            config?.let {
                mimeType = it.mimeType
                selectionMode = it.selectionMode
                isCamera = it.isCamera
                isCrop = it.isCrop
                isCircleCrop = it.isCircleCrop
                isCompress = it.isCompress
                minimumCompressSize = it.minimumCompressSize
                withAspectRatio = it.withAspectRatio
                isGif = it.isGif
                imageSpanCount = it.imageSpanCount
                minSelectNum = it.minSelectNum
                maxSelectNum = it.maxSelectNum
                localMedia = it.localMedia
                cameraSavePath = it.cameraSavePath
                compressSavePath = it.compressSavePath
            }
            return this
        }
    }
}