package afkt.project.util

import afkt.project.R
import afkt.project.databinding.IncludeBottomShopCartFloatingAnimViewBinding
import afkt.project.databinding.IncludeBottomShopCartFloatingBinding
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import android.app.Activity
import android.graphics.Point
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationSet
import android.view.animation.ScaleAnimation
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import dev.assist.NumberControlAssist
import dev.base.number.INumberListener
import dev.utils.app.ResourceUtils
import dev.utils.app.ViewUtils
import dev.utils.app.assist.floating.FloatingWindowManagerAssist2
import dev.utils.app.assist.floating.IFloatingActivity
import dev.utils.app.assist.floating.IFloatingOperate
import dev.utils.app.helper.quick.QuickHelper
import dev.utils.common.ArrayUtils

/**
 * detail: 购物车悬浮对外公开类
 * @author Ttt
 * 搭配 [IFloatingActivity]、[FloatingWindowManagerAssist2] 使用, 简化代码以及使用流程
 * 而不是在每个布局内最外层加 FrameLayout 使用 include 插入布局
 */
class ShopCartFloating(private val activity: AppCompatActivity) {

    // 数量控制辅助类
    private val numberControl = NumberControlAssist(0, Int.MAX_VALUE).setNumberListener(
        object : INumberListener {
            override fun onPrepareChanged(
                isAdd: Boolean,
                curNumber: Int,
                afterNumber: Int
            ): Boolean {
                return true
            }

            override fun onNumberChanged(
                isAdd: Boolean,
                curNumber: Int
            ) {
            }
        }
    )

    // 购物车悬浮 View
    private val binding: IncludeBottomShopCartFloatingBinding by lazy {
        IncludeBottomShopCartFloatingBinding.inflate(
            activity.layoutInflater,
            null, false
        ).apply {
            numberControl.currentNumber = 0
            // 设置购买数量
            QuickHelper.get(vidCartNumberTv)
                .setText(numberControl.currentNumber.toString())
                .setVisibilitys(true)
        }
    }

    // 悬浮窗生命周期处理类
    private val floatingLifecycle: FloatingLifecycle = FloatingLifecycle(activity, binding)

    // 购物车动画
    private val shopCartAnimation = ShopCartAnimation()

    /**
     * 执行添加购物车动画
     * @param view View
     */
    fun executeAnim(view: View) {
        numberControl.addNumber().apply {
            val numberTxt = if (currentNumber > 99) "99+" else currentNumber.toString()
            // 设置购买数量
            QuickHelper.get(binding.vidCartNumberTv)
                .setText(numberTxt)
        }
        // 开始动画
        shopCartAnimation.startAnim(
            view, binding.vidCartFrame, binding, activity
        )
    }
}

// ==================================================
// = IFloatingActivity、FloatingWindowManagerAssist2 =
// ==================================================

/**
 * detail: 悬浮窗生命周期处理类
 * @author Ttt
 */
class FloatingLifecycle(
    private val activity: AppCompatActivity,
    private val binding: IncludeBottomShopCartFloatingBinding
) : DefaultLifecycleObserver,
    IFloatingActivity {

    init {
        activity.lifecycle.addObserver(this)
    }

    // =====================
    // = IFloatingActivity =
    // =====================

    override fun getAttachActivity(): Activity {
        return activity
    }

    override fun getMapFloatingKey(): String {
        return this.toString()
    }

    override fun getMapFloatingView(): View {
        return InnerUtils.instance.createFloatingView(binding.root)
    }

    override fun getMapFloatingViewLayoutParams(): ViewGroup.LayoutParams {
        return InnerUtils.instance.createLayoutParams(this)
    }

    // ============================
    // = DefaultLifecycleObserver =
    // ============================

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        // 添加悬浮窗 View
        InnerUtils.instance.addFloatingView(this)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        // 移除悬浮窗 View
        InnerUtils.instance.removeFloatingView(this)
    }
}

/**
 * detail: 悬浮窗工具类
 * @author Ttt
 */
internal class InnerUtils private constructor() : IFloatingOperate {

    companion object {

        val instance: InnerUtils by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            InnerUtils()
        }
    }

    // 悬浮窗管理辅助类
    private val mAssist = object : FloatingWindowManagerAssist2() {
        override fun updateViewLayout(
            floatingActivity: IFloatingActivity,
            view: View
        ) {
            instance.updateViewLayout(floatingActivity, view)
        }
    }

    /**
     * 创建悬浮 View
     * @param view 悬浮窗 View
     * @return FloatingView
     */
    fun createFloatingView(view: View): View {
        return view
    }

    /**
     * 创建悬浮 View LayoutParams
     * @param floatingActivity 悬浮窗辅助类接口
     * @return ViewGroup.LayoutParams
     */
    fun createLayoutParams(floatingActivity: IFloatingActivity): ViewGroup.LayoutParams {
        return FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            // 靠左下
            gravity = Gravity.START or Gravity.BOTTOM
            // 设置边距
            setMargins(
                0, 0, 0,
                ResourceUtils.getDimensionInt(R.dimen.un_dp_70)
            )
        }
    }

    // ====================
    // = IFloatingOperate =
    // ====================

    override fun removeFloatingView(floatingActivity: IFloatingActivity): Boolean {
        return mAssist.removeFloatingView(floatingActivity)
    }

    override fun addFloatingView(floatingActivity: IFloatingActivity): Boolean {
        return mAssist.addFloatingView(floatingActivity)
    }

    override fun removeAllFloatingView() {
        mAssist.removeAllFloatingView()
    }

    override fun updateViewLayout(
        floatingActivity: IFloatingActivity,
        view: View
    ) {
    }

    override fun isNeedsAdd(): Boolean {
        return mAssist.isNeedsAdd
    }

    override fun setNeedsAdd(needsAdd: Boolean) {
        mAssist.isNeedsAdd = needsAdd
    }
}

// ============
// = 购物车动画 =
// ============

class ShopCartAnimation {

    fun startAnim(
        startView: View,
        endView: View,
        binding: IncludeBottomShopCartFloatingBinding,
        activity: AppCompatActivity
    ) {
        // 开始位置 - 起点位置
        val startPoints = ViewUtils.getLocationInWindow(startView)
        // 准备前往 - 结束位置
        val endPoints = ViewUtils.getLocationInWindow(endView)
        // 悬浮 View - 用于获取宽高
        val rootView = binding.root

        // 动画 View - 小红点
        val animBinding = IncludeBottomShopCartFloatingAnimViewBinding.inflate(
            activity.layoutInflater, ViewUtils.getContentView(activity), true
        )
        QuickHelper.get(animBinding.root)
            .setMargin(startPoints[0], startPoints[1], 0, 0)
        // 待执行动画 View
        val animView = animBinding.root

        // 创建动画并执行
        createAnimAndExecute(
            animView, endPoints[0], endPoints[1],
            startPoints[0], startPoints[1],
            rootView, object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    // 动画结束抖动数量
                    val zoomTime: Long = 100
                    val zoomAnim = ScaleAnimation(
                        1.0f, 1.1f, 1.0f, 1.1f,
                        ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                        ScaleAnimation.RELATIVE_TO_SELF, 0.5f
                    )
                    zoomAnim.duration = zoomTime
                    val zoomOutAnim = ScaleAnimation(
                        1.1f, 1.0f, 1.1f, 1.0f,
                        ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                        ScaleAnimation.RELATIVE_TO_SELF, 0.5f
                    )
                    zoomOutAnim.duration = zoomTime
                    val animatorSet = AnimationSet(false)
                    animatorSet.addAnimation(zoomAnim)
                    animatorSet.addAnimation(zoomOutAnim)
                    // 开始动画
                    QuickHelper.get(binding.vidCartNumberTv)
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
     * @param toX  前往的 X
     * @param toY  前往的 Y
     * @param cX   当前的 X
     * @param cY   当前的 Y
     * @param rootView 悬浮 View
     * @param listener 动画结束触发
     */
    private fun createAnimAndExecute(
        view: View,
        toX: Int,
        toY: Int,
        cX: Int,
        cY: Int,
        rootView: View,
        listener: AnimatorListenerAdapter
    ) {
        val wh = ViewUtils.getWidthHeight(rootView)
        // 结尾偏移值
        val endOffsetX = ArrayUtils.get(wh, 0) / 2
        val endOffsetY = -ArrayUtils.get(wh, 1) / 3
        // 中间点偏移值
        val controlOffsetX: Int = ResourceUtils.getDimensionInt(R.dimen.un_dp_50)
        val controlOffsetY: Int = ResourceUtils.getDimensionInt(R.dimen.un_dp_200)
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
        valueAnimator.duration = 5000
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