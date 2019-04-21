package dev.utils.app;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.RequiresPermission;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import java.io.File;

import dev.DevUtils;
import dev.utils.LogPrintUtils;
import dev.utils.common.FileUtils;

/**
 * detail: Intent(意图) 相关工具类
 * @author Ttt
 * <pre>
 *      7.0 及以上安装需要传入清单文件中的<provider>}的 authorities 属性
 *      查看链接:
 *      @see <a href="https://developer.android.com/reference/android/support/v4/content/FileProvider.html"/>
 * </pre>
 */
public final class IntentUtils {

    private IntentUtils() {
    }

    // 日志 TAG
    private static final String TAG = IntentUtils.class.getSimpleName();

    /**
     * 判断 Intent 是否可用
     * @param intent
     * @return
     */
    public static boolean isIntentAvailable(final Intent intent) {
        if (intent == null) return false;
        try {
            return DevUtils.getContext().getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY).size() > 0;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "isIntentAvailable");
        }
        return false;
    }

    /**
     * 获取安装 App(支持 8.0)的意图
     * <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />
     * @param filePath
     * @param authority 7.0 及以上安装需要传入清单文件中的<provider>}的 authorities 属性
     *                  <br>参看 https://developer.android.com/reference/android/support/v4/content/FileProvider.html
     * @return 安装 App(支持 8.0)的意图
     */
    public static Intent getInstallAppIntent(final String filePath, final String authority) {
        return getInstallAppIntent(FileUtils.getFileByPath(filePath), authority);
    }

    /**
     * 获取安装 App(支持 8.0)的意图
     * <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />
     * @param file
     * @param authority 7.0 及以上安装需要传入清单文件中的<provider>}的 authorities 属性
     *                  <br>参看 https://developer.android.com/reference/android/support/v4/content/FileProvider.html
     * @return 安装 App(支持 8.0)的意图
     */
    public static Intent getInstallAppIntent(final File file, final String authority) {
        return getInstallAppIntent(file, authority, false);
    }

    /**
     * 获取安装 App(支持 8.0)的意图
     * <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />
     * @param file
     * @param authority 7.0 及以上安装需要传入清单文件中的<provider>}的 authorities 属性
     *                  <br>参看 https://developer.android.com/reference/android/support/v4/content/FileProvider.html
     * @param isNewTask 是否开启新的任务栈
     * @return 安装 App(支持 8.0)的意图
     */
    public static Intent getInstallAppIntent(final File file, final String authority, final boolean isNewTask) {
        if (file == null) return null;
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri data;
            String type = "application/vnd.android.package-archive";
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                data = Uri.fromFile(file);
            } else {
                data = FileProvider.getUriForFile(DevUtils.getContext(), authority, file);
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            intent.setDataAndType(data, type);
            return getIntent(intent, isNewTask);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getInstallAppIntent");
        }
        return null;
    }

    /**
     * 获取卸载 App 的意图
     * @param packageName
     * @return 卸载 App 的意图
     */
    public static Intent getUninstallAppIntent(final String packageName) {
        return getUninstallAppIntent(packageName, false);
    }

    /**
     * 获取卸载 App 的意图
     * @param packageName
     * @param isNewTask   是否开启新的任务栈
     * @return 卸载 App 的意图
     */
    public static Intent getUninstallAppIntent(final String packageName, final boolean isNewTask) {
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
     * 获取打开 App 的意图
     * @param packageName
     * @return 打开 App 的意图
     */
    public static Intent getLaunchAppIntent(final String packageName) {
        return getLaunchAppIntent(packageName, false);
    }

    /**
     * 获取打开 App 的意图
     * @param packageName
     * @param isNewTask   是否开启新的任务栈
     * @return 打开 App 的意图
     */
    public static Intent getLaunchAppIntent(final String packageName, final boolean isNewTask) {
        try {
            Intent intent = DevUtils.getContext().getPackageManager().getLaunchIntentForPackage(packageName);
            if (intent == null) return null;
            return getIntent(intent, isNewTask);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getLaunchAppIntent");
        }
        return null;
    }

    /**
     * 获取 App 具体设置的意图
     * @param packageName
     * @return App 具体设置的意图
     */
    public static Intent getLaunchAppDetailsSettingsIntent(final String packageName) {
        return getLaunchAppDetailsSettingsIntent(packageName, false);
    }

    /**
     * 获取 App 具体设置的意图
     * @param packageName
     * @param isNewTask   是否开启新的任务栈
     * @return App 具体设置的意图
     */
    public static Intent getLaunchAppDetailsSettingsIntent(final String packageName, final boolean isNewTask) {
        try {
            Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.parse("package:" + packageName));
            return getIntent(intent, isNewTask);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getLaunchAppDetailsSettingsIntent");
        }
        return null;
    }

    /**
     * 获取 到应用商店app详情界面的意图
     * @param packageName 目标 App 的包名
     * @param marketPkg   应用商店包名 ,如果为""则由系统弹出应用商店列表供用户选择,否则调转到目标市场的应用详情界面，某些应用商店可能会失败
     */
    public static Intent getLaunchAppDetailIntent(final String packageName, final String marketPkg) {
        return getLaunchAppDetailIntent(packageName, marketPkg, false);
    }

    /**
     * 获取 到应用商店app详情界面的意图
     * @param packageName 目标App的包名
     * @param marketPkg   应用商店包名 ,如果为""则由系统弹出应用商店列表供用户选择,否则调转到目标市场的应用详情界面，某些应用商店可能会失败
     * @param isNewTask   是否开启新的任务栈
     */
    public static Intent getLaunchAppDetailIntent(final String packageName, final String marketPkg, final boolean isNewTask) {
        try {
            if (TextUtils.isEmpty(packageName)) return null;

            Uri uri = Uri.parse("market://details?id=" + packageName);
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

    public static Intent getShareTextIntent(final String content, final boolean isNewTask) {
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
    public static Intent getShareImageIntent(final String content, final String imagePath) {
        return getShareImageIntent(content, imagePath, false);
    }

    /**
     * 获取分享图片的意图
     * @param content   文本
     * @param imagePath 图片文件路径
     * @param isNewTask 是否开启新的任务栈
     * @return 分享图片的意图
     */
    public static Intent getShareImageIntent(final String content, final String imagePath, final boolean isNewTask) {
        if (imagePath == null || imagePath.length() == 0) return null;
        try {
            return getShareImageIntent(content, new File(imagePath), isNewTask);
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
    public static Intent getShareImageIntent(final String content, final File image) {
        return getShareImageIntent(content, image, false);
    }

    /**
     * 获取分享图片的意图
     * @param content   文本
     * @param image     图片文件
     * @param isNewTask 是否开启新的任务栈
     * @return 分享图片的意图
     */
    public static Intent getShareImageIntent(final String content, final File image, final boolean isNewTask) {
        if (image != null && image.isFile()) return null;
        try {
            return getShareImageIntent(content, Uri.fromFile(image), isNewTask);
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
    public static Intent getShareImageIntent(final String content, final Uri uri) {
        return getShareImageIntent(content, uri, false);
    }

    /**
     * 获取分享图片的意图
     * @param content   分享文本
     * @param uri       图片 uri
     * @param isNewTask 是否开启新的任务栈
     * @return 分享图片的意图
     */
    public static Intent getShareImageIntent(final String content, final Uri uri, final boolean isNewTask) {
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
     * @param packageName
     * @param className   全类名
     * @return 其他应用组件的意图
     */
    public static Intent getComponentIntent(final String packageName, final String className) {
        return getComponentIntent(packageName, className, null, false);
    }

    /**
     * 获取其他应用组件的意图
     * @param packageName
     * @param className   全类名
     * @param isNewTask   是否开启新的任务栈
     * @return 其他应用组件的意图
     */
    public static Intent getComponentIntent(final String packageName, final String className, final boolean isNewTask) {
        return getComponentIntent(packageName, className, null, isNewTask);
    }

    /**
     * 获取其他应用组件的意图
     * @param packageName
     * @param className   全类名
     * @param bundle
     * @return 其他应用组件的意图
     */
    public static Intent getComponentIntent(final String packageName, final String className, final Bundle bundle) {
        return getComponentIntent(packageName, className, bundle, false);
    }

    /**
     * 获取其他应用组件的意图
     * @param packageName
     * @param className   全类名
     * @param bundle
     * @param isNewTask   是否开启新的任务栈
     * @return 其他应用组件的意图
     */
    public static Intent getComponentIntent(final String packageName, final String className, final Bundle bundle, final boolean isNewTask) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            if (bundle != null) intent.putExtras(bundle);
            ComponentName cn = new ComponentName(packageName, className);
            intent.setComponent(cn);
            return getIntent(intent, isNewTask);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getComponentIntent");
        }
        return null;
    }

    /**
     * 获取关机的意图
     * <uses-permission android:name="android.permission.SHUTDOWN" />
     * @return 关机的意图
     */
    public static Intent getShutdownIntent() {
        return getShutdownIntent(false);
    }

    /**
     * 获取关机的意图
     * <uses-permission android:name="android.permission.SHUTDOWN" />
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
    public static Intent getDialIntent(final String phoneNumber, final boolean isNewTask) {
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
     * <uses-permission android:name="android.permission.CALL_PHONE" />
     * @param phoneNumber 电话号码
     * @return 拨打电话意图
     */
    @RequiresPermission(Manifest.permission.CALL_PHONE)
    public static Intent getCallIntent(final String phoneNumber) {
        return getCallIntent(phoneNumber, false);
    }

    /**
     * 获取拨打电话意图
     * <uses-permission android:name="android.permission.CALL_PHONE" />
     * @param phoneNumber 电话号码
     * @param isNewTask   是否开启新的任务栈
     * @return 拨打电话意图
     */
    @RequiresPermission(Manifest.permission.CALL_PHONE)
    public static Intent getCallIntent(final String phoneNumber, final boolean isNewTask) {
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
    public static Intent getSendSmsIntent(final String phoneNumber, final String content) {
        return getSendSmsIntent(phoneNumber, content, false);
    }

    /**
     * 获取跳至发送短信界面的意图
     * @param phoneNumber 接收号码
     * @param content     短信内容
     * @param isNewTask   是否开启新的任务栈
     * @return 发送短信界面的意图
     */
    public static Intent getSendSmsIntent(final String phoneNumber, final String content, final boolean isNewTask) {
        try {
            Uri uri = Uri.parse("smsto:" + phoneNumber);
            Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
            intent.putExtra("sms_body", content);
            return getIntent(intent, isNewTask);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getSendSmsIntent");
        }
        return null;
    }

    /**
     * 获取拍照的意图
     * @param outUri 输出的 uri
     * @return 拍照的意图
     */
    public static Intent getCaptureIntent(final Uri outUri) {
        return getCaptureIntent(outUri, false);
    }

    /**
     * 获取拍照的意图
     * @param outUri    输出的 uri
     * @param isNewTask 是否开启新的任务栈
     * @return 拍照的意图
     */
    public static Intent getCaptureIntent(final Uri outUri, final boolean isNewTask) {
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            return getIntent(intent, isNewTask);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getCaptureIntent");
        }
        return null;
    }

    /**
     * 跳转到系统设置页面
     * @param activity
     */
    public static void startSysSetting(final Activity activity) {
//        if (android.os.Build.VERSION.SDK_INT > 10 ) {
//            activity.startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
//        } else {
//            activity.startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
//        }
//        // 跳转到 无线和网络 设置页面(可以设置移动网络,sim卡1，2的移动网络)
//        Intent intent =  new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS);
//        Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
        try {
            if (activity != null) {
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                activity.startActivity(intent);
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "startSysSetting");
        }
    }

    /**
     * 跳转到系统设置页面
     * @param activity
     * @param requestCode 回传请求code
     */
    public static void startSysSetting(final Activity activity, final int requestCode) {
        try {
            Intent intent = new Intent(Settings.ACTION_SETTINGS);
            activity.startActivityForResult(intent, requestCode);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "startSysSetting");
        }
    }

    /**
     * 打开网络设置界面 - 3.0以下打开设置界面
     * @param activity
     */
    public static void openWirelessSettings(final Activity activity) {
        try {
            if (Build.VERSION.SDK_INT > 10) {
                activity.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            } else {
                activity.startActivity(new Intent(Settings.ACTION_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "openWirelessSettings");
        }
    }

    // =

    private static Intent getIntent(final Intent intent, final boolean isNewTask) {
        if (intent != null) {
            return isNewTask ? intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) : intent;
        } else {
            return null;
        }
    }

//    /**
//     * 获取选择照片的 Intent
//     *
//     * @return
//     */
//    public static Intent getPickIntentWithGallery() {
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        return intent.setType("image*//*");
//    }
//
//    /**
//     * 获取从文件中选择照片的 Intent
//     *
//     * @return
//     */
//    public static Intent getPickIntentWithDocuments() {
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        return intent.setType("image*//*");
//    }
//
//
//    public static Intent buildImageGetIntent(final Uri saveTo, final int outputX, final int outputY, final boolean returnData) {
//        return buildImageGetIntent(saveTo, 1, 1, outputX, outputY, returnData);
//    }
//
//    public static Intent buildImageGetIntent(Uri saveTo, int aspectX, int aspectY,
//                                             int outputX, int outputY, boolean returnData) {
//        Intent intent = new Intent();
//        if (Build.VERSION.SDK_INT < 19) {
//            intent.setAction(Intent.ACTION_GET_CONTENT);
//        } else {
//            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
//            intent.addCategory(Intent.CATEGORY_OPENABLE);
//        }
//        intent.setType("image*//*");
//        intent.putExtra("output", saveTo);
//        intent.putExtra("aspectX", aspectX);
//        intent.putExtra("aspectY", aspectY);
//        intent.putExtra("outputX", outputX);
//        intent.putExtra("outputY", outputY);
//        intent.putExtra("scale", true);
//        intent.putExtra("return-data", returnData);
//        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
//        return intent;
//    }
//
//    public static Intent buildImageCropIntent(final Uri uriFrom, final Uri uriTo, final int outputX, final int outputY, final boolean returnData) {
//        return buildImageCropIntent(uriFrom, uriTo, 1, 1, outputX, outputY, returnData);
//    }
//
//    public static Intent buildImageCropIntent(Uri uriFrom, Uri uriTo, int aspectX, int aspectY,
//                                              int outputX, int outputY, boolean returnData) {
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        intent.setDataAndType(uriFrom, "image*//*");
//        intent.putExtra("crop", "true");
//        intent.putExtra("output", uriTo);
//        intent.putExtra("aspectX", aspectX);
//        intent.putExtra("aspectY", aspectY);
//        intent.putExtra("outputX", outputX);
//        intent.putExtra("outputY", outputY);
//        intent.putExtra("scale", true);
//        intent.putExtra("return-data", returnData);
//        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
//        return intent;
//    }
//
//    public static Intent buildImageCaptureIntent(final Uri uri) {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//        return intent;
//    }
}
