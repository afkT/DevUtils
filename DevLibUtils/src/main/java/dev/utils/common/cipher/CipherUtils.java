package dev.utils.common.cipher;

import dev.utils.common.ByteUtils;
import dev.utils.common.HexUtils;

/**
 * detail: 加密工具类
 * Created by Ttt
 */
public class CipherUtils {

    private CipherUtils(){
    }

    // 保存加密: 如SP
    // 保存到SP,可以通过 SP.put(key, CipherUtils.encrypt(obj, cipher));
    // 获取 Object obj = CipherUtils.decrypt(SP.get(key), cipher);

    /**
     *  加密工具类
     * @param obj
     * @return
     */
    public static String encrypt(Object obj){
        return encrypt(obj, null);
    }

    /**
     *  加密工具类
     * @param obj
     * @param cipher
     * @return
     */
    public static String encrypt(Object obj, Cipher cipher){
        if (obj == null) return null;
        byte[] bytes = ByteUtils.objectToByte(obj);
        if (cipher != null) bytes = cipher.encrypt(bytes);
        return HexUtils.encodeHexStr(bytes);
    }

    // -

    /**
     * 解密方法
     * @param hex
     * @return
     */
    public static Object decrypt (String hex){
        return decrypt(hex, null);
    }

    /**
     * 解密方法
     * @param hex
     * @param cipher
     * @return
     */
    public static Object decrypt (String hex, Cipher cipher){
        if (hex == null) return null;
        byte[] bytes = HexUtils.decodeHex(hex.toCharArray());
        if (cipher != null) bytes = cipher.decrypt(bytes);
        Object obj = ByteUtils.byteToObject(bytes);
        return obj;
    }


//    private static Cipher cipher = new Cipher() {
//        @Override
//        public byte[] decrypt(byte[] res) {
//            return res;
//        }
//
//        @Override
//        public byte[] encrypt(byte[] res) {
//            return res;
//        }
//    };
}
