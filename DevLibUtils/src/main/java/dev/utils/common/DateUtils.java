package dev.utils.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import dev.utils.JCLogUtils;

/**
 * detail: 日期工具类
 * Created by Ttt
 */
public final class DateUtils {

	private DateUtils() {
	}
    
	// 日志TAG
	private static final String TAG = DateUtils.class.getSimpleName();
	/** 日期格式类型 */
	public static final String yyyy = "yyyy";
	public static final String yyyyMMdd = "yyyy-MM-dd";
	public static final String yyyyMMdd2 = "yyyyMMdd";
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
	// --
	/** 一分钟 60秒 */
	public static final int MINUTE_S = 60;
	/** 一小时 60 * 60秒 */
	public static final int HOUR_S = 3600;
	/** 一天 24 * 60 * 60*/
	public static final int DAY_S = 86400;

	/** 秒与毫秒的倍数 */
	public static final long SEC = 1000;
	/** 分与毫秒的倍数 */
	public static final long MIN = SEC * 60;
	/** 时与毫秒的倍数 */
	public static final long HOUR = MIN * 60;
	/** 天与毫秒的倍数 */
	public static final long DAY = HOUR * 24;
	/** 周与毫秒的倍数 */
	public static final long WEEK = DAY * 7;
	/** 月与毫秒的倍数 */
	public static final long MONTH = DAY * 30;
	/** 年与毫秒的倍数 */
	public static final long YEAR = DAY * 365;

	/**
	 * 获取当前日期的字符串 - "yyyy-MM-dd HH:mm:ss"
	 * @return 字符串
	 */
	public static String getDateNow(){
		return getDateNow(yyyyMMddHHmmss);
	}

	/**
	 * 获取当前日期的字符串
	 * @param format 日期格式，譬如："yyyy-MM-dd HH:mm:ss"
	 * @return 字符串
	 */
	public static String getDateNow(String format) {
		try {
			if ((format == null) || (format != null && format.equals("")))
				format = yyyyMMddHHmmss;

			Calendar cld = Calendar.getInstance();
			DateFormat df = new SimpleDateFormat(format);
			return df.format(cld.getTime());
		} catch (Exception e) {
		}
		return null;
	}

	// ==

	/**
	 * 将时间戳转换日期字符串
	 * @param time 时间戳
	 * @param format 日期格式
	 * @return 按照需求格式的日期字符串
	 */
	public static String formatTime(long time, String format){
		try {
			return new SimpleDateFormat(format).format(time);
		} catch (Exception e) {
		}
		return null;
	}
	
	/**
	 * 将Date类型转换日期字符串
	 * @param date 日期
	 * @param format 日期格式
	 * @return 按照需求格式的日期字符串
	 */
	public static String formatDate(Date date, String format) {
		try {
			return new SimpleDateFormat(format).format(date);
		} catch (Exception e) {
		}
		return null;
	}

	// ===

	/**
	 * 将时间戳转换成Date类型
	 * @param time
	 * @return
	 */
	public static Date parseDate(long time){
		try {
			return new Date(time);
		} catch (Exception e) {
			JCLogUtils.eTag(TAG, e, "parseDate");
		}
		return null;
	}

	/**
	 * 将日期字符串转换为Date类型 - 默认表示time 属于 yyyy-MM-dd HH:mm:ss 格式
	 * @param time
	 * @return
	 */
	public static Date parseDate(String time){
		return parseDate(time, yyyyMMddHHmmss);
	}

	/**
	 * 将日期字符串转换为Date类型
	 * @param time
	 * @param format
	 * @return
	 */
	public static Date parseDate(String time, String format) {
		try {
			if ((format == null) || (format != null && format.equals("")))
				format = yyyyMMddHHmmss;

			return new SimpleDateFormat(format).parse(time);
		} catch (Exception e) {
			JCLogUtils.eTag(TAG, e, "parseDate");
		}
		return null;
	}

	// ==

	/**
	 * 解析时间字符串转换为long毫秒 - 默认表示time 属于 yyyy-MM-dd HH:mm:ss 格式
	 * @param time
	 * @return
	 */
	public static long parseLong(String time){
		return parseLong(time, yyyyMMddHHmmss);
	}
	
	/**
	 * 解析时间字符串转换为long毫秒
	 * @param time 时间
	 * @param format 时间的格式
	 * @return
	 */
	public static long parseLong(String time, String format) {
		try {
			if ((format == null) || (format != null && format.equals("")))
				format = yyyyMMddHHmmss;

			SimpleDateFormat sdf = new SimpleDateFormat(format);
			// 按规定的时间格式,进行格式化时间，并且获取long时间毫秒
			long millionSeconds = sdf.parse(time).getTime();
			// 返回毫秒时间
			return millionSeconds;
		} catch (Exception e) {
			JCLogUtils.eTag(TAG, e, "parseLong");
		}
		return 0l;
	}

	// ==

	/**
	 * 获取时间差 - 分钟
	 * @param time(毫秒)
	 * @return
	 */
	public static int getTimeDiffMinute(long time){
		return (int) (time / 60000); // 60 * 1000
	}

	/**
	 * 获取时间差 - 小时
	 * @param time(毫秒)
	 * @return
	 */
	public static int getTimeDiffHour(long time){
		return (int) (time / 3600000); // 60 * 1000 * 60
	}

	/**
	 * 获取时间差 - 天
	 * @param time(毫秒)
	 * @return
	 */
	public static int getTimeDiffDay(long time){
		return (int) (time / 86400000); // 60 * 1000 * 60 * 24
	}

	/**
	 * 获取时间差 - (传入时间 - 当前时间)
	 * @param time
	 * @return
	 */
	public static long getTimeDiff(long time){
		return time - System.currentTimeMillis();
	}

	/**
	 * 获取时间差
	 * @param timeVal1
	 * @param timeVal2
	 * @return
	 */
	public static long getTimeDiff(String timeVal1, String timeVal2){
		long time1 = parseLong(timeVal1);
		long time2 = parseLong(timeVal2);
		if (time1 > 1l && time2 > 1l){
			return time1 - time2;
		}
		return -2l;
	}

	/**
	 * 获取时间差
	 * @param timeVal1
	 * @param timeFormat1
	 * @param timeVal2
	 * @param timeFormat2
	 * @return
	 */
	public static long getTimeDiff(String timeVal1, String timeFormat1, String timeVal2, String timeFormat2){
		long time1 = parseLong(timeVal1, timeFormat1);
		long time2 = parseLong(timeVal2, timeFormat2);
		if (time1 > 1l && time2 > 1l){
			return time1 - time2;
		}
		return -2l;
	}

//	System.out.println("现在时间: " + getDateNow());
//	String startTimeStr = "2017-11-27 14:45:00";
//	System.out.println("开始时间: " + startTimeStr);
//	long startTimeL = DateUtils.parseLong(startTimeStr);
//	System.out.println("转换long: " + startTimeL);
//	long timeDiff = startTimeL - System.currentTimeMillis();
//	System.out.println("时间差: " + timeDiff);

	// ======= 获取时间 =======

	/**
	 * 获取年
	 * @param date Date对象
	 * @return 年
	 */
	public static int getYear(Date date) {
		try {
			Calendar cld = Calendar.getInstance();
			cld.setTime(date);
			return cld.get(Calendar.YEAR);
		} catch (Exception e) {
		}
		return -1;
	}

	/**
	 * 获取月 (0 - 11) + 1
	 * @param date Date对象
	 * @return 月
	 */
	public static int getMonth(Date date) {
		try {
			Calendar cld = Calendar.getInstance();
			cld.setTime(date);
			return cld.get(Calendar.MONTH) + 1;
		} catch (Exception e) {
		}
		return -1;
	}

	/**
	 * 获取日
	 * @param date Date对象
	 * @return 日
	 */
	public static int getDay(Date date) {
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			return c.get(Calendar.DAY_OF_MONTH);
		} catch (Exception e) {
		}
		return -1;
	}

	/**
	 * 获取日期是星期几
	 * @param date Date对象
	 * @return 日
	 */
	public static int getWeek(Date date) {
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			int week = c.get(Calendar.DAY_OF_WEEK);
			return week;
		} catch (Exception e) {
		}
		return -1;
	}

	/**
	 * 获取时 - 24
	 * @param date Date对象
	 * @return 时
	 */
	public static int get24Hour(Date date) {
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			return c.get(Calendar.HOUR_OF_DAY);
		} catch (Exception e) {
		}
		return -1;
	}

	/**
	 * 获取时 - 12
	 * @param date Date对象
	 * @return 时
	 */
	public static int get12Hour(Date date) {
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			return c.get(Calendar.HOUR);
		} catch (Exception e) {
		}
		return -1;
	}

	/**
	 * 获取分
	 * @param date Date对象
	 * @return 分
	 */
	public static int getMinute(Date date) {
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			return c.get(Calendar.MINUTE);
		} catch (Exception e) {
		}
		return -1;
	}

	/**
	 * 获取秒
	 * @param date Date对象
	 * @return 秒
	 */
	public static int getSecond(Date date) {
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			return c.get(Calendar.SECOND);
		} catch (Exception e) {
		}
		return -1;
	}

	/**
	 * 转换时间处理, 小于10, 则自动补充 0x
	 * @param time
	 * @param append
	 * @return
	 */
	public static String convertTime(int time, boolean append){
		if (append){
			return time >= 10 ? time + "" : "0" + time;
		}
		return time + "";
	}

	// ==
	
	/**
	 * 获取年
	 * @return
	 */
	public static int getYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }
 
	/**
	 * 获取月 (0 - 11) + 1
	 * @return
	 */
    public static int getMonth() {
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        return month;
    }
 
    /**
     * 获取日
	 * @return
     */
    public static int getDay() {
        return Calendar.getInstance().get(Calendar.DATE);
    }

	/**
	 * 获取星期数
	 * @return
	 */
	public static int getWeek(){
    	return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
	}
 
    /**
     * 获取时 - 24
	 * @return
     */
    public static int get24Hour() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    }

	/**
	 * 获取时 - 12
	 * @return
	 */
	public static int get12Hour() {
		return Calendar.getInstance().get(Calendar.HOUR);
	}
 
    /**
     * 获取分
	 * @return
     */
    public static int getMinute() {
        return Calendar.getInstance().get(Calendar.MINUTE);
    }
 
    /**
     * 获取秒
     * @return
     */
    public static int getSecond() {
        return Calendar.getInstance().get(Calendar.SECOND);
    }

    // ==
    
    /**
	 * 判断是否闰年
	 * @param year 年数
	 * @return
	 */
    public static boolean isLeapYear(int year) {
		// 判断是否闰年
		if((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 获取月份 - 对应天数
	 * @param year 年数
	 * @param month 月份
	 * @return
	 */
	public static int getMonthDayNumber(int year, int month) {
		int number = 31;
		// 判断返回的标识数字
		switch(month) {
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
				if(isLeapYear(year)) {
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
    
    // =======================================================

	/**
	 * 传入时间，获取时间(00:00:00 格式) - 不处理大于一天
	 * @param time 时间(秒为单位)
	 * @return
	 */
	public static String secToTimeRetain(int time) {
		return secToTimeRetain(time, false);
	}
	
	/**
	 * 传入时间，获取时间(00:00:00 格式)
	 * @param time 时间(秒为单位)
	 * @param isHandlerMDay 是否处理大于一天的时间
	 * @return
	 */
	public static String secToTimeRetain(int time, boolean isHandlerMDay) {
		try {
			if(time <= 0) {
				return "00:00:00";
			} else {
				// 取模
				int rSecond = 0;
				int rMinute = 0;
				// 差数
				int dSecond = 0;
				int dMinute = 0;
				int dHour = 0;
				// 转换时间格式
				if(time < MINUTE_S) { // 小于1分钟
					return "00:00:" + ((time >=10)?time:("0" + time));
				} else if(time >= MINUTE_S && time < HOUR_S) { // 小于1小时
					dSecond = time % MINUTE_S; // 取模分钟，获取多出的秒数
					dMinute = (time - dSecond) / MINUTE_S;
					return "00:" +  ((dMinute >=10)?dMinute:("0" + dMinute)) + ":" + ((dSecond >=10)?dSecond:("0" + dSecond));
				} else if(time >= HOUR_S && time < DAY_S) { // 小于等于一天
					rMinute = time % HOUR_S; // 取模小时，获取多出的分钟
					dHour = (time - rMinute) / HOUR_S; // 获取小时
					dSecond = (time - dHour * HOUR_S); // 获取多出的秒数
					dMinute = dSecond / MINUTE_S; // 获取多出的分钟
					rSecond = dSecond % MINUTE_S; // 取模分钟，获取多余的秒速
					return ((dHour >= 10) ? dHour : ("0" + dHour)) + ":" + ((dMinute >= 10) ? dMinute:("0" + dMinute)) + ":" + ((rSecond >= 10) ? rSecond:"0" + rSecond);
				} else { // 多余的时间，直接格式化
					// 大于一天的情况
					if(isHandlerMDay) {
						rMinute = time % HOUR_S; // 取模小时，获取多出的分钟
						dHour = (time - rMinute) / HOUR_S; // 获取小时
						dSecond = (time - dHour * HOUR_S); // 获取多出的秒数
						dMinute = dSecond / MINUTE_S; // 获取多出的分钟
						rSecond = dSecond % MINUTE_S; // 取模分钟，获取多余的秒速
						return ((dHour >= 10) ? dHour : ("0" + dHour)) + ":" + ((dMinute >= 10) ? dMinute:("0" + dMinute)) + ":" + ((rSecond >= 10) ? rSecond:"0" + rSecond);
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
	 * @return
	 */
	public static int[] convertTimeArys(int time) {
		try {
			if(time <= 0) {
				return new int[] { 0, 0, 0 };
			} else {
				// 取模
				int rSecond = 0;
				int rMinute = 0;
				// 差数
				int dSecond = 0;
				int dMinute = 0;
				int dHour = 0;
				// 转换时间格式
				if(time < MINUTE_S) { // 小于1分钟
					return new int[]{ 0, 0, time };
				} else if(time >= MINUTE_S && time < HOUR_S) { // 小于1小时
					dSecond = time % MINUTE_S; // 取模分钟，获取多出的秒数
					dMinute = (time - dSecond) / MINUTE_S;
					return new int[]{ 0, dMinute, dSecond };
				} else if(time >= HOUR_S && time < DAY_S) { // 小于等于一天
					rMinute = time % HOUR_S; // 取模小时，获取多出的分钟
					dHour = (time - rMinute) / HOUR_S; // 获取小时
					dSecond = (time - dHour * HOUR_S); // 获取多出的秒数
					dMinute = dSecond / MINUTE_S; // 获取多出的分钟
					rSecond = dSecond % MINUTE_S; // 取模分钟，获取多余的秒速
					return new int[]{ dHour, dMinute, rSecond };
				} else { // 多余的时间，直接格式化
					// 大于一天的情况
					rMinute = time % HOUR_S; // 取模小时，获取多出的分钟
					dHour = (time - rMinute) / HOUR_S; // 获取小时
					dSecond = (time - dHour * HOUR_S); // 获取多出的秒数
					dMinute = dSecond / MINUTE_S; // 获取多出的分钟
					rSecond = dSecond % MINUTE_S; // 取模分钟，获取多余的秒速
					return new int[]{ dHour, dMinute, rSecond };
				}
			}
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 转换时间
	 * @param millis
	 * @param precision
	 * precision = 0, return null
	 * precision = 1, return 天
	 * precision = 2, return 天, 小时
	 * precision = 3, return 天, 小时, 分钟
	 * precision = 4, return 天, 小时, 分钟, 秒
	 * precision = 5，return 天, 小时, 分钟, 秒, 毫秒
	 * @return fit time span
	 */
	public static String millis2FitTimeSpan(long millis, int precision) {
		if (millis <= 0 || precision <= 0) return null;
		StringBuilder sb = new StringBuilder();
		String[] units = {"天", "小时", "分钟", "秒", "毫秒"};
		int[] unitLen = {86400000, 3600000, 60000, 1000, 1};
		precision = Math.min(precision, 5);
		for (int i = 0; i < precision; i++) {
			if (millis >= unitLen[i]) {
				long mode = millis / unitLen[i];
				millis -= mode * unitLen[i];
				sb.append(mode).append(units[i]);
			}
		}
		return sb.toString();
	}

	// == 判断是否在区间范围 ==

	/**
	 * 判断时间是否在[startTime, endTime]区间，注意时间格式要一致
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	public static boolean isInTimeHHmm(String startTime, String endTime){
		return isInTime(DateUtils.formatTime(System.currentTimeMillis(), HHmm), startTime, endTime, HHmm);
	}

	/**
	 * 判断时间是否在[startTime, endTime]区间，注意时间格式要一致
	 * @param nowTime 当前时间
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	public static boolean isInTimeHHmm(String nowTime, String startTime, String endTime){
		return isInTime(nowTime, startTime, endTime, HHmm);
	}

	/**
	 * 判断时间是否在[startTime, endTime]区间，注意时间格式要一致
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	public static boolean isInTimeHHmmss(String startTime, String endTime){
		return isInTime(DateUtils.formatTime(System.currentTimeMillis(), HHmmss), startTime, endTime, HHmmss);
	}

	/**
	 * 判断时间是否在[startTime, endTime]区间，注意时间格式要一致
	 * @param nowTime 当前时间
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	public static boolean isInTimeHHmmss(String nowTime, String startTime, String endTime){
		return isInTime(nowTime, startTime, endTime, HHmmss);
	}

	/**
	 * 判断时间是否在[startTime, endTime]区间，注意时间格式要一致
	 * @param nowTime 当前时间
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param format 时间格式
	 * @return
	 */
	public static boolean isInTime(String nowTime, String startTime, String endTime, String format){
		if (nowTime == null || startTime == null || endTime == null || format == null){
			return false;
		}
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
			if (end < start){ // 结束属于第二天区域
				if (now >= start || now <= end){
					return true;
				}
			} else {
				if (now >= start && now <= end){
					return true;
				}
			}
		} catch (Exception e){
			JCLogUtils.eTag(TAG, e, "isInTime");
		}
		return false;
	}

	/**
	 * 判断时间是否在[startTime, endTime]区间，注意时间格式要一致
	 * @param nowTime 当前时间
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	public static boolean isInTime(long nowTime, long startTime, long endTime){
		return isInDate(new Date(nowTime), new Date(startTime), new Date(endTime));
	}

	/**
	 * 判断时间是否在[startTime, endTime]区间，注意时间格式要一致
	 * @param nowTime 当前时间
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	public static boolean isInDate(Date nowTime, Date startTime, Date endTime) {
		if (nowTime == null || startTime == null || endTime == null){
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

	/**
	 * 获取指定时间距离该时间第二天的指定时段的时间 (判断凌晨情况)
	 * @param endTime 结束时间 HH:mm
	 * @return
	 */
	public static final long getEndTimeDiffHHmm(String endTime){
		return getEndTimeDiff(System.currentTimeMillis(), endTime, HHmm);
	}

	/**
	 * 获取指定时间距离该时间第二天的指定时段的时间 (判断凌晨情况)
	 * @param startTime 开始时间
	 * @param endTime 结束时间 HH:mm
	 * @return
	 */
	public static final long getEndTimeDiffHHmm(long startTime, String endTime){
		return getEndTimeDiff(startTime, endTime, HHmm);
	}

	/**
	 * 获取指定时间距离该时间第二天的指定时段的时间差 (判断凌晨情况)
	 * @param endTime 结束时间
	 * @param format 格式 如: HH:mm
	 * @return
	 */
	public static final long getEndTimeDiff(String endTime, String format){
		return getEndTimeDiff(System.currentTimeMillis(), endTime, format);
	}

	/**
	 * 获取指定时间距离该时间第二天的指定时段的时间差 (判断凌晨情况)
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param format 格式 如: HH:mm
	 * @return
	 * tips:
	 * 如当前时间 2018-12-07 15:27:23, 判断距离 14:39:20(endTime) 有多久
	 * 如果过了这个时间段, 则返回 2018-12-08 14:39:20 (明天的这个时间段时间)
	 * 如果没有过这个时间段(如: 17:39:20) 则返回当天时间段 2018-12-07 17:39:20 (2018-12-07 + endTime)
	 */
	public static final long getEndTimeDiff(long startTime, String endTime, String format){
		if (startTime < 1 || endTime == null || format == null){
			return -1;
		}
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
			if (startNumber > endNumber){
				// 时间累加一天
				cld.add(Calendar.DATE,1); // 当前日期加一天
			}
			// 获取天数时间
			String yyyyMMdd = DateUtils.formatDate(cld.getTime(), DateUtils.yyyyMMdd);
			// 累加时间
			String yyyyMMddHHmmss = yyyyMMdd + " " + endTime + (isSecond ? "" : ":00");
			// 返回转换后的时间
			return DateUtils.parseLong(yyyyMMddHHmmss, DateUtils.yyyyMMddHHmmss);
		} catch (Exception e){
			JCLogUtils.eTag(TAG, e, "getEndTimeDiff");
		}
		return -1;
	}
}
