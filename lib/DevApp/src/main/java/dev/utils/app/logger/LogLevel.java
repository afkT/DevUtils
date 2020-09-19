package dev.utils.app.logger;

/**
 * detail: 日志级别
 * @author Ttt
 * <pre>
 *     Log.v 输出颜色为黑色, 任何消息都会输出, 这里的 v 代表 verbose 啰嗦的意思, 平时使用就是 Log.v
 *     Log.d 输出颜色为蓝色, 仅输出 debug 调试的意思, 但他会输出上层的信息, 过滤起来可以通过 DDMS 的 Logcat 标签来选择
 *     Log.i 输出颜色为绿色, 一般提示性的消息 information, 它不会输出 Log.v 和 Log.d 的信息, 但会显示 i、w 和 e 的信息
 *     Log.w 输出颜色为橙色, 可以看作为 warning 警告, 一般需要我们注意优化 Android 代码, 同时选择它后还会输出 Log.e 的信息
 *     Log.e 输出颜色为红色, 可以想到 error 错误, 这里仅显示红色的错误信息, 这些错误就需要我们认真的分析, 查看栈的信息了
 * </pre>
 */
public enum LogLevel {

    /**
     * 全部不打印
     */
    NONE,

    /**
     * 调试级别 v, d ( 全部打印 )
     */
    DEBUG,

    /**
     * 正常级别 i
     */
    INFO,

    /**
     * 警告级别 w
     */
    WARN,

    /**
     * 异常级别 e, wtf
     */
    ERROR
}