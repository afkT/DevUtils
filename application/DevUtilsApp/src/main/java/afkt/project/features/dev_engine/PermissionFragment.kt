package afkt.project.features.dev_engine

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentDevEnginePermissionBinding
import android.view.View
import com.hjq.permissions.permission.PermissionLists
import dev.engine.core.permission.PermissionItem
import dev.engine.core.permission.toPermissionItem
import dev.engine.extensions.permission.permission_isGrantedPermission
import dev.engine.extensions.permission.permission_request
import dev.engine.extensions.toast.toast_showShort
import dev.engine.permission.IPermissionEngine
import dev.utils.app.ActivityUtils

/**
 * detail: Permission Engine 权限申请
 * @author Ttt
 */
class PermissionFragment : AppFragment<FragmentDevEnginePermissionBinding, PermissionViewModel>(
    R.layout.fragment_dev_engine_permission, BR.viewModel
)

/**
 * detail: Permission Engine 权限申请 ViewModel
 * @author Ttt
 */
class PermissionViewModel : AppViewModel() {

    val clickRequestCamera = View.OnClickListener { view ->
        ActivityUtils.getActivity(view)?.permission_request(
            permission = PermissionLists.getCameraPermission().toPermissionItem(),
            callback = IPermissionEngine.Callback<PermissionItem> { grantedList, deniedList ->
                if (deniedList.isEmpty()) "相机权限申请通过" else "相机权限申请被拒绝".toast_showShort()
            }
        )
    }

    val clickCheckCamera = View.OnClickListener { view ->
        val granted = view.context.permission_isGrantedPermission(
            permission = PermissionLists.getCameraPermission().toPermissionItem()
        )
        "是否已授予相机权限: $granted".toast_showShort()
    }
}