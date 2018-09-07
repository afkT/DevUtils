package dev.utils.common.encrypt;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.zip.CRC32;

import dev.utils.JCLogUtils;

/**
 * detail: CRC 工具类
 * Created by Ttt
 */
public final class CRCUtils {

    private CRCUtils() {
    }

    // 日志TAG
    private static final String TAG = CRCUtils.class.getSimpleName();

    /**
     * 获取 CRC32 值(返回Long,一定几率上唯一)
     * @param str
     * @return
     */
    public static long getCRC32(String str){
        try {
            CRC32 crc32 = new CRC32();
            crc32.update(str.getBytes());
            return crc32.getValue();
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getCRC32");
        }
        return -1l;
    }

    /**
     * 获取 CRC32 值(做了处理,返回String)
     * @param str
     * @return
     */
    public static String getCRC32Str(String str){
        try {
            CRC32 crc32 = new CRC32();
            crc32.update(str.getBytes());
            return Long.toHexString(crc32.getValue());
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getCRC32Str");
        }
        return null;
    }

    /**
     * 获取文件CRC32 值
     * @return
     */
    public static String getFileCrc32(String fPath){
        try {
            InputStream fis = new FileInputStream(fPath);
            byte[] buffer = new byte[1024];
            CRC32 crc32 = new CRC32();
            int numRead = 0;
            while ((numRead = fis.read(buffer)) > 0) {
                crc32.update(buffer, 0, numRead);
            }
            fis.close();
            return Long.toHexString(crc32.getValue());
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getFileCrc32");
        }
        return null;
    }
}
