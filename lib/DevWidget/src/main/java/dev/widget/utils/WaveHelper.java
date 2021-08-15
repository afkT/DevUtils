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
        mWaveView.setShowWave(true);
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
