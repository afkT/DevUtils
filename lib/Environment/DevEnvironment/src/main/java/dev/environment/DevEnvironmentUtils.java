package dev.environment;

import android.content.Context;
import android.content.Intent;

import java.util.List;

import dev.environment.bean.EnvironmentBean;
import dev.environment.bean.ModuleBean;
import dev.environment.log.LogUtils;

/**
 * detail: DevEnvironment 可视化工具类
 * @author Ttt
 * <pre>
 *     GitHub
 *     @see <a href="https://github.com/afkT/DevUtils"/>
 *     DevApp Api
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevApp/README.md"/>
 *     DevAssist Api
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/README.md"/>
 *     DevBase README
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevBase/README.md"/>
 *     DevBaseMVVM README
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevBaseMVVM/README.md"/>
 *     DevMVVM README
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevMVVM/README.md"/>
 *     DevEngine README
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevEngine/README.md"/>
 *     DevHttpCapture Api
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevHttpCapture/README.md"/>
 *     DevHttpManager Api
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevHttpManager/README.md"/>
 *     DevRetrofit Api
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevRetrofit/README.md"/>
 *     DevWidget Api
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/README.md"/>
 *     DevEnvironment Api
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/Environment"/>
 *     DevJava Api
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevJava/README.md"/>
 * </pre>
 */
public final class DevEnvironmentUtils {

    private DevEnvironmentUtils() {
    }

    // 日志 TAG
    private static final String TAG = DevEnvironmentUtils.class.getSimpleName();

    // callback
    static RestartCallback sCallback;

    // ============
    // = 工具类版本 =
    // ============

    /**
     * 获取 DevEnvironment 版本号
     * @return DevEnvironment versionCode
     */
    public static int getDevEnvironmentVersionCode() {
        return BuildConfig.DevEnvironment_VersionCode;
    }

    /**
     * 获取 DevEnvironment 版本
     * @return DevEnvironment versionName
     */
    public static String getDevEnvironmentVersion() {
        return BuildConfig.DevEnvironment_Version;
    }

    // ==========
    // = 跳转方法 =
    // ==========

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
     * @param callback 重启按钮点击回调
     * @return {@code true} success, {@code false} fail
     */
    public static boolean start(
            final Context context,
            final RestartCallback callback
    ) {
        if (context != null && !isRelease()) {
            try {
                DevEnvironmentUtils.sCallback = callback;
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

    // ==========
    // = 公开方法 =
    // ==========

    /**
     * 是否使用 releaseAnnotationProcessor 构建
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isRelease() {
        return Utils.isRelease();
    }

    /**
     * 获取全部 ModuleBean 配置列表
     * @return List<ModuleBean>
     */
    public static List<ModuleBean> getModuleList() {
        return Utils.getModuleList();
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
        return Utils.getModuleEnvironment(context, moduleName);
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
        if (Utils.setModuleEnvironment(context, newEnvironment)) {
            AdapterItem.changeHashCode(newEnvironment);
            return true;
        }
        return false;
    }

    // ===============
    // = AdapterItem =
    // ===============

    /**
     * 获取环境配置 Item List
     * @param context {@link Context}
     * @return AdapterItem List
     */
    public static List<AdapterItem> getAdapterItems(final Context context) {
        return AdapterItem.getAdapterItems(context);
    }
}