package afkt.project.feature.other_function.dev_function

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.base.project.BaseProjectActivity
import afkt.project.base.project.BaseProjectViewModel
import afkt.project.data_model.button.RouterPath
import afkt.project.databinding.ActivityActivityResultApiBinding
import android.Manifest
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityOptionsCompat
import com.therouter.router.Route
import dev.engine.permission.IPermissionEngine
import dev.expand.engine.image.display
import dev.expand.engine.log.log_dTag
import dev.expand.engine.permission.permission_againRequest
import dev.expand.engine.permission.permission_request
import dev.mvvm.utils.toSource
import dev.utils.app.MediaStoreUtils
import dev.utils.app.activity_result.ActivityResultAssist
import dev.utils.app.toast.ToastTintUtils

/**
 * detail: Activity Result API
 * @author Ttt
 */
@Route(path = RouterPath.OTHER_FUNCTION.ActivityResultAPIActivity_PATH)
class ActivityResultAPIActivity : BaseProjectActivity<ActivityActivityResultApiBinding, BaseProjectViewModel>(
    R.layout.activity_activity_result_api, simple_Agile = {
        if (it is ActivityResultAPIActivity) {
            it.apply {
                binding.vidTakeBtn.setOnClickListener {
                    permission_request(
                        permissions = arrayOf(Manifest.permission.CAMERA),
                        callback = object : IPermissionEngine.Callback {
                            override fun onGranted() {
                                mAssist?.launch(MediaStoreUtils.createImageUri())
                            }

                            override fun onDenied(
                                grantedList: MutableList<String>?,
                                deniedList: MutableList<String>?,
                                notFoundList: MutableList<String>?
                            ) {
                                // 拒绝了则再次请求处理
                                permission_againRequest(
                                    callback = this,
                                    deniedList = deniedList
                                )
                                ToastTintUtils.warning("拍照需摄像头权限")
                            }
                        }
                    )
                }
            }
        }
    }
) {

    // 如果使用 by lazy 要确保在 Lifecycle STARTED 状态前初始化
    private var mAssist: ActivityResultAssist<Uri, Boolean>? = null

    init {
        mAssist = ActivityResultAssist(
            this, ActivityResultContracts.TakePicture()
        ) {
            if (it) {
                binding.vidIv.display(source = mAssist?.inputValue?.toSource())
            } else {
                ToastTintUtils.warning("非成功操作")
            }
        }.setOperateCallback(object : ActivityResultAssist.OperateCallback<Uri>() {

            // ======================================
            // = 该回调可不设置, 只是提供此功能便于特殊需求 =
            // ======================================

            override fun onStart(
                assist: ActivityResultAssist<Uri, *>,
                type: Int,
                input: Uri?,
                options: ActivityOptionsCompat?
            ) {
                TAG.log_dTag(
                    message = "开始调用 ${ActivityResultAssist.getMethodType(type)} 方法前"
                )
            }

            override fun onState(
                assist: ActivityResultAssist<Uri, *>,
                type: Int,
                input: Uri?,
                options: ActivityOptionsCompat?,
                result: Boolean
            ) {
                TAG.log_dTag(
                    message = "调用 ${ActivityResultAssist.getMethodType(type)} 方法后, 调用结果: $result"
                )
            }
        })
    }
}