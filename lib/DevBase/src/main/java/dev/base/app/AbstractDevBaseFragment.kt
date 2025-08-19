package dev.base.app

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dev.base.able.IDevBase
import dev.base.utils.assist.DevBaseAssist

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

    @JvmField // Content View
    protected var mContentView: View? = null

    @JvmField // DevBase 合并相同代码辅助类
    protected var assist = DevBaseAssist()

    // ==========
    // = 生命周期 =
    // ==========

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // 获取当前类名
        TAG = this.javaClass.simpleName
        // 设置数据
        assist.setTag(TAG)
            .printLog("onAttach")
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

        if (mContentView != null) {
            // ViewUtils.removeSelfFromParent(mContentView)
            val parent = mContentView!!.parent as? ViewGroup
            // 删除已经在显示的 View 防止切回来不加载一片空白
            parent?.removeView(mContentView)
            mContentView = null
        }
        // Content View 初始化处理
        contentInit(inflater, container)
        // Inflate View 之后触发
        mContentView?.let { afterInflateView(it) }
        return mContentView
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

    // ===================
    // = IDevBaseContent =
    // ===================

    /**
     * Content View 初始化处理
     * @param inflater  [LayoutInflater]
     * @param container [ViewGroup]
     */
    private fun contentInit(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) {
        if (mContentView != null) return
        // 使用 baseContentId()
        if (baseContentId() != 0) {
            try {
                mContentView = inflater.inflate(baseContentId(), container, false)
            } catch (e: Exception) {
                assist.printLog(e, "contentInit - baseContentId")
            }
        }
        // 如果 View 等于 null, 则使用 baseContentView()
        if (mContentView == null) mContentView = baseContentView()
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
}