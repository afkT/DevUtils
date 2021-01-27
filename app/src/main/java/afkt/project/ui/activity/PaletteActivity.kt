package afkt.project.ui.activity

import afkt.project.R
import afkt.project.base.app.BaseActivity
import android.os.Bundle
import androidx.viewbinding.ViewBinding

/**
 * detail: Palette 调色板
 * @author Ttt
 */
class PaletteActivity : BaseActivity<ViewBinding>() {

    override fun isViewBinding(): Boolean = false

    override fun baseLayoutId(): Int = R.layout.activity_palette

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}