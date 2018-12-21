package dev.utils.common.cipher;

/**
 * detail: 加密/编码接口
 * Created by Ttt
 */
public interface Encrypt {

    /**
     * 加密/编码方法
     * @param res
     * @return
     */
    byte[] encrypt(byte[] res);

}
