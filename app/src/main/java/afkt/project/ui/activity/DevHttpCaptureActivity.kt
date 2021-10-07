package afkt.project.ui.activity

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.base.config.RouterPath
import afkt.project.databinding.BaseViewRecyclerviewBinding
import android.graphics.Color
import com.alibaba.android.arouter.facade.annotation.Route
import dev.base.widget.BaseTextView
import dev.utils.DevFinal
import dev.utils.app.ResourceUtils
import dev.utils.app.SpanUtils
import dev.utils.app.helper.quick.QuickHelper

/**
 * detail: DevAssist Engine 实现
 * @author Ttt
 */
@Route(path = RouterPath.DevHttpCaptureActivity_PATH)
class DevHttpCaptureActivity : BaseActivity<BaseViewRecyclerviewBinding>() {

    override fun baseLayoutId(): Int = R.layout.base_view_recyclerview

    override fun initValue() {
        super.initValue()

        val span = SpanUtils().apply {
            append("该库主要对使用 Okhttp 网络请求库的项目，提供 Http 抓包功能，并支持抓包数据加密存储。")
            append(DevFinal.NEW_LINE_STR_X2)
            appendLine("并且是以 Module ( ModuleName Key ) 为基础，支持组件化不同 Module 各自的抓包功能，")
            setBold().setForegroundColor(Color.RED)
            append("支持实时开关抓包功能、可控 Http 拦截过滤器。")
            setBold().setForegroundColor(Color.BLUE)
            append(DevFinal.NEW_LINE_STR_X2)
            append("内置两个 Http 抓包拦截器，CallbackInterceptor ( 无存储逻辑，进行回调通知 )、")
            append("HttpCaptureInterceptor ( 存在存储抓包数据逻辑 )")
            append(DevFinal.NEW_LINE_STR_X2)
            append("DevHttpCaptureCompiler")
            setBold().setForegroundColor(Color.BLUE)
            append(" 提供对 ")
            append("DevHttpCapture")
            setBold().setForegroundColor(Color.RED)
            append(" 库 抓包库可视化功能")
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
    }
}