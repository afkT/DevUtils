package utils_use;

import java.io.File;

import dev.utils.app.SDCardUtils;

/**
 * detail: 配置页面
 * @author Ttt
 */
public final class Config {

    // 项目日志 Tag
    public static final String LOG_TAG = "DevUtils_Log";

    // = 项目信息 =

    // 项目名
    public static final String BASE_NAME = "DevUtils";
    // 缩写标识 - 小写
    public static final String BASE_NAME_SHORT = "dev";
    // 缩写标识 - 大写
    public static final String BASE_NAME_SHORT_CAP = "DEV";
    // Bugly 项目标示
    public static final String BUGLY_PRONAME_MARK = "DevUtils_Android";
    // SDCard 路径
    public static final String BASE_SDCARD_PATH = SDCardUtils.getSDCardPath();

    // = 本地 SDCard 数据 =

    // SDCard Pro 文件统一前缀 SDP_xxx

    // 统一文件夹
    public static final String SDP_PATH = BASE_SDCARD_PATH + File.separator + BASE_NAME + File.separator;

    // 临时存储
    public static final String SDP_TEMP_PATH = SDP_PATH + "Temp" + File.separator;

    // 本地 SDCard 资源缓存地址
    public static final String SDP_CACHE_PATH = SDP_PATH + "Cache" + File.separator;

    // 下载保存路径
    public static final String SDP_DOWN_PATH = SDP_PATH + BASE_NAME_SHORT_CAP + "Download" + File.separator;

    // 下载图片路径
    public static final String SDP_DOWN_IMAGE_PATH = SDP_DOWN_PATH + "Image" + File.separator;

    // 文本存储路径
    public static final String SDP_TEXT_PATH = SDP_DOWN_PATH + "Text" + File.separator;

}
