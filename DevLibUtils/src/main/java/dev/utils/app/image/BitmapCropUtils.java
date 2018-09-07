package dev.utils.app.image;

import android.graphics.Bitmap;
import android.text.TextUtils;

import dev.utils.LogPrintUtils;

/**
 * detail: 图片裁剪工具类
 * Created by Ttt
 */
public final class BitmapCropUtils {

    private BitmapCropUtils(){
    }

    // 日志Tag
    private static final String TAG = BitmapCropUtils.class.getSimpleName();

    // =============== 裁剪图片,裁剪中间部分，防止全图压缩 ===============
    /**
     * 裁剪图片(默认比例16:9)
     * @param fPath 保存路径
     * @param bitmap 图片
     */
    public static void cropBitmap(String fPath, Bitmap bitmap){
        cropBitmap(fPath, bitmap, 16.0f, 9.0f);
    }

    /**
     * 裁剪图片
     * @param fPath 保存路径
     * @param bitmap 图片
     * @param wScale 宽度比例
     * @param hScale 高度比例
     */
    public static void cropBitmap(String fPath, Bitmap bitmap, float wScale, float hScale){
        if(TextUtils.isEmpty(fPath)){
            return; // 防止保存路径为null
        } else if (bitmap == null){
            return; // 防止图片为null
        }
        // 裁剪处理后的图片
        Bitmap cBitmap = null;
        try {
            // 获取图片宽度
            int iWidth = bitmap.getWidth();
            // 获取图片高度
            int iHeight = bitmap.getHeight();
            // 获取需要裁剪的高度
            int rHeight = (int) ((iWidth * hScale) / wScale);
            // 判断需要裁剪的高度与偏移差距
            int dHeight = iHeight - rHeight;
            // --
            // 判断裁剪方式
            if (dHeight >= 0){ // 属于宽度 * 对应比例 >= 高度  -> 以高度做偏移
                // 计算偏移的y轴
                int offsetY = dHeight / 2;
                // 创建图片
                cBitmap = Bitmap.createBitmap(bitmap, 0, offsetY, iWidth, rHeight, null, false);
            } else { // 以宽度做偏移
                // 获取需要裁剪的宽度
                int rWidth = (int) ((iHeight * wScale) / hScale);
                // 判断需要裁剪的宽度与偏移差距
                int dWidth = iWidth - rWidth;
                // 计算偏移的X轴
                int offsetX = dWidth / 2;
                // 创建图片
                cBitmap = Bitmap.createBitmap(bitmap, offsetX, 0, rWidth, iHeight, null, false);
            }
            if (cBitmap != null){
                // 保存图片
                BitmapUtils.saveBitmapToSDCardPNG(cBitmap, fPath, 85);
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "cropBitmap");
        } finally {
            // -- 清空资源 --
            try {
                if(cBitmap != null && !cBitmap.isRecycled()){
                    cBitmap.recycle();
                }
            } catch (Exception e) {
            }
            cBitmap = null;
        }
    }
}
