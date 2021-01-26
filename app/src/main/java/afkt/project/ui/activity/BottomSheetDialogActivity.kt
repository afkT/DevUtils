package afkt.project.ui.activity

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.ui.dialog.BottomSheetDialog
import android.os.Bundle
import androidx.viewbinding.ViewBinding

/**
 * detail: Material BottomSheetDialog
 * @author Ttt
 */
class BottomSheetDialogActivity : BaseActivity<ViewBinding>() {

    override fun isViewBinding(): Boolean = false

    override fun baseLayoutId(): Int = R.layout.activity_bottom_sheet_dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        BottomSheetDialog().show(supportFragmentManager, "BottomSheetDialog")
    }
}