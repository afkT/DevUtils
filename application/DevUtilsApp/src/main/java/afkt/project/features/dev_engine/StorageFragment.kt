package afkt.project.features.dev_engine

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentDevEngineStorageBinding
import android.graphics.BitmapFactory
import android.view.View
import dev.base.DevSource
import dev.engine.core.storage.OnDevInsertListener
import dev.engine.core.storage.StorageItem
import dev.engine.core.storage.StorageResult
import dev.engine.extensions.storage.storage_insertImageToExternal
import dev.engine.extensions.toast.toast_showShort

/**
 * detail: Storage Engine 外部、内部文件存储
 * @author Ttt
 */
class StorageFragment : AppFragment<FragmentDevEngineStorageBinding, StorageViewModel>(
    R.layout.fragment_dev_engine_storage, BR.viewModel
)

/**
 * detail: Storage Engine 外部、内部文件存储 ViewModel
 * @author Ttt
 */
class StorageViewModel : AppViewModel() {

    val clickInsertImageToExternal = View.OnClickListener { view ->
        val bitmap = BitmapFactory.decodeResource(view.resources, R.mipmap.icon_launcher)
        val fileName = "dev_engine_storage_${System.currentTimeMillis()}.jpg"
        DevSource.create(bitmap).storage_insertImageToExternal(
            params = StorageItem.createExternalItem(fileName),
            listener = object : OnDevInsertListener {
                override fun onResult(
                    result: StorageResult,
                    params: StorageItem?,
                    source: DevSource?
                ) {
                    if (result.isSuccess()) {
                        "图片已存储至外部相册"
                    } else {
                        "图片存储失败"
                    }.toast_showShort()
                }
            }
        )
    }
}