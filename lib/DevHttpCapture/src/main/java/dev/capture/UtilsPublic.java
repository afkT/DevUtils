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
}