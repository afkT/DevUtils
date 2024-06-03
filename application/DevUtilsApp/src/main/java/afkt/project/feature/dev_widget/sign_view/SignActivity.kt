package afkt.project.feature.dev_widget.sign_view

import afkt.project.base.project.BaseProjectActivity
import afkt.project.base.project.BaseProjectViewModel
import afkt.project.data_model.button.RouterPath
import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.therouter.router.Route
import dev.simple.app.base.inter.BindingActivityView
import dev.widget.function.SignView

/**
 * detail: 签名 View
 * @author Ttt
 */
@Route(path = RouterPath.DEV_WIDGET.SignActivity_PATH)
class SignActivity : BaseProjectActivity<ViewDataBinding, BaseProjectViewModel>(
    object : BindingActivityView {
        override fun bind(value: AppCompatActivity, inflater: LayoutInflater): View {
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