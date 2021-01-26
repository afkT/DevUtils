package dev.utils.common.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import dev.utils.JCLogUtils;
import dev.utils.common.StringUtils;

/**
 * detail: 居民身份证工具类
 * @author AbrahamCaiJin
 * @author Ttt
 */
public final class IDCardUtils {

    private IDCardUtils() {
    }

    // 日志 TAG
    private static final String TAG = IDCardUtils.class.getSimpleName();

    // 加权因子
    private static final int[]                POWER               = {
            7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2
    };
    // 身份证最少位数
    public static final  int                  CHINA_ID_MIN_LENGTH = 15;
    // 身份证最大位数
    public static final  int                  CHINA_ID_MAX_LENGTH = 18;
    // 省份编码
    private static final Map<String, String>  sCityCodeMaps       = new HashMap<>();
    // 台湾身份首字母对应数字
    private static final Map<String, Integer> sTWFirstCodeMaps    = new HashMap<>();
    // 香港身份首字母对应数字
    private static final Map<String, Integer> sHKFirstCodeMaps    = new HashMap<>();

    static {
        sCityCodeMaps.put("11", "北京");
        sCityCodeMaps.put("12", "天津");
        sCityCodeMaps.put("13", "河北");
        sCityCodeMaps.put("14", "山西");
        sCityCodeMaps.put("15", "内蒙古");
        sCityCodeMaps.put("21", "辽宁");
        sCityCodeMaps.put("22", "吉林");
        sCityCodeMaps.put("23", "黑龙江");
        sCityCodeMaps.put("31", "上海");
        sCityCodeMaps.put("32", "江苏");
        sCityCodeMaps.put("33", "浙江");
        sCityCodeMaps.put("34", "安徽");
        sCityCodeMaps.put("35", "福建");
        sCityCodeMaps.put("36", "江西");
        sCityCodeMaps.put("37", "山东");
        sCityCodeMaps.put("41", "河南");
        sCityCodeMaps.put("42", "湖北");
        sCityCodeMaps.put("43", "湖南");
        sCityCodeMaps.put("44", "广东");
        sCityCodeMaps.put("45", "广西");
        sCityCodeMaps.put("46", "海南");
        sCityCodeMaps.put("50", "重庆");
        sCityCodeMaps.put("51", "四川");
        sCityCodeMaps.put("52", "贵州");
        sCityCodeMaps.put("53", "云南");
        sCityCodeMaps.put("54", "西藏");
        sCityCodeMaps.put("61", "陕西");
        sCityCodeMaps.put("62", "甘肃");
        sCityCodeMaps.put("63", "青海");
        sCityCodeMaps.put("64", "宁夏");
        sCityCodeMaps.put("65", "新疆");
        sCityCodeMaps.put("71", "台湾");
        sCityCodeMaps.put("81", "香港");
        sCityCodeMaps.put("82", "澳门");
        sCityCodeMaps.put("83", "台湾");
        sCityCodeMaps.put("91", "国外");
        sTWFirstCodeMaps.put("A", 10);
        sTWFirstCodeMaps.put("B", 11);
        sTWFirstCodeMaps.put("C", 12);
        sTWFirstCodeMaps.put("D", 13);
        sTWFirstCodeMaps.put("E", 14);
        sTWFirstCodeMaps.put("F", 15);
        sTWFirstCodeMaps.put("G", 16);
        sTWFirstCodeMaps.put("H", 17);
        sTWFirstCodeMaps.put("J", 18);
        sTWFirstCodeMaps.put("K", 19);
        sTWFirstCodeMaps.put("L", 20);
        sTWFirstCodeMaps.put("M", 21);
        sTWFirstCodeMaps.put("N", 22);
        sTWFirstCodeMaps.put("P", 23);
        sTWFirstCodeMaps.put("Q", 24);
        sTWFirstCodeMaps.put("R", 25);
        sTWFirstCodeMaps.put("S", 26);
        sTWFirstCodeMaps.put("T", 27);
        sTWFirstCodeMaps.put("U", 28);
        sTWFirstCodeMaps.put("V", 29);
        sTWFirstCodeMaps.put("X", 30);
        sTWFirstCodeMaps.put("Y", 31);
        sTWFirstCodeMaps.put("W", 32);
        sTWFirstCodeMaps.put("Z", 33);
        sTWFirstCodeMaps.put("I", 34);
        sTWFirstCodeMaps.put("O", 35);
        sHKFirstCodeMaps.put("A", 1);
        sHKFirstCodeMaps.put("B", 2);
        sHKFirstCodeMaps.put("C", 3);
        sHKFirstCodeMaps.put("R", 18);
        sHKFirstCodeMaps.put("U", 21);
        sHKFirstCodeMaps.put("Z", 26);
        sHKFirstCodeMaps.put("X", 24);
        sHKFirstCodeMaps.put("W", 23);
        sHKFirstCodeMaps.put("O", 15);
        sHKFirstCodeMaps.put("N", 14);
    }

    /**
     * 身份证校验规则, 验证 15 位身份编码是否合法
     * @param idCard 待验证身份证号码
     * @return {@code true} yes, {@code false} no
     */
    public static boolean validateIdCard15(final String idCard) {
        // 属于数字, 并且长度为 15 位数
        if (isNumber(idCard) && idCard.length() == CHINA_ID_MIN_LENGTH) {
            // 获取省份编码
            String provinceCode = idCard.substring(0, 2);
            if (sCityCodeMaps.get(provinceCode) == null) return false;
            // 获取出生日期
            String birthCode = idCard.substring(6, 12);
            Date   birthDate = null;
            try {
                birthDate = new SimpleDateFormat("yy").parse(birthCode.substring(0, 2));
            } catch (ParseException e) {
                JCLogUtils.eTag(TAG, e, "validateIdCard15");
            }
            Calendar calendar = Calendar.getInstance();
            if (birthDate != null) calendar.setTime(birthDate);
            // 判断是否有效日期
            return validateDateSmallerThenNow(calendar.get(Calendar.YEAR),
                    Integer.valueOf(birthCode.substring(2, 4)),
                    Integer.valueOf(birthCode.substring(4, 6)));
        }
        return false;
    }

    /**
     * 身份证校验规则, 验证 18 位身份编码是否合法
     * @param idCard 待验证身份证号码
     * @return {@code true} yes, {@code false} no
     */
    public static boolean validateIdCard18(final String idCard) {
        if (idCard != null && idCard.length() == CHINA_ID_MAX_LENGTH) {
            // 前 17 位
            String code17 = idCard.substring(0, 17);
            // 第 18 位
            String code18 = idCard.substring(17, CHINA_ID_MAX_LENGTH);
            // 判断前 17 位是否数字
            if (isNumber(code17)) {
                try {
                    int[] cardArys = convertCharToInt(code17.toCharArray());
                    int   sum17    = getPowerSum(cardArys);
                    // 获取校验位
                    String str = getCheckCode18(sum17);
                    // 判断最后一位是否一样
                    if (str.length() > 0 && str.equalsIgnoreCase(code18)) {
                        return true;
                    }
                } catch (Exception e) {
                    JCLogUtils.eTag(TAG, e, "validateIdCard18");
                }
            }
        }
        return false;
    }

    /**
     * 将 15 位身份证号码转换为 18 位
     * @param idCard 15 位身份编码
     * @return 18 位身份编码
     */
    public static String convert15CardTo18(final String idCard) {
        // 属于数字, 并且长度为 15 位数
        if (isNumber(idCard) && idCard.length() == CHINA_ID_MIN_LENGTH) {
            String idCard18;
            Date   birthDate = null;
            // 获取出生日期
            String birthday = idCard.substring(6, 12);
            try {
                birthDate = new SimpleDateFormat("yyMMdd").parse(birthday);
            } catch (ParseException e) {
                JCLogUtils.eTag(TAG, e, "convert15CardTo18");
            }
            Calendar calendar = Calendar.getInstance();
            if (birthDate != null) calendar.setTime(birthDate);
            try {
                // 获取出生年 ( 完全表现形式, 如: 2010)
                String year = String.valueOf(calendar.get(Calendar.YEAR));
                // 保存省市区信息 + 年 + 月日 + 后续信息 ( 顺序位、性别等 )
                idCard18 = idCard.substring(0, 6) + year + idCard.substring(8);
                // 转换字符数组
                int[] cardArys = convertCharToInt(idCard18.toCharArray());
                int   sum17    = getPowerSum(cardArys);
                // 获取校验位
                String str = getCheckCode18(sum17);
                // 判断长度, 拼接校验位
                return (str.length() > 0) ? (idCard18 + str) : null;
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "convert15CardTo18");
            }
        }
        return null;
    }

    /**
     * 验证台湾身份证号码
     * @param idCard 身份证号码
     * @return {@code true} yes, {@code false} no
     */
    public static boolean validateTWCard(final String idCard) {
        // 台湾身份证 10 位
        if (idCard == null || idCard.length() != 10) return false;
        try {
            // 第一位英文 不同县市
            String  start  = idCard.substring(0, 1);
            String  mid    = idCard.substring(1, 9);
            String  end    = idCard.substring(9, 10);
            Integer iStart = sTWFirstCodeMaps.get(start);
            Integer sum    = iStart / 10 + (iStart % 10) * 9;
            char[]  chars  = mid.toCharArray();
            Integer iflag  = 8;
            for (char c : chars) {
                sum = sum + Integer.valueOf(String.valueOf(c)) * iflag;
                iflag--;
            }
            return (sum % 10 == 0 ? 0 : 10 - sum % 10) == Integer.valueOf(end);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "validateTWCard");
        }
        return false;
    }

    /**
     * 验证香港身份证号码 ( 部份特殊身份证无法检查 )
     * 身份证前 2 位为英文字符, 如果只出现一个英文字符则表示第一位是空格, 对应数字 58 前 2 位英文字符 A-Z 分别对应数字 10-35
     * 最后一位校验码为 0-9 的数字加上字符 "A", "A" 代表 10
     * 将身份证号码全部转换为数字, 分别对应乘 9-1 相加的总和, 整除 11 则证件号码有效
     * @param idCard 身份证号码
     * @return {@code true} yes, {@code false} no
     */
    public static boolean validateHKCard(final String idCard) {
        if (StringUtils.isEmpty(idCard)) return false;
        try {
            String  card = idCard.replaceAll("[\\(|\\)]", "");
            Integer sum;
            if (card.length() == 9) {
                sum = ((int) card.substring(0, 1).toUpperCase().toCharArray()[0] - 55) * 9 + ((int) card.substring(1, 2).toUpperCase().toCharArray()[0] - 55) * 8;
                card = card.substring(1, 9);
            } else {
                sum = 522 + ((int) card.substring(0, 1).toUpperCase().toCharArray()[0] - 55) * 8;
            }
            String  mid   = card.substring(1, 7);
            String  end   = card.substring(7, 8);
            char[]  chars = mid.toCharArray();
            Integer iflag = 7;
            for (char c : chars) {
                sum = sum + Integer.valueOf(String.valueOf(c)) * iflag;
                iflag--;
            }
            if (end.equalsIgnoreCase("A")) {
                sum = sum + 10;
            } else {
                sum = sum + Integer.valueOf(end);
            }
            return (sum % 11 == 0);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "validateHKCard");
        }
        return false;
    }

    /**
     * 判断 10 位数的身份证号, 是否合法
     * @param idCard 身份证号码
     * @return {@code true} yes, {@code false} no
     */
    public static String[] validateIdCard10(final String idCard) {
        if (StringUtils.isEmpty(idCard)) return null;
        String[] info = new String[3];
        info[0] = "N"; // 默认未知地区
        info[1] = "N"; // 默认未知性别
        info[2] = "false"; // 默认非法
        try {
            String card = idCard.replaceAll("[\\(|\\)]", "");
            // 获取身份证长度
            int cardLength = card.length();
            // 属于 8, 9, 10 长度范围内
            if (cardLength >= 8 || cardLength <= 10) {
                if (idCard.matches("^[a-zA-Z][0-9]{9}$")) { // 台湾
                    info[0] = "台湾";
                    String char2 = idCard.substring(1, 2);
                    if (char2.equals("1")) {
                        info[1] = "M";
                    } else if (char2.equals("2")) {
                        info[1] = "F";
                    } else {
                        info[1] = "N";
                        info[2] = "false";
                        return info;
                    }
                    info[2] = validateTWCard(idCard) ? "true" : "false";
                } else if (idCard.matches("^[1|5|7][0-9]{6}\\(?[0-9A-Z]\\)?$")) { // 澳门
                    info[0] = "澳门";
                    info[1] = "N";
                    // TODO
                } else if (idCard.matches("^[A-Z]{1,2}[0-9]{6}\\(?[0-9A]\\)?$")) { // 香港
                    info[0] = "香港";
                    info[1] = "N";
                    info[2] = validateHKCard(idCard) ? "true" : "false";
                }
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "validateIdCard10");
        }
        return info;
    }

    /**
     * 验证身份证是否合法
     * @param idCard 身份证号码
     * @return {@code true} yes, {@code false} no
     */
    public static boolean validateCard(final String idCard) {
        if (StringUtils.isEmpty(idCard)) return false;
        String card = idCard.trim();
        if (validateIdCard18(card)) return true;
        if (validateIdCard15(card)) return true;
        String[] cardArys = validateIdCard10(card);
        return (cardArys != null && "true".equals(cardArys[2]));
    }

    /**
     * 根据身份编号获取年龄
     * @param idCard 身份编号
     * @return 年龄
     */
    public static int getAgeByIdCard(final String idCard) {
        if (StringUtils.isEmpty(idCard)) return 0;
        try {
            String idCardStr = idCard;
            // 属于 15 位身份证, 则转换为 18 位
            if (idCardStr.length() == CHINA_ID_MIN_LENGTH) {
                idCardStr = convert15CardTo18(idCard);
            }
            // 属于 18 位身份证才处理
            if (idCardStr.length() == CHINA_ID_MAX_LENGTH) {
                String year = idCardStr.substring(6, 10);
                // 获取当前年份
                int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                // 当前年份 ( 出生年份 )
                return currentYear - Integer.valueOf(year);
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getAgeByIdCard");
        }
        return 0;
    }

    /**
     * 根据身份编号获取生日
     * @param idCard 身份编号
     * @return 生日 (yyyyMMdd)
     */
    public static String getBirthByIdCard(final String idCard) {
        if (StringUtils.isEmpty(idCard)) return null;
        try {
            String idCardStr = idCard;
            // 属于 15 位身份证, 则转换为 18 位
            if (idCardStr.length() == CHINA_ID_MIN_LENGTH) {
                idCardStr = convert15CardTo18(idCard);
            }
            // 属于 18 位身份证才处理
            if (idCardStr.length() == CHINA_ID_MAX_LENGTH) {
                return idCardStr.substring(6, 14);
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getBirthByIdCard");
        }
        return null;
    }

    /**
     * 根据身份编号获取生日
     * @param idCard 身份编号
     * @return 生日 (yyyyMMdd)
     */
    public static String getBirthdayByIdCard(final String idCard) {
        // 获取生日
        String birth = getBirthByIdCard(idCard);
        // 进行处理
        if (birth != null) {
            try {
                return birth.replaceAll("(\\d{4})(\\d{2})(\\d{2})", "$1-$2-$3");
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "getBirthdayByIdCard");
            }
        }
        return null;
    }

    /**
     * 根据身份编号获取生日 ( 年份 )
     * @param idCard 身份编号
     * @return 生日 (yyyy)
     */
    public static String getYearByIdCard(final String idCard) {
        // 获取生日
        String birth = getBirthByIdCard(idCard);
        // 进行处理
        if (birth != null) {
            try {
                return birth.substring(0, 4);
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "getYearByIdCard");
            }
        }
        return null;
    }

    /**
     * 根据身份编号获取生日 ( 月份 )
     * @param idCard 身份编号
     * @return 生日 (MM)
     */
    public static String getMonthByIdCard(final String idCard) {
        // 获取生日
        String birth = getBirthByIdCard(idCard);
        // 进行处理
        if (birth != null) {
            try {
                return birth.substring(4, 6);
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "getMonthByIdCard");
            }
        }
        return null;
    }

    /**
     * 根据身份编号获取生日 ( 天数 )
     * @param idCard 身份编号
     * @return 生日 (dd)
     */
    public static String getDateByIdCard(final String idCard) {
        // 获取生日
        String birth = getBirthByIdCard(idCard);
        // 进行处理
        if (birth != null) {
            try {
                return birth.substring(6, 8);
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "getDateByIdCard");
            }
        }
        return null;
    }

    /**
     * 根据身份编号获取性别
     * @param idCard 身份编号
     * @return 性别 男 (M)、女 (F)、未知 (N)
     */
    public static String getGenderByIdCard(final String idCard) {
        if (StringUtils.isEmpty(idCard)) return null;
        try {
            String idCardStr = idCard;
            // 属于 15 位身份证, 则转换为 18 位
            if (idCardStr.length() == CHINA_ID_MIN_LENGTH) {
                idCardStr = convert15CardTo18(idCard);
            }
            // 属于 18 位身份证才处理
            if (idCardStr.length() == CHINA_ID_MAX_LENGTH) {
                // 获取第 17 位性别信息
                String cardNumber = idCardStr.substring(16, 17);
                // 奇数为男, 偶数为女
                return (Integer.parseInt(cardNumber) % 2 == 0) ? "F" : "M";
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getGenderByIdCard");
        }
        // 默认未知
        return "N";
    }

    /**
     * 根据身份编号获取户籍省份
     * @param idCard 身份编码
     * @return 省级编码
     */
    public static String getProvinceByIdCard(final String idCard) {
        if (StringUtils.isEmpty(idCard)) return null;
        try {
            // 身份证长度
            int idCardLength = idCard.length();
            // 属于 15 位身份证、或 18 位身份证
            if (idCardLength == CHINA_ID_MIN_LENGTH || idCardLength == CHINA_ID_MAX_LENGTH) {
                return sCityCodeMaps.get(idCard.substring(0, 2));
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getProvinceByIdCard");
        }
        return null;
    }

    /**
     * 将身份证的每位和对应位的加权因子相乘之后, 再获取和值
     * @param data byte[] 数据
     * @return 身份证编码, 加权引子
     */
    public static int getPowerSum(final int[] data) {
        if (data == null) return 0;
        int len = data.length;
        if (len == 0) return 0;
        int powerLength = POWER.length;
        int sum         = 0;
        if (powerLength == len) {
            for (int i = 0; i < len; i++) {
                for (int j = 0; j < powerLength; j++) {
                    if (i == j) {
                        sum = sum + data[i] * POWER[j];
                    }
                }
            }
        }
        return sum;
    }

    /**
     * 将 POWER 和值与 11 取模获取余数进行校验码判断
     * @param sum {@link IDCardUtils#getPowerSum}
     * @return 校验位
     */
    public static String getCheckCode18(final int sum) {
        String code = "";
        switch (sum % 11) {
            case 10:
                code = "2";
                break;
            case 9:
                code = "3";
                break;
            case 8:
                code = "4";
                break;
            case 7:
                code = "5";
                break;
            case 6:
                code = "6";
                break;
            case 5:
                code = "7";
                break;
            case 4:
                code = "8";
                break;
            case 3:
                code = "9";
                break;
            case 2:
                code = "x";
                break;
            case 1:
                code = "0";
                break;
            case 0:
                code = "1";
                break;
        }
        return code;
    }

    // ===========
    // = 私有方法 =
    // ===========

    /**
     * 将字符数组转换成数字数组
     * @param data char[]
     * @return int[]
     */
    private static int[] convertCharToInt(final char[] data) {
        if (data == null) return null;
        int len = data.length;
        if (len == 0) return null;
        try {
            int[] arrays = new int[len];
            for (int i = 0; i < len; i++) {
                arrays[i] = Integer.parseInt(String.valueOf(data[i]));
            }
            return arrays;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "convertCharToInt");
        }
        return null;
    }

    /**
     * 验证小于当前日期 是否有效
     * @param yearData  待校验的日期 ( 年 )
     * @param monthData 待校验的日期 ( 月 1-12)
     * @param dayData   待校验的日期 ( 日 )
     * @return {@code true} yes, {@code false} no
     */
    private static boolean validateDateSmallerThenNow(
            final int yearData,
            final int monthData,
            final int dayData
    ) {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int datePerMonth;
        int MIN  = 1930;
        if (yearData < MIN || yearData >= year) {
            return false;
        }
        if (monthData < 1 || monthData > 12) {
            return false;
        }
        switch (monthData) {
            case 4:
            case 6:
            case 9:
            case 11:
                datePerMonth = 30;
                break;
            case 2:
                boolean dm = ((yearData % 4 == 0 && yearData % 100 != 0) || (yearData % 400 == 0)) && (yearData > MIN && yearData < year);
                datePerMonth = dm ? 29 : 28;
                break;
            default:
                datePerMonth = 31;
        }
        return (dayData >= 1) && (dayData <= datePerMonth);
    }

    /**
     * 检验数字
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    private static boolean isNumber(final String str) {
        return !StringUtils.isEmpty(str) && str.matches("^[0-9]*$");
    }
}