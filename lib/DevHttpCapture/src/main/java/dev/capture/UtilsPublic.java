package dev.capture;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dev.utils.common.FileUtils;

public class UtilsPublic {

    private UtilsPublic() {
    }

    /**
     * 获取抓包存储路径
     * @return 抓包存储路径
     */
    public static String getStoragePath() {
        return Utils.getStoragePath();
    }

    /**
     * 获取指定模块抓包存储路径
     * @param moduleName 模块名 ( 要求唯一性 )
     * @return 指定模块抓包存储路径
     */
    public static String getModulePath(final String moduleName) {
        return Utils.getModulePath(moduleName);
    }

    /**
     * 获取全部模块名
     * @return 模块名集合
     */
    public static List<String> getAllModuleName() {
        List<String> lists = new ArrayList<>();
        File         root  = FileUtils.getFile(getStoragePath());
        if (FileUtils.isFileExists(root)) {
            File[] files = root.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file != null && file.isDirectory()) {
                        lists.add(file.getName());
                    }
                }
            }
        }
        return lists;
    }

    /**
     * 获取全部模块所有抓包数据
     * @param isEncrypt 是否加密数据
     * @return 全部模块所有抓包数据
     */
    public static Map<String, List<CaptureItem>> getAllModule(
            final boolean isEncrypt
    ) {
        return Utils.getAllModule(isEncrypt);
    }

    /**
     * 删除指定模块抓包数据
     * <pre>
     *     耗时操作需开启线程执行
     * </pre>
     * @param moduleName 模块名 ( 要求唯一性 )
     * @return {@code true} success, {@code false} fail
     */
    public static boolean deleteModule(final String moduleName) {
        return FileUtils.deleteAllInDir(getModulePath(moduleName));
    }

    /**
     * 删除全部模块抓包数据
     * <pre>
     *     耗时操作需开启线程执行
     * </pre>
     * @return {@code true} success, {@code false} fail
     */
    public static boolean deleteAllModule() {
        return FileUtils.deleteAllInDir(getStoragePath());
    }

    /**
     * 获取指定模块抓包文件大小
     * <pre>
     *     耗时操作需开启线程执行
     * </pre>
     * @param moduleName 模块名 ( 要求唯一性 )
     * @return 指定模块抓包文件大小
     */
    public static String getModuleFileSize(final String moduleName) {
        return FileUtils.getDirSize(getModulePath(moduleName));
    }

    /**
     * 获取全部模块抓包文件大小
     * <pre>
     *     耗时操作需开启线程执行
     * </pre>
     * @return 全部模块抓包文件大小
     */
    public static String getAllModuleFileSize() {
        return FileUtils.getDirSize(getStoragePath());
    }

    /**
     * 获取指定模块抓包文件大小
     * <pre>
     *     耗时操作需开启线程执行
     * </pre>
     * @param moduleName 模块名 ( 要求唯一性 )
     * @return 指定模块抓包文件大小
     */
    public static long getModuleFileLength(final String moduleName) {
        return FileUtils.getDirLength(getModulePath(moduleName));
    }

    /**
     * 获取全部模块抓包文件大小
     * <pre>
     *     耗时操作需开启线程执行
     * </pre>
     * @return 全部模块抓包文件大小
     */
    public static long getAllModuleFileLength() {
        return FileUtils.getDirLength(getStoragePath());
    }
}