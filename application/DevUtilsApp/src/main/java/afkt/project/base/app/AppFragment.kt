package afkt.project.base.app

import afkt.project.R
import afkt.project.base.BaseFragment
import afkt.project.databinding.BaseToolbarBinding
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import dev.simple.app.base.FragmentVMType
import dev.simple.app.base.interfaces.BindingFragmentView
import dev.simple.app.controller.ui.theme.FragmentUITheme
import dev.utils.app.ViewUtils

/**
 * detail: Base App Fragment
 * @author Ttt
 */
open class AppFragment<VDB : ViewDataBinding, VM : AppViewModel> :
    BaseFragment<VDB, VM> {

    // ==========
    // = 构造函数 =
    // ==========

    constructor(
        bindLayoutId: Int,
        bindViewModelId: Int = -1,
        vmType: FragmentVMType = FragmentVMType.FRAGMENT,
        simple_Init: ((Any) -> Unit)? = null,
        simple_Start: ((Any) -> Unit)? = null,
        simple_PreLoad: ((Any) -> Unit)? = null,
        simple_Agile: ((Any) -> Unit)? = null,
        simple_UITheme: ((FragmentUITheme) -> FragmentUITheme)? = null
    ) : super(
        bindLayoutId, bindViewModelId, vmType,
        simple_Init, simple_Start, simple_PreLoad, simple_Agile, simple_UITheme
    )

    constructor(
        bindLayoutView: BindingFragmentView,
        bindViewModelId: Int = -1,
        vmType: FragmentVMType = FragmentVMType.FRAGMENT,
        simple_Init: ((Any) -> Unit)? = null,
        simple_Start: ((Any) -> Unit)? = null,
        simple_PreLoad: ((Any) -> Unit)? = null,
        simple_Agile: ((Any) -> Unit)? = null,
        simple_UITheme: ((FragmentUITheme) -> FragmentUITheme)? = null
    ) : super(
        bindLayoutView, bindViewModelId, vmType,
        simple_Init, simple_Start, simple_PreLoad, simple_Agile, simple_UITheme
    )

    // =================
    // = Skeleton View =
    // =================

//    /**
//     * 创建 TitleBar 骨架 View
//     */
//    override fun createTitleBarSkeletonView(
//        context: Context,
//        inflater: LayoutInflater,
//        owner: LifecycleOwner
//    ): View? {
//        return BaseToolbarBinding.inflate(inflater).apply {
//        }.root
//        return ViewUtils.inflate(this, R.layout.base_toolbar, null)?.let { titleView ->
//            toolbar = titleView.findViewById(R.id.vid_tb)
//            setSupportActionBar(toolbar)
//            supportActionBar?.let {
//                // 给左上角图标的左边加上一个返回的图标
//                it.setDisplayHomeAsUpEnabled(true)
//                // 对应 ActionBar.DISPLAY_SHOW_TITLE
//                it.setDisplayShowTitleEnabled(false)
//            }
//            // 设置点击事件
//            toolbar?.setNavigationOnClickListener { finish() }
//            // 设置 ToolBar 标题
//            toolbar?.title = moduleTitle
//            return titleView
//        }
//    }
}