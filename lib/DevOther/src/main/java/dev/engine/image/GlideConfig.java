package dev.engine.image;

/**
 * detail: Glide Image Config
 * @author Ttt
 */
public class GlideConfig
        extends IImageEngine.ImageConfig {

    // 是否使用缓存
    public boolean cache;
    // 图片宽高
    public int     width, height;
}
