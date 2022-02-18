package afkt.project.feature.other_function

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.base.config.RouterPath
import afkt.project.databinding.BaseViewRecyclerviewBinding
import afkt.project.feature.ButtonAdapter
import afkt.project.model.item.ButtonList
import afkt.project.model.item.ButtonValue
import android.graphics.PointF
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.alibaba.android.arouter.facade.annotation.Route
import dev.callback.DevItemClickCallback
import dev.utils.app.ViewUtils
import dev.utils.app.assist.floating.*
import dev.utils.app.toast.ToastTintUtils

/**
 * detail: 悬浮窗管理辅助类 ( 无需权限依赖 Activity )
 * @author Ttt
 */
@Route(path = RouterPath.FloatingWindowManager2Activity_PATH)
class FloatingWindowManager2Activity : BaseActivity<BaseViewRecyclerviewBinding>() {

    override fun baseLayoutId(): Int = R.layout.base_view_recyclerview

    override fun initValue() {
        super.initValue()

        // 初始化布局管理器、适配器
        ButtonAdapter(ButtonList.floatingWindowButtonValues)
            .setItemCallback(object : DevItemClickCallback<ButtonValue>() {
                override fun onItemClick(
                    buttonValue: ButtonValue,
                    param: Int
                ) {
                    when (buttonValue.type) {
                        ButtonValue.BTN_OPEN_FLOATING_WINDOW -> {
                            Utils2.instance.apply {
                                isNeedsAdd = true
                                // 添加悬浮窗 View
                                addFloatingView(this@FloatingWindowManager2Activity)
                            }
                        }
                        ButtonValue.BTN_CLOSE_FLOATING_WINDOW -> {
                            Utils2.instance.apply {
                                isNeedsAdd = false
                                // 移除所有悬浮窗 View
                                removeAllFloatingView()
                            }
                        }
                    }
                }
            }).bindAdapter(binding.vidRv)
    }
}

// ==========
// = 实现代码 =
// ==========

/**
 * detail: 悬浮窗工具类
 * @author Ttt
 */
internal class Utils2 private constructor() : IFloatingOperate {

    companion object {

        val instance: Utils2 by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            Utils2()
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
    }.apply {
        // 默认不添加悬浮处理
        isNeedsAdd = false
    }

    // 悬浮窗触摸辅助类实现
    private val mTouchAssist = DevFloatingTouchIMPL2().apply {
        (floatingEdge as? DevFloatingEdgeIMPL)?.let { edge ->
            edge.setStatusBarHeightMargin()
            edge.setNavigationBarHeightMargin()
        }
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

    /**
     * 创建悬浮 View
     * @param floatingActivity 悬浮窗辅助类接口
     * @return FloatingView
     */
    fun createFloatingView(floatingActivity: IFloatingActivity): FloatingView {
        return FloatingView(floatingActivity.attachActivity, mTouchAssist)
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
            setMargins(mTouchAssist.x, mTouchAssist.y, 0, 0)
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
        ViewUtils.setMargin(view, mTouchAssist.x, mTouchAssist.y, 0, 0)
    }

    override fun isNeedsAdd(): Boolean {
        return mAssist.isNeedsAdd
    }

    override fun setNeedsAdd(needsAdd: Boolean) {
        mAssist.isNeedsAdd = needsAdd
    }
}