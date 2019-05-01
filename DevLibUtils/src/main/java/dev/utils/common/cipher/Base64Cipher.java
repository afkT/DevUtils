package dev.utils.common.cipher;

/**
 * detail: Baes64 编/解码 并进行 加/解密
 * @author Ttt
 */
public class Base64Cipher implements Cipher {

    // 中间加密层
    private Cipher cipher;
    // Base64 编解码 flags
    private int flags = Base64.DEFAULT;

    /**
     * 构造函数
     * @param flags Base64 编解码 flags
     */
    public Base64Cipher(final int flags) {
        this.flags = flags;
    }

    /**
     * 构造函数
     * @param cipher 加/解密中间层
     */
    public Base64Cipher(final Cipher cipher) {
        this.cipher = cipher;
    }

    /**
     * 构造函数
     * @param cipher 加/解密中间层
     * @param flags  Base64 编解码 flags
     */
    public Base64Cipher(final Cipher cipher, final int flags) {
        this.cipher = cipher;
        this.flags = flags;
    }

    // =

    /**
     * 解码
     * @param data 待解码数据
     * @return 解码后的 byte[]
     */
    @Override
    public byte[] decrypt(byte[] data) {
        if (data == null) return null;
        // 先解码
        data = Base64.decode(data, flags);
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
        return Base64.encode(data, flags);
    }
}
