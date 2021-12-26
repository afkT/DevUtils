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

/**
 * detail: 悬浮窗管理辅助类 ( 需权限 )
 * @author Ttt
 */
@Route(path = RouterPath.FloatingWindowManagerActivity_PATH)
class FloatingWindowManagerActivity : BaseActivity<BaseViewRecyclerviewBinding>() {

    override fun baseLayoutId(): Int = R.layout.base_view_recyclerview

    override fun initValue() {
        super.initValue()

        // 初始化布局管理器、适配器
        ButtonAdapter(ButtonList.floatingWindowButtonValues)
            .setItemCallback(object : DevItemClickCallback<ButtonValue>() {
                override fun onItemClick(
                    buttonValue: ButtonValue,
                    param: Int
                ) {
                    when (buttonValue.type) {
                        ButtonValue.BTN_OPEN_FLOATING_WINDOW -> {

                        }
                        ButtonValue.BTN_CLOSE_FLOATING_WINDOW -> {

                        }
                    }
                }
            }).bindAdapter(binding.vidBvrRecy)
    }
}