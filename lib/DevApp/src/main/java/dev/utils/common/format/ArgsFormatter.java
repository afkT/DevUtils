package dev.utils.common.format;

import dev.utils.JCLogUtils;

/**
 * detail: 可变数组格式化
 * @author Ttt
 */
public class ArgsFormatter {

    // 日志 TAG
    private final String TAG = ArgsFormatter.class.getSimpleName();

    // 开始占位说明符
    private final String  startSpecifier;
    // 中间占位说明符
    private final String  middleSpecifier;
    // 结尾占位说明符
    private final String  endSpecifier;
    // 是否抛出异常
    private final boolean throwError;
    // 格式化异常默认值
    private final String  defaultValue;

    /**
     * 构造函数
     * @param startSpecifier  开始占位说明符
     * @param middleSpecifier 中间占位说明符
     * @param endSpecifier    结尾占位说明符
     * @param throwError      是否抛出异常
     * @param defaultValue    格式化异常默认值
     */
    public ArgsFormatter(
            final String startSpecifier,
            final String middleSpecifier,
            final String endSpecifier,
            final boolean throwError,
            final String defaultValue
    ) {
        this.startSpecifier  = startSpecifier;
        this.middleSpecifier = middleSpecifier;
        this.endSpecifier    = endSpecifier;
        this.throwError      = throwError;
        this.defaultValue    = defaultValue;
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 获取 ArgsFormatter
     * @param startSpecifier  开始占位说明符
     * @param middleSpecifier 中间占位说明符
     * @return {@link ArgsFormatter}
     */
    public static ArgsFormatter get(
            final String startSpecifier,
            final String middleSpecifier
    ) {
        return new ArgsFormatter(
                startSpecifier, middleSpecifier, middleSpecifier,
                false, null
        );
    }

    /**
     * 获取 ArgsFormatter
     * @param startSpecifier  开始占位说明符
     * @param middleSpecifier 中间占位说明符
     * @param endSpecifier    结尾占位说明符
     * @return {@link ArgsFormatter}
     */
    public static ArgsFormatter get(
            final String startSpecifier,
            final String middleSpecifier,
            final String endSpecifier
    ) {
        return new ArgsFormatter(
                startSpecifier, middleSpecifier, endSpecifier,
                false, null
        );
    }

    /**
     * 获取 ArgsFormatter
     * @param startSpecifier  开始占位说明符
     * @param middleSpecifier 中间占位说明符
     * @param endSpecifier    结尾占位说明符
     * @param throwError      是否抛出异常
     * @param defaultValue    格式化异常默认值
     * @return {@link ArgsFormatter}
     */
    public static ArgsFormatter get(
            final String startSpecifier,
            final String middleSpecifier,
            final String endSpecifier,
            final boolean throwError,
            final String defaultValue
    ) {
        return new ArgsFormatter(
                startSpecifier, middleSpecifier, endSpecifier,
                throwError, defaultValue
        );
    }

    // =======
    // = get =
    // =======

    /**
     * 获取开始占位说明符
     * @return 开始占位说明符
     */
    public String getStartSpecifier() {
        return startSpecifier;
    }

    /**
     * 获取中间占位说明符
     * @return 中间占位说明符
     */
    public String getMiddleSpecifier() {
        return middleSpecifier;
    }

    /**
     * 获取结尾占位说明符
     * @return 结尾占位说明符
     */
    public String getEndSpecifier() {
        return endSpecifier;
    }

    /**
     * 是否抛出异常
     * @return {@code true} yes, {@code false} no
     */
    public boolean isThrowError() {
        return throwError;
    }

    /**
     * 获取格式化异常默认值
     * @return 格式化异常默认值
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    // ============
    // = 格式化方法 =
    // ============

    /**
     * 根据可变参数数量自动格式化
     * @param args 格式化参数
     * @return 格式化后的字符串
     */
    public String format(final Object... args) {
        return formatByArray(args);
    }

    /**
     * 根据可变参数数量自动格式化
     * @param objects 格式化参数
     * @return 格式化后的字符串
     */
    public String formatByArray(final Object[] objects) {
        if (objects != null && objects.length != 0) {
            String format = createFormatString(objects);
            try {
                return String.format(format, objects);
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "formatByArray");
                if (throwError) throw e;
            }
        }
        return defaultValue;
    }

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * 创建格式化占位说明符字符串
     * @param objects 格式化参数
     * @return 格式化占位说明符字符串
     */
    private String createFormatString(final Object[] objects) {
        StringBuilder builder = new StringBuilder();
        builder.append(startSpecifier);
        int length = objects.length;
        for (int i = 1; i < length; i++) {
            if (i == length - 1) {
                builder.append(endSpecifier);
            } else {
                builder.append(middleSpecifier);
            }
        }
        return builder.toString();
    }
}