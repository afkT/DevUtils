package dev.utils.common;

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
}
