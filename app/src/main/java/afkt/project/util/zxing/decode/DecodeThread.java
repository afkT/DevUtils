package afkt.project.util.zxing.decode;

import android.os.Looper;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * detail: 解码线程
 * @author Ttt
 */
public class DecodeThread
        extends Thread {

    // 解码参数
    private final Map<DecodeHintType, Object> mHints;
    // 并发线程等待
    private final CountDownLatch              mHandlerInitLatch;
    // 解码处理 Handler ( DecodeHandler )
    private       DecodeHandler               mHandler;
    // 解码配置对象
    private final DecodeConfig                mDecodeConfig;

    /**
     * 构造函数
     * @param decodeConfig 解码配置
     * @param decodeMode   解码类型
     */
    public DecodeThread(
            DecodeConfig decodeConfig,
            @DecodeFormat.DecodeMode int decodeMode
    ) {
        this.mDecodeConfig = decodeConfig;
        this.mHandlerInitLatch = new CountDownLatch(1);
        this.mHints = new EnumMap<>(DecodeHintType.class);

        Collection<BarcodeFormat> decodeFormats = new ArrayList<>();
        decodeFormats.addAll(EnumSet.of(BarcodeFormat.AZTEC));
        decodeFormats.addAll(EnumSet.of(BarcodeFormat.PDF_417));
        // 判断解码类型
        switch (decodeMode) {
            case DecodeFormat.BARCODE:
                decodeFormats.addAll(DecodeFormat.getBarCodeFormats());
                break;
            case DecodeFormat.QRCODE:
                decodeFormats.addAll(DecodeFormat.getQrCodeFormats());
                break;
            case DecodeFormat.ALL:
                decodeFormats.addAll(DecodeFormat.getBarCodeFormats());
                decodeFormats.addAll(DecodeFormat.getQrCodeFormats());
                break;
            default:
                break;
        }
        // 保存解码类型
        mHints.put(DecodeHintType.POSSIBLE_FORMATS, decodeFormats);
    }

    /**
     * 获取解码 Handler
     * @return {@link DecodeHandler}
     */
    public DecodeHandler getHandler() {
        try {
            mHandlerInitLatch.await();
        } catch (InterruptedException ie) {
        }
        return mHandler;
    }

    @Override
    public void run() {
        Looper.prepare();
        mHandler = new DecodeHandler(mDecodeConfig, mHints);
        mHandlerInitLatch.countDown();
        Looper.loop();
    }
}