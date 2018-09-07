package dev.utils.common.cipher;

import dev.utils.common.assist.Base64;

/**
 * detail: Baes64 编解码, 进行加密
 * @author MaTianyu
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
     * @param res
     * @return
     */
    @Override
    public byte[] decrypt(byte[] res) {
        res = Base64.decode(res, Base64.DEFAULT);
        if (cipher != null) {
            res = cipher.decrypt(res);
        }
        return res;
    }

    /**
     * 编码
     * @param res
     * @return
     */
    @Override
    public byte[] encrypt(byte[] res) {
        if (cipher != null) {
            res = cipher.encrypt(res);
        }
        return Base64.encode(res, Base64.DEFAULT);
    }
}
