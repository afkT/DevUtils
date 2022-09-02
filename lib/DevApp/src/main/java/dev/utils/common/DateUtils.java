package dev.utils.common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dev.utils.DevFinal;
import dev.utils.JCLogUtils;

/**
 * detail: 日期工具类
 * @author Ttt
 */
public final class DateUtils {

    private DateUtils() {
    }

    // 日志 TAG
    private static final String TAG = DateUtils.class.getSimpleName();

    // 线程安全 SimpleDateFormat Map
    private static final ThreadLocal<Map<String, SimpleDateFormat>> SDEF_THREAD_LOCAL
            = new ThreadLocal<Map<String, SimpleDateFormat>>() {
        @Override
        protected Map<String, SimpleDateFormat> initialValue() {
            return new HashMap<>();
        }
    };

    /**
     * 获取默认 SimpleDateFormat ( yyyy-MM-dd HH:mm:ss )
     * @return {@link SimpleDateFormat}
     */
    public static SimpleDateFormat getDefaultFormat() {
        return getSafeDateFormat(DevFinal.TIME.yyyyMMddHHmmss_HYPHEN);
    }

    /**
     * 获取对应时间格式线程安全 SimpleDateFormat
     * @param pattern 时间格式
     * @return {@link SimpleDateFormat}
     */
    public static SimpleDateFormat getSafeDateFormat(final String pattern) {
        if (pattern == null) return null;
        Map<String, SimpleDateFormat> sdfMap = SDEF_THREAD_LOCAL.get();
        SimpleDateFormat              format = sdfMap.get(pattern);
        if (format == null) {
            format = new SimpleDateFormat(pattern);
            sdfMap.put(pattern, format);
        }
        return format;
    }

    // =

    /**
     * 获取 Calendar
     * @return {@link Calendar}
     */
    public static Calendar getCalendar() {
        return Calendar.getInstance();
    }

    /**
     * 获取 Calendar
     * @param millis 时间毫秒
     * @return {@link Calendar}
     */
    public static Calendar getCalendar(final long millis) {
        if (millis == -1L) return null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar;
    }

    /**
     * 获取 Calendar
     * @param date 日期
     * @return {@link Calendar}
     */
    public static Calendar getCalendar(final Date date) {
        if (date == null) return null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * 获取 Calendar
     * @param time 时间
     * @return {@link Calendar}
     */
    public static Calendar getCalendar(final String time) {
        return getCalendar(parseLong(time, getDefaultFormat()));
    }

    /**
     * 获取 Calendar
     * @param time    时间
     * @param pattern 时间格式
     * @return {@link Calendar}
     */
    public static Calendar getCalendar(
            final String time,
            final String pattern
    ) {
        return getCalendar(parseLong(time, getSafeDateFormat(pattern)));
    }

    /**
     * 获取 Calendar
     * @param time   时间
     * @param format {@link SimpleDateFormat}
     * @return {@link Calendar}
     */
    public static Calendar getCalendar(
            final String time,
            final SimpleDateFormat format
    ) {
        return getCalendar(parseLong(time, format));
    }

    // =

    /**
     * 获取当前时间 Date
     * @return 当前时间 Date
     */
    public static Date getCurrentTime() {
        return Calendar.getInstance().getTime();
    }

    /**
     * 获取当前时间毫秒
     * @return 当前时间毫秒
     */
    public static long getCurrentTimeMillis() {
        return getDateTime(Calendar.getInstance().getTime());
    }

    /**
     * 获取 Date Time
     * @param date 日期
     * @return Date Time
     */
    public static long getDateTime(final Date date) {
        if (date != null) return date.getTime();
        return -1L;
    }

    // =

    /**
     * 获取当前时间的字符串
     * @return 当前时间的字符串
     */
    public static String getDateNow() {
        return formatTime(getCurrentTimeMillis(), getDefaultFormat());
    }

    /**
     * 获取当前时间的字符串
     * @param pattern 时间格式
     * @return 当前时间的字符串
     */
    public static String getDateNow(final String pattern) {
        return formatTime(getCurrentTimeMillis(), getSafeDateFormat(pattern));
    }

    /**
     * 获取当前时间的字符串
     * @param format {@link SimpleDateFormat}
     * @return 当前时间的字符串
     */
    public static String getDateNow(final SimpleDateFormat format) {
        return formatTime(getCurrentTimeMillis(), format);
    }

    // =

    /**
     * 将 Date 转换日期字符串
     * @param date 日期
     * @return 按照指定格式的日期字符串
     */
    public static String formatDate(final Date date) {
        return formatTime(getDateTime(date), getDefaultFormat());
    }

    /**
     * 将 Date 转换日期字符串
     * @param date    日期
     * @param pattern 时间格式
     * @return 按照指定格式的日期字符串
     */
    public static String formatDate(
            final Date date,
            final String pattern
    ) {
        return formatTime(getDateTime(date), getSafeDateFormat(pattern));
    }

    /**
     * 将 Date 转换日期字符串
     * @param date   日期
     * @param format {@link SimpleDateFormat}
     * @return 按照指定格式的日期字符串
     */
    public static String formatDate(
            final Date date,
            final SimpleDateFormat format
    ) {
        return formatTime(getDateTime(date), format);
    }

    // =

    /**
     * 将时间毫秒转换日期字符串
     * @param millis 时间毫秒
     * @return 按照指定格式的日期字符串
     */
    public static String formatTime(final long millis) {
        return formatTime(millis, getDefaultFormat());
    }

    /**
     * 将时间毫秒转换日期字符串
     * @param millis  时间毫秒
     * @param pattern 时间格式
     * @return 按照指定格式的日期字符串
     */
    public static String formatTime(
            final long millis,
            final String pattern
    ) {
        return formatTime(millis, getSafeDateFormat(pattern));
    }

    /**
     * 将时间毫秒转换日期字符串
     * @param millis 时间毫秒
     * @param format {@link SimpleDateFormat}
     * @return 按照指定格式的日期字符串
     */
    public static String formatTime(
            final long millis,
            final SimpleDateFormat format
    ) {
        if (millis == -1L || format == null) return null;
        try {
            return format.format(millis);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "formatTime");
        }
        return null;
    }

    // =

    /**
     * 将时间毫秒转换成 Date
     * @param millis 时间毫秒
     * @return {@link Date}
     */
    public static Date parseDate(final long millis) {
        if (millis == -1L) return null;
        return new Date(millis);
    }

    /**
     * 解析时间字符串转换为 Date
     * @param time 时间
     * @return {@link Date}
     */
    public static Date parseDate(final String time) {
        return parseDate(parseLong(time, getDefaultFormat()));
    }

    /**
     * 解析时间字符串转换为 Date
     * @param time    时间
     * @param pattern 时间格式
     * @return {@link Date}
     */
    public static Date parseDate(
            final String time,
            final String pattern
    ) {
        return parseDate(parseLong(time, getSafeDateFormat(pattern)));
    }

    /**
     * 解析时间字符串转换为 Date
     * @param time   时间
     * @param format {@link SimpleDateFormat}
     * @return {@link Date}
     */
    public static Date parseDate(
            final String time,
            final SimpleDateFormat format
    ) {
        return parseDate(parseLong(time, format));
    }

    // =

    /**
     * 解析时间字符串转换为 long 毫秒
     * @param time 时间
     * @return 毫秒时间
     */
    public static long parseLong(final String time) {
        return parseLong(time, getDefaultFormat());
    }

    /**
     * 解析时间字符串转换为 long 毫秒
     * @param time    时间
     * @param pattern 时间格式
     * @return 毫秒时间
     */
    public static long parseLong(
            final String time,
            final String pattern
    ) {
        return parseLong(time, getSafeDateFormat(pattern));
    }

    /**
     * 解析时间字符串转换为 long 毫秒
     * @param time   时间
     * @param format {@link SimpleDateFormat}
     * @return 毫秒时间
     */
    public static long parseLong(
            final String time,
            final SimpleDateFormat format
    ) {
        if (time == null || format == null) return -1L;
        try {
            return format.parse(time).getTime();
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "parseLong");
        }
        return -1L;
    }

    // =

    /**
     * 解析时间字符串转换为指定格式字符串
     * @param time    需要转换的时间
     * @param pattern 把 time 转换成需要的格式
     * @return 转换指定格式的时间字符串
     */
    public static String parseStringDefault(
            final String time,
            final String pattern
    ) {
        return parseString(
                time, getDefaultFormat(),
                getSafeDateFormat(pattern)
        );
    }

    /**
     * 解析时间字符串转换为指定格式字符串
     * @param time   需要转换的时间
     * @param format 把 time 转换成需要的格式
     * @return 转换指定格式的时间字符串
     */
    public static String parseStringDefault(
            final String time,
            final SimpleDateFormat format
    ) {
        return parseString(
                time, getDefaultFormat(), format
        );
    }

    /**
     * 解析时间字符串转换为指定格式字符串
     * @param time        需要转换的时间
     * @param timePattern time 的时间格式
     * @param pattern     把 time 转换成需要的格式
     * @return 转换指定格式的时间字符串
     */
    public static String parseString(
            final String time,
            final String timePattern,
            final String pattern
    ) {
        return parseString(
                time, getSafeDateFormat(timePattern),
                getSafeDateFormat(pattern)
        );
    }

    /**
     * 解析时间字符串转换为指定格式字符串
     * @param time       需要转换的时间
     * @param timeFormat time 的时间格式
     * @param format     把 time 转换成需要的格式
     * @return 转换指定格式的时间字符串
     */
    public static String parseString(
            final String time,
            final SimpleDateFormat timeFormat,
            final SimpleDateFormat format
    ) {
        if (time != null && timeFormat != null && format != null) {
            try {
                long timeLong = parseLong(time, timeFormat);
                // 把时间转为所需格式字符串
                return formatTime(timeLong, format);
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "parseString");
            }
        }
        return null;
    }

    // ==========
    // = 获取时间 =
    // ==========

    // =====
    // = 年 =
    // =====

    /**
     * 获取年份
     * @param calendar {@link Calendar}
     * @return 年份
     */
    public static int getYear(final Calendar calendar) {
        if (calendar != null) return calendar.get(Calendar.YEAR);
        return -1;
    }

    // =

    /**
     * 获取年份
     * @return 年份
     */
    public static int getYear() {
        return getYear(getCalendar());
    }

    /**
     * 获取年份
     * @param millis 时间毫秒
     * @return 年份
     */
    public static int getYear(final long millis) {
        return getYear(getCalendar(millis));
    }

    /**
     * 获取年份
     * @param date 日期
     * @return 年份
     */
    public static int getYear(final Date date) {
        return getYear(getCalendar(date));
    }

    /**
     * 获取年份
     * @param time 时间
     * @return 年份
     */
    public static int getYear(final String time) {
        return getYear(parseLong(time));
    }

    /**
     * 获取年份
     * @param time    时间
     * @param pattern 时间格式
     * @return 年份
     */
    public static int getYear(
            final String time,
            final String pattern
    ) {
        return getYear(parseLong(time, pattern));
    }

    /**
     * 获取年份
     * @param time   时间
     * @param format {@link SimpleDateFormat}
     * @return 年份
     */
    public static int getYear(
            final String time,
            final SimpleDateFormat format
    ) {
        return getYear(parseLong(time, format));
    }

    // =====
    // = 月 =
    // =====

    /**
     * 获取月份 ( 0 - 11 ) + 1
     * @param calendar {@link Calendar}
     * @return 月份
     */
    public static int getMonth(final Calendar calendar) {
        if (calendar != null) return calendar.get(Calendar.MONTH) + 1;
        return -1;
    }

    // =

    /**
     * 获取月份 ( 0 - 11 ) + 1
     * @return 月份
     */
    public static int getMonth() {
        return getMonth(getCalendar());
    }

    /**
     * 获取月份 ( 0 - 11 ) + 1
     * @param millis 时间毫秒
     * @return 月份
     */
    public static int getMonth(final long millis) {
        return getMonth(getCalendar(millis));
    }

    /**
     * 获取月份 ( 0 - 11 ) + 1
     * @param date 日期
     * @return 月份
     */
    public static int getMonth(final Date date) {
        return getMonth(getCalendar(date));
    }

    /**
     * 获取月份 ( 0 - 11 ) + 1
     * @param time 时间
     * @return 月份
     */
    public static int getMonth(final String time) {
        return getMonth(parseLong(time));
    }

    /**
     * 获取月份 ( 0 - 11 ) + 1
     * @param time    时间
     * @param pattern 时间格式
     * @return 月份
     */
    public static int getMonth(
            final String time,
            final String pattern
    ) {
        return getMonth(parseLong(time, pattern));
    }

    /**
     * 获取月份 ( 0 - 11 ) + 1
     * @param time   时间
     * @param format {@link SimpleDateFormat}
     * @return 月份
     */
    public static int getMonth(
            final String time,
            final SimpleDateFormat format
    ) {
        return getMonth(parseLong(time, format));
    }

    // =======
    // = 天数 =
    // =======

    /**
     * 获取天数
     * @param calendar {@link Calendar}
     * @return 天数
     */
    public static int getDay(final Calendar calendar) {
        if (calendar != null) return calendar.get(Calendar.DAY_OF_MONTH);
        return -1;
    }

    // =

    /**
     * 获取天数
     * @return 天数
     */
    public static int getDay() {
        return getDay(getCalendar());
    }

    /**
     * 获取天数
     * @param millis 时间毫秒
     * @return 天数
     */
    public static int getDay(final long millis) {
        return getDay(getCalendar(millis));
    }

    /**
     * 获取天数
     * @param date 日期
     * @return 天数
     */
    public static int getDay(final Date date) {
        return getDay(getCalendar(date));
    }

    /**
     * 获取天数
     * @param time 时间
     * @return 天数
     */
    public static int getDay(final String time) {
        return getDay(parseLong(time));
    }

    /**
     * 获取天数
     * @param time    时间
     * @param pattern 时间格式
     * @return 天数
     */
    public static int getDay(
            final String time,
            final String pattern
    ) {
        return getDay(parseLong(time, pattern));
    }

    /**
     * 获取天数
     * @param time   时间
     * @param format {@link SimpleDateFormat}
     * @return 天数
     */
    public static int getDay(
            final String time,
            final SimpleDateFormat format
    ) {
        return getDay(parseLong(time, format));
    }

    // =======
    // = 星期 =
    // =======

    /**
     * 获取星期数 ( 1 - 7、日 - 六 )
     * @param calendar {@link Calendar}
     * @return 星期数
     */
    public static int getWeek(final Calendar calendar) {
        if (calendar != null) return calendar.get(Calendar.DAY_OF_WEEK);
        return -1;
    }

    // =

    /**
     * 获取星期数 ( 1 - 7、日 - 六 )
     * @return 星期数
     */
    public static int getWeek() {
        return getWeek(getCalendar());
    }

    /**
     * 获取星期数 ( 1 - 7、日 - 六 )
     * @param millis 时间毫秒
     * @return 星期数
     */
    public static int getWeek(final long millis) {
        return getWeek(getCalendar(millis));
    }

    /**
     * 获取星期数 ( 1 - 7、日 - 六 )
     * @param date 日期
     * @return 星期数
     */
    public static int getWeek(final Date date) {
        return getWeek(getCalendar(date));
    }

    /**
     * 获取星期数 ( 1 - 7、日 - 六 )
     * @param time 时间
     * @return 星期数
     */
    public static int getWeek(final String time) {
        return getWeek(parseLong(time));
    }

    /**
     * 获取星期数 ( 1 - 7、日 - 六 )
     * @param time    时间
     * @param pattern 时间格式
     * @return 星期数
     */
    public static int getWeek(
            final String time,
            final String pattern
    ) {
        return getWeek(parseLong(time, pattern));
    }

    /**
     * 获取星期数 ( 1 - 7、日 - 六 )
     * @param time   时间
     * @param format {@link SimpleDateFormat}
     * @return 星期数
     */
    public static int getWeek(
            final String time,
            final SimpleDateFormat format
    ) {
        return getWeek(parseLong(time, format));
    }

    // =======
    // = 24H =
    // =======

    /**
     * 获取小时 ( 24 )
     * @param calendar {@link Calendar}
     * @return 小时
     */
    public static int get24Hour(final Calendar calendar) {
        if (calendar != null) return calendar.get(Calendar.HOUR_OF_DAY);
        return -1;
    }

    // =

    /**
     * 获取小时 ( 24 )
     * @return 小时
     */
    public static int get24Hour() {
        return get24Hour(getCalendar());
    }

    /**
     * 获取小时 ( 24 )
     * @param millis 时间毫秒
     * @return 小时
     */
    public static int get24Hour(final long millis) {
        return get24Hour(getCalendar(millis));
    }

    /**
     * 获取小时 ( 24 )
     * @param date 日期
     * @return 小时
     */
    public static int get24Hour(final Date date) {
        return get24Hour(getCalendar(date));
    }

    /**
     * 获取小时 ( 24 )
     * @param time 时间
     * @return 小时
     */
    public static int get24Hour(final String time) {
        return get24Hour(parseLong(time));
    }

    /**
     * 获取小时 ( 24 )
     * @param time    时间
     * @param pattern 时间格式
     * @return 小时
     */
    public static int get24Hour(
            final String time,
            final String pattern
    ) {
        return get24Hour(parseLong(time, pattern));
    }

    /**
     * 获取小时 ( 24 )
     * @param time   时间
     * @param format {@link SimpleDateFormat}
     * @return 小时
     */
    public static int get24Hour(
            final String time,
            final SimpleDateFormat format
    ) {
        return get24Hour(parseLong(time, format));
    }

    // =======
    // = 12H =
    // =======

    /**
     * 获取小时 ( 12 )
     * @param calendar {@link Calendar}
     * @return 小时
     */
    public static int get12Hour(final Calendar calendar) {
        if (calendar != null) return calendar.get(Calendar.HOUR);
        return -1;
    }

    // =

    /**
     * 获取小时 ( 12 )
     * @return 小时
     */
    public static int get12Hour() {
        return get12Hour(getCalendar());
    }

    /**
     * 获取小时 ( 12 )
     * @param millis 时间毫秒
     * @return 小时
     */
    public static int get12Hour(final long millis) {
        return get12Hour(getCalendar(millis));
    }

    /**
     * 获取小时 ( 12 )
     * @param date 日期
     * @return 小时
     */
    public static int get12Hour(final Date date) {
        return get12Hour(getCalendar(date));
    }

    /**
     * 获取小时 ( 12 )
     * @param time 时间
     * @return 小时
     */
    public static int get12Hour(final String time) {
        return get12Hour(parseLong(time));
    }

    /**
     * 获取小时 ( 12 )
     * @param time    时间
     * @param pattern 时间格式
     * @return 小时
     */
    public static int get12Hour(
            final String time,
            final String pattern
    ) {
        return get12Hour(parseLong(time, pattern));
    }

    /**
     * 获取小时 ( 12 )
     * @param time   时间
     * @param format {@link SimpleDateFormat}
     * @return 小时
     */
    public static int get12Hour(
            final String time,
            final SimpleDateFormat format
    ) {
        return get12Hour(parseLong(time, format));
    }

    // =======
    // = 分钟 =
    // =======

    /**
     * 获取分钟
     * @param calendar {@link Calendar}
     * @return 分钟
     */
    public static int getMinute(final Calendar calendar) {
        if (calendar != null) return calendar.get(Calendar.MINUTE);
        return -1;
    }

    // =

    /**
     * 获取分钟
     * @return 分钟
     */
    public static int getMinute() {
        return getMinute(getCalendar());
    }

    /**
     * 获取分钟
     * @param millis 时间毫秒
     * @return 分钟
     */
    public static int getMinute(final long millis) {
        return getMinute(getCalendar(millis));
    }

    /**
     * 获取分钟
     * @param date 日期
     * @return 分钟
     */
    public static int getMinute(final Date date) {
        return getMinute(getCalendar(date));
    }

    /**
     * 获取分钟
     * @param time 时间
     * @return 分钟
     */
    public static int getMinute(final String time) {
        return getMinute(parseLong(time));
    }

    /**
     * 获取分钟
     * @param time    时间
     * @param pattern 时间格式
     * @return 分钟
     */
    public static int getMinute(
            final String time,
            final String pattern
    ) {
        return getMinute(parseLong(time, pattern));
    }

    /**
     * 获取分钟
     * @param time   时间
     * @param format {@link SimpleDateFormat}
     * @return 分钟
     */
    public static int getMinute(
            final String time,
            final SimpleDateFormat format
    ) {
        return getMinute(parseLong(time, format));
    }

    // =======
    // = 秒数 =
    // =======

    /**
     * 获取秒数
     * @param calendar {@link Calendar}
     * @return 秒数
     */
    public static int getSecond(final Calendar calendar) {
        if (calendar != null) return calendar.get(Calendar.SECOND);
        return -1;
    }

    // =

    /**
     * 获取秒数
     * @return 秒数
     */
    public static int getSecond() {
        return getSecond(getCalendar());
    }

    /**
     * 获取秒数
     * @param millis 时间毫秒
     * @return 秒数
     */
    public static int getSecond(final long millis) {
        return getSecond(getCalendar(millis));
    }

    /**
     * 获取秒数
     * @param date 日期
     * @return 秒数
     */
    public static int getSecond(final Date date) {
        return getSecond(getCalendar(date));
    }

    /**
     * 获取秒数
     * @param time 时间
     * @return 秒数
     */
    public static int getSecond(final String time) {
        return getSecond(parseLong(time));
    }

    /**
     * 获取秒数
     * @param time    时间
     * @param pattern 时间格式
     * @return 秒数
     */
    public static int getSecond(
            final String time,
            final String pattern
    ) {
        return getSecond(parseLong(time, pattern));
    }

    /**
     * 获取秒数
     * @param time   时间
     * @param format {@link SimpleDateFormat}
     * @return 秒数
     */
    public static int getSecond(
            final String time,
            final SimpleDateFormat format
    ) {
        return getSecond(parseLong(time, format));
    }

    // =======
    // = 上午 =
    // =======

    /**
     * 是否上午
     * @param calendar {@link Calendar}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isAM(final Calendar calendar) {
        if (calendar != null) {
            return calendar.get(GregorianCalendar.AM_PM) == Calendar.AM;
        }
        return false;
    }

    // =

    /**
     * 是否上午
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isAM() {
        return isAM(getCalendar());
    }

    /**
     * 是否上午
     * @param millis 时间毫秒
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isAM(final long millis) {
        return isAM(getCalendar(millis));
    }

    /**
     * 是否上午
     * @param date 日期
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isAM(final Date date) {
        return isAM(getCalendar(date));
    }

    /**
     * 是否上午
     * @param time 时间
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isAM(final String time) {
        return isAM(parseLong(time));
    }

    /**
     * 是否上午
     * @param time    时间
     * @param pattern 时间格式
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isAM(
            final String time,
            final String pattern
    ) {
        return isAM(parseLong(time, pattern));
    }

    /**
     * 是否上午
     * @param time   时间
     * @param format {@link SimpleDateFormat}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isAM(
            final String time,
            final SimpleDateFormat format
    ) {
        return isAM(parseLong(time, format));
    }

    // =======
    // = 下午 =
    // =======

    /**
     * 是否下午
     * @param calendar {@link Calendar}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isPM(final Calendar calendar) {
        if (calendar != null) {
            return calendar.get(GregorianCalendar.AM_PM) == Calendar.PM;
        }
        return false;
    }

    // =

    /**
     * 是否下午
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isPM() {
        return isPM(getCalendar());
    }

    /**
     * 是否下午
     * @param millis 时间毫秒
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isPM(final long millis) {
        return isPM(getCalendar(millis));
    }

    /**
     * 是否下午
     * @param date 日期
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isPM(final Date date) {
        return isPM(getCalendar(date));
    }

    /**
     * 是否下午
     * @param time 时间
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isPM(final String time) {
        return isPM(parseLong(time));
    }

    /**
     * 是否下午
     * @param time    时间
     * @param pattern 时间格式
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isPM(
            final String time,
            final String pattern
    ) {
        return isPM(parseLong(time, pattern));
    }

    /**
     * 是否下午
     * @param time   时间
     * @param format {@link SimpleDateFormat}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isPM(
            final String time,
            final SimpleDateFormat format
    ) {
        return isPM(parseLong(time, format));
    }

    // ==========
    // = 年份判断 =
    // ==========

    /**
     * 是否对应年份
     * @param calendar {@link Calendar}
     * @param year     待判断年份
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isYear(
            final Calendar calendar,
            final int year
    ) {
        return year != -1 && year == getYear(calendar);
    }

    // =

    /**
     * 是否对应年份
     * @param year 待判断年份
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isYear(final int year) {
        return isYear(getCalendar(), year);
    }

    /**
     * 是否对应年份
     * @param millis 时间毫秒
     * @param year   待判断年份
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isYear(
            final long millis,
            final int year
    ) {
        return isYear(getCalendar(millis), year);
    }

    /**
     * 是否对应年份
     * @param date 日期
     * @param year 待判断年份
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isYear(
            final Date date,
            final int year
    ) {
        return isYear(getCalendar(date), year);
    }

    /**
     * 是否对应年份
     * @param time 时间
     * @param year 待判断年份
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isYear(
            final String time,
            final int year
    ) {
        return isYear(parseLong(time), year);
    }

    /**
     * 是否对应年份
     * @param time    时间
     * @param pattern 时间格式
     * @param year    待判断年份
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isYear(
            final String time,
            final String pattern,
            final int year
    ) {
        return isYear(parseLong(time, pattern), year);
    }

    /**
     * 是否对应年份
     * @param time   时间
     * @param format {@link SimpleDateFormat}
     * @param year   待判断年份
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isYear(
            final String time,
            final SimpleDateFormat format,
            final int year
    ) {
        return isYear(parseLong(time, format), year);
    }

    // ==========
    // = 月份判断 =
    // ==========

    /**
     * 是否对应月份
     * @param calendar {@link Calendar}
     * @param month    待判断月份
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isMonth(
            final Calendar calendar,
            final int month
    ) {
        return month != -1 && month == getMonth(calendar);
    }

    // =

    /**
     * 是否对应月份
     * @param month 待判断月份
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isMonth(final int month) {
        return isMonth(getCalendar(), month);
    }

    /**
     * 是否对应月份
     * @param millis 时间毫秒
     * @param month  待判断月份
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isMonth(
            final long millis,
            final int month
    ) {
        return isMonth(getCalendar(millis), month);
    }

    /**
     * 是否对应月份
     * @param date  日期
     * @param month 待判断月份
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isMonth(
            final Date date,
            final int month
    ) {
        return isMonth(getCalendar(date), month);
    }

    /**
     * 是否对应月份
     * @param time  时间
     * @param month 待判断月份
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isMonth(
            final String time,
            final int month
    ) {
        return isMonth(parseLong(time), month);
    }

    /**
     * 是否对应月份
     * @param time    时间
     * @param pattern 时间格式
     * @param month   待判断月份
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isMonth(
            final String time,
            final String pattern,
            final int month
    ) {
        return isMonth(parseLong(time, pattern), month);
    }

    /**
     * 是否对应月份
     * @param time   时间
     * @param format {@link SimpleDateFormat}
     * @param month  待判断月份
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isMonth(
            final String time,
            final SimpleDateFormat format,
            final int month
    ) {
        return isMonth(parseLong(time, format), month);
    }

    // ==========
    // = 天数判断 =
    // ==========

    /**
     * 是否对应天数
     * @param calendar {@link Calendar}
     * @param day      待判断天数
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isDay(
            final Calendar calendar,
            final int day
    ) {
        return day != -1 && day == getDay(calendar);
    }

    // =

    /**
     * 是否对应天数
     * @param day 待判断天数
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isDay(final int day) {
        return isDay(getCalendar(), day);
    }

    /**
     * 是否对应天数
     * @param millis 时间毫秒
     * @param day    待判断天数
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isDay(
            final long millis,
            final int day
    ) {
        return isDay(getCalendar(millis), day);
    }

    /**
     * 是否对应天数
     * @param date 日期
     * @param day  待判断天数
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isDay(
            final Date date,
            final int day
    ) {
        return isDay(getCalendar(date), day);
    }

    /**
     * 是否对应天数
     * @param time 时间
     * @param day  待判断天数
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isDay(
            final String time,
            final int day
    ) {
        return isDay(parseLong(time), day);
    }

    /**
     * 是否对应天数
     * @param time    时间
     * @param pattern 时间格式
     * @param day     待判断天数
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isDay(
            final String time,
            final String pattern,
            final int day
    ) {
        return isDay(parseLong(time, pattern), day);
    }

    /**
     * 是否对应天数
     * @param time   时间
     * @param format {@link SimpleDateFormat}
     * @param day    待判断天数
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isDay(
            final String time,
            final SimpleDateFormat format,
            final int day
    ) {
        return isDay(parseLong(time, format), day);
    }

    // ==========
    // = 星期判断 =
    // ==========

    /**
     * 是否对应星期
     * @param calendar {@link Calendar}
     * @param week     待判断星期
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isWeek(
            final Calendar calendar,
            final int week
    ) {
        return week != -1 && week == getWeek(calendar);
    }

    // =

    /**
     * 是否对应星期
     * @param week 待判断星期
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isWeek(final int week) {
        return isWeek(getCalendar(), week);
    }

    /**
     * 是否对应星期
     * @param millis 时间毫秒
     * @param week   待判断星期
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isWeek(
            final long millis,
            final int week
    ) {
        return isWeek(getCalendar(millis), week);
    }

    /**
     * 是否对应星期
     * @param date 日期
     * @param week 待判断星期
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isWeek(
            final Date date,
            final int week
    ) {
        return isWeek(getCalendar(date), week);
    }

    /**
     * 是否对应星期
     * @param time 时间
     * @param week 待判断星期
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isWeek(
            final String time,
            final int week
    ) {
        return isWeek(parseLong(time), week);
    }

    /**
     * 是否对应星期
     * @param time    时间
     * @param pattern 时间格式
     * @param week    待判断星期
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isWeek(
            final String time,
            final String pattern,
            final int week
    ) {
        return isWeek(parseLong(time, pattern), week);
    }

    /**
     * 是否对应星期
     * @param time   时间
     * @param format {@link SimpleDateFormat}
     * @param week   待判断星期
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isWeek(
            final String time,
            final SimpleDateFormat format,
            final int week
    ) {
        return isWeek(parseLong(time, format), week);
    }

    // ==========
    // = 小时判断 =
    // ==========

    /**
     * 是否对应小时
     * @param calendar {@link Calendar}
     * @param hour     待判断小时
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isHour(
            final Calendar calendar,
            final int hour
    ) {
        return hour != -1 && hour == get24Hour(calendar);
    }

    // =

    /**
     * 是否对应小时
     * @param hour 待判断小时
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isHour(final int hour) {
        return isHour(getCalendar(), hour);
    }

    /**
     * 是否对应小时
     * @param millis 时间毫秒
     * @param hour   待判断小时
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isHour(
            final long millis,
            final int hour
    ) {
        return isHour(getCalendar(millis), hour);
    }

    /**
     * 是否对应小时
     * @param date 日期
     * @param hour 待判断小时
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isHour(
            final Date date,
            final int hour
    ) {
        return isHour(getCalendar(date), hour);
    }

    /**
     * 是否对应小时
     * @param time 时间
     * @param hour 待判断小时
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isHour(
            final String time,
            final int hour
    ) {
        return isHour(parseLong(time), hour);
    }

    /**
     * 是否对应小时
     * @param time    时间
     * @param pattern 时间格式
     * @param hour    待判断小时
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isHour(
            final String time,
            final String pattern,
            final int hour
    ) {
        return isHour(parseLong(time, pattern), hour);
    }

    /**
     * 是否对应小时
     * @param time   时间
     * @param format {@link SimpleDateFormat}
     * @param hour   待判断小时
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isHour(
            final String time,
            final SimpleDateFormat format,
            final int hour
    ) {
        return isHour(parseLong(time, format), hour);
    }

    // ==========
    // = 分钟判断 =
    // ==========

    /**
     * 是否对应分钟
     * @param calendar {@link Calendar}
     * @param minute   待判断分钟
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isMinute(
            final Calendar calendar,
            final int minute
    ) {
        return minute != -1 && minute == getMinute(calendar);
    }

    // =

    /**
     * 是否对应分钟
     * @param minute 待判断分钟
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isMinute(final int minute) {
        return isMinute(getCalendar(), minute);
    }

    /**
     * 是否对应分钟
     * @param millis 时间毫秒
     * @param minute 待判断分钟
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isMinute(
            final long millis,
            final int minute
    ) {
        return isMinute(getCalendar(millis), minute);
    }

    /**
     * 是否对应分钟
     * @param date   日期
     * @param minute 待判断分钟
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isMinute(
            final Date date,
            final int minute
    ) {
        return isMinute(getCalendar(date), minute);
    }

    /**
     * 是否对应分钟
     * @param time   时间
     * @param minute 待判断分钟
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isMinute(
            final String time,
            final int minute
    ) {
        return isMinute(parseLong(time), minute);
    }

    /**
     * 是否对应分钟
     * @param time    时间
     * @param pattern 时间格式
     * @param minute  待判断分钟
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isMinute(
            final String time,
            final String pattern,
            final int minute
    ) {
        return isMinute(parseLong(time, pattern), minute);
    }

    /**
     * 是否对应分钟
     * @param time   时间
     * @param format {@link SimpleDateFormat}
     * @param minute 待判断分钟
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isMinute(
            final String time,
            final SimpleDateFormat format,
            final int minute
    ) {
        return isMinute(parseLong(time, format), minute);
    }

    // ==========
    // = 秒数判断 =
    // ==========

    /**
     * 是否对应秒数
     * @param calendar {@link Calendar}
     * @param second   待判断秒数
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isSecond(
            final Calendar calendar,
            final int second
    ) {
        return second != -1 && second == getSecond(calendar);
    }

    // =

    /**
     * 是否对应秒数
     * @param second 待判断秒数
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isSecond(final int second) {
        return isSecond(getCalendar(), second);
    }

    /**
     * 是否对应秒数
     * @param millis 时间毫秒
     * @param second 待判断秒数
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isSecond(
            final long millis,
            final int second
    ) {
        return isSecond(getCalendar(millis), second);
    }

    /**
     * 是否对应秒数
     * @param date   日期
     * @param second 待判断秒数
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isSecond(
            final Date date,
            final int second
    ) {
        return isSecond(getCalendar(date), second);
    }

    /**
     * 是否对应秒数
     * @param time   时间
     * @param second 待判断秒数
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isSecond(
            final String time,
            final int second
    ) {
        return isSecond(parseLong(time), second);
    }

    /**
     * 是否对应秒数
     * @param time    时间
     * @param pattern 时间格式
     * @param second  待判断秒数
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isSecond(
            final String time,
            final String pattern,
            final int second
    ) {
        return isSecond(parseLong(time, pattern), second);
    }

    /**
     * 是否对应秒数
     * @param time   时间
     * @param format {@link SimpleDateFormat}
     * @param second 待判断秒数
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isSecond(
            final String time,
            final SimpleDateFormat format,
            final int second
    ) {
        return isSecond(parseLong(time, format), second);
    }

    // =

    /**
     * 获取秒数倍数
     * @param millis 时间毫秒
     * @return 秒数倍数
     */
    public static int getSecondMultiple(final long millis) {
        return getMillisMultiple(millis, DevFinal.TIME.SECOND_MS);
    }

    /**
     * 获取分钟倍数
     * @param millis 时间毫秒
     * @return 分钟倍数
     */
    public static int getMinuteMultiple(final long millis) {
        return getMillisMultiple(millis, DevFinal.TIME.MINUTE_MS);
    }

    /**
     * 获取小时倍数
     * @param millis 时间毫秒
     * @return 小时倍数
     */
    public static int getHourMultiple(final long millis) {
        return getMillisMultiple(millis, DevFinal.TIME.HOUR_MS);
    }

    /**
     * 获取天数倍数
     * @param millis 时间毫秒
     * @return 天数倍数
     */
    public static int getDayMultiple(final long millis) {
        return getMillisMultiple(millis, DevFinal.TIME.DAY_MS);
    }

    /**
     * 获取周数倍数
     * @param millis 时间毫秒
     * @return 周数倍数
     */
    public static int getWeekMultiple(final long millis) {
        return getMillisMultiple(millis, DevFinal.TIME.WEEK_MS);
    }

    /**
     * 获取对应单位倍数
     * @param millis 时间毫秒
     * @param unit   毫秒单位 ( 除数 )
     * @return 对应单位倍数
     */
    public static int getMillisMultiple(
            final long millis,
            final long unit
    ) {
        if (millis == -1L) return -1;
        return NumberUtils.multipleI(millis, unit);
    }

    // ============
    // = 时间差计算 =
    // ============

    /**
     * 获取时间差 ( 传入时间 - 当前时间 )
     * @param millis 时间毫秒
     * @return 与当前时间的时间差 ( 毫秒 )
     */
    public static long getTimeDiffByCurrent(final long millis) {
        if (millis == -1L) return -1L;
        return millis - System.currentTimeMillis();
    }

    /**
     * 获取时间差 ( 传入时间 - 当前时间 )
     * @param date 日期
     * @return 与当前时间的时间差 ( 毫秒 )
     */
    public static long getTimeDiffByCurrent(final Date date) {
        return getTimeDiffByCurrent(getDateTime(date));
    }

    /**
     * 获取时间差 ( 传入时间 - 当前时间 )
     * @param time 需要转换的时间
     * @return 与当前时间的时间差 ( 毫秒 )
     */
    public static long getTimeDiffByCurrent(final String time) {
        return getTimeDiffByCurrent(parseLong(time));
    }

    /**
     * 获取时间差 ( 传入时间 - 当前时间 )
     * @param time    需要转换的时间
     * @param pattern 把 time 转换成需要的格式
     * @return 与当前时间的时间差 ( 毫秒 )
     */
    public static long getTimeDiffByCurrent(
            final String time,
            final String pattern
    ) {
        return getTimeDiffByCurrent(parseLong(time, pattern));
    }

    /**
     * 获取时间差 ( 传入时间 - 当前时间 )
     * @param time   需要转换的时间
     * @param format 把 time 转换成需要的格式
     * @return 与当前时间的时间差 ( 毫秒 )
     */
    public static long getTimeDiffByCurrent(
            final String time,
            final SimpleDateFormat format
    ) {
        return getTimeDiffByCurrent(parseLong(time, format));
    }

    // =

    /**
     * 获取时间差
     * @param time1 时间
     * @param time2 对比时间
     * @return 时间差 ( 毫秒 )
     */
    public static long getTimeDiff(
            final String time1,
            final String time2
    ) {
        return getTimeDiff(
                time1, getDefaultFormat(),
                time2, getDefaultFormat()
        );
    }

    /**
     * 获取时间差
     * @param time1    时间
     * @param pattern1 时间格式
     * @param time2    对比时间
     * @param pattern2 对比时间格式
     * @return 时间差 ( 毫秒 )
     */
    public static long getTimeDiff(
            final String time1,
            final String pattern1,
            final String time2,
            final String pattern2
    ) {
        return getTimeDiff(
                time1, getSafeDateFormat(pattern1),
                time2, getSafeDateFormat(pattern2)
        );
    }

    /**
     * 获取时间差
     * @param time1       时间
     * @param timeFormat1 时间格式
     * @param time2       对比时间
     * @param timeFormat2 对比时间格式
     * @return 时间差 ( 毫秒 )
     */
    public static long getTimeDiff(
            final String time1,
            final SimpleDateFormat timeFormat1,
            final String time2,
            final SimpleDateFormat timeFormat2
    ) {
        long timeLong1 = parseLong(time1, timeFormat1);
        if (timeLong1 == -1L) return -1L;
        long timeLong2 = parseLong(time2, timeFormat2);
        if (timeLong2 == -1L) return -1L;
        return timeLong1 - timeLong2;
    }

    // ==========
    // = 快捷方法 =
    // ==========

    /**
     * 判断是否闰年
     * @param year 年份
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isLeapYear(final int year) {
        return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
    }

    /**
     * 根据年份、月份, 获取对应的天数 ( 完整天数, 无判断是否属于未来日期 )
     * @param year  年份
     * @param month 月份
     * @return 指定年份所属的月份的天数
     */
    public static int getMonthDayNumberAll(
            final int year,
            final int month
    ) {
        int number = 31;
        // 判断返回的标识数字
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                number = 31;
                break;
            case 2:
                if (isLeapYear(year)) {
                    number = 29;
                } else {
                    number = 28;
                }
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                number = 30;
                break;
        }
        return number;
    }

    /**
     * 根据年份, 获取对应的月份
     * <pre>
     *     传入历史、以及未来年份都返回对应年月份
     *     传入当前年份则返回当前月份
     * </pre>
     * @param year 年份
     * @return 内部判断是否相同一年, 不能超过限制未来的月份
     */
    public static int getYearMonthNumber(final int year) {
        if (year == getYear()) return getMonth();
        return 12;
    }

    /**
     * 根据年份、月份, 获取对应的天数
     * <pre>
     *     传入历史、以及未来年月份都返回对应年月份的天数
     *     传入当前年月份则返回当前天数
     * </pre>
     * @param year  年份
     * @param month 月份
     * @return 内部判断是否相同一年、月份, 不能超过限制未来的天数
     */
    public static int getMonthDayNumber(
            final int year,
            final int month
    ) {
        if (year == getYear() && month == getMonth()) {
            return getDay();
        }
        return getMonthDayNumberAll(year, month);
    }

    // =

    /**
     * 时间补 0 处理 ( 小于 10, 则自动补充 0x )
     * @param time 待处理时间
     * @return 自动补 0 时间字符串
     */
    public static String timeAddZero(final int time) {
        return timeAddZero(time, true);
    }

    /**
     * 时间补 0 处理 ( 小于 10, 则自动补充 0x )
     * @param time       待处理时间
     * @param appendZero 是否自动补 0
     * @return 自动补 0 时间字符串
     */
    public static String timeAddZero(
            final int time,
            final boolean appendZero
    ) {
        return NumberUtils.addZero(time, appendZero);
    }

    /**
     * 时间补 0 处理 ( 小于 10, 则自动补充 0x )
     * @param time 待处理时间
     * @return 自动补 0 时间字符串
     */
    public static String timeAddZero(final long time) {
        return timeAddZero(time, true);
    }

    /**
     * 时间补 0 处理 ( 小于 10, 则自动补充 0x )
     * @param time       待处理时间
     * @param appendZero 是否自动补 0
     * @return 自动补 0 时间字符串
     */
    public static String timeAddZero(
            final long time,
            final boolean appendZero
    ) {
        return NumberUtils.addZero(time, appendZero);
    }

    // =

    /**
     * 生成 HH 按时间排序数组
     * @param appendZero 是否自动补 0
     * @return 按小时排序的数组
     */
    public static String[] getArrayToHH(final boolean appendZero) {
        List<String> lists = getListToHH(appendZero);
        return lists.toArray(new String[0]);
    }

    /**
     * 生成 HH 按时间排序集合
     * @param appendZero 是否自动补 0
     * @return 按小时排序的集合
     */
    public static List<String> getListToHH(final boolean appendZero) {
        List<String> lists = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            lists.add(timeAddZero(i, appendZero));
        }
        return lists;
    }

    // =

    /**
     * 生成 MM 按时间排序数组
     * @param appendZero 是否自动补 0
     * @return 按分钟排序的数组
     */
    public static String[] getArrayToMM(final boolean appendZero) {
        List<String> lists = getListToMM(appendZero);
        return lists.toArray(new String[0]);
    }

    /**
     * 生成 MM 按时间排序集合
     * @param appendZero 是否自动补 0
     * @return 按分钟排序的集合
     */
    public static List<String> getListToMM(final boolean appendZero) {
        List<String> lists = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            lists.add(timeAddZero(i, appendZero));
        }
        return lists;
    }

    // =

    /**
     * 生成 HH:mm 按间隔时间排序数组
     * @param type       0 = 00:00 - 23:00 = 每 1  小时间隔
     *                   1 = 00:00 - 23:45 = 每 15 分钟间隔
     *                   2 = 00:00 - 23:30 = 每 30 分钟间隔
     * @param appendZero 是否自动补 0
     * @return 指定格式的数组
     */
    public static String[] getArrayToHHMM(
            final int type,
            final boolean appendZero
    ) {
        List<String> lists = getListToHHMM(type, appendZero);
        return lists.toArray(new String[0]);
    }

    /**
     * 生成 HH:mm 按间隔时间排序集合
     * @param type       0 = 00:00 - 23:00 = 每 1  小时间隔
     *                   1 = 00:00 - 23:45 = 每 15 分钟间隔
     *                   2 = 00:00 - 23:30 = 每 30 分钟间隔
     * @param appendZero 是否自动补 0
     * @return 指定格式的集合
     */
    public static List<String> getListToHHMM(
            final int type,
            final boolean appendZero
    ) {
        List<String> lists = new ArrayList<>();
        switch (type) {
            case 0:
                for (int i = 0; i < 24; i++) {
                    lists.add(timeAddZero(i, appendZero) + ":00");
                }
                break;
            case 1:
                for (int i = 0; i < 96; i++) { // 00 15 30 45 = 4 (24 * 4)
                    if (i % 2 == 0) { // 判断是否偶数 00、30
                        // 小时数
                        String hour = timeAddZero(i / 4, appendZero);
                        // 分钟数
                        String minute = i % 4 == 0 ? "00" : "30";
                        // 累加时间
                        lists.add(hour + ":" + minute);
                    } else { // 15、45
                        // 小时数
                        String hour = timeAddZero(i / 4, appendZero);
                        // 分钟数
                        String minute = (i - 1) % 4 == 0 ? "15" : "45";
                        // 累加时间
                        lists.add(hour + ":" + minute);
                    }
                }
                break;
            case 2:
                for (int i = 0; i < 48; i++) { // 00 30 = 2 (24 * 2)
                    // 小时处理
                    int hour = i / 2;
                    // 属于偶数
                    if (i % 2 == 0) {
                        lists.add(timeAddZero(hour, appendZero) + ":00");
                    } else {
                        lists.add(timeAddZero(hour, appendZero) + ":30");
                    }
                }
                break;
        }
        return lists;
    }

    /**
     * 获取 HH:mm 按间隔时间排序的集合中, 指定时间所在索引
     * @param time HH:mm 格式
     * @param type 0 = 00:00 - 23:00 = 每 1  小时间隔
     *             1 = 00:00 - 23:45 = 每 15 分钟间隔
     *             2 = 00:00 - 23:30 = 每 30 分钟间隔
     * @return 指定数据, 在对应格式类型内的索引
     */
    public static int getListToHHMMPosition(
            final String time,
            final int type
    ) {
        if (time != null && time.length() != 0) {
            // 进行拆分
            String[] timeSplit = time.split(":");
            if (timeSplit.length == 2) {
                // 转换小时
                int hour = ConvertUtils.toInt(timeSplit[0], -1);
                // 判断是否小于 0
                if (hour < 0) {
                    return -1;
                } else if (hour > 24) {
                    return -1;
                }

                // 判断格式, 进行格式处理
                switch (type) {
                    case 0:
                        return hour;
                    case 1:
                    case 2:
                        // 转换分钟
                        int minute = ConvertUtils.toInt(timeSplit[1], -1);
                        // 判断是否小于 0
                        if (minute < 0) {
                            return -1;
                        } else if (minute > 59) {
                            return -1;
                        }
                        // 判断间隔
                        if (type == 1) {
                            if (minute < 15) {
                                return hour * 4;
                            } else if (minute < 30) {
                                return hour * 4 + 1;
                            } else if (minute < 45) {
                                return hour * 4 + 2;
                            } else {
                                return hour * 4 + 3;
                            }
                        } else { // 30 分钟一个间隔
                            if (minute >= 30) { // 属于 30, 需要加 1
                                return hour * 2 + 1;
                            } else {
                                return hour * 2;
                            }
                        }
                }
            }
        }
        return -1;
    }

    /**
     * 转换时间
     * <pre>
     *     如果有其他要求可使用 {@link FormatUtils#getUnitSpanFormatter(int, boolean, String)}
     *     <p></p>
     *     该方法使用 UnitSpanFormatter 实现如下:
     *     long[]   unitSpans = {86400000, 3600000, 60000, 1000, 1};
     *     String[] units     = {"天", "小时", "分钟", "秒", "毫秒"};
     *     UnitSpanFormatter.get(precision, appendZero, "").formatBySpan(
     *                 millis, unitSpans, units
     *     );
     * </pre>
     * @param millis     时间毫秒
     * @param precision  precision = 1, return 天
     *                   precision = 2, return 天, 小时
     *                   precision = 3, return 天, 小时, 分钟
     *                   precision = 4, return 天, 小时, 分钟, 秒
     *                   precision = 5, return 天, 小时, 分钟, 秒, 毫秒
     * @param appendZero 是否自动补 0
     * @return 转换指定格式的时间字符串
     */
    public static String millisToFitTimeSpan(
            final long millis,
            final int precision,
            final boolean appendZero
    ) {
        if (precision >= 1 && precision <= 5) {
            int[]         result  = millisToTimeArrays(millis);
            String[]      units   = {"天", "小时", "分钟", "秒", "毫秒"};
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < precision; i++) {
                builder.append(timeAddZero(result[i], appendZero))
                        .append(units[i]);
            }
            return builder.toString();
        }
        return "";
    }

    /**
     * 转换时间为数组
     * @param millis 时间毫秒
     * @return int[5] { 天, 小时, 分钟, 秒, 毫秒 }
     */
    public static int[] millisToTimeArrays(final long millis) {
        if (millis > 0) {
            final long[] values = NumberUtils.calculateUnitL(
                    millis, new long[]{86400000, 3600000, 60000, 1000, 1}
            );
            return ConvertUtils.longsToInts(values);
        }
        return new int[5];
    }

    // =

    /**
     * 传入时间毫秒, 获取 00:00:00 格式 ( 不处理大于一天 )
     * @param millis 时间毫秒
     * @return 转换 ( 00:00:00 ) 时间格式字符串
     */
    public static String timeConvertByMillis(final long millis) {
        return timeConvertByMillis(millis, false);
    }

    /**
     * 传入时间毫秒, 获取 00:00:00 格式
     * <pre>
     *     小时:分钟:秒
     * </pre>
     * @param millis             时间毫秒
     * @param handlerMoreThanDay 是否处理大于一天的时间
     * @return 转换 ( 00:00:00 ) 时间格式字符串
     */
    public static String timeConvertByMillis(
            final long millis,
            final boolean handlerMoreThanDay
    ) {
        int[] result = millisToTimeArrays(millis);
        // 如果大于一天但不处理大于一天情况则返回 null
        if (result[0] > 0 && !handlerMoreThanDay) {
            return null;
        }
        return timeAddZero(result[0] * 24 + result[1])
                + ":" + timeAddZero(result[2])
                + ":" + timeAddZero(result[3]);
    }

    // =

    /**
     * 传入时间秒, 获取 00:00:00 格式 ( 不处理大于一天 )
     * @param second 时间 ( 秒 )
     * @return 转换 ( 00:00:00 ) 时间格式字符串
     */
    public static String timeConvertBySecond(final long second) {
        return timeConvertBySecond(second, false);
    }

    /**
     * 传入时间秒, 获取 00:00:00 格式
     * @param second             时间 ( 秒 )
     * @param handlerMoreThanDay 是否处理大于一天的时间
     * @return 转换 ( 00:00:00 ) 时间格式字符串
     */
    public static String timeConvertBySecond(
            final long second,
            final boolean handlerMoreThanDay
    ) {
        return timeConvertByMillis(second * 1000L, handlerMoreThanDay);
    }

    // ==================
    // = 判断是否在区间范围 =
    // ==================

    /**
     * 判断时间是否在 [startTime, endTime] 区间
     * @param time      待判断时间 ( 毫秒 )
     * @param startTime 开始时间 ( 毫秒 )
     * @param endTime   结束时间 ( 毫秒 )
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isInTime(
            final long time,
            final long startTime,
            final long endTime
    ) {
        if (time == -1L || startTime == -1L || endTime == -1L) return false;
        // 待校验时间
        Calendar check = Calendar.getInstance();
        check.setTimeInMillis(time);
        // 开始时间
        Calendar begin = Calendar.getInstance();
        begin.setTimeInMillis(startTime);
        // 结束时间
        Calendar end = Calendar.getInstance();
        end.setTimeInMillis(endTime);
        // 判断是否在 begin 之后的时间, 并且在 end 之前的时间
        if (check.after(begin) && check.before(end)) {
            return true;
        }
        // 判断时间相同情况
        return time == startTime || time == endTime;
    }

    /**
     * 判断时间是否在 [startTime, endTime] 区间
     * @param time      待判断时间
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isInTime(
            final Date time,
            final Date startTime,
            final Date endTime
    ) {
        return isInTime(getDateTime(time), getDateTime(startTime), getDateTime(endTime));
    }

    // =

    /**
     * 判断时间是否在 [startTime, endTime] 区间 ( 自定义格式 )
     * @param time      待判断时间
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param pattern   时间格式
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isInTimeFormat(
            final String time,
            final String startTime,
            final String endTime,
            final String pattern
    ) {
        return isInTimeFormat(time, startTime, endTime, pattern, false);
    }

    /**
     * 判断时间是否在 [startTime, endTime] 区间 ( 自定义格式 )
     * @param time               待判断时间
     * @param startTime          开始时间
     * @param endTime            结束时间
     * @param pattern            时间格式
     * @param handlerMoreThanDay 是否处理大于一天的时间
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isInTimeFormat(
            final String time,
            final String startTime,
            final String endTime,
            final String pattern,
            final boolean handlerMoreThanDay
    ) {
        return isInTimeFormat(
                time, startTime, endTime,
                getSafeDateFormat(pattern),
                handlerMoreThanDay
        );
    }

    /**
     * 判断时间是否在 [startTime, endTime] 区间 ( 自定义格式 )
     * @param time      待判断时间
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param format    {@link SimpleDateFormat}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isInTimeFormat(
            final String time,
            final String startTime,
            final String endTime,
            final SimpleDateFormat format
    ) {
        return isInTimeFormat(time, startTime, endTime, format, false);
    }

    /**
     * 判断时间是否在 [startTime, endTime] 区间 ( 自定义格式 )
     * <pre>
     *     handlerMoreThanDay 参数注意事项
     *     用于 {@link DevFinal.TIME#HHmm_COLON}、{@link DevFinal.TIME#HHmmss_COLON} 判断, 只有该格式判断可传入 true
     *     其他都用于 false
     * </pre>
     * @param time               待判断时间
     * @param startTime          开始时间
     * @param endTime            结束时间
     * @param format             {@link SimpleDateFormat}
     * @param handlerMoreThanDay 是否处理大于一天的时间
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isInTimeFormat(
            final String time,
            final String startTime,
            final String endTime,
            final SimpleDateFormat format,
            final boolean handlerMoreThanDay
    ) {
        if (time == null || startTime == null || endTime == null) return false;
        long check = parseLong(time, format);
        long start = parseLong(startTime, format);
        long end   = parseLong(endTime, format);
        if (check == -1L || start == -1L || end == -1L) return false;
        // 大于一天的情况 ( 指的是结束时间在开始时间之前 )
        if (handlerMoreThanDay && end < start) {
            // 结束属于第二天区域
            return check >= start || check <= end;
        }
        // 时间是否在 [startTime, endTime] 区间
        return check >= start && check <= end;
    }

    // ========
    // = HHmm =
    // ========

    /**
     * 判断时间是否在 [startTime, endTime] 区间 ( HHmm 格式 )
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isInTimeHHmm(
            final String startTime,
            final String endTime
    ) {
        return isInTimeHHmm(startTime, endTime, true);
    }

    /**
     * 判断时间是否在 [startTime, endTime] 区间 ( HHmm 格式 )
     * @param startTime          开始时间
     * @param endTime            结束时间
     * @param handlerMoreThanDay 是否处理大于一天的时间
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isInTimeHHmm(
            final String startTime,
            final String endTime,
            final boolean handlerMoreThanDay
    ) {
        return isInTimeFormat(
                getDateNow(DevFinal.TIME.HHmm_COLON), startTime, endTime,
                DevFinal.TIME.HHmm_COLON, handlerMoreThanDay
        );
    }

    /**
     * 判断时间是否在 [startTime, endTime] 区间 ( HHmm 格式 )
     * @param time      待判断时间
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isInTimeHHmm(
            final String time,
            final String startTime,
            final String endTime
    ) {
        return isInTimeHHmm(time, startTime, endTime, true);
    }

    /**
     * 判断时间是否在 [startTime, endTime] 区间 ( HHmm 格式 )
     * @param time               待判断时间
     * @param startTime          开始时间
     * @param endTime            结束时间
     * @param handlerMoreThanDay 是否处理大于一天的时间
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isInTimeHHmm(
            final String time,
            final String startTime,
            final String endTime,
            final boolean handlerMoreThanDay
    ) {
        return isInTimeFormat(
                time, startTime, endTime,
                DevFinal.TIME.HHmm_COLON, handlerMoreThanDay
        );
    }

    // ==========
    // = HHmmss =
    // ==========

    /**
     * 判断时间是否在 [startTime, endTime] 区间 ( HHmmss 格式 )
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isInTimeHHmmss(
            final String startTime,
            final String endTime
    ) {
        return isInTimeHHmmss(startTime, endTime, true);
    }

    /**
     * 判断时间是否在 [startTime, endTime] 区间 ( HHmmss 格式 )
     * @param startTime          开始时间
     * @param endTime            结束时间
     * @param handlerMoreThanDay 是否处理大于一天的时间
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isInTimeHHmmss(
            final String startTime,
            final String endTime,
            final boolean handlerMoreThanDay
    ) {
        return isInTimeFormat(
                getDateNow(DevFinal.TIME.HHmmss_COLON), startTime, endTime,
                DevFinal.TIME.HHmmss_COLON, handlerMoreThanDay
        );
    }

    /**
     * 判断时间是否在 [startTime, endTime] 区间 ( HHmmss 格式 )
     * @param time      待判断时间
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isInTimeHHmmss(
            final String time,
            final String startTime,
            final String endTime
    ) {
        return isInTimeHHmmss(time, startTime, endTime, true);
    }

    /**
     * 判断时间是否在 [startTime, endTime] 区间 ( HHmmss 格式 )
     * @param time               待判断时间
     * @param startTime          开始时间
     * @param endTime            结束时间
     * @param handlerMoreThanDay 是否处理大于一天的时间
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isInTimeHHmmss(
            final String time,
            final String startTime,
            final String endTime,
            final boolean handlerMoreThanDay
    ) {
        return isInTimeFormat(
                time, startTime, endTime,
                DevFinal.TIME.HHmmss_COLON, handlerMoreThanDay
        );
    }

    // =

    /**
     * 获取指定时间距离该时间第二天的指定时段的时间 ( 判断凌晨情况 )
     * @param endTime 结束时间 HH:mm
     * @return 距离指定结束时间还有多少毫秒
     */
    public static long getEndTimeDiffHHmm(final String endTime) {
        return getEndTimeDiff(System.currentTimeMillis(), endTime, DevFinal.TIME.HHmm_COLON);
    }

    /**
     * 获取指定时间距离该时间第二天的指定时段的时间 ( 判断凌晨情况 )
     * @param startTime 开始时间
     * @param endTime   结束时间 HH:mm
     * @return 距离指定结束时间还有多少毫秒
     */
    public static long getEndTimeDiffHHmm(
            final long startTime,
            final String endTime
    ) {
        return getEndTimeDiff(startTime, endTime, DevFinal.TIME.HHmm_COLON);
    }

    /**
     * 获取指定时间距离该时间第二天的指定时段的时间差 ( 判断凌晨情况 )
     * @param endTime 结束时间
     * @param format  格式 如: HH:mm
     * @return 距离指定结束时间还有多少毫秒
     */
    public static long getEndTimeDiff(
            final String endTime,
            final String format
    ) {
        return getEndTimeDiff(System.currentTimeMillis(), endTime, format);
    }

    /**
     * 获取指定时间距离该时间第二天的指定时段的时间差 ( 判断凌晨情况 )
     * <pre>
     *     如当前时间 2018-12-07 15:27:23, 判断距离 14:39:20 (endTime) 有多久
     *     如果过了这个时间段, 则返回 2018-12-08 14:39:20 ( 明天的这个时间段时间 )
     *     如果没有过这个时间段 ( 如: 17:39:20) 则返回当天时间段 2018-12-07 17:39:20 (2018-12-07 + endTime)
     * </pre>
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param format    格式 如: HH:mm
     * @return 距离指定结束时间还有多少毫秒
     */
    public static long getEndTimeDiff(
            final long startTime,
            final String endTime,
            final String format
    ) {
        if (startTime < 1 || endTime == null || format == null) return -1L;
        try {
            // 判断格式是否加了秒
            boolean isSecond = format.endsWith(":ss");
            // 获取开始时间
            String start = formatTime(startTime, format);
            // 转换时间
            int startNumber = Integer.parseInt(start.replace(":", ""));
            // 获取结束时间转换
            int endNumber = Integer.parseInt(endTime.replace(":", ""));
            // 时间处理
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date(startTime)); // 设置当前时间
            // 如果当前时间大于结束时间, 表示非第二天
            if (startNumber > endNumber) {
                // 时间累加一天
                calendar.add(Calendar.DATE, 1); // 当前日期加一天
            }
            // 获取天数时间
            String yyyyMMddDate = formatDate(calendar.getTime(), DevFinal.TIME.yyyyMMdd_HYPHEN);
            // 累加时间
            String yyyyMMddHHmmssDate = yyyyMMddDate + " " + endTime + (isSecond ? "" : ":00");
            // 返回转换后的时间
            return parseLong(yyyyMMddHHmmssDate, DevFinal.TIME.yyyyMMddHHmmss_HYPHEN);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getEndTimeDiff");
        }
        return -1L;
    }

    // ============
    // = 生肖、星座 =
    // ============

    // 生肖数组
    private static final String[] ZODIAC = {
            "猴", "鸡", "狗", "猪", "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊"
    };

    // 星座截止天数
    private static final int[] CONSTELLATION_DAY = {
            20, 19, 21, 21, 21, 22, 23, 23, 23, 24, 23, 22
    };

    // 星座对应日期
    private static final String[] CONSTELLATION_DATE = {
            "01.20-02.18", "02.19-03.20", "03.21-04.19", "04.20-05.20", "05.21-06.21", "06.22-07.22",
            "07.23-08.22", "08.23-09.22", "09.23-10.23", "10.24-11.22", "11.23-12.21", "12.22-01.19"
    };

    // 星座数组
    private static final String[] CONSTELLATION = {
            "水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座",
            "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座"
    };

    /**
     * 获取生肖
     * @param year 年份
     * @return 生肖
     */
    public static String getZodiac(final int year) {
        return ZODIAC[Math.abs(year) % 12];
    }

    /**
     * 获取星座
     * @param month 月份
     * @param day   天数
     * @return 星座
     */
    public static String getConstellation(
            final int month,
            final int day
    ) {
        if (month > 12 || month < 1) return null;
        return CONSTELLATION[day >= CONSTELLATION_DAY[month - 1] ? month - 1 : (month + 10) % 12];
    }

    /**
     * 获取星座日期
     * @param month 月份
     * @param day   天数
     * @return 星座日期
     */
    public static String getConstellationDate(
            final int month,
            final int day
    ) {
        if (month > 12 || month < 1) return null;
        return CONSTELLATION_DATE[day >= CONSTELLATION_DAY[month - 1] ? month - 1 : (month + 10) % 12];
    }
}