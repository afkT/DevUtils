package dev.environment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.lang.reflect.Method;

/**
 * detail: 内部工具类
 * @author Ttt
 */
public class Utils {

    // 包名
    static final String PACKAGE_NAME = "dev.environment";
    // 工具类文件名
    static final String ENVIRONMENT_FILE_NAME = "DevEnvironment";
    // 方法名
    static final String METHOD_RESET = "reset";
    static final String METHOD_IS_RELEASE = "isRelease";
    static final String METHOD_GET_MODULE_LIST = "getModuleList";
    static final String METHOD_GET_MODULE_ENVIRONMENTS_LIST = "getEnvironments";
    static final String METHOD_GET_ENVIRONMENTS_VALUE = "getValue";
    static final String METHOD_ONENVIRONMENT_CHANGED = "onEnvironmentChanged";
    static final String METHOD_ADD_ONENVIRONMENT_CHANGE_LISTENER = "addOnEnvironmentChangeListener";
    static final String METHOD_REMOVE_ONENVIRONMENT_CHANGE_LISTENER = "removeOnEnvironmentChangeListener";
    static final String METHOD_CLEAR_ONENVIRONMENT_CHANGE_LISTENER = "clearOnEnvironmentChangeListener";
    static final String METHOD_NOTIFY_ONENVIRONMENT_CHANGE_LISTENER = "notifyOnEnvironmentChangeListener";
    static final String METHOD_GET_STORAGE_DIR = "getStorageDir";
    static final String METHOD_DELETE_STORAGE_DIR = "deleteStorageDir";
    static final String METHOD_WRITE_STORAGE = "writeStorage";
    static final String METHOD_READ_STORAGE = "readStorage";
    // 变量相关
    static final String VAR_MODULE_PREFIX = "MODULE_";
    static final String VAR_ENVIRONMENT_PREFIX = "ENVIRONMENT_";
    static final String VAR_MODULELIST = "moduleList";
    static final String VAR_MODULE_LIST = "MODULE_LIST";
    static final String VAR_SELECT_ENVIRONMENT = "sSelect";
    static final String VAR_LISTENER_LIST = "LISTENER_LIST";
    static final String VAR_CONTEXT = "context";
    static final String VAR_MODULE = "module";
    static final String VAR_MODULE_NAME = "moduleName";
    static final String VAR_ENVIRONMENT = "environment";
    static final String VAR_OLD_ENVIRONMENT = "oldEnvironment";
    static final String VAR_NEW_ENVIRONMENT = "newEnvironment";
    static final String VAR_LISTENER = "listener";
    static final String VAR_NAME = "name";
    static final String VAR_VALUE = "value";
    static final String VAR_ALIAS = "alias";
    // 常量字符串
    static final String STR_MODULE = "Module";
    static final String STR_ENVIRONMENT = "Environment";
    static final String STR_ENVIRONMENT_VALUE = "EnvironmentValue";
    static final String STR_RELEASE_ENVIRONMENT = "ReleaseEnvironment";

    // callback
    protected static RestartCallBack sRestartCallBack;
    // DevEnvironment Class
    static Class<?> devEnvironmentClass;

    static {
        try {
            devEnvironmentClass = Class.forName(PACKAGE_NAME + "." + ENVIRONMENT_FILE_NAME);
        } catch (ClassNotFoundException e) {
        }
    }

    /**
     * 跳转 DevEnvironment Activity
     * @param context {@link Context}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean start(final Context context) {
        return start(context, null);
    }

    /**
     * 跳转 DevEnvironment Activity
     * @param context {@link Context}
     * @param restartCallBack {@link RestartCallBack}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean start(final Context context, final RestartCallBack restartCallBack) {
        if (context != null && !isRelease()) {
            try {
                Utils.sRestartCallBack = restartCallBack;
                Intent intent = new Intent(context, DevEnvironmentActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 是否使用 releaseAnnotationProcessor 构建
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isRelease() {
        try {
            Method isReleaseMethod = devEnvironmentClass.getMethod(METHOD_IS_RELEASE);
            return (boolean) isReleaseMethod.invoke(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
