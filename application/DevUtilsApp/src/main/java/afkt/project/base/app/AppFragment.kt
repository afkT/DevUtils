package afkt.project.base.app

import afkt.project.base.BaseFragment
import afkt.project.databinding.BaseTitleBarBinding
import afkt.project.model.adapter.ButtonAdapterModel
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar
import dev.simple.app.base.FragmentVMType
import dev.simple.app.base.interfaces.BindingFragmentView
import dev.simple.app.controller.ui.theme.FragmentUITheme
import dev.utils.common.StringUtils
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

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

    /**
     * 创建 TitleBar 骨架 View
     */
    override fun createTitleBarSkeletonView(
        context: Context,
        inflater: LayoutInflater,
        owner: LifecycleOwner
    ): View? {
        return BaseTitleBarBinding.inflate(inflater).apply {
            // 添加状态栏边距
            setOnApplyWindowInsetsListener(this.root, false)
            // 设置标题
            vidTitle.setTitle(viewModel.intentData.getTitle())
                .setOnTitleBarListener(object : OnTitleBarListener {
                    override fun onLeftClick(titleBar: TitleBar) {
                        findNavController().popBackStack()
                    }
                })
        }.root
    }

    // ============
    // = override =
    // ============

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        viewModel.intentReader(arguments)
        super.onViewCreated(view, savedInstanceState)
        // 添加 TitleBar 则不设置顶部状态栏边距
        if (isAddTitleBar()) {
            setStatusBarHeightPadding(binding.root)
        } else {
            // 默认设置顶部状态栏、底部导航栏边距
            setOnApplyWindowInsetsListener(binding.root)
        }
    }

    override fun isAddTitleBar(): Boolean {
        // 存在标题才添加 TitleBar
        return StringUtils.isNotEmpty(viewModel.intentData.getTitle())
    }

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * 给 view 设置 insets, 使得 view 不会被 system bars 遮挡
     * @param view [View]
     * @param paddingBottom 是否设置 Padding Bottom
     */
    private fun setOnApplyWindowInsetsListener(
        view: View,
        paddingBottom: Boolean = true
    ) {
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val bottom = if (paddingBottom) systemBars.bottom else 0
            v.setPadding(
                systemBars.left, systemBars.top,
                systemBars.right, bottom
            )
            insets
        }
    }

    /**
     * 设置状态栏高度边距
     * @param view [View]
     * @param paddingTop 是否设置 Padding Top
     */
    private fun setStatusBarHeightPadding(
        view: View,
        paddingTop: Boolean = false
    ) {
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val top = if (paddingTop) systemBars.top else 0
            v.setPadding(
                systemBars.left, top,
                systemBars.right, systemBars.bottom
            )
            insets
        }
    }
}

/**
 * 检查当前 Fragment 是否为指定类型，如果是则执行回调
 * @param T 目标 Fragment 类型，必须是 AppFragment 的子类
 * @param action 当 Fragment 匹配类型时执行的回调
 */
@OptIn(ExperimentalContracts::class)
inline fun <reified T : AppFragment<*, *>> Any?.asFragment(
    action: T.() -> Unit
) {
    contract {
        returns() implies (this@asFragment is T)
    }
    // 当 Fragment 非空且是目标类型时执行回调
    (this as? T)?.apply(action)
}

/**
 * 简化检查并操作 buttonAdapterModel
 * @param T 目标 Fragment 类型 ( 必须包含 buttonAdapterModel 属性 )
 * @param action 在 buttonAdapterModel 上执行的操作
 */
@OptIn(ExperimentalContracts::class)
inline fun <reified T : AppFragment<*, *>> Any?.applyToButtonAdapter(
    action: ButtonAdapterModel.() -> Unit
) {
    contract {
        returns() implies (this@applyToButtonAdapter is T)
    }
    // 当对象是目标 Fragment 类型时，获取 buttonAdapterModel 并执行操作
    (this as? T)?.viewModel?.buttonAdapterModel?.apply(action)
}