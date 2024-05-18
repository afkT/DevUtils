package afkt.project.feature.lib_frame.data_store

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.data_model.button.RouterPath
import afkt.project.databinding.ActivityDataStoreBinding
import androidx.lifecycle.lifecycleScope
import com.therouter.router.Route
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