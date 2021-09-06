package dev.utils.app.helper.view;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
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

import dev.utils.app.helper.IHelper;
import dev.utils.app.helper.quick.QuickHelper;

/**
 * detail: ViewHelper 接口
 * @author Ttt
 */
public interface IHelperByView<T>
        extends IHelper<T> {

    // ========
    // = Text =
    // ========

    /**
     * 设置 Hint 文本
     * @param view {@link TextView}
     * @param text Hint text
     * @return Helper
     */
    T setHint(
            View view,
            CharSequence text
    );

    /**
     * 设置文本
     * @param view {@link TextView}
     * @param text TextView text
     * @return Helper
     */
    T setText(
            View view,
            CharSequence text
    );

    /**
     * 设置多个 TextView 文本
     * @param text  TextView text
     * @param views View(TextView)[]
     * @return Helper
     */
    T setTexts(
            CharSequence text,
            View... views
    );

    /**
     * 设置 Html 内容
     * @param view    {@link TextView}
     * @param content Html content
     * @return Helper
     */
    T setHtmlText(
            View view,
            String content
    );

    /**
     * 设置多个 TextView Html 内容
     * @param content Html content
     * @param views   View(TextView)[]
     * @return Helper
     */
    T setHtmlTexts(
            String content,
            View... views
    );

    /**
     * 设置 Hint 字体颜色
     * @param view  {@link TextView}
     * @param color R.color.id
     * @return Helper
     */
    T setHintTextColor(
            View view,
            @ColorInt int color
    );

    /**
     * 设置 Hint 字体颜色
     * @param view   {@link TextView}
     * @param colors {@link ColorStateList}
     * @return Helper
     */
    T setHintTextColor(
            View view,
            ColorStateList colors
    );

    /**
     * 设置多个 TextView Hint 字体颜色
     * @param color R.color.id
     * @param views View(TextView)[]
     * @return Helper
     */
    T setHintTextColors(
            @ColorInt int color,
            View... views
    );

    /**
     * 设置多个 TextView Hint 字体颜色
     * @param colors {@link ColorStateList}
     * @param views  View(TextView)[]
     * @return Helper
     */
    T setHintTextColors(
            ColorStateList colors,
            View... views
    );

    /**
     * 设置字体颜色
     * @param view  {@link TextView}
     * @param color R.color.id
     * @return Helper
     */
    T setTextColor(
            View view,
            @ColorInt int color
    );

    /**
     * 设置字体颜色
     * @param view   {@link TextView}
     * @param colors {@link ColorStateList}
     * @return Helper
     */
    T setTextColor(
            View view,
            ColorStateList colors
    );

    /**
     * 设置多个 TextView 字体颜色
     * @param color R.color.id
     * @param views View(TextView)[]
     * @return Helper
     */
    T setTextColors(
            @ColorInt int color,
            View... views
    );

    /**
     * 设置多个 TextView 字体颜色
     * @param colors {@link ColorStateList}
     * @param views  View(TextView)[]
     * @return Helper
     */
    T setTextColors(
            ColorStateList colors,
            View... views
    );

    /**
     * 设置字体
     * @param view     {@link TextView}
     * @param typeface {@link Typeface} 字体样式
     * @return Helper
     */
    T setTypeface(
            View view,
            Typeface typeface
    );

    /**
     * 设置字体大小 ( px 像素 )
     * @param view {@link TextView}
     * @param size 字体大小
     * @return Helper
     */
    T setTextSizeByPx(
            View view,
            float size
    );

    /**
     * 设置字体大小 ( sp 缩放像素 )
     * @param view {@link TextView}
     * @param size 字体大小
     * @return Helper
     */
    T setTextSizeBySp(
            View view,
            float size
    );

    /**
     * 设置字体大小 ( dp 与设备无关的像素 )
     * @param view {@link TextView}
     * @param size 字体大小
     * @return Helper
     */
    T setTextSizeByDp(
            View view,
            float size
    );

    /**
     * 设置字体大小 ( inches 英寸 )
     * @param view {@link TextView}
     * @param size 字体大小
     * @return Helper
     */
    T setTextSizeByIn(
            View view,
            float size
    );

    /**
     * 设置字体大小
     * @param view {@link TextView}
     * @param unit 字体参数类型
     * @param size 字体大小
     * @return Helper
     */
    T setTextSize(
            View view,
            int unit,
            float size
    );

    /**
     * 设置多个 TextView 字体大小
     * @param views View(TextView)[]
     * @param unit  参数类型
     * @param size  字体大小
     * @return Helper
     */
    T setTextSizes(
            View[] views,
            int unit,
            float size
    );

    /**
     * 清空 flags
     * @param view {@link TextView}
     * @return Helper
     */
    T clearFlags(View view);

    /**
     * 设置 TextView flags
     * @param view  {@link TextView}
     * @param flags flags
     * @return Helper
     */
    T setPaintFlags(
            View view,
            int flags
    );

    /**
     * 设置 TextView 抗锯齿 flags
     * @param view {@link TextView}
     * @return Helper
     */
    T setAntiAliasFlag(View view);

    /**
     * 设置 TextView 是否加粗
     * @param view {@link TextView}
     * @return Helper
     */
    T setBold(View view);

    /**
     * 设置 TextView 是否加粗
     * @param view   {@link TextView}
     * @param isBold {@code true} yes, {@code false} no
     * @return Helper
     */
    T setBold(
            View view,
            boolean isBold
    );

    /**
     * 设置 TextView 是否加粗
     * @param view     {@link TextView}
     * @param typeface {@link Typeface} 字体样式
     * @param isBold   {@code true} yes, {@code false} no
     * @return Helper
     */
    T setBold(
            View view,
            Typeface typeface,
            boolean isBold
    );

    /**
     * 设置下划线
     * @param view {@link TextView}
     * @return Helper
     */
    T setUnderlineText(View view);

    /**
     * 设置下划线并加清晰
     * @param view        {@link TextView}
     * @param isAntiAlias 是否消除锯齿
     * @return Helper
     */
    T setUnderlineText(
            View view,
            boolean isAntiAlias
    );

    /**
     * 设置中划线
     * @param view {@link TextView}
     * @return Helper
     */
    T setStrikeThruText(View view);

    /**
     * 设置中划线并加清晰
     * @param view        {@link TextView}
     * @param isAntiAlias 是否消除锯齿
     * @return Helper
     */
    T setStrikeThruText(
            View view,
            boolean isAntiAlias
    );

    /**
     * 设置文字水平间距
     * @param view          {@link TextView}
     * @param letterSpacing 文字水平间距值
     * @return Helper
     */
    T setLetterSpacing(
            View view,
            float letterSpacing
    );

    /**
     * 设置文字行间距 ( 行高 )
     * @param view        {@link TextView}
     * @param lineSpacing 文字行间距 ( 行高 ), android:lineSpacingExtra
     * @return Helper
     */
    T setLineSpacing(
            View view,
            float lineSpacing
    );

    /**
     * 设置文字行间距 ( 行高 )、行间距倍数
     * @param view        {@link TextView}
     * @param lineSpacing 文字行间距 ( 行高 ), android:lineSpacingExtra
     * @param multiplier  行间距倍数, android:lineSpacingMultiplier
     * @return Helper
     */
    T setLineSpacingAndMultiplier(
            View view,
            float lineSpacing,
            float multiplier
    );

    /**
     * 设置字体水平方向的缩放
     * @param view {@link TextView}
     * @param size 缩放比例
     * @return Helper
     */
    T setTextScaleX(
            View view,
            float size
    );

    /**
     * 设置是否保留字体留白间隙区域
     * @param view           {@link TextView}
     * @param includePadding 是否保留字体留白间隙区域
     * @return Helper
     */
    T setIncludeFontPadding(
            View view,
            boolean includePadding
    );

    /**
     * 设置输入类型
     * @param view {@link TextView}
     * @param type 类型
     * @return Helper
     */
    T setInputType(
            View view,
            int type
    );

    /**
     * 设置软键盘右下角按钮类型
     * @param view       {@link TextView}
     * @param imeOptions 软键盘按钮类型
     * @return Helper
     */
    T setImeOptions(
            View view,
            int imeOptions
    );

    /**
     * 设置行数
     * @param view  {@link TextView}
     * @param lines 行数
     * @return Helper
     */
    T setLines(
            View view,
            int lines
    );

    /**
     * 设置最大行数
     * @param view     {@link TextView}
     * @param maxLines 最大行数
     * @return Helper
     */
    T setMaxLines(
            View view,
            int maxLines
    );

    /**
     * 设置最小行数
     * @param view     {@link TextView}
     * @param minLines 最小行数
     * @return Helper
     */
    T setMinLines(
            View view,
            int minLines
    );

    /**
     * 设置最大字符宽度限制
     * @param view   {@link TextView}
     * @param maxEms 最大字符
     * @return Helper
     */
    T setMaxEms(
            View view,
            int maxEms
    );

    /**
     * 设置最小字符宽度限制
     * @param view   {@link TextView}
     * @param minEms 最小字符
     * @return Helper
     */
    T setMinEms(
            View view,
            int minEms
    );

    /**
     * 设置指定字符宽度
     * @param view {@link TextView}
     * @param ems  字符
     * @return Helper
     */
    T setEms(
            View view,
            int ems
    );

    /**
     * 设置 Ellipsize 效果
     * @param view  {@link TextView}
     * @param where {@link TextUtils.TruncateAt}
     * @return Helper
     */
    T setEllipsize(
            View view,
            TextUtils.TruncateAt where
    );

    /**
     * 设置自动识别文本链接
     * @param view {@link TextView}
     * @param mask {@link android.text.util.Linkify}
     * @return Helper
     */
    T setAutoLinkMask(
            View view,
            int mask
    );

    /**
     * 设置文本全为大写
     * @param view    {@link TextView}
     * @param allCaps 是否全部大写
     * @return Helper
     */
    T setAllCaps(
            View view,
            boolean allCaps
    );

    /**
     * 设置 Text Gravity
     * @param view    {@link TextView}
     * @param gravity {@link android.view.Gravity}
     * @return Helper
     */
    T setTextGravity(
            View view,
            int gravity
    );

    // ============
    // = EditText =
    // ============

    /**
     * 设置内容
     * @param editText {@link EditText}
     * @param content  文本内容
     * @param isSelect 是否设置光标
     * @return Helper
     */
    T setText(
            EditText editText,
            CharSequence content,
            boolean isSelect
    );

    /**
     * 追加内容 ( 当前光标位置追加 )
     * @param editText {@link EditText}
     * @param content  文本内容
     * @param isSelect 是否设置光标
     * @return Helper
     */
    T insert(
            EditText editText,
            CharSequence content,
            boolean isSelect
    );

    /**
     * 追加内容
     * @param editText {@link EditText}
     * @param content  文本内容
     * @param start    开始添加的位置
     * @param isSelect 是否设置光标
     * @return Helper
     */
    T insert(
            EditText editText,
            CharSequence content,
            int start,
            boolean isSelect
    );

    /**
     * 设置长度限制
     * @param view      {@link View}
     * @param maxLength 长度限制
     * @return Helper
     */
    T setMaxLength(
            View view,
            int maxLength
    );

    /**
     * 设置长度限制, 并且设置内容
     * @param view      {@link View}
     * @param content   文本内容
     * @param maxLength 长度限制
     * @return Helper
     */
    T setMaxLengthAndText(
            View view,
            CharSequence content,
            int maxLength
    );

    /**
     * 设置是否显示光标
     * @param editText {@link EditText}
     * @param visible  是否显示光标
     * @return Helper
     */
    T setCursorVisible(
            EditText editText,
            boolean visible
    );

    /**
     * 设置光标在第一位
     * @param editText {@link EditText}
     * @return Helper
     */
    T setSelectionToTop(EditText editText);

    /**
     * 设置光标在最后一位
     * @param editText {@link EditText}
     * @return Helper
     */
    T setSelectionToBottom(EditText editText);

    /**
     * 设置光标位置
     * @param editText {@link EditText}
     * @param index    光标位置
     * @return Helper
     */
    T setSelection(
            EditText editText,
            int index
    );

    // =

    /**
     * 设置文本视图显示转换
     * @param view   {@link View}
     * @param method {@link TransformationMethod}
     * @return Helper
     */
    T setTransformationMethod(
            View view,
            TransformationMethod method
    );

    /**
     * 设置密码文本视图显示转换
     * @param view              {@link View}
     * @param isDisplayPassword 是否显示密码
     * @return Helper
     */
    T setTransformationMethod(
            View view,
            boolean isDisplayPassword
    );

    /**
     * 设置密码文本视图显示转换
     * @param editText          {@link EditText}
     * @param isDisplayPassword 是否显示密码
     * @param isSelectBottom    是否设置光标到最后
     * @return Helper
     */
    T setTransformationMethod(
            EditText editText,
            boolean isDisplayPassword,
            boolean isSelectBottom
    );

    // =

    /**
     * 添加输入监听事件
     * @param editText {@link EditText}
     * @param watcher  输入监听
     * @return Helper
     */
    T addTextChangedListener(
            EditText editText,
            TextWatcher watcher
    );

    /**
     * 移除输入监听事件
     * @param editText {@link EditText}
     * @param watcher  输入监听
     * @return Helper
     */
    T removeTextChangedListener(
            EditText editText,
            TextWatcher watcher
    );

    /**
     * 设置 KeyListener
     * @param editText    {@link EditText}
     * @param keyListener {@link KeyListener}
     * @return Helper
     */
    T setKeyListener(
            EditText editText,
            KeyListener keyListener
    );

    /**
     * 设置 KeyListener
     * @param editText {@link EditText}
     * @param accepted 允许输入的内容, 如: 0123456789
     * @return Helper
     */
    T setKeyListener(
            EditText editText,
            String accepted
    );

    /**
     * 设置 KeyListener
     * @param editText {@link EditText}
     * @param accepted 允许输入的内容
     * @return Helper
     */
    T setKeyListener(
            EditText editText,
            char[] accepted
    );

    // =========
    // = Image =
    // =========

    /**
     * 设置 ImageView 是否保持宽高比
     * @param imageView        ImageView
     * @param adjustViewBounds 是否调整此视图的边界以保持可绘制的原始纵横比
     * @return Helper
     */
    T setAdjustViewBounds(
            ImageView imageView,
            boolean adjustViewBounds
    );

    /**
     * 设置 ImageView 最大高度
     * @param imageView ImageView
     * @param maxHeight 最大高度
     * @return Helper
     */
    T setMaxHeight(
            ImageView imageView,
            int maxHeight
    );

    /**
     * 设置 ImageView 最大宽度
     * @param imageView ImageView
     * @param maxWidth  最大宽度
     * @return Helper
     */
    T setMaxWidth(
            ImageView imageView,
            int maxWidth
    );

    // =

    /**
     * 设置背景图片
     * @param view       {@link View}
     * @param background 背景图片
     * @return Helper
     */
    T setBackground(
            View view,
            Drawable background
    );

    /**
     * 设置背景颜色
     * @param view  {@link View}
     * @param color 背景颜色
     * @return Helper
     */
    T setBackgroundColor(
            View view,
            @ColorInt int color
    );

    /**
     * 设置背景资源
     * @param view  {@link View}
     * @param resId resource identifier
     * @return Helper
     */
    T setBackgroundResource(
            View view,
            @DrawableRes int resId
    );

    /**
     * 设置背景着色颜色
     * @param view {@link View}
     * @param tint 着色颜色
     * @return Helper
     */
    T setBackgroundTintList(
            View view,
            ColorStateList tint
    );

    /**
     * 设置背景着色模式
     * @param view     {@link View}
     * @param tintMode 着色模式 {@link PorterDuff.Mode}
     * @return Helper
     */
    T setBackgroundTintMode(
            View view,
            PorterDuff.Mode tintMode
    );

    /**
     * 设置前景图片
     * @param view       {@link View}
     * @param foreground 前景图片
     * @return Helper
     */
    T setForeground(
            View view,
            Drawable foreground
    );

    /**
     * 设置前景重心
     * @param view    {@link View}
     * @param gravity 重心
     * @return Helper
     */
    T setForegroundGravity(
            View view,
            int gravity
    );

    /**
     * 设置前景着色颜色
     * @param view {@link View}
     * @param tint 着色颜色
     * @return Helper
     */
    T setForegroundTintList(
            View view,
            ColorStateList tint
    );

    /**
     * 设置前景着色模式
     * @param view     {@link View}
     * @param tintMode 着色模式 {@link PorterDuff.Mode}
     * @return Helper
     */
    T setForegroundTintMode(
            View view,
            PorterDuff.Mode tintMode
    );

    /**
     * 设置 ImageView Level
     * @param view  {@link View}
     * @param level level Image
     * @return Helper
     */
    T setImageLevel(
            View view,
            int level
    );

    /**
     * 设置 ImageView Bitmap
     * @param view   {@link View}
     * @param bitmap {@link Bitmap}
     * @return Helper
     */
    T setImageBitmap(
            View view,
            Bitmap bitmap
    );

    /**
     * 设置 ImageView Drawable
     * @param view     {@link View}
     * @param drawable {@link Bitmap}
     * @return Helper
     */
    T setImageDrawable(
            View view,
            Drawable drawable
    );

    /**
     * 设置 ImageView 资源
     * @param view  {@link View}
     * @param resId resource identifier
     * @return Helper
     */
    T setImageResource(
            View view,
            @DrawableRes int resId
    );

    /**
     * 设置 ImageView Matrix
     * @param view   {@link View}
     * @param matrix {@link Matrix}
     * @return Helper
     */
    T setImageMatrix(
            View view,
            Matrix matrix
    );

    /**
     * 设置 ImageView 着色颜色
     * @param view {@link View}
     * @param tint 着色颜色
     * @return Helper
     */
    T setImageTintList(
            View view,
            ColorStateList tint
    );

    /**
     * 设置 ImageView 着色模式
     * @param view     {@link View}
     * @param tintMode 着色模式 {@link PorterDuff.Mode}
     * @return Helper
     */
    T setImageTintMode(
            View view,
            PorterDuff.Mode tintMode
    );

    /**
     * 设置 ImageView 缩放类型
     * @param view      {@link View}
     * @param scaleType 缩放类型 {@link ImageView.ScaleType}
     * @return Helper
     */
    T setScaleType(
            View view,
            ImageView.ScaleType scaleType
    );

    /**
     * ImageView 着色处理
     * @param view  {@link View}
     * @param color 颜色值
     * @return Helper
     */
    T setColorFilter(
            View view,
            @ColorInt int color
    );

    /**
     * ImageView 着色处理, 并且设置 Drawable
     * @param view     {@link View}
     * @param drawable {@link Drawable}
     * @param color    颜色值
     * @return Helper
     */
    T setColorFilter(
            View view,
            Drawable drawable,
            @ColorInt int color
    );

    /**
     * ImageView 着色处理
     * @param view        {@link View}
     * @param colorFilter 颜色过滤 ( 效果 )
     * @return Helper
     */
    T setColorFilter(
            View view,
            ColorFilter colorFilter
    );

    /**
     * ImageView 着色处理, 并且设置 Drawable
     * @param view        {@link View}
     * @param drawable    {@link Drawable}
     * @param colorFilter 颜色过滤 ( 效果 )
     * @return Helper
     */
    T setColorFilter(
            View view,
            Drawable drawable,
            ColorFilter colorFilter
    );

    /**
     * 设置 View 图片资源
     * @param resId resource identifier
     * @param views View[]
     * @return Helper
     */
    T setBackgroundResources(
            @DrawableRes int resId,
            View... views
    );

    /**
     * 设置 View 图片资源
     * @param resId        resource identifier
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param views        View[]
     * @return Helper
     */
    T setBackgroundResources(
            @DrawableRes int resId,
            int isVisibility,
            View... views
    );

    /**
     * 设置 View 图片资源
     * @param resId resource identifier
     * @param views View[]
     * @return Helper
     */
    T setImageResources(
            @DrawableRes int resId,
            View... views
    );

    /**
     * 设置 View 图片资源
     * @param resId        resource identifier
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param views        View[]
     * @return Helper
     */
    T setImageResources(
            @DrawableRes int resId,
            int isVisibility,
            View... views
    );

    /**
     * 设置 View Bitmap
     * @param bitmap {@link Bitmap}
     * @param views  View[]
     * @return Helper
     */
    T setImageBitmaps(
            Bitmap bitmap,
            View... views
    );

    /**
     * 设置 View Bitmap
     * @param bitmap       {@link Bitmap}
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param views        View[]
     * @return Helper
     */
    T setImageBitmaps(
            Bitmap bitmap,
            int isVisibility,
            View... views
    );

    /**
     * 设置 View Drawable
     * @param drawable {@link drawable}
     * @param views    View[]
     * @return Helper
     */
    T setImageDrawables(
            Drawable drawable,
            View... views
    );

    /**
     * 设置 View Drawable
     * @param drawable     {@link drawable}
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param views        View[]
     * @return Helper
     */
    T setImageDrawables(
            Drawable drawable,
            int isVisibility,
            View... views
    );

    /**
     * 设置 View 缩放模式
     * @param scaleType {@link ImageView.ScaleType}
     * @param views     View[]
     * @return Helper
     */
    T setScaleTypes(
            ImageView.ScaleType scaleType,
            View... views
    );

    /**
     * 设置 View 缩放模式
     * @param scaleType    {@link ImageView.ScaleType}
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param views        View[]
     * @return Helper
     */
    T setScaleTypes(
            ImageView.ScaleType scaleType,
            int isVisibility,
            View... views
    );

    // ========
    // = View =
    // ========

    /**
     * 设置 View 宽度、高度
     * @param view   {@link View}
     * @param width  View 宽度
     * @param height View 高度
     * @return Helper
     */
    T setWidthHeight(
            View view,
            int width,
            int height
    );

    /**
     * 设置 View 宽度、高度
     * @param view      {@link View}
     * @param width     View 宽度
     * @param height    View 高度
     * @param nullNewLP 如果 LayoutParams 为 null 是否创建新的
     * @return Helper
     */
    T setWidthHeight(
            View view,
            int width,
            int height,
            boolean nullNewLP
    );

    /**
     * 设置 View 宽度
     * @param view  {@link View}
     * @param width View 宽度
     * @return Helper
     */
    T setWidth(
            View view,
            int width
    );

    /**
     * 设置 View 宽度
     * @param view      {@link View}
     * @param width     View 宽度
     * @param nullNewLP 如果 LayoutParams 为 null 是否创建新的
     * @return Helper
     */
    T setWidth(
            View view,
            int width,
            boolean nullNewLP
    );

    /**
     * 设置 View 高度
     * @param view   {@link View}
     * @param height View 高度
     * @return Helper
     */
    T setHeight(
            View view,
            int height
    );

    /**
     * 设置 View 高度
     * @param view      {@link View}
     * @param height    View 高度
     * @param nullNewLP 如果 LayoutParams 为 null 是否创建新的
     * @return Helper
     */
    T setHeight(
            View view,
            int height,
            boolean nullNewLP
    );

    /**
     * 设置 View 最小高度
     * @param view      {@link View}
     * @param minHeight 最小高度
     * @return Helper
     */
    T setMinimumHeight(
            View view,
            int minHeight
    );

    /**
     * 设置 View 最小宽度
     * @param view     {@link View}
     * @param minWidth 最小宽度
     * @return Helper
     */
    T setMinimumWidth(
            View view,
            int minWidth
    );

    /**
     * 设置 View 透明度
     * @param view  {@link View}
     * @param alpha 透明度
     * @return Helper
     */
    T setAlpha(
            View view,
            @FloatRange(from = 0.0, to = 1.0) float alpha
    );

    /**
     * 设置 View Tag
     * @param view   {@link View}
     * @param object Tag
     * @return Helper
     */
    T setTag(
            View view,
            Object object
    );

    // =

    /**
     * View 内容滚动位置 ( 相对于初始位置移动 )
     * <pre>
     *     无滚动过程
     * </pre>
     * @param view {@link View}
     * @param x    X 轴开始坐标
     * @param y    Y 轴开始坐标
     * @return Helper
     */
    T scrollTo(
            View view,
            int x,
            int y
    );

    /**
     * View 内部滚动位置 ( 相对于上次移动的最后位置移动 )
     * <pre>
     *     无滚动过程
     * </pre>
     * @param view {@link View}
     * @param x    X 轴开始坐标
     * @param y    Y 轴开始坐标
     * @return Helper
     */
    T scrollBy(
            View view,
            int x,
            int y
    );

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
     * @return Helper
     */
    T setDescendantFocusability(
            ViewGroup viewGroup,
            int focusability
    );

    /**
     * 设置 View 滚动模式
     * <pre>
     *     设置滑动到边缘时无效果模式 {@link View#OVER_SCROLL_NEVER}
     *     android:overScrollMode="never"
     * </pre>
     * @param view           {@link View}
     * @param overScrollMode {@link View#OVER_SCROLL_ALWAYS}、{@link View#OVER_SCROLL_IF_CONTENT_SCROLLS}、{@link View#OVER_SCROLL_NEVER}
     * @return Helper
     */
    T setOverScrollMode(
            View view,
            int overScrollMode
    );

    // =

    /**
     * 设置是否绘制横向滚动条
     * @param view                       {@link View}
     * @param horizontalScrollBarEnabled {@code true} yes, {@code false} no
     * @return Helper
     */
    T setHorizontalScrollBarEnabled(
            View view,
            boolean horizontalScrollBarEnabled
    );

    /**
     * 设置是否绘制垂直滚动条
     * @param view                     {@link View}
     * @param verticalScrollBarEnabled {@code true} yes, {@code false} no
     * @return Helper
     */
    T setVerticalScrollBarEnabled(
            View view,
            boolean verticalScrollBarEnabled
    );

    // =

    /**
     * 设置 View 滚动效应
     * @param view              {@link View}
     * @param isScrollContainer 是否需要滚动效应
     * @return Helper
     */
    T setScrollContainer(
            View view,
            boolean isScrollContainer
    );

    /**
     * 设置下一个获取焦点的 View id
     * @param view               {@link View}
     * @param nextFocusForwardId 下一个获取焦点的 View id
     * @return Helper
     */
    T setNextFocusForwardId(
            View view,
            @IdRes int nextFocusForwardId
    );

    /**
     * 设置向下移动焦点时, 下一个获取焦点的 View id
     * @param view            {@link View}
     * @param nextFocusDownId 下一个获取焦点的 View id
     * @return Helper
     */
    T setNextFocusDownId(
            View view,
            @IdRes int nextFocusDownId
    );

    /**
     * 设置向左移动焦点时, 下一个获取焦点的 View id
     * @param view            {@link View}
     * @param nextFocusLeftId 下一个获取焦点的 View id
     * @return Helper
     */
    T setNextFocusLeftId(
            View view,
            @IdRes int nextFocusLeftId
    );

    /**
     * 设置向右移动焦点时, 下一个获取焦点的 View id
     * @param view             {@link View}
     * @param nextFocusRightId 下一个获取焦点的 View id
     * @return Helper
     */
    T setNextFocusRightId(
            View view,
            @IdRes int nextFocusRightId
    );

    /**
     * 设置向上移动焦点时, 下一个获取焦点的 View id
     * @param view          {@link View}
     * @param nextFocusUpId 下一个获取焦点的 View id
     * @return Helper
     */
    T setNextFocusUpId(
            View view,
            @IdRes int nextFocusUpId
    );

    /**
     * 设置 View 旋转度数
     * @param view     {@link View}
     * @param rotation 旋转度数
     * @return Helper
     */
    T setRotation(
            View view,
            float rotation
    );

    /**
     * 设置 View 水平旋转度数
     * @param view      {@link View}
     * @param rotationX 水平旋转度数
     * @return Helper
     */
    T setRotationX(
            View view,
            float rotationX
    );

    /**
     * 设置 View 竖直旋转度数
     * @param view      {@link View}
     * @param rotationY 竖直旋转度数
     * @return Helper
     */
    T setRotationY(
            View view,
            float rotationY
    );

    /**
     * 设置 View 水平方向缩放比例
     * @param view   {@link View}
     * @param scaleX 水平方向缩放比例
     * @return Helper
     */
    T setScaleX(
            View view,
            float scaleX
    );

    /**
     * 设置 View 竖直方向缩放比例
     * @param view   {@link View}
     * @param scaleY 竖直方向缩放比例
     * @return Helper
     */
    T setScaleY(
            View view,
            float scaleY
    );

    /**
     * 设置文本的显示方式
     * @param view          {@link View}
     * @param textAlignment 文本的显示方式
     * @return Helper
     */
    T setTextAlignment(
            View view,
            int textAlignment
    );

    /**
     * 设置文本的显示方向
     * @param view          {@link View}
     * @param textDirection 文本的显示方向
     * @return Helper
     */
    T setTextDirection(
            View view,
            int textDirection
    );

    /**
     * 设置水平方向偏转量
     * @param view   {@link View}
     * @param pivotX 水平方向偏转量
     * @return Helper
     */
    T setPivotX(
            View view,
            float pivotX
    );

    /**
     * 设置竖直方向偏转量
     * @param view   {@link View}
     * @param pivotY 竖直方向偏转量
     * @return Helper
     */
    T setPivotY(
            View view,
            float pivotY
    );

    /**
     * 设置水平方向的移动距离
     * @param view         {@link View}
     * @param translationX 水平方向的移动距离
     * @return Helper
     */
    T setTranslationX(
            View view,
            float translationX
    );

    /**
     * 设置竖直方向的移动距离
     * @param view         {@link View}
     * @param translationY 竖直方向的移动距离
     * @return Helper
     */
    T setTranslationY(
            View view,
            float translationY
    );

    /**
     * 设置 View 硬件加速类型
     * @param view      {@link View}
     * @param layerType 硬件加速类型
     * @param paint     {@link Paint}
     * @return Helper
     */
    T setLayerType(
            View view,
            int layerType,
            Paint paint
    );

    // =

    /**
     * 请求重新对 View 布局
     * @param view {@link View}
     * @return Helper
     */
    T requestLayout(View view);

    /**
     * View 请求获取焦点
     * @param view {@link View}
     * @return Helper
     */
    T requestFocus(View view);

    /**
     * View 清除焦点
     * @param view {@link View}
     * @return Helper
     */
    T clearFocus(View view);

    /**
     * View 请求更新
     * @param view      {@link View}
     * @param allParent 是否全部父布局 View 都请求
     * @return Helper
     */
    T requestLayoutParent(
            View view,
            boolean allParent
    );

    // =

    /**
     * 设置 View Id
     * @param view {@link View}
     * @param id   View Id
     * @return Helper
     */
    T setId(
            View view,
            int id
    );

    /**
     * 设置是否限制子 View 在其边界内绘制
     * @param viewGroup    {@link ViewGroup}
     * @param clipChildren {@code true} yes, {@code false} no
     * @return Helper
     */
    T setClipChildren(
            ViewGroup viewGroup,
            boolean clipChildren
    );

    /**
     * 设置 View LayoutParams
     * @param view   {@link View}
     * @param params LayoutParams
     * @return Helper
     */
    T setLayoutParams(
            View view,
            ViewGroup.LayoutParams params
    );

    /**
     * 设置 View 是否在触摸模式下获得焦点
     * @param focusableInTouchMode {@code true} 可获取, {@code false} 不可获取
     * @param views                View[]
     * @return Helper
     */
    T setFocusableInTouchMode(
            boolean focusableInTouchMode,
            View... views
    );

    /**
     * 设置 View 是否可以获取焦点
     * @param focusable {@code true} 可获取, {@code false} 不可获取
     * @param views     View[]
     * @return Helper
     */
    T setFocusable(
            boolean focusable,
            View... views
    );

    /**
     * 切换获取焦点状态
     * @param views View[]
     * @return Helper
     */
    T toggleFocusable(View... views);

    /**
     * 设置 View 是否选中
     * @param selected {@code true} 选中, {@code false} 非选中
     * @param views    View[]
     * @return {@code true} 选中, {@code false} 非选中
     */
    T setSelected(
            boolean selected,
            View... views
    );

    /**
     * 切换选中状态
     * @param views View[]
     * @return Helper
     */
    T toggleSelected(View... views);

    /**
     * 设置 View 是否启用
     * @param enabled {@code true} 启用, {@code false} 禁用
     * @param views   View[]
     * @return Helper
     */
    T setEnabled(
            boolean enabled,
            View... views
    );

    /**
     * 切换 View 是否启用状态
     * @param views View[]
     * @return Helper
     */
    T toggleEnabled(View... views);

    /**
     * 设置 View 是否可以点击
     * @param clickable {@code true} 可点击, {@code false} 不可点击
     * @param views     View[]
     * @return Helper
     */
    T setClickable(
            boolean clickable,
            View... views
    );

    /**
     * 切换 View 是否可以点击状态
     * @param views View[]
     * @return Helper
     */
    T toggleClickable(View... views);

    /**
     * 设置 View 是否可以长按
     * @param longClickable {@code true} 可长按, {@code false} 不可长按
     * @param views         View[]
     * @return Helper
     */
    T setLongClickable(
            boolean longClickable,
            View... views
    );

    /**
     * 切换 View 是否可以长按状态
     * @param views View[]
     * @return Helper
     */
    T toggleLongClickable(View... views);

    /**
     * 设置 View 显示的状态
     * @param isVisibility {@code true} View.VISIBLE, {@code false} View.GONE
     * @param view         {@link View}
     * @return Helper
     */
    T setVisibility(
            boolean isVisibility,
            View view
    );

    /**
     * 设置 View 显示的状态
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param view         {@link View}
     * @return Helper
     */
    T setVisibility(
            int isVisibility,
            View view
    );

    /**
     * 设置 View 显示的状态
     * @param isVisibility {@code true} View.VISIBLE, {@code false} View.INVISIBLE
     * @param view         {@link View}
     * @return Helper
     */
    T setVisibilityIN(
            boolean isVisibility,
            View view
    );

    /**
     * 设置 View 显示的状态
     * @param isVisibility {@code true} View.VISIBLE, {@code false} View.GONE
     * @param views        View[]
     * @return Helper
     */
    T setVisibilitys(
            boolean isVisibility,
            View... views
    );

    /**
     * 设置 View 显示的状态
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param views        View[]
     * @return Helper
     */
    T setVisibilitys(
            int isVisibility,
            View... views
    );

    /**
     * 设置 View 显示的状态
     * @param isVisibility {@code true} View.VISIBLE, {@code false} View.INVISIBLE
     * @param views        View[]
     * @return Helper
     */
    T setVisibilityINs(
            boolean isVisibility,
            View... views
    );

    /**
     * 切换 View 显示的状态
     * @param view  {@link View}
     * @param views View[]
     * @return Helper
     */
    T toggleVisibilitys(
            View view,
            View... views
    );

    /**
     * 切换 View 显示的状态
     * @param viewArrays View[]
     * @param views      View[]
     * @return Helper
     */
    T toggleVisibilitys(
            View[] viewArrays,
            View... views
    );

    /**
     * 切换 View 显示的状态
     * @param state      {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param viewArrays View[]
     * @param views      View[]
     * @return Helper
     */
    T toggleVisibilitys(
            int state,
            View[] viewArrays,
            View... views
    );

    /**
     * 反转 View 显示的状态
     * @param state      {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param viewArrays View[]
     * @param views      View[]
     * @return Helper
     */
    T reverseVisibilitys(
            int state,
            View[] viewArrays,
            View... views
    );

    /**
     * 反转 View 显示的状态
     * @param isVisibility {@code true} View.VISIBLE, {@code false} View.GONE
     * @param viewArrays   View[]
     * @param views        View[]
     * @return Helper
     */
    T reverseVisibilitys(
            boolean isVisibility,
            View[] viewArrays,
            View... views
    );

    /**
     * 反转 View 显示的状态
     * @param state {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param view  {@link View}
     * @param views View[]
     * @return Helper
     */
    T reverseVisibilitys(
            int state,
            View view,
            View... views
    );

    /**
     * 反转 View 显示的状态
     * @param isVisibility {@code true} View.VISIBLE, {@code false} View.GONE
     * @param view         {@link View}
     * @param views        View[]
     * @return Helper
     */
    T reverseVisibilitys(
            boolean isVisibility,
            View view,
            View... views
    );

    /**
     * 把自身从父 View 中移除
     * @param view {@link View}
     * @return Helper
     */
    T removeSelfFromParent(View view);

    /**
     * 设置 View Layout Gravity
     * @param view    {@link View}
     * @param gravity Gravity
     * @return Helper
     */
    T setLayoutGravity(
            View view,
            int gravity
    );

    /**
     * 设置 View Layout Gravity
     * @param view         {@link View}
     * @param gravity      Gravity
     * @param isReflection 是否使用反射
     * @return {@link QuickHelper}
     */
    T setLayoutGravity(
            View view,
            int gravity,
            boolean isReflection
    );

    /**
     * 设置 View Left Margin
     * @param view       {@link View}
     * @param leftMargin Left Margin
     * @return Helper
     */
    T setMarginLeft(
            View view,
            int leftMargin
    );

    /**
     * 设置 View Left Margin
     * @param view       {@link View}
     * @param leftMargin Left Margin
     * @param reset      是否重置清空其他 margin
     * @return Helper
     */
    T setMarginLeft(
            View view,
            int leftMargin,
            boolean reset
    );

    /**
     * 设置 View Top Margin
     * @param view      {@link View}
     * @param topMargin Top Margin
     * @return Helper
     */
    T setMarginTop(
            View view,
            int topMargin
    );

    /**
     * 设置 View Top Margin
     * @param view      {@link View}
     * @param topMargin Top Margin
     * @param reset     是否重置清空其他 margin
     * @return Helper
     */
    T setMarginTop(
            View view,
            int topMargin,
            boolean reset
    );

    /**
     * 设置 View Right Margin
     * @param view        {@link View}
     * @param rightMargin Right Margin
     * @return Helper
     */
    T setMarginRight(
            View view,
            int rightMargin
    );

    /**
     * 设置 View Right Margin
     * @param view        {@link View}
     * @param rightMargin Right Margin
     * @param reset       是否重置清空其他 margin
     * @return Helper
     */
    T setMarginRight(
            View view,
            int rightMargin,
            boolean reset
    );

    /**
     * 设置 View Bottom Margin
     * @param view         {@link View}
     * @param bottomMargin Bottom Margin
     * @return Helper
     */
    T setMarginBottom(
            View view,
            int bottomMargin
    );

    /**
     * 设置 View Bottom Margin
     * @param view         {@link View}
     * @param bottomMargin Bottom Margin
     * @param reset        是否重置清空其他 margin
     * @return Helper
     */
    T setMarginBottom(
            View view,
            int bottomMargin,
            boolean reset
    );

    /**
     * 设置 Margin 边距
     * @param view      {@link View}
     * @param leftRight Left and Right Margin
     * @param topBottom Top and bottom Margin
     * @return Helper
     */
    T setMargin(
            View view,
            int leftRight,
            int topBottom
    );

    /**
     * 设置 Margin 边距
     * @param view   {@link View}
     * @param margin Margin
     * @return Helper
     */
    T setMargin(
            View view,
            int margin
    );

    /**
     * 设置 Margin 边距
     * @param view   {@link View}
     * @param left   Left Margin
     * @param top    Top Margin
     * @param right  Right Margin
     * @param bottom Bottom Margin
     * @return Helper
     */
    T setMargin(
            View view,
            int left,
            int top,
            int right,
            int bottom
    );

    /**
     * 设置 Margin 边距
     * @param views     View[]
     * @param leftRight Left and Right Margin
     * @param topBottom Top and bottom Margin
     * @return Helper
     */
    T setMargin(
            View[] views,
            int leftRight,
            int topBottom
    );

    /**
     * 设置 Margin 边距
     * @param views  View[]
     * @param margin Margin
     * @return Helper
     */
    T setMargin(
            View[] views,
            int margin
    );

    /**
     * 设置 Margin 边距
     * @param views  View[]
     * @param left   Left Margin
     * @param top    Top Margin
     * @param right  Right Margin
     * @param bottom Bottom Margin
     * @return Helper
     */
    T setMargin(
            View[] views,
            int left,
            int top,
            int right,
            int bottom
    );

    /**
     * 设置 View Left Padding
     * @param view        {@link View}
     * @param leftPadding Left Padding
     * @return Helper
     */
    T setPaddingLeft(
            View view,
            int leftPadding
    );

    /**
     * 设置 View Left Padding
     * @param view        {@link View}
     * @param leftPadding Left Padding
     * @param reset       是否重置清空其他 Padding
     * @return Helper
     */
    T setPaddingLeft(
            View view,
            int leftPadding,
            boolean reset
    );

    /**
     * 设置 View Top Padding
     * @param view       {@link View}
     * @param topPadding Top Padding
     * @return Helper
     */
    T setPaddingTop(
            View view,
            int topPadding
    );

    /**
     * 设置 View Top Padding
     * @param view       {@link View}
     * @param topPadding Top Padding
     * @param reset      是否重置清空其他 Padding
     * @return Helper
     */
    T setPaddingTop(
            View view,
            int topPadding,
            boolean reset
    );

    /**
     * 设置 View Right Padding
     * @param view         {@link View}
     * @param rightPadding Right Padding
     * @return Helper
     */
    T setPaddingRight(
            View view,
            int rightPadding
    );

    /**
     * 设置 View Right Padding
     * @param view         {@link View}
     * @param rightPadding Right Padding
     * @param reset        是否重置清空其他 Padding
     * @return Helper
     */
    T setPaddingRight(
            View view,
            int rightPadding,
            boolean reset
    );

    /**
     * 设置 View Bottom Padding
     * @param view          {@link View}
     * @param bottomPadding Bottom Padding
     * @return Helper
     */
    T setPaddingBottom(
            View view,
            int bottomPadding
    );

    /**
     * 设置 View Bottom Padding
     * @param view          {@link View}
     * @param bottomPadding Bottom Padding
     * @param reset         是否重置清空其他 Padding
     * @return Helper
     */
    T setPaddingBottom(
            View view,
            int bottomPadding,
            boolean reset
    );

    /**
     * 设置 Padding 边距
     * @param view      {@link View}
     * @param leftRight Left and Right Padding
     * @param topBottom Top and bottom Padding
     * @return Helper
     */
    T setPadding(
            View view,
            int leftRight,
            int topBottom
    );

    /**
     * 设置 Padding 边距
     * @param view    {@link View}
     * @param padding Padding
     * @return Helper
     */
    T setPadding(
            View view,
            int padding
    );

    /**
     * 设置 Padding 边距
     * @param view   {@link View}
     * @param left   Left Padding
     * @param top    Top Padding
     * @param right  Right Padding
     * @param bottom Bottom Padding
     * @return Helper
     */
    T setPadding(
            View view,
            int left,
            int top,
            int right,
            int bottom
    );

    /**
     * 设置 Padding 边距
     * @param views     View[]
     * @param leftRight Left and Right Padding
     * @param topBottom Top and bottom Padding
     * @return Helper
     */
    T setPadding(
            View[] views,
            int leftRight,
            int topBottom
    );

    /**
     * 设置 Padding 边距
     * @param views   View[]
     * @param padding Padding
     * @return Helper
     */
    T setPadding(
            View[] views,
            int padding
    );

    /**
     * 设置 Padding 边距
     * @param views  View[]
     * @param left   Left Padding
     * @param top    Top Padding
     * @param right  Right Padding
     * @param bottom Bottom Padding
     * @return Helper
     */
    T setPadding(
            View[] views,
            int left,
            int top,
            int right,
            int bottom
    );

    /**
     * 设置 CompoundDrawables Padding
     * @param textView {@link TextView}
     * @param padding  CompoundDrawables Padding
     * @return Helper
     */
    T setCompoundDrawablePadding(
            TextView textView,
            int padding
    );

    /**
     * 设置 Left CompoundDrawables
     * @param textView {@link TextView}
     * @param left     left Drawable
     * @return Helper
     */
    T setCompoundDrawablesByLeft(
            TextView textView,
            Drawable left
    );

    /**
     * 设置 Top CompoundDrawables
     * @param textView {@link TextView}
     * @param top      top Drawable
     * @return Helper
     */
    T setCompoundDrawablesByTop(
            TextView textView,
            Drawable top
    );

    /**
     * 设置 Right CompoundDrawables
     * @param textView {@link TextView}
     * @param right    right Drawable
     * @return Helper
     */
    T setCompoundDrawablesByRight(
            TextView textView,
            Drawable right
    );

    /**
     * 设置 Bottom CompoundDrawables
     * @param textView {@link TextView}
     * @param bottom   bottom Drawable
     * @return Helper
     */
    T setCompoundDrawablesByBottom(
            TextView textView,
            Drawable bottom
    );

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
     * @return Helper
     */
    T setCompoundDrawables(
            TextView textView,
            Drawable left,
            Drawable top,
            Drawable right,
            Drawable bottom
    );

    /**
     * 设置 Left CompoundDrawables ( 按照原有比例大小显示图片 )
     * @param textView {@link TextView}
     * @param left     left Drawable
     * @return Helper
     */
    T setCompoundDrawablesWithIntrinsicBoundsByLeft(
            TextView textView,
            Drawable left
    );

    /**
     * 设置 Top CompoundDrawables ( 按照原有比例大小显示图片 )
     * @param textView {@link TextView}
     * @param top      top Drawable
     * @return Helper
     */
    T setCompoundDrawablesWithIntrinsicBoundsByTop(
            TextView textView,
            Drawable top
    );

    /**
     * 设置 Right CompoundDrawables ( 按照原有比例大小显示图片 )
     * @param textView {@link TextView}
     * @param right    right Drawable
     * @return Helper
     */
    T setCompoundDrawablesWithIntrinsicBoundsByRight(
            TextView textView,
            Drawable right
    );

    /**
     * 设置 Bottom CompoundDrawables ( 按照原有比例大小显示图片 )
     * @param textView {@link TextView}
     * @param bottom   bottom Drawable
     * @return Helper
     */
    T setCompoundDrawablesWithIntrinsicBoundsByBottom(
            TextView textView,
            Drawable bottom
    );

    /**
     * 设置 CompoundDrawables ( 按照原有比例大小显示图片 )
     * @param textView {@link TextView}
     * @param left     left Drawable
     * @param top      top Drawable
     * @param right    right Drawable
     * @param bottom   bottom Drawable
     * @return Helper
     */
    T setCompoundDrawablesWithIntrinsicBounds(
            TextView textView,
            Drawable left,
            Drawable top,
            Drawable right,
            Drawable bottom
    );

    // ==================
    // = RelativeLayout =
    // ==================

    /**
     * 设置 RelativeLayout View 布局规则
     * @param view {@link View}
     * @param verb 布局位置
     * @return Helper
     */
    T addRule(
            View view,
            int verb
    );

    /**
     * 设置 RelativeLayout View 布局规则
     * @param view    {@link View}
     * @param verb    布局位置
     * @param subject 关联 View id
     * @return Helper
     */
    T addRule(
            View view,
            int verb,
            int subject
    );

    /**
     * 移除 RelativeLayout View 布局规则
     * @param view {@link View}
     * @param verb 布局位置
     * @return Helper
     */
    T removeRule(
            View view,
            int verb
    );

    // =

    /**
     * 设置多个 RelativeLayout View 布局规则
     * @param verb  布局位置
     * @param views View[]
     * @return Helper
     */
    T addRules(
            int verb,
            View... views
    );

    /**
     * 设置多个 RelativeLayout View 布局规则
     * @param verb    布局位置
     * @param subject 关联 View id
     * @param views   View[]
     * @return Helper
     */
    T addRules(
            int verb,
            int subject,
            View... views
    );

    /**
     * 移除多个 RelativeLayout View 布局规则
     * @param verb  布局位置
     * @param views View[]
     * @return Helper
     */
    T removeRules(
            int verb,
            View... views
    );

    // =============
    // = Animation =
    // =============

    /**
     * 设置动画
     * @param view      {@link View}
     * @param animation {@link Animation}
     * @return Helper
     */
    T setAnimation(
            View view,
            Animation animation
    );

    /**
     * 清空动画
     * @param view {@link View}
     * @return Helper
     */
    T clearAnimation(View view);

    /**
     * 启动动画
     * @param view      {@link View}
     * @param animation {@link Animation}
     * @return Helper
     */
    T startAnimation(
            View view,
            Animation animation
    );

    /**
     * 取消动画
     * @param view {@link View}
     * @return Helper
     */
    T cancelAnimation(View view);

    // ============
    // = Listener =
    // ============

    /**
     * 设置点击事件
     * @param onClickListener {@link View.OnClickListener}
     * @param views           View 数组
     * @return Helper
     */
    T setOnClicks(
            View.OnClickListener onClickListener,
            View... views
    );

    /**
     * 设置长按事件
     * @param onLongClickListener {@link View.OnLongClickListener}
     * @param views               View 数组
     * @return Helper
     */
    T setOnLongClicks(
            View.OnLongClickListener onLongClickListener,
            View... views
    );

    /**
     * 设置触摸事件
     * @param onTouchListener {@link View.OnTouchListener}
     * @param views           View 数组
     * @return Helper
     */
    T setOnTouchs(
            View.OnTouchListener onTouchListener,
            View... views
    );

    /**
     * 增加控件的触摸范围, 最大范围只能是父布局所包含的的区域
     * @param view  待添加点击范围 View
     * @param range 点击范围
     * @return Helper
     */
    T addTouchArea(
            View view,
            int range
    );

    /**
     * 增加控件的触摸范围, 最大范围只能是父布局所包含的的区域
     * @param view   待添加点击范围 View
     * @param top    top range
     * @param bottom bottom range
     * @param left   left range
     * @param right  right range
     * @return Helper
     */
    T addTouchArea(
            View view,
            int top,
            int bottom,
            int left,
            int right
    );

    // =================
    // = ListViewUtils =
    // =================

    /**
     * 滑动到指定索引 ( 有滚动过程 )
     * @param view     {@link View}
     * @param position 索引
     * @return Helper
     */
    T smoothScrollToPosition(
            View view,
            int position
    );

    /**
     * 滑动到指定索引 ( 无滚动过程 )
     * @param view     {@link View}
     * @param position 索引
     * @return Helper
     */
    T scrollToPosition(
            View view,
            int position
    );

    // ============
    // = 滑动到顶部 =
    // ============

    /**
     * 滑动到顶部 ( 有滚动过程 )
     * @param view {@link View}
     * @return Helper
     */
    T smoothScrollToTop(View view);

    /**
     * 滑动到顶部 ( 无滚动过程 )
     * @param view {@link View}
     * @return Helper
     */
    T scrollToTop(View view);

    // ============
    // = 滑动到底部 =
    // ============

    /**
     * 滑动到底部 ( 有滚动过程 )
     * <pre>
     *     如果未到达底部 ( position 可以再加上 smoothScrollBy 搭配到底部 )
     *     smoothScrollToBottom(view)
     *     smoothScrollBy(view, 0, Integer.MAX_VALUE);
     * </pre>
     * @param view {@link View}
     * @return Helper
     */
    T smoothScrollToBottom(View view);

    /**
     * 滑动到底部 ( 无滚动过程 )
     * <pre>
     *     如果未到达底部 ( position 可以再加上 scrollBy 搭配到底部 )
     *     scrollToBottom(view)
     *     scrollBy(view, 0, Integer.MAX_VALUE);
     * </pre>
     * @param view {@link View}
     * @return Helper
     */
    T scrollToBottom(View view);

    // ==============
    // = ScrollView =
    // ==============

    /**
     * 滚动到指定位置 ( 有滚动过程, 相对于初始位置移动 )
     * @param view {@link View}
     * @param x    X 轴开始坐标
     * @param y    Y 轴开始坐标
     * @return Helper
     */
    T smoothScrollTo(
            View view,
            int x,
            int y
    );

    /**
     * 滚动到指定位置 ( 有滚动过程, 相对于上次移动的最后位置移动 )
     * @param view {@link View}
     * @param x    X 轴开始坐标
     * @param y    Y 轴开始坐标
     * @return Helper
     */
    T smoothScrollBy(
            View view,
            int x,
            int y
    );

    /**
     * 滚动方向 ( 有滚动过程 )
     * @param view      {@link View}
     * @param direction 滚动方向 如: View.FOCUS_UP、View.FOCUS_DOWN
     * @return Helper
     */
    T fullScroll(
            View view,
            int direction
    );

    // ===============
    // = ProgressBar =
    // ===============

    /**
     * 设置 ProgressBar 进度条样式
     * @param view     {@link View}
     * @param drawable {@link Drawable}
     * @return {@link View}
     */
    T setProgressDrawable(
            View view,
            Drawable drawable
    );

    /**
     * 设置 ProgressBar 进度值
     * @param view     {@link View}
     * @param progress 当前进度
     * @return Helper
     */
    T setBarProgress(
            View view,
            int progress
    );

    /**
     * 设置 ProgressBar 最大值
     * @param view {@link View}
     * @param max  最大值
     * @return Helper
     */
    T setBarMax(
            View view,
            int max
    );

    /**
     * 设置 ProgressBar 最大值
     * @param view     {@link View}
     * @param progress 当前进度
     * @param max      最大值
     * @return Helper
     */
    T setBarValue(
            View view,
            int progress,
            int max
    );
}