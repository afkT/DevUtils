package dev.widget.function;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import dev.utils.app.CapturePictureUtils;

/**
 * detail: 自定义签名 View
 * @author Ttt
 * <pre>
 *     如果需要实现不同颜色绘制不同路径, 则在 onTouchEvent {@link MotionEvent#ACTION_UP} 抬起时
 *     存储每次的 Path、Paint 为一个 Item Bean, 并且在 onDraw 循环 List<Item> 进行 canvas.drawPath(item.path, item.paint)
 *     则能够实现类似画图功能 ( SVG 绘制也是该思路 )
 * </pre>
 */
public class SignView
        extends View {

    // 绘制路径
    private Path  mPath;
    // 绘制画笔
    private Paint mPaint;
    // 绘制路径 X、Y ( 临时变量 )
    private float mX, mY;
    // 是否清空画布
    private boolean        mIsClearCanvas = false;
    // 绘制回调事件
    private OnDrawCallback mCallback;

    public SignView(Context context) {
        super(context);
    }

    public SignView(
            Context context,
            AttributeSet attrs
    ) {
        super(context, attrs);
    }

    public SignView(
            Context context,
            AttributeSet attrs,
            int defStyleAttr
    ) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SignView(
            Context context,
            AttributeSet attrs,
            int defStyleAttr,
            int defStyleRes
    ) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Path  path  = getPath();
        Paint paint = getPaint();
        // 触发回调
        if (mCallback != null) {
            if (mCallback.onDraw(canvas, path, paint, mIsClearCanvas)) {
                // 绘制路径
                canvas.drawPath(path, paint);
            }
            mIsClearCanvas = false;
        } else {
            // 绘制路径
            canvas.drawPath(path, paint);
            // 重置画布操作
            _resetCanvas(canvas);
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
     * 重置画布处理
     * @param canvas 画布
     */
    private void _resetCanvas(Canvas canvas) {
        if (mIsClearCanvas && canvas != null) {
            canvas.drawColor(Color.WHITE, PorterDuff.Mode.CLEAR);
        }
        mIsClearCanvas = false;
    }

    // =

    /**
     * 清空画布
     * @return {@link SignView}
     */
    public SignView clearCanvas() {
        mX = mY = 0;
        mPath = null;
        mIsClearCanvas = true;
        postInvalidate();
        return this;
    }

    /**
     * 通过 View 绘制为 Bitmap
     * <pre>
     *     // 可以自己设置背景色
     *     CapturePictureUtils.setBackgroundColor(Color.WHITE);
     * </pre>
     * @return {@link Bitmap}
     */
    public Bitmap snapshotByView() {
        return CapturePictureUtils.snapshotByView(this);
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
     * @param callback 绘制回调事件
     * @return {@link SignView}
     */
    public SignView setDrawCallback(OnDrawCallback callback) {
        this.mCallback = callback;
        return this;
    }

    /**
     * detail: 绘制回调事件
     * @author Ttt
     */
    public static abstract class OnDrawCallback {

        /**
         * 绘制方法
         * @param canvas      画布
         * @param path        绘制路径
         * @param paint       绘制画笔
         * @param clearCanvas 是否清空画布
         * @return 是否接着进行绘制 drawPath
         */
        public boolean onDraw(
                Canvas canvas,
                Path path,
                Paint paint,
                boolean clearCanvas
        ) {
            if (clearCanvas && canvas != null) {
                canvas.drawColor(Color.WHITE, PorterDuff.Mode.CLEAR);
            }
            return true;
        }
    }
}