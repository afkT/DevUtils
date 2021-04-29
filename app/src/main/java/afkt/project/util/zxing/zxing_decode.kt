package afkt.project.util.zxing

import android.graphics.Rect
import android.hardware.Camera
import android.os.Bundle
import android.os.Handler
import androidx.annotation.IntDef
import com.google.zxing.BarcodeFormat
import com.google.zxing.Result
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.util.*

/**
 * detail: 解码配置
 * @author Ttt
 */
interface DecodeConfig {

    // 获取处理 Handler
    val handler: Handler?

    // 是否裁减
    val isCropRect: Boolean

    // 裁减区域
    fun getCropRect(): Rect?

    // 获取预览大小
    val previewSize: Camera.Size?

    // 判断是否出现异常
    val isError: Boolean

    /**
     * 设置异常
     * @param isError 是否异常
     * @param e       异常信息
     */
    fun setError(
        isError: Boolean,
        e: Exception?
    )

    companion object {
        // 条形码数据 Key
        const val BARCODE_BITMAP = "barcode_bitmap"
    }
}

/**
 * detail: 解码结果回调
 * @author Ttt
 */
interface DecodeResult {
    /**
     * 处理解码 ( 解码成功 )
     * @param result 识别数据 [Result]
     * @param bundle [Bundle]
     */
    fun handleDecode(
        result: Result?,
        bundle: Bundle?
    )
}

/**
 * detail: 解码类型
 * @author Ttt
 */
object DecodeFormat {

    // 1D 解码
    private val PRODUCT_FORMATS: Set<BarcodeFormat>
    private val INDUSTRIAL_FORMATS: Set<BarcodeFormat>
    private val ONE_D_FORMATS: MutableSet<BarcodeFormat>

    // 二维码解码
    private val QR_CODE_FORMATS: Set<BarcodeFormat>

    init {
        // 1D 解码类型
        PRODUCT_FORMATS = EnumSet.of(
            BarcodeFormat.UPC_A, BarcodeFormat.UPC_E, BarcodeFormat.EAN_13,
            BarcodeFormat.EAN_8, BarcodeFormat.RSS_14, BarcodeFormat.RSS_EXPANDED
        )
        INDUSTRIAL_FORMATS = EnumSet.of(
            BarcodeFormat.CODE_39, BarcodeFormat.CODE_93,
            BarcodeFormat.CODE_128, BarcodeFormat.ITF, BarcodeFormat.CODABAR
        )
        ONE_D_FORMATS = EnumSet.copyOf(PRODUCT_FORMATS)
        ONE_D_FORMATS.addAll(INDUSTRIAL_FORMATS)
        // 二维码解码类型
        QR_CODE_FORMATS = EnumSet.of(BarcodeFormat.QR_CODE)
    }

    /**
     * 获取二维码解码类型集合
     * @return 二维码解码类型集合
     */
    @JvmStatic
    val qrCodeFormats: Collection<BarcodeFormat>
        get() = QR_CODE_FORMATS

    /**
     * 获取 1D 解码类型集合
     * @return 1D 解码类型集合
     */
    @JvmStatic
    val barCodeFormats: Collection<BarcodeFormat>
        get() = ONE_D_FORMATS

    // 1D 条形码
    const val BARCODE = 0X100

    // 二维码
    const val QRCODE = 0X200

    // 全部识别
    const val ALL = 0X300

    @IntDef(BARCODE, QRCODE, ALL)
    @Retention(RetentionPolicy.SOURCE)
    annotation class DecodeMode
}