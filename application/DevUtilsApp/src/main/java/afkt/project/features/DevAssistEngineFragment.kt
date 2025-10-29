package afkt.project.features

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentRecyclerViewBinding
import afkt.project.model.button.convertItemsDevAssistEngineMain
import android.graphics.Color
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import dev.DevUtils
import dev.base.simple.extensions.asFragment
import dev.engine.DevEngine
import dev.engine.core.log.DevLoggerEngineImpl
import dev.engine.extensions.log.log_dTag
import dev.engine.extensions.toast.toast_showLong
import dev.engine.log.DevLogEngine
import dev.utils.DevFinal
import dev.utils.app.ResourceUtils
import dev.utils.app.SpanUtils
import dev.utils.app.helper.quick.QuickHelper

class DevAssistEngineFragment : AppFragment<FragmentRecyclerViewBinding, AppViewModel>(
    R.layout.fragment_recycler_view, BR.viewModel,
    simple_Agile = { frg ->
        frg.asFragment<DevAssistEngineFragment> {
            // 初始化数据并设置点击事件
            viewModel.buttonAdapterModel.convertItemsDevAssistEngineMain { btn ->
                val builder = StringBuilder()
                    .append("实现已封装为 DevEngine 库，直接依赖使用")
                toast_showLong(text = builder.toString())
            }
            // 追加 TextView
            contentAssist.addContentView(createTextView(), 0)
        }
    }
)

private fun DevAssistEngineFragment.createTextView(): View {
    val span = SpanUtils().apply {
        append("DevAssist Engine 主要为了解决项目代码中")
        appendLine("对第三方框架强依赖使用、以及部分功能版本适配。")
        setBold().setForegroundColor(Color.RED)
        append("通过实现对应功能模块 Engine 接口, 实现对应的方法功能, ")
        append("对外无需关注实现代码, 直接通过 DevXXXEngine 进行调用, ")
        setBold().setForegroundColor(Color.BLUE)
        append("实现对第三方框架解耦、一键替换第三方库、同类库多 Engine 混合使用、")
        setBold().setForegroundColor(Color.RED)
        append("以及部分功能适配 ( 如外部文件存储 MediaStore 全局适配 ) 等")
        setBold().setForegroundColor(Color.RED)
    }
    return QuickHelper.get(AppCompatTextView(requireContext()))
        .setText(span.create())
        .setTextColors(Color.BLACK)
        .setTextSizeBySp(15.0F)
        .setLineSpacingAndMultiplier(15.0F, 1.1F)
        .setPadding(ResourceUtils.getDimensionInt(R.dimen.dp_20))
        .setPaddingBottom(ResourceUtils.getDimensionInt(R.dimen.dp_10), false)
        .getView<AppCompatTextView>()
}

/**
 * 模拟使用演示
 */
private fun use() {

    // 支持不同模块使用独立的 Engine
    val TAG = "LoginModule"

    // =================
    // = 模拟初始化、使用 =
    // =================

    // 设置 LogEngine 实现类
    DevLogEngine.setEngine(object : DevLoggerEngineImpl() {
        override fun isPrintLog(): Boolean {
            return DevUtils.isDebug()
        }
    })
    // 进行使用
    DevEngine.getLog()?.dTag(
        TAG, "Log Engine 方法调用"
    )

    TAG.log_dTag(
        message = "Log Engine 方法调用"
    )

    // =========================
    // = 同类库多 Engine 混合使用 =
    // =========================

    // 首页模块 Key
    val KEY = "MainModule"

    // 通过设置 KEY 区分不同 Engine 实现类
    DevLogEngine.setEngine(
        KEY, object : DevLoggerEngineImpl() {
            override fun isPrintLog(): Boolean {
                return DevUtils.isDebug()
            }
        }
    )
    DevLogEngine.getEngine(KEY)?.dTag(
        TAG, "多 Log Engine 方法调用"
    )

    TAG.log_dTag(
        engine = KEY,
        message = "多 Log Engine 方法调用"
    )
}