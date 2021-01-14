package afkt.project.util;

import android.graphics.Bitmap;

import java.io.InputStream;

import dev.DevUtils;
import dev.engine.log.DevLogEngine;
import dev.utils.common.CloseUtils;
import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageToneCurveFilter;

/**
 * detail: GPU 滤镜工具类
 * @author Ttt
 */
public final class GPUFilterUtils {

    private GPUFilterUtils() {
    }

    // 日志 TAG
    private static final String TAG = GPUFilterUtils.class.getSimpleName();

    /**
     * 获取 GPU Image 滤镜配置对象
     * @param inputStream {@link InputStream}
     * @return {@link GPUImageToneCurveFilter}
     */
    public static GPUImageToneCurveFilter getGPUImageToneCurveFilter(InputStream inputStream) {
        if (inputStream != null) {
            try {
                // 读取 Photoshop acv 文件
                GPUImageToneCurveFilter filter = new GPUImageToneCurveFilter();
                filter.setFromCurveFileInputStream(inputStream);
                return filter;
            } catch (Exception e) {
                DevLogEngine.getEngine().eTag(TAG, e, "getGPUImageToneCurveFilter");
            } finally {
                CloseUtils.closeIOQuietly(inputStream);
            }
        }
        return null;
    }

    /**
     * 获取滤镜后的 Bitmap
     * @param gpuImage       {@link GPUImage}
     * @param gpuImageFilter {@link GPUImageFilter}
     * @return 滤镜后的 Bitmap
     */
    public static Bitmap getFilterBitmap(
            GPUImage gpuImage,
            GPUImageFilter gpuImageFilter
    ) {
        if (gpuImage != null && gpuImageFilter != null) {
            gpuImage.setFilter(gpuImageFilter);
            return gpuImage.getBitmapWithFilterApplied();
        }
        return null;
    }

    /**
     * 获取滤镜后的 Bitmap
     * @param bitmap         {@link Bitmap}
     * @param gpuImageFilter {@link GPUImageFilter}
     * @return 滤镜后的 Bitmap
     */
    public static Bitmap getFilterBitmap(
            Bitmap bitmap,
            GPUImageFilter gpuImageFilter
    ) {
        if (bitmap != null && gpuImageFilter != null) {
            GPUImage gpuImage = new GPUImage(DevUtils.getContext());
            gpuImage.setImage(bitmap);
            gpuImage.setFilter(gpuImageFilter);
            return gpuImage.getBitmapWithFilterApplied();
        }
        return null;
    }

    /**
     * 获取滤镜后的 Bitmap
     * @param gpuImage       {@link GPUImage}
     * @param bitmap         {@link Bitmap}
     * @param gpuImageFilter {@link GPUImageFilter}
     * @return 滤镜后的 Bitmap
     */
    public static Bitmap getFilterBitmap(
            GPUImage gpuImage,
            Bitmap bitmap,
            GPUImageFilter gpuImageFilter
    ) {
        if (gpuImage != null && bitmap != null && gpuImageFilter != null) {
            gpuImage.setImage(bitmap);
            gpuImage.setFilter(gpuImageFilter);
            return gpuImage.getBitmapWithFilterApplied();
        }
        return null;
    }
}