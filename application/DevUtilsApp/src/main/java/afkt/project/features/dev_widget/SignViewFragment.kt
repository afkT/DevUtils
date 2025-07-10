package afkt.project.features.dev_widget

import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import dev.simple.app.base.interfaces.BindingFragmentView
import dev.widget.function.SignView

/**
 * detail: 签名 View
 * @author Ttt
 */
class SignViewFragment : AppFragment<ViewDataBinding, AppViewModel>(
    object : BindingFragmentView {
        override fun bind(
            value: Fragment,
            inflater: LayoutInflater
        ): View {
            val signView = SignView(value.context)
            signView.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )

            // 设置画笔
            val paint = Paint(Paint.ANTI_ALIAS_FLAG)
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 30F
            paint.color = Color.BLACK
            signView.paint = paint
            return signView
        }
    }
) {
    override fun isViewBinding(): Boolean {
        return false
    }
}