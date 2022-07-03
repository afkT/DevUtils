package dev.engine.media

import android.net.Uri
import dev.utils.app.UriUtils
import java.util.*

/**
 * detail: Media Selector Data
 * @author Ttt
 * 统一使用 Uri 作为资源获取来源, 减少后续适配情况
 * <p></p>
 * 唯一需要考虑的是适配第三方库转换 String Path 逻辑处理
 * get 时使用 uri.toString() 还是 uri.getPath() 亦或是其他方法
 * 根据 set 时存入什么值决定
 */
class MediaData : IMediaEngine.EngineData {

    // 第三方库原始数据 ( 可自行强转 )
    private var mLibOriginalData: Any? = null

    /**
     * 强转第三方库原始数据
     * @return 泛型类型对象
     */
    fun <T> convertLibOriginalData(): T? {
        return mLibOriginalData as? T
    }

    /**
     * 获取第三方库原始数据
     * @return 第三方库原始数据
     */
    fun getLibOriginalData(): Any? {
        return mLibOriginalData
    }

    /**
     * 设置第三方库原始数据
     * @param libOriginalData 第三方库原始数据
     * @return MediaData
     */
    fun setLibOriginalData(libOriginalData: Any?): MediaData {
        mLibOriginalData = libOriginalData
        return this
    }

    // ==========
    // = 构造函数 =
    // ==========

    constructor() : this(UUID.randomUUID().hashCode().toLong())

    constructor(uuid: Long) {
        mUUID = uuid
    }

    // ==========
    // = 通用属性 =
    // ==========

    // 随机 UUID randomUUID().hashCode()
    private var mUUID: Long = 0

    // 自定义数据
    private var mCustomData: Any? = null

    // ==============
    // = 资源路径 Uri =
    // ==============

    // 原始 Uri
    private var mOriginalUri: Uri? = null

    // 沙盒转存 Uri
    private var mSandboxUri: Uri? = null

    // 压缩 Uri
    private var mCompressUri: Uri? = null

    // 缩略图 ( 视频、图片等 ) Uri
    private var mThumbnailUri: Uri? = null

    // 水印 Uri
    private var mWatermarkUri: Uri? = null

    // 裁剪 ( 编辑、剪辑等 ) Uri
    private var mCropUri: Uri? = null

    // ==========
    // = 资源信息 =
    // ==========

    // 类型
    private var mMimeType: String? = null

    // 时长 ( 视频、音频 )
    private var mDuration: Long = 0

    // 宽度 ( 图片、视频 )
    private var mWidth = 0

    // 高度 ( 图片、视频 )
    private var mHeight = 0

    // ==============
    // = 资源裁剪信息 =
    // ==============

    // 裁剪宽度
    private var mCropImageWidth = 0

    // 裁剪高度
    private var mCropImageHeight = 0

    // 裁剪 X 轴偏移值
    private var mCropOffsetX = 0

    // 裁剪 Y 轴偏移值
    private var mCropOffsetY = 0

    // 裁剪纵横比 X:Y
    private var mCropAspectRatio = 0f

    // ==========
    // = 状态信息 =
    // ==========

    // 是否裁剪状态
    private var mCropState = false

    // 是否压缩状态
    private var mCompressState = false

    // ==============
    // = 对外公开方法 =
    // ==============

    /**
     * 获取可用资源 Uri
     * @return 可用资源路径 Uri
     * 获取有效可用 Uri 校验返回顺序为
     * 裁剪 -> 压缩 -> 水印 -> 沙盒 -> 原始
     * 如果有差异化可自行继承该类重写该方法或直接进行 Uri 获取
     */
    fun getAvailableUri(): Uri? {
        if (isExistCropUri()) {
            return mCropUri
        } else if (isExistCompressUri()) {
            return mCompressUri
        } else if (isExistWatermarkUri()) {
            return mWatermarkUri
        } else if (isExistSandboxUri()) {
            return mSandboxUri
        } else if (isExistOriginalUri()) {
            return mOriginalUri
        }
        return null
    }

    // ==========
    // = 快捷校验 =
    // ==========

    /**
     * 是否存在原始 Uri
     * @return `true` yes, `false` no
     */
    fun isExistOriginalUri(): Boolean {
        return UriUtils.isUriExists(mOriginalUri)
    }

    /**
     * 是否存在沙盒转存 Uri
     * @return `true` yes, `false` no
     */
    fun isExistSandboxUri(): Boolean {
        return UriUtils.isUriExists(mSandboxUri)
    }

    /**
     * 是否存在缩略图 Uri
     * @return `true` yes, `false` no
     */
    fun isExistThumbnailUri(): Boolean {
        return UriUtils.isUriExists(mThumbnailUri)
    }

    /**
     * 是否存在水印 Uri
     * @return `true` yes, `false` no
     */
    fun isExistWatermarkUri(): Boolean {
        return UriUtils.isUriExists(mWatermarkUri)
    }

    /**
     * 是否属于压缩状态且存在压缩 Uri
     * @return `true` yes, `false` no
     */
    fun isExistCompressUri(): Boolean {
        return mCompressState && UriUtils.isUriExists(mCompressUri)
    }

    /**
     * 是否属于裁剪状态且存在裁剪 Uri
     * @return `true` yes, `false` no
     */
    fun isExistCropUri(): Boolean {
        return mCropState && UriUtils.isUriExists(mCropUri)
    }

    // ==========
    // = 克隆方法 =
    // ==========

    /**
     * 克隆对象
     * @return MediaData
     */
    fun clone(): MediaData {
        val media = MediaData(mUUID)
        media.mCustomData = mCustomData
        media.mOriginalUri = mOriginalUri
        media.mSandboxUri = mSandboxUri
        media.mCompressUri = mCompressUri
        media.mThumbnailUri = mThumbnailUri
        media.mWatermarkUri = mWatermarkUri
        media.mCropUri = mCropUri
        media.mMimeType = mMimeType
        media.mDuration = mDuration
        media.mWidth = mWidth
        media.mHeight = mHeight
        media.mCropImageWidth = mCropImageWidth
        media.mCropImageHeight = mCropImageHeight
        media.mCropOffsetX = mCropOffsetX
        media.mCropOffsetY = mCropOffsetY
        media.mCropAspectRatio = mCropAspectRatio
        media.mCropState = mCropState
        media.mCompressState = mCompressState
        return media
    }

    /**
     * 设置 MediaData 数据
     * @param media MediaData
     * @return MediaData
     */
    fun set(media: MediaData?): MediaData {
        media?.let {
            mCustomData = it.mCustomData
            mOriginalUri = it.mOriginalUri
            mSandboxUri = it.mSandboxUri
            mCompressUri = it.mCompressUri
            mThumbnailUri = it.mThumbnailUri
            mWatermarkUri = it.mWatermarkUri
            mCropUri = it.mCropUri
            mMimeType = it.mMimeType
            mDuration = it.mDuration
            mWidth = it.mWidth
            mHeight = it.mHeight
            mCropImageWidth = it.mCropImageWidth
            mCropImageHeight = it.mCropImageHeight
            mCropOffsetX = it.mCropOffsetX
            mCropOffsetY = it.mCropOffsetY
            mCropAspectRatio = it.mCropAspectRatio
            mCropState = it.mCropState
            mCompressState = it.mCompressState
        }
        return this
    }

    // ===========
    // = get/set =
    // ===========

    /**
     * 获取唯一标识 UUID
     * @return UUID.hashCode()
     */
    fun getUUID(): Long {
        return mUUID
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
     * @return MediaData
     */
    fun setCustomData(customData: Any?): MediaData {
        mCustomData = customData
        return this
    }

    /**
     * 获取原始 Uri
     * @return 原始 Uri
     */
    fun getOriginalUri(): Uri? {
        return mOriginalUri
    }

    /**
     * 设置原始 Uri
     * @param originalUri 原始 Uri
     * @return MediaData
     */
    fun setOriginalUri(originalUri: Uri?): MediaData {
        mOriginalUri = originalUri
        return this
    }

    /**
     * 获取沙盒转存 Uri
     * @return 沙盒转存 Uri
     */
    fun getSandboxUri(): Uri? {
        return mSandboxUri
    }

    /**
     * 设置沙盒转存 Uri
     * @param sandboxUri 沙盒转存 Uri
     * @return MediaData
     */
    fun setSandboxUri(sandboxUri: Uri?): MediaData {
        mSandboxUri = sandboxUri
        return this
    }

    /**
     * 获取压缩 Uri
     * @return 压缩 Uri
     */
    fun getCompressUri(): Uri? {
        return mCompressUri
    }

    /**
     * 设置压缩 Uri
     * @param compressUri 压缩 Uri
     * @return MediaData
     */
    fun setCompressUri(compressUri: Uri?): MediaData {
        mCompressUri = compressUri
        return this
    }

    /**
     * 获取缩略图 Uri
     * @return 缩略图 Uri
     */
    fun getThumbnailUri(): Uri? {
        return mThumbnailUri
    }

    /**
     * 设置缩略图 Uri
     * @param thumbnailUri 缩略图 Uri
     * @return MediaData
     */
    fun setThumbnailUri(thumbnailUri: Uri?): MediaData {
        mThumbnailUri = thumbnailUri
        return this
    }

    /**
     * 获取水印 Uri
     * @return 水印 Uri
     */
    fun getWatermarkUri(): Uri? {
        return mWatermarkUri
    }

    /**
     * 设置水印 Uri
     * @param watermarkUri 水印 Uri
     * @return MediaData
     */
    fun setWatermarkUri(watermarkUri: Uri?): MediaData {
        mWatermarkUri = watermarkUri
        return this
    }

    /**
     * 获取裁剪 Uri
     * @return 裁剪 Uri
     */
    fun getCropUri(): Uri? {
        return mCropUri
    }

    /**
     * 设置裁剪 Uri
     * @param cropUri 裁剪 Uri
     * @return MediaData
     */
    fun setCropUri(cropUri: Uri?): MediaData {
        mCropUri = cropUri
        return this
    }

    /**
     * 获取类型
     * @return 类型
     */
    fun getMimeType(): String? {
        return mMimeType
    }

    /**
     * 设置类型
     * @param mimeType 类型
     * @return MediaData
     */
    fun setMimeType(mimeType: String?): MediaData {
        mMimeType = mimeType
        return this
    }

    /**
     * 获取时长
     * @return 时长
     */
    fun getDuration(): Long {
        return mDuration
    }

    /**
     * 设置时长
     * @param duration 时长
     * @return MediaData
     */
    fun setDuration(duration: Long): MediaData {
        mDuration = duration
        return this
    }

    /**
     * 获取宽度
     * @return 宽度
     */
    fun getWidth(): Int {
        return mWidth
    }

    /**
     * 设置宽度
     * @param width 宽度
     * @return MediaData
     */
    fun setWidth(width: Int): MediaData {
        mWidth = width
        return this
    }

    /**
     * 获取高度
     * @return 高度
     */
    fun getHeight(): Int {
        return mHeight
    }

    /**
     * 设置高度
     * @param height 高度
     * @return MediaData
     */
    fun setHeight(height: Int): MediaData {
        mHeight = height
        return this
    }

    /**
     * 获取裁剪宽度
     * @return 裁剪宽度
     */
    fun getCropImageWidth(): Int {
        return mCropImageWidth
    }

    /**
     * 设置裁剪宽度
     * @param cropImageWidth 裁剪宽度
     * @return MediaData
     */
    fun setCropImageWidth(cropImageWidth: Int): MediaData {
        mCropImageWidth = cropImageWidth
        return this
    }

    /**
     * 获取裁剪高度
     * @return 裁剪高度
     */
    fun getCropImageHeight(): Int {
        return mCropImageHeight
    }

    /**
     * 设置裁剪高度
     * @param cropImageHeight 裁剪高度
     * @return MediaData
     */
    fun setCropImageHeight(cropImageHeight: Int): MediaData {
        mCropImageHeight = cropImageHeight
        return this
    }

    /**
     * 获取裁剪 X 轴偏移值
     * @return 裁剪 X 轴偏移值
     */
    fun getCropOffsetX(): Int {
        return mCropOffsetX
    }

    /**
     * 设置裁剪 X 轴偏移值
     * @param cropOffsetX X 轴偏移值
     * @return MediaData
     */
    fun setCropOffsetX(cropOffsetX: Int): MediaData {
        mCropOffsetX = cropOffsetX
        return this
    }

    /**
     * 获取裁剪 Y 轴偏移值
     * @return 裁剪 Y 轴偏移值
     */
    fun getCropOffsetY(): Int {
        return mCropOffsetY
    }

    /**
     * 设置裁剪 Y 轴偏移值
     * @param cropOffsetY Y 轴偏移值
     * @return MediaData
     */
    fun setCropOffsetY(cropOffsetY: Int): MediaData {
        mCropOffsetY = cropOffsetY
        return this
    }

    /**
     * 获取裁剪纵横比 X:Y
     * @return 裁剪纵横比 X:Y
     */
    fun getCropAspectRatio(): Float {
        return mCropAspectRatio
    }

    /**
     * 设置裁剪纵横比 X:Y
     * @param cropAspectRatio 裁剪纵横比 X:Y
     * @return MediaData
     */
    fun setCropAspectRatio(cropAspectRatio: Float): MediaData {
        mCropAspectRatio = cropAspectRatio
        return this
    }

    /**
     * 获取裁剪状态
     * @return `true` yes, `false` no
     */
    fun isCropState(): Boolean {
        return mCropState
    }

    /**
     * 设置裁剪状态
     * @param cropState `true` yes, `false` no
     * @return MediaData
     */
    fun setCropState(cropState: Boolean): MediaData {
        mCropState = cropState
        return this
    }

    /**
     * 获取压缩状态
     * @return `true` yes, `false` no
     */
    fun isCompressState(): Boolean {
        return mCompressState
    }

    /**
     * 设置压缩状态
     * @param compressState `true` yes, `false` no
     * @return MediaData
     */
    fun setCompressState(compressState: Boolean): MediaData {
        mCompressState = compressState
        return this
    }
}