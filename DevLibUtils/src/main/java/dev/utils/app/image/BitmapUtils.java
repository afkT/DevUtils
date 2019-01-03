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
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
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
 * Created by Ttt
 */
public final class BitmapUtils {

	private BitmapUtils() {
	}

	// 日志Tag
	private static final String TAG = BitmapUtils.class.getSimpleName();

    /**
     * 图片着色
     * @param drawable
     * @param tintColor
     * @return
     */
    public static Drawable tintIcon(@NonNull Drawable drawable, @ColorInt int tintColor) {
        if (drawable != null){
            try {
                drawable.setColorFilter(tintColor, PorterDuff.Mode.SRC_IN);
            } catch (Exception e){
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
    public static Drawable tint9PatchDrawableFrame(Context context, @ColorInt int tintColor, @DrawableRes int id) {
        try {
            final NinePatchDrawable toastDrawable = (NinePatchDrawable) getDrawable(context, id);
            return tintIcon(toastDrawable, tintColor);
        } catch (Exception e){
            LogPrintUtils.eTag(TAG, e, "tint9PatchDrawableFrame");
        }
        return null;
    }

    /**
     * 设置背景
     * @param view
     * @param drawable
     */
    public static void setBackground(@NonNull View view, Drawable drawable) {
        if (view != null){
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
    public static Drawable getDrawable(Context context, @DrawableRes int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            return context.getDrawable(id);
        else
            return context.getResources().getDrawable(id);
    }

	/**
	 * 通过Resources获取Bitmap
	 * @param context
	 * @param resId
	 * @return
	 */
	public static Bitmap getBitmapFromResources(Context context, int resId) {
		Resources res = context.getResources();
		return BitmapFactory.decodeResource(res, resId);
	}

	/**
	 * 通过Resources获取Drawable
	 * @param context
	 * @param resId
	 * @return
	 */
	public static Drawable getDrawableFromResources(Context context, int resId) {
		Resources res = context.getResources();
		return res.getDrawable(resId);
	}

	/**
	 * 获取本地SDCard 图片
	 * @param fPath 图片地址
	 * @return
	 */
	public static Bitmap getSDCardBitmapStream(String fPath) {
		try {
			FileInputStream fis = new FileInputStream(new File(fPath));//文件输入流
			Bitmap bmp = BitmapFactory.decodeStream(fis);
			return bmp;
		} catch (Exception e) {
			LogPrintUtils.eTag(TAG, e, "getSDCardBitmapStream");
		}
		return null;
	}

	/**
	 * 获取本地SDCard 图片
	 * @param fPath 图片地址
	 * @return
	 */
	public static Bitmap getSDCardBitmapFile(String fPath) {
		try {
			Bitmap bmp = BitmapFactory.decodeFile(fPath);
			return bmp;
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
		return BitmapFactory.decodeStream(is);
	}

	// =====

	/**
	 * Bitmay 转换成byte数组
	 * @param bitmap
	 * @return
	 */
	public static byte[] bitmapToByte(Bitmap bitmap) {
		return bitmapToByte(bitmap, 100, Bitmap.CompressFormat.PNG);
	}

	/**
	 * Bitmay 转换成byte数组
	 * @param bitmap
	 * @param format
	 * @return
	 */
	public static byte[] bitmapToByte(Bitmap bitmap, Bitmap.CompressFormat format) {
		return bitmapToByte(bitmap, 100, format);
	}

	/**
	 * Bitmay 转换成byte数组
	 * @param bitmap
	 * @param quality
	 * @param format
	 * @return
	 */
	public static byte[] bitmapToByte(Bitmap bitmap, int quality, Bitmap.CompressFormat format) {
		if (bitmap == null) {
			return null;
		}
		ByteArrayOutputStream o = new ByteArrayOutputStream();
		bitmap.compress(format, quality, o);
		return o.toByteArray();
	}

	// =

	/**
	 * Drawable 转换成 byte数组
	 * @param drawable
	 * @return
	 */
	public static byte[] drawableToByte(Drawable drawable) {
		return bitmapToByte(drawableToBitmap(drawable));
	}

	/**
	 * Drawable 转换成 byte数组
	 * @param drawable
	 * @param format
	 * @return
	 */
	public static byte[] drawableToByte(Drawable drawable, Bitmap.CompressFormat format) {
		return bitmapToByte(drawableToBitmap(drawable), format);
	}

	/**
	 * Drawable 转换成 byte数组
	 * @param drawable
	 * @return
	 */
	public static byte[] drawableToByte2(Drawable drawable) {
		return drawable == null ? null : bitmapToByte(drawable2Bitmap(drawable));
	}

	/**
	 * Drawable 转换成 byte数组
	 * @param drawable
	 * @param format
	 * @return
	 */
	public static byte[] drawableToByte2(Drawable drawable, Bitmap.CompressFormat format) {
		return drawable == null ? null : bitmapToByte(drawable2Bitmap(drawable), format);
	}

	// ==

	/**
	 * byte 数组转换为Bitmap
	 * @param bytes
	 * @return
	 */
	public static Bitmap byteToBitmap(byte[] bytes) {
		return (bytes == null || bytes.length == 0) ? null : BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
	}

	/**
	 * Drawable 转换成 Bitmap
	 * @param drawable
	 * @return
	 */
	public static Bitmap drawableToBitmap(Drawable drawable) {
		return drawable == null ? null : ((BitmapDrawable) drawable).getBitmap();
	}

	/**
	 * Bitmap 转换成 Drawable
	 * @param bitmap
	 * @return
	 */
	public static Drawable bitmapToDrawable(Bitmap bitmap) {
		return bitmap == null ? null : new BitmapDrawable(AppUtils.getResources(), bitmap);
	}

	/**
	 * byte数组转换成Drawable
	 * @param bytes
	 * @return
	 */
	public static Drawable byteToDrawable(byte[] bytes) {
		return bitmapToDrawable(byteToBitmap(bytes));
	}

	/**
	 * Drawable 转换 Bitmap
	 * @param drawable The drawable.
	 * @return bitmap
	 */
	public static Bitmap drawable2Bitmap(final Drawable drawable) {
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
	 * @param drawable The drawable.
	 * @return bitmap
	 */
	public static Bitmap drawable3Bitmap(final Drawable drawable) {
		if (drawable == null){
			return null;
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

	// ===

	/**
	 * 保存图片到SD卡 - JPEG
	 * @param bitmap 需要保存的数据
	 * @param path 保存路径
	 * @param quality 压缩比例
	 */
	public static boolean saveBitmapToSDCardJPEG(Bitmap bitmap, String path, int quality) {
		return saveBitmapToSDCard(bitmap, path, Bitmap.CompressFormat.JPEG, quality);
	}

	/**
	 * 保存图片到SD卡 - PNG
	 * @param bitmap 需要保存的数据
	 * @param path 保存路径
	 */
	public static boolean saveBitmapToSDCardPNG(Bitmap bitmap, String path) {
		return saveBitmapToSDCard(bitmap, path, Bitmap.CompressFormat.PNG, 80);
	}

	/**
	 * 保存图片到SD卡 - PNG
	 * @param bitmap 需要保存的数据
	 * @param path 保存路径
	 * @param quality 压缩比例
	 */
	public static boolean saveBitmapToSDCardPNG(Bitmap bitmap, String path, int quality) {
		return saveBitmapToSDCard(bitmap, path, Bitmap.CompressFormat.PNG, quality);
	}

	/**
	 * 保存图片到SD卡 - PNG
	 * @param bitmap 需要保存的数据
	 * @param path 保存路径
	 * @param quality 压缩比例
	 * @return
	 */
	public static boolean saveBitmapToSDCard(Bitmap bitmap, String path, int quality) {
		return saveBitmapToSDCard(bitmap, path, Bitmap.CompressFormat.PNG, quality);
	}

	/**
	 * 保存图片到SD卡
	 * @param bitmap 图片资源
	 * @param path 保存路径
	 * @param format 如 Bitmap.CompressFormat.PNG
	 * @param quality 保存的图片质量， 100 则完整质量不压缩保存
	 * @return 保存结果
	 */
	public static boolean saveBitmapToSDCard(Bitmap bitmap, String path, Bitmap.CompressFormat format, int quality) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(path);
			if (fos != null) {
				bitmap.compress(format, quality, fos);
				fos.close();
			}
		} catch (Exception e) {
			LogPrintUtils.eTag(TAG, e, "saveBitmapToSDCard");
			return false;
		} finally {
			if(fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
				}
			}
		}
		return true;
	}

	// ==

	/**
	 * 将Drawable转化为Bitmap
	 * @param drawable Drawable
	 * @return Bitmap
	 */
	public static Bitmap getBitmapFromDrawable(Drawable drawable) {
		try {
			int width = drawable.getIntrinsicWidth();
			int height = drawable.getIntrinsicHeight();
			Bitmap bitmap = Bitmap.createBitmap(width, height, drawable .getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
			Canvas canvas = new Canvas(bitmap);
			drawable.setBounds(0, 0, width, height);
			drawable.draw(canvas);
			return bitmap;
		} catch (Exception e){
			LogPrintUtils.eTag(TAG, e, "getBitmapFromDrawable");
		}
		return null;
	}

	/**
	 * 通过View, 获取背景转换Bitmap
	 * @param view The view.
	 * @return bitmap
	 */
	public static Bitmap bitmapToViewBackGround(View view) {
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
		} catch (Exception e){
			LogPrintUtils.eTag(TAG, e, "bitmapToViewBackGround");
		}
		return null;
	}

	/**
	 * 通过View, 获取Bitmap -> 绘制整个View
	 * @param view View
	 * @return Bitmap
	 */
	public static Bitmap getBitmapFromView(View view) {
		if (view == null) return null;
		try {
			Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(bitmap);
			view.layout(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
			view.draw(canvas);
			return bitmap;
		} catch (Exception e){
			LogPrintUtils.eTag(TAG, e, "getBitmapFromView");
		}
		return null;
	}

	/**
	 * 把一个View的对象转换成bitmap
	 * @param view View
	 * @return Bitmap
	 */
	public static Bitmap getBitmapFromView2(View view) {
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
		} catch (Exception e){
			LogPrintUtils.eTag(TAG, e, "getBitmapFromView2");
		}
		return null;
	}

	// ===

	/**
	 * 计算视频宽高大小，视频比例xxx*xxx按屏幕比例放大或者缩小
	 * @param width 高度比例
	 * @param height 宽度比例
	 * @return 返回宽高 0 = 宽，1 = 高
	 */
	public static int[] reckonVideoWidthHeight(float width, float height) {
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
			return new int []{ nWidth, nHeight };
		} catch (Exception e) {
			LogPrintUtils.eTag(TAG, e, "reckonVideoWidthHeight");
		}
		return null;
	}

	/**
	 * 根据需求的宽和高以及图片实际的宽和高计算SampleSize
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static int caculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		int width = options.outWidth;
		int height = options.outHeight;

		int inSampleSize = 1;
		if (width > reqWidth || height > reqHeight) {
			int widthRadio = Math.round(width * 1.0f / reqWidth);
			int heightRadio = Math.round(height * 1.0f / reqHeight);
			inSampleSize = Math.max(widthRadio, heightRadio);
		}
		return inSampleSize;
	}

	// ================  ImageView 相关  ==================

	/**
	 * 根据ImageView获适当的压缩的宽和高
	 * @param imageView
	 * @return
	 */
	public static int[] getImageViewSize(ImageView imageView) {
		int[] imgSize = new int[] {0, 0};
		// --
		DisplayMetrics displayMetrics = imageView.getContext().getResources().getDisplayMetrics();
		LayoutParams lp = imageView.getLayoutParams();
		// 获取imageview的实际宽度
		int width = imageView.getWidth();
		if (width <= 0) {
			width = lp.width; // 获取imageview在layout中声明的宽度
		}
		if (width <= 0) {
			// width = imageView.getMaxWidth(); // 检查最大值
			width = getImageViewFieldValue(imageView, "mMaxWidth");
		}
		if (width <= 0) {
			width = displayMetrics.widthPixels;
		}
		// --
		// 获取imageview的实际高度
		int height = imageView.getHeight();
		if (height <= 0) {
			height = lp.height; // 获取imageview在layout中声明的宽度
		}
		if (height <= 0) {
			height = getImageViewFieldValue(imageView, "mMaxHeight"); // 检查最大值
		}
		if (height <= 0) {
			height = displayMetrics.heightPixels;
		}
		// --
		imgSize[0] = width;
		imgSize[1] = height;
		return imgSize;
	}

	/**
	 * 通过反射获取imageview的某个属性值
	 * @param object
	 * @param fieldName
	 * @return
	 */
	private static int getImageViewFieldValue(Object object, String fieldName) {
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
	 * @param path
	 * @return
	 */
	public static int[] getImageWidthHeight(String path){
		BitmapFactory.Options options = new BitmapFactory.Options();
		// 不解析图片信息
		options.inJustDecodeBounds = true;
		// 此时返回的bitmap为null
		Bitmap bitmap = BitmapFactory.decodeFile(path, options);
		// options.outHeight为原始图片的高
		return new int[]{options.outWidth,options.outHeight};
	}
}
