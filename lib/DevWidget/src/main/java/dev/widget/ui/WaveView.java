package dev.widget.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import dev.widget.R;

/**
 * detail: 波浪 View
 *
 * @author gelitenight
 * <pre>
 *     Android 实现波浪效果 - WaveView
 *     @see <a href="https://www.jianshu.com/p/e711e22e053e"/>
 *     <p></p>
 *     app:dev_amplitudeRatio=""
 *     app:dev_waveWaterLevel=""
 *     app:dev_waveLengthRatio=""
 *     app:dev_waveShiftRatio=""
 *     app:dev_behindWaveColor=""
 *     app:dev_frontWaveColor=""
 *     app:dev_waveShape=""
 *     app:dev_showWave=""
 * </pre>
 */
public class WaveView
        extends View {
    /**
     * +------------------------+
     * |<--wave length->        |______
     * |   /\          |   /\   |  |
     * |  /  \         |  /  \  | amplitude
     * | /    \        | /    \ |  |
     * |/      \       |/      \|__|____
     * |        \      /        |  |
     * |         \    /         |  |
     * |          \  /          |  |
     * |           \/           | water level
     * |                        |  |
     * |                        |  |
     * +------------------------+__|____
     */

    // Amplitude ( 振幅 ) - 波浪垂直振动时偏离水面的最大距离
    private static final float     DEFAULT_AMPLITUDE_RATIO   = 0.05f;
    // Wate Level ( 水位 ) - 波浪静止时水面距离底部的高度
    private static final float     DEFAULT_WATER_LEVEL_RATIO = 0.5f;
    // Wave Length ( 波长 ) - 一个完整的波浪的水平长度
    private static final float     DEFAULT_WAVE_LENGTH_RATIO = 1.0f;
    // Wave Shift ( 偏移 ) - 波浪相对于初始位置的水平偏移
    private static final float     DEFAULT_WAVE_SHIFT_RATIO  = 0.0f;
    // 波浪背景色
    public static final  int       DEFAULT_BEHIND_WAVE_COLOR = Color.parseColor("#28FFFFFF");
    // 波浪前景色
    public static final  int       DEFAULT_FRONT_WAVE_COLOR  = Color.parseColor("#3CFFFFFF");
    // 波浪形状
    public static final  ShapeType DEFAULT_WAVE_SHAPE        = ShapeType.CIRCLE;

    /**
     * detail: 波浪形状类型
     *
     * @author gelitenight
     */
    public enum ShapeType {
        CIRCLE,
        SQUARE
    }

    // if true, the shader will display the wave
    private boolean mShowWave;

    // shader containing repeated waves
    private BitmapShader mWaveShader;
    // shader matrix
    private Matrix       mShaderMatrix;
    // paint to draw wave
    private Paint        mViewPaint;
    // paint to draw border
    private Paint        mBorderPaint;

    private float  mDefaultAmplitude;
    private float  mDefaultWaterLevel;
    private float  mDefaultWaveLength;
    private double mDefaultAngularFrequency;

    private float mAmplitudeRatio  = DEFAULT_AMPLITUDE_RATIO;
    private float mWaveLengthRatio = DEFAULT_WAVE_LENGTH_RATIO;
    private float mWaterLevelRatio = DEFAULT_WATER_LEVEL_RATIO;
    private float mWaveShiftRatio  = DEFAULT_WAVE_SHIFT_RATIO;

    private int       mBehindWaveColor = DEFAULT_BEHIND_WAVE_COLOR;
    private int       mFrontWaveColor  = DEFAULT_FRONT_WAVE_COLOR;
    private ShapeType mShapeType       = DEFAULT_WAVE_SHAPE;

    public WaveView(Context context) {
        super(context);
        initAttrs(context, null, 0, 0);
    }

    public WaveView(
            Context context,
            AttributeSet attrs
    ) {
        super(context, attrs);
        initAttrs(context, attrs, 0, 0);
    }

    public WaveView(
            Context context,
            AttributeSet attrs,
            int defStyleAttr
    ) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs, defStyleAttr, 0);
    }

    /**
     * 初始化
     *
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
        if (context != null && attrs != null) {
            TypedArray a = context.obtainStyledAttributes(
                    attrs, R.styleable.DevWidget, defStyleAttr, defStyleRes
            );
            mAmplitudeRatio  = a.getFloat(R.styleable.DevWidget_dev_amplitudeRatio, DEFAULT_AMPLITUDE_RATIO);
            mWaterLevelRatio = a.getFloat(R.styleable.DevWidget_dev_waveWaterLevel, DEFAULT_WATER_LEVEL_RATIO);
            mWaveLengthRatio = a.getFloat(R.styleable.DevWidget_dev_waveLengthRatio, DEFAULT_WAVE_LENGTH_RATIO);
            mWaveShiftRatio  = a.getFloat(R.styleable.DevWidget_dev_waveShiftRatio, DEFAULT_WAVE_SHIFT_RATIO);
            mBehindWaveColor = a.getColor(R.styleable.DevWidget_dev_behindWaveColor, DEFAULT_BEHIND_WAVE_COLOR);
            mFrontWaveColor  = a.getColor(R.styleable.DevWidget_dev_frontWaveColor, DEFAULT_FRONT_WAVE_COLOR);
            mShapeType       = a.getInt(R.styleable.DevWidget_dev_waveShape, 0) == 0 ? ShapeType.CIRCLE : ShapeType.SQUARE;
            mShowWave        = a.getBoolean(R.styleable.DevWidget_dev_showWave, true);
            a.recycle();
        }
        initialize();
    }

    private void initialize() {
        mShaderMatrix = new Matrix();
        mViewPaint    = new Paint();
        mViewPaint.setAntiAlias(true);
    }

    // =

    @Override
    protected void onSizeChanged(
            int w,
            int h,
            int oldw,
            int oldh
    ) {
        super.onSizeChanged(w, h, oldw, oldh);
        createShader();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mShowWave && mWaveShader != null) {
            if (mViewPaint.getShader() == null) {
                mViewPaint.setShader(mWaveShader);
            }

            // sacle shader according to mWaveLengthRatio and mAmplitudeRatio
            // this decides the size(mWaveLengthRatio for width, mAmplitudeRatio for height) of waves
            mShaderMatrix.setScale(
                    mWaveLengthRatio / DEFAULT_WAVE_LENGTH_RATIO,
                    mAmplitudeRatio / DEFAULT_AMPLITUDE_RATIO,
                    0, mDefaultWaterLevel
            );

            // translate shader according to mWaveShiftRatio and mWaterLevelRatio
            // this decides the start position(mWaveShiftRatio for x, mWaterLevelRatio for y) of waves
            mShaderMatrix.postTranslate(
                    mWaveShiftRatio * getWidth(),
                    (DEFAULT_WATER_LEVEL_RATIO - mWaterLevelRatio) * getHeight()
            );

            mWaveShader.setLocalMatrix(mShaderMatrix);

            float borderWidth = mBorderPaint == null ? 0f : mBorderPaint.getStrokeWidth();
            switch (mShapeType) {
                case CIRCLE:
                    if (borderWidth > 0) {
                        canvas.drawCircle(
                                getWidth() / 2f, getHeight() / 2f,
                                (getWidth() - borderWidth) / 2f - 1f,
                                mBorderPaint
                        );
                    }
                    float radius = getWidth() / 2f - borderWidth;
                    canvas.drawCircle(
                            getWidth() / 2f, getHeight() / 2f,
                            radius, mViewPaint
                    );
                    break;
                case SQUARE:
                    if (borderWidth > 0) {
                        canvas.drawRect(
                                borderWidth / 2f, borderWidth / 2f,
                                getWidth() - borderWidth / 2f - 0.5f,
                                getHeight() - borderWidth / 2f - 0.5f,
                                mBorderPaint
                        );
                    }
                    canvas.drawRect(
                            borderWidth, borderWidth,
                            getWidth() - borderWidth,
                            getHeight() - borderWidth,
                            mViewPaint
                    );
                    break;
            }
        } else {
            mViewPaint.setShader(null);
        }
    }

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * Create the shader with default waves which repeat horizontally, and clamp vertically
     */
    private void createShader() {
        mDefaultAngularFrequency = 2.0f * Math.PI / DEFAULT_WAVE_LENGTH_RATIO / getWidth();
        mDefaultAmplitude        = getHeight() * DEFAULT_AMPLITUDE_RATIO;
        mDefaultWaterLevel       = getHeight() * DEFAULT_WATER_LEVEL_RATIO;
        mDefaultWaveLength       = getWidth();

        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Paint wavePaint = new Paint();
        wavePaint.setStrokeWidth(2);
        wavePaint.setAntiAlias(true);

        // Draw default waves into the bitmap
        // y = Asin(ωx+φ)+h
        final int endX = getWidth() + 1;
        final int endY = getHeight() + 1;

        float[] waveY = new float[endX];

        wavePaint.setColor(mBehindWaveColor);
        for (int beginX = 0; beginX < endX; beginX++) {
            double wx     = beginX * mDefaultAngularFrequency;
            float  beginY = (float) (mDefaultWaterLevel + mDefaultAmplitude * Math.sin(wx));
            canvas.drawLine(beginX, beginY, beginX, endY, wavePaint);
            waveY[beginX] = beginY;
        }

        wavePaint.setColor(mFrontWaveColor);
        final int wave2Shift = (int) (mDefaultWaveLength / 4);
        for (int beginX = 0; beginX < endX; beginX++) {
            canvas.drawLine(
                    beginX, waveY[(beginX + wave2Shift) % endX],
                    beginX, endY, wavePaint
            );
        }

        // use the bitamp to create the shader
        mWaveShader = new BitmapShader(
                bitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP
        );
        mViewPaint.setShader(mWaveShader);
    }

    // ==============
    // = 对外公开方法 =
    // ==============

    public float getWaveShiftRatio() {
        return mWaveShiftRatio;
    }

    /**
     * Shift the wave horizontally according to <code>waveShiftRatio</code>.
     *
     * @param waveShiftRatio Should be 0 ~ 1. Default to be 0.
     *                       Result of waveShiftRatio multiples width of WaveView is the length to shift.
     */
    public WaveView setWaveShiftRatio(float waveShiftRatio) {
        if (mWaveShiftRatio != waveShiftRatio) {
            mWaveShiftRatio = waveShiftRatio;
            invalidate();
        }
        return this;
    }

    public float getWaterLevelRatio() {
        return mWaterLevelRatio;
    }

    /**
     * Set water level according to <code>waterLevelRatio</code>.
     *
     * @param waterLevelRatio Should be 0 ~ 1. Default to be 0.5.
     *                        Ratio of water level to WaveView height.
     */
    public WaveView setWaterLevelRatio(float waterLevelRatio) {
        if (mWaterLevelRatio != waterLevelRatio) {
            mWaterLevelRatio = waterLevelRatio;
            invalidate();
        }
        return this;
    }

    public float getAmplitudeRatio() {
        return mAmplitudeRatio;
    }

    /**
     * Set vertical size of wave according to <code>amplitudeRatio</code>
     *
     * @param amplitudeRatio Default to be 0.05. Result of amplitudeRatio + waterLevelRatio should be less than 1.
     *                       Ratio of amplitude to height of WaveView.
     */
    public WaveView setAmplitudeRatio(float amplitudeRatio) {
        if (mAmplitudeRatio != amplitudeRatio) {
            mAmplitudeRatio = amplitudeRatio;
            invalidate();
        }
        return this;
    }

    public float getWaveLengthRatio() {
        return mWaveLengthRatio;
    }

    /**
     * Set horizontal size of wave according to <code>waveLengthRatio</code>
     *
     * @param waveLengthRatio Default to be 1.
     *                        Ratio of wave length to width of WaveView.
     */
    public WaveView setWaveLengthRatio(float waveLengthRatio) {
        mWaveLengthRatio = waveLengthRatio;
        return this;
    }

    public boolean isShowWave() {
        return mShowWave;
    }

    public WaveView setShowWave(boolean showWave) {
        mShowWave = showWave;
        return this;
    }

    public WaveView setBorder(
            int width,
            int color
    ) {
        if (mBorderPaint == null) {
            mBorderPaint = new Paint();
            mBorderPaint.setAntiAlias(true);
            mBorderPaint.setStyle(Style.STROKE);
        }
        mBorderPaint.setColor(color);
        mBorderPaint.setStrokeWidth(width);

        invalidate();
        return this;
    }

    public WaveView setWaveColor(
            int behindWaveColor,
            int frontWaveColor
    ) {
        mBehindWaveColor = behindWaveColor;
        mFrontWaveColor  = frontWaveColor;

        if (getWidth() > 0 && getHeight() > 0) {
            // need to recreate shader when color changed
            mWaveShader = null;
            createShader();
            invalidate();
        }
        return this;
    }

    public WaveView setShapeType(ShapeType shapeType) {
        mShapeType = shapeType;
        invalidate();
        return this;
    }
}