package dev.standard.catalog;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dev.utils.common.FileUtils;

/**
 * detail: 目录生成配置
 * @author Ttt
 */
final class Config {

    private Config() {
    }

    // 当前目录
    public static final String USER_DIR = System.getProperty("user.dir");
    // 根目录
    public static final String DIR      = FileUtils.getFile(USER_DIR).getParent();

    // ===========
    // = Android =
    // ===========

    // Android 文件名
    public static final String ANDROID_DIR_NAME   = "Android";
    // Android 项目本地路径
    public static final String ANDROID_LOCAL_PATH = DIR + File.separator + ANDROID_DIR_NAME;

    // ========
    // = Java =
    // ========

    // Java 文件名
    public static final String JAVA_DIR_NAME   = "Java";
    // Java 项目本地路径
    public static final String JAVA_LOCAL_PATH = DIR + File.separator + JAVA_DIR_NAME;

    // ================
    // = DevUtils Lib =
    // ================

    // DevUtils Lib 文件名
    public static final String DEV_UTILS_DIR_NAME   = "lib";
    // DevUtils Lib 项目本地路径
    public static final String DEV_UTILS_LOCAL_PATH = USER_DIR + File.separator + DEV_UTILS_DIR_NAME;

    // =======
    // = Map =
    // =======

    // Android 文件目录注释
    public static final Map<String, String> sAndroidCatalogMap      = new HashMap<>();
    // Java 文件目录注释
    public static final Map<String, String> sJavaCatalogMap         = new HashMap<>();
    // DevUtils Lib 文件目录注释
    public static final Map<String, String> sDevUtilsCatalogMap     = new HashMap<>();
    // DevUtils Lib 忽略目录
    public static final List<String>        sDevUtilsIgnoreCatalogs = new ArrayList<>();

    static {

        // ===========
        // = Android =
        // ===========

        sAndroidCatalogMap.put("Android", "根目录");
        sAndroidCatalogMap.put(".360RePlugin", "Android 插件化开发 - 360 RePlugin 框架");
        sAndroidCatalogMap.put(".AndroidScreenMatch", "Android 屏幕适配生成对应的尺寸文件");
        sAndroidCatalogMap.put(".AndroidVideoClip", "Android 视频裁剪 (含裁剪 View)");
        sAndroidCatalogMap.put(".AppInfoPro", "AppInfoPro (APP 信息提取器)");
        sAndroidCatalogMap.put(".AutoLockScreenPro", "AutoLockScreenPro - 推送自动锁屏");
        sAndroidCatalogMap.put(".BuglyHotfix", "Android 热修复 - Bugly");
        sAndroidCatalogMap.put(".PlaySeekbar", "视频裁剪自定义 View");
        sAndroidCatalogMap.put(".PushHandlerPro", "Android 点击推送逻辑处理、页面跳转判断");
        sAndroidCatalogMap.put(".RecordVideo", "录制视频 View (拍照 + 视频)");
        sAndroidCatalogMap.put(".RecordView", "录制进步式 View");
        sAndroidCatalogMap.put(".SophixPro", "Android - 热修复 Sophix");

        // ========
        // = Java =
        // ========

        sJavaCatalogMap.put("Java", "根目录");
        sJavaCatalogMap.put(".VideoClip", "Java 实现 MP4 裁剪功能");

        // ================
        // = DevUtils Lib =
        // ================

        sDevUtilsCatalogMap.put("lib", "根目录");
        sDevUtilsCatalogMap.put(".DevApp", "Android 工具类库");
        sDevUtilsCatalogMap.put(".DevAssist", "封装逻辑代码, 实现多个快捷功能辅助类、以及 Engine 兼容框架等");
        sDevUtilsCatalogMap.put(".DevBase", "Base ( Activity、Fragment )、MVP、ViewBinding、ContentLayout 基类库");
        sDevUtilsCatalogMap.put(".DevBase2", "Base 基础代码 ( 非基类库 )");
        sDevUtilsCatalogMap.put(".DevBaseMVVM", "MVVM ( ViewDataBinding + ViewModel ) 基类库");
        sDevUtilsCatalogMap.put(".DevJava", "Java 工具类库 ( 不依赖 android api )");
        sDevUtilsCatalogMap.put(".DevOther", "第三方库封装、以及部分特殊工具类等, 方便 copy 封装类使用");
        sDevUtilsCatalogMap.put(".DevStandard", "项目规范统一检测、生成替换等");
        sDevUtilsCatalogMap.put(".DevWidget", "自定义 View UI 库");
        sDevUtilsCatalogMap.put(".Environment", "环境配置切换库");
        sDevUtilsCatalogMap.put(".Environment.DevEnvironment", "环境切换可视化 UI 操作");
        sDevUtilsCatalogMap.put(".Environment.DevEnvironmentBase", "注解类、实体类、监听事件等通用基础");
        sDevUtilsCatalogMap.put(".Environment.DevEnvironmentCompiler", "Debug ( 打包 / 编译 ) 生成实现代码");
        sDevUtilsCatalogMap.put(".Environment.DevEnvironmentCompilerRelease", "Release ( 打包 / 编译 ) 生成实现代码");

        // ========================
        // = DevUtils Lib 忽略目录 =
        // ========================

        sDevUtilsIgnoreCatalogs.add("DevApp");
        sDevUtilsIgnoreCatalogs.add("DevAssist");
        sDevUtilsIgnoreCatalogs.add("DevBase");
        sDevUtilsIgnoreCatalogs.add("DevBase2");
        sDevUtilsIgnoreCatalogs.add("DevBaseMVVM");
        sDevUtilsIgnoreCatalogs.add("DevJava");
        sDevUtilsIgnoreCatalogs.add("DevOther");
        sDevUtilsIgnoreCatalogs.add("DevStandard");
        sDevUtilsIgnoreCatalogs.add("DevWidget");
    }
}