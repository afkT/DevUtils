package dev.utils.app.logger;

/**
 * detail: 日志操作类(对外公开直接调用)
 * Created by Ttt
 */
public final class DevLogger {

	private DevLogger() {
	}

	/** 包下LoggerPrinter类持有对象 */
	private static final IPrinter printer = new LoggerPrinter();
	
	// -- 配置方法 --

	/**
	 * 使用单次其他日志配置
	 * @param lConfig
	 * @return
	 */
	public static IPrinter other(LogConfig lConfig) {
		return printer.other(lConfig);
	}

	/**
	 * 获取日志配置信息
	 * @return
	 */
	public static LogConfig getLogConfig() {
		return printer.getLogConfig();
	}

	/**
	 * 初始化日志配置信息(可以不调用,使用了 App 默认配置)
	 * @return
	 */
	public static LogConfig init() {
		return printer.init();
	}

	/**
	 * 手动改变日志配置信息(非单次,一直持续)
	 * @param lConfig
	 */
	public static void init(LogConfig lConfig) {
		printer.init(lConfig);
	}

	// ========= 使用默认TAG =========

	// -- 日志打印方法 --
	/**
	 * 打印 Log.DEBUG
	 * @param message
	 * @param args
	 */
	public static void d(String message, Object... args) {
		printer.d(message, args);
	}

	/**
	 * 打印 Log.ERROR
	 * @param message
	 * @param args
	 */
	public static void e(String message, Object... args) {
		printer.e(message, args);
	}

	/**
	 * 打印 Log.ERROR
	 * @param throwable
	 */
	public static void e(Throwable throwable) {
		printer.e(throwable, null);
	}

	/**
	 * 打印 Log.ERROR
	 * @param throwable
	 * @param message
	 * @param args
	 */
	public static void e(Throwable throwable, String message, Object... args) {
		printer.e(throwable, message, args);
	}

	/**
	 * 打印 Log.WARN
	 * @param message
	 * @param args
	 */
	public static void w(String message, Object... args) {
		printer.w(message, args);
	}

	/**
	 * 打印 Log.INFO
	 * @param message
	 * @param args
	 */
	public static void i(String message, Object... args) {
		printer.i(message, args);
	}

	/**
	 * 打印 Log.VERBOSE
	 * @param message
	 * @param args
	 */
	public static void v(String message, Object... args) {
		printer.v(message, args);
	}

	/**
	 * 打印 Log.ASSERT
	 * @param message
	 * @param args
	 */
	public static void wtf(String message, Object... args) {
		printer.wtf(message, args);
	}
	
	// -- 其他特殊方法 --

	/**
	 * 格式化Json格式数据,并打印
	 * @param json
	 */
	public static void json(String json) {
		printer.json(json);
	}

	/**
	 * 格式化XML格式数据,并打印
	 * @param xml
	 */
	public static void xml(String xml) {
		printer.xml(xml);
	}

	
	// ========= 使用自定义TAG =========

	// -- 日志打印方法 --
	/**
	 * 打印 Log.DEBUG
	 * @param tag
	 * @param message
	 * @param args
	 */
	public static void dTag(String tag, String message, Object... args) {
		printer.dTag(tag, message, args);
	}

	/**
	 * 打印 Log.ERROR
	 * @param tag
	 * @param message
	 * @param args
	 */
	public static void eTag(String tag, String message, Object... args) {
		printer.eTag(tag, message, args);
	}

	/**
	 * 打印 Log.ERROR
	 * @param tag
	 * @param throwable
	 * @param message
	 * @param args
	 */
	public static void eTag(String tag, Throwable throwable, String message, Object... args) {
		printer.eTag(tag, throwable, message, args);
	}

	/**
	 * 打印 Log.ERROR
	 * @param tag
	 * @param throwable
	 */
	public static void eTag(String tag, Throwable throwable) {
		printer.eTag(tag, throwable, null);
	}

	/**
	 * 打印 Log.WARN
	 * @param tag
	 * @param message
	 * @param args
	 */
	public static void wTag(String tag, String message, Object... args) {
		printer.wTag(tag, message, args);
	}

	/**
	 * 打印 Log.INFO
	 * @param tag
	 * @param message
	 * @param args
	 */
	public static void iTag(String tag, String message, Object... args) {
		printer.iTag(tag, message, args);
	}

	/**
	 * 打印 Log.VERBOSE
	 * @param tag
	 * @param message
	 * @param args
	 */
	public static void vTag(String tag, String message, Object... args) {
		printer.vTag(tag, message, args);
	}

	/**
	 * 打印 Log.ASSERT
	 * @param tag
	 * @param message
	 * @param args
	 */
	public static void wtfTag(String tag, String message, Object... args) {
		printer.wtfTag(tag, message, args);
	}
	
	// -- 其他特殊方法 --

	/**
	 * 格式化Json格式数据,并打印
	 * @param tag
	 * @param json
	 */
	public static void jsonTag(String tag, String json) {
		printer.jsonTag(tag, json);
	}

	/**
	 * 格式化XML格式数据,并打印
	 * @param tag
	 * @param xml
	 */
	public static void xmlTag(String tag, String xml) {
		printer.xmlTag(tag, xml);
	}
}
