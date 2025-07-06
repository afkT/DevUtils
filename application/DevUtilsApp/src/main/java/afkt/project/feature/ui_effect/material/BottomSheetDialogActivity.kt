package afkt.project.feature.ui_effect.material

import afkt.project.R
import afkt.project.app.AppViewModel
import afkt.project.app.project.BaseProjectActivity
import afkt.project.databinding.ActivityBottomSheetDialogBinding
import afkt.project.model.data.button.RouterPath
import com.therouter.router.Route

/**
 * detail: Material BottomSheetDialog
 * @author Ttt
 */
@Route(path = RouterPath.UI_EFFECT.BottomSheetDialogActivity_PATH)
class BottomSheetDialogActivity :
    BaseProjectActivity<ActivityBottomSheetDialogBinding, AppViewModel>(
        R.layout.activity_bottom_sheet_dialog, simple_Agile = {
            if (it is BottomSheetDialogActivity) {
                it.apply {
                    BottomSheetDialog().show(supportFragmentManager, "BottomSheetDialog")
                }
            }
        }
    )