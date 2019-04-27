package dev.utils.common.cipher;

/**
 * detail: Baes64 编/解码 并进行 加/解密
 * @author Ttt
 */
public class Base64Cipher implements Cipher {

    // 中间加密层
    private Cipher cipher;

    public Base64Cipher() {
    }

    /**
     * 构造函数
     * @param cipher 加/解密中间层
     */
    public Base64Cipher(final Cipher cipher) {
        this.cipher = cipher;
    }

    /**
     * 解码
     * @param data 待解码数据
     * @return 解码后的 byte[]
     */
    @Override
    public byte[] decrypt(byte[] data) {
        if (data == null) return null;
        // 先解码
        data = Base64.decode(data, Base64.DEFAULT);
        // 再解密
        if (cipher != null) {
            data = cipher.decrypt(data);
        }
        return data;
    }

    /**
     * 编码
     * @param data 待编码数据
     * @return 编码后的 byte[]
     */
    @Override
    public byte[] encrypt(byte[] data) {
        if (data == null) return null;
        // 先加密
        if (cipher != null) {
            data = cipher.encrypt(data);
        }
        if (data == null) return null;
        // 再编码
        return Base64.encode(data, Base64.DEFAULT);
    }
}
