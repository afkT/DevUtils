package dev.widget.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;
import android.os.Build;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ColorInt;

import dev.utils.app.SizeUtils;
import dev.widget.R;

/**
 * detail: 自定义角标 View
 * @author Ttt
 * <pre>
 *     <p></p>
 *     app:dev_paddingTop=""
 *     app:dev_paddingCenter=""
 *     app:dev_paddingBottom=""
 *     app:dev_fillColor=""
 *     app:dev_flags=""
 *     <p></p>
 *     app:dev_text1=""
 *     app:dev_textColor1=""
 *     app:dev_textHeight1=""
 *     app:dev_textBold1=""
 *     <p></p>
 *     app:dev_text2=""
 *     app:dev_textColor2=""
 *     app:dev_textHeight2=""
 *     app:dev_textBold2=""
 * </pre>
 */
public class CornerLabelView
        extends View {

    // 三角形斜边长
    private static final float   SQRT2          = (float) Math.sqrt(2);
    // 内部画笔
    private              Paint   mPaint;
    // Text1 Paint
    private final        Painter mText1         = new Painter();
    // Text2 Paint
    private final        Painter mText2         = new Painter();
    // 顶部边距
    private              float   mPaddingTop    = 0;
    // Text1、Text2 边距
    private              float   mPaddingCenter = 0;
    // 底部边距
    private              float   mPaddingBottom = 0;
    // 是否左边显示角标
    private              boolean mLeft          = true;
    // 是否顶部显示角标
    private              boolean mTop           = true;
    // 是否角标三角形铺满样式
    private              boolean mTriangle      = true;
    // 当前 View 高度
    private              int     mHeight;

    public CornerLabelView(Context context) {
        super(context);
        initAttrs(context, null, 0, 0);
    }

    public CornerLabelView(
            Context context,
            AttributeSet attrs
    ) {
        super(context, attrs);
        initAttrs(context, attrs, 0, 0);
    }

    public CornerLabelView(
            Context context,
            AttributeSet attrs,
            int defStyleAttr
    ) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CornerLabelView(
            Context context,
            AttributeSet attrs,
            int defStyleAttr,
            int defStyleRes
    ) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttrs(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * 初始化
     * @param context      {@link Context}
     * @param attrs        {@link AttributeSet}
     * @param defStyleAttr 默认样式
     * @param defStyleRes  默认样式资源
     */
    private void initAttrs(
            Context context,
            AttributeSet attrs,
            int defStyleAttr,
            int defStyleRes
    ) {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        mPaint.setAntiAlias(true);

        if (context != null && attrs != null) {
            TypedArray a = context.obtainStyledAttributes(
                    attrs, R.styleable.DevWidget, defStyleAttr, defStyleRes
            );

            mPaddingTop    = a.getDimension(R.styleable.DevWidget_dev_paddingTop, SizeUtils.dp2px(getContext(), 10));
            mPaddingCenter = a.getDimension(R.styleable.DevWidget_dev_paddingCenter, 0);
            mPaddingBottom = a.getDimension(R.styleable.DevWidget_dev_paddingBottom, SizeUtils.dp2px(getContext(), 3));

            mText1.text       = a.getString(R.styleable.DevWidget_dev_text1);
            mText1.textColor  = a.getColor(R.styleable.DevWidget_dev_textColor1, 0xffffffff);
            mText1.textHeight = a.getDimension(R.styleable.DevWidget_dev_textHeight1, SizeUtils.dp2px(getContext(), 12));
            mText1.textBold   = a.getBoolean(R.styleable.DevWidget_dev_textBold1, false);
            mText1.initialize();

            mText2.text       = a.getString(R.styleable.DevWidget_dev_text2);
            mText2.textColor  = a.getColor(R.styleable.DevWidget_dev_textColor2, 0x66ffffff);
            mText2.textHeight = a.getDimension(R.styleable.DevWidget_dev_textHeight2, SizeUtils.dp2px(getContext(), 8));
            mText2.textBold   = a.getBoolean(R.styleable.DevWidget_dev_textBold2, false);
            mText2.initialize();

            int fillColor = a.getColor(R.styleable.DevWidget_dev_fillColor, 0x66000000);
            int flags     = a.getInteger(R.styleable.DevWidget_dev_flags, 0);
            a.recycle();

            mLeft     = (flags & 1) == 0;
            mTop      = (flags & 2) == 0;
            mTriangle = (flags & 4) > 0;
            mPaint.setColor(fillColor);
        }
    }

    @Override
    protected void onMeasure(
            int widthMeasureSpec,
            int heightMeasureSpec
    ) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        this.mHeight = (int) (mPaddingTop + mPaddingCenter + mPaddingBottom
                + mText1.textHeight + mText2.textHeight);
        int size = (int) (mHeight * SQRT2);
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float xy = mHeight / SQRT2;
        canvas.save();
        canvas.translate(xy, xy);
        canvas.rotate((mTop ? 1 : -1) * (mLeft ? -45 : 45));
        canvas.drawPath(calcPath(), mPaint);

        mText1.draw(canvas, mPaddingBottom, mTop);
        if (mTriangle) {
            mText2.draw(canvas, mPaddingBottom + mPaddingCenter + mText1.textHeight, mTop);
        }
        canvas.restore();
    }

    /**
     * 计算路径
     * @return {@link Path}
     */
    private Path calcPath() {
        Path path = new Path();
        path.moveTo(-mHeight, 0);
        path.lineTo(mHeight, 0);
        int factor = mTop ? -1 : 1;
        if (mTriangle) {
            path.lineTo(0, factor * mHeight);
        } else {
            int lineHeight = factor * (int) (mPaddingCenter + mPaddingBottom + mText1.textHeight);
            path.lineTo(mHeight + lineHeight, lineHeight);
            path.lineTo(-mHeight + lineHeight, lineHeight);
        }
        path.close();
        return path;
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 获取 Text1 Painter
     * @return {@link Painter}
     */
    public Painter getPainter1() {
        return mText1;
    }

    /**
     * 获取 Text2 Painter
     * @return {@link Painter}
     */
    public Painter getPainter2() {
        return mText2;
    }

    // =

    /**
     * 设置顶部边距
     * @param px 边距值 ( px )
     * @return {@link CornerLabelView}
     */
    public CornerLabelView setPaddingTop(float px) {
        this.mPaddingTop = px;
        requestLayout();
        postInvalidate();
        return this;
    }

    /**
     * 设置 Text1、Text2 边距
     * @param px 边距值 ( px )
     * @return {@link CornerLabelView}
     */
    public CornerLabelView setPaddingCenter(float px) {
        this.mPaddingCenter = px;
        requestLayout();
        postInvalidate();
        return this;
    }

    /**
     * 设置底部边距
     * @param px 边距值 ( px )
     * @return {@link CornerLabelView}
     */
    public CornerLabelView setPaddingBottom(float px) {
        this.mPaddingBottom = px;
        requestLayout();
        postInvalidate();
        return this;
    }

    /**
     * 设置背景填充颜色
     * @param color Color
     * @return {@link CornerLabelView}
     */
    public CornerLabelView setFillColor(@ColorInt int color) {
        this.mPaint.setColor(color);
        postInvalidate();
        return this;
    }

    /**
     * 设置背景填充渐变色
     * @param shader {@link Shader}
     * @return {@link CornerLabelView}
     */
    public CornerLabelView setFillShader(Shader shader) {
        this.mPaint.setShader(shader);
        postInvalidate();
        return this;
    }

    /**
     * 设置左边显示角标
     * @return {@link CornerLabelView}
     */
    public CornerLabelView left() {
        this.mLeft = true;
        postInvalidate();
        return this;
    }

    /**
     * 设置右边显示角标
     * @return {@link CornerLabelView}
     */
    public CornerLabelView right() {
        this.mLeft = false;
        postInvalidate();
        return this;
    }

    /**
     * 设置顶部显示角标
     * @return {@link CornerLabelView}
     */
    public CornerLabelView top() {
        this.mTop = true;
        postInvalidate();
        return this;
    }

    /**
     * 设置底部显示角标
     * @return {@link CornerLabelView}
     */
    public CornerLabelView bottom() {
        this.mTop = false;
        postInvalidate();
        return this;
    }

    /**
     * 设置角标三角形铺满样式
     * @param value {@code true} 展示 text1、text2, {@code false} 展示 text1
     * @return {@link CornerLabelView}
     */
    public CornerLabelView triangle(boolean value) {
        this.mTriangle = value;
        postInvalidate();
        return this;
    }

    // =========
    // = Text1 =
    // =========

    /**
     * 设置文本
     * @param text 文本
     * @return {@link CornerLabelView}
     */
    public CornerLabelView setText1(String text) {
        mText1.text = text;
        mText1.initialize();
        requestLayout();
        postInvalidate();
        return this;
    }

    /**
     * 设置字体颜色
     * @param textColor 字体颜色
     * @return {@link CornerLabelView}
     */
    public CornerLabelView setTextColor1(@ColorInt int textColor) {
        mText1.textColor = textColor;
        mText1.initialize();
        postInvalidate();
        return this;
    }

    /**
     * 设置字体高度
     * @param textHeight 字体高度 ( px )
     * @return {@link CornerLabelView}
     */
    public CornerLabelView setTextHeight1(float textHeight) {
        mText1.textHeight = textHeight;
        mText1.initialize();
        requestLayout();
        postInvalidate();
        return this;
    }

    /**
     * 设置字体是否加粗
     * @param textBold {@code true} yes, {@code false} no
     * @return {@link CornerLabelView}
     */
    public CornerLabelView setTextBold1(boolean textBold) {
        mText1.textBold = textBold;
        mText1.initialize();
        requestLayout();
        postInvalidate();
        return this;
    }

    // =========
    // = Text2 =
    // =========

    /**
     * 设置文本
     * @param text 文本
     * @return {@link CornerLabelView}
     */
    public CornerLabelView setText2(String text) {
        mText2.text = text;
        mText2.initialize();
        requestLayout();
        postInvalidate();
        return this;
    }

    /**
     * 设置字体颜色
     * @param textColor 字体颜色
     * @return {@link CornerLabelView}
     */
    public CornerLabelView setTextColor2(@ColorInt int textColor) {
        mText2.textColor = textColor;
        mText2.initialize();
        postInvalidate();
        return this;
    }

    /**
     * 设置字体高度
     * @param textHeight 字体高度 ( px )
     * @return {@link CornerLabelView}
     */
    public CornerLabelView setTextHeight2(float textHeight) {
        mText2.textHeight = textHeight;
        mText2.initialize();
        requestLayout();
        postInvalidate();
        return this;
    }

    /**
     * 设置字体是否加粗
     * @param textBold {@code true} yes, {@code false} no
     * @return {@link CornerLabelView}
     */
    public CornerLabelView setTextBold2(boolean textBold) {
        mText2.textBold = textBold;
        mText2.initialize();
        requestLayout();
        postInvalidate();
        return this;
    }

    // =

    /**
     * 刷新重绘处理
     * @return {@link CornerLabelView}
     */
    public CornerLabelView refresh() {
        mText1.initialize();
        mText2.initialize();
        requestLayout();
        postInvalidate();
        return this;
    }

    // =

    /**
     * detail: 画笔
     * @author Ttt
     */
    public static final class Painter {

        private Painter() {
        }

        // 画笔
        private final TextPaint paint      = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        // 绘制文本
        private       String    text       = "";
        // 绘制字体颜色
        private       int       textColor  = Color.WHITE;
        // 字体高度
        private       float     textHeight = 0;
        // 字体是否加粗
        private       boolean   textBold   = false;
        // 偏移值
        private       float     offset     = 0;

        /**
         * 初始化处理
         */
        void initialize() {
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setColor(textColor);
            paint.setTextSize(textHeight);
            paint.setFakeBoldText(textBold);
            // 计算偏移值
            if (text == null) text = "";
            Rect rect = new Rect();
            paint.getTextBounds(text, 0, text.length(), rect);
            offset = rect.height() / 2;
        }

        /**
         * 绘制处理
         * @param canvas 画布
         * @param y      绘制 Y 轴坐标
         * @param isTop  是否顶部绘制
         */
        void draw(
                Canvas canvas,
                float y,
                boolean isTop
        ) {
            canvas.drawText(text, 0, (isTop ? -1 : 1) * (y + textHeight / 2) + offset, paint);
        }

        // =============
        // = 对外公开方法 =
        // =============

        /**
         * 获取画笔
         * <pre>
         *     设置了部分属性样式等, 需调用 {@link #refresh}
         * </pre>
         * @return {@link TextPaint}
         */
        public TextPaint getPaint() {
            return paint;
        }

        /**
         * 获取文本
         * @return 文本
         */
        public String getText() {
            return text;
        }

        /**
         * 获取字体颜色
         * @return 字体颜色
         */
        public int getTextColor() {
            return textColor;
        }

        /**
         * 获取字体高度
         * @return 字体高度 ( px )
         */
        public float getTextHeight() {
            return textHeight;
        }

        /**
         * 获取字体是否加粗
         * @return {@code true} yes, {@code false} no
         */
        public boolean isTextBold() {
            return textBold;
        }
    }
}