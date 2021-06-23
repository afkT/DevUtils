package dev.engine.media;

import java.util.List;

/**
 * detail: Media Selector Config
 * @author Ttt
 */
public class MediaConfig
        extends IMediaEngine.EngineConfig {

    // 相册选择类型
    private int                  mimeType            = MimeType.ofImage();
    // 相册选择模式
    private int                  selectionMode       = MimeType.MULTIPLE;
    // 是否显示拍照
    private boolean              isCamera            = true;
    // 是否裁减
    private boolean              isCrop              = false;
    // 是否圆形裁减 true = 圆形, false = 矩形
    private boolean              isCircleCrop        = false;
    // 是否压缩
    private boolean              isCompress          = false;
    // 图片大于多少才进行压缩 (kb)
    private int                  minimumCompressSize = 2048;
    // 裁减比例
    private int[]                withAspectRatio     = new int[]{0, 0};
    // 是否显示 Gif
    private boolean              isGif               = false;
    // 每行显示个数
    private int                  imageSpanCount      = 4;
    // 最小选择数量
    private int                  minSelectNum        = 1;
    // 最大选择数量
    private int                  maxSelectNum        = 9;
    // 已选择的本地资源
    private List<LocalMediaData> localMedia;
    // 拍照存储地址
    private String               cameraSavePath      = null;
    // 压缩图片存储地址
    private String               compressSavePath    = null;

    /**
     * detail: 选择模式
     * @author Ttt
     */
    public static class MimeType {

        public final static int SINGLE   = 1;
        public final static int MULTIPLE = 2;

        public final static int TYPE_ALL   = 0;
        public final static int TYPE_IMAGE = 1;
        public final static int TYPE_VIDEO = 2;
        public final static int TYPE_AUDIO = 3;

        public static int ofAll() {
            return TYPE_ALL;
        }

        public static int ofImage() {
            return TYPE_IMAGE;
        }

        public static int ofVideo() {
            return TYPE_VIDEO;
        }

        public static int ofAudio() {
            return TYPE_AUDIO;
        }
    }

    // ===========
    // = get/set =
    // ===========

    /**
     * 获取相册选择类型
     * @return 相册选择类型
     */
    public int getMimeType() {
        return mimeType;
    }

    /**
     * 设置相册选择类型
     * <pre>
     *     全部 PictureMimeType.ofAll() = 0
     *     图片 ofImage() = 1
     *     视频 ofVideo() = 2
     *     音频 ofAudio() = 3
     * </pre>
     * @param mimeType 相册选择类型
     * @return {@link MediaConfig}
     */
    public MediaConfig setMimeType(final int mimeType) {
        // 超过最大、最小值都默认为全部类型
        if (mimeType > MimeType.ofAudio() || mimeType < MimeType.ofAll()) {
            this.mimeType = MimeType.ofAll();
        } else {
            this.mimeType = mimeType;
        }
        return this;
    }

    /**
     * 获取相册选择模式
     * @return 相册选择模式
     */
    public int getSelectionMode() {
        return selectionMode;
    }

    /**
     * 设置相册选择模式
     * <pre>
     *     多选 MimeType.MULTIPLE
     *     单选 MimeType.SINGLE
     * </pre>
     * @param selectionMode 相册选择模式
     * @return {@link MediaConfig}
     */
    public MediaConfig setSelectionMode(final int selectionMode) {
        if (selectionMode >= MimeType.MULTIPLE) {
            this.selectionMode = MimeType.MULTIPLE;
        } else if (selectionMode <= MimeType.SINGLE) {
            this.selectionMode = MimeType.SINGLE;
        }
        return this;
    }

    /**
     * 是否显示拍照
     * @return {@code true} yes, {@code false} no
     */
    public boolean isCamera() {
        return isCamera;
    }

    /**
     * 设置是否显示拍照
     * @param camera {@code true} yes, {@code false} no
     * @return {@link MediaConfig}
     */
    public MediaConfig setCamera(final boolean camera) {
        isCamera = camera;
        return this;
    }

    /**
     * 是否裁减
     * @return {@code true} yes, {@code false} no
     */
    public boolean isCrop() {
        return isCrop;
    }

    /**
     * 设置是否裁减
     * @param crop {@code true} yes, {@code false} no
     * @return {@link MediaConfig}
     */
    public MediaConfig setCrop(final boolean crop) {
        isCrop = crop;
        return this;
    }

    /**
     * 是否圆形裁减
     * @return {@code true} yes, {@code false} no
     */
    public boolean isCircleCrop() {
        return isCircleCrop;
    }

    /**
     * 设置是否圆形裁减
     * @param circleCrop {@code true} yes, {@code false} no
     * @return {@link MediaConfig}
     */
    public MediaConfig setCircleCrop(final boolean circleCrop) {
        isCircleCrop = circleCrop;
        return this;
    }

    /**
     * 是否压缩
     * @return {@code true} yes, {@code false} no
     */
    public boolean isCompress() {
        return isCompress;
    }

    /**
     * 设置是否压缩
     * @param compress {@code true} yes, {@code false} no
     * @return {@link MediaConfig}
     */
    public MediaConfig setCompress(final boolean compress) {
        isCompress = compress;
        return this;
    }

    /**
     * 获取图片大于多少才进行压缩
     * @return 最小压缩大小
     */
    public int getMinimumCompressSize() {
        return minimumCompressSize;
    }

    /**
     * 设置图片大于多少才进行压缩
     * @param minimumCompressSize 最小压缩大小
     * @return {@link MediaConfig}
     */
    public MediaConfig setMinimumCompressSize(final int minimumCompressSize) {
        this.minimumCompressSize = minimumCompressSize;
        return this;
    }

    /**
     * 获取裁减比例
     * @return int[] 0 = 宽比例, 1 = 高比例
     */
    public int[] getWithAspectRatio() {
        return withAspectRatio;
    }

    /**
     * 设置裁减比例
     * @param x 宽比例
     * @param y 高比例
     * @return {@link MediaConfig}
     */
    public MediaConfig setWithAspectRatio(
            final int x,
            final int y
    ) {
        this.withAspectRatio[0] = x;
        this.withAspectRatio[1] = y;
        return this;
    }

    /**
     * 是否显示 Gif
     * @return {@code true} yes, {@code false} no
     */
    public boolean isGif() {
        return isGif;
    }

    /**
     * 设置是否显示 Gif
     * @param gif {@code true} yes, {@code false} no
     * @return {@link MediaConfig}
     */
    public MediaConfig setGif(final boolean gif) {
        isGif = gif;
        return this;
    }

    /**
     * 获取每行显示个数
     * @return 每行显示个数
     */
    public int getImageSpanCount() {
        return imageSpanCount;
    }

    /**
     * 设置每行显示个数
     * @param imageSpanCount 每行显示个数
     * @return {@link MediaConfig}
     */
    public MediaConfig setImageSpanCount(final int imageSpanCount) {
        this.imageSpanCount = Math.max(imageSpanCount, 1);
        return this;
    }

    /**
     * 获取最小选择数量
     * @return 最小选择数量
     */
    public int getMinSelectNum() {
        return minSelectNum;
    }

    /**
     * 设置最小选择数量
     * @param minSelectNum 最小选择数量
     * @return {@link MediaConfig}
     */
    public MediaConfig setMinSelectNum(final int minSelectNum) {
        this.minSelectNum = minSelectNum;
        return this;
    }

    /**
     * 获取最大选择数量
     * @return 最大选择数量
     */
    public int getMaxSelectNum() {
        return maxSelectNum;
    }

    /**
     * 设置最大选择数量
     * @param maxSelectNum 最大选择数量
     * @return {@link MediaConfig}
     */
    public MediaConfig setMaxSelectNum(final int maxSelectNum) {
        this.maxSelectNum = maxSelectNum;
        return this;
    }

    /**
     * 获取已选择的本地资源
     * @return 已选择的本地资源 {@link List<LocalMediaData>}
     */
    public List<LocalMediaData> getLocalMedia() {
        return localMedia;
    }

    /**
     * 设置已选择的本地资源
     * @param localMedia {@link List<LocalMediaData>}
     * @return {@link MediaConfig}
     */
    public MediaConfig setLocalMedia(final List<LocalMediaData> localMedia) {
        this.localMedia = localMedia;
        return this;
    }

    /**
     * 获取拍照存储地址
     * @return 拍照存储地址
     */
    public String getCameraSavePath() {
        return cameraSavePath;
    }

    /**
     * 设置拍照存储地址
     * @param cameraSavePath 拍照存储地址
     * @return {@link MediaConfig}
     */
    public MediaConfig setCameraSavePath(final String cameraSavePath) {
        this.cameraSavePath = cameraSavePath;
        return this;
    }

    /**
     * 获取压缩图片存储地址
     * @return 压缩图片存储地址
     */
    public String getCompressSavePath() {
        return compressSavePath;
    }

    /**
     * 设置压缩图片存储地址
     * @param compressSavePath 压缩图片存储地址
     * @return {@link MediaConfig}
     */
    public MediaConfig setCompressSavePath(final String compressSavePath) {
        this.compressSavePath = compressSavePath;
        return this;
    }

    // =

    /**
     * 克隆新的配置信息
     * @return {@link MediaConfig}
     */
    public MediaConfig clone() {
        MediaConfig config = new MediaConfig();
        config.mimeType            = mimeType;
        config.selectionMode       = selectionMode;
        config.isCamera            = isCamera;
        config.isCrop              = isCrop;
        config.isCircleCrop        = isCircleCrop;
        config.isCompress          = isCompress;
        config.minimumCompressSize = minimumCompressSize;
        config.withAspectRatio     = withAspectRatio;
        config.isGif               = isGif;
        config.imageSpanCount      = imageSpanCount;
        config.minSelectNum        = minSelectNum;
        config.maxSelectNum        = maxSelectNum;
        config.localMedia          = localMedia;
        config.cameraSavePath      = cameraSavePath;
        config.compressSavePath    = compressSavePath;
        return config;
    }

    /**
     * 设置新的配置信息
     * @param config 新的配置信息
     * @return {@link MediaConfig}
     */
    public MediaConfig set(final MediaConfig config) {
        if (config != null) {
            mimeType            = config.mimeType;
            selectionMode       = config.selectionMode;
            isCamera            = config.isCamera;
            isCrop              = config.isCrop;
            isCircleCrop        = config.isCircleCrop;
            isCompress          = config.isCompress;
            minimumCompressSize = config.minimumCompressSize;
            withAspectRatio     = config.withAspectRatio;
            isGif               = config.isGif;
            imageSpanCount      = config.imageSpanCount;
            minSelectNum        = config.minSelectNum;
            maxSelectNum        = config.maxSelectNum;
            localMedia          = config.localMedia;
            cameraSavePath      = config.cameraSavePath;
            compressSavePath    = config.compressSavePath;
        }
        return this;
    }
}