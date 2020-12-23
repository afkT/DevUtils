package dev.utils.app.helper;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.text.method.TransformationMethod;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;
import androidx.annotation.IdRes;

import dev.utils.app.ClickUtils;
import dev.utils.app.EditTextUtils;
import dev.utils.app.HandlerUtils;
import dev.utils.app.ImageViewUtils;
import dev.utils.app.ListViewUtils;
import dev.utils.app.ListenerUtils;
import dev.utils.app.TextViewUtils;
import dev.utils.app.ViewUtils;

/**
 * detail: View 链式调用快捷设置 Helper 类
 * @author Ttt
 * <pre>
 *     通过 DevApp 工具类快捷实现
 *     <p></p>
 *     DevApp Api
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevApp/README.md"/>
 * </pre>
 */
public final class ViewHelper {

    // ViewHelper
    private static final ViewHelper HELPER = new ViewHelper();

    /**
     * 获取单例 ViewHelper
     * @return {@link ViewHelper}
     */
    public static ViewHelper get() {
        return HELPER;
    }

    // ==========
    // = Helper =
    // ==========

    /**
     * 获取 ViewHelper
     * @return {@link ViewHelper}
     */
    public ViewHelper viewHelper() {
        return HELPER;
    }

    /**
     * 获取 DevHelper
     * @return {@link DevHelper}
     */
    public DevHelper devHelper() {
        return DevHelper.get();
    }

    /**
     * 获取 QuickHelper
     * @param target 目标 View
     * @return {@link QuickHelper}
     */
    public QuickHelper quickHelper(final View target) {
        return QuickHelper.get(target);
    }

    // ===========
    // = Handler =
    // ===========

    /**
     * 在主线程 Handler 中执行任务
     * @param runnable 可执行的任务
     * @return {@link ViewHelper}
     */
    public ViewHelper postRunnable(final Runnable runnable) {
        HandlerUtils.postRunnable(runnable);
        return this;
    }

    /**
     * 在主线程 Handler 中执行延迟任务
     * @param runnable    可执行的任务
     * @param delayMillis 延迟时间
     * @return {@link ViewHelper}
     */
    public ViewHelper postRunnable(
            final Runnable runnable,
            final long delayMillis
    ) {
        HandlerUtils.postRunnable(runnable, delayMillis);
        return this;
    }

    /**
     * 在主线程 Handler 中执行延迟任务
     * @param runnable    可执行的任务
     * @param delayMillis 延迟时间
     * @param number      轮询次数
     * @param interval    轮询时间
     * @return {@link ViewHelper}
     */
    public ViewHelper postRunnable(
            final Runnable runnable,
            final long delayMillis,
            final int number,
            final int interval
    ) {
        HandlerUtils.postRunnable(runnable, delayMillis, number, interval);
        return this;
    }

    /**
     * 在主线程 Handler 中执行延迟任务
     * @param runnable      可执行的任务
     * @param delayMillis   延迟时间
     * @param number        轮询次数
     * @param interval      轮询时间
     * @param onEndListener 结束通知
     * @return {@link ViewHelper}
     */
    public ViewHelper postRunnable(
            final Runnable runnable,
            final long delayMillis,
            final int number,
            final int interval,
            final HandlerUtils.OnEndListener onEndListener
    ) {
        HandlerUtils.postRunnable(runnable, delayMillis, number, interval, onEndListener);
        return this;
    }

    /**
     * 在主线程 Handler 中清除任务
     * @param runnable 需要清除的任务
     * @return {@link ViewHelper}
     */
    public ViewHelper removeRunnable(final Runnable runnable) {
        HandlerUtils.removeRunnable(runnable);
        return this;
    }

    // ========
    // = Text =
    // ========

    /**
     * 设置 Hint 文本
     * @param view {@link TextView}
     * @param text Hint text
     * @return {@link ViewHelper}
     */
    public ViewHelper setHint(
            final View view,
            final CharSequence text
    ) {
        TextViewUtils.setHint(view, text);
        return this;
    }

    /**
     * 设置文本
     * @param view {@link TextView}
     * @param text TextView text
     * @return {@link ViewHelper}
     */
    public ViewHelper setText(
            final View view,
            final CharSequence text
    ) {
        if (view instanceof EditText) {
            EditTextUtils.setText(EditTextUtils.getEditText(view), text);
        } else {
            TextViewUtils.setText(view, text);
        }
        return this;
    }

    /**
     * 设置多个 TextView 文本
     * @param text  TextView text
     * @param views View(TextView)[]
     * @return {@link ViewHelper}
     */
    public ViewHelper setTexts(
            final CharSequence text,
            final View... views
    ) {
        TextViewUtils.setTexts(text, views);
        return this;
    }

    /**
     * 设置 Html 内容
     * @param view    {@link TextView}
     * @param content Html content
     * @return {@link ViewHelper}
     */
    public ViewHelper setHtmlText(
            final View view,
            final String content
    ) {
        TextViewUtils.setHtmlText(view, content);
        return this;
    }

    /**
     * 设置多个 TextView Html 内容
     * @param content Html content
     * @param views   View(TextView)[]
     * @return {@link ViewHelper}
     */
    public ViewHelper setHtmlTexts(
            final String content,
            final View... views
    ) {
        TextViewUtils.setHtmlTexts(content, views);
        return this;
    }

    /**
     * 设置 Hint 字体颜色
     * @param view  {@link TextView}
     * @param color R.color.id
     * @return {@link ViewHelper}
     */
    public ViewHelper setHintTextColor(
            final View view,
            @ColorInt final int color
    ) {
        TextViewUtils.setHintTextColor(view, color);
        return this;
    }

    /**
     * 设置 Hint 字体颜色
     * @param view   {@link TextView}
     * @param colors {@link ColorStateList}
     * @return {@link ViewHelper}
     */
    public ViewHelper setHintTextColor(
            final View view,
            final ColorStateList colors
    ) {
        TextViewUtils.setHintTextColor(view, colors);
        return this;
    }

    /**
     * 设置多个 TextView Hint 字体颜色
     * @param color R.color.id
     * @param views View(TextView)[]
     * @return {@link ViewHelper}
     */
    public ViewHelper setHintTextColors(
            @ColorInt final int color,
            final View... views
    ) {
        TextViewUtils.setHintTextColors(color, views);
        return this;
    }

    /**
     * 设置多个 TextView Hint 字体颜色
     * @param colors {@link ColorStateList}
     * @param views  View(TextView)[]
     * @return {@link ViewHelper}
     */
    public ViewHelper setHintTextColors(
            final ColorStateList colors,
            final View... views
    ) {
        TextViewUtils.setHintTextColors(colors, views);
        return this;
    }

    /**
     * 设置字体颜色
     * @param view  {@link TextView}
     * @param color R.color.id
     * @return {@link ViewHelper}
     */
    public ViewHelper setTextColor(
            final View view,
            @ColorInt final int color
    ) {
        TextViewUtils.setTextColor(view, color);
        return this;
    }

    /**
     * 设置字体颜色
     * @param view   {@link TextView}
     * @param colors {@link ColorStateList}
     * @return {@link ViewHelper}
     */
    public ViewHelper setTextColor(
            final View view,
            final ColorStateList colors
    ) {
        TextViewUtils.setTextColor(view, colors);
        return this;
    }

    /**
     * 设置多个 TextView 字体颜色
     * @param color R.color.id
     * @param views View(TextView)[]
     * @return {@link ViewHelper}
     */
    public ViewHelper setTextColors(
            @ColorInt final int color,
            final View... views
    ) {
        TextViewUtils.setTextColors(color, views);
        return this;
    }

    /**
     * 设置多个 TextView 字体颜色
     * @param colors {@link ColorStateList}
     * @param views  View(TextView)[]
     * @return {@link ViewHelper}
     */
    public ViewHelper setTextColors(
            final ColorStateList colors,
            final View... views
    ) {
        TextViewUtils.setTextColors(colors, views);
        return this;
    }

    /**
     * 设置字体
     * @param view     {@link TextView}
     * @param typeface {@link Typeface} 字体样式
     * @return {@link ViewHelper}
     */
    public ViewHelper setTypeface(
            final View view,
            final Typeface typeface
    ) {
        TextViewUtils.setTypeface(view, typeface);
        return this;
    }

    /**
     * 设置字体大小 ( px 像素 )
     * @param view {@link TextView}
     * @param size 字体大小
     * @return {@link ViewHelper}
     */
    public ViewHelper setTextSizeByPx(
            final View view,
            final float size
    ) {
        TextViewUtils.setTextSizeByPx(view, size);
        return this;
    }

    /**
     * 设置字体大小 ( sp 缩放像素 )
     * @param view {@link TextView}
     * @param size 字体大小
     * @return {@link ViewHelper}
     */
    public ViewHelper setTextSizeBySp(
            final View view,
            final float size
    ) {
        TextViewUtils.setTextSizeBySp(view, size);
        return this;
    }

    /**
     * 设置字体大小 ( dp 与设备无关的像素 )
     * @param view {@link TextView}
     * @param size 字体大小
     * @return {@link ViewHelper}
     */
    public ViewHelper setTextSizeByDp(
            final View view,
            final float size
    ) {
        TextViewUtils.setTextSizeByDp(view, size);
        return this;
    }

    /**
     * 设置字体大小 ( inches 英寸 )
     * @param view {@link TextView}
     * @param size 字体大小
     * @return {@link ViewHelper}
     */
    public ViewHelper setTextSizeByIn(
            final View view,
            final float size
    ) {
        TextViewUtils.setTextSizeByIn(view, size);
        return this;
    }

    /**
     * 设置字体大小
     * @param view {@link TextView}
     * @param unit 字体参数类型
     * @param size 字体大小
     * @return {@link ViewHelper}
     */
    public ViewHelper setTextSize(
            final View view,
            final int unit,
            final float size
    ) {
        TextViewUtils.setTextSize(view, unit, size);
        return this;
    }

    /**
     * 设置多个 TextView 字体大小
     * @param views View(TextView)[]
     * @param unit  参数类型
     * @param size  字体大小
     * @return {@link ViewHelper}
     */
    public ViewHelper setTextSizes(
            final View[] views,
            final int unit,
            final float size
    ) {
        TextViewUtils.setTextSizes(views, unit, size);
        return this;
    }

    /**
     * 清空 flags
     * @param view {@link TextView}
     * @return {@link ViewHelper}
     */
    public ViewHelper clearFlags(final View view) {
        TextViewUtils.clearFlags(view);
        return this;
    }

    /**
     * 设置 TextView flags
     * @param view  {@link TextView}
     * @param flags flags
     * @return {@link ViewHelper}
     */
    public ViewHelper setPaintFlags(
            final View view,
            final int flags
    ) {
        TextViewUtils.setPaintFlags(view, flags);
        return this;
    }

    /**
     * 设置 TextView 抗锯齿 flags
     * @param view {@link TextView}
     * @return {@link ViewHelper}
     */
    public ViewHelper setAntiAliasFlag(final View view) {
        TextViewUtils.setAntiAliasFlag(view);
        return this;
    }

    /**
     * 设置 TextView 是否加粗
     * @param view {@link TextView}
     * @return {@link ViewHelper}
     */
    public ViewHelper setBold(final View view) {
        TextViewUtils.setBold(view);
        return this;
    }

    /**
     * 设置 TextView 是否加粗
     * @param view   {@link TextView}
     * @param isBold {@code true} yes, {@code false} no
     * @return {@link ViewHelper}
     */
    public ViewHelper setBold(
            final View view,
            final boolean isBold
    ) {
        TextViewUtils.setBold(view, isBold);
        return this;
    }

    /**
     * 设置 TextView 是否加粗
     * @param view     {@link TextView}
     * @param typeface {@link Typeface} 字体样式
     * @param isBold   {@code true} yes, {@code false} no
     * @return {@link ViewHelper}
     */
    public ViewHelper setBold(
            final View view,
            final Typeface typeface,
            final boolean isBold
    ) {
        TextViewUtils.setBold(view, typeface, isBold);
        return this;
    }

    /**
     * 设置下划线
     * @param view {@link TextView}
     * @return {@link ViewHelper}
     */
    public ViewHelper setUnderlineText(final View view) {
        TextViewUtils.setUnderlineText(view);
        return this;
    }

    /**
     * 设置下划线并加清晰
     * @param view        {@link TextView}
     * @param isAntiAlias 是否消除锯齿
     * @return {@link ViewHelper}
     */
    public ViewHelper setUnderlineText(
            final View view,
            final boolean isAntiAlias
    ) {
        TextViewUtils.setUnderlineText(view, isAntiAlias);
        return this;
    }

    /**
     * 设置中划线
     * @param view {@link TextView}
     * @return {@link ViewHelper}
     */
    public ViewHelper setStrikeThruText(final View view) {
        TextViewUtils.setStrikeThruText(view);
        return this;
    }

    /**
     * 设置中划线并加清晰
     * @param view        {@link TextView}
     * @param isAntiAlias 是否消除锯齿
     * @return {@link ViewHelper}
     */
    public ViewHelper setStrikeThruText(
            final View view,
            final boolean isAntiAlias
    ) {
        TextViewUtils.setStrikeThruText(view, isAntiAlias);
        return this;
    }

    /**
     * 设置文字水平间距
     * @param view          {@link TextView}
     * @param letterSpacing 文字水平间距值
     * @return {@link ViewHelper}
     */
    public ViewHelper setLetterSpacing(
            final View view,
            final float letterSpacing
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            TextViewUtils.setLetterSpacing(view, letterSpacing);
        }
        return this;
    }

    /**
     * 设置文字行间距 ( 行高 )
     * @param view        {@link TextView}
     * @param lineSpacing 文字行间距 ( 行高 ), android:lineSpacingExtra
     * @return {@link ViewHelper}
     */
    public ViewHelper setLineSpacing(
            final View view,
            final float lineSpacing
    ) {
        TextViewUtils.setLineSpacing(view, lineSpacing);
        return this;
    }

    /**
     * 设置文字行间距 ( 行高 )、行间距倍数
     * @param view        {@link TextView}
     * @param lineSpacing 文字行间距 ( 行高 ), android:lineSpacingExtra
     * @param multiplier  行间距倍数, android:lineSpacingMultiplier
     * @return {@link ViewHelper}
     */
    public ViewHelper setLineSpacingAndMultiplier(
            final View view,
            final float lineSpacing,
            final float multiplier
    ) {
        TextViewUtils.setLineSpacingAndMultiplier(view, lineSpacing, multiplier);
        return this;
    }

    /**
     * 设置字体水平方向的缩放
     * @param view {@link TextView}
     * @param size 缩放比例
     * @return {@link ViewHelper}
     */
    public ViewHelper setTextScaleX(
            final View view,
            final float size
    ) {
        TextViewUtils.setTextScaleX(view, size);
        return this;
    }

    /**
     * 设置是否保留字体留白间隙区域
     * @param view           {@link TextView}
     * @param includePadding 是否保留字体留白间隙区域
     * @return {@link ViewHelper}
     */
    public ViewHelper setIncludeFontPadding(
            final View view,
            final boolean includePadding
    ) {
        TextViewUtils.setIncludeFontPadding(view, includePadding);
        return this;
    }

    /**
     * 设置输入类型
     * @param view {@link TextView}
     * @param type 类型
     * @return {@link ViewHelper}
     */
    public ViewHelper setInputType(
            final View view,
            final int type
    ) {
        TextViewUtils.setInputType(view, type);
        return this;
    }

    /**
     * 设置软键盘右下角按钮类型
     * @param view       {@link TextView}
     * @param imeOptions 软键盘按钮类型
     * @return {@link ViewHelper}
     */
    public ViewHelper setImeOptions(
            final View view,
            final int imeOptions
    ) {
        TextViewUtils.setImeOptions(view, imeOptions);
        return this;
    }

    /**
     * 设置行数
     * @param view  {@link TextView}
     * @param lines 行数
     * @return {@link ViewHelper}
     */
    public ViewHelper setLines(
            final View view,
            final int lines
    ) {
        TextViewUtils.setLines(view, lines);
        return this;
    }

    /**
     * 设置最大行数
     * @param view     {@link TextView}
     * @param maxLines 最大行数
     * @return {@link ViewHelper}
     */
    public ViewHelper setMaxLines(
            final View view,
            final int maxLines
    ) {
        TextViewUtils.setMaxLines(view, maxLines);
        return this;
    }

    /**
     * 设置最小行数
     * @param view     {@link TextView}
     * @param minLines 最小行数
     * @return {@link ViewHelper}
     */
    public ViewHelper setMinLines(
            final View view,
            final int minLines
    ) {
        TextViewUtils.setMinLines(view, minLines);
        return this;
    }

    /**
     * 设置最大字符宽度限制
     * @param view   {@link TextView}
     * @param maxEms 最大字符
     * @return {@link ViewHelper}
     */
    public ViewHelper setMaxEms(
            final View view,
            final int maxEms
    ) {
        TextViewUtils.setMaxEms(view, maxEms);
        return this;
    }

    /**
     * 设置最小字符宽度限制
     * @param view   {@link TextView}
     * @param minEms 最小字符
     * @return {@link ViewHelper}
     */
    public ViewHelper setMinEms(
            final View view,
            final int minEms
    ) {
        TextViewUtils.setMinEms(view, minEms);
        return this;
    }

    /**
     * 设置指定字符宽度
     * @param view {@link TextView}
     * @param ems  字符
     * @return {@link ViewHelper}
     */
    public ViewHelper setEms(
            final View view,
            final int ems
    ) {
        TextViewUtils.setEms(view, ems);
        return this;
    }

    /**
     * 设置 Ellipsize 效果
     * @param view  {@link TextView}
     * @param where {@link TextUtils.TruncateAt}
     * @return {@link ViewHelper}
     */
    public ViewHelper setEllipsize(
            final View view,
            final TextUtils.TruncateAt where
    ) {
        TextViewUtils.setEllipsize(view, where);
        return this;
    }

    /**
     * 设置自动识别文本链接
     * @param view {@link TextView}
     * @param mask {@link android.text.util.Linkify}
     * @return {@link ViewHelper}
     */
    public ViewHelper setAutoLinkMask(
            final View view,
            final int mask
    ) {
        TextViewUtils.setAutoLinkMask(view, mask);
        return this;
    }

    /**
     * 设置文本全为大写
     * @param view    {@link TextView}
     * @param allCaps 是否全部大写
     * @return {@link ViewHelper}
     */
    public ViewHelper setAllCaps(
            final View view,
            final boolean allCaps
    ) {
        TextViewUtils.setAllCaps(view, allCaps);
        return this;
    }

    /**
     * 设置 Text Gravity
     * @param view    {@link TextView}
     * @param gravity {@link android.view.Gravity}
     * @return {@link ViewHelper}
     */
    public ViewHelper setTextGravity(
            final View view,
            final int gravity
    ) {
        TextViewUtils.setGravity(view, gravity);
        return this;
    }

    // ============
    // = EditText =
    // ============

    /**
     * 设置内容
     * @param editText {@link EditText}
     * @param content  文本内容
     * @param isSelect 是否设置光标
     * @return {@link ViewHelper}
     */
    public ViewHelper setText(
            final EditText editText,
            final CharSequence content,
            final boolean isSelect
    ) {
        EditTextUtils.setText(editText, content, isSelect);
        return this;
    }

    /**
     * 追加内容 ( 当前光标位置追加 )
     * @param editText {@link EditText}
     * @param content  文本内容
     * @param isSelect 是否设置光标
     * @return {@link ViewHelper}
     */
    public ViewHelper insert(
            final EditText editText,
            final CharSequence content,
            final boolean isSelect
    ) {
        EditTextUtils.insert(editText, content, isSelect);
        return this;
    }

    /**
     * 追加内容
     * @param editText {@link EditText}
     * @param content  文本内容
     * @param start    开始添加的位置
     * @param isSelect 是否设置光标
     * @return {@link ViewHelper}
     */
    public ViewHelper insert(
            final EditText editText,
            final CharSequence content,
            final int start,
            final boolean isSelect
    ) {
        EditTextUtils.insert(editText, content, start, isSelect);
        return this;
    }

    /**
     * 设置长度限制
     * @param view      {@link View}
     * @param maxLength 长度限制
     * @return {@link ViewHelper}
     */
    public ViewHelper setMaxLength(
            final View view,
            final int maxLength
    ) {
        if (view instanceof EditText) {
            EditTextUtils.setMaxLength(EditTextUtils.getEditText(view), maxLength);
        } else {
            TextViewUtils.setMaxLength(view, maxLength);
        }
        return this;
    }

    /**
     * 设置长度限制, 并且设置内容
     * @param view      {@link View}
     * @param content   文本内容
     * @param maxLength 长度限制
     * @return {@link ViewHelper}
     */
    public ViewHelper setMaxLengthAndText(
            final View view,
            final CharSequence content,
            final int maxLength
    ) {
        if (view instanceof EditText) {
            EditTextUtils.setMaxLengthAndText(EditTextUtils.getEditText(view), content, maxLength);
        } else {
            TextViewUtils.setMaxLengthAndText(view, content, maxLength);
        }
        return this;
    }

    /**
     * 设置是否显示光标
     * @param editText {@link EditText}
     * @param visible  是否显示光标
     * @return {@link ViewHelper}
     */
    public ViewHelper setCursorVisible(
            final EditText editText,
            final boolean visible
    ) {
        EditTextUtils.setCursorVisible(editText, visible);
        return this;
    }

    /**
     * 设置光标在第一位
     * @param editText {@link EditText}
     * @return {@link ViewHelper}
     */
    public ViewHelper setSelectionToTop(final EditText editText) {
        EditTextUtils.setSelectionToTop(editText);
        return this;
    }

    /**
     * 设置光标在最后一位
     * @param editText {@link EditText}
     * @return {@link ViewHelper}
     */
    public ViewHelper setSelectionToBottom(final EditText editText) {
        EditTextUtils.setSelectionToBottom(editText);
        return this;
    }

    /**
     * 设置光标位置
     * @param editText {@link EditText}
     * @param index    光标位置
     * @return {@link ViewHelper}
     */
    public ViewHelper setSelection(
            final EditText editText,
            final int index
    ) {
        EditTextUtils.setSelection(editText, index);
        return this;
    }

    // =

    /**
     * 设置文本视图显示转换
     * @param view   {@link View}
     * @param method {@link TransformationMethod}
     * @return {@link ViewHelper}
     */
    public ViewHelper setTransformationMethod(
            final View view,
            final TransformationMethod method
    ) {
        if (view instanceof EditText) {
            EditTextUtils.setTransformationMethod(EditTextUtils.getEditText(view), method);
        } else {
            TextViewUtils.setTransformationMethod(view, method);
        }
        return this;
    }

    /**
     * 设置密码文本视图显示转换
     * @param view              {@link View}
     * @param isDisplayPassword 是否显示密码
     * @return {@link ViewHelper}
     */
    public ViewHelper setTransformationMethod(
            final View view,
            final boolean isDisplayPassword
    ) {
        if (view instanceof EditText) {
            EditTextUtils.setTransformationMethod(EditTextUtils.getEditText(view), isDisplayPassword);
        } else {
            TextViewUtils.setTransformationMethod(view, isDisplayPassword);
        }
        return this;
    }

    /**
     * 设置密码文本视图显示转换
     * @param editText          {@link EditText}
     * @param isDisplayPassword 是否显示密码
     * @param isSelectBottom    是否设置光标到最后
     * @return {@link ViewHelper}
     */
    public ViewHelper setTransformationMethod(
            final EditText editText,
            final boolean isDisplayPassword,
            final boolean isSelectBottom
    ) {
        EditTextUtils.setTransformationMethod(editText, isDisplayPassword, isSelectBottom);
        return this;
    }

    // =

    /**
     * 添加输入监听事件
     * @param editText {@link EditText}
     * @param watcher  输入监听
     * @return {@link ViewHelper}
     */
    public ViewHelper addTextChangedListener(
            final EditText editText,
            final TextWatcher watcher
    ) {
        EditTextUtils.addTextChangedListener(editText, watcher);
        return this;
    }

    /**
     * 移除输入监听事件
     * @param editText {@link EditText}
     * @param watcher  输入监听
     * @return {@link ViewHelper}
     */
    public ViewHelper removeTextChangedListener(
            final EditText editText,
            final TextWatcher watcher
    ) {
        EditTextUtils.removeTextChangedListener(editText, watcher);
        return this;
    }

    /**
     * 设置 KeyListener
     * @param editText    {@link EditText}
     * @param keyListener {@link KeyListener}
     * @return {@link ViewHelper}
     */
    public ViewHelper setKeyListener(
            final EditText editText,
            final KeyListener keyListener
    ) {
        EditTextUtils.setKeyListener(editText, keyListener);
        return this;
    }

    /**
     * 设置 KeyListener
     * @param editText {@link EditText}
     * @param accepted 允许输入的内容, 如: 0123456789
     * @return {@link ViewHelper}
     */
    public ViewHelper setKeyListener(
            final EditText editText,
            final String accepted
    ) {
        EditTextUtils.setKeyListener(editText, accepted);
        return this;
    }

    /**
     * 设置 KeyListener
     * @param editText {@link EditText}
     * @param accepted 允许输入的内容
     * @return {@link ViewHelper}
     */
    public ViewHelper setKeyListener(
            final EditText editText,
            final char[] accepted
    ) {
        EditTextUtils.setKeyListener(editText, accepted);
        return this;
    }

    // =========
    // = Image =
    // =========

    /**
     * 设置 ImageView 是否保持宽高比
     * @param imageView        ImageView
     * @param adjustViewBounds 是否调整此视图的边界以保持可绘制的原始纵横比
     * @return {@link ViewHelper}
     */
    public ViewHelper setAdjustViewBounds(
            final ImageView imageView,
            final boolean adjustViewBounds
    ) {
        ImageViewUtils.setAdjustViewBounds(imageView, adjustViewBounds);
        return this;
    }

    /**
     * 设置 ImageView 最大高度
     * @param imageView ImageView
     * @param maxHeight 最大高度
     * @return {@link ViewHelper}
     */
    public ViewHelper setMaxHeight(
            final ImageView imageView,
            final int maxHeight
    ) {
        ImageViewUtils.setMaxHeight(imageView, maxHeight);
        return this;
    }

    /**
     * 设置 ImageView 最大宽度
     * @param imageView ImageView
     * @param maxWidth  最大宽度
     * @return {@link ViewHelper}
     */
    public ViewHelper setMaxWidth(
            final ImageView imageView,
            final int maxWidth
    ) {
        ImageViewUtils.setMaxWidth(imageView, maxWidth);
        return this;
    }

    // =

    /**
     * 设置背景图片
     * @param view       {@link View}
     * @param background 背景图片
     * @return {@link ViewHelper}
     */
    public ViewHelper setBackground(
            final View view,
            final Drawable background
    ) {
        ViewUtils.setBackground(view, background);
        return this;
    }

    /**
     * 设置背景颜色
     * @param view  {@link View}
     * @param color 背景颜色
     * @return {@link ViewHelper}
     */
    public ViewHelper setBackgroundColor(
            final View view,
            @ColorInt final int color
    ) {
        ViewUtils.setBackgroundColor(view, color);
        return this;
    }

    /**
     * 设置背景资源
     * @param view  {@link View}
     * @param resId resource identifier
     * @return {@link ViewHelper}
     */
    public ViewHelper setBackgroundResource(
            final View view,
            @DrawableRes final int resId
    ) {
        ViewUtils.setBackgroundResource(view, resId);
        return this;
    }

    /**
     * 设置背景着色颜色
     * @param view {@link View}
     * @param tint 着色颜色
     * @return {@link ViewHelper}
     */
    public ViewHelper setBackgroundTintList(
            final View view,
            final ColorStateList tint
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ViewUtils.setBackgroundTintList(view, tint);
        }
        return this;
    }

    /**
     * 设置背景着色模式
     * @param view     {@link View}
     * @param tintMode 着色模式 {@link PorterDuff.Mode}
     * @return {@link ViewHelper}
     */
    public ViewHelper setBackgroundTintMode(
            final View view,
            final PorterDuff.Mode tintMode
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ViewUtils.setBackgroundTintMode(view, tintMode);
        }
        return this;
    }

    /**
     * 设置前景图片
     * @param view       {@link View}
     * @param foreground 前景图片
     * @return {@link ViewHelper}
     */
    public ViewHelper setForeground(
            final View view,
            final Drawable foreground
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ViewUtils.setForeground(view, foreground);
        }
        return this;
    }

    /**
     * 设置前景重心
     * @param view    {@link View}
     * @param gravity 重心
     * @return {@link ViewHelper}
     */
    public ViewHelper setForegroundGravity(
            final View view,
            final int gravity
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ViewUtils.setForegroundGravity(view, gravity);
        }
        return this;
    }

    /**
     * 设置前景着色颜色
     * @param view {@link View}
     * @param tint 着色颜色
     * @return {@link ViewHelper}
     */
    public ViewHelper setForegroundTintList(
            final View view,
            final ColorStateList tint
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ViewUtils.setForegroundTintList(view, tint);
        }
        return this;
    }

    /**
     * 设置前景着色模式
     * @param view     {@link View}
     * @param tintMode 着色模式 {@link PorterDuff.Mode}
     * @return {@link ViewHelper}
     */
    public ViewHelper setForegroundTintMode(
            final View view,
            final PorterDuff.Mode tintMode
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ViewUtils.setForegroundTintMode(view, tintMode);
        }
        return this;
    }

    /**
     * 设置 ImageView Bitmap
     * @param view   {@link View}
     * @param bitmap {@link Bitmap}
     * @return {@link ViewHelper}
     */
    public ViewHelper setImageBitmap(
            final View view,
            final Bitmap bitmap
    ) {
        ImageViewUtils.setImageBitmap(view, bitmap);
        return this;
    }

    /**
     * 设置 ImageView Drawable
     * @param view     {@link View}
     * @param drawable {@link Bitmap}
     * @return {@link ViewHelper}
     */
    public ViewHelper setImageDrawable(
            final View view,
            final Drawable drawable
    ) {
        ImageViewUtils.setImageDrawable(view, drawable);
        return this;
    }

    /**
     * 设置 ImageView 资源
     * @param view  {@link View}
     * @param resId resource identifier
     * @return {@link ViewHelper}
     */
    public ViewHelper setImageResource(
            final View view,
            @DrawableRes final int resId
    ) {
        ImageViewUtils.setImageResource(view, resId);
        return this;
    }

    /**
     * 设置 ImageView Matrix
     * @param view   {@link View}
     * @param matrix {@link Matrix}
     * @return {@link ViewHelper}
     */
    public ViewHelper setImageMatrix(
            final View view,
            final Matrix matrix
    ) {
        ImageViewUtils.setImageMatrix(view, matrix);
        return this;
    }

    /**
     * 设置 ImageView 着色颜色
     * @param view {@link View}
     * @param tint 着色颜色
     * @return {@link ViewHelper}
     */
    public ViewHelper setImageTintList(
            final View view,
            final ColorStateList tint
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ImageViewUtils.setImageTintList(view, tint);
        }
        return this;
    }

    /**
     * 设置 ImageView 着色模式
     * @param view     {@link View}
     * @param tintMode 着色模式 {@link PorterDuff.Mode}
     * @return {@link ViewHelper}
     */
    public ViewHelper setImageTintMode(
            final View view,
            final PorterDuff.Mode tintMode
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ImageViewUtils.setImageTintMode(view, tintMode);
        }
        return this;
    }

    /**
     * 设置 ImageView 缩放类型
     * @param view      {@link View}
     * @param scaleType 缩放类型 {@link ImageView.ScaleType}
     * @return {@link ViewHelper}
     */
    public ViewHelper setScaleType(
            final View view,
            final ImageView.ScaleType scaleType
    ) {
        ImageViewUtils.setScaleType(view, scaleType);
        return this;
    }

    /**
     * ImageView 着色处理
     * @param view  {@link View}
     * @param color 颜色值
     * @return {@link ViewHelper}
     */
    public ViewHelper setColorFilter(
            final View view,
            @ColorInt final int color
    ) {
        ViewUtils.setColorFilter(view, color);
        return this;
    }

    /**
     * ImageView 着色处理, 并且设置 Drawable
     * @param view     {@link View}
     * @param drawable {@link Drawable}
     * @param color    颜色值
     * @return {@link ViewHelper}
     */
    public ViewHelper setColorFilter(
            final View view,
            final Drawable drawable,
            @ColorInt final int color
    ) {
        ViewUtils.setColorFilter(view, drawable, color);
        return this;
    }

    /**
     * ImageView 着色处理
     * @param view        {@link View}
     * @param colorFilter 颜色过滤 ( 效果 )
     * @return {@link ViewHelper}
     */
    public ViewHelper setColorFilter(
            final View view,
            final ColorFilter colorFilter
    ) {
        ViewUtils.setColorFilter(view, colorFilter);
        return this;
    }

    /**
     * ImageView 着色处理, 并且设置 Drawable
     * @param view        {@link View}
     * @param drawable    {@link Drawable}
     * @param colorFilter 颜色过滤 ( 效果 )
     * @return {@link ViewHelper}
     */
    public ViewHelper setColorFilter(
            final View view,
            final Drawable drawable,
            final ColorFilter colorFilter
    ) {
        ViewUtils.setColorFilter(view, drawable, colorFilter);
        return this;
    }

    /**
     * 设置 View 图片资源
     * @param resId resource identifier
     * @param views View[]
     * @return {@link ViewHelper}
     */
    public ViewHelper setBackgroundResources(
            @DrawableRes final int resId,
            final View... views
    ) {
        ImageViewUtils.setBackgroundResources(resId, views);
        return this;
    }

    /**
     * 设置 View 图片资源
     * @param resId        resource identifier
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param views        View[]
     * @return {@link ViewHelper}
     */
    public ViewHelper setBackgroundResources(
            @DrawableRes final int resId,
            final int isVisibility,
            final View... views
    ) {
        ImageViewUtils.setBackgroundResources(resId, isVisibility, views);
        return this;
    }

    /**
     * 设置 View 图片资源
     * @param resId resource identifier
     * @param views View[]
     * @return {@link ViewHelper}
     */
    public ViewHelper setImageResources(
            @DrawableRes final int resId,
            final View... views
    ) {
        ImageViewUtils.setImageResources(resId, views);
        return this;
    }

    /**
     * 设置 View 图片资源
     * @param resId        resource identifier
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param views        View[]
     * @return {@link ViewHelper}
     */
    public ViewHelper setImageResources(
            @DrawableRes final int resId,
            final int isVisibility,
            final View... views
    ) {
        ImageViewUtils.setImageResources(resId, isVisibility, views);
        return this;
    }

    /**
     * 设置 View Bitmap
     * @param bitmap {@link Bitmap}
     * @param views  View[]
     * @return {@link ViewHelper}
     */
    public ViewHelper setImageBitmaps(
            final Bitmap bitmap,
            final View... views
    ) {
        ImageViewUtils.setImageBitmaps(bitmap, views);
        return this;
    }

    /**
     * 设置 View Bitmap
     * @param bitmap       {@link Bitmap}
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param views        View[]
     * @return {@link ViewHelper}
     */
    public ViewHelper setImageBitmaps(
            final Bitmap bitmap,
            final int isVisibility,
            final View... views
    ) {
        ImageViewUtils.setImageBitmaps(bitmap, isVisibility, views);
        return this;
    }

    /**
     * 设置 View Drawable
     * @param drawable {@link drawable}
     * @param views    View[]
     * @return {@link ViewHelper}
     */
    public ViewHelper setImageDrawables(
            final Drawable drawable,
            final View... views
    ) {
        ImageViewUtils.setImageDrawables(drawable, views);
        return this;
    }

    /**
     * 设置 View Drawable
     * @param drawable     {@link drawable}
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param views        View[]
     * @return {@link ViewHelper}
     */
    public ViewHelper setImageDrawables(
            final Drawable drawable,
            final int isVisibility,
            final View... views
    ) {
        ImageViewUtils.setImageDrawables(drawable, isVisibility, views);
        return this;
    }

    /**
     * 设置 View 缩放模式
     * @param scaleType {@link ImageView.ScaleType}
     * @param views     View[]
     * @return {@link ViewHelper}
     */
    public ViewHelper setScaleTypes(
            final ImageView.ScaleType scaleType,
            final View... views
    ) {
        ImageViewUtils.setScaleTypes(scaleType, views);
        return this;
    }

    /**
     * 设置 View 缩放模式
     * @param scaleType    {@link ImageView.ScaleType}
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param views        View[]
     * @return {@link ViewHelper}
     */
    public ViewHelper setScaleTypes(
            final ImageView.ScaleType scaleType,
            final int isVisibility,
            final View... views
    ) {
        ImageViewUtils.setScaleTypes(scaleType, isVisibility, views);
        return this;
    }

    // ========
    // = View =
    // ========

    /**
     * 设置 View 宽度、高度
     * @param view   {@link View}
     * @param width  View 宽度
     * @param height View 高度
     * @return {@link ViewHelper}
     */
    public ViewHelper setWidthHeight(
            final View view,
            final int width,
            final int height
    ) {
        ViewUtils.setWidthHeight(view, width, height);
        return this;
    }

    /**
     * 设置 View 宽度、高度
     * @param view      {@link View}
     * @param width     View 宽度
     * @param height    View 高度
     * @param nullNewLP 如果 LayoutParams 为 null 是否创建新的
     * @return {@link ViewHelper}
     */
    public ViewHelper setWidthHeight(
            final View view,
            final int width,
            final int height,
            final boolean nullNewLP
    ) {
        ViewUtils.setWidthHeight(view, width, height, nullNewLP);
        return this;
    }

    /**
     * 设置 View 宽度
     * @param view  {@link View}
     * @param width View 宽度
     * @return {@link ViewHelper}
     */
    public ViewHelper setWidth(
            final View view,
            final int width
    ) {
        ViewUtils.setWidth(view, width);
        return this;
    }

    /**
     * 设置 View 宽度
     * @param view      {@link View}
     * @param width     View 宽度
     * @param nullNewLP 如果 LayoutParams 为 null 是否创建新的
     * @return {@link ViewHelper}
     */
    public ViewHelper setWidth(
            final View view,
            final int width,
            final boolean nullNewLP
    ) {
        ViewUtils.setWidth(view, width, nullNewLP);
        return this;
    }

    /**
     * 设置 View 高度
     * @param view   {@link View}
     * @param height View 高度
     * @return {@link ViewHelper}
     */
    public ViewHelper setHeight(
            final View view,
            final int height
    ) {
        ViewUtils.setHeight(view, height);
        return this;
    }

    /**
     * 设置 View 高度
     * @param view      {@link View}
     * @param height    View 高度
     * @param nullNewLP 如果 LayoutParams 为 null 是否创建新的
     * @return {@link ViewHelper}
     */
    public ViewHelper setHeight(
            final View view,
            final int height,
            final boolean nullNewLP
    ) {
        ViewUtils.setHeight(view, height, nullNewLP);
        return this;
    }

    /**
     * 设置 View 最小高度
     * @param view      {@link View}
     * @param minHeight 最小高度
     * @return {@link ViewHelper}
     */
    public ViewHelper setMinimumHeight(
            final View view,
            final int minHeight
    ) {
        ViewUtils.setMinimumHeight(view, minHeight);
        return this;
    }

    /**
     * 设置 View 最小宽度
     * @param view     {@link View}
     * @param minWidth 最小宽度
     * @return {@link ViewHelper}
     */
    public ViewHelper setMinimumWidth(
            final View view,
            final int minWidth
    ) {
        ViewUtils.setMinimumWidth(view, minWidth);
        return this;
    }

    /**
     * 设置 View 透明度
     * @param view  {@link View}
     * @param alpha 透明度
     * @return {@link ViewHelper}
     */
    public ViewHelper setAlpha(
            final View view,
            @FloatRange(from = 0.0, to = 1.0) final float alpha
    ) {
        ViewUtils.setAlpha(view, alpha);
        return this;
    }

    /**
     * 设置 View Tag
     * @param view   {@link View}
     * @param object Tag
     * @return {@link ViewHelper}
     */
    public ViewHelper setTag(
            final View view,
            final Object object
    ) {
        ViewUtils.setTag(view, object);
        return this;
    }

    // =

    /**
     * View 内容滚动位置 ( 相对于初始位置移动 )
     * <pre>
     *     无滚动过程
     * </pre>
     * @param view {@link View}
     * @param x    X 轴开始坐标
     * @param y    Y 轴开始坐标
     * @return {@link ViewHelper}
     */
    public ViewHelper scrollTo(
            final View view,
            final int x,
            final int y
    ) {
        ViewUtils.scrollTo(view, x, y);
        return this;
    }

    /**
     * View 内部滚动位置 ( 相对于上次移动的最后位置移动 )
     * <pre>
     *     无滚动过程
     * </pre>
     * @param view {@link View}
     * @param x    X 轴开始坐标
     * @param y    Y 轴开始坐标
     * @return {@link ViewHelper}
     */
    public ViewHelper scrollBy(
            final View view,
            final int x,
            final int y
    ) {
        ViewUtils.scrollBy(view, x, y);
        return this;
    }

    // =

    /**
     * 设置 ViewGroup 和其子控件两者之间的关系
     * <pre>
     *     beforeDescendants : ViewGroup 会优先其子类控件而获取到焦点
     *     afterDescendants : ViewGroup 只有当其子类控件不需要获取焦点时才获取焦点
     *     blocksDescendants : ViewGroup 会覆盖子类控件而直接获得焦点
     *     android:descendantFocusability="blocksDescendants"
     * </pre>
     * @param viewGroup    {@link ViewGroup}
     * @param focusability {@link ViewGroup#FOCUS_BEFORE_DESCENDANTS}、{@link ViewGroup#FOCUS_AFTER_DESCENDANTS}、{@link ViewGroup#FOCUS_BLOCK_DESCENDANTS}
     * @return {@link ViewHelper}
     */
    public ViewHelper setDescendantFocusability(
            final ViewGroup viewGroup,
            final int focusability
    ) {
        ViewUtils.setDescendantFocusability(viewGroup, focusability);
        return this;
    }

    /**
     * 设置 View 滚动模式
     * <pre>
     *     设置滑动到边缘时无效果模式 {@link View#OVER_SCROLL_NEVER}
     *     android:overScrollMode="never"
     * </pre>
     * @param view           {@link View}
     * @param overScrollMode {@link View#OVER_SCROLL_ALWAYS}、{@link View#OVER_SCROLL_IF_CONTENT_SCROLLS}、{@link View#OVER_SCROLL_NEVER}
     * @return {@link ViewHelper}
     */
    public ViewHelper setOverScrollMode(
            final View view,
            final int overScrollMode
    ) {
        ViewUtils.setOverScrollMode(view, overScrollMode);
        return this;
    }

    // =

    /**
     * 设置是否绘制横向滚动条
     * @param view                       {@link View}
     * @param horizontalScrollBarEnabled {@code true} yes, {@code false} no
     * @return {@link ViewHelper}
     */
    public ViewHelper setHorizontalScrollBarEnabled(
            final View view,
            final boolean horizontalScrollBarEnabled
    ) {
        ViewUtils.setHorizontalScrollBarEnabled(view, horizontalScrollBarEnabled);
        return this;
    }

    /**
     * 设置是否绘制垂直滚动条
     * @param view                     {@link View}
     * @param verticalScrollBarEnabled {@code true} yes, {@code false} no
     * @return {@link ViewHelper}
     */
    public ViewHelper setVerticalScrollBarEnabled(
            final View view,
            final boolean verticalScrollBarEnabled
    ) {
        ViewUtils.setVerticalScrollBarEnabled(view, verticalScrollBarEnabled);
        return this;
    }

    // =

    /**
     * 设置 View 滚动效应
     * @param view              {@link View}
     * @param isScrollContainer 是否需要滚动效应
     * @return {@link ViewHelper}
     */
    public ViewHelper setScrollContainer(
            final View view,
            final boolean isScrollContainer
    ) {
        ViewUtils.setScrollContainer(view, isScrollContainer);
        return this;
    }

    /**
     * 设置下一个获取焦点的 View id
     * @param view               {@link View}
     * @param nextFocusForwardId 下一个获取焦点的 View id
     * @return {@link ViewHelper}
     */
    public ViewHelper setNextFocusForwardId(
            final View view,
            @IdRes final int nextFocusForwardId
    ) {
        ViewUtils.setNextFocusForwardId(view, nextFocusForwardId);
        return this;
    }

    /**
     * 设置向下移动焦点时, 下一个获取焦点的 View id
     * @param view            {@link View}
     * @param nextFocusDownId 下一个获取焦点的 View id
     * @return {@link ViewHelper}
     */
    public ViewHelper setNextFocusDownId(
            final View view,
            @IdRes final int nextFocusDownId
    ) {
        ViewUtils.setNextFocusDownId(view, nextFocusDownId);
        return this;
    }

    /**
     * 设置向左移动焦点时, 下一个获取焦点的 View id
     * @param view            {@link View}
     * @param nextFocusLeftId 下一个获取焦点的 View id
     * @return {@link ViewHelper}
     */
    public ViewHelper setNextFocusLeftId(
            final View view,
            @IdRes final int nextFocusLeftId
    ) {
        ViewUtils.setNextFocusLeftId(view, nextFocusLeftId);
        return this;
    }

    /**
     * 设置向右移动焦点时, 下一个获取焦点的 View id
     * @param view             {@link View}
     * @param nextFocusRightId 下一个获取焦点的 View id
     * @return {@link ViewHelper}
     */
    public ViewHelper setNextFocusRightId(
            final View view,
            @IdRes final int nextFocusRightId
    ) {
        ViewUtils.setNextFocusRightId(view, nextFocusRightId);
        return this;
    }

    /**
     * 设置向上移动焦点时, 下一个获取焦点的 View id
     * @param view          {@link View}
     * @param nextFocusUpId 下一个获取焦点的 View id
     * @return {@link ViewHelper}
     */
    public ViewHelper setNextFocusUpId(
            final View view,
            @IdRes final int nextFocusUpId
    ) {
        ViewUtils.setNextFocusUpId(view, nextFocusUpId);
        return this;
    }

    /**
     * 设置 View 旋转度数
     * @param view     {@link View}
     * @param rotation 旋转度数
     * @return {@link ViewHelper}
     */
    public ViewHelper setRotation(
            final View view,
            final float rotation
    ) {
        ViewUtils.setRotation(view, rotation);
        return this;
    }

    /**
     * 设置 View 水平旋转度数
     * @param view      {@link View}
     * @param rotationX 水平旋转度数
     * @return {@link ViewHelper}
     */
    public ViewHelper setRotationX(
            final View view,
            final float rotationX
    ) {
        ViewUtils.setRotationX(view, rotationX);
        return this;
    }

    /**
     * 设置 View 竖直旋转度数
     * @param view      {@link View}
     * @param rotationY 竖直旋转度数
     * @return {@link ViewHelper}
     */
    public ViewHelper setRotationY(
            final View view,
            final float rotationY
    ) {
        ViewUtils.setRotationY(view, rotationY);
        return this;
    }

    /**
     * 设置 View 水平方向缩放比例
     * @param view   {@link View}
     * @param scaleX 水平方向缩放比例
     * @return {@link ViewHelper}
     */
    public ViewHelper setScaleX(
            final View view,
            final float scaleX
    ) {
        ViewUtils.setScaleX(view, scaleX);
        return this;
    }

    /**
     * 设置 View 竖直方向缩放比例
     * @param view   {@link View}
     * @param scaleY 竖直方向缩放比例
     * @return {@link ViewHelper}
     */
    public ViewHelper setScaleY(
            final View view,
            final float scaleY
    ) {
        ViewUtils.setScaleY(view, scaleY);
        return this;
    }

    /**
     * 设置文本的显示方式
     * @param view          {@link View}
     * @param textAlignment 文本的显示方式
     * @return {@link ViewHelper}
     */
    public ViewHelper setTextAlignment(
            final View view,
            final int textAlignment
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            ViewUtils.setTextAlignment(view, textAlignment);
        }
        return this;
    }

    /**
     * 设置文本的显示方向
     * @param view          {@link View}
     * @param textDirection 文本的显示方向
     * @return {@link ViewHelper}
     */
    public ViewHelper setTextDirection(
            final View view,
            final int textDirection
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            ViewUtils.setTextDirection(view, textDirection);
        }
        return this;
    }

    /**
     * 设置水平方向偏转量
     * @param view   {@link View}
     * @param pivotX 水平方向偏转量
     * @return {@link ViewHelper}
     */
    public ViewHelper setPivotX(
            final View view,
            final float pivotX
    ) {
        ViewUtils.setPivotX(view, pivotX);
        return this;
    }

    /**
     * 设置竖直方向偏转量
     * @param view   {@link View}
     * @param pivotY 竖直方向偏转量
     * @return {@link ViewHelper}
     */
    public ViewHelper setPivotY(
            final View view,
            final float pivotY
    ) {
        ViewUtils.setPivotY(view, pivotY);
        return this;
    }

    /**
     * 设置水平方向的移动距离
     * @param view         {@link View}
     * @param translationX 水平方向的移动距离
     * @return {@link ViewHelper}
     */
    public ViewHelper setTranslationX(
            final View view,
            final float translationX
    ) {
        ViewUtils.setTranslationX(view, translationX);
        return this;
    }

    /**
     * 设置竖直方向的移动距离
     * @param view         {@link View}
     * @param translationY 竖直方向的移动距离
     * @return {@link ViewHelper}
     */
    public ViewHelper setTranslationY(
            final View view,
            final float translationY
    ) {
        ViewUtils.setTranslationY(view, translationY);
        return this;
    }

    /**
     * 设置 View 硬件加速类型
     * @param view      {@link View}
     * @param layerType 硬件加速类型
     * @param paint     {@link Paint}
     * @return {@link ViewHelper}
     */
    public ViewHelper setLayerType(
            final View view,
            final int layerType,
            final Paint paint
    ) {
        ViewUtils.setLayerType(view, layerType, paint);
        return this;
    }

    // =

    /**
     * 请求重新对 View 布局
     * @param view {@link View}
     * @return {@link ViewHelper}
     */
    public ViewHelper requestLayout(final View view) {
        ViewUtils.requestLayout(view);
        return this;
    }

    /**
     * View 请求获取焦点
     * @param view {@link View}
     * @return {@link ViewHelper}
     */
    public ViewHelper requestFocus(final View view) {
        ViewUtils.requestFocus(view);
        return this;
    }

    /**
     * View 清除焦点
     * @param view {@link View}
     * @return {@link ViewHelper}
     */
    public ViewHelper clearFocus(final View view) {
        ViewUtils.clearFocus(view);
        return this;
    }

    /**
     * View 请求更新
     * @param view      {@link View}
     * @param allParent 是否全部父布局 View 都请求
     * @return {@link ViewHelper}
     */
    public ViewHelper requestLayoutParent(
            final View view,
            final boolean allParent
    ) {
        ViewUtils.requestLayoutParent(view, allParent);
        return this;
    }

    // =

    /**
     * 设置 View Id
     * @param view {@link View}
     * @param id   View Id
     * @return {@link ViewHelper}
     */
    public ViewHelper setId(
            final View view,
            final int id
    ) {
        ViewUtils.setId(view, id);
        return this;
    }

    /**
     * 设置是否限制子 View 在其边界内绘制
     * @param viewGroup    {@link ViewGroup}
     * @param clipChildren {@code true} yes, {@code false} no
     * @return {@link ViewHelper}
     */
    public ViewHelper setClipChildren(
            final ViewGroup viewGroup,
            final boolean clipChildren
    ) {
        ViewUtils.setClipChildren(viewGroup, clipChildren);
        return this;
    }

    /**
     * 设置 View LayoutParams
     * @param view   {@link View}
     * @param params LayoutParams
     * @return {@link ViewHelper}
     */
    public ViewHelper setLayoutParams(
            final View view,
            final ViewGroup.LayoutParams params
    ) {
        ViewUtils.setLayoutParams(view, params);
        return this;
    }

    /**
     * 设置 View 是否在触摸模式下获得焦点
     * @param focusableInTouchMode {@code true} 可获取, {@code false} 不可获取
     * @param views                View[]
     * @return {@link ViewHelper}
     */
    public ViewHelper setFocusableInTouchMode(
            final boolean focusableInTouchMode,
            final View... views
    ) {
        ViewUtils.setFocusableInTouchMode(focusableInTouchMode, views);
        return this;
    }

    /**
     * 设置 View 是否可以获取焦点
     * @param focusable {@code true} 可获取, {@code false} 不可获取
     * @param views     View[]
     * @return {@link ViewHelper}
     */
    public ViewHelper setFocusable(
            final boolean focusable,
            final View... views
    ) {
        ViewUtils.setFocusable(focusable, views);
        return this;
    }

    /**
     * 切换获取焦点状态
     * @param views View[]
     * @return {@link ViewHelper}
     */
    public ViewHelper toggleFocusable(final View... views) {
        ViewUtils.toggleFocusable(views);
        return this;
    }

    /**
     * 设置 View 是否选中
     * @param selected {@code true} 选中, {@code false} 非选中
     * @param views    View[]
     * @return {@code true} 选中, {@code false} 非选中
     */
    public ViewHelper setSelected(
            final boolean selected,
            final View... views
    ) {
        ViewUtils.setSelected(selected, views);
        return this;
    }

    /**
     * 切换选中状态
     * @param views View[]
     * @return {@link ViewHelper}
     */
    public ViewHelper toggleSelected(final View... views) {
        ViewUtils.toggleSelected(views);
        return this;
    }

    /**
     * 设置 View 是否启用
     * @param enabled {@code true} 启用, {@code false} 禁用
     * @param views   View[]
     * @return {@link ViewHelper}
     */
    public ViewHelper setEnabled(
            final boolean enabled,
            final View... views
    ) {
        ViewUtils.setEnabled(enabled, views);
        return this;
    }

    /**
     * 切换 View 是否启用状态
     * @param views View[]
     * @return {@link ViewHelper}
     */
    public ViewHelper toggleEnabled(final View... views) {
        ViewUtils.toggleEnabled(views);
        return this;
    }

    /**
     * 设置 View 是否可以点击
     * @param clickable {@code true} 可点击, {@code false} 不可点击
     * @param views     View[]
     * @return {@link ViewHelper}
     */
    public ViewHelper setClickable(
            final boolean clickable,
            final View... views
    ) {
        ViewUtils.setClickable(clickable, views);
        return this;
    }

    /**
     * 切换 View 是否可以点击状态
     * @param views View[]
     * @return {@link ViewHelper}
     */
    public ViewHelper toggleClickable(final View... views) {
        ViewUtils.toggleClickable(views);
        return this;
    }

    /**
     * 设置 View 是否可以长按
     * @param longClickable {@code true} 可长按, {@code false} 不可长按
     * @param views         View[]
     * @return {@link ViewHelper}
     */
    public ViewHelper setLongClickable(
            final boolean longClickable,
            final View... views
    ) {
        ViewUtils.setLongClickable(longClickable, views);
        return this;
    }

    /**
     * 切换 View 是否可以长按状态
     * @param views View[]
     * @return {@link ViewHelper}
     */
    public ViewHelper toggleLongClickable(final View... views) {
        ViewUtils.toggleLongClickable(views);
        return this;
    }

    /**
     * 设置 View 显示的状态
     * @param isVisibility {@code true} View.VISIBLE, {@code false} View.GONE
     * @param view         {@link View}
     * @return {@link ViewHelper}
     */
    public ViewHelper setVisibility(
            final boolean isVisibility,
            final View view
    ) {
        ViewUtils.setVisibility(isVisibility, view);
        return this;
    }

    /**
     * 设置 View 显示的状态
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param view         {@link View}
     * @return {@link ViewHelper}
     */
    public ViewHelper setVisibility(
            final int isVisibility,
            final View view
    ) {
        ViewUtils.setVisibility(isVisibility, view);
        return this;
    }

    /**
     * 设置 View 显示的状态
     * @param isVisibility {@code true} View.VISIBLE, {@code false} View.GONE
     * @param views        View[]
     * @return {@link ViewHelper}
     */
    public ViewHelper setVisibilitys(
            final boolean isVisibility,
            final View... views
    ) {
        ViewUtils.setVisibilitys(isVisibility, views);
        return this;
    }

    /**
     * 设置 View 显示的状态
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param views        View[]
     * @return {@link ViewHelper}
     */
    public ViewHelper setVisibilitys(
            final int isVisibility,
            final View... views
    ) {
        ViewUtils.setVisibilitys(isVisibility, views);
        return this;
    }

    /**
     * 切换 View 显示的状态
     * @param view  {@link View}
     * @param views View[]
     * @return {@link ViewHelper}
     */
    public ViewHelper toggleVisibilitys(
            final View view,
            final View... views
    ) {
        ViewUtils.toggleVisibilitys(view, views);
        return this;
    }

    /**
     * 切换 View 显示的状态
     * @param viewArys View[]
     * @param views    View[]
     * @return {@link ViewHelper}
     */
    public ViewHelper toggleVisibilitys(
            final View[] viewArys,
            final View... views
    ) {
        ViewUtils.toggleVisibilitys(viewArys, views);
        return this;
    }

    /**
     * 切换 View 显示的状态
     * @param state    {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param viewArys View[]
     * @param views    View[]
     * @return {@link ViewHelper}
     */
    public ViewHelper toggleVisibilitys(
            final int state,
            final View[] viewArys,
            final View... views
    ) {
        ViewUtils.toggleVisibilitys(state, viewArys, views);
        return this;
    }

    /**
     * 反转 View 显示的状态
     * @param state    {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param viewArys View[]
     * @param views    View[]
     * @return {@link ViewHelper}
     */
    public ViewHelper reverseVisibilitys(
            final int state,
            final View[] viewArys,
            final View... views
    ) {
        ViewUtils.reverseVisibilitys(state, viewArys, views);
        return this;
    }

    /**
     * 反转 View 显示的状态
     * @param isVisibility {@code true} View.VISIBLE, {@code false} View.GONE
     * @param viewArys     View[]
     * @param views        View[]
     * @return {@link ViewHelper}
     */
    public ViewHelper reverseVisibilitys(
            final boolean isVisibility,
            final View[] viewArys,
            final View... views
    ) {
        ViewUtils.reverseVisibilitys(isVisibility, viewArys, views);
        return this;
    }

    /**
     * 反转 View 显示的状态
     * @param state {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param view  {@link View}
     * @param views View[]
     * @return {@link ViewHelper}
     */
    public ViewHelper reverseVisibilitys(
            final int state,
            final View view,
            final View... views
    ) {
        ViewUtils.reverseVisibilitys(state, view, views);
        return this;
    }

    /**
     * 反转 View 显示的状态
     * @param isVisibility {@code true} View.VISIBLE, {@code false} View.GONE
     * @param view         {@link View}
     * @param views        View[]
     * @return {@link ViewHelper}
     */
    public ViewHelper reverseVisibilitys(
            final boolean isVisibility,
            final View view,
            final View... views
    ) {
        ViewUtils.reverseVisibilitys(isVisibility, view, views);
        return this;
    }

    /**
     * 把自身从父 View 中移除
     * @param view {@link View}
     * @return {@link ViewHelper}
     */
    public ViewHelper removeSelfFromParent(final View view) {
        ViewUtils.removeSelfFromParent(view);
        return this;
    }

    /**
     * 设置 View Layout Gravity
     * @param view    {@link View}
     * @param gravity Gravity
     * @return {@link ViewHelper}
     */
    public ViewHelper setLayoutGravity(
            final View view,
            final int gravity
    ) {
        ViewUtils.setLayoutGravity(view, gravity);
        return this;
    }

    /**
     * 设置 View Layout Gravity
     * @param view         {@link View}
     * @param gravity      Gravity
     * @param isReflection 是否使用反射
     * @return {@link QuickHelper}
     */
    public ViewHelper setLayoutGravity(
            final View view,
            final int gravity,
            final boolean isReflection
    ) {
        ViewUtils.setLayoutGravity(view, gravity, isReflection);
        return this;
    }

    /**
     * 设置 View Left Margin
     * @param view       {@link View}
     * @param leftMargin Left Margin
     * @return {@link ViewHelper}
     */
    public ViewHelper setMarginLeft(
            final View view,
            final int leftMargin
    ) {
        ViewUtils.setMarginLeft(view, leftMargin);
        return this;
    }

    /**
     * 设置 View Left Margin
     * @param view       {@link View}
     * @param leftMargin Left Margin
     * @param reset      是否重置清空其他 margin
     * @return {@link ViewHelper}
     */
    public ViewHelper setMarginLeft(
            final View view,
            final int leftMargin,
            final boolean reset
    ) {
        ViewUtils.setMarginLeft(view, leftMargin, reset);
        return this;
    }

    /**
     * 设置 View Top Margin
     * @param view      {@link View}
     * @param topMargin Top Margin
     * @return {@link ViewHelper}
     */
    public ViewHelper setMarginTop(
            final View view,
            final int topMargin
    ) {
        ViewUtils.setMarginTop(view, topMargin);
        return this;
    }

    /**
     * 设置 View Top Margin
     * @param view      {@link View}
     * @param topMargin Top Margin
     * @param reset     是否重置清空其他 margin
     * @return {@link ViewHelper}
     */
    public ViewHelper setMarginTop(
            final View view,
            final int topMargin,
            final boolean reset
    ) {
        ViewUtils.setMarginTop(view, topMargin, reset);
        return this;
    }

    /**
     * 设置 View Right Margin
     * @param view        {@link View}
     * @param rightMargin Right Margin
     * @return {@link ViewHelper}
     */
    public ViewHelper setMarginRight(
            final View view,
            final int rightMargin
    ) {
        ViewUtils.setMarginRight(view, rightMargin);
        return this;
    }

    /**
     * 设置 View Right Margin
     * @param view        {@link View}
     * @param rightMargin Right Margin
     * @param reset       是否重置清空其他 margin
     * @return {@link ViewHelper}
     */
    public ViewHelper setMarginRight(
            final View view,
            final int rightMargin,
            final boolean reset
    ) {
        ViewUtils.setMarginRight(view, rightMargin, reset);
        return this;
    }

    /**
     * 设置 View Bottom Margin
     * @param view         {@link View}
     * @param bottomMargin Bottom Margin
     * @return {@link ViewHelper}
     */
    public ViewHelper setMarginBottom(
            final View view,
            final int bottomMargin
    ) {
        ViewUtils.setMarginBottom(view, bottomMargin);
        return this;
    }

    /**
     * 设置 View Bottom Margin
     * @param view         {@link View}
     * @param bottomMargin Bottom Margin
     * @param reset        是否重置清空其他 margin
     * @return {@link ViewHelper}
     */
    public ViewHelper setMarginBottom(
            final View view,
            final int bottomMargin,
            final boolean reset
    ) {
        ViewUtils.setMarginBottom(view, bottomMargin, reset);
        return this;
    }

    /**
     * 设置 Margin 边距
     * @param view      {@link View}
     * @param leftRight Left and Right Margin
     * @param topBottom Top and bottom Margin
     * @return {@link ViewHelper}
     */
    public ViewHelper setMargin(
            final View view,
            final int leftRight,
            final int topBottom
    ) {
        ViewUtils.setMargin(view, leftRight, topBottom);
        return this;
    }

    /**
     * 设置 Margin 边距
     * @param view   {@link View}
     * @param margin Margin
     * @return {@link ViewHelper}
     */
    public ViewHelper setMargin(
            final View view,
            final int margin
    ) {
        ViewUtils.setMargin(view, margin);
        return this;
    }

    /**
     * 设置 Margin 边距
     * @param view   {@link View}
     * @param left   Left Margin
     * @param top    Top Margin
     * @param right  Right Margin
     * @param bottom Bottom Margin
     * @return {@link ViewHelper}
     */
    public ViewHelper setMargin(
            final View view,
            final int left,
            final int top,
            final int right,
            final int bottom
    ) {
        ViewUtils.setMargin(view, left, top, right, bottom);
        return this;
    }

    /**
     * 设置 Margin 边距
     * @param views     View[]
     * @param leftRight Left and Right Margin
     * @param topBottom Top and bottom Margin
     * @return {@link ViewHelper}
     */
    public ViewHelper setMargin(
            final View[] views,
            final int leftRight,
            final int topBottom
    ) {
        ViewUtils.setMargin(views, leftRight, topBottom);
        return this;
    }

    /**
     * 设置 Margin 边距
     * @param views  View[]
     * @param margin Margin
     * @return {@link ViewHelper}
     */
    public ViewHelper setMargin(
            final View[] views,
            final int margin
    ) {
        ViewUtils.setMargin(views, margin);
        return this;
    }

    /**
     * 设置 Margin 边距
     * @param views  View[]
     * @param left   Left Margin
     * @param top    Top Margin
     * @param right  Right Margin
     * @param bottom Bottom Margin
     * @return {@link ViewHelper}
     */
    public ViewHelper setMargin(
            final View[] views,
            final int left,
            final int top,
            final int right,
            final int bottom
    ) {
        ViewUtils.setMargin(views, left, top, right, bottom);
        return this;
    }

    /**
     * 设置 View Left Padding
     * @param view        {@link View}
     * @param leftPadding Left Padding
     * @return {@link ViewHelper}
     */
    public ViewHelper setPaddingLeft(
            final View view,
            final int leftPadding
    ) {
        ViewUtils.setPaddingLeft(view, leftPadding);
        return this;
    }

    /**
     * 设置 View Left Padding
     * @param view        {@link View}
     * @param leftPadding Left Padding
     * @param reset       是否重置清空其他 Padding
     * @return {@link ViewHelper}
     */
    public ViewHelper setPaddingLeft(
            final View view,
            final int leftPadding,
            final boolean reset
    ) {
        ViewUtils.setPaddingLeft(view, leftPadding, reset);
        return this;
    }

    /**
     * 设置 View Top Padding
     * @param view       {@link View}
     * @param topPadding Top Padding
     * @return {@link ViewHelper}
     */
    public ViewHelper setPaddingTop(
            final View view,
            final int topPadding
    ) {
        ViewUtils.setPaddingTop(view, topPadding);
        return this;
    }

    /**
     * 设置 View Top Padding
     * @param view       {@link View}
     * @param topPadding Top Padding
     * @param reset      是否重置清空其他 Padding
     * @return {@link ViewHelper}
     */
    public ViewHelper setPaddingTop(
            final View view,
            final int topPadding,
            final boolean reset
    ) {
        ViewUtils.setPaddingTop(view, topPadding, reset);
        return this;
    }

    /**
     * 设置 View Right Padding
     * @param view         {@link View}
     * @param rightPadding Right Padding
     * @return {@link ViewHelper}
     */
    public ViewHelper setPaddingRight(
            final View view,
            final int rightPadding
    ) {
        ViewUtils.setPaddingRight(view, rightPadding);
        return this;
    }

    /**
     * 设置 View Right Padding
     * @param view         {@link View}
     * @param rightPadding Right Padding
     * @param reset        是否重置清空其他 Padding
     * @return {@link ViewHelper}
     */
    public ViewHelper setPaddingRight(
            final View view,
            final int rightPadding,
            final boolean reset
    ) {
        ViewUtils.setPaddingRight(view, rightPadding, reset);
        return this;
    }

    /**
     * 设置 View Bottom Padding
     * @param view          {@link View}
     * @param bottomPadding Bottom Padding
     * @return {@link ViewHelper}
     */
    public ViewHelper setPaddingBottom(
            final View view,
            final int bottomPadding
    ) {
        ViewUtils.setPaddingBottom(view, bottomPadding);
        return this;
    }

    /**
     * 设置 View Bottom Padding
     * @param view          {@link View}
     * @param bottomPadding Bottom Padding
     * @param reset         是否重置清空其他 Padding
     * @return {@link ViewHelper}
     */
    public ViewHelper setPaddingBottom(
            final View view,
            final int bottomPadding,
            final boolean reset
    ) {
        ViewUtils.setPaddingBottom(view, bottomPadding, reset);
        return this;
    }

    /**
     * 设置 Padding 边距
     * @param view      {@link View}
     * @param leftRight Left and Right Padding
     * @param topBottom Top and bottom Padding
     * @return {@link ViewHelper}
     */
    public ViewHelper setPadding(
            final View view,
            final int leftRight,
            final int topBottom
    ) {
        ViewUtils.setPadding(view, leftRight, topBottom);
        return this;
    }

    /**
     * 设置 Padding 边距
     * @param view    {@link View}
     * @param padding Padding
     * @return {@link ViewHelper}
     */
    public ViewHelper setPadding(
            final View view,
            final int padding
    ) {
        ViewUtils.setPadding(view, padding);
        return this;
    }

    /**
     * 设置 Padding 边距
     * @param view   {@link View}
     * @param left   Left Padding
     * @param top    Top Padding
     * @param right  Right Padding
     * @param bottom Bottom Padding
     * @return {@link ViewHelper}
     */
    public ViewHelper setPadding(
            final View view,
            final int left,
            final int top,
            final int right,
            final int bottom
    ) {
        ViewUtils.setPadding(view, left, top, right, bottom);
        return this;
    }

    /**
     * 设置 Padding 边距
     * @param views     View[]
     * @param leftRight Left and Right Padding
     * @param topBottom Top and bottom Padding
     * @return {@link ViewHelper}
     */
    public ViewHelper setPadding(
            final View[] views,
            final int leftRight,
            final int topBottom
    ) {
        ViewUtils.setPadding(views, leftRight, topBottom);
        return this;
    }

    /**
     * 设置 Padding 边距
     * @param views   View[]
     * @param padding Padding
     * @return {@link ViewHelper}
     */
    public ViewHelper setPadding(
            final View[] views,
            final int padding
    ) {
        ViewUtils.setPadding(views, padding);
        return this;
    }

    /**
     * 设置 Padding 边距
     * @param views  View[]
     * @param left   Left Padding
     * @param top    Top Padding
     * @param right  Right Padding
     * @param bottom Bottom Padding
     * @return {@link ViewHelper}
     */
    public ViewHelper setPadding(
            final View[] views,
            final int left,
            final int top,
            final int right,
            final int bottom
    ) {
        ViewUtils.setPadding(views, left, top, right, bottom);
        return this;
    }

    /**
     * 设置 CompoundDrawables Padding
     * @param textView {@link TextView}
     * @param padding  CompoundDrawables Padding
     * @return {@link ViewHelper}
     */
    public ViewHelper setCompoundDrawablePadding(
            final TextView textView,
            final int padding
    ) {
        TextViewUtils.setCompoundDrawablePadding(textView, padding);
        return this;
    }

    /**
     * 设置 Left CompoundDrawables
     * @param textView {@link TextView}
     * @param left     left Drawable
     * @return {@link ViewHelper}
     */
    public ViewHelper setCompoundDrawablesByLeft(
            final TextView textView,
            final Drawable left
    ) {
        TextViewUtils.setCompoundDrawablesByLeft(textView, left);
        return this;
    }

    /**
     * 设置 Top CompoundDrawables
     * @param textView {@link TextView}
     * @param top      top Drawable
     * @return {@link ViewHelper}
     */
    public ViewHelper setCompoundDrawablesByTop(
            final TextView textView,
            final Drawable top
    ) {
        TextViewUtils.setCompoundDrawablesByTop(textView, top);
        return this;
    }

    /**
     * 设置 Right CompoundDrawables
     * @param textView {@link TextView}
     * @param right    right Drawable
     * @return {@link ViewHelper}
     */
    public ViewHelper setCompoundDrawablesByRight(
            final TextView textView,
            final Drawable right
    ) {
        TextViewUtils.setCompoundDrawablesByRight(textView, right);
        return this;
    }

    /**
     * 设置 Bottom CompoundDrawables
     * @param textView {@link TextView}
     * @param bottom   bottom Drawable
     * @return {@link ViewHelper}
     */
    public ViewHelper setCompoundDrawablesByBottom(
            final TextView textView,
            final Drawable bottom
    ) {
        TextViewUtils.setCompoundDrawablesByBottom(textView, bottom);
        return this;
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
     * @return {@link ViewHelper}
     */
    public ViewHelper setCompoundDrawables(
            final TextView textView,
            final Drawable left,
            final Drawable top,
            final Drawable right,
            final Drawable bottom
    ) {
        TextViewUtils.setCompoundDrawables(textView, left, top, right, bottom);
        return this;
    }

    /**
     * 设置 Left CompoundDrawables ( 按照原有比例大小显示图片 )
     * @param textView {@link TextView}
     * @param left     left Drawable
     * @return {@link ViewHelper}
     */
    public ViewHelper setCompoundDrawablesWithIntrinsicBoundsByLeft(
            final TextView textView,
            final Drawable left
    ) {
        TextViewUtils.setCompoundDrawablesWithIntrinsicBoundsByLeft(textView, left);
        return this;
    }

    /**
     * 设置 Top CompoundDrawables ( 按照原有比例大小显示图片 )
     * @param textView {@link TextView}
     * @param top      top Drawable
     * @return {@link ViewHelper}
     */
    public ViewHelper setCompoundDrawablesWithIntrinsicBoundsByTop(
            final TextView textView,
            final Drawable top
    ) {
        TextViewUtils.setCompoundDrawablesWithIntrinsicBoundsByTop(textView, top);
        return this;
    }

    /**
     * 设置 Right CompoundDrawables ( 按照原有比例大小显示图片 )
     * @param textView {@link TextView}
     * @param right    right Drawable
     * @return {@link ViewHelper}
     */
    public ViewHelper setCompoundDrawablesWithIntrinsicBoundsByRight(
            final TextView textView,
            final Drawable right
    ) {
        TextViewUtils.setCompoundDrawablesWithIntrinsicBoundsByRight(textView, right);
        return this;
    }

    /**
     * 设置 Bottom CompoundDrawables ( 按照原有比例大小显示图片 )
     * @param textView {@link TextView}
     * @param bottom   bottom Drawable
     * @return {@link ViewHelper}
     */
    public ViewHelper setCompoundDrawablesWithIntrinsicBoundsByBottom(
            final TextView textView,
            final Drawable bottom
    ) {
        TextViewUtils.setCompoundDrawablesWithIntrinsicBoundsByBottom(textView, bottom);
        return this;
    }

    /**
     * 设置 CompoundDrawables ( 按照原有比例大小显示图片 )
     * @param textView {@link TextView}
     * @param left     left Drawable
     * @param top      top Drawable
     * @param right    right Drawable
     * @param bottom   bottom Drawable
     * @return {@link ViewHelper}
     */
    public ViewHelper setCompoundDrawablesWithIntrinsicBounds(
            final TextView textView,
            final Drawable left,
            final Drawable top,
            final Drawable right,
            final Drawable bottom
    ) {
        TextViewUtils.setCompoundDrawablesWithIntrinsicBounds(textView, left, top, right, bottom);
        return this;
    }

    // ==================
    // = RelativeLayout =
    // ==================

    /**
     * 设置 RelativeLayout View 布局规则
     * @param view {@link View}
     * @param verb 布局位置
     * @return {@link ViewHelper}
     */
    public ViewHelper addRule(
            final View view,
            final int verb
    ) {
        ViewUtils.addRule(view, verb);
        return this;
    }

    /**
     * 设置 RelativeLayout View 布局规则
     * @param view    {@link View}
     * @param verb    布局位置
     * @param subject 关联 View id
     * @return {@link ViewHelper}
     */
    public ViewHelper addRule(
            final View view,
            final int verb,
            final int subject
    ) {
        ViewUtils.addRule(view, verb, subject);
        return this;
    }

    /**
     * 移除 RelativeLayout View 布局规则
     * @param view {@link View}
     * @param verb 布局位置
     * @return {@link ViewHelper}
     */
    public ViewHelper removeRule(
            final View view,
            final int verb
    ) {
        ViewUtils.removeRule(view, verb);
        return this;
    }

    // =

    /**
     * 设置多个 RelativeLayout View 布局规则
     * @param verb  布局位置
     * @param views View[]
     * @return {@link ViewHelper}
     */
    public ViewHelper addRules(
            final int verb,
            final View... views
    ) {
        ViewUtils.addRules(verb, views);
        return this;
    }

    /**
     * 设置多个 RelativeLayout View 布局规则
     * @param verb    布局位置
     * @param subject 关联 View id
     * @param views   View[]
     * @return {@link ViewHelper}
     */
    public ViewHelper addRules(
            final int verb,
            final int subject,
            final View... views
    ) {
        ViewUtils.addRules(verb, subject, views);
        return this;
    }

    /**
     * 移除多个 RelativeLayout View 布局规则
     * @param verb  布局位置
     * @param views View[]
     * @return {@link ViewHelper}
     */
    public ViewHelper removeRules(
            final int verb,
            final View... views
    ) {
        ViewUtils.removeRules(verb, views);
        return this;
    }

    // =============
    // = Animation =
    // =============

    /**
     * 设置动画
     * @param view      {@link View}
     * @param animation {@link Animation}
     * @return {@link ViewHelper}
     */
    public ViewHelper setAnimation(
            final View view,
            final Animation animation
    ) {
        ViewUtils.setAnimation(view, animation);
        return this;
    }

    /**
     * 清空动画
     * @param view {@link View}
     * @return {@link ViewHelper}
     */
    public ViewHelper clearAnimation(final View view) {
        ViewUtils.clearAnimation(view);
        return this;
    }

    /**
     * 启动动画
     * @param view      {@link View}
     * @param animation {@link Animation}
     * @return {@link ViewHelper}
     */
    public ViewHelper startAnimation(
            final View view,
            final Animation animation
    ) {
        ViewUtils.startAnimation(view, animation);
        return this;
    }

    /**
     * 取消动画
     * @param view {@link View}
     * @return {@link ViewHelper}
     */
    public ViewHelper cancelAnimation(final View view) {
        ViewUtils.cancelAnimation(view);
        return this;
    }

    // ============
    // = Listener =
    // ============

    /**
     * 设置点击事件
     * @param onClickListener {@link View.OnClickListener}
     * @param views           View 数组
     * @return {@link ViewHelper}
     */
    public ViewHelper setOnClicks(
            final View.OnClickListener onClickListener,
            final View... views
    ) {
        ListenerUtils.setOnClicks(onClickListener, views);
        return this;
    }

    /**
     * 设置长按事件
     * @param onLongClickListener {@link View.OnLongClickListener}
     * @param views               View 数组
     * @return {@link ViewHelper}
     */
    public ViewHelper setOnLongClicks(
            final View.OnLongClickListener onLongClickListener,
            final View... views
    ) {
        ListenerUtils.setOnLongClicks(onLongClickListener, views);
        return this;
    }

    /**
     * 设置触摸事件
     * @param onTouchListener {@link View.OnTouchListener}
     * @param views           View 数组
     * @return {@link ViewHelper}
     */
    public ViewHelper setOnTouchs(
            final View.OnTouchListener onTouchListener,
            final View... views
    ) {
        ListenerUtils.setOnTouchs(onTouchListener, views);
        return this;
    }

    /**
     * 增加控件的触摸范围, 最大范围只能是父布局所包含的的区域
     * @param view  待添加点击范围 View
     * @param range 点击范围
     * @return {@link ViewHelper}
     */
    public ViewHelper addTouchArea(
            final View view,
            final int range
    ) {
        ClickUtils.addTouchArea(view, range);
        return this;
    }

    /**
     * 增加控件的触摸范围, 最大范围只能是父布局所包含的的区域
     * @param view   待添加点击范围 View
     * @param top    top range
     * @param bottom bottom range
     * @param left   left range
     * @param right  right range
     * @return {@link ViewHelper}
     */
    public ViewHelper addTouchArea(
            final View view,
            final int top,
            final int bottom,
            final int left,
            final int right
    ) {
        ClickUtils.addTouchArea(view, top, bottom, left, right);
        return this;
    }

    // =================
    // = ListViewUtils =
    // =================

    /**
     * 滑动到指定索引 ( 有滚动过程 )
     * @param view     {@link View}
     * @param position 索引
     * @return {@link ViewHelper}
     */
    public ViewHelper smoothScrollToPosition(
            final View view,
            final int position
    ) {
        ListViewUtils.smoothScrollToPosition(view, position);
        return this;
    }

    /**
     * 滑动到指定索引 ( 无滚动过程 )
     * @param view     {@link View}
     * @param position 索引
     * @return {@link ViewHelper}
     */
    public ViewHelper scrollToPosition(
            final View view,
            final int position
    ) {
        ListViewUtils.scrollToPosition(view, position);
        return this;
    }

    // =============
    // = 滑动到顶部 =
    // =============

    /**
     * 滑动到顶部 ( 有滚动过程 )
     * @param view {@link View}
     * @return {@link ViewHelper}
     */
    public ViewHelper smoothScrollToTop(final View view) {
        ListViewUtils.smoothScrollToTop(view);
        return this;
    }

    /**
     * 滑动到顶部 ( 无滚动过程 )
     * @param view {@link View}
     * @return {@link ViewHelper}
     */
    public ViewHelper scrollToTop(final View view) {
        ListViewUtils.scrollToTop(view);
        return this;
    }

    // =============
    // = 滑动到底部 =
    // =============

    /**
     * 滑动到底部 ( 有滚动过程 )
     * <pre>
     *     如果未到达底部 ( position 可以再加上 smoothScrollBy 搭配到底部 )
     *     smoothScrollToBottom(view)
     *     smoothScrollBy(view, 0, Integer.MAX_VALUE);
     * </pre>
     * @param view {@link View}
     * @return {@link ViewHelper}
     */
    public ViewHelper smoothScrollToBottom(final View view) {
        ListViewUtils.smoothScrollToBottom(view);
        return this;
    }

    /**
     * 滑动到底部 ( 无滚动过程 )
     * <pre>
     *     如果未到达底部 ( position 可以再加上 scrollBy 搭配到底部 )
     *     scrollToBottom(view)
     *     scrollBy(view, 0, Integer.MAX_VALUE);
     * </pre>
     * @param view {@link View}
     * @return {@link ViewHelper}
     */
    public ViewHelper scrollToBottom(final View view) {
        ListViewUtils.scrollToBottom(view);
        return this;
    }

    // ==============
    // = ScrollView =
    // ==============

    /**
     * 滚动到指定位置 ( 有滚动过程, 相对于初始位置移动 )
     * @param view {@link View}
     * @param x    X 轴开始坐标
     * @param y    Y 轴开始坐标
     * @return {@link ViewHelper}
     */
    public ViewHelper smoothScrollTo(
            final View view,
            final int x,
            final int y
    ) {
        ListViewUtils.smoothScrollTo(view, x, y);
        return this;
    }

    /**
     * 滚动到指定位置 ( 有滚动过程, 相对于上次移动的最后位置移动 )
     * @param view {@link View}
     * @param x    X 轴开始坐标
     * @param y    Y 轴开始坐标
     * @return {@link ViewHelper}
     */
    public ViewHelper smoothScrollBy(
            final View view,
            final int x,
            final int y
    ) {
        ListViewUtils.smoothScrollBy(view, x, y);
        return this;
    }

    /**
     * 滚动方向 ( 有滚动过程 )
     * @param view      {@link View}
     * @param direction 滚动方向 如: View.FOCUS_UP、View.FOCUS_DOWN
     * @return {@link ViewHelper}
     */
    public ViewHelper fullScroll(
            final View view,
            final int direction
    ) {
        ListViewUtils.fullScroll(view, direction);
        return this;
    }
}