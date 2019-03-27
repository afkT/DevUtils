package dev.utils.common.cipher;

import dev.utils.common.assist.Base64;

/**
 * detail: Baes64 编/解码 并进行 加/解密
 * Created by Ttt
 */
public class Base64Cipher implements Cipher {

    // 中间加密层
    private Cipher cipher;

    public Base64Cipher() {
    }

    public Base64Cipher(Cipher cipher) {
        this.cipher = cipher;
    }

    /**
     * 解码
     * @param data
     * @return
     */
    @Override
    public byte[] decrypt(byte[] data) {
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
     * @param data
     * @return
     */
    @Override
    public byte[] encrypt(byte[] data) {
        // 先加密
        if (cipher != null) {
            data = cipher.encrypt(data);
        }
        // 再编码
        return Base64.encode(data, Base64.DEFAULT);
    }
}
