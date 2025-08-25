package afkt.project.features.ui_effect.capture_picture

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.databinding.FragmentUiEffectCapturePictureListViewBinding
import dev.base.simple.extensions.asFragment
import dev.utils.app.CapturePictureUtils

/**
 * detail: CapturePictureUtils ListView 截图
 * @author Ttt
 */
class CapturePictureListViewFragment : AppFragment<FragmentUiEffectCapturePictureListViewBinding, CapturePictureListViewModel>(
    R.layout.fragment_ui_effect_capture_picture_list_view, BR.viewModel,
    simple_Agile = { frg ->
        frg.asFragment<CapturePictureListViewFragment> {
            setTitleBarRight("截图") { view ->
                val bitmap = CapturePictureUtils.snapshotByListView(binding.vidLv)
                CapturePictureFragment.saveBitmap("list_view.jpg", bitmap)
            }
        }
    }
)

class CapturePictureListViewModel : CapturePictureRecyclerViewModel()