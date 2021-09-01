package afkt.project.util

import android.graphics.Bitmap
import dev.DevUtils
import dev.engine.log.DevLogEngine
import dev.utils.common.CloseUtils
import jp.co.cyberagent.android.gpuimage.GPUImage
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageToneCurveFilter
import java.io.InputStream

/**
 * detail: GPU 滤镜工具类
 * @author Ttt
 */
object GPUFilterUtils {

    // 日志 TAG
    private val TAG = GPUFilterUtils::class.java.simpleName

    /**
     * 获取 GPU Image 滤镜配置对象
     * @param inputStream [InputStream]
     * @return [GPUImageToneCurveFilter]
     */
    @JvmStatic
    fun getGPUImageToneCurveFilter(inputStream: InputStream?): GPUImageToneCurveFilter? {
        if (inputStream != null) {
            try {
                // 读取 PhotoShop acv 文件
                val filter = GPUImageToneCurveFilter()
                filter.setFromCurveFileInputStream(inputStream)
                return filter
            } catch (e: Exception) {
                DevLogEngine.getEngine()?.eTag(TAG, e, "getGPUImageToneCurveFilter")
            } finally {
                CloseUtils.closeIOQuietly(inputStream)
            }
        }
        return null
    }

    /**
     * 获取滤镜后的 Bitmap
     * @param gpuImage       [GPUImage]
     * @param gpuImageFilter [GPUImageFilter]
     * @return 滤镜后的 Bitmap
     */
    @JvmStatic
    fun getFilterBitmap(
        gpuImage: GPUImage?,
        gpuImageFilter: GPUImageFilter?
    ): Bitmap? {
        if (gpuImage != null && gpuImageFilter != null) {
            gpuImage.setFilter(gpuImageFilter)
            return gpuImage.bitmapWithFilterApplied
        }
        return null
    }

    /**
     * 获取滤镜后的 Bitmap
     * @param bitmap         [Bitmap]
     * @param gpuImageFilter [GPUImageFilter]
     * @return 滤镜后的 Bitmap
     */
    @JvmStatic
    fun getFilterBitmap(
        bitmap: Bitmap?,
        gpuImageFilter: GPUImageFilter?
    ): Bitmap? {
        if (bitmap != null && gpuImageFilter != null) {
            val gpuImage = GPUImage(DevUtils.getContext())
            gpuImage.setImage(bitmap)
            gpuImage.setFilter(gpuImageFilter)
            return gpuImage.bitmapWithFilterApplied
        }
        return null
    }

    /**
     * 获取滤镜后的 Bitmap
     * @param gpuImage       [GPUImage]
     * @param bitmap         [Bitmap]
     * @param gpuImageFilter [GPUImageFilter]
     * @return 滤镜后的 Bitmap
     */
    @JvmStatic
    fun getFilterBitmap(
        gpuImage: GPUImage?,
        bitmap: Bitmap?,
        gpuImageFilter: GPUImageFilter?
    ): Bitmap? {
        if (gpuImage != null && bitmap != null && gpuImageFilter != null) {
            gpuImage.setImage(bitmap)
            gpuImage.setFilter(gpuImageFilter)
            return gpuImage.bitmapWithFilterApplied
        }
        return null
    }
}