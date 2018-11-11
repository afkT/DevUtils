package dev.utils.app;

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
                    for (String str : arys){
                        if (!TextUtils.isEmpty(str)){
                            try {
                                String[] datas = str.split("=");
                                if (datas.length == 2){
                                    if (datas[0].toLowerCase().equals("versionCode".toLowerCase())){
                                        return Integer.parseInt(datas[1]);
                                    }
                                }
                            } catch (Exception e){
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
            return "";
        }
        try {
            // 执行 shell
            ShellUtils.CommandResult result = ShellUtils.execCmd("dumpsys package " + packageName + " | grep version", true);
            if (result != null && result.result == SUCCESS){
                if (!TextUtils.isEmpty(result.successMsg)){
                    String[] arys = result.successMsg.split(SPACE_STR);
                    for (String str : arys){
                        if (!TextUtils.isEmpty(str)){
                            try {
                                String[] datas = str.split("=");
                                if (datas.length == 2){
                                    if (datas[0].toLowerCase().equals("versionName".toLowerCase())){
                                        return datas[1];
                                    }
                                }
                            } catch (Exception e){
                            }
                        }
                    }
                }
            }
        } catch (Exception e){
            LogPrintUtils.eTag(TAG, e, "getVersionName");
        }
        return "";
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
     * 获取当前显示的 Activity
     * @return
     */
    public static String getAppActivityToCurrent(){
        return getAppActivityToPack("Current", true);
    }

    /**
     * 获取当前显示的 Activity
     * @param split
     * @return
     */
    public static String getAppActivityToCurrent(boolean split){
        return getAppActivityToPack("Current", split);
    }

    /**
     * 获取已经启动的 App 当前页面 Activity
     * @param packageName
     * @return
     */
    public static String getAppActivityToPack(String packageName){
        return getAppActivityToPack(packageName, true);
    }

    /**
     * 获取已经启动的 App 当前页面 Activity
     * @param packageName
     * @param split 是否进行裁剪
     * @return
     */
    public static String getAppActivityToPack(String packageName, boolean split){
        if (TextUtils.isEmpty(packageName)){
            return "";
        }
        String cmd = "dumpsys window windows | grep '%s'";
        // 执行 shell
        ShellUtils.CommandResult result = ShellUtils.execCmd(String.format(cmd, packageName), true);
        if (result != null && result.result == SUCCESS){
            if (!TextUtils.isEmpty(result.successMsg)){
                if (split){
                    try {
                        String[] arys = result.successMsg.split("\\s");
                        for (String str : arys){
                            if (!TextUtils.isEmpty(str)){
                                try {
                                    // 处理 包名
                                    if (str.indexOf("/.") != -1){
                                        if (str.endsWith("}")){
                                            return str.substring(0, str.length() - 1);
                                        }
                                        // 获取包
                                        String pack = str.split("/.")[0];
                                        str = str.replace("/", "/" + pack);
                                        return str;
                                    } else if (str.endsWith("}")){ // 处理 Current
                                        if (str.indexOf("/") != -1){
                                            return str.substring(0, str.length() - 1);
                                        }
                                    } else if (str.endsWith("};")){ // 处理 Current
                                        if (str.indexOf("/") != -1){
                                            return str.substring(0, str.length() - 2);
                                        }
                                    }
                                } catch (Exception e){
                                }
                            }
                        }
                    } catch (Exception e){
                        LogPrintUtils.eTag(TAG, e, "getAppActivityToPack " + packageName);
                    }
                }
            }
        }
        return "";
    }

    /**
     * 获取对应包名应用启动页面 => android.intent.category.LAUNCHER (android.intent.action.MAIN)
     * @param packageName
     * @return
     */
    public static String getAppActivityToLauncher(String packageName){
        if (TextUtils.isEmpty(packageName)){
            return "";
        }
        String cmd = "dumpsys package %s";
        // 执行 shell
        ShellUtils.CommandResult result = ShellUtils.execCmd(String.format(cmd, packageName), true);
        if (result != null && result.result == SUCCESS){
            if (!TextUtils.isEmpty(result.successMsg)){
                String startStr = "android.intent.action.MAIN:";
                int start = result.successMsg.indexOf(startStr);
                int end = result.successMsg.indexOf("Action: \"android.intent.action.MAIN\"");
                // 防止都为null
                if (start != -1){
                    if (end != -1){
                        try {
                            // 获取数据
                            String data = result.successMsg.substring(start + startStr.length(), end);
                            // 进行拆分
                            String[] arys = data.split("\\s");
                            for (String str : arys){
                                if (!TextUtils.isEmpty(str)){
                                    try {
                                        // 属于包名/ 前缀的
                                        if (str.indexOf(packageName + "/") != -1){
                                            // 防止属于 包名/.xx.Main_Activity
                                            if (str.indexOf("/.") != -1){
                                                String pack = str.split("/.")[0];
                                                str = str.replace("/", "/" + pack);
                                            }
                                            return str;
                                        }
                                    } catch (Exception e){
                                    }
                                }
                            }
                        } catch (Exception e){
                            LogPrintUtils.eTag(TAG, e, "getAppActivityToLauncher " + packageName);
                        }
                    } else { // 表示找不到
                        // 获取数据
                        String data = result.successMsg.substring(start + startStr.length(), result.successMsg.length());
                        // 进行拆分
                        String[] arys = data.split(NEW_LINE_STR);
                        try {
                            for (String str : arys){
                                // 防止为null
                                if (!TextUtils.isEmpty(str)){
                                    // 存在包名才处理
                                    if (str.indexOf(packageName) != -1){
                                        String[] splitArys = str.split("\\s");
                                        for (String strData : splitArys){
                                            if (!TextUtils.isEmpty(strData)){
                                                // 属于包名/ 前缀的
                                                if (strData.indexOf(packageName + "/") != -1){
                                                    // 防止属于 包名/.xx.Main_Activity
                                                    if (strData.indexOf("/.") != -1){
                                                        String pack = str.split("/.")[0];
                                                        strData = strData.replace("/", "/" + pack);
                                                    }
                                                    return strData;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } catch (Exception e){
                            LogPrintUtils.eTag(TAG, e, "getAppActivityToLauncher " + packageName);
                        }
                    }
                }
            }
        }
        return "";
    }

    // ========
    // == am ==
    // ========

    /**
     * 跳转页面 Activity
     * @param packAddActivityName
     * @param closeActivity 关闭Activity所属的App进程后再启动Activity
     * @return
     */
    public static boolean startActivity(String packAddActivityName, boolean closeActivity){
        if (TextUtils.isEmpty(packAddActivityName)){
            return false;
        }
        try {
            // am start [options] <INTENT>
            String cmd = "am start %s";
            if (closeActivity){
                cmd = String.format(cmd, "-S " + packAddActivityName);
            } else {
                cmd = String.format(cmd, packAddActivityName);
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
