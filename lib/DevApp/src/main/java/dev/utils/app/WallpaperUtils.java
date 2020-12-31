package dev.utils.app;

import android.Manifest;
import android.app.WallpaperColors;
import android.app.WallpaperInfo;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RawRes;
import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;

import java.io.InputStream;

import dev.utils.LogPrintUtils;

/**
 * detail: 壁纸工具类
 * @author Ttt
 * <pre>
 *     所需权限
 *     <uses-permission android:name="android.permission.SET_WALLPAPER" />
 *     <p></p>
 *     修改 ( 设置、清空 ) 壁纸成功都会触发 {@link Intent#ACTION_WALLPAPER_CHANGED} 广播
 *     <p></p>
 *     桌面壁纸
 *     {@link WallpaperManager#FLAG_SYSTEM}
 *     锁屏壁纸
 *     {@link WallpaperManager#FLAG_LOCK}
 *     <p></p>
 *     动态壁纸可参考 ( 支持 Gif、Camera、Video 动态壁纸 ) :
 *     @see <a href="https://github.com/tyhjh/liveWallpaper"/>
 * </pre>
 */
public final class WallpaperUtils {

    private WallpaperUtils() {
    }

    // 日志 TAG
    private static final String TAG = WallpaperUtils.class.getSimpleName();

    /**
     * 是否支持壁纸
     * @return {@code true} yes, {@code false} no
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean isWallpaperSupported() {
        try {
            return AppUtils.getWallpaperManager().isWallpaperSupported();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "isWallpaperSupported");
        }
        return false;
    }

    /**
     * 是否支持设置壁纸
     * @return {@code true} yes, {@code false} no
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static boolean isSetWallpaperAllowed() {
        try {
            return AppUtils.getWallpaperManager().isSetWallpaperAllowed();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "isSetWallpaperAllowed");
        }
        return false;
    }

    /**
     * 判断当前壁纸是否使用给定的资源 Id
     * <pre>
     *     前提是壁纸是通过 {@link WallpaperManager#setResource(int)} 进行设置的
     * </pre>
     * @param resId resource identifier
     * @return {@code true} yes, {@code false} no
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean hasResourceWallpaper(@RawRes final int resId) {
        try {
            return AppUtils.getWallpaperManager().hasResourceWallpaper(resId);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "hasResourceWallpaper");
        }
        return false;
    }

    // ===========
    // = 删除操作 =
    // ===========

    /**
     * 删除所有内部引用到最后加载的壁纸
     */
    public static void forgetLoadedWallpaper() {
        AppUtils.getWallpaperManager().forgetLoadedWallpaper();
    }

    /**
     * 删除壁纸 ( 恢复为系统内置桌面壁纸 )
     * @return {@code true} success, {@code false} fail
     */
    @RequiresPermission(Manifest.permission.SET_WALLPAPER)
    public static boolean clear() {
        try {
            AppUtils.getWallpaperManager().clear();
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "clear");
        }
        return false;
    }

    /**
     * 删除壁纸 ( 恢复为系统内置壁纸 )
     * @param which {@link WallpaperManager#FLAG_SYSTEM} or {@link WallpaperManager#FLAG_LOCK}
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @RequiresPermission(Manifest.permission.SET_WALLPAPER)
    public static boolean clear(final int which) {
        try {
            AppUtils.getWallpaperManager().clear(which);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "clear which: %s", which);
        }
        return false;
    }

    /**
     * 删除壁纸 ( 恢复为系统内置壁纸 )
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.P)
    @RequiresPermission(Manifest.permission.SET_WALLPAPER)
    public static boolean clearWallpaper() {
        try {
            AppUtils.getWallpaperManager().clearWallpaper();
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "clearWallpaper");
        }
        return false;
    }

    // ===========
    // = 获取操作 =
    // ===========

    /**
     * 获取当前壁纸 Id
     * @param which {@link WallpaperManager#FLAG_SYSTEM} or {@link WallpaperManager#FLAG_LOCK}
     * @return 当前壁纸 Id
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static int getWallpaperId(final int which) {
        try {
            return AppUtils.getWallpaperManager().getWallpaperId(which);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getWallpaperId which: %s", which);
        }
        return 0;
    }

    /**
     * 获取动态壁纸信息
     * <pre>
     *     如果非动态壁纸则返回 null
     * </pre>
     * @return {@link WallpaperInfo}
     */
    public static WallpaperInfo getWallpaperInfo() {
        try {
            return AppUtils.getWallpaperManager().getWallpaperInfo();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getWallpaperInfo");
        }
        return null;
    }

    /**
     * 获取壁纸颜色信息
     * @param which {@link WallpaperManager#FLAG_SYSTEM} or {@link WallpaperManager#FLAG_LOCK}
     * @return {@link WallpaperColors}
     */
    @RequiresApi(api = Build.VERSION_CODES.O_MR1)
    public static WallpaperColors getWallpaperColors(final int which) {
        try {
            return AppUtils.getWallpaperManager().getWallpaperColors(which);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getWallpaperColors which: %s", which);
        }
        return null;
    }

    /**
     * 获取壁纸所需最小高度
     * @return 壁纸所需最小高度
     */
    public static int getDesiredMinimumHeight() {
        try {
            return AppUtils.getWallpaperManager().getDesiredMinimumHeight();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getDesiredMinimumHeight");
        }
        return 0;
    }

    /**
     * 获取壁纸所需最小宽度
     * @return 壁纸所需最小宽度
     */
    public static int getDesiredMinimumWidth() {
        try {
            return AppUtils.getWallpaperManager().getDesiredMinimumWidth();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getDesiredMinimumWidth");
        }
        return 0;
    }

    /**
     * 获取系统内置静态壁纸 ( 桌面壁纸 )
     * @return {@link Drawable}
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static Drawable getBuiltInDrawable() {
        try {
            return AppUtils.getWallpaperManager().getBuiltInDrawable();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getBuiltInDrawable");
        }
        return null;
    }

    /**
     * 获取系统内置静态壁纸
     * @param which {@link WallpaperManager#FLAG_SYSTEM} or {@link WallpaperManager#FLAG_LOCK}
     * @return {@link Drawable}
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Drawable getBuiltInDrawable(final int which) {
        try {
            return AppUtils.getWallpaperManager().getBuiltInDrawable(which);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getBuiltInDrawable which: %s", which);
        }
        return null;
    }

    /**
     * 获取当前壁纸 ( 桌面壁纸 )
     * <pre>
     *     如果未设置壁纸, 则返回系统内置的静态墙纸
     * </pre>
     * @return {@link Drawable}
     */
    public static Drawable getDrawable() {
        try {
            return AppUtils.getWallpaperManager().getDrawable();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getDrawable");
        }
        return null;
    }

    /**
     * 获取当前壁纸 ( 桌面壁纸 )
     * <pre>
     *     与相似 {@link #getDrawable()} 但返回的 Drawable 具有许多限制, 以尽可能减少其开销
     * </pre>
     * @return {@link Drawable}
     */
    @RequiresPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    public static Drawable getFastDrawable() {
        try {
            return AppUtils.getWallpaperManager().getFastDrawable();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getFastDrawable");
        }
        return null;
    }

    /**
     * 获取当前壁纸 ( 桌面壁纸 )
     * <pre>
     *     如果未设置壁纸, 则返回 null
     * </pre>
     * @return {@link Drawable}
     */
    public static Drawable peekDrawable() {
        try {
            return AppUtils.getWallpaperManager().peekDrawable();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "peekDrawable");
        }
        return null;
    }

    /**
     * 获取当前壁纸 ( 桌面壁纸 )
     * <pre>
     *     与相似 {@link #peekDrawable()} 但返回的 Drawable 具有许多限制, 以尽可能减少其开销
     * </pre>
     * @return {@link Drawable}
     */
    @RequiresPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    public static Drawable peekFastDrawable() {
        try {
            return AppUtils.getWallpaperManager().peekFastDrawable();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "peekFastDrawable");
        }
        return null;
    }

    // ===========
    // = 设置操作 =
    // ===========

    /**
     * 通过 Bitmap 设置壁纸 ( 桌面壁纸 )
     * @param bitmap {@link Bitmap}
     * @return {@code true} success, {@code false} fail
     */
    @RequiresPermission(Manifest.permission.SET_WALLPAPER)
    public static boolean setBitmap(final Bitmap bitmap) {
        try {
            AppUtils.getWallpaperManager().setBitmap(bitmap);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "setBitmap");
        }
        return false;
    }

    /**
     * 通过 Bitmap 设置壁纸
     * @param bitmap {@link Bitmap}
     * @param which  {@link WallpaperManager#FLAG_SYSTEM} or {@link WallpaperManager#FLAG_LOCK}
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @RequiresPermission(Manifest.permission.SET_WALLPAPER)
    public static boolean setBitmap(
            final Bitmap bitmap,
            final int which
    ) {
        try {
            AppUtils.getWallpaperManager().setBitmap(bitmap, null, true, which);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "setBitmap which: %s", which);
        }
        return false;
    }

    /**
     * 通过 res 设置壁纸
     * @param resId resource identifier
     * @return {@code true} success, {@code false} fail
     */
    @RequiresPermission(Manifest.permission.SET_WALLPAPER)
    public static boolean setResource(@RawRes final int resId) {
        try {
            AppUtils.getWallpaperManager().setResource(resId);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "setResource");
        }
        return false;
    }

    /**
     * 通过 res 设置壁纸
     * @param resId resource identifier
     * @param which {@link WallpaperManager#FLAG_SYSTEM} or {@link WallpaperManager#FLAG_LOCK}
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @RequiresPermission(Manifest.permission.SET_WALLPAPER)
    public static boolean setResource(
            @RawRes final int resId,
            final int which
    ) {
        try {
            AppUtils.getWallpaperManager().setResource(resId, which);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "setResource which: %s", which);
        }
        return false;
    }

    /**
     * 通过 InputStream 设置壁纸
     * @param inputStream bitmapData InputStream
     * @return {@code true} success, {@code false} fail
     */
    @RequiresPermission(Manifest.permission.SET_WALLPAPER)
    public static boolean setStream(final InputStream inputStream) {
        try {
            AppUtils.getWallpaperManager().setStream(inputStream);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "setStream");
        }
        return false;
    }

    /**
     * 通过 InputStream 设置壁纸
     * @param inputStream bitmapData InputStream
     * @param which       {@link WallpaperManager#FLAG_SYSTEM} or {@link WallpaperManager#FLAG_LOCK}
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @RequiresPermission(Manifest.permission.SET_WALLPAPER)
    public static boolean setStream(
            final InputStream inputStream,
            final int which
    ) {
        try {
            AppUtils.getWallpaperManager().setStream(inputStream, null, true, which);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "setStream which: %s", which);
        }
        return false;
    }

    // ===========
    // = 适配设置 =
    // ===========

    /**
     * detail: 其他回调
     * @author Ttt
     */
    public interface OnOtherCallback {

        /**
         * 非适配 ROM 则触发回调
         * @return {@code true} success, {@code false} fail
         */
        boolean callback();
    }

    /**
     * 通过 Uri 设置壁纸 ( 跳转到设置页 )
     * @param uri {@link Uri}
     * @return {@code true} success, {@code false} fail
     */
    @RequiresPermission(Manifest.permission.SET_WALLPAPER)
    public static boolean setUri(final Uri uri) {
        return setUri(uri, null);
    }

    /**
     * 通过 Uri 设置壁纸 ( 跳转到设置页 )
     * @param uri      {@link Uri}
     * @param callback {@link OnOtherCallback}
     * @return {@code true} success, {@code false} fail
     */
    @RequiresPermission(Manifest.permission.SET_WALLPAPER)
    public static boolean setUri(
            final Uri uri,
            final OnOtherCallback callback
    ) {
        if (uri == null) return false;
        Intent intent;
        try {
            if (ROMUtils.isXiaomi()) {
                ComponentName componentName = new ComponentName(
                        "com.android.thememanager",
                        "com.android.thememanager.activity.WallpaperDetailActivity"
                );
                intent = new Intent("miui.intent.action.START_WALLPAPER_DETAIL");
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(uri, "image/*");
                intent.putExtra("mimeType", "image/*");
                intent.setComponent(componentName);
                return AppUtils.startActivity(intent);
            }

            if (ROMUtils.isHuawei()) {
                ComponentName componentName = new ComponentName(
                        "com.android.gallery3d", "com.android.gallery3d.app.Wallpaper"
                );
                intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(uri, "image/*");
                intent.putExtra("mimeType", "image/*");
                intent.setComponent(componentName);
                return AppUtils.startActivity(intent);
            }

            if (ROMUtils.isOppo()) {
                ComponentName componentName = new ComponentName(
                        "com.coloros.gallery3d", "com.oppo.gallery3d.app.Wallpaper"
                );
                intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(uri, "image/*");
                intent.putExtra("mimeType", "image/*");
                intent.setComponent(componentName);
                return AppUtils.startActivity(intent);
            }

            if (ROMUtils.isVivo()) {
                ComponentName componentName = new ComponentName(
                        "com.vivo.gallery", "com.android.gallery3d.app.Wallpaper"
                );
                intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(uri, "image/*");
                intent.putExtra("mimeType", "image/*");
                intent.setComponent(componentName);
                return AppUtils.startActivity(intent);
            }

            if (callback != null) {
                return callback.callback();
            }
            return setStream(ResourceUtils.openInputStream(uri));
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "setUri");
        }
        return false;
    }
}