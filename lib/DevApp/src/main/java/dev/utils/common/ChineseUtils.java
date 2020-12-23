package dev.utils.common;

import java.math.BigDecimal;
import java.util.Random;

import dev.utils.JCLogUtils;

/**
 * detail: 中文工具类
 * @author Ttt
 */
public final class ChineseUtils {

    private ChineseUtils() {
    }

    // 日志 TAG
    private static final String TAG = ChineseUtils.class.getSimpleName();

    /**
     * 随机生成汉字
     * @param number 字数
     * @return 随机汉字
     */
    public static String randomWord(final int number) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < number; i++) {
            builder.append(randomWord());
        }
        return builder.toString();
    }

    /**
     * 随机生成汉字
     * @return 一个随机汉字
     */
    public static String randomWord() {
        Random random    = new Random();
        int    heightPos = 176 + Math.abs(random.nextInt(39));
        int    lowPos    = 161 + Math.abs(random.nextInt(93));
        byte[] bytes     = new byte[2];
        bytes[0] = Integer.valueOf(heightPos).byteValue();
        bytes[1] = Integer.valueOf(lowPos).byteValue();
        try {
            return new String(bytes, "GBK");
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "randomWord");
        }
        return "";
    }

    /**
     * 随机生成名字
     * @param surnames 姓氏数组
     * @param names    名字数组
     * @return 随机名字
     */
    public static String randomName(
            final String[] surnames,
            final String[] names
    ) {
        return randomName(surnames, names, 1);
    }

    /**
     * 随机生成名字
     * @param surnames   姓氏数组
     * @param names      名字数组
     * @param nameLength 名字长度
     * @return 随机名字
     */
    public static String randomName(
            final String[] surnames,
            final String[] names,
            final int nameLength
    ) {
        if (surnames != null && surnames.length != 0 && names != null && names.length != 0) {
            return RandomUtils.getRandom(surnames, 1) + RandomUtils.getRandom(names, nameLength);
        }
        return null;
    }

    // =================
    // = 数字、中文互转 =
    // =================

    // 零索引
    private static final int      ZERO                   = 0;
    // 十位数索引
    private static final int      TEN_POS                = 10;
    // 中文 ( 数字单位 )
    private static final String[] CHN_NUMBER_UNITS       = {
            "零", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "百", "千", "万", "亿", "兆"
    };
    // 中文大写 ( 数字单位 )
    private static final String[] CHN_NUMBER_UPPER_UNITS = {
            "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖", "拾", "佰", "仟", "万", "亿", "兆"
    };
    // 数字单位对应数值
    private static final double[] NUMBER_UNITS           = {
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 100, 1000, 10000, 100000000, 1000000000000d
    };

    // ===============
    // = 对外公开方法 =
    // ===============

    // =============
    // = 数字转中文 =
    // =============

    /**
     * 数字转中文数值
     * @param number  数值
     * @param isUpper 是否大写金额
     * @return 数字中文化字符串
     */
    public static String numberToCHN(
            final double number,
            final boolean isUpper
    ) {
        try {
            return numberToCHNNumber(BigDecimal.valueOf(number), isUpper ? CHN_NUMBER_UPPER_UNITS : CHN_NUMBER_UNITS);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "numberToCHN");
        }
        return null;
    }

    /**
     * 数字转中文数值
     * @param number  数值
     * @param isUpper 是否大写金额
     * @return 数字中文化字符串
     */
    public static String numberToCHN(
            final String number,
            final boolean isUpper
    ) {
        if (number != null) {
            try {
                return numberToCHNNumber(new BigDecimal(number), isUpper ? CHN_NUMBER_UPPER_UNITS : CHN_NUMBER_UNITS);
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "numberToCHN");
            }
        }
        return null;
    }

    /**
     * 数字转中文数值
     * @param number  数值
     * @param isUpper 是否大写金额
     * @return 数字中文化字符串
     */
    public static String numberToCHN(
            final BigDecimal number,
            final boolean isUpper
    ) {
        return numberToCHNNumber(number, isUpper ? CHN_NUMBER_UPPER_UNITS : CHN_NUMBER_UNITS);
    }

    // ===========
    // = 内部方法 =
    // ===========

    // =============
    // = 数字转中文 =
    // =============

    /**
     * 数字转中文数值
     * @param bigDecimal 数值
     * @param chnUnits   中文数字单位数组
     * @return 数字中文化字符串
     */
    private static String numberToCHNNumber(
            final BigDecimal bigDecimal,
            final String[] chnUnits
    ) {
        // 防止为 null
        if (bigDecimal == null) return null;
        // 去除小数点
        BigDecimal number = bigDecimal.setScale(0, BigDecimal.ROUND_DOWN);
        // 防止小于 1, 直接返回 0
        if (number.doubleValue() < 1) return chnUnits[ZERO];

        StringBuilder builder = new StringBuilder();
        // 索引记录
        int unitIndex       = 0; // 当前数字单位
        int beforeUnitIndex = 0; // 之前的数字单位
        // 循环处理
        for (int i = NUMBER_UNITS.length - 1; i > 0; i--) {
            if (number.doubleValue() >= NUMBER_UNITS[i]) {
                final int multiple = (int) (number.doubleValue() / NUMBER_UNITS[i]); // 倍数
                number = number.subtract(BigDecimal.valueOf(multiple * NUMBER_UNITS[i])); // 递减
                // 如果倍数大于万倍, 则直接递归
                if (multiple >= 10000) {
                    // 拼接数值
                    builder.append(numberToCHNNumber(new BigDecimal(multiple), chnUnits));
                    builder.append(chnUnits[i]); // 数字单位
                    // 判断是否需要补零
                    if (unitIndex > i && unitIndex != 0) {
                        builder.append(chnUnits[ZERO]); // 补零
                    }
                } else {
                    // 判断 兆级与亿级、亿级与万级 补零操作
                    if ((i == 14 && unitIndex == 15) || (i == 13 && unitIndex == 14)) {
                        if (multiple < 1000) {
                            builder.append(chnUnits[ZERO]); // 补零
                        }
                    } else if (unitIndex > i && unitIndex != 0) { // 跨数字单位处理
                        builder.append(chnUnits[ZERO]); // 补零
                    }
                    // 拼接数值
                    builder.append(thousandConvertCHN(multiple, chnUnits));
                    builder.append(chnUnits[i]); // 数字单位
                }
                // 保存旧的数字单位索引
                beforeUnitIndex = unitIndex;
                // 保存新的数字单位索引
                unitIndex = i;
            }

            double numberValue = number.doubleValue();
            // 如果位数小于万位 ( 属于千位 ), 则进行处理
            if (numberValue < 10000) {
                // 判断是否需要补零
                if (unitIndex >= (TEN_POS + 3)) {
                    // 是否大于 1 ( 结尾零, 则不补充数字单位 )
                    if (numberValue >= 1) {
                        if (beforeUnitIndex == 0) {
                            beforeUnitIndex = unitIndex;
                        }
                        // 如果旧的索引, 大于当前索引, 则补零
                        if (unitIndex != 13 && (beforeUnitIndex == 14 || beforeUnitIndex == 15)
                                && beforeUnitIndex >= unitIndex) { // 属于亿、兆级别, 都需要补零
                            builder.append(chnUnits[ZERO]); // 补零
                        } else { // 当前数字单位属于万级
                            if (numberValue < 1000) {
                                builder.append(chnUnits[ZERO]); // 补零
                            }
                        }
                    }
                }
                // 拼接数值
                builder.append(thousandConvertCHN((int) numberValue, chnUnits));
                return builder.toString();
            }
        }
        return builder.toString();
    }

    /**
     * 万位以下 ( 千位 ) 级别转换
     * @param value    数值
     * @param chnUnits 中文数字单位数组
     * @return 数字中文化字符串
     */
    private static String thousandConvertCHN(
            final int value,
            final String[] chnUnits
    ) {
        StringBuilder builder = new StringBuilder();
        // 转换数字
        int number = value;
        // 对应数值
        int[] units = {10, 100, 1000};
        // 判断是否需要补零
        boolean[] zeros = new boolean[3];
        zeros[0] = number >= 10; // 大于等于十位, 才需要补零
        zeros[1] = number >= 100; // 大于等于百位, 才需要补零
        zeros[2] = false; // 千位数, 不需要补零
        // 循环处理
        for (int i = 2; i >= 0; i--) {
            if (number >= units[i]) {
                int multiple = (number / units[i]);
                number -= multiple * units[i];
                // 拼接数值
                builder.append(chnUnits[multiple]); // 个位数
                builder.append(chnUnits[TEN_POS + i]); // 数字单位
                // 进行改变处理
                zeros[i] = false;
            }
            // 补零判断处理
            if (number >= 1 && zeros[i]) {
                if (i == 1) { // 属于百位
                    builder.append(chnUnits[ZERO]); // 补零
                } else { // 属于十位
                    // 如果百位, 补零了, 个位数则不用补零
                    if (!zeros[i + 1]) {
                        builder.append(chnUnits[ZERO]); // 补零
                    }
                }
            }
        }
        // 判断最后值, 是否大于 1 ( 结尾零, 则不补充数字单位 )
        if (number >= 1) {
            builder.append(chnUnits[number]);
        }
        return builder.toString();
    }
}