package afkt.project.ui.activity

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.databinding.ActivityViewAssistBinding
import afkt.project.model.item.ButtonValue
import android.view.LayoutInflater
import android.view.View
import dev.utils.app.HandlerUtils
import dev.utils.app.ListenerUtils
import dev.utils.app.SizeUtils
import dev.utils.app.ViewUtils
import dev.utils.app.toast.ToastTintUtils
import dev.widget.assist.ViewAssist

/**
 * detail: ViewAssist Activity
 * @author Ttt
 */
class ViewAssistActivity : BaseActivity<ActivityViewAssistBinding>() {

    lateinit var viewAssist: ViewAssist

    override fun baseLayoutId(): Int = R.layout.activity_view_assist

    override fun initValue() {
        super.initValue()

        viewAssist = ViewAssist.wrap(binding.vidAvaFrame)

        when (getModuleType()) {
            ButtonValue.BTN_VIEW_ASSIST_ERROR -> errorType()
            ButtonValue.BTN_VIEW_ASSIST_EMPTY -> emptyType()
            ButtonValue.BTN_VIEW_ASSIST_CUSTOM -> customType()
        }
    }

    private fun errorType() {
        ViewUtils.setPadding(binding.vidAvaFrame, SizeUtils.dipConvertPx(50f))
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
                    ToastTintUtils.normal("click retry")
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
                    { ToastTintUtils.normal("Custom Type") },
                    view.findViewById<View>(R.id.vid_vac_cardview)
                )
            }
        }).showIng()
    }
}