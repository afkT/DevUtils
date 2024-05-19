package afkt.project.feature.dev_engine

import afkt.project.R
import afkt.project.base.project.BaseProjectActivity
import afkt.project.base.project.BaseProjectViewModel
import afkt.project.base.project.ext.bindAdapter
import afkt.project.data_model.button.ButtonList.moduleDevAssistEngineButtonValues
import afkt.project.data_model.button.RouterPath
import afkt.project.databinding.BaseViewRecyclerviewBinding
import android.graphics.Color
import com.therouter.router.Route
import dev.DevUtils
import dev.base.widget.BaseTextView
import dev.engine.DevEngine
import dev.engine.log.DevLogEngine
import dev.engine.log.DevLoggerEngineImpl
import dev.expand.engine.log.log_dTag
import dev.utils.DevFinal
import dev.utils.app.ResourceUtils
import dev.utils.app.SnackbarUtils
import dev.utils.app.SpanUtils
import dev.utils.app.helper.quick.QuickHelper

/**
 * detail: DevAssist Engine 实现
 * @author Ttt
 */
@Route(path = RouterPath.DEV_LIBS.DevAssistEngineActivity_PATH)
class DevAssistEngineActivity :
    BaseProjectActivity<BaseViewRecyclerviewBinding, BaseProjectViewModel>(
        R.layout.base_view_recyclerview, simple_Agile = {
            if (it is DevAssistEngineActivity) {
                it.apply {
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
                        .setTextSizeBySp(15.0F)
                        .setLineSpacingAndMultiplier(15.0F, 1.1F)
                        .setPadding(ResourceUtils.getDimensionInt(R.dimen.dp_20))
                        .setPaddingBottom(ResourceUtils.getDimensionInt(R.dimen.dp_10), false)
                        .getView<BaseTextView>()
                    // 添加 View
                    contentAssist.addContentView(view = view, index = 0)

                    // 初始化布局管理器、适配器
                    binding.vidRv.bindAdapter(moduleDevAssistEngineButtonValues) { buttonValue ->
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

                    val KEY = "QPQPQPQP"
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
            }
        }
    )