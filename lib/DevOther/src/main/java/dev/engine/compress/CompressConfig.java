package dev.engine.compress;

/**
 * detail: Image Compress Config
 * @author Ttt
 */
public class CompressConfig
        extends ICompressEngine.EngineConfig {

    // 单位 KB 默认 100 kb 以下不压缩
    public final int     ignoreSize;
    // 是否保留透明通道
    public final boolean focusAlpha;
    // 压缩图片存储路径
    public final String  targetDir;

    public CompressConfig(int ignoreSize) {
        this(ignoreSize, true, null);
    }

    public CompressConfig(String targetDir) {
        this(100, true, targetDir);
    }

    public CompressConfig(
            int ignoreSize,
            String targetDir
    ) {
        this(ignoreSize, true, targetDir);
    }

    public CompressConfig(
            int ignoreSize,
            boolean focusAlpha,
            String targetDir
    ) {
        this.ignoreSize = ignoreSize;
        this.focusAlpha = focusAlpha;
        this.targetDir = targetDir;
    }
}