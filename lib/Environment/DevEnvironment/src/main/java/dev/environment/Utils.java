package dev.environment;

import android.content.Context;
import android.content.Intent;

import java.lang.reflect.Method;
import java.util.List;

import dev.environment.bean.EnvironmentBean;
import dev.environment.bean.ModuleBean;
import dev.environment.log.LogUtils;

/**
 * detail: 内部工具类
 * @author Ttt
 */
class Utils {

    // 包名
    static final String PACKAGE_NAME           = "dev.environment";
    // 工具类文件名
    static final String ENVIRONMENT_FILE_NAME  = "DevEnvironment";
    // 方法名
    static final String METHOD_IS_RELEASE      = "isRelease";
    static final String METHOD_GET_MODULE_LIST = "getModuleList";
    // 常量字符串
    static final String STR_ENVIRONMENT        = "Environment";

    // callback
    protected static RestartCallback sCallback;
    // DevEnvironment Class
    static           Class<?>        devEnvironmentClass;

    static {
        try {
            devEnvironmentClass = Class.forName(PACKAGE_NAME + "." + ENVIRONMENT_FILE_NAME);
        } catch (ClassNotFoundException e) {
        }
    }

    // ===========
    // = 跳转方法 =
    // ===========

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
     * @param context  {@link Context}
     * @param callback {@link RestartCallback}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean start(
            final Context context,
            final RestartCallback callback
    ) {
        if (context != null && !isRelease()) {
            try {
                Utils.sCallback = callback;
                Intent intent = new Intent(context, DevEnvironmentActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                return true;
            } catch (Exception e) {
                LogUtils.printStackTrace(e);
            }
        }
        return false;
    }

    // ===========
    // = 反射方法 =
    // ===========

    /**
     * 是否使用 releaseAnnotationProcessor 构建
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isRelease() {
        try {
            Method isReleaseMethod = devEnvironmentClass.getMethod(METHOD_IS_RELEASE);
            return (boolean) isReleaseMethod.invoke(null);
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return true;
    }

    /**
     * 获取全部 ModuleBean 配置列表
     * @return List<ModuleBean>
     */
    public static List<ModuleBean> getModuleList() {
        try {
            Method getModuleListMethod = devEnvironmentClass.getMethod(METHOD_GET_MODULE_LIST);
            return (List<ModuleBean>) getModuleListMethod.invoke(null);
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return null;
    }

    /**
     * 获取 Module Selected Environment
     * @param context    {@link Context}
     * @param moduleName module Name
     * @return {@link EnvironmentBean}
     */
    public static EnvironmentBean getModuleEnvironment(
            final Context context,
            final String moduleName
    ) {
        try {
            String getModuleEnvironmentMethodName = "get" + moduleName + STR_ENVIRONMENT;
            Method getModuleEnvironmentMethod     = devEnvironmentClass.getMethod(getModuleEnvironmentMethodName, Context.class);
            return (EnvironmentBean) getModuleEnvironmentMethod.invoke(null, context);
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return null;
    }

    /**
     * 设置 Module Selected Environment
     * @param context        {@link Context}
     * @param newEnvironment environment bean
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setModuleEnvironment(
            final Context context,
            final EnvironmentBean newEnvironment
    ) {
        try {
            String moduleName                     = newEnvironment.getModule().getName();
            String setModuleEnvironmentMethodName = "set" + moduleName + STR_ENVIRONMENT;
            Method setModuleEnvironmentMethod = devEnvironmentClass.getMethod(setModuleEnvironmentMethodName,
                    Context.class, EnvironmentBean.class);
            return (boolean) setModuleEnvironmentMethod.invoke(null, context, newEnvironment);
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return false;
    }
}