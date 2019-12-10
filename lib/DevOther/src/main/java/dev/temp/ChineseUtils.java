package dev.temp;

import java.util.Random;

import dev.utils.JCLogUtils;

/**
 * detail: 随机生成汉字工具类
 * @author Ttt
 */
public final class ChineseUtils {

    private ChineseUtils() {
    }

    // 日志 TAG
    private static final String TAG = ChineseUtils.class.getSimpleName();

    /**
     * 获取随机汉字
     * @param number 汉字个数
     * @return 随机汉字
     */
    public static String getRandomWord(final int number) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < number; i++) {
            builder.append(getRandomWord());
        }
        return builder.toString();
    }

    /**
     * 获取一个随机汉字
     * @return 一个随机汉字
     */
    public static String getRandomWord() {
        Random random = new Random();
        int heightPos = 176 + Math.abs(random.nextInt(39));
        int lowPos = 161 + Math.abs(random.nextInt(93));
        byte[] bytes = new byte[2];
        bytes[0] = Integer.valueOf(heightPos).byteValue();
        bytes[1] = Integer.valueOf(lowPos).byteValue();
        try {
            return new String(bytes, "GBK");
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getRandomWord");
        }
        return "";
    }
}
