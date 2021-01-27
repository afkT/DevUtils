package afkt.project.ui.activity

import afkt.project.R
import afkt.project.base.app.BaseActivity
import android.os.Bundle
import androidx.viewbinding.ViewBinding

/**
 * detail: Palette 调色板
 * @author Ttt
 * Android Palette 基本使用
 * @see https://www.jianshu.com/p/d3c13eb700a4
 * Android Material Design 系列之 Palette 开发详解
 * @see https://blog.csdn.net/jaynm/article/details/107076754
 */
class PaletteActivity : BaseActivity<ViewBinding>() {

    override fun isViewBinding(): Boolean = false

    override fun baseLayoutId(): Int = R.layout.activity_palette

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}