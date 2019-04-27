package dev.utils.common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

    // ================
    // = 日期格式类型 =
    // ================

    public static final String yyyy = "yyyy";
    public static final String yyyyMMdd = "yyyy-MM-dd";
    public static final String yyyyMMdd2 = "yyyyMMdd";
    public static final String yyyyMMdd3 = "yyyy年MM月dd日";
    public static final String yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";
    public static final String yyyyMMddHHmmss_2 = "yyyy年M月d日 HH:mm:ss";
    public static final String MMdd = "MM-dd";
    public static final String MMdd2 = "MM月dd日";
    public static final String MMdd3 = "MMdd";
    public static final String HHmm = "HH:mm";
    public static final String HHmm2 = "HHmm";
    public static final String HHmmss = "HH:mm:ss";
    public static final String HHmmss2 = "HHmmss";
    public static final String hhmmMMDDyyyy = "hh:mm M月d日 yyyy";
    public static final String hhmmssMMDDyyyy = "hh:mm:ss M月d日 yyyy";

    // 一分钟 60秒
    public static final int MINUTE_S = 60;
    // 一小时 60 * 60秒
    public static final int HOUR_S = 3600;
    // 一天 24 * 60 * 60*/
    public static final int DAY_S = 86400;

    // 秒与毫秒的倍数
    public static final long SEC = 1000;
    // 分与毫秒的倍数
    public static final long MIN = SEC * 60;
    // 时与毫秒的倍数
    public static final long HOUR = MIN * 60;
    // 天与毫秒的倍数
    public static final long DAY = HOUR * 24;
    // 周与毫秒的倍数
    public static final long WEEK = DAY * 7;
    // 月与毫秒的倍数
    public static final long MONTH = DAY * 30;
    // 年与毫秒的倍数
    public static final long YEAR = DAY * 365;

    /**
     * 获取当前日期的字符串 - yyyy-MM-dd HH:mm:ss
     * @return 当前日期 yyyy-MM-dd HH:mm:ss 格式字符串
     */
    public static String getDateNow() {
        return getDateNow(yyyyMMddHHmmss);
    }

    /**
     * 获取当前日期的字符串
     * @param format 日期格式，如: yyyy-MM-dd HH:mm:ss
     * @return 当前日期指定格式字符串
     */
    public static String getDateNow(final String format) {
        if (format == null) return null;
        try {
            return new SimpleDateFormat(format).format(Calendar.getInstance().getTime());
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getDateNow");
        }
        return null;
    }

    // =

    /**
     * 将时间戳转换日期字符串
     * @param time   时间戳
     * @param format 日期格式
     * @return 按照指定格式的日期字符串
     */
    public static String formatTime(final long time, final String format) {
        if (format == null) return null;
        try {
            return new SimpleDateFormat(format).format(time);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "formatTime");
        }
        return null;
    }

    /**
     * 将 Date 转换日期字符串
     * @param date   日期
     * @param format 日期格式
     * @return 按照指定格式的日期字符串
     */
    public static String formatDate(final Date date, final String format) {
        if (date == null || format == null) return null;
        try {
            return new SimpleDateFormat(format).format(date);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "formatDate");
        }
        return null;
    }

    // =

    /**
     * 将时间戳转换成 Date
     * @param time 时间戳
     * @return {@link Date}
     */
    public static Date parseDate(final long time) {
        try {
            return new Date(time);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "parseDate");
        }
        return null;
    }

    /**
     * 将日期字符串转换为 Date - 默认表示time 属于 yyyy-MM-dd HH:mm:ss 格式
     * @param time 时间
     * @return {@link Date}
     */
    public static Date parseDate(final String time) {
        return parseDate(time, yyyyMMddHHmmss);
    }

    /**
     * 将日期字符串转换为 Date
     * @param time   时间
     * @param format 日期格式
     * @return {@link Date}
     */
    public static Date parseDate(final String time, final String format) {
        if (time == null || format == null) return null;
        try {
            return new SimpleDateFormat(format).parse(time);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "parseDate");
        }
        return null;
    }

    // =

    /**
     * 解析时间字符串转换为long毫秒 - 默认表示time 属于 yyyy-MM-dd HH:mm:ss 格式
     * @param time 时间
     * @return 毫秒时间
     */
    public static long parseLong(final String time) {
        return parseLong(time, yyyyMMddHHmmss);
    }

    /**
     * 解析时间字符串转换为long毫秒
     * @param time   时间
     * @param format 时间的格式
     * @return 毫秒时间
     */
    public static long parseLong(final String time, final String format) {
        if (time == null || format == null) return 0l;
        try {
            // 按规定的时间格式,进行格式化时间，并且获取long时间毫秒，返回毫秒时间
            return new SimpleDateFormat(format).parse(time).getTime();
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "parseLong");
        }
        return 0l;
    }

    /**
     * 转换时间为指定字符串
     * @param time       需要转换的时间
     * @param timeFormat time 的 时间格式
     * @param format     把 time 转换成需要的格式
     * @return 转换指定格式的时间字符串
     */
    public static String parseToString(final String time, final String timeFormat, final String format) {
        if (time != null && timeFormat != null && format != null) {
            try {
                Date date = parseDate(time, timeFormat);
                // 把时间转为所需格式字符串
                return formatDate(date, format);
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "parseToString");
            }
        }
        return null;
    }

    // =

    /**
     * 获取时间差 - 分钟
     * @param time 毫秒
     * @return 分钟
     */
    public static int getTimeDiffMinute(final long time) {
        return (int) (time / 60000); // 60 * 1000
    }

    /**
     * 获取时间差 - 小时
     * @param time 毫秒
     * @return 小时
     */
    public static int getTimeDiffHour(final long time) {
        return (int) (time / 3600000); // 60 * 1000 * 60
    }

    /**
     * 获取时间差 - 天
     * @param time 毫秒
     * @return 天
     */
    public static int getTimeDiffDay(final long time) {
        return (int) (time / 86400000); // 60 * 1000 * 60 * 24
    }

    /**
     * 获取时间差 - (传入时间 - 当前时间)
     * @param time 毫秒
     * @return 与当前时间的时间差(毫秒)
     */
    public static long getTimeDiff(final long time) {
        return time - System.currentTimeMillis();
    }

    /**
     * 获取时间差
     * @param time1 时间 yyyy-MM-dd HH:mm:ss 格式
     * @param time2 对比时间 yyyy-MM-dd HH:mm:ss 格式
     * @return 时间差(毫秒)
     */
    public static long getTimeDiff(final String time1, final String time2) {
        long timeLong1 = parseLong(time1);
        long timeLong2 = parseLong(time2);
        if (timeLong1 > 1l && timeLong2 > 1l) {
            return timeLong1 - timeLong2;
        }
        return -2l;
    }

    /**
     * 获取时间差
     * @param time1       时间
     * @param timeFormat1 时间格式
     * @param time2       对比时间
     * @param timeFormat2 对比时间格式
     * @return 时间差(毫秒)
     */
    public static long getTimeDiff(final String time1, final String timeFormat1, final String time2, final String timeFormat2) {
        long timeLong1 = parseLong(time1, timeFormat1);
        long timeLong2 = parseLong(time2, timeFormat2);
        if (timeLong1 > 1l && timeLong2 > 1l) {
            return timeLong1 - timeLong2;
        }
        return -2l;
    }

    // ============
    // = 获取时间 =
    // ============

    /**
     * 获取年
     * @param date 日期
     * @return 年
     */
    public static int getYear(final Date date) {
        if (date == null) return -1;
        try {
            Calendar cld = Calendar.getInstance();
            cld.setTime(date);
            return cld.get(Calendar.YEAR);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getYear");
        }
        return -1;
    }

    /**
     * 获取月 (0 - 11) + 1
     * @param date 日期
     * @return 月
     */
    public static int getMonth(final Date date) {
        if (date == null) return -1;
        try {
            Calendar cld = Calendar.getInstance();
            cld.setTime(date);
            return cld.get(Calendar.MONTH) + 1;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getMonth");
        }
        return -1;
    }

    /**
     * 获取日
     * @param date 日期
     * @return 日
     */
    public static int getDay(final Date date) {
        if (date == null) return -1;
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            return c.get(Calendar.DAY_OF_MONTH);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getDay");
        }
        return -1;
    }

    /**
     * 获取日期是星期几
     * @param date 日期
     * @return 日
     */
    public static int getWeek(final Date date) {
        if (date == null) return -1;
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            int week = c.get(Calendar.DAY_OF_WEEK);
            return week;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getWeek");
        }
        return -1;
    }

    /**
     * 获取时 - 24
     * @param date 日期
     * @return 时
     */
    public static int get24Hour(final Date date) {
        if (date == null) return -1;
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            return c.get(Calendar.HOUR_OF_DAY);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "get24Hour");
        }
        return -1;
    }

    /**
     * 获取时 - 12
     * @param date 日期
     * @return 时
     */
    public static int get12Hour(final Date date) {
        if (date == null) return -1;
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            return c.get(Calendar.HOUR);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "get12Hour");
        }
        return -1;
    }

    /**
     * 获取分
     * @param date 日期
     * @return 分
     */
    public static int getMinute(final Date date) {
        if (date == null) return -1;
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            return c.get(Calendar.MINUTE);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getMinute");
        }
        return -1;
    }

    /**
     * 获取秒
     * @param date 日期
     * @return 秒
     */
    public static int getSecond(final Date date) {
        if (date == null) return -1;
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            return c.get(Calendar.SECOND);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getSecond");
        }
        return -1;
    }

    /**
     * 转换时间处理, 小于10, 则自动补充 0x
     * @param time 待处理时间
     * @return 自动补0时间字符串
     */
    public static String convertTime(int time) {
        return convertTime(time, true);
    }

    /**
     * 转换时间处理, 小于10, 则自动补充 0x
     * @param time   待处理时间
     * @param append 判断是否需要自动补0
     * @return 自动补0时间字符串
     */
    public static String convertTime(final int time, final boolean append) {
        if (append) {
            int timeTmpe = time;
            // 防止出现负数
            timeTmpe = Math.max(0, timeTmpe);
            return timeTmpe >= 10 ? timeTmpe + "" : "0" + timeTmpe;
        }
        return time + "";
    }

    // =

    /**
     * 获取年
     * @return 年
     */
    public static int getYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    /**
     * 获取月 (0 - 11) + 1
     * @return 月
     */
    public static int getMonth() {
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        return month;
    }

    /**
     * 获取日
     * @return 日
     */
    public static int getDay() {
        return Calendar.getInstance().get(Calendar.DATE);
    }

    /**
     * 获取星期数
     * @return 星期
     */
    public static int getWeek() {
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取时 - 24
     * @return 24小时制小时
     */
    public static int get24Hour() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取时 - 12
     * @return 12小时制小时
     */
    public static int get12Hour() {
        return Calendar.getInstance().get(Calendar.HOUR);
    }

    /**
     * 获取分
     * @return 分钟
     */
    public static int getMinute() {
        return Calendar.getInstance().get(Calendar.MINUTE);
    }

    /**
     * 获取秒
     * @return 秒数
     */
    public static int getSecond() {
        return Calendar.getInstance().get(Calendar.SECOND);
    }

    // =

    /**
     * 判断是否闰年
     * @param year 年数
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isLeapYear(final int year) {
        // 判断是否闰年
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            return true;
        }
        return false;
    }

    /**
     * 根据年份、月份，获取对应的天数 (完整天数, 无判断是否属于未来日期)
     * @param year  年数
     * @param month 月份
     * @return 获取指定年份所属的月份的天数
     */
    public static int getMonthDayNumberAll(final int year, final int month) {
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
     * 根据年份，获取对应的月份
     * @param year 年份
     * @return 内部判断是否相同一年, 不能超过限制未来的月份
     */
    public static int getYearMonthNumber(final int year) {
        // 如: 当前 2019-01, 传入 2019 则返回 1
        // 传入 2018, 返回 12, 不会返回未来的月份
        if (year == getYear()) {
            return getMonth();
        }
        return 12;
    }

    /**
     * 根据年份、月份，获取对应的天数
     * @param year  年份
     * @param month 月份
     * @return 内部判断是否相同一年、月份, 不能超过限制未来的天数
     */
    public static int getMonthDayNumber(final int year, final int month) {
        // 判断年份, 相同则判断月份
        if (year == getYear()) {
            // 判断月份, 先同则返回天数
            if (getYearMonthNumber(year) == month) {
                return getDay();
            }
        }
        return getMonthDayNumberAll(year, month);
    }

    // =

    /**
     * 生成 HH 按时间排序数组
     * @return 按小时排序的数组
     */
    public static String[] getArrayToHH() {
        List<String> lists = getListToHH();
        return lists.toArray(new String[lists.size()]);
    }

    /**
     * 生成 HH 按时间排序集合
     * @return 按小时排序的集合
     */
    public static List<String> getListToHH() {
        List<String> lists = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            lists.add(DateUtils.convertTime(i, true));
        }
        return lists;
    }

    /**
     * 生成 MM 按时间排序数组
     * @return 按分钟排序的数组
     */
    public static String[] getArrayToMM() {
        List<String> lists = getListToMM();
        return lists.toArray(new String[lists.size()]);
    }

    /**
     * 生成 MM 按时间排序集合
     * @return 按分钟排序的集合
     */
    public static List<String> getListToMM() {
        List<String> lists = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            lists.add(DateUtils.convertTime(i, true));
        }
        return lists;
    }

    /**
     * 生成 HH:mm 按间隔时间排序数组
     * @param type 0 = 00:00 - 23:00 => 每小时间隔
     *             1 = 00:00 - 23:45 => 每15分钟间隔
     *             2 = 00:00 - 23:30 => 每30分钟间隔
     * @return 指定格式的数组
     */
    public static String[] getArrayToHHMM(final int type) {
        List<String> lists = getListToHHMM(type);
        return lists.toArray(new String[lists.size()]);
    }

    /**
     * 生成 HH:mm 按间隔时间排序集合
     * @param type 0 = 00:00 - 23:00 => 每小时间隔
     *             1 = 00:00 - 23:45 => 每15分钟间隔
     *             2 = 00:00 - 23:30 => 每30分钟间隔
     * @return 指定格式的集合
     */
    public static List<String> getListToHHMM(final int type) {
        List<String> lists = new ArrayList<>();
        switch (type) {
            case 0:
                for (int i = 0; i < 24; i++) {
                    lists.add(DateUtils.convertTime(i, true) + ":00");
                }
                break;
            case 1:
                for (int i = 0; i < 96; i++) { // 00 15 30 45 = 4 => 24 * 4
                    if (i % 2 == 0) { // 判断是否偶数 00、30
                        // 小时数
                        String hour = DateUtils.convertTime(i / 4, true);
                        // 分钟数
                        String minute = i % 4 == 0 ? "00" : "30";
                        // 累加时间
                        lists.add(hour + ":" + minute);
                    } else { // 15、45
                        // 小时数
                        String hour = DateUtils.convertTime(i / 4, true);
                        // 分钟数
                        String minute = (i - 1) % 4 == 0 ? "15" : "45";
                        // 累加时间
                        lists.add(hour + ":" + minute);
                    }
                }
                break;
            case 2:
                for (int i = 0; i < 48; i++) { // 00 30 = 2 => 24 * 2
                    // 小时处理
                    int hour = i / 2;
                    // 属于偶数
                    if (i % 2 == 0) {
                        lists.add(DateUtils.convertTime(hour, true) + ":00");
                    } else {
                        lists.add(DateUtils.convertTime(hour, true) + ":30");
                    }
                }
                break;
        }
        return lists;
    }

    /**
     * 获取 HH:mm 按间隔时间排序的集合中, 指定时间所在索引
     * @param time HH:mm格式
     * @param type 0 = 00:00 - 23:00 => 每小时间隔
     *             1 = 00:00 - 23:45 => 每15分钟间隔
     *             2 = 00:00 - 23:30 => 每30分钟间隔
     * @return 获取指定数据, 在对应格式类型内的索引
     */
    public static int getListToHHMMPosition(final String time, final int type) {
        if (time != null && time.length() != 0) {
            // 进行拆分
            String[] timeSplit = time.split(":");
            if (timeSplit != null && timeSplit.length == 2) {
                // 转换小时
                int hour = toInt(timeSplit[0], -1);
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
                        int minute = toInt(timeSplit[1], -1);
                        // 判断是否小于 0
                        if (minute < 0) {
                            return -1;
                        } else if (minute > 59) {
                            return -1;
                        }
                        // 判断间隔
                        if (type == 1) {
                            if (minute >= 0 && minute < 15) {
                                return hour * 4;
                            } else if (minute >= 15 && minute < 30) {
                                return hour * 4 + 1;
                            } else if (minute >= 30 && minute < 45) {
                                return hour * 4 + 2;
                            } else if (minute >= 30 && minute < 60) {
                                return hour * 4 + 3;
                            }
                        } else if (type == 2) { // 30 分钟一个间隔
                            // 大于等于30, 表示属于基数
                            if (minute >= 30) { // 属于奇数(30), 需要加 1
                                return hour * 2 + 1;
                            } else {
                                return hour * 2;
                            }
                        }
                        break;
                }
            }
        }
        return -1;
    }

    // =

    /**
     * 传入时间，获取时间(00:00:00 格式) - 不处理大于一天
     * @param time 时间(秒为单位)
     * @return 转换 (00:00:00 格式) 时间字符串
     */
    public static String secToTimeRetain(final int time) {
        return secToTimeRetain(time, false);
    }

    /**
     * 传入时间，获取时间(00:00:00 格式)
     * @param time          时间(秒为单位)
     * @param isHandlerMDay 是否处理大于一天的时间
     * @return 转换 (00:00:00 格式) 时间字符串
     */
    public static String secToTimeRetain(final int time, final boolean isHandlerMDay) {
        try {
            if (time <= 0) {
                return "00:00:00";
            } else {
                // 取模
                int rSecond;
                int rMinute;
                // 差数
                int dSecond;
                int dMinute;
                int dHour;
                // 转换时间格式
                if (time < MINUTE_S) { // 小于1分钟
                    return "00:00:" + ((time >= 10) ? time : ("0" + time));
                } else if (time >= MINUTE_S && time < HOUR_S) { // 小于1小时
                    dSecond = time % MINUTE_S; // 取模分钟，获取多出的秒数
                    dMinute = (time - dSecond) / MINUTE_S;
                    return "00:" + ((dMinute >= 10) ? dMinute : ("0" + dMinute)) + ":" + ((dSecond >= 10) ? dSecond : ("0" + dSecond));
                } else if (time >= HOUR_S && time < DAY_S) { // 小于等于一天
                    rMinute = time % HOUR_S; // 取模小时，获取多出的分钟
                    dHour = (time - rMinute) / HOUR_S; // 获取小时
                    dSecond = (time - dHour * HOUR_S); // 获取多出的秒数
                    dMinute = dSecond / MINUTE_S; // 获取多出的分钟
                    rSecond = dSecond % MINUTE_S; // 取模分钟，获取多余的秒速
                    return ((dHour >= 10) ? dHour : ("0" + dHour)) + ":" + ((dMinute >= 10) ? dMinute : ("0" + dMinute)) + ":" + ((rSecond >= 10) ? rSecond : "0" + rSecond);
                } else { // 多余的时间，直接格式化
                    // 大于一天的情况
                    if (isHandlerMDay) {
                        rMinute = time % HOUR_S; // 取模小时，获取多出的分钟
                        dHour = (time - rMinute) / HOUR_S; // 获取小时
                        dSecond = (time - dHour * HOUR_S); // 获取多出的秒数
                        dMinute = dSecond / MINUTE_S; // 获取多出的分钟
                        rSecond = dSecond % MINUTE_S; // 取模分钟，获取多余的秒速
                        return ((dHour >= 10) ? dHour : ("0" + dHour)) + ":" + ((dMinute >= 10) ? dMinute : ("0" + dMinute)) + ":" + ((rSecond >= 10) ? rSecond : "0" + rSecond);
                    }
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 传入时间,时间参数(小时、分钟、秒)
     * @param time 时间(秒为单位)
     * @return int[] { 小时、分钟、秒}
     */
    public static int[] convertTimeArys(final int time) {
        try {
            if (time <= 0) {
                return new int[]{0, 0, 0};
            } else {
                // 取模
                int rSecond;
                int rMinute;
                // 差数
                int dSecond;
                int dMinute;
                int dHour;
                // 转换时间格式
                if (time < MINUTE_S) { // 小于1分钟
                    return new int[]{0, 0, time};
                } else if (time >= MINUTE_S && time < HOUR_S) { // 小于1小时
                    dSecond = time % MINUTE_S; // 取模分钟，获取多出的秒数
                    dMinute = (time - dSecond) / MINUTE_S;
                    return new int[]{0, dMinute, dSecond};
                } else if (time >= HOUR_S && time < DAY_S) { // 小于等于一天
                    rMinute = time % HOUR_S; // 取模小时，获取多出的分钟
                    dHour = (time - rMinute) / HOUR_S; // 获取小时
                    dSecond = (time - dHour * HOUR_S); // 获取多出的秒数
                    dMinute = dSecond / MINUTE_S; // 获取多出的分钟
                    rSecond = dSecond % MINUTE_S; // 取模分钟，获取多余的秒速
                    return new int[]{dHour, dMinute, rSecond};
                } else { // 多余的时间，直接格式化
                    // 大于一天的情况
                    rMinute = time % HOUR_S; // 取模小时，获取多出的分钟
                    dHour = (time - rMinute) / HOUR_S; // 获取小时
                    dSecond = (time - dHour * HOUR_S); // 获取多出的秒数
                    dMinute = dSecond / MINUTE_S; // 获取多出的分钟
                    rSecond = dSecond % MINUTE_S; // 取模分钟，获取多余的秒速
                    return new int[]{dHour, dMinute, rSecond};
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 转换时间
     * @param millis    时间毫秒
     * @param precision precision = 0, return null
     *                  precision = 1, return 天
     *                  precision = 2, return 天, 小时
     *                  precision = 3, return 天, 小时, 分钟
     *                  precision = 4, return 天, 小时, 分钟, 秒
     *                  precision = 5，return 天, 小时, 分钟, 秒, 毫秒
     * @return 转换指定格式的时间字符串
     */
    public static String millisToFitTimeSpan(final long millis, final int precision) {
        if (millis <= 0 || precision <= 0) return null;

        long millisTime = millis;
        int precisionFormat = precision;

        StringBuilder builder = new StringBuilder();
        String[] units = {"天", "小时", "分钟", "秒", "毫秒"};
        int[] unitLen = {86400000, 3600000, 60000, 1000, 1};
        precisionFormat = Math.min(precisionFormat, 5);
        for (int i = 0; i < precisionFormat; i++) {
            if (millisTime >= unitLen[i]) {
                long mode = millisTime / unitLen[i];
                millisTime -= mode * unitLen[i];
                builder.append(mode).append(units[i]);
            }
        }
        return builder.toString();
    }

    /**
     * 转换时间为数组
     * @param millis 时间毫秒
     * @return int[5] { 天, 小时, 分钟, 秒, 毫秒}
     */
    public static int[] millisToTimeArys(final long millis) {
        if (millis <= 0) return null;
        int[] timeArys = new int[5];
        int[] unitLen = {86400000, 3600000, 60000, 1000, 1};
        long millisTime = millis;
        for (int i = 0; i < 5; i++) {
            if (millisTime >= unitLen[i]) {
                long mode = millisTime / unitLen[i];
                millisTime -= mode * unitLen[i];
                timeArys[i] = (int) mode;
            }
        }
        return timeArys;
    }

    // ======================
    // = 判断是否在区间范围 =
    // ======================

    /**
     * 判断时间是否在[startTime, endTime]区间，注意时间格式要一致
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isInTimeHHmm(final String startTime, final String endTime) {
        return isInTime(DateUtils.formatTime(System.currentTimeMillis(), HHmm), startTime, endTime, HHmm);
    }

    /**
     * 判断时间是否在[startTime, endTime]区间，注意时间格式要一致
     * @param nowTime   当前时间
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isInTimeHHmm(final String nowTime, final String startTime, final String endTime) {
        return isInTime(nowTime, startTime, endTime, HHmm);
    }

    /**
     * 判断时间是否在 [startTime, endTime] 区间，注意时间格式要一致
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isInTimeHHmmss(final String startTime, final String endTime) {
        return isInTime(DateUtils.formatTime(System.currentTimeMillis(), HHmmss), startTime, endTime, HHmmss);
    }

    /**
     * 判断时间是否在 [startTime, endTime] 区间，注意时间格式要一致
     * @param nowTime   当前时间
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isInTimeHHmmss(final String nowTime, final String startTime, final String endTime) {
        return isInTime(nowTime, startTime, endTime, HHmmss);
    }

    /**
     * 判断时间是否在 [startTime, endTime] 区间，注意时间格式要一致
     * @param nowTime   当前时间
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param format    时间格式
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isInTime(final String nowTime, final String startTime, final String endTime, final String format) {
        if (nowTime == null || startTime == null || endTime == null || format == null) return false;
        try {
            // 格式化日期
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            // 当前时间转换
            long now = sdf.parse(nowTime).getTime();
            // 开始时间转换
            long start = sdf.parse(startTime).getTime();
            // 结束时间转换
            long end = sdf.parse(endTime).getTime();
            // 判断结束时间是否小于开始时间
            if (end < start) { // 结束属于第二天区域
                if (now >= start || now <= end) {
                    return true;
                }
            } else {
                if (now >= start && now <= end) {
                    return true;
                }
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "isInTime");
        }
        return false;
    }

    /**
     * 判断时间是否在 [startTime, endTime] 区间，注意时间格式要一致
     * @param nowTime   当前时间
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isInTime(final long nowTime, final long startTime, final long endTime) {
        return isInDate(new Date(nowTime), new Date(startTime), new Date(endTime));
    }

    /**
     * 判断时间是否在 [startTime, endTime] 区间，注意时间格式要一致
     * @param nowTime   当前时间
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isInDate(final Date nowTime, final Date startTime, final Date endTime) {
        if (nowTime == null || startTime == null || endTime == null) {
            return false;
        } else if (nowTime.getTime() == startTime.getTime() || nowTime.getTime() == endTime.getTime()) {
            return true;
        }
        // 当前时间
        Calendar now = Calendar.getInstance();
        now.setTime(nowTime);
        // 开始时间
        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);
        // 结束时间
        Calendar end = Calendar.getInstance();
        end.setTime(endTime);
        // 判断是否在 begin 之后的时间, 并且在 end 之前的时间
        if (now.after(begin) && now.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    // =

    /**
     * 获取指定时间距离该时间第二天的指定时段的时间 (判断凌晨情况)
     * @param endTime 结束时间 HH:mm
     * @return 获取距离指定结束时间还有多少毫秒
     */
    public static long getEndTimeDiffHHmm(final String endTime) {
        return getEndTimeDiff(System.currentTimeMillis(), endTime, HHmm);
    }

    /**
     * 获取指定时间距离该时间第二天的指定时段的时间 (判断凌晨情况)
     * @param startTime 开始时间
     * @param endTime   结束时间 HH:mm
     * @return 获取距离指定结束时间还有多少毫秒
     */
    public static long getEndTimeDiffHHmm(final long startTime, final String endTime) {
        return getEndTimeDiff(startTime, endTime, HHmm);
    }

    /**
     * 获取指定时间距离该时间第二天的指定时段的时间差 (判断凌晨情况)
     * @param endTime 结束时间
     * @param format  格式 如: HH:mm
     * @return 获取距离指定结束时间还有多少毫秒
     */
    public static long getEndTimeDiff(final String endTime, final String format) {
        return getEndTimeDiff(System.currentTimeMillis(), endTime, format);
    }

    /**
     * 获取指定时间距离该时间第二天的指定时段的时间差 (判断凌晨情况)
     * <pre>
     *      如当前时间 2018-12-07 15:27:23, 判断距离 14:39:20(endTime) 有多久
     *      如果过了这个时间段, 则返回 2018-12-08 14:39:20 (明天的这个时间段时间)
     *      如果没有过这个时间段(如: 17:39:20) 则返回当天时间段 2018-12-07 17:39:20 (2018-12-07 + endTime)
     * </pre>
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param format    格式 如: HH:mm
     * @return 获取距离指定结束时间还有多少毫秒
     */
    public static long getEndTimeDiff(final long startTime, final String endTime, final String format) {
        if (startTime < 1 || endTime == null || format == null) return -1;
        try {
            // 判断格式是否加了秒
            boolean isSecond = format.endsWith(":ss");
            // 获取开始时间
            String start = DateUtils.formatTime(startTime, format);
            // 转换时间
            int startNumber = Integer.parseInt(start.replace(":", ""));
            // 获取结束时间转换
            int endNumber = Integer.parseInt(endTime.replace(":", ""));
            // 时间处理
            Calendar cld = Calendar.getInstance();
            cld.setTime(new Date(startTime)); // 设置当前时间
            // 如果当前时间大于结束时间, 表示非第二天
            if (startNumber > endNumber) {
                // 时间累加一天
                cld.add(Calendar.DATE, 1); // 当前日期加一天
            }
            // 获取天数时间
            String yyyyMMdd = DateUtils.formatDate(cld.getTime(), DateUtils.yyyyMMdd);
            // 累加时间
            String yyyyMMddHHmmss = yyyyMMdd + " " + endTime + (isSecond ? "" : ":00");
            // 返回转换后的时间
            return DateUtils.parseLong(yyyyMMddHHmmss, DateUtils.yyyyMMddHHmmss);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getEndTimeDiff");
        }
        return -1;
    }

    // =

    /**
     * 字符串 转 int
     * @param str          String
     * @param defaultValue 默认值
     * @return int, 如果转换失败, 则返回 defaultValue
     */
    private static int toInt(final String str, final int defaultValue) {
        if (str == null) return defaultValue;
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toInt");
        }
        return defaultValue;
    }
}
