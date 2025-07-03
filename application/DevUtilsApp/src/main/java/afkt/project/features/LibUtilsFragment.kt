package afkt.project.features

import afkt.project.BR
import afkt.project.R
import afkt.project.base.app.AppFragment
import afkt.project.base.app.AppViewModel
import afkt.project.base.app.applyToButtonAdapter
import afkt.project.databinding.FragmentRecyclerViewBinding
import afkt.project.model.data.button.ButtonEnum
import afkt.project.model.data.button.convertItemsLib
import dev.expand.engine.toast.toast_showShort

class LibUtilsFragment : AppFragment<FragmentRecyclerViewBinding, AppViewModel>(
    R.layout.fragment_recycler_view, BR.viewModel,
    simple_Agile = { frg ->
        frg.applyToButtonAdapter<LibUtilsFragment> {
            // 初始化数据并设置点击事件
            convertItemsLib().setOnItemClick { btn ->
                when (btn) {
                    ButtonEnum.BTN_LIB_ROOM, ButtonEnum.BTN_LIB_GREEN_DAO -> toast_showShort(
                        text = "具体查看: 【DevUtils-repo】application/AppDB"
                    )

                    else -> toast_showShort(
                        text = "具体搜索: 【DevUtils-repo】lib/LocalModules/DevOther ${btn.text}"
                    )
                }
            }
        }
    }
)