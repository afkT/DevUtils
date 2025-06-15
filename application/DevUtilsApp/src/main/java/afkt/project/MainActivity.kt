package afkt.project

import afkt.project.base.project.BaseProjectActivity
import afkt.project.base.app.AppViewModel
import afkt.project.base.project.bindAdapter
import afkt.project.model.data.button.ButtonList
import afkt.project.databinding.ActivityMainBinding

class MainActivity : BaseProjectActivity<ActivityMainBinding, AppViewModel>(
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