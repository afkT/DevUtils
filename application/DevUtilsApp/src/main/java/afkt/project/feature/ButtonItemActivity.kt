package afkt.project.feature

import afkt.project.R
import afkt.project.base.project.BaseProjectActivity
import afkt.project.base.app.AppViewModel
import afkt.project.base.project.bindAdapter
import afkt.project.model.data.button.ButtonList
import afkt.project.model.data.button.RouterPath
import afkt.project.databinding.BaseViewRecyclerviewBinding
import com.therouter.router.Route

/**
 * detail: Button 列表 Activity
 * @author Ttt
 */
@Route(path = RouterPath.ButtonItemActivity_PATH)
class ButtonItemActivity : BaseProjectActivity<BaseViewRecyclerviewBinding, AppViewModel>(
    R.layout.base_view_recyclerview, simple_Agile = {
        if (it is ButtonItemActivity) {
            it.apply {
                binding.vidRv.bindAdapter(ButtonList.getButtonValues(it.moduleType))
                // 注册观察者
                registerAdapterDataObserver(binding.vidRv, true)
            }
        }
    }
)