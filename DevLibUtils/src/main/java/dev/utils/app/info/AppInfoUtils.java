package dev.utils.app.info;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;

import java.util.ArrayList;
import java.util.List;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: App 信息获取工具类
 * Created by Ttt
 */
public final class AppInfoUtils {

    private AppInfoUtils(){
    }

    // 日志Tag
    private static final String TAG = AppInfoUtils.class.getSimpleName();

    /**
     * 通过 apk路径 初始化 App 信息实体类
     * @param apkUri apk路径
     */
    public static AppInfoBean obtainUri(String apkUri){
        return AppInfoBean.obtainUri(apkUri);
    }

    /**
     * 通过包名 初始化 App 信息实体类
     * @param pckName 包名
     */
    public static AppInfoBean obtainPck(String pckName){
        return AppInfoBean.obtainPck(pckName);
    }

    /**
     * 初始化当前 App 信息实体类
     */
    public static AppInfoBean obtain(){
        return AppInfoBean.obtain();
    }

    // ==================
    // == 获取详细信息 ==
    // ==================

    /**
     * 获取 Apk 详细信息
     * @param apkUri
     * @return
     */
    public static ApkInfoItem getApkInfoItem(String apkUri){
        try {
            return ApkInfoItem.obtain(apkUri);
        } catch (Exception e){
            return null;
        }
    }

    /**
     * 获取 App 详细信息
     * @param pckName
     * @return
     */
    public static AppInfoItem getAppInfoItem(String pckName){
        try {
            return AppInfoItem.obtain(pckName);
        } catch (Exception e){
            return null;
        }
    }

    /**
     * 获取 App 详细信息
     * @return
     */
    public static AppInfoItem getAppInfoItem(){
        return getAppInfoItem(DevUtils.getContext().getPackageName());
    }

    // =

    /**
     * 获取全部 App 列表
     * @return 返回 App 信息实体类集合
     */
    public static List<AppInfoBean> getAppLists() {
        return getAppLists(AppInfoBean.AppType.ALL);
    }

    /**
     * 获取 App 列表
     * @param appType App 类型
     * @return 返回 App 信息实体类集合
     */
    public static List<AppInfoBean> getAppLists(AppInfoBean.AppType appType) {
        // App信息
        ArrayList<AppInfoBean> listApps = new ArrayList<>();
        // 防止为null
        if (appType != null) {
            // 管理应用程序包
            PackageManager pManager = DevUtils.getContext().getPackageManager();
            // 获取手机内所有应用
            List<PackageInfo> packlist = pManager.getInstalledPackages(0);
            // 判断是否属于添加全部
            if (appType == AppInfoBean.AppType.ALL){
                // 遍历 App 列表
                for (int i = 0, len = packlist.size(); i < len; i++) {
                    PackageInfo pInfo = packlist.get(i);
                    // 添加符合条件的 App 应用信息
                    listApps.add(new AppInfoBean(pInfo, pManager));
                }
            } else {
                // 遍历 App 列表
                for (int i = 0, len = packlist.size(); i < len; i++) {
                    PackageInfo pInfo = packlist.get(i);
                    // 获取 App 类型
                    AppInfoBean.AppType cAppType = AppInfoBean.getAppType(pInfo);
                    // 判断类型
                    if (appType == cAppType){
                        // 添加符合条件的 App 应用信息
                        listApps.add(new AppInfoBean(pInfo, pManager));
                    }
//                    // 判断类型
//                    switch (cAppType) {
//                        case USER:
//                            // 添加符合条件的 App 应用信息
//                            listApps.add(new AppInfoBean(pInfo, pManager));
//                            break;
//                        case SYSTEM:
//                            // 添加符合条件的 App 应用信息
//                            listApps.add(new AppInfoBean(pInfo, pManager));
//                            break;
//                    }
                }
            }
        }
        return listApps;
    }

    // =

    /**
     * 获取 APK 注册的权限
     * @param pckName
     * https://www.cnblogs.com/leaven/p/5485864.html
     */
    public static String [] getApkPermission(String pckName){
        try {
            PackageManager packageManager = DevUtils.getApplication().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(pckName, PackageManager.GET_PERMISSIONS);
            return packageInfo.requestedPermissions;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getUsesPermission");
        }
        return null;
    }

    /**
     * 打印 APK 注册的权限
     * @param pckName
     * https://www.cnblogs.com/leaven/p/5485864.html
     */
    public static void printApkPermission(String pckName){
        try {
            PackageManager packageManager = DevUtils.getApplication().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(pckName, PackageManager.GET_PERMISSIONS);
            String [] usesPermissionsArray = packageInfo.requestedPermissions;
            for (int i = 0; i < usesPermissionsArray.length; i++) {
                // 获取每个权限的名字,如:android.permission.INTERNET
                String usesPermissionName = usesPermissionsArray[i];
                LogPrintUtils.dTag(TAG, "usesPermissionName = " + usesPermissionName);

                // 通过usesPermissionName获取该权限的详细信息
                PermissionInfo permissionInfo = packageManager.getPermissionInfo(usesPermissionName, 0);

                // 获得该权限属于哪个权限组,如:网络通信
                PermissionGroupInfo permissionGroupInfo  =  packageManager.getPermissionGroupInfo(permissionInfo.group, 0);
                LogPrintUtils.dTag(TAG, "permissionGroup = " + permissionGroupInfo.loadLabel(packageManager).toString());

                // 获取该权限的标签信息,比如:完全的网络访问权限
                String permissionLabel = permissionInfo.loadLabel(packageManager).toString();
                LogPrintUtils.dTag(TAG, "permissionLabel = " + permissionLabel);

                // 获取该权限的详细描述信息,比如:允许该应用创建网络套接字和使用自定义网络协议
                // 浏览器和其他某些应用提供了向互联网发送数据的途径,因此应用无需该权限即可向互联网发送数据.
                String permissionDescription = permissionInfo.loadDescription(packageManager).toString();
                LogPrintUtils.dTag(TAG, "permissionDescription = " + permissionDescription);
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "printApkPermission");
        }
    }
}
