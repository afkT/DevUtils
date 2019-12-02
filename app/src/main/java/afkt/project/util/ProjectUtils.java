package afkt.project.util;

import afkt.project.base.config.PathConfig;
import dev.utils.common.FileUtils;

/**
 * detail: 项目工具类
 * @author Ttt
 */
public final class ProjectUtils {

    private ProjectUtils() {
    }

    /**
     * 初始化创建文件夹
     * @return {@code true} success, {@code false} fail
     */
    public static boolean createFolder() {
        // 默认创建的文件夹路径
        String[] paths = new String[]{
                // 临时存储
                PathConfig.SDP_TEMP_PATH,
                // 本地 SDCard 资源缓存地址
                PathConfig.SDP_CACHE_PATH,
                // 下载路径
                PathConfig.SDP_DOWN_PATH,
                // 图片路径
                PathConfig.SDP_DOWN_IMAGE_PATH,
                // 文本路径
                PathConfig.SDP_TEXT_PATH,
                // 错误日志路径
                PathConfig.SDP_ERROR_PATH
        };
        return FileUtils.createFolderByPaths(paths);
    }
}
