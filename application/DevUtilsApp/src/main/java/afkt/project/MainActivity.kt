package afkt.project

import afkt.project.base.project.BaseProjectActivity
import afkt.project.base.project.BaseProjectViewModel
import afkt.project.base.project.ext.bindAdapter
import afkt.project.data_model.button.ButtonList
import afkt.project.databinding.ActivityMainBinding

class MainActivity : BaseProjectActivity<ActivityMainBinding, BaseProjectViewModel>(
    R.layout.activity_main, simple_Agile = {
        if (it is MainActivity) {
            it.binding.vidInclude.vidRv.bindAdapter(
                ButtonList.mainButtonValues
            )
        }
    }, simple_UITheme = {
        it.setAddTitleBar(false)
    }
)