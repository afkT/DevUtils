package afkt.project.util.zxing.decode;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import afkt.project.R;
import dev.engine.log.DevLogEngine;

/**
 * detail: 解码处理
 * @author Ttt
 */
public class DecodeHandler
        extends Handler {

    // 日志 TAG
    private final String            TAG      = DecodeHandler.class.getSimpleName();
    // 是否运行中
    private       boolean           mRunning = true;
    // 读取图像数据对象
    private final MultiFormatReader mMultiFormatReader;
    // 解码配置对象
    private final DecodeConfig      mDecodeConfig;

    /**
     * 构造函数
     * @param decodeConfig 解码配置
     * @param hints        解码参数
     */
    public DecodeHandler(
            DecodeConfig decodeConfig,
            Map<DecodeHintType, Object> hints
    ) {
        this.mDecodeConfig = decodeConfig;
        this.mMultiFormatReader = new MultiFormatReader();
        this.mMultiFormatReader.setHints(hints);
    }

    @Override
    public void handleMessage(Message message) {
        // 如果非运行中, 则不处理
        if (!mRunning) return;
        // 判断类型
        if (message.what == R.id.decode) {
            // 解码图像数据
            decode((byte[]) message.obj, message.arg1, message.arg2);
        } else if (message.what == R.id.quit) {
            mRunning = false;
            Looper.myLooper().quit();
        }
    }

    /**
     * 处理缩略图
     * @param source {@link PlanarYUVLuminanceSource}
     * @param bundle 存储数据 {@link Bundle}
     */
    private static void bundleThumbnail(
            PlanarYUVLuminanceSource source,
            Bundle bundle
    ) {
        int[]                 pixels = source.renderThumbnail();
        int                   width  = source.getThumbnailWidth();
        int                   height = source.getThumbnailHeight();
        Bitmap                bitmap = Bitmap.createBitmap(pixels, 0, width, width, height, Bitmap.Config.ARGB_8888);
        ByteArrayOutputStream out    = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, out);
        bundle.putByteArray(DecodeConfig.BARCODE_BITMAP, out.toByteArray());
    }

    /**
     * Decode the data within the viewfinder rectangle, and time how long it
     * took. For efficiency, reuse the same reader objects from one decode to the next.
     * <pre>
     *     对取景器矩形内的数据进行解码, 并计算其占用的时间, 为了效率从一个解码到下一个重复使用相同的读取器对象
     * </pre>
     * @param data   The YUV preview frame.
     * @param width  The width of the preview frame.
     * @param height The height of the preview frame.
     */
    private void decode(
            byte[] data,
            int width,
            int height
    ) {
        if (mDecodeConfig == null || mDecodeConfig.getHandler() == null) {
            return;
        }
        // 获取 Camera 预览大小
        Size size = mDecodeConfig.getPreviewSize();
        // 防止为 null
        if (size == null) return;
        // 这里需要将获取的 data 翻转一下, 因为相机默认拿的的横屏的数据
        byte[] rotatedData = new byte[data.length];
        for (int y = 0; y < size.height; y++) {
            for (int x = 0; x < size.width; x++) {
                rotatedData[x * size.height + size.height - y - 1] = data[x + y * size.width];
            }
        }
        // 宽高也要调整
        int tmp = size.width;
        size.width = size.height;
        size.height = tmp;

        // 处理数据
        Result                   rawResult = null;
        PlanarYUVLuminanceSource source    = buildLuminanceSource(rotatedData, size.width, size.height);
        if (source != null) {
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            try {
                rawResult = mMultiFormatReader.decodeWithState(bitmap);
            } catch (ReaderException re) {
                // continue
            } finally {
                mMultiFormatReader.reset();
            }
        }

        Handler handler = mDecodeConfig.getHandler();
        if (rawResult != null) {
            DevLogEngine.getEngine().dTag(TAG, "解析成功, 发送数据");
            // Don't log the barcode contents for security.
            if (handler != null) {
                Message message = Message.obtain(handler, R.id.decode_succeeded, rawResult);
                Bundle  bundle  = new Bundle();
                bundleThumbnail(source, bundle);
                message.setData(bundle);
                message.sendToTarget();
            }
        } else {
            DevLogEngine.getEngine().dTag(TAG, "解析失败");
            if (handler != null) {
                Message message = Message.obtain(handler, R.id.decode_failed);
                message.sendToTarget();
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
    public PlanarYUVLuminanceSource buildLuminanceSource(
            byte[] data,
            int width,
            int height
    ) {
        if (mDecodeConfig == null) {
            return null;
        }
        DevLogEngine.getEngine().dTag(TAG, "buildLuminanceSource 解析摄像头数据");
        // 判断是否裁减
        if (mDecodeConfig.isCropRect() && mDecodeConfig.getCropRect() != null) {
            // 判断是否出现异常
            if (mDecodeConfig.isError()) {
                return new PlanarYUVLuminanceSource(data, width, height, 0, 0, width, height, false);
            } else {
                try {
                    // 获取裁减识别区域
                    Rect rect = mDecodeConfig.getCropRect();
                    // Go ahead and assume it's YUV rather than die.
                    return new PlanarYUVLuminanceSource(data, width, height, rect.left, rect.top, rect.width(), rect.height(), false);
                } catch (Exception e) { // 出现异常时, 使用全屏
                    // 设置异常
                    mDecodeConfig.setError(true, e);
                    // 全屏处理
                    return new PlanarYUVLuminanceSource(data, width, height, 0, 0, width, height, false);
                }
            }
        } else {
            // Go ahead and assume it's YUV rather than die.
            return new PlanarYUVLuminanceSource(data, width, height, 0, 0, width, height, false);
        }
    }
}