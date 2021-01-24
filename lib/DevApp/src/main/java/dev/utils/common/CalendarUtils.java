package dev.utils.common;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import dev.utils.JCLogUtils;

/**
 * detail: 日历工具类
 * @author Ttt
 * <pre>
 *     公历、农历转换
 *     @see <a href="https://github.com/isee15/Lunar-Solar-Calendar-Converter/blob/master/Java/cn/z/LunarSolarConverter.java"/>
 *     关于日历实现代码里 0x04bd8, 0x04ae0, 0x0a570 的解释
 *     @see <a href="https://blog.csdn.net/onlyonecoder/article/details/8484118"/>
 *     Android 农历和节气相关工具类 ( 记录 )
 *     @see <a href="https://my.oschina.net/lanyu96/blog/3059730"/>
 * </pre>
 */
public final class CalendarUtils {

    private CalendarUtils() {
    }

    // 日志 TAG
    private static final String TAG = CalendarUtils.class.getSimpleName();

    // 支持转换的最小农历年份
    public static final int MIN_LUNAR_YEAR = 1900;
    // 支持转换的最小公历年份
    public static final int MIN_SOLAR_YEAR = 1900; // 1901
    // 支持转换的最大公历、农历年份
    public static final int MAX_YEAR       = 2099;

    /**
     * 是否支持农历年份计算
     * @param year 年份
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isSupportLunar(final int year) {
        return year <= MAX_YEAR && year >= MIN_LUNAR_YEAR;
    }

    /**
     * 是否支持公历年份计算
     * @param year 年份
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isSupportSolar(final int year) {
        return year <= MAX_YEAR && year >= MIN_SOLAR_YEAR;
    }

    // ===========
    // = 农历转换 =
    // ===========

    /**
     * 公历转农历
     * @param year  公历年
     * @param month 公历月
     * @param day   公历日
     * @return [0] 农历年 [1] 农历月 [2] 农历日 [3] 是否闰月 0 false、1 true
     */
    public static int[] solarToLunar(
            final int year,
            final int month,
            final int day
    ) {
        // 不支持的公历年份, 则返回 null
        if (!isSupportSolar(year)) return null;

        int[] lunarInt = new int[4];
        int   index    = year - SOLAR[0];
        int   data     = (year << 9) | (month << 5) | (day);
        int   solar11;
        if (SOLAR[index] > data) {
            index--;
        }
        solar11 = SOLAR[index];
        int  y      = getBitInt(solar11, 12, 9);
        int  m      = getBitInt(solar11, 4, 5);
        int  d      = getBitInt(solar11, 5, 0);
        long offset = solarToInt(year, month, day) - solarToInt(y, m, d);

        int days = LUNAR_MONTH_DAYS[index];
        int leap = getBitInt(days, 4, 13);

        int lunarY = index + SOLAR[0];
        int lunarM = 1;
        int lunarD;
        offset += 1;

        for (int i = 0; i < 13; i++) {
            int dm = getBitInt(days, 1, 12 - i) == 1 ? 30 : 29;
            if (offset > dm) {
                lunarM++;
                offset -= dm;
            } else {
                break;
            }
        }
        lunarD = (int) (offset);
        lunarInt[0] = lunarY;
        lunarInt[1] = lunarM;
        lunarInt[3] = 0;

        if (leap != 0 && lunarM > leap) {
            lunarInt[1] = lunarM - 1;
            if (lunarM == leap + 1) {
                lunarInt[3] = 1;
            }
        }
        lunarInt[2] = lunarD;
        return lunarInt;
    }

    /**
     * 农历转公历
     * @param lunarYear  农历年
     * @param lunarMonth 农历月
     * @param lunarDay   农历日
     * @param isLeap     是否闰月
     * @return [0] 公历年 [1] 公历月 [2] 公历日
     */
    public static int[] lunarToSolar(
            final int lunarYear,
            final int lunarMonth,
            final int lunarDay,
            final boolean isLeap
    ) {
        // 不支持的农历年份, 则返回 null
        if (!isSupportLunar(lunarYear)) return null;

        int days   = LUNAR_MONTH_DAYS[lunarYear - LUNAR_MONTH_DAYS[0]];
        int leap   = getBitInt(days, 4, 13);
        int offset = 0;
        int loop   = leap;
        if (!isLeap) {
            if (lunarMonth <= leap || leap == 0) {
                loop = lunarMonth - 1;
            } else {
                loop = lunarMonth;
            }
        }
        for (int i = 0; i < loop; i++) {
            offset += getBitInt(days, 1, 12 - i) == 1 ? 30 : 29;
        }
        offset += lunarDay;

        int solar11 = SOLAR[lunarYear - SOLAR[0]];

        int y = getBitInt(solar11, 12, 9);
        int m = getBitInt(solar11, 4, 5);
        int d = getBitInt(solar11, 5, 0);

        return solarFromInt(solarToInt(y, m, d) + offset - 1);
    }

    // =

    /**
     * 获取农历年份总天数
     * @param year 农历年
     * @return 农历年份总天数
     */
    public static int getLunarYearDays(final int year) {
        if (!isSupportLunar(year)) return 0;
        int i, sum = 348;
        for (i = 0x8000; i > 0x8; i >>= 1) {
            if ((LUNAR_INFO[year - 1900] & i) != 0) {
                sum += 1;
            }
        }
        return (sum + getLunarLeapDays(year));
    }

    /**
     * 获取农历年份闰月天数
     * @param year 农历年
     * @return 农历年份闰月天数
     */
    public static int getLunarLeapDays(final int year) {
        if (!isSupportLunar(year)) return 0;
        if (getLunarLeapMonth(year) != 0) {
            if ((LUNAR_INFO[year - 1899] & 0xf) != 0) {
                return 30;
            } else {
                return 29;
            }
        } else {
            return 0;
        }
    }

    /**
     * 获取农历年份哪个月是闰月
     * <pre>
     *     返回 1 - 12 无闰月返回 0
     * </pre>
     * @param year 农历年
     * @return 农历年份哪个月是闰月
     */
    public static int getLunarLeapMonth(final int year) {
        if (!isSupportLunar(year)) return 0;
        long var = LUNAR_INFO[year - 1900] & 0xf;
        return (int) (var == 0xf ? 0 : var);
    }

    /**
     * 获取农历年份与月份总天数
     * @param year  农历年
     * @param month 农历月
     * @return 农历年份与月份总天数
     */
    public static int getLunarMonthDays(
            final int year,
            final int month
    ) {
        if (!isSupportLunar(year)) return 0;
        if ((LUNAR_INFO[year - 1900] & (0x10000 >> month)) == 0) {
            return 29;
        } else {
            return 30;
        }
    }

    /**
     * 获取干支历
     * @param year 年份
     * @return 干支历
     */
    public static String getLunarGanZhi(final int year) {
        int y = Math.abs(year - 4);
        return TIAN_GAN[y % 10] + DI_ZHI[y % 12];
    }

    /**
     * 获取农历中文月份
     * @param month  农历月
     * @param isLeap 是否闰月
     * @return 农历中文月份
     */
    public static String getLunarMonthChinese(
            final int month,
            final boolean isLeap
    ) {
        if (month > 12 || month < 1) return null;
        return isLeap ? "闰" + CHINESE_NUMBER[month - 1] + "月" : CHINESE_NUMBER[month - 1] + "月";
    }

    /**
     * 获取农历中文天数
     * @param day 天数
     * @return 农历中文天数
     */
    public static String getLunarDayChinese(final int day) {
        if (day > 30 || day < 1) return null;
        if (day == 10) return "初十";
        if (day == 20) return "二十";
        int dayIndex = (day % 10 == 0) ? 9 : day % 10 - 1;
        return CHINESE_TEN[day / 10] + CHINESE_DAY[dayIndex];
    }

    /**
     * 获取二十四节气 ( 公历 ) 索引
     * @param month 公历月
     * @param day   公历天
     * @return 二十四节气 ( 公历 ) 索引
     */
    public static int getSolarTermsIndex(
            final int month,
            final int day
    ) {
        if (month > 12 || month < 1) return -1;
        int   start     = (month - 2) >= 0 ? month - 2 : 11;
        int   leftIndex = start * 2; // 左边节气索引
        int[] dates     = solarTermsDateSplit(leftIndex);
        if (dates != null && day >= dates[0] && day <= dates[1]) return leftIndex;
        int rightIndex = leftIndex + 1; // 右边节气索引
        dates = solarTermsDateSplit(rightIndex);
        if (dates != null && day >= dates[0] && day <= dates[1]) return rightIndex;
        return -1;
    }

    /**
     * 获取二十四节气 ( 公历 )
     * @param month 公历月
     * @param day   公历天
     * @return 二十四节气 ( 公历 )
     */
    public static String getSolarTerms(
            final int month,
            final int day
    ) {
        int index = getSolarTermsIndex(month, day);
        return index != -1 ? SOLAR_TERMS[index] : null;
    }

    /**
     * 获取二十四节气 ( 公历 ) 时间
     * @param month 公历月
     * @param day   公历天
     * @return 二十四节气 ( 公历 ) 时间
     */
    public static String getSolarTermsDate(
            final int month,
            final int day
    ) {
        int index = getSolarTermsIndex(month, day);
        return index != -1 ? SOLAR_TERMS_DATE[index] : null;
    }

    /**
     * 校验是否相同节日
     * @param festival 节日信息
     * @param year     年份
     * @param month    月份
     * @param day      天数
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isFestival(
            final Festival festival,
            final int year,
            final int month,
            final int day
    ) {
        return isFestival(festival, year, month, day, sFestivalHook);
    }

    /**
     * 校验是否相同节日
     * @param festival     节日信息
     * @param year         年份
     * @param month        月份
     * @param day          天数
     * @param festivalHook 节日 Hook 接口
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isFestival(
            final Festival festival,
            final int year,
            final int month,
            final int day,
            final FestivalHook festivalHook
    ) {
        if (festival == null) return false;
        List<Festival> list = new ArrayList<>();
        list.add(festival);
        return getFestival(list, year, month, day, festivalHook) != null;
    }

    /**
     * 获取符合条件的节日信息
     * @param list  节日集合
     * @param year  年份
     * @param month 月份
     * @param day   天数
     * @return {@link Festival}
     */
    public static Festival getFestival(
            final List<Festival> list,
            final int year,
            final int month,
            final int day
    ) {
        return getFestival(list, year, month, day, sFestivalHook);
    }

    /**
     * 获取符合条件的节日信息
     * <pre>
     *     list 不能混合公历、农历节日防止判断出错
     * </pre>
     * @param list         节日集合
     * @param year         年份
     * @param month        月份
     * @param day          天数
     * @param festivalHook 节日 Hook 接口
     * @return {@link Festival}
     */
    public static Festival getFestival(
            final List<Festival> list,
            final int year,
            final int month,
            final int day,
            final FestivalHook festivalHook
    ) {
        if (list == null) return null;
        for (Festival festival : list) {
            if (festival != null) {
                if (festivalHook != null) {
                    Festival hook = festivalHook.hook(festival, year, month, day);
                    if (hook != null) return hook;
                }
                if (festival.isFestival(month, day)) return festival;
            }
        }
        return null;
    }

    /**
     * 获取公历符合条件的节日信息
     * @param year  年份
     * @param month 月份
     * @param day   天数
     * @return {@link Festival}
     */
    public static Festival getSolarFestival(
            final int year,
            final int month,
            final int day
    ) {
        return getFestival(SOLAR_FESTIVAL_LIST, year, month, day);
    }

    /**
     * 获取农历符合条件的节日信息
     * @param year  农历年
     * @param month 农历月
     * @param day   农历日
     * @return {@link Festival}
     */
    public static Festival getLunarFestival(
            final int year,
            final int month,
            final int day
    ) {
        return getFestival(LUNAR_FESTIVAL_LIST, year, month, day);
    }

    // =======
    // = 常量 =
    // =======

    // 天干
    private static final String[]       TIAN_GAN            = {
            "甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"
    };
    // 地支
    private static final String[]       DI_ZHI              = {
            "子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"
    };
    // 中文年份
    private static final String[]       CHINESE_NUMBER      = {
            "正", "二", "三", "四", "五", "六", "七", "八", "九", "十", "冬", "腊"
    };
    // 中文十位数
    private static final String[]       CHINESE_TEN         = {
            "初", "十", "廿", "三"
    };
    // 中文一到十
    private static final String[]       CHINESE_DAY         = {
            "一", "二", "三", "四", "五", "六", "七", "八", "九", "十"
    };
    // 二十四节气 ( 公历 )
    private static final String[]       SOLAR_TERMS         = {
            // 春季 2-4
            "立春", "雨水",
            "惊蛰", "春分",
            "清明", "谷雨",
            // 夏季 5-7
            "立夏", "小满",
            "芒种", "夏至",
            "小暑", "大暑",
            // 秋季 8-10
            "立秋", "处暑",
            "白露", "秋分",
            "寒露", "霜降",
            // 冬季 11-1
            "立冬", "小雪",
            "大雪", "冬至",
            "小寒", "大寒"
    };
    // 二十四节气 ( 公历 ) 时间
    private static final String[]       SOLAR_TERMS_DATE    = {
            // 春季 2-4
            "2.3-5", "2.18-20",
            "3.5-7", "3.20-22",
            "4.4-6", "4.19-21",
            // 夏季 5-7
            "5.5-7", "5.20-22",
            "6.5-7", "6.21-22",
            "7.6-8", "7.22-24",
            // 秋季 8-10
            "8.7-9", "8.22-24",
            "9.7-9", "9.22-24",
            "10.8-9", "10.23-24",
            // 冬季 11-1
            "11.7-8", "11.22-23",
            "12.6-8", "12.21-23",
            "1.5-7", "1.20-21"
    };
    // 部分公历节日集合
    private static final List<Festival> SOLAR_FESTIVAL_LIST = new ArrayList<>();
    // 部分农历节日集合
    private static final List<Festival> LUNAR_FESTIVAL_LIST = new ArrayList<>();

    private static final long[] LUNAR_INFO = {
            0x4bd8, 0x4ae0, 0xa570, 0x54d5, 0xd260, 0xd950, 0x5554, 0x56af, 0x9ad0, 0x55d2,
            0x4ae0, 0xa5b6, 0xa4d0, 0xd250, 0xd255, 0xb54f, 0xd6a0, 0xada2, 0x95b0, 0x4977,
            0x497f, 0xa4b0, 0xb4b5, 0x6a50, 0x6d40, 0xab54, 0x2b6f, 0x9570, 0x52f2, 0x4970,
            0x6566, 0xd4a0, 0xea50, 0x6a95, 0x5adf, 0x2b60, 0x86e3, 0x92ef, 0xc8d7, 0xc95f,
            0xd4a0, 0xd8a6, 0xb55f, 0x56a0, 0xa5b4, 0x25df, 0x92d0, 0xd2b2, 0xa950, 0xb557,
            0x6ca0, 0xb550, 0x5355, 0x4daf, 0xa5b0, 0x4573, 0x52bf, 0xa9a8, 0xe950, 0x6aa0,
            0xaea6, 0xab50, 0x4b60, 0xaae4, 0xa570, 0x5260, 0xf263, 0xd950, 0x5b57, 0x56a0,
            0x96d0, 0x4dd5, 0x4ad0, 0xa4d0, 0xd4d4, 0xd250, 0xd558, 0xb540, 0xb6a0, 0x95a6,
            0x95bf, 0x49b0, 0xa974, 0xa4b0, 0xb27a, 0x6a50, 0x6d40, 0xaf46, 0xab60, 0x9570,
            0x4af5, 0x4970, 0x64b0, 0x74a3, 0xea50, 0x6b58, 0x5ac0, 0xab60, 0x96d5, 0x92e0,
            0xc960, 0xd954, 0xd4a0, 0xda50, 0x7552, 0x56a0, 0xabb7, 0x25d0, 0x92d0, 0xcab5,
            0xa950, 0xb4a0, 0xbaa4, 0xad50, 0x55d9, 0x4ba0, 0xa5b0, 0x5176, 0x52bf, 0xa930,
            0x7954, 0x6aa0, 0xad50, 0x5b52, 0x4b60, 0xa6e6, 0xa4e0, 0xd260, 0xea65, 0xd530,
            0x5aa0, 0x76a3, 0x96d0, 0x4afb, 0x4ad0, 0xa4d0, 0xd0b6, 0xd25f, 0xd520, 0xdd45,
            0xb5a0, 0x56d0, 0x55b2, 0x49b0, 0xa577, 0xa4b0, 0xaa50, 0xb255, 0x6d2f, 0xada0,
            0x4b63, 0x937f, 0x49f8, 0x4970, 0x64b0, 0x68a6, 0xea5f, 0x6b20, 0xa6c4, 0xaaef,
            0x92e0, 0xd2e3, 0xc960, 0xd557, 0xd4a0, 0xda50, 0x5d55, 0x56a0, 0xa6d0, 0x55d4,
            0x52d0, 0xa9b8, 0xa950, 0xb4a0, 0xb6a6, 0xad50, 0x55a0, 0xaba4, 0xa5b0, 0x52b0,
            0xb273, 0x6930, 0x7337, 0x6aa0, 0xad50, 0x4b55, 0x4b6f, 0xa570, 0x54e4, 0xd260,
            0xe968, 0xd520, 0xdaa0, 0x6aa6, 0x56df, 0x4ae0, 0xa9d4, 0xa4d0, 0xd150, 0xf252,
            0xd520
    };

    private static final int[] LUNAR_MONTH_DAYS = {
            1887, 0x1694, 0x16aa, 0x4ad5, 0xab6, 0xc4b7, 0x4ae, 0xa56, 0xb52a, 0x1d2a,
            0xd54, 0x75aa, 0x156a, 0x1096d, 0x95c, 0x14ae, 0xaa4d, 0x1a4c, 0x1b2a, 0x8d55, 0xad4, 0x135a, 0x495d, 0x95c,
            0xd49b, 0x149a, 0x1a4a, 0xbaa5, 0x16a8, 0x1ad4, 0x52da, 0x12b6, 0xe937, 0x92e, 0x1496, 0xb64b, 0xd4a, 0xda8,
            0x95b5, 0x56c, 0x12ae, 0x492f, 0x92e, 0xcc96, 0x1a94, 0x1d4a, 0xada9, 0xb5a, 0x56c, 0x726e, 0x125c, 0xf92d,
            0x192a, 0x1a94, 0xdb4a, 0x16aa, 0xad4, 0x955b, 0x4ba, 0x125a, 0x592b, 0x152a, 0xf695, 0xd94, 0x16aa, 0xaab5,
            0x9b4, 0x14b6, 0x6a57, 0xa56, 0x1152a, 0x1d2a, 0xd54, 0xd5aa, 0x156a, 0x96c, 0x94ae, 0x14ae, 0xa4c, 0x7d26,
            0x1b2a, 0xeb55, 0xad4, 0x12da, 0xa95d, 0x95a, 0x149a, 0x9a4d, 0x1a4a, 0x11aa5, 0x16a8, 0x16d4, 0xd2da,
            0x12b6, 0x936, 0x9497, 0x1496, 0x1564b, 0xd4a, 0xda8, 0xd5b4, 0x156c, 0x12ae, 0xa92f, 0x92e, 0xc96, 0x6d4a,
            0x1d4a, 0x10d65, 0xb58, 0x156c, 0xb26d, 0x125c, 0x192c, 0x9a95, 0x1a94, 0x1b4a, 0x4b55, 0xad4, 0xf55b,
            0x4ba, 0x125a, 0xb92b, 0x152a, 0x1694, 0x96aa, 0x15aa, 0x12ab5, 0x974, 0x14b6, 0xca57, 0xa56, 0x1526,
            0x8e95, 0xd54, 0x15aa, 0x49b5, 0x96c, 0xd4ae, 0x149c, 0x1a4c, 0xbd26, 0x1aa6, 0xb54, 0x6d6a, 0x12da,
            0x1695d, 0x95a, 0x149a, 0xda4b, 0x1a4a, 0x1aa4, 0xbb54, 0x16b4, 0xada, 0x495b, 0x936, 0xf497, 0x1496,
            0x154a, 0xb6a5, 0xda4, 0x15b4, 0x6ab6, 0x126e, 0x1092f, 0x92e, 0xc96, 0xcd4a, 0x1d4a, 0xd64, 0x956c, 0x155c,
            0x125c, 0x792e, 0x192c, 0xfa95, 0x1a94, 0x1b4a, 0xab55, 0xad4, 0x14da, 0x8a5d, 0xa5a, 0x1152b, 0x152a,
            0x1694, 0xd6aa, 0x15aa, 0xab4, 0x94ba, 0x14b6, 0xa56, 0x7527, 0xd26, 0xee53, 0xd54, 0x15aa, 0xa9b5, 0x96c,
            0x14ae, 0x8a4e, 0x1a4c, 0x11d26, 0x1aa4, 0x1b54, 0xcd6a, 0xada, 0x95c, 0x949d, 0x149a, 0x1a2a, 0x5b25,
            0x1aa4, 0xfb52, 0x16b4, 0xaba, 0xa95b, 0x936, 0x1496, 0x9a4b, 0x154a, 0x136a5, 0xda4, 0x15ac
    };

    private static final int[] SOLAR = {
            1887, 0xec04c, 0xec23f, 0xec435, 0xec649, 0xec83e, 0xeca51, 0xecc46, 0xece3a,
            0xed04d, 0xed242, 0xed436, 0xed64a, 0xed83f, 0xeda53, 0xedc48, 0xede3d, 0xee050, 0xee244, 0xee439, 0xee64d,
            0xee842, 0xeea36, 0xeec4a, 0xeee3e, 0xef052, 0xef246, 0xef43a, 0xef64e, 0xef843, 0xefa37, 0xefc4b, 0xefe41,
            0xf0054, 0xf0248, 0xf043c, 0xf0650, 0xf0845, 0xf0a38, 0xf0c4d, 0xf0e42, 0xf1037, 0xf124a, 0xf143e, 0xf1651,
            0xf1846, 0xf1a3a, 0xf1c4e, 0xf1e44, 0xf2038, 0xf224b, 0xf243f, 0xf2653, 0xf2848, 0xf2a3b, 0xf2c4f, 0xf2e45,
            0xf3039, 0xf324d, 0xf3442, 0xf3636, 0xf384a, 0xf3a3d, 0xf3c51, 0xf3e46, 0xf403b, 0xf424e, 0xf4443, 0xf4638,
            0xf484c, 0xf4a3f, 0xf4c52, 0xf4e48, 0xf503c, 0xf524f, 0xf5445, 0xf5639, 0xf584d, 0xf5a42, 0xf5c35, 0xf5e49,
            0xf603e, 0xf6251, 0xf6446, 0xf663b, 0xf684f, 0xf6a43, 0xf6c37, 0xf6e4b, 0xf703f, 0xf7252, 0xf7447, 0xf763c,
            0xf7850, 0xf7a45, 0xf7c39, 0xf7e4d, 0xf8042, 0xf8254, 0xf8449, 0xf863d, 0xf8851, 0xf8a46, 0xf8c3b, 0xf8e4f,
            0xf9044, 0xf9237, 0xf944a, 0xf963f, 0xf9853, 0xf9a47, 0xf9c3c, 0xf9e50, 0xfa045, 0xfa238, 0xfa44c, 0xfa641,
            0xfa836, 0xfaa49, 0xfac3d, 0xfae52, 0xfb047, 0xfb23a, 0xfb44e, 0xfb643, 0xfb837, 0xfba4a, 0xfbc3f, 0xfbe53,
            0xfc048, 0xfc23c, 0xfc450, 0xfc645, 0xfc839, 0xfca4c, 0xfcc41, 0xfce36, 0xfd04a, 0xfd23d, 0xfd451, 0xfd646,
            0xfd83a, 0xfda4d, 0xfdc43, 0xfde37, 0xfe04b, 0xfe23f, 0xfe453, 0xfe648, 0xfe83c, 0xfea4f, 0xfec44, 0xfee38,
            0xff04c, 0xff241, 0xff436, 0xff64a, 0xff83e, 0xffa51, 0xffc46, 0xffe3a, 0x10004e, 0x100242, 0x100437,
            0x10064b, 0x100841, 0x100a53, 0x100c48, 0x100e3c, 0x10104f, 0x101244, 0x101438, 0x10164c, 0x101842,
            0x101a35, 0x101c49, 0x101e3d, 0x102051, 0x102245, 0x10243a, 0x10264e, 0x102843, 0x102a37, 0x102c4b,
            0x102e3f, 0x103053, 0x103247, 0x10343b, 0x10364f, 0x103845, 0x103a38, 0x103c4c, 0x103e42, 0x104036,
            0x104249, 0x10443d, 0x104651, 0x104846, 0x104a3a, 0x104c4e, 0x104e43, 0x105038, 0x10524a, 0x10543e,
            0x105652, 0x105847, 0x105a3b, 0x105c4f, 0x105e45, 0x106039, 0x10624c, 0x106441, 0x106635, 0x106849,
            0x106a3d, 0x106c51, 0x106e47, 0x10703c, 0x10724f, 0x107444, 0x107638, 0x10784c, 0x107a3f, 0x107c53,
            0x107e48
    };

    static {
        // 部分公历节日集合
        SOLAR_FESTIVAL_LIST.clear();
        SOLAR_FESTIVAL_LIST.add(new Festival("元旦", 1, 1));
        SOLAR_FESTIVAL_LIST.add(new Festival("中国人民警察节", 1, 10));
        SOLAR_FESTIVAL_LIST.add(new Festival("情人节", 2, 14));
        SOLAR_FESTIVAL_LIST.add(new Festival("妇女节", 3, 8));
        SOLAR_FESTIVAL_LIST.add(new Festival("植树节", 3, 12));
        SOLAR_FESTIVAL_LIST.add(new Festival("消费者权益日", 3, 15));
        SOLAR_FESTIVAL_LIST.add(new Festival("愚人节", 4, 1));
        SOLAR_FESTIVAL_LIST.add(new Festival("清明节", 4, 4));
        SOLAR_FESTIVAL_LIST.add(new Festival("世界卫生日", 4, 7));
        SOLAR_FESTIVAL_LIST.add(new Festival("地球日", 4, 22));
        SOLAR_FESTIVAL_LIST.add(new Festival("劳动节", 5, 1));
        SOLAR_FESTIVAL_LIST.add(new Festival("青年节", 5, 4));
        SOLAR_FESTIVAL_LIST.add(new Festival("护士节", 5, 12));
        SOLAR_FESTIVAL_LIST.add(new Festival("全国科技工作者日", 5, 30));
        SOLAR_FESTIVAL_LIST.add(new Festival("儿童节", 6, 1));
        SOLAR_FESTIVAL_LIST.add(new Festival("全国爱眼日", 6, 6));
        SOLAR_FESTIVAL_LIST.add(new Festival("国际禁毒日", 6, 26));
        SOLAR_FESTIVAL_LIST.add(new Festival("建党节", 7, 1));
        SOLAR_FESTIVAL_LIST.add(new Festival("中国航海日", 7, 11));
        SOLAR_FESTIVAL_LIST.add(new Festival("建军节", 8, 1));
        SOLAR_FESTIVAL_LIST.add(new Festival("中国医师节", 8, 19));
        SOLAR_FESTIVAL_LIST.add(new Festival("教师节", 9, 10));
        SOLAR_FESTIVAL_LIST.add(new Festival("九一八纪念日", 9, 18));
        SOLAR_FESTIVAL_LIST.add(new Festival("世界阿尔茨海默病日", 9, 21));
        SOLAR_FESTIVAL_LIST.add(new Festival("烈士纪念日", 9, 30));
        SOLAR_FESTIVAL_LIST.add(new Festival("国庆节", 10, 1));
        SOLAR_FESTIVAL_LIST.add(new Festival("全国高血压日", 10, 8));
        SOLAR_FESTIVAL_LIST.add(new Festival("万圣节", 10, 31));
        SOLAR_FESTIVAL_LIST.add(new Festival("记者节", 11, 8));
        SOLAR_FESTIVAL_LIST.add(new Festival("全国消防日", 11, 9));
        SOLAR_FESTIVAL_LIST.add(new Festival("光棍节", 11, 11));
        SOLAR_FESTIVAL_LIST.add(new Festival("国际大学生节", 11, 17));
        SOLAR_FESTIVAL_LIST.add(new Festival("世界艾滋病日", 12, 1));
        SOLAR_FESTIVAL_LIST.add(new Festival("国际残疾人日", 12, 3));
        SOLAR_FESTIVAL_LIST.add(new Festival("世界人权日", 12, 10));
        SOLAR_FESTIVAL_LIST.add(new Festival("平安夜", 12, 24));
        SOLAR_FESTIVAL_LIST.add(new Festival("圣诞节", 12, 25));

        // 部分农历节日集合
        LUNAR_FESTIVAL_LIST.clear();
        LUNAR_FESTIVAL_LIST.add(new Festival("春节", 1, 1, false));
        LUNAR_FESTIVAL_LIST.add(new Festival("元宵节", 1, 15, false));
        LUNAR_FESTIVAL_LIST.add(new Festival("龙抬头", 2, 2, false));
        LUNAR_FESTIVAL_LIST.add(new Festival("上巳节", 3, 3, false));
        LUNAR_FESTIVAL_LIST.add(new Festival("端午节", 5, 5, false));
        LUNAR_FESTIVAL_LIST.add(new Festival("七夕节", 7, 7, false));
        LUNAR_FESTIVAL_LIST.add(new Festival("中元节", 7, 15, false));
        LUNAR_FESTIVAL_LIST.add(new Festival("地藏节", 7, 30, false));
        LUNAR_FESTIVAL_LIST.add(new Festival("灶君诞", 8, 2, false));
        LUNAR_FESTIVAL_LIST.add(new Festival("中秋节", 8, 15, false));
        LUNAR_FESTIVAL_LIST.add(new Festival("先师诞", 8, 27, false));
        LUNAR_FESTIVAL_LIST.add(new Festival("重阳节", 9, 9, false));
        LUNAR_FESTIVAL_LIST.add(new Festival("寒衣节", 10, 1, false));
        LUNAR_FESTIVAL_LIST.add(new Festival("下元节", 10, 15, false));
        LUNAR_FESTIVAL_LIST.add(new Festival("腊八节", 12, 8, false));
        LUNAR_FESTIVAL_LIST.add(new Festival("小年", 12, 23, false));
        // LUNAR_FESTIVAL_LIST.add(new Festival("除夕", 12, 30, false)); // 除夕得判断是 29 还是 30 需要特殊判断
    }

    // ===========
    // = 内部方法 =
    // ===========

    private static int getBitInt(
            int data,
            int length,
            int shift
    ) {
        return (data & (((1 << length) - 1) << shift)) >> shift;
    }

    private static long solarToInt(
            int y,
            int m,
            int d
    ) {
        m = (m + 9) % 12;
        y = y - m / 10;
        return 365 * y + y / 4 - y / 100 + y / 400 + (m * 306 + 5) / 10 + (d - 1);
    }

    private static int[] solarFromInt(long g) {
        long y   = (10000 * g + 14780) / 3652425;
        long ddd = g - (365 * y + y / 4 - y / 100 + y / 400);
        if (ddd < 0) {
            y--;
            ddd = g - (365 * y + y / 4 - y / 100 + y / 400);
        }
        long mi = (100 * ddd + 52) / 3060;
        long mm = (mi + 2) % 12 + 1;
        y = y + (mi + 2) / 12;
        long  dd    = ddd - (mi * 306 + 5) / 10 + 1;
        int[] solar = new int[3];
        solar[0] = (int) y;
        solar[1] = (int) mm;
        solar[2] = (int) dd;
        return solar;
    }

    /**
     * 拆分二十四节气 ( 公历 ) 时间
     * @param index 二十四节气索引
     * @return [0] 开始日 [1] 结束日
     */
    private static int[] solarTermsDateSplit(final int index) {
        try {
            String   date   = SOLAR_TERMS_DATE[index];
            String[] splits = date.substring(date.indexOf('.') + 1).split("-");
            return new int[]{
                    ConvertUtils.toInt(splits[0]),
                    ConvertUtils.toInt(splits[1])
            };
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "solarTermsDateSplit");
        }
        return null;
    }

    // =========
    // = 实体类 =
    // =========

    /**
     * detail: 公历农历实体类
     * @author Ttt
     */
    public static class SolarLunar {

        // 公历年
        public final int     year;
        // 公历月
        public final int     month;
        // 公历日
        public final int     day;
        // 公历农历互转是否成功
        public final boolean result;
        // 农历年
        public final int     lunarYear;
        // 农历月
        public final int     lunarMonth;
        // 农历日
        public final int     lunarDay;
        // 农历月是否闰月
        public final boolean isLunarLeap;

        /**
         * 公历转农历
         * @param year  公历年
         * @param month 公历月
         * @param day   公历日
         */
        public SolarLunar(
                int year,
                int month,
                int day
        ) {
            this.year = year;
            this.month = month;
            this.day = day;
            // 公历转农历
            int[] lunars = CalendarUtils.solarToLunar(year, month, day);
            result = lunars != null;
            if (result) {
                lunarYear = lunars[0];
                lunarMonth = lunars[1];
                lunarDay = lunars[2];
                isLunarLeap = lunars[3] == 1;
            } else {
                lunarYear = 0;
                lunarMonth = 0;
                lunarDay = 0;
                isLunarLeap = false;
            }
        }

        /**
         * 农历转公历
         * @param lunarYear   农历年
         * @param lunarMonth  农历月
         * @param lunarDay    农历日
         * @param isLunarLeap 是否闰月
         */
        public SolarLunar(
                int lunarYear,
                int lunarMonth,
                int lunarDay,
                boolean isLunarLeap
        ) {
            this.lunarYear = lunarYear;
            this.lunarMonth = lunarMonth;
            this.lunarDay = lunarDay;
            this.isLunarLeap = isLunarLeap;
            // 农历转公历
            int[] solars = CalendarUtils.lunarToSolar(lunarYear, lunarMonth, lunarDay, isLunarLeap);
            result = solars != null;
            if (result) {
                year = solars[0];
                month = solars[1];
                day = solars[2];
            } else {
                year = 0;
                month = 0;
                day = 0;
            }
        }

        /**
         * 获取农历年份总天数
         * @return 农历年份总天数
         */
        public int getLunarYearDays() {
            return CalendarUtils.getLunarYearDays(lunarYear);
        }

        /**
         * 获取农历年份闰月天数
         * @return 农历年份闰月天数
         */
        public int getLunarLeapDays() {
            return CalendarUtils.getLunarLeapDays(lunarYear);
        }

        /**
         * 获取农历闰月
         * @return 农历闰月
         */
        public int getLunarLeapMonth() {
            return isLunarLeap ? lunarMonth : 0;
        }

        /**
         * 获取农历年份与月份总天数
         * @return 农历年份与月份总天数
         */
        public int getLunarMonthDays() {
            return CalendarUtils.getLunarMonthDays(lunarYear, lunarMonth);
        }

        /**
         * 获取干支历
         * @return 干支历
         */
        public String getLunarGanZhi() {
            return CalendarUtils.getLunarGanZhi(lunarYear);
        }

        /**
         * 获取农历中文月份
         * @return 农历中文月份
         */
        public String getLunarMonthChinese() {
            return CalendarUtils.getLunarMonthChinese(lunarMonth, isLunarLeap);
        }

        /**
         * 获取农历中文天数
         * @return 农历中文天数
         */
        public String getLunarDayChinese() {
            return CalendarUtils.getLunarDayChinese(lunarDay);
        }

        /**
         * 获取二十四节气 ( 公历 ) 索引
         * @return 二十四节气 ( 公历 ) 索引
         */
        public int getSolarTermsIndex() {
            return CalendarUtils.getSolarTermsIndex(month, day);
        }

        /**
         * 获取二十四节气 ( 公历 )
         * @return 二十四节气 ( 公历 )
         */
        public String getSolarTerms() {
            return CalendarUtils.getSolarTerms(month, day);
        }

        /**
         * 获取二十四节气 ( 公历 ) 时间
         * @return 二十四节气 ( 公历 ) 时间
         */
        public String getSolarTermsDate() {
            return CalendarUtils.getSolarTermsDate(month, day);
        }
    }

    // =

    /**
     * detail: 节日实体类
     * @author Ttt
     */
    public static class Festival
            implements Comparable<Festival> {

        // 节日名
        public final  String  name;
        // 节日月份
        public final  int     month;
        // 节日天数
        public final  int     day;
        // 是否公历节日
        public final  boolean isSolarFestival;
        // 内部排序值
        private final int     compareValue;

        public Festival(
                String name,
                int month,
                int day
        ) {
            this(name, month, day, true);
        }

        public Festival(
                String name,
                int month,
                int day,
                boolean isSolarFestival
        ) {
            this.name = name;
            this.month = month;
            this.day = day;
            this.isSolarFestival = isSolarFestival;
            this.compareValue = ConvertUtils.toInt(month + NumberUtils.addZero(day));
        }

        /**
         * 校验是否相同节日
         * @param festival {@link Festival}
         * @return {@code true} yes, {@code false} no
         */
        public boolean isFestival(final Festival festival) {
            if (festival == null) return false;
            return isFestival(festival.month, festival.day, festival.isSolarFestival);
        }

        /**
         * 校验是否相同节日
         * @param month 月份
         * @param day   天数
         * @return {@code true} yes, {@code false} no
         */
        public boolean isFestival(
                final int month,
                final int day
        ) {
            return this.month == month && this.day == day;
        }

        /**
         * 校验是否相同节日
         * @param month         月份
         * @param day           天数
         * @param solarFestival 是否公历节日
         * @return {@code true} yes, {@code false} no
         */
        public boolean isFestival(
                final int month,
                final int day,
                final boolean solarFestival
        ) {
            return this.month == month && this.day == day && this.isSolarFestival == solarFestival;
        }

        @Override
        public int compareTo(Festival festival) {
            int value  = compareValue;
            int value1 = festival.compareValue;
            if (value < value1) return 1;
            if (value > value1) return -1;
            return 0;
        }

        @Override
        public String toString() {
            return StringUtils.checkValue(name) + " " + NumberUtils.addZero(month)
                    + NumberUtils.addZero(day);
        }
    }

    // =======
    // = 接口 =
    // =======

    // 节日 Hook 接口
    private static FestivalHook sFestivalHook = new FestivalHook() {
        @Override
        public Festival hook(
                Festival festival,
                int year,
                int month,
                int day
        ) {
            if (festival != null) {
                if (festival.isSolarFestival) { // 公历节日
                    Calendar calendar;
                    int      monthDay; // 该月存在天数
                    int      sundays = 0; // 星期天累加次数
                    // 月份判断
                    switch (month) {
                        case 5:
                            calendar = Calendar.getInstance();
                            calendar.set(Calendar.YEAR, year);
                            calendar.set(Calendar.MONTH, month - 1);
                            monthDay = calendar.getActualMaximum(Calendar.DATE);
                            for (int i = 1; i <= monthDay; i++) {
                                calendar.set(Calendar.DATE, i);
                                if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                                    sundays++;
                                    if (sundays == 2) {
                                        if (i == day) { // 母亲节每年 5 月的第二个星期日
                                            return new Festival("母亲节", month, day, true);
                                        }
                                        return null;
                                    }
                                }
                            }
                            break;
                        case 6:
                            calendar = Calendar.getInstance();
                            calendar.set(Calendar.YEAR, year);
                            calendar.set(Calendar.MONTH, month - 1);
                            monthDay = calendar.getActualMaximum(Calendar.DATE);
                            for (int i = 1; i <= monthDay; i++) {
                                calendar.set(Calendar.DATE, i);
                                if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                                    sundays++;
                                    if (sundays == 3) {
                                        if (i == day) { // 父亲节最广泛的日期在每年 6 月的第三个星期日
                                            return new Festival("父亲节", month, day, true);
                                        }
                                        return null;
                                    }
                                }
                            }
                            break;
                    }
                } else { // 农历节日
                    switch (month) {
                        case 12: // 腊月
                            if (CalendarUtils.getLunarMonthDays(year, month) == day && day != 0) {
                                return new Festival("除夕", month, day, false);
                            }
                            break;
                    }
                }
            }
            return null;
        }
    };

    /**
     * detail: 节日 Hook 接口
     * @author Ttt
     * <pre>
     *     主要用于特殊节日判断, 如 除夕 非确定天数无法直接进行添加
     * </pre>
     */
    public interface FestivalHook {

        /**
         * 节日 hook 判断
         * @param festival 节日信息
         * @param year     年份
         * @param month    月份
         * @param day      天数
         * @return 如果特殊判断成功则返回节日
         */
        Festival hook(
                Festival festival,
                int year,
                int month,
                int day
        );
    }

    /**
     * 获取节日 Hook 接口
     * @return {@link FestivalHook}
     */
    public static FestivalHook getFestivalHook() {
        return sFestivalHook;
    }

    /**
     * 设置节日 Hook 接口
     * @param festivalHook {@link FestivalHook}
     */
    public static void setFestivalHook(final FestivalHook festivalHook) {
        CalendarUtils.sFestivalHook = festivalHook;
    }
}