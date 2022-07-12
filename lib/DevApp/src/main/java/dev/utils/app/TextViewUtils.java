package dev.utils.app;

import android.content.res.ColorStateList;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Html;
import android.text.InputFilter;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.RequiresApi;
import androidx.core.widget.TextViewCompat;

import java.util.ArrayList;
import java.util.List;

import dev.utils.LogPrintUtils;

/**
 * detail: TextView 工具类
 * @author Ttt
 * <pre>
 *     获取字体信息 Paint.FontMetrics
 *     @see <a href="https://blog.csdn.net/superbigcupid/article/details/47153139"/>
 *     @see <a href="http://www.cnblogs.com/tianzhijiexian/p/4297664.html"/>
 *     <p></p>
 *     TextView 设置行间距、行高, 以及字间距
 *     @see <a href="https://blog.csdn.net/shanshan_1117/article/details/79564271"/>
 *     <p></p>
 *     android:includeFontPadding
 *     @see <a href="https://blog.csdn.net/bdmh/article/details/78110557"/>
 *     <p></p>
 *     设置文字水平间距: {@link TextViewUtils#setLetterSpacing(View, float)}
 *     android:letterSpacing
 *     设置文字行间距 ( 行高 ): {@link TextViewUtils#setLineSpacing(View, float)}、{@link TextViewUtils#setLineSpacingAndMultiplier(View, float, float)}
 *     android:lineSpacingExtra
 *     android:lineSpacingMultiplier
 *     <p></p>
 *     setPaintFlags:
 *     Paint.ANTI_ALIAS_FLAG 抗锯齿标志
 *     Paint.FILTER_BITMAP_FLAG 使位图过滤的位掩码标志
 *     Paint.DITHER_FLAG 使位图进行有利的抖动的位掩码标志
 *     Paint.UNDERLINE_TEXT_FLAG 下划线
 *     Paint.STRIKE_THRU_TEXT_FLAG 中划线
 *     Paint.FAKE_BOLD_TEXT_FLAG 加粗
 *     Paint.LINEAR_TEXT_FLAG 使文本平滑线性扩展的油漆标志
 *     Paint.SUBPIXEL_TEXT_FLAG 使文本的亚像素定位的绘图标志
 *     Paint.EMBEDDED_BITMAP_TEXT_FLAG 绘制文本时允许使用位图字体的绘图标志
 * </pre>
 */
public final class TextViewUtils {

    private TextViewUtils() {
    }

    // 日志 TAG
    private static final String TAG = TextViewUtils.class.getSimpleName();

    // ================
    // = 获取 TextView =
    // ================

    /**
     * 获取 TextView
     * @param view {@link View}
     * @param <T>  泛型
     * @return {@link TextView}
     */
    public static <T extends TextView> T getTextView(final View view) {
        if (view instanceof TextView) {
            try {
                return (T) view;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getTextView");
            }
        }
        return null;
    }

    // ========
    // = Hint =
    // ========

    /**
     * 获取 Hint 文本
     * @param textView {@link TextView}
     * @param <T>      泛型
     * @return {@link TextView#getHint()}
     */
    public static <T extends TextView> String getHint(final T textView) {
        if (textView != null) {
            if (textView.getHint() != null) {
                return textView.getHint().toString();
            }
        }
        return null;
    }

    /**
     * 获取 Hint 文本
     * @param view {@link TextView}
     * @return {@link TextView#getHint()}
     */
    public static String getHint(final View view) {
        return getHint(getTextView(view));
    }

    /**
     * 获取多个 TextView Hint 文本
     * @param views View[]
     * @return {@link List<String>} 多个 TextView Hint 文本
     */
    public static List<String> getHints(final View... views) {
        List<String> lists = new ArrayList<>();
        if (views != null) {
            for (View view : views) {
                String text = getHint(view);
                if (text != null) {
                    lists.add(text);
                }
            }
        }
        return lists;
    }

    /**
     * 获取多个 TextView Hint 文本
     * @param views TextView[]
     * @param <T>   泛型
     * @return {@link List<String>} 多个 TextView Hint 文本
     */
    public static <T extends TextView> List<String> getHints(final T... views) {
        List<String> lists = new ArrayList<>();
        if (views != null) {
            for (T view : views) {
                String text = getHint(view);
                if (text != null) {
                    lists.add(text);
                }
            }
        }
        return lists;
    }

    /**
     * 设置 Hint 文本
     * @param textView {@link TextView}
     * @param text     Hint text
     * @param <T>      泛型
     * @return {@link TextView}
     */
    public static <T extends TextView> T setHint(
            final T textView,
            final CharSequence text
    ) {
        if (textView != null) {
            textView.setHint(text);
        }
        return textView;
    }

    /**
     * 设置 Hint 文本
     * @param view {@link TextView}
     * @param text Hint text
     * @return {@link View}
     */
    public static View setHint(
            final View view,
            final CharSequence text
    ) {
        setHint(getTextView(view), text);
        return view;
    }

    /**
     * 获取 Hint 字体颜色
     * @param textView {@link TextView}
     * @param <T>      泛型
     * @return {@link ColorStateList}
     */
    public static <T extends TextView> ColorStateList getHintTextColors(final T textView) {
        if (textView != null) {
            return textView.getHintTextColors();
        }
        return null;
    }

    /**
     * 获取 Hint 字体颜色
     * @param view {@link TextView}
     * @return {@link ColorStateList}
     */
    public static ColorStateList getHintTextColors(final View view) {
        return getHintTextColors(getTextView(view));
    }

    /**
     * 设置 Hint 字体颜色
     * @param textView {@link TextView}
     * @param color    R.color.id
     * @param <T>      泛型
     * @return {@link TextView}
     */
    public static <T extends TextView> T setHintTextColor(
            final T textView,
            @ColorInt final int color
    ) {
        if (textView != null) {
            textView.setHintTextColor(color);
        }
        return textView;
    }

    /**
     * 设置 Hint 字体颜色
     * @param view  {@link TextView}
     * @param color R.color.id
     * @return {@link View}
     */
    public static View setHintTextColor(
            final View view,
            @ColorInt final int color
    ) {
        setHintTextColor(getTextView(view), color);
        return view;
    }

    /**
     * 设置 Hint 字体颜色
     * @param textView {@link TextView}
     * @param colors   {@link ColorStateList}
     * @param <T>      泛型
     * @return {@link TextView}
     */
    public static <T extends TextView> T setHintTextColor(
            final T textView,
            final ColorStateList colors
    ) {
        if (textView != null) {
            textView.setHintTextColor(colors);
        }
        return textView;
    }

    /**
     * 设置 Hint 字体颜色
     * @param view   {@link TextView}
     * @param colors {@link ColorStateList}
     * @return {@link View}
     */
    public static View setHintTextColor(
            final View view,
            final ColorStateList colors
    ) {
        setHintTextColor(getTextView(view), colors);
        return view;
    }

    /**
     * 设置多个 TextView Hint 字体颜色
     * @param color R.color.id
     * @param views View[]
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setHintTextColors(
            @ColorInt final int color,
            final View... views
    ) {
        if (views != null) {
            for (View view : views) {
                setHintTextColor(view, color);
            }
            return true;
        }
        return false;
    }

    /**
     * 设置多个 TextView Hint 字体颜色
     * @param color R.color.id
     * @param views TextView[]
     * @param <T>   泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T extends TextView> boolean setHintTextColors(
            @ColorInt final int color,
            final T... views
    ) {
        if (views != null) {
            for (T view : views) {
                setHintTextColor(view, color);
            }
            return true;
        }
        return false;
    }

    /**
     * 设置多个 TextView Hint 字体颜色
     * @param colors {@link ColorStateList}
     * @param views  View[]
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setHintTextColors(
            final ColorStateList colors,
            final View... views
    ) {
        if (views != null) {
            for (View view : views) {
                setHintTextColor(view, colors);
            }
            return true;
        }
        return false;
    }

    /**
     * 设置多个 TextView Hint 字体颜色
     * @param colors {@link ColorStateList}
     * @param views  TextView[]
     * @param <T>    泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T extends TextView> boolean setHintTextColors(
            final ColorStateList colors,
            final T... views
    ) {
        if (views != null) {
            for (T view : views) {
                setHintTextColor(view, colors);
            }
            return true;
        }
        return false;
    }

    // ========
    // = Text =
    // ========

    /**
     * 获取文本
     * @param textView {@link TextView}
     * @param <T>      泛型
     * @return {@link TextView#getText()}
     */
    public static <T extends TextView> String getText(final T textView) {
        if (textView != null) {
            return textView.getText().toString();
        }
        return null;
    }

    /**
     * 获取文本
     * @param view {@link TextView}
     * @return {@link TextView#getText()}
     */
    public static String getText(final View view) {
        return getText(getTextView(view));
    }

    /**
     * 获取多个 TextView 文本
     * @param views View[]
     * @return {@link List<String>} 多个 TextView 文本
     */
    public static List<String> getTexts(final View... views) {
        List<String> lists = new ArrayList<>();
        if (views != null) {
            for (View view : views) {
                String text = getText(view);
                if (text != null) {
                    lists.add(text);
                }
            }
        }
        return lists;
    }

    /**
     * 获取多个 TextView 文本
     * @param views TextView[]
     * @param <T>   泛型
     * @return {@link List<String>} 多个 TextView 文本
     */
    public static <T extends TextView> List<String> getTexts(final T... views) {
        List<String> lists = new ArrayList<>();
        if (views != null) {
            for (T view : views) {
                String text = getText(view);
                if (text != null) {
                    lists.add(text);
                }
            }
        }
        return lists;
    }

    /**
     * 设置文本
     * @param textView {@link TextView}
     * @param text     TextView text
     * @param <T>      泛型
     * @return {@link TextView}
     */
    public static <T extends TextView> T setText(
            final T textView,
            final CharSequence text
    ) {
        if (textView != null) {
            textView.setText(text);
        }
        return textView;
    }

    /**
     * 设置文本
     * @param view {@link TextView}
     * @param text TextView text
     * @return {@link View}
     */
    public static View setText(
            final View view,
            final CharSequence text
    ) {
        setText(getTextView(view), text);
        return view;
    }

    /**
     * 设置多个 TextView 文本
     * @param text  TextView text
     * @param views View[]
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setTexts(
            final CharSequence text,
            final View... views
    ) {
        if (views != null) {
            for (View view : views) {
                setText(view, text);
            }
            return true;
        }
        return false;
    }

    /**
     * 设置多个 TextView 文本
     * @param text  TextView text
     * @param views TextView[]
     * @param <T>   泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T extends TextView> boolean setTexts(
            final CharSequence text,
            final T... views
    ) {
        if (views != null) {
            for (T view : views) {
                setText(view, text);
            }
            return true;
        }
        return false;
    }

    /**
     * 获取字体颜色
     * @param textView {@link TextView}
     * @param <T>      泛型
     * @return {@link ColorStateList}
     */
    public static <T extends TextView> ColorStateList getTextColors(final T textView) {
        if (textView != null) {
            return textView.getTextColors();
        }
        return null;
    }

    /**
     * 获取字体颜色
     * @param view {@link TextView}
     * @return {@link ColorStateList}
     */
    public static ColorStateList getTextColors(final View view) {
        return getTextColors(getTextView(view));
    }

    /**
     * 设置字体颜色
     * @param textView {@link TextView}
     * @param color    R.color.id
     * @param <T>      泛型
     * @return {@link TextView}
     */
    public static <T extends TextView> T setTextColor(
            final T textView,
            @ColorInt final int color
    ) {
        if (textView != null) {
            textView.setTextColor(color);
        }
        return textView;
    }

    /**
     * 设置字体颜色
     * @param view  {@link TextView}
     * @param color R.color.id
     * @return {@link View}
     */
    public static View setTextColor(
            final View view,
            @ColorInt final int color
    ) {
        setTextColor(getTextView(view), color);
        return view;
    }

    /**
     * 设置字体颜色
     * @param textView {@link TextView}
     * @param colors   {@link ColorStateList}
     * @param <T>      泛型
     * @return {@link TextView}
     */
    public static <T extends TextView> T setTextColor(
            final T textView,
            final ColorStateList colors
    ) {
        if (textView != null) {
            textView.setTextColor(colors);
        }
        return textView;
    }

    /**
     * 设置字体颜色
     * @param view   {@link TextView}
     * @param colors {@link ColorStateList}
     * @return {@link View}
     */
    public static View setTextColor(
            final View view,
            final ColorStateList colors
    ) {
        setTextColor(getTextView(view), colors);
        return view;
    }

    /**
     * 设置多个 TextView 字体颜色
     * @param color R.color.id
     * @param views View[]
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setTextColors(
            @ColorInt final int color,
            final View... views
    ) {
        if (views != null) {
            for (View view : views) {
                setTextColor(view, color);
            }
            return true;
        }
        return false;
    }

    /**
     * 设置多个 TextView 字体颜色
     * @param color R.color.id
     * @param views TextView[]
     * @param <T>   泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T extends TextView> boolean setTextColors(
            @ColorInt final int color,
            final T... views
    ) {
        if (views != null) {
            for (T view : views) {
                setTextColor(view, color);
            }
            return true;
        }
        return false;
    }

    /**
     * 设置多个 TextView 字体颜色
     * @param colors {@link ColorStateList}
     * @param views  View[]
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setTextColors(
            final ColorStateList colors,
            final View... views
    ) {
        if (views != null) {
            for (View view : views) {
                setTextColor(view, colors);
            }
            return true;
        }
        return false;
    }

    /**
     * 设置多个 TextView 字体颜色
     * @param colors {@link ColorStateList}
     * @param views  TextView[]
     * @param <T>    泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T extends TextView> boolean setTextColors(
            final ColorStateList colors,
            final T... views
    ) {
        if (views != null) {
            for (T view : views) {
                setTextColor(view, colors);
            }
            return true;
        }
        return false;
    }

    // ========
    // = Html =
    // ========

    /**
     * 设置 Html 内容
     * @param textView {@link TextView}
     * @param content  Html content
     * @param <T>      泛型
     * @return {@link TextView}
     */
    public static <T extends TextView> T setHtmlText(
            final T textView,
            final String content
    ) {
        if (textView != null && content != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                textView.setText(Html.fromHtml(content, Html.FROM_HTML_MODE_LEGACY));
            } else {
                textView.setText(Html.fromHtml(content));
            }
        }
        return textView;
    }

    /**
     * 设置 Html 内容
     * @param view    {@link TextView}
     * @param content Html content
     * @return {@link View}
     */
    public static View setHtmlText(
            final View view,
            final String content
    ) {
        setHtmlText(getTextView(view), content);
        return view;
    }

    /**
     * 设置多个 TextView Html 内容
     * @param content Html content
     * @param views   View[]
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setHtmlTexts(
            final String content,
            final View... views
    ) {
        if (content != null && views != null) {
            for (View view : views) {
                setHtmlText(view, content);
            }
            return true;
        }
        return false;
    }

    /**
     * 设置多个 TextView Html 内容
     * @param content Html content
     * @param views   TextView[]
     * @param <T>     泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T extends TextView> boolean setHtmlTexts(
            final String content,
            final T... views
    ) {
        if (content != null && views != null) {
            for (T view : views) {
                setHtmlText(view, content);
            }
            return true;
        }
        return false;
    }

    // ==========
    // = 字体相关 =
    // ==========

    /**
     * 获取字体
     * @param textView {@link TextView}
     * @param <T>      泛型
     * @return {@link Typeface}
     */
    public static <T extends TextView> Typeface getTypeface(final T textView) {
        if (textView != null) {
            return textView.getTypeface();
        }
        return null;
    }

    /**
     * 设置字体
     * @param textView {@link TextView}
     * @param typeface {@link Typeface} 字体样式
     * @param <T>      泛型
     * @return {@link TextView}
     */
    public static <T extends TextView> T setTypeface(
            final T textView,
            final Typeface typeface
    ) {
        if (textView != null && typeface != null) {
            textView.setTypeface(typeface);
        }
        return textView;
    }

    /**
     * 设置字体
     * @param textView {@link TextView}
     * @param typeface {@link Typeface} 字体样式
     * @param style    样式
     * @param <T>      泛型
     * @return {@link TextView}
     */
    public static <T extends TextView> T setTypeface(
            final T textView,
            final Typeface typeface,
            final int style
    ) {
        if (textView != null && typeface != null) {
            textView.setTypeface(typeface, style);
        }
        return textView;
    }

    /**
     * 设置字体
     * @param view     {@link TextView}
     * @param typeface {@link Typeface} 字体样式
     * @return {@link View}
     */
    public static View setTypeface(
            final View view,
            final Typeface typeface
    ) {
        setTypeface(getTextView(view), typeface);
        return view;
    }

    /**
     * 设置字体
     * @param view     {@link TextView}
     * @param typeface {@link Typeface} 字体样式
     * @param style    样式
     * @return {@link View}
     */
    public static View setTypeface(
            final View view,
            final Typeface typeface,
            final int style
    ) {
        setTypeface(getTextView(view), typeface, style);
        return view;
    }

    // =

    /**
     * 设置字体大小 ( px 像素 )
     * @param textView {@link TextView}
     * @param size     字体大小
     * @param <T>      泛型
     * @return {@link TextView}
     */
    public static <T extends TextView> T setTextSizeByPx(
            final T textView,
            final float size
    ) {
        return setTextSize(textView, TypedValue.COMPLEX_UNIT_PX, size);
    }

    /**
     * 设置字体大小 ( sp 缩放像素 )
     * @param textView {@link TextView}
     * @param size     字体大小
     * @param <T>      泛型
     * @return {@link TextView}
     */
    public static <T extends TextView> T setTextSizeBySp(
            final T textView,
            final float size
    ) {
        return setTextSize(textView, TypedValue.COMPLEX_UNIT_SP, size);
    }

    /**
     * 设置字体大小 ( dp 与设备无关的像素 )
     * @param textView {@link TextView}
     * @param size     字体大小
     * @param <T>      泛型
     * @return {@link TextView}
     */
    public static <T extends TextView> T setTextSizeByDp(
            final T textView,
            final float size
    ) {
        return setTextSize(textView, TypedValue.COMPLEX_UNIT_DIP, size);
    }

    /**
     * 设置字体大小 ( inches 英寸 )
     * @param textView {@link TextView}
     * @param size     字体大小
     * @param <T>      泛型
     * @return {@link TextView}
     */
    public static <T extends TextView> T setTextSizeByIn(
            final T textView,
            final float size
    ) {
        return setTextSize(textView, TypedValue.COMPLEX_UNIT_IN, size);
    }

    // =

    /**
     * 设置字体大小 ( px 像素 )
     * @param view {@link TextView}
     * @param size 字体大小
     * @return {@link View}
     */
    public static View setTextSizeByPx(
            final View view,
            final float size
    ) {
        setTextSize(getTextView(view), TypedValue.COMPLEX_UNIT_PX, size);
        return view;
    }

    /**
     * 设置字体大小 ( sp 缩放像素 )
     * @param view {@link TextView}
     * @param size 字体大小
     * @return {@link View}
     */
    public static View setTextSizeBySp(
            final View view,
            final float size
    ) {
        setTextSize(getTextView(view), TypedValue.COMPLEX_UNIT_SP, size);
        return view;
    }

    /**
     * 设置字体大小 ( dp 与设备无关的像素 )
     * @param view {@link TextView}
     * @param size 字体大小
     * @return {@link View}
     */
    public static View setTextSizeByDp(
            final View view,
            final float size
    ) {
        setTextSize(getTextView(view), TypedValue.COMPLEX_UNIT_DIP, size);
        return view;
    }

    /**
     * 设置字体大小 ( inches 英寸 )
     * @param view {@link TextView}
     * @param size 字体大小
     * @return {@link View}
     */
    public static View setTextSizeByIn(
            final View view,
            final float size
    ) {
        setTextSize(getTextView(view), TypedValue.COMPLEX_UNIT_IN, size);
        return view;
    }

    // =

    /**
     * 设置字体大小
     * @param textView {@link TextView}
     * @param unit     字体参数类型
     * @param size     字体大小
     * @param <T>      泛型
     * @return {@link TextView}
     */
    public static <T extends TextView> T setTextSize(
            final T textView,
            final int unit,
            final float size
    ) {
        if (textView != null) {
            textView.setTextSize(unit, size);
        }
        return textView;
    }

    /**
     * 设置字体大小
     * @param view {@link TextView}
     * @param unit 字体参数类型
     * @param size 字体大小
     * @return {@link View}
     */
    public static View setTextSize(
            final View view,
            final int unit,
            final float size
    ) {
        setTextSize(getTextView(view), unit, size);
        return view;
    }

    // =

    /**
     * 设置多个 TextView 字体大小
     * @param views View[]
     * @param unit  参数类型
     * @param size  字体大小
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setTextSizes(
            final View[] views,
            final int unit,
            final float size
    ) {
        if (views != null) {
            for (View view : views) {
                setTextSize(view, unit, size);
            }
            return true;
        }
        return false;
    }

    /**
     * 设置多个 TextView 字体大小
     * @param views TextView[]
     * @param unit  参数类型
     * @param size  字体大小
     * @param <T>   泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T extends TextView> boolean setTextSizes(
            final T[] views,
            final int unit,
            final float size
    ) {
        if (views != null) {
            for (T view : views) {
                setTextSize(view, unit, size);
            }
            return true;
        }
        return false;
    }

    // =

    /**
     * 获取 TextView 字体大小 ( px )
     * @param textView {@link TextView}
     * @param <T>      泛型
     * @return 字体大小 (px)
     */
    public static <T extends TextView> float getTextSize(final T textView) {
        if (textView != null) {
            return textView.getTextSize();
        }
        return -1F;
    }

    /**
     * 获取 TextView 字体大小 ( px )
     * @param view {@link TextView}
     * @return 字体大小 (px)
     */
    public static float getTextSize(final View view) {
        return getTextSize(getTextView(view));
    }

    // =

    /**
     * 清空 flags
     * @param textView {@link TextView}
     * @param <T>      泛型
     * @return {@link TextView}
     */
    public static <T extends TextView> T clearFlags(final T textView) {
        if (textView != null) {
            textView.setPaintFlags(0);
        }
        return textView;
    }

    /**
     * 清空 flags
     * @param view {@link TextView}
     * @return {@link View}
     */
    public static View clearFlags(final View view) {
        clearFlags(getTextView(view));
        return view;
    }

    // =

    /**
     * 设置 TextView flags
     * @param textView {@link TextView}
     * @param flags    flags
     * @param <T>      泛型
     * @return {@link TextView}
     */
    public static <T extends TextView> T setPaintFlags(
            final T textView,
            final int flags
    ) {
        if (textView != null) {
            textView.setPaintFlags(flags);
        }
        return textView;
    }

    /**
     * 设置 TextView flags
     * @param view  {@link TextView}
     * @param flags flags
     * @return {@link View}
     */
    public static View setPaintFlags(
            final View view,
            final int flags
    ) {
        setPaintFlags(getTextView(view), flags);
        return view;
    }

    /**
     * 设置 TextView 抗锯齿 flags
     * @param textView {@link TextView}
     * @param <T>      泛型
     * @return {@link TextView}
     */
    public static <T extends TextView> T setAntiAliasFlag(final T textView) {
        if (textView != null) {
            textView.setPaintFlags(Paint.ANTI_ALIAS_FLAG);
        }
        return textView;
    }

    /**
     * 设置 TextView 抗锯齿 flags
     * @param view {@link TextView}
     * @return {@link View}
     */
    public static View setAntiAliasFlag(final View view) {
        setAntiAliasFlag(getTextView(view));
        return view;
    }

    /**
     * 设置 TextView 是否加粗
     * @param textView {@link TextView}
     * @param <T>      泛型
     * @return {@link TextView}
     */
    public static <T extends TextView> T setBold(final T textView) {
        return setBold(textView, true);
    }

    /**
     * 设置 TextView 是否加粗
     * @param textView {@link TextView}
     * @param isBold   {@code true} yes, {@code false} no
     * @param <T>      泛型
     * @return {@link TextView}
     */
    public static <T extends TextView> T setBold(
            final T textView,
            final boolean isBold
    ) {
        if (textView != null) {
            textView.setTypeface(Typeface.defaultFromStyle(isBold ? Typeface.BOLD : Typeface.NORMAL));
        }
        return textView;
    }

    /**
     * 设置 TextView 是否加粗
     * @param textView {@link TextView}
     * @param typeface {@link Typeface} 字体样式
     * @param isBold   {@code true} yes, {@code false} no
     * @param <T>      泛型
     * @return {@link TextView}
     */
    public static <T extends TextView> T setBold(
            final T textView,
            final Typeface typeface,
            final boolean isBold
    ) {
        if (textView != null && typeface != null) {
            textView.setTypeface(typeface, isBold ? Typeface.BOLD : Typeface.NORMAL);
        }
        return textView;
    }

    /**
     * 设置 TextView 是否加粗
     * @param view {@link TextView}
     * @return {@link View}
     */
    public static View setBold(final View view) {
        setBold(getTextView(view), true);
        return view;
    }

    /**
     * 设置 TextView 是否加粗
     * @param view   {@link TextView}
     * @param isBold {@code true} yes, {@code false} no
     * @return {@link View}
     */
    public static View setBold(
            final View view,
            final boolean isBold
    ) {
        setBold(getTextView(view), isBold);
        return view;
    }

    /**
     * 设置 TextView 是否加粗
     * @param view     {@link TextView}
     * @param typeface {@link Typeface} 字体样式
     * @param isBold   {@code true} yes, {@code false} no
     * @return {@link View}
     */
    public static View setBold(
            final View view,
            final Typeface typeface,
            final boolean isBold
    ) {
        setBold(getTextView(view), typeface, isBold);
        return view;
    }

    // =

    /**
     * 设置下划线
     * @param textView {@link TextView}
     * @param <T>      泛型
     * @return {@link TextView}
     */
    public static <T extends TextView> T setUnderlineText(final T textView) {
        return setUnderlineText(textView, true);
    }

    /**
     * 设置下划线并加清晰
     * @param textView    {@link TextView}
     * @param isAntiAlias 是否消除锯齿
     * @param <T>         泛型
     * @return {@link TextView}
     */
    public static <T extends TextView> T setUnderlineText(
            final T textView,
            final boolean isAntiAlias
    ) {
        if (textView != null) {
            textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            if (isAntiAlias) {
                textView.setPaintFlags(textView.getPaintFlags() | Paint.ANTI_ALIAS_FLAG);
            }
        }
        return textView;
    }

    // =

    /**
     * 设置下划线
     * @param view {@link TextView}
     * @return {@link View}
     */
    public static View setUnderlineText(final View view) {
        setUnderlineText(getTextView(view), true);
        return view;
    }

    /**
     * 设置下划线并加清晰
     * @param view        {@link TextView}
     * @param isAntiAlias 是否消除锯齿
     * @return {@link View}
     */
    public static View setUnderlineText(
            final View view,
            final boolean isAntiAlias
    ) {
        setUnderlineText(getTextView(view), isAntiAlias);
        return view;
    }

    // =

    /**
     * 设置中划线
     * @param textView {@link TextView}
     * @param <T>      泛型
     * @return {@link TextView}
     */
    public static <T extends TextView> T setStrikeThruText(final T textView) {
        return setStrikeThruText(textView, true);
    }

    /**
     * 设置中划线并加清晰
     * @param textView    {@link TextView}
     * @param isAntiAlias 是否消除锯齿
     * @param <T>         泛型
     * @return {@link TextView}
     */
    public static <T extends TextView> T setStrikeThruText(
            final T textView,
            final boolean isAntiAlias
    ) {
        if (textView != null) {
            textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            if (isAntiAlias) {
                textView.setPaintFlags(textView.getPaintFlags() | Paint.ANTI_ALIAS_FLAG);
            }
        }
        return textView;
    }

    // =

    /**
     * 设置中划线
     * @param view {@link TextView}
     * @return {@link View}
     */
    public static View setStrikeThruText(final View view) {
        setStrikeThruText(getTextView(view), true);
        return view;
    }

    /**
     * 设置中划线并加清晰
     * @param view        {@link TextView}
     * @param isAntiAlias 是否消除锯齿
     * @return {@link View}
     */
    public static View setStrikeThruText(
            final View view,
            final boolean isAntiAlias
    ) {
        setStrikeThruText(getTextView(view), isAntiAlias);
        return view;
    }

    // =

    /**
     * 获取文字水平间距
     * @param textView {@link TextView}
     * @param <T>      泛型
     * @return 文字水平间距
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static <T extends TextView> float getLetterSpacing(final T textView) {
        if (textView != null) {
            return textView.getLetterSpacing();
        }
        return 0F;
    }

    /**
     * 设置文字水平间距
     * <pre>
     *     android:letterSpacing
     * </pre>
     * @param textView      {@link TextView}
     * @param letterSpacing 文字水平间距
     * @param <T>           泛型
     * @return {@link TextView}
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static <T extends TextView> T setLetterSpacing(
            final T textView,
            final float letterSpacing
    ) {
        if (textView != null) {
            textView.setLetterSpacing(letterSpacing);
        }
        return textView;
    }

    /**
     * 设置文字水平间距
     * @param view          {@link TextView}
     * @param letterSpacing 文字水平间距
     * @return {@link View}
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static View setLetterSpacing(
            final View view,
            final float letterSpacing
    ) {
        setLetterSpacing(getTextView(view), letterSpacing);
        return view;
    }

    // =

    /**
     * 获取文字行间距 ( 行高 )
     * @param textView {@link TextView}
     * @param <T>      泛型
     * @return 文字行间距 ( 行高 )
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static <T extends TextView> float getLineSpacingExtra(final T textView) {
        if (textView != null) {
            return textView.getLineSpacingExtra();
        }
        return 0F;
    }

    /**
     * 获取文字行间距倍数
     * @param textView {@link TextView}
     * @param <T>      泛型
     * @return 文字行间距倍数
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static <T extends TextView> float getLineSpacingMultiplier(final T textView) {
        if (textView != null) {
            return textView.getLineSpacingMultiplier();
        }
        return 0F;
    }

    /**
     * 设置文字行间距 ( 行高 )
     * @param textView    {@link TextView}
     * @param lineSpacing 文字行间距 ( 行高 ), android:lineSpacingExtra
     * @param <T>         泛型
     * @return {@link TextView}
     */
    public static <T extends TextView> T setLineSpacing(
            final T textView,
            final float lineSpacing
    ) {
        return setLineSpacingAndMultiplier(textView, lineSpacing, 1.0F);
    }

    /**
     * 设置文字行间距 ( 行高 ) 、行间距倍数
     * @param textView    {@link TextView}
     * @param lineSpacing 文字行间距 ( 行高 ), android:lineSpacingExtra
     * @param multiplier  行间距倍数, android:lineSpacingMultiplier
     * @param <T>         泛型
     * @return {@link TextView}
     */
    public static <T extends TextView> T setLineSpacingAndMultiplier(
            final T textView,
            final float lineSpacing,
            final float multiplier
    ) {
        if (textView != null) {
            textView.setLineSpacing(lineSpacing, multiplier);
        }
        return textView;
    }

    // =

    /**
     * 设置文字行间距 ( 行高 )
     * @param view        {@link TextView}
     * @param lineSpacing 文字行间距 ( 行高 ), android:lineSpacingExtra
     * @return {@link View}
     */
    public static View setLineSpacing(
            final View view,
            final float lineSpacing
    ) {
        setLineSpacingAndMultiplier(getTextView(view), lineSpacing, 1.0F);
        return view;
    }

    /**
     * 设置文字行间距 ( 行高 ) 、行间距倍数
     * @param view        {@link TextView}
     * @param lineSpacing 文字行间距 ( 行高 ), android:lineSpacingExtra
     * @param multiplier  行间距倍数, android:lineSpacingMultiplier
     * @return {@link View}
     */
    public static View setLineSpacingAndMultiplier(
            final View view,
            final float lineSpacing,
            final float multiplier
    ) {
        setLineSpacingAndMultiplier(getTextView(view), lineSpacing, multiplier);
        return view;
    }

    // =

    /**
     * 获取字体水平方向的缩放
     * @param textView {@link TextView}
     * @param <T>      泛型
     * @return 字体水平方向的缩放
     */
    public static <T extends TextView> float getTextScaleX(final T textView) {
        if (textView != null) {
            return textView.getTextScaleX();
        }
        return 0F;
    }

    /**
     * 设置字体水平方向的缩放
     * <pre>
     *     android:textScaleX
     * </pre>
     * @param textView {@link TextView}
     * @param size     缩放比例
     * @param <T>      泛型
     * @return {@link TextView}
     */
    public static <T extends TextView> T setTextScaleX(
            final T textView,
            final float size
    ) {
        if (textView != null) {
            textView.setTextScaleX(size);
        }
        return textView;
    }

    /**
     * 设置字体水平方向的缩放
     * @param view {@link TextView}
     * @param size 缩放比例
     * @return {@link View}
     */
    public static View setTextScaleX(
            final View view,
            final float size
    ) {
        setTextScaleX(getTextView(view), size);
        return view;
    }

    // =

    /**
     * 是否保留字体留白间隙区域
     * @param textView {@link TextView}
     * @param <T>      泛型
     * @return {@code true} yes, {@code false} no
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static <T extends TextView> boolean getIncludeFontPadding(final T textView) {
        if (textView != null) {
            return textView.getIncludeFontPadding();
        }
        return false;
    }

    /**
     * 设置是否保留字体留白间隙区域
     * <pre>
     *     android:includeFontPadding
     * </pre>
     * @param textView       {@link TextView}
     * @param includePadding 是否保留字体留白间隙区域
     * @param <T>            泛型
     * @return {@link TextView}
     */
    public static <T extends TextView> T setIncludeFontPadding(
            final T textView,
            final boolean includePadding
    ) {
        if (textView != null) {
            textView.setIncludeFontPadding(includePadding);
        }
        return textView;
    }

    /**
     * 设置是否保留字体留白间隙区域
     * @param view           {@link TextView}
     * @param includePadding 是否保留字体留白间隙区域
     * @return {@link View}
     */
    public static View setIncludeFontPadding(
            final View view,
            final boolean includePadding
    ) {
        setIncludeFontPadding(getTextView(view), includePadding);
        return view;
    }

    // =

    /**
     * 获取输入类型
     * @param textView {@link TextView}
     * @param <T>      泛型
     * @return 输入类型
     */
    public static <T extends TextView> int getInputType(final T textView) {
        if (textView != null) {
            return textView.getInputType();
        }
        return 0;
    }

    /**
     * 设置输入类型
     * @param textView {@link TextView}
     * @param type     类型
     * @param <T>      泛型
     * @return {@link TextView}
     */
    public static <T extends TextView> T setInputType(
            final T textView,
            final int type
    ) {
        if (textView != null) {
            textView.setInputType(type);
        }
        return textView;
    }

    /**
     * 设置输入类型
     * @param view {@link TextView}
     * @param type 类型
     * @return {@link View}
     */
    public static View setInputType(
            final View view,
            final int type
    ) {
        setInputType(getTextView(view), type);
        return view;
    }

    // =

    /**
     * 获取软键盘右下角按钮类型
     * @param textView {@link TextView}
     * @param <T>      泛型
     * @return 软键盘右下角按钮类型
     */
    public static <T extends TextView> int getImeOptions(final T textView) {
        if (textView != null) {
            return textView.getImeOptions();
        }
        return 0;
    }

    /**
     * 设置软键盘右下角按钮类型
     * @param textView   {@link TextView}
     * @param imeOptions 软键盘按钮类型
     * @param <T>        泛型
     * @return {@link TextView}
     */
    public static <T extends TextView> T setImeOptions(
            final T textView,
            final int imeOptions
    ) {
        if (textView != null) {
            textView.setImeOptions(imeOptions);
        }
        return textView;
    }

    /**
     * 设置软键盘右下角按钮类型
     * @param view       {@link TextView}
     * @param imeOptions 软键盘按钮类型
     * @return {@link View}
     */
    public static View setImeOptions(
            final View view,
            final int imeOptions
    ) {
        setImeOptions(getTextView(view), imeOptions);
        return view;
    }

    // =

    /**
     * 设置行数
     * @param textView {@link TextView}
     * @param lines    行数
     * @param <T>      泛型
     * @return {@link TextView}
     */
    public static <T extends TextView> T setLines(
            final T textView,
            final int lines
    ) {
        if (textView != null) {
            textView.setLines(lines);
        }
        return textView;
    }

    /**
     * 设置行数
     * @param view  {@link TextView}
     * @param lines 行数
     * @return {@link View}
     */
    public static View setLines(
            final View view,
            final int lines
    ) {
        setLines(getTextView(view), lines);
        return view;
    }

    // =

    /**
     * 获取最大行数
     * @param textView {@link TextView}
     * @param <T>      泛型
     * @return 最大行数
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static <T extends TextView> int getMaxLines(final T textView) {
        if (textView != null) {
            return textView.getMaxLines();
        }
        return 0;
    }

    /**
     * 设置最大行数
     * @param textView {@link TextView}
     * @param maxLines 最大行数
     * @param <T>      泛型
     * @return {@link TextView}
     */
    public static <T extends TextView> T setMaxLines(
            final T textView,
            final int maxLines
    ) {
        if (textView != null) {
            textView.setMaxLines(maxLines);
        }
        return textView;
    }

    /**
     * 设置最大行数
     * @param view     {@link TextView}
     * @param maxLines 最大行数
     * @return {@link View}
     */
    public static View setMaxLines(
            final View view,
            final int maxLines
    ) {
        setMaxLines(getTextView(view), maxLines);
        return view;
    }

    // =

    /**
     * 获取最小行数
     * @param textView {@link TextView}
     * @param <T>      泛型
     * @return 最小行数
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static <T extends TextView> int getMinLines(final T textView) {
        if (textView != null) {
            return textView.getMinLines();
        }
        return 0;
    }

    /**
     * 设置最小行数
     * @param textView {@link TextView}
     * @param minLines 最小行数
     * @param <T>      泛型
     * @return {@link TextView}
     */
    public static <T extends TextView> T setMinLines(
            final T textView,
            final int minLines
    ) {
        if (textView != null && minLines > 0) {
            textView.setMinLines(minLines);
        }
        return textView;
    }

    /**
     * 设置最小行数
     * @param view     {@link TextView}
     * @param minLines 最小行数
     * @return {@link View}
     */
    public static View setMinLines(
            final View view,
            final int minLines
    ) {
        setMinLines(getTextView(view), minLines);
        return view;
    }

    // =

    /**
     * 获取最大字符宽度限制
     * @param textView {@link TextView}
     * @param <T>      泛型
     * @return 最大字符宽度限制
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static <T extends TextView> int getMaxEms(final T textView) {
        if (textView != null) {
            return textView.getMaxEms();
        }
        return 0;
    }

    /**
     * 设置最大字符宽度限制
     * @param textView {@link TextView}
     * @param maxEms   最大字符
     * @param <T>      泛型
     * @return {@link TextView}
     */
    public static <T extends TextView> T setMaxEms(
            final T textView,
            final int maxEms
    ) {
        if (textView != null && maxEms > 0) {
            textView.setMaxEms(maxEms);
        }
        return textView;
    }

    /**
     * 设置最大字符宽度限制
     * @param view   {@link TextView}
     * @param maxEms 最大字符
     * @return {@link View}
     */
    public static View setMaxEms(
            final View view,
            final int maxEms
    ) {
        setMaxEms(getTextView(view), maxEms);
        return view;
    }

    // =

    /**
     * 获取最小字符宽度限制
     * @param textView {@link TextView}
     * @param <T>      泛型
     * @return 最小字符宽度限制
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static <T extends TextView> int getMinEms(final T textView) {
        if (textView != null) {
            return textView.getMinEms();
        }
        return 0;
    }

    /**
     * 设置最小字符宽度限制
     * @param textView {@link TextView}
     * @param minEms   最小字符
     * @param <T>      泛型
     * @return {@link TextView}
     */
    public static <T extends TextView> T setMinEms(
            final T textView,
            final int minEms
    ) {
        if (textView != null && minEms > 0) {
            textView.setMinEms(minEms);
        }
        return textView;
    }

    /**
     * 设置最小字符宽度限制
     * @param view   {@link TextView}
     * @param minEms 最小字符
     * @return {@link View}
     */
    public static View setMinEms(
            final View view,
            final int minEms
    ) {
        setMinEms(getTextView(view), minEms);
        return view;
    }

    // =

    /**
     * 设置指定字符宽度
     * @param textView {@link TextView}
     * @param ems      字符
     * @param <T>      泛型
     * @return {@link TextView}
     */
    public static <T extends TextView> T setEms(
            final T textView,
            final int ems
    ) {
        if (textView != null && ems > 0) {
            textView.setEms(ems);
        }
        return textView;
    }

    /**
     * 设置指定字符宽度
     * @param view {@link TextView}
     * @param ems  字符
     * @return {@link View}
     */
    public static View setEms(
            final View view,
            final int ems
    ) {
        setEms(getTextView(view), ems);
        return view;
    }

    // =

    /**
     * 设置长度限制
     * @param textView  {@link TextView}
     * @param maxLength 长度限制
     * @param <T>       泛型
     * @return {@link TextView}
     */
    public static <T extends TextView> T setMaxLength(
            final T textView,
            final int maxLength
    ) {
        if (textView != null && maxLength > 0) {
            // 设置最大长度限制
            InputFilter[] filters = {new InputFilter.LengthFilter(maxLength)};
            textView.setFilters(filters);
        }
        return textView;
    }

    /**
     * 设置长度限制
     * @param view      {@link TextView}
     * @param maxLength 长度限制
     * @return {@link View}
     */
    public static View setMaxLength(
            final View view,
            final int maxLength
    ) {
        setMaxLength(getTextView(view), maxLength);
        return view;
    }

    // =

    /**
     * 设置长度限制, 并且设置内容
     * @param textView  {@link TextView}
     * @param content   文本内容
     * @param maxLength 长度限制
     * @param <T>       泛型
     * @return {@link TextView}
     */
    public static <T extends TextView> T setMaxLengthAndText(
            final T textView,
            final CharSequence content,
            final int maxLength
    ) {
        setText(setMaxLength(textView, maxLength), content);
        return textView;
    }

    /**
     * 设置长度限制, 并且设置内容
     * @param view      {@link TextView}
     * @param content   文本内容
     * @param maxLength 长度限制
     * @return {@link View}
     */
    public static View setMaxLengthAndText(
            final View view,
            final CharSequence content,
            final int maxLength
    ) {
        return setText(setMaxLength(view, maxLength), content);
    }

    // =

    /**
     * 获取 Ellipsize 效果
     * @param textView {@link TextView}
     * @param <T>      泛型
     * @return Ellipsize 效果
     */
    public static <T extends TextView> TextUtils.TruncateAt getEllipsize(final T textView) {
        if (textView != null) {
            return textView.getEllipsize();
        }
        return null;
    }

    /**
     * 设置 Ellipsize 效果
     * @param textView {@link TextView}
     * @param where    {@link TextUtils.TruncateAt}
     * @param <T>      泛型
     * @return {@link TextView}
     */
    public static <T extends TextView> T setEllipsize(
            final T textView,
            final TextUtils.TruncateAt where
    ) {
        if (textView != null) {
            textView.setEllipsize(where);
        }
        return textView;
    }

    /**
     * 设置 Ellipsize 效果
     * @param view  {@link TextView}
     * @param where {@link TextUtils.TruncateAt}
     * @return {@link View}
     */
    public static View setEllipsize(
            final View view,
            final TextUtils.TruncateAt where
    ) {
        setEllipsize(getTextView(view), where);
        return view;
    }

    // =

    /**
     * 获取自动识别文本类型
     * @param textView {@link TextView}
     * @param <T>      泛型
     * @return 自动识别文本类型
     */
    public static <T extends TextView> int getAutoLinkMask(final T textView) {
        if (textView != null) {
            return textView.getAutoLinkMask();
        }
        return 0;
    }

    /**
     * 设置自动识别文本链接
     * @param textView {@link TextView}
     * @param mask     {@link android.text.util.Linkify}
     * @param <T>      泛型
     * @return {@link TextView}
     */
    public static <T extends TextView> T setAutoLinkMask(
            final T textView,
            final int mask
    ) {
        if (textView != null) {
            textView.setAutoLinkMask(mask);
        }
        return textView;
    }

    /**
     * 设置自动识别文本链接
     * @param view {@link TextView}
     * @param mask {@link android.text.util.Linkify}
     * @return {@link View}
     */
    public static View setAutoLinkMask(
            final View view,
            final int mask
    ) {
        setAutoLinkMask(getTextView(view), mask);
        return view;
    }

    // =

    /**
     * 设置文本全为大写
     * @param textView {@link TextView}
     * @param allCaps  是否全部大写
     * @param <T>      泛型
     * @return {@link TextView}
     */
    public static <T extends TextView> T setAllCaps(
            final T textView,
            final boolean allCaps
    ) {
        if (textView != null) {
            textView.setAllCaps(allCaps);
        }
        return textView;
    }

    /**
     * 设置文本全为大写
     * @param view    {@link TextView}
     * @param allCaps 是否全部大写
     * @return {@link View}
     */
    public static View setAllCaps(
            final View view,
            final boolean allCaps
    ) {
        setAllCaps(getTextView(view), allCaps);
        return view;
    }

    // =

    /**
     * 获取 Gravity
     * @param textView {@link TextView}
     * @param <T>      泛型
     * @return {@link android.view.Gravity}
     */
    public static <T extends TextView> int getGravity(final T textView) {
        if (textView != null) {
            return textView.getGravity();
        }
        return 0;
    }

    /**
     * 设置 Gravity
     * @param textView {@link TextView}
     * @param gravity  {@link android.view.Gravity}
     * @param <T>      泛型
     * @return {@link TextView}
     */
    public static <T extends TextView> T setGravity(
            final T textView,
            final int gravity
    ) {
        if (textView != null) {
            textView.setGravity(gravity);
        }
        return textView;
    }

    /**
     * 设置 Gravity
     * @param view    {@link TextView}
     * @param gravity {@link android.view.Gravity}
     * @return {@link View}
     */
    public static View setGravity(
            final View view,
            final int gravity
    ) {
        setGravity(getTextView(view), gravity);
        return view;
    }

    // =

    /**
     * 获取文本视图显示转换
     * @param textView {@link TextView}
     * @param <T>      泛型
     * @return {@link TransformationMethod}
     */
    public static <T extends TextView> TransformationMethod getTransformationMethod(final T textView) {
        if (textView != null) {
            return textView.getTransformationMethod();
        }
        return null;
    }

    /**
     * 设置文本视图显示转换
     * @param textView {@link TextView}
     * @param method   {@link TransformationMethod}
     * @param <T>      泛型
     * @return {@link TextView}
     */
    public static <T extends TextView> T setTransformationMethod(
            final T textView,
            final TransformationMethod method
    ) {
        if (textView != null) {
            textView.setTransformationMethod(method);
        }
        return textView;
    }

    /**
     * 设置文本视图显示转换
     * @param view   {@link TextView}
     * @param method {@link TransformationMethod}
     * @return {@link View}
     */
    public static View setTransformationMethod(
            final View view,
            final TransformationMethod method
    ) {
        setTransformationMethod(getTextView(view), method);
        return view;
    }

    // =

    /**
     * 设置密码文本视图显示转换
     * @param textView          {@link TextView}
     * @param isDisplayPassword 是否显示密码
     * @param <T>               泛型
     * @return {@link TextView}
     */
    public static <T extends TextView> T setTransformationMethod(
            final T textView,
            final boolean isDisplayPassword
    ) {
        if (textView != null) {
            textView.setTransformationMethod(isDisplayPassword ?
                    HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance());
        }
        return textView;
    }

    /**
     * 设置密码文本视图显示转换
     * @param view              {@link TextView}
     * @param isDisplayPassword 是否显示密码
     * @return {@link View}
     */
    public static View setTransformationMethod(
            final View view,
            final boolean isDisplayPassword
    ) {
        setTransformationMethod(getTextView(view), isDisplayPassword);
        return view;
    }

    // =

    /**
     * 获取 TextView Paint
     * @param view {@link TextView}
     * @param <T>  泛型
     * @return {@link Paint}
     */
    public static <T extends TextView> Paint getPaint(final View view) {
        return getPaint(getTextView(view));
    }

    /**
     * 获取 TextView Paint
     * @param textView {@link TextView}
     * @param <T>      泛型
     * @return {@link Paint}
     */
    public static <T extends TextView> Paint getPaint(final T textView) {
        if (textView != null) {
            return textView.getPaint();
        }
        return null;
    }

    /**
     * 获取字体高度
     * @param textView {@link TextView}
     * @param <T>      泛型
     * @return 字体高度
     */
    public static <T extends TextView> int getTextHeight(final T textView) {
        return getTextHeight(getPaint(textView));
    }

    /**
     * 获取字体高度
     * @param paint {@link TextView#getPaint()}
     * @return 字体高度
     */
    public static int getTextHeight(final Paint paint) {
        if (paint != null) {
            // 获取字体高度
            Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
            // 计算内容高度
            return (int) Math.ceil((fontMetrics.descent - fontMetrics.ascent));
        }
        return -1;
    }

    // =

    /**
     * 获取字体顶部偏移高度
     * @param textView {@link TextView}
     * @param <T>      泛型
     * @return 字体顶部偏移高度
     */
    public static <T extends TextView> int getTextTopOffsetHeight(final T textView) {
        return getTextTopOffsetHeight(getPaint(textView));
    }

    /**
     * 获取字体顶部偏移高度
     * @param paint {@link TextView#getPaint()}
     * @return 字体顶部偏移高度
     */
    public static int getTextTopOffsetHeight(final Paint paint) {
        if (paint != null) {
            // 获取字体高度
            Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
            // 计算字体偏差 ( 顶部偏差 ) baseLine
            return (int) Math.ceil(Math.abs(fontMetrics.top) - Math.abs(fontMetrics.ascent));
        }
        return -1;
    }

    // =

    /**
     * 计算字体宽度
     * @param textView {@link TextView}
     * @param text     待测量文本
     * @param <T>      泛型
     * @return 字体宽度
     */
    public static <T extends TextView> float getTextWidth(
            final T textView,
            final String text
    ) {
        return getTextWidth(getPaint(textView), text);
    }

    /**
     * 计算字体宽度
     * @param textView {@link TextView}
     * @param <T>      泛型
     * @return 字体宽度
     */
    public static <T extends TextView> float getTextWidth(final T textView) {
        return getTextWidth(getPaint(textView), getText(textView));
    }

    /**
     * 计算字体宽度
     * @param paint {@link TextView#getPaint()}
     * @param text  待测量文本
     * @return 字体宽度
     */
    public static float getTextWidth(
            final Paint paint,
            final String text
    ) {
        if (paint != null && text != null) {
            return paint.measureText(text);
        }
        return -1F;
    }

    // =

    /**
     * 计算字体宽度
     * @param view  {@link TextView}
     * @param text  待测量文本
     * @param start 开始位置
     * @param end   结束位置
     * @return 字体宽度
     */
    public static float getTextWidth(
            final View view,
            final String text,
            final int start,
            final int end
    ) {
        return getTextWidth(getPaint(view), text, start, end);
    }

    /**
     * 计算字体宽度
     * @param view  {@link TextView}
     * @param text  待测量文本
     * @param start 开始位置
     * @param end   结束位置
     * @return 字体宽度
     */
    public static float getTextWidth(
            final View view,
            final CharSequence text,
            final int start,
            final int end
    ) {
        return getTextWidth(getPaint(view), text, start, end);
    }

    /**
     * 计算字体宽度
     * @param view  {@link TextView}
     * @param text  待测量文本
     * @param start 开始位置
     * @param end   结束位置
     * @return 字体宽度
     */
    public static float getTextWidth(
            final View view,
            final char[] text,
            final int start,
            final int end
    ) {
        return getTextWidth(getPaint(view), text, start, end);
    }

    // =

    /**
     * 计算字体宽度
     * @param paint {@link TextView#getPaint()}
     * @param text  待测量文本
     * @param start 开始位置
     * @param end   结束位置
     * @return 字体宽度
     */
    public static float getTextWidth(
            final Paint paint,
            final String text,
            final int start,
            final int end
    ) {
        if (paint != null && text != null) {
            try {
                return paint.measureText(text, start, end);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getTextWidth");
            }
        }
        return -1F;
    }

    /**
     * 计算字体宽度
     * @param paint {@link TextView#getPaint()}
     * @param text  待测量文本
     * @param start 开始位置
     * @param end   结束位置
     * @return 字体宽度
     */
    public static float getTextWidth(
            final Paint paint,
            final CharSequence text,
            final int start,
            final int end
    ) {
        if (paint != null && text != null) {
            try {
                return paint.measureText(text, start, end);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getTextWidth");
            }
        }
        return -1F;
    }

    /**
     * 计算字体宽度
     * @param paint {@link TextView#getPaint()}
     * @param text  待测量文本
     * @param start 开始位置
     * @param end   结束位置
     * @return 字体宽度
     */
    public static float getTextWidth(
            final Paint paint,
            final char[] text,
            final int start,
            final int end
    ) {
        if (paint != null && text != null) {
            try {
                return paint.measureText(text, start, end);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getTextWidth");
            }
        }
        return -1F;
    }

    // =

    /**
     * 获取画布中间居中位置
     * @param targetRect {@link Rect} 目标坐标
     * @param paint      {@link TextView#getPaint()}
     * @return 画布 Y 轴居中位置
     */
    public static int getCenterRectY(
            final Rect targetRect,
            final Paint paint
    ) {
        if (targetRect != null && paint != null) {
            // 获取字体高度
            Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
            // 获取底部 Y 轴居中位置
            return targetRect.top + (targetRect.bottom - targetRect.top) / 2 - (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.top;
            // canvas.drawText(testString, targetRect.centerX(), baseline, paint);
        }
        return -1;
    }

    /**
     * 通过需要的高度, 计算字体大小
     * @param height 需要的高度
     * @return 字体大小
     */
    public static float reckonTextSizeByHeight(final int height) {
        return reckonTextSizeByHeight(height, 40.0F);
    }

    /**
     * 通过需要的高度, 计算字体大小
     * @param height    需要的高度
     * @param startSize 字体开始预估大小
     * @return 字体大小
     */
    public static float reckonTextSizeByHeight(
            final int height,
            final float startSize
    ) {
        if (height <= 0 || startSize <= 0) return 0F;
        Paint paint = new Paint();
        // 默认字体大小
        float textSize = startSize;
        // 计算内容高度
        int calcTextHeight;
        // 特殊处理 ( 防止死循环记录控制 )
        int state = 0; // 1 -=, 2 +=
        // 循环计算
        while (true) {
            // 设置画笔大小
            paint.setTextSize(textSize);
            // 获取字体高度
            Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
            // 计算内容高度
            calcTextHeight = (int) Math.ceil((fontMetrics.descent - fontMetrics.ascent));
            // 符合条件则直接返回
            if (calcTextHeight == height) {
                return textSize;
            } else if (calcTextHeight > height) { // 如果计算的字体高度大于
                textSize -= 0.5F;
                state = 1;
            } else {
                textSize += 0.5F;
                if (state == 1) {
                    return textSize;
                }
                state = 2;
            }
        }
    }

    /**
     * 通过需要的宽度, 计算字体大小 ( 最接近该宽度的字体大小 )
     * @param width    需要的宽度
     * @param textView {@link TextView}
     * @return 字体大小
     */
    public static float reckonTextSizeByWidth(
            final int width,
            final TextView textView
    ) {
        return reckonTextSizeByWidth(width, textView, TextViewUtils.getText(textView));
    }

    /**
     * 通过需要的宽度, 计算字体大小 ( 最接近该宽度的字体大小 )
     * @param width    需要的宽度
     * @param textView {@link TextView}
     * @param content  待计算内容
     * @return 字体大小
     */
    public static float reckonTextSizeByWidth(
            final int width,
            final TextView textView,
            final String content
    ) {
        if (textView == null || content == null) return 0F;
        return reckonTextSizeByWidth(width, TextViewUtils.getPaint(textView),
                TextViewUtils.getTextSize(textView), content
        );
    }

    /**
     * 通过需要的宽度, 计算字体大小 ( 最接近该宽度的字体大小 )
     * @param width       需要的宽度
     * @param curTextSize 当前字体大小
     * @param content     待计算内容
     * @return 字体大小
     */
    public static float reckonTextSizeByWidth(
            final int width,
            final float curTextSize,
            final String content
    ) {
        if (width <= 0 || curTextSize <= 0 || content == null) return 0F;
        return reckonTextSizeByWidth(width, new Paint(), curTextSize, content);
    }

    /**
     * 通过需要的宽度, 计算字体大小 ( 最接近该宽度的字体大小 )
     * @param width       需要的宽度
     * @param paint       {@link Paint}
     * @param curTextSize 当前字体大小
     * @param content     待计算内容
     * @return 字体大小
     */
    public static float reckonTextSizeByWidth(
            final int width,
            final Paint paint,
            final float curTextSize,
            final String content
    ) {
        if (paint == null || width <= 0 || curTextSize <= 0 || content == null) return 0F;
        if (TextUtils.isEmpty(content)) return curTextSize;
        // 初始化内容画笔, 计算宽高
        TextPaint tvPaint = new TextPaint(paint);
        // 字体大小
        float textSize = curTextSize;
        // 字体内容宽度
        int calcTextWidth;
        // 特殊处理 ( 防止死循环记录控制 )
        int state = 0; // 1 -=, 2 +=
        // 循环计算
        while (true) {
            // 设置画笔大小
            tvPaint.setTextSize(textSize);
            // 获取字体内容宽度
            calcTextWidth = (int) tvPaint.measureText(content);
            // 符合条件则直接返回
            if (calcTextWidth == width) {
                return textSize;
            } else if (calcTextWidth > width) { // 如果计算的字体宽度大于
                textSize -= 0.5F;
                state = 1;
            } else {
                textSize += 0.5F;
                if (state == 1) {
                    return textSize;
                }
                state = 2;
            }
        }
    }

    // =

    /**
     * 计算第几位超过宽度
     * @param textView {@link TextView}
     * @param text     待测量文本
     * @param width    指定的宽度
     * @param <T>      泛型
     * @return -1 表示没超过, 其他值表示对应的索引位置
     */
    public static <T extends TextView> int calcTextWidth(
            final T textView,
            final String text,
            final float width
    ) {
        return calcTextWidth(getPaint(textView), text, width);
    }

    /**
     * 计算第几位超过宽度
     * @param textView {@link TextView}
     * @param width    指定的宽度
     * @param <T>      泛型
     * @return -1 表示没超过, 其他值表示对应的索引位置
     */
    public static <T extends TextView> int calcTextWidth(
            final T textView,
            final float width
    ) {
        return calcTextWidth(getPaint(textView), getText(textView), width);
    }

    /**
     * 计算第几位超过宽度
     * @param paint {@link TextView#getPaint()}
     * @param text  文本内容
     * @param width 指定的宽度
     * @return -1 表示没超过, 其他值表示对应的索引位置
     */
    public static int calcTextWidth(
            final Paint paint,
            final String text,
            final float width
    ) {
        if (paint != null && text != null && width > 0) {
            // 全部文本宽度
            float allTextWidth = getTextWidth(paint, text);
            // 判断是否超过
            if (allTextWidth <= width) return -1; // 表示没超过
            // 获取数据长度
            int length = text.length();
            // 超过长度且只有一个数据, 那么只能是第一个就超过了
            if (length == 1) return 1;
            // 二分法寻找最佳位置
            int start = 0;
            int end   = length;
            int mid   = 0;
            // 判断是否大于位置
            while (start < end) {
                mid = (start + end) / 2;
                // 获取字体宽度
                float textWidth = getTextWidth(paint, text, 0, mid);
                // 如果相等直接返回
                if (textWidth == width) {
                    return mid;
                } else if (textWidth > width) {
                    end = mid - 1;
                } else {
                    start = mid + 1;
                }
            }
            // 计算最符合的位置
            for (int i = Math.min(Math.min(start, mid), end); i <= length; i++) {
                float textWidth = TextViewUtils.getTextWidth(paint, text, 0, i);
                if (textWidth >= width) return i;
            }
            return start;
        }
        return -1;
    }

    /**
     * 计算文本换行行数
     * @param textView {@link TextView}
     * @param text     待测量文本
     * @param width    指定的宽度
     * @param <T>      泛型
     * @return 行数
     */
    public static <T extends TextView> int calcTextLine(
            final T textView,
            final String text,
            final float width
    ) {
        return calcTextLine(getPaint(textView), text, width);
    }

    /**
     * 计算文本行数
     * @param textView {@link TextView}
     * @param width    指定的宽度
     * @param <T>      泛型
     * @return 行数
     */
    public static <T extends TextView> int calcTextLine(
            final T textView,
            final float width
    ) {
        return calcTextLine(getPaint(textView), getText(textView), width);
    }

    /**
     * 计算文本行数
     * @param paint {@link TextView#getPaint()}
     * @param text  文本内容
     * @param width 指定的宽度
     * @return 行数
     */
    public static int calcTextLine(
            final Paint paint,
            final String text,
            final float width
    ) {
        if (paint != null && text != null && width > 0) {
            // 全部文本宽度
            float allTextWidth = getTextWidth(paint, text);
            // 判断是否超过
            if (allTextWidth <= width) return 1;
            int result = (int) (allTextWidth / width);
            return ((allTextWidth - width * result == 0F) ? result : result + 1);
        }
        return 0;
    }

    // =====================
    // = CompoundDrawables =
    // =====================

    /**
     * 获取 CompoundDrawables
     * @param textView {@link TextView}
     * @param <T>      泛型
     * @return Drawable[] { left, top, right, bottom }
     */
    public static <T extends TextView> Drawable[] getCompoundDrawables(final T textView) {
        if (textView != null) {
            return textView.getCompoundDrawables();
        }
        return new Drawable[]{null, null, null, null};
    }

    /**
     * 获取 CompoundDrawables Padding
     * @param textView {@link TextView}
     * @param <T>      泛型
     * @return CompoundDrawables Padding
     */
    public static <T extends TextView> int getCompoundDrawablePadding(final T textView) {
        if (textView != null) {
            return textView.getCompoundDrawablePadding();
        }
        return 0;
    }

    /**
     * 设置 CompoundDrawables Padding
     * @param textView {@link TextView}
     * @param padding  CompoundDrawables Padding
     * @param <T>      泛型
     * @return {@link View}
     */
    public static <T extends TextView> T setCompoundDrawablePadding(
            final T textView,
            final int padding
    ) {
        if (textView != null) textView.setCompoundDrawablePadding(padding);
        return textView;
    }

    // ========================
    // = setCompoundDrawables =
    // ========================

    /**
     * 设置 Left CompoundDrawables
     * @param textView {@link TextView}
     * @param left     left Drawable
     * @param <T>      泛型
     * @return {@link View}
     */
    public static <T extends TextView> T setCompoundDrawablesByLeft(
            final T textView,
            final Drawable left
    ) {
        return setCompoundDrawables(textView, left, null, null, null);
    }

    /**
     * 设置 Top CompoundDrawables
     * @param textView {@link TextView}
     * @param top      top Drawable
     * @param <T>      泛型
     * @return {@link View}
     */
    public static <T extends TextView> T setCompoundDrawablesByTop(
            final T textView,
            final Drawable top
    ) {
        return setCompoundDrawables(textView, null, top, null, null);
    }

    /**
     * 设置 Right CompoundDrawables
     * @param textView {@link TextView}
     * @param right    right Drawable
     * @param <T>      泛型
     * @return {@link View}
     */
    public static <T extends TextView> T setCompoundDrawablesByRight(
            final T textView,
            final Drawable right
    ) {
        return setCompoundDrawables(textView, null, null, right, null);
    }

    /**
     * 设置 Bottom CompoundDrawables
     * @param textView {@link TextView}
     * @param bottom   bottom Drawable
     * @param <T>      泛型
     * @return {@link View}
     */
    public static <T extends TextView> T setCompoundDrawablesByBottom(
            final T textView,
            final Drawable bottom
    ) {
        return setCompoundDrawables(textView, null, null, null, bottom);
    }

    /**
     * 设置 CompoundDrawables
     * <pre>
     *     CompoundDrawable 的大小控制是通过 drawable.setBounds() 控制
     *     需要先设置 Drawable 的 setBounds
     *     {@link dev.utils.app.image.ImageUtils#setBounds}
     * </pre>
     * @param textView {@link TextView}
     * @param left     left Drawable
     * @param top      top Drawable
     * @param right    right Drawable
     * @param bottom   bottom Drawable
     * @param <T>      泛型
     * @return {@link View}
     */
    public static <T extends TextView> T setCompoundDrawables(
            final T textView,
            final Drawable left,
            final Drawable top,
            final Drawable right,
            final Drawable bottom
    ) {
        if (textView != null) {
            try {
                textView.setCompoundDrawables(left, top, right, bottom);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setCompoundDrawables");
            }
        }
        return textView;
    }

    // ===========================================
    // = setCompoundDrawablesWithIntrinsicBounds =
    // ===========================================

    /**
     * 设置 Left CompoundDrawables ( 按照原有比例大小显示图片 )
     * @param textView {@link TextView}
     * @param left     left Drawable
     * @param <T>      泛型
     * @return {@link View}
     */
    public static <T extends TextView> T setCompoundDrawablesWithIntrinsicBoundsByLeft(
            final T textView,
            final Drawable left
    ) {
        return setCompoundDrawablesWithIntrinsicBounds(textView, left, null, null, null);
    }

    /**
     * 设置 Top CompoundDrawables ( 按照原有比例大小显示图片 )
     * @param textView {@link TextView}
     * @param top      top Drawable
     * @param <T>      泛型
     * @return {@link View}
     */
    public static <T extends TextView> T setCompoundDrawablesWithIntrinsicBoundsByTop(
            final T textView,
            final Drawable top
    ) {
        return setCompoundDrawablesWithIntrinsicBounds(textView, null, top, null, null);
    }

    /**
     * 设置 Right CompoundDrawables ( 按照原有比例大小显示图片 )
     * @param textView {@link TextView}
     * @param right    right Drawable
     * @param <T>      泛型
     * @return {@link View}
     */
    public static <T extends TextView> T setCompoundDrawablesWithIntrinsicBoundsByRight(
            final T textView,
            final Drawable right
    ) {
        return setCompoundDrawablesWithIntrinsicBounds(textView, null, null, right, null);
    }

    /**
     * 设置 Bottom CompoundDrawables ( 按照原有比例大小显示图片 )
     * @param textView {@link TextView}
     * @param bottom   bottom Drawable
     * @param <T>      泛型
     * @return {@link View}
     */
    public static <T extends TextView> T setCompoundDrawablesWithIntrinsicBoundsByBottom(
            final T textView,
            final Drawable bottom
    ) {
        return setCompoundDrawablesWithIntrinsicBounds(textView, null, null, null, bottom);
    }

    /**
     * 设置 CompoundDrawables ( 按照原有比例大小显示图片 )
     * @param textView {@link TextView}
     * @param left     left Drawable
     * @param top      top Drawable
     * @param right    right Drawable
     * @param bottom   bottom Drawable
     * @param <T>      泛型
     * @return {@link View}
     */
    public static <T extends TextView> T setCompoundDrawablesWithIntrinsicBounds(
            final T textView,
            final Drawable left,
            final Drawable top,
            final Drawable right,
            final Drawable bottom
    ) {
        if (textView != null) {
            try {
                textView.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setCompoundDrawablesWithIntrinsicBounds");
            }
        }
        return textView;
    }

    // =============
    // = AppCompat =
    // =============

    // ================
    // = AutoSizeable =
    // ================

    /**
     * 通过设置默认的自动调整大小配置, 决定是否自动缩放文本
     * @param view             {@link TextView}
     * @param autoSizeTextType 自动调整大小类型
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setAutoSizeTextTypeWithDefaults(
            final View view,
            @TextViewCompat.AutoSizeTextType final int autoSizeTextType
    ) {
        TextView textView = getTextView(view);
        if (textView != null) {
            try {
                TextViewCompat.setAutoSizeTextTypeWithDefaults(
                        textView, autoSizeTextType
                );
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setAutoSizeTextTypeWithDefaults");
            }
        }
        return false;
    }

    /**
     * 设置 TextView 自动调整字体大小配置
     * @param view                    {@link TextView}
     * @param autoSizeMinTextSize     自动调整最小字体大小
     * @param autoSizeMaxTextSize     自动调整最大字体大小
     * @param autoSizeStepGranularity 自动调整大小变动粒度 ( 跨度区间值 )
     * @param unit                    字体参数类型
     */
    public static boolean setAutoSizeTextTypeUniformWithConfiguration(
            final View view,
            final int autoSizeMinTextSize,
            final int autoSizeMaxTextSize,
            final int autoSizeStepGranularity,
            final int unit
    ) {
        TextView textView = getTextView(view);
        if (textView != null) {
            try {
                TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(
                        textView, autoSizeMinTextSize, autoSizeMaxTextSize,
                        autoSizeStepGranularity, unit
                );
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setAutoSizeTextTypeUniformWithConfiguration");
            }
        }
        return false;
    }

    /**
     * 设置 TextView 自动调整如果预设字体大小范围有效则修改类型为 AUTO_SIZE_TEXT_TYPE_UNIFORM
     * @param view        {@link TextView}
     * @param presetSizes 预设字体大小范围像素为单位
     * @param unit        字体参数类型
     */
    public static boolean setAutoSizeTextTypeUniformWithPresetSizes(
            final View view,
            final int[] presetSizes,
            final int unit
    ) {
        TextView textView = getTextView(view);
        if (textView != null) {
            try {
                TextViewCompat.setAutoSizeTextTypeUniformWithPresetSizes(
                        textView, presetSizes, unit
                );
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setAutoSizeTextTypeUniformWithPresetSizes");
            }
        }
        return false;
    }

    /**
     * 获取 TextView 自动调整大小类型
     * @param view {@link TextView}
     * @return 自动调整大小类型
     */
    @TextViewCompat.AutoSizeTextType
    public static int getAutoSizeTextType(final View view) {
        TextView textView = getTextView(view);
        if (textView != null) {
            try {
                return TextViewCompat.getAutoSizeTextType(textView);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getAutoSizeTextType");
            }
        }
        return TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE;
    }

    /**
     * 获取 TextView 自动调整大小变动粒度 ( 跨度区间值 )
     * @param view {@link TextView}
     * @return 自动调整大小变动粒度 ( 跨度区间值 )
     */
    public static int getAutoSizeStepGranularity(final View view) {
        TextView textView = getTextView(view);
        if (textView != null) {
            try {
                return TextViewCompat.getAutoSizeStepGranularity(textView);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getAutoSizeStepGranularity");
            }
        }
        return -1;
    }

    /**
     * 获取 TextView 自动调整最小字体大小
     * @param view {@link TextView}
     * @return 自动调整最小字体大小
     */
    public static int getAutoSizeMinTextSize(final View view) {
        TextView textView = getTextView(view);
        if (textView != null) {
            try {
                return TextViewCompat.getAutoSizeMinTextSize(textView);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getAutoSizeMinTextSize");
            }
        }
        return -1;
    }

    /**
     * 获取 TextView 自动调整最大字体大小
     * @param view {@link TextView}
     * @return 自动调整最大字体大小
     */
    public static int getAutoSizeMaxTextSize(final View view) {
        TextView textView = getTextView(view);
        if (textView != null) {
            try {
                return TextViewCompat.getAutoSizeMaxTextSize(textView);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getAutoSizeMaxTextSize");
            }
        }
        return -1;
    }

    /**
     * 获取 TextView 自动调整大小预设范围数组
     * @param view {@link TextView}
     * @return 自动调整大小预设范围数组
     */
    public static int[] getAutoSizeTextAvailableSizes(final View view) {
        TextView textView = getTextView(view);
        if (textView != null) {
            try {
                return TextViewCompat.getAutoSizeTextAvailableSizes(textView);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getAutoSizeTextAvailableSizes");
            }
        }
        return new int[0];
    }
}