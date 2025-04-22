package afkt.project.feature.dev_http_capture

import afkt.project.R
import afkt.project.base.project.BaseProjectActivity
import afkt.project.base.project.BaseProjectViewModel
import afkt.project.base.project.bindAdapter
import afkt.project.data_model.button.ButtonList.moduleDevHttpCaptureButtonValues
import afkt.project.data_model.button.RouterPath
import afkt.project.databinding.BaseViewRecyclerviewBinding
import android.graphics.Color
import androidx.appcompat.widget.AppCompatTextView
import com.therouter.router.Route
import dev.DevHttpCaptureCompiler
import dev.capture.DevHttpCaptureToast
import dev.utils.DevFinal
import dev.utils.app.ResourceUtils
import dev.utils.app.SpanUtils
import dev.utils.app.helper.quick.QuickHelper
import dev.utils.app.toast.ToastTintUtils

/**
 * detail: DevAssist Engine 实现
 * @author Ttt
 */
@Route(path = RouterPath.DEV_LIBS.DevHttpCaptureActivity_PATH)
class DevHttpCaptureActivity :
    BaseProjectActivity<BaseViewRecyclerviewBinding, BaseProjectViewModel>(
        R.layout.base_view_recyclerview, simple_Agile = {
            if (it is DevHttpCaptureActivity) {
                it.apply {
                    val span = SpanUtils().apply {
                        append("该库主要对使用 OkHttp 网络请求库的项目，提供 Http 抓包功能，并支持抓包数据加密存储。")
                        append(DevFinal.SYMBOL.NEW_LINE_X2)
                        appendLine("并且是以 Module ( ModuleName Key ) 为基础，支持组件化不同 Module 各自的抓包功能，")
                        setBold().setForegroundColor(Color.RED)
                        append("支持实时开关抓包功能、可控 Http 拦截过滤器。")
                        setBold().setForegroundColor(Color.BLUE)
                        append(DevFinal.SYMBOL.NEW_LINE_X2)
                        append("内置两个 Http 抓包拦截器，CallbackInterceptor ( 无存储逻辑，进行回调通知 )、")
                        append("HttpCaptureInterceptor ( 存在存储抓包数据逻辑 )")
                        append(DevFinal.SYMBOL.NEW_LINE_X2)
                        append("DevHttpCaptureCompiler")
                        setBold().setForegroundColor(Color.BLUE)
                        append(" 提供对 ")
                        append("DevHttpCapture")
                        setBold().setForegroundColor(Color.RED)
                        append(" 库 抓包库可视化功能")
                    }

                    val view = QuickHelper.get(AppCompatTextView(it))
                        .setText(span.create())
                        .setTextColors(ResourceUtils.getColor(R.color.black))
                        .setTextSizeBySp(15.0F)
                        .setLineSpacingAndMultiplier(15.0F, 1.1F)
                        .setPadding(ResourceUtils.getDimensionInt(R.dimen.dp_20))
                        .setPaddingBottom(ResourceUtils.getDimensionInt(R.dimen.dp_10), false)
                        .getView<AppCompatTextView>()
                    // 添加 View
                    contentAssist.addContentView(view = view, index = 0)

                    // 初始化布局管理器、适配器
                    binding.vidRv.bindAdapter(moduleDevHttpCaptureButtonValues) { buttonValue ->
                        DevHttpCaptureCompiler.start(this)

                        // 修改内部 Toast 样式【可不设置, 默认使用系统 Toast】
                        DevHttpCaptureCompiler.setToastIMPL(object : DevHttpCaptureToast {
                            override fun normal(id: Int) {
                                ToastTintUtils.normal(ResourceUtils.getString(id))
                            }

                            override fun success(id: Int) {
                                ToastTintUtils.success(ResourceUtils.getString(id))
                            }
                        })
                    }
                }
            }
        }
    )