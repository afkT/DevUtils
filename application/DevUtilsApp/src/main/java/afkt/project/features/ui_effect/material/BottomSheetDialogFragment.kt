package afkt.project.features.ui_effect.material

import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentUiEffectMaterialBottomSheetDialogBinding
import dev.base.simple.extensions.asFragment

/**
 * detail: Material BottomSheetDialog
 * @author Ttt
 */
class BottomSheetDialogFragment : AppFragment<FragmentUiEffectMaterialBottomSheetDialogBinding, AppViewModel>(
    R.layout.fragment_ui_effect_material_bottom_sheet_dialog, simple_Agile = { frg ->
        frg.asFragment<BottomSheetDialogFragment> {
            BottomSheetDialog().show(childFragmentManager, "BottomSheetDialog")
        }
    }
)