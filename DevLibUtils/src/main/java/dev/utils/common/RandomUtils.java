package dev.utils.common;

import java.util.Random;

import dev.utils.JCLogUtils;

/**
 * detail: 随机生成工具类
 * @author Ttt
 */
public final class RandomUtils {

    private RandomUtils() {
    }

    // 日志 TAG
    private static final String TAG = RandomUtils.class.getSimpleName();

    // 0123456789
    public static final char[] NUMBERS = new char[]{48, 49, 50, 51, 52, 53, 54, 55, 56, 57};

    // abcdefghijklmnopqrstuvwxyz
    public static final char[] LOWER_CASE_LETTERS = new char[]{97, 98, 99,
            100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112,
            113, 114, 115, 116, 117, 118, 119, 120, 121, 122};

    // ABCDEFGHIJKLMNOPQRSTUVWXYZ
    public static final char[] CAPITAL_LETTERS = new char[]{65, 66, 67, 68,
            69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85,
            86, 87, 88, 89, 90};

    // abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ
    public static final char[] LETTERS = new char[]{97, 98, 99, 100, 101,
            102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114,
            115, 116, 117, 118, 119, 120, 121, 122, 65, 66, 67, 68, 69, 70, 71,
            72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88,
            89, 90};

    // 0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ
    public static final char[] NUMBERS_AND_LETTERS = new char[]{48, 49, 50,
            51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102, 103, 104,
            105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117,
            118, 119, 120, 121, 122, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74,
            75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90};

    // =

    /**
     * 获取伪随机 boolean 值
     * @param random Random
     * @return 随机 boolean 值
     */
    public static boolean nextBoolean(final Random random) {
        return random != null ? random.nextBoolean() : new Random().nextBoolean();
    }

    /**
     * 获取伪随机 byte[]
     * @param random Random
     * @param data   随机数据源
     * @return 随机 byte[]
     */
    public static byte[] nextBytes(final Random random, final byte[] data) {
        if (random == null || data == null) return data;
        try {
            random.nextBytes(data);
        } catch (Exception e) {
        }
        return data;
    }

    /**
     * 获取伪随机 double 值
     * @param random Random
     * @return 随机 double 值
     */
    public static double nextDouble(final Random random) {
        return random != null ? random.nextDouble() : new Random().nextDouble();
    }

    /**
     * 获取伪随机高斯分布值
     * @param random Random
     * @return 伪随机高斯分布值
     */
    public static double nextGaussian(final Random random) {
        return random != null ? random.nextGaussian() : new Random().nextGaussian();
    }

    /**
     * 获取伪随机 float 值
     * @param random Random
     * @return 随机 float 值
     */
    public static float nextFloat(final Random random) {
        return random != null ? random.nextFloat() : new Random().nextFloat();
    }

    /**
     * 获取伪随机 int 值
     * @param random Random
     * @return 随机 int 值
     */
    public static int nextInt(final Random random) {
        return random != null ? random.nextInt() : new Random().nextInt();
    }

    /**
     * 获取伪随机 int 值 - 该值介于 [0,n) 的区间
     * @param random Random
     * @param number 最大随机值
     * @return 随机介于 [0,n) 的区间值
     */
    public static int nextInt(final Random random, final int number) {
        if (number <= 0) return 0;
        return random != null ? random.nextInt(number) : new Random().nextInt(number);
    }

    /**
     * 获取伪随机 long 值
     * @param random Random
     * @return 随机 long 值
     */
    public static long nextLong(final Random random) {
        return random != null ? random.nextLong() : new Random().nextLong();
    }

    // =

    /**
     * 获取伪随机 boolean 值
     * @return 随机 boolean 值
     */
    public static boolean nextBoolean() {
        return new Random().nextBoolean();
    }

    /**
     * 获取伪随机 byte[]
     * @param data 随机数据源
     * @return 随机 byte[]
     */
    public static byte[] nextBytes(final byte[] data) {
        if (data == null) return null;
        try {
            new Random().nextBytes(data);
        } catch (Exception e) {
        }
        return data;
    }

    /**
     * 获取伪随机 double 值
     * @return 随机 double 值
     */
    public static double nextDouble() {
        return new Random().nextDouble();
    }

    /**
     * 获取伪随机高斯分布值
     * @return 伪随机高斯分布值
     */
    public static double nextGaussian() {
        return new Random().nextGaussian();
    }

    /**
     * 获取伪随机 float 值
     * @return 随机 float 值
     */
    public static float nextFloat() {
        return new Random().nextFloat();
    }

    /**
     * 获取伪随机 int 值
     * @return 随机 int 值
     */
    public static int nextInt() {
        return new Random().nextInt();
    }

    /**
     * 获取伪随机 int 值 - 该值介于 [0,n) 的区间
     * @param number 最大随机值
     * @return 随机介于 [0,n) 的区间值
     */
    public static int nextInt(final int number) {
        if (number <= 0) return 0;
        return new Random().nextInt(number);
    }

    /**
     * 获取伪随机 long 值
     * @return 随机 long 值
     */
    public static long nextLong() {
        return new Random().nextLong();
    }

    // =

    /**
     * 获取数字自定义长度的随机数
     * @param length 长度
     * @return 随机字符串
     */
    public static String getRandomNumbers(final int length) {
        return getRandom(NUMBERS, length);
    }

    /**
     * 获取小写字母自定义长度的随机数
     * @param length 长度
     * @return 随机字符串
     */
    public static String getRandomLowerCaseLetters(final int length) {
        return getRandom(LOWER_CASE_LETTERS, length);
    }

    /**
     * 获取大写字母自定义长度的随机数
     * @param length 长度
     * @return 随机字符串
     */
    public static String getRandomCapitalLetters(final int length) {
        return getRandom(CAPITAL_LETTERS, length);
    }

    /**
     * 获取大小写字母自定义长度的随机数
     * @param length 长度
     * @return 随机字符串
     */
    public static String getRandomLetters(final int length) {
        return getRandom(LETTERS, length);
    }

    /**
     * 获取数字、大小写字母自定义长度的随机数
     * @param length 长度
     * @return 随机字符串
     */
    public static String getRandomNumbersAndLetters(final int length) {
        return getRandom(NUMBERS_AND_LETTERS, length);
    }

    /**
     * 获取自定义数据自定义长度的随机数
     * @param length 长度
     * @return 随机字符串
     */
    public static String getRandom(final String source, final int length) {
        if (source == null) return null;
        return getRandom(source.toCharArray(), length);
    }

    /**
     * 获取 char[] 内的随机数
     * @param chars  随机的数据源
     * @param length 需要最终长度
     * @return 随机字符串
     */
    public static String getRandom(final char[] chars, final int length) {
        if (length > 0 && chars != null && chars.length != 0) {
            StringBuilder builder = new StringBuilder(length);
            Random random = new Random();
            for (int i = 0; i < length; i++) {
                builder.append(chars[random.nextInt(chars.length)]);
            }
            return builder.toString();
        }
        return null;
    }

    /**
     * 获取 0 - 最大随机数之间的随机数
     * @param max 最大随机数
     * @return 随机介于 [0,max) 的区间值
     */
    public static int getRandom(final int max) {
        return getRandom(0, max);
    }

    /**
     * 获取两个数之间的随机数(不含最大随机数,需要 + 1)
     * @param min 最小随机数
     * @param max 最大随机数
     * @return 随机介于 [min,max) 的区间值
     */
    public static int getRandom(final int min, final int max) {
        if (min > max) {
            return 0;
        } else if (min == max) {
            return min;
        }
        return min + new Random().nextInt(max - min);
    }

    // =

    // 内置洗牌算法
    // java.util.Collections.shuffle(List<?> list);

    /**
     * 洗牌算法(第一种)，随机置换指定的数组使用的默认源的随机性(随机数据源小于三个, 则无效)
     * @param objects 随机数据源
     * @return {@code true} success, {@code false} fail
     */
    public static boolean shuffle(final Object[] objects) {
        if (objects == null) return false;
        return shuffle(objects, getRandom(1, objects.length));
    }

    /**
     * 洗牌算法(第一种)，随机置换指定的数组使用的默认源的随机性(随机数据源小于三个, 则无效)
     * @param objects      随机数据源
     * @param shuffleCount 洗牌次数
     * @return {@code true} success, {@code false} fail
     */
    public static boolean shuffle(final Object[] objects, final int shuffleCount) {
        int length;
        if (shuffleCount > 0 && objects != null && (length = objects.length) >= shuffleCount) {
            for (int i = 1; i <= shuffleCount; i++) {
                int random = getRandom(0, length - i);
                Object temp = objects[length - i];
                objects[length - i] = objects[random];
                objects[random] = temp;
            }
            return true;
        }
        return false;
    }

    /**
     * 洗牌算法(第一种)，随机置换指定的数组使用的默认源的随机性(随机数据源小于三个, 则无效)
     * @param ints 随机数据源
     * @return 随机 int[]
     */
    public static int[] shuffle(final int[] ints) {
        if (ints == null) return null;
        return shuffle(ints, getRandom(1, ints.length));
    }

    /**
     * 洗牌算法(第一种)，随机置换指定的数组使用的默认源的随机性(随机数据源小于三个, 则无效)
     * @param ints         随机数据源
     * @param shuffleCount 洗牌次数
     * @return 随机 int[]
     */
    public static int[] shuffle(final int[] ints, final int shuffleCount) {
        int length;
        if (shuffleCount > 0 && ints != null && (length = ints.length) >= shuffleCount) {
            int[] out = new int[shuffleCount];
            for (int i = 1; i <= shuffleCount; i++) {
                int random = getRandom(0, length - i);
                out[i - 1] = ints[random];
                int temp = ints[length - i];
                ints[length - i] = ints[random];
                ints[random] = temp;
            }
            return out;
        }
        return null;
    }

    // =

    /**
     * 洗牌算法(第二种)，随机置换指定的数组使用的默认源的随机性
     * @param objects 随机数据源
     * @return {@code true} success, {@code false} fail
     */
    public static boolean shuffle2(final Object[] objects) {
        if (objects == null) return false;
        int len = objects.length;
        if (len > 0) {
            for (int i = 0; i < len - 1; i++) {
                int idx = (int) (Math.random() * (len - i));
                Object temp = objects[idx];
                objects[idx] = objects[len - i - 1];
                objects[len - i - 1] = temp;
            }
            return true;
        }
        return false;
    }

    // =

    /**
     * 获取指定范围 Int 值
     * @param origin 开始值
     * @param bound  范围值
     * @return 属于指定范围随机 int 值
     * @throws IllegalArgumentException 参数错误
     */
    public static int nextIntRange(final int origin, final int bound) throws IllegalArgumentException {
        if (origin > bound) {
            throw new IllegalArgumentException("bound must be greater than origin");
        } else if (origin == bound) {
            return origin;
        }
        Random random = new Random();
        int n = bound - origin;
        if (n > 0) {
            return random.nextInt(n) + origin;
        } else {
            int r;
            do {
                r = random.nextInt();
            } while (r < origin || r >= bound);
            return r;
        }
    }

    /**
     * 获取指定范围 long 值
     * @param origin 开始值
     * @param bound  范围值
     * @return 属于指定范围随机 long 值
     * @throws IllegalArgumentException 参数错误
     */
    public static long nextLongRange(final long origin, final long bound) throws IllegalArgumentException {
        if (origin > bound) {
            throw new IllegalArgumentException("bound must be greater than origin");
        } else if (origin == bound) {
            return origin;
        }
        Random random = new Random();
        long r = random.nextLong();
        long n = bound - origin, m = n - 1;
        if ((n & m) == 0L)  // power of two
            r = (r & m) + origin;
        else if (n > 0L) {  // reject over-represented candidates
            for (long u = r >>> 1;            // ensure nonnegative
                 u + m - (r = u % n) < 0L;    // rejection check
                 u = random.nextLong() >>> 1) // retry
                ;
            r += origin;
        } else {              // range not representable as long
            while (r < origin || r >= bound)
                r = random.nextLong();
        }
        return r;
    }

    /**
     * 获取指定范围 double 值
     * @param origin 开始值
     * @param bound  范围值
     * @return 属于指定范围随机 double 值
     * @throws IllegalArgumentException 参数错误
     */
    public static double nextDoubleRange(final double origin, final double bound) throws IllegalArgumentException {
        if (origin > bound) {
            throw new IllegalArgumentException("bound must be greater than origin");
        } else if (origin == bound) {
            return origin;
        }
        double r = new Random().nextDouble();
        r = r * (bound - origin) + origin;
        if (r >= bound) // correct for rounding
            r = Double.longBitsToDouble(Double.doubleToLongBits(bound) - 1);
        return r;
    }

    /**
     * 获取随机 int 数组
     * @param streamSize         数组长度
     * @param randomNumberOrigin 开始值
     * @param randomNumberBound  结束值(最大值范围)
     * @return 指定范围随机 int[]
     */
    public static int[] ints(final int streamSize, final int randomNumberOrigin, final int randomNumberBound) {
        if (randomNumberOrigin >= randomNumberBound) {
            return null;
        } else if (streamSize < 0) {
            return null;
        }
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//			IntStream intStream = new Random().ints(streamSize, randomNumberOrigin, randomNumberBound);
//			return intStream.toArray();
//		} else {
        try {
            int[] ints = new int[streamSize];
            for (int i = 0; i < streamSize; i++) {
                ints[i] = nextIntRange(randomNumberOrigin, randomNumberBound);
            }
            return ints;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "ints");
        }
        return null;
//		}
    }

    /**
     * 获取随机 long 数组
     * @param streamSize         数组长度
     * @param randomNumberOrigin 开始值
     * @param randomNumberBound  结束值(最大值范围)
     * @return 指定范围随机 long[]
     */
    public static long[] longs(final int streamSize, final long randomNumberOrigin, final long randomNumberBound) {
        if (randomNumberOrigin >= randomNumberBound) {
            return null;
        } else if (streamSize < 0) {
            return null;
        }
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//			LongStream longStream = new Random().longs(streamSize, randomNumberOrigin, randomNumberBound);
//			return longStream.toArray();
//		} else {
        try {
            long[] longs = new long[streamSize];
            for (int i = 0; i < streamSize; i++) {
                longs[i] = nextLongRange(randomNumberOrigin, randomNumberBound);
            }
            return longs;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "longs");
        }
        return null;
//		}
    }

    /**
     * 获取随机 double 数组
     * @param streamSize         数组长度
     * @param randomNumberOrigin 开始值
     * @param randomNumberBound  结束值(最大值范围)
     * @return 指定范围随机 double[]
     */
    public static double[] doubles(final int streamSize, final double randomNumberOrigin, final double randomNumberBound) {
        if (randomNumberOrigin >= randomNumberBound) {
            return null;
        } else if (streamSize < 0) {
            return null;
        }
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//			DoubleStream doubleStream = new Random().doubles(streamSize, randomNumberOrigin, randomNumberBound);
//			return doubleStream.toArray();
//		} else {
        try {
            double[] doubles = new double[streamSize];
            for (int i = 0; i < streamSize; i++) {
                doubles[i] = nextDoubleRange(randomNumberOrigin, randomNumberBound);
            }
            return doubles;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "doubles");
        }
        return null;
//		}
    }
}
