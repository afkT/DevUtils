package dev.engine.media

/**
 * detail: Media Selector Config
 * @author Ttt
 * 图片选择库可配置选项过多且不一致
 * 所以新增一个 mLibCustomConfig 第三方库自定义配置
 * 要求必须设置第三方库配置、参数等自行转换进行使用
 */
class MediaConfig : IMediaEngine.EngineConfig() {

    // 第三方库自定义配置 ( 可自行强转 )
    private var mLibCustomConfig: Any? = null

    /**
     * 强转第三方库自定义配置
     * @return 泛型类型对象
     */
    fun <T> convertLibCustomConfig(): T? {
        return mLibCustomConfig as? T
    }

    /**
     * 获取第三方库自定义配置
     * @return 第三方库自定义配置
     */
    fun getLibCustomConfig(): Any? {
        return mLibCustomConfig
    }

    /**
     * 设置第三方库自定义配置
     * @param libCustomConfig 第三方库自定义配置
     * @return MediaConfig
     */
    fun setLibCustomConfig(libCustomConfig: Any?): MediaConfig {
        mLibCustomConfig = libCustomConfig
        return this
    }

    // ==========
    // = 常用配置 =
    // ==========

    // 相册选择类型
    private var mMimeType: Int = MimeType.ofImage()

    // 相册选择模式
    private var mSelectionMode: Int = MimeType.MULTIPLE

    // 自定义数据
    private var mCustomData: Any? = null

    // 已选择的资源
    private var mMediaDatas: MutableList<MediaData>? = null

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

        const val OPEN_CAMERA = 1
        const val OPEN_GALLERY = 2
        const val OPEN_PREVIEW = 3

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
     * 全部 ofAll()   = 0
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
     * 获取自定义数据
     * @return 自定义数据
     */
    fun getCustomData(): Any? {
        return mCustomData
    }

    /**
     * 设置自定义数据
     * @param customData 自定义数据
     * @return MediaConfig
     */
    fun setCustomData(customData: Any?): MediaConfig {
        mCustomData = customData
        return this
    }

    /**
     * 获取已选择的资源
     * @return 已选择的资源
     */
    fun getMediaDatas(): List<MediaData>? {
        return mMediaDatas
    }

    /**
     * 设置已选择的资源
     * @param mediaDatas 选择的资源
     * @return [MediaConfig]
     */
    fun setMediaDatas(mediaDatas: MutableList<MediaData>?): MediaConfig {
        mMediaDatas = mediaDatas
        return this
    }

    // =

    /**
     * 克隆新的配置信息
     * @return [MediaConfig]
     */
    fun clone(): MediaConfig {
        val config = MediaConfig()
        config.mLibCustomConfig = mLibCustomConfig
        config.mMimeType = mMimeType
        config.mSelectionMode = mSelectionMode
        config.mCustomData = mCustomData
        config.mMediaDatas = mMediaDatas
        return config
    }

    /**
     * 设置新的配置信息
     * @param config 新的配置信息
     * @return [MediaConfig]
     */
    fun set(config: MediaConfig?): MediaConfig {
        config?.let {
            mLibCustomConfig = it.mLibCustomConfig
            mMimeType = it.mMimeType
            mSelectionMode = it.mSelectionMode
            mCustomData = it.mCustomData
            mMediaDatas = it.mMediaDatas
        }
        return this
    }
}