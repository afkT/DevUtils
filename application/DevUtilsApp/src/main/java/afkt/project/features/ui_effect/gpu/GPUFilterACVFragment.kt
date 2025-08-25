package afkt.project.features.ui_effect.gpu

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.databinding.FragmentUiEffectGpuFilterBinding
import dev.base.simple.extensions.asFragment

class GPUFilterACVFragment : AppFragment<FragmentUiEffectGpuFilterBinding, GPUFilterViewModel>(
    R.layout.fragment_ui_effect_gpu_filter, BR.viewModel,
    simple_Agile = { frg ->
        frg.asFragment<GPUFilterACVFragment> {
            viewModel.initializeRecyclerView(binding.vidRv)
            viewModel.adapterModel.addAllAndClear(GPUFilterItem.ACV_LISTS)
            // 初始化相册选择图片处理
            viewModel.initializeImageSelect(this)
        }
    }
)