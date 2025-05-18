package afkt.environment.use

import afkt.environment.use.base.BaseFragment
import afkt.environment.use.databinding.FragmentCustomBinding
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar
import dev.simple.app.base.FragmentVMType

class CustomFragment : BaseFragment<FragmentCustomBinding, AppViewModel>(
    R.layout.fragment_custom, BR.viewModel,
    FragmentVMType.ACTIVITY, simple_Agile = { frg ->
        if (frg is CustomFragment) {
            frg.apply {
                binding.vidTitle.setOnTitleBarListener(object : OnTitleBarListener {
                    override fun onLeftClick(titleBar: TitleBar) {
                        viewModel.postBackPressed()
                    }
                })
            }
        }
    }
) {
    override fun onResume() {
        super.onResume()
        // 转换 Item 数据
        viewModel.customAdapterModel.convertItems()
    }
}