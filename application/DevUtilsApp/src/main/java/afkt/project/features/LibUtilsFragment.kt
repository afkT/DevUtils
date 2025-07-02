package afkt.project.features

import afkt.project.BR
import afkt.project.R
import afkt.project.base.app.AppFragment
import afkt.project.base.app.AppViewModel
import afkt.project.databinding.FragmentRecyclerViewBinding
import afkt.project.model.data.button.ButtonEnum
import afkt.project.model.data.button.convertItemsLib
import dev.utils.app.toast.ToastTintUtils

class LibUtilsFragment : AppFragment<FragmentRecyclerViewBinding, AppViewModel>(
    R.layout.fragment_recycler_view, BR.viewModel,
    simple_Agile = {
        if (it is LibUtilsFragment) {
            it.viewModel.buttonAdapterModel.apply {
                // 初始化数据
                convertItemsLib()
                // 设置点击事件
                setOnItemClick { btn ->
                    when (btn) {
                        ButtonEnum.BTN_LIB_ROOM, ButtonEnum.BTN_LIB_GREEN_DAO -> ToastTintUtils.info(
                            "具体查看: 【DevUtils-repo】application/AppDB"
                        )

                        else -> ToastTintUtils.info(
                            "具体搜索: 【DevUtils-repo】lib/LocalModules/DevOther ${btn.text}"
                        )
                    }
                }
            }
        }
    }
)