package dev.widget.adjust;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * detail: 自动调节高度 RecyclerView
 * @author Ttt
 */
public class AdjustHeightRecyclerView extends RecyclerView {

    public AdjustHeightRecyclerView(Context context) {
        super(context);
    }

    public AdjustHeightRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AdjustHeightRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}