package dev.other;

import android.graphics.Bitmap;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

import java.util.EnumMap;
import java.util.Map;

import dev.utils.LogPrintUtils;
import dev.utils.common.thread.DevThreadManager;

/**
 * detail: ZXing 二维码工具类
 * @author Ttt
 */
public final class ZXingQRCodeUtils {

    private ZXingQRCodeUtils() {
    }

    private static final String TAG = ZXingQRCodeUtils.class.getSimpleName();

    // ==============
    // = 生成二维码 =
    // ==============

    /**
     * detail: 生成二维码结果回调
     * @author Ttt
     */
    public interface QRResultCallBack {

        /**
         * 生成二维码结果回调
         * @param success 是否成功
         * @param bitmap  成功图片
         * @param e       异常信息
         */
        void onResult(boolean success, Bitmap bitmap, Exception e);
    }

    // =

    /**
     * 生成二维码图片
     * @param content 生成内容
     * @param size    图片宽高大小 ( 正方形 px )
     */
    public static void createQRCodeImage(final String content, final int size) {
        createQRCodeImage(content, size, null, null);
    }

    /**
     * 生成二维码图片
     * @param content          生成内容
     * @param size             图片宽高大小 ( 正方形 px )
     * @param qrResultCallBack 生成结果回调
     */
    public static void createQRCodeImage(final String content, final int size, final QRResultCallBack qrResultCallBack) {
        createQRCodeImage(content, size, null, qrResultCallBack);
    }

    /**
     * 生成二维码图片
     * @param content          生成内容
     * @param size             图片宽高大小 ( 正方形 px )
     * @param logo             中间 Logo
     * @param qrResultCallBack 生成结果回调
     */
    public static void createQRCodeImage(final String content, final int size, final Bitmap logo, final QRResultCallBack qrResultCallBack) {
        DevThreadManager.getInstance(10).execute(new Runnable() {
            @Override
            public void run() {
                try {
                    // 生成二维码图片
                    Bitmap qrCodeBitmap = EncodingUtils.syncEncodeQRCode(content, size);
                    if (logo != null) { // 中间 Logo
                        qrCodeBitmap = EncodingUtils.addLogoToQRCode(qrCodeBitmap, logo);
                    }
                    // 触发回调
                    if (qrResultCallBack != null) {
                        qrResultCallBack.onResult(true, qrCodeBitmap, null);
                    }
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "createQRCodeImage");
                    // 触发回调
                    if (qrResultCallBack != null) {
                        qrResultCallBack.onResult(false, null, e);
                    }
                }
            }
        });
    }

    // ==============
    // = 解析二维码 =
    // ==============

    // 解析配置
    public static final Map<DecodeHintType, Object> HINTS = new EnumMap<>(DecodeHintType.class);

    /**
     * detail: 二维码扫描结果回调
     * @author Ttt
     */
    public interface QRScanCallBack {

        /**
         * 二维码扫描结果回调
         * @param success 是否成功
         * @param result  识别数据 {@link Result}
         * @param e       异常信息
         */
        void onResult(boolean success, Result result, Exception e);
    }

    // =

    /**
     * 解析二维码图片
     * @param bitmap         待解析的二维码图片
     * @param qrScanCallBack 解析结果回调
     */
    public static void decodeQRCode(final Bitmap bitmap, final QRScanCallBack qrScanCallBack) {
        if (bitmap == null) {
            if (qrScanCallBack != null) {
                qrScanCallBack.onResult(false, null, new Exception("bitmap is null"));
            }
            return;
        }
        // 开始解析
        DevThreadManager.getInstance(5).execute(new Runnable() {
            @Override
            public void run() {
                try {
                    int width = bitmap.getWidth();
                    int height = bitmap.getHeight();
                    int[] pixels = new int[width * height];
                    bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
                    RGBLuminanceSource source = new RGBLuminanceSource(width, height, pixels);
                    Result result = new MultiFormatReader().decode(new BinaryBitmap(new HybridBinarizer(source)), HINTS);
                    // 触发回调
                    if (qrScanCallBack != null) {
                        qrScanCallBack.onResult((result != null), result, null);
                    }
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "decodeQRCode");
                    // 触发回调
                    if (qrScanCallBack != null) {
                        qrScanCallBack.onResult(false, null, e);
                    }
                }
            }
        });
    }

    // ============
    // = 获取结果 =
    // ============

    /**
     * 获取扫描结果数据
     * @param result 识别数据 {@link Result}
     * @return 扫描结果数据
     */
    public static String getResultData(final Result result) {
        return (result != null) ? result.getText() : null;
    }
}
