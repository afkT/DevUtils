package afkt.project.feature.dev_widget.view_assist

import afkt.project.R
import android.view.LayoutInflater
import android.view.View
import dev.widget.assist.ViewAssist

/**
 * detail: ViewAssist Adapter 工具类
 * @author Ttt
 */
object ViewAssistUtils {

    /**
     * 注册 Recycler Loading type
     * @param viewAssist [ViewAssist]
     * @param listener   点击事件
     */
    fun registerRecyclerLoading(
        viewAssist: ViewAssist?,
        listener: View.OnClickListener?
    ) {
        viewAssist?.let {
            // 设置加载中样式
            it.register(ViewAssist.TYPE_ING, object : ViewAssist.Adapter {
                override fun onCreateView(
                    assist: ViewAssist,
                    inflater: LayoutInflater
                ): View? {
                    return inflater.inflate(R.layout.view_assist_recy_loading, null)
                }

                override fun onBindView(
                    assist: ViewAssist,
                    view: View,
                    type: Int
                ) {
                }
            })
            // 设置加载成功样式
            it.register(ViewAssist.TYPE_SUCCESS, object : ViewAssist.Adapter {
                override fun onCreateView(
                    assist: ViewAssist,
                    inflater: LayoutInflater
                ): View? {
                    return null
                }

                override fun onBindView(
                    assist: ViewAssist,
                    view: View,
                    type: Int
                ) {
                    // 可以设置渐变动画, 并在结束时隐藏根布局 -> assist.goneWrapper()
                    assist.goneWrapper()
                }
            })
            // 设置加载失败样式
            it.register(ViewAssist.TYPE_FAILED, object : ViewAssist.Adapter {
                override fun onCreateView(
                    assist: ViewAssist,
                    inflater: LayoutInflater
                ): View? {
                    return inflater.inflate(R.layout.view_assist_recy_failed, null)
                }

                override fun onBindView(
                    assist: ViewAssist,
                    view: View,
                    type: Int
                ) {
                    listener?.apply { view.setOnClickListener(this) }
                }
            })
        }
    }
}