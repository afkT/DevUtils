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
        return FileUtils.createFolderByPaths(PathConfig.SDP_TEMP_PATH, PathConfig.SDP_CACHE_PATH,
                PathConfig.SDP_DOWN_PATH, PathConfig.SDP_DOWN_IMAGE_PATH,
                PathConfig.SDP_TEXT_PATH, PathConfig.SDP_ERROR_PATH);
    }
}
