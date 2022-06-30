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
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

import afkt.project.ui.widget.render.LoadingRenderer;
import dev.kotlin.utils.size.AppSize;

public class CircleBroodLoadingRenderer
        extends LoadingRenderer {

    private final Interpolator MOTHER_MOVE_INTERPOLATOR = new MotherMoveInterpolator();
    private final Interpolator CHILD_MOVE_INTERPOLATOR  = new ChildMoveInterpolator();

    private final Interpolator ACCELERATE_INTERPOLATOR03 = new AccelerateInterpolator(0.3F);
    private final Interpolator ACCELERATE_INTERPOLATOR05 = new AccelerateInterpolator(0.5F);
    private final Interpolator ACCELERATE_INTERPOLATOR08 = new AccelerateInterpolator(0.8F);
    private final Interpolator ACCELERATE_INTERPOLATOR10 = new AccelerateInterpolator(1.0F);

    private final Interpolator DECELERATE_INTERPOLATOR03 = new DecelerateInterpolator(0.3F);
    private final Interpolator DECELERATE_INTERPOLATOR05 = new DecelerateInterpolator(0.5F);
    private final Interpolator DECELERATE_INTERPOLATOR08 = new DecelerateInterpolator(0.8F);
    private final Interpolator DECELERATE_INTERPOLATOR10 = new DecelerateInterpolator(1.0F);

    private final float STAGE_MOTHER_FORWARD_TOP_LEFT     = 0.34F;
    private final float STAGE_MOTHER_BACKWARD_TOP_LEFT    = 0.5F;
    private final float STAGE_MOTHER_FORWARD_BOTTOM_LEFT  = 0.65F;
    private final float STAGE_MOTHER_BACKWARD_BOTTOM_LEFT = 0.833F;

    private final float STAGE_CHILD_DELAY                 = 0.1F;
    private final float STAGE_CHILD_PRE_FORWARD_TOP_LEFT  = 0.26F;
    private final float STAGE_CHILD_FORWARD_TOP_LEFT      = 0.34F;
    private final float STAGE_CHILD_PRE_BACKWARD_TOP_LEFT = 0.42F;
    private final float STAGE_CHILD_BACKWARD_TOP_LEFT     = 0.5F;
    private final float STAGE_CHILD_FORWARD_BOTTOM_LEFT   = 0.7F;
    private final float STAGE_CHILD_BACKWARD_BOTTOM_LEFT  = 0.9F;

    private final float OVAL_BEZIER_FACTOR = 0.55152F;

    private final float DEFAULT_WIDTH                  = 200.0F;
    private final float DEFAULT_HEIGHT                 = 150.0F;
    private final float MAX_MATHER_OVAL_SIZE           = 19;
    private final float MIN_CHILD_OVAL_RADIUS          = 5;
    private final float MAX_MATHER_SHAPE_CHANGE_FACTOR = 0.8452F;

    private final int DEFAULT_OVAL_COLOR            = Color.parseColor("#FFBE1C23");
    private final int DEFAULT_OVAL_DEEP_COLOR       = Color.parseColor("#FFB21721");
    private final int DEFAULT_BACKGROUND_COLOR      = Color.parseColor("#FFE3C172");
    private final int DEFAULT_BACKGROUND_DEEP_COLOR = Color.parseColor("#FFE2B552");

    private final long ANIMATION_DURATION = 4111;

    private final Paint mPaint          = new Paint();
    private final RectF mCurrentBounds  = new RectF();
    private final Path  mMotherOvalPath = new Path();
    private final Path  mMotherMovePath = new Path();
    private final Path  mChildMovePath  = new Path();

    private final float[]     mMotherPosition        = new float[2];
    private final float[]     mChildPosition         = new float[2];
    private final PathMeasure mMotherMovePathMeasure = new PathMeasure();
    private final PathMeasure mChildMovePathMeasure  = new PathMeasure();

    private float mChildOvalRadius;
    private float mBasicChildOvalRadius;
    private float mMaxMotherOvalSize;
    private float mMotherOvalHalfWidth;
    private float mMotherOvalHalfHeight;

    private float mChildLeftXOffset;
    private float mChildLeftYOffset;
    private float mChildRightXOffset;
    private float mChildRightYOffset;

    private int mOvalColor;
    private int mOvalDeepColor;
    private int mBackgroundColor;
    private int mBackgroundDeepColor;
    private int mCurrentOvalColor;
    private int mCurrentBackgroundColor;

    private int mRevealCircleRadius;
    private int mMaxRevealCircleRadius;

    private int mRotateDegrees;

    private float mStageMotherForwardTopLeftLength;
    private float mStageMotherBackwardTopLeftLength;
    private float mStageMotherForwardBottomLeftLength;
    private float mStageMotherBackwardBottomLeftLength;

    private float mStageChildPreForwardTopLeftLength;
    private float mStageChildForwardTopLeftLength;
    private float mStageChildPreBackwardTopLeftLength;
    private float mStageChildBackwardTopLeftLength;
    private float mStageChildForwardBottomLeftLength;
    private float mStageChildBackwardBottomLeftLength;

    private CircleBroodLoadingRenderer(Context context) {
        super(context);
        initialize(context);
        setupPaint();
    }

    private void initialize(Context context) {
        mWidth  = AppSize.INSTANCE.dp2px(DEFAULT_WIDTH);
        mHeight = AppSize.INSTANCE.dp2px(DEFAULT_HEIGHT);

        mMaxMotherOvalSize    = AppSize.INSTANCE.dp2px(MAX_MATHER_OVAL_SIZE);
        mBasicChildOvalRadius = AppSize.INSTANCE.dp2px(MIN_CHILD_OVAL_RADIUS);

        mOvalColor           = DEFAULT_OVAL_COLOR;
        mOvalDeepColor       = DEFAULT_OVAL_DEEP_COLOR;
        mBackgroundColor     = DEFAULT_BACKGROUND_COLOR;
        mBackgroundDeepColor = DEFAULT_BACKGROUND_DEEP_COLOR;

        mMotherOvalHalfWidth  = mMaxMotherOvalSize;
        mMotherOvalHalfHeight = mMaxMotherOvalSize;

        mMaxRevealCircleRadius = (int) (Math.sqrt(mWidth * mWidth + mHeight * mHeight) / 2 + 1);

        mDuration = ANIMATION_DURATION;
    }

    private void setupPaint() {
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(1.0F);
    }

    @Override
    protected void draw(
            Canvas canvas,
            Rect bounds
    ) {
        int saveCount = canvas.save();

        RectF arcBounds = mCurrentBounds;
        arcBounds.set(bounds);

        //draw background
        canvas.drawColor(mCurrentBackgroundColor);
        //draw reveal circle
        if (mRevealCircleRadius > 0) {
            mPaint.setColor(mCurrentBackgroundColor == mBackgroundColor ? mBackgroundDeepColor : mBackgroundColor);
            canvas.drawCircle(arcBounds.centerX(), arcBounds.centerY(), mRevealCircleRadius, mPaint);
        }

        //draw mother oval
        mPaint.setColor(mCurrentOvalColor);

        int motherSaveCount = canvas.save();
        canvas.rotate(mRotateDegrees, mMotherPosition[0], mMotherPosition[1]);
        canvas.drawPath(createMotherPath(), mPaint);
        canvas.drawPath(createLinkPath(), mPaint);
        canvas.restoreToCount(motherSaveCount);

        int childSaveCount = canvas.save();
        canvas.rotate(mRotateDegrees, mChildPosition[0], mChildPosition[1]);
        canvas.drawPath(createChildPath(), mPaint);
        canvas.restoreToCount(childSaveCount);
        canvas.restoreToCount(saveCount);

//    canvas.drawPath(mMotherMovePath, mPaint);
//    canvas.drawPath(mChildMovePath, mPaint);
//    canvas.drawLine(mMotherPosition[0], mMotherPosition[1], mChildPosition[0], mChildPosition[1], mPaint);
    }

    private Path createMotherPath() {
        mMotherOvalPath.reset();

        mMotherOvalPath.addOval(new RectF(mMotherPosition[0] - mMotherOvalHalfWidth, mMotherPosition[1] - mMotherOvalHalfHeight,
                mMotherPosition[0] + mMotherOvalHalfWidth, mMotherPosition[1] + mMotherOvalHalfHeight
        ), Path.Direction.CW);

        return mMotherOvalPath;
    }

    private Path createChildPath() {
        float bezierOffset = mChildOvalRadius * OVAL_BEZIER_FACTOR;

        Path path = new Path();
        path.moveTo(mChildPosition[0], mChildPosition[1] - mChildOvalRadius);
        //left_top arc
        path.cubicTo(mChildPosition[0] - bezierOffset - mChildLeftXOffset, mChildPosition[1] - mChildOvalRadius,
                mChildPosition[0] - mChildOvalRadius - mChildLeftXOffset, mChildPosition[1] - bezierOffset + mChildLeftYOffset,
                mChildPosition[0] - mChildOvalRadius - mChildLeftXOffset, mChildPosition[1]
        );
        //left_bottom arc
        path.cubicTo(mChildPosition[0] - mChildOvalRadius - mChildLeftXOffset, mChildPosition[1] + bezierOffset - mChildLeftYOffset,
                mChildPosition[0] - bezierOffset - mChildLeftXOffset, mChildPosition[1] + mChildOvalRadius,
                mChildPosition[0], mChildPosition[1] + mChildOvalRadius
        );

        //right_bottom arc
        path.cubicTo(mChildPosition[0] + bezierOffset + mChildRightXOffset, mChildPosition[1] + mChildOvalRadius,
                mChildPosition[0] + mChildOvalRadius + mChildRightXOffset, mChildPosition[1] + bezierOffset - mChildRightYOffset,
                mChildPosition[0] + mChildOvalRadius + mChildRightXOffset, mChildPosition[1]
        );
        //right_top arc
        path.cubicTo(mChildPosition[0] + mChildOvalRadius + mChildRightXOffset, mChildPosition[1] - bezierOffset + mChildRightYOffset,
                mChildPosition[0] + bezierOffset + mChildRightXOffset, mChildPosition[1] - mChildOvalRadius,
                mChildPosition[0], mChildPosition[1] - mChildOvalRadius
        );

        return path;
    }

    private Path createLinkPath() {
        Path  path         = new Path();
        float bezierOffset = mMotherOvalHalfWidth * OVAL_BEZIER_FACTOR;

        float distance = (float) Math.sqrt(Math.pow(mMotherPosition[0] - mChildPosition[0], 2.0F) + Math.pow(mMotherPosition[1] - mChildPosition[1], 2.0F));
        if (distance <= mMotherOvalHalfWidth + mChildOvalRadius * 1.2F
                && distance >= mMotherOvalHalfWidth - mChildOvalRadius * 1.2F) {
            float maxOffsetY = 2 * mChildOvalRadius * 1.2F;
            float offsetRate = (distance - (mMotherOvalHalfWidth - mChildOvalRadius * 1.2F)) / maxOffsetY;

            float mMotherOvalOffsetY = mMotherOvalHalfHeight - offsetRate * (mMotherOvalHalfHeight - mChildOvalRadius) * 0.85F;

            mMotherOvalPath.addOval(new RectF(mMotherPosition[0] - mMotherOvalHalfWidth, mMotherPosition[1] - mMotherOvalOffsetY,
                    mMotherPosition[0] + mMotherOvalHalfWidth, mMotherPosition[1] + mMotherOvalOffsetY
            ), Path.Direction.CW);

            float mMotherXOffset = distance - mMotherOvalHalfWidth + mChildOvalRadius;
            float distanceUltraLeft = (float) Math.sqrt(Math.pow(mMotherPosition[0] - mMotherOvalHalfWidth - mChildPosition[0], 2.0F)
                    + Math.pow(mMotherPosition[1] - mChildPosition[1], 2.0F));
            float distanceUltraRight = (float) Math.sqrt(Math.pow(mMotherPosition[0] + mMotherOvalHalfWidth - mChildPosition[0], 2.0F)
                    + Math.pow(mMotherPosition[1] - mChildPosition[1], 2.0F));

            path.moveTo(mMotherPosition[0], mMotherPosition[1] + mMotherOvalOffsetY);
            if (distanceUltraRight < distanceUltraLeft) {
                //right_bottom arc
                path.cubicTo(mMotherPosition[0] + bezierOffset + mMotherXOffset, mMotherPosition[1] + mMotherOvalOffsetY,
                        mMotherPosition[0] + distance + mChildOvalRadius, mMotherPosition[1] + mChildOvalRadius * 1.5F,
                        mMotherPosition[0] + distance + mChildOvalRadius, mMotherPosition[1]
                );
                //right_top arc
                path.cubicTo(mMotherPosition[0] + distance + mChildOvalRadius, mMotherPosition[1] - mChildOvalRadius * 1.5F,
                        mMotherPosition[0] + bezierOffset + mMotherXOffset, mMotherPosition[1] - mMotherOvalOffsetY,
                        mMotherPosition[0], mMotherPosition[1] - mMotherOvalOffsetY
                );
            } else {
                //left_bottom arc
                path.cubicTo(mMotherPosition[0] - bezierOffset - mMotherXOffset, mMotherPosition[1] + mMotherOvalOffsetY,
                        mMotherPosition[0] - distance - mChildOvalRadius, mMotherPosition[1] + mChildOvalRadius * 1.5F,
                        mMotherPosition[0] - distance - mChildOvalRadius, mMotherPosition[1]
                );
                //left_top arc
                path.cubicTo(mMotherPosition[0] - distance - mChildOvalRadius, mMotherPosition[1] - mChildOvalRadius * 1.5F,
                        mMotherPosition[0] - bezierOffset - mMotherXOffset, mMotherPosition[1] - mMotherOvalOffsetY,
                        mMotherPosition[0], mMotherPosition[1] - mMotherOvalOffsetY
                );
            }
            path.lineTo(mMotherPosition[0], mMotherPosition[1] + mMotherOvalOffsetY);
        }

        return path;
    }

    @Override
    protected void computeRender(float renderProgress) {
        if (mCurrentBounds.isEmpty()) {
            return;
        }

        if (mMotherMovePath.isEmpty()) {
            mMotherMovePath.set(createMotherMovePath());
            mMotherMovePathMeasure.setPath(mMotherMovePath, false);

            mChildMovePath.set(createChildMovePath());
            mChildMovePathMeasure.setPath(mChildMovePath, false);
        }

        //mother oval
        float motherMoveProgress = MOTHER_MOVE_INTERPOLATOR.getInterpolation(renderProgress);
        mMotherMovePathMeasure.getPosTan(getCurrentMotherMoveLength(motherMoveProgress), mMotherPosition, null);
        mMotherOvalHalfWidth  = mMaxMotherOvalSize;
        mMotherOvalHalfHeight = mMaxMotherOvalSize * getMotherShapeFactor(motherMoveProgress);

        //child Oval
        float childMoveProgress = CHILD_MOVE_INTERPOLATOR.getInterpolation(renderProgress);
        mChildMovePathMeasure.getPosTan(getCurrentChildMoveLength(childMoveProgress), mChildPosition, null);
        setupChildParams(childMoveProgress);

        mRotateDegrees = (int) (Math.toDegrees(Math.atan((mMotherPosition[1] - mChildPosition[1]) /
                (mMotherPosition[0] - mChildPosition[0]))));

        mRevealCircleRadius     = getCurrentRevealCircleRadius(renderProgress);
        mCurrentOvalColor       = getCurrentOvalColor(renderProgress);
        mCurrentBackgroundColor = getCurrentBackgroundColor(renderProgress);
    }

    private void setupChildParams(float input) {
        mChildOvalRadius = mBasicChildOvalRadius;

        mChildRightXOffset = 0.0F;
        mChildLeftXOffset  = 0.0F;

        if (input <= STAGE_CHILD_PRE_FORWARD_TOP_LEFT) {
            if (input >= 0.25) {
                float shapeProgress = (input - 0.25F) / 0.01F;
                mChildLeftXOffset = (1.0F - shapeProgress) * mChildOvalRadius * 0.25F;
            } else {
                mChildLeftXOffset = mChildOvalRadius * 0.25F;
            }
        } else if (input <= STAGE_CHILD_FORWARD_TOP_LEFT) {
            if (input > 0.275F && input < 0.285F) {
                float shapeProgress = (input - 0.275F) / 0.01F;
                mChildLeftXOffset = shapeProgress * mChildOvalRadius * 0.25F;
            } else if (input > 0.285F) {
                mChildLeftXOffset = mChildOvalRadius * 0.25F;
            }
        } else if (input <= STAGE_CHILD_PRE_BACKWARD_TOP_LEFT) {
            if (input > 0.38F) {
                float radiusProgress = (input - 0.38F) / 0.04F;
                mChildOvalRadius = mBasicChildOvalRadius * (1.0F + radiusProgress);
            }
        } else if (input <= STAGE_CHILD_BACKWARD_TOP_LEFT) {
            if (input < 0.46F) {
                float radiusProgress = (input - 0.42F) / 0.04F;
                mChildOvalRadius = mBasicChildOvalRadius * (2.0F - radiusProgress);
            }
        } else if (input <= STAGE_CHILD_FORWARD_BOTTOM_LEFT) {
            if (input > 0.65F) {
                float radiusProgress = (input - 0.65F) / 0.05F;
                mChildOvalRadius = mBasicChildOvalRadius * (1.0F + radiusProgress);
            }
        } else if (input <= STAGE_CHILD_BACKWARD_BOTTOM_LEFT) {
            if (input < 0.71F) {
                mChildOvalRadius = mBasicChildOvalRadius * 2.0F;
            } else if (input < 0.76F) {
                float radiusProgress = (input - 0.71F) / 0.05F;
                mChildOvalRadius = mBasicChildOvalRadius * (2.0F - radiusProgress);
            }
        } else {
        }

        mChildRightYOffset = mChildRightXOffset / 2.5F;
        mChildLeftYOffset  = mChildLeftXOffset / 2.5F;
    }

    private float getMotherShapeFactor(float input) {

        float shapeProgress;
        if (input <= STAGE_MOTHER_FORWARD_TOP_LEFT) {
            shapeProgress = input / STAGE_MOTHER_FORWARD_TOP_LEFT;
        } else if (input <= STAGE_MOTHER_BACKWARD_TOP_LEFT) {
            shapeProgress = (input - STAGE_MOTHER_FORWARD_TOP_LEFT) / (STAGE_MOTHER_BACKWARD_TOP_LEFT - STAGE_MOTHER_FORWARD_TOP_LEFT);
        } else if (input <= STAGE_MOTHER_FORWARD_BOTTOM_LEFT) {
            shapeProgress = (input - STAGE_MOTHER_BACKWARD_TOP_LEFT) / (STAGE_MOTHER_FORWARD_BOTTOM_LEFT - STAGE_MOTHER_BACKWARD_TOP_LEFT);
        } else if (input <= STAGE_MOTHER_BACKWARD_BOTTOM_LEFT) {
            shapeProgress = (input - STAGE_MOTHER_FORWARD_BOTTOM_LEFT) / (STAGE_MOTHER_BACKWARD_BOTTOM_LEFT - STAGE_MOTHER_FORWARD_BOTTOM_LEFT);
        } else {
            shapeProgress = 1.0F;
        }

        return shapeProgress < 0.5F ?
                1.0F - (1.0F - MAX_MATHER_SHAPE_CHANGE_FACTOR) * shapeProgress * 2.0F :
                MAX_MATHER_SHAPE_CHANGE_FACTOR + (1.0F - MAX_MATHER_SHAPE_CHANGE_FACTOR) * (shapeProgress - 0.5F) * 2.0F;
    }

    private float getCurrentMotherMoveLength(float input) {
        float currentStartDistance      = 0.0F;
        float currentStageDistance      = 0.0F;
        float currentStateStartProgress = 0.0F;
        float currentStateEndProgress   = 0.0F;

        if (input > 0.0F) {
            currentStartDistance += currentStageDistance;
            currentStageDistance      = mStageMotherForwardTopLeftLength;
            currentStateStartProgress = 0.0F;
            currentStateEndProgress   = STAGE_MOTHER_FORWARD_TOP_LEFT;
        }

        if (input > STAGE_MOTHER_FORWARD_TOP_LEFT) {
            currentStartDistance += currentStageDistance;
            currentStageDistance      = mStageMotherBackwardTopLeftLength;
            currentStateStartProgress = STAGE_MOTHER_FORWARD_TOP_LEFT;
            currentStateEndProgress   = STAGE_MOTHER_BACKWARD_TOP_LEFT;
        }

        if (input > STAGE_MOTHER_BACKWARD_TOP_LEFT) {
            currentStartDistance += currentStageDistance;
            currentStageDistance      = mStageMotherForwardBottomLeftLength;
            currentStateStartProgress = STAGE_MOTHER_BACKWARD_TOP_LEFT;
            currentStateEndProgress   = STAGE_MOTHER_FORWARD_BOTTOM_LEFT;
        }

        if (input > STAGE_MOTHER_FORWARD_BOTTOM_LEFT) {
            currentStartDistance += currentStageDistance;
            currentStageDistance      = mStageMotherBackwardBottomLeftLength;
            currentStateStartProgress = STAGE_MOTHER_FORWARD_BOTTOM_LEFT;
            currentStateEndProgress   = STAGE_MOTHER_BACKWARD_BOTTOM_LEFT;
        }

        if (input > STAGE_MOTHER_BACKWARD_BOTTOM_LEFT) {
            return currentStartDistance + currentStageDistance;
        }

        return currentStartDistance + (input - currentStateStartProgress) /
                (currentStateEndProgress - currentStateStartProgress) * currentStageDistance;
    }

    private float getCurrentChildMoveLength(float input) {
        float currentStartDistance      = 0.0F;
        float currentStageDistance      = 0.0F;
        float currentStateStartProgress = 0.0F;
        float currentStateEndProgress   = 0.0F;

        if (input > 0.0F) {
            currentStartDistance += currentStageDistance;
            currentStageDistance      = mStageChildPreForwardTopLeftLength;
            currentStateStartProgress = 0.0F;
            currentStateEndProgress   = STAGE_CHILD_PRE_FORWARD_TOP_LEFT;
        }

        if (input > STAGE_CHILD_PRE_FORWARD_TOP_LEFT) {
            currentStartDistance += currentStageDistance;
            currentStageDistance      = mStageChildForwardTopLeftLength;
            currentStateStartProgress = STAGE_CHILD_PRE_FORWARD_TOP_LEFT;
            currentStateEndProgress   = STAGE_CHILD_FORWARD_TOP_LEFT;
        }

        if (input > STAGE_CHILD_FORWARD_TOP_LEFT) {
            currentStartDistance += currentStageDistance;
            currentStageDistance      = mStageChildPreBackwardTopLeftLength;
            currentStateStartProgress = STAGE_CHILD_FORWARD_TOP_LEFT;
            currentStateEndProgress   = STAGE_CHILD_PRE_BACKWARD_TOP_LEFT;
        }

        if (input > STAGE_CHILD_PRE_BACKWARD_TOP_LEFT) {
            currentStartDistance += currentStageDistance;
            currentStageDistance      = mStageChildBackwardTopLeftLength;
            currentStateStartProgress = STAGE_CHILD_PRE_BACKWARD_TOP_LEFT;
            currentStateEndProgress   = STAGE_CHILD_BACKWARD_TOP_LEFT;
        }

        if (input > STAGE_CHILD_BACKWARD_TOP_LEFT) {
            currentStartDistance += currentStageDistance;
            currentStageDistance      = mStageChildForwardBottomLeftLength;
            currentStateStartProgress = STAGE_CHILD_BACKWARD_TOP_LEFT;
            currentStateEndProgress   = STAGE_CHILD_FORWARD_BOTTOM_LEFT;
        }

        if (input > STAGE_CHILD_FORWARD_BOTTOM_LEFT) {
            currentStartDistance += currentStageDistance;
            currentStageDistance      = mStageChildBackwardBottomLeftLength;
            currentStateStartProgress = STAGE_CHILD_FORWARD_BOTTOM_LEFT;
            currentStateEndProgress   = STAGE_CHILD_BACKWARD_BOTTOM_LEFT;
        }

        if (input > STAGE_CHILD_BACKWARD_BOTTOM_LEFT) {
            return currentStartDistance + currentStageDistance;
        }

        return currentStartDistance + (input - currentStateStartProgress) /
                (currentStateEndProgress - currentStateStartProgress) * currentStageDistance;
    }

    private Path createMotherMovePath() {
        Path path = new Path();

        float centerX           = mCurrentBounds.centerX();
        float centerY           = mCurrentBounds.centerY();
        float currentPathLength = 0.0F;

        path.moveTo(centerX, centerY);
        //forward top left
        path.quadTo(centerX - mMotherOvalHalfWidth * 2.0F, centerY,
                centerX - mMotherOvalHalfWidth * 2.0F, centerY - mMotherOvalHalfHeight
        );
        mStageMotherForwardTopLeftLength = getRestLength(path, currentPathLength);
        currentPathLength += mStageMotherForwardTopLeftLength;

        //backward top left
        path.quadTo(centerX - mMotherOvalHalfWidth * 1.0F, centerY - mMotherOvalHalfHeight,
                centerX, centerY
        );
        mStageMotherBackwardTopLeftLength = getRestLength(path, currentPathLength);
        currentPathLength += mStageMotherBackwardTopLeftLength;
        //forward bottom left
        path.quadTo(centerX, centerY + mMotherOvalHalfHeight,
                centerX - mMotherOvalHalfWidth / 2, centerY + mMotherOvalHalfHeight * 1.1F
        );
        mStageMotherForwardBottomLeftLength = getRestLength(path, currentPathLength);
        currentPathLength += mStageMotherForwardBottomLeftLength;
        //backward bottom left
        path.quadTo(centerX - mMotherOvalHalfWidth / 2, centerY + mMotherOvalHalfHeight * 0.6F,
                centerX, centerY
        );
        mStageMotherBackwardBottomLeftLength = getRestLength(path, currentPathLength);

        return path;
    }

    private Path createChildMovePath() {
        Path path = new Path();

        float centerX           = mCurrentBounds.centerX();
        float centerY           = mCurrentBounds.centerY();
        float currentPathLength = 0.0F;

        //start
        path.moveTo(centerX, centerY);
        //pre forward top left
        path.lineTo(centerX + mMotherOvalHalfWidth * 0.75F, centerY);
        mStageChildPreForwardTopLeftLength = getRestLength(path, currentPathLength);
        currentPathLength += mStageChildPreForwardTopLeftLength;
        //forward top left
        path.quadTo(centerX - mMotherOvalHalfWidth * 0.5F, centerY,
                centerX - mMotherOvalHalfWidth * 2.0F, centerY - mMotherOvalHalfHeight
        );
        mStageChildForwardTopLeftLength = getRestLength(path, currentPathLength);
        currentPathLength += mStageChildForwardTopLeftLength;
        //pre backward top left
        path.lineTo(centerX - mMotherOvalHalfWidth * 2.0F + mMotherOvalHalfWidth * 0.2F, centerY - mMotherOvalHalfHeight);
        path.quadTo(centerX - mMotherOvalHalfWidth * 2.5F, centerY - mMotherOvalHalfHeight * 2,
                centerX - mMotherOvalHalfWidth * 1.5F, centerY - mMotherOvalHalfHeight * 2.25F
        );
        mStageChildPreBackwardTopLeftLength = getRestLength(path, currentPathLength);
        currentPathLength += mStageChildPreBackwardTopLeftLength;
        //backward top left
        path.quadTo(centerX - mMotherOvalHalfWidth * 0.2F, centerY - mMotherOvalHalfHeight * 2.25F,
                centerX, centerY
        );
        mStageChildBackwardTopLeftLength = getRestLength(path, currentPathLength);
        currentPathLength += mStageChildBackwardTopLeftLength;
        //forward bottom left
        path.cubicTo(centerX, centerY + mMotherOvalHalfHeight,
                centerX - mMotherOvalHalfWidth, centerY + mMotherOvalHalfHeight * 2.5F,
                centerX - mMotherOvalHalfWidth * 1.5F, centerY + mMotherOvalHalfHeight * 2.5F
        );
        mStageChildForwardBottomLeftLength = getRestLength(path, currentPathLength);
        currentPathLength += mStageChildForwardBottomLeftLength;
        //backward bottom left
        path.cubicTo(
                centerX - mMotherOvalHalfWidth * 2.0F, centerY + mMotherOvalHalfHeight * 2.5F,
                centerX - mMotherOvalHalfWidth * 3.0F, centerY + mMotherOvalHalfHeight * 0.8F,
                centerX, centerY
        );
        mStageChildBackwardBottomLeftLength = getRestLength(path, currentPathLength);

        return path;
    }

    private int getCurrentRevealCircleRadius(float input) {
        int result = 0;
        if (input > 0.44F && input < 0.48F) {
            result = (int) ((input - 0.44F) / 0.04F * mMaxRevealCircleRadius);
        }

        if (input > 0.81F && input < 0.85F) {
            result = (int) ((input - 0.81F) / 0.04F * mMaxRevealCircleRadius);
        }

        return result;
    }

    private int getCurrentBackgroundColor(float input) {
        return input < 0.48F || input > 0.85F ? mBackgroundColor : mBackgroundDeepColor;
    }

    private int getCurrentOvalColor(float input) {
        int result;

        if (input < 0.5F) {
            result = mOvalColor;
        } else if (input < 0.75F) {
            float colorProgress = (input - 0.5F) / 0.2F;
            result = evaluateColorChange(colorProgress, mOvalColor, mOvalDeepColor);
        } else if (input < 0.85F) {
            result = mOvalDeepColor;
        } else {
            float colorProgress = (input - 0.9F) / 0.1F;
            result = evaluateColorChange(colorProgress, mOvalDeepColor, mOvalColor);
        }

        return result;
    }

    private int evaluateColorChange(
            float fraction,
            int startValue,
            int endValue
    ) {
        int startA = (startValue >> 24) & 0xff;
        int startR = (startValue >> 16) & 0xff;
        int startG = (startValue >> 8) & 0xff;
        int startB = startValue & 0xff;

        int endA = (endValue >> 24) & 0xff;
        int endR = (endValue >> 16) & 0xff;
        int endG = (endValue >> 8) & 0xff;
        int endB = endValue & 0xff;

        return ((startA + (int) (fraction * (endA - startA))) << 24) |
                ((startR + (int) (fraction * (endR - startR))) << 16) |
                ((startG + (int) (fraction * (endG - startG))) << 8) |
                ((startB + (int) (fraction * (endB - startB))));
    }

    private float getRestLength(
            Path path,
            float startD
    ) {
        Path        tempPath    = new Path();
        PathMeasure pathMeasure = new PathMeasure(path, false);

        pathMeasure.getSegment(startD, pathMeasure.getLength(), tempPath, true);

        pathMeasure.setPath(tempPath, false);

        return pathMeasure.getLength();
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

    private class MotherMoveInterpolator
            implements Interpolator {
        @Override
        public float getInterpolation(float input) {
            float result;

            if (input <= STAGE_MOTHER_FORWARD_TOP_LEFT) {
                result = ACCELERATE_INTERPOLATOR10.getInterpolation(input * 2.941F) / 2.941F;
            } else if (input <= STAGE_MOTHER_BACKWARD_TOP_LEFT) {
                result = 0.34F + DECELERATE_INTERPOLATOR10.getInterpolation((input - 0.34F) * 6.25F) / 6.25F;
            } else if (input <= STAGE_MOTHER_FORWARD_BOTTOM_LEFT) {
                result = 0.5F + ACCELERATE_INTERPOLATOR03.getInterpolation((input - 0.5F) * 6.666F) / 4.0F;
            } else if (input <= STAGE_MOTHER_BACKWARD_BOTTOM_LEFT) {
                result = 0.75F + DECELERATE_INTERPOLATOR03.getInterpolation((input - 0.65F) * 5.46F) / 4.0F;
            } else {
                result = 1.0F;
            }

            return result;
        }
    }

    private class ChildMoveInterpolator
            implements Interpolator {

        @Override
        public float getInterpolation(float input) {
            float result;

            if (input < STAGE_CHILD_DELAY) {
                return 0.0F;
            } else if (input <= STAGE_CHILD_PRE_FORWARD_TOP_LEFT) {
                result = DECELERATE_INTERPOLATOR10.getInterpolation((input - 0.1F) * 6.25F) / 3.846F;
            } else if (input <= STAGE_CHILD_FORWARD_TOP_LEFT) {
                result = 0.26F + ACCELERATE_INTERPOLATOR10.getInterpolation((input - 0.26F) * 12.5F) / 12.5F;
            } else if (input <= STAGE_CHILD_PRE_BACKWARD_TOP_LEFT) {
                result = 0.34F + DECELERATE_INTERPOLATOR08.getInterpolation((input - 0.34F) * 12.5F) / 12.5F;
            } else if (input <= STAGE_CHILD_BACKWARD_TOP_LEFT) {
                result = 0.42F + ACCELERATE_INTERPOLATOR08.getInterpolation((input - 0.42F) * 12.5F) / 12.5F;
            } else if (input <= STAGE_CHILD_FORWARD_BOTTOM_LEFT) {
                result = 0.5F + DECELERATE_INTERPOLATOR05.getInterpolation((input - 0.5F) * 5.0F) / 5.0F;
            } else if (input <= STAGE_CHILD_BACKWARD_BOTTOM_LEFT) {
                result = 0.7F + ACCELERATE_INTERPOLATOR05.getInterpolation((input - 0.7F) * 5.0F) / 3.33F;
            } else {
                result = 1.0F;
            }

            return result;
        }
    }

    public static class Builder {
        private final Context mContext;

        public Builder(Context context) {
            this.mContext = context;
        }

        public CircleBroodLoadingRenderer build() {
            return new CircleBroodLoadingRenderer(mContext);
        }
    }
}