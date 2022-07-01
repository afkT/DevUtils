package java.dev.engine.media;

import java.util.List;

import dev.engine.media.IMediaEngine;

/**
 * detail: Media Selector Config
 * @author Ttt
 * <pre>
 *     图片选择库可配置选项过多且不一致
 *     所以新增一个 mLibCustomConfig 第三方库自定义配置
 *     要求必须设置第三方库配置、参数等自行转换进行使用
 * </pre>
 */
public class MediaConfig
        extends IMediaEngine.EngineConfig {

    // 第三方库自定义配置 ( 可自行强转 )
    private Object mLibCustomConfig;

    /**
     * 强转第三方库自定义配置
     * @param <T> 泛型
     * @return 泛型类型对象
     */
    public <T> T convertLibCustomConfig() {
        if (mLibCustomConfig == null) return null;
        return (T) mLibCustomConfig;
    }

    /**
     * 获取第三方库自定义配置
     * @return 第三方库自定义配置
     */
    public Object getLibCustomConfig() {
        return mLibCustomConfig;
    }

    /**
     * 设置第三方库自定义配置
     * @param libCustomConfig 第三方库自定义配置
     * @return MediaData
     */
    public MediaConfig setLibCustomConfig(final Object libCustomConfig) {
        this.mLibCustomConfig = libCustomConfig;
        return this;
    }

    // ==========
    // = 常用配置 =
    // ==========

    // 相册选择类型
    private int             mMimeType      = MimeType.ofImage();
    // 相册选择模式
    private int             mSelectionMode = MimeType.MULTIPLE;
    // 已选择的资源
    private List<MediaData> mMediaDatas;

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

        public final static int OPEN_CAMERA  = 1;
        public final static int OPEN_GALLERY = 2;
        public final static int OPEN_PREVIEW = 3;

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
        return mMimeType;
    }

    /**
     * 设置相册选择类型
     * <pre>
     *     全部 ofAll()   = 0
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
            this.mMimeType = MimeType.ofAll();
        } else {
            this.mMimeType = mimeType;
        }
        return this;
    }

    /**
     * 获取相册选择模式
     * @return 相册选择模式
     */
    public int getSelectionMode() {
        return mSelectionMode;
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
            this.mSelectionMode = MimeType.MULTIPLE;
        } else {
            this.mSelectionMode = MimeType.SINGLE;
        }
        return this;
    }

    /**
     * 获取已选择的资源
     * @return 已选择的资源 {@link List<MediaData>}
     */
    public List<MediaData> getMediaDatas() {
        return mMediaDatas;
    }

    /**
     * 设置已选择的资源
     * @param mediaDatas {@link List<MediaData>}
     * @return {@link MediaConfig}
     */
    public MediaConfig setMediaDatas(final List<MediaData> mediaDatas) {
        this.mMediaDatas = mediaDatas;
        return this;
    }

    // =

    /**
     * 克隆新的配置信息
     * @return {@link MediaConfig}
     */
    public MediaConfig clone() {
        MediaConfig config = new MediaConfig();
        config.mLibCustomConfig = mLibCustomConfig;
        config.mMimeType        = mMimeType;
        config.mSelectionMode   = mSelectionMode;
        config.mMediaDatas      = mMediaDatas;
        return config;
    }

    /**
     * 设置新的配置信息
     * @param config 新的配置信息
     * @return {@link MediaConfig}
     */
    public MediaConfig set(final MediaConfig config) {
        if (config != null) {
            mLibCustomConfig = config.mLibCustomConfig;
            mMimeType        = config.mMimeType;
            mSelectionMode   = config.mSelectionMode;
            mMediaDatas      = config.mMediaDatas;
        }
        return this;
    }
}