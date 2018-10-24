package dev.utils.common.cipher;

/**
 * detail: 解密/解码接口
 * Created by MaTianyu
 * Update to Ttt
 */
public interface Decrypt {

    /**
     * 解密/解码方法
     * @param res
     * @return
     */
    byte[] decrypt(byte[] res);

}
