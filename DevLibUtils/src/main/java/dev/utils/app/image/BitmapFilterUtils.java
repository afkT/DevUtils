package dev.utils.app.image;

import android.graphics.Bitmap;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RSRuntimeException;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.IntRange;
import android.support.annotation.RequiresApi;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: Bitmap 滤镜、效果工具类
 * @author Ttt
 * <pre>
 * </pre>
 */
public final class BitmapFilterUtils {

    private BitmapFilterUtils() {
    }

    // 日志 TAG
    private static final String TAG = BitmapFilterUtils.class.getSimpleName();

    /**
     * RenderScript 实现图片模糊
     * @param bitmap 待模糊图片
     * @param radius 模糊度(0-25)
     * @param newBitmap 是否创建新的 Bitmap
     * @return 模糊后的图片
     */
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static Bitmap blur(Bitmap bitmap, @IntRange(from = 0, to = 25) final int radius, final boolean newBitmap){
        try {
            bitmap = copyBitmap(bitmap, newBitmap);
            // 模糊处理
            RenderScript rs = RenderScript.create(DevUtils.getContext());
            Allocation input = Allocation.createFromBitmap(rs, bitmap, Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT);
            Allocation output = Allocation.createTyped(rs, input.getType());
            ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
            blur.setInput(input);
            blur.setRadius(radius);
            blur.forEach(output);
            output.copyTo(bitmap);
            rs.destroy();
            return bitmap;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "blur");
        }
        return null;
    }

    // ======================
    // = 其他工具类实现代码 =
    // ======================

    // ================
    // = ConvertUtils =
    // ================

    /**
     * 复制 Bitmap
     * @param bitmap       {@link Bitmap}
     * @param newBitmap 是否创建新的 Bitmap
     * @return {@link Bitmap}
     */
    private static Bitmap copyBitmap(final Bitmap bitmap, final boolean newBitmap) {
        if (bitmap == null) return null;
        // Bitmap 属于可修改, 且创建新的 Bitmap, 才进行 copy
        if (newBitmap && bitmap.isMutable()) {
            return bitmap.copy(bitmap.getConfig(), true);
        } else {
            return bitmap;
        }
    }
}
