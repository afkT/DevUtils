package afkt.project.features.dev_engine

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentDevEngineAnalyticsBinding
import android.view.View
import dev.engine.analytics.IAnalyticsEngine
import dev.engine.extensions.analytics.analytics_register
import dev.engine.extensions.analytics.analytics_track
import dev.engine.extensions.analytics.analytics_unregister
import dev.engine.extensions.toast.toast_showShort

/**
 * detail: Analytics Engine 数据统计 ( 埋点 )
 * @author Ttt
 * Analytics Engine 默认未配置实现, 需依赖具体统计平台后注册实现
 */
class AnalyticsFragment : AppFragment<FragmentDevEngineAnalyticsBinding, AnalyticsViewModel>(
    R.layout.fragment_dev_engine_analytics, BR.viewModel
)

/**
 * detail: Analytics Engine 数据统计 ( 埋点 ) ViewModel
 * @author Ttt
 */
class AnalyticsViewModel : AppViewModel() {

    val clickTrack = View.OnClickListener {
        val item = object : IAnalyticsEngine.EngineItem {}
        val result = item.analytics_track()
        toast_showShort(text = "track 埋点: $result ( 需配置统计平台实现 )")
    }

    val clickRegister = View.OnClickListener { view ->
        view.context.analytics_register<IAnalyticsEngine.EngineConfig>(config = null)
        toast_showShort(text = "register 已调用")
    }

    val clickUnregister = View.OnClickListener { view ->
        view.context.analytics_unregister<IAnalyticsEngine.EngineConfig>(config = null)
        toast_showShort(text = "unregister 已调用")
    }
}