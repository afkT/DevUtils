package afkt.project

import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.MainFragmentBinding
import afkt.project.model.data.button.convertItemsModule

class MainFragment : AppFragment<MainFragmentBinding, MainViewModel>(
    R.layout.main_fragment, BR.viewModel
)

/**
 * detail: Main Fragment ViewModel
 * @author Ttt
 * 示例项目，避免目录 ViewModel 文件过多，统一将对应的 ViewModel 放到对应 Fragment.kt 中
 */
class MainViewModel : AppViewModel() {

    init {
        buttonAdapterModel.convertItemsModule()
    }
}