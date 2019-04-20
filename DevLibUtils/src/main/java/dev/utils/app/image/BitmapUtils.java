package dev.utils.app.image;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

import dev.utils.LogPrintUtils;
import dev.utils.app.AppUtils;
import dev.utils.app.ScreenUtils;

/**
 * detail: Bitmap工具类
 * @author Ttt
 */
public final class BitmapUtils {

    private BitmapUtils() {
    }

    // 日志 TAG
    private static final String TAG = BitmapUtils.class.getSimpleName();

    /**
     * 将10进制颜色（Int）转换为Drawable对象
     * @param color
     * @return
     */
    public static Drawable intToDrawable(final int color) {
        try {
            return new ColorDrawable(color);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "intToDrawable");
        }
        return null;
    }

    /**
     * 将16进制颜色（String）转化为Drawable对象
     * @param color
     * @return
     */
    public static Drawable stringToDrawable(final String color) {
        if (TextUtils.isEmpty(color)) return null;
        try {
            return new ColorDrawable(Color.parseColor(color));
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "stringToDrawable");
        }
        return null;
    }

    /**
     * 图片着色
     * @param drawable
     * @param tintColor
     * @return
     */
    public static Drawable tintIcon(final Drawable drawable, final @ColorInt int tintColor) {
        if (drawable != null) {
            try {
                drawable.setColorFilter(tintColor, PorterDuff.Mode.SRC_IN);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "tintIcon");
            }
        }
        return drawable;
    }

    /**
     * .9 图片着色
     * @param context
     * @param tintColor
     * @param id
     * @return
     */
    public static Drawable tint9PatchDrawableFrame(final Context context, final @ColorInt int tintColor, final @DrawableRes int id) {
        if (context == null) return null;
        try {
            final NinePatchDrawable toastDrawable = (NinePatchDrawable) getDrawable(context, id);
            return tintIcon(toastDrawable, tintColor);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "tint9PatchDrawableFrame");
        }
        return null;
    }

    /**
     * 设置背景
     * @param view
     * @param drawable
     */
    public static void setBackground(final @NonNull View view, final Drawable drawable) {
        if (view != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                view.setBackground(drawable);
            else
                view.setBackgroundDrawable(drawable);
        }
    }

    /**
     * 获取 Drawable
     * @param context
     * @param id
     * @return
     */
    public static Drawable getDrawable(final Context context, final @DrawableRes int id) {
        if (context == null) return null;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                return context.getDrawable(id);
            else
                return context.getResources().getDrawable(id);
        } catch (Resources.NotFoundException e) {
            LogPrintUtils.eTag(TAG, e, "getDrawable");
        }
        return null;
    }

    /**
     * 通过Resources获取Bitmap
     * @param context
     * @param resId
     * @return
     */
    public static Bitmap getBitmapFromResources(final Context context, final int resId) {
        if (context == null) return null;
        try {
            return BitmapFactory.decodeResource(context.getResources(), resId);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getBitmapFromResources");
        }
        return null;
    }

    /**
     * 通过Resources获取Drawable
     * @param context
     * @param resId
     * @return
     */
    public static Drawable getDrawableFromResources(final Context context, final int resId) {
        if (context == null) return null;
        try {
            return context.getResources().getDrawable(resId);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getDrawableFromResources");
        }
        return null;
    }

    /**
     * 获取本地SDCard 图片
     * @param filePath 文件路径
     * @return
     */
    public static Bitmap getSDCardBitmapStream(final String filePath) {
        try {
            FileInputStream fis = new FileInputStream(new File(filePath));//文件输入流
            Bitmap bmp = BitmapFactory.decodeStream(fis);
            return bmp;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getSDCardBitmapStream");
        }
        return null;
    }

    /**
     * 获取本地SDCard 图片
     * @param filePath 文件路径
     * @return
     */
    public static Bitmap getSDCardBitmapFile(final String filePath) {
        try {
            return BitmapFactory.decodeFile(filePath);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getSDCardBitmapFile");
        }
        return null;
    }

    /**
     * 获取Bitmap
     * @param is
     * @return
     */
    public static Bitmap getBitmap(final InputStream is) {
        if (is == null) return null;
        try {
            return BitmapFactory.decodeStream(is);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getBitmap");
        }
        return null;
    }

    // =

    /**
     * Bitmay 转换成byte数组
     * @param bitmap
     * @return
     */
    public static byte[] bitmapToByte(final Bitmap bitmap) {
        return bitmapToByte(bitmap, 100, Bitmap.CompressFormat.PNG);
    }

    /**
     * Bitmay 转换成byte数组
     * @param bitmap
     * @param format
     * @return
     */
    public static byte[] bitmapToByte(final Bitmap bitmap, final Bitmap.CompressFormat format) {
        return bitmapToByte(bitmap, 100, format);
    }

    /**
     * Bitmay 转换成byte数组
     * @param bitmap
     * @param quality
     * @param format
     * @return
     */
    public static byte[] bitmapToByte(final Bitmap bitmap, final int quality, final Bitmap.CompressFormat format) {
        if (bitmap == null || format == null) return null;
        try {
            ByteArrayOutputStream o = new ByteArrayOutputStream();
            bitmap.compress(format, quality, o);
            return o.toByteArray();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "bitmapToByte");
        }
        return null;
    }

    // =

    /**
     * Drawable 转换成 byte数组
     * @param drawable
     * @return
     */
    public static byte[] drawableToByte(final Drawable drawable) {
        return bitmapToByte(drawableToBitmap(drawable));
    }

    /**
     * Drawable 转换成 byte数组
     * @param drawable
     * @param format
     * @return
     */
    public static byte[] drawableToByte(final Drawable drawable, final Bitmap.CompressFormat format) {
        return bitmapToByte(drawableToBitmap(drawable), format);
    }

    /**
     * Drawable 转换成 byte数组
     * @param drawable
     * @return
     */
    public static byte[] drawableToByte2(final Drawable drawable) {
        return drawable == null ? null : bitmapToByte(drawableToBitmap2(drawable));
    }

    /**
     * Drawable 转换成 byte数组
     * @param drawable
     * @param format
     * @return
     */
    public static byte[] drawableToByte2(final Drawable drawable, final Bitmap.CompressFormat format) {
        return drawable == null ? null : bitmapToByte(drawableToBitmap2(drawable), format);
    }

    // =

    /**
     * byte 数组转换为Bitmap
     * @param bytes
     * @return
     */
    public static Bitmap byteToBitmap(final byte[] bytes) {
        try {
            return (bytes == null || bytes.length == 0) ? null : BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "byteToBitmap");
        }
        return null;
    }

    /**
     * byte数组转换成Drawable
     * @param bytes
     * @return
     */
    public static Drawable byteToDrawable(final byte[] bytes) {
        return bitmapToDrawable(byteToBitmap(bytes));
    }

    /**
     * Bitmap 转换成 Drawable
     * @param bitmap
     * @return
     */
    public static Drawable bitmapToDrawable(final Bitmap bitmap) {
        try {
            return bitmap == null ? null : new BitmapDrawable(AppUtils.getResources(), bitmap);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "bitmapToDrawable");
        }
        return null;
    }

    // =

    /**
     * Drawable 转换成 Bitmap
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap(final Drawable drawable) {
        try {
            return drawable == null ? null : ((BitmapDrawable) drawable).getBitmap();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "drawableToBitmap");
        }
        return null;
    }

    /**
     * Drawable 转换 Bitmap
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap2(final Drawable drawable) {
        if (drawable == null) return null;
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }
        Bitmap bitmap;
        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1,
                    drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                    drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        }
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * Drawable 转换 Bitmap
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap3(final Drawable drawable) {
        if (drawable == null) return null;
        Bitmap bitmap;
        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1,
                    drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                    drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        }
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    // =

    /**
     * 保存图片到SD卡 - JPEG
     * @param bitmap  需要保存的数据
     * @param path    保存路径
     * @param quality 压缩比例
     */
    public static boolean saveBitmapToSDCardJPEG(final Bitmap bitmap, final String path, final int quality) {
        return saveBitmapToSDCard(bitmap, path, Bitmap.CompressFormat.JPEG, quality);
    }

    /**
     * 保存图片到SD卡 - PNG
     * @param bitmap 需要保存的数据
     * @param path   保存路径
     */
    public static boolean saveBitmapToSDCardPNG(final Bitmap bitmap, final String path) {
        return saveBitmapToSDCard(bitmap, path, Bitmap.CompressFormat.PNG, 80);
    }

    /**
     * 保存图片到SD卡 - PNG
     * @param bitmap  需要保存的数据
     * @param path    保存路径
     * @param quality 压缩比例
     */
    public static boolean saveBitmapToSDCardPNG(final Bitmap bitmap, final String path, final int quality) {
        return saveBitmapToSDCard(bitmap, path, Bitmap.CompressFormat.PNG, quality);
    }

    /**
     * 保存图片到SD卡 - PNG
     * @param bitmap  需要保存的数据
     * @param path    保存路径
     * @param quality 压缩比例
     * @return
     */
    public static boolean saveBitmapToSDCard(final Bitmap bitmap, final String path, final int quality) {
        return saveBitmapToSDCard(bitmap, path, Bitmap.CompressFormat.PNG, quality);
    }

    /**
     * 保存图片到SD卡
     * @param bitmap   图片资源
     * @param filePath 保存路径
     * @param format   如 Bitmap.CompressFormat.PNG
     * @param quality  保存的图片质量， 100 则完整质量不压缩保存
     * @return 保存结果
     */
    public static boolean saveBitmapToSDCard(final Bitmap bitmap, final String filePath, final Bitmap.CompressFormat format, final int quality) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filePath);
            if (fos != null) {
                bitmap.compress(format, quality, fos);
                fos.close();
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "saveBitmapToSDCard");
            return false;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                }
            }
        }
        return true;
    }

    // =

    /**
     * 将Drawable转化为Bitmap
     * @param drawable
     * @return
     */
    public static Bitmap getBitmapFromDrawable(final Drawable drawable) {
        try {
            int width = drawable.getIntrinsicWidth();
            int height = drawable.getIntrinsicHeight();
            Bitmap bitmap = Bitmap.createBitmap(width, height, drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, width, height);
            drawable.draw(canvas);
            return bitmap;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getBitmapFromDrawable");
        }
        return null;
    }

    /**
     * 通过View, 获取背景转换Bitmap
     * @param view
     * @return
     */
    public static Bitmap bitmapToViewBackGround(final View view) {
        if (view == null) return null;
        try {
            Bitmap ret = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(ret);
            Drawable bgDrawable = view.getBackground();
            if (bgDrawable != null) {
                bgDrawable.draw(canvas);
            } else {
                canvas.drawColor(Color.WHITE);
            }
            view.draw(canvas);
            return ret;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "bitmapToViewBackGround");
        }
        return null;
    }

    /**
     * 通过View, 获取Bitmap -> 绘制整个View
     * @param view
     * @return
     */
    public static Bitmap getBitmapFromView(final View view) {
        if (view == null) return null;
        try {
            Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            view.layout(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
            view.draw(canvas);
            return bitmap;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getBitmapFromView");
        }
        return null;
    }

    /**
     * 把一个View的对象转换成bitmap
     * @param view
     * @return
     */
    public static Bitmap getBitmapFromView2(final View view) {
        if (view == null) return null;
        try {
            view.clearFocus();
            view.setPressed(false);
            // 能画缓存就返回false
            boolean willNotCache = view.willNotCacheDrawing();
            view.setWillNotCacheDrawing(false);
            int color = view.getDrawingCacheBackgroundColor();
            view.setDrawingCacheBackgroundColor(0);
            if (color != 0) {
                view.destroyDrawingCache();
            }
            view.buildDrawingCache();
            Bitmap cacheBitmap = view.getDrawingCache();
            if (cacheBitmap == null) {
                return null;
            }
            Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);
            // Restore the view
            view.destroyDrawingCache();
            view.setWillNotCacheDrawing(willNotCache);
            view.setDrawingCacheBackgroundColor(color);
            return bitmap;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getBitmapFromView2");
        }
        return null;
    }

    // =

    /**
     * 计算视频宽高大小，视频比例xxx*xxx按屏幕比例放大或者缩小
     * @param width  高度比例
     * @param height 宽度比例
     * @return 返回宽高 0 = 宽，1 = 高
     */
    public static int[] reckonVideoWidthHeight(final float width, final float height) {
        try {
            // 获取屏幕宽度
            int sWidth = ScreenUtils.getScreenWidth();
            // 判断宽度比例
            float wRatio = 0.0f;
            // 计算比例
            wRatio = (sWidth - width) / width;
            // 等比缩放
            int nWidth = sWidth;
            int nHeight = (int) (height * (wRatio + 1));
            return new int[]{nWidth, nHeight};
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "reckonVideoWidthHeight");
        }
        return null;
    }

    /**
     * 根据需求的宽和高以及图片实际的宽和高计算SampleSize
     * @param options
     * @param targetWidth
     * @param targetHeight
     * @return
     */
    public static int caculateInSampleSize(final BitmapFactory.Options options, final int targetWidth, final int targetHeight) {
        if (options == null) return 0;

        int width = options.outWidth;
        int height = options.outHeight;

        int inSampleSize = 1;
        if (width > targetWidth || height > targetHeight) {
            int widthRadio = Math.round(width * 1.0f / targetWidth);
            int heightRadio = Math.round(height * 1.0f / targetHeight);
            inSampleSize = Math.max(widthRadio, heightRadio);
        }
        return inSampleSize;
    }

    // = ImageView 相关 =

    /**
     * 根据ImageView获适当的压缩的宽和高
     * @param imageView
     * @return
     */
    public static int[] getImageViewSize(final ImageView imageView) {
        int[] imgSize = new int[]{0, 0};
        if (imageView == null) return imgSize;
        // =
        DisplayMetrics displayMetrics = imageView.getContext().getResources().getDisplayMetrics();
        LayoutParams lp = imageView.getLayoutParams();
        // 获取 imageView 的实际宽度
        int width = imageView.getWidth();
        if (width <= 0) {
            width = lp.width; // 获取 imageView 在layout中声明的宽度
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
            height = lp.height; // 获取 imageView 在layout中声明的宽度
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

    /**
     * 获取图片宽度高度(不加载解析图片)
     * @param filePath
     * @return
     */
    public static int[] getImageWidthHeight(final String filePath) {
        if (TextUtils.isEmpty(filePath)) return null;
        File file = new File(filePath);
        if (file.isDirectory() || !file.exists()) return null;

        BitmapFactory.Options options = new BitmapFactory.Options();
        // 不解析图片信息
        options.inJustDecodeBounds = true;
        // 此时返回的bitmap为null
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
        // options.outHeight为原始图片的高
        return new int[]{options.outWidth, options.outHeight};
    }
}
