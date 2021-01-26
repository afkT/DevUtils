package dev.utils.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.AlignmentSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.CharacterStyle;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.LeadingMarginSpan;
import android.text.style.LineHeightSpan;
import android.text.style.MaskFilterSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ReplacementSpan;
import android.text.style.ScaleXSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.text.style.UpdateAppearance;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;
import androidx.annotation.IntDef;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.io.InputStream;
import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;

import dev.DevUtils;
import dev.utils.DevFinal;
import dev.utils.LogPrintUtils;
import dev.utils.common.CloseUtils;

/**
 * detail: SpannableString 工具类
 * @author Blankj
 * @author Ttt
 * <pre>
 *     SpannableStringBuilder 工具类 ( 用于设置文字的前景色、背景色、Typeface、粗体、斜体、字号、超链接、删除线、下划线、上下标等 )
 *     <p></p>
 *     Android 开发 SpannableString 和 SpannableStringBuilder 的使用详解
 *     @see <a href="https://blog.csdn.net/baidu_31956557/article/details/78339071"/>
 *     <p></p>
 *     Android 强大的 SpannableStringBuilder
 *     @see <a href="https://www.jianshu.com/p/f004300c6920"/>
 *     <p></p>
 *     SpannableString 之富文本显示效果
 *     @see <a href="https://www.cnblogs.com/qynprime/p/8026672.html"/>
 *     <p></p>
 *     SpannableString 与 SpannableStringBuilder 区别就比如 String 和 StringBuilder 一样
 *     <p></p>
 *     setSpan() - int flags
 *     <p></p>
 *     Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
 *     前后都不包括 ( 在标志位 [start, end) 前后添加文字, 新添加的文字不会有任何设置的属性 )
 *     <p></p>
 *     Spannable.SPAN_EXCLUSIVE_INCLUSIVE
 *     前面不包括, 后面包括 ( 在标志位 [start, end) 前添加文字, 新添加的文字不会有任何设置的属性, 后边的添加的文字会带有设置的 what 属性 )
 *     <p></p>
 *     Spannable.SPAN_INCLUSIVE_EXCLUSIVE
 *     前面包括, 后面不包括 ( 在标志位 [start, end) 后添加文字, 新添加的文字不会有任何设置的属性, 前边边的添加的文字会带有设置的 what 属性 )
 *     <p></p>
 *     Spannable.SPAN_INCLUSIVE_INCLUSIVE
 *     前后都包括 前后都不包括 ( 在标志位 [start, end) 前后添加文字, 新添加的文字会有设置的属性 )
 * </pre>
 */
public final class SpanUtils {

    // 日志 TAG
    private static final String TAG = SpanUtils.class.getSimpleName();

    // 对齐类型
    public static final int ALIGN_BOTTOM   = 0;
    public static final int ALIGN_BASELINE = 1;
    public static final int ALIGN_CENTER   = 2;
    public static final int ALIGN_TOP      = 3;

    @IntDef({ALIGN_BOTTOM, ALIGN_BASELINE, ALIGN_CENTER, ALIGN_TOP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Align {
    }

    // 内部 SpannableString 实现类
    private final SerializableSpannableStringBuilder mBuilder;
    // TextView create setText
    private       TextView                           mTextView;
    // 中转文本
    private       CharSequence                       mText;

    // 内部标记应用类型
    private              int mType;
    private static final int TYPE_CHAR_SEQUENCE = 0;
    private static final int TYPE_IMAGE         = 1;
    private static final int TYPE_SPACE         = 2;

    // 默认颜色
    private static final int                 COLOR_DEFAULT = Color.WHITE;
    // 配置参数
    private              int                 flag; // setSpan flag
    private              int                 foregroundColor; // 前景色
    private              int                 backgroundColor; // 背景色
    private              int                 lineHeight; // 行高
    private              int                 alignLine; // 行对齐类型
    private              int                 quoteColor; // 引用线颜色
    private              int                 stripeWidth; // 线条宽度
    private              int                 quoteGapWidth; // 间隙宽度
    private              int                 first; // 第一行缩进
    private              int                 rest; // 其他行缩进
    private              int                 bulletColor; // 列表项颜色
    private              int                 bulletRadius; // 列表项半径
    private              int                 bulletGapWidth; // 列表项间隙宽度
    private              int                 fontSize; // 字体大小 px
    private              boolean             fontSizeIsDp; // 字体大小是否使用 dp
    private              float               proportion; // 字体缩放比例
    private              float               xProportion; // 字体横向缩放比例
    private              boolean             isStrikethrough; // 是否中划线
    private              boolean             isUnderline; // 是否下划线
    private              boolean             isSuperscript; // 是否上标
    private              boolean             isSubscript; // 是否下标
    private              boolean             isBold; // 是否加粗
    private              boolean             isItalic; // 是否斜体
    private              boolean             isBoldItalic; // 是否粗斜体
    private              String              fontFamily; // 字体系列
    private              Typeface            typeface; // 字体
    private              Layout.Alignment    alignment; // 对齐类型 ( 水平对齐 )
    private              int                 verticalAlign; // 垂直对齐类型
    private              ClickableSpan       clickSpan; // 点击区域
    private              String              url; // Url
    private              float               blurRadius; // 模糊程度
    private              BlurMaskFilter.Blur style; // 模糊样式
    private              Shader              shader; // 着色
    private              float               shadowRadius; // 阴影半径
    private              float               shadowDx; // X 轴阴影偏移值
    private              float               shadowDy; // Y 轴阴影偏移值
    private              int                 shadowColor; // 阴影颜色
    private              int                 spaceSize; // 空格大小
    private              int                 spaceColor; // 空格颜色
    private              Object[]            spans; // 自定义 setSpan 参数

    // 图片相关
    private Bitmap   imageBitmap; // Bitmap
    private Drawable imageDrawable; // Drawable
    private Uri      imageUri; // Uri
    private int      imageResourceId; // resource id
    private int      alignImage; // 图片对齐类型

    // ===========
    // = 构造函数 =
    // ===========

    /**
     * 构造函数
     * @param textView {@link TextView}
     */
    private SpanUtils(TextView textView) {
        this();
        mTextView = textView;
    }

    /**
     * 构造函数
     */
    public SpanUtils() {
        mBuilder = new SerializableSpannableStringBuilder();
        mText = "";
        mType = -1;
        setDefault();
    }

    /**
     * 获取持有 TextView SpannableString Utils
     * @param textView {@link TextView}
     * @return {@link SpanUtils}
     */
    public static SpanUtils with(final TextView textView) {
        return new SpanUtils(textView);
    }

    // ===========
    // = 实现方法 =
    // ===========

    /**
     * 设置标识
     * @param flag 标识
     *             <ul>
     *             <li>{@link Spanned#SPAN_INCLUSIVE_EXCLUSIVE}</li>
     *             <li>{@link Spanned#SPAN_INCLUSIVE_INCLUSIVE}</li>
     *             <li>{@link Spanned#SPAN_EXCLUSIVE_EXCLUSIVE}</li>
     *             <li>{@link Spanned#SPAN_EXCLUSIVE_INCLUSIVE}</li>
     *             </ul>
     * @return {@link SpanUtils}
     */
    public SpanUtils setFlag(final int flag) {
        this.flag = flag;
        return this;
    }

    /**
     * 设置前景色
     * @param color 前景色
     * @return {@link SpanUtils}
     */
    public SpanUtils setForegroundColor(@ColorInt final int color) {
        this.foregroundColor = color;
        return this;
    }

    /**
     * 设置背景色
     * @param color 背景色
     * @return {@link SpanUtils}
     */
    public SpanUtils setBackgroundColor(@ColorInt final int color) {
        this.backgroundColor = color;
        return this;
    }

    /**
     * 设置行高
     * @param lineHeight 行高
     * @return {@link SpanUtils}
     */
    public SpanUtils setLineHeight(@IntRange(from = 0) final int lineHeight) {
        return setLineHeight(lineHeight, ALIGN_CENTER);
    }

    /**
     * 设置行高
     * @param lineHeight 行高
     * @param align      行对齐类型
     *                   <ul>
     *                   <li>{@link Align#ALIGN_TOP   }</li>
     *                   <li>{@link Align#ALIGN_CENTER}</li>
     *                   <li>{@link Align#ALIGN_BOTTOM}</li>
     *                   </ul>
     * @return {@link SpanUtils}
     */
    public SpanUtils setLineHeight(
            @IntRange(from = 0) final int lineHeight,
            @Align final int align
    ) {
        this.lineHeight = lineHeight;
        this.alignLine = align;
        return this;
    }

    /**
     * 设置引用线的颜色
     * @param color 引用线颜色
     * @return {@link SpanUtils}
     */
    public SpanUtils setQuoteColor(@ColorInt final int color) {
        return setQuoteColor(color, 2, 2);
    }

    /**
     * 设置引用线的颜色
     * @param color       引用线颜色
     * @param stripeWidth 线条宽度
     * @param gapWidth    间隙宽度
     * @return {@link SpanUtils}
     */
    public SpanUtils setQuoteColor(
            @ColorInt final int color,
            @IntRange(from = 1) final int stripeWidth,
            @IntRange(from = 0) final int gapWidth
    ) {
        this.quoteColor = color;
        this.stripeWidth = stripeWidth;
        this.quoteGapWidth = gapWidth;
        return this;
    }

    /**
     * 设置缩进
     * @param first 段落第一行的缩进值
     * @param rest  段落其余行的缩进值
     * @return {@link SpanUtils}
     */
    public SpanUtils setLeadingMargin(
            @IntRange(from = 0) final int first,
            @IntRange(from = 0) final int rest
    ) {
        this.first = first;
        this.rest = rest;
        return this;
    }

    /**
     * 设置列表标记
     * @param gapWidth 列表间隙宽度
     * @return {@link SpanUtils}
     */
    public SpanUtils setBullet(@IntRange(from = 0) final int gapWidth) {
        return setBullet(0, 3, gapWidth);
    }

    /**
     * 设置列表标记
     * @param color    列表颜色
     * @param radius   列表半径
     * @param gapWidth 列表间隙宽度
     * @return {@link SpanUtils}
     */
    public SpanUtils setBullet(
            @ColorInt final int color,
            @IntRange(from = 0) final int radius,
            @IntRange(from = 0) final int gapWidth
    ) {
        this.bulletColor = color;
        this.bulletRadius = radius;
        this.bulletGapWidth = gapWidth;
        return this;
    }

    /**
     * 设置字体尺寸
     * @param size 字体大小
     * @return {@link SpanUtils}
     */
    public SpanUtils setFontSize(@IntRange(from = 0) final int size) {
        return setFontSize(size, false);
    }

    /**
     * 设置字体尺寸
     * @param size 字体大小
     * @param isDp 是否使用 dp
     * @return {@link SpanUtils}
     */
    public SpanUtils setFontSize(
            @IntRange(from = 0) final int size,
            final boolean isDp
    ) {
        this.fontSize = size;
        this.fontSizeIsDp = isDp;
        return this;
    }

    /**
     * 设置字体比例
     * @param proportion 字体比例
     * @return {@link SpanUtils}
     */
    public SpanUtils setFontProportion(final float proportion) {
        this.proportion = proportion;
        return this;
    }

    /**
     * 设置字体横向比例
     * @param proportion 字体横向比例
     * @return {@link SpanUtils}
     */
    public SpanUtils setFontXProportion(final float proportion) {
        this.xProportion = proportion;
        return this;
    }

    // =

    /**
     * 设置删除线
     * @return {@link SpanUtils}
     */
    public SpanUtils setStrikethrough() {
        this.isStrikethrough = true;
        return this;
    }

    /**
     * 设置下划线
     * @return {@link SpanUtils}
     */
    public SpanUtils setUnderline() {
        this.isUnderline = true;
        return this;
    }

    /**
     * 设置上标
     * @return {@link SpanUtils}
     */
    public SpanUtils setSuperscript() {
        this.isSuperscript = true;
        return this;
    }

    /**
     * 设置下标
     * @return {@link SpanUtils}
     */
    public SpanUtils setSubscript() {
        this.isSubscript = true;
        return this;
    }

    /**
     * 设置粗体
     * @return {@link SpanUtils}
     */
    public SpanUtils setBold() {
        isBold = true;
        return this;
    }

    /**
     * 设置斜体
     * @return {@link SpanUtils}
     */
    public SpanUtils setItalic() {
        isItalic = true;
        return this;
    }

    /**
     * 设置粗斜体
     * @return {@link SpanUtils}
     */
    public SpanUtils setBoldItalic() {
        isBoldItalic = true;
        return this;
    }

    /**
     * 设置字体系列
     * @param fontFamily 字体系列
     *                   <ul>
     *                   <li>monospace</li>
     *                   <li>serif</li>
     *                   <li>sans-serif</li>
     *                   </ul>
     * @return {@link SpanUtils}
     */
    public SpanUtils setFontFamily(@NonNull final String fontFamily) {
        this.fontFamily = fontFamily;
        return this;
    }

    /**
     * 设置字体
     * @param typeface {@link Typeface}
     * @return {@link SpanUtils}
     */
    public SpanUtils setTypeface(@NonNull final Typeface typeface) {
        this.typeface = typeface;
        return this;
    }

    /**
     * 设置水平对齐
     * @param alignment 对齐类型
     *                  <ul>
     *                  <li>{@link Layout.Alignment#ALIGN_NORMAL  }</li>
     *                  <li>{@link Layout.Alignment#ALIGN_OPPOSITE}</li>
     *                  <li>{@link Layout.Alignment#ALIGN_CENTER  }</li>
     *                  </ul>
     * @return {@link SpanUtils}
     */
    public SpanUtils setHorizontalAlign(@NonNull final Layout.Alignment alignment) {
        this.alignment = alignment;
        return this;
    }

    /**
     * 设置垂直对齐
     * @param align 对齐类型
     *              <ul>
     *              <li>{@link Align#ALIGN_TOP     }</li>
     *              <li>{@link Align#ALIGN_CENTER  }</li>
     *              <li>{@link Align#ALIGN_BASELINE}</li>
     *              <li>{@link Align#ALIGN_BOTTOM  }</li>
     *              </ul>
     * @return {@link SpanUtils}
     */
    public SpanUtils setVerticalAlign(@Align final int align) {
        this.verticalAlign = align;
        return this;
    }

    /**
     * 设置点击事件
     * <pre>
     *     需设置 {@code view.setMovementMethod(LinkMovementMethod.getInstance())}
     * </pre>
     * @param clickSpan {@link ClickableSpan}
     * @return {@link SpanUtils}
     */
    public SpanUtils setClickSpan(@NonNull final ClickableSpan clickSpan) {
        if (mTextView != null && mTextView.getMovementMethod() == null) {
            mTextView.setMovementMethod(LinkMovementMethod.getInstance());
        }
        this.clickSpan = clickSpan;
        return this;
    }

    /**
     * 设置超链接
     * <pre>
     *     需设置 {@code view.setMovementMethod(LinkMovementMethod.getInstance())}
     * </pre>
     * @param url 超链接
     * @return {@link SpanUtils}
     */
    public SpanUtils setUrl(@NonNull final String url) {
        if (mTextView != null && mTextView.getMovementMethod() == null) {
            mTextView.setMovementMethod(LinkMovementMethod.getInstance());
        }
        this.url = url;
        return this;
    }

    /**
     * 设置模糊
     * @param radius 模糊程度
     * @param style  模糊样式
     *               <ul>
     *               <li>{@link BlurMaskFilter.Blur#NORMAL}</li>
     *               <li>{@link BlurMaskFilter.Blur#SOLID}</li>
     *               <li>{@link BlurMaskFilter.Blur#OUTER}</li>
     *               <li>{@link BlurMaskFilter.Blur#INNER}</li>
     *               </ul>
     * @return {@link SpanUtils}
     */
    public SpanUtils setBlur(
            @FloatRange(from = 0, fromInclusive = false) final float radius,
            final BlurMaskFilter.Blur style
    ) {
        this.blurRadius = radius;
        this.style = style;
        return this;
    }

    /**
     * 设置着色器
     * @param shader {@link Shader}
     * @return {@link SpanUtils}
     */
    public SpanUtils setShader(@NonNull final Shader shader) {
        this.shader = shader;
        return this;
    }

    /**
     * 设置阴影
     * @param radius      阴影半径
     * @param dx          X 轴阴影偏移值
     * @param dy          Y 轴阴影偏移值
     * @param shadowColor 阴影颜色
     * @return {@link SpanUtils}
     */
    public SpanUtils setShadow(
            @FloatRange(from = 0, fromInclusive = false) final float radius,
            final float dx,
            final float dy,
            final int shadowColor
    ) {
        this.shadowRadius = radius;
        this.shadowDx = dx;
        this.shadowDy = dy;
        this.shadowColor = shadowColor;
        return this;
    }

    // =

    /**
     * 自定义 setSpan 参数
     * @param spans span 数组参数
     * @return {@link SpanUtils}
     */
    public SpanUtils setSpans(@NonNull final Object... spans) {
        if (spans != null && spans.length > 0) {
            this.spans = spans;
        }
        return this;
    }

    /**
     * 追加文本
     * @param text 文本内容
     * @return {@link SpanUtils}
     */
    public SpanUtils append(@NonNull final CharSequence text) {
        apply(TYPE_CHAR_SEQUENCE);
        mText = text;
        return this;
    }

    /**
     * 追加换行
     * @return {@link SpanUtils}
     */
    public SpanUtils appendLine() {
        apply(TYPE_CHAR_SEQUENCE);
        mText = DevFinal.NEW_LINE_STR;
        return this;
    }

    /**
     * 追加文本 ( 换行 )
     * @param text 文本内容
     * @return {@link SpanUtils}
     */
    public SpanUtils appendLine(@NonNull final CharSequence text) {
        apply(TYPE_CHAR_SEQUENCE);
        mText = text + DevFinal.NEW_LINE_STR;
        return this;
    }

    // =

    /**
     * 追加 Image
     * @param bitmap {@link Bitmap} image
     * @return {@link SpanUtils}
     */
    public SpanUtils appendImage(@NonNull final Bitmap bitmap) {
        return appendImage(bitmap, ALIGN_BOTTOM);
    }

    /**
     * 追加 Image
     * @param bitmap {@link Bitmap} image
     * @param align  对齐类型
     *               <ul>
     *               <li>{@link Align#ALIGN_TOP     }</li>
     *               <li>{@link Align#ALIGN_CENTER  }</li>
     *               <li>{@link Align#ALIGN_BASELINE}</li>
     *               <li>{@link Align#ALIGN_BOTTOM  }</li>
     *               </ul>
     * @return {@link SpanUtils}
     */
    public SpanUtils appendImage(
            @NonNull final Bitmap bitmap,
            @Align final int align
    ) {
        apply(TYPE_IMAGE);
        this.imageBitmap = bitmap;
        this.alignImage = align;
        return this;
    }

    /**
     * 追加 Image
     * @param drawable {@link Drawable} image
     * @return {@link SpanUtils}
     */
    public SpanUtils appendImage(@NonNull final Drawable drawable) {
        return appendImage(drawable, ALIGN_BOTTOM);
    }

    /**
     * 追加 Image
     * @param drawable {@link Drawable} image
     * @param align    对齐类型
     *                 <ul>
     *                 <li>{@link Align#ALIGN_TOP     }</li>
     *                 <li>{@link Align#ALIGN_CENTER  }</li>
     *                 <li>{@link Align#ALIGN_BASELINE}</li>
     *                 <li>{@link Align#ALIGN_BOTTOM  }</li>
     *                 </ul>
     * @return {@link SpanUtils}
     */
    public SpanUtils appendImage(
            @NonNull final Drawable drawable,
            @Align final int align
    ) {
        apply(TYPE_IMAGE);
        this.imageDrawable = drawable;
        this.alignImage = align;
        return this;
    }

    /**
     * 追加 Image
     * @param uri {@link Uri} image
     * @return {@link SpanUtils}
     */
    public SpanUtils appendImage(@NonNull final Uri uri) {
        return appendImage(uri, ALIGN_BOTTOM);
    }

    /**
     * 追加 Image
     * @param uri   {@link Uri} image
     * @param align 对齐类型
     *              <ul>
     *              <li>{@link Align#ALIGN_TOP     }</li>
     *              <li>{@link Align#ALIGN_CENTER  }</li>
     *              <li>{@link Align#ALIGN_BASELINE}</li>
     *              <li>{@link Align#ALIGN_BOTTOM  }</li>
     *              </ul>
     * @return {@link SpanUtils}
     */
    public SpanUtils appendImage(
            @NonNull final Uri uri,
            @Align final int align
    ) {
        apply(TYPE_IMAGE);
        this.imageUri = uri;
        this.alignImage = align;
        return this;
    }

    /**
     * 追加 Image
     * @param resourceId The resource id of image
     * @return {@link SpanUtils}
     */
    public SpanUtils appendImage(@DrawableRes final int resourceId) {
        return appendImage(resourceId, ALIGN_BOTTOM);
    }

    /**
     * 追加 Image
     * @param resourceId The resource id of image
     * @param align      对齐类型
     *                   <ul>
     *                   <li>{@link Align#ALIGN_TOP     }</li>
     *                   <li>{@link Align#ALIGN_CENTER  }</li>
     *                   <li>{@link Align#ALIGN_BASELINE}</li>
     *                   <li>{@link Align#ALIGN_BOTTOM  }</li>
     *                   </ul>
     * @return {@link SpanUtils}
     */
    public SpanUtils appendImage(
            @DrawableRes final int resourceId,
            @Align final int align
    ) {
        apply(TYPE_IMAGE);
        this.imageResourceId = resourceId;
        this.alignImage = align;
        return this;
    }

    // =

    /**
     * 追加空格
     * @param size 空格大小
     * @return {@link SpanUtils}
     */
    public SpanUtils appendSpace(@IntRange(from = 0) final int size) {
        return appendSpace(size, Color.TRANSPARENT);
    }

    /**
     * 追加空格
     * @param size  空格大小
     * @param color 空格颜色
     * @return {@link SpanUtils}
     */
    public SpanUtils appendSpace(
            @IntRange(from = 0) final int size,
            @ColorInt final int color
    ) {
        apply(TYPE_SPACE);
        this.spaceSize = size;
        this.spaceColor = color;
        return this;
    }

    // =

    /**
     * 获取 SpannableStringBuilder
     * @return {@link SpannableStringBuilder}
     */
    public SpannableStringBuilder get() {
        return mBuilder;
    }

    /**
     * 创建 SpannableStringBuilder
     * @return {@link SpannableStringBuilder}
     */
    public SpannableStringBuilder create() {
        applyLast();
        if (mTextView != null) {
            mTextView.setText(mBuilder);
        }
        return mBuilder;
    }

    // ===========
    // = 私有方法 =
    // ===========

    /**
     * 应用效果
     * @param type 效果 ( 应用 ) 类型
     */
    private void apply(final int type) {
        applyLast();
        mType = type;
    }

    /**
     * 应用效果, 并重置配置
     */
    private void applyLast() {
        if (mType == TYPE_CHAR_SEQUENCE) {
            updateCharCharSequence();
        } else if (mType == TYPE_IMAGE) {
            updateImage();
        } else if (mType == TYPE_SPACE) {
            updateSpace();
        }
        setDefault();
    }

    /**
     * 更新 CharSequence 字符
     */
    private void updateCharCharSequence() {
        if (mText.length() == 0) return;
        int start = mBuilder.length();
        if (start == 0 && lineHeight != -1) { // bug of LineHeightSpan when first line
            mBuilder.append(Character.toString((char) 2)).append("\n")
                    .setSpan(new AbsoluteSizeSpan(0), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            start = 2;
        }
        mBuilder.append(mText);
        int end = mBuilder.length();
        if (verticalAlign != -1) {
            mBuilder.setSpan(new VerticalAlignSpan(verticalAlign), start, end, flag);
        }
        if (foregroundColor != COLOR_DEFAULT) {
            mBuilder.setSpan(new ForegroundColorSpan(foregroundColor), start, end, flag);
        }
        if (backgroundColor != COLOR_DEFAULT) {
            mBuilder.setSpan(new BackgroundColorSpan(backgroundColor), start, end, flag);
        }
        if (first != -1) {
            mBuilder.setSpan(new LeadingMarginSpan.Standard(first, rest), start, end, flag);
        }
        if (quoteColor != COLOR_DEFAULT) {
            mBuilder.setSpan(new CustomQuoteSpan(quoteColor, stripeWidth, quoteGapWidth), start, end, flag);
        }
        if (bulletColor != COLOR_DEFAULT) {
            mBuilder.setSpan(new CustomBulletSpan(bulletColor, bulletRadius, bulletGapWidth), start, end, flag);
        }
        if (fontSize != -1) {
            mBuilder.setSpan(new AbsoluteSizeSpan(fontSize, fontSizeIsDp), start, end, flag);
        }
        if (proportion != -1) {
            mBuilder.setSpan(new RelativeSizeSpan(proportion), start, end, flag);
        }
        if (xProportion != -1) {
            mBuilder.setSpan(new ScaleXSpan(xProportion), start, end, flag);
        }
        if (lineHeight != -1) {
            mBuilder.setSpan(new CustomLineHeightSpan(lineHeight, alignLine), start, end, flag);
        }
        if (isStrikethrough) {
            mBuilder.setSpan(new StrikethroughSpan(), start, end, flag);
        }
        if (isUnderline) {
            mBuilder.setSpan(new UnderlineSpan(), start, end, flag);
        }
        if (isSuperscript) {
            mBuilder.setSpan(new SuperscriptSpan(), start, end, flag);
        }
        if (isSubscript) {
            mBuilder.setSpan(new SubscriptSpan(), start, end, flag);
        }
        if (isBold) {
            mBuilder.setSpan(new StyleSpan(Typeface.BOLD), start, end, flag);
        }
        if (isItalic) {
            mBuilder.setSpan(new StyleSpan(Typeface.ITALIC), start, end, flag);
        }
        if (isBoldItalic) {
            mBuilder.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), start, end, flag);
        }
        if (fontFamily != null) {
            mBuilder.setSpan(new TypefaceSpan(fontFamily), start, end, flag);
        }
        if (typeface != null) {
            mBuilder.setSpan(new CustomTypefaceSpan(typeface), start, end, flag);
        }
        if (alignment != null) {
            mBuilder.setSpan(new AlignmentSpan.Standard(alignment), start, end, flag);
        }
        if (clickSpan != null) {
            mBuilder.setSpan(clickSpan, start, end, flag);
        }
        if (url != null) {
            mBuilder.setSpan(new URLSpan(url), start, end, flag);
        }
        if (blurRadius != -1) {
            mBuilder.setSpan(new MaskFilterSpan(new BlurMaskFilter(blurRadius, style)), start, end, flag);
        }
        if (shader != null) {
            mBuilder.setSpan(new ShaderSpan(shader), start, end, flag);
        }
        if (shadowRadius != -1) {
            mBuilder.setSpan(new ShadowSpan(shadowRadius, shadowDx, shadowDy, shadowColor), start, end, flag);
        }
        if (spans != null) {
            for (Object span : spans) {
                mBuilder.setSpan(span, start, end, flag);
            }
        }
    }

    /**
     * 更新 Image
     */
    private void updateImage() {
        int start = mBuilder.length();
        mText = "<img>";
        updateCharCharSequence();
        int end = mBuilder.length();
        if (imageBitmap != null) {
            mBuilder.setSpan(new CustomImageSpan(imageBitmap, alignImage), start, end, flag);
        } else if (imageDrawable != null) {
            mBuilder.setSpan(new CustomImageSpan(imageDrawable, alignImage), start, end, flag);
        } else if (imageUri != null) {
            mBuilder.setSpan(new CustomImageSpan(imageUri, alignImage), start, end, flag);
        } else if (imageResourceId != -1) {
            mBuilder.setSpan(new CustomImageSpan(imageResourceId, alignImage), start, end, flag);
        }
    }

    /**
     * 更新 Span
     */
    private void updateSpace() {
        int start = mBuilder.length();
        mText = "< >";
        updateCharCharSequence();
        int end = mBuilder.length();
        mBuilder.setSpan(new SpaceSpan(spaceSize, spaceColor), start, end, flag);
    }

    /**
     * 初始化默认值
     */
    private void setDefault() {
        flag = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;
        foregroundColor = COLOR_DEFAULT;
        backgroundColor = COLOR_DEFAULT;
        lineHeight = -1;
        quoteColor = COLOR_DEFAULT;
        first = -1;
        bulletColor = COLOR_DEFAULT;
        fontSize = -1;
        proportion = -1;
        xProportion = -1;
        isStrikethrough = false;
        isUnderline = false;
        isSuperscript = false;
        isSubscript = false;
        isBold = false;
        isItalic = false;
        isBoldItalic = false;
        fontFamily = null;
        typeface = null;
        alignment = null;
        verticalAlign = -1;
        clickSpan = null;
        url = null;
        blurRadius = -1;
        shader = null;
        shadowRadius = -1;
        spaceSize = -1;
        spans = null;

        imageBitmap = null;
        imageDrawable = null;
        imageUri = null;
        imageResourceId = -1;
    }

    // ==========================
    // = 内部自定义 Span 效果实现 =
    // ==========================

    /**
     * detail: 垂直对齐 Span
     * @author Ttt
     */
    static class VerticalAlignSpan
            extends ReplacementSpan {

        static final int ALIGN_CENTER = 2;
        static final int ALIGN_TOP    = 3;

        final int mVerticalAlignment;

        VerticalAlignSpan(int verticalAlignment) {
            mVerticalAlignment = verticalAlignment;
        }

        @Override
        public int getSize(
                @NonNull Paint paint,
                CharSequence text,
                int start,
                int end,
                @Nullable Paint.FontMetricsInt fm
        ) {
            text = text.subSequence(start, end);
            return (int) paint.measureText(text.toString());
        }

        @Override
        public void draw(
                @NonNull Canvas canvas,
                CharSequence text,
                int start,
                int end,
                float x,
                int top,
                int y,
                int bottom,
                @NonNull Paint paint
        ) {
            text = text.subSequence(start, end);
            Paint.FontMetricsInt fm = paint.getFontMetricsInt();
            canvas.drawText(text.toString(), x, y - ((y + fm.descent + y + fm.ascent) / 2 - (bottom + top) / 2), paint);
        }
    }

    /**
     * detail: 行高 Span
     * @author Ttt
     */
    static class CustomLineHeightSpan
            implements LineHeightSpan {

        private final int height;

        static final int ALIGN_CENTER = 2;
        static final int ALIGN_TOP    = 3;

        final  int                  mVerticalAlignment;
        static Paint.FontMetricsInt sfm;

        CustomLineHeightSpan(
                int height,
                int verticalAlignment
        ) {
            this.height = height;
            mVerticalAlignment = verticalAlignment;
        }

        @Override
        public void chooseHeight(
                final CharSequence text,
                final int start,
                final int end,
                final int spanstartv,
                final int v,
                final Paint.FontMetricsInt fm
        ) {
            if (sfm == null) {
                sfm = new Paint.FontMetricsInt();
                sfm.top = fm.top;
                sfm.ascent = fm.ascent;
                sfm.descent = fm.descent;
                sfm.bottom = fm.bottom;
                sfm.leading = fm.leading;
            } else {
                fm.top = sfm.top;
                fm.ascent = sfm.ascent;
                fm.descent = sfm.descent;
                fm.bottom = sfm.bottom;
                fm.leading = sfm.leading;
            }
            int need = height - (v + fm.descent - fm.ascent - spanstartv);
            if (need > 0) {
                if (mVerticalAlignment == ALIGN_TOP) {
                    fm.descent += need;
                } else if (mVerticalAlignment == ALIGN_CENTER) {
                    fm.descent += need / 2;
                    fm.ascent -= need / 2;
                } else {
                    fm.ascent -= need;
                }
            }
            need = height - (v + fm.bottom - fm.top - spanstartv);
            if (need > 0) {
                if (mVerticalAlignment == ALIGN_TOP) {
                    fm.bottom += need;
                } else if (mVerticalAlignment == ALIGN_CENTER) {
                    fm.bottom += need / 2;
                    fm.top -= need / 2;
                } else {
                    fm.top -= need;
                }
            }
            if (end == ((Spanned) text).getSpanEnd(this)) {
                sfm = null;
            }
        }
    }

    /**
     * detail: 空格 Span
     * @author Ttt
     */
    static class SpaceSpan
            extends ReplacementSpan {

        private final int   width;
        private final Paint paint = new Paint();

        private SpaceSpan(final int width) {
            this(width, Color.TRANSPARENT);
        }

        private SpaceSpan(
                final int width,
                final int color
        ) {
            super();
            this.width = width;
            paint.setColor(color);
            paint.setStyle(Paint.Style.FILL);
        }

        @Override
        public int getSize(
                @NonNull final Paint paint,
                final CharSequence text,
                @IntRange(from = 0) final int start,
                @IntRange(from = 0) final int end,
                @Nullable final Paint.FontMetricsInt fm
        ) {
            return width;
        }

        @Override
        public void draw(
                @NonNull final Canvas canvas,
                final CharSequence text,
                @IntRange(from = 0) final int start,
                @IntRange(from = 0) final int end,
                final float x,
                final int top,
                final int y,
                final int bottom,
                @NonNull final Paint paint
        ) {
            canvas.drawRect(x, top, x + width, bottom, this.paint);
        }
    }

    /**
     * detail: 自定义引用样式 Span
     * @author Ttt
     */
    static class CustomQuoteSpan
            implements LeadingMarginSpan {

        private final int color;
        private final int stripeWidth;
        private final int gapWidth;

        private CustomQuoteSpan(
                final int color,
                final int stripeWidth,
                final int gapWidth
        ) {
            super();
            this.color = color;
            this.stripeWidth = stripeWidth;
            this.gapWidth = gapWidth;
        }

        public int getLeadingMargin(final boolean first) {
            return stripeWidth + gapWidth;
        }

        public void drawLeadingMargin(
                final Canvas c,
                final Paint p,
                final int x,
                final int dir,
                final int top,
                final int baseline,
                final int bottom,
                final CharSequence text,
                final int start,
                final int end,
                final boolean first,
                final Layout layout
        ) {
            Paint.Style style = p.getStyle();
            int         color = p.getColor();

            p.setStyle(Paint.Style.FILL);
            p.setColor(this.color);

            c.drawRect(x, top, x + dir * stripeWidth, bottom, p);

            p.setStyle(style);
            p.setColor(color);
        }
    }

    /**
     * detail: 自定义列表项样式 Span
     * @author Ttt
     */
    static class CustomBulletSpan
            implements LeadingMarginSpan {

        private final int color;
        private final int radius;
        private final int gapWidth;

        private Path sBulletPath = null;

        private CustomBulletSpan(
                final int color,
                final int radius,
                final int gapWidth
        ) {
            this.color = color;
            this.radius = radius;
            this.gapWidth = gapWidth;
        }

        public int getLeadingMargin(final boolean first) {
            return 2 * radius + gapWidth;
        }

        public void drawLeadingMargin(
                final Canvas c,
                final Paint p,
                final int x,
                final int dir,
                final int top,
                final int baseline,
                final int bottom,
                final CharSequence text,
                final int start,
                final int end,
                final boolean first,
                final Layout l
        ) {
            if (((Spanned) text).getSpanStart(this) == start) {
                Paint.Style style = p.getStyle();
                int         oldColor;
                oldColor = p.getColor();
                p.setColor(color);
                p.setStyle(Paint.Style.FILL);
                if (c.isHardwareAccelerated()) {
                    if (sBulletPath == null) {
                        sBulletPath = new Path();
                        // Bullet is slightly better to avoid aliasing artifacts on mdpi devices.
                        sBulletPath.addCircle(0.0f, 0.0f, radius, Path.Direction.CW);
                    }
                    c.save();
                    c.translate(x + dir * radius, (top + bottom) / 2.0f);
                    c.drawPath(sBulletPath, p);
                    c.restore();
                } else {
                    c.drawCircle(x + dir * radius, (top + bottom) / 2.0f, radius, p);
                }
                p.setColor(oldColor);
                p.setStyle(style);
            }
        }
    }

    /**
     * detail: 自定义字体样式 Span
     * @author Ttt
     */
    @SuppressLint("ParcelCreator")
    static class CustomTypefaceSpan
            extends TypefaceSpan {

        private final Typeface newType;

        private CustomTypefaceSpan(final Typeface type) {
            super("");
            newType = type;
        }

        @Override
        public void updateDrawState(final TextPaint textPaint) {
            apply(textPaint, newType);
        }

        @Override
        public void updateMeasureState(final TextPaint paint) {
            apply(paint, newType);
        }

        private void apply(
                final Paint paint,
                final Typeface tf
        ) {
            int      oldStyle;
            Typeface old = paint.getTypeface();
            if (old == null) {
                oldStyle = 0;
            } else {
                oldStyle = old.getStyle();
            }

            int fake = oldStyle & ~tf.getStyle();
            if ((fake & Typeface.BOLD) != 0) {
                paint.setFakeBoldText(true);
            }

            if ((fake & Typeface.ITALIC) != 0) {
                paint.setTextSkewX(-0.25f);
            }

            paint.getShader();
            paint.setTypeface(tf);
        }
    }

    /**
     * detail: 自定义图片绘制效果 Span
     * @author Ttt
     */
    static class CustomImageSpan
            extends CustomDynamicDrawableSpan {

        private Drawable mDrawable;
        private Uri      mContentUri;
        private int      mResourceId;

        private CustomImageSpan(
                final Bitmap bitmap,
                final int verticalAlignment
        ) {
            super(verticalAlignment);
            mDrawable = new BitmapDrawable(getContext().getResources(), bitmap);
            mDrawable.setBounds(0, 0, mDrawable.getIntrinsicWidth(), mDrawable.getIntrinsicHeight());
        }

        private CustomImageSpan(
                final Drawable drawable,
                final int verticalAlignment
        ) {
            super(verticalAlignment);
            mDrawable = drawable;
            mDrawable.setBounds(0, 0, mDrawable.getIntrinsicWidth(), mDrawable.getIntrinsicHeight());
        }

        private CustomImageSpan(
                final Uri uri,
                final int verticalAlignment
        ) {
            super(verticalAlignment);
            mContentUri = uri;
        }

        private CustomImageSpan(
                @DrawableRes final int resourceId,
                final int verticalAlignment
        ) {
            super(verticalAlignment);
            mResourceId = resourceId;
        }

        @Override
        public Drawable getDrawable() {
            Drawable drawable = null;
            if (mDrawable != null) {
                drawable = mDrawable;
            } else if (mContentUri != null) {
                Bitmap bitmap;
                try {
                    InputStream is = getContext().getContentResolver().openInputStream(mContentUri);
                    bitmap = BitmapFactory.decodeStream(is);
                    drawable = new BitmapDrawable(getContext().getResources(), bitmap);
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                    CloseUtils.closeIOQuietly(is);
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "Failed to loaded content %s", mContentUri);
                }
            } else {
                try {
                    drawable = ContextCompat.getDrawable(getContext(), mResourceId);
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "Unable to find resource: %s", mResourceId);
                }
            }
            return drawable;
        }
    }

    /**
     * detail: 自定义 Drawable 绘制效果 Span
     * @author Ttt
     */
    static abstract class CustomDynamicDrawableSpan
            extends ReplacementSpan {

        static final int ALIGN_BOTTOM   = 0;
        static final int ALIGN_BASELINE = 1;
        static final int ALIGN_CENTER   = 2;
        static final int ALIGN_TOP      = 3;
        final        int mVerticalAlignment;

        private CustomDynamicDrawableSpan() {
            mVerticalAlignment = ALIGN_BOTTOM;
        }

        private CustomDynamicDrawableSpan(final int verticalAlignment) {
            mVerticalAlignment = verticalAlignment;
        }

        public abstract Drawable getDrawable();

        @Override
        public int getSize(
                @NonNull final Paint paint,
                final CharSequence text,
                final int start,
                final int end,
                final Paint.FontMetricsInt fm
        ) {
            Drawable drawable = getCachedDrawable();
            Rect     rect     = drawable.getBounds();
            if (fm != null) {
                int lineHeight = fm.bottom - fm.top;
                if (lineHeight < rect.height()) {
                    if (mVerticalAlignment == ALIGN_TOP) {
                        fm.bottom = rect.height() + fm.top;
                    } else if (mVerticalAlignment == ALIGN_CENTER) {
                        fm.top = -rect.height() / 2 - lineHeight / 4;
                        fm.bottom = rect.height() / 2 - lineHeight / 4;
                    } else {
                        fm.top = -rect.height() + fm.bottom;
                    }
                    fm.ascent = fm.top;
                    fm.descent = fm.bottom;
                }
            }
            return rect.right;
        }

        @Override
        public void draw(
                @NonNull final Canvas canvas,
                final CharSequence text,
                final int start,
                final int end,
                final float x,
                final int top,
                final int y,
                final int bottom,
                @NonNull final Paint paint
        ) {
            Drawable drawable = getCachedDrawable();
            Rect     rect     = drawable.getBounds();
            canvas.save();
            float transY;
            int   lineHeight = bottom - top;
            if (rect.height() < lineHeight) {
                if (mVerticalAlignment == ALIGN_TOP) {
                    transY = top;
                } else if (mVerticalAlignment == ALIGN_CENTER) {
                    transY = (bottom + top - rect.height()) / 2;
                } else if (mVerticalAlignment == ALIGN_BASELINE) {
                    transY = y - rect.height();
                } else {
                    transY = bottom - rect.height();
                }
                canvas.translate(x, transY);
            } else {
                canvas.translate(x, top);
            }
            drawable.draw(canvas);
            canvas.restore();
        }

        private Drawable getCachedDrawable() {
            WeakReference<Drawable> wr       = mDrawableRef;
            Drawable                drawable = null;
            if (wr != null) {
                drawable = wr.get();
            }
            if (drawable == null) {
                drawable = getDrawable();
                mDrawableRef = new WeakReference<>(drawable);
            }
            return drawable;
        }

        private WeakReference<Drawable> mDrawableRef;
    }

    /**
     * detail: 着色效果 Span
     * @author Ttt
     */
    static class ShaderSpan
            extends CharacterStyle
            implements UpdateAppearance {
        private final Shader mShader;

        private ShaderSpan(final Shader shader) {
            this.mShader = shader;
        }

        @Override
        public void updateDrawState(final TextPaint tp) {
            tp.setShader(mShader);
        }
    }

    /**
     * detail: 阴影效果 Span
     * @author Ttt
     */
    static class ShadowSpan
            extends CharacterStyle
            implements UpdateAppearance {
        private final float radius;
        private final float dx;
        private final float dy;
        private final int   shadowColor;

        private ShadowSpan(
                final float radius,
                final float dx,
                final float dy,
                final int shadowColor
        ) {
            this.radius = radius;
            this.dx = dx;
            this.dy = dy;
            this.shadowColor = shadowColor;
        }

        @Override
        public void updateDrawState(final TextPaint tp) {
            tp.setShadowLayer(radius, dx, dy, shadowColor);
        }
    }

    // ===========
    // = 内部方法 =
    // ===========

    /**
     * detail: SpannableStringBuilder 内部类, 实现序列化方便传值
     * @author Ttt
     */
    private static class SerializableSpannableStringBuilder
            extends SpannableStringBuilder
            implements Serializable {

        private static final long serialVersionUID = 2514562037168478805L;
    }

    /**
     * 获取 Context
     * @return {@link Context}
     */
    private static Context getContext() {
        return DevUtils.getContext();
    }
}