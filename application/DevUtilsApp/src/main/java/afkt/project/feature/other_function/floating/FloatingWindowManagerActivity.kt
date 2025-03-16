package afkt.project.feature.other_function.floating

import afkt.project.R
import afkt.project.base.project.BaseProjectActivity
import afkt.project.base.project.BaseProjectViewModel
import afkt.project.base.project.bindAdapter
import afkt.project.data_model.button.ButtonList
import afkt.project.data_model.button.ButtonValue
import afkt.project.data_model.button.RouterPath
import afkt.project.databinding.BaseViewRecyclerviewBinding
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.PointF
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import com.therouter.router.Route
import dev.DevUtils
import dev.expand.engine.log.log_dTag
import dev.utils.app.assist.floating.*
import dev.utils.app.toast.ToastTintUtils

/**
 * detail: 悬浮窗管理辅助类 ( 需权限 )
 * @author Ttt
 */
@Route(path = RouterPath.OTHER_FUNCTION.FloatingWindowManagerActivity_PATH)
class FloatingWindowManagerActivity :
    BaseProjectActivity<BaseViewRecyclerviewBinding, BaseProjectViewModel>(
        R.layout.base_view_recyclerview, simple_Agile = {
            if (it is FloatingWindowManagerActivity) {
                it.apply {
                    binding.vidRv.bindAdapter(ButtonList.floatingWindowButtonValues) { buttonValue ->
                        when (buttonValue.type) {
                            ButtonValue.BTN_OPEN_FLOATING_WINDOW -> {
                                if (checkOverlayPermission()) {
                                    Utils.instance.addView()
                                }
                            }

                            ButtonValue.BTN_CLOSE_FLOATING_WINDOW -> {
                                Utils.instance.removeView()
                            }
                        }
                    }
                }
            }
        }
    ) {

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        intent: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, intent)

        if (FloatingWindowManagerAssist.isOverlayRequestCode(requestCode)) {
            checkOverlayPermission()
        }
    }

    private fun checkOverlayPermission(): Boolean {
        return if (FloatingWindowManagerAssist.checkOverlayPermission(this, true)) {
            true
        } else {
            ToastTintUtils.error("请先开启悬浮窗权限")
            false
        }
    }
}

// ==========
// = 实现代码 =
// ==========

/**
 * detail: 悬浮窗工具类
 * @author Ttt
 */
internal class Utils private constructor() {

    companion object {

        val instance: Utils by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            Utils()
        }
    }

    // 悬浮窗管理辅助类
    private val mAssist = FloatingWindowManagerAssist()

    // 悬浮窗触摸辅助类实现
    private val mTouchAssist: IFloatingTouch by lazy {
        object : DevFloatingTouchIMPL() {
            override fun updateViewLayout(
                view: View?,
                dx: Int,
                dy: Int
            ) {
                mAssist.updateViewLayout(view, dx, dy)
            }
        }.apply {
            // 悬浮窗触摸事件接口 ( 如果不需要触发点击、长按则可不设置 )
            floatingListener = object : DevFloatingListener() {
                override fun onClick(
                    view: View?,
                    event: MotionEvent,
                    firstPoint: PointF
                ): Boolean {
                    if (DevFloatingCommon.isValidEvent(event, firstPoint)) {
                        ToastTintUtils.info("触发点击")
                    }
                    return true
                }

                override fun onLongClick(
                    view: View?,
                    event: MotionEvent,
                    firstPoint: PointF
                ): Boolean {
                    if (DevFloatingCommon.isValidEvent(event, firstPoint)) {
                        ToastTintUtils.info("触发长按")
                    }
                    return true
                }
            }
        }
    }

    // 悬浮 View
    private val mFloatingView: FloatingView by lazy {
        FloatingView(DevUtils.getContext(), mTouchAssist)
    }

    /**
     * 添加悬浮 View
     */
    fun addView() {
        mAssist.addView(mFloatingView)
    }

    /**
     * 移除悬浮 View
     */
    fun removeView() {
        mAssist.removeView(mFloatingView)
    }
}

/**
 * detail: 悬浮 View
 * @author Ttt
 */
@SuppressLint("ViewConstructor")
class FloatingView(
    thisContext: Context,
    private val assist: IFloatingTouch?
) : LinearLayout(thisContext) {

    init {
        initialize()
    }

    private fun initialize() {
        View.inflate(context, R.layout.layout_floating, this)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        assist?.onTouchEvent(this, event)
        return true
    }

    // =

    private val TAG = FloatingView::class.java.simpleName

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        TAG.log_dTag(
            message = "onAttachedToWindow"
        )
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

        TAG.log_dTag(
            message = "onDetachedFromWindow"
        )
    }
}