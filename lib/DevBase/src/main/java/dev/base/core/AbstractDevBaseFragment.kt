package dev.base.core

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import dev.base.core.interfaces.IDevBase
import dev.base.databinding.BaseContentViewBinding
import dev.base.utils.assist.DevBaseAssist
import dev.base.utils.assist.DevBaseContentAssist

/**
 * detail: Fragment 抽象基类
 * @author Ttt
 */
abstract class AbstractDevBaseFragment : Fragment(),
    IDevBase {

    // ==========
    // = Object =
    // ==========

    @JvmField // 日志 TAG ( 根据使用习惯命名大写 )
    protected var TAG = AbstractDevBaseFragment::class.java.simpleName

    @JvmField // DevBase 合并相同代码辅助类
    protected var assist = DevBaseAssist()

    @JvmField // DevBase ContentView 填充辅助类
    protected var contentAssist = DevBaseContentAssist()

    // ==========
    // = 生命周期 =
    // ==========

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // 获取当前类名
        TAG = this.javaClass.simpleName
        // 是否安全处理 addView
        contentAssist.setSafe(isContentAssistSafe())
        // 设置 TAG
        assist.setTag(TAG).printLog("onAttach")
    }

    override fun onDetach() {
        super.onDetach()
        assist.printLog("onDetach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        assist.printLog("onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        assist.printLog("onCreateView")
        // Inflate View 之前触发
        beforeInflateView()
        // DevBase ContentView 填充
        BaseContentViewBinding.inflate(
            layoutInflater
        ).apply {
            // 设置 Binding
            contentAssist.bind(this)
            // Inflate View 之后触发
            afterInflateView(root)
            // 插入布局【具体实际布局】
            insertLayout()
        }
        return contentAssist.bindingRoot()
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        assist.printLog("onViewCreated")
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        assist.printLog("onHiddenChanged - hidden: $hidden")
    }

    @Deprecated("deprecated setUserVisibleHint(Boolean)")
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        assist.printLog("setUserVisibleHint")
    }

    override fun onStart() {
        super.onStart()
        assist.printLog("onStart")
    }

    override fun onResume() {
        super.onResume()
        assist.printLog("onResume")
    }

    override fun onPause() {
        super.onPause()
        assist.printLog("onPause")
    }

    override fun onStop() {
        super.onStop()
        assist.printLog("onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        assist.printLog("onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        assist.printLog("onDestroy")
    }

    // ==================
    // = IDevBaseMethod =
    // ==================

    // ============
    // = 初始化方法 =
    // ============

    override fun initView() {
        assist.printLog("initView")
    }

    override fun initValue() {
        assist.printLog("initValue")
    }

    override fun initListener() {
        assist.printLog("initListener")
    }

    override fun initObserve() {
        assist.printLog("initObserve")
    }

    override fun initOther() {
        assist.printLog("initOther")
    }

    // ==================
    // = IDevBaseLayout =
    // ==================

    @JvmField // Layout View
    protected var layoutView: View? = null

    /**
     * Layout View 初始化处理
     * @param inflater  [LayoutInflater]
     */
    private fun layoutInit(
        inflater: LayoutInflater
    ) {
        if (layoutView != null) return
        // 使用 baseLayoutId()
        if (baseLayoutId() != 0) {
            try {
                layoutView = inflater.inflate(baseLayoutId(), null, false)
            } catch (e: Exception) {
                assist.printLog(e, "layoutInit - baseLayoutId")
            }
        }
        // 如果 View 等于 null, 则使用 baseLayoutView()
        if (layoutView == null) layoutView = baseLayoutView()
    }

    /**
     * 插入布局【具体实际布局】
     */
    private fun insertLayout() {
        // Layout View 初始化处理
        layoutInit(layoutInflater)
        // 添加到 contentLinear
        if (isLayoutMatchParent()) {
            contentAssist.addContentView(
                layoutView,
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
                )
            )
        } else {
            contentAssist.addContentView(layoutView)
        }
    }
}