package dev.engine.image;

/**
 * detail: Glide Image Config
 * @author Ttt
 */
public class GlideConfig
        extends IImageEngine.ImageConfig {

    // 是否使用缓存
    private boolean cache;
    // 加载图片宽
    private int     width;
    // 加载图片高
    private int     height;
    // 加载缩略图时应用于尺寸的乘数
    private float   thumbnail;

    public boolean isCache() {
        return cache;
    }

    public GlideConfig setCache(boolean cache) {
        this.cache = cache;
        return this;
    }

    public int getWidth() {
        return width;
    }

    public GlideConfig setWidth(int width) {
        this.width = width;
        return this;
    }

    public int getHeight() {
        return height;
    }

    public GlideConfig setHeight(int height) {
        this.height = height;
        return this;
    }

    public float getThumbnail() {
        return thumbnail;
    }

    public GlideConfig setThumbnail(float thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }

    // ===========
    // = 其他方法 =
    // ===========

    /**
     * 克隆配置信息
     * @param config {@link GlideConfig}
     * @return {@link GlideConfig}
     */
    public GlideConfig clone(final GlideConfig config) {
        return new GlideConfig(config);
    }

    // ===========
    // = 构造函数 =
    // ===========

    public GlideConfig() {
    }

    public GlideConfig(final GlideConfig config) {
        if (config != null) {
            this.cache = config.cache;
            this.width = config.width;
            this.height = config.height;
            this.thumbnail = config.thumbnail;
        }
    }
}