package afkt.project.feature.ui_effect.status_bar

import afkt.project.R
import afkt.project.base.project.BaseProjectActivity
import afkt.project.base.project.BaseProjectViewModel
import afkt.project.data_model.button.RouterPath
import afkt.project.databinding.ActivityStatusBarBinding
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.therouter.router.Route
import dev.utils.app.BarUtils
import dev.utils.app.ResourceUtils
import dev.utils.app.assist.WindowAssist
import dev.utils.app.helper.quick.QuickHelper

/**
 * detail: 点击 显示/隐藏 ( 状态栏 )
 * @author Ttt
 */
@Route(path = RouterPath.UI_EFFECT.StatusBarActivity_PATH)
class StatusBarActivity : BaseProjectActivity<ActivityStatusBarBinding, BaseProjectViewModel>(
    R.layout.activity_status_bar, simple_Agile = {
        if (it is StatusBarActivity) {
            it.apply {
                // 判断是否显示
                var display = true

                val assist = WindowAssist.get(it)

                // 想要实现点击, 显示状态栏图标, 再次点击切换不显示, 并且整体不会上下移动
                // 需要先设置 Activity  Theme => android:Theme.Light.NoTitleBar
                // 第二就是 Activity 最外层布局 view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)

                View(this).let { statusView ->
                    // 设置状态栏 View 高度
                    val layoutParams = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, BarUtils.getStatusBarHeight()
                    )
                    statusView.setBackgroundColor(ResourceUtils.getColor(R.color.colorPrimary))
                    statusView.layoutParams = layoutParams
                    contentAssist.addStatusBarView(statusView)
                }
                // 设置全屏显示, 但是会被状态栏覆盖
                contentAssist.rootLinear?.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // 设置是否显示
                binding.vidToggleBtn.setOnClickListener {
                    display = !display
                    if (display) {
                        assist.clearFlagFullScreen()
                    } else {
                        assist.setFlagFullScreen()
                    }
                    QuickHelper.get(contentAssist.statusBarLinear)
                        .setVisibilitys(display)
                }
            }
        }
    }
)