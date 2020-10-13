package dev.standard.catalog;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    public static final HashMap<String, String> sAndroidCatelogMap      = new HashMap<>();
    // Java 文件目录注释
    public static final HashMap<String, String> sJavaCatelogMap         = new HashMap<>();
    // DevUtils Lib 文件目录注释
    public static final HashMap<String, String> sDevUtilsCatelogMap     = new HashMap<>();
    // DevUtils Lib 忽略目录
    public static final List<String>            sDevUtilsIgnoreCatelogs = new ArrayList<>();

    static {

        // ===========
        // = Android =
        // ===========

        sAndroidCatelogMap.put("Android", "根目录");
        sAndroidCatelogMap.put(".360RePlugin", "Android 插件化开发 - 360 RePlugin 框架");
        sAndroidCatelogMap.put(".AndroidScreenMatch", "Android 屏幕适配生成对应的尺寸文件");
        sAndroidCatelogMap.put(".AndroidVideoClip", "Android 视频裁剪 (含裁剪 View)");
        sAndroidCatelogMap.put(".AppInfoPro", "AppInfoPro (APP 信息提取器)");
        sAndroidCatelogMap.put(".AutoLockScreenPro", "AutoLockScreenPro - 推送自动锁屏");
        sAndroidCatelogMap.put(".BuglyHotfix", "Android 热修复 - Bugly");
        sAndroidCatelogMap.put(".PlaySeekbar", "视频裁剪自定义 View");
        sAndroidCatelogMap.put(".PushHandlerPro", "Android 点击推送逻辑处理、页面跳转判断");
        sAndroidCatelogMap.put(".RecordVideo", "录制视频 View (拍照 + 视频)");
        sAndroidCatelogMap.put(".RecordView", "录制进步式 View");
        sAndroidCatelogMap.put(".SophixPro", "Android - 热修复 Sophix");

        // ========
        // = Java =
        // ========

        sJavaCatelogMap.put("Java", "根目录");
        sJavaCatelogMap.put(".VideoClip", "Java 实现 MP4 裁剪功能");

        // ================
        // = DevUtils Lib =
        // ================

        sDevUtilsCatelogMap.put("lib", "根目录");
        sDevUtilsCatelogMap.put(".DevApp", "Android 工具类库");
        sDevUtilsCatelogMap.put(".DevAssist", "封装逻辑代码, 实现多个快捷功能辅助类、以及 Engine 兼容框架等");
        sDevUtilsCatelogMap.put(".DevBase", "Base ( Activity、Fragment )、MVP、ViewBinding、ContentLayout 基类库");
        sDevUtilsCatelogMap.put(".DevBase2", "Base 基础代码 ( 非基类库 )");
        sDevUtilsCatelogMap.put(".DevJava", "Java 工具类库 ( 不依赖 android api )");
        sDevUtilsCatelogMap.put(".DevOther", "第三方库封装、以及部分特殊工具类等, 方便 copy 封装类使用");
        sDevUtilsCatelogMap.put(".DevStandard", "项目规范统一检测、生成替换等");
        sDevUtilsCatelogMap.put(".DevWidget", "自定义 View UI 库");
        sDevUtilsCatelogMap.put(".Environment", "环境配置切换库");
        sDevUtilsCatelogMap.put(".Environment.DevEnvironment", "环境切换可视化 UI 操作");
        sDevUtilsCatelogMap.put(".Environment.DevEnvironmentBase", "注解类、实体类、监听事件等通用基础");
        sDevUtilsCatelogMap.put(".Environment.DevEnvironmentCompiler", "Debug ( 打包 / 编译 ) 生成实现代码");
        sDevUtilsCatelogMap.put(".Environment.DevEnvironmentCompilerRelease", "Release ( 打包 / 编译 ) 生成实现代码");

        // ========================
        // = DevUtils Lib 忽略目录 =
        // ========================

        sDevUtilsIgnoreCatelogs.add("DevApp");
        sDevUtilsIgnoreCatelogs.add("DevAssist");
        sDevUtilsIgnoreCatelogs.add("DevBase");
        sDevUtilsIgnoreCatelogs.add("DevBase2");
        sDevUtilsIgnoreCatelogs.add("DevJava");
        sDevUtilsIgnoreCatelogs.add("DevOther");
        sDevUtilsIgnoreCatelogs.add("DevStandard");
        sDevUtilsIgnoreCatelogs.add("DevWidget");
    }
}