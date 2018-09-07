package dev.utils.common;

import java.util.Random;
import java.util.UUID;

import dev.utils.common.encrypt.MD5Utils;

/**
 * detail: 快捷辅助工具类
 * Created by Ttt
 */
public final class AssistUtils {

    private AssistUtils(){
    }

    /**
     * 获取随机唯一数
     * @return
     */
    public static UUID randomUUID(){
        return UUID.randomUUID();
    }

    /**
     * 获取随机唯一数 HashCode
     * @return
     */
    public static int randomUUIDToHashCode(){
        return UUID.randomUUID().hashCode();
    }

    /**
     * 获取随机唯一数 HashCode
     * @param uuid
     * @return
     */
    public static int randomUUIDToHashCode(UUID uuid){
        if (uuid != null){
            return uuid.hashCode();
        }
        return -1;
    }

    /**
     * 获取随机数 唯一id
     * @return
     */
    public static String getRandomUUID(){
        // 获取随机数
        String random1 = (900000 + new Random().nextInt(10000)) + "";
        // 获取随机数
        String random2 = (900000 + new Random().nextInt(10000)) + "";
        // 获取当前时间
        String cTime = Long.toString(System.currentTimeMillis()) + random1 + random2;
        // 生成唯一随机uuid  cTime.hashCode(), random1.hashCode() | random2.hashCode()
        UUID randomUUID = new UUID(cTime.hashCode(), ((long) random1.hashCode() << 32) | random2.hashCode());
        // 获取uid
        return randomUUID.toString();
    }

    // -

    /**
     * 循环MD5 加密处理
     * @param data
     * @param number
     * @param isUppercase
     * @param salts
     * @return
     */
    public static String whileMD5(String data, int number, boolean isUppercase, String... salts){
        if (data != null && number >= 1){
            int saltLen = (salts != null) ? salts.length : 0;
            // 判断是否大写
            if (isUppercase){
                // 循环加密
                for (int i = 0; i < number; i++){
                    if (saltLen >= i){
                        String salt = salts[i];
                        if (salt != null){
                            data = MD5Utils.md5Upper(data + salt);
                        } else {
                            data = MD5Utils.md5Upper(data);
                        }
                    } else {
                        data = MD5Utils.md5Upper(data);
                    }
                }
            } else {
                // 循环加密
                for (int i = 0; i < number; i++){
                    if (saltLen >= i){
                        String salt = salts[i];
                        if (salt != null){
                            data = MD5Utils.md5(data + salt);
                        } else {
                            data = MD5Utils.md5(data);
                        }
                    } else {
                        data = MD5Utils.md5(data);
                    }
                }
            }
        }
        return data;
    }
}
