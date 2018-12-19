package dev.utils.app.toast;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: 自定义Toast工具类
 * Created by Ttt
 */
public final class ToastUtils {

	private ToastUtils() {
	}

	// 日志TAG
	private static final String TAG = ToastUtils.class.getSimpleName();

	/** 内部持有唯一 */
	private static Toast mToast = null;
	// 判断是否使用 Handler
	private static boolean isHandler = true;
	// 内部 Handler
	private static final Handler sHandler = new Handler(Looper.getMainLooper());

	/**
	 * 设置是否使用 Handler 显示 Toast
	 * @param isHandler
	 */
	public static void setHandler(boolean isHandler) {
		ToastUtils.isHandler = isHandler;
	}

	/**
	 * 获取内部唯一Toast对象
	 * @return
	 */
	public static Toast getSignleToast(){
		return mToast;
	}

	// =====================
	// === 统一显示Toast ===
	// =====================

	// ========================
	// == Toast.LENGTH_SHORT ==
	// ========================

	/**
	 * 显示 一个短Toast
	 * @param text
	 */
	public static void showShort(String text) {
		showShort(null, text);
	}

	/**
	 * 显示 一个短Toast
	 * @param context
	 * @param text
	 */
	public static void showShort(Context context, String text) {
		handlerToastStr(true, context, text, Toast.LENGTH_SHORT);
	}

	/**
	 * 显示 一个短Toast
	 * @param text
	 * @param objs
	 */
	public static void showShort(String text, Object... objs) {
		showShort(null, text, objs);
	}

	/**
	 * 显示 一个短Toast
	 * @param context
	 * @param text
	 * @param objs
	 */
	public static void showShort(Context context, String text, Object... objs) {
		handlerToastStr(true, context, text, Toast.LENGTH_SHORT, objs);
	}

	/**
	 * 显示 一个短Toast
	 * @param resId
	 */
	public static void showShort(int resId) {
		showShort(null, resId);
	}

	/**
	 * 显示 一个短Toast
	 * @param context
	 * @param resId
	 */
	public static void showShort(Context context, int resId) {
		handlerToastRes(true, context, resId, Toast.LENGTH_SHORT);
	}

	/**
	 * 显示 一个短Toast
	 * @param resId
	 * @param objs
	 */
	public static void showShort(int resId, Object... objs) {
		showShort(null, resId, objs);
	}

	/**
	 * 显示 一个短Toast
	 * @param context
	 * @param resId
	 * @param objs
	 */
	public static void showShort(Context context, int resId, Object... objs) {
		handlerToastRes(true, context, resId, Toast.LENGTH_SHORT, objs);
	}

	// ========================
	// == Toast.LENGTH_LONG ===
	// ========================

	/**
	 * 显示 一个长Toast
	 * @param text
	 */
	public static void showLong(String text) {
		showLong(null, text);
	}

	/**
	 * 显示 一个长Toast
	 * @param context
	 * @param text
	 */
	public static void showLong(Context context, String text) {
		handlerToastStr(true, context, text, Toast.LENGTH_LONG);
	}

	/**
	 * 显示 一个长Toast
	 * @param text
	 * @param objs
	 */
	public static void showLong(String text, Object... objs) {
		showLong(null, text, objs);
	}

	/**
	 * 显示 一个长Toast
	 * @param context
	 * @param text
	 * @param objs
	 */
	public static void showLong(Context context, String text, Object... objs) {
		handlerToastStr(true, context, text, Toast.LENGTH_LONG, objs);
	}

	/**
	 * 显示 一个长Toast
	 * @param resId
	 */
	public static void showLong(int resId) {
		showLong(null, resId);
	}

	/**
	 * 显示 一个长Toast
	 * @param context
	 * @param resId
	 */
	public static void showLong(Context context, int resId) {
		handlerToastRes(true, context, resId, Toast.LENGTH_LONG);
	}

	/**
	 * 显示 一个长Toast
	 * @param resId
	 * @param objs
	 */
	public static void showLong(int resId, Object...objs) {
		showLong(null, resId, objs);
	}

	/**
	 * 显示 一个长Toast
	 * @param context
	 * @param resId
	 * @param objs
	 */
	public static void showLong(Context context, int resId, Object...objs) {
		handlerToastRes(true, context, resId, Toast.LENGTH_LONG, objs);
	}

	// =======================
	// ==== 最终Toast方法 ====
	// =======================

	/**
	 * 显示Toast
	 * @param resId
	 * @param duration
	 */
	public static void showToast(int resId, int duration) {
		showToast(null, resId, duration);
	}

	/**
	 * 显示Toast
	 * @param context
	 * @param resId
	 * @param duration
	 */
	public static void showToast(Context context, int resId, int duration) {
		handlerToastRes(true, context, resId, duration);
	}

	/**
	 * 显示Toast
	 * @param text
	 * @param duration
	 */
	public static void showToast(String text, int duration) {
		showToast(null, text, duration);
	}

	/**
	 * 显示Toast
	 * @param context
	 * @param text
	 * @param duration
	 */
	public static void showToast(Context context, String text, int duration) {
		showToast(true, context, text, duration);
	}

	// ==

	/**
	 * 最终显示的Toast方法
	 * @param isSingle
	 * @param context
	 * @param text
	 * @param duration
	 * @Toast
	 */
	private static void showToast(final boolean isSingle, final Context context, final String text, final int duration) {
		if (isHandler){
			sHandler.post(new Runnable() {
				@Override
				public void run() {
					try {
						newToast(isSingle, context, text, duration).show();
					} catch (Exception e) {
						LogPrintUtils.eTag(TAG, e, "showToast");
					}
				}
			});
		} else {
			try {
				newToast(isSingle, context, text, duration).show();
			} catch (Exception e) {
				LogPrintUtils.eTag(TAG, e, "showToast");
			}
		}
	}

	/**
	 * 获取一个新的 Toast
	 * @param isSingle
	 * @param context
	 * @param text
	 * @param duration
	 * @return
	 */
	public static Toast newToast(boolean isSingle, Context context, String text, int duration){
		if (context == null){
			context = DevUtils.getContext();
		}
		// 尽心设置为null, 便于提示排查
		if (text == null) {
			text = "null";
		}
		// 判断是否显示唯一, 单独共用一个
		if (isSingle) {
			try {
				if (mToast != null) {
					mToast.setDuration(duration);
					mToast.setText(text);
				} else {
					if (context != null) {
						// 解决 MIUI 会显示应用名称问题
						mToast = Toast.makeText(context, null, duration);
						mToast.setText(text);
					}
				}
			} catch (Exception e){
				LogPrintUtils.eTag(TAG, e, "newToast");
			}
			return mToast;
		} else {
			Toast toast = null;
			try {
				// 解决 MIUI 会显示应用名称问题
				toast = Toast.makeText(context, null, duration);
				toast.setText(text);
			} catch (Exception e){
				LogPrintUtils.eTag(TAG, e, "newToast");
			}
			return toast;
		}
	}

	// =====

	/**
	 * 处理 R.string 资源Toast的格式化
	 * @param isSingle 是否单独共用显示一个
	 * @param context
	 * @param resId
	 * @param duration
	 * @param objs
	 */
	private static void handlerToastRes(boolean isSingle, Context context, int resId, int duration, Object... objs) {
		if (context == null){
			context = DevUtils.getContext();
		}
		if (context != null) {
			String text;
			try {
				// 获取字符串并且进行格式化
				if (objs != null && objs.length != 0) {
					text = context.getString(resId, objs);
				} else {
					text = context.getString(resId);
				}
			} catch (Exception e) {
				LogPrintUtils.eTag(TAG, e, "handlerToastRes");
				text = e.getMessage();
			}
			showToast(isSingle, context, text, duration);
		}
	}

	/**
	 * 处理字符串Toast的格式化
	 * @param context
	 * @param text
	 * @param duration
	 * @param objs
	 */
	private static void handlerToastStr(boolean isSingle, Context context, String text, int duration, Object... objs) {
		if (context == null){
			context = DevUtils.getContext();
		}
		// 防止 Context 为null
		if (context != null) {
			// 表示需要格式化字符串,只是为了减少 format步骤,增加判断，为null不影响
			if (objs != null && objs.length != 0) {
				if (text != null) { // String.format() 中的 objs 可以为null,但是 text不能为null
					try {
						showToast(isSingle, context, String.format(text, objs), duration);
					} catch (Exception e){
						LogPrintUtils.eTag(TAG, e, "handlerToastStr");
						showToast(isSingle, context, e.getMessage(), duration);
					}
				} else {
					showToast(isSingle, context, text, duration);
				}
			} else {
				showToast(isSingle, context, text, duration);
			}
		}
	}

	// =====================
	// == 非统一显示Toast ==
	// =====================

	// ========================
	// == Toast.LENGTH_SHORT ==
	// ========================

	/**
	 * 显示 一个新的短Toast
	 * @param text
	 */
	public static void showShortNew(String text) {
		showShortNew(null, text);
	}

	/**
	 * 显示 一个新的短Toast
	 * @param context
	 * @param text
	 */
	public static void showShortNew(Context context, String text) {
		handlerToastStr(false, context, text, Toast.LENGTH_SHORT);
	}

	/**
	 * 显示 一个新的短Toast
	 * @param text
	 * @param objs
	 */
	public static void showShortNew(String text, Object... objs) {
		showShortNew(null, text, objs);
	}

	/**
	 * 显示 一个新的短Toast
	 * @param context
	 * @param text
	 * @param objs
	 */
	public static void showShortNew(Context context, String text, Object... objs) {
		handlerToastStr(false, context, text, Toast.LENGTH_SHORT, objs);
	}

	/**
	 * 显示 一个新的短Toast
	 * @param resId
	 */
	public static void showShortNew(int resId) {
		showShortNew(null, resId);
	}

	/**
	 * 显示 一个新的短Toast
	 * @param context
	 * @param resId
	 */
	public static void showShortNew(Context context, int resId) {
		handlerToastRes(false, context, resId, Toast.LENGTH_SHORT);
	}

	/**
	 * 显示 一个新的短Toast
	 * @param resId
	 * @param objs
	 */
	public static void showShortNew(int resId, Object... objs) {
		showShortNew(null, resId, objs);
	}

	/**
	 * 显示 一个新的短Toast
	 * @param context
	 * @param resId
	 * @param objs
	 */
	public static void showShortNew(Context context, int resId, Object... objs) {
		handlerToastRes(false, context, resId, Toast.LENGTH_SHORT, objs);
	}

	// ========================
	// == Toast.LENGTH_LONG ===
	// ========================

	/**
	 * 显示 一个新的长Toast
	 * @param text
	 */
	public static void showLongNew(String text) {
		showLongNew(null, text);
	}

	/**
	 * 显示 一个新的长Toast
	 * @param context
	 * @param text
	 */
	public static void showLongNew(Context context, String text) {
		handlerToastStr(false, context, text, Toast.LENGTH_LONG);
	}

	/**
	 * 显示 一个新的长Toast
	 * @param text
	 * @param objs
	 */
	public static void showLongNew(String text, Object... objs) {
		showLongNew(null, text, objs);
	}

	/**
	 * 显示 一个新的长Toast
	 * @param context
	 * @param text
	 * @param objs
	 */
	public static void showLongNew(Context context, String text, Object... objs) {
		handlerToastStr(false, context, text, Toast.LENGTH_LONG, objs);
	}

	/**
	 * 显示 一个新的长Toast
	 * @param resId
	 */
	public static void showLongNew(int resId) {
		showLongNew(null, resId);
	}

	/**
	 * 显示 一个新的长Toast
	 * @param context
	 * @param resId
	 */
	public static void showLongNew(Context context, int resId) {
		handlerToastRes(false, context, resId, Toast.LENGTH_LONG);
	}

	/**
	 * 显示 一个新的长Toast
	 * @param resId
	 * @param objs
	 */
	public static void showLongNew(int resId, Object...objs) {
		showLongNew(null, resId, objs);
	}

	/**
	 * 显示 一个新的长Toast
	 * @param context
	 * @param resId
	 * @param objs
	 */
	public static void showLongNew(Context context, int resId, Object...objs) {
		handlerToastRes(false, context, resId, Toast.LENGTH_LONG, objs);
	}

	// =======================
	// ==== 最终Toast方法 ====
	// =======================

	/**
	 * 显示新的 Toast
	 * @param resId
	 * @param duration
	 */
	public static void showToastNew(int resId, int duration) {
		showToastNew(null, resId, duration);
	}

	/**
	 * 显示新的 Toast
	 * @param context
	 * @param resId
	 * @param duration
	 */
	public static void showToastNew(Context context, int resId, int duration) {
		handlerToastRes(false, context, resId, duration);
	}

	/**
	 * 显示新的 Toast
	 * @param text
	 * @param duration
	 */
	public static void showToastNew(String text, int duration) {
		showToastNew(null, text, duration);
	}

	/**
	 * 显示新的 Toast
	 * @param context
	 * @param text
	 * @param duration
	 */
	public static void showToastNew(Context context, String text, int duration) {
		showToast(false, context, text, duration);
	}

	// =======================
	// ==== 最终Toast方法 ====
	// =======================

	/**
	 * 最终显示Toast方法
	 * @param view
	 * @param duration
	 * @param isNewToast
	 */
	public static void showToast(View view, int duration, boolean isNewToast) {
		showToast(null, view, duration, isNewToast);
	}

	/**
	 * 最终显示Toast方法
	 * @param context
	 * @param view
	 * @param duration
	 * @param isNewToast
	 */
	public static void showToast(final Context context, final View view, final int duration, final boolean isNewToast) {
		if (isHandler){
			sHandler.post(new Runnable() {
				@Override
				public void run() {
					try {
						newToast(context, view, duration, isNewToast).show();
					} catch (Exception e) {
						LogPrintUtils.eTag(TAG, e, "showToast");
					}
				}
			});
		} else {
			try {
				newToast(context, view, duration, isNewToast).show();
			} catch (Exception e) {
				LogPrintUtils.eTag(TAG, e, "showToast");
			}
		}
	}


	/**
	 * 获取一个新的 Toast
	 * @param context
	 * @param view
	 * @param duration
	 * @param isNewToast
	 * @return
	 */
	public static Toast newToast(Context context, View view, int duration, boolean isNewToast){
		if (context == null){
			context = DevUtils.getContext();
		}
		// 防止 Context 为null
		if (context == null){
			return null;
		} else if (view == null) { // 防止显示的View 为null
			return null;
		}
		try {
			// 判断是否显示新的 Toast
			if (isNewToast){
				// 生成新的Toast
				Toast toast = new Toast(context);
				// 设置显示的View
				toast.setView(view);
				// 设置显示的时间
				toast.setDuration(duration);
				return toast;
			} else {
				if (mToast == null){
					// 生成新的Toast
					mToast = new Toast(context);
				}
				// 设置显示的View
				mToast.setView(view);
				// 设置显示的时间
				mToast.setDuration(duration);
				return mToast;
			}
		} catch (Exception e){
			LogPrintUtils.eTag(TAG, e, "newToast");
		}
		return null;
	}
}
