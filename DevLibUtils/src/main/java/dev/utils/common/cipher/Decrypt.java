package dev.utils.common.cipher;

/**
 * detail: 解密/解码 接口
 * @author Ttt
 */
public interface Decrypt {

    /**
     * 解密/解码 方法
     * @param data byte[]
     * @return byte[]
     */
    byte[] decrypt(byte[] data);
}
