package dev.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * detail: 签名画笔 View
 * @author Ttt
 */
public class SignView extends View {

    // 绘制路径
    private Path mPath;
    // 画笔
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    // 绘制路径 X、Y
    private float mX, mY;

    public SignView(Context context) {
        this(context, null);
    }

    public SignView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SignView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // 初始化画笔
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        // 画笔不为 null, 才进行绘制
        if (mPaint != null) canvas.drawPath(mPath, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mX = event.getX();
                mY = event.getY();
                mPath.moveTo(mX, mY);
                break;
            case MotionEvent.ACTION_MOVE:
                float x1 = event.getX();
                float y1 = event.getY();
                float cx = (x1 + mX) / 2;
                float cy = (y1 + mY) / 2;
                mPath.quadTo(mX, mY, cx, cy);
                mX = x1;
                mY = y1;
                break;
        }
        invalidate();
        return true;
    }

    /**
     * 设置画笔
     * @param paint {@link Paint}
     * @return {@link SignView}
     */
    public SignView setPaint(Paint paint) {
        this.mPaint = paint;
        return this;
    }
}