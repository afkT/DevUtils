package dev.utils.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dev.utils.JCLogUtils;

/**
 * detail: 计算比例工具类
 * @author Ttt
 */
public final class ScaleUtils {

    private ScaleUtils() {
    }

    // 日志 TAG
    private static final String TAG = ScaleUtils.class.getSimpleName();

    /**
     * 计算比例 ( 商 )
     * @param dividend 被除数
     * @param divisor  除数
     * @return 商
     */
    public static double calcScale(
            final double dividend,
            final double divisor
    ) {
        try {
            return dividend / divisor;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "calcScale");
        }
        return -1d;
    }

    /**
     * 计算比例 ( 被除数 ( 最大值 ) / 除数 ( 最小值 ) )
     * @param value1 第一个值
     * @param value2 第二个值
     * @return 被除数 ( 最大值 ) / 除数 ( 最小值 ) = 商
     */
    public static double calcScaleToMath(
            final double value1,
            final double value2
    ) {
        try {
            return Math.max(value1, value2) / Math.min(value1, value2);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "calcScaleToMath");
        }
        return -1d;
    }

    // ==========
    // = double =
    // ==========

    /**
     * 计算缩放比例 ( 根据宽度比例转换高度 )
     * @param targetWidth   需要的最终宽度
     * @param currentWidth  当前宽度
     * @param currentHeight 当前高度
     * @return double[] { 宽度, 高度 }
     */
    public static double[] calcScaleToWidth(
            final double targetWidth,
            final double currentWidth,
            final double currentHeight
    ) {
        try {
            if (currentWidth == 0d) {
                return new double[]{0d, 0d};
            }
            // 计算比例
            double scale = targetWidth / currentWidth;
            // 计算缩放后的高度
            double scaleHeight = scale * currentHeight;
            // 返回对应的数据
            return new double[]{targetWidth, scaleHeight};
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "calcScaleToWidth");
        }
        return null;
    }

    /**
     * 计算缩放比例 ( 根据高度比例转换宽度 )
     * @param targetHeight  需要的最终高度
     * @param currentWidth  当前宽度
     * @param currentHeight 当前高度
     * @return double[] { 宽度, 高度 }
     */
    public static double[] calcScaleToHeight(
            final double targetHeight,
            final double currentWidth,
            final double currentHeight
    ) {
        try {
            if (currentHeight == 0d) {
                return new double[]{0d, 0d};
            }
            // 计算比例
            double scale = targetHeight / currentHeight;
            // 计算缩放后的宽度
            double scaleWidth = scale * currentWidth;
            // 返回对应的数据
            return new double[]{scaleWidth, targetHeight};
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "calcScaleToHeight");
        }
        return null;
    }

    /**
     * 通过宽度、高度根据对应的比例, 转换成对应的比例宽度高度 ( 智能转换 )
     * @param width       宽度
     * @param height      高度
     * @param widthScale  宽度比例
     * @param heightScale 高度比例
     * @return double[] { 宽度, 高度 }
     */
    public static double[] calcWidthHeightToScale(
            final double width,
            final double height,
            final double widthScale,
            final double heightScale
    ) {
        try {
            // 如果宽度的比例, 大于等于高度比例
            if (widthScale >= heightScale) { // 以宽度为基准
                // 设置宽度, 以宽度为基准
                double scaleWidth = width;
                // 计算宽度
                double scaleHeight = scaleWidth * (heightScale / widthScale);
                // 返回对应的比例
                return new double[]{scaleWidth, scaleHeight};
            } else { // 以高度为基准
                // 设置高度
                double scaleHeight = height;
                // 同步缩放比例
                double scaleWidth = scaleHeight * (widthScale / heightScale);
                // 返回对应的比例
                return new double[]{scaleWidth, scaleHeight};
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "calcWidthHeightToScale");
        }
        return null;
    }

    /**
     * 以宽度为基准, 转换对应比例的高度
     * @param width       宽度
     * @param widthScale  宽度比例
     * @param heightScale 高度比例
     * @return double[] { 宽度, 高度 }
     */
    public static double[] calcWidthToScale(
            final double width,
            final double widthScale,
            final double heightScale
    ) {
        try {
            // 设置宽度
            double scaleWidth = width;
            // 计算高度
            double scaleHeight = scaleWidth * (heightScale / widthScale);
            // 返回对应的比例
            return new double[]{scaleWidth, scaleHeight};
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "calcWidthToScale");
        }
        return null;
    }

    /**
     * 以高度为基准, 转换对应比例的宽度
     * @param height      高度
     * @param widthScale  宽度比例
     * @param heightScale 高度比例
     * @return double[] { 宽度, 高度 }
     */
    public static double[] calcHeightToScale(
            final double height,
            final double widthScale,
            final double heightScale
    ) {
        try {
            // 设置高度
            double scaleHeight = height;
            // 计算宽度
            double scaleWidth = scaleHeight * (widthScale / heightScale);
            // 返回对应的比例
            return new double[]{scaleWidth, scaleHeight};
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "calcHeightToScale");
        }
        return null;
    }

    // =======
    // = int =
    // =======

    /**
     * 计算缩放比例 ( 根据宽度比例转换高度 )
     * @param targetWidth   需要的最终宽度
     * @param currentWidth  当前宽度
     * @param currentHeight 当前高度
     * @return int[] { 宽度, 高度 }
     */
    public static int[] calcScaleToWidthI(
            final double targetWidth,
            final double currentWidth,
            final double currentHeight
    ) {
        double[] result = calcScaleToWidth(
                targetWidth, currentWidth, currentHeight
        );
        if (result != null) {
            return new int[]{
                    Double.valueOf(result[0]).intValue(),
                    Double.valueOf(result[1]).intValue()
            };
        }
        return null;
    }

    /**
     * 计算缩放比例 ( 根据高度比例转换宽度 )
     * @param targetHeight  需要的最终高度
     * @param currentWidth  当前宽度
     * @param currentHeight 当前高度
     * @return int[] { 宽度, 高度 }
     */
    public static int[] calcScaleToHeightI(
            final double targetHeight,
            final double currentWidth,
            final double currentHeight
    ) {
        double[] result = calcScaleToHeight(
                targetHeight, currentWidth, currentHeight
        );
        if (result != null) {
            return new int[]{
                    Double.valueOf(result[0]).intValue(),
                    Double.valueOf(result[1]).intValue()
            };
        }
        return null;
    }

    /**
     * 通过宽度、高度根据对应的比例, 转换成对应的比例宽度高度 ( 智能转换 )
     * @param width       宽度
     * @param height      高度
     * @param widthScale  宽度比例
     * @param heightScale 高度比例
     * @return int[] { 宽度, 高度 }
     */
    public static int[] calcWidthHeightToScaleI(
            final double width,
            final double height,
            final double widthScale,
            final double heightScale
    ) {
        double[] result = calcWidthHeightToScale(
                width, height, widthScale, heightScale
        );
        if (result != null) {
            return new int[]{
                    Double.valueOf(result[0]).intValue(),
                    Double.valueOf(result[1]).intValue()
            };
        }
        return null;
    }

    /**
     * 以宽度为基准, 转换对应比例的高度
     * @param width       宽度
     * @param widthScale  宽度比例
     * @param heightScale 高度比例
     * @return int[] { 宽度, 高度 }
     */
    public static int[] calcWidthToScaleI(
            final double width,
            final double widthScale,
            final double heightScale
    ) {
        double[] result = calcWidthToScale(
                width, widthScale, heightScale
        );
        if (result != null) {
            return new int[]{
                    Double.valueOf(result[0]).intValue(),
                    Double.valueOf(result[1]).intValue()
            };
        }
        return null;
    }

    /**
     * 以高度为基准, 转换对应比例的宽度
     * @param height      高度
     * @param widthScale  宽度比例
     * @param heightScale 高度比例
     * @return int[] { 宽度, 高度 }
     */
    public static int[] calcHeightToScaleI(
            final double height,
            final double widthScale,
            final double heightScale
    ) {
        double[] result = calcHeightToScale(
                height, widthScale, heightScale
        );
        if (result != null) {
            return new int[]{
                    Double.valueOf(result[0]).intValue(),
                    Double.valueOf(result[1]).intValue()
            };
        }
        return null;
    }

    // ========
    // = long =
    // ========

    /**
     * 计算缩放比例 ( 根据宽度比例转换高度 )
     * @param targetWidth   需要的最终宽度
     * @param currentWidth  当前宽度
     * @param currentHeight 当前高度
     * @return long[] { 宽度, 高度 }
     */
    public static long[] calcScaleToWidthL(
            final double targetWidth,
            final double currentWidth,
            final double currentHeight
    ) {
        double[] result = calcScaleToWidth(
                targetWidth, currentWidth, currentHeight
        );
        if (result != null) {
            return new long[]{
                    Double.valueOf(result[0]).longValue(),
                    Double.valueOf(result[1]).longValue()
            };
        }
        return null;
    }

    /**
     * 计算缩放比例 ( 根据高度比例转换宽度 )
     * @param targetHeight  需要的最终高度
     * @param currentWidth  当前宽度
     * @param currentHeight 当前高度
     * @return long[] { 宽度, 高度 }
     */
    public static long[] calcScaleToHeightL(
            final double targetHeight,
            final double currentWidth,
            final double currentHeight
    ) {
        double[] result = calcScaleToHeight(
                targetHeight, currentWidth, currentHeight
        );
        if (result != null) {
            return new long[]{
                    Double.valueOf(result[0]).longValue(),
                    Double.valueOf(result[1]).longValue()
            };
        }
        return null;
    }

    /**
     * 通过宽度、高度根据对应的比例, 转换成对应的比例宽度高度 ( 智能转换 )
     * @param width       宽度
     * @param height      高度
     * @param widthScale  宽度比例
     * @param heightScale 高度比例
     * @return long[] { 宽度, 高度 }
     */
    public static long[] calcWidthHeightToScaleL(
            final double width,
            final double height,
            final double widthScale,
            final double heightScale
    ) {
        double[] result = calcWidthHeightToScale(
                width, height, widthScale, heightScale
        );
        if (result != null) {
            return new long[]{
                    Double.valueOf(result[0]).longValue(),
                    Double.valueOf(result[1]).longValue()
            };
        }
        return null;
    }

    /**
     * 以宽度为基准, 转换对应比例的高度
     * @param width       宽度
     * @param widthScale  宽度比例
     * @param heightScale 高度比例
     * @return long[] { 宽度, 高度 }
     */
    public static long[] calcWidthToScaleL(
            final double width,
            final double widthScale,
            final double heightScale
    ) {
        double[] result = calcWidthToScale(
                width, widthScale, heightScale
        );
        if (result != null) {
            return new long[]{
                    Double.valueOf(result[0]).longValue(),
                    Double.valueOf(result[1]).longValue()
            };
        }
        return null;
    }

    /**
     * 以高度为基准, 转换对应比例的宽度
     * @param height      高度
     * @param widthScale  宽度比例
     * @param heightScale 高度比例
     * @return long[] { 宽度, 高度 }
     */
    public static long[] calcHeightToScaleL(
            final double height,
            final double widthScale,
            final double heightScale
    ) {
        double[] result = calcHeightToScale(
                height, widthScale, heightScale
        );
        if (result != null) {
            return new long[]{
                    Double.valueOf(result[0]).longValue(),
                    Double.valueOf(result[1]).longValue()
            };
        }
        return null;
    }

    // =========
    // = float =
    // =========

    /**
     * 计算缩放比例 ( 根据宽度比例转换高度 )
     * @param targetWidth   需要的最终宽度
     * @param currentWidth  当前宽度
     * @param currentHeight 当前高度
     * @return float[] { 宽度, 高度 }
     */
    public static float[] calcScaleToWidthF(
            final double targetWidth,
            final double currentWidth,
            final double currentHeight
    ) {
        double[] result = calcScaleToWidth(
                targetWidth, currentWidth, currentHeight
        );
        if (result != null) {
            return new float[]{
                    Double.valueOf(result[0]).floatValue(),
                    Double.valueOf(result[1]).floatValue()
            };
        }
        return null;
    }

    /**
     * 计算缩放比例 ( 根据高度比例转换宽度 )
     * @param targetHeight  需要的最终高度
     * @param currentWidth  当前宽度
     * @param currentHeight 当前高度
     * @return float[] { 宽度, 高度 }
     */
    public static float[] calcScaleToHeightF(
            final double targetHeight,
            final double currentWidth,
            final double currentHeight
    ) {
        double[] result = calcScaleToHeight(
                targetHeight, currentWidth, currentHeight
        );
        if (result != null) {
            return new float[]{
                    Double.valueOf(result[0]).floatValue(),
                    Double.valueOf(result[1]).floatValue()
            };
        }
        return null;
    }

    /**
     * 通过宽度、高度根据对应的比例, 转换成对应的比例宽度高度 ( 智能转换 )
     * @param width       宽度
     * @param height      高度
     * @param widthScale  宽度比例
     * @param heightScale 高度比例
     * @return float[] { 宽度, 高度 }
     */
    public static float[] calcWidthHeightToScaleF(
            final double width,
            final double height,
            final double widthScale,
            final double heightScale
    ) {
        double[] result = calcWidthHeightToScale(
                width, height, widthScale, heightScale
        );
        if (result != null) {
            return new float[]{
                    Double.valueOf(result[0]).floatValue(),
                    Double.valueOf(result[1]).floatValue()
            };
        }
        return null;
    }

    /**
     * 以宽度为基准, 转换对应比例的高度
     * @param width       宽度
     * @param widthScale  宽度比例
     * @param heightScale 高度比例
     * @return float[] { 宽度, 高度 }
     */
    public static float[] calcWidthToScaleF(
            final double width,
            final double widthScale,
            final double heightScale
    ) {
        double[] result = calcWidthToScale(
                width, widthScale, heightScale
        );
        if (result != null) {
            return new float[]{
                    Double.valueOf(result[0]).floatValue(),
                    Double.valueOf(result[1]).floatValue()
            };
        }
        return null;
    }

    /**
     * 以高度为基准, 转换对应比例的宽度
     * @param height      高度
     * @param widthScale  宽度比例
     * @param heightScale 高度比例
     * @return float[] { 宽度, 高度 }
     */
    public static float[] calcHeightToScaleF(
            final double height,
            final double widthScale,
            final double heightScale
    ) {
        double[] result = calcHeightToScale(
                height, widthScale, heightScale
        );
        if (result != null) {
            return new float[]{
                    Double.valueOf(result[0]).floatValue(),
                    Double.valueOf(result[1]).floatValue()
            };
        }
        return null;
    }

    // ========
    // = XY 比 =
    // ========

    /**
     * 计算 XY 比
     * @param x X 值
     * @param y Y 值
     * @return XY 比实体类
     */
    public static XY calcXY(
            final int x,
            final int y
    ) {
        return calcXY(XY_LIST, x, y);
    }

    /**
     * 计算 XY 比
     * @param xyLists XY 比集合
     * @param x       X 值
     * @param y       Y 值
     * @return XY 比实体类
     */
    public static XY calcXY(
            final List<XY> xyLists,
            final int x,
            final int y
    ) {
        if (xyLists != null && xyLists.size() != 0) {
            List<XY> lists = new ArrayList<>(xyLists);
            Collections.sort(lists);
            double scale = calcScale(x, y);
            for (int i = 0, len = lists.size(); i < len; i++) {
                XY xy = lists.get(i);
                if (scale >= xy.scale) return xy;
            }
        }
        return null;
    }

    // ========
    // = 实体类 =
    // ========

    public static final List<XY> XY_LIST;

    static {
        List<XY> xys = new ArrayList<>();
        xys.add(new XY(16, 9));
        xys.add(new XY(17, 10));
        xys.add(new XY(15, 9));
        xys.add(new XY(16, 10));
        xys.add(new XY(3, 2));
        xys.add(new XY(4, 3));
        xys.add(new XY(5, 4));
        xys.add(new XY(1, 1));
        XY_LIST = Collections.unmodifiableList(xys);
    }

    /**
     * detail: XY 比实体类
     * @author Ttt
     */
    public static class XY
            implements Comparable<XY> {

        public XY(
                final int x,
                final int y
        ) {
            this(x, y, 0);
        }

        public XY(
                final int x,
                final int y,
                final int type
        ) {
            this.x     = x;
            this.y     = y;
            this.scale = calcScale(x, y);
            this.type  = type;
        }

        public final int    x;
        public final int    y;
        public final double scale;
        public final int    type;

        public String getXYx() {
            return getXY("x");
        }

        public String getXY() {
            return getXY(":");
        }

        public String getXY(String joint) {
            return x + joint + y;
        }

        @Override
        public int compareTo(XY xy) {
            return Double.compare(xy.scale, this.scale);
        }
    }
}