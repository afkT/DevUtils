package dev.standard.catalog;

import java.util.HashMap;

import dev.utils.common.FileUtils;
import dev.utils.common.StringUtils;

/**
 * detail: 目录生成 Main 方法
 * @author Ttt
 */
final class CatalogMain {

    public static void main(String[] args) {
        // 生成 Android 汇总项目目录结构
        print(Config.ANDROID_LOCAL_PATH, Config.ANDROID_PACKAGE, Config.sAndroidCatelogMap);

        // 生成 Java 汇总项目目录结构
        print(Config.JAVA_LOCAL_PATH, Config.JAVA_PACKAGE, Config.sJavaCatelogMap);

        // 生成 DevUtils Lib 汇总项目目录结构
        print(Config.DEV_UTILS_LOCAL_PATH, Config.DEV_UTILS_PACKAGE, Config.sDevUtilsCatelogMap);
    }

    // =

    private static final String FORMAT = "\"%s\" not found";

    /**
     * 打印目录信息
     * @param path        文件路径
     * @param packageName 包名
     * @param mapCatelog  对应目录注释
     */
    private static void print(final String path, final String packageName, final HashMap<String, String> mapCatelog) {
        System.out.println(StringUtils.NEW_LINE_STR_X2);
        if (FileUtils.isFileExists(path)) {
            // 生成汇总项目目录结构
            String devUtilsCatelog = PackageCatalog.apiCatalog(false, path, packageName, mapCatelog);
            System.out.println(devUtilsCatelog);
        } else {
            System.out.println(String.format(FORMAT, path));
        }
    }
}
