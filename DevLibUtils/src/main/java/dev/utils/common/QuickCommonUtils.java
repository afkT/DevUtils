package dev.utils.common;

import android.os.SystemClock;

import java.util.Random;
import java.util.UUID;

import dev.utils.JCLogUtils;
import dev.utils.common.encrypt.MD5Utils;

/**
 * detail: 快捷通用
 * Created by Ttt
 */
public final class QuickCommonUtils {

    private QuickCommonUtils() {
    }

    // 日志 TAG
    private static final String TAG = QuickCommonUtils.class.getSimpleName();
    // 换行字符串
    private static final String NEW_LINE_STR = System.getProperty("line.separator");

    /**
     * 获取随机唯一数
     * @return
     */
    public static UUID randomUUID() {
        return UUID.randomUUID();
    }

    /**
     * 获取随机唯一数 HashCode
     * @return
     */
    public static int randomUUIDToHashCode() {
        return UUID.randomUUID().hashCode();
    }

    /**
     * 获取随机唯一数 HashCode
     * @param uuid
     * @return
     */
    public static int randomUUIDToHashCode(final UUID uuid) {
        return  (uuid != null) ? uuid.hashCode() : 0;
    }

    /**
     * 获取随机数 唯一id
     * @return
     */
    public static String getRandomUUID() {
        // 获取随机数
        String random1 = (900000 + new Random().nextInt(10000)) + "";
        // 获取随机数
        String random2 = (900000 + new Random().nextInt(10000)) + "";
        // 获取当前时间
        String cTime = Long.toString(System.currentTimeMillis()) + random1 + random2;
        // 生成唯一随机uuid  cTime.hashCode(), random1.hashCode() | random2.hashCode()
        UUID randomUUID = new UUID(cTime.hashCode(), ((long) random1.hashCode() << 32) | random2.hashCode());
        // 获取uid
        return randomUUID.toString();
    }

    // -

    /**
     * 循环 MD5 加密处理
     * @param data
     * @param number
     * @param isUppercase
     * @param salts
     * @return
     */
    public static String whileMD5(final String data, final int number, final boolean isUppercase, final String... salts) {
        if (data != null && number >= 1) {
            int saltLen = (salts != null) ? salts.length : 0;
            // 临时字符串
            String dataTemp = data;
            // 判断是否大写
            if (isUppercase) {
                // 循环加密
                for (int i = 0; i < number; i++) {
                    if (saltLen >= i) {
                        String salt = salts[i];
                        if (salt != null) {
                            dataTemp = MD5Utils.md5Upper(dataTemp + salt);
                        } else {
                            dataTemp = MD5Utils.md5Upper(dataTemp);
                        }
                    } else {
                        dataTemp = MD5Utils.md5Upper(dataTemp);
                    }
                }
            } else {
                // 循环加密
                for (int i = 0; i < number; i++) {
                    if (saltLen >= i) {
                        String salt = salts[i];
                        if (salt != null) {
                            dataTemp = MD5Utils.md5(dataTemp + salt);
                        } else {
                            dataTemp = MD5Utils.md5(dataTemp);
                        }
                    } else {
                        dataTemp = MD5Utils.md5(dataTemp);
                    }
                }
            }
        }
        return data;
    }

    // =

    /**
     * 转换手机号
     * @param phone
     */
    public static String converHideMobile(final String phone) {
        return converHideMobile(phone, "*");
    }

    /**
     * 转换手机号
     * @param phone
     * @param symbol 符号
     */
    public static String converHideMobile(final String phone, final String symbol) {
        return DevCommonUtils.converSymbolHide(3, phone, symbol);
    }

    /**
     * 耗时时间记录
     * @param buffer
     * @param sTime  开始时间
     * @param eTime  结束时间
     */
    public static void timeRecord(final StringBuffer buffer, final long sTime, final long eTime) {
        timeRecord(buffer, null, sTime, eTime);
    }

    /**
     * 耗时时间记录
     * @param buffer
     * @param title  标题
     * @param sTime  开始时间
     * @param eTime  结束时间
     */
    public static void timeRecord(final StringBuffer buffer, final String title, final long sTime, final long eTime) {
        if (buffer == null) return;
        // 使用时间
        long uTime = eTime - sTime;
        // 计算时间
        if (!DevCommonUtils.isEmpty(title)) {
            buffer.append(NEW_LINE_STR + title);
        }
        // 计算时间
        buffer.append("\n开始时间：" + DateUtils.formatTime(sTime, DateUtils.yyyyMMddHHmmss));
        buffer.append("\n结束时间：" + DateUtils.formatTime(eTime, DateUtils.yyyyMMddHHmmss));
        buffer.append("\n所用时间(毫秒)：" + uTime);
        buffer.append("\n所用时间(秒): " + (uTime / 1000));
    }

    /**
     * 获取操作时间
     * @param operateTime 操作时间(毫秒)
     * @return
     */
    public static long getOperateTime(final long operateTime) {
        return getOperateTime(operateTime, -1);
    }

    /**
     * 获取操作时间
     * @param operateTime 操作时间(毫秒)
     * @param randomTime  随机范围(毫秒)
     */
    public static long getOperateTime(final long operateTime, final int randomTime) {
        int random = 0;
        // 大于2才处理
        if (randomTime >= 2) {
            // 随机时间
            random = RandomUtils.getRandom(randomTime);
        }
        // 返回操作时间
        return Math.max(0, operateTime) + random;
    }

    /**
     * 堵塞操作
     * @param sleepTime
     */
    public static void sleepOperate(final long sleepTime) {
        sleepOperate(sleepTime, -1, false);
    }

    /**
     * 堵塞操作
     * @param sleepTime
     * @param randomTime
     */
    public static void sleepOperate(final long sleepTime, final int randomTime) {
        sleepOperate(sleepTime, randomTime, false);
    }

    /**
     * 堵塞操作
     * @param sleepTime
     * @param randomTime
     * @param isSystemClock
     */
    public static void sleepOperate(final long sleepTime, final int randomTime, final boolean isSystemClock) {
        long time = getOperateTime(sleepTime, randomTime);
        if (time != -1) {
            if (isSystemClock) {
                try {
                    SystemClock.sleep(sleepTime);
                } catch (Throwable e) {
                    JCLogUtils.eTag(TAG, e, "sleepOperate - SystemClock");
                    try {
                        Thread.sleep(sleepTime);
                    } catch (Throwable e1) {
                        JCLogUtils.eTag(TAG, e1, "sleepOperate - SystemClock Thread");
                    }
                }
            } else {
                try {
                    Thread.sleep(sleepTime);
                } catch (Throwable e1) {
                    JCLogUtils.eTag(TAG, e1, "sleepOperate - Thread");
                }
            }
        }
    }
}
