package java.dev.other;

import android.graphics.Bitmap;

import java.dev.engine.barcode.BarCodeConfig;
import java.dev.engine.barcode.BarCodeData;
import java.dev.engine.barcode.BarCodeResult;
import java.dev.engine.barcode.ZXingEngineImpl;

import dev.engine.barcode.listener.BarCodeDecodeCallback;
import dev.engine.barcode.listener.BarCodeEncodeCallback;

/**
 * detail: ZXing 条形码、二维码工具类
 * @author Ttt
 * @deprecated 推荐使用 DevBarCodeEngine 实现类 ZXingEngineImpl
 */
@Deprecated
public final class ZXingUtils {

    private ZXingUtils() {
    }

    private static final ZXingEngineImpl IMPL = new ZXingEngineImpl();

    // =============
    // = 对外公开方法 =
    // =============

    public static void initialize(BarCodeConfig config) {
        IMPL.initialize(config);
    }

    public static BarCodeConfig getConfig() {
        return IMPL.getConfig();
    }

    // ==========
    // = 生成条码 =
    // ==========

    public static void encodeBarCode(
            BarCodeData params,
            BarCodeEncodeCallback callback
    ) {
        IMPL.encodeBarCode(params, callback);
    }

    public static Bitmap encodeBarCodeSync(BarCodeData params)
            throws Exception {
        return IMPL.encodeBarCodeSync(params);
    }

    // ==========
    // = 解析条码 =
    // ==========

    public static void decodeBarCode(
            Bitmap bitmap,
            BarCodeDecodeCallback<BarCodeResult> callback
    ) {
        IMPL.decodeBarCode(bitmap, callback);
    }

    public static BarCodeResult decodeBarCodeSync(Bitmap bitmap)
            throws Exception {
        return IMPL.decodeBarCodeSync(bitmap);
    }

    // ==========
    // = 其他功能 =
    // ==========

    public static Bitmap addIconToBarCode(
            BarCodeData params,
            Bitmap src,
            Bitmap icon
    )
            throws Exception {
        return IMPL.addIconToBarCode(params, src, icon);
    }
}