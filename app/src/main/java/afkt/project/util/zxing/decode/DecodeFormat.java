package afkt.project.util.zxing.decode;

import androidx.annotation.IntDef;

import com.google.zxing.BarcodeFormat;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

/**
 * detail: 解码类型
 * @author Ttt
 */
public final class DecodeFormat {

    private DecodeFormat() {
    }

    // 1D 解码
    private static final Set<BarcodeFormat> PRODUCT_FORMATS;
    private static final Set<BarcodeFormat> INDUSTRIAL_FORMATS;
    private static final Set<BarcodeFormat> ONE_D_FORMATS;
    // 二维码解码
    private static final Set<BarcodeFormat> QR_CODE_FORMATS;

    static {
        // 1D 解码类型
        PRODUCT_FORMATS = EnumSet.of(BarcodeFormat.UPC_A, BarcodeFormat.UPC_E, BarcodeFormat.EAN_13,
                BarcodeFormat.EAN_8, BarcodeFormat.RSS_14, BarcodeFormat.RSS_EXPANDED);
        INDUSTRIAL_FORMATS = EnumSet.of(BarcodeFormat.CODE_39, BarcodeFormat.CODE_93,
                BarcodeFormat.CODE_128, BarcodeFormat.ITF, BarcodeFormat.CODABAR);
        ONE_D_FORMATS = EnumSet.copyOf(PRODUCT_FORMATS);
        ONE_D_FORMATS.addAll(INDUSTRIAL_FORMATS);
        // 二维码解码类型
        QR_CODE_FORMATS = EnumSet.of(BarcodeFormat.QR_CODE);
    }

    /**
     * 获取二维码解码类型集合
     * @return 二维码解码类型集合
     */
    public static Collection<BarcodeFormat> getQrCodeFormats() {
        return QR_CODE_FORMATS;
    }

    /**
     * 获取 1D 解码类型集合
     * @return 1D 解码类型集合
     */
    public static Collection<BarcodeFormat> getBarCodeFormats() {
        return ONE_D_FORMATS;
    }

    // 1D 条形码
    public static final int BARCODE = 0X100;
    // 二维码
    public static final int QRCODE  = 0X200;
    // 全部识别
    public static final int ALL     = 0X300;

    @IntDef({BARCODE, QRCODE, ALL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DecodeMode {
    }
}