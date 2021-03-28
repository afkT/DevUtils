package dev.widget.ui;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import dev.utils.app.ViewUtils;
import dev.widget.R;

/**
 * detail: 翻转卡片 View
 * @author Ttt
 */
public class FlipCardView
        extends FrameLayout {

    // 正面 Layout
    private FrameLayout mFrontLayout;
    // 背面 Layout
    private FrameLayout mBackLayout;
    // 当前是否显示正面 Layout
    private boolean     isFront   = true;
    // 翻转出入动画
    private Animator    mOutAnim;
    private Animator    mInAnim;
    // 数据源适配器
    private Adapter     mAdapter;
    // 当前显示索引
    private int         mPosition = 0;

    public FlipCardView(Context context) {
        super(context);
        init();
    }

    public FlipCardView(
            Context context,
            AttributeSet attrs
    ) {
        super(context, attrs);
        init();
    }

    public FlipCardView(
            Context context,
            AttributeSet attrs,
            int defStyleAttr
    ) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FlipCardView(
            Context context,
            AttributeSet attrs,
            int defStyleAttr,
            int defStyleRes
    ) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
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

        int   distance = 16000;
        float scale    = getResources().getDisplayMetrics().density * distance;
        mFrontLayout.setCameraDistance(scale);
        mBackLayout.setCameraDistance(scale);

        // 初始化动画
        mOutAnim = AnimatorInflater.loadAnimator(getContext(), R.animator.card_flip_anim_out);
        mInAnim = AnimatorInflater.loadAnimator(getContext(), R.animator.card_flip_anim_in);
        mInAnim.addListener(mInAnimListener);
    }

    // ===============
    // = 对外公开方法 =
    // ===============

    /**
     * 当前是否显示正面 Layout
     * @return {@code true} yes, {@code false} no
     */
    public boolean isFront() {
        return isFront;
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
    public FlipCardView setAdapter(Adapter adapter) {
        // 取消动画
        mOutAnim.cancel();
        mInAnim.cancel();
        // 重置处理
        isFront = true;
        mPosition = 0;
        // 设置适配器
        this.mAdapter = adapter;
        // 开始加载 View ( 连续加载两次 正面、背面 )
        loadView(true).loadView(false);
        return this;
    }

    /**
     * 翻转操作
     * <pre>
     *     需先调用 {@link #setAdapter(Adapter)}
     *     且在定时器每隔一段时间调用该方法
     * </pre>
     */
    public void flip() {
        // 开始翻转时都显示 View
        ViewUtils.setVisibilitys(true, mFrontLayout, mBackLayout);

        if (isFront) { // 正面朝上
            isFront = false;
            mOutAnim.setTarget(mFrontLayout);
            mInAnim.setTarget(mBackLayout);
        } else { // 背面朝上
            isFront = true;
            mOutAnim.setTarget(mBackLayout);
            mInAnim.setTarget(mFrontLayout);
        }
        mOutAnim.start();
        mInAnim.start();
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

    // ===========
    // = 内部方法 =
    // ===========

    // 进入动画监听
    private Animator.AnimatorListener mInAnimListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            postDelayed(() -> {
                // 进行翻转 View 显示状态
                ViewUtils.reverseVisibilitys(isFront, mFrontLayout, mBackLayout);
                // 加载下一索引 View
                loadView(!isFront);
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
    private FlipCardView loadView(boolean front) {
        if (front) {
            mFrontLayout.removeAllViews();
            // 初始化 View 并添加
            View itemView = mAdapter.getItemView(getContext(), calculatePosition(), true);
            mFrontLayout.addView(itemView);
        } else {
            mBackLayout.removeAllViews();
            // 初始化 View 并添加
            View itemView = mAdapter.getItemView(getContext(), calculatePosition(), false);
            mBackLayout.addView(itemView);
        }
        return this;
    }

    /**
     * 计算待显示索引
     * @return 计算后的索引
     */
    private int calculatePosition() {
        int size = mAdapter.getItemCount();
        mPosition = (mPosition >= size) ? 0 : (mPosition + 1);
        return (mPosition == 0) ? 0 : (mPosition - 1);
    }
}