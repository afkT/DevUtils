package dev.utils.app.image;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;

import dev.utils.LogPrintUtils;

/**
 * detail: 图片Buf转换 - 转换BMP图片
 * @author Ttt
 */
public final class ImageBmpUtils {

    private ImageBmpUtils() {
    }

    // 日志 TAG
    private static final String TAG = ImageBmpUtils.class.getSimpleName();

    // = 转换BMP图片 =

    /**
     * bmp位图，头结构
     * @param size 文件总大小(字节数)
     * @return
     */
    private static byte[] addBMPImageHeader(final int size) {
        byte[] buffer = new byte[14];
        // = 文件标识 BM =
        buffer[0] = 0x42;
        buffer[1] = 0x4D;
        // = 位图文件大小 =
        buffer[2] = (byte) (size >> 0);
        buffer[3] = (byte) (size >> 8);
        buffer[4] = (byte) (size >> 16);
        buffer[5] = (byte) (size >> 24);
        // = 位图文件保留字，必须为0 =
        buffer[6] = 0x00;
        buffer[7] = 0x00;
        buffer[8] = 0x00;
        buffer[9] = 0x00;
        // = 位图数据开始之间的偏移量 =
        buffer[10] = 0x36;
        buffer[11] = 0x00;
        buffer[12] = 0x00;
        buffer[13] = 0x00;
        return buffer;
    }

    /**
     * bmp位图，头信息
     * @param w 宽度
     * @param h 高度
     * @return
     */
    private static byte[] addBMPImageInfosHeader(final int w, final int h) {
        byte[] buffer = new byte[40];
        // =
        buffer[0] = 0x28;
        buffer[1] = 0x00;
        buffer[2] = 0x00;
        buffer[3] = 0x00;
        // =
        buffer[4] = (byte) (w >> 0);
        buffer[5] = (byte) (w >> 8);
        buffer[6] = (byte) (w >> 16);
        buffer[7] = (byte) (w >> 24);
        // =
        buffer[8] = (byte) (h >> 0);
        buffer[9] = (byte) (h >> 8);
        buffer[10] = (byte) (h >> 16);
        buffer[11] = (byte) (h >> 24);
        // =
        buffer[12] = 0x01;
        buffer[13] = 0x00;
        // =
        buffer[14] = 0x20; // 位数 0x20 - 32位
        buffer[15] = 0x00;
        // =
        buffer[16] = 0x00;
        buffer[17] = 0x00;
        buffer[18] = 0x00;
        buffer[19] = 0x00;
        // =
        buffer[20] = 0x00;
        buffer[21] = 0x00;
        buffer[22] = 0x00;
        buffer[23] = 0x00;
        // =
        buffer[24] = (byte) 0xE0;
        buffer[25] = 0x01;
        buffer[26] = 0x00;
        buffer[27] = 0x00;
        // =
        buffer[28] = 0x02;
        buffer[29] = 0x03;
        buffer[30] = 0x00;
        buffer[31] = 0x00;
        // =
        buffer[32] = 0x00;
        buffer[33] = 0x00;
        buffer[34] = 0x00;
        buffer[35] = 0x00;
        // =
        buffer[36] = 0x00;
        buffer[37] = 0x00;
        buffer[38] = 0x00;
        buffer[39] = 0x00;
        return buffer;
    }

    /**
     * 增加位图 ARGB值
     * @param data 数据
     * @param w    宽度
     * @param h    高度
     * @return
     */
    private static byte[] addBMP_ARGB_8888(final int[] data, final int w, final int h) {
        if (data == null) return null;
        int len = data.length;
        if (len == 0) return null;
        byte[] buffer = new byte[w * h * 4]; // A + R + G + B = 4
        int offset = 0; // 计算偏移量
        for (int i = len - 1; i >= 0; i -= w) {
            // DIB文件格式最后一行为第一行，每行按从左到右顺序
            int end = i, start = i - w + 1;
            for (int j = start; j <= end; j++) {
                buffer[offset] = (byte) (data[j] >> 0);
                buffer[offset + 1] = (byte) (data[j] >> 8);
                buffer[offset + 2] = (byte) (data[j] >> 16);
                buffer[offset + 3] = (byte) (data[j] >> 24);
                offset += 4;
            }
        }
        return buffer;
    }

    /**
     * 获取bmp位图 byte数据
     * @param bitmap
     * @return
     */
    private static byte[] getBmpBytes(final Bitmap bitmap) {
        try {
            if (bitmap != null) {
                int w = bitmap.getWidth(), h = bitmap.getHeight();
                int[] pixels = new int[w * h];
                bitmap.getPixels(pixels, 0, w, 0, 0, w, h);
                // =
                byte[] rgb = addBMP_ARGB_8888(pixels, w, h);
                byte[] header = addBMPImageHeader(rgb.length);
                byte[] infos = addBMPImageInfosHeader(w, h);
                // =
                byte[] buffer = new byte[54 + rgb.length];
                // =
                System.arraycopy(header, 0, buffer, 0, header.length);
                System.arraycopy(infos, 0, buffer, 14, infos.length);
                System.arraycopy(rgb, 0, buffer, 54, rgb.length);
                // =
                return buffer;
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getBmpBytes");
        }
        return null;
    }

    /**
     * 保存Bmp图片
     * @param path   保存路径
     * @param bitmap 图片信息
     * @return {@code true} 保存成功, {@code false} 保存失败
     */
    public static boolean saveBmpImg(final String path, final Bitmap bitmap) {
        if (bitmap == null) return false;

        FileOutputStream fos = null;
        try {
            // 转换Bmp byte数据
            byte[] buffer = getBmpBytes(bitmap);
            // 创建写入流
            fos = new FileOutputStream(new File(path));
            // 写入数据
            fos.write(buffer);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "saveBmpImg");
        } finally { // 最终关闭写入流
            try {
                if (fos != null) {
                    fos.flush();
                }
            } catch (Exception e) {
            }
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
            }
        }
        return false;
    }
}
