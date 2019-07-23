package dev.utils.app;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
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
     * 设置背景着色
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
     * 设置前景着色
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
     * 设置 ImageView 着色
     * @param view {@link View}
     * @param tint 着色颜色
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void setImageTintList(final View view, final ColorStateList tint) {
        setImageTintList(getImageView(view), tint);
    }

    /**
     * 设置 ImageView 着色
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
     * @param view     {@link View}
     * @param scaleType 缩放类型 {@link ImageView.ScaleType}
     */
    public static void setScaleType(final View view, final ImageView.ScaleType scaleType) {
        setScaleType(getImageView(view), scaleType);
    }

    /**
     * 设置 ImageView 着色模式
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