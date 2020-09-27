package dev.utils.app;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.lang.reflect.Method;

import dev.utils.LogPrintUtils;
import dev.utils.app.info.ApkInfoItem;
import dev.utils.app.info.AppInfoBean;
import dev.utils.app.info.AppInfoUtils;
import dev.utils.common.FileUtils;

/**
 * detail: APK Resource 工具类
 * @author JiZhi-Error <a href="https://github.com/JiZhi-Error"/>
 * @author Ttt
 */
public final class ResourcePluginUtils {

    private ResourcePluginUtils() {
    }

    // 日志 TAG
    private static final String TAG = ResourcePluginUtils.class.getSimpleName();

    // APK Resources
    private Resources   mResources;
    // 包名
    private String      mPackageName;
    // APK 文件路径
    private String      mAPKPath;
    // APK 信息 Item
    private ApkInfoItem mApkInfoItem;

    // ==========
    // = invoke =
    // ==========

    /**
     * 通过 packageName 获取 APK Resources
     * @param packageName 应用包名
     * @return {@link ResourcePluginUtils}
     */
    public static final ResourcePluginUtils invokeByPackageName(final String packageName) {
        AppInfoBean appInfoBean = AppInfoUtils.getAppInfoBean(packageName);
        String sourceDir = (appInfoBean != null) ? appInfoBean.getSourceDir() : null;
        return invokeByAPKPath(sourceDir);
    }

    /**
     * 通过 APK 文件获取 APK Resources
     * @param apkPath APK 文件路径
     * @return {@link ResourcePluginUtils}
     */
    public static final ResourcePluginUtils invokeByAPKPath(final String apkPath) {
        ResourcePluginUtils utils = new ResourcePluginUtils();
        utils.mAPKPath = apkPath;
        // 文件存在才进行处理
        if (FileUtils.isFileExists(apkPath)) {
            try {
                AssetManager asset = AssetManager.class.newInstance();
                Method addAssetPath = asset.getClass().getMethod("addAssetPath", String.class);
                addAssetPath.invoke(asset, apkPath);
                Resources resources = new Resources(
                        asset,
                        ResourceUtils.getDisplayMetrics(),
                        ResourceUtils.getConfiguration()
                );
                PackageInfo packageInfo = AppUtils.getPackageManager().getPackageArchiveInfo(
                        apkPath, PackageManager.GET_ACTIVITIES
                );
                utils.mPackageName = packageInfo.packageName;
                utils.mResources = resources;
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
        return mResources;
    }

    /**
     * 获取 APK 包名
     * @return APK 包名
     */
    public String getPackageName() {
        return mPackageName;
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

    // =


}