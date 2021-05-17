package ktx.dev.other

import ktx.dev.engine.media.LocalMediaData

/**
 * detail: Android 平台下的图片选择器
 * @author Ttt
 * 功能配置文档
 * https://github.com/LuckSiege/PictureSelector
 * 所需权限
 * <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
 * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
 * <uses-permission android:name="android.permission.CAMERA"/>
 */
object PictureSelectorUtils {

    // 日志 TAG
    private val TAG = PictureSelectorUtils::class.java.simpleName

    // 全局请求跳转回传 code
    private val PIC_REQUEST_CODE = 159857

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
        private var localMedia: List<LocalMediaData>? = null

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
        fun getLocalMedia(): List<LocalMediaData>? {
            return localMedia
        }

        /**
         * 设置已选择的本地资源
         * @param localMedia [<]
         * @return [MediaConfig]
         */
        fun setLocalMedia(localMedia: List<LocalMediaData>?): MediaConfig {
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