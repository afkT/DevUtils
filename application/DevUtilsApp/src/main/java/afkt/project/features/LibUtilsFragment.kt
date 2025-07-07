package afkt.project.features

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.app.buttonAdapter
import afkt.project.databinding.FragmentRecyclerViewBinding
import afkt.project.model.button.ButtonEnum
import afkt.project.model.button.convertItemsLibUtilsMain
import dev.expand.engine.toast.toast_showShort

class LibUtilsFragment : AppFragment<FragmentRecyclerViewBinding, AppViewModel>(
    R.layout.fragment_recycler_view, BR.viewModel, simple_Agile = { frg ->
        // 初始化数据并设置点击事件
        frg.buttonAdapter()?.convertItemsLibUtilsMain { btn ->
            when (btn) {
                ButtonEnum.BTN_LIB_UTILS_ROOM,
                ButtonEnum.BTN_LIB_UTILS_GREEN_DAO -> toast_showShort(
                    text = "具体查看: 【DevUtils-repo】application/AppDB"
                )

                else -> toast_showShort(
                    text = "具体搜索: 【DevUtils-repo】lib/LocalModules/DevOther ${btn.text}"
                )
            }
        }
    }
)