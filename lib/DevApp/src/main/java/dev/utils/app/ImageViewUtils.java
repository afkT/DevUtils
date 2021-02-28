package dev.utils.app;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.RequiresApi;

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

    // ==================
    // = 获取 ImageView =
    // ==================

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

    // ========
    // = 宽高 =
    // ========

    /**
     * 获取 ImageView 是否保持宽高比
     * @param imageView ImageView
     * @return {@code true} yes, {@code false} no
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean getAdjustViewBounds(final ImageView imageView) {
        if (imageView != null) {
            return imageView.getAdjustViewBounds();
        }
        return false;
    }

    /**
     * 设置 ImageView 是否保持宽高比
     * @param imageView        ImageView
     * @param adjustViewBounds 是否调整此视图的边界以保持可绘制的原始纵横比
     * @return {@link ImageView}
     */
    public static ImageView setAdjustViewBounds(
            final ImageView imageView,
            final boolean adjustViewBounds
    ) {
        if (imageView != null) {
            imageView.setAdjustViewBounds(adjustViewBounds);
        }
        return imageView;
    }

    /**
     * 获取 ImageView 最大高度
     * @param imageView ImageView
     * @return view 最大高度
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static int getMaxHeight(final ImageView imageView) {
        if (imageView != null) {
            return imageView.getMaxHeight();
        }
        return -1;
    }

    /**
     * 设置 ImageView 最大高度
     * @param imageView ImageView
     * @param maxHeight 最大高度
     * @return {@link ImageView}
     */
    public static ImageView setMaxHeight(
            final ImageView imageView,
            final int maxHeight
    ) {
        if (imageView != null) {
            imageView.setMaxHeight(maxHeight);
        }
        return imageView;
    }

    /**
     * 获取 ImageView 最大宽度
     * @param imageView ImageView
     * @return view 最大宽度
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static int getMaxWidth(final ImageView imageView) {
        if (imageView != null) {
            return imageView.getMaxWidth();
        }
        return -1;
    }

    /**
     * 设置 ImageView 最大宽度
     * @param imageView ImageView
     * @param maxWidth  最大宽度
     * @return {@link ImageView}
     */
    public static ImageView setMaxWidth(
            final ImageView imageView,
            final int maxWidth
    ) {
        if (imageView != null) {
            imageView.setMaxWidth(maxWidth);
        }
        return imageView;
    }

    // =============
    // = ImageView =
    // =============

    /**
     * 设置 ImageView Bitmap
     * @param view   {@link View}
     * @param bitmap {@link Bitmap}
     * @return {@link View}
     */
    public static View setImageBitmap(
            final View view,
            final Bitmap bitmap
    ) {
        setImageBitmap(getImageView(view), bitmap);
        return view;
    }

    /**
     * 设置 ImageView Bitmap
     * @param imageView {@link ImageView}
     * @param bitmap    {@link Bitmap}
     * @param <T>       泛型
     * @return {@link ImageView}
     */
    public static <T extends ImageView> T setImageBitmap(
            final T imageView,
            final Bitmap bitmap
    ) {
        if (imageView != null) {
            try {
                imageView.setImageBitmap(bitmap);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setImageBitmap");
            }
        }
        return imageView;
    }

    // =

    /**
     * 设置 ImageView Drawable
     * @param view     {@link View}
     * @param drawable {@link Bitmap}
     * @return {@link View}
     */
    public static View setImageDrawable(
            final View view,
            final Drawable drawable
    ) {
        setImageDrawable(getImageView(view), drawable);
        return view;
    }

    /**
     * 设置 ImageView Drawable
     * @param imageView {@link ImageView}
     * @param drawable  {@link Drawable}
     * @param <T>       泛型
     * @return {@link ImageView}
     */
    public static <T extends ImageView> T setImageDrawable(
            final T imageView,
            final Drawable drawable
    ) {
        if (imageView != null) {
            try {
                imageView.setImageDrawable(drawable);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setImageDrawable");
            }
        }
        return imageView;
    }

    // =

    /**
     * 设置 ImageView 资源
     * @param view  {@link View}
     * @param resId resource identifier
     * @return {@link View}
     */
    public static View setImageResource(
            final View view,
            @DrawableRes final int resId
    ) {
        setImageResource(getImageView(view), resId);
        return view;
    }

    /**
     * 设置 ImageView 资源
     * @param imageView {@link ImageView}
     * @param resId     resource identifier
     * @param <T>       泛型
     * @return {@link ImageView}
     */
    public static <T extends ImageView> T setImageResource(
            final T imageView,
            @DrawableRes final int resId
    ) {
        if (imageView != null) {
            try {
                imageView.setImageResource(resId);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setImageResource");
            }
        }
        return imageView;
    }

    // =

    /**
     * 设置 ImageView Matrix
     * @param view   {@link View}
     * @param matrix {@link Matrix}
     * @return {@link View}
     */
    public static View setImageMatrix(
            final View view,
            final Matrix matrix
    ) {
        setImageMatrix(getImageView(view), matrix);
        return view;
    }

    /**
     * 设置 ImageView Matrix
     * @param imageView {@link ImageView}
     * @param matrix    {@link Matrix}
     * @param <T>       泛型
     * @return {@link ImageView}
     */
    public static <T extends ImageView> T setImageMatrix(
            final T imageView,
            final Matrix matrix
    ) {
        if (imageView != null) {
            try {
                imageView.setImageMatrix(matrix);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setImageMatrix");
            }
        }
        return imageView;
    }

    // =

    /**
     * 设置 ImageView 着色颜色
     * @param view {@link View}
     * @param tint 着色颜色
     * @return {@link View}
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static View setImageTintList(
            final View view,
            final ColorStateList tint
    ) {
        setImageTintList(getImageView(view), tint);
        return view;
    }

    /**
     * 设置 ImageView 着色颜色
     * @param imageView {@link ImageView}
     * @param tint      着色颜色
     * @param <T>       泛型
     * @return {@link ImageView}
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static <T extends ImageView> T setImageTintList(
            final T imageView,
            final ColorStateList tint
    ) {
        if (imageView != null) {
            try {
                imageView.setImageTintList(tint);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setImageTintList");
            }
        }
        return imageView;
    }

    // =

    /**
     * 设置 ImageView 着色模式
     * @param view     {@link View}
     * @param tintMode 着色模式 {@link PorterDuff.Mode}
     * @return {@link View}
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static View setImageTintMode(
            final View view,
            final PorterDuff.Mode tintMode
    ) {
        setImageTintMode(getImageView(view), tintMode);
        return view;
    }

    /**
     * 设置 ImageView 着色模式
     * @param imageView {@link ImageView}
     * @param tintMode  着色模式 {@link PorterDuff.Mode}
     * @param <T>       泛型
     * @return {@link ImageView}
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static <T extends ImageView> T setImageTintMode(
            final T imageView,
            final PorterDuff.Mode tintMode
    ) {
        if (imageView != null) {
            try {
                imageView.setImageTintMode(tintMode);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setImageTintMode");
            }
        }
        return imageView;
    }

    // =

    /**
     * 设置 ImageView 缩放类型
     * @param view      {@link View}
     * @param scaleType 缩放类型 {@link ImageView.ScaleType}
     * @return {@link View}
     */
    public static View setScaleType(
            final View view,
            final ImageView.ScaleType scaleType
    ) {
        setScaleType(getImageView(view), scaleType);
        return view;
    }

    /**
     * 设置 ImageView 缩放类型
     * @param imageView {@link ImageView}
     * @param scaleType 缩放类型 {@link ImageView.ScaleType}
     * @param <T>       泛型
     * @return {@link ImageView}
     */
    public static <T extends ImageView> T setScaleType(
            final T imageView,
            final ImageView.ScaleType scaleType
    ) {
        if (imageView != null) {
            try {
                imageView.setScaleType(scaleType);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setScaleType");
            }
        }
        return imageView;
    }

    // ========
    // = 获取 =
    // ========

    /**
     * 获取 ImageView Matrix
     * @param view {@link View}
     * @return {@link Matrix}
     */
    public static Matrix getImageMatrix(final View view) {
        return getImageMatrix(getImageView(view));
    }

    /**
     * 获取 ImageView Matrix
     * @param imageView {@link ImageView}
     * @param <T>       泛型
     * @return {@link Matrix}
     */
    public static <T extends ImageView> Matrix getImageMatrix(final T imageView) {
        if (imageView != null) return imageView.getImageMatrix();
        return null;
    }

    // =

    /**
     * 获取 ImageView 着色颜色
     * @param view {@link View}
     * @return {@link ColorStateList}
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static ColorStateList getImageTintList(final View view) {
        return getImageTintList(getImageView(view));
    }

    /**
     * 获取 ImageView 着色颜色
     * @param imageView {@link ImageView}
     * @param <T>       泛型
     * @return {@link ColorStateList}
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static <T extends ImageView> ColorStateList getImageTintList(final T imageView) {
        if (imageView != null) return imageView.getImageTintList();
        return null;
    }

    // =

    /**
     * 获取 ImageView 着色模式
     * @param view {@link View}
     * @return {@link PorterDuff.Mode}
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static PorterDuff.Mode getImageTintMode(final View view) {
        return getImageTintMode(getImageView(view));
    }

    /**
     * 获取 ImageView 着色模式
     * @param imageView {@link ImageView}
     * @param <T>       泛型
     * @return {@link PorterDuff.Mode}
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static <T extends ImageView> PorterDuff.Mode getImageTintMode(final T imageView) {
        if (imageView != null) return imageView.getImageTintMode();
        return null;
    }

    // =

    /**
     * 获取 ImageView 缩放模式
     * @param view {@link View}
     * @return {@link ImageView.ScaleType}
     */
    public static ImageView.ScaleType getScaleType(final View view) {
        return getScaleType(getImageView(view));
    }

    /**
     * 获取 ImageView 缩放模式
     * @param imageView {@link ImageView}
     * @param <T>       泛型
     * @return {@link ImageView.ScaleType}
     */
    public static <T extends ImageView> ImageView.ScaleType getScaleType(final T imageView) {
        if (imageView != null) return imageView.getScaleType();
        return null;
    }

    // =

    /**
     * 获取 ImageView Drawable
     * @param view {@link View}
     * @return {@link Drawable}
     */
    public static Drawable getDrawable(final View view) {
        return getDrawable(getImageView(view));
    }

    /**
     * 获取 ImageView Drawable
     * @param imageView {@link ImageView}
     * @param <T>       泛型
     * @return {@link Drawable}
     */
    public static <T extends ImageView> Drawable getDrawable(final T imageView) {
        if (imageView != null) return imageView.getDrawable();
        return null;
    }

    // ===========
    // = 多个操作 =
    // ===========

    /**
     * 设置 View 图片资源
     * @param resId resource identifier
     * @param views View[]
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setBackgroundResources(
            @DrawableRes final int resId,
            final View... views
    ) {
        return setBackgroundResources(resId, View.VISIBLE, views);
    }

    /**
     * 设置 View 图片资源
     * @param resId        resource identifier
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param views        View[]
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setBackgroundResources(
            @DrawableRes final int resId,
            final int isVisibility,
            final View... views
    ) {
        if (views != null) {
            for (int i = 0, len = views.length; i < len; i++) {
                View view = views[i];
                if (view != null) {
                    try {
                        // 设置显示状态
                        view.setVisibility(isVisibility);
                        // 设置图片资源
                        view.setBackgroundResource(resId);
                    } catch (Exception e) {
                        LogPrintUtils.eTag(TAG, e, "setBackgroundResources");
                    }
                }
            }
            return true;
        }
        return false;
    }

    // =

    /**
     * 设置 View 图片资源
     * @param resId resource identifier
     * @param views View[]
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setImageResources(
            @DrawableRes final int resId,
            final View... views
    ) {
        return setImageResources(resId, View.VISIBLE, views);
    }

    /**
     * 设置 View 图片资源
     * @param resId        resource identifier
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param views        View[]
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setImageResources(
            @DrawableRes final int resId,
            final int isVisibility,
            final View... views
    ) {
        if (views != null) {
            for (int i = 0, len = views.length; i < len; i++) {
                ImageView imageView = getImageView(views[i]);
                if (imageView != null) {
                    try {
                        // 设置显示状态
                        imageView.setVisibility(isVisibility);
                        // 设置图片资源
                        imageView.setImageResource(resId);
                    } catch (Exception e) {
                        LogPrintUtils.eTag(TAG, e, "setImageResources");
                    }
                }
            }
            return true;
        }
        return false;
    }

    // =

    /**
     * 设置 View Bitmap
     * @param bitmap {@link Bitmap}
     * @param views  View[]
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setImageBitmaps(
            final Bitmap bitmap,
            final View... views
    ) {
        return setImageBitmaps(bitmap, View.VISIBLE, views);
    }

    /**
     * 设置 View Bitmap
     * @param bitmap       {@link Bitmap}
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param views        View[]
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setImageBitmaps(
            final Bitmap bitmap,
            final int isVisibility,
            final View... views
    ) {
        if (views != null) {
            for (int i = 0, len = views.length; i < len; i++) {
                ImageView imageView = getImageView(views[i]);
                if (imageView != null) {
                    try {
                        // 设置显示状态
                        imageView.setVisibility(isVisibility);
                        // 设置 Bitmap
                        imageView.setImageBitmap(bitmap);
                    } catch (Exception e) {
                        LogPrintUtils.eTag(TAG, e, "setImageBitmaps");
                    }
                }
            }
            return true;
        }
        return false;
    }

    // =

    /**
     * 设置 View Drawable
     * @param drawable {@link drawable}
     * @param views    View[]
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setImageDrawables(
            final Drawable drawable,
            final View... views
    ) {
        return setImageDrawables(drawable, View.VISIBLE, views);
    }

    /**
     * 设置 View Drawable
     * @param drawable     {@link drawable}
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param views        View[]
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setImageDrawables(
            final Drawable drawable,
            final int isVisibility,
            final View... views
    ) {
        if (views != null) {
            for (int i = 0, len = views.length; i < len; i++) {
                ImageView imageView = getImageView(views[i]);
                if (imageView != null) {
                    try {
                        // 设置显示状态
                        imageView.setVisibility(isVisibility);
                        // 设置 Drawable
                        imageView.setImageDrawable(drawable);
                    } catch (Exception e) {
                        LogPrintUtils.eTag(TAG, e, "setImageDrawables");
                    }
                }
            }
            return true;
        }
        return false;
    }

    // =

    /**
     * 设置 View 缩放模式
     * @param scaleType {@link ImageView.ScaleType}
     * @param views     View[]
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setScaleTypes(
            final ImageView.ScaleType scaleType,
            final View... views
    ) {
        return setScaleTypes(scaleType, View.VISIBLE, views);
    }

    /**
     * 设置 View 缩放模式
     * @param scaleType    {@link ImageView.ScaleType}
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param views        View[]
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setScaleTypes(
            final ImageView.ScaleType scaleType,
            final int isVisibility,
            final View... views
    ) {
        if (views != null) {
            for (int i = 0, len = views.length; i < len; i++) {
                ImageView imageView = getImageView(views[i]);
                if (imageView != null) {
                    try {
                        // 设置显示状态
                        imageView.setVisibility(isVisibility);
                        // 设置缩放模式
                        imageView.setScaleType(scaleType);
                    } catch (Exception e) {
                        LogPrintUtils.eTag(TAG, e, "setScaleTypes");
                    }
                }
            }
            return true;
        }
        return false;
    }

    // ===========
    // = 信息获取 =
    // ===========

    /**
     * 根据 ImageView 获适当的宽高
     * @param imageView {@link ImageView}
     * @return 宽高, 0 = 宽, 1 = 高
     */
    public static int[] getImageViewSize(final ImageView imageView) {
        int[] imageSize = new int[]{0, 0};
        try {
            if (imageView == null) return imageSize;

            DisplayMetrics         displayMetrics = ResourceUtils.getDisplayMetrics(imageView.getContext());
            ViewGroup.LayoutParams layoutParams   = imageView.getLayoutParams();

            // 获取 ImageView 的实际宽度
            int width = imageView.getWidth();
            if (width <= 0) {
                width = layoutParams.width; // 获取 imageView 在 layout 中声明的宽度
            }
            if (width <= 0) {
//                width = imageView.getMaxWidth(); // 检查最大值
                width = getImageViewFieldValue(imageView, "mMaxWidth");
            }
            if (width <= 0) {
                width = displayMetrics.widthPixels;
            }

            // 获取 ImageView 的实际高度
            int height = imageView.getHeight();
            if (height <= 0) {
                height = layoutParams.height; // 获取 imageView 在 layout 中声明的高度
            }
            if (height <= 0) {
//                height = imageView.getMaxHeight(); // 检查最大值
                height = getImageViewFieldValue(imageView, "mMaxHeight"); // 检查最大值
            }
            if (height <= 0) {
                height = displayMetrics.heightPixels;
            }

            // 填充宽高信息
            imageSize[0] = width;
            imageSize[1] = height;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getImageViewSize");
        }
        return imageSize;
    }

    /**
     * 通过反射获取 ImageView 的属性值
     * @param object    对象
     * @param fieldName 属性名
     * @return 指定属性的值
     */
    private static int getImageViewFieldValue(
            final Object object,
            final String fieldName
    ) {
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