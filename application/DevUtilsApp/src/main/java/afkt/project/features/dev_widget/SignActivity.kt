package afkt.project.features.dev_widget

import afkt.project.app.AppViewModel
import afkt.project.app.project.BaseProjectActivity
import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import dev.simple.app.base.interfaces.BindingActivityView
import dev.widget.function.SignView

/**
 * detail: 签名 View
 * @author Ttt
 */
class SignActivity : BaseProjectActivity<ViewDataBinding, AppViewModel>(
    object : BindingActivityView {
        override fun bind(
            value: AppCompatActivity,
            inflater: LayoutInflater
        ): View {
            val signView = SignView(value)
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