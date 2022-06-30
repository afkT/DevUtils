package afkt.project.ui.widget.render.shapechange;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

import afkt.project.ui.widget.render.LoadingRenderer;
import dev.kotlin.utils.size.AppSize;

public class CoolWaitLoadingRenderer
        extends LoadingRenderer {
    private final Interpolator ACCELERATE_INTERPOLATOR08 = new AccelerateInterpolator(0.8F);
    private final Interpolator ACCELERATE_INTERPOLATOR10 = new AccelerateInterpolator(1.0F);
    private final Interpolator ACCELERATE_INTERPOLATOR15 = new AccelerateInterpolator(1.5F);

    private final Interpolator DECELERATE_INTERPOLATOR03 = new DecelerateInterpolator(0.3F);
    private final Interpolator DECELERATE_INTERPOLATOR05 = new DecelerateInterpolator(0.5F);
    private final Interpolator DECELERATE_INTERPOLATOR08 = new DecelerateInterpolator(0.8F);
    private final Interpolator DECELERATE_INTERPOLATOR10 = new DecelerateInterpolator(1.0F);

    private static final Interpolator ACCELERATE_DECELERATE_INTERPOLATOR = new AccelerateDecelerateInterpolator();

    private final float DEFAULT_WIDTH        = 200.0F;
    private final float DEFAULT_HEIGHT       = 150.0F;
    private final float DEFAULT_STROKE_WIDTH = 8.0F;
    private final float WAIT_CIRCLE_RADIUS   = 50.0F;

    private static final float WAIT_TRIM_DURATION_OFFSET = 0.5F;
    private static final float END_TRIM_DURATION_OFFSET  = 1.0F;

    private final long ANIMATION_DURATION = 2222;

    private final Paint mPaint = new Paint();

    private final Path        mWaitPath              = new Path();
    private final Path        mCurrentTopWaitPath    = new Path();
    private final Path        mCurrentMiddleWaitPath = new Path();
    private final Path        mCurrentBottomWaitPath = new Path();
    private final PathMeasure mWaitPathMeasure       = new PathMeasure();

    private final RectF mCurrentBounds = new RectF();

    private float mStrokeWidth;
    private float mWaitCircleRadius;
    private float mOriginEndDistance;
    private float mOriginStartDistance;
    private float mWaitPathLength;

    private int mTopColor;
    private int mMiddleColor;
    private int mBottomColor;

    private CoolWaitLoadingRenderer(Context context) {
        super(context);
        initialize(context);
        setupPaint();
    }

    private void initialize(Context context) {
        mWidth            = AppSize.INSTANCE.dp2px(DEFAULT_WIDTH);
        mHeight           = AppSize.INSTANCE.dp2px(DEFAULT_HEIGHT);
        mStrokeWidth      = AppSize.INSTANCE.dp2px(DEFAULT_STROKE_WIDTH);
        mWaitCircleRadius = AppSize.INSTANCE.dp2px(WAIT_CIRCLE_RADIUS);

//        mTopColor = Color.parseColor("#FF1B78DD");
        mTopColor    = Color.WHITE;
        mMiddleColor = Color.parseColor("#FFF3C742");
        mBottomColor = Color.parseColor("#FF89CC59");

        mDuration = ANIMATION_DURATION;
    }

    private void setupPaint() {
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void draw(
            Canvas canvas,
            Rect bounds
    ) {
        int   saveCount = canvas.save();
        RectF arcBounds = mCurrentBounds;
        arcBounds.set(bounds);

        mPaint.setColor(mBottomColor);
        canvas.drawPath(mCurrentBottomWaitPath, mPaint);

        mPaint.setColor(mMiddleColor);
        canvas.drawPath(mCurrentMiddleWaitPath, mPaint);

        mPaint.setColor(mTopColor);
        canvas.drawPath(mCurrentTopWaitPath, mPaint);

        canvas.restoreToCount(saveCount);
    }

    private Path createWaitPath(RectF bounds) {
        Path path = new Path();
        //create circle
        path.moveTo(bounds.centerX() + mWaitCircleRadius, bounds.centerY());

        //create w
        path.cubicTo(bounds.centerX() + mWaitCircleRadius, bounds.centerY() - mWaitCircleRadius * 0.5F,
                bounds.centerX() + mWaitCircleRadius * 0.3F, bounds.centerY() - mWaitCircleRadius,
                bounds.centerX() - mWaitCircleRadius * 0.35F, bounds.centerY() + mWaitCircleRadius * 0.5F
        );
        path.quadTo(bounds.centerX() + mWaitCircleRadius, bounds.centerY() - mWaitCircleRadius,
                bounds.centerX() + mWaitCircleRadius * 0.05F, bounds.centerY() + mWaitCircleRadius * 0.5F
        );
        path.lineTo(bounds.centerX() + mWaitCircleRadius * 0.75F, bounds.centerY() - mWaitCircleRadius * 0.2F);

        path.cubicTo(bounds.centerX(), bounds.centerY() + mWaitCircleRadius * 1F,
                bounds.centerX() + mWaitCircleRadius, bounds.centerY() + mWaitCircleRadius * 0.4F,
                bounds.centerX() + mWaitCircleRadius, bounds.centerY()
        );

        //create arc
        path.arcTo(new RectF(bounds.centerX() - mWaitCircleRadius, bounds.centerY() - mWaitCircleRadius,
                bounds.centerX() + mWaitCircleRadius, bounds.centerY() + mWaitCircleRadius
        ), 0, -359);
        path.arcTo(new RectF(bounds.centerX() - mWaitCircleRadius, bounds.centerY() - mWaitCircleRadius,
                bounds.centerX() + mWaitCircleRadius, bounds.centerY() + mWaitCircleRadius
        ), 1, -359);
        path.arcTo(new RectF(bounds.centerX() - mWaitCircleRadius, bounds.centerY() - mWaitCircleRadius,
                bounds.centerX() + mWaitCircleRadius, bounds.centerY() + mWaitCircleRadius
        ), 2, -2);
        //create w
        path.cubicTo(bounds.centerX() + mWaitCircleRadius, bounds.centerY() - mWaitCircleRadius * 0.5F,
                bounds.centerX() + mWaitCircleRadius * 0.3F, bounds.centerY() - mWaitCircleRadius,
                bounds.centerX() - mWaitCircleRadius * 0.35F, bounds.centerY() + mWaitCircleRadius * 0.5F
        );
        path.quadTo(bounds.centerX() + mWaitCircleRadius, bounds.centerY() - mWaitCircleRadius,
                bounds.centerX() + mWaitCircleRadius * 0.05F, bounds.centerY() + mWaitCircleRadius * 0.5F
        );
        path.lineTo(bounds.centerX() + mWaitCircleRadius * 0.75F, bounds.centerY() - mWaitCircleRadius * 0.2F);

        path.cubicTo(bounds.centerX(), bounds.centerY() + mWaitCircleRadius * 1F,
                bounds.centerX() + mWaitCircleRadius, bounds.centerY() + mWaitCircleRadius * 0.4F,
                bounds.centerX() + mWaitCircleRadius, bounds.centerY()
        );

        return path;
    }

    @Override
    protected void computeRender(float renderProgress) {
        if (mCurrentBounds.isEmpty()) {
            return;
        }

        if (mWaitPath.isEmpty()) {
            mWaitPath.set(createWaitPath(mCurrentBounds));
            mWaitPathMeasure.setPath(mWaitPath, false);
            mWaitPathLength = mWaitPathMeasure.getLength();

            mOriginEndDistance   = mWaitPathLength * 0.255F;
            mOriginStartDistance = mWaitPathLength * 0.045F;
        }

        mCurrentTopWaitPath.reset();
        mCurrentMiddleWaitPath.reset();
        mCurrentBottomWaitPath.reset();

        //draw the first half : top
        if (renderProgress <= WAIT_TRIM_DURATION_OFFSET) {
            float topTrimProgress  = ACCELERATE_DECELERATE_INTERPOLATOR.getInterpolation(renderProgress / WAIT_TRIM_DURATION_OFFSET);
            float topEndDistance   = mOriginEndDistance + mWaitPathLength * 0.3F * topTrimProgress;
            float topStartDistance = mOriginStartDistance + mWaitPathLength * 0.48F * topTrimProgress;
            mWaitPathMeasure.getSegment(topStartDistance, topEndDistance, mCurrentTopWaitPath, true);
        }

        //draw the first half : middle
        if (renderProgress > 0.02F * WAIT_TRIM_DURATION_OFFSET && renderProgress <= WAIT_TRIM_DURATION_OFFSET * 0.75F) {
            float middleStartTrimProgress = ACCELERATE_INTERPOLATOR10.getInterpolation((renderProgress - 0.02F * WAIT_TRIM_DURATION_OFFSET) / (WAIT_TRIM_DURATION_OFFSET * 0.73F));
            float middleEndTrimProgress   = DECELERATE_INTERPOLATOR08.getInterpolation((renderProgress - 0.02F * WAIT_TRIM_DURATION_OFFSET) / (WAIT_TRIM_DURATION_OFFSET * 0.73F));

            float middleEndDistance   = mOriginStartDistance + mWaitPathLength * 0.42F * middleEndTrimProgress;
            float middleStartDistance = mOriginStartDistance + mWaitPathLength * 0.42F * middleStartTrimProgress;
            mWaitPathMeasure.getSegment(middleStartDistance, middleEndDistance, mCurrentMiddleWaitPath, true);
        }

        //draw the first half : bottom
        if (renderProgress > 0.04F * WAIT_TRIM_DURATION_OFFSET && renderProgress <= WAIT_TRIM_DURATION_OFFSET * 0.75F) {
            float bottomStartTrimProgress = ACCELERATE_INTERPOLATOR15.getInterpolation((renderProgress - 0.04F * WAIT_TRIM_DURATION_OFFSET) / (WAIT_TRIM_DURATION_OFFSET * 0.71F));
            float bottomEndTrimProgress   = DECELERATE_INTERPOLATOR05.getInterpolation((renderProgress - 0.04F * WAIT_TRIM_DURATION_OFFSET) / (WAIT_TRIM_DURATION_OFFSET * 0.71F));

            float bottomEndDistance   = mOriginStartDistance + mWaitPathLength * 0.42F * bottomEndTrimProgress;
            float bottomStartDistance = mOriginStartDistance + mWaitPathLength * 0.42F * bottomStartTrimProgress;
            mWaitPathMeasure.getSegment(bottomStartDistance, bottomEndDistance, mCurrentBottomWaitPath, true);
        }

        //draw the last half : top
        if (renderProgress <= END_TRIM_DURATION_OFFSET && renderProgress > WAIT_TRIM_DURATION_OFFSET) {
            float trimProgress     = ACCELERATE_DECELERATE_INTERPOLATOR.getInterpolation((renderProgress - WAIT_TRIM_DURATION_OFFSET) / (END_TRIM_DURATION_OFFSET - WAIT_TRIM_DURATION_OFFSET));
            float topEndDistance   = mOriginEndDistance + mWaitPathLength * 0.3F + mWaitPathLength * 0.45F * trimProgress;
            float topStartDistance = mOriginStartDistance + mWaitPathLength * 0.48F + mWaitPathLength * 0.27F * trimProgress;
            mWaitPathMeasure.getSegment(topStartDistance, topEndDistance, mCurrentTopWaitPath, true);
        }

        //draw the last half : middle
        if (renderProgress > WAIT_TRIM_DURATION_OFFSET + 0.02F * WAIT_TRIM_DURATION_OFFSET && renderProgress <= WAIT_TRIM_DURATION_OFFSET + WAIT_TRIM_DURATION_OFFSET * 0.62F) {
            float middleStartTrimProgress = ACCELERATE_INTERPOLATOR08.getInterpolation((renderProgress - WAIT_TRIM_DURATION_OFFSET - 0.02F * WAIT_TRIM_DURATION_OFFSET) / (WAIT_TRIM_DURATION_OFFSET * 0.60F));
            float middleEndTrimProgress   = DECELERATE_INTERPOLATOR03.getInterpolation((renderProgress - WAIT_TRIM_DURATION_OFFSET - 0.02F * WAIT_TRIM_DURATION_OFFSET) / (WAIT_TRIM_DURATION_OFFSET * 0.60F));

            float middleEndDistance   = mOriginStartDistance + mWaitPathLength * 0.48F + mWaitPathLength * 0.20F * middleEndTrimProgress;
            float middleStartDistance = mOriginStartDistance + mWaitPathLength * 0.48F + mWaitPathLength * 0.10F * middleStartTrimProgress;
            mWaitPathMeasure.getSegment(middleStartDistance, middleEndDistance, mCurrentMiddleWaitPath, true);
        }

        if (renderProgress > WAIT_TRIM_DURATION_OFFSET + 0.62F * WAIT_TRIM_DURATION_OFFSET && renderProgress <= END_TRIM_DURATION_OFFSET) {
            float middleStartTrimProgress = DECELERATE_INTERPOLATOR10.getInterpolation((renderProgress - WAIT_TRIM_DURATION_OFFSET - 0.62F * WAIT_TRIM_DURATION_OFFSET) / (WAIT_TRIM_DURATION_OFFSET * 0.38F));
            float middleEndTrimProgress   = DECELERATE_INTERPOLATOR03.getInterpolation((renderProgress - WAIT_TRIM_DURATION_OFFSET - 0.62F * WAIT_TRIM_DURATION_OFFSET) / (WAIT_TRIM_DURATION_OFFSET * 0.38F));

            float middleEndDistance   = mOriginStartDistance + mWaitPathLength * 0.68F + mWaitPathLength * 0.325F * middleEndTrimProgress;
            float middleStartDistance = mOriginStartDistance + mWaitPathLength * 0.58F + mWaitPathLength * 0.17F * middleStartTrimProgress;
            mWaitPathMeasure.getSegment(middleStartDistance, middleEndDistance, mCurrentMiddleWaitPath, true);
        }

        //draw the last half : bottom
        if (renderProgress > WAIT_TRIM_DURATION_OFFSET + 0.10F * WAIT_TRIM_DURATION_OFFSET && renderProgress <= WAIT_TRIM_DURATION_OFFSET + WAIT_TRIM_DURATION_OFFSET * 0.70F) {
            float bottomStartTrimProgress = ACCELERATE_INTERPOLATOR15.getInterpolation((renderProgress - WAIT_TRIM_DURATION_OFFSET - 0.10F * WAIT_TRIM_DURATION_OFFSET) / (WAIT_TRIM_DURATION_OFFSET * 0.60F));
            float bottomEndTrimProgress   = DECELERATE_INTERPOLATOR03.getInterpolation((renderProgress - WAIT_TRIM_DURATION_OFFSET - 0.10F * WAIT_TRIM_DURATION_OFFSET) / (WAIT_TRIM_DURATION_OFFSET * 0.60F));

            float bottomEndDistance   = mOriginStartDistance + mWaitPathLength * 0.48F + mWaitPathLength * 0.20F * bottomEndTrimProgress;
            float bottomStartDistance = mOriginStartDistance + mWaitPathLength * 0.48F + mWaitPathLength * 0.10F * bottomStartTrimProgress;
            mWaitPathMeasure.getSegment(bottomStartDistance, bottomEndDistance, mCurrentBottomWaitPath, true);
        }

        if (renderProgress > WAIT_TRIM_DURATION_OFFSET + 0.70F * WAIT_TRIM_DURATION_OFFSET && renderProgress <= END_TRIM_DURATION_OFFSET) {
            float bottomStartTrimProgress = DECELERATE_INTERPOLATOR05.getInterpolation((renderProgress - WAIT_TRIM_DURATION_OFFSET - 0.70F * WAIT_TRIM_DURATION_OFFSET) / (WAIT_TRIM_DURATION_OFFSET * 0.30F));
            float bottomEndTrimProgress   = DECELERATE_INTERPOLATOR03.getInterpolation((renderProgress - WAIT_TRIM_DURATION_OFFSET - 0.70F * WAIT_TRIM_DURATION_OFFSET) / (WAIT_TRIM_DURATION_OFFSET * 0.30F));

            float bottomEndDistance   = mOriginStartDistance + mWaitPathLength * 0.68F + mWaitPathLength * 0.325F * bottomEndTrimProgress;
            float bottomStartDistance = mOriginStartDistance + mWaitPathLength * 0.58F + mWaitPathLength * 0.17F * bottomStartTrimProgress;
            mWaitPathMeasure.getSegment(bottomStartDistance, bottomEndDistance, mCurrentBottomWaitPath, true);
        }

    }

    @Override
    protected void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);

    }

    @Override
    protected void setColorFilter(ColorFilter cf) {
        mPaint.setColorFilter(cf);

    }

    @Override
    protected void reset() {
    }

    public static class Builder {
        private final Context mContext;

        public Builder(Context context) {
            this.mContext = context;
        }

        public CoolWaitLoadingRenderer build() {
            return new CoolWaitLoadingRenderer(mContext);
        }
    }
}