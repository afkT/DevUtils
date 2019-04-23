package dev.utils.common.cipher;

/**
 * detail: 解密/解码 接口
 * @author Ttt
 */
public interface Decrypt {

    /**
     * 解密/解码 方法
     * @param data 待解码数据
     * @return 解码后的 byte[]
     */
    byte[] decrypt(byte[] data);
}
