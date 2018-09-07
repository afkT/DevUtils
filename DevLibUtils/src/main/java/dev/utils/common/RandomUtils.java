package dev.utils.common;

import java.util.Random;

/**
 * detail: 随机生成工具类
 * Created by Ttt
 */
public final class RandomUtils {

	private RandomUtils() {
	}

	// 0123456789
	public static final char[] NUMBERS = new char[] { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57 };

	// abcdefghijklmnopqrstuvwxyz
	public static final char[] LOWER_CASE_LETTERS = new char[] { 97, 98, 99,
			100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112,
			113, 114, 115, 116, 117, 118, 119, 120, 121, 122 };

	// ABCDEFGHIJKLMNOPQRSTUVWXYZ
	public static final char[] CAPITAL_LETTERS = new char[] { 65, 66, 67, 68,
			69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85,
			86, 87, 88, 89, 90 };

	// abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ
	public static final char[] LETTERS = new char[] { 97, 98, 99, 100, 101,
			102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114,
			115, 116, 117, 118, 119, 120, 121, 122, 65, 66, 67, 68, 69, 70, 71,
			72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88,
			89, 90 };

	// 0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ
	public static final char[] NUMBERS_AND_LETTERS = new char[] { 48, 49, 50,
			51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102, 103, 104,
			105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117,
			118, 119, 120, 121, 122, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74,
			75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90 };

	// =

	public static boolean nextBoolean(Random random) {
		return random.nextBoolean();
	}

	public static byte[] nextBytes(Random random, byte[] buf) {
		try {
			random.nextBytes(buf);
		} catch (Exception e) {
		}
		return buf;
	}

	public static double nextDouble(Random random) {
		return random.nextDouble();
	}

	public synchronized double nextGaussian(Random random) {
		return random.nextGaussian();
	}

	public static float nextFloat(Random random) {
		return random.nextFloat();
	}

	public static int nextInt(Random random) {
		return random.nextInt();
	}

	public static int nextInt(Random random, int n) {
		return random.nextInt(n);
	}

	public static long nextLong(Random random) {
		return random.nextLong();
	}

	// == ----------------------------------------- ==

	public static boolean nextBoolean() {
		return new Random().nextBoolean();
	}

	public static byte[] nextBytes(byte[] buf) {
		return nextBytes(new Random(), buf);
	}

	public static double nextDouble() {
		return Math.random();
	}

	public synchronized double nextGaussian() {
		return new Random().nextGaussian();
	}

	public static float nextFloat() {
		return new Random().nextFloat();
	}

	public static int nextInt() {
		return new Random().nextInt();
	}

	public static int nextInt(int n) {
		return new Random().nextInt(n);
	}

	public static long nextLong() {
		return new Random().nextLong();
	}

	// == ----------------------------------------- ==

	/**
	 * 获取数字自定义长度的随机数
	 * @param length 长度
	 * @return 随机数字符串
	 */
	public static String getRandomNumbers(int length) {
		return getRandom(NUMBERS, length);
	}
	
	/**
	 * 获取小写字母自定义长度的随机数
	 * @param length 长度
	 * @return 随机字符串
	 */
	public static String getRandomLowerCaseLetters(int length) {
		return getRandom(LOWER_CASE_LETTERS, length);
	}
	
	/**
	 * 获取大写字母自定义长度的随机数
	 * @param length 长度
	 * @return 随机字符串
	 */
	public static String getRandomCapitalLetters(int length) {
		return getRandom(CAPITAL_LETTERS, length);
	}
	
	/**
	 * 获取大小写字母自定义长度的随机数
	 * @param length 长度
	 * @return 随机字符串
	 */
	public static String getRandomLetters(int length) {
		return getRandom(LETTERS, length);
	}
	
	/**
	 * 获取数字、大小写字母自定义长度的随机数
	 * @param length 长度
	 * @return 随机字符串
	 */
	public static String getRandomNumbersAndLetters(int length) {
		return getRandom(NUMBERS_AND_LETTERS, length);
	}
	
	/**
	 * 获取自定义数据自定义长度的随机数
	 * @param length 长度
	 * @return 随机字符串
	 */
	public static String getRandom(String source, int length) {
		return getRandom(source.toCharArray(), length);
	}
	
	/**
	 * 获取char[]内的随机数
	 * @param chars 随机的数据源
	 * @param length 需要最终长度
	 * @return
	 */
	public static String getRandom(char[] chars, int length) {
		if(length > 0 && chars != null && chars.length != 0) {
			StringBuilder str = new StringBuilder(length);
			Random random = new Random();
			for (int i = 0; i < length; i++) {
				str.append(chars[random.nextInt(chars.length)]);
			}
			return str.toString();
		}
		return null;
	}
	
	/**
	 * 获取 0 - 最大随机数之间的随机数
	 * @param max 最大随机数
	 */
	public static int getRandom(int max) {
		return getRandom(0, max);
	}
	
	/**
	 * 获取两个数之间的随机数(不含最大随机数,需要 + 1)
	 * @param min 最小随机数
	 * @param max 最大随机数
	 */
	public static int getRandom(int min, int max) {
		if (min > max) {
			return 0;
		} else if (min == max) {
			return min;
		}
		return min + new Random().nextInt(max - min);
	}
	
	/**
	 * 洗牌算法，随机置换指定的数组使用的默认源的随机性
	 * @param objArray
	 * @return
	 */
	public static boolean shuffle(Object[] objArray) {
		if (objArray == null) {
			return false;
		}
		return shuffle(objArray, getRandom(1,objArray.length));
	}
	
	/**
	 * 洗牌算法，随机置换指定的数组
	 * @param objArray
	 * @param shuffleCount
	 * @return
	 */
	public static boolean shuffle(Object[] objArray, int shuffleCount) {
		int length;
		if(shuffleCount > 0 && objArray != null && (length = objArray.length) >= shuffleCount) {
			for (int i = 1; i <= shuffleCount; i++) {
				int random = getRandom(0,length - i);
				Object temp = objArray[length - i];
				objArray[length - i] = objArray[random];
				objArray[random] = temp;
			}
			return true;
		}
		return false;
	}
	
	/**
	 * 洗牌算法，随机置换指定数组的使用随机的默认源
	 * @param intArray
	 * @return
	 */
	public static int[] shuffle(int[] intArray) {
		if (intArray == null) {
			return null;
		}
		return shuffle(intArray, getRandom(1,intArray.length));
	}
	
	/**
	 * 洗牌算法，随机置换指定的数组
	 * @param intArray
	 * @param shuffleCount
	 * @return
	 */
	public static int[] shuffle(int[] intArray, int shuffleCount) {
		int length;
		if(shuffleCount > 0 && intArray != null && (length = intArray.length) >= shuffleCount) {
			int[] out = new int[shuffleCount];
			for (int i = 1; i <= shuffleCount; i++) {
				int random = getRandom(0,length - i);
				out[i - 1] = intArray[random];
				int temp = intArray[length - i];
				intArray[length - i] = intArray[random];
				intArray[random] = temp;
			}
			return out;
		}
		return null;
	}
}
