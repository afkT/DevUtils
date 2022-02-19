package afkt.project.feature.dev_widget.recy_item_decoration.linear

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.databinding.ActivityLinearItemDecorationBinding
import afkt.project.model.item.RouterPath
import com.alibaba.android.arouter.facade.annotation.Route

/**
 * detail: Linear ItemDecoration
 * @author Ttt
 */
@Route(path = RouterPath.DEV_WIDGET.LinearItemDecorationActivity_PATH)
class LinearItemDecorationActivity : BaseActivity<ActivityLinearItemDecorationBinding>() {

    override fun baseLayoutId(): Int = R.layout.activity_linear_item_decoration

    override fun initValue() {
        super.initValue()
    }
}