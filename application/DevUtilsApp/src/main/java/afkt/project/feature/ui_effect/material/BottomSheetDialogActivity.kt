package afkt.project.feature.ui_effect.material

import afkt.project.R
import afkt.project.base.project.BaseProjectActivity
import afkt.project.base.project.BaseProjectViewModel
import afkt.project.data_model.button.RouterPath
import afkt.project.databinding.ActivityBottomSheetDialogBinding
import com.therouter.router.Route

/**
 * detail: Material BottomSheetDialog
 * @author Ttt
 */
@Route(path = RouterPath.UI_EFFECT.BottomSheetDialogActivity_PATH)
class BottomSheetDialogActivity :
    BaseProjectActivity<ActivityBottomSheetDialogBinding, BaseProjectViewModel>(
        R.layout.activity_bottom_sheet_dialog, simple_Agile = {
            if (it is BottomSheetDialogActivity) {
                it.apply {
                    BottomSheetDialog().show(supportFragmentManager, "BottomSheetDialog")
                }
            }
        }
    )