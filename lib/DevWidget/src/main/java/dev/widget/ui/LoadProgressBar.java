package dev.widget.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ColorInt;

import dev.utils.app.SizeUtils;
import dev.utils.app.TextViewUtils;
import dev.widget.R;

/**
 * detail: 自定义加载 ProgressBar 样式 View
 * @author Ttt
 * <pre>
 *     内外圆环 + 数字 + 无扇形
 *     view.setProgressStyle(LoadProgressBar.ProgressStyle.RINGS)
 *              .setOuterRingWidth(SizeUtils.dipConvertPx(5)) // 内环宽度
 *              .setOuterRingColor(ResourceUtils.getColor(R.color.khaki)) // 内环颜色
 *              .setProgressColor(ResourceUtils.getColor(R.color.color_88)) // 进度颜色
 *              .setCanvasNumber(true); // 是否绘制数字
 *     <dev.widget.ui.LoadProgressBar
 *        app:dev_canvasNumber="true"
 *        app:dev_outerRingColor="@color/khaki"
 *        app:dev_outerRingWidth="5.0dp"
 *        app:dev_progressColor="#888888"
 *        app:dev_progressStyle="rings" />
 *     <p></p>
 *     扇形 + 数字 + 无内外圆环
 *     view.setProgressStyle(CustomProgressBar.ProgressStyle.FAN_SHAPED)
 *              .setProgressColor(ResourceUtils.getColor(R.color.sky_blue)) // 进度颜色
 *              .setCanvasNumber(true); // 是否绘制数字
 *     <dev.widget.ui.LoadProgressBar
 *        app:dev_canvasNumber="true"
 *        app:dev_progressColor="@color/sky_blue"
 *        app:dev_progressStyle="fanShaped" />
 *     <p></p>
 *     扇形 + 数字 + 外圆环
 *     view.setProgressStyle(LoadProgressBar.ProgressStyle.ARC_FAN_SHAPED)
 *              .setOuterRingWidth(SizeUtils.dipConvertPx(1)) // 内环宽度
 *              .setOuterRingColor(Color.RED) // 内环颜色
 *              .setProgressColor(ResourceUtils.getColor(R.color.mediumturquoise)) // 进度颜色
 *              .setNumberTextColor(Color.parseColor("#FB7D00")) // 字体颜色
 *              .setCanvasNumber(true); // 是否绘制数字
 *     <dev.widget.ui.LoadProgressBar
 *        app:dev_canvasNumber="true"
 *        app:dev_numberTextColor="#FB7D00"
 *        app:dev_outerRingColor="@color/red"
 *        app:dev_outerRingWidth="1.0dp"
 *        app:dev_progressColor="@color/mediumturquoise"
 *        app:dev_progressStyle="arcFanShaped" />
 *     <p></p>
 *     单独字体
 *     view.setProgressStyle(CustomProgressBar.ProgressStyle.NUMBER)
 *              .setNumberTextSize(20f) // 字体大小
 *              .setNumberTextColor(ResourceUtils.getColor(R.color.deeppink)); // 字体颜色
 *     <dev.widget.ui.LoadProgressBar
 *        app:dev_numberTextColor="@color/deeppink"
 *        app:dev_numberTextSize="40.0sp"
 *        app:dev_progressStyle="number" />
 *     <p></p>
 *     app:dev_canvasNumber=""
 *     app:dev_progressColor=""
 *     app:dev_outerRingColor=""
 *     app:dev_insideCircleWidth=""
 *     app:dev_outerRingWidth=""
 *     app:dev_numberTextSize=""
 *     app:dev_numberTextColor=""
 *     app:dev_progressStyle=""
 * </pre>
 */
public class LoadProgressBar
        extends View {

    // 画笔
    private       Paint         mPaint;
    // 字体画笔
    private final Paint         mTextPaint      = new Paint();
    // 最大进度
    private       int           mMax            = 100;
    // 当前进度
    private       int           mProgress       = 0;
    // 进度条样式
    private       ProgressStyle mProgressStyle  = ProgressStyle.RINGS;
    // 进度条颜色
    private       int           mProgressColor;
    // 外环进度条颜色
    private       int           mOuterRingColor;
    // 内环进度条宽度
    private       float         mInsideCircleWidth;
    // 外环进度条宽度
    private       float         mOuterRingWidth;
    // 是否绘制数字
    private       boolean       mIsCanvasNumber = false;
    // 绘制的字体大小
    private       float         mNumberTextSize;
    // 绘制的数字颜色
    private       int           mNumberTextColor;

    public LoadProgressBar(Context context) {
        super(context);
        initAttrs(context, null);
    }

    public LoadProgressBar(
            Context context,
            AttributeSet attrs
    ) {
        super(context, attrs);
        initAttrs(context, attrs);
    }

    public LoadProgressBar(
            Context context,
            AttributeSet attrs,
            int defStyleAttr
    ) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LoadProgressBar(
            Context context,
            AttributeSet attrs,
            int defStyleAttr,
            int defStyleRes
    ) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttrs(context, attrs);
    }

    /**
     * 初始化
     * @param context {@link Context}
     * @param attrs   {@link AttributeSet}
     */
    private void initAttrs(
            Context context,
            AttributeSet attrs
    ) {
        init(); // 默认初始化配置

        if (context != null && attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DevWidget);
            mIsCanvasNumber = a.getBoolean(R.styleable.DevWidget_dev_canvasNumber, mIsCanvasNumber);
            mProgressColor = a.getColor(R.styleable.DevWidget_dev_progressColor, mProgressColor);
            mOuterRingColor = a.getColor(R.styleable.DevWidget_dev_outerRingColor, mOuterRingColor);
            mInsideCircleWidth = a.getLayoutDimension(R.styleable.DevWidget_dev_insideCircleWidth, (int) mInsideCircleWidth);
            mOuterRingWidth = a.getLayoutDimension(R.styleable.DevWidget_dev_outerRingWidth, (int) mOuterRingWidth);
            mNumberTextSize = a.getDimensionPixelSize(R.styleable.DevWidget_dev_numberTextSize, (int) mNumberTextSize);
            mNumberTextColor = a.getColor(R.styleable.DevWidget_dev_numberTextColor, mNumberTextColor);
            int progressStyle = a.getInt(R.styleable.DevWidget_dev_progressStyle, 0);
            a.recycle();

            switch (progressStyle) {
                case 0:
                    mProgressStyle = ProgressStyle.RINGS;
                    break;
                case 1:
                    mProgressStyle = ProgressStyle.FAN_SHAPED;
                    break;
                case 2:
                    mProgressStyle = ProgressStyle.ARC_FAN_SHAPED;
                    break;
                case 3:
                    mProgressStyle = ProgressStyle.NUMBER;
                    break;
                default:
                    mProgressStyle = ProgressStyle.RINGS;
                    break;
            }
        }
    }

    // ===============
    // = 内部处理方法 =
    // ===============

    /**
     * 初始化方法
     * @return {@link LoadProgressBar}
     */
    private LoadProgressBar init() {
        // 初始化画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // 设置进度颜色值 ( 白色 )
        mProgressColor = Color.rgb(255, 255, 255); // Color.WHITE
        // 设置外环颜色
        mOuterRingColor = Color.argb(30, 255, 255, 255);
        // 设置外环进度条的宽度
        mOuterRingWidth = SizeUtils.dipConvertPx(4.0f);
        // 设置绘制的数字颜色
        mNumberTextColor = Color.BLACK;
        // 初始化处理
        mNumberTextSize = 0;
        mInsideCircleWidth = 0;
        mIsCanvasNumber = false;
        return this;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 是否绘制数字
        boolean isDrawNumber = mIsCanvasNumber;
        // 防止没有设置样式
        if (mProgressStyle == null) mProgressStyle = ProgressStyle.RINGS;
        // 属于默认类型
        if (mProgressStyle == ProgressStyle.RINGS) { // 绘制圆环
            float centre = getWidth() / 2; // 获取圆心的 x 坐标
            float radius = (centre - mOuterRingWidth / 2); // 圆环的半径
            mPaint.setColor(mOuterRingColor); // 设置圆环的颜色
            mPaint.setStyle(Paint.Style.STROKE); // 设置空心
            mPaint.setStrokeWidth(mOuterRingWidth); // 设置圆环的宽度
            mPaint.setAntiAlias(true); // 消除锯齿
            canvas.drawCircle(centre, centre, radius, mPaint); // 画出圆环

            // 设置进度是实心还是空心
            mPaint.setStrokeWidth(mOuterRingWidth); // 设置圆环的宽度
            mPaint.setStyle(Paint.Style.STROKE); // 设置空心
            mPaint.setColor(mProgressColor); // 设置进度的颜色
            mPaint.setAntiAlias(true); // 消除锯齿
            // 用于定义的圆弧的形状和大小的界限
            RectF oval = new RectF(centre - radius, centre - radius, centre + radius, centre + radius);
            // 根据进度画圆弧 ( 0 从右边开始 , 270 从上边开始 )
            canvas.drawArc(oval, 270, 360 * mProgress / mMax, false, mPaint);
        } else if (mProgressStyle == ProgressStyle.FAN_SHAPED) { // 绘制扇形
            int centre = getWidth() / 2; // 获取圆心的 x 坐标
            int radius = centre; // 扇形的半径
            mPaint.setStrokeWidth(centre); // 设置扇形的宽度
            mPaint.setStyle(Paint.Style.FILL); // 设置实心
            mPaint.setColor(mProgressColor);  // 设置进度的颜色
            mPaint.setAntiAlias(true);  // 消除锯齿
            // 绘制扇形
            RectF ovalBorder = new RectF(centre - radius, centre - radius, centre + radius, centre + radius);
            canvas.drawArc(ovalBorder, 270, 360 * mProgress / mMax, true, mPaint);
        } else if (mProgressStyle == ProgressStyle.ARC_FAN_SHAPED) {
            // 判断是否没设置内圈宽度, 如果没有设置, 则设置为贴着外圈
            if (mInsideCircleWidth <= 0) {
                // 设置内圈大小
                mInsideCircleWidth = getWidth() - mOuterRingWidth * 2;
            }
            // 绘制圆环 + 扇形
            float centre = getWidth() / 2; // 获取圆心的 x 坐标
            float radius = (centre - mOuterRingWidth / 2); // 圆环的半径
            // 设置进度是实心还是空心
            mPaint.setStrokeWidth(mOuterRingWidth); // 设置圆环的宽度
            mPaint.setStyle(Paint.Style.STROKE); // 设置空心
            mPaint.setColor(mOuterRingColor);  // 设置进度的颜色
            mPaint.setAntiAlias(true);  // 消除锯齿
            // 绘制圆外环
            RectF ovalBorder = new RectF(centre - radius, centre - radius, centre + radius, centre + radius);
            canvas.drawArc(ovalBorder, 270, 360, false, mPaint);
            // 绘制椭圆
            mPaint.setStrokeWidth(mInsideCircleWidth); // 设置圆的宽度
            mPaint.setStyle(Paint.Style.FILL); // 设置实心
            mPaint.setColor(mProgressColor);  // 设置进度的颜色
            // 获取圆心的 x 坐标
            centre = (mInsideCircleWidth / 2);
            // 圆的半径
            radius = centre;
            // 边距
            float margin = (getWidth() - mInsideCircleWidth) / 2;
            // 绘制扇形
            RectF oval = new RectF(margin + centre - radius, margin + centre - radius,
                    margin + centre + radius, margin + centre + radius);
            canvas.drawArc(oval, 270, (360 * mProgress) / mMax, true, mPaint);  // 根据进度画圆弧
        } else if (mProgressStyle == ProgressStyle.NUMBER) {
            // 绘制的内容
            String progressText = (mProgress * 100 / mMax) + "%";
            // 判断是否存在计算的字体大小
            if (mNumberTextSize <= 0) {
                int tempWidth = getWidth();
                // 计算字体大小
                mNumberTextSize = TextViewUtils.reckonTextSizeByWidth(tempWidth, mTextPaint,
                        SizeUtils.pxConvertSp(tempWidth), "100%");
            }
            // 绘制进度文本
            drawProgressText(canvas, mNumberTextSize, mNumberTextColor, progressText);
            // 已经绘制数字, 则不绘制
            isDrawNumber = false;
        }
        // 判断是否绘制内容
        if (isDrawNumber) {
            // 设置实心
            mPaint.setStyle(Paint.Style.FILL);
            // 判断样式
            switch (mProgressStyle) {
                case RINGS: // 圆环
                case FAN_SHAPED: // 扇形
                    // 判断是否存在计算的字体大小
                    if (mNumberTextSize <= 0) {
                        int tempWidth = getWidth() / 3 * 2;
                        // 计算字体大小
                        mNumberTextSize = TextViewUtils.reckonTextSizeByWidth(tempWidth, mTextPaint,
                                SizeUtils.pxConvertSp(tempWidth), "100%");
                    }
                    // 绘制进度文本
                    drawProgressText(canvas, mNumberTextSize, mNumberTextColor, (mProgress * 100 / mMax) + "%");
                    break;
                case ARC_FAN_SHAPED:
                    // 判断是否存在计算的字体大小
                    if (mNumberTextSize <= 0) {
                        // 计算字体大小
                        if (mInsideCircleWidth < 0f) {
                            int tempWidth = getWidth() / 3 * 2;
                            // 计算字体大小
                            mNumberTextSize = TextViewUtils.reckonTextSizeByWidth(tempWidth, mTextPaint,
                                    SizeUtils.pxConvertSp(tempWidth), "100%");
                        } else {
                            int tempWidth = (int) mInsideCircleWidth / 3 * 2;
                            // 计算字体大小
                            mNumberTextSize = TextViewUtils.reckonTextSizeByWidth(tempWidth, mTextPaint,
                                    SizeUtils.pxConvertSp(tempWidth), "100%");
                        }
                    }
                    // 绘制进度文本
                    drawProgressText(canvas, mNumberTextSize, mNumberTextColor, (mProgress * 100 / mMax) + "%");
                    break;
            }
        }
    }

    /**
     * 绘制进度文本
     * @param canvas       {@link Canvas}
     * @param textSize     字体大小
     * @param textColor    字体颜色
     * @param progressText 绘制文本
     */
    private void drawProgressText(
            Canvas canvas,
            float textSize,
            @ColorInt int textColor,
            String progressText
    ) {
        drawProgressText(canvas, getWidth(), getHeight(), textSize, textColor, progressText);
    }

    /**
     * 绘制进度文本
     * @param canvas       {@link Canvas}
     * @param width        对应的宽度 ( 居中使用 )
     * @param height       对应的高度 ( 居中使用 )
     * @param textSize     字体大小
     * @param textColor    字体颜色
     * @param progressText 绘制文本
     */
    private void drawProgressText(
            Canvas canvas,
            int width,
            int height,
            float textSize,
            @ColorInt int textColor,
            String progressText
    ) {
        mPaint.setColor(textColor);  // 设置进度的颜色
        mPaint.setTextSize(textSize); // 设置字体大小
        // 获取字体高度
        Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
        // 获取字体内容宽度
        float tWidth = mPaint.measureText(progressText);
        // 计算字体内容高度
        float fontTotalHeight = fontMetrics.bottom - fontMetrics.top;
        float offY            = fontTotalHeight / 2 - fontMetrics.bottom;
        // 设置左、上边距
        float x = (width - tWidth) / 2;
        float y = height / 2 + offY;
        // 绘制内容
        canvas.drawText(progressText, x, y, mPaint);
    }

    // ===============
    // = 对外公开方法 =
    // ===============

    /**
     * 重置参数
     * @return {@link LoadProgressBar}
     */
    public LoadProgressBar reset() {
        return init();
    }

    /**
     * 获取最大值
     * @return 最大值
     */
    public synchronized int getMax() {
        return mMax;
    }

    /**
     * 设置最大值
     * @param max 最大值
     * @return {@link LoadProgressBar}
     */
    public synchronized LoadProgressBar setMax(int max) {
        if (max <= 0) throw new IllegalArgumentException("max not less than 0");
        this.mMax = max;
        return this;
    }

    /**
     * 获取当前进度
     * @return 当前进度
     */
    public synchronized int getProgress() {
        return mProgress;
    }

    /**
     * 设置当前进度
     * @param progress 当前进度
     * @return {@link LoadProgressBar}
     */
    public synchronized LoadProgressBar setProgress(int progress) {
        if (progress < 0) throw new IllegalArgumentException("progress not less than 0");
        if (progress > mMax) {
            progress = mMax;
        }
        if (progress <= mMax) {
            this.mProgress = progress;
            postInvalidate();
        }
        return this;
    }

    /**
     * 获取进度条颜色
     * @return 进度条颜色
     */
    public int getProgressColor() {
        return mProgressColor;
    }

    /**
     * 设置进度条颜色
     * @param progressColor 进度条颜色
     * @return {@link LoadProgressBar}
     */
    public LoadProgressBar setProgressColor(@ColorInt int progressColor) {
        this.mProgressColor = progressColor;
        return this;
    }

    /**
     * 获取外环进度条颜色
     * @return 外环进度条颜色
     */
    public int getOuterRingColor() {
        return mOuterRingColor;
    }

    /**
     * 设置外环进度条颜色
     * @param outerRingColor 外环进度条颜色
     * @return {@link LoadProgressBar}
     */
    public LoadProgressBar setOuterRingColor(@ColorInt int outerRingColor) {
        this.mOuterRingColor = outerRingColor;
        return this;
    }

    /**
     * 获取内环进度条宽度
     * @return 内环进度条宽度
     */
    public float getInsideCircleWidth() {
        return mInsideCircleWidth;
    }

    /**
     * 设置内环进度条宽度
     * @param insideCircleWidth 内环进度条宽度
     * @return {@link LoadProgressBar}
     */
    public LoadProgressBar setInsideCircleWidth(float insideCircleWidth) {
        this.mInsideCircleWidth = Math.abs(insideCircleWidth);
        return this;
    }

    /**
     * 获取外环进度条宽度
     * @return 外环进度条宽度
     */
    public float getOuterRingWidth() {
        return mOuterRingWidth;
    }

    /**
     * 设置外环进度条宽度
     * @param outerRingWidth 外环进度条宽度
     * @return {@link LoadProgressBar}
     */
    public LoadProgressBar setOuterRingWidth(float outerRingWidth) {
        this.mOuterRingWidth = Math.abs(outerRingWidth);
        return this;
    }

    /**
     * 是否绘制数字
     * @return {@code true} yes, {@code false} no
     */
    public boolean isCanvasNumber() {
        return mIsCanvasNumber;
    }

    /**
     * 设置是否绘制数字
     * @param canvasNumber {@code true} yes, {@code false} no
     * @return {@link LoadProgressBar}
     */
    public LoadProgressBar setCanvasNumber(boolean canvasNumber) {
        this.mIsCanvasNumber = canvasNumber;
        return this;
    }

    /**
     * 获取绘制的字体大小
     * @return 绘制的字体大小
     */
    public float getNumberTextSize() {
        return mNumberTextSize;
    }

    /**
     * 设置绘制的字体大小
     * @param numberTextSize 绘制的字体大小
     * @return {@link LoadProgressBar}
     */
    public LoadProgressBar setNumberTextSize(float numberTextSize) {
        this.mNumberTextSize = numberTextSize;
        return this;
    }

    /**
     * 获取绘制的数字颜色
     * @return 绘制的数字颜色
     */
    public int getNumberTextColor() {
        return mNumberTextColor;
    }

    /**
     * 设置绘制的数字颜色
     * @param numberTextColor 绘制的数字颜色
     * @return {@link LoadProgressBar}
     */
    public LoadProgressBar setNumberTextColor(@ColorInt int numberTextColor) {
        this.mNumberTextColor = numberTextColor;
        return this;
    }

    /**
     * 获取进度条样式
     * @return {@link ProgressStyle}
     */
    public ProgressStyle getProgressStyle() {
        return mProgressStyle;
    }

    /**
     * 设置进度条样式
     * @param progressStyle {@link ProgressStyle}
     * @return {@link LoadProgressBar}
     */
    public LoadProgressBar setProgressStyle(ProgressStyle progressStyle) {
        this.mProgressStyle = (progressStyle == null) ? ProgressStyle.RINGS : progressStyle;
        return this;
    }

    // =============
    // = 进度条样式 =
    // =============

    /**
     * detail: 进度条样式
     * @author Ttt
     */
    public enum ProgressStyle {

        // 圆环
        RINGS,

        // 扇形进度样式
        FAN_SHAPED,

        // 圆环扇形进度样式
        ARC_FAN_SHAPED,

        // 绘制数字
        NUMBER
    }
}