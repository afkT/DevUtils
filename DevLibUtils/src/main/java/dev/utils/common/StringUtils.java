package dev.utils.common;

import java.net.URLEncoder;
import java.util.Locale;

import dev.utils.JCLogUtils;

/**
 * detail: 字符串工具类
 * Created by Ttt
 */
public final class StringUtils {

	private StringUtils() {
	}

	// 日志 TAG
	private static final String TAG = StringUtils.class.getSimpleName();
	/** 换行字符串 */
	public static final String NEW_LINE_STR = System.getProperty("line.separator");
	/** 换行字符串 - 两行 */
	public static final String NEW_LINE_STR_X2 = NEW_LINE_STR + NEW_LINE_STR;

	/**
	 * 获取长度，如果字符串为null,则返回0
	 * @param str
	 * @return
	 */
	public static int length(String str) {
		return str == null ? 0 : str.length();
	}

	/**
	 * 判断是否为null
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return (str == null || str.length() == 0);
	}

	/**
	 * 判断字符串是否为 null 或全为空白字符
	 * @param str 待校验字符串
	 * @return
	 */
	public static boolean isSpace(final String str) {
		if (str == null) return true;
		for (int i = 0, len = str.length(); i < len; ++i) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 清空全部空格,并返回处理后的字符串
	 * @param str
	 * @return
	 */
	public static String toClearSpace(String str) {
		if (isEmpty(str)) {
			return str;
		}
		return str.replaceAll(" ", "");
	}

	/**
	 * 清空前后空格,并返回处理后的字符串
	 * @param str
	 * @return
	 */
	public static String toClearSpaceTrim(String str) {
		if (isEmpty(str)) return str;
		// 如果前面或者后面都是 空格开头,就一直进行处理
		while (str.startsWith(" ") || str.endsWith(" ")) {
			str = str.trim();
		}
		return str;
	}

	// ==

	/**
	 * 字符串进行 GBK 编码
	 * @param str
	 * @return
	 */
	public static String toGBKEncode(String str) {
		return toStrEncode(str, "GBK");
	}

	/**
	 * 字符串进行 GBK2312 编码
	 * @param str
	 * @return
	 */
	public static String toGBK2312Encode(String str) {
		return toStrEncode(str, "GBK-2312");
	}

	/**
	 * 字符串进行 UTF-8 编码
	 * @param str
	 * @return
	 */
	public static String toUTF8Encode(String str) {
		return toStrEncode(str, "UTF-8");
	}

	/**
	 * 进行字符串编码
	 * @param str
	 * @param enc
	 * @return
	 */
	public static String toStrEncode(String str, String enc) {
		try {
			return new String(str.getBytes(), enc);
		} catch (Exception e) {
			JCLogUtils.eTag(TAG, e, "toStrEncode");
		}
		return str;
	}

	// ==

	/**
	 * 进行 URL 编码,默认UTF-8
	 * @param str
	 * @return
	 */
	public static String toUrlEncode(String str) {
		return toUrlEncode(str, "UTF-8");
	}

	/**
	 * 进行 URL 编码
	 * @param str
	 * @param enc
	 * @return
	 */
	public static String toUrlEncode(String str, String enc) {
		try {
			return URLEncoder.encode(str, enc);
		} catch (Exception e) {
			JCLogUtils.eTag(TAG, e, "toUrlEncode");
			// 如果URL编码失败,则直接进行字符串编码
			return toStrEncode(str, enc);
		}
	}

	// =

	/**
	 * 将字符串转移为ASCII码
	 * @param str 字符串
	 * @return 字符串ASCII码
	 */
	public static String toASCII(String str) {
		if (isEmpty(str)) return str;
		StringBuffer strBuf = new StringBuffer();
		byte[] bytes = str.getBytes();
		for (int i = 0, len = bytes.length; i < len; i++) {
			strBuf.append(Integer.toHexString(bytes[i] & 0xff));
		}
		return strBuf.toString();
	}

	/**
	 * 将字符串转移为Unicode码
	 * @param str 字符串
	 * @return
	 */
	public static String toUnicode(String str) {
		if (isEmpty(str)) return str;
		StringBuffer strBuf = new StringBuffer();
		char[] chars = str.toCharArray();
		for (int i = 0, len = chars.length; i < len; i++) {
			strBuf.append("\\u").append(Integer.toHexString(chars[i]));
		}
		return strBuf.toString();
	}

	/**
	 * 将字符串转移为Unicode码
	 * @param chars 字符数组
	 * @return
	 */
	public static String toUnicodeString(char[] chars) {
		if (chars == null) return null;
		StringBuffer strBuf = new StringBuffer();
		for (int i = 0, len = chars.length; i < len; i++) {
			strBuf.append("\\u").append(Integer.toHexString(chars[i]));
		}
		return strBuf.toString();
	}

	/**
	 * 转化为半角字符
	 * @param str 待转字符串
	 * @return 半角字符串
	 */
	public static String toDBC(final String str) {
		if (isEmpty(str)) return str;
		char[] chars = str.toCharArray();
		for (int i = 0, len = chars.length; i < len; i++) {
			if (chars[i] == 12288) {
				chars[i] = ' ';
			} else if (65281 <= chars[i] && chars[i] <= 65374) {
				chars[i] = (char) (chars[i] - 65248);
			} else {
				chars[i] = chars[i];
			}
		}
		return new String(chars);
	}

	/**
	 * 转化为全角字符 如： a => ａ A => Ａ
	 * @param str 待转字符串
	 * @return 全角字符串
	 */
	public static String toSBC(final String str) {
		if (isEmpty(str)) return str;
		char[] chars = str.toCharArray();
		for (int i = 0, len = chars.length; i < len; i++) {
			if (chars[i] == ' ') {
				chars[i] = (char) 12288;
			} else if (33 <= chars[i] && chars[i] <= 126) {
				chars[i] = (char) (chars[i] + 65248);
			} else {
				chars[i] = chars[i];
			}
		}
		return new String(chars);
	}

	// ==

	/**
	 * byte[]数组转换为16进制的字符串
	 * @param data 要转换的字节数组
	 * @return 转换后的结果
	 */
	public static final String byteArrayToHexString(byte[] data) {
		if (data == null) return null;
		StringBuilder sBuilder = new StringBuilder(data.length * 2);
		for (byte b : data) {
			int v = b & 0xff;
			if (v < 16) {
				sBuilder.append('0');
			}
			sBuilder.append(Integer.toHexString(v));
		}
		return sBuilder.toString().toUpperCase(Locale.getDefault());
	}

	/**
	 * 进行转换
	 * @param bData
	 * @param hexDigits
	 * @return
	 */
	public static String toHexString(byte[] bData, char[] hexDigits) {
		if (bData == null || hexDigits == null) return null;
		StringBuilder sBuilder = new StringBuilder(bData.length * 2);
		for (int i = 0; i < bData.length; i++) {
			sBuilder.append(hexDigits[(bData[i] & 0xf0) >>> 4]);
			sBuilder.append(hexDigits[bData[i] & 0x0f]);
		}
		return sBuilder.toString();
	}

	/**
	 * 16进制表示的字符串转换为字节数组
	 * @param str 16进制表示的字符串
	 * @return byte[] 字节数组
	 */
	public static byte[] hexStringToByteArray(String str) {
		if (isEmpty(str)) return null;
		int len = str.length();
		byte[] d = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			// 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个进制字节
			d[i / 2] = (byte) ((Character.digit(str.charAt(i), 16) << 4) + Character.digit(str.charAt(i + 1), 16));
		}
		return d;
	}

	/**
	 * 检测String是否全是中文
	 * @param str
	 * @return
	 */
	public static boolean checkCheseToString(String str) {
		if (isEmpty(str)) return false;
		boolean result = true;
		char[] chars = str.toCharArray();
		for (int i = 0, len = chars.length; i < len; i++) {
			if (!isChinese(chars[i])) {
				result = false;
				break;
			}
		}
		return result;
	}

	/**
	 * 判定输入汉字
	 * @param c
	 * @return
	 */
	public static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}

	// == 字符串处理方法 ==

	/**
	 * 首字母大写
	 * @param str 待转字符串
	 * @return 首字母大写字符串
	 */
	public static String upperFirstLetter(final String str) {
		if (isEmpty(str) || !Character.isLowerCase(str.charAt(0))) return str;
		try {
			return String.valueOf((char) (str.charAt(0) - 32)) + str.substring(1);
		} catch (Exception e) {
			JCLogUtils.eTag(TAG, e, "upperFirstLetter");
			return str;
		}
	}

	/**
	 * 首字母小写
	 * @param str 待转字符串
	 * @return 首字母小写字符串
	 */
	public static String lowerFirstLetter(final String str) {
		if (isEmpty(str) || !Character.isUpperCase(str.charAt(0))) return str;
		try {
			return String.valueOf((char) (str.charAt(0) + 32)) + str.substring(1);
		} catch (Exception e) {
			JCLogUtils.eTag(TAG, e, "lowerFirstLetter");
			return str;
		}
	}

	/**
	 * 反转字符串
	 * @param str 待反转字符串
	 * @return 反转字符串
	 */
	public static String reverse(final String str) {
		int len = length(str);
		if (len <= 1) return str;
		int mid = len >> 1;
		char[] chars = str.toCharArray();
		char c;
		for (int i = 0; i < mid; ++i) {
			c = chars[i];
			chars[i] = chars[len - i - 1];
			chars[len - i - 1] = c;
		}
		return new String(chars);
	}

	/**
	 * 字符串连接，将参数列表拼接为一个字符串
	 * @param more 追加
	 * @return 返回拼接后的字符串
	 */
	public static String concat(Object... more) {
		return concatSpiltWith("", more);
	}

	/**
	 * 字符串连接，将参数列表拼接为一个字符串
	 * @param split
	 * @param more
	 * @return 回拼接后的字符串
	 */
	public static String concatSpiltWith(String split, Object... more) {
		if (more == null) return null;
		StringBuilder buf = new StringBuilder();
		for (int i = 0, len = more.length; i < len; i++) {
			if (i != 0) buf.append(split);
			buf.append(more[i]);
		}
		return buf.toString();
	}

	/**
	 * 下划线命名转为驼峰命名
	 * @param str 下划线命名格式
	 * @return 驼峰命名格式
	 */
	public static final String underScoreCase2CamelCase(String str) {
		if (isEmpty(str)) return str;
		if (!str.contains("_")) return str;
		StringBuilder sb = new StringBuilder();
		char[] chars = str.toCharArray();
		boolean hitUnderScore = false;
		sb.append(chars[0]);
		for (int i = 1, len = chars.length; i < len; i++) {
			char c = chars[i];
			if (c == '_') {
				hitUnderScore = true;
			} else {
				if (hitUnderScore) {
					sb.append(Character.toUpperCase(c));
					hitUnderScore = false;
				} else {
					sb.append(c);
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 驼峰命名法转为下划线命名
	 * @param str 驼峰命名格式
	 * @return 下划线命名格式
	 */
	public static final String camelCase2UnderScoreCase(String str) {
		if (isEmpty(str)) return str;
		StringBuilder sb = new StringBuilder();
		char[] chars = str.toCharArray();
		for (int i = 0, len = chars.length; i < len; i++) {
			char c = chars[i];
			if (Character.isUpperCase(c)) {
				sb.append("_").append(Character.toLowerCase(c));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * 数据库字符转义
	 * @param keyWord
	 * @return
	 */
	public static String sqliteEscape(String keyWord) {
		if (isEmpty(keyWord)) return keyWord;
		// 替换关键字
		keyWord = keyWord.replace("/", "//");
		keyWord = keyWord.replace("'", "''");
		keyWord = keyWord.replace("[", "/[");
		keyWord = keyWord.replace("]", "/]");
		keyWord = keyWord.replace("%", "/%");
		keyWord = keyWord.replace("&","/&");
		keyWord = keyWord.replace("_", "/_");
		keyWord = keyWord.replace("(", "/(");
		keyWord = keyWord.replace(")", "/)");
		return keyWord;
	}
}
