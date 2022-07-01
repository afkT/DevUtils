package java.dev.engine.media;

import android.net.Uri;

import java.util.UUID;

import dev.engine.media.IMediaEngine;
import dev.utils.app.UriUtils;

/**
 * detail: Media Selector Data
 * @author Ttt
 * <pre>
 *     统一使用 Uri 作为资源获取来源, 减少后续适配情况
 *     <p></p>
 *     唯一需要考虑的是适配第三方库转换 String Path 逻辑处理
 *     get 时使用 uri.toString() 还是 uri.getPath() 亦或是其他方法
 *     根据 set 时存入什么值决定
 * </pre>
 */
public class MediaData
        extends IMediaEngine.EngineData {

    // 第三方库原始数据 ( 可自行强转 )
    private Object mLibOriginalData;

    /**
     * 强转第三方库原始数据
     * @param <T> 泛型
     * @return 泛型类型对象
     */
    public <T> T convertLibOriginalData() {
        if (mLibOriginalData == null) return null;
        return (T) mLibOriginalData;
    }

    /**
     * 获取第三方库原始数据
     * @return 第三方库原始数据
     */
    public Object getLibOriginalData() {
        return mLibOriginalData;
    }

    /**
     * 设置第三方库原始数据
     * @param libOriginalData 第三方库原始数据
     * @return MediaData
     */
    public MediaData setLibOriginalData(final Object libOriginalData) {
        this.mLibOriginalData = libOriginalData;
        return this;
    }

    // ==========
    // = 构造函数 =
    // ==========

    public MediaData() {
        this(UUID.randomUUID().hashCode());
    }

    public MediaData(final long uuid) {
        this.mUUID = uuid;
    }

    // ==========
    // = 通用属性 =
    // ==========

    // 随机 UUID randomUUID().hashCode()
    private final long mUUID;

    // 自定义数据
    private Object mCustomData;

    // ==============
    // = 资源路径 Uri =
    // ==============

    // 原始 Uri
    private Uri mOriginalUri;

    // 沙盒转存 Uri
    private Uri mSandboxUri;

    // 压缩 Uri
    private Uri mCompressUri;

    // 缩略图 ( 视频、图片等 ) Uri
    private Uri mThumbnailUri;

    // 水印 Uri
    private Uri mWatermarkUri;

    // 裁剪 ( 编辑、剪辑等 ) Uri
    private Uri mCropUri;

    // ==========
    // = 资源信息 =
    // ==========

    // 类型
    private String mMimeType;

    // 时长 ( 视频、音频 )
    private long mDuration;

    // 宽度 ( 图片、视频 )
    private int mWidth;

    // 高度 ( 图片、视频 )
    private int mHeight;

    // ==============
    // = 资源裁剪信息 =
    // ==============

    // 裁剪宽度
    private int mCropImageWidth;

    // 裁剪高度
    private int mCropImageHeight;

    // 裁剪 X 轴偏移值
    private int mCropOffsetX;

    // 裁剪 Y 轴偏移值
    private int mCropOffsetY;

    // 裁剪纵横比 X:Y
    private float mCropAspectRatio;

    // ==========
    // = 状态信息 =
    // ==========

    // 是否裁剪状态
    private boolean mCropState = false;

    // 是否压缩状态
    private boolean mCompressState = false;

    // ==============
    // = 对外公开方法 =
    // ==============

    /**
     * 获取可用资源 Uri
     * <pre>
     *     获取有效可用 Uri 校验返回顺序为
     *     裁剪 -> 压缩 -> 水印 -> 沙盒 -> 原始
     *     <p></p>
     *     如果有差异化可自行继承该类重写该方法或直接进行 Uri 获取
     * </pre>
     * @return 可用资源路径 Uri
     */
    public Uri getAvailableUri() {
        if (isExistCropUri()) {
            return mCropUri;
        } else if (isExistCompressUri()) {
            return mCompressUri;
        } else if (isExistWatermarkUri()) {
            return mWatermarkUri;
        } else if (isExistSandboxUri()) {
            return mSandboxUri;
        } else if (isExistOriginalUri()) {
            return mOriginalUri;
        }
        return null;
    }

    // ==========
    // = 快捷校验 =
    // ==========

    /**
     * 是否存在原始 Uri
     * @return {@code true} yes, {@code false} no
     */
    public boolean isExistOriginalUri() {
        return UriUtils.isUriExists(mOriginalUri);
    }

    /**
     * 是否存在沙盒转存 Uri
     * @return {@code true} yes, {@code false} no
     */
    public boolean isExistSandboxUri() {
        return UriUtils.isUriExists(mSandboxUri);
    }

    /**
     * 是否存在缩略图 Uri
     * @return {@code true} yes, {@code false} no
     */
    public boolean isExistThumbnailUri() {
        return UriUtils.isUriExists(mThumbnailUri);
    }

    /**
     * 是否存在水印 Uri
     * @return {@code true} yes, {@code false} no
     */
    public boolean isExistWatermarkUri() {
        return UriUtils.isUriExists(mWatermarkUri);
    }

    /**
     * 是否属于压缩状态且存在压缩 Uri
     * @return {@code true} yes, {@code false} no
     */
    public boolean isExistCompressUri() {
        return mCompressState && UriUtils.isUriExists(mCompressUri);
    }

    /**
     * 是否属于裁剪状态且存在裁剪 Uri
     * @return {@code true} yes, {@code false} no
     */
    public boolean isExistCropUri() {
        return mCropState && UriUtils.isUriExists(mCropUri);
    }

    // ==========
    // = 克隆方法 =
    // ==========

    /**
     * 克隆对象
     * @return MediaData
     */
    public MediaData clone() {
        MediaData media = new MediaData(mUUID);
        media.mCustomData      = mCustomData;
        media.mOriginalUri     = mOriginalUri;
        media.mSandboxUri      = mSandboxUri;
        media.mCompressUri     = mCompressUri;
        media.mThumbnailUri    = mThumbnailUri;
        media.mWatermarkUri    = mWatermarkUri;
        media.mCropUri         = mCropUri;
        media.mMimeType        = mMimeType;
        media.mDuration        = mDuration;
        media.mWidth           = mWidth;
        media.mHeight          = mHeight;
        media.mCropImageWidth  = mCropImageWidth;
        media.mCropImageHeight = mCropImageHeight;
        media.mCropOffsetX     = mCropOffsetX;
        media.mCropOffsetY     = mCropOffsetY;
        media.mCropAspectRatio = mCropAspectRatio;
        media.mCropState       = mCropState;
        media.mCompressState   = mCompressState;
        return media;
    }

    /**
     * 设置 MediaData 数据
     * @param media MediaData
     * @return MediaData
     */
    public MediaData set(final MediaData media) {
        if (media != null) {
            mCustomData      = media.mCustomData;
            mOriginalUri     = media.mOriginalUri;
            mSandboxUri      = media.mSandboxUri;
            mCompressUri     = media.mCompressUri;
            mThumbnailUri    = media.mThumbnailUri;
            mWatermarkUri    = media.mWatermarkUri;
            mCropUri         = media.mCropUri;
            mMimeType        = media.mMimeType;
            mDuration        = media.mDuration;
            mWidth           = media.mWidth;
            mHeight          = media.mHeight;
            mCropImageWidth  = media.mCropImageWidth;
            mCropImageHeight = media.mCropImageHeight;
            mCropOffsetX     = media.mCropOffsetX;
            mCropOffsetY     = media.mCropOffsetY;
            mCropAspectRatio = media.mCropAspectRatio;
            mCropState       = media.mCropState;
            mCompressState   = media.mCompressState;
        }
        return this;
    }

    // ===========
    // = get/set =
    // ===========

    /**
     * 获取唯一标识 UUID
     * @return UUID.hashCode()
     */
    public long getUUID() {
        return mUUID;
    }

    /**
     * 获取自定义数据
     * @return 自定义数据
     */
    public Object getCustomData() {
        return mCustomData;
    }

    /**
     * 设置自定义数据
     * @param customData 自定义数据
     * @return MediaData
     */
    public MediaData setCustomData(final Object customData) {
        this.mCustomData = customData;
        return this;
    }

    /**
     * 获取原始 Uri
     * @return 原始 Uri
     */
    public Uri getOriginalUri() {
        return mOriginalUri;
    }

    /**
     * 设置原始 Uri
     * @param originalUri 原始 Uri
     * @return MediaData
     */
    public MediaData setOriginalUri(final Uri originalUri) {
        this.mOriginalUri = originalUri;
        return this;
    }

    /**
     * 获取沙盒转存 Uri
     * @return 沙盒转存 Uri
     */
    public Uri getSandboxUri() {
        return mSandboxUri;
    }

    /**
     * 设置沙盒转存 Uri
     * @param sandboxUri 沙盒转存 Uri
     * @return MediaData
     */
    public MediaData setSandboxUri(final Uri sandboxUri) {
        this.mSandboxUri = sandboxUri;
        return this;
    }

    /**
     * 获取压缩 Uri
     * @return 压缩 Uri
     */
    public Uri getCompressUri() {
        return mCompressUri;
    }

    /**
     * 设置压缩 Uri
     * @param compressUri 压缩 Uri
     * @return MediaData
     */
    public MediaData setCompressUri(final Uri compressUri) {
        this.mCompressUri = compressUri;
        return this;
    }

    /**
     * 获取缩略图 Uri
     * @return 缩略图 Uri
     */
    public Uri getThumbnailUri() {
        return mThumbnailUri;
    }

    /**
     * 设置缩略图 Uri
     * @param thumbnailUri 缩略图 Uri
     * @return MediaData
     */
    public MediaData setThumbnailUri(final Uri thumbnailUri) {
        this.mThumbnailUri = thumbnailUri;
        return this;
    }

    /**
     * 获取水印 Uri
     * @return 水印 Uri
     */
    public Uri getWatermarkUri() {
        return mWatermarkUri;
    }

    /**
     * 设置水印 Uri
     * @param watermarkUri 水印 Uri
     * @return MediaData
     */
    public MediaData setWatermarkUri(final Uri watermarkUri) {
        this.mWatermarkUri = watermarkUri;
        return this;
    }

    /**
     * 获取裁剪 Uri
     * @return 裁剪 Uri
     */
    public Uri getCropUri() {
        return mCropUri;
    }

    /**
     * 设置裁剪 Uri
     * @param cropUri 裁剪 Uri
     * @return MediaData
     */
    public MediaData setCropUri(final Uri cropUri) {
        this.mCropUri = cropUri;
        return this;
    }

    /**
     * 获取类型
     * @return 类型
     */
    public String getMimeType() {
        return mMimeType;
    }

    /**
     * 设置类型
     * @param mimeType 类型
     * @return MediaData
     */
    public MediaData setMimeType(final String mimeType) {
        this.mMimeType = mimeType;
        return this;
    }

    /**
     * 获取时长
     * @return 时长
     */
    public long getDuration() {
        return mDuration;
    }

    /**
     * 设置时长
     * @param duration 时长
     * @return MediaData
     */
    public MediaData setDuration(final long duration) {
        this.mDuration = duration;
        return this;
    }

    /**
     * 获取宽度
     * @return 宽度
     */
    public int getWidth() {
        return mWidth;
    }

    /**
     * 设置宽度
     * @param width 宽度
     * @return MediaData
     */
    public MediaData setWidth(final int width) {
        this.mWidth = width;
        return this;
    }

    /**
     * 获取高度
     * @return 高度
     */
    public int getHeight() {
        return mHeight;
    }

    /**
     * 设置高度
     * @param height 高度
     * @return MediaData
     */
    public MediaData setHeight(final int height) {
        this.mHeight = height;
        return this;
    }

    /**
     * 获取裁剪宽度
     * @return 裁剪宽度
     */
    public int getCropImageWidth() {
        return mCropImageWidth;
    }

    /**
     * 设置裁剪宽度
     * @param cropImageWidth 裁剪宽度
     * @return MediaData
     */
    public MediaData setCropImageWidth(final int cropImageWidth) {
        this.mCropImageWidth = cropImageWidth;
        return this;
    }

    /**
     * 获取裁剪高度
     * @return 裁剪高度
     */
    public int getCropImageHeight() {
        return mCropImageHeight;
    }

    /**
     * 设置裁剪高度
     * @param cropImageHeight 裁剪高度
     * @return MediaData
     */
    public MediaData setCropImageHeight(final int cropImageHeight) {
        this.mCropImageHeight = cropImageHeight;
        return this;
    }

    /**
     * 获取裁剪 X 轴偏移值
     * @return 裁剪 X 轴偏移值
     */
    public int getCropOffsetX() {
        return mCropOffsetX;
    }

    /**
     * 设置裁剪 X 轴偏移值
     * @param cropOffsetX X 轴偏移值
     * @return MediaData
     */
    public MediaData setCropOffsetX(final int cropOffsetX) {
        this.mCropOffsetX = cropOffsetX;
        return this;
    }

    /**
     * 获取裁剪 Y 轴偏移值
     * @return 裁剪 Y 轴偏移值
     */
    public int getCropOffsetY() {
        return mCropOffsetY;
    }

    /**
     * 设置裁剪 Y 轴偏移值
     * @param cropOffsetY Y 轴偏移值
     * @return MediaData
     */
    public MediaData setCropOffsetY(final int cropOffsetY) {
        this.mCropOffsetY = cropOffsetY;
        return this;
    }

    /**
     * 获取裁剪纵横比 X:Y
     * @return 裁剪纵横比 X:Y
     */
    public float getCropAspectRatio() {
        return mCropAspectRatio;
    }

    /**
     * 设置裁剪纵横比 X:Y
     * @param cropAspectRatio 裁剪纵横比 X:Y
     * @return MediaData
     */
    public MediaData setCropAspectRatio(final float cropAspectRatio) {
        this.mCropAspectRatio = cropAspectRatio;
        return this;
    }

    /**
     * 获取裁剪状态
     * @return {@code true} yes, {@code false} no
     */
    public boolean isCropState() {
        return mCropState;
    }

    /**
     * 设置裁剪状态
     * @param cropState {@code true} yes, {@code false} no
     * @return MediaData
     */
    public MediaData setCropState(final boolean cropState) {
        this.mCropState = cropState;
        return this;
    }

    /**
     * 获取压缩状态
     * @return {@code true} yes, {@code false} no
     */
    public boolean isCompressState() {
        return mCompressState;
    }

    /**
     * 设置压缩状态
     * @param compressState {@code true} yes, {@code false} no
     * @return MediaData
     */
    public MediaData setCompressState(final boolean compressState) {
        this.mCompressState = compressState;
        return this;
    }
}