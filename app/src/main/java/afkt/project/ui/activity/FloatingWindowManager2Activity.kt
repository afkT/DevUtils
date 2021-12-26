package afkt.project.ui.activity

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.base.config.RouterPath
import afkt.project.databinding.BaseViewRecyclerviewBinding
import afkt.project.model.item.ButtonList
import afkt.project.model.item.ButtonValue
import afkt.project.ui.adapter.ButtonAdapter
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.alibaba.android.arouter.facade.annotation.Route
import dev.callback.DevItemClickCallback
import dev.utils.app.ViewUtils
import dev.utils.app.assist.floating.*

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
            }).bindAdapter(binding.vidBvrRecy)
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
    private val mAssist = FloatingWindowManagerAssist2().apply {
        // 默认不添加悬浮处理
        isNeedsAdd = false
    }

    private var x: Int = 0
    private var y: Int = 0

    // 悬浮窗触摸辅助类实现
    private val mTouchAssist: IFloatingTouch by lazy {
        object : DevFloatingTouchIMPL() {
            override fun updateViewLayout(
                view: View?,
                dx: Int,
                dy: Int
            ) {
                x += dx
                y += dy
                ViewUtils.setMargin(
                    view, x, y, 0, 0
                )
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
            setMargins(x, y, 0, 0)
        }
    }

    // ====================
    // = IFloatingOperate =
    // ====================

    override fun removeFloatingView(floatingActivity: IFloatingActivity?): Boolean {
        return mAssist.removeFloatingView(floatingActivity)
    }

    override fun addFloatingView(floatingActivity: IFloatingActivity?): Boolean {
        return mAssist.addFloatingView(floatingActivity)
    }

    override fun removeAllFloatingView() {
        mAssist.removeAllFloatingView()
    }

    override fun isNeedsAdd(): Boolean {
        return mAssist.isNeedsAdd
    }

    override fun setNeedsAdd(needsAdd: Boolean) {
        mAssist.isNeedsAdd = needsAdd
    }
}