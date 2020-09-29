package dev.utils.app;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.lang.reflect.Method;

import dev.DevUtils;
import dev.utils.LogPrintUtils;
import dev.utils.app.assist.ResourceAssist;
import dev.utils.app.info.ApkInfoItem;
import dev.utils.app.info.AppInfoBean;
import dev.utils.app.info.AppInfoUtils;
import dev.utils.common.FileUtils;

/**
 * detail: APK Resource 工具类
 * @author JiZhi-Error <a href="https://github.com/JiZhi-Error"/>
 * @author Ttt
 * <pre>
 *     从 APK 中读取 Resources ( 可实现换肤、组件化工具类 )
 * </pre>
 */
public final class ResourcePluginUtils {

    private ResourcePluginUtils() {
    }

    // 日志 TAG
    private static final String TAG = ResourcePluginUtils.class.getSimpleName();

    // Resources 辅助类
    private ResourceAssist mResourceAssist;
    // APK 文件路径
    private String         mAPKPath;
    // APK 信息 Item
    private ApkInfoItem    mApkInfoItem;

    // =======================
    // = invokeByPackageName =
    // =======================

    /**
     * 通过 packageName 获取 APK Resources
     * @param packageName 应用包名
     * @return {@link ResourcePluginUtils}
     */
    public static final ResourcePluginUtils invokeByPackageName(final String packageName) {
        return invokeByPackageName(packageName, DevUtils.getContext());
    }

    /**
     * 通过 packageName 获取 APK Resources
     * @param packageName 应用包名
     * @param context     {@link Context}
     * @return {@link ResourcePluginUtils}
     */
    public static final ResourcePluginUtils invokeByPackageName(final String packageName,
                                                                final Context context) {
        DisplayMetrics metrics = null;
        Configuration config = null;
        Resources resources = ResourceAssist.staticResources(context);
        if (resources != null) {
            metrics = resources.getDisplayMetrics();
            config = resources.getConfiguration();
        }
        return invokeByPackageName(packageName, metrics, config);
    }

    /**
     * 通过 packageName 获取 APK Resources
     * @param packageName 应用包名
     * @param metrics     {@link DisplayMetrics}
     * @param config      {@link Configuration}
     * @return {@link ResourcePluginUtils}
     */
    public static final ResourcePluginUtils invokeByPackageName(final String packageName,
                                                                final DisplayMetrics metrics,
                                                                final Configuration config) {
        AppInfoBean appInfoBean = AppInfoUtils.getAppInfoBean(packageName);
        String sourceDir = (appInfoBean != null) ? appInfoBean.getSourceDir() : null;
        return invokeByAPKPath(sourceDir, metrics, config);
    }

    // ===================
    // = invokeByAPKPath =
    // ===================

    /**
     * 通过 APK 文件获取 APK Resources
     * @param apkPath APK 文件路径
     * @return {@link ResourcePluginUtils}
     */
    public static final ResourcePluginUtils invokeByAPKPath(final String apkPath) {
        return invokeByAPKPath(apkPath, DevUtils.getContext());
    }


    /**
     * 通过 APK 文件获取 APK Resources
     * @param apkPath APK 文件路径
     * @param context {@link Context}
     * @return {@link ResourcePluginUtils}
     */
    public static final ResourcePluginUtils invokeByAPKPath(final String apkPath, final Context context) {
        DisplayMetrics metrics = null;
        Configuration config = null;
        Resources resources = ResourceAssist.staticResources(context);
        if (resources != null) {
            metrics = resources.getDisplayMetrics();
            config = resources.getConfiguration();
        }
        return invokeByAPKPath(apkPath, metrics, config);
    }

    /**
     * 通过 APK 文件获取 APK Resources
     * @param apkPath APK 文件路径
     * @param metrics {@link DisplayMetrics}
     * @param config  {@link Configuration}
     * @return {@link ResourcePluginUtils}
     */
    public static final ResourcePluginUtils invokeByAPKPath(final String apkPath, final DisplayMetrics metrics, final Configuration config) {
        ResourcePluginUtils utils = new ResourcePluginUtils();
        utils.mAPKPath = apkPath;
        // 文件存在才进行处理
        if (FileUtils.isFileExists(apkPath)) {
            try {
                AssetManager asset = AssetManager.class.newInstance();
                Method addAssetPath = asset.getClass().getMethod("addAssetPath", String.class);
                addAssetPath.invoke(asset, apkPath);
                Resources resources = new Resources(
                        asset, metrics, config
                );
                PackageInfo packageInfo = AppUtils.getPackageManager().getPackageArchiveInfo(
                        apkPath, PackageManager.GET_ACTIVITIES
                );
                utils.mResourceAssist = ResourceAssist.get(
                        resources, packageInfo.packageName
                );
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "invokeByAPKPath - apkPath: " + apkPath);
            }
        }
        return utils;
    }

    // ===============
    // = 对外公开方法 =
    // ===============

    /**
     * 获取 Resources
     * @return {@link Resources}
     */
    public Resources getResources() {
        return mResourceAssist != null ? mResourceAssist.getResources() : null;
    }

    /**
     * 获取 APK 包名
     * @return APK 包名
     */
    public String getPackageName() {
        return mResourceAssist != null ? mResourceAssist.getPackageName() : null;
    }

    /**
     * 获取 APK 文件路径
     * @return APK 文件路径
     */
    public String getAPKPath() {
        return mAPKPath;
    }

    /**
     * 获取 APK 信息 Item
     * @return {@link ApkInfoItem}
     */
    public ApkInfoItem getApkInfoItem() {
        if (mApkInfoItem == null) {
            mApkInfoItem = AppInfoUtils.getApkInfoItem(mAPKPath);
        }
        return mApkInfoItem;
    }
}