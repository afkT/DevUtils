package afkt.project.base.app

import afkt.project.base.BaseFragment
import afkt.project.databinding.BaseTitleBarBinding
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
        // 不添加 TitleBar 则添加 Top Padding
        if (!isAddTitleBar()) {
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
}