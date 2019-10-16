package dev.utils.app;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.view.Gravity;
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

    // ========
    // = 宽高 =
    // ========

    /**
     * 获取 ImageView 是否保持宽高比
     * @param imageView ImageView
     * @return {@code true} yes, {@code false} no
     */
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
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
     */
    public static void setAdjustViewBounds(final ImageView imageView, final boolean adjustViewBounds) {
        if (imageView != null) {
            imageView.setAdjustViewBounds(adjustViewBounds);
        }
    }

    /**
     * 获取 ImageView 最大高度
     * @param imageView ImageView
     * @return view 最大高度
     */
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
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
     */
    public static void setMaxHeight(final ImageView imageView, final int maxHeight) {
        if (imageView != null) {
            imageView.setMaxHeight(maxHeight);
        }
    }

    /**
     * 获取 ImageView 最大宽度
     * @param imageView ImageView
     * @return view 最大宽度
     */
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
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
     */
    public static void setMaxWidth(final ImageView imageView, final int maxWidth) {
        if (imageView != null) {
            imageView.setMaxWidth(maxWidth);
        }
    }

    // ========
    // = 背景 =
    // ========

    /**
     * 设置背景图片
     * @param view       {@link View}
     * @param background 背景图片
     */
    public static void setBackground(final View view, final Drawable background) {
        if (view != null) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    view.setBackground(background);
                } else {
                    view.setBackgroundDrawable(background);
                }
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setBackground");
            }
        }
    }

    /**
     * 设置背景颜色
     * @param view  {@link View}
     * @param color 背景颜色
     */
    public static void setBackgroundColor(final View view, @ColorInt final int color) {
        if (view != null) {
            try {
                view.setBackgroundColor(color);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setBackgroundColor");
            }
        }
    }

    /**
     * 设置背景资源
     * @param view  {@link View}
     * @param resId resource identifier
     */
    public static void setBackgroundResource(final View view, @DrawableRes final int resId) {
        if (view != null) {
            try {
                view.setBackgroundResource(resId);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setBackgroundResource");
            }
        }
    }

    /**
     * 设置背景着色颜色
     * @param view {@link View}
     * @param tint 着色颜色
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void setBackgroundTintList(final View view, final ColorStateList tint) {
        if (view != null) {
            try {
                view.setBackgroundTintList(tint);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setBackgroundTintList");
            }
        }
    }

    /**
     * 设置背景着色模式
     * @param view     {@link View}
     * @param tintMode 着色模式 {@link PorterDuff.Mode}
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void setBackgroundTintMode(final View view, final PorterDuff.Mode tintMode) {
        if (view != null) {
            try {
                view.setBackgroundTintMode(tintMode);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setBackgroundTintMode");
            }
        }
    }

    // ========
    // = 前景 =
    // ========

    /**
     * 设置前景图片
     * @param view       {@link View}
     * @param foreground 前景图片
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void setForeground(final View view, final Drawable foreground) {
        if (view != null) {
            try {
                view.setForeground(foreground);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setForeground");
            }
        }
    }

    /**
     * 设置前景重心
     * @param view    {@link View}
     * @param gravity 重心
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void setForegroundGravity(final View view, final int gravity) {
        if (view != null) {
            try {
                view.setForegroundGravity(gravity);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setForegroundGravity");
            }
        }
    }

    /**
     * 设置前景着色颜色
     * @param view {@link View}
     * @param tint 着色颜色
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void setForegroundTintList(final View view, final ColorStateList tint) {
        if (view != null) {
            try {
                view.setForegroundTintList(tint);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setForegroundTintList");
            }
        }
    }

    /**
     * 设置前景着色模式
     * @param view     {@link View}
     * @param tintMode 着色模式 {@link PorterDuff.Mode}
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void setForegroundTintMode(final View view, final PorterDuff.Mode tintMode) {
        if (view != null) {
            try {
                view.setForegroundTintMode(tintMode);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setForegroundTintMode");
            }
        }
    }

    // =============
    // = ImageView =
    // =============

    /**
     * 设置 ImageView Bitmap
     * @param view   {@link View}
     * @param bitmap {@link Bitmap}
     */
    public static void setImageBitmap(final View view, final Bitmap bitmap) {
        setImageBitmap(getImageView(view), bitmap);
    }

    /**
     * 设置 ImageView Bitmap
     * @param imageView {@link ImageView}
     * @param bitmap    {@link Bitmap}
     * @param <T>       泛型
     * @return {@link ImageView}
     */
    public static <T extends ImageView> T setImageBitmap(final T imageView, final Bitmap bitmap) {
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
     */
    public static void setImageDrawable(final View view, final Drawable drawable) {
        setImageDrawable(getImageView(view), drawable);
    }

    /**
     * 设置 ImageView Drawable
     * @param imageView {@link ImageView}
     * @param drawable  {@link Drawable}
     * @param <T>       泛型
     * @return {@link ImageView}
     */
    public static <T extends ImageView> T setImageDrawable(final T imageView, final Drawable drawable) {
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
     */
    public static void setImageResource(final View view, @DrawableRes final int resId) {
        setImageResource(getImageView(view), resId);
    }

    /**
     * 设置 ImageView 资源
     * @param imageView {@link ImageView}
     * @param resId     resource identifier
     * @param <T>       泛型
     * @return {@link ImageView}
     */
    public static <T extends ImageView> T setImageResource(final T imageView, @DrawableRes final int resId) {
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
     */
    public static void setImageMatrix(final View view, final Matrix matrix) {
        setImageMatrix(getImageView(view), matrix);
    }

    /**
     * 设置 ImageView Matrix
     * @param imageView {@link ImageView}
     * @param matrix    {@link Matrix}
     * @param <T>       泛型
     * @return {@link ImageView}
     */
    public static <T extends ImageView> T setImageMatrix(final T imageView, final Matrix matrix) {
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
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void setImageTintList(final View view, final ColorStateList tint) {
        setImageTintList(getImageView(view), tint);
    }

    /**
     * 设置 ImageView 着色颜色
     * @param imageView {@link ImageView}
     * @param tint      着色颜色
     * @param <T>       泛型
     * @return {@link ImageView}
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static <T extends ImageView> T setImageTintList(final T imageView, final ColorStateList tint) {
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
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void setImageTintMode(final View view, final PorterDuff.Mode tintMode) {
        setImageTintMode(getImageView(view), tintMode);
    }

    /**
     * 设置 ImageView 着色模式
     * @param imageView {@link ImageView}
     * @param tintMode  着色模式 {@link PorterDuff.Mode}
     * @param <T>       泛型
     * @return {@link ImageView}
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static <T extends ImageView> T setImageTintMode(final T imageView, final PorterDuff.Mode tintMode) {
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
     */
    public static void setScaleType(final View view, final ImageView.ScaleType scaleType) {
        setScaleType(getImageView(view), scaleType);
    }

    /**
     * 设置 ImageView 缩放类型
     * @param imageView {@link ImageView}
     * @param scaleType 缩放类型 {@link ImageView.ScaleType}
     * @param <T>       泛型
     * @return {@link ImageView}
     */
    public static <T extends ImageView> T setScaleType(final T imageView, final ImageView.ScaleType scaleType) {
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
     * 获取 View 背景 Drawable
     * @param view {@link View}
     * @return 背景 Drawable
     */
    public static Drawable getBackground(final View view) {
        if (view != null) return view.getBackground();
        return null;
    }

    /**
     * 获取 View 背景着色颜色
     * @param view {@link View}
     * @return 背景着色颜色 {@link ColorStateList}
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static ColorStateList getBackgroundTintList(final View view) {
        if (view != null) return view.getBackgroundTintList();
        return null;
    }

    /**
     * 获取 View 背景着色模式
     * @param view {@link View}
     * @return 背景着色模式 {@link PorterDuff.Mode}
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static PorterDuff.Mode getBackgroundTintMode(final View view) {
        if (view != null) return view.getBackgroundTintMode();
        return null;
    }

    // =

    /**
     * 获取 View 前景 Drawable
     * @param view {@link View}
     * @return 前景 Drawable
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static Drawable getForeground(final View view) {
        if (view != null) return view.getForeground();
        return null;
    }

    /**
     * 获取 View 前景重心
     * @param view {@link View}
     * @return 前景重心 {@link Gravity}
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static int getForegroundGravity(final View view) {
        if (view != null) return view.getForegroundGravity();
        return Gravity.FILL;
    }

    /**
     * 获取 View 前景着色颜色
     * @param view {@link View}
     * @return 前景着色颜色 {@link ColorStateList}
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static ColorStateList getForegroundTintList(final View view) {
        if (view != null) return view.getForegroundTintList();
        return null;
    }

    /**
     * 获取 View 前景着色模式
     * @param view {@link View}
     * @return 前景着色模式 {@link PorterDuff.Mode}
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static PorterDuff.Mode getForegroundTintMode(final View view) {
        if (view != null) return view.getForegroundTintMode();
        return null;
    }

    // =

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

    // ============
    // = 着色处理 =
    // ============

    /**
     * ImageView 着色处理
     * @param imageView {@link ImageView}
     * @param color     颜色值
     * @param <T>       泛型
     * @return {@link ImageView}
     */
    public static <T extends ImageView> T setColorFilter(final T imageView, @ColorInt final int color) {
        return setColorFilter(imageView, getDrawable(imageView), color);
    }

    /**
     * ImageView 着色处理, 并且设置 Drawable
     * @param imageView {@link ImageView}
     * @param drawable  {@link Drawable}
     * @param color     颜色值
     * @param <T>       泛型
     * @return {@link ImageView}
     */
    public static <T extends ImageView> T setColorFilter(final T imageView, final Drawable drawable, @ColorInt final int color) {
        try {
            setBackground(imageView, setColorFilter(drawable, color));
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "setColorFilter");
        }
        return imageView;
    }

    // =

    /**
     * ImageView 着色处理
     * @param imageView   {@link ImageView}
     * @param colorFilter 颜色过滤 ( 效果 )
     * @param <T>         泛型
     * @return {@link ImageView}
     */
    public static <T extends ImageView> T setColorFilter(final T imageView, final ColorFilter colorFilter) {
        return setColorFilter(imageView, getDrawable(imageView), colorFilter);
    }

    /**
     * ImageView 着色处理, 并且设置 Drawable
     * @param imageView   {@link ImageView}
     * @param drawable    {@link Drawable}
     * @param colorFilter 颜色过滤 ( 效果 )
     * @param <T>         泛型
     * @return {@link ImageView}
     */
    public static <T extends ImageView> T setColorFilter(final T imageView, final Drawable drawable, final ColorFilter colorFilter) {
        try {
            setBackground(imageView, setColorFilter(drawable, colorFilter));
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "setColorFilter");
        }
        return imageView;
    }

    // ============
    // = 多个操作 =
    // ============

    /**
     * 设置 View 图片资源
     * @param resId resource identifier
     * @param views View[]
     */
    public static void setBackgroundResources(@DrawableRes final int resId, final View... views) {
        setBackgroundResources(resId, View.VISIBLE, views);
    }

    /**
     * 设置 View 图片资源
     * @param resId        resource identifier
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param views        View[]
     */
    public static void setBackgroundResources(@DrawableRes final int resId, final int isVisibility, final View... views) {
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
        }
    }

    // =

    /**
     * 设置 View 图片资源
     * @param resId resource identifier
     * @param views View[]
     */
    public static void setImageResources(@DrawableRes final int resId, final View... views) {
        setImageResources(resId, View.VISIBLE, views);
    }

    /**
     * 设置 View 图片资源
     * @param resId        resource identifier
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param views        View[]
     */
    public static void setImageResources(@DrawableRes final int resId, final int isVisibility, final View... views) {
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
        }
    }

    // =

    /**
     * 设置 View Bitmap
     * @param bitmap {@link Bitmap}
     * @param views  View[]
     */
    public static void setImageBitmaps(final Bitmap bitmap, final View... views) {
        setImageBitmaps(bitmap, View.VISIBLE, views);
    }

    /**
     * 设置 View Bitmap
     * @param bitmap       {@link Bitmap}
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param views        View[]
     */
    public static void setImageBitmaps(final Bitmap bitmap, final int isVisibility, final View... views) {
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
        }
    }

    // =

    /**
     * 设置 View Drawable
     * @param drawable {@link drawable}
     * @param views    View[]
     */
    public static void setImageDrawables(final Drawable drawable, final View... views) {
        setImageDrawables(drawable, View.VISIBLE, views);
    }

    /**
     * 设置 View Drawable
     * @param drawable     {@link drawable}
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param views        View[]
     */
    public static void setImageDrawables(final Drawable drawable, final int isVisibility, final View... views) {
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
        }
    }

    // =

    /**
     * 设置 View 缩放模式
     * @param scaleType {@link ImageView.ScaleType}
     * @param views     View[]
     */
    public static void setScaleTypes(final ImageView.ScaleType scaleType, final View... views) {
        setScaleTypes(scaleType, View.VISIBLE, views);
    }

    /**
     * 设置 View 缩放模式
     * @param scaleType    {@link ImageView.ScaleType}
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param views        View[]
     */
    public static void setScaleTypes(final ImageView.ScaleType scaleType, final int isVisibility, final View... views) {
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
        }
    }

    // ============
    // = 信息获取 =
    // ============

    /**
     * 根据 ImageView 获适当的宽高
     * @param imageView {@link ImageView}
     * @return 宽高, 0 = 宽, 1 = 高
     */
    public static int[] getImageViewSize(final ImageView imageView) {
        int[] imageSize = new int[]{0, 0};
        try {
            if (imageView == null) return imageSize;
            // =
            DisplayMetrics displayMetrics = imageView.getContext().getResources().getDisplayMetrics();
            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();

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

    // ======================
    // = 其他工具类实现代码 =
    // ======================

    // ==============
    // = ImageUtils =
    // ==============

    /**
     * 图片着色 - tint
     * @param drawable {@link Drawable}
     * @param color    颜色值
     * @return 着色后的 {@link Drawable}
     */
    private static Drawable setColorFilter(final Drawable drawable, @ColorInt final int color) {
        return setColorFilter(drawable, color, PorterDuff.Mode.SRC_IN);
    }

    /**
     * 图片着色 - tint
     * @param drawable {@link Drawable}
     * @param color    颜色值
     * @param mode     着色模式 {@link PorterDuff.Mode}
     * @return 着色后的 {@link Drawable}
     */
    private static Drawable setColorFilter(final Drawable drawable, @ColorInt final int color, final PorterDuff.Mode mode) {
        if (drawable != null && mode != null) {
            try {
                drawable.setColorFilter(color, mode);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setColorFilter");
            }
        }
        return drawable;
    }

    /**
     * 图片着色 - tint
     * @param drawable    {@link Drawable}
     * @param colorFilter 颜色过滤 ( 效果 )
     * @return 着色后的 {@link Drawable}
     */
    private static Drawable setColorFilter(final Drawable drawable, final ColorFilter colorFilter) {
        if (drawable != null && colorFilter != null) {
            try {
                drawable.setColorFilter(colorFilter);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setColorFilter");
            }
        }
        return drawable;
    }
}