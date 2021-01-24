package dev.widget.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.core.graphics.drawable.DrawableCompat;

import java.util.Arrays;

import dev.utils.LogPrintUtils;
import dev.utils.app.SizeUtils;
import dev.widget.R;

/**
 * detail: 自定义扫描 ( 二维码 / AR ) 效果形状 View
 * @author Ttt
 */
public class ScanShapeView
        extends View {

    // 日志 TAG
    private final String TAG = ScanShapeView.class.getSimpleName();

    /**
     * detail: 形状类型
     * @author Ttt
     */
    public enum Shape {

        // 正方形
        Square,

        // 六边形
        Hexagon, // 大小以宽度为准 ( 正方形 )

        // 环形
        Annulus, // 以最小的为基准
    }

    /**
     * detail: 自定义 CornerPathEffect
     * @author Ttt
     * <pre>
     *     便于获取拐角圆角大小
     * </pre>
     */
    public static final class CornerEffect
            extends CornerPathEffect {

        // 拐角圆角大小
        private final float radius;

        public CornerEffect(float radius) {
            super(radius);
            this.radius = radius;
        }

        public float getRadius() {
            return radius;
        }
    }

    // 形状类型 ( 默认正方形 )
    private       Shape        mShapeType    = Shape.Square;
    // 是否需要重新处理动画
    private       boolean      mIsReAnim     = true;
    // 默认通用 DP
    private       float        mDFCommonDP;
    // 空白画笔 ( 绘制边框使用, 不绘制边框时 )
    private final Paint        mEmptyPaint   = new Paint();
    // 是否设置拐角圆角 ( 圆润 )
    private       CornerEffect mCornerEffect = new CornerEffect(10);

    // ===========
    // = 背景相关 =
    // ===========

    // 是否绘制背景
    private       boolean mIsDrawBackground = true;
    // 绘制背景画笔
    private final Paint   mBackgroundPaint  = new Paint(Paint.ANTI_ALIAS_FLAG);

    // ================
    // = 绘制扫描 View =
    // ================

    // 是否绘制扫描区域边框
    private       boolean mIsDrawBorder = true;
    // 扫描边框 View
    private final Paint   mBorderPaint  = new Paint();
    // 边框边距
    private       float   mBorderMargin = 0;
    // 边框宽度
    private       float   mBorderWidth;
    // 扫描区域块 ( 默认 700x700 ) - 绘制的宽 (x), 高 (y)
    private final PointF  mPointF       = new PointF(700, 700);

    // ======================
    // = 正方形 ( 边框相关 ) =
    // ======================

    // 正方形描边 ( 边框 ) 类型 0 = 单独四个角落, 1 = 单独边框, 2 = 全部
    private int     mBorderToSquare  = 0;
    // 正方形描边 ( 边框 ) 宽度
    private float   mBorderWidthToSquare;
    // 每个角的点距离 ( 长度 ) 正方形四个角落区域
    private float   mTriAngleLength;
    // 是否特殊处理
    private boolean mSpecialToSquare = false;

    // ===========
    // = 环形相关 =
    // ===========

    // 环形画笔, [] { 0 - 外环, 1 - 中间环, 2 - 外环 }
    private final Paint[]   mAnnulusPaints  = new Paint[3];
    // 三个环宽度
    private       float[]   mAnnulusWidths  = new float[3];
    // 三个环长度
    private       int[]     mAnnulusLengths = new int[]{20, 30, 85};
    // 三个环是否绘制
    private       boolean[] mAnnulusDraws   = new boolean[]{true, true, true};
    // 三个环分别角度
    private final int[]     mAnnulusAngles  = new int[]{0, -15, 0};
    // 三个环颜色值
    private       int[]     mAnnulusColors  = new int[]{Color.BLUE, Color.RED, Color.WHITE};
    // 三个环之间的边距
    private       float[]   mAnnulusMargins = new float[3];

    // ===========
    // = 动画相关 =
    // ===========

    // 是否绘制动画
    private boolean mIsDrawAnim = true;
    // 是否自动开启动画
    private boolean mIsAutoAnim = true;

    // =======================
    // = 正方形 ( 动画 ) 相关 =
    // =======================

    // 正方形扫描动画 对象
    private       ValueAnimator mAnimToSquare;
    // 正方形扫描动画速度 ( 毫秒 )
    private       long          mLineDurationToSquare   = 10L;
    // 正方形线条画笔
    private final Paint         mLinePaintToSquare      = new Paint(Paint.ANTI_ALIAS_FLAG);
    // 扫描线条 Bitmap
    private       Bitmap        mBitmapToSquare;
    // 线条偏离值
    private       int           mLineOffsetToSquare     = 0;
    // 线条向上 ( 下 ) 边距
    private       float         mLineMarginTopToSquare  = 0f;
    // 线条向左 ( 右 ) 边距
    private       float         mLineMarginLeftToSquare = 0f;
    // 线条颜色
    private       int           mLineColorToSquare      = 0;

    // =======================
    // = 六边形 ( 动画 ) 相关 =
    // =======================

    // 边框外动画 对象
    private       ValueAnimator mAnimToHexagon;
    // 六边形线条画笔
    private final Paint         mLinePaintToHexagon  = new Paint();
    // 六边形线条路径
    private       Path          mLinePathToHexagon   = new Path();
    // 六边形线条 Canvas( 动画中实时绘制计算路径 )
    private       Canvas        mCanvasToHexagon;
    // 六边形线条 绘制出来的 Bitmap
    private       Bitmap        mBitmapToHexagon;
    // 线条中心点
    private       float         mCenterToHexagon     = 0;
    // 线条宽度
    private final float         mLineWidthToHexagon  = 4f;
    // 绘制线条边距 ( 针对绘制区域 )
    private       float         mLineMarginToHexagon = 20f;
    // 动画方向 ( 六边形线条 ) - true = 左, false = 右
    private       boolean       mLineAnimDirection   = true;

    // =====================
    // = 环形 ( 动画 ) 相关 =
    // =====================

    // 环形动画 对象
    private ValueAnimator mAnimToAnnulus;
    // 动画效果临时变量
    private float         mAnimOffsetToAnnulus      = 0f;
    // 是否达到偏移值最大值
    private boolean       mIsOffsetMaxToAnnulus     = true;
    // 线条向上 ( 下 ) 边距
    private float         mLineOffsetToAnnulus      = 0f;
    // 扫描线条 Bitmap
    private Bitmap        mBitmapToAnnulus;
    // 线条颜色
    private int           mLineColorToAnnulus       = 0;
    // 绘制扫描线条偏移速度
    private float         mLineOffsetSpeedToAnnulus = 4f;

    // ===========
    // = 构造函数 =
    // ===========

    public ScanShapeView(Context context) {
        super(context);
        init();
    }

    public ScanShapeView(
            Context context,
            AttributeSet attrs
    ) {
        super(context, attrs);
        init();
    }

    public ScanShapeView(
            Context context,
            AttributeSet attrs,
            int defStyleAttr
    ) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ScanShapeView(
            Context context,
            AttributeSet attrs,
            int defStyleAttr,
            int defStyleRes
    ) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    /**
     * 初始化处理
     */
    private void init() {
        mDFCommonDP = SizeUtils.dipConvertPx(5);
        mBorderWidth = SizeUtils.dipConvertPx(2);
        mBorderWidthToSquare = SizeUtils.dipConvertPx(1);
        mTriAngleLength = SizeUtils.dipConvertPxf(20);

        mAnnulusWidths[0] = SizeUtils.dipConvertPx(3);
        mAnnulusWidths[1] = SizeUtils.dipConvertPx(7);
        mAnnulusWidths[2] = SizeUtils.dipConvertPx(7);

        mAnnulusMargins[0] = SizeUtils.dipConvertPx(7);
        mAnnulusMargins[1] = SizeUtils.dipConvertPx(7);
        mAnnulusMargins[2] = SizeUtils.dipConvertPx(7);

        // 设置背景颜色 ( 黑色 百分之 40 透明度 ) #66000000
        mBackgroundPaint.setColor(Color.argb(102, 0, 0, 0));

        // 扫描边框 View 画笔
        mBorderPaint.setStrokeWidth(mBorderWidth);
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setColor(Color.WHITE);
        mBorderPaint.setStyle(Paint.Style.STROKE);

        // 空白画笔 ( 绘制边框使用, 不绘制边框时 )
        mEmptyPaint.setStrokeWidth(0);
        mEmptyPaint.setAntiAlias(true);
        mEmptyPaint.setColor(Color.TRANSPARENT);
        mEmptyPaint.setStyle(Paint.Style.STROKE);

        // ========
        // = 环形 =
        // ========

        // 外环
        mAnnulusPaints[0] = new Paint();
        mAnnulusPaints[0].setColor(mAnnulusColors[0]);
        mAnnulusPaints[0].setAntiAlias(true);
        mAnnulusPaints[0].setStyle(Paint.Style.STROKE);
        mAnnulusPaints[0].setStrokeWidth(mAnnulusWidths[0]);
        // 中间环
        mAnnulusPaints[1] = new Paint();
        mAnnulusPaints[1].setColor(mAnnulusColors[1]);
        mAnnulusPaints[1].setAntiAlias(true);
        mAnnulusPaints[1].setStyle(Paint.Style.STROKE);
        mAnnulusPaints[1].setStrokeWidth(mAnnulusWidths[1]);
        // 内环
        mAnnulusPaints[2] = new Paint();
        mAnnulusPaints[2].setColor(mAnnulusColors[2]);
        mAnnulusPaints[2].setAntiAlias(true);
        mAnnulusPaints[2].setStyle(Paint.Style.STROKE);
        mAnnulusPaints[2].setStrokeWidth(mAnnulusWidths[2]);

        // ===============
        // = 动画相关画笔 =
        // ===============

        // 六边形线条画笔
        mLinePaintToHexagon.setStrokeWidth(mLineWidthToHexagon);
        mLinePaintToHexagon.setAntiAlias(true);
        mLinePaintToHexagon.setStyle(Paint.Style.STROKE);

        // 统一处理画笔拐角
        handlerCornerPathEffect();

        // 加载正方形扫描线条
        mBitmapToSquare = ((BitmapDrawable) (getResources().getDrawable(R.drawable.dev_scan_line))).getBitmap();

        // 加载圆环扫描
        mBitmapToAnnulus = ((BitmapDrawable) (getResources().getDrawable(R.drawable.dev_scan_line))).getBitmap();

        // 重置动画处理
        initAnim();
    }

    /**
     * 处理拐角
     */
    private void handlerCornerPathEffect() {
        // 设置绘制边框拐角
        mBorderPaint.setPathEffect(mCornerEffect);
        // 判断是否加入拐角
        switch (mShapeType) {
            case Square: // 正方形
                mBackgroundPaint.setPathEffect(mCornerEffect);
                break;
            default: // 其他不设置拐角
                mBackgroundPaint.setPathEffect(null);
                break;
        }
        // 设置绘制六边形线条拐角
        mLinePaintToHexagon.setPathEffect(mCornerEffect);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // ===================
        // = 判断是否绘制背景 =
        // ===================

        if (mIsDrawBackground) { // 绘制计算背景
            makeBackground(calcShapeRegion(mBorderMargin), canvas);
        }

        // ===========================
        // = 绘制扫描区域 ( 包括边框 ) =
        // ===========================

        // 绘制计算边框
        makeShape(calcShapeRegion(), canvas, mIsDrawBorder ? mBorderPaint : mEmptyPaint, true);

        // ===========
        // = 动画相关 =
        // ===========

        // 判断是否需要重新处理动画
        if (mIsReAnim) { // 为了节省资源, 只用绘制一次
            mIsReAnim = false;

            // 判断是否需要动画
            if (mIsDrawAnim) {
                // 计算动画信息
                makeAnim(canvas);
                // 判断是否自动开启动画
                if (mIsAutoAnim) {
                    // 开始动画
                    startAnim();
                }
            }
        }

        // ===============
        // = 绘制扫描动画 =
        // ===============

        // 判断是否需要动画
        if (mIsDrawAnim) {
            drawAnim(canvas);
        }
    }

    // ===============
    // = 对外公开方法 =
    // ===============

    /**
     * 销毁处理
     */
    public void destroy() {
        // 停止动画
        stopAnim();
        // 清空 Bitmap
        mBitmapToSquare = null;
        mBitmapToHexagon = null;
        mBitmapToAnnulus = null;
    }

    /**
     * 获取扫描形状类型
     * @return 扫描形状类型
     */
    public Shape getShapeType() {
        return mShapeType;
    }

    /**
     * 设置扫描形状类型
     * @param shapeType 扫描形状类型
     * @return {@link ScanShapeView}
     */
    public ScanShapeView setShapeType(Shape shapeType) {
        if (shapeType == null) {
            shapeType = Shape.Square;
        }
        // 停止动画
        stopAnim();
        // 设置类型
        this.mShapeType = shapeType;
        // 刷新拐角处理
        handlerCornerPathEffect();
        // 动画处理
        resetAnim(true);
        return this;
    }

    /**
     * 获取拐角角度大小
     * @return 拐角角度大小
     */
    public float getCornerRadius() {
        if (this.mCornerEffect != null) {
            return mCornerEffect.getRadius();
        }
        return 0f;
    }

    /**
     * 设置是否拐角圆角 ( 主要是控制绘制边框的线 )
     * @param cornerEffect 拐角角度大小
     * @return {@link ScanShapeView}
     */
    public ScanShapeView setCornerEffect(CornerEffect cornerEffect) {
        this.mCornerEffect = cornerEffect;
        // 判断是否小于 0
        if (getCornerRadius() <= 0) {
            this.mCornerEffect = null;
        }
        // 刷新拐角处理
        handlerCornerPathEffect();
        return this;
    }

    /**
     * 设置扫描区域大小
     * @param wide 扫描区域大小
     * @return {@link ScanShapeView}
     */
    public ScanShapeView setRegion(float wide) {
        if (wide > 0) {
            // 设置宽高
            mPointF.x = mPointF.y = wide;
        }
        return this;
    }

    /**
     * 设置扫描区域大小
     * @param width  扫描区域宽
     * @param height 扫描区域高
     * @return {@link ScanShapeView}
     */
    public ScanShapeView setRegion(
            float width,
            float height
    ) {
        if (width > 0 && height > 0) {
            // 设置宽
            mPointF.x = width;
            // 设置高
            mPointF.y = height;
        }
        return this;
    }

    /**
     * 设置扫描区域大小
     * @param rect 扫描区域
     * @return {@link ScanShapeView}
     */
    public ScanShapeView setRegion(Rect rect) {
        if (rect != null) {
            // 设置宽
            mPointF.x = rect.right - rect.left;
            // 设置高
            mPointF.y = rect.bottom - rect.top;
        }
        return this;
    }

    /**
     * 获取扫描绘制区域距离左 / 右边距
     * @return 扫描绘制区域距离左 / 右边距
     */
    public float getRegionLeft() {
        return getRegionMarginLeft();
    }

    /**
     * 获取扫描绘制区域距离上 / 下边距
     * @return 扫描绘制区域距离上 / 下边距
     */
    public float getRegionTop() {
        return getRegionMarginTop();
    }

    /**
     * 获取扫描区域宽度
     * @return 扫描区域宽度
     */
    public float getRegionWidth() {
        return mPointF.x;
    }

    /**
     * 获取扫描区域高度
     * @return 扫描区域高度
     */
    public float getRegionHeight() {
        return mPointF.y;
    }

    /**
     * 获取扫描区域信息
     * @return 扫描区域信息
     */
    public RectF getRegion() {
        return calcShapeRegion();
    }

    /**
     * 获取扫描区域信息
     * @param left 向左偏差距离 ( 实际屏幕中位置 )
     * @param top  向上偏差距离 ( 实际屏幕中位置 )
     * @return 扫描区域信息
     */
    public RectF getRegion(
            float left,
            float top
    ) {
        RectF rectF = calcShapeRegion();
        rectF.left += left;
        rectF.right += left;
        rectF.top += top;
        rectF.bottom += top;
        return rectF;
    }

    /**
     * 获取在父布局中实际的位置
     * <pre>
     *     如该 View 没有铺满, 但是为了扫描优化速度, 专门获取扫描区域实际位置
     * </pre>
     * @return 在父布局中实际的位置
     */
    public RectF getRegionParent() {
        return getRegion(getLeft(), getTop());
    }

    /**
     * 获取边框边距
     * @return 边框边距
     */
    public float getBorderMargin() {
        return mBorderMargin;
    }

    /**
     * 设置边框边距
     * @param borderMargin 边框边距
     * @return {@link ScanShapeView}
     */
    public ScanShapeView setBorderMargin(float borderMargin) {
        this.mBorderMargin = borderMargin;
        return this;
    }

    /**
     * 获取边框颜色
     * @return 边框颜色
     */
    public int getBorderColor() {
        return mBorderPaint.getColor();
    }

    /**
     * 设置边框颜色
     * @param color 边框颜色
     * @return {@link ScanShapeView}
     */
    public ScanShapeView setBorderColor(@ColorInt int color) {
        mBorderPaint.setColor(color);
        return this;
    }

    /**
     * 获取边框宽度
     * @return 边框宽度
     */
    public float getBorderWidth() {
        return mBorderPaint.getStrokeWidth();
    }

    /**
     * 设置边框宽度
     * @param width 边框宽度
     * @return {@link ScanShapeView}
     */
    public ScanShapeView setBorderWidth(float width) {
        if (width <= 0) {
            width = mBorderWidth;
        }
        mBorderPaint.setStrokeWidth(width);
        return this;
    }

    /**
     * 是否绘制边框
     * @return {@code true} yes, {@code false} no
     */
    public boolean isDrawBorder() {
        return mIsDrawBorder;
    }

    /**
     * 设置是否绘制边框
     * @param drawBorder 是否绘制边框
     * @return {@link ScanShapeView}
     */
    public ScanShapeView setDrawBorder(boolean drawBorder) {
        mIsDrawBorder = drawBorder;
        return this;
    }

    /**
     * 是否绘制背景
     * @return {@code true} yes, {@code false} no
     */
    public boolean isDrawBackground() {
        return mIsDrawBackground;
    }

    /**
     * 设置是否绘制背景
     * @param drawBackground 是否绘制背景
     * @return {@link ScanShapeView}
     */
    public ScanShapeView setDrawBackground(boolean drawBackground) {
        mIsDrawBackground = drawBackground;
        return this;
    }

    /**
     * 获取绘制的背景颜色
     * @return 绘制的背景颜色
     */
    public int getBGColor() {
        return mBackgroundPaint.getColor();
    }

    /**
     * 设置绘制的背景颜色
     * @param color 绘制的背景颜色
     * @return {@link ScanShapeView}
     */
    public ScanShapeView setBGColor(@ColorInt int color) {
        mBackgroundPaint.setColor(color);
        return this;
    }

    /**
     * 是否绘制动画效果
     * @return {@code true} yes, {@code false} no
     */
    public boolean isDrawAnim() {
        return mIsDrawAnim;
    }

    /**
     * 设置是否绘制动画效果
     * @param drawAnim 是否绘制动画效果
     * @return {@link ScanShapeView}
     */
    public ScanShapeView setDrawAnim(boolean drawAnim) {
        mIsDrawAnim = drawAnim;
        // 动画处理
        resetAnim(true);
        return this;
    }

    /**
     * 是否自动播放动画
     * @return {@code true} yes, {@code false} no
     */
    public boolean isAutoAnim() {
        return mIsAutoAnim;
    }

    /**
     * 设置是否自动播放动画
     * @param autoAnim 是否自动播放动画
     * @return {@link ScanShapeView}
     */
    public ScanShapeView setAutoAnim(boolean autoAnim) {
        mIsAutoAnim = autoAnim;
        // 动画处理
        resetAnim(true);
        return this;
    }

    // =========
    // = 正方形 =
    // =========

    /**
     * 获取正方形描边 ( 边框 ) 类型 0 = 单独四个角落, 1 = 单独边框, 2 = 全部
     * @return 正方形描边 ( 边框 ) 类型
     */
    public int getBorderToSquare() {
        return mBorderToSquare;
    }

    /**
     * 设置正方形描边 ( 边框 ) 类型 0 = 单独四个角落, 1 = 单独边框, 2 = 全部
     * @param borderToSquare 0 = 单独四个角落, 1 = 单独边框, 2 = 全部
     * @return {@link ScanShapeView}
     */
    public ScanShapeView setBorderToSquare(int borderToSquare) {
        // 防止出现负数
        borderToSquare = Math.max(0, borderToSquare);
        // 防止出现大于异常值
        if (borderToSquare > 2) {
            borderToSquare = 0;
        }
        this.mBorderToSquare = borderToSquare;
        return this;
    }

    /**
     * 获取正方形描边 ( 边框 ) 宽度
     * @return 正方形描边 ( 边框 ) 宽度
     */
    public float getBorderWidthToSquare() {
        return mBorderWidthToSquare;
    }

    /**
     * 设置正方形描边 ( 边框 ) 宽度
     * @param borderWidthToSquare 正方形描边 ( 边框 ) 宽度
     * @return {@link ScanShapeView}
     */
    public ScanShapeView setBorderWidthToSquare(float borderWidthToSquare) {
        this.mBorderWidthToSquare = borderWidthToSquare;
        return this;
    }

    /**
     * 获取每个角的点距离 ( 长度 ) 正方形四个角落区域
     * @return 每个角的点距离 ( 长度 ) 正方形四个角落区域
     */
    public float getTriAngleLength() {
        return mTriAngleLength;
    }

    /**
     * 设置每个角的点距离 ( 长度 ) 正方形四个角落区域
     * @param triAngleLength 每个角的点距离 ( 长度 )
     * @return {@link ScanShapeView}
     */
    public ScanShapeView setTriAngleLength(float triAngleLength) {
        this.mTriAngleLength = triAngleLength;
        return this;
    }

    /**
     * 是否特殊处理 ( 正方形边框 )
     * @return {@code true} yes, {@code false} no
     */
    public boolean isSpecialToSquare() {
        return mSpecialToSquare;
    }

    /**
     * 设置是否特殊处理 ( 正方形边框 )
     * @param specialToSquare 是否特殊处理 ( 正方形边框 )
     * @return {@link ScanShapeView}
     */
    public ScanShapeView setSpecialToSquare(boolean specialToSquare) {
        this.mSpecialToSquare = specialToSquare;
        return this;
    }

    /**
     * 获取正方形扫描动画速度 ( 毫秒 )
     * @return 正方形扫描动画速度 ( 毫秒 )
     */
    public long getLineDurationToSquare() {
        return mLineDurationToSquare;
    }

    /**
     * 设置正方形扫描动画速度 ( 毫秒 )
     * @param lineDurationToSquare 正方形扫描动画速度 ( 毫秒 )
     * @return {@link ScanShapeView}
     */
    public ScanShapeView setLineDurationToSquare(long lineDurationToSquare) {
        if (lineDurationToSquare <= 0) {
            lineDurationToSquare = 10L;
        }
        this.mLineDurationToSquare = lineDurationToSquare;
        return this;
    }

    /**
     * 获取正方形扫描线条 Bitmap
     * @return 正方形扫描线条 Bitmap
     */
    public Bitmap getBitmapToSquare() {
        return mBitmapToSquare;
    }

    /**
     * 设置正方形扫描线条 Bitmap
     * @param bitmapToSquare 正方形扫描线条 Bitmap
     * @return {@link ScanShapeView}
     */
    public ScanShapeView setBitmapToSquare(Bitmap bitmapToSquare) {
        this.mBitmapToSquare = bitmapToSquare;
        // 刷新颜色
        refLineColorToSquare();
        return this;
    }

    /**
     * 获取正方形扫描线条向上 ( 下 ) 边距
     * @return 正方形扫描线条向上 ( 下 ) 边距
     */
    public float getLineMarginTopToSquare() {
        return mLineMarginTopToSquare;
    }

    /**
     * 设置正方形扫描线条向上 ( 下 ) 边距
     * @param lineMarginTopToSquare 正方形扫描线条向上 ( 下 ) 边距
     * @return {@link ScanShapeView}
     */
    public ScanShapeView setLineMarginTopToSquare(float lineMarginTopToSquare) {
        if (lineMarginTopToSquare < 0f) {
            lineMarginTopToSquare = 0f;
        }
        this.mLineMarginTopToSquare = lineMarginTopToSquare;
        return this;
    }

    /**
     * 获取正方形扫描线条向左 ( 右 ) 边距
     * @return 正方形扫描线条向左 ( 右 ) 边距
     */
    public float getLineMarginLeftToSquare() {
        return mLineMarginLeftToSquare;
    }

    /**
     * 设置正方形扫描线条向左 ( 右 ) 边距
     * @param lineMarginLeftToSquare 正方形扫描线条向左 ( 右 ) 边距
     * @return {@link ScanShapeView}
     */
    public ScanShapeView setLineMarginLeftToSquare(float lineMarginLeftToSquare) {
        if (lineMarginLeftToSquare < 0f) {
            lineMarginLeftToSquare = 0f;
        }
        this.mLineMarginLeftToSquare = lineMarginLeftToSquare;
        return this;
    }

    /**
     * 获取正方形线条动画颜色 ( 着色 )
     * @return 正方形线条动画颜色 ( 着色 )
     */
    public int getLineColorToSquare() {
        return mLineColorToSquare;
    }

    /**
     * 设置正方形线条动画 ( 着色 )
     * @param lineColorToSquare 正方形线条动画颜色 ( 着色 )
     * @return {@link ScanShapeView}
     */
    public ScanShapeView setLineColorToSquare(int lineColorToSquare) {
        this.mLineColorToSquare = lineColorToSquare;
        // 刷新颜色
        refLineColorToSquare();
        return this;
    }

    // =========
    // = 六边形 =
    // =========

    /**
     * 获取六边形线条动画 ( 线条宽度 )
     * @return 六边形线条动画 ( 线条宽度 )
     */
    public float getLineWidthToHexagon() {
        return mLinePaintToHexagon.getStrokeWidth();
    }

    /**
     * 设置六边形线条动画 ( 线条宽度 )
     * @param lineWidthToHexagon 六边形线条动画 ( 线条宽度 )
     * @return {@link ScanShapeView}
     */
    public ScanShapeView setLineWidthToHexagon(float lineWidthToHexagon) {
        if (lineWidthToHexagon <= 0) {
            lineWidthToHexagon = this.mBorderWidth;
        }
        mLinePaintToHexagon.setStrokeWidth(lineWidthToHexagon);
        return this;
    }

    /**
     * 获取六边形线条动画 ( 线条边距 )
     * @return 六边形线条动画 ( 线条边距 )
     */
    public float getLineMarginToHexagon() {
        return mLineMarginToHexagon;
    }

    /**
     * 设置六边形线条动画 ( 线条边距 )
     * @param lineMarginToHexagon 六边形线条动画 ( 线条边距 )
     * @return {@link ScanShapeView}
     */
    public ScanShapeView setLineMarginToHexagon(float lineMarginToHexagon) {
        this.mLineMarginToHexagon = lineMarginToHexagon;
        return this;
    }

    /**
     * 获取六边形线条动画方向 ( true = 从左到右, false = 从右到左 )
     * @return 六边形线条动画方向 ( true = 从左到右, false = 从右到左 )
     */
    public boolean isLineAnimDirection() {
        return mLineAnimDirection;
    }

    /**
     * 设置六边形线条动画方向 ( true = 从左到右, false = 从右到左 )
     * @param lineAnimDirection 六边形线条动画方向
     * @return {@link ScanShapeView}
     */
    public ScanShapeView setLineAnimDirection(boolean lineAnimDirection) {
        this.mLineAnimDirection = lineAnimDirection;
        return this;
    }

    /**
     * 获取六边形线条动画颜色
     * @return 六边形线条动画颜色
     */
    public int getLineColorToHexagon() {
        return mLineColorToHexagon;
    }

    /**
     * 设置六边形线条动画颜色
     * @param lineColorToHexagon 六边形线条动画颜色
     * @return {@link ScanShapeView}
     */
    public ScanShapeView setLineColorToHexagon(int lineColorToHexagon) {
        this.mLineColorToHexagon = lineColorToHexagon;
        // 刷新颜色
        refLineColorToHexagon();
        return this;
    }

    // ========
    // = 环形 =
    // ========

    /**
     * 获取环形扫描线条 Bitmap
     * @return 环形扫描线条 Bitmap
     */
    public Bitmap getBitmapToAnnulus() {
        return mBitmapToAnnulus;
    }

    /**
     * 设置环形扫描线条 Bitmap
     * @param bitmapToAnnulus 环形扫描线条 Bitmap
     * @return {@link ScanShapeView}
     */
    public ScanShapeView setBitmapToAnnulus(Bitmap bitmapToAnnulus) {
        this.mBitmapToAnnulus = bitmapToAnnulus;
        // 刷新颜色
        refLineColorToAnnulus();
        return this;
    }

    /**
     * 获取环形线条动画颜色 ( 着色 )
     * @return 环形线条动画颜色 ( 着色 )
     */
    public int getLineColorToAnnulus() {
        return mLineColorToAnnulus;
    }

    /**
     * 设置环形线条动画 ( 着色 )
     * @param lineColorToAnnulus 环形线条动画 ( 着色 )
     * @return {@link ScanShapeView}
     */
    public ScanShapeView setLineColorToAnnulus(int lineColorToAnnulus) {
        this.mLineColorToAnnulus = lineColorToAnnulus;
        // 刷新颜色
        refLineColorToAnnulus();
        return this;
    }

    /**
     * 获取环形扫描线条速度
     * @return 环形扫描线条速度
     */
    public float getLineOffsetSpeedToAnnulus() {
        return mLineOffsetSpeedToAnnulus;
    }

    /**
     * 设置环形扫描线条速度
     * @param lineOffsetSpeedToAnnulus 尽量接近 3 - 6
     * @return {@link ScanShapeView}
     */
    public ScanShapeView setLineOffsetSpeedToAnnulus(float lineOffsetSpeedToAnnulus) {
        if (lineOffsetSpeedToAnnulus < 0) {
            lineOffsetSpeedToAnnulus = 4f;
        }
        this.mLineOffsetSpeedToAnnulus = lineOffsetSpeedToAnnulus;
        return this;
    }

    /**
     * 获取环形对应的环是否绘制
     * @return 环形对应的环是否绘制
     */
    public boolean[] getAnnulusDraws() {
        return mAnnulusDraws;
    }

    /**
     * 设置环形对应的环是否绘制
     * @param annulusDraws 环形对应的环是否绘制
     * @return {@link ScanShapeView}
     */
    public ScanShapeView setAnnulusDraws(boolean... annulusDraws) {
        if (annulusDraws == null) {
            annulusDraws = new boolean[]{true, true, true};
        }
        // 设置临时数据
        boolean[] temp = Arrays.copyOf(annulusDraws, 3);
        // 如果小于 3 位, 则特殊处理
        if (annulusDraws.length < 3) {
            // 没有传递的, 则使用之前的配置
            for (int i = annulusDraws.length; i < 3; i++) {
                temp[i] = this.mAnnulusDraws[i];
            }
        }
        this.mAnnulusDraws = temp;
        return this;
    }

    /**
     * 获取环形对应的环绘制颜色
     * @return 环形对应的环绘制颜色
     */
    public int[] getAnnulusColors() {
        return mAnnulusColors;
    }

    /**
     * 设置环形对应的环绘制颜色
     * @param annulusColors 环形对应的环绘制颜色
     * @return {@link ScanShapeView}
     */
    public ScanShapeView setAnnulusColors(@ColorInt int... annulusColors) {
        if (annulusColors == null) {
            annulusColors = new int[]{Color.BLUE, Color.RED, Color.WHITE};
        }
        // 设置临时数据
        int[] temp = Arrays.copyOf(annulusColors, 3);
        // 如果小于 3 位, 则特殊处理
        if (annulusColors.length < 3) {
            // 没有传递的, 则使用之前的配置
            for (int i = annulusColors.length; i < 3; i++) {
                temp[i] = this.mAnnulusColors[i];
            }
        }
        this.mAnnulusColors = temp;
        // 刷新环形画笔信息
        refPaintToAnnulus();
        return this;
    }

    /**
     * 获取环形对应的环绘制长度
     * @return 环形对应的环绘制长度
     */
    public int[] getAnnulusLengths() {
        return mAnnulusLengths;
    }

    /**
     * 设置环形对应的环绘制长度
     * @param annulusLengths 环形对应的环绘制长度
     * @return {@link ScanShapeView}
     */
    public ScanShapeView setAnnulusLengths(int... annulusLengths) {
        if (annulusLengths == null) {
            annulusLengths = new int[]{20, 30, 85};
        }
        // 设置临时数据
        int[] temp = Arrays.copyOf(annulusLengths, 3);
        // 如果小于 3 位, 则特殊处理
        if (annulusLengths.length < 3) {
            // 没有传递的, 则使用之前的配置
            for (int i = annulusLengths.length; i < 3; i++) {
                temp[i] = this.mAnnulusLengths[i];
            }
        }
        this.mAnnulusLengths = temp;
        return this;
    }

    /**
     * 获取环形对应的环绘制宽度
     * @return 环形对应的环绘制宽度
     */
    public float[] getAnnulusWidths() {
        return mAnnulusWidths;
    }

    /**
     * 设置环形对应的环绘制宽度
     * @param annulusWidths 环形对应的环绘制宽度
     * @return {@link ScanShapeView}
     */
    public ScanShapeView setAnnulusWidths(float... annulusWidths) {
        if (annulusWidths == null) {
            annulusWidths = new float[3];
            annulusWidths[0] = SizeUtils.dipConvertPx(3);
            annulusWidths[1] = SizeUtils.dipConvertPx(7);
            annulusWidths[2] = SizeUtils.dipConvertPx(7);
        }
        // 设置临时数据
        float[] temp = Arrays.copyOf(annulusWidths, 3);
        // 如果小于 3 位, 则特殊处理
        if (annulusWidths.length < 3) {
            // 没有传递的, 则使用之前的配置
            for (int i = annulusWidths.length; i < 3; i++) {
                temp[i] = this.mAnnulusWidths[i];
            }
        }
        this.mAnnulusWidths = temp;
        // 刷新环形画笔信息
        refPaintToAnnulus();
        return this;
    }

    /**
     * 获取环形对应的环绘制边距
     * @return 环形对应的环绘制边距
     */
    public float[] getAnnulusMargins() {
        return mAnnulusMargins;
    }

    /**
     * 设置环形对应的环绘制边距
     * @param annulusMargins 环形对应的环绘制边距
     * @return {@link ScanShapeView}
     */
    public ScanShapeView setAnnulusMargins(float... annulusMargins) {
        if (annulusMargins == null) {
            int dp = SizeUtils.dipConvertPx(7);
            annulusMargins = new float[]{dp, dp, dp};
        }
        // 设置临时数据
        float[] temp = Arrays.copyOf(annulusMargins, 3);
        // 如果小于 3 位, 则特殊处理
        if (annulusMargins.length < 3) {
            // 没有传递的, 则使用之前的配置
            for (int i = annulusMargins.length; i < 3; i++) {
                temp[i] = this.mAnnulusMargins[i];
            }
        }
        this.mAnnulusMargins = temp;
        return this;
    }

    // ===========
    // = 内部方法 =
    // ===========

    /**
     * 刷新环形画笔信息
     */
    private void refPaintToAnnulus() {
        // 循环重置宽度、颜色值
        for (int i = 0; i < 3; i++) {
            mAnnulusPaints[i].setColor(mAnnulusColors[i]);
            mAnnulusPaints[i].setStrokeWidth(mAnnulusWidths[i]);
        }
    }

    // ===========
    // = 计算相关 =
    // ===========

    /**
     * Math.sin 的参数为弧度, 使用起来不方便, 重新封装一个根据角度求 sin 的方法
     * @param num 角度
     * @return sin
     */
    private float sin(int num) {
        return (float) Math.sin(num * Math.PI / 180);
    }

    /**
     * 获取扫描区域左边边距 ( 左右相等 ) = ( View 宽度 - 扫描区域宽度 ) / 2
     * @return 扫描区域左边边距
     */
    private float getRegionMarginLeft() {
        return (getWidth() - mPointF.x) / 2;
    }

    /**
     * 获取扫描区域向上边距 ( 上下相等 ) = ( View 宽度 - 扫描区域宽度 ) / 2
     * @return 扫描区域向上边距
     */
    private float getRegionMarginTop() {
        return (getHeight() - mPointF.y) / 2;
    }

    /**
     * 计算扫描区域, 并返回区域信息
     * @return 扫描区域信息
     */
    private RectF calcShapeRegion() {
        return calcShapeRegion(0f);
    }

    /**
     * 计算扫描区域, 并返回区域信息
     * @param margin 边距
     * @return 扫描区域信息
     */
    private RectF calcShapeRegion(float margin) {
        // 获取左边边距
        float left = getRegionMarginLeft();
        // 获取向上边距
        float top = getRegionMarginTop();
        // 生成扫描区域信息
        return new RectF(
                left - margin, top - margin,
                mPointF.x + left + margin, mPointF.y + top + margin
        );
    }

    // ===========
    // = 绘制形状 =
    // ===========

    /**
     * 绘制计算形状 ( 边框外形 )
     * @param rectF  绘制区域块
     * @param canvas 画布
     * @param paint  画笔
     * @param isDraw 是否进行绘制
     * @return 绘制路径信息 {@link Path}
     */
    private Path makeShape(
            RectF rectF,
            Canvas canvas,
            Paint paint,
            boolean isDraw
    ) {
        // 绘制路径
        Path path = new Path();

        // 位置信息
        float r  = (rectF.right - rectF.left) / 2; // 半径
        float mX = (rectF.right + rectF.left) / 2; // X 轴中心点位置
        float mY = (rectF.top + rectF.bottom) / 2; // Y 轴中心点位置

        // 判断形状类型
        switch (mShapeType) {
            case Square: // 正方形
                boolean[] isBorderToSquare = new boolean[]{false, false};
                // 判断正方形描边类型
                switch (mBorderToSquare) {
                    case 0: // 表示只需要四个角落
                        isBorderToSquare[1] = true;
                        break;
                    case 1: // 表示只需要边缘
                        isBorderToSquare[0] = true;
                        break;
                    case 2: // 表示全部绘制
                        isBorderToSquare[0] = true;
                        isBorderToSquare[1] = true;
                        break;
                    default: // 默认只需要四个角落
                        isBorderToSquare[1] = true;
                        break;
                }

                if (isBorderToSquare[0]) {
                    Paint borderPaint = new Paint(paint);
                    // 判断是否也显示角落, 是的话才重置
                    if (isBorderToSquare[1]) {
                        borderPaint.setStrokeWidth(mBorderWidthToSquare);
                    }
                    // 完整绘制的横线 ( 正方形 )
                    path.moveTo(mX, mY - r); // 设置起始点
                    // 左边
                    path.lineTo(mX - r, mY - r); // 左边第一条上横线
                    path.lineTo(mX - r, mY + r); // 左边第二条竖线
//                    path.lineTo(mX - r, mY + r); // 左边第三条下横线 // 不绘制不会有直角
                    // 右边
                    path.lineTo(mX + r, mY + r); // 右边边第一条下横线
                    path.lineTo(mX + r, mY - r); // 右边边第二条竖线
                    path.close();
                    // 进行绘制
                    canvas.drawPath(path, borderPaint);
                }

                if (isBorderToSquare[1]) {
                    Paint borderPaint = new Paint(paint);
                    // 判断是否特殊处理
                    if (mSpecialToSquare) {
                        // 如果已经绘制边框, 则不设置圆角
                        if (isBorderToSquare[0] && borderPaint.getPathEffect() != null) {
                            borderPaint.setPathEffect(null);
                        }
                    }

                    rectF.left += mBorderWidth / 2;
                    rectF.top += mBorderWidth / 2;
                    rectF.right -= mBorderWidth / 2;
                    rectF.bottom -= mBorderWidth / 2;
                    // 四个角落的三角
                    Path leftTopPath = new Path();
                    leftTopPath.moveTo(rectF.left + mTriAngleLength, rectF.top);
                    leftTopPath.lineTo(rectF.left, rectF.top);
                    leftTopPath.lineTo(rectF.left, rectF.top + mTriAngleLength);
                    canvas.drawPath(leftTopPath, borderPaint);

                    Path rightTopPath = new Path();
                    rightTopPath.moveTo(rectF.right - mTriAngleLength, rectF.top);
                    rightTopPath.lineTo(rectF.right, rectF.top);
                    rightTopPath.lineTo(rectF.right, rectF.top + mTriAngleLength);
                    canvas.drawPath(rightTopPath, borderPaint);

                    Path leftBottomPath = new Path();
                    leftBottomPath.moveTo(rectF.left, rectF.bottom - mTriAngleLength);
                    leftBottomPath.lineTo(rectF.left, rectF.bottom);
                    leftBottomPath.lineTo(rectF.left + mTriAngleLength, rectF.bottom);
                    canvas.drawPath(leftBottomPath, borderPaint);

                    Path rightBottomPath = new Path();
                    rightBottomPath.moveTo(rectF.right - mTriAngleLength, rectF.bottom);
                    rightBottomPath.lineTo(rectF.right, rectF.bottom);
                    rightBottomPath.lineTo(rectF.right, rectF.bottom - mTriAngleLength);
                    canvas.drawPath(rightBottomPath, borderPaint);
                }
                break;
            case Hexagon: // 六方形
                // 对应 6 条线角度计算
                path.moveTo(mX, mY - r); // 1
                path.lineTo(mX + r * sin(60), mY - r / 2); // 3
                path.lineTo(mX + r * sin(60), mY + r / 2); // 5
                path.lineTo(mX, mY + r); // 6
                path.lineTo(mX - r * sin(60), mY + r / 2); // 4
                path.lineTo(mX - r * sin(60), mY - r / 2); // 2
                path.close();
                // 判断是否需要绘制
                if (isDraw) {
                    // 进行绘制
                    canvas.drawPath(path, paint);
                }
                break;
            case Annulus: // 环形
                // 判断是否动画中
                if (isAnimRunning()) {
                    // 判断是否绘制最外层
                    if (mAnnulusDraws[0]) {
                        // 第一个小弧度
                        canvas.drawArc(rectF, mAnnulusAngles[0], mAnnulusLengths[0], false, mAnnulusPaints[0]);
                        // 第二个小弧度
                        canvas.drawArc(rectF, mAnnulusAngles[0] + 180, mAnnulusLengths[0], false, mAnnulusPaints[0]);
                    }
                    // 判断是否绘制中间层
                    if (mAnnulusDraws[1]) {
                        // 计算缩放动画偏移
                        if (mAnimOffsetToAnnulus > 0) {
                            mAnimOffsetToAnnulus -= 2;
                        } else {
                            mAnimOffsetToAnnulus = 0f;
                        }
                        // 计算中间层间隔距离
                        float middleSpace = mAnnulusWidths[0] + mAnnulusWidths[1] + mAnnulusMargins[0] + mAnimOffsetToAnnulus;
                        canvas.drawCircle(mX, mY, r - middleSpace, mAnnulusPaints[1]);
                        // 中间层, 两个弧
                        if (mAnimOffsetToAnnulus == 0f && mAnnulusMargins[0] / 2 > 0f) { // 小于 0 则不绘制
                            // 计算中间层边距
                            float middleMargin = mAnnulusWidths[0] + mAnnulusWidths[1] + mAnnulusMargins[0] / 2;
                            // 计算新的路径
                            RectF outsiderRectF = new RectF(rectF);
                            outsiderRectF.left += middleMargin;
                            outsiderRectF.top += middleMargin;
                            outsiderRectF.right -= middleMargin;
                            outsiderRectF.bottom -= middleMargin;
                            // 第一个小弧度
                            canvas.drawArc(outsiderRectF, mAnnulusAngles[1], mAnnulusLengths[1], false, mAnnulusPaints[1]);
                            // 第二个小弧度
                            canvas.drawArc(outsiderRectF, mAnnulusAngles[1] + 180, mAnnulusLengths[1], false, mAnnulusPaints[1]);
                        }
                    }

                    // 判断是否绘制最内层
                    if (mAnnulusDraws[2]) {
                        // 计算最内层间隔距离
                        float insideSpace = mAnnulusWidths[0] + mAnnulusWidths[1] + mAnnulusWidths[2] + mAnnulusMargins[0];
                        // 计算新的路径
                        RectF outsiderRectF = new RectF(rectF);
                        outsiderRectF.left += insideSpace;
                        outsiderRectF.top += insideSpace;
                        outsiderRectF.right -= insideSpace;
                        outsiderRectF.bottom -= insideSpace;
                        // 绘制最内层, 4 个弧
                        canvas.drawArc(outsiderRectF, mAnnulusAngles[2], mAnnulusLengths[2], false, mAnnulusPaints[2]);
                        canvas.drawArc(outsiderRectF, mAnnulusAngles[2] + 90, mAnnulusLengths[2], false, mAnnulusPaints[2]);
                        canvas.drawArc(outsiderRectF, mAnnulusAngles[2] + 180, mAnnulusLengths[2], false, mAnnulusPaints[2]);
                        canvas.drawArc(outsiderRectF, mAnnulusAngles[2] + 270, mAnnulusLengths[2], false, mAnnulusPaints[2]);
                    }
                } else { // 停止结束状态
                    // 判断绘制动画效果 ( 只有结束后, 做的一个动画效果 )
                    boolean isDrawAnim = false;
                    // 判断是否绘制中间层
                    if (mAnnulusDraws[1]) {
                        // 计算中间层间隔距离
                        float middleSpace = mAnnulusWidths[0] + mAnnulusWidths[1] + mAnnulusMargins[0] + mAnimOffsetToAnnulus;
                        // 计算最内层间隔距离
                        float insideSpace = mAnnulusWidths[0] + mAnnulusWidths[1] + mAnnulusWidths[2] + mAnnulusMargins[0];
                        // 计算缩放动画偏移
                        if (middleSpace < insideSpace + mAnnulusWidths[1]) {
                            middleSpace += 2;
                            mAnimOffsetToAnnulus += 2;
                            isDrawAnim = true;
                        }
                        canvas.drawCircle(mX, mY, r - middleSpace, mAnnulusPaints[1]);
                    }

                    // 判断是否绘制最内层
                    if (mAnnulusDraws[2]) {
                        // 计算最内层间隔距离
                        float insideSpace = mAnnulusWidths[0] + mAnnulusWidths[1] + mAnnulusWidths[2] + mAnnulusMargins[0];
                        // 计算新的路径
                        RectF outsiderRectF = new RectF(rectF);
                        outsiderRectF.left += insideSpace;
                        outsiderRectF.top += insideSpace;
                        outsiderRectF.right -= insideSpace;
                        outsiderRectF.bottom -= insideSpace;
                        // 绘制最内层, 4 个弧
                        canvas.drawArc(outsiderRectF, mAnnulusAngles[2], mAnnulusLengths[2], false, mAnnulusPaints[2]);
                        canvas.drawArc(outsiderRectF, mAnnulusAngles[2] + 90, mAnnulusLengths[2], false, mAnnulusPaints[2]);
                        canvas.drawArc(outsiderRectF, mAnnulusAngles[2] + 180, mAnnulusLengths[2], false, mAnnulusPaints[2]);
                        canvas.drawArc(outsiderRectF, mAnnulusAngles[2] + 270, mAnnulusLengths[2], false, mAnnulusPaints[2]);
                    }

                    // 设置是否绘制
                    if (isDrawAnim) {
                        postInvalidate();
                    }
                }
                break;
        }
        return path;
    }

    /**
     * 绘制背景
     * @param rectF  绘制区域块
     * @param canvas 画布
     */
    private void makeBackground(
            RectF rectF,
            Canvas canvas
    ) {
        // 都小于 0 则不处理
        if (rectF.left <= 0 && rectF.top <= 0) {
            return;
        }

        Path leftPath  = new Path(); // 左边路径
        Path rightPath = new Path(); // 右边路径
        // 位置信息
        float r  = (rectF.right - rectF.left) / 2; // 半径
        float mX = (rectF.right + rectF.left) / 2; // X 轴中心点位置
        float mY = (rectF.top + rectF.bottom) / 2; // Y 轴中心点位置

        // 获取 View 宽度
        final int width = getWidth();
        // 获取 View 高度
        final int height = getHeight();

        // 判断形状类型
        switch (mShapeType) {
            case Square: // 正方形
                // 因为使用正方形, 如果使用圆角, 在拐角处, 会有圆圈, 所以拐角处, 统一加大边距处理
                // 解决路径拐角有圆圈, 透过底层颜色

                // 获取拐角大小
                float radius = getCornerRadius();
                // =
                leftPath.moveTo(mX, 0); // 设置起始点
                leftPath.lineTo(0 - radius, 0); // 从中间到顶部边缘
                leftPath.lineTo(0 - radius, height + radius); // 从顶部到最下面
                leftPath.lineTo(mX + radius, height + radius); // 底部到中间点
                leftPath.lineTo(mX + radius, rectF.bottom); // 中间点到区域底部
                leftPath.lineTo(mX + radius, rectF.bottom); // 再次绘制覆盖左侧底部中间上方拐角
                leftPath.lineTo(rectF.left, rectF.bottom); // 区域底部到区域左
                leftPath.lineTo(rectF.left, rectF.top); // 区域底部到区域顶部
                leftPath.lineTo(mX + radius, rectF.top); // 区域顶部到 ( 顶部 ) 中心点
                leftPath.lineTo(mX + radius, rectF.top); // 再次绘制覆盖左侧顶部中间上方拐角
                leftPath.lineTo(mX + radius, -radius); // 回到起始点
                leftPath.close();
                // 进行绘制背景
                canvas.drawPath(leftPath, mBackgroundPaint);
                // =
                rightPath.moveTo(mX, 0); // 设置起始点
                rightPath.lineTo(width + radius, 0); // 从中间到顶部边缘
                rightPath.lineTo(width + radius, height + radius); // 从顶部到最下面
                rightPath.lineTo(mX + radius, height + radius); // 底部到中间点
                rightPath.lineTo(mX + radius, rectF.bottom); // 中间点到区域底部
                rightPath.lineTo(mX + radius, rectF.bottom); // 再次绘制覆盖右侧底部中间上方拐角
                rightPath.lineTo(rectF.right, rectF.bottom); // 区域底部到区域右
                rightPath.lineTo(rectF.right, rectF.top); // 区域底部到区域顶部
                rightPath.lineTo(mX + radius, rectF.top); // 区域顶部到 ( 顶部 ) 中心点
                rightPath.lineTo(mX + radius, rectF.top); // 再次绘制覆盖右侧顶部中间上方拐角
                rightPath.lineTo(mX + radius, -radius); // 回到起始点
                rightPath.close();
                // 进行绘制背景
                canvas.drawPath(rightPath, mBackgroundPaint);
                break;
            case Hexagon: // 六方形
                leftPath.moveTo(0, 0); // 左上
                leftPath.lineTo(width / 2, 0); // 顶部中心点
                leftPath.lineTo(mX, mY - r); // 1
                leftPath.lineTo(mX - r * sin(60), mY - r / 2); // 2
                leftPath.lineTo(mX - r * sin(60), mY + r / 2); // 4
                leftPath.lineTo(mX, mY + r); // 6
                leftPath.lineTo(width / 2, height); // 底部中心点
                leftPath.lineTo(0, height); // 左下
                leftPath.close();
                // 进行绘制背景
                canvas.drawPath(leftPath, mBackgroundPaint);
                // =
                rightPath.moveTo(width, 0); // 右上
                rightPath.lineTo(width / 2, 0); // 顶部中心点
                rightPath.lineTo(mX, mY - r); // 1
                rightPath.lineTo(mX + r * sin(60), mY - r / 2); // 3
                rightPath.lineTo(mX + r * sin(60), mY + r / 2); // 5
                rightPath.lineTo(mX, mY + r); // 6
                rightPath.lineTo(width / 2, height); // 底部中心点
                rightPath.lineTo(width, height); // 右下
                rightPath.close();
                // 进行绘制背景
                canvas.drawPath(rightPath, mBackgroundPaint);
                break;
            case Annulus: // 环形
                leftPath.moveTo(mX, 0); // 中心点
                leftPath.lineTo(0, 0); // 顶部最左边
                leftPath.lineTo(0, height); // 底部最左边
                leftPath.lineTo(mX, height); // 底部中间
                leftPath.lineTo(mX, rectF.bottom); // 底部 bottom 位置
                leftPath.arcTo(rectF, -270, 180); // 从第三象限到第一象限
                //leftPath.lineTo(mX, rectF.top);
                leftPath.lineTo(mX, 0);
                leftPath.close();
                // 进行绘制背景
                canvas.drawPath(leftPath, mBackgroundPaint);
                // =
                rightPath.moveTo(mX, 0); // 中心点
                rightPath.lineTo(width, 0); // 顶部最右边
                rightPath.lineTo(width, height); // 底部最左边
                rightPath.lineTo(mX, height); // 底部中间
                rightPath.lineTo(mX, rectF.bottom); // 底部 bottom 位置
                rightPath.arcTo(rectF, -270, -180); // 从第三象限到第一象限
                //rightPath.lineTo(mX, rectF.top);
                rightPath.lineTo(mX, 0);
                rightPath.close();
                // 进行绘制背景
                canvas.drawPath(rightPath, mBackgroundPaint);
                break;
        }
    }

    /**
     * 计算动画相关信息
     * @param canvas 画布
     */
    private void makeAnim(Canvas canvas) {
        // 判断形状类型
        switch (mShapeType) {
            case Square: // 正方形
                // 正方形不需要绘制计算 ( 初始化 )
                break;
            case Hexagon: // 六边形
                // 获取绘制的区域 ( 绘制扫描区域 + 线条居于绘制边框距离 )
                RectF rectF = calcShapeRegion(mLineMarginToHexagon);
                // 线条路径计算重置起始位置
                RectF lineRectF = new RectF(0, 0, rectF.right - rectF.left, rectF.right - rectF.left);
                // 计算边距处理
                mLinePathToHexagon = makeShape(lineRectF, canvas, mLinePaintToHexagon, false);
                // 生成 Bitmap
                mBitmapToHexagon = Bitmap.createBitmap((int) (rectF.right - rectF.left), (int) (rectF.right - rectF.left), Bitmap.Config.ARGB_8888);
                // 生成新的 Canvas
                mCanvasToHexagon = new Canvas(mBitmapToHexagon);
                // 计算中心点
                mCenterToHexagon = ((rectF.right - rectF.left) / 2);
                break;
            case Annulus: // 环形
                // 环形不需要绘制计算 ( 初始化 )
                break;
        }
    }

    /**
     * 绘制动画相关处理
     * @param canvas 画布
     */
    private void drawAnim(Canvas canvas) {
        try {
            // 位置信息
            float r; // 半径
            float mX; // X 轴中心点位置
            float mY; // Y 轴中心点位置
            // 获取扫描区域大小
            RectF rectF;
            // 判断形状类型
            switch (mShapeType) {
                case Square: // 正方形
                    // 如果 bitmap 不为 null, 才处理
                    if (mBitmapToSquare != null) {
                        // 获取扫描区域大小 ( 正方形在内部绘制, 不需要加上外边距 )
                        rectF = calcShapeRegion();
                        // 计算边距处理
                        rectF.left = rectF.left + mLineMarginLeftToSquare;
                        rectF.top = rectF.top + mLineMarginTopToSquare;
                        rectF.right = rectF.right - mLineMarginLeftToSquare;
                        rectF.bottom = rectF.bottom - mLineMarginTopToSquare;
                        // 循环划线, 从上到下
                        if (mLineOffsetToSquare > rectF.bottom - rectF.top - mDFCommonDP) {
                            mLineOffsetToSquare = 0;
                        } else {
                            mLineOffsetToSquare = mLineOffsetToSquare + 6;
                            // 设置线条区域
                            Rect lineRect = new Rect();
                            lineRect.left = (int) rectF.left;
                            lineRect.top = (int) (rectF.top + mLineOffsetToSquare);
                            lineRect.right = (int) rectF.right;
                            lineRect.bottom = (int) (rectF.top + mDFCommonDP + mLineOffsetToSquare);
                            canvas.drawBitmap(mBitmapToSquare, null, lineRect, mLinePaintToSquare);
                        }
                    }
                    break;
                case Hexagon: // 六边形
                    // 获取扫描区域大小
                    rectF = calcShapeRegion(mLineMarginToHexagon);
                    // 位置信息
                    r = (rectF.right - rectF.left) / 2; // 半径
//                    mX = (rectF.right + rectF.left) / 2; // X 轴中心点位置
                    mY = (rectF.top + rectF.bottom) / 2; // Y 轴中心点位置
                    // 绘制线条
                    canvas.drawBitmap(mBitmapToHexagon, rectF.left, mY - r, null);
                    break;
                case Annulus: // 环形
                    // 动画运行中才处理
                    if (isAnimRunning()) {
                        if (mBitmapToAnnulus != null) {
                            float margin = -(mAnnulusWidths[0] + mAnnulusWidths[1] + mAnnulusWidths[2] + mAnnulusMargins[0]);
                            // 获取扫描区域大小
                            rectF = calcShapeRegion(margin);
                            // 位置信息
                            r = (rectF.right - rectF.left) / 2; // 半径
                            mX = (rectF.right + rectF.left) / 2; // X 轴中心点位置
//                            mY = (rectF.top + rectF.bottom) / 2; // Y 轴中心点位置
                            // =
                            mLineOffsetToAnnulus += mLineOffsetSpeedToAnnulus;
                            if (mLineOffsetToAnnulus > r * 2 - (mAnnulusWidths[2])) {
                                mLineOffsetToAnnulus = 0;
                            }
                            float p1, p2, hw;
                            if (mLineOffsetToAnnulus >= r) {
                                p1 = (mLineOffsetToAnnulus - r) * (mLineOffsetToAnnulus - r);
                            } else {
                                p1 = (r - mLineOffsetToAnnulus) * (r - mLineOffsetToAnnulus);
                            }
                            p2 = r * r;
                            hw = (int) Math.sqrt(p2 - p1) - mAnnulusMargins[2];

                            // 获取图片高度
                            int bitmapHeight = mBitmapToAnnulus.getHeight();

                            RectF lineRectF = new RectF();
                            lineRectF.left = mX - hw;
                            lineRectF.top = rectF.top + mLineOffsetToAnnulus;
                            lineRectF.right = mX + hw;
                            lineRectF.bottom = rectF.top + mLineOffsetToAnnulus + bitmapHeight;
                            canvas.drawBitmap(mBitmapToAnnulus, null, lineRectF, null);
                        }
                    }
                    break;
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "drawAnim %s", mShapeType.name());
        }
    }

    // ===========
    // = 动画相关 =
    // ===========

    // ===========
    // = 内部处理 =
    // ===========

    /**
     * 重新设置动画
     * @param init 是否重新初始化
     */
    private void resetAnim(boolean init) {
        if (init) {
            // 停止动画
            stopAnim();
            // 重置动画处理
            initAnim();
        }
        // 表示需要重新处理
        mIsReAnim = true;
    }

    // 动画操作
    private final int START_ANIM = 10; // 开始动画
    private final int STOP_ANIM  = 11; // 停止动画

    /**
     * 启动动画
     */
    public void startAnim() {
        // 已经在运行了, 则不处理
        if (isAnimRunning()) return;
        animSwitch(START_ANIM);
    }

    /**
     * 停止动画
     */
    public void stopAnim() {
        animSwitch(STOP_ANIM);
    }

    /**
     * 动画开关统一方法
     * @param operate 动画操作
     */
    private void animSwitch(int operate) {
        try {
            // 动画对象
            ValueAnimator valueAnimator = null;
            // 判断形状类型
            switch (mShapeType) {
                case Square: // 正方形
                    valueAnimator = mAnimToSquare;
                    break;
                case Hexagon: // 六边形
                    valueAnimator = mAnimToHexagon;
                    break;
                case Annulus: // 环形
                    valueAnimator = mAnimToAnnulus;
                    break;
            }
            if (valueAnimator != null) {
                switch (operate) {
                    case START_ANIM:
                        valueAnimator.start();
                        break;
                    case STOP_ANIM:
                        valueAnimator.cancel();
                        break;
                }
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "animSwitch %s", mShapeType.name());
        }
    }

    /**
     * 是否动画运行中
     * @return {@code true} yes, {@code false} no
     */
    public boolean isAnimRunning() {
        try {
            // 判断形状类型
            switch (mShapeType) {
                case Square: // 正方形
                    if (mAnimToSquare != null) {
                        return mAnimToSquare.isRunning();
                    }
                    break;
                case Hexagon: // 六边形
                    if (mAnimToHexagon != null) {
                        return mAnimToHexagon.isRunning();
                    }
                    break;
                case Annulus: // 环形
                    if (mAnimToAnnulus != null) {
                        return mAnimToAnnulus.isRunning();
                    }
                    break;
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "isAnimRunning %s", mShapeType.name());
        }
        return false;
    }

    // =================
    // = 正方形动画参数 =
    // =================

    /**
     * 重置线条颜色 ( 进行着色 )
     */
    private void refLineColorToSquare() {
        if (mBitmapToSquare != null && mLineColorToSquare != 0) {
            try {
                // 转换 Drawable
                Drawable drawable     = new BitmapDrawable(getResources(), mBitmapToSquare);
                Drawable tintDrawable = DrawableCompat.wrap(drawable);
                // 进行着色
                DrawableCompat.setTint(tintDrawable, mLineColorToSquare);
                // 保存着色后的 Bitmap
//                mBitmapToSquare = ((BitmapDrawable) tintDrawable).getBitmap();
                // 临时 Bitmap
                Bitmap bitmap;
                // 创建新的 Bitmap
                if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
                    bitmap = Bitmap.createBitmap(1, 1,
                            drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
                } else {
                    bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                            drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
                }
                Canvas canvas = new Canvas(bitmap);
                drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                drawable.draw(canvas);
                // 保存着色后的 Bitmap
                mBitmapToSquare = bitmap;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "refLineColorToSquare");
            }
        }
    }

    // =================
    // = 六边形动画参数 =
    // =================

    private float   mStartLinePoint; // 起点位置
    private float   mEndLinePoint; // 结束点位置
    private float   mOffsetLinePoint; // 移动点位置
    // 线条颜色数组 ( 渐变 )
    private int[]   mLineColorArray;
    // 线条移动位置数组
    private float[] mLinePathArray;
    // 线条颜色
    private int     mLineColorToHexagon = Color.WHITE;
    // 线条 rgb 色值
    private int     mLineRed, mLineGreen, mLineBlue;
    // 透明度 0, 透明度 255 对应的颜色
    private int mLineTran00Color, mLineTran255Color;

    /**
     * 刷新线条颜色
     * <pre>
     *     每次设置颜色值, 需要同步更新
     * </pre>
     */
    private void refLineColorToHexagon() {
        // 获取红色色值
        mLineRed = Color.red(mLineColorToHexagon);
        // 获取绿色色值
        mLineGreen = Color.green(mLineColorToHexagon);
        // 获取蓝色色值
        mLineBlue = Color.blue(mLineColorToHexagon);
        // 透明度 0 线条
        mLineTran00Color = Color.argb(0, mLineRed, mLineGreen, mLineBlue);
        mLineTran255Color = Color.argb(255, mLineRed, mLineGreen, mLineBlue);
    }

    // ===============
    // = 环形动画参数 =
    // ===============

    /**
     * 重置线条颜色 ( 进行着色 )
     */
    private void refLineColorToAnnulus() {
        if (mBitmapToAnnulus != null && mLineColorToAnnulus != 0) {
            try {
                // 转换 Drawable
                Drawable drawable     = new BitmapDrawable(getResources(), mBitmapToAnnulus);
                Drawable tintDrawable = DrawableCompat.wrap(drawable);
                // 进行着色
                DrawableCompat.setTint(tintDrawable, mLineColorToAnnulus);
                // 保存着色后的 Bitmap
//                mBitmapToAnnulus = ((BitmapDrawable) tintDrawable).getBitmap();
                // 临时 Bitmap
                Bitmap bitmap;
                // 创建新的 Bitmap
                if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
                    bitmap = Bitmap.createBitmap(1, 1,
                            drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
                } else {
                    bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                            drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
                }
                Canvas canvas = new Canvas(bitmap);
                drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                drawable.draw(canvas);
                // 保存着色后的 Bitmap
                mBitmapToAnnulus = bitmap;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "refLineColorToAnnulus");
            }
        }
    }

    // =============
    // = 动画初始化 =
    // =============

    /**
     * 初始化动画
     */
    private void initAnim() {
        // 判断是否绘制动画
        if (!mIsDrawAnim) return;
        // 判断形状类型
        switch (mShapeType) {
            case Square: // 正方形
                mAnimToSquare = ValueAnimator.ofInt(10, 20);
                mAnimToSquare.setDuration(mLineDurationToSquare);
                mAnimToSquare.setRepeatCount(ValueAnimator.INFINITE);
                mAnimToSquare.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationRepeat(Animator animation) {
                        super.onAnimationRepeat(animation);
                        // 绘制
                        postInvalidate();
                    }
                });
                break;
            case Hexagon: // 六边形
                // 刷新颜色值
                refLineColorToHexagon();
                // 360, 0 从左到右, 0, 360 从右到左
                mAnimToHexagon = ValueAnimator.ofInt(360, 0); // 暂时不修改该方法, 在内部更新方法写逻辑计算
                mAnimToHexagon.setDuration(5 * 360);
                mAnimToHexagon.setRepeatCount(ValueAnimator.INFINITE);
                mAnimToHexagon.setInterpolator(new TimeInterpolator() {
                    @Override
                    public float getInterpolation(float input) {
                        return input;
                    }
                });
                mAnimToHexagon.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        if (mCanvasToHexagon == null) {
                            return;
                        }
                        // 获取递减值 => ofInt 360 递减
                        Integer value = (Integer) animation.getAnimatedValue();
                        // 从左往右动画
                        if (mLineAnimDirection) {
                            mStartLinePoint = value / 360f;
                            if (mStartLinePoint >= 0.25f) {
                                mStartLinePoint = mStartLinePoint - 0.25f;
                            } else {
                                mStartLinePoint = mStartLinePoint + 0.75f;
                            }
                            // 计算结束点的位置
                            mEndLinePoint = mStartLinePoint + 0.5f;
                            if (mStartLinePoint > 0.5f) {
                                // 计算移动距离, 对应的透明度
                                mOffsetLinePoint = mStartLinePoint - 0.5f;
                                // 转换 argb
                                int splitColor = Color.argb((int) (255 * (mOffsetLinePoint / 0.5f)), mLineRed, mLineGreen, mLineBlue);
                                // 设置线条颜色
                                mLineColorArray = new int[]{splitColor, mLineTran00Color, 0, 0, mLineTran255Color, splitColor};
                                // 设置线条动画路径
                                mLinePathArray = new float[]{0f, mOffsetLinePoint, mOffsetLinePoint, mStartLinePoint, mStartLinePoint, 1f};
                            } else {
                                // 设置线条颜色
                                mLineColorArray = new int[]{0, 0, mLineTran255Color, mLineTran00Color, 0, 0};
                                // 设置线条动画路径
                                mLinePathArray = new float[]{0f, mStartLinePoint, mStartLinePoint, mEndLinePoint, mEndLinePoint, 1f};
                            }
                        } else { // 从右向左动画
                            mStartLinePoint = (360 - value) / 360f;
                            if (mStartLinePoint >= 0.25f) {
                                mStartLinePoint = mStartLinePoint - 0.25f;
                            } else {
                                mStartLinePoint = mStartLinePoint + 0.75f;
                            }
                            // 计算结束点的位置
                            mEndLinePoint = mStartLinePoint + 0.5f;
                            if (mStartLinePoint > 0.5f) {
                                // 计算移动距离, 对应的透明度
                                mOffsetLinePoint = mStartLinePoint - 0.5f;
                                // 转换 argb
                                int splitColor = Color.argb((int) (255 * (mOffsetLinePoint / 0.5f)), mLineRed, mLineGreen, mLineBlue);
                                // 设置线条颜色
                                mLineColorArray = new int[]{splitColor, mLineTran00Color, 0, 0, mLineTran255Color, splitColor};
                                // 设置线条动画路径
                                mLinePathArray = new float[]{0f, mOffsetLinePoint, mOffsetLinePoint, mStartLinePoint, mStartLinePoint, 1f};
                            } else {
                                // 设置线条颜色
                                mLineColorArray = new int[]{0, 0, mLineTran255Color, mLineTran00Color, 0, 0};
                                // 设置线条动画路径
                                mLinePathArray = new float[]{0f, mStartLinePoint, mStartLinePoint, mEndLinePoint, mEndLinePoint, 1f};
                            }
                        }
                        // 绘制线条渐变效果
                        SweepGradient mShader = new SweepGradient(mCenterToHexagon, mCenterToHexagon, mLineColorArray, mLinePathArray);
                        mLinePaintToHexagon.setShader(mShader);
                        mCanvasToHexagon.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                        mCanvasToHexagon.drawPath(mLinePathToHexagon, mLinePaintToHexagon);
                        // 绘制
                        postInvalidate();
                    }
                });
                break;
            case Annulus: // 环形
                mAnimToAnnulus = ValueAnimator.ofInt(10, 20);
                mAnimToAnnulus.setDuration(1L);
                mAnimToAnnulus.setRepeatCount(ValueAnimator.INFINITE);
                mAnimToAnnulus.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationRepeat(Animator animation) {
                        super.onAnimationRepeat(animation);
                        // 动画计算旋转
                        mAnnulusAngles[0] += 4;
                        mAnnulusAngles[1] += 2;
                        if (mIsOffsetMaxToAnnulus) {
                            if (mAnnulusAngles[2] == 30) {
                                mIsOffsetMaxToAnnulus = false;
                            } else {
                                mAnnulusAngles[2]++;
                            }
                        } else {
                            if (mAnnulusAngles[2] == -30) {
                                mIsOffsetMaxToAnnulus = true;
                            } else {
                                mAnnulusAngles[2]--;
                            }
                        }
                        // 绘制
                        postInvalidate();
                    }
                });
                break;
        }
    }
}