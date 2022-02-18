package afkt.project.feature.ui_effect

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.base.config.RouterPath
import afkt.project.ui.dialog.BottomSheetDialog
import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.facade.annotation.Route

/**
 * detail: Material BottomSheetDialog
 * @author Ttt
 */
@Route(path = RouterPath.UI_EFFECT.BottomSheetDialogActivity_PATH)
class BottomSheetDialogActivity : BaseActivity<ViewBinding>() {

    override fun isViewBinding(): Boolean = false

    override fun baseLayoutId(): Int = R.layout.activity_bottom_sheet_dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        BottomSheetDialog().show(supportFragmentManager, "BottomSheetDialog")
    }
}