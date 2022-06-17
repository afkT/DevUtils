package afkt.project.feature.lib_frame.data_store

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.databinding.BaseViewRecyclerviewBinding
import afkt.project.model.item.RouterPath
import com.alibaba.android.arouter.facade.annotation.Route

/**
 * detail: DataStore Use Activity
 * @author Ttt
 */
@Route(path = RouterPath.LIB_FRAME.DataStoreActivity_PATH)
class DataStoreActivity : BaseActivity<BaseViewRecyclerviewBinding>() {

    override fun baseLayoutId(): Int = R.layout.base_view_recyclerview

    override fun initValue() {
        super.initValue()
    }
}