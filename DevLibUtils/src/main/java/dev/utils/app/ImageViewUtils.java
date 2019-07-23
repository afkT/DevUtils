package dev.utils.app;

import android.app.Activity;
import android.support.annotation.IdRes;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import java.lang.reflect.Field;

import dev.utils.LogPrintUtils;

/**
 * detail: ImageView 工具类
 * @author Ttt
 */
public final class ImageViewUtils {

    private ImageViewUtils() {
    }

    // 日志 TAG
    private static final String TAG = ImageViewUtils.class.getSimpleName();

    // =================
    // = 获取 ImageView =
    // =================

    /**
     * 获取 ImageView
     * @param view {@link View}
     * @param <T>  泛型
     * @return {@link ImageView}
     */
    public static <T extends ImageView> T getImageView(final View view) {
        if (view != null) {
            try {
                return (T) view;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getImageView");
            }
        }
        return null;
    }

    /**
     * 获取 ImageView
     * @param view {@link View}
     * @param id   R.id.viewId
     * @param <T>  泛型
     * @return {@link ImageView}
     */
    public static <T extends ImageView> T getImageView(final View view, @IdRes final int id) {
        if (view != null) {
            try {
                return view.findViewById(id);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getImageView");
            }
        }
        return null;
    }

    /**
     * 获取 ImageView
     * @param window {@link Window}
     * @param id     R.id.viewId
     * @param <T>    泛型
     * @return {@link ImageView}
     */
    public static <T extends ImageView> T getImageView(final Window window, @IdRes final int id) {
        if (window != null) {
            try {
                return window.findViewById(id);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getImageView");
            }
        }
        return null;
    }

    /**
     * 获取 ImageView
     * @param activity {@link Activity}
     * @param id       R.id.viewId
     * @param <T>      泛型
     * @return {@link ImageView}
     */
    public static <T extends ImageView> T getImageView(final Activity activity, @IdRes final int id) {
        if (activity != null) {
            try {
                return activity.findViewById(id);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getImageView");
            }
        }
        return null;
    }

    // =

    /**
     * 设置 View 图片资源
     * @param draw  R.drawable.id
     * @param views View[]
     */
    public static void setViewImageRes(final int draw, final ImageView... views) {
        setViewImageRes(draw, View.VISIBLE, views);
    }

    /**
     * 设置 View 图片资源
     * @param draw         R.drawable.id
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param views        View[]
     */
    public static void setViewImageRes(final int draw, final int isVisibility, final ImageView... views) {
        if (views != null) {
            for (int i = 0, len = views.length; i < len; i++) {
                ImageView view = views[i];
                if (view != null) {
                    try {
                        // 设置背景
                        view.setImageResource(draw);
                        // 是否显示
                        view.setVisibility(isVisibility);
                    } catch (Exception e) {
                        LogPrintUtils.eTag(TAG, e, "setViewImageRes");
                    }
                }
            }
        }
    }

    // = ImageView 相关 =

    /**
     * 根据 ImageView 获适当的压缩的宽和高
     * @param imageView
     * @return
     */
    public static int[] getImageViewSize(final ImageView imageView) {
        int[] imgSize = new int[]{0, 0};
        if (imageView == null) return imgSize;
        // =
        DisplayMetrics displayMetrics = imageView.getContext().getResources().getDisplayMetrics();
        ViewGroup.LayoutParams lp = imageView.getLayoutParams();
        // 获取 imageView 的实际宽度
        int width = imageView.getWidth();
        if (width <= 0) {
            width = lp.width; // 获取 imageView 在 layout 中声明的宽度
        }
        if (width <= 0) {
            // width = imageView.getMaxWidth(); // 检查最大值
            width = getImageViewFieldValue(imageView, "mMaxWidth");
        }
        if (width <= 0) {
            width = displayMetrics.widthPixels;
        }
        // =
        // 获取 imageView 的实际高度
        int height = imageView.getHeight();
        if (height <= 0) {
            height = lp.height; // 获取 imageView 在 layout 中声明的宽度
        }
        if (height <= 0) {
            height = getImageViewFieldValue(imageView, "mMaxHeight"); // 检查最大值
        }
        if (height <= 0) {
            height = displayMetrics.heightPixels;
        }
        // =
        imgSize[0] = width;
        imgSize[1] = height;
        return imgSize;
    }

    /**
     * 通过反射获取 imageView 的某个属性值
     * @param object
     * @param fieldName
     * @return
     */
    private static int getImageViewFieldValue(final Object object, final String fieldName) {
        int value = 0;
        try {
            Field field = ImageView.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            int fieldValue = field.getInt(object);
            if (fieldValue > 0 && fieldValue < Integer.MAX_VALUE) {
                value = fieldValue;
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getImageViewFieldValue");
        }
        return value;
    }
}