package dev.utils.app;

import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import dev.utils.LogPrintUtils;

/**
 * detail: 大小工具类 ( dp, px, sp 转换、View 获取宽高等 )
 * @author Ttt
 */
public final class SizeUtils {

    private SizeUtils() {
    }

    // 日志 TAG
    private static final String TAG = SizeUtils.class.getSimpleName();

    /**
     * dp 转 px
     * @param dpValue 待转换值
     * @return 转换后的值
     */
    public static int dipConvertPx(final float dpValue) {
        return (int) dipConvertPxf(dpValue);
    }

    /**
     * dp 转 px (float)
     * @param dpValue 待转换值
     * @return 转换后的值
     */
    public static float dipConvertPxf(final float dpValue) {
        return applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, ResourceUtils.getDisplayMetrics());
    }

    // =

    /**
     * px 转 dp
     * @param pxValue 待转换值
     * @return 转换后的值
     */
    public static int pxConvertDip(final float pxValue) {
        return (int) pxConvertDipf(pxValue);
    }

    /**
     * px 转 dp (float)
     * @param pxValue 待转换值
     * @return 转换后的值
     */
    public static float pxConvertDipf(final float pxValue) {
        try {
            float scale = ResourceUtils.getDisplayMetrics().density;
            return (pxValue / scale);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "pxConvertDipf");
        }
        return 0f;
    }

    // =

    /**
     * sp 转 px
     * @param spValue 待转换值
     * @return 转换后的值
     */
    public static int spConvertPx(final float spValue) {
        return (int) spConvertPxf(spValue);
    }

    /**
     * sp 转 px (float)
     * @param spValue 待转换值
     * @return 转换后的值
     */
    public static float spConvertPxf(final float spValue) {
        return applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, ResourceUtils.getDisplayMetrics());
    }

    // =

    /**
     * px 转 sp
     * @param pxValue 待转换值
     * @return 转换后的值
     */
    public static int pxConvertSp(final float pxValue) {
        return (int) pxConvertSpf(pxValue);
    }

    /**
     * px 转 sp (float)
     * @param pxValue 待转换值
     * @return 转换后的值
     */
    public static float pxConvertSpf(final float pxValue) {
        try {
            float scale = ResourceUtils.getDisplayMetrics().scaledDensity;
            return (pxValue / scale);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "pxConvertSpf");
        }
        return 0f;
    }

    // =

    /**
     * 各种单位转换 ( 该方法存在于 TypedValue.applyDimension )
     * @param unit  单位
     * @param value 值
     * @return 转换后的值
     */
    public static float applyDimension(
            final int unit,
            final float value
    ) {
        return applyDimension(unit, value, ResourceUtils.getDisplayMetrics());
    }

    /**
     * 各种单位转换 ( 该方法存在于 TypedValue.applyDimension )
     * @param unit    单位
     * @param value   值
     * @param metrics {@link DisplayMetrics}
     * @return 转换后的值
     */
    public static float applyDimension(
            final int unit,
            final float value,
            final DisplayMetrics metrics
    ) {
        if (metrics != null) {
            return TypedValue.applyDimension(unit, value, metrics);
        }
        return 0f;
    }

    // =

    /**
     * 在 onCreate 中获取视图的尺寸 ( 需回调 onGetSizeListener 接口, 在 onGetSize 中获取 View 宽高 )
     * <pre>
     *     用法示例如下所示
     *     <p></p>
     *     SizeUtils.forceGetViewSize(view, new SizeUtils.onGetSizeListener() {
     *          Override
     *          public void onGetSize(final View view) {
     *              view.getWidth();
     *          }
     *     });
     * </pre>
     * @param view     {@link View}
     * @param listener {@link OnGetSizeListener}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean forceGetViewSize(
            final View view,
            final OnGetSizeListener listener
    ) {
        if (view != null) {
            view.post(new Runnable() {
                @Override
                public void run() {
                    if (listener != null) {
                        listener.onGetSize(view);
                    }
                }
            });
            return true;
        }
        return false;
    }

    /**
     * detail: 获取 View 宽高监听
     * @author Ttt
     */
    public interface OnGetSizeListener {

        /**
         * 获取到 View 宽高监听通知
         * @param view {@link View}
         */
        void onGetSize(View view);
    }

    // =============
    // = ViewUtils =
    // =============

    /**
     * 测量 View
     * @param view {@link View}
     * @return int[] 0 = 宽度, 1 = 高度
     */
    public static int[] measureView(final View view) {
        return WidgetUtils.measureView(view);
    }

    /**
     * 获取 View 的宽度
     * @param view {@link View}
     * @return View 的宽度
     */
    public static int getMeasuredWidth(final View view) {
        return WidgetUtils.getMeasuredWidth(view);
    }

    /**
     * 获取 View 的高度
     * @param view {@link View}
     * @return View 的高度
     */
    public static int getMeasuredHeight(final View view) {
        return WidgetUtils.getMeasuredHeight(view);
    }
}