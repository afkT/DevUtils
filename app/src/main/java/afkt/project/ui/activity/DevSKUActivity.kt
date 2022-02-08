package afkt.project.ui.activity

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.base.config.RouterPath
import afkt.project.databinding.BaseViewRecyclerviewBinding
import afkt.project.model.item.ButtonList
import afkt.project.model.item.ButtonValue
import afkt.project.ui.adapter.ButtonAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import dev.callback.DevItemClickCallback
import dev.utils.app.toast.ToastTintUtils

/**
 * detail: DevSKU 商品 SKU 组合封装实现
 * @author Ttt
 */
@Route(path = RouterPath.DevSKUActivity_PATH)
class DevSKUActivity : BaseActivity<BaseViewRecyclerviewBinding>() {

    override fun baseLayoutId(): Int = R.layout.base_view_recyclerview

    override fun initValue() {
        super.initValue()
        // 初始化布局管理器、适配器
        ButtonAdapter(ButtonList.moduleDevSKUButtonValues)
            .setItemCallback(object : DevItemClickCallback<ButtonValue>() {
                override fun onItemClick(
                    buttonValue: ButtonValue,
                    param: Int
                ) {
                    when (buttonValue.type) {
                        ButtonValue.BTN_SKU_DIALOG -> {
                            ToastTintUtils.success("SKU Dialog")
                        }
                        else -> routerActivity(buttonValue)
                    }
                }
            }).bindAdapter(binding.vidRv)
    }
}