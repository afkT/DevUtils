package afkt.project.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

/**
 * detail: 滑动 ImageView ( 未调整优化 )
 * @author Ttt
 * https://blog.csdn.net/qq_18833399/article/details/60958658
 * https://blog.csdn.net/csdnwangzhan/article/details/51597142
 * https://blog.csdn.net/nugongahou110/article/details/50668925
 * https://blog.csdn.net/qq_30716173/article/details/51460627
 * https://yq.aliyun.com/ziliao/131530
 * https://blog.csdn.net/zjutkz/article/details/46553017
 * https://blog.csdn.net/u010335298/article/details/52278609
 * https://github.com/LuckyJayce/LargeImage
 */
public class SlideImageView
        extends AppCompatImageView {

    // 判断是否允许滑动 => 如果可以滑动 isNeedSlide = true, 该参数限制才有效
    private boolean isAllowSlide = true;
    // 是否需要滑动
    private boolean isNeedSlide;
    // View 宽度, 高度
    private int     viewWidth, viewHeight;
    // 需要绘制的图片的区域
    private final Rect   srcRect  = new Rect();
    // 绘制的区域
    private final RectF  dstRectF = new RectF();
    // 画笔
    private final Paint  paint    = new Paint();
    // 已经滑动过的高度
    private       float  slideHeight;
    // 绘制的 Bitmap
    private       Bitmap drawBitmap;
    // 设置计算倍数
    private       float  scale    = 1.0f;

    {
        // 初始化画笔
        paint.setAntiAlias(true); // 抗锯齿
        paint.setDither(true); // 防抖动
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(1.0f);
    }

    public SlideImageView(Context context) {
        super(context);
    }

    public SlideImageView(
            Context context,
            @Nullable AttributeSet attrs
    ) {
        super(context, attrs);
    }

    public SlideImageView(
            Context context,
            @Nullable AttributeSet attrs,
            int defStyleAttr
    ) {
        super(context, attrs, defStyleAttr);
    }

    // = 优化处理, 防止多次执行 onMeasure 计算处理 =
    // 预计宽度, 小于预计的宽度才处理
    private int estimateWidth = -1;
    // 计算次数, 计算多次后, 就不处理
    private int measureCount  = -1;

    @Override
    protected void onMeasure(
            int widthMeasureSpec,
            int heightMeasureSpec
    ) {
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        viewWidth = getPaddingLeft() + getPaddingRight() + specSize;
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        viewHeight = getPaddingTop() + getPaddingBottom() + specSize;
        // 防止多次调用 onMeasure, 内部缩放 resizeImage 导致卡顿
        if (estimateWidth == -1 || viewWidth <= estimateWidth) {
            // 计算总数大于 1 表示允许计算
            if (measureCount != 0) {
                // 获取设置的 View
                Drawable drawable = getDrawable();
                // 防止为 null
                if (drawable != null) {
                    try {
                        // 根据图片宽度为基准, 缩放图片大小
                        drawBitmap = resizeImage(((BitmapDrawable) drawable).getBitmap(), viewWidth);
                        measureCount--;
                    } catch (Exception e) {
                        drawBitmap = null;
                    }
                    // 防止为 null
                    if (drawBitmap != null) {
                        // 判断图片高度, 是否超过 View 的高度 => scale 倍
                        if (drawBitmap.getHeight() > scale * viewHeight) {
                            // 需要滑动
                            setNeedSlide(true);
                        } else {
                            // 不需要滑动
                            setNeedSlide(false);
                            srcRect.left = 0;
                            srcRect.top = 0;
                            srcRect.right = drawBitmap.getWidth();
                            srcRect.bottom = drawBitmap.getHeight();
                            // 如果高度大于 View 的高度, 则以高度为基准 ( 铺满高度, 宽度跟随比例缩放 )
                            if (drawBitmap.getHeight() > viewHeight) {
                                // 生成新的图片 ( 高度铺满, 宽度按比例缩放 )
                                drawBitmap = resizeImageH(drawBitmap, viewHeight);
                            } else { // 图片高度 小于 View 高度, 则计算多出来的差距
                                float space = (viewHeight - drawBitmap.getHeight());
                                dstRectF.left = 0;
                                dstRectF.top = space;
                                dstRectF.right = viewWidth;
                                dstRectF.bottom = viewHeight - space;
                            }
                        }
                    }
                }
            }
        }
        setMeasuredDimension(viewWidth, viewHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (drawBitmap != null) {
            // 绘制 View
            canvas.drawBitmap(drawBitmap, (viewWidth - drawBitmap.getWidth()) / 2, slideHeight, paint);
        }
    }

    // 触摸操作的坐标
    private float lastX, lastY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 如果不符合要求, 也不做处理
        if (!isNeedSlide) {
            return super.onTouchEvent(event);
        }
        // 如果不允许滑动, 则不处理
        if (!isAllowSlide) {
            return super.onTouchEvent(event);
        }
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN: // 按下
                lastX = event.getX();
                lastY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE: // 移动
                float moveX = event.getX();
                if (moveX - lastX > 50) {
                    // 判断为左右滑动
                    return super.onTouchEvent(event);
                }
                // 获取移动上下值
                float moveY = event.getY();
                // 计算滑动距离
                float distance = moveY - lastY;
                // 设置最后滑动的值
                lastY = moveY;
                // 滑动的位置累加
                slideHeight += distance;
                // 如果属于到顶了, 还一直向上滑, 则设置为 0
                if (slideHeight >= 0) {
                    slideHeight = 0;
                }
                // 计算滑动的距离 ( 需要取反, 因为向下滑为负数 )
                float calcDistance = (-1) * (drawBitmap.getHeight() - viewHeight);
                // 向下滑为负数
                if (slideHeight <= calcDistance) {
                    slideHeight = calcDistance;
                }
                // 重新绘制
                postInvalidate();
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 重新计算大小, 以宽度为基准
     * @param bitmap
     * @param w
     * @return
     */
    public Bitmap resizeImage(
            Bitmap bitmap,
            int w
    ) {
        int    width      = bitmap.getWidth();
        int    height     = bitmap.getHeight();
        float  scaleWidth = ((float) w) / width;
        Matrix matrix     = new Matrix();
        matrix.postScale(scaleWidth, scaleWidth);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

    /**
     * 重新计算大小, 以高度为基准
     * @param bitmap
     * @param h
     * @return
     */
    public Bitmap resizeImageH(
            Bitmap bitmap,
            int h
    ) {
        int    width      = bitmap.getWidth();
        int    height     = bitmap.getHeight();
        float  scaleWidth = ((float) h) / height;
        Matrix matrix     = new Matrix();
        matrix.postScale(scaleWidth, scaleWidth);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

    /**
     * 获取设置的预计宽度
     * @return
     */
    public int getEstimateWidth() {
        return estimateWidth;
    }

    /**
     * 设置预计高度
     * @param estimateWidth
     */
    public SlideImageView setEstimateWidth(int estimateWidth) {
        this.estimateWidth = estimateWidth;
        return this;
    }

    /**
     * 获取计算次数
     * @return
     */
    public int getMeasureCount() {
        return measureCount;
    }

    /**
     * 设置计算次数
     * @param measureCount
     */
    public SlideImageView setMeasureCount(int measureCount) {
        this.measureCount = measureCount;
        return this;
    }

    // =

    /**
     * 是否需要滑动 -> 图片是否符合要求能够滑动
     * @return
     */
    public boolean isNeedSlide() {
        return isNeedSlide;
    }

    /**
     * 设置是否需要滑动
     * @param needSlide
     */
    public SlideImageView setNeedSlide(boolean needSlide) {
        isNeedSlide = needSlide;
        return this;
    }

    /**
     * 是否允许滑动
     * @return
     */
    public boolean isAllowSlide() {
        return isAllowSlide;
    }

    /**
     * 设置是否允许滑动
     * @param allowSlide
     */
    public SlideImageView setAllowSlide(boolean allowSlide) {
        isAllowSlide = allowSlide;
        return this;
    }

    /**
     * 获取比例
     * @return
     */
    public float getScale() {
        return scale;
    }

    /**
     * 设置计算比例
     * @param scale
     */
    public SlideImageView setScale(float scale) {
        this.scale = scale;
        return this;
    }

    // = 动画相关 =

    private final Handler handler        = new Handler();
    // 动画滑动距离
    private       float   slideLenth     = 10f;
    // 滑动速度 -> 时间
    private       long    slideSpeed     = 100L;
    // 检测时间
    private       long    checkTime      = 20L;
    // 是否滑动到底部
    private       boolean isScrollBottom = true;
    // 是否开启动画
    private       boolean isStartAnim    = false;
    // 是否关闭动画
    private       boolean isStopAnim     = false;

    // 动画线程
    private final Runnable animRunnable = new Runnable() {
        @Override
        public void run() {
            // 如果关闭了动画, 则不处理
            if (isStopAnim) {
                return;
            }
            // 防止 图片 为 null
            if (drawBitmap == null) {
                // 进行延时处理
                handler.postDelayed(this, checkTime);
                return;
            }
            // 计算滑动比例
            calcSlideScale();
            // 向下滑动
            if (isScrollBottom) {
                slideHeight -= slideLenth;
            } else { // 向上滑动
                slideHeight += slideLenth;
            }
            // 判断是否滑动到顶部
            if (slideHeight >= 0) {
                slideHeight = 0;
                // 表示准备滑动到底部
                isScrollBottom = true;
            }
            // 计算滑动的距离
            float calcDistance = (-1) * (drawBitmap.getHeight() - viewHeight);
            // 向下滑为负数
            if (slideHeight <= calcDistance) {
                slideHeight = calcDistance;
                // 表示向上滑动
                isScrollBottom = false;
            }
            if (isStopAnim) {
                return;
            }
            // 重新绘制
            postInvalidate();
            // 进行延时处理
            handler.postDelayed(this, slideSpeed);
        }
    };

    /**
     * 启动动画
     */
    public void startAnim() {
        // 如果没启动则允许启动
        if (!isStartAnim) {
            // 表示开启了动画
            isStartAnim = true;
            // 表示没有关闭动画
            isStopAnim = false;
            // 开启动画
            new Thread(animRunnable).start();
        }
    }

    /**
     * 关闭动画
     */
    public void stopAnim() {
        // 表示关闭动画
        isStopAnim = true;
        // 表示非启动动画
        isStartAnim = false;
    }

    // =

    /**
     * 是否启动了动画
     * @return
     */
    public boolean isStartAnim() {
        return isStartAnim;
    }

    /**
     * 获取滑动长度
     * @return
     */
    public float getSlideLenth() {
        return slideLenth;
    }

    /**
     * 设置滑动长度
     * @param slideLenth
     */
    public SlideImageView setSlideLenth(float slideLenth) {
        this.slideLenth = slideLenth;
        return this;
    }

    /**
     * 获取滑动速度 - 时间
     * @return
     */
    public long getSlideSpeed() {
        return slideSpeed;
    }

    /**
     * 设置滑动速度 - 时间
     * @param slideSpeed
     */
    public SlideImageView setSlideSpeed(long slideSpeed) {
        this.slideSpeed = slideSpeed;
        return this;
    }

    /**
     * 获取检测时间
     * @return
     */
    public long getCheckTime() {
        return checkTime;
    }

    /**
     * 设置检测时间
     * @param checkTime
     */
    public void setCheckTime(long checkTime) {
        this.checkTime = checkTime;
    }

    // =

    /**
     * 是否向下滑动
     * @return
     */
    public boolean isScrollBottom() {
        return isScrollBottom;
    }

    /**
     * 设置滑动方向
     * @param scrollBottom 是否向下滑动
     */
    public SlideImageView setScrollDirection(boolean scrollBottom) {
        isScrollBottom = scrollBottom;
        return this;
    }

    /**
     * 获取当前滑动的高度
     * @return
     */
    public float getSlideHeight() {
        return slideHeight;
    }

    /**
     * 设置当前滑动的高度
     * @param slideHeight
     */
    public SlideImageView setSlideHeight(float slideHeight) {
        this.slideHeight = slideHeight;
        return this;
    }

    // 设置滑动比例
    private float slideCalcScale = -1f;

    /**
     * 设置滑动比例
     * @param scale
     */
    public SlideImageView setSlideHeightScale(float scale) {
        slideCalcScale = scale;
        return this;
    }

    /**
     * 计算滑动比例
     */
    private void calcSlideScale() {
        if (slideCalcScale != -1f) {
            // 临时处理
            float tScalc = slideCalcScale;
            // 清空比例
            slideCalcScale = -1f;
            // 超过限制, 则默认为 1 倍
            if (tScalc > 1.0f) {
                tScalc = 1.0f;
            } else if (tScalc < 0f) {
                tScalc = 0f; // 默认为 0 倍
            }
            // 计算滑动的距离
            float calcDistance = (-1) * (drawBitmap.getHeight() - viewHeight) * tScalc;
            // 设置新的滑动距离
            slideHeight = calcDistance;
//            // 判断是否滑动到顶部
//            if (slideHeight >= 0) {
//                slideHeight = 0;
////                // 表示准备滑动到底部
////                isScrollBottom = true;
//            }
//            // 向下滑为负数
//            if (slideHeight <= calcDistance) {
//                slideHeight = calcDistance;
////                // 表示向上滑动
////                isScrollBottom = false;
//            }
        }
    }
}