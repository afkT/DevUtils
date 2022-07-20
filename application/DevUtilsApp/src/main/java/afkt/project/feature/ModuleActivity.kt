package afkt.project.feature

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.databinding.BaseViewRecyclerviewBinding
import afkt.project.model.item.ButtonList
import afkt.project.model.item.ButtonValue
import afkt.project.model.item.RouterPath
import com.alibaba.android.arouter.facade.annotation.Route
import dev.callback.DevItemClickCallback
import dev.utils.app.toast.ToastTintUtils

/**
 * detail: Module 列表 Activity
 * @author Ttt
 */
@Route(path = RouterPath.ModuleActivity_PATH)
class ModuleActivity : BaseActivity<BaseViewRecyclerviewBinding>() {

    override fun baseLayoutId(): Int = R.layout.base_view_recyclerview

    override fun initValue() {
        super.initValue()
        // 初始化布局管理器、适配器
        ButtonAdapter(ButtonList.getModuleButtonValues(moduleType))
            .setItemCallback(object : DevItemClickCallback<ButtonValue>() {
                override fun onItemClick(
                    buttonValue: ButtonValue,
                    param: Int
                ) {
                    when (buttonValue.type) {
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
                        else -> routerActivity(buttonValue)
                    }
                }
            }).bindAdapter(binding.vidRv)
        // 注册观察者
        registerAdapterDataObserver(binding.vidRv, true)
    }
}