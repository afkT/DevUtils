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
        initAnimation();
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

    // ==========
    // = 内部方法 =
    // ==========

    private void initAnimation() {
        List<Animator> animators = new ArrayList<>();

        // horizontal animation.
        // wave waves infinitely.
        ObjectAnimator waveShiftAnim = ObjectAnimator.ofFloat(
                mWaveView, "waveShiftRatio", 0f, 1f);
        waveShiftAnim.setRepeatCount(ValueAnimator.INFINITE);
        waveShiftAnim.setDuration(1000);
        waveShiftAnim.setInterpolator(new LinearInterpolator());
        animators.add(waveShiftAnim);

        // vertical animation.
        // water level increases from 0 to center of WaveView
        ObjectAnimator waterLevelAnim = ObjectAnimator.ofFloat(
                mWaveView, "waterLevelRatio", 0f, 0.7f);
        waterLevelAnim.setDuration(10000);
        waterLevelAnim.setInterpolator(new DecelerateInterpolator());
        animators.add(waterLevelAnim);

        // amplitude animation.
        // wave grows big then grows small, repeatedly
        ObjectAnimator amplitudeAnim = ObjectAnimator.ofFloat(
                mWaveView, "amplitudeRatio", 0.0001f, 0.05f);
        amplitudeAnim.setRepeatCount(ValueAnimator.INFINITE);
        amplitudeAnim.setRepeatMode(ValueAnimator.REVERSE);
        amplitudeAnim.setDuration(5000);
        amplitudeAnim.setInterpolator(new LinearInterpolator());
        animators.add(amplitudeAnim);

        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.playTogether(animators);
    }
}
