package afkt.project.features

import afkt.project.BR
import afkt.project.R
import afkt.project.base.app.AppFragment
import afkt.project.base.app.AppViewModel
import afkt.project.databinding.FragmentLibUtilsBinding
import afkt.project.model.adapter.ButtonAdapterModel
import afkt.project.model.data.button.ButtonEnum
import afkt.project.model.data.button.convertItemsLib
import dev.utils.app.toast.ToastTintUtils

class LibUtilsFragment : AppFragment<FragmentLibUtilsBinding, LibUtilsViewModel>(
    R.layout.fragment_lib_utils, BR.viewModel
)

class LibUtilsViewModel : AppViewModel() {

    // Button Adapter Model
    val buttonAdapterModel = ButtonAdapterModel {
        when (it) {
            ButtonEnum.BTN_LIB_ROOM, ButtonEnum.BTN_LIB_GREEN_DAO -> ToastTintUtils.info(
                "具体查看: 【DevUtils-repo】application/AppDB"
            )

            else -> ToastTintUtils.info(
                "具体搜索: 【DevUtils-repo】lib/LocalModules/DevOther ${it.text}"
            )
        }
    }.apply {
        convertItemsLib()
    }
}