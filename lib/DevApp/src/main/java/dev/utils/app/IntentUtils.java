package dev.utils.app;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.core.content.FileProvider;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dev.DevUtils;
import dev.utils.LogPrintUtils;
import dev.utils.common.FileUtils;

/**
 * detail: Intent 相关工具类
 * @author Ttt
 * <pre>
 *     所需权限
 *     <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />
 *     <uses-permission android:name="android.permission.SHUTDOWN" />
 *     <uses-permission android:name="android.permission.CALL_PHONE" />
 *     <uses-permission android:name="android.permission.MANAGE_EXTERNAL_STORAGE" />
 * </pre>
 */
public final class IntentUtils {

    private IntentUtils() {
    }

    // 日志 TAG
    private static final String TAG = IntentUtils.class.getSimpleName();

    /**
     * 获取 Intent
     * @param intent    {@link Intent}
     * @param isNewTask 是否开启新的任务栈 (Context 非 Activity 则需要设置 FLAG_ACTIVITY_NEW_TASK)
     * @return {@link Intent}
     */
    public static Intent getIntent(
            final Intent intent,
            final boolean isNewTask
    ) {
        if (intent != null) {
            return isNewTask ? intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) : intent;
        }
        return null;
    }

    /**
     * 判断 Intent 是否可用
     * @param intent {@link Intent}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isIntentAvailable(final Intent intent) {
        if (intent != null) {
            try {
                return AppUtils.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY).size() > 0;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "isIntentAvailable");
            }
        }
        return false;
    }

    /**
     * 获取 CATEGORY_LAUNCHER Intent
     * @param className class.getCanonicalName()
     * @return {@link Intent}
     */
    public static Intent getCategoryLauncherIntent(final String className) {
        return getCategoryLauncherIntent(AppUtils.getPackageName(), className, true);
    }

    /**
     * 获取 CATEGORY_LAUNCHER Intent
     * @param className class.getCanonicalName()
     * @param isNewTask 是否开启新的任务栈
     * @return {@link Intent}
     */
    public static Intent getCategoryLauncherIntent(
            final String className,
            final boolean isNewTask
    ) {
        return getCategoryLauncherIntent(AppUtils.getPackageName(), className, isNewTask);
    }

    /**
     * 获取 CATEGORY_LAUNCHER Intent
     * @param packageName 应用包名
     * @param className   class.getCanonicalName()
     * @return {@link Intent}
     */
    public static Intent getCategoryLauncherIntent(
            final String packageName,
            final String className
    ) {
        return getCategoryLauncherIntent(packageName, className, true);
    }

    /**
     * 获取 CATEGORY_LAUNCHER Intent
     * @param packageName 应用包名
     * @param className   class.getCanonicalName()
     * @param isNewTask   是否开启新的任务栈
     * @return {@link Intent}
     */
    public static Intent getCategoryLauncherIntent(
            final String packageName,
            final String className,
            final boolean isNewTask
    ) {
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.setComponent(new ComponentName(packageName, className));
            if (isNewTask) {
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            }
            return intent;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getCategoryLauncherIntent");
        }
        return null;
    }

    /**
     * 获取安装 APP( 支持 8.0) 的意图
     * @param filePath 文件路径
     * @return 安装 APP( 支持 8.0) 的意图
     */
    public static Intent getInstallAppIntent(final String filePath) {
        return getInstallAppIntent(FileUtils.getFileByPath(filePath));
    }

    /**
     * 获取安装 APP( 支持 8.0) 的意图
     * @param file 文件
     * @return 安装 APP( 支持 8.0) 的意图
     */
    public static Intent getInstallAppIntent(final File file) {
        return getInstallAppIntent(file, false);
    }

    /**
     * 获取安装 APP( 支持 8.0) 的意图
     * @param file      文件
     * @param isNewTask 是否开启新的任务栈
     * @return 安装 APP( 支持 8.0) 的意图
     */
    public static Intent getInstallAppIntent(
            final File file,
            final boolean isNewTask
    ) {
        if (file == null) return null;
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri    data;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                data = Uri.fromFile(file);
            } else {
                data = FileProvider.getUriForFile(DevUtils.getContext(), DevUtils.getAuthority(), file);
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            intent.setDataAndType(data, "application/vnd.android.package-archive");
            return getIntent(intent, isNewTask);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getInstallAppIntent");
        }
        return null;
    }

    /**
     * 获取卸载 APP 的意图
     * @param packageName 应用包名
     * @return 卸载 APP 的意图
     */
    public static Intent getUninstallAppIntent(final String packageName) {
        return getUninstallAppIntent(packageName, false);
    }

    /**
     * 获取卸载 APP 的意图
     * @param packageName 应用包名
     * @param isNewTask   是否开启新的任务栈
     * @return 卸载 APP 的意图
     */
    public static Intent getUninstallAppIntent(
            final String packageName,
            final boolean isNewTask
    ) {
        try {
            Intent intent = new Intent(Intent.ACTION_DELETE);
            intent.setData(Uri.parse("package:" + packageName));
            return getIntent(intent, isNewTask);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getUninstallAppIntent");
        }
        return null;
    }

    /**
     * 获取打开 APP 的意图
     * @param packageName 应用包名
     * @return 打开 APP 的意图
     */
    public static Intent getLaunchAppIntent(final String packageName) {
        return getLaunchAppIntent(packageName, false);
    }

    /**
     * 获取打开 APP 的意图
     * @param packageName 应用包名
     * @param isNewTask   是否开启新的任务栈
     * @return 打开 APP 的意图
     */
    public static Intent getLaunchAppIntent(
            final String packageName,
            final boolean isNewTask
    ) {
        try {
            Intent intent = AppUtils.getPackageManager().getLaunchIntentForPackage(packageName);
            return getIntent(intent, isNewTask);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getLaunchAppIntent");
        }
        return null;
    }

    /**
     * 获取跳转到系统设置的意图
     * @param isNewTask 是否开启新的任务栈
     * @return 跳转到系统设置的意图
     */
    public static Intent getSystemSettingIntent(final boolean isNewTask) {
        try {
            Intent intent = new Intent(Settings.ACTION_SETTINGS);
            return getIntent(intent, isNewTask);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getSystemSettingIntent");
        }
        return null;
    }

    /**
     * 获取 APP 安装权限设置的意图
     * @return APP 安装权限设置的意图
     */
    public static Intent getLaunchAppInstallPermissionSettingsIntent() {
        return getLaunchAppInstallPermissionSettingsIntent(AppUtils.getPackageName(), false);
    }

    /**
     * 获取 APP 安装权限设置的意图
     * @param packageName 应用包名
     * @return APP 安装权限设置的意图
     */
    public static Intent getLaunchAppInstallPermissionSettingsIntent(final String packageName) {
        return getLaunchAppInstallPermissionSettingsIntent(packageName, false);
    }

    /**
     * 获取 APP 安装权限设置的意图
     * @param packageName 应用包名
     * @param isNewTask   是否开启新的任务栈
     * @return APP 安装权限设置的意图
     */
    public static Intent getLaunchAppInstallPermissionSettingsIntent(
            final String packageName,
            final boolean isNewTask
    ) {
        try {
            Uri    uri    = Uri.parse("package:" + packageName);
            Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, uri);
            return getIntent(intent, isNewTask);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getLaunchAppInstallPermissionSettingsIntent");
        }
        return null;
    }

    /**
     * 获取 APP 通知权限设置的意图
     * @return APP 通知权限设置的意图
     */
    public static Intent getLaunchAppNotificationSettingsIntent() {
        return getLaunchAppNotificationSettingsIntent(AppUtils.getPackageName(), false);
    }

    /**
     * 获取 APP 通知权限设置的意图
     * @param packageName 应用包名
     * @return APP 通知权限设置的意图
     */
    public static Intent getLaunchAppNotificationSettingsIntent(final String packageName) {
        return getLaunchAppNotificationSettingsIntent(packageName, false);
    }

    /**
     * 获取 APP 通知权限设置的意图
     * @param packageName 应用包名
     * @param isNewTask   是否开启新的任务栈
     * @return APP 通知权限设置的意图
     */
    public static Intent getLaunchAppNotificationSettingsIntent(
            final String packageName,
            final boolean isNewTask
    ) {
        try {
            ApplicationInfo applicationInfo = AppUtils.getPackageInfo(packageName, 0).applicationInfo;
            Intent          intent          = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            // 这种方案适用于 API 26 即 8.0 ( 含 8.0) 以上可以用
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, packageName);
            intent.putExtra(Settings.EXTRA_CHANNEL_ID, applicationInfo.uid);
            // 这种方案适用于 API 21 - 25 即 5.0 - 7.1 之间的版本可以使用
            intent.putExtra("app_package", packageName);
            intent.putExtra("app_uid", applicationInfo.uid);
            return getIntent(intent, isNewTask);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getLaunchAppNotificationSettingsIntent");
        }
        return null;
    }

    /**
     * 获取 APP 通知使用权页面
     * @return APP 通知使用权页面
     */
    public static Intent getLaunchAppNotificationListenSettingsIntent() {
        return getLaunchAppNotificationListenSettingsIntent(false);
    }

    /**
     * 获取 APP 通知使用权页面
     * @param isNewTask 是否开启新的任务栈
     * @return APP 通知使用权页面
     */
    public static Intent getLaunchAppNotificationListenSettingsIntent(final boolean isNewTask) {
        Intent intent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            intent = new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
        } else {
            intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
        }
        return getIntent(intent, isNewTask);
    }

    /**
     * 获取悬浮窗口权限列表的意图
     * @return 悬浮窗口权限列表的意图
     */
    public static Intent getManageOverlayPermissionIntent() {
        return getManageOverlayPermissionIntent(false);
    }

    /**
     * 获取悬浮窗口权限列表的意图
     * @param isNewTask 是否开启新的任务栈
     * @return 悬浮窗口权限列表的意图
     */
    public static Intent getManageOverlayPermissionIntent(final boolean isNewTask) {
        try {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            return getIntent(intent, isNewTask);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getManageOverlayPermissionIntent");
        }
        return null;
    }

    /**
     * 获取 APP 授予所有文件管理权限的意图
     * @return APP 授予所有文件管理权限的意图
     */
    public static Intent getManageAppAllFilesAccessPermissionIntent() {
        return getManageAppAllFilesAccessPermissionIntent(AppUtils.getPackageName(), false);
    }

    /**
     * 获取 APP 授予所有文件管理权限的意图
     * @param packageName 应用包名
     * @return APP 授予所有文件管理权限的意图
     */
    public static Intent getManageAppAllFilesAccessPermissionIntent(final String packageName) {
        return getManageAppAllFilesAccessPermissionIntent(packageName, false);
    }

    /**
     * 获取 APP 授予所有文件管理权限的意图
     * @param packageName 应用包名
     * @param isNewTask   是否开启新的任务栈
     * @return APP 授予所有文件管理权限的意图
     */
    public static Intent getManageAppAllFilesAccessPermissionIntent(
            final String packageName,
            final boolean isNewTask
    ) {
        try {
            Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
            intent.setData(Uri.parse("package:" + packageName));
            return getIntent(intent, isNewTask);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getManageAppAllFilesAccessPermissionIntent");
        }
        return null;
    }

    /**
     * 获取授予所有文件管理权限列表的意图
     * @return 授予所有文件管理权限列表的意图
     */
    public static Intent getManageAllFilesAccessPermissionIntent() {
        return getManageAllFilesAccessPermissionIntent(false);
    }

    /**
     * 获取授予所有文件管理权限列表的意图
     * @param isNewTask 是否开启新的任务栈
     * @return 授予所有文件管理权限列表的意图
     */
    public static Intent getManageAllFilesAccessPermissionIntent(final boolean isNewTask) {
        try {
            Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
            return getIntent(intent, isNewTask);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getManageAllFilesAccessPermissionIntent");
        }
        return null;
    }

    /**
     * 获取 APP 具体设置的意图
     * @return APP 具体设置的意图
     */
    public static Intent getLaunchAppDetailsSettingsIntent() {
        return getLaunchAppDetailsSettingsIntent(AppUtils.getPackageName(), false);
    }

    /**
     * 获取 APP 具体设置的意图
     * @param packageName 应用包名
     * @return APP 具体设置的意图
     */
    public static Intent getLaunchAppDetailsSettingsIntent(final String packageName) {
        return getLaunchAppDetailsSettingsIntent(packageName, false);
    }

    /**
     * 获取 APP 具体设置的意图
     * @param packageName 应用包名
     * @param isNewTask   是否开启新的任务栈
     * @return APP 具体设置的意图
     */
    public static Intent getLaunchAppDetailsSettingsIntent(
            final String packageName,
            final boolean isNewTask
    ) {
        try {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + packageName));
            return getIntent(intent, isNewTask);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getLaunchAppDetailsSettingsIntent");
        }
        return null;
    }

    /**
     * 获取到应用商店 APP 详情界面的意图
     * @param packageName 应用包名
     * @param marketPkg   应用商店包名, 如果为 ""  则由系统弹出应用商店列表供用户选择, 否则调转到目标市场的应用详情界面, 某些应用商店可能会失败
     * @return 到应用商店 APP 详情界面的意图
     */
    public static Intent getLaunchAppDetailIntent(
            final String packageName,
            final String marketPkg
    ) {
        return getLaunchAppDetailIntent(packageName, marketPkg, false);
    }

    /**
     * 获取到应用商店 APP 详情界面的意图
     * @param packageName 应用包名
     * @param marketPkg   应用商店包名, 如果为 ""  则由系统弹出应用商店列表供用户选择, 否则调转到目标市场的应用详情界面, 某些应用商店可能会失败
     * @param isNewTask   是否开启新的任务栈
     * @return 到应用商店 APP 详情界面的意图
     */
    public static Intent getLaunchAppDetailIntent(
            final String packageName,
            final String marketPkg,
            final boolean isNewTask
    ) {
        try {
            if (TextUtils.isEmpty(packageName)) return null;

            Uri    uri    = Uri.parse("market://details?id=" + packageName);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            if (!TextUtils.isEmpty(marketPkg)) {
                intent.setPackage(marketPkg);
            }
            return getIntent(intent, isNewTask);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getLaunchAppDetailIntent");
        }
        return null;
    }

    // =

    /**
     * 获取分享文本的意图
     * @param content 分享文本
     * @return 分享文本的意图
     */
    public static Intent getShareTextIntent(final String content) {
        return getShareTextIntent(content, false);
    }

    /**
     * 获取分享文本的意图
     * @param content   分享文本
     * @param isNewTask 是否开启新的任务栈
     * @return 分享文本的意图
     */
    public static Intent getShareTextIntent(
            final String content,
            final boolean isNewTask
    ) {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, content);
            return getIntent(intent, isNewTask);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getShareTextIntent");
        }
        return null;
    }

    /**
     * 获取分享图片的意图
     * @param content   文本
     * @param imagePath 图片文件路径
     * @return 分享图片的意图
     */
    public static Intent getShareImageIntent(
            final String content,
            final String imagePath
    ) {
        return getShareImageIntent(content, imagePath, false);
    }

    /**
     * 获取分享图片的意图
     * @param content   文本
     * @param imagePath 图片文件路径
     * @param isNewTask 是否开启新的任务栈
     * @return 分享图片的意图
     */
    public static Intent getShareImageIntent(
            final String content,
            final String imagePath,
            final boolean isNewTask
    ) {
        try {
            return getShareImageIntent(content, FileUtils.getFileByPath(imagePath), isNewTask);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getShareImageIntent");
        }
        return null;
    }

    /**
     * 获取分享图片的意图
     * @param content 文本
     * @param image   图片文件
     * @return 分享图片的意图
     */
    public static Intent getShareImageIntent(
            final String content,
            final File image
    ) {
        return getShareImageIntent(content, image, false);
    }

    /**
     * 获取分享图片的意图
     * @param content   文本
     * @param image     图片文件
     * @param isNewTask 是否开启新的任务栈
     * @return 分享图片的意图
     */
    public static Intent getShareImageIntent(
            final String content,
            final File image,
            final boolean isNewTask
    ) {
        try {
            return getShareImageIntent(content, DevUtils.getUriForFile(image), isNewTask);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getShareImageIntent");
        }
        return null;
    }

    /**
     * 获取分享图片的意图
     * @param content 分享文本
     * @param uri     图片 uri
     * @return 分享图片的意图
     */
    public static Intent getShareImageIntent(
            final String content,
            final Uri uri
    ) {
        return getShareImageIntent(content, uri, false);
    }

    /**
     * 获取分享图片的意图
     * @param content   分享文本
     * @param uri       图片 uri
     * @param isNewTask 是否开启新的任务栈
     * @return 分享图片的意图
     */
    public static Intent getShareImageIntent(
            final String content,
            final Uri uri,
            final boolean isNewTask
    ) {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, content);
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            intent.setType("image/*");
            return getIntent(intent, isNewTask);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getShareImageIntent");
        }
        return null;
    }

    /**
     * 获取其他应用组件的意图
     * @param packageName 应用包名
     * @param className   class.getCanonicalName()
     * @return 其他应用组件的意图
     */
    public static Intent getComponentIntent(
            final String packageName,
            final String className
    ) {
        return getComponentIntent(packageName, className, null, false);
    }

    /**
     * 获取其他应用组件的意图
     * @param packageName 应用包名
     * @param className   class.getCanonicalName()
     * @param isNewTask   是否开启新的任务栈
     * @return 其他应用组件的意图
     */
    public static Intent getComponentIntent(
            final String packageName,
            final String className,
            final boolean isNewTask
    ) {
        return getComponentIntent(packageName, className, null, isNewTask);
    }

    /**
     * 获取其他应用组件的意图
     * @param packageName 应用包名
     * @param className   class.getCanonicalName()
     * @param bundle      {@link Bundle}
     * @return 其他应用组件的意图
     */
    public static Intent getComponentIntent(
            final String packageName,
            final String className,
            final Bundle bundle
    ) {
        return getComponentIntent(packageName, className, bundle, false);
    }

    /**
     * 获取其他应用组件的意图
     * @param packageName 应用包名
     * @param className   class.getCanonicalName()
     * @param bundle      {@link Bundle}
     * @param isNewTask   是否开启新的任务栈
     * @return 其他应用组件的意图
     */
    public static Intent getComponentIntent(
            final String packageName,
            final String className,
            final Bundle bundle,
            final boolean isNewTask
    ) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            if (bundle != null) intent.putExtras(bundle);
            ComponentName componentName = new ComponentName(packageName, className);
            intent.setComponent(componentName);
            return getIntent(intent, isNewTask);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getComponentIntent");
        }
        return null;
    }

    /**
     * 获取关机的意图
     * @return 关机的意图
     */
    public static Intent getShutdownIntent() {
        return getShutdownIntent(false);
    }

    /**
     * 获取关机的意图
     * @param isNewTask 是否开启新的任务栈
     * @return 关机的意图
     */
    public static Intent getShutdownIntent(final boolean isNewTask) {
        try {
            Intent intent = new Intent(Intent.ACTION_SHUTDOWN);
            return getIntent(intent, isNewTask);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getShutdownIntent");
        }
        return null;
    }

    /**
     * 获取跳至拨号界面意图
     * @param phoneNumber 电话号码
     * @return 跳至拨号界面意图
     */
    public static Intent getDialIntent(final String phoneNumber) {
        return getDialIntent(phoneNumber, false);
    }

    /**
     * 获取跳至拨号界面意图
     * @param phoneNumber 电话号码
     * @param isNewTask   是否开启新的任务栈
     * @return 跳至拨号界面意图
     */
    public static Intent getDialIntent(
            final String phoneNumber,
            final boolean isNewTask
    ) {
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
            return getIntent(intent, isNewTask);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getDialIntent");
        }
        return null;
    }

    /**
     * 获取拨打电话意图
     * @param phoneNumber 电话号码
     * @return 拨打电话意图
     */
    public static Intent getCallIntent(final String phoneNumber) {
        return getCallIntent(phoneNumber, false);
    }

    /**
     * 获取拨打电话意图
     * @param phoneNumber 电话号码
     * @param isNewTask   是否开启新的任务栈
     * @return 拨打电话意图
     */
    public static Intent getCallIntent(
            final String phoneNumber,
            final boolean isNewTask
    ) {
        try {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
            return getIntent(intent, isNewTask);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getCallIntent");
        }
        return null;
    }

    /**
     * 获取发送短信界面的意图
     * @param phoneNumber 接收号码
     * @param content     短信内容
     * @return 发送短信界面的意图
     */
    public static Intent getSendSmsIntent(
            final String phoneNumber,
            final String content
    ) {
        return getSendSmsIntent(phoneNumber, content, false);
    }

    /**
     * 获取跳至发送短信界面的意图
     * @param phoneNumber 接收号码
     * @param content     短信内容
     * @param isNewTask   是否开启新的任务栈
     * @return 发送短信界面的意图
     */
    public static Intent getSendSmsIntent(
            final String phoneNumber,
            final String content,
            final boolean isNewTask
    ) {
        try {
            Uri    uri    = Uri.parse("smsto:" + phoneNumber);
            Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
            intent.putExtra("sms_body", content);
            return getIntent(intent, isNewTask);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getSendSmsIntent");
        }
        return null;
    }

    /**
     * 获取图片拍摄的意图
     * @param outUri 输出的 uri ( 保存地址 )
     * @return 图片拍摄的意图
     */
    public static Intent getImageCaptureIntent(final Uri outUri) {
        return getImageCaptureIntent(outUri, false);
    }

    /**
     * 获取图片拍摄的意图
     * @param outUri    输出的 uri ( 保存地址 )
     * @param isNewTask 是否开启新的任务栈
     * @return 图片拍摄的意图
     */
    public static Intent getImageCaptureIntent(
            final Uri outUri,
            final boolean isNewTask
    ) {
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            return getIntent(intent, isNewTask);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getImageCaptureIntent");
        }
        return null;
    }

    /**
     * 获取视频拍摄的意图
     * @param outUri 输出的 uri ( 保存地址 )
     * @return 视频拍摄的意图
     */
    public static Intent getVideoCaptureIntent(final Uri outUri) {
        return getVideoCaptureIntent(outUri, false);
    }

    /**
     * 获取视频拍摄的意图
     * @param outUri    输出的 uri ( 保存地址 )
     * @param isNewTask 是否开启新的任务栈
     * @return 视频拍摄的意图
     */
    public static Intent getVideoCaptureIntent(
            final Uri outUri,
            final boolean isNewTask
    ) {
        try {
            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            return getIntent(intent, isNewTask);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getVideoCaptureIntent");
        }
        return null;
    }

    /**
     * 获取存储访问框架的意图
     * @return 存储访问框架的意图
     */
    public static Intent getOpenDocumentIntent() {
        return getOpenDocumentIntent("*/*");
    }

    /**
     * 获取存储访问框架的意图
     * <pre>
     *     SAF 存储访问框架 Storage Access Framework
     * </pre>
     * @param type 跳转类型
     * @return 存储访问框架的意图
     */
    public static Intent getOpenDocumentIntent(final String type) {
        try {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType(type);
            return intent;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getOpenDocumentIntent");
        }
        return null;
    }

    /**
     * 获取创建文件的意图
     * <pre>
     *     getCreateDocumentIntent("text/plain", "foobar.txt");
     *     getCreateDocumentIntent("image/png", "picture.png");
     *     <p></p>
     *     创建后在 onActivityResult 中获取到 Uri, 对 Uri 进行读写
     * </pre>
     * @param mimeType 资源类型
     * @param fileName 文件名
     * @return 创建文件的意图
     */
    public static Intent getCreateDocumentIntent(
            final String mimeType,
            final String fileName
    ) {
        try {
            Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType(mimeType);
            intent.putExtra(Intent.EXTRA_TITLE, fileName);
            return intent;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getCreateDocumentIntent");
        }
        return null;
    }

    /**
     * 获取打开浏览器的意图
     * <pre>
     *     Uri uri = Uri.parse("https://www.baidu.com")
     *     如果手机本身安装了多个浏览器而又没有设置默认浏览器的话, 系统将让用户选择使用哪个浏览器来打开链接
     * </pre>
     * @param uri       链接地址
     * @param isNewTask 是否开启新的任务栈
     * @return 打开浏览器的意图
     */
    public static Intent getOpenBrowserIntent(
            final Uri uri,
            final boolean isNewTask
    ) {
        return getOpenBrowserIntent(uri, null, null, isNewTask);
    }

    /**
     * 获取打开 Android 浏览器的意图
     * @param uri       链接地址
     * @param isNewTask 是否开启新的任务栈
     * @return 打开 Android 浏览器的意图
     */
    public static Intent getOpenAndroidBrowserIntent(
            final Uri uri,
            final boolean isNewTask
    ) {
        return getOpenBrowserIntent(uri, "com.android.browser", "com.android.browser.BrowserActivity", isNewTask);
    }

    /**
     * 获取打开指定浏览器的意图
     * <pre>
     *     打开指定浏览器, 如:
     *     intent.setClassName("com.UCMobile", "com.uc.browser.InnerUCMobile"); // 打开 UC 浏览器
     *     intent.setClassName("com.tencent.mtt", "com.tencent.mtt.MainActivity"); // 打开 QQ 浏览器
     *     intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity"); // 系统指定浏览器
     * </pre>
     * @param uri         链接地址
     * @param packageName 应用包名
     * @param className   完整类名 ( 可不传 )
     * @param isNewTask   是否开启新的任务栈
     * @return 打开指定浏览器的意图
     */
    public static Intent getOpenBrowserIntent(
            final Uri uri,
            final String packageName,
            final String className,
            final boolean isNewTask
    ) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            if (!TextUtils.isEmpty(packageName)) {
//                intent.setClassName(packageName, className);
                List<ResolveInfo>   lists    = AppUtils.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                Map<String, String> browsers = new HashMap<>();
                for (ResolveInfo resolveInfo : lists) {
                    ActivityInfo activityInfo = resolveInfo.activityInfo;
                    if (activityInfo != null) { // 包名, Activity Name
                        browsers.put(activityInfo.packageName, activityInfo.targetActivity);
                    }
                }
                if (browsers.containsKey(packageName)) {
                    if (TextUtils.isEmpty(className)) {
                        intent.setComponent(new ComponentName(packageName, browsers.get(packageName)));
                    } else {
                        intent.setComponent(new ComponentName(packageName, className));
                    }
                }
            }
            return getIntent(intent, isNewTask);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getOpenBrowserIntent");
        }
        return null;
    }
}