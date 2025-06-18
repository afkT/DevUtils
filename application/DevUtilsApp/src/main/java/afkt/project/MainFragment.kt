package afkt.project

import afkt.project.base.app.AppFragment
import afkt.project.base.app.AppViewModel
import afkt.project.base.project.routerActivity
import afkt.project.databinding.MainFragmentBinding
import afkt.project.model.adapter.ButtonAdapterModel
import afkt.project.model.data.button.ButtonList

class MainFragment : AppFragment<MainFragmentBinding, MainViewModel>(
    R.layout.main_fragment, BR.viewModel
) {
    override fun initValue() {
        viewModel.buttonAdapterModel.addAllAndClear(ButtonList.mainButtonValues)
    }
}

/**
 * detail: Main Fragment ViewModel
 * @author Ttt
 * 示例项目，避免目录 ViewModel 文件过多，统一将对应的 ViewModel 放到对应 Fragment.kt 中
 */
class MainViewModel : AppViewModel() {

    // Button Adapter Model
    val buttonAdapterModel = ButtonAdapterModel {
        it.routerActivity()
    }
}