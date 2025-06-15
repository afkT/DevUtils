package afkt.project.feature

import afkt.project.R
import afkt.project.base.project.BaseProjectActivity
import afkt.project.base.app.AppViewModel
import afkt.project.base.project.bindAdapter
import afkt.project.base.project.routerActivity
import afkt.project.model.data.button.ButtonList
import afkt.project.model.data.button.ButtonValue
import afkt.project.model.data.button.RouterPath
import afkt.project.databinding.BaseViewRecyclerviewBinding
import com.therouter.router.Route
import dev.utils.app.toast.ToastTintUtils

/**
 * detail: Module 列表 Activity
 * @author Ttt
 */
@Route(path = RouterPath.ModuleActivity_PATH)
class ModuleActivity : BaseProjectActivity<BaseViewRecyclerviewBinding, AppViewModel>(
    R.layout.base_view_recyclerview, simple_Agile = {
        if (it is ModuleActivity) {
            it.apply {
                binding.vidRv.bindAdapter(ButtonList.getModuleButtonValues(it.moduleType)) { buttonValue ->
                    when (buttonValue.type) {
                        ButtonValue.BTN_ROOM,
                        ButtonValue.BTN_GREEN_DAO -> ToastTintUtils.info(
                            "具体请查看: 【DevUtils-repo】application/AppDB"
                        )

                        ButtonValue.BTN_EVENT_BUS,
                        ButtonValue.BTN_GLIDE,
                        ButtonValue.BTN_IMAGE_LOADER,
                        ButtonValue.BTN_GSON,
                        ButtonValue.BTN_FASTJSON,
                        ButtonValue.BTN_ZXING,
                        ButtonValue.BTN_PICTURE_SELECTOR,
                        ButtonValue.BTN_OKGO,
                        ButtonValue.BTN_LUBAN,
                        ButtonValue.BTN_MMKV,
                        ButtonValue.BTN_WORK_MANAGER -> ToastTintUtils.info(
                            "具体请搜索: 【DevUtils-repo】lib/LocalModules/DevOther " + buttonValue.text
                        )

                        else -> buttonValue.routerActivity()
                    }
                }
                // 注册观察者
                registerAdapterDataObserver(binding.vidRv, true)
            }
        }
    }
)