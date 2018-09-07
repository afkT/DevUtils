package dev.utils.app.toast;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import dev.DevUtils;

/**
 * detail: 自定义Toast 使用View 工具类
 * Created by Ttt
 */
public final class ToastViewUtils {

	private ToastViewUtils() {
	}

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

	/**
	 * 最终显示Toast方法
	 * @param context
	 * @param view
	 * @param duration
	 * @param isNewToast
	 */
	public static void showToast(Context context, View view, int duration, boolean isNewToast) {
		// 防止上下文为null
		if (context == null){
			return;
		} else if (view == null) { // 防止显示的View 为null
			return;
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
				// 显示Toast
				toast.show();
			} else {
				if (mToast == null){
					// 生成新的Toast
					mToast = new Toast(context);
				}
				// 设置显示的View
				mToast.setView(view);
				// 设置显示的时间
				mToast.setDuration(duration);
				// 显示Toast
				mToast.show();
			}
		} catch (Exception e){
		}
	}
}
