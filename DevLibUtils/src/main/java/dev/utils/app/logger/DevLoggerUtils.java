package dev.utils.app.logger;

import android.content.Context;
import android.text.TextUtils;

import java.io.File;

import dev.utils.LogPrintUtils;

/**
 * detail: 日志操作工具类
 * Created by Ttt
 */
public final class DevLoggerUtils {

	private DevLoggerUtils(){
	}

	// 日志TAG
	private static final String TAG = DevLoggerUtils.class.getSimpleName();

	/**
	 * App初始化调用方法(获取版本号)
	 * @param context
	 */
	public static void appInit(Context context) {
		// 保存 App 版本信息
		Utils.appInit(context);
	}
	
	// ================= 内部快速初始化LogConfig  ==================
	
	/**
	 * 获取发布Log配置(打印线程信息,显示方法总数3,从0开始,不进行排序, 默认属于ERROR级别日志)
	 * @param tag
	 * @return
	 */
	public static LogConfig getReleaseLogConfig(String tag) {
		return getLogConfig(tag, 3, 0, false, true, false, LogLevel.ERROR);
	}
	
	/**
	 * 获取发布Log配置(打印线程信息,显示方法总数3,从0开始,不进行排序)
	 * @param tag
	 * @param lLevel 日志级别(分四种) - LogLevel
	 * @return
	 */
	public static LogConfig getReleaseLogConfig(String tag, LogLevel lLevel) {
		return getLogConfig(tag, 3, 0, false, true, false, lLevel);
	}
	
	// --
	
	/**
	 * 获取调试Log配置(打印线程信息,显示方法总数3,从0开始,不进行排序, 默认属于ERROR级别日志)
	 * @param tag
	 * @return
	 */
	public static LogConfig getDebugLogConfig(String tag) {
		return getLogConfig(tag, 3, 0, false, true, false, LogLevel.DEBUG);
	}
	
	/**
	 * 获取调试Log配置(打印线程信息,显示方法总数3,从0开始,进行排序)
	 * @param tag
	 * @param lLevel 日志级别(分四种) - {@link LogLevel}
	 * @return
	 */
	public static LogConfig getDebugLogConfig(String tag, LogLevel lLevel) {
		return getLogConfig(tag, 3, 0, false, true, false, lLevel);
	}
	
	// --
	
	/**
	 * 获取Log配置(打印线程信息,显示方法总数3,从0开始,并且美化日志信息, 默认属于DEBUG级别日志)
	 * @param tag 日志Tag
	 * @return
	 */
	public static LogConfig getSortLogConfig(String tag) {
		return getLogConfig(tag, 3, 0, false, true, true, LogLevel.DEBUG);
	}

	/**
	 * 获取Log配置(打印线程信息,显示方法总数3,从0开始,并且美化日志信息)
	 * @param tag 日志Tag
	 * @param lLevel 日志级别(分四种) - LogLevel
	 * @return
	 */
	public static LogConfig getSortLogConfig(String tag, LogLevel lLevel) {
		return getLogConfig(tag, 3, 0, false, true, true, lLevel);
	}
	
	// --
	
	/**
	 * 获取Log配置
	 * @param tag 日志Tag
	 * @param mCount 显示的方法总数(推荐3)
	 * @param mOffset 方法偏移索引(从第几个方法开始打印,默认推荐0)
	 * @param mAll 是否打印全部方法
	 * @param tInfo 是否显示线程信息
	 * @param isSortLog 是否排序日志(美化)
	 * @param lLevel 日志级别(分四种) - LogLevel
	 * @return
	 */
	public static LogConfig getLogConfig(String tag, int mCount, int mOffset, boolean mAll, boolean tInfo, boolean isSortLog, LogLevel lLevel) {
		// 生成默认配置信息
		LogConfig logConfig = new LogConfig();
		// 堆栈方法总数(显示经过的方法)
		logConfig.methodCount = mCount;
		// 堆栈方法索引偏移(0 = 最新经过调用的方法信息,偏移则往上推,如 1 = 倒数第二条经过调用的方法信息)
		logConfig.methodOffset = mOffset;
		// 是否输出全部方法(在特殊情况下,如想要打印全部经过的方法,但是不知道经过的总数)
		logConfig.isOutputMethodAll = mAll;
		// 显示日志线程信息(特殊情况,显示经过的线程信息,具体情况如上)
		logConfig.isDisplayThreadInfo = tInfo;
		// 是否排序日志(格式化)
		logConfig.isSortLog = isSortLog;
		// 日志级别
		logConfig.logLevel = lLevel;
		// 设置Tag(特殊情况使用,不使用全部的Tag时,如单独输出在某个Tag下)
		logConfig.tag = tag;
		// 返回日志配置
		return logConfig;
	}
	
	// =============== 错误日志处理   ==================

	/**
	 * 保存app错误日志
	 * @param ex 错误信息
	 * @param fPath 保存路径 + 文件名(含后缀)
	 * @param isNewLines 是否换行
	 * @param eHint 错误提示(无设备信息、失败信息获取失败)
	 */
	public static boolean saveErrorLog(Throwable ex, String fPath, boolean isNewLines, String... eHint) {
		return saveErrorLog(ex,null, null, fPath, isNewLines, eHint);
	}
	
	/**
	 * 保存app错误日志
	 * @param ex 错误信息
	 * @param head 顶部标题
	 * @param bottom 底部内容
	 * @param fPath 保存路径 + 文件名(含后缀)
	 * @param isNewLines 是否换行
	 * @param eHint 错误提示(无设备信息、失败信息获取失败)
	 */
	public static boolean saveErrorLog(Throwable ex, String head, String bottom, String fPath, boolean isNewLines, String... eHint) {
		if(TextUtils.isEmpty(fPath)) {
			return false;
		}
		try {
			File file = new File(fPath);
			// 获取文件名
			String fName = file.getName();
			// 判断是否这个文件名结尾
			if(fPath.endsWith(fName)) {
				// 重新裁剪
				fPath = fPath.substring(0, fPath.length() - fName.length());
				// 进行保存
				return saveErrorLog(ex, head, bottom, fPath, fName, isNewLines, eHint);
			} else {
				// 进行保存
				return saveErrorLog(ex, head, bottom, fPath, fPath, isNewLines, eHint);
			}
		} catch (Exception e) {
			LogPrintUtils.eTag(TAG, e, "saveErrorLog");
		}
		return false;
	}

	/**
	 * 保存app错误日志
	 * @param ex 错误信息
	 * @param head 顶部标题
	 * @param bottom 底部内容
	 * @param fPath 保存路径
	 * @param fName 文件名(含后缀)
	 * @param isNewLines 是否换行
	 * @param eHint 错误提示(无设备信息、失败信息获取失败)
	 * @return
	 */
	public static boolean saveErrorLog(Throwable ex, String head, String bottom, String fPath, String fName, boolean isNewLines, String... eHint) {
		if(TextUtils.isEmpty(fPath)) {
			return false;
		} else if (TextUtils.isEmpty(fName)) {
			return false;
		}
		return Utils.saveErrorLog(ex, head, bottom, fPath, fName, isNewLines, eHint);
	}

	/**
	 * 保存app日志
	 * @param log 日志信息
	 * @param fPath 保存路径
	 * @param fName 文件名(含后缀)
	 * @param eHint 错误提示(无设备信息、失败信息获取失败)
	 * @return
	 */
	public static boolean saveLog(String log, String fPath, String fName, String... eHint){
		return saveLog(log, null, null, fPath, fName, eHint);
	}

	/**
	 * 保存app日志
	 * @param log 日志信息
	 * @param head 顶部标题
	 * @param bottom 底部内容
	 * @param fPath 保存路径
	 * @param fName 文件名(含后缀)
	 * @param eHint 错误提示(无设备信息、失败信息获取失败)
	 * @return
	 */
	public static boolean saveLog(String log, String head, String bottom, String fPath, String fName, String... eHint) {
		if(TextUtils.isEmpty(fPath)) {
			return false;
		} else if (TextUtils.isEmpty(fName)) {
			return false;
		}
		return Utils.saveLog(log, head, bottom, fPath, fName, eHint);
	}
}
