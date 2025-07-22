package afkt.project.features.dev_widget.view_assist

import afkt.project.R
import afkt.project.app.AppViewModel
import afkt.project.app.project.BaseProjectActivity
import afkt.project.databinding.ActivityViewAssistBinding
import android.view.LayoutInflater
import android.view.View
import dev.expand.engine.toast.toast_showShort
import dev.mvvm.utils.size.AppSize
import dev.utils.app.HandlerUtils
import dev.utils.app.ListenerUtils
import dev.utils.app.ViewUtils
import dev.widget.assist.ViewAssist

/**
 * detail: ViewAssist Activity
 * @author Ttt
 */
class ViewAssistActivity : BaseProjectActivity<ActivityViewAssistBinding, AppViewModel>(
    R.layout.activity_view_assist, simple_Agile = {
        if (it is ViewAssistActivity) {
            it.apply {
                viewAssist = ViewAssist.wrap(binding.vidFl)

//                when (moduleType) {
//                    ButtonValue.BTN_VIEW_ASSIST_ERROR -> errorType()
//                    ButtonValue.BTN_VIEW_ASSIST_EMPTY -> emptyType()
//                    ButtonValue.BTN_VIEW_ASSIST_CUSTOM -> customType()
//                }
            }
        }
    }
) {

    private lateinit var viewAssist: ViewAssist

    private fun errorType() {
        ViewUtils.setPadding(binding.vidFl, AppSize.dp2px(50F))
        viewAssist.register(ViewAssist.TYPE_ING, object : ViewAssist.Adapter {
            override fun onCreateView(
                assist: ViewAssist,
                inflater: LayoutInflater
            ): View {
                return inflater.inflate(R.layout.view_assist_loading, null)
            }

            override fun onBindView(
                assist: ViewAssist,
                view: View,
                type: Int
            ) {
                val isContent = assist.tag == null
                HandlerUtils.postRunnable({
                    if (isContent) {
                        assist.showType(100)
                    } else {
                        assist.showType(200)
                    }
                }, if (isContent) 3000 else 2000.toLong())
            }
        }).register(100, object : ViewAssist.Adapter {
            override fun onCreateView(
                assist: ViewAssist,
                inflater: LayoutInflater
            ): View {
                return inflater.inflate(R.layout.view_assist_error, null)
            }

            override fun onBindView(
                assist: ViewAssist,
                view: View,
                type: Int
            ) {
                ListenerUtils.setOnClicks({
                    toast_showShort(text = "click retry")
                    assist.setTag("").showIng()
                }, view)
            }
        }).register(200, object : ViewAssist.Adapter {
            override fun onCreateView(
                assist: ViewAssist,
                inflater: LayoutInflater
            ): View {
                return inflater.inflate(R.layout.view_assist_content, null)
            }

            override fun onBindView(
                assist: ViewAssist,
                view: View,
                type: Int
            ) {
            }
        }).showIng()
    }

    private fun emptyType() {
        viewAssist.register(ViewAssist.TYPE_ING, object : ViewAssist.Adapter {
            override fun onCreateView(
                assist: ViewAssist,
                inflater: LayoutInflater
            ): View {
                return inflater.inflate(R.layout.view_assist_loading2, null)
            }

            override fun onBindView(
                assist: ViewAssist,
                view: View,
                type: Int
            ) {
                HandlerUtils.postRunnable({ assist.showType(Int.MAX_VALUE) }, 3000)
            }
        }).register(Int.MAX_VALUE, object : ViewAssist.Adapter {
            override fun onCreateView(
                assist: ViewAssist,
                inflater: LayoutInflater
            ): View {
                return inflater.inflate(R.layout.view_assist_empty, null)
            }

            override fun onBindView(
                assist: ViewAssist,
                view: View,
                type: Int
            ) {
            }
        }).showIng()
    }

    private fun customType() {
        viewAssist.register(ViewAssist.TYPE_ING, object : ViewAssist.Adapter {
            override fun onCreateView(
                assist: ViewAssist,
                inflater: LayoutInflater
            ): View {
                return inflater.inflate(R.layout.view_assist_loading3, null)
            }

            override fun onBindView(
                assist: ViewAssist,
                view: View,
                type: Int
            ) {
                HandlerUtils.postRunnable({ assist.showType(159) }, 3000)
            }
        }).register(159, object : ViewAssist.Adapter {
            override fun onCreateView(
                assist: ViewAssist,
                inflater: LayoutInflater
            ): View {
                return inflater.inflate(R.layout.view_assist_custom, null)
            }

            override fun onBindView(
                assist: ViewAssist,
                view: View,
                type: Int
            ) {
                ListenerUtils.setOnClicks(
                    { toast_showShort(text = "Custom Type") },
                    view.findViewById(R.id.vid_cv)
                )
            }
        }).showIng()
    }
}