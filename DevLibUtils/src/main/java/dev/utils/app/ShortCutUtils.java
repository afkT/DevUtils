package dev.utils.app;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;

import java.util.List;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: 创建删除快捷图标工具类
 * @author Ttt
 * <pre>
 *     需要的权限:
 *     <uses-permission android:name="com.android.launcher.permission.INSTALL_SHORTCUT" />
 *     <uses-permission android:name="com.android.launcher.permission.UNINSTALL_SHORTCUT" />
 *     <uses-permission android:name="com.android.launcher.permission.READ_SETTINGS" />
 *     <uses-permission android:name="com.android.launcher.permission.WRITE_SETTINGS" />
 *     <p></p>
 *     READ_SETTINGS 用于判断是否存在快捷图标
 * </pre>
 */
public final class ShortCutUtils {

    private ShortCutUtils() {
    }

    // 日志 TAG
    private static final String TAG = ShortCutUtils.class.getSimpleName();

    /**
     * 检测是否存在桌面快捷方式
     * @param name 快捷方式名称
     * @return {@code true} yes, {@code false} no
     */
    public static boolean hasShortcut(final String name) {
        Cursor cursor = null;
        try {
            Context context = DevUtils.getContext();
            Uri uri = Uri.parse("content://" + getAuthority(context) + "/favorites?notify=true");
            ContentResolver resolver = context.getContentResolver();
            cursor = resolver.query(uri, new String[]{"title", "iconResource"}, "title=?", new String[]{name}, null);
            // 判断是否存在快捷方式
            if (cursor != null && cursor.getCount() > 0) {
                return true;
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "hasShortcut");
        } finally {
            if (cursor != null) {
                try {
                    cursor.close();
                } catch (Exception e) {
                }
            }
        }
        return false;
    }

    // ====================
    // = 创建桌面快捷方式 =
    // ====================

    /**
     * 创建桌面快捷方式
     * @param clazz 快捷方式点击 Intent class
     * @param name  快捷方式名称
     * @param icon  快捷方式图标
     */
    public static void addShortcut(final Class clazz, final String name, final int icon) {
        if (clazz != null) {
            addShortcut(clazz.getName(), name, icon);
        }
    }

    /**
     * 创建桌面快捷方式
     * @param className 快捷方式点击 Intent className(class.getName())
     * @param name      快捷方式名称
     * @param icon      快捷方式图标
     */
    public static void addShortcut(final String className, final String name, final int icon) {
        if (className != null && name != null) {
            try {
                // 快捷方式点击 Intent 跳转
                Intent shortcutIntent = new Intent(Intent.ACTION_MAIN);
                shortcutIntent.setClassName(DevUtils.getContext(), className);
                shortcutIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                addShortcut(shortcutIntent, name, icon);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "addShortcut");
            }
        }
    }

    /**
     * 创建桌面快捷方式
     * @param shortcutIntent 快捷方式点击 Intent 跳转
     * @param name           快捷方式名称
     * @param icon           快捷方式图标
     */
    public static void addShortcut(final Intent shortcutIntent, final String name, final int icon) {
        if (shortcutIntent != null && name != null) {
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
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "addShortcut");
            }
        }
    }

    // ====================
    // = 删除桌面快捷方式 =
    // ====================

    /**
     * 删除桌面快捷方式
     * @param clazz 快捷方式点击 Intent class
     * @param name  快捷方式名称
     */
    public static void deleteShortcut(final Class clazz, final String name) {
        if (clazz != null) {
            deleteShortcut(clazz.getName(), name);
        }
    }

    /**
     * 删除桌面快捷方式
     * @param className 快捷方式点击 Intent className(class.getName())
     * @param name      快捷方式名称
     */
    public static void deleteShortcut(final String className, final String name) {
        if (className != null && name != null) {
            try {
                Context context = DevUtils.getContext();
                // 快捷方式 Intent
                Intent shortcutIntent = new Intent(Intent.ACTION_MAIN);
                shortcutIntent.setClassName(context, className);
                // 删除快捷方式 Intent
                Intent shortcut = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
                shortcut.putExtra("duplicate", false);
                shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, name); // 快捷方式名称
                shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
                context.sendBroadcast(shortcut);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "deleteShortcut");
            }
        }
    }

    // ============
    // = 内部方法 =
    // ============

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
    private static String getAuthorityFromPermission(final Context context, final String permission) {
        if (permission != null) {
            List<PackageInfo> lists = context.getPackageManager().getInstalledPackages(PackageManager.GET_PROVIDERS);
            if (lists != null) {
                for (PackageInfo packageInfo : lists) {
                    ProviderInfo[] providers = packageInfo.providers;
                    if (providers != null) {
                        for (ProviderInfo provider : providers) {
                            if (permission.equals(provider.readPermission))
                                return provider.authority;
                            if (permission.equals(provider.writePermission))
                                return provider.authority;
                        }
                    }
                }
            }
        }
        return null;
    }
}