package dev.utils.app.image;

import android.graphics.Bitmap;

import dev.utils.LogPrintUtils;

/**
 * detail: 图片格式转换工具类
 * @author Ttt
 */
public final class ImageConvertUtils {

    private ImageConvertUtils() {
    }

    // 日志 TAG
    private static final String TAG = ImageConvertUtils.class.getSimpleName();

    // ================
    // = 转换 BMP 图片 =
    // ================

    /**
     * detail: 转换 BMP 内部类
     * @author Ttt
     */
    private static final class BMP {

        private BMP() {
        }

        /**
         * BMP 位图, 头结构
         * @param size 文件总大小 ( 字节数 )
         * @return BMP 头结构 byte[]
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
            // = 位图文件保留字, 必须为 0 =
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
         * BMP 位图, 头信息
         * @param width  宽度
         * @param height 高度
         * @return BMP 头信息 byte[]
         */
        private static byte[] addBMPImageInfosHeader(
                final int width,
                final int height
        ) {
            byte[] buffer = new byte[40];
            // =
            buffer[0] = 0x28;
            buffer[1] = 0x00;
            buffer[2] = 0x00;
            buffer[3] = 0x00;
            // =
            buffer[4] = (byte) (width >> 0);
            buffer[5] = (byte) (width >> 8);
            buffer[6] = (byte) (width >> 16);
            buffer[7] = (byte) (width >> 24);
            // =
            buffer[8] = (byte) (height >> 0);
            buffer[9] = (byte) (height >> 8);
            buffer[10] = (byte) (height >> 16);
            buffer[11] = (byte) (height >> 24);
            // =
            buffer[12] = 0x01;
            buffer[13] = 0x00;
            // =
            buffer[14] = 0x20; // 位数 0x20 ( 32 位 )
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
         * 增加位图 ARGB 值
         * @param data   图片数据
         * @param width  宽度
         * @param height 高度
         * @return BMP ARGB byte[]
         */
        private static byte[] addBMP_ARGB8888(
                final int[] data,
                final int width,
                final int height
        ) {
            if (data == null) return null;
            int len = data.length;
            if (len == 0) return null;
            byte[] buffer = new byte[width * height * 4]; // A + R + G + B = 4
            int    offset = 0; // 计算偏移量
            for (int i = len - 1; i >= 0; i -= width) {
                // DIB 文件格式最后一行为第一行, 每行按从左到右顺序
                int end = i, start = i - width + 1;
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
         * 图片转换 BMP 格式 byte[] 数据
         * @param bitmap 待转换图片
         * @return BMP 格式 byte[] 数据
         */
        public static byte[] convertBMP(final Bitmap bitmap) {
            if (bitmap == null) return null;
            try {
                int   width  = bitmap.getWidth(), height = bitmap.getHeight();
                int[] pixels = new int[width * height];
                bitmap.getPixels(pixels, 0, width, 0, 0, width, height);

                byte[] rgb    = addBMP_ARGB8888(pixels, width, height);
                byte[] header = addBMPImageHeader(rgb.length);
                byte[] infos  = addBMPImageInfosHeader(width, height);
                byte[] buffer = new byte[54 + rgb.length];

                System.arraycopy(header, 0, buffer, 0, header.length);
                System.arraycopy(infos, 0, buffer, 14, infos.length);
                System.arraycopy(rgb, 0, buffer, 54, rgb.length);
                return buffer;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "convertBMP");
            }
            return null;
        }
    }

    // =

    /**
     * 图片转换 BMP 格式 byte[] 数据
     * @param bitmap 待转换图片
     * @return BMP 格式 byte[] 数据
     */
    public static byte[] convertBMP(final Bitmap bitmap) {
        return BMP.convertBMP(bitmap);
    }
}