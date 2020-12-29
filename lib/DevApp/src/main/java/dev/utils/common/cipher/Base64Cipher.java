package dev.utils.common.cipher;

/**
 * detail: Base64 编解码 ( 并进行 ) 加解密
 * @author Ttt
 */
public class Base64Cipher
        implements Cipher {

    // 中间加密层
    private Cipher mCipher;
    // Base64 编解码 flags
    private int    mFlags = Base64.DEFAULT;

    /**
     * 构造函数
     * @param flags Base64 编解码 flags
     */
    public Base64Cipher(final int flags) {
        this.mFlags = flags;
    }

    /**
     * 构造函数
     * @param cipher 加解密中间层
     */
    public Base64Cipher(final Cipher cipher) {
        this.mCipher = cipher;
    }

    /**
     * 构造函数
     * @param cipher 加解密中间层
     * @param flags  Base64 编解码 flags
     */
    public Base64Cipher(
            final Cipher cipher,
            final int flags
    ) {
        this.mCipher = cipher;
        this.mFlags = flags;
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
        data = Base64.decode(data, mFlags);
        // 再解密
        if (mCipher != null) {
            data = mCipher.decrypt(data);
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
        if (mCipher != null) {
            data = mCipher.encrypt(data);
        }
        if (data == null) return null;
        // 再编码
        return Base64.encode(data, mFlags);
    }
}