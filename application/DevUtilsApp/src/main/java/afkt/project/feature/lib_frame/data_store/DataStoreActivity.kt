package afkt.project.feature.lib_frame.data_store

import afkt.project.R
import afkt.project.app.AppViewModel
import afkt.project.app.project.BaseProjectActivity
import afkt.project.databinding.ActivityDataStoreBinding
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

/**
 * detail: DataStore Use Activity
 * @author Ttt
 */
class DataStoreActivity : BaseProjectActivity<ActivityDataStoreBinding, AppViewModel>(
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