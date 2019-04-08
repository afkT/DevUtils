package dev.utils.common.encrypt;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

import dev.utils.JCLogUtils;

/**
 * detail: MD5加密 不可逆(Message Digest，消息摘要算法)
 * Created by Ttt
 */
public final class MD5Utils {

    private MD5Utils() {
    }

    // 日志 TAG
    private static final String TAG = MD5Utils.class.getSimpleName();
    // 小写
    public static final char HEX_DIGITS[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    // 大写
    public static final char HEX_DIGITS_UPPER[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * 加密内容 - 32 位 MD5 - 小写
     * @param str
     * @return
     */
    public static String md5(final String str) {
        if (str == null) return null;
        try {
            return md5(str.getBytes());
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "md5");
        }
        return null;
    }

    /**
     * 加密内容 - 32 位 MD5 - 小写
     * @param data
     * @return
     */
    public static String md5(final byte[] data) {
        if (data == null) return null;
        try {
            // 获取MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(data);
            // 获取密文
            byte[] md = mdInst.digest();
            return toHexString(md, HEX_DIGITS);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "md5");
        }
        return null;
    }

    /**
     * 加密内容 - 32 位 MD5 - 大写
     * @param str
     * @return
     */
    public static String md5Upper(final String str) {
        if (str == null) return null;
        try {
            return md5Upper(str.getBytes());
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "md5Upper");
        }
        return null;
    }

    /**
     * 加密内容 - 32 位 MD5 - 大写
     * @param data
     * @return
     */
    public static String md5Upper(final byte[] data) {
        if (data == null) return null;
        try {
            // 获取MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(data);
            // 获取密文
            byte[] md = mdInst.digest();
            return toHexString(md, HEX_DIGITS_UPPER);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "md5Upper");
        }
        return null;
    }

    /**
     * 进行十六进制转换
     * @param data
     * @return
     */
    public static String toHexString(final byte[] data) {
        return toHexString(data, HEX_DIGITS);
    }

    /**
     * 进行十六进制转换
     * @param data
     * @param hexDigits
     * @return
     */
    public static String toHexString(final byte[] data, final char[] hexDigits) {
        if (data == null || hexDigits == null) return null;
        try {
            StringBuilder builder = new StringBuilder(data.length * 2);
            for (int i = 0, len = data.length; i < len; i++) {
                builder.append(hexDigits[(data[i] & 0xf0) >>> 4]);
                builder.append(hexDigits[data[i] & 0x0f]);
            }
            return builder.toString();
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toHexString");
        }
        return null;
    }

    /**
     * 获取文件 MD5 值 - 小写
     * @param path 文件地址
     * @return
     */
    public static String getFileMD5(final String path) {
        if (path == null) return null;
        try {
            InputStream fis = new FileInputStream(path);
            byte[] buffer = new byte[1024];
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            int numRead;
            while ((numRead = fis.read(buffer)) > 0) {
                md5.update(buffer, 0, numRead);
            }
            fis.close();
            return toHexString(md5.digest(), HEX_DIGITS);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getFileMD5");
        }
        return null;
    }

    // =

//	/**
//	 * 加密内容 - 32 位 MD5 - 小写
//	 * @param str 源字符串
//	 * @return
//	 */
//	public static String md5_2(final String str) {
//        if (str == null) return null;
//		try {
//			byte[] hash = MessageDigest.getInstance("MD5").digest(str.getBytes("UTF-8"));
//			StringBuilder builder = new StringBuilder(hash.length * 2);
//			for (byte b : hash) {
//				if ((b & 0xFF) < 0x10)
//					builder.append("0");
//				builder.append(Integer.toHexString(b & 0xFF));
//			}
//			return builder.toString();
//		} catch (Exception e) {
//			JCLogUtils.eTag(TAG, e, "md5");
//		}
//		return null;
//	}
//
//	/**
//	 * 加密内容 - 32 位 MD5 - 大写
//	 * @param str 源字符串
//	 * @return
//	 */
//	public static String md5Upper_2(final String str) {
//        String data = md5_2(str);
//        if (data != null) return data.toUpperCase();
//		return null;
//	}
}
