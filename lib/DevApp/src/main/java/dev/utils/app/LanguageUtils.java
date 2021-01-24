package dev.utils.app;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import dev.utils.LogPrintUtils;
import dev.utils.common.StringUtils;

/**
 * detail: 语言工具类
 * @author Ttt
 */
public final class LanguageUtils {

    private LanguageUtils() {
    }

    // 日志 TAG
    private static final String TAG = LanguageUtils.class.getSimpleName();

    /**
     * 获取系统语言
     * @return Locale Language
     */
    public static String getSystemLanguage() {
        Locale locale = getSystemPreferredLanguage();
        return (locale != null) ? locale.getLanguage() : "";
    }

    /**
     * 获取系统语言区域
     * @return Locale Language
     */
    public static String getSystemCountry() {
        Locale locale = getSystemPreferredLanguage();
        return (locale != null) ? locale.getCountry() : "";
    }

    /**
     * 获取系统首选语言
     * @return {@link Locale}
     */
    public static Locale getSystemPreferredLanguage() {
        Locale locale = null;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                //locale = LocaleList.getDefault().get(0);
                locale = ResourceUtils.getConfiguration().getLocales().get(0);
            } else {
                //locale = Locale.getDefault();
                locale = ResourceUtils.getConfiguration().locale;
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getSystemPreferredLanguage");
        }
        return locale;
    }

    /**
     * 修改系统语言 ( APP 多语言, 单独改变 APP 语言 )
     * @param context {@link Context} - Activity
     * @param locale  {@link Locale}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean applyLanguage(
            final Context context,
            final Locale locale
    ) {
        if (context != null && locale != null) {
            try {
                // 获取 res 资源对象
                Resources resources = context.getResources();
                // 获取设置对象
                Configuration config = resources.getConfiguration();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    // apply locale
                    config.setLocale(locale);
                    context.createConfigurationContext(config);
                } else {
                    // updateConfiguration
                    // 获取屏幕参数: 主要是分辨率, 像素等
                    DisplayMetrics displayMetrics = resources.getDisplayMetrics();
                    config.locale = locale;
                    // 更新语言
                    resources.updateConfiguration(config, displayMetrics);
                }
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "applyLanguage");
            }
        }
        return false;
    }

    /**
     * 修改系统语言 (APP 多语言, 单独改变 APP 语言 )
     * @param context  {@link Context}
     * @param language 语言
     * @return {@code true} success, {@code false} fail
     */
    public static boolean applyLanguage(
            final Context context,
            final String language
    ) {
        Locale locale = getSupportLanguage(language);
        if (locale != null) {
            return applyLanguage(context, locale);
        } else { // 如果为 null, 则使用系统默认语言
            return applyLanguage(context, getSystemPreferredLanguage());
        }
    }

    // =

    // 英语
    public static final  String              ENGLISH              = "en";
    // 英语 ( 英式 )
    public static final  String              UK                   = "enGB";
    // 英语 ( 美式 )
    public static final  String              US                   = "enUS";
    // 法语
    public static final  String              FRENCH               = "fr";
    // 德语
    public static final  String              GERMAN               = "de";
    // 日文
    public static final  String              JAPAN                = "jp";
    // 韩文
    public static final  String              KOREA                = "kr";
    // 中文
    public static final  String              CHINESE              = "zh";
    // 简体中文
    public static final  String              SIMPLIFIED_CHINESE   = "zhCN";
    // 繁体中文 ( 默认台湾 )
    public static final  String              TRADITIONAL_CHINESE  = "zhTW";
    // 台湾
    public static final  String              TAIWAN_CHINESE       = TRADITIONAL_CHINESE;
    // 支持的语言字典
    private static final Map<String, Locale> sSupportLanguageMaps = new HashMap<>(20);

    static {
        // 英语
        sSupportLanguageMaps.put(ENGLISH, Locale.ENGLISH);
        // 英语 ( 英式 )
        sSupportLanguageMaps.put(UK, Locale.UK);
        // 英语 ( 美式 )
        sSupportLanguageMaps.put(US, Locale.US);
        // 法语
        sSupportLanguageMaps.put(FRENCH, Locale.FRENCH);
        // 德语
        sSupportLanguageMaps.put(GERMAN, Locale.GERMAN);
        // 日文
        sSupportLanguageMaps.put(JAPAN, Locale.JAPAN);
        // 韩文
        sSupportLanguageMaps.put(KOREA, Locale.KOREA);
        // 中文
        sSupportLanguageMaps.put(CHINESE, Locale.CHINESE);
        // 简体中文
        sSupportLanguageMaps.put(SIMPLIFIED_CHINESE, Locale.SIMPLIFIED_CHINESE);
        // 繁体中文 ( 默认台湾 )
        sSupportLanguageMaps.put(TRADITIONAL_CHINESE, Locale.TRADITIONAL_CHINESE);
        // 台湾
        sSupportLanguageMaps.put(TAIWAN_CHINESE, Locale.TAIWAN);
    }

    /**
     * 获取支持的语言
     * @return {@link Map} 支持的语言
     */
    public static Map<String, Locale> getSupportLanguages() {
        return new HashMap<>(sSupportLanguageMaps);
    }

    /**
     * 添加支持的语言
     * @param language 语言
     * @param locale   {@link Locale}
     */
    public static void putSupportLanguage(
            final String language,
            final Locale locale
    ) {
        sSupportLanguageMaps.put(language, locale);
    }

    /**
     * 移除支持的语言
     * @param language 语言
     */
    public static void removeSupportLanguage(final String language) {
        sSupportLanguageMaps.remove(language);
    }

    /**
     * 是否支持此语言
     * @param language 语言
     * @return {@code true} 支持, {@code false} 不支持
     */
    public static boolean isSupportLanguage(final String language) {
        return sSupportLanguageMaps.containsKey(language);
    }

    /**
     * 获取支持语言
     * @param language 语言
     * @return {@link Locale}
     */
    public static Locale getSupportLanguage(final String language) {
        if (isSupportLanguage(language)) {
            return sSupportLanguageMaps.get(language);
        }
        return null;
    }

    // ===========
    // = 语言判断 =
    // ===========

    /**
     * 判断是否为英文语言环境
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isEn() {
        return isLanguage("en");
    }

    /**
     * 判断是否为中文语言环境
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isZh() {
        return isLanguage("zh");
    }

    /**
     * 判断是否为中文简体语言环境
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isZhCN() {
        return isRegion("CN", "zh");
    }

    /**
     * 判断是否为中文繁体语言环境
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isZhTW() {
        return isRegion("TW", "zh");
    }

    // =

    /**
     * 判断是否为指定语言环境
     * @param language 语言
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isLanguage(final String language) {
        if (StringUtils.isEmpty(language)) return false;
        Locale locale = getSystemPreferredLanguage();
        if (locale != null) {
            String lang = locale.getLanguage();
            return (lang != null && lang.equalsIgnoreCase(language));
        }
        return false;
    }

    /**
     * 判断是否为指定区域语言环境
     * @param region 区域
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isRegion(final String region) {
        if (StringUtils.isEmpty(region)) return false;
        Locale locale = getSystemPreferredLanguage();
        if (locale != null) {
            String country = locale.getCountry();
            return (country != null && country.equalsIgnoreCase(region));
        }
        return false;
    }

    /**
     * 判断是否为指定区域语言环境
     * @param region   区域
     * @param language 语言
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isRegion(
            final String region,
            final String language
    ) {
        if (StringUtils.isEmpty(region)) return false;
        if (StringUtils.isEmpty(language)) return false;
        Locale locale = getSystemPreferredLanguage();
        if (locale != null) {
            String country = locale.getCountry();
            String lang    = locale.getLanguage();
            return (country != null && country.equalsIgnoreCase(region))
                    && (lang != null && lang.equalsIgnoreCase(language));
        }
        return false;
    }
}