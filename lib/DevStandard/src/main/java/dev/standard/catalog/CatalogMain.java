package dev.standard.catalog;

import java.util.List;
import java.util.Map;

import dev.utils.common.FileUtils;
import dev.utils.common.StringUtils;

/**
 * detail: 目录生成 Main 方法
 * @author Ttt
 */
final class CatalogMain {

    public static void main(String[] args) {
        // 生成 Android 汇总项目目录结构 - https://github.com/afkT/Android
        print(Config.ANDROID_LOCAL_PATH, Config.ANDROID_DIR_NAME, Config.sAndroidCatalogMap, null, 0);

        // 生成 Java 汇总项目目录结构 - https://github.com/afkT/Java
        print(Config.JAVA_LOCAL_PATH, Config.JAVA_DIR_NAME, Config.sJavaCatalogMap, null, 0);

        // 生成 DevUtils Lib 汇总项目目录结构 - https://github.com/afkT/DevUtils/blob/master/lib
        print(Config.DEV_UTILS_LOCAL_PATH, Config.DEV_UTILS_DIR_NAME, Config.sDevUtilsCatalogMap, Config.sDevUtilsIgnoreCatalogs, 1);
    }

    // =

    private static final String FORMAT = "\"%s\" not found";

    /**
     * 打印目录信息
     * @param path              文件路径
     * @param dirName           文件名
     * @param mapCatalog        对应目录注释
     * @param listIgnoreCatalog 忽略目录
     * @param layer             目录层级
     */
    private static void print(final String path, final String dirName, final Map<String, String> mapCatalog,
                              final List<String> listIgnoreCatalog, final int layer) {
        System.out.println(StringUtils.NEW_LINE_STR_X2);
        if (FileUtils.isFileExists(path)) {
            System.out.println(CatalogGenerate.generate(path, dirName, mapCatalog, listIgnoreCatalog, layer));
        } else {
            System.out.println(String.format(FORMAT, path));
        }
    }
}