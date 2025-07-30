package afkt.project.features.ui_effect.qrcode

import android.graphics.Bitmap
import android.graphics.Rect
import android.hardware.Camera
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.annotation.IntDef
import com.google.zxing.*
import com.google.zxing.common.HybridBinarizer
import dev.expand.engine.log.log_dTag
import java.io.ByteArrayOutputStream
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.util.*
import java.util.concurrent.CountDownLatch

const val WHAT_QUIT = 100
const val WHAT_DECODE = 101
const val WHAT_DECODE_FAILED = 102
const val WHAT_DECODE_SUCCEEDED = 103
const val WHAT_RESTART_PREVIEW = 104

/**
 * detail: 解码配置
 * @author Ttt
 */
interface DecodeConfig {

    // 获取处理 Handler
    fun getHandler(): Handler?

    // 是否裁减
    fun isCropRect(): Boolean

    // 裁减区域
    fun getCropRect(): Rect?

    // 获取预览大小
    fun getPreviewSize(): Camera.Size?

    // 判断是否出现异常
    fun isError(): Boolean

    /**
     * 设置异常
     * @param isError 是否异常
     * @param error   异常信息
     */
    fun setError(
        isError: Boolean,
        error: Throwable?
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

    // 1D 解码类型
    private val PRODUCT_FORMATS = EnumSet.of(
        BarcodeFormat.UPC_A, BarcodeFormat.UPC_E, BarcodeFormat.EAN_13,
        BarcodeFormat.EAN_8, BarcodeFormat.RSS_14, BarcodeFormat.RSS_EXPANDED
    )
    private val INDUSTRIAL_FORMATS = EnumSet.of(
        BarcodeFormat.CODE_39, BarcodeFormat.CODE_93,
        BarcodeFormat.CODE_128, BarcodeFormat.ITF, BarcodeFormat.CODABAR
    )
    private val ONE_D_FORMATS = EnumSet.copyOf(PRODUCT_FORMATS).apply {
        addAll(INDUSTRIAL_FORMATS)
    }

    // 二维码解码类型
    private val QR_CODE_FORMATS = EnumSet.of(BarcodeFormat.QR_CODE)

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

/**
 * detail: 解码线程
 * @author Ttt
 */
class DecodeThread(
    // 解码配置
    private val mDecodeConfig: DecodeConfig,
    // 解码类型
    @DecodeFormat.DecodeMode decodeMode: Int
) : Thread() {

    // 解码参数
    private val mHints: MutableMap<DecodeHintType, Any> = EnumMap(DecodeHintType::class.java)

    // 并发线程等待
    private val mHandlerInitLatch = CountDownLatch(1)

    // 解码处理 Handler ( DecodeHandler )
    private var mHandler: DecodeHandler? = null

    init {
        val decodeFormats = mutableListOf<BarcodeFormat>()
        decodeFormats.addAll(EnumSet.of(BarcodeFormat.AZTEC))
        decodeFormats.addAll(EnumSet.of(BarcodeFormat.PDF_417))
        when (decodeMode) {
            DecodeFormat.BARCODE -> {
                decodeFormats.addAll(DecodeFormat.barCodeFormats)
            }

            DecodeFormat.QRCODE -> {
                decodeFormats.addAll(DecodeFormat.qrCodeFormats)
            }

            DecodeFormat.ALL -> {
                decodeFormats.addAll(DecodeFormat.barCodeFormats)
                decodeFormats.addAll(DecodeFormat.qrCodeFormats)
            }
        }
        // 保存解码类型
        mHints[DecodeHintType.POSSIBLE_FORMATS] = decodeFormats
    }

    /**
     * 获取解码 Handler
     * @return [DecodeHandler]
     */
    val handler: DecodeHandler?
        get() {
            try {
                mHandlerInitLatch.await()
            } catch (_: Exception) {
            }
            return mHandler
        }

    override fun run() {
        Looper.prepare()
        mHandler = DecodeHandler(mDecodeConfig, mHints)
        mHandlerInitLatch.countDown()
        Looper.loop()
    }
}

/**
 * detail: 解码处理
 * @author Ttt
 */
class DecodeHandler(
    // 解码配置对象
    private val mDecodeConfig: DecodeConfig,
    // 解码参数
    hints: Map<DecodeHintType, Any>
) : Handler(Looper.getMainLooper()) {

    // 日志 TAG
    private val TAG = DecodeHandler::class.java.simpleName

    // 是否运行中
    private var mRunning = true

    // 读取图像数据对象
    private val mMultiFormatReader = MultiFormatReader()

    init {
        mMultiFormatReader.setHints(hints)
    }

    override fun handleMessage(message: Message) {
        // 如果非运行中, 则不处理
        if (!mRunning) return
        // 判断类型
        if (message.what == WHAT_DECODE) {
            // 解码图像数据
            decode(message.obj as ByteArray, message.arg1, message.arg2)
        } else if (message.what == WHAT_QUIT) {
            mRunning = false
            try {
                /**
                 * 因为使用 单 Activity 多 Fragment 显示
                 * 调用该方法会抛出异常, 不允许退出主线程
                 * 多 Activity 则不会影响, 统一进行捕获异常
                 */
                Looper.myLooper()?.quit()
            } catch (_: Exception) {
            }
        }
    }

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * 处理缩略图
     * @param source [PlanarYUVLuminanceSource]
     * @param bundle 存储数据 [Bundle]
     */
    private fun bundleThumbnail(
        source: PlanarYUVLuminanceSource?,
        bundle: Bundle
    ) {
        source?.let { it ->
            val pixels = it.renderThumbnail()
            val width = it.thumbnailWidth
            val height = it.thumbnailHeight
            val bitmap = Bitmap.createBitmap(
                pixels, 0, width,
                width, height, Bitmap.Config.ARGB_8888
            )
            val out = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, out)
            bundle.putByteArray(DecodeConfig.BARCODE_BITMAP, out.toByteArray())
        }
    }

    /**
     * 对取景器矩形内的数据进行解码, 并计算其占用的时间
     * 为了效率从一个解码到下一个重复使用相同的读取器对象
     * @param data   The YUV preview frame.
     * @param width  The width of the preview frame.
     * @param height The height of the preview frame.
     */
    private fun decode(
        data: ByteArray,
        width: Int,
        height: Int
    ) {
        mDecodeConfig.getHandler() ?: return
        // 获取 Camera 预览大小
        val size = mDecodeConfig.getPreviewSize() ?: return
        // 这里需要将获取的 data 翻转, 因为相机默认拿的的横屏的数据
        val rotatedData = ByteArray(data.size)
        for (y in 0 until size.height) {
            for (x in 0 until size.width) {
                rotatedData[x * size.height + size.height - y - 1] = data[x + y * size.width]
            }
        }
        // 宽高也要调整
        val tmp = size.width
        size.width = size.height
        size.height = tmp

        // 处理数据
        var rawResult: Result? = null
        val source = buildLuminanceSource(
            rotatedData, size.width, size.height
        )
        try {
            val bitmap = BinaryBitmap(HybridBinarizer(source))
            rawResult = mMultiFormatReader.decodeWithState(bitmap)
        } catch (_: Exception) {
            // continue
        } finally {
            mMultiFormatReader.reset()
        }
        val handler = mDecodeConfig.getHandler()
        if (rawResult != null) {
            TAG.log_dTag(message = "解析成功, 发送数据")
            if (handler != null) {
                val message = Message.obtain(
                    handler, WHAT_DECODE_SUCCEEDED, rawResult
                )
                val bundle = Bundle()
                bundleThumbnail(source, bundle)
                message.data = bundle
                message.sendToTarget()
            }
        } else {
            TAG.log_dTag(message = "解析失败")
            if (handler != null) {
                val message = Message.obtain(handler, WHAT_DECODE_FAILED)
                message.sendToTarget()
            }
        }
    }

    /**
     * A factory method to build the appropriate LuminanceSource object based on
     * the format of the preview buffers, as described by Camera.Parameters.
     * @param data   A preview frame.
     * @param width  The width of the image.
     * @param height The height of the image.
     * @return A PlanarYUVLuminanceSource instance.
     */
    private fun buildLuminanceSource(
        data: ByteArray?,
        width: Int,
        height: Int
    ): PlanarYUVLuminanceSource {
        TAG.log_dTag(message = "buildLuminanceSource 解析摄像头数据")
        // 判断是否裁减
        return if (mDecodeConfig.isCropRect() && mDecodeConfig.getCropRect() != null) {
            // 判断是否出现异常
            if (mDecodeConfig.isError()) {
                PlanarYUVLuminanceSource(
                    data, width, height,
                    0, 0, width, height, false
                )
            } else {
                try {
                    // 获取裁减识别区域
                    val rect = mDecodeConfig.getCropRect() as Rect
                    PlanarYUVLuminanceSource(
                        data, width, height,
                        rect.left, rect.top, rect.width(),
                        rect.height(), false
                    )
                } catch (e: Exception) { // 出现异常时, 使用全屏解析, 不解析指定识别区域
                    // 设置异常
                    mDecodeConfig.setError(true, e)
                    // 全屏处理
                    PlanarYUVLuminanceSource(
                        data, width, height,
                        0, 0, width, height, false
                    )
                }
            }
        } else {
            PlanarYUVLuminanceSource(
                data, width, height,
                0, 0, width, height, false
            )
        }
    }
}