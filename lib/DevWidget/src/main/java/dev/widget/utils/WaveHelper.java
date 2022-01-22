package dev.widget.utils;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.List;

import dev.widget.ui.WaveView;

/**
 * detail: 波浪 View Helper 类
 * @author Ttt
 */
public class WaveHelper {

    // 波浪 View
    private WaveView    mWaveView;
    // 波浪动画集合
    private AnimatorSet mAnimatorSet;

    /**
     * 构造函数
     * @param waveView 波浪 View
     */
    private WaveHelper(final WaveView waveView) {
        mWaveView = waveView;
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 获取 WaveHelper
     * @param waveView 波浪 View
     * @return {@link WaveHelper}
     */
    public static WaveHelper get(final WaveView waveView) {
        return new WaveHelper(waveView);
    }

    // ==========
    // = 动画开关 =
    // ==========

    /**
     * 启动动画
     */
    public void start() {
        // 显示波浪效果
        setShowWave(true);

        if (mAnimatorSet != null) {
            mAnimatorSet.start();
        }
    }

    /**
     * 关闭动画
     */
    public void cancel() {
        if (mAnimatorSet != null) {
            mAnimatorSet.end();
        }
    }

    // ===========
    // = get/set =
    // ===========

    /**
     * 获取波浪垂直振幅比率
     * @return 波浪垂直振幅比率
     */
    public float getAmplitudeRatio() {
        if (mWaveView != null) {
            return mWaveView.getAmplitudeRatio();
        }
        return WaveView.DEFAULT_AMPLITUDE_RATIO;
    }

    /**
     * 设置波浪垂直振幅比率
     * <pre>
     *     默认为 0.05 并且 amplitudeRatio + waterLevelRatio 需小于 1
     * </pre>
     * @param amplitudeRatio 波浪垂直振幅比率
     * @return {@link WaveHelper}
     */
    public WaveHelper setAmplitudeRatio(float amplitudeRatio) {
        if (mWaveView != null) {
            mWaveView.setAmplitudeRatio(amplitudeRatio);
        }
        return this;
    }

    /**
     * 获取波浪水位比率
     * @return 波浪水位比率
     */
    public float getWaterLevelRatio() {
        if (mWaveView != null) {
            return mWaveView.getWaterLevelRatio();
        }
        return WaveView.DEFAULT_WATER_LEVEL_RATIO;
    }

    /**
     * 设置波浪水位比率
     * <pre>
     *     默认为 0.5 值范围在 0-1 之间
     * </pre>
     * @param waterLevelRatio 波浪水位比率
     * @return {@link WaveHelper}
     */
    public WaveHelper setWaterLevelRatio(float waterLevelRatio) {
        if (mWaveView != null) {
            mWaveView.setWaterLevelRatio(waterLevelRatio);
        }
        return this;
    }

    /**
     * 获取波浪波长比率
     * @return 波浪波长比率
     */
    public float getWaveLengthRatio() {
        if (mWaveView != null) {
            return mWaveView.getWaveLengthRatio();
        }
        return WaveView.DEFAULT_WAVE_LENGTH_RATIO;
    }

    /**
     * 设置波浪波长比率
     * <pre>
     *     默认为 1
     * </pre>
     * @param waveLengthRatio 波浪波长比率
     * @return {@link WaveHelper}
     */
    public WaveHelper setWaveLengthRatio(float waveLengthRatio) {
        if (mWaveView != null) {
            mWaveView.setWaveLengthRatio(waveLengthRatio);
        }
        return this;
    }

    /**
     * 获取波浪水平偏移比率
     * @return 波浪水平偏移比率
     */
    public float getWaveShiftRatio() {
        if (mWaveView != null) {
            return mWaveView.getWaveShiftRatio();
        }
        return WaveView.DEFAULT_WAVE_SHIFT_RATIO;
    }

    /**
     * 设置波浪水平偏移比率
     * <pre>
     *     默认为 0 值范围在 0-1 之间
     * </pre>
     * @param waveShiftRatio 波浪水平偏移比率
     * @return {@link WaveHelper}
     */
    public WaveHelper setWaveShiftRatio(float waveShiftRatio) {
        if (mWaveView != null) {
            mWaveView.setWaveShiftRatio(waveShiftRatio);
        }
        return this;
    }

    /**
     * 获取边框宽度
     * @return 边框宽度
     */
    public float getBorderWidth() {
        if (mWaveView != null) {
            return mWaveView.getBorderWidth();
        }
        return WaveView.DEFAULT_BORDER_WIDTH;
    }

    /**
     * 获取边框颜色
     * @return 边框颜色
     */
    public int getBorderColor() {
        if (mWaveView != null) {
            return mWaveView.getBorderColor();
        }
        return WaveView.DEFAULT_BORDER_COLOR;
    }

    /**
     * 设置边框宽度、颜色
     * @param width 边框宽度
     * @param color 边框颜色
     * @return {@link WaveHelper}
     */
    public WaveHelper setBorder(
            float width,
            int color
    ) {
        if (mWaveView != null) {
            mWaveView.setBorder(width, color);
        }
        return this;
    }

    /**
     * 获取波浪背景色
     * @return 波浪背景色
     */
    public int getBehindWaveColor() {
        if (mWaveView != null) {
            return mWaveView.getBehindWaveColor();
        }
        return WaveView.DEFAULT_BEHIND_WAVE_COLOR;
    }

    /**
     * 获取波浪前景色
     * @return 波浪前景色
     */
    public int getFrontWaveColor() {
        if (mWaveView != null) {
            return mWaveView.getFrontWaveColor();
        }
        return WaveView.DEFAULT_FRONT_WAVE_COLOR;
    }

    /**
     * 设置波浪颜色
     * @param behindWaveColor 波浪背景色
     * @param frontWaveColor  波浪前景色
     * @return {@link WaveHelper}
     */
    public WaveHelper setWaveColor(
            int behindWaveColor,
            int frontWaveColor
    ) {
        if (mWaveView != null) {
            mWaveView.setWaveColor(behindWaveColor, frontWaveColor);
        }
        return this;
    }

    /**
     * 获取波浪外形形状
     * @return 波浪外形形状
     */
    public WaveView.ShapeType getShapeType() {
        if (mWaveView != null) {
            return mWaveView.getShapeType();
        }
        return WaveView.ShapeType.CIRCLE;
    }

    /**
     * 设置波浪外形形状
     * @param shapeType 波浪外形形状
     * @return {@link WaveHelper}
     */
    public WaveHelper setShapeType(WaveView.ShapeType shapeType) {
        if (mWaveView != null) {
            mWaveView.setShapeType(shapeType);
        }
        return this;
    }

    /**
     * 是否进行波浪图形处理
     * @return {@code true} yes, {@code false} no
     */
    public boolean isShowWave() {
        if (mWaveView != null) {
            return mWaveView.isShowWave();
        }
        return true;
    }

    /**
     * 设置是否进行波浪图形处理
     * @param showWave {@code true} yes, {@code false} no
     * @return {@link WaveHelper}
     */
    public WaveHelper setShowWave(boolean showWave) {
        if (mWaveView != null) {
            mWaveView.setShowWave(showWave);
        }
        return this;
    }

    // =============
    // = 动画构建方法 =
    // =============

    /**
     * 通过属性动画进行设置波浪 View 动画效果
     * @param property 波浪属性动画配置信息
     * @return {@link WaveHelper}
     */
    public WaveHelper buildPropertyAnimation(final WaveProperty property) {
        if (mWaveView != null && property != null) {
            List<Animator> animators = new ArrayList<>();

            // 让波浪一直向右移动, 效果就是波形一直在波动
            ObjectAnimator waveShiftAnim = ObjectAnimator.ofFloat(
                    mWaveView, "waveShiftRatio",
                    property.waveShiftRatioStart,
                    property.waveShiftRatioEnd
            );
            waveShiftAnim.setRepeatCount(ValueAnimator.INFINITE);
            waveShiftAnim.setDuration(property.waveShiftRatioMillis);
            waveShiftAnim.setInterpolator(new LinearInterpolator());
            animators.add(waveShiftAnim);

            // 水位高度从 xx 到 xx 值
            ObjectAnimator waterLevelAnim = ObjectAnimator.ofFloat(
                    mWaveView, "waterLevelRatio",
                    property.waterLevelRatioStart,
                    property.waterLevelRatioEnd
            );
            waterLevelAnim.setDuration(property.waterLevelRatioMillis);
            waterLevelAnim.setInterpolator(new DecelerateInterpolator());
            animators.add(waterLevelAnim);

            // 波浪大小 ( 上下波动 ) 效果, 先大后小, 再从小变大
            ObjectAnimator amplitudeAnim = ObjectAnimator.ofFloat(
                    mWaveView, "amplitudeRatio",
                    property.amplitudeRatioStart,
                    property.amplitudeRatioEnd
            );
            amplitudeAnim.setRepeatCount(ValueAnimator.INFINITE);
            amplitudeAnim.setRepeatMode(ValueAnimator.REVERSE);
            amplitudeAnim.setDuration(property.amplitudeRatioMillis);
            amplitudeAnim.setInterpolator(new LinearInterpolator());
            animators.add(amplitudeAnim);

            mAnimatorSet = new AnimatorSet();
            mAnimatorSet.playTogether(animators);
        }
        return this;
    }

    /**
     * detail: 波浪属性动画配置信息
     * @author Ttt
     */
    public static final class WaveProperty {

        // 波浪移动方向效果属性值
        private final float waveShiftRatioStart;
        private final float waveShiftRatioEnd;
        private final long  waveShiftRatioMillis;

        // 波浪大小 ( 上下波动 ) 效果属性值
        private final float amplitudeRatioStart;
        private final float amplitudeRatioEnd;
        private final long  amplitudeRatioMillis;

        // 水位高度属性值
        private final float waterLevelRatioStart;
        private final float waterLevelRatioEnd;
        private final long  waterLevelRatioMillis;

        private WaveProperty(final Builder builder) {
            this.waveShiftRatioStart   = builder.waveShiftRatioStart;
            this.waveShiftRatioEnd     = builder.waveShiftRatioEnd;
            this.waveShiftRatioMillis  = builder.waveShiftRatioMillis;
            this.amplitudeRatioStart   = builder.amplitudeRatioStart;
            this.amplitudeRatioEnd     = builder.amplitudeRatioEnd;
            this.amplitudeRatioMillis  = builder.amplitudeRatioMillis;
            this.waterLevelRatioStart  = builder.waterLevelRatioStart;
            this.waterLevelRatioEnd    = builder.waterLevelRatioEnd;
            this.waterLevelRatioMillis = builder.waterLevelRatioMillis;
        }

        public static final class Builder {

            // 波浪移动方向效果属性值
            private float waveShiftRatioStart  = 0F;
            private float waveShiftRatioEnd    = 1F;
            private long  waveShiftRatioMillis = 1000L;

            // 波浪大小 ( 上下波动 ) 效果属性值
            private float amplitudeRatioStart  = 0.0001F;
            private float amplitudeRatioEnd    = 0.05F;
            private long  amplitudeRatioMillis = 5000L;

            // 水位高度属性值
            private float waterLevelRatioStart  = 0F;
            private float waterLevelRatioEnd    = 0F;
            private long  waterLevelRatioMillis = 5000L;

            // ==========
            // = 构建方法 =
            // ==========

            public Builder() {
            }

            public Builder(final WaveProperty property) {
                if (property != null) {
                    this.waveShiftRatioStart   = property.waveShiftRatioStart;
                    this.waveShiftRatioEnd     = property.waveShiftRatioEnd;
                    this.waveShiftRatioMillis  = property.waveShiftRatioMillis;
                    this.amplitudeRatioStart   = property.amplitudeRatioStart;
                    this.amplitudeRatioEnd     = property.amplitudeRatioEnd;
                    this.amplitudeRatioMillis  = property.amplitudeRatioMillis;
                    this.waterLevelRatioStart  = property.waterLevelRatioStart;
                    this.waterLevelRatioEnd    = property.waterLevelRatioEnd;
                    this.waterLevelRatioMillis = property.waterLevelRatioMillis;
                }
            }

            public Builder(final Builder builder) {
                if (builder != null) {
                    this.waveShiftRatioStart   = builder.waveShiftRatioStart;
                    this.waveShiftRatioEnd     = builder.waveShiftRatioEnd;
                    this.waveShiftRatioMillis  = builder.waveShiftRatioMillis;
                    this.amplitudeRatioStart   = builder.amplitudeRatioStart;
                    this.amplitudeRatioEnd     = builder.amplitudeRatioEnd;
                    this.amplitudeRatioMillis  = builder.amplitudeRatioMillis;
                    this.waterLevelRatioStart  = builder.waterLevelRatioStart;
                    this.waterLevelRatioEnd    = builder.waterLevelRatioEnd;
                    this.waterLevelRatioMillis = builder.waterLevelRatioMillis;
                }
            }

            public WaveProperty build() {
                return new WaveProperty(this);
            }

            // ===========
            // = get/set =
            // ===========

            // =====================
            // = 波浪移动方向效果属性值 =
            // =====================

            public float getWaveShiftRatioStart() {
                return waveShiftRatioStart;
            }

            public float getWaveShiftRatioEnd() {
                return waveShiftRatioEnd;
            }

            public long getWaveShiftRatioMillis() {
                return waveShiftRatioMillis;
            }

            /**
             * 设置波浪移动方向效果属性值
             * @param waveShiftRatioMillis 动画时间
             * @return {@link Builder}
             */
            public Builder setWaveShiftRatioMillis(long waveShiftRatioMillis) {
                this.waveShiftRatioMillis = waveShiftRatioMillis;
                return this;
            }

            /**
             * 设置波浪移动方向效果属性值
             * @param waveShiftRatioStart 开始值
             * @param waveShiftRatioEnd   结束值
             * @return {@link Builder}
             */
            public Builder setWaveShiftRatio(
                    float waveShiftRatioStart,
                    float waveShiftRatioEnd
            ) {
                return setWaveShiftRatio(
                        waveShiftRatioStart,
                        waveShiftRatioEnd,
                        this.waveShiftRatioMillis
                );
            }

            /**
             * 设置波浪移动方向效果属性值
             * @param waveShiftRatioStart  开始值
             * @param waveShiftRatioEnd    结束值
             * @param waveShiftRatioMillis 动画时间
             * @return {@link Builder}
             */
            public Builder setWaveShiftRatio(
                    float waveShiftRatioStart,
                    float waveShiftRatioEnd,
                    long waveShiftRatioMillis
            ) {
                this.waveShiftRatioStart  = waveShiftRatioStart;
                this.waveShiftRatioEnd    = waveShiftRatioEnd;
                this.waveShiftRatioMillis = waveShiftRatioMillis;
                return this;
            }

            // ===============================
            // = 波浪大小 ( 上下波动 ) 效果属性值 =
            // ===============================

            public float getAmplitudeRatioStart() {
                return amplitudeRatioStart;
            }

            public float getAmplitudeRatioEnd() {
                return amplitudeRatioEnd;
            }

            public long getAmplitudeRatioMillis() {
                return amplitudeRatioMillis;
            }

            /**
             * 设置波浪大小 ( 上下波动 ) 效果属性值
             * @param amplitudeRatioMillis 动画时间
             * @return {@link Builder}
             */
            public Builder setAmplitudeRatioMillis(long amplitudeRatioMillis) {
                this.amplitudeRatioMillis = amplitudeRatioMillis;
                return this;
            }

            /**
             * 设置波浪大小 ( 上下波动 ) 效果属性值
             * @param amplitudeRatioStart 开始值
             * @param amplitudeRatioEnd   结束值
             * @return {@link Builder}
             */
            public Builder setAmplitudeRatio(
                    float amplitudeRatioStart,
                    float amplitudeRatioEnd
            ) {
                return setAmplitudeRatio(
                        amplitudeRatioStart,
                        amplitudeRatioEnd,
                        this.amplitudeRatioMillis
                );
            }

            /**
             * 设置波浪大小 ( 上下波动 ) 效果属性值
             * @param amplitudeRatioStart  开始值
             * @param amplitudeRatioEnd    结束值
             * @param amplitudeRatioMillis 动画时间
             * @return {@link Builder}
             */
            public Builder setAmplitudeRatio(
                    float amplitudeRatioStart,
                    float amplitudeRatioEnd,
                    long amplitudeRatioMillis
            ) {
                this.amplitudeRatioStart  = amplitudeRatioStart;
                this.amplitudeRatioEnd    = amplitudeRatioEnd;
                this.amplitudeRatioMillis = amplitudeRatioMillis;
                return this;
            }

            // ===============
            // = 水位高度属性值 =
            // ===============

            public float getWaterLevelRatioStart() {
                return waterLevelRatioStart;
            }

            public float getWaterLevelRatioEnd() {
                return waterLevelRatioEnd;
            }

            public float getWaterLevelRatioMillis() {
                return waterLevelRatioMillis;
            }

            /**
             * 设置水位高度属性值
             * @param waterLevelRatioMillis 动画时间
             * @return {@link Builder}
             */
            public Builder setWaterLevelRatioMillis(long waterLevelRatioMillis) {
                this.waterLevelRatioMillis = waterLevelRatioMillis;
                return this;
            }

            /**
             * 设置水位高度属性值
             * @param waterLevelRatioStart 开始值
             * @param waterLevelRatioEnd   结束值
             * @return {@link Builder}
             */
            public Builder setWaterLevelRatio(
                    float waterLevelRatioStart,
                    float waterLevelRatioEnd
            ) {
                return setWaterLevelRatio(
                        waterLevelRatioStart,
                        waterLevelRatioEnd,
                        this.waterLevelRatioMillis
                );
            }

            /**
             * 设置水位高度属性值
             * @param waterLevelRatioStart  开始值
             * @param waterLevelRatioEnd    结束值
             * @param waterLevelRatioMillis 动画时间
             * @return {@link Builder}
             */
            public Builder setWaterLevelRatio(
                    float waterLevelRatioStart,
                    float waterLevelRatioEnd,
                    long waterLevelRatioMillis
            ) {
                this.waterLevelRatioStart  = waterLevelRatioStart;
                this.waterLevelRatioEnd    = waterLevelRatioEnd;
                this.waterLevelRatioMillis = waterLevelRatioMillis;
                return this;
            }
        }
    }
}