package dev.widget.function;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * detail: 自定义签名 View
 * @author Ttt
 * <pre>
 *     如果需要实现不同颜色绘制不同路径, 则在 onTouchEvent {@link MotionEvent#ACTION_UP} 抬起时
 *     存储每次的 Path、Paint 为一个 Item, 并且在 onDraw 循环 List<Item> 进行 canvas.drawPath(item.path, item.paint)
 *     则能够实现类似画图 View 一样 ( SVG 绘制也是该思路 )
 * </pre>
 */
public class SignView extends View {

    // 绘制路径
    private Path mPath;
    // 绘制画笔
    private Paint mPaint;
    // 绘制路径 X、Y ( 临时变量 )
    private float mX, mY;
    // 绘制回调事件
    private OnDrawCallBack mDrawCallBack;

    public SignView(Context context) {
        super(context);
    }

    public SignView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SignView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Path path = getPath();
        Paint paint = getPaint();
        // 触发回调
        if (mDrawCallBack != null) {
            if (mDrawCallBack.onDraw(canvas, path, paint)) {
                // 绘制路径
                canvas.drawPath(path, paint);
            }
        } else {
            // 绘制路径
            canvas.drawPath(path, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mX = event.getX();
                mY = event.getY();
                getPath().moveTo(mX, mY);
                break;
            case MotionEvent.ACTION_MOVE:
                float x1 = event.getX();
                float y1 = event.getY();
                float cx = (x1 + mX) / 2;
                float cy = (y1 + mY) / 2;
                getPath().quadTo(mX, mY, cx, cy);
                mX = x1;
                mY = y1;
                break;
        }
        invalidate();
        return true;
    }

    /**
     * 获取绘制路径
     * @return {@link Path}
     */
    public Path getPath() {
        if (mPath == null) mPath = new Path();
        return mPath;
    }

    /**
     * 设置绘制路径
     * @param path {@link Path}
     * @return {@link SignView}
     */
    public SignView setPath(Path path) {
        this.mPath = path;
        return this;
    }

    /**
     * 获取绘制画笔
     * @return {@link Paint}
     */
    public Paint getPaint() {
        if (mPaint == null) {
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(10);
        }
        return mPaint;
    }

    /**
     * 设置绘制画笔
     * @param paint {@link Paint}
     * @return {@link SignView}
     */
    public SignView setPaint(Paint paint) {
        this.mPaint = paint;
        return this;
    }

    /**
     * 设置绘制回调事件
     * @param drawCallBack 绘制回调事件
     * @return {@link SignView}
     */
    public SignView setDrawCallBack(OnDrawCallBack drawCallBack) {
        this.mDrawCallBack = drawCallBack;
        return this;
    }

    /**
     * detail: 绘制回调事件
     * @author Ttt
     */
    public abstract class OnDrawCallBack {

        /**
         * 绘制方法
         * @param canvas 画布
         * @param path   绘制路径
         * @param paint  绘制画笔
         * @return 是否接着进行绘制 drawPath
         */
        public boolean onDraw(Canvas canvas, Path path, Paint paint) {
            return true;
        }
    }
}