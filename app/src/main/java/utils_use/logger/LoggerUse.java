package utils_use.logger;

import android.util.Log;

import dev.utils.app.logger.DevLogger;
import dev.utils.app.logger.LogConfig;
import dev.utils.app.logger.LogLevel;
import dev.utils.common.DevCommonUtils;

/**
 * detail: 日志使用方法
 * @author Ttt
 */
public final class LoggerUse {

    private LoggerUse() {
    }

    // 日志 TAG
    private static final String LOG_TAG = LoggerUse.class.getSimpleName();

    // =======
    // = 配置 =
    // =======

    /**
     * 日志配置相关
     */
    private void logConfig() {
        // = 在 BaseApplication 中调用 =
        // 初始化日志配置
        LogConfig logConfig = new LogConfig();
        // 堆栈方法总数 ( 显示经过的方法 )
        logConfig.methodCount = 3;
        // 堆栈方法索引偏移 (0 = 最新经过调用的方法信息, 偏移则往上推, 如 1 = 倒数第二条经过调用的方法信息 )
        logConfig.methodOffset = 0;
        // 是否输出全部方法 ( 在特殊情况下, 如想要打印全部经过的方法, 但是不知道经过的总数 )
        logConfig.outputMethodAll = false;
        // 显示日志线程信息 ( 特殊情况, 显示经过的线程信息, 具体情况如上 )
        logConfig.displayThreadInfo = false;
        // 是否排序日志 ( 格式化后 )
        logConfig.sortLog = false; // 是否美化日志, 边框包围
        // 日志级别
        logConfig.logLevel = LogLevel.DEBUG;
        // 设置 TAG ( 特殊情况使用, 不使用全部的 TAG 时, 如单独输出在某个 TAG 下 )
        logConfig.tag = "BaseLog";
        // 进行初始化配置, 这样设置后, 默认全部日志都使用改配置, 特殊使用 DevLogger.other(config).d(xxx);
        DevLogger.init(logConfig);
    }

    // = 使用 =

    /**
     * 日志使用方法
     */
    public static void loggerUse() {
        // 测试打印 Log 所用时间
        testTime();

        // 使用日志操作
        tempLog();
    }

    /**
     * 测试打印 Log 所用时间
     */
    public static void testTime() {
        // 拼接字符串
        StringBuilder builder = new StringBuilder();
        // 日志 TAG
        final String tag = "CALC_TIME";
        // =
        // 遍历次数
        int count = 100;
        // 设置开始时间
        long sTime = System.currentTimeMillis();
        // 开始遍历
        for (int i = 0; i < count; i++) {
            Log.d(tag, "A: " + (i + 1));
        }
        // 拼接时间信息
        DevCommonUtils.timeRecord(builder, "正常系统 Log 耗时记录", sTime, System.currentTimeMillis());

        // =
        // 设置开始时间
        sTime = System.currentTimeMillis();
        // 开始遍历
        for (int i = 0; i < count; i++) {
            // DevLogger.d("B: %s", (i + 1));
            DevLogger.dTag(tag, "B: %s", (i + 1));
        }
        // 拼接时间信息
        DevCommonUtils.timeRecord(builder, "\nLogger 耗时记录", sTime, System.currentTimeMillis());

        // =
        // 初始化日志配置
        LogConfig logConfig = new LogConfig();
        // 显示日志线程信息 ( 特殊情况, 显示经过的线程信息, 具体情况如上 )
        logConfig.displayThreadInfo = true;
        // 是否排序日志 ( 格式化后 )
        logConfig.sortLog = true;
        // 日志级别
        logConfig.logLevel = LogLevel.DEBUG;
        // 设置开始时间
        sTime = System.currentTimeMillis();
        // 开始遍历
        for (int i = 0; i < count; i++) {
            DevLogger.other(logConfig).dTag(tag, "C: %s", (i + 1));
        }
        // 拼接时间信息
        DevCommonUtils.timeRecord(builder, "\nLogger 耗时记录 - 使用自定义日志配置", sTime, System.currentTimeMillis());
        // 打印次数
        builder.append("\n\n打印次数: ").append(count);
        // 打印耗时信息
        DevLogger.dTag(LOG_TAG, builder.toString());
    }

    /**
     * 打印临时日志
     */
    public static void tempLog() {
        // = 打印零散数据 =
        TestData.ShareMsgVo sMsgVo = new TestData.ShareMsgVo();
        sMsgVo.sTitle = "分享 Blog";
        sMsgVo.sText = null;
        sMsgVo.sImagePath = "http://t.jpg";
        sMsgVo.sTitleUrl = "http://www.test.com";

        TestData.UserInfoVo uInfoVo = new TestData.UserInfoVo();
        uInfoVo.uName = "BlogRecord";
        uInfoVo.uPwd = "log_pwd";
        uInfoVo.uAge = 100;

        // 打印分享数据
        DevLogger.d(LogTools.getShareMsgVoData(sMsgVo));
        // 打印用户数据
        DevLogger.d(LogTools.getUserInfoVoData(uInfoVo));
        // 打印零散数据
        DevLogger.d(LogTools.getScatteredData(uInfoVo.uName, sMsgVo.sTitle, uInfoVo.uAge));

        // = 打印测试数据 =
        // 日志 TAG
        final String tag = LOG_TAG;
        // = 使用 BaseApplication 默认配置 =
        // JSON 数组
        DevLogger.json("[" + TestData.JSON_WITH_NO_LINE_BREAK + "," + TestData.JSON_WITH_NO_LINE_BREAK + "]");
        // JSON 对象
        DevLogger.json(TestData.SMALL_SON_WITH_NO_LINE_BREAK);
        // XML 数据
        DevLogger.xml(TestData.XML_DATA);
        // = 其他 =
        DevLogger.v("测试数据 - v");
        DevLogger.d("测试数据 - d");
        DevLogger.i("测试数据 - i");
        DevLogger.w("测试数据 - w");
        DevLogger.e("错误 - e");
        DevLogger.wtf("测试数据 - wtf");
        // =
        DevLogger.vTag(tag, "测试数据 - v");
        DevLogger.vTag(tag, "测试数据 - d");
        try {
            Class clazz = Class.forName("asdfasd");
        } catch (ClassNotFoundException e) {
            DevLogger.e(e, "发生异常");
        }
        // 占位符 ( 其他类型, 一样 )
        DevLogger.d("%s 测试占位符数据 d%s", "1.", "Format");
        // =
        DevLogger.dTag(tag, "%s 测试占位符数据 d%s", "1.", "Format");

        // = 使用自定义临时配置 =
        // 自定义配置, 如下使用方式
        // DevLogger.other(logConfig).d(message);
        // DevLogger.other(logConfig).dTag(tag, message);
        // 打印不换行的日志信息
        DevLogger.other(LogConfig.getDebugLogConfig(tag)).vTag("Temp", "测试数据 - v");
        DevLogger.other(LogConfig.getDebugLogConfig(tag)).d("测试数据 - d");
        DevLogger.other(LogConfig.getDebugLogConfig(tag)).i("测试数据 - i");
        DevLogger.other(LogConfig.getDebugLogConfig(tag)).w("测试数据 - w");
        DevLogger.other(LogConfig.getDebugLogConfig(tag)).e("错误 - e");
        DevLogger.other(LogConfig.getDebugLogConfig(tag)).wtf(tag, "测试数据 - wtf");
        // =
        DevLogger.other(LogConfig.getDebugLogConfig(tag, LogLevel.DEBUG)).json(TestData.SMALL_SON_WITH_NO_LINE_BREAK);

        // =
        // 初始化日志配置
        LogConfig logConfig = new LogConfig();
        // 堆栈方法总数 ( 显示经过的方法 )
        logConfig.methodCount = 3;
        // 堆栈方法索引偏移 (0 = 最新经过调用的方法信息, 偏移则往上推, 如 1 = 倒数第二条经过调用的方法信息 )
        logConfig.methodOffset = 0;
        // 是否输出全部方法 ( 在特殊情况下, 如想要打印全部经过的方法, 但是不知道经过的总数 )
        logConfig.outputMethodAll = false;
        // 显示日志线程信息 ( 特殊情况, 显示经过的线程信息, 具体情况如上 )
        logConfig.displayThreadInfo = true;
        // 是否排序日志 ( 格式化后 )
        logConfig.sortLog = true;
        // 日志级别
        logConfig.logLevel = LogLevel.DEBUG;
        // 设置 TAG ( 特殊情况使用, 不使用全部的 TAG 时, 如单独输出在某个 TAG 下 )
        logConfig.tag = "SAD";
        // 打印不换行的日志信息
        DevLogger.other(logConfig).e("new Config - e");

        // =
        // 使用方法
        LogConfig tempLogConfig = new LogConfig();
        // 堆栈方法总数 ( 显示经过的方法 )
        tempLogConfig.methodCount = 10;
        // 堆栈方法索引偏移 (0 = 最新经过调用的方法信息, 偏移则往上推, 如 1 = 倒数第二条经过调用的方法信息 )
        tempLogConfig.methodOffset = 0;
        // 是否输出全部方法 ( 在特殊情况下, 如想要打印全部经过的方法, 但是不知道经过的总数 )
        tempLogConfig.outputMethodAll = false;
        // 显示日志线程信息 ( 特殊情况, 显示经过的线程信息, 具体情况如上 )
        tempLogConfig.displayThreadInfo = true;
        // 是否排序日志 ( 格式化后 )
        tempLogConfig.sortLog = true;
        // 日志级别
        tempLogConfig.logLevel = LogLevel.DEBUG;
        // 设置 TAG ( 特殊情况使用, 不使用全部的 TAG 时, 如单独输出在某个 TAG 下 )
        tempLogConfig.tag = "SAD";
        try {
            String s = null;
            s.indexOf("tempLogConfig");
        } catch (Exception e) {
            // 打印不换行的日志信息
            DevLogger.other(tempLogConfig).e(e, "new Config - e");
        }
    }
}