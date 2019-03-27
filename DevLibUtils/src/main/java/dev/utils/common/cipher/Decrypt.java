package dev.utils.common.cipher;

/**
 * detail: 解密/解码 接口
 * Created by Ttt
 */
public interface Decrypt {

    /**
     * 解密/解码 方法
     * @param data
     * @return
     */
    byte[] decrypt(byte[] data);

}
