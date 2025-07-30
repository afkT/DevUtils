package afkt.project.features.ui_effect.shop_cart

import afkt.project.databinding.IncludeShopCartRedDotViewBinding
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import android.graphics.Point
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationSet
import android.view.animation.ScaleAnimation
import dev.utils.app.ViewUtils
import dev.utils.app.helper.quick.QuickHelper
import dev.utils.common.ArrayUtils

/**
 * detail: 购物车动画
 * @author Ttt
 */
class ShopCartAnimation {

    fun startAnim(
        startView: View,
        endView: View
    ) {
        // 开始位置 - 起点位置
        val startPoints = ViewUtils.getLocationInWindow(startView)
        // 准备前往 - 结束位置
        val endPoints = ViewUtils.getLocationInWindow(endView)
        // 动画时间
        val duration = 2000L

        // 添加在对应的 Fragment 显示的 View 中
        val parent: ViewGroup = startView.rootView as ViewGroup
        // 动画 View - 小红点
        val animBinding = IncludeShopCartRedDotViewBinding.inflate(
            LayoutInflater.from(startView.context),
            parent, true
        )
        QuickHelper.get(animBinding.root)
            .setMargin(startPoints[0], startPoints[1], 0, 0)
        // 待执行动画 View
        val animView = animBinding.root

        // 创建动画并执行
        createAnimAndExecute(
            animView, endView, endPoints[0], endPoints[1],
            startPoints[0], startPoints[1],
            duration, object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    // 动画结束抖动数量
                    val zoomTime = 100L
                    val zoomAnim = ScaleAnimation(
                        1.0F, 1.1F, 1.0F, 1.1F,
                        ScaleAnimation.RELATIVE_TO_SELF, 0.5F,
                        ScaleAnimation.RELATIVE_TO_SELF, 0.5F
                    )
                    zoomAnim.duration = zoomTime
                    val zoomOutAnim = ScaleAnimation(
                        1.1F, 1.0F, 1.1F, 1.0F,
                        ScaleAnimation.RELATIVE_TO_SELF, 0.5F,
                        ScaleAnimation.RELATIVE_TO_SELF, 0.5F
                    )
                    zoomOutAnim.duration = zoomTime
                    val animatorSet = AnimationSet(false)
                    animatorSet.addAnimation(zoomAnim)
                    animatorSet.addAnimation(zoomOutAnim)
                    // 开始动画
                    QuickHelper.get(endView)
                        .clearAnimation().startAnimation(animatorSet)
                }
            }
        )
    }

    // ==========
    // = 动画代码 =
    // ==========

    /**
     * 创建动画并执行
     * @param view 待执行 View
     * @param endView 结束位置 View
     * @param toX  前往的 X
     * @param toY  前往的 Y
     * @param cX   当前的 X
     * @param cY   当前的 Y
     * @param duration 动画时间
     * @param listener 动画结束触发
     */
    private fun createAnimAndExecute(
        view: View,
        endView: View,
        toX: Int,
        toY: Int,
        cX: Int,
        cY: Int,
        duration: Long,
        listener: AnimatorListenerAdapter
    ) {
        val wh = ViewUtils.getWidthHeight(endView)
        // 结尾偏移值
        val endOffsetX = ArrayUtils.get(wh, 0) / 2
        val endOffsetY = -ArrayUtils.get(wh, 1) / 3
        // 中间点偏移值
        val controlOffsetX = 0 // 可以设置结束点偏移位置
        val controlOffsetY = 0
        // 开始、结束位置
        val startPosition = Point(cX, cY)
        val endPosition = Point(toX + endOffsetX, toY + endOffsetY)
        // 中间控制点
        val pointX = (startPosition.x + endPosition.x) / 2 - controlOffsetX
        val pointY = startPosition.y - controlOffsetY
        val controlPoint = Point(pointX, pointY)
        // 开始执行属性动画
        val valueAnimator = ValueAnimator.ofObject(
            BezierCurve2(controlPoint), startPosition, endPosition
        )
        valueAnimator.duration = duration
        valueAnimator.start()
        valueAnimator.addUpdateListener { valueAnimator ->
            val point = valueAnimator.animatedValue as Point
            view.x = point.x.toFloat()
            view.y = point.y.toFloat()
        }
        // 添加动画结束监听
        valueAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                ViewUtils.removeSelfFromParent(view)
                // 触发事件
                listener.onAnimationEnd(animation)
            }
        })
    }

    /**
     * 贝塞尔曲线 ( 二阶抛物线 )
     * controlPoint 是中间的转折点
     * startValue 是起始的位置
     * endValue 是结束的位置
     */
    private class BezierCurve2(private val controlPoint: Point) : TypeEvaluator<Point> {
        override fun evaluate(
            t: Float,
            startValue: Point,
            endValue: Point
        ): Point {
            val x =
                ((1 - t) * (1 - t) * startValue.x + 2 * t * (1 - t) * controlPoint.x + t * t * endValue.x).toInt()
            val y =
                ((1 - t) * (1 - t) * startValue.y + 2 * t * (1 - t) * controlPoint.y + t * t * endValue.y).toInt()
            return Point(x, y)
        }
    }
}