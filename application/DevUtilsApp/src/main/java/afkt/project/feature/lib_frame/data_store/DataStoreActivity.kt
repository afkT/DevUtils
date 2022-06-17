package afkt.project.feature.lib_frame.data_store

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.databinding.ActivityDataStoreBinding
import afkt.project.model.item.RouterPath
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import kotlinx.coroutines.launch

/**
 * detail: DataStore Use Activity
 * @author Ttt
 */
@Route(path = RouterPath.LIB_FRAME.DataStoreActivity_PATH)
class DataStoreActivity : BaseActivity<ActivityDataStoreBinding>() {

    override fun baseLayoutId(): Int = R.layout.activity_data_store

    override fun initValue() {
        super.initValue()

        lifecycleScope.launch {
            DataStoreUse.use(this@DataStoreActivity)
        }
    }
}