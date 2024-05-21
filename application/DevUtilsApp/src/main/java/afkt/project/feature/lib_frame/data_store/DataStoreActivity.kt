package afkt.project.feature.lib_frame.data_store

import afkt.project.R
import afkt.project.base.project.BaseProjectActivity
import afkt.project.base.project.BaseProjectViewModel
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
class DataStoreActivity : BaseProjectActivity<ActivityDataStoreBinding, BaseProjectViewModel>(
    R.layout.activity_data_store, simple_Agile = {
        if (it is DataStoreActivity) {
            it.apply {
                lifecycleScope.launch {
                    DataStoreUse.use(it)
                }
            }
        }
    }
)