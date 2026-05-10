package dev.utils.app;

import android.content.Context;
import android.os.Build;

/**
 * detail: 版本工具类
 * @author Ttt
 */
public final class VersionUtils {

    private VersionUtils() {
    }

    // ===============
    // = Android SDK =
    // ===============

    /**
     * 获取 SDK 版本
     * @return SDK 版本
     */
    public static int getSDKVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 是否在 2.1 版本及以上
     * @return 是否在 2.1 版本及以上
     */
    public static boolean isEclair() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR;
    }

    /**
     * 是否在 2.2 版本及以上
     * @return 是否在 2.2 版本及以上
     */
    public static boolean isFroyo() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
    }

    /**
     * 是否在 2.3 版本及以上
     * @return 是否在 2.3 版本及以上
     */
    public static boolean isGingerbread() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    /**
     * 是否在 2.3.3 版本及以上
     * @return 是否在 2.3.3 版本及以上
     */
    public static boolean isGingerbreadMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1;
    }

    /**
     * 是否在 3.0 版本及以上
     * @return 是否在 3.0 版本及以上
     */
    public static boolean isHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    /**
     * 是否在 3.1 版本及以上
     * @return 是否在 3.1 版本及以上
     */
    public static boolean isHoneycombMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
    }

    /**
     * 是否在 4.0 版本及以上
     * @return 是否在 4.0 版本及以上
     */
    public static boolean isIceCreamSandwich() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    /**
     * 是否在 4.0.3 版本及以上
     * @return 是否在 4.0.3 版本及以上
     */
    public static boolean isIceCreamSandwichMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1;
    }

    /**
     * 是否在 4.1 版本及以上
     * @return 是否在 4.1 版本及以上
     */
    public static boolean isJellyBean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    /**
     * 是否在 4.2 版本及以上
     * @return 是否在 4.2 版本及以上
     */
    public static boolean isJellyBeanMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
    }

    /**
     * 是否在 4.3 版本及以上
     * @return 是否在 4.3 版本及以上
     */
    public static boolean isJellyBeanMR2() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2;
    }

    /**
     * 是否在 4.4.2 版本及以上
     * @return 是否在 4.4.2 版本及以上
     */
    public static boolean isKitkat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    /**
     * 是否在 4.4W 版本及以上
     * @return 是否在 4.4W 版本及以上
     */
    public static boolean isKitkat_Watch() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH;
    }

    /**
     * 是否在 5.0 版本及以上
     * @return 是否在 5.0 版本及以上
     */
    public static boolean isLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    /**
     * 是否在 5.1 版本及以上
     * @return 是否在 5.1 版本及以上
     */
    public static boolean isLollipop_MR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1;
    }

    /**
     * 是否在 6.0 版本及以上
     * @return 是否在 6.0 版本及以上
     */
    public static boolean isM() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * 是否在 7.0 版本及以上
     * @return 是否在 7.0 版本及以上
     */
    public static boolean isN() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }

    /**
     * 是否在 7.1.1 版本及以上
     * @return 是否在 7.1.1 版本及以上
     */
    public static boolean isN_MR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1;
    }

    /**
     * 是否在 8.0 版本及以上
     * @return 是否在 8.0 版本及以上
     */
    public static boolean isO() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
    }

    /**
     * 是否在 8.1 版本及以上
     * @return 是否在 8.1 版本及以上
     */
    public static boolean isO_MR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1;
    }

    /**
     * 是否在 9.0 版本及以上
     * @return 是否在 9.0 版本及以上
     */
    public static boolean isP() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P;
    }

    /**
     * 是否在 10.0 版本及以上
     * @return 是否在 10.0 版本及以上
     */
    public static boolean isQ() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q;
    }

    /**
     * 是否在 11.0 版本及以上
     * @return 是否在 11.0 版本及以上
     */
    public static boolean isR() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.R;
    }

    /**
     * 是否在 12.0 版本及以上
     * @return 是否在 12.0 版本及以上
     */
    public static boolean isS() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.S;
    }

    /**
     * 是否在 12.0 L 版本及以上
     * @return 是否在 12.0 L 版本及以上
     */
    public static boolean isSV2() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.S_V2;
    }

    /**
     * 是否在 13.0 版本及以上
     * @return 是否在 13.0 版本及以上
     */
    public static boolean isTiramisu() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU;
    }

    /**
     * 是否在 14.0 版本及以上
     * @return 是否在 14.0 版本及以上
     */
    public static boolean isUpsideDownCake() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE;
    }

    /**
     * 是否在 15.0 版本及以上
     * @return 是否在 15.0 版本及以上
     */
    public static boolean isVanillaIceCream() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM;
    }

    /**
     * 是否在 16.0 版本及以上
     * @return 是否在 16.0 版本及以上
     */
    public static boolean isBaklava() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.BAKLAVA;
    }

    /**
     * 是否在 17.0 版本及以上
     * @return 是否在 17.0 版本及以上
     */
    public static boolean isCinnamonBun() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.CINNAMON_BUN;
    }

    /**
     * 转换 SDK 版本 convertSDKVersion(31) = Android 12.0
     * @return SDK 版本
     */
    public static String convertSDKVersion() {
        return convertSDKVersion(Build.VERSION.SDK_INT);
    }

    /**
     * 转换 SDK 版本 convertSDKVersion(31) = Android 12.0
     * @param sdkVersion SDK 版本
     * @return SDK 版本
     */
    public static String convertSDKVersion(final int sdkVersion) {
        switch (sdkVersion) {
            case 1:
                return "Android 1.0";
            case 2:
                return "Android 1.1";
            case 3:
                return "Android 1.5";
            case 4:
                return "Android 1.6";
            case 5:
                return "Android 2.0";
            case 6:
                return "Android 2.0.1";
            case 7:
                return "Android 2.1.x";
            case 8:
                return "Android 2.2.x";
            case 9:
                return "Android 2.3.0-2";
            case 10:
                return "Android 2.3.3-4";
            case 11:
                return "Android 3.0.x";
            case 12:
                return "Android 3.1.x";
            case 13:
                return "Android 3.2";
            case 14:
                return "Android 4.0.0-2";
            case 15:
                return "Android 4.0.3-4";
            case 16:
                return "Android 4.1.x";
            case 17:
                return "Android 4.2.x";
            case 18:
                return "Android 4.3";
            case 19:
                return "Android 4.4";
            case 20:
                return "Android 4.4W";
            case 21:
                return "Android 5.0";
            case 22:
                return "Android 5.1";
            case 23:
                return "Android 6.0";
            case 24:
                return "Android 7.0";
            case 25:
                return "Android 7.1.1";
            case 26:
                return "Android 8.0";
            case 27:
                return "Android 8.1";
            case 28:
                return "Android 9.0";
            case 29:
                return "Android 10.0";
            case 30:
                return "Android 11.0";
            case 31:
                return "Android 12.0";
            case 32:
                return "Android 12L";
            case 33:
                return "Android 13.0";
            case 34:
                return "Android 14.0";
            case 35:
                return "Android 15.0";
            case 36:
                return "Android 16.0";
            case 37:
                return "Android 17.0";
        }
        return "unknown";
    }

    /**
     * 转换 SDK 版本名字 convertSDKVersionName(31) = Android S
     * @return SDK 版本名字
     */
    public static String convertSDKVersionName() {
        return convertSDKVersionName(Build.VERSION.SDK_INT);
    }

    /**
     * 转换 SDK 版本名字 convertSDKVersionName(31) = Android S
     * @param sdkVersion SDK 版本
     * @return SDK 版本名字
     */
    public static String convertSDKVersionName(final int sdkVersion) {
        switch (sdkVersion) {
            case 1:
                return "Android Base";
            case 2:
                return "Android Base_1_1";
            case 3:
                return "Android Cupcake";
            case 4:
                return "Android Donut";
            case 5:
                return "Android Eclair";
            case 6:
                return "Android Eclair_0_1";
            case 7:
                return "Android Eclair_MR1";
            case 8:
                return "Android Froyo";
            case 9:
                return "Android Gingerbread";
            case 10:
                return "Android Gingerbread_MR1";
            case 11:
                return "Android Honeycomb";
            case 12:
                return "Android Honeycomb_MR1";
            case 13:
                return "Android Honeycomb_MR2";
            case 14:
                return "Android Ice_Cream_Sandwich";
            case 15:
                return "Android Ice_Cream_Sandwich_MR1";
            case 16:
                return "Android Jelly_Bean";
            case 17:
                return "Android Jelly_Bean_MR1";
            case 18:
                return "Android Jelly_Bean_MR2";
            case 19:
                return "Android Kitkat";
            case 20:
                return "Android Kitkat_Watch";
            case 21:
                return "Android Lollipop";
            case 22:
                return "Android Lollipop_MR1";
            case 23:
                return "Android M";
            case 24:
                return "Android N";
            case 25:
                return "Android N_MR1";
            case 26:
                return "Android O";
            case 27:
                return "Android O_MR1";
            case 28:
                return "Android P";
            case 29:
                return "Android Q";
            case 30:
                return "Android R";
            case 31:
                return "Android S";
            case 32:
                return "Android S_V2";
            case 33:
                return "Android Tiramisu";
            case 34:
                return "Android UpsideDownCake";
            case 35:
                return "Android VanillaIceCream";
            case 36:
                return "Android Baklava";
            case 37:
                return "Android CinnamonBun";
        }
        return "unknown";
    }

    // ==========
    // = 版本适配 =
    // ==========

    /**
     * 判断 Android 16 大屏方向、定时任务等平台行为是否对当前进程生效
     * 宿主应用 {@link android.content.pm.ApplicationInfo#targetSdkVersion}
     * <p>用于结合 {@link #isBaklava()} 。</p>
     * @param context {@link Context}
     * @return targetSdkVersion，{@code context == null} 或异常时返回 -1
     */
    public static int getTargetSdkVersion(final Context context) {
        try {
            return AppUtils.getApplicationInfo(context).targetSdkVersion;
        } catch (Throwable ignored) {
            return -1;
        }
    }

    /**
     * Android 16+ 主/次版本合并值
     * <pre>
     *     {@link Build.VERSION#SDK_INT_FULL}；低版本或异常时退回 {@link Build.VERSION#SDK_INT}
     * </pre>
     * @see <a href="https://developer.android.com/about/versions/16/features">Android 16 Features</a>
     */
    public static int getSdkIntFullOrSdkInt() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.BAKLAVA) {
            return Build.VERSION.SDK_INT;
        }
        try {
            return Build.VERSION.SDK_INT_FULL;
        } catch (Exception ignored) {
            return Build.VERSION.SDK_INT;
        }
    }

    /**
     * Android 16+：次版本号
     * <pre>
     *     解析 {@link Build.VERSION#SDK_INT_FULL} 的次版本号；低版本或异常时返回 0
     * </pre>
     * @return 次版本号
     */
    public static int getMinorSdkVersionFromSdkIntFullSafe() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.BAKLAVA) {
            return 0;
        }
        try {
            return Build.getMinorSdkVersion(Build.VERSION.SDK_INT_FULL);
        } catch (Exception ignored) {
            return 0;
        }
    }
}