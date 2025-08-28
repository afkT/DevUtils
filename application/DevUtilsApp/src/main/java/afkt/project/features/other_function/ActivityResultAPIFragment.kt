package afkt.project.features.other_function

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentOtherFunctionActivityResultApiBinding
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityOptionsCompat
import androidx.databinding.ObservableField
import dev.base.DevSource
import dev.base.simple.extensions.asFragment
import dev.engine.extensions.log.log_dTag
import dev.engine.extensions.permission.permission_againRequest
import dev.engine.extensions.permission.permission_request
import dev.engine.extensions.toast.toast_showShort
import dev.engine.permission.IPermissionEngine
import dev.simple.core.utils.hi.hiif.hiIfNotNull
import dev.simple.core.utils.toSource
import dev.utils.app.ActivityUtils
import dev.utils.app.IntentUtils
import dev.utils.app.MediaStoreUtils
import dev.utils.app.activity_result.ActivityResultAssist

/**
 * detail: Activity Result API
 * @author Ttt
 */
class ActivityResultAPIFragment :
    AppFragment<FragmentOtherFunctionActivityResultApiBinding, ActivityResultAPIViewModel>(
        R.layout.fragment_other_function_activity_result_api,
        BR.viewModel, simple_Agile = { frg ->
            frg.asFragment<ActivityResultAPIFragment> {
                viewModel.initializeTakePictures(this)
            }
        }
    )

class ActivityResultAPIViewModel : AppViewModel() {

    // 拍照图片 Uri
    val imageUri = ObservableField(DevSource.create(""))

    // 如果使用 by lazy 要确保在 Lifecycle STARTED 状态前初始化
    private var mAssist: ActivityResultAssist<Uri, Boolean>? = null

    /**
     * 初始化【拍照】回传处理
     */
    fun initializeTakePictures(caller: ActivityResultCaller) {
        mAssist = ActivityResultAssist(
            caller, ActivityResultContracts.TakePicture()
        ) {
            if (it) {
                imageUri.set(mAssist?.inputValue?.toSource())
            } else {
                toast_showShort(text = "非成功操作")
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

    // 点击拍照
    val clickTakePictures = View.OnClickListener { view ->
        val activity = ActivityUtils.getActivity(view)
        activity.permission_request(
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
                    activity.permission_againRequest(
                        callback = this,
                        deniedList = deniedList
                    )
                    toast_showShort(text = "拍照需摄像头权限")
                }
            }
        )
    }

    // =========================
    // = 跳转 Intent 回传 Result =
    // =========================

    // 跳转回传辅助类
    private var intentResultAssist: ActivityResultAssist<Intent, ActivityResult>? = null

    /**
     * 初始化【跳转 Intent】回传处理
     */
    fun initializeIntentResult(caller: ActivityResultCaller) {
        this.intentResultAssist = ActivityResultAssist(
            caller, ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.data != null) {
                // 跳转回传值
                val intent = result.data
            }
            // 或者判断
            if (result.resultCode == Activity.RESULT_OK) {
                // 操作成功
            }
        }
    }

    private fun intentRouter() {
        intentResultAssist.hiIfNotNull {
            val intent = IntentUtils.getManageOverlayPermissionIntent()
            it.launch(intent)
        }
    }
}