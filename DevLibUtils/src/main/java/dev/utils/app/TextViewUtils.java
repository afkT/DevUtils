package dev.utils.app;

import android.app.Activity;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.ColorInt;
import android.support.annotation.IdRes;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import dev.utils.LogPrintUtils;

/**
 * detail: TextView 工具类
 * Created by Ttt
 */
public final class TextViewUtils {

    private TextViewUtils() {
    }

    // 日志TAG
    private static final String TAG = TextViewUtils.class.getSimpleName();

    /**
     * 获取TextView
     * @param view
     * @return
     */
    public static TextView getTextView(View view){
        if (view != null){
            try {
                return (TextView) view;
            } catch (Exception e){
                LogPrintUtils.eTag(TAG, e, "getTextView");
            }
        }
        return null;
    }

    /**
     * 获取TextView
     * @param view
     * @return
     */
    public static TextView getTextView(View view, @IdRes int id){
        if (view != null){
            try {
                return view.findViewById(id);
            } catch (Exception e){
                LogPrintUtils.eTag(TAG, e, "getTextView");
            }
        }
        return null;
    }

    /**
     * 获取TextView
     * @param activity
     * @return
     */
    public static TextView getTextView(Activity activity, @IdRes int id){
        if (activity != null){
            try {
                return activity.findViewById(id);
            } catch (Exception e){
                LogPrintUtils.eTag(TAG, e, "getTextView");
            }
        }
        return null;
    }

    /**
     * 获取TextView
     * @param window
     * @return
     */
    public static TextView getTextView(Window window, @IdRes int id){
        if (window != null){
            try {
                return window.findViewById(id);
            } catch (Exception e){
                LogPrintUtils.eTag(TAG, e, "getTextView");
            }
        }
        return null;
    }

    // =

    /**
     * 获取文本
     * @param textView
     * @return
     */
    public static String getText(TextView textView){
        if (textView != null){
            return textView.getText().toString();
        }
        return null;
    }

    /**
     * 获取文本
     * @param view
     * @return
     */
    public static String getText(View view) {
        if (view != null){
            try {
                return ((TextView) view).getText().toString();
            } catch (Exception e){
                LogPrintUtils.eTag(TAG, e, "getText");
            }
        }
        return null;
    }

    /**
     * 获取文本
     * @param view
     * @return
     */
    public static String getText(View view, @IdRes int id){
        if (view != null){
            try {
                return ((TextView) view.findViewById(id)).getText().toString();
            } catch (Exception e){
                LogPrintUtils.eTag(TAG, e, "getText");
            }
        }
        return null;
    }

    /**
     * 获取文本
     * @param activity
     * @return
     */
    public static String getText(Activity activity, @IdRes int id){
        if (activity != null){
            try {
                return ((TextView) activity.findViewById(id)).getText().toString();
            } catch (Exception e){
                LogPrintUtils.eTag(TAG, e, "getText");
            }
        }
        return null;
    }

    /**
     * 获取文本
     * @param window
     * @return
     */
    public static String getText(Window window, @IdRes int id){
        if (window != null){
            try {
                return ((TextView) window.findViewById(id)).getText().toString();
            } catch (Exception e){
                LogPrintUtils.eTag(TAG, e, "getText");
            }
        }
        return null;
    }

    // =

    /**
     * 设置是否加粗
     * @param view
     * @param isBold
     * @return
     */
    public static void setBold(View view, boolean isBold){
        if (view != null){
            if (view instanceof TextView){
                ((TextView) view).setTypeface(Typeface.defaultFromStyle(isBold ? Typeface.BOLD : Typeface.NORMAL));
            }
        }
    }

    /**
     * 设置是否加粗
     * @param view
     * @param typeface
     * @param isBold
     * @return
     */
    public static void setBold(View view, Typeface typeface, boolean isBold){
        if (view != null && typeface != null){
            if (view instanceof TextView){
                ((TextView) view).setTypeface(typeface, isBold ? Typeface.BOLD : Typeface.NORMAL);
            }
        }
    }

    /**
     * 设置字体颜色
     * @param view
     * @param color
     */
    public static void setTextColor(View view, @ColorInt int color){
        if (view != null){
            if (view instanceof TextView){
                ((TextView) view).setTextColor(color);
            }
        }
    }

    /**
     * 设置字体颜色
     * @param textView
     * @param color
     */
    public static void setTextColor(TextView textView, @ColorInt int color){
        if (textView != null){
            textView.setTextColor(color);
        }
    }

    /**
     * 设置字体颜色
     * @param view
     * @param id
     * @param color
     */
    public static void setTextColor(View view, @IdRes int id, @ColorInt int color){
        if (view != null){
            try {
                ((TextView) view.findViewById(id)).setTextColor(color);
            } catch (Exception e){
                LogPrintUtils.eTag(TAG, e, "setTextColor");
            }
        }
    }

    /**
     * 设置字体颜色
     * @param activity
     * @param id
     * @param color
     */
    public static void setTextColor(Activity activity, @IdRes int id, @ColorInt int color){
        if (activity != null){
            try {
                ((TextView) activity.findViewById(id)).setTextColor(color);
            } catch (Exception e){
                LogPrintUtils.eTag(TAG, e, "setTextColor");
            }
        }
    }

    /**
     * 设置字体颜色
     * @param window
     * @param id
     * @param color
     */
    public static void setTextColor(Window window, @IdRes int id, @ColorInt int color){
        if (window != null){
            try {
                ((TextView) window.findViewById(id)).setTextColor(color);
            } catch (Exception e){
                LogPrintUtils.eTag(TAG, e, "setTextColor");
            }
        }
    }

    // =

    /**
     * 设置内容
     * @param view
     * @param content
     */
    public static void setText(View view, String content){
        if (view != null && content != null){
            if (view instanceof TextView){
                ((TextView) view).setText(content);
            }
        }
    }

    /**
     * 设置内容
     * @param view
     * @param id
     * @param content
     */
    public static void setText(View view, @IdRes int id, String content){
        if (view != null){
            try {
                ((TextView) view.findViewById(id)).setText(content);
            } catch (Exception e){
                LogPrintUtils.eTag(TAG, e, "setText");
            }
        }
    }

    /**
     * 设置内容
     * @param activity
     * @param id
     * @param content
     */
    public static void setText(Activity activity, @IdRes int id, String content){
        if (activity != null){
            try {
                ((TextView) activity.findViewById(id)).setText(content);
            } catch (Exception e){
                LogPrintUtils.eTag(TAG, e, "setText");
            }
        }
    }

    /**
     * 设置内容
     * @param window
     * @param id
     * @param content
     */
    public static void setText(Window window, @IdRes int id, String content){
        if (window != null){
            try {
                ((TextView) window.findViewById(id)).setText(content);
            } catch (Exception e){
                LogPrintUtils.eTag(TAG, e, "setText");
            }
        }
    }

    // =

    /**
     * 设置 Html 内容
     * @param view
     * @param content
     */
    public static void setHtmlText(View view, String content){
        if (view != null && content != null){
            if (view instanceof TextView){
                ((TextView) view).setText(Html.fromHtml(content));
            }
        }
    }

    /**
     * 设置 Html 内容
     * @param view
     * @param id
     * @param content
     */
    public static void setHtmlText(View view, @IdRes int id, String content){
        if (view != null){
            try {
                ((TextView) view.findViewById(id)).setText(Html.fromHtml(content));
            } catch (Exception e){
                LogPrintUtils.eTag(TAG, e, "setHtmlText");
            }
        }
    }

    /**
     * 设置 Html 内容
     * @param activity
     * @param id
     * @param content
     */
    public static void setHtmlText(Activity activity, @IdRes int id, String content){
        if (activity != null){
            try {
                ((TextView) activity.findViewById(id)).setText(Html.fromHtml(content));
            } catch (Exception e){
                LogPrintUtils.eTag(TAG, e, "setHtmlText");
            }
        }
    }

    /**
     * 设置 Html 内容
     * @param window
     * @param id
     * @param content
     */
    public static void setHtmlText(Window window, @IdRes int id, String content){
        if (window != null){
            try {
                ((TextView) window.findViewById(id)).setText(Html.fromHtml(content));
            } catch (Exception e){
                LogPrintUtils.eTag(TAG, e, "setHtmlText");
            }
        }
    }

    // -

    /**
     * 设置内容
     * @param textView
     * @param content
     */
    public static void setText(TextView textView, String content){
        if (textView != null && content != null){
            textView.setText(content);
        }
    }

    /**
     * 设置 Html 内容
     * @param textView
     * @param content
     */
    public static void setHtmlText(TextView textView, String content){
        if (textView != null && content != null){
            textView.setText(Html.fromHtml(content));
        }
    }

    /**
     * 给TextView设置下划线
     * @param textView
     */
    public static void setTVUnderLine(TextView textView) {
        if (textView != null){
            Paint paint = textView.getPaint();
            paint.setFlags(Paint.UNDERLINE_TEXT_FLAG);
            paint.setAntiAlias(true);
        }
    }

    /**
     * 获取字体高度
     * @param textView
     * @return
     */
    public static int getTextHeight(TextView textView){
        if (textView != null){
            return getTextHeight(textView.getPaint());
        }
        return 0;
    }

    /**
     * 获取字体高度
     * @param paint
     * @return
     */
    public static int getTextHeight(Paint paint){
        // https://blog.csdn.net/superbigcupid/article/details/47153139
        // 获取字体高度
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        // 计算内容高度
        int tHeight = (int) Math.ceil((fontMetrics.descent - fontMetrics.ascent));
        // 返回字体高度
        return tHeight;
    }

    /**
     * 获取字体顶部偏移高度
     * @param textView
     * @return
     */
    public static int getTextTopOffsetHeight(TextView textView){
        if (textView != null){
            return getTextTopOffsetHeight(textView.getPaint());
        }
        return 0;
    }

    /**
     * 获取字体顶部偏移高度
     * @param paint
     * @return
     */
    public static int getTextTopOffsetHeight(Paint paint){
        // 获取字体高度
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        // 计算字体偏差(顶部偏差)
        int baseLine = (int) Math.ceil(Math.abs(fontMetrics.top) - Math.abs(fontMetrics.ascent));
        // 返回顶部偏差
        return baseLine;
    }

    /**
     * 计算字体宽度
     * @param textView
     * @return
     */
    public static float getTextWidth(TextView textView){
        return getTextWidth(textView.getPaint(), textView.getText().toString());
    }

    /**
     * 计算字体宽度
     * @param paint
     * @param hintStr
     * @return
     */
    public static float getTextWidth(Paint paint, String hintStr){
        return paint.measureText(hintStr);
    }

    /**
     * 获取画布中间居中位置
     * @param targetRect
     * @param paint
     * @return
     */
    public static int getCenterRectY(Rect targetRect, Paint paint){
        // 将字画在矩形背景的正中位置
        // https://blog.csdn.net/superbigcupid/article/details/47153139
        // 获取字体高度
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        // 获取底部Y轴居中位置
        return targetRect.top + (targetRect.bottom - targetRect.top) / 2 - (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.top;
        // canvas.drawText(testString, targetRect.centerX(), baseline, paint);
    }

    /**
     * 通过需要的高度, 计算字体大小
     * @param tHeight 需要的字体高度
     * @return
     */
    public static float reckonTextSize(int tHeight){
        // 创建画笔
        Paint paint = new Paint();
        // 默认字体大小
        float textSize = 90.0f;
        // 计算内容高度
        int calcTextHeight = -1;
        // 循环计算
        while (true){
            // 设置画笔大小
            paint.setTextSize(textSize);
            // 获取字体高度
            Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
            // 计算内容高度
            calcTextHeight = (int) Math.ceil((fontMetrics.descent - fontMetrics.ascent));
            // 符合条件则直接返回
            if (calcTextHeight == tHeight){
                return textSize;
            } else if (calcTextHeight > tHeight){ // 如果计算的字体高度大于
                textSize -= 0.5f;
            } else {
                textSize += 0.5f;
            }
        }
    }

    /**
     * 计算第几位超过宽度
     * @param textView
     * @param width
     * @return -1 表示没超过
     */
    public static int calcTextWidth(TextView textView, float width){
        if (textView != null){
            return calcTextWidth(textView.getPaint(), textView.getText().toString(), width);
        }
        return -1;
    }

    /**
     * 计算第几位超过宽度
     * @param text
     * @param width
     * @return -1 表示没超过
     */
    public static int calcTextWidth(Paint paint, String text, float width){
        // 先获取宽度
        float textWidth = getTextWidth(paint, text);
        // 判断是否超过
        if (textWidth <= width){
            return -1; // 表示没超过
        }
        // 获取数据长度
        int length = text.length();
        // 循环除2
        while (true) {
            // 数据至少为2位以上
            if (length < 2) {
                return 0; // 表示第一位已经超过
            }
            // 从中间取值
            length = length / 2;
            // 计算宽度
            textWidth = getTextWidth(paint, text.substring(0, length));
            // 判断是否小于宽度 - 进行返回长度
            if (textWidth <= width){
                break;
            }
        }
        // 遍历计算
        for (int i = length, len = text.length(); i < len; i++){
            // 获取字体内容宽度
            float tWidth = paint.measureText(text.substring(0, i));
            // 判断是否大于指定宽度
            if (tWidth > width){
                return i - 1; // 返回超过前的长度
            } else if (tWidth == width){
                return i; // 返回超过前的长度
            }
        }
        return -1;
    }
}
