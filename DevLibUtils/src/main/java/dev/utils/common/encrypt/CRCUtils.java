package dev.utils.common.encrypt;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.zip.CRC32;

import dev.utils.JCLogUtils;

/**
 * detail: CRC 工具类
 * Created by Ttt
 * ==============
 * Cyclic Redundancy Check, CRC 循环冗余校验 是一种根据网络数据包或电脑文件等数据产生简短固定位数校验码的一种散列函数
 */
public final class CRCUtils {

    private CRCUtils() {
    }

    // 日志 TAG
    private static final String TAG = CRCUtils.class.getSimpleName();

    /**
     * 获取 CRC32 值
     * @param str
     * @return
     */
    public static long getCRC32(final String str) {
        if (str == null) return -1l;
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
     * 获取 CRC32 值
     * @param str
     * @return
     */
    public static String getCRC32Str(final String str) {
        if (str == null) return null;
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
     * 获取文件 CRC32 值
     * @param filePath
     * @return
     */
    public static String getFileCrc32(final String filePath) {
        if (filePath == null) return null;
        try {
            InputStream fis = new FileInputStream(filePath);
            byte[] buffer = new byte[1024];
            CRC32 crc32 = new CRC32();
            int numRead;
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
