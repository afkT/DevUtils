package dev.utils.app.helper;

import android.app.Dialog;
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

import java.lang.ref.WeakReference;

import dev.utils.LogPrintUtils;
import dev.utils.app.ClickUtils;
import dev.utils.app.EditTextUtils;
import dev.utils.app.HandlerUtils;
import dev.utils.app.ImageViewUtils;
import dev.utils.app.KeyBoardUtils;
import dev.utils.app.ListViewUtils;
import dev.utils.app.ListenerUtils;
import dev.utils.app.SizeUtils;
import dev.utils.app.TextViewUtils;
import dev.utils.app.ViewUtils;

/**
 * detail: 简化链式设置 View Helper 类
 * @author Ttt
 * <pre>
 *     整合 {@link ViewHelper}、{@link DevHelper} 部分代码
 *     针对单个 View 设置处理, 无需多次传入 View
 * </pre>
 */
public final class QuickHelper {

    // 日志 TAG
    private static final String TAG = QuickHelper.class.getSimpleName();

    // 持有 View
    private final WeakReference<View> mViewRef;

    /**
     * 构造函数
     * @param target 目标 View
     */
    public QuickHelper(final View target) {
        this.mViewRef = new WeakReference<>(target);
    }

    /**
     * 获取 QuickHelper
     * @param target 目标 View
     * @return {@link QuickHelper}
     */
    public static QuickHelper get(final View target) {
        return new QuickHelper(target);
    }

    /**
     * 获取 View
     * @param <T> 泛型
     * @return {@link View}
     */
    public <T extends View> T getView() {
        return ViewUtils.convertView(targetView());
    }

    /**
     * 获取目标 View
     * @return {@link View}
     */
    private View targetView() {
        return mViewRef.get();
    }

    /**
     * 获取目标 View ( 转 ViewGroup )
     * @return {@link ViewGroup}
     */
    private ViewGroup targetViewGroup() {
        try {
            return (ViewGroup) targetView();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "targetViewGroup");
        }
        return null;
    }

    /**
     * 获取目标 View ( 转 ImageView )
     * @return {@link ImageView}
     */
    private ImageView targetImageView() {
        try {
            return (ImageView) targetView();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "targetImageView");
        }
        return null;
    }

    /**
     * 获取目标 View ( 转 TextView )
     * @return {@link TextView}
     */
    private TextView targetTextView() {
        try {
            return (TextView) targetView();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "targetTextView");
        }
        return null;
    }

    /**
     * 获取目标 View ( 转 EditText )
     * @return {@link EditText}
     */
    private EditText targetEditText() {
        try {
            return (EditText) targetView();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "targetEditText");
        }
        return null;
    }

    // ==========
    // = Helper =
    // ==========

    /**
     * 获取 ViewHelper
     * @return {@link ViewHelper}
     */
    public ViewHelper viewHelper() {
        return ViewHelper.get();
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

    // ==============
    // = ViewHelper =
    // ==============

    // ===========
    // = Handler =
    // ===========

    /**
     * 在主线程 Handler 中执行任务
     * @param runnable 可执行的任务
     * @return {@link QuickHelper}
     */
    public QuickHelper postRunnable(final Runnable runnable) {
        HandlerUtils.postRunnable(runnable);
        return this;
    }

    /**
     * 在主线程 Handler 中执行延迟任务
     * @param runnable    可执行的任务
     * @param delayMillis 延迟时间
     * @return {@link QuickHelper}
     */
    public QuickHelper postRunnable(
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
     * @return {@link QuickHelper}
     */
    public QuickHelper postRunnable(
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
     * @return {@link QuickHelper}
     */
    public QuickHelper postRunnable(
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
     * @return {@link QuickHelper}
     */
    public QuickHelper removeRunnable(final Runnable runnable) {
        HandlerUtils.removeRunnable(runnable);
        return this;
    }

    // ========
    // = Text =
    // ========

    /**
     * 设置 Hint 文本
     * @param text Hint text
     * @return {@link QuickHelper}
     */
    public QuickHelper setHint(final CharSequence text) {
        TextViewUtils.setHint(targetTextView(), text);
        return this;
    }

    /**
     * 设置文本
     * @param text TextView text
     * @return {@link QuickHelper}
     */
    public QuickHelper setText(final CharSequence text) {
        View view = targetView();
        if (view instanceof EditText) {
            EditTextUtils.setText(EditTextUtils.getEditText(view), text);
        } else {
            TextViewUtils.setText(view, text);
        }
        return this;
    }

    /**
     * 设置 Html 内容
     * @param content Html content
     * @return {@link QuickHelper}
     */
    public QuickHelper setHtmlText(final String content) {
        TextViewUtils.setHtmlText(targetTextView(), content);
        return this;
    }

    /**
     * 设置 Hint 字体颜色
     * @param color R.color.id
     * @return {@link QuickHelper}
     */
    public QuickHelper setHintTextColor(@ColorInt final int color) {
        TextViewUtils.setHintTextColor(targetTextView(), color);
        return this;
    }

    /**
     * 设置 Hint 字体颜色
     * @param colors {@link ColorStateList}
     * @return {@link QuickHelper}
     */
    public QuickHelper setHintTextColor(final ColorStateList colors) {
        TextViewUtils.setHintTextColor(targetTextView(), colors);
        return this;
    }

    /**
     * 设置字体颜色
     * @param color R.color.id
     * @return {@link QuickHelper}
     */
    public QuickHelper setTextColor(@ColorInt final int color) {
        TextViewUtils.setTextColor(targetTextView(), color);
        return this;
    }

    /**
     * 设置字体颜色
     * @param colors {@link ColorStateList}
     * @return {@link QuickHelper}
     */
    public QuickHelper setTextColor(final ColorStateList colors) {
        TextViewUtils.setTextColor(targetTextView(), colors);
        return this;
    }

    /**
     * 设置字体
     * @param typeface {@link Typeface} 字体样式
     * @return {@link QuickHelper}
     */
    public QuickHelper setTypeface(final Typeface typeface) {
        TextViewUtils.setTypeface(targetTextView(), typeface);
        return this;
    }

    /**
     * 设置字体大小 ( px 像素 )
     * @param size 字体大小
     * @return {@link QuickHelper}
     */
    public QuickHelper setTextSizeByPx(final float size) {
        TextViewUtils.setTextSizeByPx(targetTextView(), size);
        return this;
    }

    /**
     * 设置字体大小 ( sp 缩放像素 )
     * @param size 字体大小
     * @return {@link QuickHelper}
     */
    public QuickHelper setTextSizeBySp(final float size) {
        TextViewUtils.setTextSizeBySp(targetTextView(), size);
        return this;
    }

    /**
     * 设置字体大小 ( dp 与设备无关的像素 )
     * @param size 字体大小
     * @return {@link QuickHelper}
     */
    public QuickHelper setTextSizeByDp(final float size) {
        TextViewUtils.setTextSizeByDp(targetTextView(), size);
        return this;
    }

    /**
     * 设置字体大小 ( inches 英寸 )
     * @param size 字体大小
     * @return {@link QuickHelper}
     */
    public QuickHelper setTextSizeByIn(final float size) {
        TextViewUtils.setTextSizeByIn(targetTextView(), size);
        return this;
    }

    /**
     * 设置字体大小
     * @param unit 字体参数类型
     * @param size 字体大小
     * @return {@link QuickHelper}
     */
    public QuickHelper setTextSize(
            final int unit,
            final float size
    ) {
        TextViewUtils.setTextSize(targetTextView(), unit, size);
        return this;
    }

    /**
     * 清空 flags
     * @return {@link QuickHelper}
     */
    public QuickHelper clearFlags() {
        TextViewUtils.clearFlags(targetView());
        return this;
    }

    /**
     * 设置 TextView flags
     * @param flags flags
     * @return {@link QuickHelper}
     */
    public QuickHelper setPaintFlags(final int flags) {
        TextViewUtils.setPaintFlags(targetTextView(), flags);
        return this;
    }

    /**
     * 设置 TextView 抗锯齿 flags
     * @return {@link QuickHelper}
     */
    public QuickHelper setAntiAliasFlag() {
        TextViewUtils.setAntiAliasFlag(targetView());
        return this;
    }

    /**
     * 设置 TextView 是否加粗
     * @return {@link QuickHelper}
     */
    public QuickHelper setBold() {
        TextViewUtils.setBold(targetView());
        return this;
    }

    /**
     * 设置 TextView 是否加粗
     * @param isBold {@code true} yes, {@code false} no
     * @return {@link QuickHelper}
     */
    public QuickHelper setBold(final boolean isBold) {
        TextViewUtils.setBold(targetTextView(), isBold);
        return this;
    }

    /**
     * 设置 TextView 是否加粗
     * @param typeface {@link Typeface} 字体样式
     * @param isBold   {@code true} yes, {@code false} no
     * @return {@link QuickHelper}
     */
    public QuickHelper setBold(
            final Typeface typeface,
            final boolean isBold
    ) {
        TextViewUtils.setBold(targetTextView(), typeface, isBold);
        return this;
    }

    /**
     * 设置下划线
     * @return {@link QuickHelper}
     */
    public QuickHelper setUnderlineText() {
        TextViewUtils.setUnderlineText(targetView());
        return this;
    }

    /**
     * 设置下划线并加清晰
     * @param isAntiAlias 是否消除锯齿
     * @return {@link QuickHelper}
     */
    public QuickHelper setUnderlineText(final boolean isAntiAlias) {
        TextViewUtils.setUnderlineText(targetTextView(), isAntiAlias);
        return this;
    }

    /**
     * 设置中划线
     * @return {@link QuickHelper}
     */
    public QuickHelper setStrikeThruText() {
        TextViewUtils.setStrikeThruText(targetView());
        return this;
    }

    /**
     * 设置中划线并加清晰
     * @param isAntiAlias 是否消除锯齿
     * @return {@link QuickHelper}
     */
    public QuickHelper setStrikeThruText(final boolean isAntiAlias) {
        TextViewUtils.setStrikeThruText(targetTextView(), isAntiAlias);
        return this;
    }

    /**
     * 设置文字水平间距
     * @param letterSpacing 文字水平间距值
     * @return {@link QuickHelper}
     */
    public QuickHelper setLetterSpacing(final float letterSpacing) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            TextViewUtils.setLetterSpacing(targetTextView(), letterSpacing);
        }
        return this;
    }

    /**
     * 设置文字行间距 ( 行高 )
     * @param lineSpacing 文字行间距 ( 行高 ), android:lineSpacingExtra
     * @return {@link QuickHelper}
     */
    public QuickHelper setLineSpacing(final float lineSpacing) {
        TextViewUtils.setLineSpacing(targetTextView(), lineSpacing);
        return this;
    }

    /**
     * 设置文字行间距 ( 行高 )、行间距倍数
     * @param lineSpacing 文字行间距 ( 行高 ), android:lineSpacingExtra
     * @param multiplier  行间距倍数, android:lineSpacingMultiplier
     * @return {@link QuickHelper}
     */
    public QuickHelper setLineSpacingAndMultiplier(
            final float lineSpacing,
            final float multiplier
    ) {
        TextViewUtils.setLineSpacingAndMultiplier(targetTextView(), lineSpacing, multiplier);
        return this;
    }

    /**
     * 设置字体水平方向的缩放
     * @param size 缩放比例
     * @return {@link QuickHelper}
     */
    public QuickHelper setTextScaleX(final float size) {
        TextViewUtils.setTextScaleX(targetTextView(), size);
        return this;
    }

    /**
     * 设置是否保留字体留白间隙区域
     * @param includePadding 是否保留字体留白间隙区域
     * @return {@link QuickHelper}
     */
    public QuickHelper setIncludeFontPadding(final boolean includePadding) {
        TextViewUtils.setIncludeFontPadding(targetTextView(), includePadding);
        return this;
    }

    /**
     * 设置输入类型
     * @param type 类型
     * @return {@link QuickHelper}
     */
    public QuickHelper setInputType(final int type) {
        TextViewUtils.setInputType(targetTextView(), type);
        return this;
    }

    /**
     * 设置软键盘右下角按钮类型
     * @param imeOptions 软键盘按钮类型
     * @return {@link QuickHelper}
     */
    public QuickHelper setImeOptions(final int imeOptions) {
        TextViewUtils.setImeOptions(targetTextView(), imeOptions);
        return this;
    }

    /**
     * 设置行数
     * @param lines 行数
     * @return {@link QuickHelper}
     */
    public QuickHelper setLines(final int lines) {
        TextViewUtils.setLines(targetTextView(), lines);
        return this;
    }

    /**
     * 设置最大行数
     * @param maxLines 最大行数
     * @return {@link QuickHelper}
     */
    public QuickHelper setMaxLines(final int maxLines) {
        TextViewUtils.setMaxLines(targetTextView(), maxLines);
        return this;
    }

    /**
     * 设置最小行数
     * @param minLines 最小行数
     * @return {@link QuickHelper}
     */
    public QuickHelper setMinLines(final int minLines) {
        TextViewUtils.setMinLines(targetTextView(), minLines);
        return this;
    }

    /**
     * 设置最大字符宽度限制
     * @param maxEms 最大字符
     * @return {@link QuickHelper}
     */
    public QuickHelper setMaxEms(final int maxEms) {
        TextViewUtils.setMaxEms(targetTextView(), maxEms);
        return this;
    }

    /**
     * 设置最小字符宽度限制
     * @param minEms 最小字符
     * @return {@link QuickHelper}
     */
    public QuickHelper setMinEms(final int minEms) {
        TextViewUtils.setMinEms(targetTextView(), minEms);
        return this;
    }

    /**
     * 设置指定字符宽度
     * @param ems 字符
     * @return {@link QuickHelper}
     */
    public QuickHelper setEms(final int ems) {
        TextViewUtils.setEms(targetTextView(), ems);
        return this;
    }

    /**
     * 设置 Ellipsize 效果
     * @param where {@link TextUtils.TruncateAt}
     * @return {@link QuickHelper}
     */
    public QuickHelper setEllipsize(final TextUtils.TruncateAt where) {
        TextViewUtils.setEllipsize(targetTextView(), where);
        return this;
    }

    /**
     * 设置自动识别文本链接
     * @param mask {@link android.text.util.Linkify}
     * @return {@link QuickHelper}
     */
    public QuickHelper setAutoLinkMask(final int mask) {
        TextViewUtils.setAutoLinkMask(targetTextView(), mask);
        return this;
    }

    /**
     * 设置文本全为大写
     * @param allCaps 是否全部大写
     * @return {@link QuickHelper}
     */
    public QuickHelper setAllCaps(final boolean allCaps) {
        TextViewUtils.setAllCaps(targetTextView(), allCaps);
        return this;
    }

    /**
     * 设置 Text Gravity
     * @param gravity {@link android.view.Gravity}
     * @return {@link QuickHelper}
     */
    public QuickHelper setTextGravity(final int gravity) {
        TextViewUtils.setGravity(targetTextView(), gravity);
        return this;
    }

    // ============
    // = EditText =
    // ============

    /**
     * 设置内容
     * @param content  文本内容
     * @param isSelect 是否设置光标
     * @return {@link QuickHelper}
     */
    public QuickHelper setText(
            final CharSequence content,
            final boolean isSelect
    ) {
        EditTextUtils.setText(targetEditText(), content, isSelect);
        return this;
    }

    /**
     * 追加内容 ( 当前光标位置追加 )
     * @param content  文本内容
     * @param isSelect 是否设置光标
     * @return {@link QuickHelper}
     */
    public QuickHelper insert(
            final CharSequence content,
            final boolean isSelect
    ) {
        EditTextUtils.insert(targetEditText(), content, isSelect);
        return this;
    }

    /**
     * 追加内容
     * @param content  文本内容
     * @param start    开始添加的位置
     * @param isSelect 是否设置光标
     * @return {@link QuickHelper}
     */
    public QuickHelper insert(
            final CharSequence content,
            final int start,
            final boolean isSelect
    ) {
        EditTextUtils.insert(targetEditText(), content, start, isSelect);
        return this;
    }

    /**
     * 设置长度限制
     * @param maxLength 长度限制
     * @return {@link QuickHelper}
     */
    public QuickHelper setMaxLength(final int maxLength) {
        View view = targetView();
        if (view instanceof EditText) {
            EditTextUtils.setMaxLength(EditTextUtils.getEditText(view), maxLength);
        } else {
            TextViewUtils.setMaxLength(view, maxLength);
        }
        return this;
    }

    /**
     * 设置长度限制, 并且设置内容
     * @param content   文本内容
     * @param maxLength 长度限制
     * @return {@link QuickHelper}
     */
    public QuickHelper setMaxLengthAndText(
            final CharSequence content,
            final int maxLength
    ) {
        View view = targetView();
        if (view instanceof EditText) {
            EditTextUtils.setMaxLengthAndText(EditTextUtils.getEditText(view), content, maxLength);
        } else {
            TextViewUtils.setMaxLengthAndText(view, content, maxLength);
        }
        return this;
    }

    /**
     * 设置是否显示光标
     * @param visible 是否显示光标
     * @return {@link QuickHelper}
     */
    public QuickHelper setCursorVisible(final boolean visible) {
        EditTextUtils.setCursorVisible(targetEditText(), visible);
        return this;
    }

    /**
     * 设置光标在第一位
     * @return {@link QuickHelper}
     */
    public QuickHelper setSelectionToTop() {
        EditTextUtils.setSelectionToTop(targetEditText());
        return this;
    }

    /**
     * 设置光标在最后一位
     * @return {@link QuickHelper}
     */
    public QuickHelper setSelectionToBottom() {
        EditTextUtils.setSelectionToBottom(targetEditText());
        return this;
    }

    /**
     * 设置光标位置
     * @param index 光标位置
     * @return {@link QuickHelper}
     */
    public QuickHelper setSelection(final int index) {
        EditTextUtils.setSelection(targetEditText(), index);
        return this;
    }

    // =

    /**
     * 设置文本视图显示转换
     * @param method {@link TransformationMethod}
     * @return {@link QuickHelper}
     */
    public QuickHelper setTransformationMethod(final TransformationMethod method) {
        View view = targetView();
        if (view instanceof EditText) {
            EditTextUtils.setTransformationMethod(EditTextUtils.getEditText(view), method);
        } else {
            TextViewUtils.setTransformationMethod(view, method);
        }
        return this;
    }

    /**
     * 设置密码文本视图显示转换
     * @param isDisplayPassword 是否显示密码
     * @return {@link QuickHelper}
     */
    public QuickHelper setTransformationMethod(final boolean isDisplayPassword) {
        View view = targetView();
        if (view instanceof EditText) {
            EditTextUtils.setTransformationMethod(EditTextUtils.getEditText(view), isDisplayPassword);
        } else {
            TextViewUtils.setTransformationMethod(view, isDisplayPassword);
        }
        return this;
    }

    /**
     * 设置密码文本视图显示转换
     * @param isDisplayPassword 是否显示密码
     * @param isSelectBottom    是否设置光标到最后
     * @return {@link QuickHelper}
     */
    public QuickHelper setTransformationMethod(
            final boolean isDisplayPassword,
            final boolean isSelectBottom
    ) {
        EditTextUtils.setTransformationMethod(targetEditText(), isDisplayPassword, isSelectBottom);
        return this;
    }

    // =

    /**
     * 添加输入监听事件
     * @param watcher 输入监听
     * @return {@link QuickHelper}
     */
    public QuickHelper addTextChangedListener(final TextWatcher watcher) {
        EditTextUtils.addTextChangedListener(targetEditText(), watcher);
        return this;
    }

    /**
     * 移除输入监听事件
     * @param watcher 输入监听
     * @return {@link QuickHelper}
     */
    public QuickHelper removeTextChangedListener(final TextWatcher watcher) {
        EditTextUtils.removeTextChangedListener(targetEditText(), watcher);
        return this;
    }

    /**
     * 设置 KeyListener
     * @param keyListener {@link KeyListener}
     * @return {@link QuickHelper}
     */
    public QuickHelper setKeyListener(final KeyListener keyListener) {
        EditTextUtils.setKeyListener(targetEditText(), keyListener);
        return this;
    }

    /**
     * 设置 KeyListener
     * @param accepted 允许输入的内容, 如: 0123456789
     * @return {@link QuickHelper}
     */
    public QuickHelper setKeyListener(final String accepted) {
        EditTextUtils.setKeyListener(targetEditText(), accepted);
        return this;
    }

    /**
     * 设置 KeyListener
     * @param accepted 允许输入的内容
     * @return {@link QuickHelper}
     */
    public QuickHelper setKeyListener(final char[] accepted) {
        EditTextUtils.setKeyListener(targetEditText(), accepted);
        return this;
    }

    // =========
    // = Image =
    // =========

    /**
     * 设置 ImageView 是否保持宽高比
     * @param adjustViewBounds 是否调整此视图的边界以保持可绘制的原始纵横比
     * @return {@link QuickHelper}
     */
    public QuickHelper setAdjustViewBounds(final boolean adjustViewBounds) {
        ImageViewUtils.setAdjustViewBounds(targetImageView(), adjustViewBounds);
        return this;
    }

    /**
     * 设置 ImageView 最大高度
     * @param maxHeight 最大高度
     * @return {@link QuickHelper}
     */
    public QuickHelper setMaxHeight(final int maxHeight) {
        ImageViewUtils.setMaxHeight(targetImageView(), maxHeight);
        return this;
    }

    /**
     * 设置 ImageView 最大宽度
     * @param maxWidth 最大宽度
     * @return {@link QuickHelper}
     */
    public QuickHelper setMaxWidth(final int maxWidth) {
        ImageViewUtils.setMaxWidth(targetImageView(), maxWidth);
        return this;
    }

    // =

    /**
     * 设置背景图片
     * @param background 背景图片
     * @return {@link QuickHelper}
     */
    public QuickHelper setBackground(final Drawable background) {
        ViewUtils.setBackground(targetView(), background);
        return this;
    }

    /**
     * 设置背景颜色
     * @param color 背景颜色
     * @return {@link QuickHelper}
     */
    public QuickHelper setBackgroundColor(@ColorInt final int color) {
        ViewUtils.setBackgroundColor(targetView(), color);
        return this;
    }

    /**
     * 设置背景资源
     * @param resId resource identifier
     * @return {@link QuickHelper}
     */
    public QuickHelper setBackgroundResource(@DrawableRes final int resId) {
        ViewUtils.setBackgroundResource(targetView(), resId);
        return this;
    }

    /**
     * 设置背景着色颜色
     * @param tint 着色颜色
     * @return {@link QuickHelper}
     */
    public QuickHelper setBackgroundTintList(final ColorStateList tint) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ViewUtils.setBackgroundTintList(targetView(), tint);
        }
        return this;
    }

    /**
     * 设置背景着色模式
     * @param tintMode 着色模式 {@link PorterDuff.Mode}
     * @return {@link QuickHelper}
     */
    public QuickHelper setBackgroundTintMode(final PorterDuff.Mode tintMode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ViewUtils.setBackgroundTintMode(targetView(), tintMode);
        }
        return this;
    }

    /**
     * 设置前景图片
     * @param foreground 前景图片
     * @return {@link QuickHelper}
     */
    public QuickHelper setForeground(final Drawable foreground) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ViewUtils.setForeground(targetView(), foreground);
        }
        return this;
    }

    /**
     * 设置前景重心
     * @param gravity 重心
     * @return {@link QuickHelper}
     */
    public QuickHelper setForegroundGravity(final int gravity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ViewUtils.setForegroundGravity(targetView(), gravity);
        }
        return this;
    }

    /**
     * 设置前景着色颜色
     * @param tint 着色颜色
     * @return {@link QuickHelper}
     */
    public QuickHelper setForegroundTintList(final ColorStateList tint) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ViewUtils.setForegroundTintList(targetView(), tint);
        }
        return this;
    }

    /**
     * 设置前景着色模式
     * @param tintMode 着色模式 {@link PorterDuff.Mode}
     * @return {@link QuickHelper}
     */
    public QuickHelper setForegroundTintMode(final PorterDuff.Mode tintMode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ViewUtils.setForegroundTintMode(targetView(), tintMode);
        }
        return this;
    }

    /**
     * 设置 ImageView Bitmap
     * @param bitmap {@link Bitmap}
     * @return {@link QuickHelper}
     */
    public QuickHelper setImageBitmap(final Bitmap bitmap) {
        ImageViewUtils.setImageBitmap(targetView(), bitmap);
        return this;
    }

    /**
     * 设置 ImageView Drawable
     * @param drawable {@link Bitmap}
     * @return {@link QuickHelper}
     */
    public QuickHelper setImageDrawable(final Drawable drawable) {
        ImageViewUtils.setImageDrawable(targetView(), drawable);
        return this;
    }

    /**
     * 设置 ImageView 资源
     * @param resId resource identifier
     * @return {@link QuickHelper}
     */
    public QuickHelper setImageResource(@DrawableRes final int resId) {
        ImageViewUtils.setImageResource(targetView(), resId);
        return this;
    }

    /**
     * 设置 ImageView Matrix
     * @param matrix {@link Matrix}
     * @return {@link QuickHelper}
     */
    public QuickHelper setImageMatrix(final Matrix matrix) {
        ImageViewUtils.setImageMatrix(targetView(), matrix);
        return this;
    }

    /**
     * 设置 ImageView 着色颜色
     * @param tint 着色颜色
     * @return {@link QuickHelper}
     */
    public QuickHelper setImageTintList(final ColorStateList tint) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ImageViewUtils.setImageTintList(targetView(), tint);
        }
        return this;
    }

    /**
     * 设置 ImageView 着色模式
     * @param tintMode 着色模式 {@link PorterDuff.Mode}
     * @return {@link QuickHelper}
     */
    public QuickHelper setImageTintMode(final PorterDuff.Mode tintMode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ImageViewUtils.setImageTintMode(targetView(), tintMode);
        }
        return this;
    }

    /**
     * 设置 ImageView 缩放类型
     * @param scaleType 缩放类型 {@link ImageView.ScaleType}
     * @return {@link QuickHelper}
     */
    public QuickHelper setScaleType(final ImageView.ScaleType scaleType) {
        ImageViewUtils.setScaleType(targetView(), scaleType);
        return this;
    }

    /**
     * ImageView 着色处理
     * @param color 颜色值
     * @return {@link QuickHelper}
     */
    public QuickHelper setColorFilter(@ColorInt final int color) {
        ViewUtils.setColorFilter(targetView(), color);
        return this;
    }

    /**
     * ImageView 着色处理, 并且设置 Drawable
     * @param drawable {@link Drawable}
     * @param color    颜色值
     * @return {@link QuickHelper}
     */
    public QuickHelper setColorFilter(
            final Drawable drawable,
            @ColorInt final int color
    ) {
        ViewUtils.setColorFilter(targetView(), drawable, color);
        return this;
    }

    /**
     * ImageView 着色处理
     * @param colorFilter 颜色过滤 ( 效果 )
     * @return {@link QuickHelper}
     */
    public QuickHelper setColorFilter(final ColorFilter colorFilter) {
        ViewUtils.setColorFilter(targetView(), colorFilter);
        return this;
    }

    /**
     * ImageView 着色处理, 并且设置 Drawable
     * @param drawable    {@link Drawable}
     * @param colorFilter 颜色过滤 ( 效果 )
     * @return {@link QuickHelper}
     */
    public QuickHelper setColorFilter(
            final Drawable drawable,
            final ColorFilter colorFilter
    ) {
        ViewUtils.setColorFilter(targetView(), drawable, colorFilter);
        return this;
    }

    /**
     * 设置 View 图片资源
     * @param resId resource identifier
     * @return {@link QuickHelper}
     */
    public QuickHelper setBackgroundResources(@DrawableRes final int resId) {
        ImageViewUtils.setBackgroundResources(resId, targetView());
        return this;
    }

    /**
     * 设置 View 图片资源
     * @param resId        resource identifier
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @return {@link QuickHelper}
     */
    public QuickHelper setBackgroundResources(
            @DrawableRes final int resId,
            final int isVisibility
    ) {
        ImageViewUtils.setBackgroundResources(resId, isVisibility, targetView());
        return this;
    }

    /**
     * 设置 View 图片资源
     * @param resId resource identifier
     * @return {@link QuickHelper}
     */
    public QuickHelper setImageResources(@DrawableRes final int resId) {
        ImageViewUtils.setImageResources(resId, targetView());
        return this;
    }

    /**
     * 设置 View 图片资源
     * @param resId        resource identifier
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @return {@link QuickHelper}
     */
    public QuickHelper setImageResources(
            @DrawableRes final int resId,
            final int isVisibility
    ) {
        ImageViewUtils.setImageResources(resId, isVisibility, targetView());
        return this;
    }

    /**
     * 设置 View Bitmap
     * @param bitmap {@link Bitmap}
     * @return {@link QuickHelper}
     */
    public QuickHelper setImageBitmaps(final Bitmap bitmap) {
        ImageViewUtils.setImageBitmaps(bitmap, targetView());
        return this;
    }

    /**
     * 设置 View Bitmap
     * @param bitmap       {@link Bitmap}
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @return {@link QuickHelper}
     */
    public QuickHelper setImageBitmaps(
            final Bitmap bitmap,
            final int isVisibility
    ) {
        ImageViewUtils.setImageBitmaps(bitmap, isVisibility, targetView());
        return this;
    }

    /**
     * 设置 View Drawable
     * @param drawable {@link drawable}
     * @return {@link QuickHelper}
     */
    public QuickHelper setImageDrawables(final Drawable drawable) {
        ImageViewUtils.setImageDrawables(drawable, targetView());
        return this;
    }

    /**
     * 设置 View Drawable
     * @param drawable     {@link drawable}
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @return {@link QuickHelper}
     */
    public QuickHelper setImageDrawables(
            final Drawable drawable,
            final int isVisibility
    ) {
        ImageViewUtils.setImageDrawables(drawable, isVisibility, targetView());
        return this;
    }

    /**
     * 设置 View 缩放模式
     * @param scaleType {@link ImageView.ScaleType}
     * @return {@link QuickHelper}
     */
    public QuickHelper setScaleTypes(final ImageView.ScaleType scaleType) {
        ImageViewUtils.setScaleTypes(scaleType, targetView());
        return this;
    }

    /**
     * 设置 View 缩放模式
     * @param scaleType    {@link ImageView.ScaleType}
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @return {@link QuickHelper}
     */
    public QuickHelper setScaleTypes(
            final ImageView.ScaleType scaleType,
            final int isVisibility
    ) {
        ImageViewUtils.setScaleTypes(scaleType, isVisibility, targetView());
        return this;
    }

    // ========
    // = View =
    // ========

    /**
     * 设置 View 宽度、高度
     * @param width  View 宽度
     * @param height View 高度
     * @return {@link QuickHelper}
     */
    public QuickHelper setWidthHeight(
            final int width,
            final int height
    ) {
        ViewUtils.setWidthHeight(targetView(), width, height);
        return this;
    }

    /**
     * 设置 View 宽度、高度
     * @param width     View 宽度
     * @param height    View 高度
     * @param nullNewLP 如果 LayoutParams 为 null 是否创建新的
     * @return {@link QuickHelper}
     */
    public QuickHelper setWidthHeight(
            final int width,
            final int height,
            final boolean nullNewLP
    ) {
        ViewUtils.setWidthHeight(targetView(), width, height, nullNewLP);
        return this;
    }

    /**
     * 设置 View 宽度
     * @param width View 宽度
     * @return {@link QuickHelper}
     */
    public QuickHelper setWidth(final int width) {
        ViewUtils.setWidth(targetView(), width);
        return this;
    }

    /**
     * 设置 View 宽度
     * @param width     View 宽度
     * @param nullNewLP 如果 LayoutParams 为 null 是否创建新的
     * @return {@link QuickHelper}
     */
    public QuickHelper setWidth(
            final int width,
            final boolean nullNewLP
    ) {
        ViewUtils.setWidth(targetView(), width, nullNewLP);
        return this;
    }

    /**
     * 设置 View 高度
     * @param height View 高度
     * @return {@link QuickHelper}
     */
    public QuickHelper setHeight(final int height) {
        ViewUtils.setHeight(targetView(), height);
        return this;
    }

    /**
     * 设置 View 高度
     * @param height    View 高度
     * @param nullNewLP 如果 LayoutParams 为 null 是否创建新的
     * @return {@link QuickHelper}
     */
    public QuickHelper setHeight(
            final int height,
            final boolean nullNewLP
    ) {
        ViewUtils.setHeight(targetView(), height, nullNewLP);
        return this;
    }

    /**
     * 设置 View 最小高度
     * @param minHeight 最小高度
     * @return {@link QuickHelper}
     */
    public QuickHelper setMinimumHeight(final int minHeight) {
        ViewUtils.setMinimumHeight(targetView(), minHeight);
        return this;
    }

    /**
     * 设置 View 最小宽度
     * @param minWidth 最小宽度
     * @return {@link QuickHelper}
     */
    public QuickHelper setMinimumWidth(final int minWidth) {
        ViewUtils.setMinimumWidth(targetView(), minWidth);
        return this;
    }

    /**
     * 设置 View 透明度
     * @param alpha 透明度
     * @return {@link QuickHelper}
     */
    public QuickHelper setAlpha(@FloatRange(from = 0.0, to = 1.0) final float alpha) {
        ViewUtils.setAlpha(targetView(), alpha);
        return this;
    }

    /**
     * 设置 View Tag
     * @param object Tag
     * @return {@link QuickHelper}
     */
    public QuickHelper setTag(final Object object) {
        ViewUtils.setTag(targetView(), object);
        return this;
    }

    // =

    /**
     * View 内容滚动位置 ( 相对于初始位置移动 )
     * <pre>
     *     无滚动过程
     * </pre>
     * @param x X 轴开始坐标
     * @param y Y 轴开始坐标
     * @return {@link QuickHelper}
     */
    public QuickHelper scrollTo(
            final int x,
            final int y
    ) {
        ViewUtils.scrollTo(targetView(), x, y);
        return this;
    }

    /**
     * View 内部滚动位置 ( 相对于上次移动的最后位置移动 )
     * <pre>
     *     无滚动过程
     * </pre>
     * @param x X 轴开始坐标
     * @param y Y 轴开始坐标
     * @return {@link QuickHelper}
     */
    public QuickHelper scrollBy(
            final int x,
            final int y
    ) {
        ViewUtils.scrollBy(targetView(), x, y);
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
     * @param focusability {@link ViewGroup#FOCUS_BEFORE_DESCENDANTS}、{@link ViewGroup#FOCUS_AFTER_DESCENDANTS}、{@link ViewGroup#FOCUS_BLOCK_DESCENDANTS}
     * @return {@link QuickHelper}
     */
    public QuickHelper setDescendantFocusability(final int focusability) {
        ViewUtils.setDescendantFocusability(targetViewGroup(), focusability);
        return this;
    }

    /**
     * 设置 View 滚动模式
     * <pre>
     *     设置滑动到边缘时无效果模式 {@link View#OVER_SCROLL_NEVER}
     *     android:overScrollMode="never"
     * </pre>
     * @param overScrollMode {@link View#OVER_SCROLL_ALWAYS}、{@link View#OVER_SCROLL_IF_CONTENT_SCROLLS}、{@link View#OVER_SCROLL_NEVER}
     * @return {@link QuickHelper}
     */
    public QuickHelper setOverScrollMode(final int overScrollMode) {
        ViewUtils.setOverScrollMode(targetView(), overScrollMode);
        return this;
    }

    // =

    /**
     * 设置是否绘制横向滚动条
     * @param horizontalScrollBarEnabled {@code true} yes, {@code false} no
     * @return {@link QuickHelper}
     */
    public QuickHelper setHorizontalScrollBarEnabled(final boolean horizontalScrollBarEnabled) {
        ViewUtils.setHorizontalScrollBarEnabled(targetView(), horizontalScrollBarEnabled);
        return this;
    }

    /**
     * 设置是否绘制垂直滚动条
     * @param verticalScrollBarEnabled {@code true} yes, {@code false} no
     * @return {@link QuickHelper}
     */
    public QuickHelper setVerticalScrollBarEnabled(final boolean verticalScrollBarEnabled) {
        ViewUtils.setVerticalScrollBarEnabled(targetView(), verticalScrollBarEnabled);
        return this;
    }

    // =

    /**
     * 设置 View 滚动效应
     * @param isScrollContainer 是否需要滚动效应
     * @return {@link QuickHelper}
     */
    public QuickHelper setScrollContainer(final boolean isScrollContainer) {
        ViewUtils.setScrollContainer(targetView(), isScrollContainer);
        return this;
    }

    /**
     * 设置下一个获取焦点的 View id
     * @param nextFocusForwardId 下一个获取焦点的 View id
     * @return {@link QuickHelper}
     */
    public QuickHelper setNextFocusForwardId(@IdRes final int nextFocusForwardId) {
        ViewUtils.setNextFocusForwardId(targetView(), nextFocusForwardId);
        return this;
    }

    /**
     * 设置向下移动焦点时, 下一个获取焦点的 View id
     * @param nextFocusDownId 下一个获取焦点的 View id
     * @return {@link QuickHelper}
     */
    public QuickHelper setNextFocusDownId(@IdRes final int nextFocusDownId) {
        ViewUtils.setNextFocusDownId(targetView(), nextFocusDownId);
        return this;
    }

    /**
     * 设置向左移动焦点时, 下一个获取焦点的 View id
     * @param nextFocusLeftId 下一个获取焦点的 View id
     * @return {@link QuickHelper}
     */
    public QuickHelper setNextFocusLeftId(@IdRes final int nextFocusLeftId) {
        ViewUtils.setNextFocusLeftId(targetView(), nextFocusLeftId);
        return this;
    }

    /**
     * 设置向右移动焦点时, 下一个获取焦点的 View id
     * @param nextFocusRightId 下一个获取焦点的 View id
     * @return {@link QuickHelper}
     */
    public QuickHelper setNextFocusRightId(@IdRes final int nextFocusRightId) {
        ViewUtils.setNextFocusRightId(targetView(), nextFocusRightId);
        return this;
    }

    /**
     * 设置向上移动焦点时, 下一个获取焦点的 View id
     * @param nextFocusUpId 下一个获取焦点的 View id
     * @return {@link QuickHelper}
     */
    public QuickHelper setNextFocusUpId(@IdRes final int nextFocusUpId) {
        ViewUtils.setNextFocusUpId(targetView(), nextFocusUpId);
        return this;
    }

    /**
     * 设置 View 旋转度数
     * @param rotation 旋转度数
     * @return {@link QuickHelper}
     */
    public QuickHelper setRotation(final float rotation) {
        ViewUtils.setRotation(targetView(), rotation);
        return this;
    }

    /**
     * 设置 View 水平旋转度数
     * @param rotationX 水平旋转度数
     * @return {@link QuickHelper}
     */
    public QuickHelper setRotationX(final float rotationX) {
        ViewUtils.setRotationX(targetView(), rotationX);
        return this;
    }

    /**
     * 设置 View 竖直旋转度数
     * @param rotationY 竖直旋转度数
     * @return {@link QuickHelper}
     */
    public QuickHelper setRotationY(final float rotationY) {
        ViewUtils.setRotationY(targetView(), rotationY);
        return this;
    }

    /**
     * 设置 View 水平方向缩放比例
     * @param scaleX 水平方向缩放比例
     * @return {@link QuickHelper}
     */
    public QuickHelper setScaleX(final float scaleX) {
        ViewUtils.setScaleX(targetView(), scaleX);
        return this;
    }

    /**
     * 设置 View 竖直方向缩放比例
     * @param scaleY 竖直方向缩放比例
     * @return {@link QuickHelper}
     */
    public QuickHelper setScaleY(final float scaleY) {
        ViewUtils.setScaleY(targetView(), scaleY);
        return this;
    }

    /**
     * 设置文本的显示方式
     * @param textAlignment 文本的显示方式
     * @return {@link QuickHelper}
     */
    public QuickHelper setTextAlignment(final int textAlignment) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            ViewUtils.setTextAlignment(targetView(), textAlignment);
        }
        return this;
    }

    /**
     * 设置文本的显示方向
     * @param textDirection 文本的显示方向
     * @return {@link QuickHelper}
     */
    public QuickHelper setTextDirection(final int textDirection) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            ViewUtils.setTextDirection(targetView(), textDirection);
        }
        return this;
    }

    /**
     * 设置水平方向偏转量
     * @param pivotX 水平方向偏转量
     * @return {@link QuickHelper}
     */
    public QuickHelper setPivotX(final float pivotX) {
        ViewUtils.setPivotX(targetView(), pivotX);
        return this;
    }

    /**
     * 设置竖直方向偏转量
     * @param pivotY 竖直方向偏转量
     * @return {@link QuickHelper}
     */
    public QuickHelper setPivotY(final float pivotY) {
        ViewUtils.setPivotY(targetView(), pivotY);
        return this;
    }

    /**
     * 设置水平方向的移动距离
     * @param translationX 水平方向的移动距离
     * @return {@link QuickHelper}
     */
    public QuickHelper setTranslationX(final float translationX) {
        ViewUtils.setTranslationX(targetView(), translationX);
        return this;
    }

    /**
     * 设置竖直方向的移动距离
     * @param translationY 竖直方向的移动距离
     * @return {@link QuickHelper}
     */
    public QuickHelper setTranslationY(final float translationY) {
        ViewUtils.setTranslationY(targetView(), translationY);
        return this;
    }

    /**
     * 设置 View 硬件加速类型
     * @param layerType 硬件加速类型
     * @param paint     {@link Paint}
     * @return {@link QuickHelper}
     */
    public QuickHelper setLayerType(
            final int layerType,
            final Paint paint
    ) {
        ViewUtils.setLayerType(targetView(), layerType, paint);
        return this;
    }

    // =

    /**
     * 请求重新对 View 布局
     * @return {@link QuickHelper}
     */
    public QuickHelper requestLayout() {
        ViewUtils.requestLayout(targetView());
        return this;
    }

    /**
     * View 请求获取焦点
     * @return {@link QuickHelper}
     */
    public QuickHelper requestFocus() {
        ViewUtils.requestFocus(targetView());
        return this;
    }

    /**
     * View 清除焦点
     * @return {@link QuickHelper}
     */
    public QuickHelper clearFocus() {
        ViewUtils.clearFocus(targetView());
        return this;
    }

    /**
     * View 请求更新
     * @param allParent 是否全部父布局 View 都请求
     * @return {@link QuickHelper}
     */
    public QuickHelper requestLayoutParent(final boolean allParent) {
        ViewUtils.requestLayoutParent(targetView(), allParent);
        return this;
    }

    // =

    /**
     * 设置是否限制子 View 在其边界内绘制
     * @param clipChildren {@code true} yes, {@code false} no
     * @return {@link QuickHelper}
     */
    public QuickHelper setClipChildren(final boolean clipChildren) {
        ViewUtils.setClipChildren(targetViewGroup(), clipChildren);
        return this;
    }

    /**
     * 设置 View LayoutParams
     * @param params LayoutParams
     * @return {@link QuickHelper}
     */
    public QuickHelper setLayoutParams(final ViewGroup.LayoutParams params) {
        ViewUtils.setLayoutParams(targetView(), params);
        return this;
    }

    /**
     * 设置 View 是否在触摸模式下获得焦点
     * @param focusableInTouchMode {@code true} 可获取, {@code false} 不可获取
     * @return {@link QuickHelper}
     */
    public QuickHelper setFocusableInTouchMode(final boolean focusableInTouchMode) {
        ViewUtils.setFocusableInTouchMode(focusableInTouchMode, targetView());
        return this;
    }

    /**
     * 设置 View 是否可以获取焦点
     * @param focusable {@code true} 可获取, {@code false} 不可获取
     * @return {@link QuickHelper}
     */
    public QuickHelper setFocusable(final boolean focusable) {
        ViewUtils.setFocusable(focusable, targetView());
        return this;
    }

    /**
     * 切换获取焦点状态
     * @return {@link QuickHelper}
     */
    public QuickHelper toggleFocusable() {
        ViewUtils.toggleFocusable(targetView());
        return this;
    }

    /**
     * 设置 View 是否选中
     * @param selected {@code true} 选中, {@code false} 非选中
     * @return {@code true} 选中, {@code false} 非选中
     */
    public QuickHelper setSelected(final boolean selected) {
        ViewUtils.setSelected(selected, targetView());
        return this;
    }

    /**
     * 切换选中状态
     * @return {@link QuickHelper}
     */
    public QuickHelper toggleSelected() {
        ViewUtils.toggleSelected(targetView());
        return this;
    }

    /**
     * 设置 View 是否启用
     * @param enabled {@code true} 启用, {@code false} 禁用
     * @return {@link QuickHelper}
     */
    public QuickHelper setEnabled(final boolean enabled) {
        ViewUtils.setEnabled(enabled, targetView());
        return this;
    }

    /**
     * 切换 View 是否启用状态
     * @return {@link QuickHelper}
     */
    public QuickHelper toggleEnabled() {
        ViewUtils.toggleEnabled(targetView());
        return this;
    }

    /**
     * 设置 View 是否可以点击
     * @param clickable {@code true} 可点击, {@code false} 不可点击
     * @return {@link QuickHelper}
     */
    public QuickHelper setClickable(final boolean clickable) {
        ViewUtils.setClickable(clickable, targetView());
        return this;
    }

    /**
     * 切换 View 是否可以点击状态
     * @return {@link QuickHelper}
     */
    public QuickHelper toggleClickable() {
        ViewUtils.toggleClickable(targetView());
        return this;
    }

    /**
     * 设置 View 是否可以长按
     * @param longClickable {@code true} 可长按, {@code false} 不可长按
     * @return {@link QuickHelper}
     */
    public QuickHelper setLongClickable(final boolean longClickable) {
        ViewUtils.setLongClickable(longClickable, targetView());
        return this;
    }

    /**
     * 切换 View 是否可以长按状态
     * @return {@link QuickHelper}
     */
    public QuickHelper toggleLongClickable() {
        ViewUtils.toggleLongClickable(targetView());
        return this;
    }

    /**
     * 设置 View 显示的状态
     * @param isVisibility {@code true} View.VISIBLE, {@code false} View.GONE
     * @return {@link QuickHelper}
     */
    public QuickHelper setVisibility(final boolean isVisibility) {
        ViewUtils.setVisibility(isVisibility, targetView());
        return this;
    }

    /**
     * 设置 View 显示的状态
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @return {@link QuickHelper}
     */
    public QuickHelper setVisibility(final int isVisibility) {
        ViewUtils.setVisibility(isVisibility, targetView());
        return this;
    }

    /**
     * 切换 View 显示的状态
     * @param views View[]
     * @return {@link QuickHelper}
     */
    public QuickHelper toggleVisibilitys(final View... views) {
        ViewUtils.toggleVisibilitys(targetView(), views);
        return this;
    }

    /**
     * 反转 View 显示的状态
     * @param state {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param views View[]
     * @return {@link QuickHelper}
     */
    public QuickHelper reverseVisibilitys(
            final int state,
            final View... views
    ) {
        ViewUtils.reverseVisibilitys(state, targetView(), views);
        return this;
    }

    /**
     * 反转 View 显示的状态
     * @param isVisibility {@code true} View.VISIBLE, {@code false} View.GONE
     * @param views        View[]
     * @return {@link QuickHelper}
     */
    public QuickHelper reverseVisibilitys(
            final boolean isVisibility,
            final View... views
    ) {
        ViewUtils.reverseVisibilitys(isVisibility, targetView(), views);
        return this;
    }

    /**
     * 把自身从父 View 中移除
     * @return {@link QuickHelper}
     */
    public QuickHelper removeSelfFromParent() {
        ViewUtils.removeSelfFromParent(targetView());
        return this;
    }

    /**
     * 设置 View Layout Gravity
     * @param gravity Gravity
     * @return {@link QuickHelper}
     */
    public QuickHelper setLayoutGravity(final int gravity) {
        ViewUtils.setLayoutGravity(targetView(), gravity);
        return this;
    }

    /**
     * 设置 View Layout Gravity
     * @param gravity      Gravity
     * @param isReflection 是否使用反射
     * @return {@link QuickHelper}
     */
    public QuickHelper setLayoutGravity(
            final int gravity,
            final boolean isReflection
    ) {
        ViewUtils.setLayoutGravity(targetView(), gravity, isReflection);
        return this;
    }

    /**
     * 设置 View Left Margin
     * @param leftMargin Left Margin
     * @return {@link QuickHelper}
     */
    public QuickHelper setMarginLeft(final int leftMargin) {
        ViewUtils.setMarginLeft(targetView(), leftMargin);
        return this;
    }

    /**
     * 设置 View Left Margin
     * @param leftMargin Left Margin
     * @param reset      是否重置清空其他 margin
     * @return {@link QuickHelper}
     */
    public QuickHelper setMarginLeft(
            final int leftMargin,
            final boolean reset
    ) {
        ViewUtils.setMarginLeft(targetView(), leftMargin, reset);
        return this;
    }

    /**
     * 设置 View Top Margin
     * @param topMargin Top Margin
     * @return {@link QuickHelper}
     */
    public QuickHelper setMarginTop(final int topMargin) {
        ViewUtils.setMarginTop(targetView(), topMargin);
        return this;
    }

    /**
     * 设置 View Top Margin
     * @param topMargin Top Margin
     * @param reset     是否重置清空其他 margin
     * @return {@link QuickHelper}
     */
    public QuickHelper setMarginTop(
            final int topMargin,
            final boolean reset
    ) {
        ViewUtils.setMarginTop(targetView(), topMargin, reset);
        return this;
    }

    /**
     * 设置 View Right Margin
     * @param rightMargin Right Margin
     * @return {@link QuickHelper}
     */
    public QuickHelper setMarginRight(final int rightMargin) {
        ViewUtils.setMarginRight(targetView(), rightMargin);
        return this;
    }

    /**
     * 设置 View Right Margin
     * @param rightMargin Right Margin
     * @param reset       是否重置清空其他 margin
     * @return {@link QuickHelper}
     */
    public QuickHelper setMarginRight(
            final int rightMargin,
            final boolean reset
    ) {
        ViewUtils.setMarginRight(targetView(), rightMargin, reset);
        return this;
    }

    /**
     * 设置 View Bottom Margin
     * @param bottomMargin Bottom Margin
     * @return {@link QuickHelper}
     */
    public QuickHelper setMarginBottom(final int bottomMargin) {
        ViewUtils.setMarginBottom(targetView(), bottomMargin);
        return this;
    }

    /**
     * 设置 View Bottom Margin
     * @param bottomMargin Bottom Margin
     * @param reset        是否重置清空其他 margin
     * @return {@link QuickHelper}
     */
    public QuickHelper setMarginBottom(
            final int bottomMargin,
            final boolean reset
    ) {
        ViewUtils.setMarginBottom(targetView(), bottomMargin, reset);
        return this;
    }

    /**
     * 设置 Margin 边距
     * @param leftRight Left and Right Margin
     * @param topBottom Top and bottom Margin
     * @return {@link QuickHelper}
     */
    public QuickHelper setMargin(
            final int leftRight,
            final int topBottom
    ) {
        ViewUtils.setMargin(targetView(), leftRight, topBottom);
        return this;
    }

    /**
     * 设置 Margin 边距
     * @param margin Margin
     * @return {@link QuickHelper}
     */
    public QuickHelper setMargin(final int margin) {
        ViewUtils.setMargin(targetView(), margin);
        return this;
    }

    /**
     * 设置 Margin 边距
     * @param left   Left Margin
     * @param top    Top Margin
     * @param right  Right Margin
     * @param bottom Bottom Margin
     * @return {@link QuickHelper}
     */
    public QuickHelper setMargin(
            final int left,
            final int top,
            final int right,
            final int bottom
    ) {
        ViewUtils.setMargin(targetView(), left, top, right, bottom);
        return this;
    }

    /**
     * 设置 View Left Padding
     * @param leftPadding Left Padding
     * @return {@link QuickHelper}
     */
    public QuickHelper setPaddingLeft(final int leftPadding) {
        ViewUtils.setPaddingLeft(targetView(), leftPadding);
        return this;
    }

    /**
     * 设置 View Left Padding
     * @param leftPadding Left Padding
     * @param reset       是否重置清空其他 Padding
     * @return {@link QuickHelper}
     */
    public QuickHelper setPaddingLeft(
            final int leftPadding,
            final boolean reset
    ) {
        ViewUtils.setPaddingLeft(targetView(), leftPadding, reset);
        return this;
    }

    /**
     * 设置 View Top Padding
     * @param topPadding Top Padding
     * @return {@link QuickHelper}
     */
    public QuickHelper setPaddingTop(final int topPadding) {
        ViewUtils.setPaddingTop(targetView(), topPadding);
        return this;
    }

    /**
     * 设置 View Top Padding
     * @param topPadding Top Padding
     * @param reset      是否重置清空其他 Padding
     * @return {@link QuickHelper}
     */
    public QuickHelper setPaddingTop(
            final int topPadding,
            final boolean reset
    ) {
        ViewUtils.setPaddingTop(targetView(), topPadding, reset);
        return this;
    }

    /**
     * 设置 View Right Padding
     * @param rightPadding Right Padding
     * @return {@link QuickHelper}
     */
    public QuickHelper setPaddingRight(final int rightPadding) {
        ViewUtils.setPaddingRight(targetView(), rightPadding);
        return this;
    }

    /**
     * 设置 View Right Padding
     * @param rightPadding Right Padding
     * @param reset        是否重置清空其他 Padding
     * @return {@link QuickHelper}
     */
    public QuickHelper setPaddingRight(
            final int rightPadding,
            final boolean reset
    ) {
        ViewUtils.setPaddingRight(targetView(), rightPadding, reset);
        return this;
    }

    /**
     * 设置 View Bottom Padding
     * @param bottomPadding Bottom Padding
     * @return {@link QuickHelper}
     */
    public QuickHelper setPaddingBottom(final int bottomPadding) {
        ViewUtils.setPaddingBottom(targetView(), bottomPadding);
        return this;
    }

    /**
     * 设置 View Bottom Padding
     * @param bottomPadding Bottom Padding
     * @param reset         是否重置清空其他 Padding
     * @return {@link QuickHelper}
     */
    public QuickHelper setPaddingBottom(
            final int bottomPadding,
            final boolean reset
    ) {
        ViewUtils.setPaddingBottom(targetView(), bottomPadding, reset);
        return this;
    }

    /**
     * 设置 Padding 边距
     * @param leftRight Left and Right Padding
     * @param topBottom Top and bottom Padding
     * @return {@link QuickHelper}
     */
    public QuickHelper setPadding(
            final int leftRight,
            final int topBottom
    ) {
        ViewUtils.setPadding(targetView(), leftRight, topBottom);
        return this;
    }

    /**
     * 设置 Padding 边距
     * @param padding Padding
     * @return {@link QuickHelper}
     */
    public QuickHelper setPadding(final int padding) {
        ViewUtils.setPadding(targetView(), padding);
        return this;
    }

    /**
     * 设置 Padding 边距
     * @param left   Left Padding
     * @param top    Top Padding
     * @param right  Right Padding
     * @param bottom Bottom Padding
     * @return {@link QuickHelper}
     */
    public QuickHelper setPadding(
            final int left,
            final int top,
            final int right,
            final int bottom
    ) {
        ViewUtils.setPadding(targetView(), left, top, right, bottom);
        return this;
    }

    /**
     * 设置 CompoundDrawables Padding
     * @param padding CompoundDrawables Padding
     * @return {@link QuickHelper}
     */
    public QuickHelper setCompoundDrawablePadding(final int padding) {
        TextViewUtils.setCompoundDrawablePadding(targetTextView(), padding);
        return this;
    }

    /**
     * 设置 Left CompoundDrawables
     * @param left left Drawable
     * @return {@link QuickHelper}
     */
    public QuickHelper setCompoundDrawablesByLeft(final Drawable left) {
        TextViewUtils.setCompoundDrawablesByLeft(targetTextView(), left);
        return this;
    }

    /**
     * 设置 Top CompoundDrawables
     * @param top top Drawable
     * @return {@link QuickHelper}
     */
    public QuickHelper setCompoundDrawablesByTop(final Drawable top) {
        TextViewUtils.setCompoundDrawablesByTop(targetTextView(), top);
        return this;
    }

    /**
     * 设置 Right CompoundDrawables
     * @param right right Drawable
     * @return {@link QuickHelper}
     */
    public QuickHelper setCompoundDrawablesByRight(final Drawable right) {
        TextViewUtils.setCompoundDrawablesByRight(targetTextView(), right);
        return this;
    }

    /**
     * 设置 Bottom CompoundDrawables
     * @param bottom bottom Drawable
     * @return {@link QuickHelper}
     */
    public QuickHelper setCompoundDrawablesByBottom(final Drawable bottom) {
        TextViewUtils.setCompoundDrawablesByBottom(targetTextView(), bottom);
        return this;
    }

    /**
     * 设置 CompoundDrawables
     * <pre>
     *     CompoundDrawable 的大小控制是通过 drawable.setBounds() 控制
     *     需要先设置 Drawable 的 setBounds
     *     {@link dev.utils.app.image.ImageUtils#setBounds}
     * </pre>
     * @param left   left Drawable
     * @param top    top Drawable
     * @param right  right Drawable
     * @param bottom bottom Drawable
     * @return {@link QuickHelper}
     */
    public QuickHelper setCompoundDrawables(
            final Drawable left,
            final Drawable top,
            final Drawable right,
            final Drawable bottom
    ) {
        TextViewUtils.setCompoundDrawables(targetTextView(), left, top, right, bottom);
        return this;
    }

    /**
     * 设置 Left CompoundDrawables ( 按照原有比例大小显示图片 )
     * @param left left Drawable
     * @return {@link QuickHelper}
     */
    public QuickHelper setCompoundDrawablesWithIntrinsicBoundsByLeft(final Drawable left) {
        TextViewUtils.setCompoundDrawablesWithIntrinsicBoundsByLeft(targetTextView(), left);
        return this;
    }

    /**
     * 设置 Top CompoundDrawables ( 按照原有比例大小显示图片 )
     * @param top top Drawable
     * @return {@link QuickHelper}
     */
    public QuickHelper setCompoundDrawablesWithIntrinsicBoundsByTop(final Drawable top) {
        TextViewUtils.setCompoundDrawablesWithIntrinsicBoundsByTop(targetTextView(), top);
        return this;
    }

    /**
     * 设置 Right CompoundDrawables ( 按照原有比例大小显示图片 )
     * @param right right Drawable
     * @return {@link QuickHelper}
     */
    public QuickHelper setCompoundDrawablesWithIntrinsicBoundsByRight(final Drawable right) {
        TextViewUtils.setCompoundDrawablesWithIntrinsicBoundsByRight(targetTextView(), right);
        return this;
    }

    /**
     * 设置 Bottom CompoundDrawables ( 按照原有比例大小显示图片 )
     * @param bottom bottom Drawable
     * @return {@link QuickHelper}
     */
    public QuickHelper setCompoundDrawablesWithIntrinsicBoundsByBottom(final Drawable bottom) {
        TextViewUtils.setCompoundDrawablesWithIntrinsicBoundsByBottom(targetTextView(), bottom);
        return this;
    }

    /**
     * 设置 CompoundDrawables ( 按照原有比例大小显示图片 )
     * @param left   left Drawable
     * @param top    top Drawable
     * @param right  right Drawable
     * @param bottom bottom Drawable
     * @return {@link QuickHelper}
     */
    public QuickHelper setCompoundDrawablesWithIntrinsicBounds(
            final Drawable left,
            final Drawable top,
            final Drawable right,
            final Drawable bottom
    ) {
        TextViewUtils.setCompoundDrawablesWithIntrinsicBounds(targetTextView(), left, top, right, bottom);
        return this;
    }

    // ==================
    // = RelativeLayout =
    // ==================

    /**
     * 设置 RelativeLayout View 布局规则
     * @param verb 布局位置
     * @return {@link QuickHelper}
     */
    public QuickHelper addRule(final int verb) {
        ViewUtils.addRule(targetView(), verb);
        return this;
    }

    /**
     * 设置 RelativeLayout View 布局规则
     * @param verb    布局位置
     * @param subject 关联 View id
     * @return {@link QuickHelper}
     */
    public QuickHelper addRule(
            final int verb,
            final int subject
    ) {
        ViewUtils.addRule(targetView(), verb, subject);
        return this;
    }

    /**
     * 移除 RelativeLayout View 布局规则
     * @param verb 布局位置
     * @return {@link QuickHelper}
     */
    public QuickHelper removeRule(final int verb) {
        ViewUtils.removeRule(targetView(), verb);
        return this;
    }

    // =============
    // = Animation =
    // =============

    /**
     * 设置动画
     * @param animation {@link Animation}
     * @return {@link QuickHelper}
     */
    public QuickHelper setAnimation(final Animation animation) {
        ViewUtils.setAnimation(targetView(), animation);
        return this;
    }

    /**
     * 清空动画
     * @return {@link QuickHelper}
     */
    public QuickHelper clearAnimation() {
        ViewUtils.clearAnimation(targetView());
        return this;
    }

    /**
     * 启动动画
     * @param animation {@link Animation}
     * @return {@link QuickHelper}
     */
    public QuickHelper startAnimation(final Animation animation) {
        ViewUtils.startAnimation(targetView(), animation);
        return this;
    }

    /**
     * 取消动画
     * @return {@link QuickHelper}
     */
    public QuickHelper cancelAnimation() {
        ViewUtils.cancelAnimation(targetView());
        return this;
    }

    // ============
    // = Listener =
    // ============

    /**
     * 设置点击事件
     * @param onClickListener {@link View.OnClickListener}
     * @return {@link QuickHelper}
     */
    public QuickHelper setOnClicks(final View.OnClickListener onClickListener) {
        ListenerUtils.setOnClicks(onClickListener, targetView());
        return this;
    }

    /**
     * 设置长按事件
     * @param onLongClickListener {@link View.OnLongClickListener}
     * @return {@link QuickHelper}
     */
    public QuickHelper setOnLongClicks(final View.OnLongClickListener onLongClickListener) {
        ListenerUtils.setOnLongClicks(onLongClickListener, targetView());
        return this;
    }

    /**
     * 设置触摸事件
     * @param onTouchListener {@link View.OnTouchListener}
     * @return {@link QuickHelper}
     */
    public QuickHelper setOnTouchs(final View.OnTouchListener onTouchListener) {
        ListenerUtils.setOnTouchs(onTouchListener, targetView());
        return this;
    }

    /**
     * 增加控件的触摸范围, 最大范围只能是父布局所包含的的区域
     * @param range 点击范围
     * @return {@link QuickHelper}
     */
    public QuickHelper addTouchArea(final int range) {
        ClickUtils.addTouchArea(targetView(), range);
        return this;
    }

    /**
     * 增加控件的触摸范围, 最大范围只能是父布局所包含的的区域
     * @param top    top range
     * @param bottom bottom range
     * @param left   left range
     * @param right  right range
     * @return {@link QuickHelper}
     */
    public QuickHelper addTouchArea(
            final int top,
            final int bottom,
            final int left,
            final int right
    ) {
        ClickUtils.addTouchArea(targetView(), top, bottom, left, right);
        return this;
    }

    // =================
    // = ListViewUtils =
    // =================

    /**
     * 滑动到指定索引 ( 有滚动过程 )
     * @param position 索引
     * @return {@link QuickHelper}
     */
    public QuickHelper smoothScrollToPosition(final int position) {
        ListViewUtils.smoothScrollToPosition(targetView(), position);
        return this;
    }

    /**
     * 滑动到指定索引 ( 无滚动过程 )
     * @param position 索引
     * @return {@link QuickHelper}
     */
    public QuickHelper scrollToPosition(final int position) {
        ListViewUtils.scrollToPosition(targetView(), position);
        return this;
    }

    // =============
    // = 滑动到顶部 =
    // =============

    /**
     * 滑动到顶部 ( 有滚动过程 )
     * @return {@link QuickHelper}
     */
    public QuickHelper smoothScrollToTop() {
        ListViewUtils.smoothScrollToTop(targetView());
        return this;
    }

    /**
     * 滑动到顶部 ( 无滚动过程 )
     * @return {@link QuickHelper}
     */
    public QuickHelper scrollToTop() {
        ListViewUtils.scrollToTop(targetView());
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
     * @return {@link QuickHelper}
     */
    public QuickHelper smoothScrollToBottom() {
        ListViewUtils.smoothScrollToBottom(targetView());
        return this;
    }

    /**
     * 滑动到底部 ( 无滚动过程 )
     * <pre>
     *     如果未到达底部 ( position 可以再加上 scrollBy 搭配到底部 )
     *     scrollToBottom(view)
     *     scrollBy(view, 0, Integer.MAX_VALUE);
     * </pre>
     * @return {@link QuickHelper}
     */
    public QuickHelper scrollToBottom() {
        ListViewUtils.scrollToBottom(targetView());
        return this;
    }

    // ==============
    // = ScrollView =
    // ==============

    /**
     * 滚动到指定位置 ( 有滚动过程, 相对于初始位置移动 )
     * @param x X 轴开始坐标
     * @param y Y 轴开始坐标
     * @return {@link QuickHelper}
     */
    public QuickHelper smoothScrollTo(
            final int x,
            final int y
    ) {
        ListViewUtils.smoothScrollTo(targetView(), x, y);
        return this;
    }

    /**
     * 滚动到指定位置 ( 有滚动过程, 相对于上次移动的最后位置移动 )
     * @param x X 轴开始坐标
     * @param y Y 轴开始坐标
     * @return {@link QuickHelper}
     */
    public QuickHelper smoothScrollBy(
            final int x,
            final int y
    ) {
        ListViewUtils.smoothScrollBy(targetView(), x, y);
        return this;
    }

    /**
     * 滚动方向 ( 有滚动过程 )
     * @param direction 滚动方向 如: View.FOCUS_UP、View.FOCUS_DOWN
     * @return {@link QuickHelper}
     */
    public QuickHelper fullScroll(final int direction) {
        ListViewUtils.fullScroll(targetView(), direction);
        return this;
    }

    // =============
    // = DevHelper =
    // =============

    // =================
    // = KeyBoardUtils =
    // =================

    // =============
    // = 打开软键盘 =
    // =============

    /**
     * 打开软键盘
     * @return {@link QuickHelper}
     */
    public QuickHelper openKeyboard() {
        KeyBoardUtils.openKeyboard(targetEditText());
        return this;
    }

    /**
     * 延时打开软键盘
     * @return {@link QuickHelper}
     */
    public QuickHelper openKeyboardDelay() {
        KeyBoardUtils.openKeyboardDelay(targetEditText());
        return this;
    }

    /**
     * 延时打开软键盘
     * @param delayMillis 延迟时间 ( 毫秒 )
     * @return {@link QuickHelper}
     */
    public QuickHelper openKeyboardDelay(final int delayMillis) {
        KeyBoardUtils.openKeyboardDelay(targetEditText(), delayMillis);
        return this;
    }

    // =============
    // = 关闭软键盘 =
    // =============

    /**
     * 关闭软键盘
     * @return {@link QuickHelper}
     */
    public QuickHelper closeKeyboard() {
        KeyBoardUtils.closeKeyboard(targetEditText());
        return this;
    }

    /**
     * 关闭软键盘
     * @param dialog {@link Dialog}
     * @return {@link QuickHelper}
     */
    public QuickHelper closeKeyBoardSpecial(final Dialog dialog) {
        KeyBoardUtils.closeKeyBoardSpecial(targetEditText(), dialog);
        return this;
    }

    /**
     * 延时关闭软键盘
     * @param dialog {@link Dialog}
     * @return {@link QuickHelper}
     */
    public QuickHelper closeKeyBoardSpecialDelay(final Dialog dialog) {
        KeyBoardUtils.closeKeyBoardSpecialDelay(targetEditText(), dialog);
        return this;
    }

    /**
     * 延时关闭软键盘
     * @param dialog      {@link Dialog}
     * @param delayMillis 延迟时间 ( 毫秒 )
     * @return {@link QuickHelper}
     */
    public QuickHelper closeKeyBoardSpecialDelay(
            final Dialog dialog,
            final int delayMillis
    ) {
        KeyBoardUtils.closeKeyBoardSpecialDelay(targetEditText(), dialog, delayMillis);
        return this;
    }

    // =============
    // = SizeUtils =
    // =============

    /**
     * 在 onCreate 中获取视图的尺寸 ( 需回调 onGetSizeListener 接口, 在 onGetSize 中获取 View 宽高 )
     * @param listener {@link SizeUtils.OnGetSizeListener}
     * @return {@link QuickHelper}
     */
    public QuickHelper forceGetViewSize(final SizeUtils.OnGetSizeListener listener) {
        SizeUtils.forceGetViewSize(targetView(), listener);
        return this;
    }
}