package dev.utils.app;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.Properties;

import dev.utils.common.CloseUtils;

/**
 * detail: ROM 相关工具类
 * @author Ttt
 */
public final class ROMUtils {

    private ROMUtils() {
    }

    // ===============
    // = ROM 标识信息 =
    // ===============

    private static final String[] ROM_HUAWEI    = {"huawei"};
    private static final String[] ROM_VIVO      = {"vivo"};
    private static final String[] ROM_XIAOMI    = {"xiaomi"};
    private static final String[] ROM_OPPO      = {"oppo"};
    private static final String[] ROM_LEECO     = {"leeco", "letv"};
    private static final String[] ROM_360       = {"360", "qiku"};
    private static final String[] ROM_ZTE       = {"zte"};
    private static final String[] ROM_ONEPLUS   = {"oneplus"};
    private static final String[] ROM_NUBIA     = {"nubia"};
    private static final String[] ROM_COOLPAD   = {"coolpad", "yulong"};
    private static final String[] ROM_LG        = {"lg", "lge"};
    private static final String[] ROM_GOOGLE    = {"google"};
    private static final String[] ROM_SAMSUNG   = {"samsung"};
    private static final String[] ROM_MEIZU     = {"meizu"};
    private static final String[] ROM_LENOVO    = {"lenovo"};
    private static final String[] ROM_SMARTISAN = {"smartisan", "deltainno"};
    private static final String[] ROM_HTC       = {"htc"};
    private static final String[] ROM_SONY      = {"sony"};
    private static final String[] ROM_GIONEE    = {"gionee", "amigo"};
    private static final String[] ROM_MOTOROLA  = {"motorola"};

    private static final String VERSION_PROPERTY_HUAWEI  = "ro.build.version.emui";
    private static final String VERSION_PROPERTY_VIVO    = "ro.vivo.os.build.display.id";
    private static final String VERSION_PROPERTY_XIAOMI  = "ro.build.version.incremental";
    private static final String VERSION_PROPERTY_OPPO    = "ro.build.version.opporom";
    private static final String VERSION_PROPERTY_LEECO   = "ro.letv.release.version";
    private static final String VERSION_PROPERTY_360     = "ro.build.uiversion";
    private static final String VERSION_PROPERTY_ZTE     = "ro.build.MiFavor_version";
    private static final String VERSION_PROPERTY_ONEPLUS = "ro.rom.version";
    private static final String VERSION_PROPERTY_NUBIA   = "ro.build.rom.id";

    /**
     * 判断 ROM 是否 Huawei ( 华为 )
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isHuawei() {
        return ROM_HUAWEI[0].equals(getRomInfo().name);
    }

    /**
     * 判断 ROM 是否 Vivo ( VIVO )
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isVivo() {
        return ROM_VIVO[0].equals(getRomInfo().name);
    }

    /**
     * 判断 ROM 是否 Xiaomi ( 小米 )
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isXiaomi() {
        return ROM_XIAOMI[0].equals(getRomInfo().name);
    }

    /**
     * 判断 ROM 是否 Oppo ( OPPO )
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isOppo() {
        return ROM_OPPO[0].equals(getRomInfo().name);
    }

    /**
     * 判断 ROM 是否 Leeco ( 乐视 )
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isLeeco() {
        return ROM_LEECO[0].equals(getRomInfo().name);
    }

    /**
     * 判断 ROM 是否 360 ( 360 )
     * @return {@code true} yes, {@code false} no
     */
    public static boolean is360() {
        return ROM_360[0].equals(getRomInfo().name);
    }

    /**
     * 判断 ROM 是否 Zte ( 中兴 )
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isZte() {
        return ROM_ZTE[0].equals(getRomInfo().name);
    }

    /**
     * 判断 ROM 是否 Oneplus ( 一加 )
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isOneplus() {
        return ROM_ONEPLUS[0].equals(getRomInfo().name);
    }

    /**
     * 判断 ROM 是否 Nubia ( 努比亚 )
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNubia() {
        return ROM_NUBIA[0].equals(getRomInfo().name);
    }

    /**
     * 判断 ROM 是否 Coolpad ( 酷派 )
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isCoolpad() {
        return ROM_COOLPAD[0].equals(getRomInfo().name);
    }

    /**
     * 判断 ROM 是否 Lg ( LG )
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isLg() {
        return ROM_LG[0].equals(getRomInfo().name);
    }

    /**
     * 判断 ROM 是否 Google ( 谷歌 )
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isGoogle() {
        return ROM_GOOGLE[0].equals(getRomInfo().name);
    }

    /**
     * 判断 ROM 是否 Samsung ( 三星 )
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isSamsung() {
        return ROM_SAMSUNG[0].equals(getRomInfo().name);
    }

    /**
     * 判断 ROM 是否 Meizu ( 魅族 )
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isMeizu() {
        return ROM_MEIZU[0].equals(getRomInfo().name);
    }

    /**
     * 判断 ROM 是否 Lenovo ( 联想 )
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isLenovo() {
        return ROM_LENOVO[0].equals(getRomInfo().name);
    }

    /**
     * 判断 ROM 是否 Smartisan ( 锤子 )
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isSmartisan() {
        return ROM_SMARTISAN[0].equals(getRomInfo().name);
    }

    /**
     * 判断 ROM 是否 Htc ( HTC )
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isHtc() {
        return ROM_HTC[0].equals(getRomInfo().name);
    }

    /**
     * 判断 ROM 是否 Sony ( 索尼 )
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isSony() {
        return ROM_SONY[0].equals(getRomInfo().name);
    }

    /**
     * 判断 ROM 是否 Gionee ( 金立 )
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isGionee() {
        return ROM_GIONEE[0].equals(getRomInfo().name);
    }

    /**
     * 判断 ROM 是否 Motorola ( 摩托罗拉 )
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isMotorola() {
        return ROM_MOTOROLA[0].equals(getRomInfo().name);
    }

    /**
     * 获取 ROM 信息
     * @return {@link RomInfo}
     */
    public static RomInfo getRomInfo() {
        if (sBean != null) return sBean;
        sBean = _getRomInfo();
        return sBean;
    }

    // ===========
    // = 内部方法 =
    // ===========

    private static final String UNKNOWN = "unknown";

    /**
     * 是否匹配正确 ROM
     * @param brand        产品 / 硬件品牌信息
     * @param manufacturer 产品 / 硬件制造商信息
     * @param names        品牌名称集合
     * @return {@code true} yes, {@code false} no
     */
    private static boolean isRightRom(
            final String brand,
            final String manufacturer,
            final String... names
    ) {
        for (String name : names) {
            if (brand.contains(name) || manufacturer.contains(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取产品 / 硬件制造商信息
     * @return 产品 / 硬件制造商信息
     */
    private static String getManufacturer() {
        try {
            String manufacturer = Build.MANUFACTURER;
            if (!TextUtils.isEmpty(manufacturer)) {
                return manufacturer.toLowerCase();
            }
        } catch (Throwable ignore) {
        }
        return UNKNOWN;
    }

    /**
     * 获取产品 / 硬件品牌信息
     * @return 产品 / 硬件品牌信息
     */
    private static String getBrand() {
        try {
            String brand = Build.BRAND;
            if (!TextUtils.isEmpty(brand)) {
                return brand.toLowerCase();
            }
        } catch (Throwable ignore) {
        }
        return UNKNOWN;
    }

    /**
     * 获取 ROM 版本信息
     * @param propertyName 属性名
     * @return ROM 版本信息
     */
    private static String getRomVersion(final String propertyName) {
        String ret = "";
        if (!TextUtils.isEmpty(propertyName)) {
            ret = getSystemProperty(propertyName);
        }
        if (TextUtils.isEmpty(ret) || ret.equals(UNKNOWN)) {
            try {
                String display = Build.DISPLAY;
                if (!TextUtils.isEmpty(display)) {
                    ret = display.toLowerCase();
                }
            } catch (Throwable ignore) {
            }
        }
        if (TextUtils.isEmpty(ret)) {
            return UNKNOWN;
        }
        return ret;
    }

    /**
     * 获取 system prop 文件指定属性信息
     * @param name 属性名
     * @return system prop 文件指定属性信息
     */
    private static String getSystemProperty(final String name) {
        String prop = getSystemPropertyByShell(name);
        if (!TextUtils.isEmpty(prop)) return prop;
        prop = getSystemPropertyByStream(name);
        if (!TextUtils.isEmpty(prop)) return prop;
        if (Build.VERSION.SDK_INT < 28) {
            return getSystemPropertyByReflect(name);
        }
        return prop;
    }

    /**
     * 通过 shell 方式获取 system prop 文件指定属性信息
     * @param propName 属性名
     * @return system prop 文件指定属性信息
     */
    private static String getSystemPropertyByShell(final String propName) {
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            String ret = input.readLine();
            if (ret != null) {
                return ret;
            }
        } catch (Throwable ignore) {
        } finally {
            CloseUtils.closeIOQuietly(input);
        }
        return "";
    }

    /**
     * 获取 system prop 文件指定属性信息
     * @param key 属性 key
     * @return system prop 文件指定属性信息
     */
    private static String getSystemPropertyByStream(final String key) {
        try {
            Properties prop = new Properties();
            FileInputStream is = new FileInputStream(
                    new File(Environment.getRootDirectory(), "build.prop")
            );
            prop.load(is);
            return prop.getProperty(key, "");
        } catch (Throwable ignore) {
        }
        return "";
    }

    /**
     * 获取 system prop 文件指定属性信息
     * @param key 属性 key
     * @return system prop 文件指定属性信息
     */
    private static String getSystemPropertyByReflect(final String key) {
        try {
            @SuppressLint("PrivateApi")
            Class<?> clz = Class.forName("android.os.SystemProperties");
            Method getMethod = clz.getMethod("get", String.class, String.class);
            return (String) getMethod.invoke(clz, key, "");
        } catch (Throwable e) {
        }
        return "";
    }

    // =========
    // = 实体类 =
    // =========

    private static RomInfo sBean = null;

    /**
     * detail: ROM 信息实体类
     * @author Ttt
     */
    public static class RomInfo {

        private String name;
        private String version;

        /**
         * 获取 ROM 名称
         * @return ROM 名称
         */
        public String getName() {
            return name;
        }

        /**
         * 获取 ROM 版本信息
         * @return ROM 版本信息
         */
        public String getVersion() {
            return version;
        }

        @Override
        public String toString() {
            return "RomInfo{name=" + name + ", version=" + version + "}";
        }
    }

    /**
     * 获取 ROM 信息
     * @return {@link RomInfo}
     */
    private static RomInfo _getRomInfo() {
        RomInfo      bean         = new RomInfo();
        final String brand        = getBrand();
        final String manufacturer = getManufacturer();
        if (isRightRom(brand, manufacturer, ROM_HUAWEI)) {
            bean.name = ROM_HUAWEI[0];
            String   version = getRomVersion(VERSION_PROPERTY_HUAWEI);
            String[] temp    = version.split("_");
            if (temp.length > 1) {
                bean.version = temp[1];
            } else {
                bean.version = version;
            }
            return bean;
        }
        if (isRightRom(brand, manufacturer, ROM_VIVO)) {
            bean.name = ROM_VIVO[0];
            bean.version = getRomVersion(VERSION_PROPERTY_VIVO);
            return bean;
        }
        if (isRightRom(brand, manufacturer, ROM_XIAOMI)) {
            bean.name = ROM_XIAOMI[0];
            bean.version = getRomVersion(VERSION_PROPERTY_XIAOMI);
            return bean;
        }
        if (isRightRom(brand, manufacturer, ROM_OPPO)) {
            bean.name = ROM_OPPO[0];
            bean.version = getRomVersion(VERSION_PROPERTY_OPPO);
            return bean;
        }
        if (isRightRom(brand, manufacturer, ROM_LEECO)) {
            bean.name = ROM_LEECO[0];
            bean.version = getRomVersion(VERSION_PROPERTY_LEECO);
            return bean;
        }
        if (isRightRom(brand, manufacturer, ROM_360)) {
            bean.name = ROM_360[0];
            bean.version = getRomVersion(VERSION_PROPERTY_360);
            return bean;
        }
        if (isRightRom(brand, manufacturer, ROM_ZTE)) {
            bean.name = ROM_ZTE[0];
            bean.version = getRomVersion(VERSION_PROPERTY_ZTE);
            return bean;
        }
        if (isRightRom(brand, manufacturer, ROM_ONEPLUS)) {
            bean.name = ROM_ONEPLUS[0];
            bean.version = getRomVersion(VERSION_PROPERTY_ONEPLUS);
            return bean;
        }
        if (isRightRom(brand, manufacturer, ROM_NUBIA)) {
            bean.name = ROM_NUBIA[0];
            bean.version = getRomVersion(VERSION_PROPERTY_NUBIA);
            return bean;
        }
        if (isRightRom(brand, manufacturer, ROM_COOLPAD)) {
            bean.name = ROM_COOLPAD[0];
        } else if (isRightRom(brand, manufacturer, ROM_LG)) {
            bean.name = ROM_LG[0];
        } else if (isRightRom(brand, manufacturer, ROM_GOOGLE)) {
            bean.name = ROM_GOOGLE[0];
        } else if (isRightRom(brand, manufacturer, ROM_SAMSUNG)) {
            bean.name = ROM_SAMSUNG[0];
        } else if (isRightRom(brand, manufacturer, ROM_MEIZU)) {
            bean.name = ROM_MEIZU[0];
        } else if (isRightRom(brand, manufacturer, ROM_LENOVO)) {
            bean.name = ROM_LENOVO[0];
        } else if (isRightRom(brand, manufacturer, ROM_SMARTISAN)) {
            bean.name = ROM_SMARTISAN[0];
        } else if (isRightRom(brand, manufacturer, ROM_HTC)) {
            bean.name = ROM_HTC[0];
        } else if (isRightRom(brand, manufacturer, ROM_SONY)) {
            bean.name = ROM_SONY[0];
        } else if (isRightRom(brand, manufacturer, ROM_GIONEE)) {
            bean.name = ROM_GIONEE[0];
        } else if (isRightRom(brand, manufacturer, ROM_MOTOROLA)) {
            bean.name = ROM_MOTOROLA[0];
        } else {
            bean.name = manufacturer;
        }
        bean.version = getRomVersion("");
        return bean;
    }
}