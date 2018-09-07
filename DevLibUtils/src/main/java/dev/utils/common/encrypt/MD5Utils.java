package dev.utils.common.encrypt;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

import dev.utils.JCLogUtils;

/**
 * detail: MD5加密 不可逆（Message Digest，消息摘要算法）
 * Created by Ttt
 */
public final class MD5Utils {

	private MD5Utils() {
	}

	// 日志TAG
	private static final String TAG = MD5Utils.class.getSimpleName();
    
	/** 小写 */
	public static final char HEX_DIGITS[] = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
	/** 大写 */
	public static final char HEX_DIGITS_UPPER[] = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};

	/**
	 * 加密内容 - 32位大小MD5 - 小写
	 * @param str 加密内容
	 * @return
	 */
	public final static String md5(String str) {
        try {
            return md5(str.getBytes());
        } catch (Exception e) {
			JCLogUtils.eTag(TAG, e, "MD5");
        }
        return null;
    }

	/**
	 * 加密内容 - 32位大小MD5 - 小写
	 * @param bytes
	 * @return
	 */
	public final static String md5(byte[] bytes) {
		try {
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(bytes);
			// 获得密文
			byte[] md = mdInst.digest();
			return toHexString(md, HEX_DIGITS);
		} catch (Exception e) {
			JCLogUtils.eTag(TAG, e, "MD5");
		}
		return null;
	}

	/**
	 * 加密内容 - 32位大小MD5 - 大写
	 * @param str 加密内容
	 * @return
	 */
    public final static String md5Upper(String str) {
		try {
			return md5Upper(str.getBytes());
		} catch (Exception e) {
			JCLogUtils.eTag(TAG, e, "MD5Upper");
		}
		return null;
	}

	/**
	 * 加密内容 - 32位大小MD5 - 大写
	 * @param bytes
	 * @return
	 */
	public final static String md5Upper(byte[] bytes) {
		try {
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(bytes);
			// 获得密文
			byte[] md = mdInst.digest();
			return toHexString(md, HEX_DIGITS_UPPER);
		} catch (Exception e) {
			JCLogUtils.eTag(TAG, e, "MD5Upper");
		}
		return null;
	}

	/**
	 * 进行转换
	 * @param bData
	 * @return
	 */
	public static String toHexString(byte[] bData) {
		return toHexString(bData, HEX_DIGITS);
	}

	/**
	 * 进行转换
	 * @param bData
	 * @param hexDigits
	 * @return
	 */
	public static String toHexString(byte[] bData, char[] hexDigits) {
		if (bData == null || hexDigits == null){
			return null;
		}
		StringBuilder sBuilder = new StringBuilder(bData.length * 2);
		for (int i = 0, len = bData.length; i < len; i++) {
			sBuilder.append(hexDigits[(bData[i] & 0xf0) >>> 4]);
			sBuilder.append(hexDigits[bData[i] & 0x0f]);
		}
		return sBuilder.toString();
	}

	/**
	 * 获取文件MD5值 - 小写
	 * @param fPath 文件地址
	 * @return
	 */
	public static String getFileMD5(String fPath) {
		try {
			InputStream fis = new FileInputStream(fPath);
			byte[] buffer = new byte[1024];
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			int numRead = 0;
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

//	/**
//	 * MD5加密(小写)
//	 * @param str 源字符串
//	 * @return 加密后的字符串
//	 */
//	public static String md5(String str) {
//		try {
//			byte[] hash = MessageDigest.getInstance("MD5").digest(str.getBytes("UTF-8"));
//			StringBuilder hex = new StringBuilder(hash.length * 2);
//			for (byte b : hash) {
//				if ((b & 0xFF) < 0x10)
//					hex.append("0");
//				hex.append(Integer.toHexString(b & 0xFF));
//			}
//			return hex.toString();
//		} catch (Exception e) {
//			JCLogUtils.eTag(TAG, e, "md5");
//		}
//		return null;
//	}
}
