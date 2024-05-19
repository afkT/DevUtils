package afkt.project

import afkt.project.base.project.BaseProjectActivity
import afkt.project.base.project.BaseProjectViewModel
import afkt.project.base.project.ext.routerActivity
import afkt.project.data_model.button.ButtonList
import afkt.project.data_model.button.ButtonValue
import afkt.project.databinding.ActivityMainBinding
import afkt.project.feature.ButtonAdapter
import dev.callback.DevItemClickCallback

class MainActivity : BaseProjectActivity<ActivityMainBinding, BaseProjectViewModel>(
    R.layout.activity_main, -1, simple_Agile = {
        if (it is MainActivity) {
            // 初始化布局管理器、适配器
            ButtonAdapter(ButtonList.mainButtonValues)
                .setItemCallback(object : DevItemClickCallback<ButtonValue>() {
                    override fun onItemClick(
                        buttonValue: ButtonValue,
                        param: Int
                    ) {
                        buttonValue.routerActivity()
                    }
                }).bindAdapter(it.binding.vidInclude.vidRv)
        }
    }, simple_UITheme = {
        it.setAddTitleBar(false)
    }
)