package afkt.project.ui.activity

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.base.config.RouterPath
import afkt.project.databinding.BaseViewRecyclerviewBinding
import afkt.project.model.item.ButtonList.moduleDevAssistEngineButtonValues
import afkt.project.model.item.ButtonValue
import afkt.project.ui.adapter.ButtonAdapter
import android.graphics.Color
import com.alibaba.android.arouter.facade.annotation.Route
import dev.DevUtils
import dev.base.widget.BaseTextView
import dev.callback.DevItemClickCallback
import dev.engine.DevEngine
import dev.engine.log.DevLogEngine
import dev.engine.log.DevLoggerEngineImpl
import dev.utils.DevFinal
import dev.utils.app.ResourceUtils
import dev.utils.app.SnackbarUtils
import dev.utils.app.SpanUtils
import dev.utils.app.helper.quick.QuickHelper

/**
 * detail: DevAssist Engine 实现
 * @author Ttt
 */
@Route(path = RouterPath.DevAssistEngineActivity_PATH)
class DevAssistEngineActivity : BaseActivity<BaseViewRecyclerviewBinding>() {

    override fun baseLayoutId(): Int = R.layout.base_view_recyclerview

    override fun initValue() {
        super.initValue()

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

        val view = QuickHelper.get(BaseTextView(this))
            .setText(span.create())
            .setTextColors(ResourceUtils.getColor(R.color.black))
            .setTextSizeBySp(15.0f)
            .setLineSpacingAndMultiplier(15.0f, 1.1f)
            .setPadding(ResourceUtils.getDimensionInt(R.dimen.un_dp_20))
            .setPaddingBottom(ResourceUtils.getDimensionInt(R.dimen.un_dp_10), false)
            .getView<BaseTextView>()
        // 添加 View
        contentAssist.addContentView(view = view, index = 0)

        // 初始化布局管理器、适配器
        ButtonAdapter(moduleDevAssistEngineButtonValues)
            .setItemCallback(object : DevItemClickCallback<ButtonValue>() {
                override fun onItemClick(
                    buttonValue: ButtonValue,
                    param: Int
                ) {
                    val builder = StringBuilder()
                        .append("Java 实现在 DevOther Module java.dev.engine 目录下")
                        .append(DevFinal.SYMBOL.NEW_LINE)
                        .append("Kotlin 实现已封装为 DevEngine 库")
                    // 进行显示
                    SnackbarUtils.with(mActivity).also { sn ->
                        // 设置多行显示
                        sn.style.textMaxLines = Int.MAX_VALUE
                        // 显示 Snackbar
                        sn.setAction({ sn.dismiss() }, "我知道了")
                            .showIndefinite(builder.toString())
                    }
                }
            }).bindAdapter(binding.vidRv)

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
        DevEngine.getLog()?.dTag(TAG, "Log Engine 方法调用")

        // =========================
        // = 同类库多 Engine 混合使用 =
        // =========================

        val KEY = "QPQPQPQP"
        // 通过设置 KEY 区分不同 Engine 实现类
        DevLogEngine.setEngine(
            KEY, object : DevLoggerEngineImpl() {
                override fun isPrintLog(): Boolean {
                    return DevUtils.isDebug()
                }
            }
        )
        DevLogEngine.getEngine(KEY)?.dTag(TAG, "多 Log Engine 方法调用")
    }
}