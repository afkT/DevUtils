package dev.utils.common;

import android.os.SystemClock;

/**
 * detail: 快捷通用
 * Created by Ttt
 */
public final class QuickCommonUtils {

    private QuickCommonUtils(){
    }

    /**
     * 转换手机号
     * @param phone
     */
    public static String converHideMobile(String phone){
        return converHideMobile(phone, "*");
    }

    /**
     * 转换手机号
     * @param phone
     * @param symbol 符号
     */
    public static String converHideMobile(String phone, String symbol){
        return DevCommonUtils.converSymbolHide(3, phone, symbol);
    }

    /**
     * 耗时时间记录
     * @param buffer
     * @param title 标题
     * @param sTime 开始时间
     * @param eTime 结束时间
     */
    public static void timeRecord(StringBuffer buffer, String title, long sTime, long eTime) {
        // 使用时间
        long uTime = eTime - sTime;
        // 计算时间
        buffer.append("\n" + title);
        buffer.append("\n开始时间：" + sTime);
        buffer.append("\n结束时间：" + eTime);
        buffer.append("\n所用时间：" + uTime);
    }

    /**
     * 获取操作时间
     * @param operateTime 操作时间(毫秒)
     * @return
     */
    public static long getOperateTime(long operateTime){
        return getOperateTime(operateTime, -1);
    }

    /**
     * 获取操作时间
     * @param operateTime 操作时间(毫秒)
     * @param randomTime 随机范围(毫秒)
     */
    public static long getOperateTime(long operateTime, int randomTime){
        int random = 0;
        // 大于2才处理
        if (randomTime >= 2){
            // 随机时间
            random = RandomUtils.getRandom(randomTime);
        }
        if (operateTime >= 1){
            // 返回操作时间
            return operateTime + random;
        }
        return -1;
    }

    /**
     * 堵塞操作
     * @param sleepTime
     */
    public static void sleepOperate(long sleepTime){
        sleepOperate(sleepTime, -1);
    }

    /**
     * 堵塞操作
     * @param sleepTime
     * @param randomTime
     */
    public static void sleepOperate(long sleepTime, int randomTime){
        long time = getOperateTime(sleepTime, randomTime);
        if (time != -1){
            try {
                Thread.sleep(sleepTime);
            } catch (Exception e1){
            }
//            try {
//                SystemClock.sleep(sleepTime);
//            } catch (Throwable e){
//                try {
//                    Thread.sleep(sleepTime);
//                } catch (Exception e1){
//                }
//            }
        }
    }
}
