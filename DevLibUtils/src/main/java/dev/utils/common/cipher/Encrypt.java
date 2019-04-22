package dev.utils.common.cipher;

/**
 * detail: 加密/编码 接口
 * @author Ttt
 */
public interface Encrypt {

    /**
     * 加密/编码 方法
     * @param data byte[]
     * @return byte[]
     */
    byte[] encrypt(byte[] data);
}
