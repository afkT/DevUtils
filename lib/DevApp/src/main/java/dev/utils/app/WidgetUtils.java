package dev.utils.app;

import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

import dev.utils.LogPrintUtils;

/**
 * detail: 控件工具类
 * @author Ttt
 */
public final class WidgetUtils {

    private WidgetUtils() {
    }

    // 日志 TAG
    private static final String TAG = WidgetUtils.class.getSimpleName();

    // 默认值
    public static final int DEF_VALUE = -1;

    /**
     * View Measure
     * @param view              待计算 View
     * @param widthMeasureSpec  horizontal space requirements as imposed by the parent
     * @param heightMeasureSpec vertical space requirements as imposed by the parent
     * @param maximumWidth      maximum Width
     * @param maximumHeight     maximum Height
     * @return measure space Array
     */
    public static int[] viewMeasure(
            final View view,
            final int widthMeasureSpec,
            final int heightMeasureSpec,
            final int maximumWidth,
            final int maximumHeight
    ) {
        return viewMeasure(view, widthMeasureSpec, heightMeasureSpec, maximumWidth, maximumHeight, DEF_VALUE);
    }

    /**
     * View Measure
     * @param view              待计算 View
     * @param widthMeasureSpec  horizontal space requirements as imposed by the parent
     * @param heightMeasureSpec vertical space requirements as imposed by the parent
     * @param maximumWidth      maximum Width
     * @param maximumHeight     maximum Height
     * @param defaultValue      默认值
     * @return measure space Array
     */
    public static int[] viewMeasure(
            final View view,
            final int widthMeasureSpec,
            final int heightMeasureSpec,
            final int maximumWidth,
            final int maximumHeight,
            final int defaultValue
    ) {
        int minimumWidth = 0, minimumHeight = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            minimumWidth = view.getMinimumWidth();
            minimumHeight = view.getMinimumHeight();
        }
        return viewMeasure(widthMeasureSpec, heightMeasureSpec,
                minimumWidth, maximumWidth, minimumHeight, maximumHeight, defaultValue);
    }

    /**
     * View Measure
     * @param widthMeasureSpec  horizontal space requirements as imposed by the parent
     * @param heightMeasureSpec vertical space requirements as imposed by the parent
     * @param minimumWidth      minimum Width
     * @param maximumWidth      maximum Width
     * @param minimumHeight     minimum Height
     * @param maximumHeight     maximum Height
     * @return measure space Array
     */
    public static int[] viewMeasure(
            final int widthMeasureSpec,
            final int heightMeasureSpec,
            final int minimumWidth,
            final int maximumWidth,
            final int minimumHeight,
            final int maximumHeight
    ) {
        return viewMeasure(widthMeasureSpec, heightMeasureSpec,
                minimumWidth, maximumWidth, minimumHeight, maximumHeight, DEF_VALUE);
    }

    /**
     * View Measure
     * @param widthMeasureSpec  horizontal space requirements as imposed by the parent
     * @param heightMeasureSpec vertical space requirements as imposed by the parent
     * @param minimumWidth      minimum Width
     * @param maximumWidth      maximum Width
     * @param minimumHeight     minimum Height
     * @param maximumHeight     maximum Height
     * @param defaultValue      默认值
     * @return measure space Array
     */
    public static int[] viewMeasure(
            final int widthMeasureSpec,
            final int heightMeasureSpec,
            final int minimumWidth,
            final int maximumWidth,
            final int minimumHeight,
            final int maximumHeight,
            final int defaultValue
    ) {
        // 获取原来宽、高 size
        int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);

        widthSize = calculateSize(widthSize, minimumWidth, maximumWidth, defaultValue);
        heightSize = calculateSize(heightSize, minimumHeight, maximumHeight, defaultValue);

        int calcWidthMeasureSpec  = View.MeasureSpec.makeMeasureSpec(widthSize, widthMode);
        int calcHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(heightSize, heightMode);

        return new int[]{calcWidthMeasureSpec, calcHeightMeasureSpec};
    }

    /**
     * 计算大小
     * @param size    待计算值
     * @param minimum 最小值
     * @param maximum 最大值
     * @return 计算后的大小
     */
    public static int calculateSize(
            final int size,
            final int minimum,
            final int maximum
    ) {
        return calculateSize(size, minimum, maximum, DEF_VALUE);
    }

    /**
     * 计算大小
     * @param size         待计算值
     * @param minimum      最小值
     * @param maximum      最大值
     * @param defaultValue 默认值
     * @return 计算后的大小
     */
    public static int calculateSize(
            final int size,
            final int minimum,
            final int maximum,
            final int defaultValue
    ) {
        if (maximum == defaultValue) {
            if (minimum >= size) {
                return minimum;
            } else {
                return size;
            }
        } else {
            if (size >= maximum) {
                return maximum;
            } else {
                if (minimum >= size) {
                    return minimum;
                } else {
                    return size;
                }
            }
        }
    }

    /**
     * 从提供的测量规范中提取大小
     * @param measureSpec 测量规范
     * @return 测量大小
     */
    public static int getSize(final int measureSpec) {
        return View.MeasureSpec.getSize(measureSpec);
    }

    /**
     * 从提供的测量规范中提取模式
     * @param measureSpec 测量规范
     * @return 测量模式
     */
    public static int getMode(final int measureSpec) {
        return View.MeasureSpec.getMode(measureSpec);
    }

    /**
     * 测量 View
     * @param view {@link View}
     * @return int[] 0 = 宽度, 1 = 高度
     */
    public static int[] measureView(final View view) {
        if (view != null) {
            try {
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                if (layoutParams == null) {
                    layoutParams = new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    );
                }
                int widthSpec = ViewGroup.getChildMeasureSpec(0, 0, layoutParams.width);
                int height    = layoutParams.height;
                int heightSpec;
                if (height > 0) {
                    heightSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);
                } else {
                    heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                }
                view.measure(widthSpec, heightSpec);
                return new int[]{view.getMeasuredWidth(), view.getMeasuredHeight()};
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "measureView");
            }
        }
        return new int[]{0, 0};
    }

    /**
     * 获取 View 的宽度
     * @param view {@link View}
     * @return View 的宽度
     */
    public static int getMeasuredWidth(final View view) {
        if (view != null) {
            measureView(view);
            return view.getMeasuredWidth();
        }
        return 0;
    }

    /**
     * 获取 View 的高度
     * @param view {@link View}
     * @return View 的高度
     */
    public static int getMeasuredHeight(final View view) {
        if (view != null) {
            measureView(view);
            return view.getMeasuredHeight();
        }
        return 0;
    }

    /**
     * 测量 View
     * @param view           {@link View}
     * @param specifiedWidth 指定宽度
     * @return {@code true} success, {@code false} fail
     */
    public static boolean measureView(
            final View view,
            final int specifiedWidth
    ) {
        return measureView(view, specifiedWidth, 0);
    }

    /**
     * 测量 View
     * @param view            {@link View}
     * @param specifiedWidth  指定宽度
     * @param specifiedHeight 指定高度
     * @return {@code true} success, {@code false} fail
     */
    public static boolean measureView(
            final View view,
            final int specifiedWidth,
            final int specifiedHeight
    ) {
        try {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            // MeasureSpec
            int widthMeasureSpec  = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            // 如果大于 0
            if (specifiedWidth > 0) {
                widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(specifiedWidth, View.MeasureSpec.EXACTLY);
            }
            // 如果大于 0
            if (specifiedHeight > 0) {
                heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(specifiedHeight, View.MeasureSpec.EXACTLY);
            }
            // 判断是否存在自定义宽高
            if (layoutParams != null) {
                int width  = layoutParams.width;
                int height = layoutParams.height;
                if (width > 0 && height > 0) {
                    widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
                    heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);
                } else if (width > 0) {
                    widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
                } else if (height > 0) {
                    heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);
                }
            }
            view.measure(widthMeasureSpec, heightMeasureSpec);
            view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "measureView");
        }
        return false;
    }
}