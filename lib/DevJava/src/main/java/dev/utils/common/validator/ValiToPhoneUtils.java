package dev.utils.common.validator;

/**
 * detail: 检验联系 ( 手机号、座机 ) 工具类
 * @author Ttt
 * <pre>
 *     @see <a href="http://www.cnblogs.com/zengxiangzhan/p/phone.html"/>
 *     <p></p>
 *     验证手机号是否正确
 *     移动: 134 135 136 137 138 139 147 148 150 151 152 157 158 159 165 172 178 182 183 184 187 188 198
 *     联通: 130 131 132 145 146 155 156 166 171 175 176 185 186
 *     电信: 133 149 153 173 174 177 180 181 189 191 199
 *     卫星通信: 1349
 *     虚拟运营商: 170
 * </pre>
 */
public final class ValiToPhoneUtils {

    private ValiToPhoneUtils() {
    }

    /**
     * 中国手机号格式验证, 在输入可以调用该方法, 点击发送验证码, 使用 isPhone
     * @param phone 待校验的手机号
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isPhoneCheck(final String phone) {
        return ValidatorUtils.match(CHAIN_PHONE_FORMAT_CHECK, phone);
    }

    /**
     * 是否中国手机号
     * @param phone 待校验的手机号
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isPhone(final String phone) {
        return ValidatorUtils.match(CHINA_PHONE_PATTERN, phone);
    }

    /**
     * 是否中国电信手机号码
     * @param phone 待校验的手机号
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isPhoneToChinaTelecom(final String phone) {
        return ValidatorUtils.match(CHINA_TELECOM_PATTERN, phone);
    }

    /**
     * 是否中国联通手机号码
     * @param phone 待校验的手机号
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isPhoneToChinaUnicom(final String phone) {
        return ValidatorUtils.match(CHINA_UNICOM_PATTERN, phone);
    }

    /**
     * 是否中国移动手机号码
     * @param phone 待校验的手机号
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isPhoneToChinaMobile(final String phone) {
        return ValidatorUtils.match(CHINA_MOBILE_PATTERN, phone);
    }

    /**
     * 判断是否香港手机号
     * @param phone 待校验的手机号
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isPhoneToHkMobile(final String phone) {
        return ValidatorUtils.match(HK_PHONE_PATTERN, phone);
    }

    /**
     * 验证电话号码的格式
     * @param phone 待校验的号码
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isPhoneCallNum(final String phone) {
        return ValidatorUtils.match(PHONE_CALL_PATTERN, phone);
    }

    // =============
    // = 手机号判断 =
    // =============

    // 简单手机号码校验 校验手机号码的长度和 1 开头 ( 是否 11 位 )
    public static final String CHAIN_PHONE_FORMAT_CHECK = "^(?:\\+86)?1\\d{10}$";

    // 中国手机号正则
    public static final String CHINA_PHONE_PATTERN;

    // 中国电信号码正则
    public static final String CHINA_TELECOM_PATTERN;

    // 中国联通号码正则
    public static final String CHINA_UNICOM_PATTERN;

    // 中国移动号码正则
    public static final String CHINA_MOBILE_PATTERN;

    // 香港手机号码正则 香港手机号码 8 位数, 5|6|8|9 开头 + 7 位任意数
    public static final String HK_PHONE_PATTERN = "^(5|6|8|9)\\d{7}$";

    // ===========
    // = 座机判断 =
    // ===========

    // 座机电话格式验证
    public static final String PHONE_CALL_PATTERN = "^(?:\\(\\d{3,4}\\)|\\d{3,4}-)?\\d{7,8}(?:-\\d{1,4})?$";

    static {

        // ===========
        // = 中国电信 =
        // ===========

        // 电信: 133 149 153 173 174 177 180 181 189 191 199
        // 进行拼接字符串, 便于理解
        StringBuilder builder = new StringBuilder();
        builder.append("^13[3]{1}\\d{8}$"); // 13 开头
        builder.append("|"); // 或
        builder.append("^14[9]{1}\\d{8}$"); // 14 开头
        builder.append("|");
        builder.append("^15[3]{1}\\d{8}$"); // 15 开头
        builder.append("|");
        builder.append("^17[3,4,7]{1}\\d{8}$"); // 17 开头
        builder.append("|");
        builder.append("^18[0,1,9]{1}\\d{8}$"); // 18 开头
        builder.append("|");
        builder.append("^19[1,9]{1}\\d{8}$"); // 19 开头
        // 手机正则
        CHINA_TELECOM_PATTERN = builder.toString();

        // ===========
        // = 中国联通 =
        // ===========

        // 联通: 130 131 132 145 146 155 156 166 171 175 176 185 186
        // 进行拼接字符串, 便于理解
        builder = new StringBuilder();
        builder.append("^13[0,1,2]{1}\\d{8}$"); // 13 开头
        builder.append("|"); // 或
        builder.append("^14[5,6]{1}\\d{8}$"); // 14 开头
        builder.append("|");
        builder.append("^15[5,6]{1}\\d{8}$"); // 15 开头
        builder.append("|");
        builder.append("^16[6]{1}\\d{8}$"); // 16 开头
        builder.append("|");
        builder.append("^17[1,5,6]{1}\\d{8}$"); // 17 开头
        builder.append("|");
        builder.append("^18[5,6]{1}\\d{8}$"); // 18 开头
        // 手机正则
        CHINA_UNICOM_PATTERN = builder.toString();

        // ===========
        // = 中国移动 =
        // ===========

        // 移动: 134 135 136 137 138 139 147 148 150 151 152 157 158 159 165 172 178 182 183 184 187 188 198
        // 进行拼接字符串, 便于理解
        builder = new StringBuilder();
        builder.append("^13[4,5,6,7,8,9]{1}\\d{8}$"); // 13 开头
        builder.append("|"); // 或
        builder.append("^14[7,8]{1}\\d{8}$"); // 14 开头
        builder.append("|");
        builder.append("^15[0,1,2,7,8,9]{1}\\d{8}$"); // 15 开头
        builder.append("|");
        builder.append("^16[5]{1}\\d{8}$"); // 16 开头
        builder.append("|");
        builder.append("^17[2,8]{1}\\d{8}$"); // 17 开头
        builder.append("|");
        builder.append("^18[2,3,4,7,8]{1}\\d{8}$"); // 18 开头
        builder.append("|");
        builder.append("^19[8]{1}\\d{8}$"); // 19 开头
        // 手机正则
        CHINA_MOBILE_PATTERN = builder.toString();

        /**
         * 验证手机号是否正确
         * 移动: 134 135 136 137 138 139 147 148 150 151 152 157 158 159 165 172 178 182 183 184 187 188 198
         * 联通: 130 131 132 145 146 155 156 166 171 175 176 185 186
         * 电信: 133 149 153 173 174 177 180 181 189 191 199
         * 卫星通信: 1349
         * 虚拟运营商: 170
         */
        CHINA_PHONE_PATTERN = "^13[\\d]{9}$|^14[5,6,7,8,9]{1}\\d{8}$|^15[^4]{1}\\d{8}$|^16[5,6]{1}\\d{8}$|^17[0,1,2,3,4,5,6,7,8]{1}\\d{8}$|^18[\\d]{9}$|^19[1,8,9]{1}\\d{8}$";
    }
}