package dev;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.text.method.KeyListener;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import dev.utils.app.EditTextUtils;
import dev.utils.app.ImageViewUtils;
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

    // ========
    // = Text =
    // ========

    /**
     * 设置文本
     * @param view {@link TextView}
     * @param text TextView text
     * @return {@link ViewHelper}
     */
    public ViewHelper setText(final View view, final String text) {
        TextViewUtils.setText(view, text);
        return this;
    }

    /**
     * 设置多个 TextView 文本
     * @param text  TextView text
     * @param views View(TextView)[]
     * @return {@link ViewHelper}
     */
    public ViewHelper setTexts(final String text, final View... views) {
        TextViewUtils.setTexts(text, views);
        return this;
    }

    /**
     * 设置 Html 内容
     * @param view    {@link TextView}
     * @param content Html content
     * @return {@link ViewHelper}
     */
    public ViewHelper setHtmlText(final View view, final String content) {
        TextViewUtils.setHtmlText(view, content);
        return this;
    }

    /**
     * 设置多个 TextView Html 内容
     * @param content Html content
     * @param views   View(TextView)[]
     * @return {@link ViewHelper}
     */
    public ViewHelper setHtmlTexts(final String content, final View... views) {
        TextViewUtils.setHtmlTexts(content, views);
        return this;
    }

    /**
     * 设置字体颜色
     * @param view  {@link TextView}
     * @param color R.color.id
     * @return {@link ViewHelper}
     */
    public ViewHelper setTextColor(final View view, @ColorInt final int color) {
        TextViewUtils.setTextColor(view, color);
        return this;
    }

    /**
     * 设置多个 TextView 字体颜色
     * @param color R.color.id
     * @param views View(TextView)[]
     * @return {@link ViewHelper}
     */
    public ViewHelper setTextColors(@ColorInt final int color, final View... views) {
        TextViewUtils.setTextColors(color, views);
        return this;
    }

    /**
     * 设置字体
     * @param view     {@link TextView}
     * @param typeface {@link Typeface} 字体样式
     * @return {@link ViewHelper}
     */
    public ViewHelper setTypeface(final View view, final Typeface typeface) {
        TextViewUtils.setTypeface(view, typeface);
        return this;
    }

    /**
     * 设置字体大小 - px 像素
     * @param view {@link TextView}
     * @param size 字体大小
     * @return {@link ViewHelper}
     */
    public ViewHelper setTextSizeByPx(final View view, final float size) {
        TextViewUtils.setTextSizeByPx(view, size);
        return this;
    }

    /**
     * 设置字体大小 - sp 缩放像素
     * @param view {@link TextView}
     * @param size 字体大小
     * @return {@link ViewHelper}
     */
    public ViewHelper setTextSizeBySp(final View view, final float size) {
        TextViewUtils.setTextSizeBySp(view, size);
        return this;
    }

    /**
     * 设置字体大小 - dp 与设备无关的像素
     * @param view {@link TextView}
     * @param size 字体大小
     * @return {@link ViewHelper}
     */
    public ViewHelper setTextSizeByDp(final View view, final float size) {
        TextViewUtils.setTextSizeByDp(view, size);
        return this;
    }

    /**
     * 设置字体大小 - inches 英寸
     * @param view {@link TextView}
     * @param size 字体大小
     * @return {@link ViewHelper}
     */
    public ViewHelper setTextSizeByIn(final View view, final float size) {
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
    public ViewHelper setTextSize(final View view, final int unit, final float size) {
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
    public ViewHelper setTextSizes(final View[] views, final int unit, final float size) {
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
     * 设置 TextView 是否加粗
     * @param view   {@link TextView}
     * @param isBold {@code true} yes, {@code false} no
     * @return {@link ViewHelper}
     */
    public ViewHelper setBold(final View view, final boolean isBold) {
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
    public ViewHelper setBold(final View view, final Typeface typeface, final boolean isBold) {
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
    public ViewHelper setUnderlineText(final View view, final boolean isAntiAlias) {
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
    public ViewHelper setStrikeThruText(final View view, final boolean isAntiAlias) {
        TextViewUtils.setStrikeThruText(view, isAntiAlias);
        return this;
    }

    /**
     * 设置文字水平间距
     * @param view          {@link TextView}
     * @param letterSpacing 文字水平间距值
     * @return {@link ViewHelper}
     */
    public ViewHelper setLetterSpacing(final View view, final float letterSpacing) {
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
    public ViewHelper setLineSpacing(final View view, final float lineSpacing) {
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
    public ViewHelper setLineSpacingAndMultiplier(final View view, final float lineSpacing, final float multiplier) {
        TextViewUtils.setLineSpacingAndMultiplier(view, lineSpacing, multiplier);
        return this;
    }

    /**
     * 设置字体水平方向的缩放
     * @param view {@link TextView}
     * @param size 缩放比例
     * @return {@link ViewHelper}
     */
    public ViewHelper setTextScaleX(final View view, final float size) {
        TextViewUtils.setTextScaleX(view, size);
        return this;
    }

    /**
     * 设置是否保留字体留白间隙区域
     * @param view       {@link TextView}
     * @param includepad 是否保留字体留白间隙区域
     * @return {@link ViewHelper}
     */
    public ViewHelper setIncludeFontPadding(final View view, final boolean includepad) {
        TextViewUtils.setIncludeFontPadding(view, includepad);
        return this;
    }

    // ============
    // = EditText =
    // ============

    /**
     * 设置内容
     * @param editText {@link EditText}
     * @param content  文本内容
     * @return {@link ViewHelper}
     */
    public ViewHelper setText(final EditText editText, final String content) {
        EditTextUtils.setText(editText, content);
        return this;
    }

    /**
     * 设置内容
     * @param editText {@link EditText}
     * @param content  文本内容
     * @param isSelect 是否设置光标
     * @return {@link ViewHelper}
     */
    public ViewHelper setText(final EditText editText, final String content, final boolean isSelect) {
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
    public ViewHelper insert(final EditText editText, final String content, final boolean isSelect) {
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
    public ViewHelper insert(final EditText editText, final String content, final int start, final boolean isSelect) {
        EditTextUtils.insert(editText, content, start, isSelect);
        return this;
    }

    /**
     * 设置长度限制
     * @param editText  {@link EditText}
     * @param maxLength 长度限制
     * @return {@link ViewHelper}
     */
    public ViewHelper setMaxLength(final EditText editText, final int maxLength) {
        EditTextUtils.setMaxLength(editText, maxLength);
        return this;
    }

    /**
     * 设置长度限制, 并且设置内容
     * @param editText  {@link EditText}
     * @param content   文本内容
     * @param maxLength 长度限制
     * @return {@link ViewHelper}
     */
    public ViewHelper setMaxLengthAndText(final EditText editText, final String content, final int maxLength) {
        EditTextUtils.setMaxLengthAndText(editText, content, maxLength);
        return this;
    }

    /**
     * 设置是否显示光标
     * @param editText {@link EditText}
     * @param visible  是否显示光标
     * @return {@link ViewHelper}
     */
    public ViewHelper setCursorVisible(final EditText editText, final boolean visible) {
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
    public ViewHelper setSelection(final EditText editText, final int index) {
        EditTextUtils.setSelection(editText, index);
        return this;
    }

    /**
     * 设置 KeyListener
     * @param editText    {@link EditText}
     * @param keyListener {@link KeyListener}
     * @return {@link ViewHelper}
     */
    public ViewHelper setKeyListener(final EditText editText, final KeyListener keyListener) {
        EditTextUtils.setKeyListener(editText, keyListener);
        return this;
    }

    /**
     * 设置 KeyListener
     * @param editText {@link EditText}
     * @param accepted 允许输入的内容, 如: 0123456789
     * @return {@link ViewHelper}
     */
    public ViewHelper setKeyListener(final EditText editText, final String accepted) {
        EditTextUtils.setKeyListener(editText, accepted);
        return this;
    }

    /**
     * 设置 KeyListener
     * @param editText {@link EditText}
     * @param accepted 允许输入的内容
     * @return {@link ViewHelper}
     */
    public ViewHelper setKeyListener(final EditText editText, final char[] accepted) {
        EditTextUtils.setKeyListener(editText, accepted);
        return this;
    }

    // =========
    // = Image =
    // =========

    /**
     * 设置背景图片
     * @param view       {@link View}
     * @param background 背景图片
     * @return {@link ViewHelper}
     */
    public ViewHelper setBackground(final View view, final Drawable background) {
        ImageViewUtils.setBackground(view, background);
        return this;
    }

    /**
     * 设置背景颜色
     * @param view  {@link View}
     * @param color 背景颜色
     * @return {@link ViewHelper}
     */
    public ViewHelper setBackgroundColor(final View view, @ColorInt final int color) {
        ImageViewUtils.setBackgroundColor(view, color);
        return this;
    }

    /**
     * 设置背景资源
     * @param view  {@link View}
     * @param resId resource identifier
     * @return {@link ViewHelper}
     */
    public ViewHelper setBackgroundResource(final View view, @DrawableRes final int resId) {
        ImageViewUtils.setBackgroundResource(view, resId);
        return this;
    }

    /**
     * 设置背景着色颜色
     * @param view {@link View}
     * @param tint 着色颜色
     * @return {@link ViewHelper}
     */
    public ViewHelper setBackgroundTintList(final View view, final ColorStateList tint) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ImageViewUtils.setBackgroundTintList(view, tint);
        }
        return this;
    }

    /**
     * 设置背景着色模式
     * @param view     {@link View}
     * @param tintMode 着色模式 {@link PorterDuff.Mode}
     * @return {@link ViewHelper}
     */
    public ViewHelper setBackgroundTintMode(final View view, final PorterDuff.Mode tintMode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ImageViewUtils.setBackgroundTintMode(view, tintMode);
        }
        return this;
    }

    /**
     * 设置前景图片
     * @param view       {@link View}
     * @param foreground 前景图片
     * @return {@link ViewHelper}
     */
    public ViewHelper setForeground(final View view, final Drawable foreground) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ImageViewUtils.setForeground(view, foreground);
        }
        return this;
    }

    /**
     * 设置前景重心
     * @param view    {@link View}
     * @param gravity 重心
     * @return {@link ViewHelper}
     */
    public ViewHelper setForegroundGravity(final View view, final int gravity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ImageViewUtils.setForegroundGravity(view, gravity);
        }
        return this;
    }

    /**
     * 设置前景着色颜色
     * @param view {@link View}
     * @param tint 着色颜色
     * @return {@link ViewHelper}
     */
    public ViewHelper setForegroundTintList(final View view, final ColorStateList tint) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ImageViewUtils.setForegroundTintList(view, tint);
        }
        return this;
    }

    /**
     * 设置前景着色模式
     * @param view     {@link View}
     * @param tintMode 着色模式 {@link PorterDuff.Mode}
     * @return {@link ViewHelper}
     */
    public ViewHelper setForegroundTintMode(final View view, final PorterDuff.Mode tintMode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ImageViewUtils.setForegroundTintMode(view, tintMode);
        }
        return this;
    }

    /**
     * 设置 ImageView Bitmap
     * @param view   {@link View}
     * @param bitmap {@link Bitmap}
     * @return {@link ViewHelper}
     */
    public ViewHelper setImageBitmap(final View view, final Bitmap bitmap) {
        ImageViewUtils.setImageBitmap(view, bitmap);
        return this;
    }

    /**
     * 设置 ImageView Drawable
     * @param view     {@link View}
     * @param drawable {@link Bitmap}
     * @return {@link ViewHelper}
     */
    public ViewHelper setImageDrawable(final View view, final Drawable drawable) {
        ImageViewUtils.setImageDrawable(view, drawable);
        return this;
    }

    /**
     * 设置 ImageView 资源
     * @param view  {@link View}
     * @param resId resource identifier
     * @return {@link ViewHelper}
     */
    public ViewHelper setImageResource(final View view, @DrawableRes final int resId) {
        ImageViewUtils.setImageResource(view, resId);
        return this;
    }

    /**
     * 设置 ImageView Matrix
     * @param view   {@link View}
     * @param matrix {@link Matrix}
     * @return {@link ViewHelper}
     */
    public ViewHelper setImageMatrix(final View view, final Matrix matrix) {
        ImageViewUtils.setImageMatrix(view, matrix);
        return this;
    }

    /**
     * 设置 ImageView 着色颜色
     * @param view {@link View}
     * @param tint 着色颜色
     * @return {@link ViewHelper}
     */
    public ViewHelper setImageTintList(final View view, final ColorStateList tint) {
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
    public ViewHelper setImageTintMode(final View view, final PorterDuff.Mode tintMode) {
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
    public ViewHelper setScaleType(final View view, final ImageView.ScaleType scaleType) {
        ImageViewUtils.setScaleType(view, scaleType);
        return this;
    }

    /**
     * ImageView 着色处理
     * @param imageView {@link ImageView}
     * @param color     颜色值
     * @return {@link ViewHelper}
     */
    public ViewHelper setColorFilter(final ImageView imageView, @ColorInt final int color) {
        ImageViewUtils.setColorFilter(imageView, color);
        return this;
    }

    /**
     * ImageView 着色处理, 并且设置 Drawable
     * @param imageView {@link ImageView}
     * @param drawable  {@link Drawable}
     * @param color     颜色值
     * @return {@link ViewHelper}
     */
    public ViewHelper setColorFilter(final ImageView imageView, final Drawable drawable, @ColorInt final int color) {
        ImageViewUtils.setColorFilter(imageView, drawable, color);
        return this;
    }

    /**
     * ImageView 着色处理
     * @param imageView   {@link ImageView}
     * @param colorFilter 颜色过滤 ( 效果 )
     * @return {@link ViewHelper}
     */
    public ViewHelper setColorFilter(final ImageView imageView, final ColorFilter colorFilter) {
        ImageViewUtils.setColorFilter(imageView, colorFilter);
        return this;
    }

    /**
     * ImageView 着色处理, 并且设置 Drawable
     * @param imageView   {@link ImageView}
     * @param drawable    {@link Drawable}
     * @param colorFilter 颜色过滤 ( 效果 )
     * @return {@link ViewHelper}
     */
    public ViewHelper setColorFilter(final ImageView imageView, final Drawable drawable, final ColorFilter colorFilter) {
        ImageViewUtils.setColorFilter(imageView, drawable, colorFilter);
        return this;
    }

    /**
     * 设置 View 图片资源
     * @param resId resource identifier
     * @param views View[]
     * @return {@link ViewHelper}
     */
    public ViewHelper setBackgroundResources(@DrawableRes final int resId, final View... views) {
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
    public ViewHelper setBackgroundResources(@DrawableRes final int resId, final int isVisibility, final View... views) {
        ImageViewUtils.setBackgroundResources(resId, isVisibility, views);
        return this;
    }

    /**
     * 设置 View 图片资源
     * @param resId resource identifier
     * @param views View[]
     * @return {@link ViewHelper}
     */
    public ViewHelper setImageResources(@DrawableRes final int resId, final View... views) {
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
    public ViewHelper setImageResources(@DrawableRes final int resId, final int isVisibility, final View... views) {
        ImageViewUtils.setImageResources(resId, isVisibility, views);
        return this;
    }

    /**
     * 设置 View Bitmap
     * @param bitmap {@link Bitmap}
     * @param views  View[]
     * @return {@link ViewHelper}
     */
    public ViewHelper setImageBitmaps(final Bitmap bitmap, final View... views) {
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
    public ViewHelper setImageBitmaps(final Bitmap bitmap, final int isVisibility, final View... views) {
        ImageViewUtils.setImageBitmaps(bitmap, isVisibility, views);
        return this;
    }

    /**
     * 设置 View Drawable
     * @param drawable {@link drawable}
     * @param views    View[]
     * @return {@link ViewHelper}
     */
    public ViewHelper setImageDrawables(final Drawable drawable, final View... views) {
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
    public ViewHelper setImageDrawables(final Drawable drawable, final int isVisibility, final View... views) {
        ImageViewUtils.setImageDrawables(drawable, isVisibility, views);
        return this;
    }

    /**
     * 设置 View 缩放模式
     * @param scaleType {@link ImageView.ScaleType}
     * @param views     View[]
     * @return {@link ViewHelper}
     */
    public ViewHelper setScaleTypes(final ImageView.ScaleType scaleType, final View... views) {
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
    public ViewHelper setScaleTypes(final ImageView.ScaleType scaleType, final int isVisibility, final View... views) {
        ImageViewUtils.setScaleTypes(scaleType, isVisibility, views);
        return this;
    }

    // ========
    // = View =
    // ========

    /**
     * 设置 View Tag
     * @param view   View
     * @param object Tag
     * @return {@link ViewHelper}
     */
    public ViewHelper setTag(final View view, final Object object) {
        ViewUtils.setTag(view, object);
        return this;
    }
}
