package dev.utils.common.encrypt;

/**
 * detail: 异或 ( 加密 ) 工具类
 * @author Ttt
 * <pre>
 *     位运算可以实现很多高级、高效的运算
 *     可用于 IM 二进制数据包加密
 *     1. 能够实现加密
 *     2. 采用异或加密算法不会改变二进制数据的长度这对二进制数据包封包起到不小的好处
 *     也可用于记事本等场景
 *     <p></p>
 *     参考链接
 *     @see <a href="http://www.cnblogs.com/whoislcj/p/5944917.html"/>
 * </pre>
 */
public final class XorUtils {

    private XorUtils() {
    }

    /**
     * 加解密 ( 固定 Key 方式 ) 这种方式 加解密 方法共用
     * <pre>
     *     加密: byte[] bytes = encryptAsFix("123".getBytes());
     *     解密: String str = new String(encryptAsFix(bytes));
     * </pre>
     * @param data 待加解密数据
     * @return 加解密后的数据 byte[]
     */
    public static byte[] encryptAsFix(final byte[] data) {
        if (data == null) return null;
        int len = data.length;
        if (len == 0) return null;
        int key = 0x12;
        for (int i = 0; i < len; i++) {
            data[i] ^= key;
        }
        return data;
    }

    // =

    /**
     * 加密 ( 非固定 Key 方式 )
     * @param data 待加密数据
     * @return 加密后的数据 byte[]
     */
    public static byte[] encrypt(final byte[] data) {
        if (data == null) return null;
        int len = data.length;
        if (len == 0) return null;
        int key = 0x12;
        for (int i = 0; i < len; i++) {
            data[i] = (byte) (data[i] ^ key);
            key = data[i];
        }
        return data;
    }

    /**
     * 解密 ( 非固定 Key 方式 )
     * @param data 待解密数据
     * @return 解密后的数据 byte[]
     */
    public static byte[] decrypt(final byte[] data) {
        if (data == null) return null;
        int len = data.length;
        if (len == 0) return null;
        int key = 0x12;
        for (int i = len - 1; i > 0; i--) {
            data[i] = (byte) (data[i] ^ data[i - 1]);
        }
        data[0] = (byte) (data[0] ^ key);
        return data;
    }
}