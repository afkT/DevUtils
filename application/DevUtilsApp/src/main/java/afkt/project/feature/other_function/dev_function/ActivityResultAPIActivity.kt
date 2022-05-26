package afkt.project.feature.other_function.dev_function

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.databinding.ActivityActivityResultApiBinding
import afkt.project.model.item.RouterPath
import android.Manifest
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityOptionsCompat
import com.alibaba.android.arouter.facade.annotation.Route
import dev.engine.permission.IPermissionEngine
import dev.kotlin.engine.image.display
import dev.kotlin.engine.log.log_dTag
import dev.kotlin.engine.permission.permission_againRequest
import dev.kotlin.engine.permission.permission_request
import dev.kotlin.utils.toSource
import dev.utils.app.MediaStoreUtils
import dev.utils.app.activity_result.ActivityResultAssist
import dev.utils.app.toast.ToastTintUtils

/**
 * detail: Activity Result API
 * @author Ttt
 */
@Route(path = RouterPath.OTHER_FUNCTION.ActivityResultAPIActivity_PATH)
class ActivityResultAPIActivity : BaseActivity<ActivityActivityResultApiBinding>() {

    override fun baseLayoutId(): Int = R.layout.activity_activity_result_api

    override fun initListener() {
        super.initListener()
        binding.vidTakeBtn.setOnClickListener {
            permission_request(
                permissions = arrayOf(Manifest.permission.CAMERA),
                callback = object : IPermissionEngine.Callback {
                    override fun onGranted() {
                        mAssist.launch(MediaStoreUtils.createImageUri())
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

    private fun getAssist(): ActivityResultAssist<Uri, Boolean> {
        return mAssist
    }

    private val mAssist: ActivityResultAssist<Uri, Boolean> = ActivityResultAssist(
        this, ActivityResultContracts.TakePicture()
    ) {
        if (it) {
            binding.vidIv.display(source = getAssist().inputValue?.toSource())
        } else {
            ToastTintUtils.warning("没有进行拍照")
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