package dev.widget.ui;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import dev.utils.LogPrintUtils;
import dev.utils.app.ViewUtils;
import dev.widget.R;

/**
 * detail: 翻转卡片 View
 * @author Ttt
 */
public class FlipCardView
        extends FrameLayout {

    // 日志 TAG
    public static final String TAG = FlipCardView.class.getSimpleName();

    // 正面 Layout
    private FrameLayout mFrontLayout;
    // 背面 Layout
    private FrameLayout mBackLayout;
    // 当前是否显示正面 Layout
    private boolean     mFront    = true;
    // 翻转出入动画
    private Animator    mOutAnim;
    private Animator    mInAnim;
    // 数据源适配器
    private Adapter     mAdapter;
    // 当前显示索引
    private int         mPosition = 0;

    public FlipCardView(Context context) {
        super(context);
        initialize();
    }

    public FlipCardView(
            Context context,
            AttributeSet attrs
    ) {
        super(context, attrs);
        initialize();
    }

    public FlipCardView(
            Context context,
            AttributeSet attrs,
            int defStyleAttr
    ) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FlipCardView(
            Context context,
            AttributeSet attrs,
            int defStyleAttr,
            int defStyleRes
    ) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize();
    }

    /**
     * 初始化
     */
    private void initialize() {
        removeAllViews();
        // 初始化 View
        mFrontLayout = new FrameLayout(getContext());
        mFrontLayout.setLayoutParams(
                new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        );
        mBackLayout = new FrameLayout(getContext());
        mBackLayout.setLayoutParams(
                new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        );
        addView(mBackLayout);
        addView(mFrontLayout);
        // 设置翻牌角度
        setFlipDistance(16000);
        // 初始化动画
        mOutAnim = AnimatorInflater.loadAnimator(getContext(), R.animator.dev_flip_card_out);
        mInAnim  = AnimatorInflater.loadAnimator(getContext(), R.animator.dev_flip_card_in);
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 当前是否显示正面 Layout
     * @return {@code true} yes, {@code false} no
     */
    public boolean isFront() {
        return mFront;
    }

    /**
     * 获取当前显示的索引
     * @return 当前显示的索引
     */
    public int getCurrentPosition() {
        if (mAdapter != null) {
            int index = mPosition - 2;
            int size  = mAdapter.getItemCount();
            if (index >= 0) return index;
            index = index + size;
            return Math.max(index, 0);
        }
        return 0;
    }

    /**
     * 获取数据源适配器
     * @return {@link Adapter}
     */
    public Adapter getAdapter() {
        return mAdapter;
    }

    /**
     * 设置数据源适配器
     * @param adapter {@link Adapter}
     * @return {@link FlipCardView}
     */
    public FlipCardView setAdapter(final Adapter adapter) {
        // 取消动画
        mOutAnim.cancel();
        mInAnim.cancel();
        // 默认两面都显示
        ViewUtils.setVisibilitys(true, mFrontLayout, mBackLayout);
        // 重置处理
        mFront    = true;
        mPosition = 0;
        // 设置适配器
        this.mAdapter = adapter;
        // 开始加载 View ( 连续加载两次 正面、背面 )
        loadView(true).loadView(false);
        return this;
    }

    /**
     * Adapter 数据源变更时调用
     */
    public void notifyDataSetChanged() {
        setAdapter(mAdapter);
    }

    /**
     * 翻转操作
     * <pre>
     *     需先调用 {@link #setAdapter(Adapter)}
     *     且在定时器每隔一段时间调用该方法
     * </pre>
     */
    public void flip() {
        if (this.mAdapter == null) return;
        // 开始翻转时都显示 View
        ViewUtils.setVisibilitys(true, mFrontLayout, mBackLayout);

        if (mFront) { // 正面朝上
            mFront = false;
            mOutAnim.setTarget(mFrontLayout);
            mInAnim.setTarget(mBackLayout);
        } else { // 背面朝上
            mFront = true;
            mOutAnim.setTarget(mBackLayout);
            mInAnim.setTarget(mFrontLayout);
        }
        // 设置动画结束监听
        mInAnim.removeListener(mInnerInAnimListener);
        mInAnim.addListener(mInnerInAnimListener);
        // 启动动画
        mOutAnim.start();
        mInAnim.start();
    }

    /**
     * 设置进出动画
     * <pre>
     *     内部不判空, 传入自行控制
     *     可参考 R.animator.dev_flip_card_in、dev_flip_card_out 复制修改
     *     需要注意的是 startOffset 需要是 duration 的一半
     *     背面布局出现时间 ( 也是正面布局消失时间, 正好是总共旋转时间的一半 )
     * </pre>
     * @param inAnim  翻牌淡入动画
     * @param outAnim 翻牌淡出动画
     * @return {@link FlipCardView}
     */
    public FlipCardView setInOutAnimator(
            final Animator inAnim,
            final Animator outAnim
    ) {
        this.mInAnim  = inAnim;
        this.mOutAnim = outAnim;
        return this;
    }

    /**
     * 设置翻牌角度
     * @param distance 翻转角度
     * @return {@link FlipCardView}
     */
    public FlipCardView setFlipDistance(final int distance) {
        float scale = getResources().getDisplayMetrics().density * distance;
        mFrontLayout.setCameraDistance(scale);
        mBackLayout.setCameraDistance(scale);
        return this;
    }

    /**
     * detail: 翻牌适配器
     * @author Ttt
     */
    public interface Adapter {

        int getItemCount();

        View getItemView(
                Context context,
                int position,
                boolean isFrontView
        );
    }

    // ==========
    // = 内部方法 =
    // ==========

    // 内部进入动画监听
    private final Animator.AnimatorListener mInnerInAnimListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            postDelayed(() -> {
                // 进行翻转 View 显示状态
                ViewUtils.reverseVisibilitys(mFront, mFrontLayout, mBackLayout);
                // 加载下一索引 View
                loadView(!mFront);
            }, 100);
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };

    /**
     * 加载翻牌 View
     * @param front 是否显示正面 View
     * @return {@link FlipCardView}
     */
    private FlipCardView loadView(final boolean front) {
        if (mAdapter != null) {
            int size = mAdapter.getItemCount();
            if (size == 0) return this;
            int position = calculatePosition(size);
            if (front) {
                try {
                    mFrontLayout.removeAllViews();
                    // 初始化 View 并添加
                    View itemView = mAdapter.getItemView(getContext(), position, true);
                    if (itemView != null) {
                        mFrontLayout.addView(itemView);
                    }
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "loadView - front");
                }
            } else {
                try {
                    mBackLayout.removeAllViews();
                    // 初始化 View 并添加
                    View itemView = mAdapter.getItemView(getContext(), position, false);
                    if (itemView != null) {
                        mBackLayout.addView(itemView);
                    }
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "loadView - back");
                }
            }
        }
        return this;
    }

    /**
     * 计算待显示索引
     * @param size {@link Adapter#getItemCount()}
     * @return 计算后的索引
     */
    private int calculatePosition(final int size) {
        if (mPosition >= size) {
            mPosition = 1;
            return 0;
        } else {
            int position = mPosition;
            mPosition++;
            return position;
        }
    }
}