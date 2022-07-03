package dev.utils.common.validator;

/**
 * detail: 检验联系 ( 手机号码、座机 ) 工具类
 * @author Ttt
 * <pre>
 *     @see <a href="http://www.cnblogs.com/zengxiangzhan/p/phone.html"/>
 *     <p></p>
 *     验证手机号码是否正确
 *     移动: 134 135 136 137 138 139 147 148 150 151 152 157 158 159 172 178 182 183 184 187 188 195 198
 *     联通: 130 131 132 145 146 155 156 166 167 171 175 176 185 186 196
 *     电信: 133 149 153 173 174 177 180 181 189 190 191 193 199
 *     广电: 192
 *     虚拟运营商: 162 165 167 170 171
 * </pre>
 */
public final class ValiToPhoneUtils {

    private ValiToPhoneUtils() {
    }

    /**
     * 中国手机号码格式验证 ( 简单手机号码校验 )
     * <pre>
     *     在输入可以调用该方法, 点击发送验证码, 使用 isPhone
     *     简单手机号码校验 校验手机号码的长度和 1 开头 ( 是否 11 位 )
     * </pre>
     * @param phone 待校验的手机号码
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isPhoneSimple(final String phone) {
        return ValidatorUtils.match(CHAIN_PHONE_SIMPLE, phone);
    }

    /**
     * 是否中国手机号码
     * @param phone 待校验的手机号码
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isPhone(final String phone) {
        return ValidatorUtils.match(CHINA_PHONE_PATTERN, phone);
    }

    /**
     * 是否中国移动手机号码
     * @param phone 待校验的手机号码
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isPhoneToChinaMobile(final String phone) {
        return ValidatorUtils.match(CHINA_MOBILE_PATTERN, phone);
    }

    /**
     * 是否中国联通手机号码
     * @param phone 待校验的手机号码
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isPhoneToChinaUnicom(final String phone) {
        return ValidatorUtils.match(CHINA_UNICOM_PATTERN, phone);
    }

    /**
     * 是否中国电信手机号码
     * @param phone 待校验的手机号码
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isPhoneToChinaTelecom(final String phone) {
        return ValidatorUtils.match(CHINA_TELECOM_PATTERN, phone);
    }

    /**
     * 是否中国广电手机号码
     * @param phone 待校验的手机号码
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isPhoneToChinaBroadcast(final String phone) {
        return ValidatorUtils.match(CHINA_BROADCAST_PATTERN, phone);
    }

    /**
     * 是否中国虚拟运营商手机号码
     * @param phone 待校验的手机号码
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isPhoneToChinaVirtual(final String phone) {
        return ValidatorUtils.match(CHINA_VIRTUAL_PATTERN, phone);
    }

    // =

    /**
     * 是否中国香港手机号码
     * @param phone 待校验的手机号码
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isPhoneToChinaHkMobile(final String phone) {
        return ValidatorUtils.match(CHINA_HK_PHONE_PATTERN, phone);
    }

    /**
     * 验证电话号码的格式
     * @param phone 待校验的号码
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isPhoneCallNum(final String phone) {
        return ValidatorUtils.match(PHONE_CALL_PATTERN, phone);
    }

    // ==============
    // = 手机号码判断 =
    // ==============

    // 简单手机号码校验 校验手机号码的长度和 1 开头 ( 是否 11 位 )
    public static final String CHAIN_PHONE_SIMPLE = "^(?:\\+86)?1\\d{10}$";

    // 中国手机号码正则
    public static final String CHINA_PHONE_PATTERN;

    // 中国移动号码正则
    public static final String CHINA_MOBILE_PATTERN;

    // 中国联通号码正则
    public static final String CHINA_UNICOM_PATTERN;

    // 中国电信号码正则
    public static final String CHINA_TELECOM_PATTERN;

    // 中国广电号码正则
    public static final String CHINA_BROADCAST_PATTERN;

    // 中国虚拟运营商号码正则
    public static final String CHINA_VIRTUAL_PATTERN;

    // 中国香港手机号码正则 香港手机号码 8 位数, 5|6|8|9 开头 + 7 位任意数
    public static final String CHINA_HK_PHONE_PATTERN = "^(5|6|8|9)\\d{7}$";

    // ==========
    // = 座机判断 =
    // ==========

    // 座机电话格式验证
    public static final String PHONE_CALL_PATTERN = "^(?:\\(\\d{3,4}\\)|\\d{3,4}-)?\\d{7,8}(?:-\\d{1,4})?$";

    static {

        StringBuilder builder;

        // ==========
        // = 中国移动 =
        // ==========

        // 中国移动: 134 135 136 137 138 139 147 148 150 151 152 157 158 159 172 178 182 183 184 187 188 195 198
        builder = new StringBuilder();
        builder.append("^13[4,5,6,7,8,9]{1}\\d{8}$"); // 13 开头
        builder.append("|");
        builder.append("^14[7,8]{1}\\d{8}$"); // 14 开头
        builder.append("|");
        builder.append("^15[0,1,2,7,8,9]{1}\\d{8}$"); // 15 开头
        builder.append("|");
        builder.append("^17[2,8]{1}\\d{8}$"); // 17 开头
        builder.append("|");
        builder.append("^18[2,3,4,7,8]{1}\\d{8}$"); // 18 开头
        builder.append("|");
        builder.append("^19[5,8]{1}\\d{8}$"); // 19 开头
        CHINA_MOBILE_PATTERN = builder.toString();

        // ==========
        // = 中国联通 =
        // ==========

        // 中国联通: 130 131 132 145 146 155 156 166 167 171 175 176 185 186 196
        builder = new StringBuilder();
        builder.append("^13[0,1,2]{1}\\d{8}$"); // 13 开头
        builder.append("|");
        builder.append("^14[5,6]{1}\\d{8}$"); // 14 开头
        builder.append("|");
        builder.append("^15[5,6]{1}\\d{8}$"); // 15 开头
        builder.append("|");
        builder.append("^16[6,7]{1}\\d{8}$"); // 16 开头
        builder.append("|");
        builder.append("^17[1,5,6]{1}\\d{8}$"); // 17 开头
        builder.append("|");
        builder.append("^18[5,6]{1}\\d{8}$"); // 18 开头
        builder.append("|");
        builder.append("^19[6]{1}\\d{8}$"); // 19 开头
        CHINA_UNICOM_PATTERN = builder.toString();

        // ==========
        // = 中国电信 =
        // ==========

        // 中国电信: 133 149 153 173 174 177 180 181 189 190 191 193 199
        builder = new StringBuilder();
        builder.append("^13[3]{1}\\d{8}$"); // 13 开头
        builder.append("|");
        builder.append("^14[9]{1}\\d{8}$"); // 14 开头
        builder.append("|");
        builder.append("^15[3]{1}\\d{8}$"); // 15 开头
        builder.append("|");
        builder.append("^17[3,4,7]{1}\\d{8}$"); // 17 开头
        builder.append("|");
        builder.append("^18[0,1,9]{1}\\d{8}$"); // 18 开头
        builder.append("|");
        builder.append("^19[0,1,3,9]{1}\\d{8}$"); // 19 开头
        CHINA_TELECOM_PATTERN = builder.toString();

        // ==========
        // = 中国广电 =
        // ==========

        // 中国广电: 192
        builder = new StringBuilder();
        builder.append("^19[2]{1}\\d{8}$"); // 19 开头
        CHINA_BROADCAST_PATTERN = builder.toString();

        // ============
        // = 虚拟运营商 =
        // ============

        // 虚拟运营商: 162 165 167 170 171
        builder = new StringBuilder();
        builder.append("^16[2,5,7]{1}\\d{8}$"); // 16 开头
        builder.append("|");
        builder.append("^17[0,1]{1}\\d{8}$"); // 17 开头
        CHINA_VIRTUAL_PATTERN = builder.toString();

        // ==========
        // = 中国手机 =
        // ==========

        // 中国手机: 130 131 132 133 134 135 136 137 138 139 145 146 147 148 149 150 151 152 153 155 156 157 158 159 162 165 166 167 167 170 171 171 172 173 174 175 176 177 178 180 181 182 183 184 185 186 187 188 189 190 191 192 193 195 196 198 199
        builder = new StringBuilder();
        builder.append("^13[0,1,2,3,4,5,6,7,8,9]{1}\\d{8}$"); // 13 开头
        builder.append("|");
        builder.append("^14[5,6,7,8,9]{1}\\d{8}$"); // 14 开头
        builder.append("|");
        builder.append("^15[0,1,2,3,5,6,7,8,9]{1}\\d{8}$"); // 15 开头
        builder.append("|");
        builder.append("^16[2,5,6,7,7]{1}\\d{8}$"); // 16 开头
        builder.append("|");
        builder.append("^17[0,1,1,2,3,4,5,6,7,8]{1}\\d{8}$"); // 17 开头
        builder.append("|");
        builder.append("^18[0,1,2,3,4,5,6,7,8,9]{1}\\d{8}$"); // 18 开头
        builder.append("|");
        builder.append("^19[0,1,2,3,5,6,8,9]{1}\\d{8}$"); // 19 开头
        CHINA_PHONE_PATTERN = builder.toString();
    }
}