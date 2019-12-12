package afkt.project.base.config;

import java.io.File;

import dev.utils.app.PathUtils;

/**
 * detail: 文件路径配置
 * @author Ttt
 */
public final class PathConfig {

    private PathConfig() {
    }

    // SDCard 路径
    public static final String BASE_SDCARD_PATH = PathUtils.getSDCard().getSDCardPath();

    // ==========
    // = SDCard =
    // ==========

    // SDCard Project 文件统一前缀 SDP_xxx

    // 统一文件夹
    public static final String SDP_PATH = BASE_SDCARD_PATH + File.separator + AppConfig.BASE_NAME + File.separator;

    // 临时存储
    public static final String SDP_TEMP_PATH = SDP_PATH + "Temp" + File.separator;

    // 本地 SDCard 资源缓存地址
    public static final String SDP_CACHE_PATH = SDP_PATH + "Cache" + File.separator;

    // 下载路径
    public static final String SDP_DOWN_PATH = SDP_PATH + "Download" + File.separator;

    // 图片路径
    public static final String SDP_DOWN_IMAGE_PATH = SDP_PATH + "Image" + File.separator;

    // 文本路径
    public static final String SDP_TEXT_PATH = SDP_PATH + "Text" + File.separator;

    // 错误日志路径
    public static final String SDP_ERROR_PATH = SDP_PATH + "Error" + File.separator;
}
