package afkt.project.ui.activity

import afkt.project.base.app.BaseActivity
import android.graphics.Color
import android.graphics.Paint
import android.view.View
import android.widget.LinearLayout
import androidx.viewbinding.ViewBinding
import dev.widget.function.SignView

/**
 * detail: 签名 View
 * @author Ttt
 */
class SignActivity : BaseActivity<ViewBinding>() {

    override fun isViewBinding(): Boolean = false

    override fun baseLayoutId(): Int = 0

    override fun baseLayoutView(): View {
        val signView = SignView(this)
        signView.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )

        // 设置画笔
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 30f
        paint.color = Color.BLACK
        signView.paint = paint
        return signView
    }
}