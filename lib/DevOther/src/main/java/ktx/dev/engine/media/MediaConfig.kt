package ktx.dev.engine.media

import dev.engine.media.IMediaEngine

/**
 * detail: Media Selector Config
 * @author Ttt
 */
class MediaConfig : IMediaEngine.EngineConfig() {

    // 相册选择类型
    private var mMimeType: Int = MimeType.ofImage()

    // 相册选择模式
    private var mSelectionMode: Int = MimeType.MULTIPLE

    // 是否显示拍照
    private var mIsCamera = true

    // 是否裁减
    private var mIsCrop = false

    // 是否圆形裁减 true = 圆形, false = 矩形
    private var mIsCircleCrop = false

    // 是否压缩
    private var mIsCompress = false

    // 图片大于多少才进行压缩 (kb)
    private var mMinimumCompressSize = 2048

    // 裁减比例
    private var mWithAspectRatio = intArrayOf(0, 0)

    // 是否显示 Gif
    private var mIsGif = false

    // 每行显示个数
    private var mImageSpanCount = 4

    // 最小选择数量
    private var mMinSelectNum = 1

    // 最大选择数量
    private var mMaxSelectNum = 9

    // 已选择的本地资源
    private var mLocalMedia: List<LocalMediaData>? = null

    // 拍照存储地址
    private var mCameraSavePath: String? = null

    // 压缩图片存储地址
    private var mCompressSavePath: String? = null

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
        return mMimeType
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
            this.mMimeType = MimeType.ofAll()
        } else {
            this.mMimeType = mimeType
        }
        return this
    }

    /**
     * 获取相册选择模式
     * @return 相册选择模式
     */
    fun getSelectionMode(): Int {
        return mSelectionMode
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
            this.mSelectionMode = MimeType.MULTIPLE
        } else if (selectionMode <= MimeType.SINGLE) {
            this.mSelectionMode = MimeType.SINGLE
        }
        return this
    }

    /**
     * 是否显示拍照
     * @return `true` yes, `false` no
     */
    fun isCamera(): Boolean {
        return mIsCamera
    }

    /**
     * 设置是否显示拍照
     * @param camera `true` yes, `false` no
     * @return [MediaConfig]
     */
    fun setCamera(camera: Boolean): MediaConfig {
        mIsCamera = camera
        return this
    }

    /**
     * 是否裁减
     * @return `true` yes, `false` no
     */
    fun isCrop(): Boolean {
        return mIsCrop
    }

    /**
     * 设置是否裁减
     * @param crop `true` yes, `false` no
     * @return [MediaConfig]
     */
    fun setCrop(crop: Boolean): MediaConfig {
        mIsCrop = crop
        return this
    }

    /**
     * 是否圆形裁减
     * @return `true` yes, `false` no
     */
    fun isCircleCrop(): Boolean {
        return mIsCircleCrop
    }

    /**
     * 设置是否圆形裁减
     * @param circleCrop `true` yes, `false` no
     * @return [MediaConfig]
     */
    fun setCircleCrop(circleCrop: Boolean): MediaConfig {
        mIsCircleCrop = circleCrop
        return this
    }

    /**
     * 是否压缩
     * @return `true` yes, `false` no
     */
    fun isCompress(): Boolean {
        return mIsCompress
    }

    /**
     * 设置是否压缩
     * @param compress `true` yes, `false` no
     * @return [MediaConfig]
     */
    fun setCompress(compress: Boolean): MediaConfig {
        mIsCompress = compress
        return this
    }

    /**
     * 获取图片大于多少才进行压缩
     * @return 最小压缩大小
     */
    fun getMinimumCompressSize(): Int {
        return mMinimumCompressSize
    }

    /**
     * 设置图片大于多少才进行压缩
     * @param minimumCompressSize 最小压缩大小
     * @return [MediaConfig]
     */
    fun setMinimumCompressSize(minimumCompressSize: Int): MediaConfig {
        this.mMinimumCompressSize = minimumCompressSize
        return this
    }

    /**
     * 获取裁减比例
     * @return int[] 0 = 宽比例, 1 = 高比例
     */
    fun getWithAspectRatio(): IntArray {
        return mWithAspectRatio
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
        mWithAspectRatio[0] = x
        mWithAspectRatio[1] = y
        return this
    }

    /**
     * 是否显示 Gif
     * @return `true` yes, `false` no
     */
    fun isGif(): Boolean {
        return mIsGif
    }

    /**
     * 设置是否显示 Gif
     * @param gif `true` yes, `false` no
     * @return [MediaConfig]
     */
    fun setGif(gif: Boolean): MediaConfig {
        mIsGif = gif
        return this
    }

    /**
     * 获取每行显示个数
     * @return 每行显示个数
     */
    fun getImageSpanCount(): Int {
        return mImageSpanCount
    }

    /**
     * 设置每行显示个数
     * @param imageSpanCount 每行显示个数
     * @return [MediaConfig]
     */
    fun setImageSpanCount(imageSpanCount: Int): MediaConfig {
        this.mImageSpanCount = Math.max(imageSpanCount, 1)
        return this
    }

    /**
     * 获取最小选择数量
     * @return 最小选择数量
     */
    fun getMinSelectNum(): Int {
        return mMinSelectNum
    }

    /**
     * 设置最小选择数量
     * @param minSelectNum 最小选择数量
     * @return [MediaConfig]
     */
    fun setMinSelectNum(minSelectNum: Int): MediaConfig {
        this.mMinSelectNum = minSelectNum
        return this
    }

    /**
     * 获取最大选择数量
     * @return 最大选择数量
     */
    fun getMaxSelectNum(): Int {
        return mMaxSelectNum
    }

    /**
     * 设置最大选择数量
     * @param maxSelectNum 最大选择数量
     * @return [MediaConfig]
     */
    fun setMaxSelectNum(maxSelectNum: Int): MediaConfig {
        this.mMaxSelectNum = maxSelectNum
        return this
    }

    /**
     * 获取已选择的本地资源
     * @return 已选择的本地资源 [<]
     */
    fun getLocalMedia(): List<LocalMediaData>? {
        return mLocalMedia
    }

    /**
     * 设置已选择的本地资源
     * @param localMedia [<]
     * @return [MediaConfig]
     */
    fun setLocalMedia(localMedia: List<LocalMediaData>?): MediaConfig {
        this.mLocalMedia = localMedia
        return this
    }

    /**
     * 获取拍照存储地址
     * @return 拍照存储地址
     */
    fun getCameraSavePath(): String? {
        return mCameraSavePath
    }

    /**
     * 设置拍照存储地址
     * @param cameraSavePath 拍照存储地址
     * @return [MediaConfig]
     */
    fun setCameraSavePath(cameraSavePath: String?): MediaConfig {
        this.mCameraSavePath = cameraSavePath
        return this
    }

    /**
     * 获取压缩图片存储地址
     * @return 压缩图片存储地址
     */
    fun getCompressSavePath(): String? {
        return mCompressSavePath
    }

    /**
     * 设置压缩图片存储地址
     * @param compressSavePath 压缩图片存储地址
     * @return [MediaConfig]
     */
    fun setCompressSavePath(compressSavePath: String?): MediaConfig {
        this.mCompressSavePath = compressSavePath
        return this
    }

    // =

    /**
     * 克隆新的配置信息
     * @return [MediaConfig]
     */
    fun clone(): MediaConfig {
        val config = MediaConfig()
        config.mMimeType = mMimeType
        config.mSelectionMode = mSelectionMode
        config.mIsCamera = mIsCamera
        config.mIsCrop = mIsCrop
        config.mIsCircleCrop = mIsCircleCrop
        config.mIsCompress = mIsCompress
        config.mMinimumCompressSize = mMinimumCompressSize
        config.mWithAspectRatio = mWithAspectRatio
        config.mIsGif = mIsGif
        config.mImageSpanCount = mImageSpanCount
        config.mMinSelectNum = mMinSelectNum
        config.mMaxSelectNum = mMaxSelectNum
        config.mLocalMedia = mLocalMedia
        config.mCameraSavePath = mCameraSavePath
        config.mCompressSavePath = mCompressSavePath
        return config
    }

    /**
     * 设置新的配置信息
     * @param config 新的配置信息
     * @return [MediaConfig]
     */
    fun set(config: MediaConfig?): MediaConfig {
        config?.let {
            mMimeType = it.mMimeType
            mSelectionMode = it.mSelectionMode
            mIsCamera = it.mIsCamera
            mIsCrop = it.mIsCrop
            mIsCircleCrop = it.mIsCircleCrop
            mIsCompress = it.mIsCompress
            mMinimumCompressSize = it.mMinimumCompressSize
            mWithAspectRatio = it.mWithAspectRatio
            mIsGif = it.mIsGif
            mImageSpanCount = it.mImageSpanCount
            mMinSelectNum = it.mMinSelectNum
            mMaxSelectNum = it.mMaxSelectNum
            mLocalMedia = it.mLocalMedia
            mCameraSavePath = it.mCameraSavePath
            mCompressSavePath = it.mCompressSavePath
        }
        return this
    }
}