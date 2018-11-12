package dev.utils.app;

import android.os.Build;
import android.text.TextUtils;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import dev.utils.LogPrintUtils;

/**
 * detail: ADB shell 工具类
 * Created by Ttt
 * hint:
 *
 * // Awesome Adb——一份超全超详细的 ADB 用法大全
 * https://github.com/mzlogin/awesome-adb
 *
 * // Process.waitFor()的返回值含义
 * https://blog.csdn.net/qq_35661171/article/details/79096786
 *
 * // adb shell input
 * https://blog.csdn.net/soslinken/article/details/49587497
 *
 * android 上发送adb 指令，不需要加 adb shell
 *
 * // https://www.imooc.com/qadetail/198264
 * grep 是 linux 下的命令, windows 用 findstr
 *
 * 开启 Thread 执行, 非主线程, 否则无响应并无效
 */
public final class ADBUtils {

    private ADBUtils(){
    }

    // 日志 TAG
    private static final String TAG = ADBUtils.class.getSimpleName();
    // 操作成功
    public static final int SUCCESS = 0;
    // 正则 - 空格
    private static final String SPACE_STR = "\\s";
    /** 换行字符串 */
    private static final String NEW_LINE_STR = System.getProperty("line.separator");

    /**
     * 判断设备是否 root
     * @return
     */
    public static boolean isDeviceRooted() {
        String su = "su";
        String[] locations = { "/system/bin/", "/system/xbin/", "/sbin/", "/system/sd/xbin/",
                "/system/bin/failsafe/", "/data/local/xbin/", "/data/local/bin/", "/data/local/" };
        for (String location : locations) {
            if (new File(location + su).exists()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 请求 Root 权限
     */
    public static void requestRoot(){
        ShellUtils.execCmd("exit", true);
    }

    /**
     * 判断 App 是否授权 Root 权限
     * @return
     */
    public static boolean isGrantedRoot(){
        ShellUtils.CommandResult result = ShellUtils.execCmd("exit", true);
        return result.result == SUCCESS;
    }

    /**
     * 获取 App 列表(包名)
     * @param type
     * @return
     * https://blog.csdn.net/henni_719/article/details/62222439
     */
    public static List<String> getAppList(String type){
        // adb shell pm list packages [options]
        String typeStr = TextUtils.isEmpty(type) ? "" : " " + type;
        // 执行 shell cmd
        ShellUtils.CommandResult result = ShellUtils.execCmd("pm list packages" + typeStr, false);
        if (result != null && result.result == SUCCESS){
            if (!TextUtils.isEmpty(result.successMsg)){
                try {
                    String[] arys = result.successMsg.split(NEW_LINE_STR);
                    return Arrays.asList(arys);
                } catch (Exception e){
                    LogPrintUtils.eTag(TAG, e, "getAppList type => " + typeStr);
                }
            }
        }
        return null;
    }

    /**
     * 获取 App 安装列表(包名)
     * @return
     */
    public static List<String> getInstallAppList(){
        return getAppList(null);
    }

    /**
     * 获取用户安装的应用列表(包名)
     * @return
     */
    public static List<String> getUserAppList(){
        return getAppList("-3");
    }

    /**
     * 获取系统应用列表(包名)
     * @return
     */
    public static List<String> getSystemAppList(){
        return getAppList("-s");
    }

    /**
     * 获取启用的应用列表(包名)
     * @return
     */
    public static List<String> getEnableAppList(){
        return getAppList("-e");
    }

    /**
     * 获取禁用的应用列表(包名)
     * @return
     */
    public static List<String> getDisableAppList(){
        return getAppList("-d");
    }

    /**
     * 获取包名包含字符串 xxx 的应用列表
     * @param strFilter
     * @return
     */
    public static List<String> getAppListToFilter(String strFilter){
        if (TextUtils.isEmpty(strFilter)){
            return null;
        }
        return getAppList("| grep " + strFilter.trim());
    }

    /**
     * 判断是否安装应用
     * @param packageName
     * @return
     */
    public static boolean isInstalledApp(String packageName){
        if (TextUtils.isEmpty(packageName)){
            return false;
        }
        // 执行 shell
        ShellUtils.CommandResult result = ShellUtils.execCmd("pm path " + packageName, false);
        if (result != null && result.result == SUCCESS){
            if (!TextUtils.isEmpty(result.successMsg)){
                return true;
            }
        }
        return false;
    }

    /**
     * 获取 App versionCode
     * @param packageName
     * @return
     */
    public static int getVersionCode(String packageName){
        if (TextUtils.isEmpty(packageName)){
            return 0;
        }
        try {
            // 执行 shell
            ShellUtils.CommandResult result = ShellUtils.execCmd("dumpsys package " + packageName + " | grep version", true);
            if (result != null && result.result == SUCCESS){
                if (!TextUtils.isEmpty(result.successMsg)){
                    String[] arys = result.successMsg.split(SPACE_STR);
                    for (String str : arys) {
                        if (!TextUtils.isEmpty(str)) {
                            try {
                                String[] datas = str.split("=");
                                if (datas.length == 2) {
                                    if (datas[0].toLowerCase().equals("versionCode".toLowerCase())) {
                                        return Integer.parseInt(datas[1]);
                                    }
                                }
                            } catch (Exception e) {
                            }
                        }
                    }
                }
            }
        } catch (Exception e){
            LogPrintUtils.eTag(TAG, e, "getVersionCode");
        }
        return 0;
    }

    /**
     * 获取 App versionName
     * @param packageName
     * @return
     */
    public static String getVersionName(String packageName){
        if (TextUtils.isEmpty(packageName)){
            return null;
        }
        try {
            // 执行 shell
            ShellUtils.CommandResult result = ShellUtils.execCmd("dumpsys package " + packageName + " | grep version", true);
            if (result != null && result.result == SUCCESS){
                if (!TextUtils.isEmpty(result.successMsg)){
                    String[] arys = result.successMsg.split(SPACE_STR);
                    for (String str : arys) {
                        if (!TextUtils.isEmpty(str)) {
                            try {
                                String[] datas = str.split("=");
                                if (datas.length == 2) {
                                    if (datas[0].toLowerCase().equals("versionName".toLowerCase())) {
                                        return datas[1];
                                    }
                                }
                            } catch (Exception e) {
                            }
                        }
                    }
                }
            }
        } catch (Exception e){
            LogPrintUtils.eTag(TAG, e, "getVersionName");
        }
        return null;
    }

    // ===========
    // == Input ==
    // ===========

    // = tap = 模拟touch屏幕的事件

    /**
     * 点击某个区域
     * @param x
     * @param y
     * @return
     */
    public static boolean tap(float x, float y){
        try {
            // input [touchscreen|touchpad|touchnavigation] tap <x> <y>
            // input [屏幕、触摸板、导航键] tap
            String cmd = "input touchscreen tap %s %s";
            // 执行 shell
            ShellUtils.CommandResult result = ShellUtils.execCmd(String.format(cmd, (int) x, (int) y), true);
            if (result != null && result.result == SUCCESS){
                return true;
            }
        } catch (Exception e){
            LogPrintUtils.eTag(TAG, e, "tap");
        }
        return false;
    }

    // = swipe = 滑动事件

    /**
     * 按压某个区域(点击)
     * @param x
     * @param y
     * @return
     */
    public static boolean swipeClick(float x, float y){
        return swipe(x, y, x, y, 100l);
    }

    /**
     * 按压某个区域 time 大于一定时间变成长按
     * @param x
     * @param y
     * @param time 按压时间
     * @return
     */
    public static boolean swipeClick(float x, float y, long time){
        return swipe(x, y, x, y, time);
    }

    /**
     * 滑动到某个区域
     * @param x
     * @param y
     * @param tX
     * @param tY
     * @param time 滑动时间(毫秒)
     * @return
     */
    public static boolean swipe(float x, float y, float tX, float tY, long time){
        try {
            // input [touchscreen|touchpad|touchnavigation] swipe <x1> <y1> <x2> <y2> [duration(ms)]
            String cmd = "input touchscreen swipe %s %s %s %s %s";
            // 执行 shell
            ShellUtils.CommandResult result = ShellUtils.execCmd(String.format(cmd, (int) x, (int) y, (int) tX, (int) tY, time), true);
            if (result != null && result.result == SUCCESS){
                return true;
            }
        } catch (Exception e){
            LogPrintUtils.eTag(TAG, e, "swipe");
        }
        return false;
    }

    // = text = 模拟输入

    /**
     * 输入文本 - 不支持中文
     * @param txt
     * @return
     */
    public static boolean text(String txt){
        if (TextUtils.isEmpty(txt)){
            return false;
        }
        try {
            // input text <string>
            String cmd = "input text %s";
            // 执行 shell
            ShellUtils.CommandResult result = ShellUtils.execCmd(String.format(cmd, txt), true); // false 可以执行
            if (result != null && result.result == SUCCESS){
                return true;
            }
        } catch (Exception e){
            LogPrintUtils.eTag(TAG, e, "text");
        }
        return false;
    }

    // = keyevent = 按键操作

    /**
     * 触发某些按键
     * @param keyCode  KeyEvent.xxx => KeyEvent.KEYCODE_BACK(返回键)
     * @return
     */
    public static boolean keyevent(int keyCode){
        try {
            // input keyevent <key code number or name>
            String cmd = "input keyevent %s";
            // 执行 shell
            ShellUtils.CommandResult result = ShellUtils.execCmd(String.format(cmd, keyCode), true); // false 可以执行
            if (result != null && result.result == SUCCESS){
                return true;
            }
        } catch (Exception e){
            LogPrintUtils.eTag(TAG, e, "keyevent");
        }
        return false;
    }

    // =============
    // == dumpsys ==
    // =============

    /**
     * 获取对应包名应用启动 Activity
     * android.intent.category.LAUNCHER (android.intent.action.MAIN)
     * @param packageName
     * @return
     */
    public static String getActivityToLauncher(String packageName){
        if (TextUtils.isEmpty(packageName)){
            return null;
        }
        String cmd = "dumpsys package %s";
        // 执行 shell
        ShellUtils.CommandResult result = ShellUtils.execCmd(String.format(cmd, packageName), true);
        if (result != null && result.result == SUCCESS){
            if (!TextUtils.isEmpty(result.successMsg)){
                String mainStr = "android.intent.action.MAIN:";
                int start = result.successMsg.indexOf(mainStr);
                // 防止都为null
                if (start != -1){
                    try {
                        // 进行裁减字符串
                        String subData = result.successMsg.substring(start + mainStr.length(), result.successMsg.length());
                        // 进行拆分
                        String[] arys = subData.split(NEW_LINE_STR);
                        for (String str : arys){
                            if (!TextUtils.isEmpty(str)){
                                // 存在包名才处理
                                if (str.indexOf(packageName) != -1){
                                    String[] splitArys = str.split(SPACE_STR);
                                    for (String strData : splitArys){
                                        if (!TextUtils.isEmpty(strData)){
                                            // 属于 包名/ 前缀的
                                            if (strData.indexOf(packageName + "/") != -1){
                                                // 防止属于 包名/.xx.Main_Activity
                                                if (strData.indexOf("/.") != -1){
                                                    // 包名/.xx.Main_Activity => 包名/包名.xx.Main_Activity
                                                    strData = strData.replace("/", "/" + packageName);
                                                }
                                                return strData;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e){
                        LogPrintUtils.eTag(TAG, e, "getActivityToLauncher " + packageName);
                    }
                }
            }
        }
        return null;
    }

    // == 获取当前Window ==

    /**
     * 获取当前显示的 Window
     * adb shell dumpsys window -h
     * @return
     */
    public static String getWindowCurrent(){
        String cmd = "dumpsys window w | grep \\/  |  grep name=";
        // 执行 shell
        ShellUtils.CommandResult result = ShellUtils.execCmd(cmd, true);
        if (result != null && result.result == SUCCESS){
            if (!TextUtils.isEmpty(result.successMsg)){
                try {
                    String nameStr = "name=";
                    String[] arys = result.successMsg.split(NEW_LINE_STR);
                    for (String str : arys){
                        if (!TextUtils.isEmpty(str)){
                            int start = str.indexOf(nameStr);
                            if (start != -1){
                                try {
                                    String subData = str.substring(start + nameStr.length());
                                    if (subData.indexOf(")") != -1){
                                        return subData.substring(0, subData.length() - 1);
                                    }
                                    return subData;
                                } catch (Exception e) {
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "getWindowCurrent");
                }
            }
        }
        return null;
    }

    /**
     * 获取当前显示的 Window
     * @return
     */
    public static String getWindowCurrent2(){
        String cmd = "dumpsys window windows | grep Current";
        // 执行 shell
        ShellUtils.CommandResult result = ShellUtils.execCmd(cmd, true);
        if (result != null && result.result == SUCCESS){
            if (!TextUtils.isEmpty(result.successMsg)){
                try {
                    // 拆分换行, 并循环
                    String[] arys = result.successMsg.split(NEW_LINE_STR);
                    for (String str : arys){
                        if (!TextUtils.isEmpty(str)){
                            String[] splitArys = str.split(SPACE_STR);
                            if (splitArys != null && splitArys.length != 0){
                                for (String splitStr : splitArys){
                                    if (!TextUtils.isEmpty(splitStr)){
                                        int start = splitStr.indexOf("/");
                                        int lastIndex = splitStr.lastIndexOf("}");
                                        if (start != -1 && lastIndex != -1){
                                            // 获取裁减数据
                                            String strData = splitStr.substring(0, lastIndex);
                                            // 防止属于 包名/.xx.Main_Activity
                                            if (strData.indexOf("/.") != -1){
                                                // 包名/.xx.Main_Activity => 包名/包名.xx.Main_Activity
                                                strData = strData.replace("/", "/" + splitStr.substring(0, start));
                                            }
                                            return strData;
                                        }
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "getWindowCurrent2");
                }
            }
        }
        return null;
    }

    /**
     * 获取对应包名 显示的 Window
     * @param packageName
     * @return
     */
    public static String getWindowCurrentToPackage(String packageName){
        if (TextUtils.isEmpty(packageName)){
            return null;
        }
        String cmd = "dumpsys window windows | grep %s";
        // 执行 shell
        ShellUtils.CommandResult result = ShellUtils.execCmd(String.format(cmd, packageName), true);
        if (result != null && result.result == SUCCESS) {
            if (!TextUtils.isEmpty(result.successMsg)) {
                try {
                    // 拆分换行, 并循环
                    String[] arys = result.successMsg.split(NEW_LINE_STR);
                    for (String str : arys){
                        if (!TextUtils.isEmpty(str)){
                            String[] splitArys = str.split(SPACE_STR);
                            if (splitArys != null && splitArys.length != 0){
                                for (String splitStr : splitArys){
                                    if (!TextUtils.isEmpty(splitStr)){
                                        int start = splitStr.indexOf("/");
                                        int lastIndex = splitStr.lastIndexOf("}");
                                        if (start != -1 && lastIndex != -1 && splitStr.indexOf(packageName) == 0){
                                            // 获取裁减数据
                                            String strData = splitStr.substring(0, lastIndex);
                                            // 防止属于 包名/.xx.Main_Activity
                                            if (strData.indexOf("/.") != -1){
                                                // 包名/.xx.Main_Activity => 包名/包名.xx.Main_Activity
                                                strData = strData.replace("/", "/" + packageName);
                                            }
                                            return strData;
                                        }
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "getWindowCurrentToPackage");
                }
            }
        }
        return null;
    }

    // == 获取当前Activity ==

    /**
     * 获取当前显示的 Activity
     * @return
     */
    public static String getActivityCurrent(){
        String cmd = "dumpsys activity activities | grep mFocusedActivity";
        if (Build.VERSION.SDK_INT >=  Build.VERSION_CODES.O){
            cmd = "dumpsys activity activities | grep mResumedActivity";
        }
        // 执行 shell
        ShellUtils.CommandResult result = ShellUtils.execCmd(cmd, true);
        if (result != null && result.result == SUCCESS) {
            if (!TextUtils.isEmpty(result.successMsg)) {
                try {
                    // 拆分换行, 并循环
                    String[] arys = result.successMsg.split(NEW_LINE_STR);
                    for (String str : arys){
                        if (!TextUtils.isEmpty(str)){
                            String[] splitArys = str.split(SPACE_STR);
                            if (splitArys != null && splitArys.length != 0){
                                for (String splitStr : splitArys){
                                    if (!TextUtils.isEmpty(splitStr)){
                                        int start = splitStr.indexOf("/");
                                        if (start != -1){
                                            // 获取裁减数据
                                            String strData = splitStr;
                                            // 防止属于 包名/.xx.Main_Activity
                                            if (strData.indexOf("/.") != -1){
                                                // 包名/.xx.Main_Activity => 包名/包名.xx.Main_Activity
                                                strData = strData.replace("/", "/" + splitStr.substring(0, start));
                                            }
                                            return strData;
                                        }
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "getActivityCurrent");
                }
            }
        }
        return null;
    }

    // ========
    // == am ==
    // ========

    /**
     * 跳转页面 Activity
     * @param packageAndLauncher
     * @param closeActivity 关闭Activity所属的App进程后再启动Activity
     * @return
     */
    public static boolean startActivity(String packageAndLauncher, boolean closeActivity){
        if (TextUtils.isEmpty(packageAndLauncher)){
            return false;
        }
        try {
            // am start [options] <INTENT>
            String cmd = "am start %s";
            if (closeActivity){
                cmd = String.format(cmd, "-S " + packageAndLauncher);
            } else {
                cmd = String.format(cmd, packageAndLauncher);
            }
            // 执行 shell
            ShellUtils.CommandResult result = ShellUtils.execCmd(cmd, true);
            if (result != null && result.result == SUCCESS){
                return true;
            }
        } catch (Exception e){
            LogPrintUtils.eTag(TAG, e, "startActivity");
        }
        return false;
    }

    /**
     * 销毁进程
     * @param packageName
     * @return
     */
    public static boolean kill(String packageName){
        if (TextUtils.isEmpty(packageName)){
            return false;
        }
        try {
            String cmd = "am force-stop %s";
            // 执行 shell
            ShellUtils.CommandResult result = ShellUtils.execCmd(String.format(cmd, packageName), true);
            if (result != null && result.result == SUCCESS){
                return true;
            }
        } catch (Exception e){
            LogPrintUtils.eTag(TAG, e, "kill");
        }
        return false;
    }
}
