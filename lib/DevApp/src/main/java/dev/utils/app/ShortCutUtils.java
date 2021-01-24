package dev.utils.app;

import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.database.Cursor;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.AnyRes;

import java.util.List;

import dev.DevUtils;
import dev.utils.LogPrintUtils;
import dev.utils.common.CloseUtils;

/**
 * detail: 快捷方式工具类
 * @author Ttt
 * <pre>
 *     所需权限
 *     <uses-permission android:name="com.android.launcher.permission.INSTALL_SHORTCUT" />
 *     <uses-permission android:name="com.android.launcher.permission.UNINSTALL_SHORTCUT" />
 *     <uses-permission android:name="com.android.launcher.permission.READ_SETTINGS" />
 *     <uses-permission android:name="com.android.launcher2.permission.READ_SETTINGS" />
 *     <uses-permission android:name="com.android.launcher3.permission.READ_SETTINGS" />
 *     <p></p>
 *     READ_SETTINGS 用于判断是否存在快捷图标
 *     <p></p>
 *     @see <a href="https://www.jianshu.com/p/18be986553db"/>
 *     @see <a href="https://blog.csdn.net/m0_37218227/article/details/84071043"/>
 * </pre>
 */
public final class ShortCutUtils {

    private ShortCutUtils() {
    }

    // 日志 TAG
    private static final String TAG = ShortCutUtils.class.getSimpleName();

    // ======================
    // = 检测是否存在快捷方式 =
    // ======================

    /**
     * 检测是否存在桌面快捷方式
     * @param name 快捷方式名称
     * @return {@code true} yes, {@code false} no
     */
    public static boolean hasShortcut(final String name) {
        Cursor cursor = null;
        try {
            Context         context  = DevUtils.getContext();
            Uri             uri      = Uri.parse("content://" + getAuthority(context) + "/favorites?notify=true");
            ContentResolver resolver = context.getContentResolver();
            cursor = resolver.query(uri, new String[]{"title", "iconResource"}, "title=?", new String[]{name}, null);
            // 判断是否存在快捷方式
            return (cursor != null && cursor.getCount() > 0);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "hasShortcut");
        } finally {
            CloseUtils.closeIOQuietly(cursor);
        }
        return false;
    }

    // ===================
    // = 创建桌面快捷方式 =
    // ===================

    /**
     * 获取桌面快捷方式点击 Intent
     * @param clazz 快捷方式点击 Intent className(class.getName())
     * @return {@link Intent}
     */
    public static Intent getShortCutIntent(final Class clazz) {
        return (clazz != null) ? getShortCutIntent(clazz.getName()) : null;
    }

    /**
     * 获取桌面快捷方式点击 Intent
     * @param className 快捷方式点击 Intent className(class.getName())
     * @return {@link Intent}
     */
    public static Intent getShortCutIntent(final String className) {
        if (className != null) {
            try {
                // 快捷方式点击 Intent 跳转
                Intent shortcutIntent = new Intent(Intent.ACTION_MAIN);
                shortcutIntent.setClassName(DevUtils.getContext(), className);
                shortcutIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                return shortcutIntent;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getShortCutIntent");
            }
        }
        return null;
    }

    // =

    /**
     * 创建桌面快捷方式
     * @param clazz 快捷方式点击 Intent class
     * @param name  快捷方式名称
     * @param icon  快捷方式图标
     * @return {@code true} success, {@code false} fail
     */
    public static boolean addShortcut(
            final Class clazz,
            final String name,
            @AnyRes final int icon
    ) {
        return (clazz != null) && addShortcut(clazz.getName(), name, icon);
    }

    /**
     * 创建桌面快捷方式
     * @param className 快捷方式点击 Intent className(class.getName())
     * @param name      快捷方式名称
     * @param icon      快捷方式图标
     * @return {@code true} success, {@code false} fail
     */
    public static boolean addShortcut(
            final String className,
            final String name,
            @AnyRes final int icon
    ) {
        if (className != null && name != null) {
            try {
                // 快捷方式点击 Intent 跳转
                Intent shortcutIntent = new Intent(Intent.ACTION_MAIN);
                shortcutIntent.setClassName(DevUtils.getContext(), className);
                shortcutIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                return addShortcut(shortcutIntent, name, icon);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "addShortcut");
            }
        }
        return false;
    }

    /**
     * 创建桌面快捷方式
     * @param shortcutIntent 快捷方式点击 Intent 跳转
     * @param name           快捷方式名称
     * @param icon           快捷方式图标
     * @return {@code true} success, {@code false} fail
     */
    public static boolean addShortcut(
            final Intent shortcutIntent,
            final String name,
            @AnyRes final int icon
    ) {
        return addShortcut(shortcutIntent, name, icon, null);
    }

    /**
     * 创建桌面快捷方式
     * @param shortcutIntent 快捷方式点击 Intent 跳转
     * @param name           快捷方式名称
     * @param icon           快捷方式图标
     * @param pendingIntent  创建结果通知 (Android 8.0)
     * @return {@code true} success, {@code false} fail
     */
    public static boolean addShortcut(
            final Intent shortcutIntent,
            final String name,
            @AnyRes final int icon,
            final PendingIntent pendingIntent
    ) {
        if (shortcutIntent != null && name != null) {
            // Android 8.0 之前
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                try {
                    Context context = DevUtils.getContext();
                    // 快捷方式图标
                    ShortcutIconResource iconRes = ShortcutIconResource.fromContext(context, icon);
                    // 创建快捷方式 Intent
                    Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
                    shortcut.putExtra("duplicate", false); // 不允许重复创建
                    shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, name); // 快捷方式名称
                    shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent); // 快捷方式点击跳转
                    shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);
                    // 发送广播, 创建快捷方式
                    context.sendBroadcast(shortcut);
                    return true;
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "addShortcut");
                }
            } else { // Android 8.0 之后
                try {
                    Context context = DevUtils.getContext();
                    // 获取 ShortcutManager
                    ShortcutManager shortcutManager = AppUtils.getShortcutManager();
                    // 如果默认桌面支持 requestPinShortcut(ShortcutInfo、IntentSender) 方法
                    if (shortcutManager != null && shortcutManager.isRequestPinShortcutSupported()) {
                        // 快捷方式创建相关信息
                        ShortcutInfo shortcutInfo = new ShortcutInfo.Builder(context, String.valueOf(name.hashCode()))
                                .setIcon(Icon.createWithResource(context, icon)) // 快捷方式图标
                                .setShortLabel(name) // 快捷方式名字
                                .setIntent(shortcutIntent) // 快捷方式跳转 Intent
                                .build();
                        // 创建快捷方式
                        if (pendingIntent != null) {
                            shortcutManager.requestPinShortcut(shortcutInfo, pendingIntent.getIntentSender());
                        } else {
                            shortcutManager.requestPinShortcut(shortcutInfo, null);
                        }
                        return true;
                    }
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "addShortcut");
                }
            }
        }
        return false;
    }

    // ===================
    // = 删除桌面快捷方式 =
    // ===================

    /**
     * 删除桌面快捷方式
     * @param clazz 快捷方式点击 Intent class
     * @param name  快捷方式名称
     * @return {@code true} success, {@code false} fail
     */
    public static boolean deleteShortcut(
            final Class clazz,
            final String name
    ) {
        return (clazz != null) && deleteShortcut(clazz.getName(), name);
    }

    /**
     * 删除桌面快捷方式
     * <pre>
     *     Android 6.0 以后因存在安全隐患 Google 移除了 UninstallShortcutReceiver 无法进行删除桌面快捷
     * </pre>
     * @param className 快捷方式点击 Intent className(class.getName())
     * @param name      快捷方式名称
     * @return {@code true} success, {@code false} fail
     */
    public static boolean deleteShortcut(
            final String className,
            final String name
    ) {
        if (className != null && name != null) {
            try {
                Context context = DevUtils.getContext();
                // 快捷方式 Intent
                Intent shortcutIntent = new Intent(Intent.ACTION_MAIN);
                shortcutIntent.setClassName(context, className);
                // 删除快捷方式 Intent
                Intent shortcut = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
                shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, name); // 快捷方式名称
                shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
                context.sendBroadcast(shortcut);
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "deleteShortcut");
            }
        }
        return false;
    }

    // ===========
    // = 内部方法 =
    // ===========

    /**
     * 获取 Authority
     * @param context {@link Context}
     * @return Authority
     */
    private static String getAuthority(final Context context) {
        // 获取 Authority
        String authority = getAuthorityFromPermission(context);
        // 如果等于 null
        if (authority == null) {
            int version = android.os.Build.VERSION.SDK_INT;
            if (version < 8) { // Android 2.1.x (API 7) 以及以下的
                authority = "com.android.launcher.settings";
            } else if (version < 19) { // Android 4.4 以下
                authority = "com.android.launcher2.settings";
            } else { // 4.4 以及以上
                authority = "com.android.launcher3.settings";
            }
            return authority;
        }
        return authority;
    }

    /**
     * 通过权限获取 Authority
     * @param context {@link Context}
     * @return Authority
     */
    private static String getAuthorityFromPermission(final Context context) {
        // 权限判断
        String[] permissions = {"com.android.launcher.permission.WRITE_SETTINGS", "com.android.launcher.permission.READ_SETTINGS"};
        // 遍历权限
        for (String permission : permissions) {
            try {
                String authority = getAuthorityFromPermission(context, permission);
                if (authority != null) {
                    return authority;
                }
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getAuthorityFromPermission");
            }
        }
        return null;
    }

    /**
     * 通过权限获取 Authority
     * <pre>
     *     android 默认的 AUTHORITY 在 2.2 以后是 com.android.launcher2.settings
     *     但是不同的厂商可能会做不同的修改, 由于不同的厂商 uri 的前缀不同
     *     所以需要去查询 provider 获取真实的 content 的 uri 前缀
     * </pre>
     * @param context    {@link Context}
     * @param permission 权限
     * @return Authority
     */
    private static String getAuthorityFromPermission(
            final Context context,
            final String permission
    ) {
        if (permission != null) {
            List<PackageInfo> lists = context.getPackageManager().getInstalledPackages(PackageManager.GET_PROVIDERS);
            if (lists != null) {
                for (PackageInfo packageInfo : lists) {
                    ProviderInfo[] providers = packageInfo.providers;
                    if (providers != null) {
                        for (ProviderInfo provider : providers) {
                            if (permission.equals(provider.readPermission)) {
                                return provider.authority;
                            }
                            if (permission.equals(provider.writePermission)) {
                                return provider.authority;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
}