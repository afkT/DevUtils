package afkt.project.features.other_function.floating

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppContext
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentOtherFunctionFloatingBinding
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.PointF
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.contract.ActivityResultContracts
import dev.base.simple.extensions.asFragment
import dev.expand.engine.log.log_dTag
import dev.expand.engine.toast.toast_showShort
import dev.mvvm.utils.hi.hiif.hiIfNotNull
import dev.utils.app.IntentUtils
import dev.utils.app.activity_result.ActivityResultAssist
import dev.utils.app.assist.floating.*

/**
 * detail: 悬浮窗管理辅助类 ( 需权限 )
 * @author Ttt
 * 推荐使用
 * 悬浮窗框架 https://github.com/getActivity/EasyWindow
 * 悬浮窗解决方案 https://github.com/Petterpx/FloatingX
 */
class FloatingWindowManagerFragment : AppFragment<FragmentOtherFunctionFloatingBinding, FloatingWindowManagerViewModel>(
    R.layout.fragment_other_function_floating,
    BR.viewModel, simple_Agile = { frg ->
        frg.asFragment<FloatingWindowManagerFragment> {
            viewModel.initializeIntentResult(this)
        }
    }
)

class FloatingWindowManagerViewModel : AppViewModel() {

    // 打开悬浮窗
    val clickOpen = View.OnClickListener { view ->
        if (checkOverlayPermission()) {
            Utils.instance.addView()
        }
    }

    // 关闭悬浮窗
    val clickClose = View.OnClickListener { view ->
        Utils.instance.removeView()
    }

    /**
     * 检测是否存在悬浮窗权限并跳转
     */
    private fun checkOverlayPermission(): Boolean {
        if (FloatingWindowManagerAssist.canDrawOverlays(AppContext.context())) {
            return true
        }
        toast_showShort(text = "请先开启悬浮窗权限")
        intentRouter()
        return false
    }

    // =========================
    // = 跳转 Intent 回传 Result =
    // =========================

    // 跳转回传辅助类
    private var intentResultAssist: ActivityResultAssist<Intent, ActivityResult>? = null

    /**
     * 初始化【跳转 Intent】回传处理
     */
    fun initializeIntentResult(caller: ActivityResultCaller) {
        this.intentResultAssist = ActivityResultAssist(
            caller, ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // 再次检查权限
                checkOverlayPermission()
            }
        }
    }

    private fun intentRouter() {
        intentResultAssist.hiIfNotNull {
            val intent = IntentUtils.getManageOverlayPermissionIntent()
            it.launch(intent)
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

    // 悬浮窗管理辅助类 ( 需权限 )
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
                        toast_showShort(text = "触发点击")
                    }
                    return true
                }

                override fun onLongClick(
                    view: View?,
                    event: MotionEvent,
                    firstPoint: PointF
                ): Boolean {
                    if (DevFloatingCommon.isValidEvent(event, firstPoint)) {
                        toast_showShort(text = "触发长按")
                    }
                    return true
                }
            }
        }
    }

    // 悬浮 View
    private val mFloatingView: FloatingView by lazy {
        FloatingView(AppContext.context(), mTouchAssist)
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
        inflate(context, R.layout.layout_floating, this)
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