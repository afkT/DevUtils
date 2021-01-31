package afkt.project.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.core.widget.NestedScrollView;

public class VerticalScrollView
        extends NestedScrollView {

    private int     mScrollY;
    private int     mLastMotionY;
    private boolean mClampedY;

    public VerticalScrollView(
            Context context,
            AttributeSet attrs
    ) {
        super(context, attrs);
    }

    public VerticalScrollView(
            Context context,
            AttributeSet attrs,
            int defStyleAttr
    ) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onOverScrolled(
            int scrollX,
            int scrollY,
            boolean clampedX,
            boolean clampedY
    ) {
        mScrollY = scrollY;
        mClampedY = clampedY;
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_MOVE: {
                final int y     = (int) ev.getY();
                final int yDiff = y - mLastMotionY;
                // 8 为阈值, 可自行定义 ( 这里为方便用的魔法值 )
                if (yDiff > 8) { // 向下滑动
                    mLastMotionY = y;
                    if (mScrollY == 0 && mClampedY) {
                        // ScrollView 已经滑到顶部了, 再向下滑动, 那就不处理了, 让父控件决定是否拦截事件
                        getParent().requestDisallowInterceptTouchEvent(false);
                    } else {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                } else if (yDiff < -8) { // 向上滑动
                    mLastMotionY = y;
                    if (mScrollY > 0 && mClampedY) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                    } else {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                }
                break;
            }
            case MotionEvent.ACTION_DOWN: {
                // 先告知父控件不要拦截事件, 我先处理
                getParent().requestDisallowInterceptTouchEvent(true);
                mLastMotionY = (int) ev.getY();
                break;
            }
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                // 还原
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}