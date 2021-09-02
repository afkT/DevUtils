package java.dev.engine.barcode;

import android.graphics.Bitmap;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

import dev.engine.barcode.IBarCodeEngine;
import dev.engine.barcode.listener.BarCodeDecodeCallback;
import dev.engine.barcode.listener.BarCodeEncodeCallback;
import dev.utils.LogPrintUtils;
import dev.utils.common.StringUtils;
import dev.utils.common.thread.DevThreadManager;

/**
 * detail: BarCode Engine 实现
 * @author Ttt
 */
public class ZXingEngineImpl
        implements IBarCodeEngine<BarCodeConfig, BarCodeData, BarCodeResult> {

    // 日志 TAG
    private final String TAG = ZXingEngineImpl.class.getSimpleName();

    // 线程数量
    private final int           THREAD_NUMBER  = 6;
    // 默认条码配置
    private final BarCodeConfig DEFAULT_CONFIG = new BarCodeConfig().defaultEncode();
    // 当前条码配置
    private       BarCodeConfig mBarCodeConfig;

    // =============
    // = 对外公开方法 =
    // =============

    @Override
    public void initialize(BarCodeConfig config) {
        this.mBarCodeConfig = config;
    }

    @Override
    public BarCodeConfig getConfig() {
        return mBarCodeConfig;
    }

    // ==========
    // = 生成条码 =
    // ==========

    @Override
    public void encodeBarCode(
            BarCodeData params,
            BarCodeEncodeCallback callback
    ) {
        Exception error = isValidData(params);
        if (error != null) {
            encodeFailureCallback(callback, error);
            return;
        }
        DevThreadManager.getInstance(THREAD_NUMBER).execute(() -> {
            try {
                Bitmap bitmap = encodeBarCodeSync(params);
                // 条码嵌入 icon、logo
                if (params.getIcon() != null) {
                    bitmap = addLogoToBarCode(params, bitmap, params.getIcon());
                }
                encodeCallback(callback, bitmap);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "encodeBarCode");
                // 触发回调
                encodeFailureCallback(callback, e);
            }
        });
    }

    @Override
    public Bitmap encodeBarCodeSync(BarCodeData params)
            throws Exception {
        Exception error = isValidData(params);
        if (error == null) {
            BarCodeConfig config = getInnerConfig();
            // 条码宽高
            int width  = params.getWidth();
            int height = params.getHeight();
            // 创建条码矩阵
            BitMatrix matrix = new MultiFormatWriter().encode(
                    params.getContent(), params.getFormat(),
                    width, height, config.getEncodeHints()
            );
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (matrix.get(x, y)) {
                        pixels[y * height + x] = params.getForegroundColor();
                    } else {
                        pixels[y * height + x] = params.getBackgroundColor();
                    }
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return bitmap;
        }
        throw error;
    }

    // ==========
    // = 解析条码 =
    // ==========

    @Override
    public void decodeBarCode(
            Bitmap bitmap,
            BarCodeDecodeCallback<BarCodeResult> callback
    ) {
        if (bitmap == null) {
            decodeFailureCallback(callback, new Exception("BarCode decode Bitmap is null"));
            return;
        }
        DevThreadManager.getInstance(THREAD_NUMBER).execute(() -> {
            try {
                BarCodeResult result = decodeBarCodeSync(bitmap);
                // 触发回调
                decodeCallback(callback, result);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "decodeBarCode");
                // 触发回调
                decodeFailureCallback(callback, e);
            }
        });
    }

    @Override
    public BarCodeResult decodeBarCodeSync(Bitmap bitmap)
            throws Exception {
        if (bitmap != null) {
            BarCodeConfig config = getInnerConfig();
            // 解码处理
            int   width  = bitmap.getWidth();
            int   height = bitmap.getHeight();
            int[] pixels = new int[width * height];
            bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
            RGBLuminanceSource source = new RGBLuminanceSource(width, height, pixels);
            Result result = new MultiFormatReader().decode(
                    new BinaryBitmap(new HybridBinarizer(source)),
                    config.getDecodeHints()
            );
            return new BarCodeResult(result);
        }
        throw new Exception("BarCode decode Bitmap is null");
    }

    // ==========
    // = 其他功能 =
    // ==========

    @Override
    public Bitmap addLogoToBarCode(
            BarCodeData params,
            Bitmap src,
            Bitmap logo
    ) throws Exception {

        return null;
    }

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * 获取 BarCode Config
     * @return BarCode Config
     */
    private BarCodeConfig getInnerConfig() {
        if (mBarCodeConfig != null) return mBarCodeConfig;
        return DEFAULT_CONFIG;
    }

    /**
     * 判断是否有效数据
     * @param params BarCode ( Data、Params ) Item
     * @return 如果属于有效数据则返回 null
     */
    private Exception isValidData(BarCodeData params) {
        if (params == null) {
            return new Exception("BarCode ( Data、Params ) Item is null");
        }
        if (StringUtils.isEmpty(params.getContent())) {
            return new Exception("BarCode content is null");
        }
        if (params.getWidth() <= 0 || params.getHeight() <= 0) {
            return new Exception("BarCode width、height Less than or equal to 0");
        }
        if (params.getFormat() == null) {
            return new Exception("BarCode format is null");
        }
        return null;
    }

    // =

    /**
     * 编码 ( 生成 ) 回调
     * @param callback 生成结果回调
     * @param bitmap   条码图片
     */
    private void encodeCallback(
            BarCodeEncodeCallback callback,
            Bitmap bitmap
    ) {
        if (callback != null) {
            if (bitmap != null) {
                callback.onResult(true, bitmap, null);
            } else {
                callback.onResult(
                        false, null,
                        new Exception("BarCode encode Bitmap is null")
                );
            }
        }
    }

    /**
     * 编码 ( 生成 ) 失败回调
     * @param callback 生成结果回调
     * @param error    异常信息
     */
    private void encodeFailureCallback(
            BarCodeEncodeCallback callback,
            Throwable error
    ) {
        if (callback != null) {
            callback.onResult(false, null, error);
        }
    }

    // =

    /**
     * 解码 ( 解析 ) 回调
     * @param callback 生成结果回调
     * @param result   识别结果
     */
    private void decodeCallback(
            BarCodeDecodeCallback<BarCodeResult> callback,
            BarCodeResult result
    ) {
        if (callback != null) {
            if (result != null) {
                callback.onResult(true, result, null);
            } else {
                callback.onResult(
                        false, null,
                        new Exception("BarCode Result is null")
                );
            }
        }
    }

    /**
     * 解码 ( 解析 ) 失败回调
     * @param callback 生成结果回调
     * @param error    异常信息
     */
    private void decodeFailureCallback(
            BarCodeDecodeCallback<BarCodeResult> callback,
            Throwable error
    ) {
        if (callback != null) {
            callback.onResult(false, null, error);
        }
    }
}