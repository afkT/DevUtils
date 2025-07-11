package afkt.project.features.dev_widget

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentDevWidgetSignViewBinding
import android.graphics.Color
import android.graphics.Paint
import dev.simple.app.base.asFragment

/**
 * detail: 签名 View
 * @author Ttt
 */
class SignViewFragment : AppFragment<FragmentDevWidgetSignViewBinding, AppViewModel>(
    R.layout.fragment_dev_widget_sign_view, BR.viewModel, simple_Agile = { frg ->
        frg.asFragment<SignViewFragment> {
            // 设置画笔
            val paint = Paint(Paint.ANTI_ALIAS_FLAG)
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 30F
            paint.color = Color.BLACK
            binding.vidSign.paint = paint
        }
    }
)