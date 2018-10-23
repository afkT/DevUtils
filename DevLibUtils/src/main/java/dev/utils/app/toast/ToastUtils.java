package dev.utils.app.toast;

import android.content.Context;
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

	/**
	 * 内部处理防止Context 为null奔溃问题
	 * @return
	 */
	private static Context getContext(Context context){
		if (context != null){
			return context;
		} else {
			// 设置全局Context
			return DevUtils.getContext();
		}
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

	public static Toast showShort(Context context, String text) {
		return handlerToastStr(true, context, text, Toast.LENGTH_SHORT);
	}

	public static Toast showShort(Context context, String text, Object... objs) {
		return handlerToastStr(true, context, text, Toast.LENGTH_SHORT, objs);
	}

	public static Toast showShort(Context context, int resId) {
		return handlerToastRes(true, context, resId, Toast.LENGTH_SHORT);
	}

	public static Toast showShort(Context context, int resId, Object... objs) {
		return handlerToastRes(true, context, resId, Toast.LENGTH_SHORT, objs);
	}

	// ========================
	// == Toast.LENGTH_LONG ===
	// ========================

	public static Toast showLong(Context context, String text) {
		return handlerToastStr(true, context, text, Toast.LENGTH_LONG);
	}

	public static Toast showLong(Context context, String text, Object... objs) {
		return handlerToastStr(true, context, text, Toast.LENGTH_LONG, objs);
	}

	public static Toast showLong(Context context, int resId) {
		return handlerToastRes(true, context, resId, Toast.LENGTH_LONG);
	}

	public static Toast showLong(Context context, int resId, Object...objs) {
		return handlerToastRes(true, context, resId, Toast.LENGTH_LONG, objs);
	}

	// =======================
	// ==== 最终Toast方法 ====
	// =======================

	/**
	 * 显示Toast
	 * @param context
	 * @param resId
	 * @param duration
	 */
	public static Toast showToast(Context context, int resId, int duration) {
		return handlerToastRes(true, context, resId, duration);
	}

	/**
	 * 显示Toast
	 * @param context
	 * @param text
	 * @param duration
	 */
	public static Toast showToast(Context context, String text, int duration) {
		return showToast(true, context, text, duration);
	}

	// ==

	/**
	 * 最终显示的Toast方法
	 * @param isSingle
	 * @param context
	 * @param text
	 * @param duration
	 * @return Toast
	 */
	private static Toast showToast(boolean isSingle, Context context, String text, int duration) {
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
						mToast = Toast.makeText(context, text, duration);
					}
				}
				// 处理后,不为null,才进行处理
				if (mToast != null) {
					mToast.show();
				}
			} catch (Exception e){
				LogPrintUtils.eTag(TAG, e, "showToast");
			}
			return mToast;
		} else {
			Toast toast = null;
			try {
				toast = Toast.makeText(context, text, duration);
				toast.show();
			} catch (Exception e){
				LogPrintUtils.eTag(TAG, e, "showToast");
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
	private static Toast handlerToastRes(boolean isSingle, Context context, int resId, int duration, Object... objs) {
		if (getContext(context) != null) {
			String text;
			try {
				// 获取字符串并且进行格式化
				if (objs != null && objs.length != 0) {
					text = getContext(context).getString(resId, objs);
				} else {
					text = getContext(context).getString(resId);
				}
			} catch (Exception e) {
				LogPrintUtils.eTag(TAG, e, "handlerToastRes");
				text = e.getMessage();
			}
			return showToast(isSingle, context, text, duration);
		}
		return null;
	}

	/**
	 * 处理字符串Toast的格式化
	 * @param context
	 * @param text
	 * @param duration
	 * @param objs
	 */
	private static Toast handlerToastStr(boolean isSingle, Context context, String text, int duration, Object... objs) {
		// 防止 Context 为null
		if (context != null) {
			// 表示需要格式化字符串,只是为了减少 format步骤,增加判断，为null不影响
			if (objs != null && objs.length != 0) {
				if (text != null) { // String.format() 中的 objs 可以为null,但是 text不能为null
					try {
						return showToast(isSingle, context, String.format(text, objs), duration);
					} catch (Exception e){
						LogPrintUtils.eTag(TAG, e, "handlerToastStr");
						return showToast(isSingle, context, e.getMessage(), duration);
					}
				} else {
					return showToast(isSingle, context, text, duration);
				}
			} else {
				return showToast(isSingle, context, text, duration);
			}
		}
		return null;
	}

	// =====================
	// == 非统一显示Toast ==
	// =====================

	// ========================
	// == Toast.LENGTH_SHORT ==
	// ========================

	public static Toast showShortNew(Context context, String text) {
		return handlerToastStr(false, context, text, Toast.LENGTH_SHORT);
	}

	public static Toast showShortNew(Context context, String text, Object... objs) {
		return handlerToastStr(false, context, text, Toast.LENGTH_SHORT, objs);
	}

	public static Toast showShortNew(Context context, int resId) {
		return handlerToastRes(false, context, resId, Toast.LENGTH_SHORT);
	}

	public static Toast showShortNew(Context context, int resId, Object... objs) {
		return handlerToastRes(false, context, resId, Toast.LENGTH_SHORT, objs);
	}

	// ========================
	// == Toast.LENGTH_LONG ===
	// ========================

	public static Toast showLongNew(Context context, String text) {
		return handlerToastStr(false, context, text, Toast.LENGTH_LONG);
	}

	public static Toast showLongNew(Context context, String text, Object... objs) {
		return handlerToastStr(false, context, text, Toast.LENGTH_LONG, objs);
	}

	public static Toast showLongNew(Context context, int resId) {
		return handlerToastRes(false, context, resId, Toast.LENGTH_LONG);
	}

	public static Toast showLongNew(Context context, int resId, Object...objs) {
		return handlerToastRes(false, context, resId, Toast.LENGTH_LONG, objs);
	}

	// =======================
	// ==== 最终Toast方法 ====
	// =======================

	/**
	 * 显示新的 Toast
	 * @param context
	 * @param resId
	 * @param duration
	 */
	public static Toast showToastNew(Context context, int resId, int duration) {
		return handlerToastRes(false, context, resId, duration);
	}

	/**
	 * 显示新的 Toast
	 * @param context
	 * @param text
	 * @param duration
	 */
	public static Toast showToastNew(Context context, String text, int duration) {
		return showToast(false, context, text, duration);
	}
}
