package afkt.project.ui.dialog

import afkt.project.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dev.utils.app.ResourceUtils
import dev.utils.app.SizeUtils

/**
 * detail: Material BottomSheetDialog
 * @author Ttt
 */
class BottomSheetDialog : BottomSheetDialogFragment() {

    private lateinit var mBehavior: BottomSheetBehavior<FrameLayout>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_bottom_sheet, container, false)
    }

    override fun onStart() {
        super.onStart()

        val mDialog = dialog as? BottomSheetDialog?
        val bottomSheet =
            mDialog?.delegate?.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheet?.apply {
//            val layoutParams = layoutParams as CoordinatorLayout.LayoutParams
//            // 最大高度
//            layoutParams.height = (ScreenUtils.getScreenHeight() * 0.7f).toInt()

            setBackgroundColor(ResourceUtils.getColor(R.color.transparent))

            mBehavior = BottomSheetBehavior.from(this)
            // 默认显示高度
            mBehavior.peekHeight = SizeUtils.dipConvertPx(290F)
            mBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }
}