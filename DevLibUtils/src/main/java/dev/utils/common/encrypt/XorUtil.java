package dev.utils.common.encrypt;

/**
 * detail: 异或加密工具类
 * Created by albert-lii
 * 位运算可以实现很多高级，高效的运算。
 * 可用于 IM 二进制数据包加密，第一能够实现加密，第二采用异或加密算法不会改变二进制数据的长度这对二进制数据包封包起到不小的好处
 * 也可用于记事本等场景
 * 参考链接：http://www.cnblogs.com/whoislcj/p/5944917.html
 */
public final class XorUtil {

    private XorUtil() {
    }

    /**
     * 固定 key 的方式加密
     * 这种方式加密解密 算法一样
     * 加密：byte[] bytes = encryptAsFix("liyi".getBytes());
     * 解密：String str = new String(encryptAsFix(bytes));
     * @param bytes 待加密数据
     * @return 加密后的数据
     */
    public static byte[] encryptAsFix(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        int len = bytes.length;
        int key = 0x12;
        for (int i = 0; i < len; i++) {
            bytes[i] ^= key;
        }
        return bytes;
    }


    /**
     * 非固定 key 的方式加密
     * @param bytes 待加密数据
     * @return 加密后的数据
     */
    public static byte[] encrypt(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        int len = bytes.length;
        int key = 0x12;
        for (int i = 0; i < len; i++) {
            bytes[i] = (byte) (bytes[i] ^ key);
            key = bytes[i];
        }
        return bytes;
    }

    /**
     * 解密
     * @param bytes 待解密数据
     * @return 解密后的数据
     */
    public byte[] decrypt(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        int len = bytes.length;
        int key = 0x12;
        for (int i = len - 1; i > 0; i--) {
            bytes[i] = (byte) (bytes[i] ^ bytes[i - 1]);
        }
        bytes[0] = (byte) (bytes[0] ^ key);
        return bytes;
    }
}
