package afkt.project.feature

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.data_model.button.ButtonList
import afkt.project.data_model.button.ButtonValue
import afkt.project.data_model.button.RouterPath
import afkt.project.databinding.BaseViewRecyclerviewBinding
import com.therouter.router.Route
import dev.callback.DevItemClickCallback

/**
 * detail: Button 列表 Activity
 * @author Ttt
 */
@Route(path = RouterPath.ButtonItemActivity_PATH)
class ButtonItemActivity : BaseActivity<BaseViewRecyclerviewBinding>() {

    override fun baseLayoutId(): Int = R.layout.base_view_recyclerview

    override fun initValue() {
        super.initValue()
        // 初始化布局管理器、适配器
        ButtonAdapter(ButtonList.getButtonValues(moduleType))
            .setItemCallback(object : DevItemClickCallback<ButtonValue>() {
                override fun onItemClick(
                    buttonValue: ButtonValue,
                    param: Int
                ) {
                    routerActivity(buttonValue)
                }
            }).bindAdapter(binding.vidRv)
        // 注册观察者
        registerAdapterDataObserver(binding.vidRv, true)
    }
}