package afkt.project.features

import afkt.project.BR
import afkt.project.R
import afkt.project.base.app.AppFragment
import afkt.project.base.app.AppViewModel
import afkt.project.base.appViewModel
import afkt.project.databinding.FragmentLibUtilsBinding
import afkt.project.model.adapter.ButtonAdapterModel
import afkt.project.model.data.button.convertItemsLib
import afkt.project.model.data.button.fragmentId

class LibUtilsFragment : AppFragment<FragmentLibUtilsBinding, LibUtilsViewModel>(
    R.layout.fragment_lib_utils, BR.viewModel
)

class LibUtilsViewModel : AppViewModel() {

    // Button Adapter Model
    val buttonAdapterModel = ButtonAdapterModel {
        appViewModel().navigate(it.fragmentId())
    }.apply {
        convertItemsLib()
    }
}