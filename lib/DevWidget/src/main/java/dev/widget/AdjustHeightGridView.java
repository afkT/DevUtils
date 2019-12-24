package dev.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

import androidx.annotation.Nullable;

/**
 * detail: 自动调节高度 GridView
 * @author Ttt
 */
public class AdjustHeightGridView extends GridView {

    public AdjustHeightGridView(Context context) {
        super(context);
    }

    public AdjustHeightGridView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AdjustHeightGridView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}