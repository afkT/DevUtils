package dev.widget.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * detail: 翻转卡片 View
 * @author Ttt
 */
public class SpinCardView
        extends FrameLayout {

    // 正面 Layout
    private FrameLayout mFrontLayout;
    // 背面 Layout
    private FrameLayout mBackLayout;
    // 当前是否显示正面 Layout
    private boolean     isFront = true;

    public SpinCardView(Context context) {
        super(context);
        init();
    }

    public SpinCardView(
            Context context,
            AttributeSet attrs
    ) {
        super(context, attrs);
        init();
    }

    public SpinCardView(
            Context context,
            AttributeSet attrs,
            int defStyleAttr
    ) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SpinCardView(
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

//        int   distance = 16000;
//        float scale    = getResources().getDisplayMetrics().density * distance;
//        mFrontLayout.setCameraDistance(scale);
//        mBackLayout.setCameraDistance(scale);
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
}