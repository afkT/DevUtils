package dev.widget.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dev.utils.app.ResourceUtils;
import dev.widget.R;

/**
 * detail: 自定义点赞效果 View
 * @author Ttt
 * <pre>
 *     通过 setDrawables、setDrawablesById 设置点赞漂浮 Icon
 *     注意事项:
 *     新添加的 View 属于居中下, 向下边距为子 View 总高度
 *     如果 FlowLikeView layout_width 设置为非 wrap_content 则内部子 View 需要 android:layout_gravity="bottom|center"
 *     这样点赞效果, 才会在子 View 上方显示
 *     <p></p>
 *     app:dev_animDuration=""
 *     app:dev_iconHeight=""
 *     app:dev_iconWidth=""
 * </pre>
 */
public class FlowLikeView
        extends FrameLayout {

    // 在 XML 布局文件中添加的子 View 的总高度
    private int mChildViewHeight;
    // View 宽度、高度
    private int mViewWidth, mViewHeight;
    // 添加动画 View Layout 参数
    private LayoutParams   mLayoutParams;
    // 用于产生随机数
    private Random         mRandom;
    // Icon 集合
    private List<Drawable> mDrawables;
    // 点赞 Icon 宽高
    private int            mIconWidth, mIconHeight;
    // 点赞动画执行时间
    private long mAnimDuration = 2000L;

    public FlowLikeView(Context context) {
        super(context);
        initAttrs(context, null);
    }

    public FlowLikeView(
            Context context,
            AttributeSet attrs
    ) {
        super(context, attrs);
        initAttrs(context, attrs);
    }

    public FlowLikeView(
            Context context,
            AttributeSet attrs,
            int defStyleAttr
    ) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FlowLikeView(
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
        // 初始化操作
        mRandom = new Random();
        mDrawables = new ArrayList<>();

        if (context != null && attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DevWidget);
            mAnimDuration = a.getInt(R.styleable.DevWidget_dev_animDuration, 2000);
            mIconWidth = a.getLayoutDimension(R.styleable.DevWidget_dev_iconWidth, 0);
            mIconHeight = a.getLayoutDimension(R.styleable.DevWidget_dev_iconHeight, 0);
            a.recycle();
        }

        // 重置 LayoutParams
        resetLayoutParams();
    }

    @Override
    protected void onMeasure(
            int widthMeasureSpec,
            int heightMeasureSpec
    ) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mChildViewHeight <= 0) {
            for (int i = 0, size = getChildCount(); i < size; i++) {
                View childView = getChildAt(i);
                measureChild(childView, widthMeasureSpec, heightMeasureSpec);
                mChildViewHeight += childView.getMeasuredHeight();
            }
            // 重置 LayoutParams
            resetLayoutParams();
//            // 设置底部间距 ( 防止当 addView 显示与动画起始位置相差太远 )
//            mLayoutParams.bottomMargin = mChildViewHeight;
        }
    }

    @Override
    protected void onSizeChanged(
            int w,
            int h,
            int oldw,
            int oldh
    ) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = getWidth();
        mViewHeight = getHeight();
    }

    // ===========
    // = 内部处理 =
    // ===========

    /**
     * detail: 动画结束监听器, 用于释放无用的资源
     * @author Ttt
     */
    private class AnimationEndListener
            extends AnimatorListenerAdapter {

        private final View target;

        public AnimationEndListener(View target) {
            this.target = target;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            removeView(target);
        }
    }

    /**
     * detail: 动画曲线路径更新监听器, 用于动态更新动画作用对象的位置
     * @author Ttt
     */
    private class CurveUpdateLister
            implements ValueAnimator.AnimatorUpdateListener {

        private final View target;

        public CurveUpdateLister(View target) {
            this.target = target;
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            if (target != null) {
                // 获取当前动画运行的状态值, 使得动 x 画作用对象沿着曲线 ( 涉及贝塞儿曲线 ) 运动
                PointF pointF = (PointF) animation.getAnimatedValue();
                target.setX(pointF.x);
                target.setY(pointF.y);
                target.setAlpha(1 - animation.getAnimatedFraction());
            }
        }
    }

    /**
     * detail: 自定义估值算法, 计算对象当前运动的具体位置 Point
     * @author Ttt
     */
    private class CurveEvaluator
            implements TypeEvaluator<PointF> {

        private final PointF ctrlPointF;

        public CurveEvaluator(PointF ctrlPointF) {
            this.ctrlPointF = ctrlPointF;
        }

        @Override
        public PointF evaluate(
                float fraction,
                PointF startValue,
                PointF endValue
        ) {
            float  leftTime     = 1.0f - fraction;
            PointF resultPointF = new PointF();
            // 二阶贝塞儿曲线
            resultPointF.x = (float) Math.pow(leftTime, 2) * startValue.x + 2 * fraction * leftTime * ctrlPointF.x
                    + ((float) Math.pow(fraction, 2)) * endValue.x;
            resultPointF.y = (float) Math.pow(leftTime, 2) * startValue.y + 2 * fraction * leftTime * ctrlPointF.y
                    + ((float) Math.pow(fraction, 2)) * endValue.y;
            return resultPointF;
        }
    }

    /**
     * 生成贝塞儿曲线的控制点
     * @param value 设置控制点 y 轴上取值区域
     * @return 控制点的 x y 坐标
     */
    private PointF generateCTRLPointF(int value) {
        PointF pointF = new PointF();
        pointF.x = mViewWidth / 2 - mRandom.nextInt(100);
        pointF.y = mRandom.nextInt(mViewHeight / value);
        return pointF;
    }

    /**
     * 生成曲线运动动画
     * @param target 动画作用 View
     * @return 动画集合
     */
    private ValueAnimator generateCurveAnimation(View target) {
        CurveEvaluator evaluator = new CurveEvaluator(generateCTRLPointF(1));
        ValueAnimator valueAnimator = ValueAnimator.ofObject(evaluator,
                new PointF((mViewWidth - mIconWidth) / 2, mViewHeight - mChildViewHeight - mIconHeight),
                new PointF((mViewWidth) / 2 + (mRandom.nextBoolean() ? 1 : -1) * mRandom.nextInt(100), 0)
        );
        valueAnimator.setDuration(mAnimDuration);
        valueAnimator.addUpdateListener(new CurveUpdateLister(target));
        valueAnimator.setTarget(target);
        return valueAnimator;
    }

    /**
     * 生成进入动画
     * @param target 动画作用 View
     * @return 动画集合
     */
    private AnimatorSet generateEnterAnimation(View target) {
        ObjectAnimator alpha          = ObjectAnimator.ofFloat(target, "alpha", 0.2f, 1f);
        ObjectAnimator scaleX         = ObjectAnimator.ofFloat(target, "scaleX", 0.5f, 1f);
        ObjectAnimator scaleY         = ObjectAnimator.ofFloat(target, "scaleY", 0.5f, 1f);
        AnimatorSet    enterAnimation = new AnimatorSet();
        enterAnimation.playTogether(alpha, scaleX, scaleY);
        enterAnimation.setDuration(10);
        enterAnimation.setTarget(target);
        return enterAnimation;
    }

    /**
     * 开始动画处理
     * @param target 动画作用 View
     */
    private void startAnimation(View target) {
        // 进入动画
        AnimatorSet enterAnimator = generateEnterAnimation(target);
        // 路径动画
        ValueAnimator curveAnimator = generateCurveAnimation(target);

        // 设置动画集合, 先执行进入动画, 最后再执行运动曲线动画
        AnimatorSet finalAnimatorSet = new AnimatorSet();
        finalAnimatorSet.setTarget(target);
        finalAnimatorSet.playSequentially(enterAnimator, curveAnimator);
        finalAnimatorSet.addListener(new AnimationEndListener(target));
        finalAnimatorSet.start();
    }

    /**
     * 重置 LayoutParams
     */
    private void resetLayoutParams() {
        mLayoutParams = new LayoutParams(mIconWidth, mIconHeight);
        mLayoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
        // 设置底部间距 ( 防止当 addView 显示与动画起始位置相差太远 )
        mLayoutParams.bottomMargin = mChildViewHeight;
    }

    // ===============
    // = 对外公开方法 =
    // ===============

    /**
     * 点赞操作
     */
    public void like() {
        int iconSizes = mDrawables.size();
        if (iconSizes != 0) {
            ImageView likeView = new ImageView(getContext());
            likeView.setImageDrawable(mDrawables.get(mRandom.nextInt(iconSizes)));
            likeView.setLayoutParams(mLayoutParams);

            addView(likeView);
            startAnimation(likeView);
        }
    }

    /**
     * 获取 Icon 集合
     * @return Icon 集合
     */
    public List<Drawable> getDrawables() {
        return mDrawables;
    }

    /**
     * 设置 Icon 集合
     * @param drawables Icon 集合
     * @return {@link FlowLikeView}
     */
    public FlowLikeView setDrawables(List<Drawable> drawables) {
        this.mDrawables.clear();
        if (drawables != null) {
            this.mDrawables.addAll(drawables);
            // 如果没有设置 Icon 宽高则获取传入的 Icon 集合宽高
            if (mIconWidth == 0 || mIconHeight == 0) {
                for (Drawable drawable : drawables) {
                    if (drawable != null) {
                        mIconWidth = drawable.getIntrinsicWidth();
                        mIconHeight = drawable.getIntrinsicHeight();
                        resetLayoutParams();
                        break;
                    }
                }
            }
        }
        return this;
    }

    /**
     * 设置 Icon 集合
     * @param drawableIds Icon 集合
     * @return {@link FlowLikeView}
     */
    public FlowLikeView setDrawablesById(@DrawableRes final int... drawableIds) {
        if (drawableIds != null) {
            List<Drawable> lists = new ArrayList<>();
            for (int drawableId : drawableIds) {
                Drawable drawable = ResourceUtils.getDrawable(drawableId);
                lists.add(drawable);
            }
            return setDrawables(lists);
        }
        return this;
    }

    /**
     * 获取点赞 Icon 宽度
     * @return 点赞 Icon 宽度
     */
    public int getIconWidth() {
        return mIconWidth;
    }

    /**
     * 设置点赞 Icon 宽度
     * @param iconWidth 点赞 Icon 宽度
     * @return {@link FlowLikeView}
     */
    public FlowLikeView setIconWidth(int iconWidth) {
        this.mIconWidth = iconWidth;
        resetLayoutParams();
        return this;
    }

    /**
     * 获取点赞 Icon 高度
     * @return 点赞 Icon 高度
     */
    public int getIconHeight() {
        return mIconHeight;
    }

    /**
     * 设置点赞 Icon 高度
     * @param iconHeight 点赞 Icon 高度
     * @return {@link FlowLikeView}
     */
    public FlowLikeView setIconHeight(int iconHeight) {
        this.mIconHeight = iconHeight;
        resetLayoutParams();
        return this;
    }

    /**
     * 获取点赞动画执行时间
     * @return 点赞动画执行时间
     */
    public long getAnimDuration() {
        return mAnimDuration;
    }

    /**
     * 设置点赞动画执行时间
     * @param animDuration 点赞动画执行时间
     * @return {@link FlowLikeView}
     */
    public FlowLikeView setAnimDuration(long animDuration) {
        this.mAnimDuration = animDuration;
        return this;
    }
}