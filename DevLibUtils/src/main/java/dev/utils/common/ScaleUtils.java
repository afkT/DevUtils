package dev.utils.common;

import dev.utils.JCLogUtils;

/**
 * detail: 计算比例方法
 * Created by Ttt
 */
public final class ScaleUtils {

    private ScaleUtils() {
    }

    // 日志 TAG
    private static final String TAG = ScaleUtils.class.getSimpleName();

    /**
     * 计算缩放比例 - 根据宽度比例转换高度
     * @param width 需要的最终宽度
     * @param cWidth 当前宽度
     * @param cHeight 当前高度
     * @return [0] = 宽度, [1] = 高度
     */
    public static int[] calcScaleToWidth(int width, int cWidth, int cHeight) {
        try {
            if (cWidth == 0) {
                return new int[] { 0, 0 };
            }
            // 计算比例
            float scale = ((float) width) / ((float) cWidth);
            // 计算缩放后的高度
            int sHeight = (int) (scale * (float) cHeight);
            // 返回对应的数据
            return new int[] { width, sHeight };
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "calcScaleToWidth");
        }
        return null;
    }

    /**
     * 计算缩放比例 - 根据高度比例转换宽度
     * @param height 需要的最终高度
     * @param cWidth 当前宽度
     * @param cHeight 当前高度
     * @return [0] = 宽度, [1] = 高度
     */
    public static int[] calcScaleToHeight(int height, int cWidth, int cHeight) {
        try {
            if (cHeight == 0) {
                return new int[] { 0, 0 };
            }
            // 计算比例
            float scale = ((float) height) / ((float) cHeight);
            // 计算缩放后的宽度
            int sWidth = (int) (scale * (float) cWidth);
            // 返回对应的数据
            return new int[] { sWidth, height };
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "calcScaleToHeight");
        }
        return null;
    }

    /**
     * 通过宽度,高度,根据对应的比例 -> 转换成对应的比例宽度高度 - 智能转换
     * @param width
     * @param height
     * @param wScale
     * @param hScale
     * @return
     */
    public static int[] calcWidthHeightToScale(int width, int height, float wScale, float hScale) {
        try {
            // 如果宽度的比例,大于等于高度比例
            if (wScale >= hScale) { // 以宽度为基准
                // 设置宽度 -> 以宽度为基准
                int sWidth = width;
                // 计算宽度
                int sHeight = (int) (((float) sWidth) * (hScale / wScale));
                // 返回对应的比例
                return new int[] { sWidth , sHeight };
            } else { // 以高度为基准
                // 设置高度
                int sHeight = height;
                // 同步缩放比例
                int sWidth = (int) (((float) sHeight) * (wScale / hScale));
                // 返回对应的比例
                return new int[] { sWidth , sHeight };
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "calcWidthHeightToScale");
        }
        return null;
    }

    /**
     * 以宽度为基准 -> 转换对应比例的高度
     * @param width
     * @param wScale
     * @param hScale
     * @return
     */
    public static int[] calcWidthToScale(int width, float wScale, float hScale) {
        try {
            // 设置宽度
            int sWidth = width;
            // 计算高度
            int sHeight = (int) (((float) sWidth) * (hScale / wScale));
            // 返回对应的比例
            return new int[] { sWidth , sHeight };
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "calcWidthToScale");
        }
        return null;
    }

    /**
     * 以高度为基准 -> 转换对应比例的宽度
     * @param height
     * @param wScale
     * @param hScale
     * @return
     */
    public static int[] calcHeightToScale(int height, float wScale, float hScale) {
        try {
            // 设置高度
            int sHeight = height;
            // 计算宽度
            int sWidth = (int) (((float) sHeight) * (wScale / hScale));
            // 返回对应的比例
            return new int[] { sWidth , sHeight };
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "calcHeightToScale");
        }
        return null;
    }
}
